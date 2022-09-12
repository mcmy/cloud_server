package com.nfcat.cloud.exception;

import com.nfcat.cloud.enums.ResultCode;

public class EmailSendException extends CloudException {
   public EmailSendException(){
        super(ResultCode.EMAIL_SEND_ERROR);
    }
}
