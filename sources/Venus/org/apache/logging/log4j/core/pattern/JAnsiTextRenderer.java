/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.fusesource.jansi.Ansi
 *  org.fusesource.jansi.AnsiRenderer$Code
 */
package org.apache.logging.log4j.core.pattern;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.logging.log4j.core.pattern.TextRenderer;
import org.apache.logging.log4j.status.StatusLogger;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiRenderer;

public final class JAnsiTextRenderer
implements TextRenderer {
    public static final Map<String, AnsiRenderer.Code[]> DefaultExceptionStyleMap;
    static final Map<String, AnsiRenderer.Code[]> DefaultMessageStyleMap;
    private static final Map<String, Map<String, AnsiRenderer.Code[]>> PrefedinedStyleMaps;
    private final String beginToken;
    private final int beginTokenLen;
    private final String endToken;
    private final int endTokenLen;
    private final Map<String, AnsiRenderer.Code[]> styleMap;

    private static void put(Map<String, AnsiRenderer.Code[]> map, String string, AnsiRenderer.Code ... codeArray) {
        map.put(string, codeArray);
    }

    public JAnsiTextRenderer(String[] stringArray, Map<String, AnsiRenderer.Code[]> map) {
        Map<String, AnsiRenderer.Code[]> map2;
        String string = "@|";
        String string2 = "|@";
        if (stringArray.length > 1) {
            String string3 = stringArray[0];
            String[] stringArray2 = string3.split(" ");
            map2 = new HashMap<String, AnsiRenderer.Code[]>(stringArray2.length + map.size());
            map2.putAll(map);
            block10: for (String string4 : stringArray2) {
                String[] stringArray3 = string4.split("=");
                if (stringArray3.length != 2) {
                    StatusLogger.getLogger().warn("{} parsing style \"{}\", expected format: StyleName=Code(,Code)*", (Object)this.getClass().getSimpleName(), (Object)string4);
                    continue;
                }
                String string5 = stringArray3[0];
                String string6 = stringArray3[5];
                String[] stringArray4 = string6.split(",");
                if (stringArray4.length == 0) {
                    StatusLogger.getLogger().warn("{} parsing style \"{}\", expected format: StyleName=Code(,Code)*", (Object)this.getClass().getSimpleName(), (Object)string4);
                    continue;
                }
                switch (string5) {
                    case "BeginToken": {
                        string = stringArray4[0];
                        continue block10;
                    }
                    case "EndToken": {
                        string2 = stringArray4[0];
                        continue block10;
                    }
                    case "StyleMapName": {
                        String string7 = stringArray4[0];
                        Map<String, AnsiRenderer.Code[]> map3 = PrefedinedStyleMaps.get(string7);
                        if (map3 != null) {
                            map2.putAll(map3);
                            continue block10;
                        }
                        StatusLogger.getLogger().warn("Unknown predefined map name {}, pick one of {}", (Object)string7, (Object)null);
                        continue block10;
                    }
                    default: {
                        AnsiRenderer.Code[] codeArray = new AnsiRenderer.Code[stringArray4.length];
                        for (int i = 0; i < codeArray.length; ++i) {
                            codeArray[i] = this.toCode(stringArray4[i]);
                        }
                        map2.put(string5, codeArray);
                    }
                }
            }
        } else {
            map2 = map;
        }
        this.styleMap = map2;
        this.beginToken = string;
        this.endToken = string2;
        this.beginTokenLen = string.length();
        this.endTokenLen = string2.length();
    }

    public Map<String, AnsiRenderer.Code[]> getStyleMap() {
        return this.styleMap;
    }

    private void render(Ansi ansi, AnsiRenderer.Code code) {
        if (code.isColor()) {
            if (code.isBackground()) {
                ansi.bg(code.getColor());
            } else {
                ansi.fg(code.getColor());
            }
        } else if (code.isAttribute()) {
            ansi.a(code.getAttribute());
        }
    }

    private void render(Ansi ansi, AnsiRenderer.Code ... codeArray) {
        for (AnsiRenderer.Code code : codeArray) {
            this.render(ansi, code);
        }
    }

    private String render(String string, String ... stringArray) {
        Ansi ansi = Ansi.ansi();
        for (String string2 : stringArray) {
            AnsiRenderer.Code[] codeArray = this.styleMap.get(string2);
            if (codeArray != null) {
                this.render(ansi, codeArray);
                continue;
            }
            this.render(ansi, this.toCode(string2));
        }
        return ansi.a(string).reset().toString();
    }

    @Override
    public void render(String string, StringBuilder stringBuilder, String string2) throws IllegalArgumentException {
        stringBuilder.append(this.render(string, string2));
    }

    @Override
    public void render(StringBuilder stringBuilder, StringBuilder stringBuilder2) throws IllegalArgumentException {
        int n = 0;
        while (true) {
            int n2;
            if ((n2 = stringBuilder.indexOf(this.beginToken, n)) == -1) {
                if (n == 0) {
                    stringBuilder2.append((CharSequence)stringBuilder);
                    return;
                }
                stringBuilder2.append(stringBuilder.substring(n, stringBuilder.length()));
                return;
            }
            stringBuilder2.append(stringBuilder.substring(n, n2));
            int n3 = stringBuilder.indexOf(this.endToken, n2);
            if (n3 == -1) {
                stringBuilder2.append((CharSequence)stringBuilder);
                return;
            }
            String string = stringBuilder.substring(n2 += this.beginTokenLen, n3);
            String[] stringArray = string.split(" ", 2);
            if (stringArray.length == 1) {
                stringBuilder2.append((CharSequence)stringBuilder);
                return;
            }
            String string2 = this.render(stringArray[0], stringArray[5].split(","));
            stringBuilder2.append(string2);
            n = n3 + this.endTokenLen;
        }
    }

    private AnsiRenderer.Code toCode(String string) {
        return AnsiRenderer.Code.valueOf((String)string.toUpperCase(Locale.ENGLISH));
    }

    public String toString() {
        return "JAnsiMessageRenderer [beginToken=" + this.beginToken + ", beginTokenLen=" + this.beginTokenLen + ", endToken=" + this.endToken + ", endTokenLen=" + this.endTokenLen + ", styleMap=" + this.styleMap + "]";
    }

    static {
        HashMap<String, Map<String, AnsiRenderer.Code[]>> hashMap = new HashMap<String, Map<String, AnsiRenderer.Code[]>>();
        HashMap<String, Object> hashMap2 = new HashMap<String, AnsiRenderer.Code[]>();
        JAnsiTextRenderer.put(hashMap2, "Prefix", AnsiRenderer.Code.WHITE);
        JAnsiTextRenderer.put(hashMap2, "Name", AnsiRenderer.Code.BG_RED, AnsiRenderer.Code.WHITE);
        JAnsiTextRenderer.put(hashMap2, "NameMessageSeparator", AnsiRenderer.Code.BG_RED, AnsiRenderer.Code.WHITE);
        JAnsiTextRenderer.put(hashMap2, "Message", AnsiRenderer.Code.BG_RED, AnsiRenderer.Code.WHITE, AnsiRenderer.Code.BOLD);
        JAnsiTextRenderer.put(hashMap2, "At", AnsiRenderer.Code.WHITE);
        JAnsiTextRenderer.put(hashMap2, "CauseLabel", AnsiRenderer.Code.WHITE);
        JAnsiTextRenderer.put(hashMap2, "Text", AnsiRenderer.Code.WHITE);
        JAnsiTextRenderer.put(hashMap2, "More", AnsiRenderer.Code.WHITE);
        JAnsiTextRenderer.put(hashMap2, "Suppressed", AnsiRenderer.Code.WHITE);
        JAnsiTextRenderer.put(hashMap2, "StackTraceElement.ClassName", AnsiRenderer.Code.YELLOW);
        JAnsiTextRenderer.put(hashMap2, "StackTraceElement.ClassMethodSeparator", AnsiRenderer.Code.YELLOW);
        JAnsiTextRenderer.put(hashMap2, "StackTraceElement.MethodName", AnsiRenderer.Code.YELLOW);
        JAnsiTextRenderer.put(hashMap2, "StackTraceElement.NativeMethod", AnsiRenderer.Code.YELLOW);
        JAnsiTextRenderer.put(hashMap2, "StackTraceElement.FileName", AnsiRenderer.Code.RED);
        JAnsiTextRenderer.put(hashMap2, "StackTraceElement.LineNumber", AnsiRenderer.Code.RED);
        JAnsiTextRenderer.put(hashMap2, "StackTraceElement.Container", AnsiRenderer.Code.RED);
        JAnsiTextRenderer.put(hashMap2, "StackTraceElement.ContainerSeparator", AnsiRenderer.Code.WHITE);
        JAnsiTextRenderer.put(hashMap2, "StackTraceElement.UnknownSource", AnsiRenderer.Code.RED);
        JAnsiTextRenderer.put(hashMap2, "ExtraClassInfo.Inexact", AnsiRenderer.Code.YELLOW);
        JAnsiTextRenderer.put(hashMap2, "ExtraClassInfo.Container", AnsiRenderer.Code.YELLOW);
        JAnsiTextRenderer.put(hashMap2, "ExtraClassInfo.ContainerSeparator", AnsiRenderer.Code.YELLOW);
        JAnsiTextRenderer.put(hashMap2, "ExtraClassInfo.Location", AnsiRenderer.Code.YELLOW);
        JAnsiTextRenderer.put(hashMap2, "ExtraClassInfo.Version", AnsiRenderer.Code.YELLOW);
        DefaultExceptionStyleMap = Collections.unmodifiableMap(hashMap2);
        hashMap.put("Spock", DefaultExceptionStyleMap);
        hashMap2 = new HashMap();
        JAnsiTextRenderer.put(hashMap2, "Prefix", AnsiRenderer.Code.WHITE);
        JAnsiTextRenderer.put(hashMap2, "Name", AnsiRenderer.Code.BG_RED, AnsiRenderer.Code.YELLOW, AnsiRenderer.Code.BOLD);
        JAnsiTextRenderer.put(hashMap2, "NameMessageSeparator", AnsiRenderer.Code.BG_RED, AnsiRenderer.Code.YELLOW);
        JAnsiTextRenderer.put(hashMap2, "Message", AnsiRenderer.Code.BG_RED, AnsiRenderer.Code.WHITE, AnsiRenderer.Code.BOLD);
        JAnsiTextRenderer.put(hashMap2, "At", AnsiRenderer.Code.WHITE);
        JAnsiTextRenderer.put(hashMap2, "CauseLabel", AnsiRenderer.Code.WHITE);
        JAnsiTextRenderer.put(hashMap2, "Text", AnsiRenderer.Code.WHITE);
        JAnsiTextRenderer.put(hashMap2, "More", AnsiRenderer.Code.WHITE);
        JAnsiTextRenderer.put(hashMap2, "Suppressed", AnsiRenderer.Code.WHITE);
        JAnsiTextRenderer.put(hashMap2, "StackTraceElement.ClassName", AnsiRenderer.Code.BG_RED, AnsiRenderer.Code.WHITE);
        JAnsiTextRenderer.put(hashMap2, "StackTraceElement.ClassMethodSeparator", AnsiRenderer.Code.BG_RED, AnsiRenderer.Code.YELLOW);
        JAnsiTextRenderer.put(hashMap2, "StackTraceElement.MethodName", AnsiRenderer.Code.BG_RED, AnsiRenderer.Code.YELLOW);
        JAnsiTextRenderer.put(hashMap2, "StackTraceElement.NativeMethod", AnsiRenderer.Code.BG_RED, AnsiRenderer.Code.YELLOW);
        JAnsiTextRenderer.put(hashMap2, "StackTraceElement.FileName", AnsiRenderer.Code.RED);
        JAnsiTextRenderer.put(hashMap2, "StackTraceElement.LineNumber", AnsiRenderer.Code.RED);
        JAnsiTextRenderer.put(hashMap2, "StackTraceElement.Container", AnsiRenderer.Code.RED);
        JAnsiTextRenderer.put(hashMap2, "StackTraceElement.ContainerSeparator", AnsiRenderer.Code.WHITE);
        JAnsiTextRenderer.put(hashMap2, "StackTraceElement.UnknownSource", AnsiRenderer.Code.RED);
        JAnsiTextRenderer.put(hashMap2, "ExtraClassInfo.Inexact", AnsiRenderer.Code.YELLOW);
        JAnsiTextRenderer.put(hashMap2, "ExtraClassInfo.Container", AnsiRenderer.Code.WHITE);
        JAnsiTextRenderer.put(hashMap2, "ExtraClassInfo.ContainerSeparator", AnsiRenderer.Code.WHITE);
        JAnsiTextRenderer.put(hashMap2, "ExtraClassInfo.Location", AnsiRenderer.Code.YELLOW);
        JAnsiTextRenderer.put(hashMap2, "ExtraClassInfo.Version", AnsiRenderer.Code.YELLOW);
        hashMap.put("Kirk", Collections.unmodifiableMap(hashMap2));
        hashMap2 = new HashMap();
        DefaultMessageStyleMap = Collections.unmodifiableMap(hashMap2);
        PrefedinedStyleMaps = Collections.unmodifiableMap(hashMap);
    }
}

