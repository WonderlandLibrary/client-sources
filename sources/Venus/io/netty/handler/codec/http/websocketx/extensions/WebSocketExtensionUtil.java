/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.websocketx.extensions;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class WebSocketExtensionUtil {
    private static final String EXTENSION_SEPARATOR = ",";
    private static final String PARAMETER_SEPARATOR = ";";
    private static final char PARAMETER_EQUAL = '=';
    private static final Pattern PARAMETER = Pattern.compile("^([^=]+)(=[\\\"]?([^\\\"]+)[\\\"]?)?$");

    static boolean isWebsocketUpgrade(HttpHeaders httpHeaders) {
        return httpHeaders.containsValue(HttpHeaderNames.CONNECTION, HttpHeaderValues.UPGRADE, false) && httpHeaders.contains(HttpHeaderNames.UPGRADE, HttpHeaderValues.WEBSOCKET, false);
    }

    public static List<WebSocketExtensionData> extractExtensions(String string) {
        String[] stringArray = string.split(EXTENSION_SEPARATOR);
        if (stringArray.length > 0) {
            ArrayList<WebSocketExtensionData> arrayList = new ArrayList<WebSocketExtensionData>(stringArray.length);
            for (String string2 : stringArray) {
                Map<String, String> map;
                String[] stringArray2 = string2.split(PARAMETER_SEPARATOR);
                String string3 = stringArray2[0].trim();
                if (stringArray2.length > 1) {
                    map = new HashMap(stringArray2.length - 1);
                    for (int i = 1; i < stringArray2.length; ++i) {
                        String string4 = stringArray2[i].trim();
                        Matcher matcher = PARAMETER.matcher(string4);
                        if (!matcher.matches() || matcher.group(1) == null) continue;
                        map.put(matcher.group(1), matcher.group(3));
                    }
                } else {
                    map = Collections.emptyMap();
                }
                arrayList.add(new WebSocketExtensionData(string3, map));
            }
            return arrayList;
        }
        return Collections.emptyList();
    }

    static String appendExtension(String string, String string2, Map<String, String> map) {
        StringBuilder stringBuilder = new StringBuilder(string != null ? string.length() : string2.length() + 1);
        if (string != null && !string.trim().isEmpty()) {
            stringBuilder.append(string);
            stringBuilder.append(EXTENSION_SEPARATOR);
        }
        stringBuilder.append(string2);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            stringBuilder.append(PARAMETER_SEPARATOR);
            stringBuilder.append(entry.getKey());
            if (entry.getValue() == null) continue;
            stringBuilder.append('=');
            stringBuilder.append(entry.getValue());
        }
        return stringBuilder.toString();
    }

    private WebSocketExtensionUtil() {
    }
}

