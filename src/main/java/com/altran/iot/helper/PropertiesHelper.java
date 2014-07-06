package com.altran.iot.helper;

import com.altran.iot.IoTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * @author <a href="bard.lind@gmail.com">Bard Lind</a>
 */
public class PropertiesHelper {
    public static final int DEFAULT_PORT_NO = 80;
    private static final Logger log = LoggerFactory.getLogger(PropertiesHelper.class);
    protected static FileReader findPropertiesFile(String filename) {
        FileReader propertiesFile = null;
        try {
            propertiesFile = new FileReader(filename);
        }catch (Exception e) {
            log.warn("Could not find properties file: " + filename + ", reason: " + e.getMessage());
        }
        return propertiesFile;
    }
    public static Properties findProperties() {
        Properties properties = new Properties();
        String classpathFileName = "IoT-service.properties";
        String overrideFileName = "./config_override/IoT-service.properties";
        FileReader classpathFile = findPropertiesFile(classpathFileName);
        FileReader overrideFile = findPropertiesFile(overrideFileName);
        if (overrideFile == null && classpathFile == null) {
            throw new IoTException("Failed to load properties. Neither " + classpathFileName + " nor " +overrideFileName +" were found.", StatusType.RETRY_NOT_POSSIBLE );
        }
        try {
            if (classpathFile != null) {
                log.info("Loading properties from {}", classpathFileName);
                properties.load(classpathFile);
            }
            if (overrideFile != null) {
                log.info("Loading properties from {}", overrideFileName);
                properties.load(overrideFile);
            }
            log.debug("Properties loaded: {}", properties.toString());
        } catch (IOException e) {
            throw new IoTException("Could not load properties from file.", e,StatusType.RETRY_NOT_POSSIBLE);
        }
        return properties;
    }
    public static int findHttpPort(Properties resoruces) throws IoTException {
        int retPort = -1;
        String httpPort = resoruces.getProperty("jetty.http.port");

        if (httpPort == null || httpPort.length() == 0) {
            log.info("jetty.http.port missing. Will use default port {}", DEFAULT_PORT_NO);
            retPort = DEFAULT_PORT_NO;
        } else {
            try {
                retPort = new Integer(httpPort).intValue();
            } catch (NumberFormatException nfe) {
                log.error("Could not convert {} to int. No jetty port is set.", httpPort);
                throw new IoTException("Proprerty 'jetty.http.port' with value "+ httpPort +" could not be cast to int.", StatusType.RETRY_NOT_POSSIBLE);
            }
        }
        return retPort;
    }
}
