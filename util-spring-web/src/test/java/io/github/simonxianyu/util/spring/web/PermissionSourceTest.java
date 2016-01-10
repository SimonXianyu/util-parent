package io.github.simonxianyu.util.spring.web;

import io.github.simonxianyu.util.permission.PermDomain;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 *
 * Created by Simon Xianyu on 2015/9/28 0028.
 */
public class PermissionSourceTest {
  private PermissionSource source;

  @Before
  public void setUp() {
    source = new PermissionSource();
  }

  @Test
  public void testReadNormal() throws Exception {
    ClassPathResource resource = new ClassPathResource("/permissions/normal.xml");
    source.setPermissionFile(resource);
    source.init();
    assertThat(source.getDomains(), notNullValue());
    assertThat(source.getDomains(), contains(
            hasProperty("name", is("sys"))
    ));
    PermDomain domain = source.getDomains().get(0);
    assertThat(domain.getModules(), contains(
            hasProperty("name", is("SysUser.basic")),
            hasProperty("name", is("SysRole.basic"))

    ));
  }
}
