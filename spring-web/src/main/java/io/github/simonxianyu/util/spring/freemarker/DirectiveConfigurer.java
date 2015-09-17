package io.github.simonxianyu.util.spring.freemarker;

import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.util.Map;

/**
 * Use this to inject customized freemarker directive.
 * Created by Simon Xianyu on 2015/9/6 0006.
 */
public class DirectiveConfigurer extends FreeMarkerConfigurer {
  private Map<String, TemplateDirectiveModel> directiveMap;

  @Override
  public void afterPropertiesSet() throws IOException, TemplateException {
    super.afterPropertiesSet();
    if (null != directiveMap) {
      for(Map.Entry<String, TemplateDirectiveModel> e: directiveMap.entrySet()) {
        getConfiguration().setSharedVariable(e.getKey(), e.getValue());
      }
    }
  }

  public void setDirectiveMap(Map<String, TemplateDirectiveModel> directiveMap) {
    this.directiveMap = directiveMap;
  }

}
