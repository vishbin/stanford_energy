package edu.stanford.base.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import nl.chess.it.util.config.Config;
import nl.chess.it.util.config.ConfigurationException;

public abstract class BaseConfig extends Config {
	public final static String  ROOT_CONFIG_DIR = "C:\\lcportal2\\lcp2\\config\\";
	
	private final static String RESOURCE_NAME = ROOT_CONFIG_DIR + "lcportalconfig.properties";
	
	public BaseConfig(){
		super(load(RESOURCE_NAME));
		
		
	}
	protected BaseConfig(String resourceName){
		
		super(load(resourceName));
	}

	
	private static Properties load(String fileName){

		 
        

        try {
        	InputStream inputStream = new FileInputStream(new File(fileName));
            

            Properties tmpproperties = new Properties();
            tmpproperties.load(inputStream);
            return tmpproperties;
        } catch (Exception e) {
            throw new ConfigurationException("Could not read resource '" + fileName
                    + "' to load configuration properties from.", e);
        }
	}
}
