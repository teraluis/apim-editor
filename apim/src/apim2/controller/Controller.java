package apim2.controller;

import java.io.File;
import java.util.Arrays;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import apim2.manager.FileEditor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Controller {
	private String name;
	private String version;
	private String path;
	private String product;
	private String api;
	private static String EXT = ".yaml";
	private static String SLASH = "/";
	
	private static final Logger logger = Logger.getLogger(Controller.class.getName());
	
	public void changeVersion() {
		editProducts();
		editInterfaces();
		renameApisFolders();
	}

	private void editProducts() {		
		editProductFile(getFileProduct());
		renameProductFile(getFileProduct());
		
	}

	private void editInterfaces() {
		FileEditor editor = new FileEditor(getVersion(), getProduct(), getApi());
		final File fichier = new File(getDirectoryApi().getPath() + "/interface.yaml");
		editor.edit(fichier);
	}

	private void renameApisFolders() {
		final String oldName = getDirectoryApi().getName().split("_")[0];
		final Boolean isRenamed = getDirectoryApi().renameTo(new File(getPath() + "/apis/" + oldName + "_v" + getVersion() + EXT));
		if(Boolean.TRUE.equals(isRenamed)) logger.info("renamed");
	}
	
	private File getFileProduct() {
		return Arrays.stream(new File(getPath()).listFiles())
		.filter(File::isFile)
		.filter(f -> f.getName().contains(getProduct()))
		.filter(f -> f.getName().endsWith(EXT))
		.collect(Collectors.toList()).get(0);
	}
	
	private File getDirectoryApi() {
		return Arrays.stream(new File(getPath() + "/apis").listFiles())
		.filter(File::isDirectory)
		.filter(f -> f.getName().contains(getApi()))
		.collect(Collectors.toList()).get(0);
	}
	
	
	
	private void renameProductFile(File productFile) {
		final String oldName = productFile.getName().split("_")[0];
		final Boolean isRenamed = productFile.renameTo(new File(getPath() + SLASH + oldName + "_v" + getVersion() + EXT));
		
		if(Boolean.TRUE.equals(isRenamed)) logger.info("renamed");

	}
	
	private void editProductFile(File filetoEdit) {
		FileEditor editor = new FileEditor(getVersion(), getProduct(), getApi());
		editor.edit(filetoEdit);
	}
	
}
