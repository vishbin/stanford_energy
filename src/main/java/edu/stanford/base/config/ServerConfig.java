package edu.stanford.base.config;



public class ServerConfig extends BaseConfig {
	
	public final static String  ROOT_DIR = "C:\\h_workspace_4\\StanEnergy_svn\\src\\conf\\dev";
	private static final String RESOURCE_NAME = ROOT_CONFIG_DIR + "server.properties";

	public static enum ServerMode{production, test};
	
	private static ServerConfig instance;
	
	public final static ServerConfig instance(){
		if(instance == null){
			instance = new ServerConfig();
		}
		return instance;
	}
	
	private ServerConfig(){
		super(RESOURCE_NAME);
	}
	
	
	/**
	 * 
	 * 
	 * @return url for docViewer servlet to view server temp file.
	 */
	public String getDBUrl(){
		return getString("docviewer.url");
	}
	
	

	
}
