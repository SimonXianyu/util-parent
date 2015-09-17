package io.github.simonxianyu.util.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * common static method to operate date
 * Created by Simon Xianyu on 2015/9/17 0017.
 */
public class DateUtil {

  public Date parseByFormat(String format, String str) {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    try {
      return sdf.parse(str);
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
  }
}
