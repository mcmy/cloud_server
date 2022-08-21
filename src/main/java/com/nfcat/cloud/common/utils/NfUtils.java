package com.nfcat.cloud.common.utils;

import org.apache.commons.lang3.ObjectUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class NfUtils {

    private static final int XOR_DEFAUL_TKEY = 2;
    public static final char[] CAPITAL = "QWERTYUIOPASDFGHJKLZXCVBNM".toCharArray();
    public static final char[] LOWER_CASE = "qwertyuiopasdfghjklzxcvbnm".toCharArray();
    public static final char[] NUMBER = "0123456789".toCharArray();
    public static final char[] ALL_CHAR = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm0123456789".toCharArray();

    public static @NotNull String pwdEncrypt(@NotNull String str) {
        return DigestUtils.md5DigestAsHex(xorEncrypt(str.getBytes(StandardCharsets.UTF_8)));
    }

    public static @NotNull String pwdEncrypt(@NotNull String str, @NotNull String salt) {
        return DigestUtils.md5DigestAsHex(xorEncrypt((str + salt).getBytes(StandardCharsets.UTF_8)));
    }

    public static byte[] xorEncrypt(byte @NotNull [] data) {
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) (data[i] ^ XOR_DEFAUL_TKEY);
        }
        return data;
    }

    public static byte @Nullable [] xorEncrypt(byte @NotNull [] bt, byte @NotNull [] key) {
        if (bt.length > key.length) return null;
        for (int i = 0; i < bt.length; i++) {
            bt[i] = (byte) (bt[i] ^ key[i]);
        }
        return bt;
    }

    public static @NotNull String randomCharacter(int l) {
        return randomCharacter(l, ALL_CHAR);
    }

    public static @NotNull String randomCharacter(int l, @NotNull String str) {
        return randomCharacter(l, str.toCharArray());
    }

    public static @NotNull String randomCharacter(int l, char @NotNull [] chars) {
        StringBuilder s = new StringBuilder();
        Random random = new Random();
        for (int i = 0, allLength = chars.length; i < l; i++) {
            s.append(chars[random.nextInt(allLength)]);
        }
        return s.toString();
    }

    public static @NotNull String randToken() {
        return randToken(NfUtils.randomCharacter(24));
    }

    public static @NotNull String randToken(String s) {
        return "k-" + System.currentTimeMillis() + "-" + s;
    }

    public static @NotNull String randSocketID() {
        return randSocketID(NfUtils.randomCharacter(14));
    }

    public static @NotNull String randSocketID(String s) {
        return "s-" + System.currentTimeMillis() + "-" + s;
    }

    public static @NotNull String randUserSuperKey() {
        return randUserSuperKey(NfUtils.randomCharacter(32));
    }

    public static @NotNull String randUserSuperKey(String s) {
        return "sk-" + System.currentTimeMillis() + "-" + s;
    }

    public static @NotNull String randEmailID() {
        return randEmailID(NfUtils.randomCharacter(16));
    }

    public static @NotNull String randEmailID(String s) {
        return "e-" + System.currentTimeMillis() + "-" + s;
    }

    public static @NotNull String randIMGID() {
        return randIMGID(NfUtils.randomCharacter(10));
    }

    public static @NotNull String randIMGID(String s) {
        return "i-" + System.currentTimeMillis() + "-" + s;
    }

    public static @NotNull String randPhoneCode() {
        return randomCharacter(6, "0123456789");
    }

    public static @Nullable String getResourcesText(String path) {
        try {
            InputStream inputStream = getResourcesInputStream(path);
            if (inputStream == null) return null;
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String data;
            StringBuilder stringBuilder = new StringBuilder();
            while ((data = bufferedReader.readLine()) != null) {
                stringBuilder.append(data).append("\n");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            return stringBuilder.toString();
        } catch (Exception ignored) {
        }
        return null;
    }

    public static @Nullable InputStream getResourcesInputStream(String path) {
        try {
            ClassPathResource resource = new ClassPathResource(path);
//            return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
            return resource.getInputStream();
        } catch (Exception ignored) {
        }
        return null;
    }

    public static @NotNull String ListToSeparatorString(String[] list, String separator) {
        if (ObjectUtils.isEmpty(list)) return "";
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : list) {
            stringBuilder.append(s).append(separator);
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    public static LocalDateTime dateToLocalDateTime(final Date date) {
        if (null == date) {
            return null;
        }
        final Instant instant = date.toInstant();
        final ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    public static LocalDate dateToLocalDate(final Date date) {
        if (null == date) {
            return null;
        }
        final Instant instant = date.toInstant();
        final ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDate();
    }

    public static Date localDateTimeToDate(final LocalDateTime localDateTime) {
        if (null == localDateTime) {
            return null;
        }
        final ZoneId zoneId = ZoneId.systemDefault();
        final ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    public static Date localDateToDate(final LocalDate localDate) {
        if (null == localDate) {
            return null;
        }
        final ZoneId zoneId = ZoneId.systemDefault();
        final ZonedDateTime zdt = localDate.atStartOfDay().atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    private static final Pattern linePattern = Pattern.compile("_(\\w)");
    private static final Pattern humpPattern = Pattern.compile("[A-Z]");

    public static @NotNull String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0));
        }
        matcher.appendTail(sb);
        if (sb.indexOf("_") == 0) sb.deleteCharAt(0);
        return sb.toString().toLowerCase();
    }

    public static @NotNull String lineToHump(String str) {
        Matcher matcher = linePattern.matcher("_" + str);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

}