package io.github.simonxianyu.util.permission;

/**
 * User: Simon Xianyu
 * Date: 13-10-4
 * Time: 下午3:30
 */
public class PermissionDef {
    private String name;
    private String text;
    private String intro;
    private PermDomain domain;
    private PermModule module;

    @Override
    public String toString() {
        return name+"["+text+"]";
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFullName() {
        return domain.getName()+":"+module.getName()+"."+name;
    }

    public PermDomain getDomain() {
        return domain;
    }

    public void setDomain(PermDomain domain) {
        this.domain = domain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public PermModule getModule() {
        return module;
    }

    public void setModule(PermModule module) {
        this.module = module;
    }
}
