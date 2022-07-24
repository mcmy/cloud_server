package com.nfcat.cloud.enums;

import com.nfcat.cloud.utils.TypeConversionTool;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public enum MatcherString {
    USERNAME("^[a-zA-Z][a-zA-Z0-9_]{4,15}$"),
    PASSWORD("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20}$"),
    EMAIL("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$"),
    PHONE("^(13[0-9]|14[5|7]|15[0-9]|18[0-3|5-9])\\d{8}$"),
    DOMAIN("^[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+\\.?$"),
    URL("^[a-zA-z]{3,5}://[^\\s]*$"),
    QQ("^[1-9][\\d]{4,9}$");

    private final String matcherString;

    MatcherString(String string) {
        this.matcherString = string;
    }

    public static boolean matcher(@NotNull MatcherString matcherString, Object o) {
        if (o == null) return false;
        Pattern r = Pattern.compile(matcherString.getMatcherString());
        Matcher m = r.matcher(TypeConversionTool.string(o));
        return m.matches();
    }

    public boolean matcher(Object o) {
        if (o == null) return false;
        Pattern r = Pattern.compile(matcherString);
        Matcher m = r.matcher(TypeConversionTool.string(o));
        return m.matches();
    }
}
