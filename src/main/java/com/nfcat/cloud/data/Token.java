package com.nfcat.cloud.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Token {
    public String token;
    public LocalDateTime createTime;
    public LocalDateTime refreshTime;

    public Map<String, Object> buildMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("createTime", createTime);
        map.put("refreshTime", refreshTime);
        return map;
    }

    public static Token buildToken(Map<String, Object> map) {
        if (map == null) return new Token();
        return new Token()
                .setToken(toString(map.get("token")))
                .setCreateTime(toLocalDateTime(map.get("createTime")))
                .setRefreshTime(toLocalDateTime(map.get("refreshTime")));
    }

    private static String toString(Object o) {
        if (o == null) return null;
        return o.toString();
    }

    private static LocalDateTime toLocalDateTime(Object o) {
        if (o == null) return null;
        if (o instanceof LocalDateTime l){
            return l;
        }
        return null;
    }
}
