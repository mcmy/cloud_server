package com.nfcat.cloud.enums;

import com.nfcat.cloud.common.utils.TypeConversionTool;
import com.nfcat.cloud.sql.entity.NfUser;

public class Permission {
    public static final int VISITOR = 0;
    public static final int USER = 1;
    public static final int BI_ADMIN = 7000;
    public static final int ADMIN = 9000;

    public static boolean hasPermission(Object o, int permission) {
        if (o instanceof NfUser nfUser) {
            return TypeConversionTool.toInt(nfUser.getUserGroup()) >= permission;
        }
        return false;
    }
}
