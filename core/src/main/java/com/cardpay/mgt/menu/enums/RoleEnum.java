package com.cardpay.mgt.menu.enums;

/**
 * 角色类型枚举
 *
 * @author yanweichen
 */
public enum RoleEnum {

    SUPERS("super", 1),
    ADMIN("admin", 2),
    MANAGER("manager", 3),
    EXPERT("expert", 4);

    RoleEnum(String roleName, Integer roleId) {
        this.roleName = roleName;
        this.roleId = roleId;
    }

    private String roleName;
    private Integer roleId;

    public String getRoleName() {
        return roleName;
    }

    private void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public static RoleEnum getValueById(Integer id) {
        switch (id) {
            case 1:
                return SUPERS;
            case 2:
                return ADMIN;
            case 3:
                return MANAGER;
            case 4:
                return EXPERT;
            default:
                break;
        }
        throw new IllegalArgumentException("未找到对应角色枚举");
    }
}