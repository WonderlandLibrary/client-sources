/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.util.UUIDTypeAdapter;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import net.minecraft.client.Minecraft;

public class RealmsUtil {
    private static final YggdrasilAuthenticationService field_225195_b = new YggdrasilAuthenticationService(Minecraft.getInstance().getProxy());
    private static final MinecraftSessionService field_225196_c = field_225195_b.createMinecraftSessionService();
    public static LoadingCache<String, GameProfile> field_225194_a = CacheBuilder.newBuilder().expireAfterWrite(60L, TimeUnit.MINUTES).build(new CacheLoader<String, GameProfile>(){

        @Override
        public GameProfile load(String string) throws Exception {
            GameProfile gameProfile = field_225196_c.fillProfileProperties(new GameProfile(UUIDTypeAdapter.fromString(string), null), false);
            if (gameProfile == null) {
                throw new Exception("Couldn't get profile");
            }
            return gameProfile;
        }

        @Override
        public Object load(Object object) throws Exception {
            return this.load((String)object);
        }
    });

    public static String func_225193_a(String string) throws Exception {
        GameProfile gameProfile = field_225194_a.get(string);
        return gameProfile.getName();
    }

    public static Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> func_225191_b(String string) {
        try {
            GameProfile gameProfile = field_225194_a.get(string);
            return field_225196_c.getTextures(gameProfile, false);
        } catch (Exception exception) {
            return Maps.newHashMap();
        }
    }

    public static String func_225192_a(long l) {
        if (l < 0L) {
            return "right now";
        }
        long l2 = l / 1000L;
        if (l2 < 60L) {
            return (String)(l2 == 1L ? "1 second" : l2 + " seconds") + " ago";
        }
        if (l2 < 3600L) {
            long l3 = l2 / 60L;
            return (String)(l3 == 1L ? "1 minute" : l3 + " minutes") + " ago";
        }
        if (l2 < 86400L) {
            long l4 = l2 / 3600L;
            return (String)(l4 == 1L ? "1 hour" : l4 + " hours") + " ago";
        }
        long l5 = l2 / 86400L;
        return (String)(l5 == 1L ? "1 day" : l5 + " days") + " ago";
    }

    public static String func_238105_a_(Date date) {
        return RealmsUtil.func_225192_a(System.currentTimeMillis() - date.getTime());
    }
}

