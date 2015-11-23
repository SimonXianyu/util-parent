package io.github.simonxianyu.util.spring.web;

import io.github.simonxianyu.util.common.DwUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Try to read css configurations.
 * Created by Simon Xianyu on 2015/11/22 0022.
 */
public class CssConfigSource implements InitializingBean {
  private Logger log = LogManager.getLogger();
  private Map<String, Resource> cssResources;

  private Map<String, Map<String, String>> cssMap = new HashMap<>();

  @Override
  public void afterPropertiesSet() throws Exception {
    init();
  }

  public void init() {
    if (null == cssResources) {
      throw new RuntimeException("CssResource should be declared.");
    }
    for(Map.Entry<String, Resource> e : cssResources.entrySet()) {
      if (cssMap.containsKey(e.getKey())) {
        throw new IllegalArgumentException("duplicated scope:"+e.getKey());
      }

      Properties prop = new Properties();
      InputStream in = null;
      try {
        in = e.getValue().getInputStream();
        prop.load( in );
        if (prop.size()>0) {
          Map<String, String> m = new HashMap<>();
          for(Map.Entry<Object, Object> e1 : prop.entrySet()) {
            m.put(e1.getKey().toString(), e1.getValue().toString());
          }
          cssMap.put(e.getKey(), m);
        } else {
          log.warn("scope "+e.getKey() +" has no values");
        }
      } catch (IOException e1) {
//        e1.printStackTrace();
        log.error("Failed to read scope "+e.getKey() +" resource "+e.getValue().getFilename());
      } finally {
        DwUtil.closeQuietly(in);
      }
    }
  }

  public Map<String, Resource> getCssResources() {
    return cssResources;
  }

  public void setCssResources(Map<String, Resource> cssResources) {
    this.cssResources = cssResources;
  }


}
