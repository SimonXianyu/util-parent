package io.github.simonxianyu.util.spring;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 *
 * Created by Simon Xianyu on 2016/4/8 0008.
 */
public class MyPropertiesFactoryBeanTest {
  @Test
  public void testWithWildcard() throws IOException {
    ClassPathXmlApplicationContext actx = new ClassPathXmlApplicationContext("/context-test.xml");
    actx.refresh();
    Properties prop = actx.getBean("propConfig", Properties.class);
    System.out.println(prop);
  }

}