/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.resources;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.InsecureTextureException;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.Property;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DownloadingTexture;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;

public class SkinManager {
    private final TextureManager textureManager;
    private final File skinCacheDir;
    private final MinecraftSessionService sessionService;
    private final LoadingCache<String, Map<MinecraftProfileTexture.Type, MinecraftProfileTexture>> skinCacheLoader;

    public SkinManager(TextureManager textureManager, File file, MinecraftSessionService minecraftSessionService) {
        this.textureManager = textureManager;
        this.skinCacheDir = file;
        this.sessionService = minecraftSessionService;
        this.skinCacheLoader = CacheBuilder.newBuilder().expireAfterAccess(15L, TimeUnit.SECONDS).build(new CacheLoader<String, Map<MinecraftProfileTexture.Type, MinecraftProfileTexture>>(this, minecraftSessionService){
            final MinecraftSessionService val$sessionService;
            final SkinManager this$0;
            {
                this.this$0 = skinManager;
                this.val$sessionService = minecraftSessionService;
            }

            @Override
            public Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> load(String string) {
                GameProfile gameProfile = new GameProfile(null, "dummy_mcdummyface");
                gameProfile.getProperties().put("textures", new Property("textures", string, ""));
                try {
                    return this.val$sessionService.getTextures(gameProfile, false);
                } catch (Throwable throwable) {
                    return ImmutableMap.of();
                }
            }

            @Override
            public Object load(Object object) throws Exception {
                return this.load((String)object);
            }
        });
    }

    public ResourceLocation loadSkin(MinecraftProfileTexture minecraftProfileTexture, MinecraftProfileTexture.Type type) {
        return this.loadSkin(minecraftProfileTexture, type, null);
    }

    private ResourceLocation loadSkin(MinecraftProfileTexture minecraftProfileTexture, MinecraftProfileTexture.Type type, @Nullable ISkinAvailableCallback iSkinAvailableCallback) {
        String string = Hashing.sha1().hashUnencodedChars(minecraftProfileTexture.getHash()).toString();
        ResourceLocation resourceLocation = new ResourceLocation("skins/" + string);
        Texture texture = this.textureManager.getTexture(resourceLocation);
        if (texture != null) {
            if (iSkinAvailableCallback != null) {
                iSkinAvailableCallback.onSkinTextureAvailable(type, resourceLocation, minecraftProfileTexture);
            }
        } else {
            File file = new File(this.skinCacheDir, string.length() > 2 ? string.substring(0, 2) : "xx");
            File file2 = new File(file, string);
            DownloadingTexture downloadingTexture = new DownloadingTexture(file2, minecraftProfileTexture.getUrl(), DefaultPlayerSkin.getDefaultSkinLegacy(), type == MinecraftProfileTexture.Type.SKIN, () -> SkinManager.lambda$loadSkin$0(iSkinAvailableCallback, type, resourceLocation, minecraftProfileTexture));
            this.textureManager.loadTexture(resourceLocation, downloadingTexture);
        }
        return resourceLocation;
    }

    public void loadProfileTextures(GameProfile gameProfile, ISkinAvailableCallback iSkinAvailableCallback, boolean bl) {
        Runnable runnable = () -> this.lambda$loadProfileTextures$4(gameProfile, bl, iSkinAvailableCallback);
        Util.getServerExecutor().execute(runnable);
    }

    public Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> loadSkinFromCache(GameProfile gameProfile) {
        Property property = Iterables.getFirst(gameProfile.getProperties().get("textures"), null);
        return property == null ? ImmutableMap.of() : this.skinCacheLoader.getUnchecked(property.getValue());
    }

    private void lambda$loadProfileTextures$4(GameProfile gameProfile, boolean bl, ISkinAvailableCallback iSkinAvailableCallback) {
        HashMap<MinecraftProfileTexture.Type, MinecraftProfileTexture> hashMap = Maps.newHashMap();
        try {
            hashMap.putAll(this.sessionService.getTextures(gameProfile, bl));
        } catch (InsecureTextureException insecureTextureException) {
            // empty catch block
        }
        if (hashMap.isEmpty()) {
            gameProfile.getProperties().clear();
            if (gameProfile.getId().equals(Minecraft.getInstance().getSession().getProfile().getId())) {
                gameProfile.getProperties().putAll(Minecraft.getInstance().getProfileProperties());
                hashMap.putAll(this.sessionService.getTextures(gameProfile, false));
            } else if (gameProfile.getId() != null) {
                this.sessionService.fillProfileProperties(gameProfile, bl);
                try {
                    hashMap.putAll(this.sessionService.getTextures(gameProfile, bl));
                } catch (InsecureTextureException insecureTextureException) {
                    // empty catch block
                }
            }
        }
        Minecraft.getInstance().execute(() -> this.lambda$loadProfileTextures$3(hashMap, iSkinAvailableCallback));
    }

    private void lambda$loadProfileTextures$3(Map map, ISkinAvailableCallback iSkinAvailableCallback) {
        RenderSystem.recordRenderCall(() -> this.lambda$loadProfileTextures$2(map, iSkinAvailableCallback));
    }

    private void lambda$loadProfileTextures$2(Map map, ISkinAvailableCallback iSkinAvailableCallback) {
        ImmutableList.of(MinecraftProfileTexture.Type.SKIN, MinecraftProfileTexture.Type.CAPE).forEach(arg_0 -> this.lambda$loadProfileTextures$1(map, iSkinAvailableCallback, arg_0));
    }

    private void lambda$loadProfileTextures$1(Map map, ISkinAvailableCallback iSkinAvailableCallback, MinecraftProfileTexture.Type type) {
        if (map.containsKey((Object)type)) {
            this.loadSkin((MinecraftProfileTexture)map.get((Object)type), type, iSkinAvailableCallback);
        }
    }

    private static void lambda$loadSkin$0(ISkinAvailableCallback iSkinAvailableCallback, MinecraftProfileTexture.Type type, ResourceLocation resourceLocation, MinecraftProfileTexture minecraftProfileTexture) {
        if (iSkinAvailableCallback != null) {
            iSkinAvailableCallback.onSkinTextureAvailable(type, resourceLocation, minecraftProfileTexture);
        }
    }

    public static interface ISkinAvailableCallback {
        public void onSkinTextureAvailable(MinecraftProfileTexture.Type var1, ResourceLocation var2, MinecraftProfileTexture var3);
    }
}

