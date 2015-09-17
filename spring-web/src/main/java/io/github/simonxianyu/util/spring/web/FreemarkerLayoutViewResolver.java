package io.github.simonxianyu.util.spring.web;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to replace default freemarker viewResolver in layout form..
 * This class will make render with a layout page and target view will be rendered by <#include ${mainPage}>
 */
public class FreemarkerLayoutViewResolver extends FreeMarkerViewResolver
        implements InitializingBean {
  private Logger log = LogManager.getLogger(getClass());

  private AntPathMatcher pathMatcher = new AntPathMatcher();
  /**
   * Default layout page path
   */
  private String defaultLayout;
  /**
   * Default mobile layout, added for avoid layout conflict
   */
  private String mobileDefaultLayout;

  private Map<String, String> layoutMapping;
  private Map<String, Object> extraPageBeans;

  @Override
  protected AbstractUrlBasedView buildView(String viewName) throws Exception {
    // ignore / in view name
    if (viewName.startsWith("/")) {
      viewName = viewName.substring(1);
    }
    AbstractUrlBasedView view = super.buildView(viewName);
    appendExtraAttributes(view);
    String layout = defaultLayout;
    if (viewName.startsWith("mobile")) {
      layout = mobileDefaultLayout;
    }
    for (Map.Entry<String, String> entry : layoutMapping.entrySet()) {
//			if (viewName.startsWith(entry.getKey())) {
      if (pathMatcher.match(entry.getKey(), viewName)) {
        if ("none".equals(entry.getValue())) {
          return view;
        }
        layout = entry.getValue();
        break;
      }
    }
    view.setUrl(getPrefix() + layout + getSuffix());
    view.addStaticAttribute("mainPage", getPrefix() + viewName + getSuffix());
    view.addStaticAttribute("mainView", getPrefix() + viewName);

    return view;
  }


  /**
   * Seems to be no need because there is exposedContextBeanNames provided in super class.
   * But this method can specify exposed name in context.
   */
  private void appendExtraAttributes(AbstractUrlBasedView view) {
    if (this.extraPageBeans != null) {
      for (Map.Entry<String, Object> entry : this.extraPageBeans.entrySet()) {
        Object value = entry.getValue();
//                if (value instanceof MenuBarSource) {
//                    view.addStaticAttribute(entry.getKey(), ((MenuBarSource)value).getMenuBar());
//                } else {
        view.addStaticAttribute(entry.getKey(), value);
//                }
      }
    }
  }

  public void afterPropertiesSet() throws Exception {
    if (null != layoutMapping) {
      Map<String, String> layoutMapping2 = new HashMap<String, String>(layoutMapping.size());
      for (Map.Entry<String, String> entry : layoutMapping.entrySet()) {
        if (entry.getKey().charAt(0) == '/') {
          layoutMapping2.put(entry.getKey().substring(1), entry.getValue());
        } else {
          layoutMapping2.put(entry.getKey(), entry.getValue());
        }
      }
      this.layoutMapping = layoutMapping2;
    } else {
      log.warn("layout mapping is not specified.");
    }
  }

  public String getDefaultLayout() {
    return defaultLayout;
  }

  public void setDefaultLayout(String defaultLayout) {
    this.defaultLayout = defaultLayout;
  }

  public Map<String, String> getLayoutMapping() {
    return layoutMapping;
  }

  public void setLayoutMapping(Map<String, String> layoutMapping) {
    this.layoutMapping = layoutMapping;
  }

  public Map<String, Object> getExtraPageBeans() {
    return extraPageBeans;
  }

  public void setExtraPageBeans(Map<String, Object> extraPageBeans) {
    this.extraPageBeans = extraPageBeans;
  }

  public String getMobileDefaultLayout() {
    return mobileDefaultLayout;
  }

  public void setMobileDefaultLayout(String mobileDefaultLayout) {
    this.mobileDefaultLayout = mobileDefaultLayout;
  }
}
