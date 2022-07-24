package com.nfcat.cloud.data;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.nfcat.cloud.enums.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JsonResponse {

    @JSONField(ordinal = 1)
    public int code = 200;
    @JSONField(ordinal = 2)
    public String msg = "操作成功";
    @JSONField(ordinal = 3)
    public Object data;

    public JsonResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public JsonResponse(@NotNull ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public JsonResponse(@NotNull ResultCode resultCode, Object data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
