package com.nfcat.cloud.enums;

import com.nfcat.cloud.sql.entity.NfUser;

public enum Permission {
    VISITOR(PermissionStringEnum.PER_VISITOR),
    USER(PermissionStringEnum.PER_USER),
    BI_ADMIN(PermissionStringEnum.PER_BI_ADMIN),
    ADMIN(PermissionStringEnum.PER_ADMIN);

    static class PermissionStringEnum {
        public static final int[] PER_VISITOR = new int[]{0, 0, 0, 0};
        public static final int[] PER_USER = new int[]{0, 0, 0, 1};
        public static final int[] PER_BI_ADMIN = new int[]{7, 0, 0, 0};
        public static final int[] PER_ADMIN = new int[]{9, 0, 0, 0};
    }

    private final int[] permission;

    Permission(int[] permission) {
        this.permission = permission;
    }

    public boolean hasPermission(Object obj) {
        if (this == VISITOR) return true;
        if (obj == null) return false;
        if (obj instanceof NfUser nfUser) {
            final String[] split = nfUser.getUserGroup().split("");
            for (int i = 0; i < split.length; i++) {
                if (Integer.parseInt(split[i]) >= permission[i])
                    return true;
            }
        }
        return false;
    }
}
