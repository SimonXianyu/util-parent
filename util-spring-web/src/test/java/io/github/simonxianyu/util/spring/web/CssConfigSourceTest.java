package io.github.simonxianyu.util.spring.web;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by Simon Xianyu on 2015/11/24 0024.
 */
public class CssConfigSourceTest {
  private CssConfigSource cssConfigSource;

  @Before
  public void setUp() {
    cssConfigSource = new CssConfigSource();
  }
  @Test
  public void testInit() {
    Map<String, Resource> resourceMap = new HashMap<>();
    resourceMap.put("global", new ClassPathResource("css/global.properties"));
    cssConfigSource.setCssResources(resourceMap);
    cssConfigSource.init();
    assertNotNull(cssConfigSource.getScope("global"));
    assertNotNull(cssConfigSource.getCss("global", "bootstrap"));
  }

}
