package io.github.simonxianyu.util.spring.freemarker;

import freemarker.core.Environment;
import freemarker.ext.beans.BeanModel;
import freemarker.template.*;
import io.github.simonxianyu.util.common.DwUtil;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.support.RequestContext;

import java.io.IOException;
import java.util.Map;

/**
 * Same function like spring message tag
 * Created by Simon Xianyu on 2015/9/7 0007.
 */
public class MessageDirective implements TemplateDirectiveModel {

  private MessageSource messageSource;

  @Override
  public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
    String var=null;
    String code=null;
    String text=null;
    for (Object o : params.entrySet()) {
      Map.Entry e = (Map.Entry) o;

      if ("var".equals(e.getKey())) {
        var = DwUtil.stringValue(e.getValue());
      } else if ("code".equals(e.getKey())) {
        code = DwUtil.stringValue(e.getValue());
      } else if ("text".equals(e.getKey())) {
        text = DwUtil.stringValue(e.getValue());
      }
    }

    RequestContext requestContext = extractRequestContext(env);
    if (null == requestContext) {
      throw new TemplateModelException("Failed to find request context");
    }
    String message = messageSource.getMessage(code, new String[] {text}, requestContext.getLocale());
    if (null == var) {
      env.getOut().append(message);
    } else {
      env.setVariable(var, env.getObjectWrapper().wrap(message));
    }
  }

  private RequestContext extractRequestContext(Environment env) throws TemplateModelException {
    TemplateModel model = env.getVariable("request");
    if (model instanceof BeanModel) {
      BeanModel m2 = (BeanModel) model;
      if (m2.getWrappedObject() instanceof RequestContext) {
        return (RequestContext) m2.getWrappedObject();
      }
    }
    return null;
  }

  public void setMessageSource(MessageSource messageSource) {
    this.messageSource = messageSource;
  }
}
