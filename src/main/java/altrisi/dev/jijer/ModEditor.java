package altrisi.dev.jijer;

import java.io.IOException;
import java.nio.file.*;
import java.util.Map;

import mjson.Json;

import static altrisi.dev.jijer.JijerMain.OUT;

public class ModEditor implements AutoCloseable {
	private final FileSystem fileSystem;
	private final Path jsonPath;
	private final Json jsonContents;
	private final Path jarsFolder;
	
	public ModEditor(Path modPath) throws IOException {
		this.fileSystem =  FileSystems.newFileSystem(modPath, Map.of());
    	this.jsonPath = fileSystem.getPath("fabric.mod.json");
    	this.jsonContents = Json.read(Files.readString(jsonPath));
    	this.jarsFolder = fileSystem.getPath("META-INF/jars");
	}
	
	public void addJar(Path jarPath) throws IOException {
		String jarName = jarPath.getFileName().toString();
		OUT.println("Adding " + jarName + " to mod jar...");
		Path destination = jarsFolder.resolve(jarName);
		Files.createDirectories(jarsFolder);
		Files.copy(jarPath, destination, StandardCopyOption.REPLACE_EXISTING);
		Json jars = getOrAddEmpty(jsonContents, "jars");
		Json entry = Json.object().set("file", "META-INF/jars/" + jarName);
		if (!jars.asJsonList().contains(entry)) {
			jars.add(entry);
		} else {
			OUT.println("Replacing existing file...");
		}
	}
	
	@Override
	public void close() throws IOException {
		// We save here because I believe Java anyways doesn't write to the zip until closing
		Files.writeString(jsonPath, jsonContents.toString());
		fileSystem.close();
	}
	
	private Json getOrAddEmpty(Json json, String path) {
		Json at = json.at(path);
		if (at == null) {
			Json ret = Json.array();
			json.set(path, ret);
			if (!ret.isArray()) throw null;
			return ret;
		}
		if (!at.isArray()) throw null;
		return at;
	}
}
