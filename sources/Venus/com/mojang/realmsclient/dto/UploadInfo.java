/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.dto;

import com.google.common.annotations.VisibleForTesting;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.realmsclient.dto.ValueObject;
import com.mojang.realmsclient.util.JsonUtils;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UploadInfo
extends ValueObject {
    private static final Logger field_230638_a_ = LogManager.getLogger();
    private static final Pattern field_243085_b = Pattern.compile("^[a-zA-Z][-a-zA-Z0-9+.]+:");
    private final boolean field_230639_b_;
    @Nullable
    private final String field_230640_c_;
    private final URI field_230641_d_;

    private UploadInfo(boolean bl, @Nullable String string, URI uRI) {
        this.field_230639_b_ = bl;
        this.field_230640_c_ = string;
        this.field_230641_d_ = uRI;
    }

    @Nullable
    public static UploadInfo func_230796_a_(String string) {
        try {
            int n;
            URI uRI;
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = jsonParser.parse(string).getAsJsonObject();
            String string2 = JsonUtils.func_225171_a("uploadEndpoint", jsonObject, null);
            if (string2 != null && (uRI = UploadInfo.func_243087_a(string2, n = JsonUtils.func_225172_a("port", jsonObject, -1))) != null) {
                boolean bl = JsonUtils.func_225170_a("worldClosed", jsonObject, false);
                String string3 = JsonUtils.func_225171_a("token", jsonObject, null);
                return new UploadInfo(bl, string3, uRI);
            }
        } catch (Exception exception) {
            field_230638_a_.error("Could not parse UploadInfo: " + exception.getMessage());
        }
        return null;
    }

    @Nullable
    @VisibleForTesting
    public static URI func_243087_a(String string, int n) {
        Matcher matcher = field_243085_b.matcher(string);
        String string2 = UploadInfo.func_243088_a(string, matcher);
        try {
            URI uRI = new URI(string2);
            int n2 = UploadInfo.func_243086_a(n, uRI.getPort());
            return n2 != uRI.getPort() ? new URI(uRI.getScheme(), uRI.getUserInfo(), uRI.getHost(), n2, uRI.getPath(), uRI.getQuery(), uRI.getFragment()) : uRI;
        } catch (URISyntaxException uRISyntaxException) {
            field_230638_a_.warn("Failed to parse URI {}", (Object)string2, (Object)uRISyntaxException);
            return null;
        }
    }

    private static int func_243086_a(int n, int n2) {
        if (n != -1) {
            return n;
        }
        return n2 != -1 ? n2 : 8080;
    }

    private static String func_243088_a(String string, Matcher matcher) {
        return matcher.find() ? string : "http://" + string;
    }

    public static String func_243090_b(@Nullable String string) {
        JsonObject jsonObject = new JsonObject();
        if (string != null) {
            jsonObject.addProperty("token", string);
        }
        return jsonObject.toString();
    }

    @Nullable
    public String func_230795_a_() {
        return this.field_230640_c_;
    }

    public URI func_243089_b() {
        return this.field_230641_d_;
    }

    public boolean func_230799_c_() {
        return this.field_230639_b_;
    }
}

