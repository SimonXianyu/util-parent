package io.github.simonxianyu.util.spring.freemarker;

import freemarker.core.Environment;
import freemarker.ext.beans.BeanModel;
import freemarker.template.*;
import io.github.simonxianyu.util.common.DwUtil;
import org.springframework.web.servlet.support.RequestContext;

import java.io.IOException;
import java.util.Map;

/**
 * implement same function as spring url tag
 * Created by Simon Xianyu on 2015/9/7 0007.
 */
public class UrlDirective implements TemplateDirectiveModel {

  @Override
  public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
    String var=null;
    String url=null;
    params.entrySet();
    for (Object o : params.entrySet()) {
      Map.Entry e = (Map.Entry) o;

      if ("var".equals(e.getKey())) {
        var = DwUtil.stringValue(e.getValue());
      } else if ("value".equals(e.getKey())) {
        url = DwUtil.stringValue(e.getValue());
      }
    }
    if (url == null) {
      throw new TemplateModelException("value required");
    }
    TemplateModel model = env.getVariable("request");
    String resultUrl;
    if (model instanceof BeanModel) {
      BeanModel m2 = (BeanModel) model;
      if (m2.getWrappedObject() instanceof RequestContext) {
        resultUrl = ((RequestContext) m2.getWrappedObject()).getContextPath() + url;
        if (var != null) {
          env.setVariable(var, env.getObjectWrapper().wrap(resultUrl));
        } else {
          env.getOut().append(resultUrl);
        }
        return;
      }
    }
    throw new TemplateModelException("No request instance found");
  }
}
