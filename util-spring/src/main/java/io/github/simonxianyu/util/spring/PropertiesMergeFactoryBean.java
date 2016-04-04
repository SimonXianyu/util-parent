package io.github.simonxianyu.util.spring;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;
import java.util.Properties;

import static io.github.simonxianyu.util.spring.FilterPropertyUtil.*;

/**
 *
 * Created by simon on 16/4/5.
 */
public class PropertiesMergeFactoryBean implements FactoryBean<Properties>, InitializingBean {
  public static final String FILTERED = "filtered";
  private List<Properties> defaultProperties;
  private List<Properties> optionalProperties;

  private Properties merged = new Properties();

  @Override
  public void afterPropertiesSet() throws Exception {
    if (null != defaultProperties) {
      for(Properties d : defaultProperties) {
        merged.putAll(d);
      }
    }
    if (null != optionalProperties) {
      for(Properties op : optionalProperties) {
        if (op.containsKey(FILTERED) ) {
          if (Boolean.parseBoolean(op.getProperty(FILTERED))) {
            applyPropertiesTo(op, merged);
          }
        } else {
          applyPropertiesTo(op, merged);
        }
      }
    }
  }

  @Override
  public Properties getObject() throws Exception {
    return merged;
  }

  @Override
  public Class<?> getObjectType() {
    return Properties.class;
  }

  @Override
  public boolean isSingleton() {
    return false;
  }

  public void setDefaultProperties(List<Properties> defaultProperties) {
    this.defaultProperties = defaultProperties;
  }

  public void setOptionalProperties(List<Properties> optionalProperties) {
    this.optionalProperties = optionalProperties;
  }
}
