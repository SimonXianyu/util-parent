package io.github.simonxianyu.util.permission;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO for permission domain.
 * Created by simon on 14-5-21.
 */
public class PermDomainDto {
    private String name;
    private String text;
    private boolean allAssigned;
    private boolean someAssigned = false;
    private List<PermModuleDto> modules;

    public PermDomainDto() {

    }
    public PermDomainDto(PermDomain domain) {
        this.name = domain.getName();
        this.text = domain.getText();
        if (domain.getModules()!=null) {
            modules = new ArrayList<PermModuleDto>(domain.getModules().size());
            for(PermModule pm : domain.getModules()) {
                modules.add(new PermModuleDto(pm));
            }
        }
    }

    public PermDomainDto(PermDomain domain, List<RolePermIntf> permList) {
        this.name = domain.getName();
        this.text = domain.getText();
        if (domain.getModules()!=null) {
            modules = new ArrayList<PermModuleDto>(domain.getModules().size());
            boolean allAssigned = true;
            for(PermModule pm : domain.getModules()) {
                PermModuleDto moduleDto = new PermModuleDto(domain, pm, permList);
                modules.add(moduleDto);
                if (!moduleDto.isAllAssigned()) {
                    allAssigned = false;
                }
                if (moduleDto.isSomeAssigned()) {
                    someAssigned = true;
                }
            }
            this.allAssigned = allAssigned;
        }
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public List<PermModuleDto> getModules() {
        return modules;
    }

    public boolean isAllAssigned() {
        return allAssigned;
    }

    public boolean isSomeAssigned() {
        return someAssigned;
    }

    public int getChildState() {
        if (allAssigned) {
            return 2;
        }
        if ( someAssigned) {
            return 1;
        }
        return -1;
    }
}
