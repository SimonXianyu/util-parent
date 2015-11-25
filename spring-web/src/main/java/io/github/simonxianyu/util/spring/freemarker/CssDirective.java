package io.github.simonxianyu.util.spring.freemarker;

import freemarker.core.Environment;
import freemarker.ext.beans.StringModel;
import freemarker.ext.servlet.HttpRequestHashModel;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import io.github.simonxianyu.util.common.DwUtil;
import io.github.simonxianyu.util.spring.web.AbstractWebConfig;
import io.github.simonxianyu.util.spring.web.CssConfigSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.support.RequestContext;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * Use this directive to collect css info
 * Created by Simon Xianyu on 2015/11/22 0022.
 */
public class CssDirective implements TemplateDirectiveModel {
  @Autowired
  private CssConfigSource cssConfigSource;
  @Autowired
  private AbstractWebConfig webConfig;

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
    String url = cssConfigSource.getCss(scope, cssKey);
    if (null == url) {
      throw new TemplateException("css key not found :"+scope+":"+cssKey, env);
    }
    Writer w = env.getOut();
    w.append("<link href=\"");
    if (webConfig.isLocal()) {
      TemplateModel reqModel = env.getGlobalVariable("request");

      if (reqModel instanceof StringModel) {
        Object obj = ((StringModel) reqModel).getWrappedObject();
        if (obj instanceof RequestContext) {
          w.append(((RequestContext)obj).getContextPath());
        }
      }
    }
    w.append(url).append("\" rel=\"stylesheet\" type=\"text/css\" />");
//    TemplateModel m = env.getGlobalVariable("cssHash");
//    SimpleHash keys ;
//    if (m instanceof SimpleHash) {
//      keys = (SimpleHash) m;
//    } else {
//      keys = new SimpleHash(env.getObjectWrapper());
//      env.setGlobalVariable("cssHash", keys);
//    }
//    keys.put(cssKey, cssKey);

  }
}
