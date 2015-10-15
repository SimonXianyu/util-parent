package io.github.simonxianyu.util.permission;

/**
 *
 * Created by simon on 14-5-21.
 */
public class PermDto {
    /** name with domain and module */
    private String fullName;
    private String name;
    private String text;
    private boolean assigned = false;

    public PermDto() {
    }

    public PermDto(PermissionDef def) {
        this.name = def.getName();
        this.text = def.getText();
        this.fullName = def.getFullName();
    }

    public PermDto(PermissionDef def, boolean assigned) {
        this.name = def.getName();
        this.text = def.getText();
        this.assigned = assigned;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public boolean isAssigned() {
        return assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }
}
