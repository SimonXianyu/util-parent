package io.github.simonxianyu.util.permission;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by simon on 15/2/22.
 */
public class PermModuleDto {
    private String name;
    private String text;
    private boolean allAssigned;
    private boolean someAssigned = false;
    private List<PermDto> perms;

    public PermModuleDto(PermModule pm) {
        this.perms = new ArrayList<PermDto>();
        if (pm != null) {
            this.name = pm.getName();
            this.text = pm.getText();
            if (null != pm.getPerms()) {
                for(PermissionDef perm: pm.getPerms()) {
                    this.perms.add(new PermDto(perm));
                }
            }
        }
    }

    public PermModuleDto(PermDomain domain, PermModule pm, List<RolePermIntf> permList) {
        name = pm.getName();
        text = pm.getText();
        if (null == pm.getPerms()) {
            return;
        }
        if (null == perms) {
            perms = new ArrayList<PermDto>();
        }
        this.allAssigned=true;
        for (PermissionDef def : pm.getPerms()) {
            String fullPermStr = domain.getName() +"."+pm.getName()+ ":" + def.getName();
            PermDto permDto = new PermDto(def);
            if (null != permList && 0 < permList.size()) {
                for (RolePermIntf rolePerm : permList) {
                    if (fullPermStr.equals(rolePerm.getPerm())) {
                        permDto.setAssigned(true);
                        someAssigned = true;
                        break;
                    }
                }
                if (!permDto.isAssigned()) {
                    allAssigned = false;
                }
            } else {
                allAssigned = false;
            }
            perms.add(permDto);
        }
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public List<PermDto> getPerms() {
        return perms;
    }

    public boolean isAllAssigned() {
        return allAssigned;
    }

    public boolean isSomeAssigned() {
        return someAssigned;
    }
}
