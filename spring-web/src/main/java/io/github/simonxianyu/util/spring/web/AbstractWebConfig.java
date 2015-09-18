package io.github.simonxianyu.util.spring.web;

import org.springframework.beans.factory.InitializingBean;

/**
 * abstract class
 * Created by Simon Xianyu on 2015/9/18 0018.
 */
abstract public class AbstractWebConfig implements InitializingBean {
  /** This property is used to check whether resource is in package.*/
  protected boolean local=false;
  protected String localFlag;
  protected String extResUrl;
  protected String casUrl;
  protected String serverUrl;
  
  @Override
  public void afterPropertiesSet() throws Exception {
    local = Boolean.parseBoolean(localFlag);
  }

  public boolean isLocal() {
    return local;
  }

  public void setLocal(boolean local) {
    this.local = local;
  }

  public String getLocalFlag() {
    return localFlag;
  }

  public void setLocalFlag(String localFlag) {
    this.localFlag = localFlag;
  }

  public String getExtResUrl() {
    return extResUrl;
  }

  public void setExtResUrl(String extResUrl) {
    this.extResUrl = extResUrl;
  }

  public String getCasUrl() {
    return casUrl;
  }

  public void setCasUrl(String casUrl) {
    this.casUrl = casUrl;
  }

  public String getServerUrl() {
    return serverUrl;
  }

  public void setServerUrl(String serverUrl) {
    this.serverUrl = serverUrl;
  }
}
