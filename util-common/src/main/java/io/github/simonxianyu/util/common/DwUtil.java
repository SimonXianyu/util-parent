package io.github.simonxianyu.util.common;

import java.io.Closeable;
import java.io.IOException;

/**
 *
 * Created by Simon Xianyu on 2015/9/16 0016.
 */
public class DwUtil {
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
