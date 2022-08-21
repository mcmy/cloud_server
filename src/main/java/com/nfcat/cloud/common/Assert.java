package com.nfcat.cloud.common;

import com.nfcat.cloud.enums.ResultCode;
import com.nfcat.cloud.exception.AssertException;

public class Assert {
    public static void notNull(Object obj, ResultCode resultCode) {
        if (obj == null) throw new AssertException(resultCode);
    }

    public static void isNull(Object obj, ResultCode resultCode) {
        if (obj != null) throw new AssertException(resultCode);
    }
}
