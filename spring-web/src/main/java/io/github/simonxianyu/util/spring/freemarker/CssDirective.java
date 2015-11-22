package io.github.simonxianyu.util.spring.freemarker;

import freemarker.core.Environment;
import freemarker.template.*;
import io.github.simonxianyu.util.common.DwUtil;
import io.github.simonxianyu.util.spring.web.AbstractWebConfig;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Use this directive to collect css info
 * Created by Simon Xianyu on 2015/11/22 0022.
 */
public class CssDirective implements TemplateDirectiveModel {

  @Override
  public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
    String scope = null;
    String cssKey = null;

    for (Object o : params.entrySet()) {
      Map.Entry e = (Map.Entry) o;

      if ("key".equals(e.getKey())) {
        cssKey = DwUtil.stringValue(e.getValue());
      } else if ("scope".equals(e.getKey())) {
        scope = DwUtil.stringValue(e.getValue());
      }
    }

    TemplateModel m = env.getGlobalVariable("cssHash");
    SimpleHash keys ;
    if (m instanceof SimpleHash) {
      keys = (SimpleHash) m;
    } else {
      keys = new SimpleHash(env.getObjectWrapper());
      env.setGlobalVariable("cssHash", keys);
    }
    keys.put(cssKey, cssKey);

  }
}
