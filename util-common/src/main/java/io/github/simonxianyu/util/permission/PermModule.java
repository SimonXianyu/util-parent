package io.github.simonxianyu.util.permission;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by simon on 15/2/22.
 */
public class PermModule {
    private String name;
    private String text;
    private PermDomain domain;
    private List<PermissionDef> perms;

    @Override
    public String toString() {
        return "module{" +
                "name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", domain=" + domain.getName() +
                ", perms=" + perms +
                '}';
    }

    public void addPermission(PermissionDef def ) {
        if (null == perms) {
            perms = new ArrayList<PermissionDef>();
        }
        perms.add(def);
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<PermissionDef> getPerms() {
        return perms;
    }

    public void setPerms(List<PermissionDef> perms) {
        this.perms = perms;
    }
}
