package io.github.simonxianyu.util.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 *
 * Created by Simon Xianyu on 2015/11/25 0025.
 */
public class ShiroUtils {
  public static Subject getSubject() {
    return SecurityUtils.getSubject();
  }
}
