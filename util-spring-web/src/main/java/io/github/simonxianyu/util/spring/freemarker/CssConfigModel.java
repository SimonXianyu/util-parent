package io.github.simonxianyu.util.spring.freemarker;

import java.util.Map;

/**
 * Model to transfer css locations.
 * Created by Simon Xianyu on 2015/11/22 0022.
 */
public class CssConfigModel {

  private Map<String, Map<String, String>> cssMap;

  public Map<String, Map<String, String>> getCssMap() {
    return cssMap;
  }

  public void setCssMap(Map<String, Map<String, String>> cssMap) {
    this.cssMap = cssMap;
  }
}
