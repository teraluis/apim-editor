package apim2;

import java.util.Map;
import java.util.logging.Logger;

import apim2.controller.Controller;
import apim2.ihm.Ihm;

public class Main {

	private static final Logger logger = Logger.getLogger(Main.class.getName());
	
	public static void main(String[] args) {
		
		logger.info("start");
		Map<String, String> params = Ihm.start();
		
		final String path = params.get("path"); 
		final String product = params.get("product");
		final String version = params.get("version");
		final String api = params.get("api"); 
		
		final Controller produit = Controller.builder().path(path).product(product).version(version).api(api).build();

		produit.changeVersion();
	}
	

}
