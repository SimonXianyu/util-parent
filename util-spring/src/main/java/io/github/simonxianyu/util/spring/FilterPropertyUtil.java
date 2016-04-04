package io.github.simonxianyu.util.spring;

import java.util.Properties;

/**
 *
 * Created by simon on 16/4/5.
 */
public class FilterPropertyUtil {
  public static void applyPropertiesTo(Properties newProperties, Properties result) {
    for(String key : newProperties.stringPropertyNames()) {
      String v = newProperties.getProperty(key);
      if (null == v || v.contains("${")) { // skip null && ${
        continue;
      }
      result.put(key, v);
    }
  }
}
