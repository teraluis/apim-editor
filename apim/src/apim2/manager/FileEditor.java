package apim2.manager;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;


public class FileEditor extends Ressource {


	private static final Logger logger = Logger.getLogger(FileEditor.class.getName());
	private static final String EXT = ".yaml";

	public FileEditor(String version, String product, String api) {
		super(version, product, api);
	}

	public void edit(File edit) {

		ObjectMapper objectMapper = new YAMLMapper().configure(YAMLGenerator.Feature.WRITE_DOC_START_MARKER, false);
		Map<String, Object> conf;
		
		try {
			conf = objectMapper.readValue(edit, new TypeReference<Map<String, Object>>() {});
			
			objectMapper.writeValue(edit, editProduit(conf, version, api));
	
			objectMapper.writeValue(edit, editInterface(conf, version));
			
		} catch (IOException e) {
			logger.severe(e.getMessage());
		}
	}
	
	
	private Map<String, Object> editProduit(Map<String, Object> conf, String version, String api) {
		@SuppressWarnings("unchecked")
		Map<String, Object> apis = (Map<String, Object>) conf.get("apis");			
		
		if (Optional.ofNullable(apis).isPresent() && Optional.ofNullable(apis.get(api)).isPresent()) {		

			@SuppressWarnings("unchecked")
			final Map<String, Object> field = (Map<String, Object>) apis.get(api);
			final String url = (String) field.get("$ref");
			field.remove("$ref");
			field.put("$ref", url.split("_")[0] + "_v" + version + "/interface" + EXT);			
		}
		return conf;
	}
	
	private Map<String, Object> editInterface(Map<String, Object> conf, String version) {		
		final String versionField = "version";
		@SuppressWarnings("unchecked")
		Map<String, Object> info = (Map<String, Object>) conf.get("info");	
		if(Optional.ofNullable(info).isPresent() && Optional.ofNullable(info.get(versionField)).isPresent()) {
			info.remove(versionField);
			info.put(versionField, version);
			
		}
		return conf;
	}

	
}
