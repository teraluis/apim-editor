package apim2.manager;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Ressource {
	protected String version;
	protected String product;
	protected String api;

	protected Ressource(String version, String product, String api) {
		this.version = version;
		this.product = product;
		this.api = api;
	}
	
	
}
