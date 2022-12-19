package main;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;

public class PuzzleReflectionFactory {

	private static final String ROOT_PACKAGE_NAME = "puzzles";
	private static PuzzleReflectionFactory instance;

	public static PuzzleReflectionFactory getInstance() {
		if(instance == null) {
			instance = new PuzzleReflectionFactory(ROOT_PACKAGE_NAME);
		}

		return instance;
	}

	private String[] puzzleRegistrations;
	private int latestDay = 0;

	private PuzzleReflectionFactory(String rootPackageName) {
		try {
			String[] classNames;
			this.puzzleRegistrations = new String[PuzzleMaster.NUM_PUZZLES];

			for(int day = 0; day < PuzzleMaster.NUM_DAYS; day ++) {
				String packageName = new StringBuilder(ROOT_PACKAGE_NAME).append(".day").append(day).toString();
				classNames = this.getClasses(packageName);
				this.registerClasses(classNames);
			}

			return;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	private String[] getClasses(String packageName) {
		try {
			ClassLoader classLoader;
			ArrayList<String> retVal;
			ArrayList<File> classFiles;
			Enumeration<URL> resources;
			retVal = new ArrayList<>();
			classFiles = new ArrayList<>();
			classLoader = this.getClass().getClassLoader();
			resources = classLoader.getResources(packageName.replace('.', '/'));
			while (resources.hasMoreElements()) {
				File parent = new File(resources.nextElement().getFile());
				if (parent.exists()) {
					if (parent.canRead()) {
						if (parent.isDirectory()) {
							for (File child : parent.listFiles()) {
								if (child.exists()) {
									if (child.canRead()) {
										if (child.isFile()) {
											if (child.getName().endsWith(".class")) {
												classFiles.add(child);
											}
										}
									}
								}
							}
						} else if (parent.isFile()) {
							if (parent.getName().endsWith(".class")) {
								classFiles.add(parent);
							}
						}
					}
				}
			}
			for (File classFile : classFiles) {
				retVal.add(packageName + '.' + classFile.getName().substring(0, classFile.getName().length() - 6));
			}
			return retVal.toArray(new String[retVal.size()]);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public int getLatestDay() {
		return this.latestDay;
	}

	public Puzzle getLatestPuzzle() {
		return getPuzzle(latestDay);
	}

	public Puzzle getPuzzle(int day) {
		if(this.puzzleRegistrations[day - 1] == null) {
			System.err.println("Cannot find day " + day + "!");
			return null;
		}

		try {
			return (Puzzle) Class.forName(this.puzzleRegistrations[day - 1]).getConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private void registerClasses(String[] classNames) {
		try {
			for (String className : classNames) {
				Class<?> c = Class.forName(className);
				Annotation[] annotations = c.getAnnotations();

				for(Annotation annotation : annotations){
				    if(annotation instanceof Solution){
				    	Solution solution = (Solution) annotation;
				    	int day = solution.day();
						if(day > latestDay) latestDay = day;
						puzzleRegistrations[day - 1] = className;
				    }
				}
			}
			return;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

}
