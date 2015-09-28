package io.github.simonxianyu.util.permission;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Simon Xianyu
 * Date: 13-10-4
 * Time: 下午3:30
 */
public class PermDomain {
    private String name;
    private String text;
    private List<PermModule> modules = new ArrayList<PermModule>();

    @Override
    public String toString() {
        return "domain{" +
                 name +  '[' + text + ']' +
                ", modules=" + modules +
                '}';
    }

    public void addModule(PermModule m) {
        m.setDomain(this);
        this.modules.add(m);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PermModule> getModules() {
        return modules;
    }

    public void setModules(List<PermModule> modules) {
        this.modules = modules;
    }
}
