package com.nfcat.cloud.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.nfcat.cloud.data.JsonResponse;
import com.nfcat.cloud.enums.ResultCode;
import com.nfcat.cloud.exception.CloudException;
import com.nfcat.cloud.exception.EmailSendException;
import com.nfcat.cloud.exception.MissingParametersException;
import com.nfcat.cloud.exception.UploadException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.sql.SQLException;
import java.util.Map;


@Slf4j
@Controller
@ControllerAdvice
public class ExceptionInterceptor implements ErrorController {

    static final String d = ",";

    //请求方式错误->errorHTML
    @ResponseBody
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ModelAndView httpRequestMethodNotSupportedException(@NotNull Exception ex) {
        return errorHTML(ex, 500, ex.getMessage());
    }

    //HTTP消息不可读，SQL语句错误->验证失败
    @ResponseBody
    @ExceptionHandler(value = {HttpMessageNotReadableException.class, BadSqlGrammarException.class})
    public String httpMessageNotReadableException(Exception ex) {
        return errorJSON(ex, ResultCode.VALIDATE_FAILED);
    }

    //缺少参数
    @ResponseBody
    @ExceptionHandler(value = MissingParametersException.class)
    public String missingParametersException(Exception ex) {
        return errorJSON(ex, ResultCode.MISSING_PARAMETERS);
    }

    //参数异常
    @ResponseBody
    @ExceptionHandler(value = {IllegalArgumentException.class, ConstraintViolationException.class})
    public String constraintViolationException(Exception ex) {
        return errorJSON(ex, new JsonResponse(1002, ex.getMessage()));
    }

    //验证异常
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public String methodArgumentNotValidException(@NotNull MethodArgumentNotValidException ex) {
        StringBuilder stringBuilder = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach(a -> stringBuilder.append(d).append(a.getDefaultMessage()));
        stringBuilder.deleteCharAt(0);
        return errorJSON(ex, new JsonResponse(1002, stringBuilder.toString()));
    }

    //验证异常2
    @ResponseBody
    @ExceptionHandler(value = ValidationException.class)
    public String validationException(@NotNull ValidationException ex) {
        final Throwable cause = ex.getCause();
        if (cause == null) return errorJSON(ex, ResultCode.VALIDATE_FAILED);
        return errorJSON(ex, new JsonResponse(1002, cause.getMessage()));
    }

    //上传文件失败
    @ResponseBody
    @ExceptionHandler(value = UploadException.class)
    public String uploadException(Exception ex) {
        return errorJSON(ex, ResultCode.UPLOAD_ERROR);
    }

    //数据库错误
    @ResponseBody
    @ExceptionHandler(value = SQLException.class)
    public String sqlException(Exception ex) {
        return errorJSON(ex, ResultCode.SQL_ERROR);
    }

    //邮件发送失败
    @ResponseBody
    @ExceptionHandler(value = EmailSendException.class)
    public String emailSendException(Exception ex) {
        return errorJSON(ex, ResultCode.EMAIL_SEND_ERROR);
    }

    //其他Cloud错误
    @ResponseBody
    @ExceptionHandler(value = CloudException.class)
    public String cloudException(CloudException ex) {
        return errorJSON(ex, ex.getResultCode());
    }

    //404
    @RequestMapping(value = {"/error"})
    public ModelAndView error() {
        return errorHTML(404, "404 Not Found");
    }

    //其他全部错误
    @ResponseBody
    @ExceptionHandler(value = {Throwable.class})
    public String error(@NotNull Exception ex) {
        return errorJSON(ex, new JsonResponse(201, ex.getMessage()));
    }

    public ModelAndView errorHTML(int code, String msg) {
        return new ModelAndView("error", Map.of(
                "code", code,
                "text", msg
        ));
    }

    public ModelAndView errorHTML(Exception e, int code, String msg) {
        log.debug("Controller error:", e);
        return errorHTML(code, msg);
    }

    public String errorJSON(Exception e, @NotNull ResultCode resultCode) {
        return errorJSON(e, resultCode.toJsonResponse());
    }

    public String errorJSON(Exception e, JsonResponse jsonResponse) {
        log.debug("Controller error:", e);
        return JSONObject.toJSONString(jsonResponse);
    }
}
