package main;

import java.io.File;
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
			retVal = new ArrayList<String>();
			classFiles = new ArrayList<File>();
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
			return (String[]) retVal.toArray(new String[retVal.size()]);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Puzzle getPuzzle(int id) {
		try {
			return (Puzzle) Class.forName(this.puzzleRegistrations[id]).getConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private void registerClasses(String[] classNames) {
		try {
			for (String className : classNames) {
				System.out.println(className);
				Puzzle puzzle = (Puzzle) Class.forName(className).getConstructor().newInstance();
				puzzleRegistrations[puzzle.getID()] = className;
			}
			return;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
}
