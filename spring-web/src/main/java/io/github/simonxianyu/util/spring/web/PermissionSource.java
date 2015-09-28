package io.github.simonxianyu.util.spring.web;

import io.github.simonxianyu.util.common.DwUtil;
import io.github.simonxianyu.util.permission.PermDomain;
import io.github.simonxianyu.util.permission.PermModule;
import io.github.simonxianyu.util.permission.PermissionDef;
import org.apache.commons.digester3.Digester;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to read permissions from XML file.
 * User: Simon Xianyu
 * Date: 13-10-4
 * Time: 下午3:18
 */
public class PermissionSource {
    private Resource permissionFile;

    private List<PermDomain> domains;
    private List<PermissionDef> permissions = new ArrayList<PermissionDef>();

    public void init() throws Exception {
        readPermissions();
    }

    public static void main(String[] args) {
        PermissionSource self = new PermissionSource();
        self.setPermissionFile(new FileSystemResource("src/main/webapp/WEB-INF/conf/permissions.xml"));
        self.readPermissions();
        for(PermDomain domain: self.domains) {
            System.out.println(" domain :" +domain.getText());
            if (null != domain.getModules()) {
                for(PermModule pm : domain.getModules()) {
                    System.out.println("   module :" +pm.getText());
                    if (null != pm.getPerms()) {
                        for(PermissionDef def : pm.getPerms()) {
                            System.out.println("     action :" +def.getText());
                        }
                    }
                }
            }
        }
    }

    public void readPermissions() {
        Digester dig = getDigester();

        InputStream in = null;
        try {
            in = permissionFile.getInputStream();
            if (in != null) {
                domains = dig.parse(in);
                for(PermDomain domain: domains) {
                    for(PermModule pm : domain.getModules()) {
                        if (null == pm.getPerms()) {
                            continue;
                        }
                        for (PermissionDef def : pm.getPerms()) {
                            permissions.add(def);
                        }
                    }
                }
            }
        } catch (IOException e) {
//            e.printStackTrace();
            throw new RuntimeException("Failed to reading permission file", e);
        } catch (SAXException e) {
//            e.printStackTrace();
            throw new RuntimeException("Xml error in parsing permission file.", e);
        } finally {
            DwUtil.closeQuietly(in);
        }
    }

    private Digester getDigester() {
        Digester dig = new Digester();
        dig.addObjectCreate("permissions", ArrayList.class);
        dig.addObjectCreate("permissions/domain", PermDomain.class);
        dig.addSetProperties("permissions/domain");
        dig.addSetNext("permissions/domain", "add");

        dig.addObjectCreate("permissions/domain/module", PermModule.class);
        dig.addSetProperties("permissions/domain/module");
        dig.addSetNext("permissions/domain/module","addModule");

        dig.addObjectCreate("permissions/domain/module/perm", PermissionDef.class);
        dig.addSetProperties("permissions/domain/module/perm");
        dig.addSetNext("permissions/domain/module/perm","addPermission");
        return dig;
    }
    public List<PermDomain> getDomains() {
        return domains;
    }

    public void setDomains(List<PermDomain> domains) {
        this.domains = domains;
    }

    public Resource getPermissionFile() {
        return permissionFile;
    }

    public void setPermissionFile(Resource permissionFile) {
        this.permissionFile = permissionFile;
    }

    public List<PermissionDef> getPermissions() {
        return permissions;
    }
}
