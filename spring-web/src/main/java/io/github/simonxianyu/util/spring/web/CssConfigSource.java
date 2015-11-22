package io.github.simonxianyu.util.spring.web;

import org.springframework.core.io.Resource;

import java.util.Map;

/**
 * Try to read css configurations.
 * Created by Simon Xianyu on 2015/11/22 0022.
 */
public class CssConfigSource {
  private Map<String, Resource> cssResources;

  public void init() {

  }

  public Map<String, Resource> getCssResources() {
    return cssResources;
  }

  public void setCssResources(Map<String, Resource> cssResources) {
    this.cssResources = cssResources;
  }
}
