package io.github.simonxianyu.util.spring.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Load extra properties if the properties file valid.
 * Created by Simon Xianyu on 2015/4/12 0012.
 */
public class MyPropertiesFactoryBean extends PropertiesFactoryBean {
  private static final Logger log = LogManager.getLogger(MyPropertiesFactoryBean.class);
  public static final String FILTERED = "filtered";
  protected List<Resource> extraLocations;

  @Override
  protected Properties mergeProperties() throws IOException {
    Properties result = super.mergeProperties();
    if (extraLocations!=null) {
      for(Resource resource : extraLocations) {
        if (!resource.exists()) {
          continue;
        }
        Properties newProperties = new Properties();
        PropertiesLoaderUtils.fillProperties(newProperties, resource);
        if (newProperties.containsKey(FILTERED) ) {
          if (Boolean.parseBoolean(newProperties.getProperty(FILTERED))) {
            applyPropertiesTo(newProperties, result);
//            result.putAll(newProperties);
            log.info("Merge extra file: {}", resource.getURI());
          }
        } else {
          applyPropertiesTo(newProperties, result);
          log.info("Merge extra file: {}", resource.getURI());
        }
      }
    }
    return result;
  }

  private void applyPropertiesTo(Properties newProperties, Properties result) {
    for(String key : newProperties.stringPropertyNames()) {
      String v = newProperties.getProperty(key);
      if (null == v || v.contains("${")) { // skip null && ${
        continue;
      }
      result.put(key, v);
    }
  }

  public void setExtraLocations(List<Resource> extraLocations) {
    this.extraLocations = extraLocations;
  }
}
