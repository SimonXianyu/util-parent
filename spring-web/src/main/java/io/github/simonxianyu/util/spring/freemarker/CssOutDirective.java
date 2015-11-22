package io.github.simonxianyu.util.spring.freemarker;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import io.github.simonxianyu.util.spring.web.AbstractWebConfig;

import java.io.IOException;
import java.util.Map;

/**
 * Use this directive to output former declared css.
 * Created by Simon Xianyu on 2015/11/22 0022.
 */
public class CssOutDirective implements TemplateDirectiveModel {
  private AbstractWebConfig webConfig;

  @Override
  public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {

  }

  public void setWebConfig(AbstractWebConfig webConfig) {
    this.webConfig = webConfig;
  }
}
