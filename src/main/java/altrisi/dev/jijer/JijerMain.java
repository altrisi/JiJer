package altrisi.dev.jijer;

import java.io.*;
import java.nio.file.*;

public class JijerMain {
	private static final String VERSION = "1.0.0";
	private static final NoThrowBufferedReader IN = new NoThrowBufferedReader(new InputStreamReader(System.in));
	static final PrintStream OUT = System.out;
    
    public static void main(String[] args) {
    	if (args.length > 0) throw new IllegalArgumentException("There is no implemented behaviour for passing arguments!");
        OUT.println("JiJer " + VERSION);
        Path modJar;
        OUT.print("Path to the mod jar: ");
        do {
        	modJar = Paths.get(upgradeInput(IN.readLine()));
        } while (!valid(modJar, false));
        
        OUT.print("Path to the jar to add (or directory with jars): ");
        Path pathToAdd;
        do {
        	pathToAdd = Paths.get(upgradeInput(IN.readLine()));
        } while (!valid(pathToAdd, true));
        
        try (ModEditor mod = new ModEditor(modJar)) {
        	if (Files.isDirectory(pathToAdd)) {
        		for (Path jar : Files.newDirectoryStream(pathToAdd, p -> p.toString().endsWith(".jar"))) {
        			mod.addJar(jar);
        		}
        	} else {
        		mod.addJar(pathToAdd);
        	}
        } catch (IOException e) {
        	throw new UncheckedIOException("Failed to add the jars!", e);
        }
        OUT.println("Operation completed successfully!");
    }
    
    private static boolean valid(Path path, boolean allowDirectory) {
    	boolean valid = Files.exists(path);
    	valid &= allowDirectory || (!Files.isDirectory(path)); // disallow directories if not allowed
    	valid &= Files.isDirectory(path) || path.getFileName().toString().endsWith(".jar"); // must be .jar if not directory
    	
    	if (!valid) OUT.println("Enter a valid path.");
    	return valid;
    }
    
    private static String upgradeInput(String input) {
    	input = input.trim();
    	if (input.charAt(0) == '"') {
    		// Unquote string
    		input = input.substring(1, input.length()-1);
    	}
    	
    	return input;
    }
}