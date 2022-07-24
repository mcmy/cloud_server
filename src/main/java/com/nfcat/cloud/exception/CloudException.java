package com.nfcat.cloud.exception;

import com.nfcat.cloud.enums.ResultCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Getter
@NoArgsConstructor
public class CloudException extends RuntimeException {
    private ResultCode resultCode;

    public CloudException(@NotNull ResultCode resultCode) {
        super(resultCode.getMsg());
        this.resultCode = resultCode;
    }
}
