package io.github.simonxianyu.util.common;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 *
 * Created by Simon Xianyu on 2015/9/16 0016.
 */
public class DwUtil {

  @SuppressWarnings("unchecked")
  public static <T> List<T> safe(List<T> a) {
    return a == null ? Collections.EMPTY_LIST : a;
  }

  public static String stringValue(Object obj) {
    return null == obj ? null : obj.toString();
  }

  public static void closeQuietly(Closeable cl) {
    if (null != cl) {
      try {
        cl.close();
      } catch (IOException e) {
        // do nothing here.
      }
    }
  }
}
