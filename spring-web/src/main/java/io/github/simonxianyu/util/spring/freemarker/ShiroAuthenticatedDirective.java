package io.github.simonxianyu.util.spring.freemarker;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import io.github.simonxianyu.util.shiro.ShiroUtils;
import org.apache.shiro.subject.Subject;

import java.io.IOException;
import java.util.Map;

/**
 *
 * Created by Simon Xianyu on 2015/9/9 0009.
 */
public class ShiroAuthenticatedDirective implements TemplateDirectiveModel {
  @Override
  public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
    Subject subject = ShiroUtils.getSubject();
    if (subject.isAuthenticated()) {
      body.render(env.getOut());
    }
  }
}
