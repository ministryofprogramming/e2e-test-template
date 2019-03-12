package com.ministryofprogramming.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {

    private static volatile ConfigManager instance;
    private static Properties properties;

    /** Define constant for our config properties file directory so that we can load configuration **/
    private final String CONFIG_PROPERTIES_DIRECTORY = System.getProperty("user.dir") + "//src//test//resources//properties//" + System.getProperty("env", "local") + ".properties";

    /** Private constructor to prevent others from instantiating this class **/
    private ConfigManager(){}

   public static ConfigManager getInstance() {
        if (instance == null) {
            synchronized (ConfigManager.class) {
                if (instance == null) {
                    instance = new ConfigManager();
                    instance.readProperties();
                }
            }
        }
        return instance;
    }

    /** Read properties configuration **/
    private void readProperties()  {
        properties = new Properties();
        try {
            properties.load(new FileInputStream(CONFIG_PROPERTIES_DIRECTORY));
        } catch (IOException e) {
            System.out.println("Configuration properties file cannot be found!");
        }
    }

    /** Getters **/
    public static String getEnvConfig(String value) { return properties.getProperty(value); }
}