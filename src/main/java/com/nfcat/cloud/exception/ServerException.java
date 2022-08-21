package com.nfcat.cloud.exception;

import com.nfcat.cloud.enums.ResultCode;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor
public class ServerException extends CloudException {
    public ServerException(@NotNull ResultCode resultCode) {
        super(resultCode);
    }
}
