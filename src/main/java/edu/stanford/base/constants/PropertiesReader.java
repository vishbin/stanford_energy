
package edu.stanford.base.constants;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Simple class meant to read a properties file.
 */
public class PropertiesReader {
	/** The Constant log. */
	private static final Log log = LogFactory.getLog(PropertiesReader.class);
    /**
     * Default Constructor.
     */
    public PropertiesReader() {

    }

    /**
     * Some Method.
     *
     * @param fileName the file name
     * @return the properties
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void getProperties(String fileName) throws IOException {
        // Get the inputStream
        InputStream inputStream = this.getClass().getClassLoader()
                .getResourceAsStream("database.properties");

        Properties properties = new Properties();
        log.info("InputStream is: " + inputStream);

        // load the inputStream using the Properties
        properties.load(inputStream);
        // get the value of the property
        String propValue = properties.getProperty("abc");

        log.info("Property value is: " + propValue);
    }

}

