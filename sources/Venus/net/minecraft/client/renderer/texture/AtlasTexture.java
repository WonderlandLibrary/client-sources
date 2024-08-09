/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import java.awt.Dimension;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.StitcherException;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.client.renderer.texture.MissingTextureSprite;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.PngSizeInfo;
import net.minecraft.client.renderer.texture.Stitcher;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.optifine.Config;
import net.optifine.EmissiveTextures;
import net.optifine.SmartAnimations;
import net.optifine.reflect.Reflector;
import net.optifine.shaders.ITextureFormat;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.ShadersTex;
import net.optifine.shaders.ShadersTextureType;
import net.optifine.texture.ColorBlenderLinear;
import net.optifine.texture.IColorBlender;
import net.optifine.util.CounterInt;
import net.optifine.util.TextureUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AtlasTexture
extends Texture
implements ITickable {
    private static final Logger LOGGER = LogManager.getLogger();
    @Deprecated
    public static final ResourceLocation LOCATION_BLOCKS_TEXTURE = PlayerContainer.LOCATION_BLOCKS_TEXTURE;
    @Deprecated
    public static final ResourceLocation LOCATION_PARTICLES_TEXTURE = new ResourceLocation("textures/atlas/particles.png");
    private final List<TextureAtlasSprite> listAnimatedSprites = Lists.newArrayList();
    private final Set<ResourceLocation> sprites = Sets.newHashSet();
    private final Map<ResourceLocation, TextureAtlasSprite> mapUploadedSprites = Maps.newHashMap();
    private final ResourceLocation textureLocation;
    private final int maximumTextureSize;
    private Map<ResourceLocation, TextureAtlasSprite> mapRegisteredSprites = new LinkedHashMap<ResourceLocation, TextureAtlasSprite>();
    private Map<ResourceLocation, TextureAtlasSprite> mapMissingSprites = new LinkedHashMap<ResourceLocation, TextureAtlasSprite>();
    private TextureAtlasSprite[] iconGrid = null;
    private int iconGridSize = -1;
    private int iconGridCountX = -1;
    private int iconGridCountY = -1;
    private double iconGridSizeU = -1.0;
    private double iconGridSizeV = -1.0;
    private CounterInt counterIndexInMap = new CounterInt(0);
    public int atlasWidth = 0;
    public int atlasHeight = 0;
    public int mipmapLevel = 0;
    private int countAnimationsActive;
    private int frameCountAnimations;
    private boolean terrain;
    private boolean shaders;
    private boolean multiTexture;
    private ITextureFormat textureFormat;

    public AtlasTexture(ResourceLocation resourceLocation) {
        this.textureLocation = resourceLocation;
        this.maximumTextureSize = RenderSystem.maxSupportedTextureSize();
        this.terrain = resourceLocation.equals(LOCATION_BLOCKS_TEXTURE);
        this.shaders = Config.isShaders();
        this.multiTexture = Config.isMultiTexture();
        if (this.terrain) {
            Config.setTextureMap(this);
        }
    }

    @Override
    public void loadTexture(IResourceManager iResourceManager) throws IOException {
    }

    public void upload(SheetData sheetData) {
        this.sprites.clear();
        this.sprites.addAll(sheetData.spriteLocations);
        LOGGER.info("Created: {}x{}x{} {}-atlas", (Object)sheetData.width, (Object)sheetData.height, (Object)sheetData.mipmapLevel, (Object)this.textureLocation);
        TextureUtil.prepareImage(this.getGlTextureId(), sheetData.mipmapLevel, sheetData.width, sheetData.height);
        this.atlasWidth = sheetData.width;
        this.atlasHeight = sheetData.height;
        this.mipmapLevel = sheetData.mipmapLevel;
        if (this.shaders) {
            ShadersTex.allocateTextureMapNS(sheetData.mipmapLevel, sheetData.width, sheetData.height, this);
        }
        this.clear();
        for (TextureAtlasSprite iterator2 : sheetData.sprites) {
            this.mapUploadedSprites.put(iterator2.getName(), iterator2);
            try {
                iterator2.uploadMipmaps();
            } catch (Throwable throwable) {
                CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Stitching texture atlas");
                CrashReportCategory crashReportCategory = crashReport.makeCategory("Texture being stitched together");
                crashReportCategory.addDetail("Atlas path", this.textureLocation);
                crashReportCategory.addDetail("Sprite", iterator2);
                throw new ReportedException(crashReport);
            }
            if (!iterator2.hasAnimationMetadata()) continue;
            iterator2.setAnimationIndex(this.listAnimatedSprites.size());
            this.listAnimatedSprites.add(iterator2);
        }
        TextureUtils.refreshCustomSprites(this);
        Config.log("Animated sprites: " + this.listAnimatedSprites.size());
        if (Config.isMultiTexture()) {
            for (TextureAtlasSprite textureAtlasSprite : sheetData.sprites) {
                AtlasTexture.uploadMipmapsSingle(textureAtlasSprite);
                if (textureAtlasSprite.spriteNormal != null) {
                    AtlasTexture.uploadMipmapsSingle(textureAtlasSprite.spriteNormal);
                }
                if (textureAtlasSprite.spriteSpecular == null) continue;
                AtlasTexture.uploadMipmapsSingle(textureAtlasSprite.spriteSpecular);
            }
            GlStateManager.bindTexture(this.getGlTextureId());
        }
        if (Config.isShaders()) {
            TextureAtlasSprite textureAtlasSprite;
            TextureAtlasSprite textureAtlasSprite2;
            List<TextureAtlasSprite> list = sheetData.sprites;
            if (Shaders.configNormalMap) {
                GlStateManager.bindTexture(this.getMultiTexID().norm);
                Iterator iterator2 = list.iterator();
                while (iterator2.hasNext()) {
                    textureAtlasSprite2 = (TextureAtlasSprite)iterator2.next();
                    textureAtlasSprite = textureAtlasSprite2.spriteNormal;
                    if (textureAtlasSprite == null) continue;
                    textureAtlasSprite.uploadMipmaps();
                }
            }
            if (Shaders.configSpecularMap) {
                GlStateManager.bindTexture(this.getMultiTexID().spec);
                Iterator iterator3 = list.iterator();
                while (iterator3.hasNext()) {
                    textureAtlasSprite2 = (TextureAtlasSprite)iterator3.next();
                    textureAtlasSprite = textureAtlasSprite2.spriteSpecular;
                    if (textureAtlasSprite == null) continue;
                    textureAtlasSprite.uploadMipmaps();
                }
            }
            GlStateManager.bindTexture(this.getGlTextureId());
        }
        Reflector.callVoid(Reflector.ForgeHooksClient_onTextureStitchedPost, this);
        this.updateIconGrid(sheetData.width, sheetData.height);
        if (Config.equals(System.getProperty("saveTextureMap"), "true")) {
            Config.dbg("Exporting texture map: " + this.textureLocation);
            TextureUtils.saveGlTexture("debug/" + this.textureLocation.getPath().replaceAll("/", "_"), this.getGlTextureId(), sheetData.mipmapLevel, sheetData.width, sheetData.height);
            if (this.shaders) {
                if (Shaders.configNormalMap) {
                    TextureUtils.saveGlTexture("debug/" + this.textureLocation.getPath().replaceAll("/", "_").replace(".png", "_n.png"), this.multiTex.norm, sheetData.mipmapLevel, sheetData.width, sheetData.height);
                }
                if (Shaders.configSpecularMap) {
                    TextureUtils.saveGlTexture("debug/" + this.textureLocation.getPath().replaceAll("/", "_").replace(".png", "_s.png"), this.multiTex.spec, sheetData.mipmapLevel, sheetData.width, sheetData.height);
                }
                GlStateManager.bindTexture(this.getGlTextureId());
            }
        }
    }

    public SheetData stitch(IResourceManager iResourceManager, Stream<ResourceLocation> stream, IProfiler iProfiler, int n) {
        int n2;
        int n3;
        this.terrain = this.textureLocation.equals(LOCATION_BLOCKS_TEXTURE);
        this.shaders = Config.isShaders();
        this.multiTexture = Config.isMultiTexture();
        this.textureFormat = ITextureFormat.readConfiguration();
        int n4 = n;
        this.mapRegisteredSprites.clear();
        this.mapMissingSprites.clear();
        this.counterIndexInMap.reset();
        iProfiler.startSection("preparing");
        Set<ResourceLocation> set = stream.peek(AtlasTexture::lambda$stitch$0).collect(Collectors.toSet());
        Config.dbg("Multitexture: " + Config.isMultiTexture());
        TextureUtils.registerCustomSprites(this);
        set.addAll(this.mapRegisteredSprites.keySet());
        Set<ResourceLocation> set2 = AtlasTexture.newHashSet(set, this.mapRegisteredSprites.keySet());
        EmissiveTextures.updateIcons(this, set2);
        set.addAll(this.mapRegisteredSprites.keySet());
        if (n >= 4) {
            n4 = this.detectMaxMipmapLevel(set, iResourceManager);
            Config.log("Mipmap levels: " + n4);
        }
        int n5 = TextureUtils.getGLMaximumTextureSize();
        Stitcher stitcher = new Stitcher(n5, n5, n);
        int n6 = Integer.MAX_VALUE;
        this.iconGridSize = n3 = AtlasTexture.getMinSpriteSize(n4);
        int n7 = 1 << n;
        iProfiler.endStartSection("extracting_frames");
        Reflector.callVoid(Reflector.ForgeHooksClient_onTextureStitchedPre, this, set);
        for (TextureAtlasSprite.Info info : this.makeSprites(iResourceManager, set)) {
            n2 = info.getSpriteWidth();
            int n8 = info.getSpriteHeight();
            if (n2 >= 1 && n8 >= 1) {
                int n9;
                if (n2 < n3 || n4 > 0) {
                    int n10 = n9 = n4 > 0 ? TextureUtils.scaleToGrid(n2, n3) : TextureUtils.scaleToMin(n2, n3);
                    if (n9 != n2) {
                        if (!TextureUtils.isPowerOfTwo(n2)) {
                            Config.log("Scaled non power of 2: " + info.getSpriteLocation() + ", " + n2 + " -> " + n9);
                        } else {
                            Config.log("Scaled too small texture: " + info.getSpriteLocation() + ", " + n2 + " -> " + n9);
                        }
                        int n11 = n8 * n9 / n2;
                        info.setSpriteWidth(n9);
                        info.setSpriteHeight(n11);
                        info.setScaleFactor((double)n9 * 1.0 / (double)n2);
                    }
                }
                n6 = Math.min(n6, Math.min(info.getSpriteWidth(), info.getSpriteHeight()));
                n9 = Math.min(Integer.lowestOneBit(info.getSpriteWidth()), Integer.lowestOneBit(info.getSpriteHeight()));
                if (n9 < n7) {
                    LOGGER.warn("Texture {} with size {}x{} limits mip level from {} to {}", (Object)info.getSpriteLocation(), (Object)info.getSpriteWidth(), (Object)info.getSpriteHeight(), (Object)MathHelper.log2(n7), (Object)MathHelper.log2(n9));
                    n7 = n9;
                }
                stitcher.addSprite(info);
                continue;
            }
            Config.warn("Invalid sprite size: " + info.getSpriteLocation());
        }
        int n12 = Math.min(n6, n7);
        int n13 = MathHelper.log2(n12);
        if (n13 < 0) {
            n13 = 0;
        }
        if (n13 < n) {
            LOGGER.warn("{}: dropping miplevel from {} to {}, because of minimum power of two: {}", (Object)this.textureLocation, (Object)n, (Object)n13, (Object)n12);
            n2 = n13;
        } else {
            n2 = n;
        }
        iProfiler.endStartSection("register");
        TextureAtlasSprite.Info info = AtlasTexture.fixSpriteSize(MissingTextureSprite.getSpriteInfo(), n3);
        stitcher.addSprite(info);
        iProfiler.endStartSection("stitching");
        try {
            stitcher.doStitch();
        } catch (StitcherException stitcherException) {
            CrashReport crashReport = CrashReport.makeCrashReport(stitcherException, "Stitching");
            CrashReportCategory crashReportCategory = crashReport.makeCategory("Stitcher");
            crashReportCategory.addDetail("Sprites", stitcherException.getSpriteInfos().stream().map(AtlasTexture::lambda$stitch$1).collect(Collectors.joining(",")));
            crashReportCategory.addDetail("Max Texture Size", n5);
            throw new ReportedException(crashReport);
        }
        iProfiler.endStartSection("loading");
        List<TextureAtlasSprite> list = this.getStitchedSprites(iResourceManager, stitcher, n2);
        iProfiler.endSection();
        return new SheetData(set, stitcher.getCurrentWidth(), stitcher.getCurrentHeight(), n2, list);
    }

    private Collection<TextureAtlasSprite.Info> makeSprites(IResourceManager iResourceManager, Set<ResourceLocation> set) {
        ArrayList<CompletableFuture<Void>> arrayList = Lists.newArrayList();
        ConcurrentLinkedQueue<TextureAtlasSprite.Info> concurrentLinkedQueue = new ConcurrentLinkedQueue<TextureAtlasSprite.Info>();
        for (ResourceLocation resourceLocation : set) {
            if (MissingTextureSprite.getLocation().equals(resourceLocation)) continue;
            arrayList.add(CompletableFuture.runAsync(() -> this.lambda$makeSprites$2(resourceLocation, iResourceManager, concurrentLinkedQueue), Util.getServerExecutor()));
        }
        CompletableFuture.allOf(arrayList.toArray(new CompletableFuture[0])).join();
        return concurrentLinkedQueue;
    }

    private List<TextureAtlasSprite> getStitchedSprites(IResourceManager iResourceManager, Stitcher stitcher, int n) {
        ConcurrentLinkedQueue concurrentLinkedQueue = new ConcurrentLinkedQueue();
        ArrayList arrayList = Lists.newArrayList();
        stitcher.getStitchSlots((arg_0, arg_1, arg_2, arg_3, arg_4) -> this.lambda$getStitchedSprites$4(n, iResourceManager, concurrentLinkedQueue, arrayList, arg_0, arg_1, arg_2, arg_3, arg_4));
        CompletableFuture.allOf(arrayList.toArray(new CompletableFuture[0])).join();
        return Lists.newArrayList(concurrentLinkedQueue);
    }

    @Nullable
    private TextureAtlasSprite loadSprite(IResourceManager iResourceManager, TextureAtlasSprite.Info info, int n, int n2, int n3, int n4, int n5) {
        TextureAtlasSprite textureAtlasSprite;
        block9: {
            ResourceLocation resourceLocation = this.getSpritePath(info.getSpriteLocation());
            IResource iResource = iResourceManager.getResource(resourceLocation);
            try {
                NativeImage nativeImage = NativeImage.read(iResource.getInputStream());
                TextureAtlasSprite textureAtlasSprite2 = new TextureAtlasSprite(this, info, n3, n, n2, n4, n5, nativeImage);
                textureAtlasSprite2.update(iResourceManager);
                textureAtlasSprite = textureAtlasSprite2;
                if (iResource == null) break block9;
            } catch (Throwable throwable) {
                try {
                    if (iResource != null) {
                        try {
                            iResource.close();
                        } catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                } catch (RuntimeException runtimeException) {
                    LOGGER.error("Unable to parse metadata from {}", (Object)resourceLocation, (Object)runtimeException);
                    return null;
                } catch (IOException iOException) {
                    LOGGER.error("Using missing texture, unable to load {}", (Object)resourceLocation, (Object)iOException);
                    return null;
                }
            }
            iResource.close();
        }
        return textureAtlasSprite;
    }

    public ResourceLocation getSpritePath(ResourceLocation resourceLocation) {
        return this.isAbsoluteLocation(resourceLocation) ? new ResourceLocation(resourceLocation.getNamespace(), resourceLocation.getPath() + ".png") : new ResourceLocation(resourceLocation.getNamespace(), String.format("textures/%s%s", resourceLocation.getPath(), ".png"));
    }

    public void updateAnimations() {
        boolean bl = false;
        boolean bl2 = false;
        if (!this.listAnimatedSprites.isEmpty()) {
            this.bindTexture();
        }
        int n = 0;
        for (TextureAtlasSprite textureAtlasSprite : this.listAnimatedSprites) {
            if (!this.isAnimationEnabled(textureAtlasSprite)) continue;
            textureAtlasSprite.updateAnimation();
            if (textureAtlasSprite.isAnimationActive()) {
                ++n;
            }
            if (textureAtlasSprite.spriteNormal != null) {
                bl = true;
            }
            if (textureAtlasSprite.spriteSpecular == null) continue;
            bl2 = true;
        }
        if (Config.isShaders()) {
            if (bl) {
                GlStateManager.bindTexture(this.getMultiTexID().norm);
                for (TextureAtlasSprite textureAtlasSprite : this.listAnimatedSprites) {
                    if (textureAtlasSprite.spriteNormal == null || !this.isAnimationEnabled(textureAtlasSprite) || !textureAtlasSprite.isAnimationActive()) continue;
                    textureAtlasSprite.spriteNormal.updateAnimation();
                    if (!textureAtlasSprite.spriteNormal.isAnimationActive()) continue;
                    ++n;
                }
            }
            if (bl2) {
                GlStateManager.bindTexture(this.getMultiTexID().spec);
                for (TextureAtlasSprite textureAtlasSprite : this.listAnimatedSprites) {
                    if (textureAtlasSprite.spriteSpecular == null || !this.isAnimationEnabled(textureAtlasSprite) || !textureAtlasSprite.isAnimationActive()) continue;
                    textureAtlasSprite.spriteSpecular.updateAnimation();
                    if (!textureAtlasSprite.spriteSpecular.isAnimationActive()) continue;
                    ++n;
                }
            }
            if (bl || bl2) {
                GlStateManager.bindTexture(this.getGlTextureId());
            }
        }
        if (Config.isMultiTexture()) {
            for (TextureAtlasSprite textureAtlasSprite : this.listAnimatedSprites) {
                if (!this.isAnimationEnabled(textureAtlasSprite) || !textureAtlasSprite.isAnimationActive()) continue;
                n += AtlasTexture.updateAnimationSingle(textureAtlasSprite);
                if (textureAtlasSprite.spriteNormal != null) {
                    n += AtlasTexture.updateAnimationSingle(textureAtlasSprite.spriteNormal);
                }
                if (textureAtlasSprite.spriteSpecular == null) continue;
                n += AtlasTexture.updateAnimationSingle(textureAtlasSprite.spriteSpecular);
            }
            GlStateManager.bindTexture(this.getGlTextureId());
        }
        if (this.terrain) {
            int n2 = Config.getMinecraft().worldRenderer.getFrameCount();
            if (n2 != this.frameCountAnimations) {
                this.countAnimationsActive = n;
                this.frameCountAnimations = n2;
            }
            if (SmartAnimations.isActive()) {
                SmartAnimations.resetSpritesRendered(this);
            }
        }
    }

    @Override
    public void tick() {
        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(this::updateAnimations);
        } else {
            this.updateAnimations();
        }
    }

    public TextureAtlasSprite getSprite(ResourceLocation resourceLocation) {
        TextureAtlasSprite textureAtlasSprite = this.mapUploadedSprites.get(resourceLocation);
        return textureAtlasSprite == null ? this.mapUploadedSprites.get(MissingTextureSprite.getLocation()) : textureAtlasSprite;
    }

    public void clear() {
        for (TextureAtlasSprite textureAtlasSprite : this.mapUploadedSprites.values()) {
            textureAtlasSprite.close();
        }
        if (this.multiTexture) {
            for (TextureAtlasSprite textureAtlasSprite : this.mapUploadedSprites.values()) {
                textureAtlasSprite.deleteSpriteTexture();
                if (textureAtlasSprite.spriteNormal != null) {
                    textureAtlasSprite.spriteNormal.deleteSpriteTexture();
                }
                if (textureAtlasSprite.spriteSpecular == null) continue;
                textureAtlasSprite.spriteSpecular.deleteSpriteTexture();
            }
        }
        this.mapUploadedSprites.clear();
        this.listAnimatedSprites.clear();
    }

    public ResourceLocation getTextureLocation() {
        return this.textureLocation;
    }

    public void setBlurMipmap(SheetData sheetData) {
        this.setBlurMipmapDirect(false, sheetData.mipmapLevel > 0);
    }

    private boolean isAbsoluteLocation(ResourceLocation resourceLocation) {
        String string = resourceLocation.getPath();
        return this.isAbsoluteLocationPath(string);
    }

    private boolean isAbsoluteLocationPath(String string) {
        String string2 = string.toLowerCase();
        return string2.startsWith("optifine/");
    }

    public TextureAtlasSprite getRegisteredSprite(String string) {
        ResourceLocation resourceLocation = new ResourceLocation(string);
        return this.getRegisteredSprite(resourceLocation);
    }

    public TextureAtlasSprite getRegisteredSprite(ResourceLocation resourceLocation) {
        return this.mapRegisteredSprites.get(resourceLocation);
    }

    public TextureAtlasSprite getUploadedSprite(String string) {
        ResourceLocation resourceLocation = new ResourceLocation(string);
        return this.getUploadedSprite(resourceLocation);
    }

    public TextureAtlasSprite getUploadedSprite(ResourceLocation resourceLocation) {
        return this.mapUploadedSprites.get(resourceLocation);
    }

    private boolean isAnimationEnabled(TextureAtlasSprite textureAtlasSprite) {
        if (!this.terrain) {
            return false;
        }
        if (textureAtlasSprite != TextureUtils.iconWaterStill && textureAtlasSprite != TextureUtils.iconWaterFlow) {
            if (textureAtlasSprite != TextureUtils.iconLavaStill && textureAtlasSprite != TextureUtils.iconLavaFlow) {
                if (textureAtlasSprite != TextureUtils.iconFireLayer0 && textureAtlasSprite != TextureUtils.iconFireLayer1) {
                    if (textureAtlasSprite != TextureUtils.iconSoulFireLayer0 && textureAtlasSprite != TextureUtils.iconSoulFireLayer1) {
                        if (textureAtlasSprite != TextureUtils.iconCampFire && textureAtlasSprite != TextureUtils.iconCampFireLogLit) {
                            if (textureAtlasSprite != TextureUtils.iconSoulCampFire && textureAtlasSprite != TextureUtils.iconSoulCampFireLogLit) {
                                return textureAtlasSprite == TextureUtils.iconPortal ? Config.isAnimatedPortal() : Config.isAnimatedTerrain();
                            }
                            return Config.isAnimatedFire();
                        }
                        return Config.isAnimatedFire();
                    }
                    return Config.isAnimatedFire();
                }
                return Config.isAnimatedFire();
            }
            return Config.isAnimatedLava();
        }
        return Config.isAnimatedWater();
    }

    private static void uploadMipmapsSingle(TextureAtlasSprite textureAtlasSprite) {
        TextureAtlasSprite textureAtlasSprite2 = textureAtlasSprite.spriteSingle;
        if (textureAtlasSprite2 != null) {
            textureAtlasSprite2.setAnimationIndex(textureAtlasSprite.getAnimationIndex());
            textureAtlasSprite.bindSpriteTexture();
            try {
                textureAtlasSprite2.uploadMipmaps();
            } catch (Exception exception) {
                Config.dbg("Error uploading sprite single: " + textureAtlasSprite2 + ", parent: " + textureAtlasSprite);
                exception.printStackTrace();
            }
        }
    }

    private static int updateAnimationSingle(TextureAtlasSprite textureAtlasSprite) {
        TextureAtlasSprite textureAtlasSprite2 = textureAtlasSprite.spriteSingle;
        if (textureAtlasSprite2 != null) {
            textureAtlasSprite.bindSpriteTexture();
            textureAtlasSprite2.updateAnimation();
            if (textureAtlasSprite2.isAnimationActive()) {
                return 0;
            }
        }
        return 1;
    }

    public int getCountRegisteredSprites() {
        return this.counterIndexInMap.getValue();
    }

    private int detectMaxMipmapLevel(Set<ResourceLocation> set, IResourceManager iResourceManager) {
        int n;
        int n2 = this.detectMinimumSpriteSize(set, iResourceManager, 20);
        if (n2 < 16) {
            n2 = 16;
        }
        if ((n2 = MathHelper.smallestEncompassingPowerOfTwo(n2)) > 16) {
            Config.log("Sprite size: " + n2);
        }
        if ((n = MathHelper.log2(n2)) < 4) {
            n = 4;
        }
        return n;
    }

    private int detectMinimumSpriteSize(Set<ResourceLocation> set, IResourceManager iResourceManager, int n) {
        int n2;
        int n3;
        Object exception;
        Object object;
        HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
        for (ResourceLocation object32 : set) {
            object = this.getSpritePath(object32);
            try {
                InputStream n6;
                exception = iResourceManager.getResource((ResourceLocation)object);
                if (exception == null || (n6 = exception.getInputStream()) == null) continue;
                Dimension n7 = TextureUtils.getImageSize(n6, "png");
                n6.close();
                if (n7 == null) continue;
                int iterator2 = n7.width;
                n3 = MathHelper.smallestEncompassingPowerOfTwo(iterator2);
                if (!hashMap.containsKey(n3)) {
                    hashMap.put(n3, 1);
                    continue;
                }
                n2 = (Integer)hashMap.get(n3);
                hashMap.put(n3, n2 + 1);
            } catch (Exception n8) {}
        }
        int n5 = 0;
        Set set2 = hashMap.keySet();
        object = new TreeSet(set2);
        exception = object.iterator();
        while (exception.hasNext()) {
            int n9 = (Integer)exception.next();
            int n10 = (Integer)hashMap.get(n9);
            n5 += n10;
        }
        int n4 = 16;
        int n6 = 0;
        int n7 = n5 * n / 100;
        Iterator iterator2 = object.iterator();
        while (iterator2.hasNext()) {
            n3 = (Integer)iterator2.next();
            n2 = (Integer)hashMap.get(n3);
            n6 += n2;
            if (n3 > n4) {
                n4 = n3;
            }
            if (n6 <= n7) continue;
            return n4;
        }
        return n4;
    }

    private static int getMinSpriteSize(int n) {
        int n2 = 1 << n;
        if (n2 < 8) {
            n2 = 8;
        }
        return n2;
    }

    private static TextureAtlasSprite.Info fixSpriteSize(TextureAtlasSprite.Info info, int n) {
        if (info.getSpriteWidth() >= n && info.getSpriteHeight() >= n) {
            return info;
        }
        int n2 = Math.max(info.getSpriteWidth(), n);
        int n3 = Math.max(info.getSpriteHeight(), n);
        return new TextureAtlasSprite.Info(info.getSpriteLocation(), n2, n3, info.getSpriteAnimationMetadata());
    }

    public boolean isTextureBound() {
        int n;
        int n2 = GlStateManager.getBoundTexture();
        return n2 == (n = this.getGlTextureId());
    }

    private void updateIconGrid(int n, int n2) {
        this.iconGridCountX = -1;
        this.iconGridCountY = -1;
        this.iconGrid = null;
        if (this.iconGridSize > 0) {
            this.iconGridCountX = n / this.iconGridSize;
            this.iconGridCountY = n2 / this.iconGridSize;
            this.iconGrid = new TextureAtlasSprite[this.iconGridCountX * this.iconGridCountY];
            this.iconGridSizeU = 1.0 / (double)this.iconGridCountX;
            this.iconGridSizeV = 1.0 / (double)this.iconGridCountY;
            for (TextureAtlasSprite textureAtlasSprite : this.mapUploadedSprites.values()) {
                double d = 0.5 / (double)n;
                double d2 = 0.5 / (double)n2;
                double d3 = (double)Math.min(textureAtlasSprite.getMinU(), textureAtlasSprite.getMaxU()) + d;
                double d4 = (double)Math.min(textureAtlasSprite.getMinV(), textureAtlasSprite.getMaxV()) + d2;
                double d5 = (double)Math.max(textureAtlasSprite.getMinU(), textureAtlasSprite.getMaxU()) - d;
                double d6 = (double)Math.max(textureAtlasSprite.getMinV(), textureAtlasSprite.getMaxV()) - d2;
                int n3 = (int)(d3 / this.iconGridSizeU);
                int n4 = (int)(d4 / this.iconGridSizeV);
                int n5 = (int)(d5 / this.iconGridSizeU);
                int n6 = (int)(d6 / this.iconGridSizeV);
                for (int i = n3; i <= n5; ++i) {
                    if (i >= 0 && i < this.iconGridCountX) {
                        for (int j = n4; j <= n6; ++j) {
                            if (j >= 0 && j < this.iconGridCountX) {
                                int n7 = j * this.iconGridCountX + i;
                                this.iconGrid[n7] = textureAtlasSprite;
                                continue;
                            }
                            Config.warn("Invalid grid V: " + j + ", icon: " + textureAtlasSprite.getName());
                        }
                        continue;
                    }
                    Config.warn("Invalid grid U: " + i + ", icon: " + textureAtlasSprite.getName());
                }
            }
        }
    }

    public TextureAtlasSprite getIconByUV(double d, double d2) {
        if (this.iconGrid == null) {
            return null;
        }
        int n = (int)(d2 / this.iconGridSizeV);
        int n2 = (int)(d / this.iconGridSizeU);
        int n3 = n * this.iconGridCountX + n2;
        return n3 >= 0 && n3 <= this.iconGrid.length ? this.iconGrid[n3] : null;
    }

    public int getCountAnimations() {
        return this.listAnimatedSprites.size();
    }

    public int getCountAnimationsActive() {
        return this.countAnimationsActive;
    }

    public TextureAtlasSprite registerSprite(ResourceLocation resourceLocation) {
        if (resourceLocation == null) {
            throw new IllegalArgumentException("Location cannot be null!");
        }
        TextureAtlasSprite textureAtlasSprite = this.mapRegisteredSprites.get(resourceLocation);
        if (textureAtlasSprite != null) {
            return textureAtlasSprite;
        }
        this.sprites.add(resourceLocation);
        textureAtlasSprite = new TextureAtlasSprite(resourceLocation);
        this.mapRegisteredSprites.put(resourceLocation, textureAtlasSprite);
        textureAtlasSprite.updateIndexInMap(this.counterIndexInMap);
        return textureAtlasSprite;
    }

    public Collection<TextureAtlasSprite> getRegisteredSprites() {
        return Collections.unmodifiableCollection(this.mapRegisteredSprites.values());
    }

    public boolean isTerrain() {
        return this.terrain;
    }

    public CounterInt getCounterIndexInMap() {
        return this.counterIndexInMap;
    }

    private void onSpriteMissing(ResourceLocation resourceLocation) {
        TextureAtlasSprite textureAtlasSprite = this.mapRegisteredSprites.get(resourceLocation);
        if (textureAtlasSprite != null) {
            this.mapMissingSprites.put(resourceLocation, textureAtlasSprite);
        }
    }

    private static <T> Set<T> newHashSet(Set<T> set, Set<T> set2) {
        HashSet<T> hashSet = new HashSet<T>();
        hashSet.addAll(set);
        hashSet.addAll(set2);
        return hashSet;
    }

    public int getMipmapLevel() {
        return this.mipmapLevel;
    }

    public boolean isMipmaps() {
        return this.mipmapLevel > 0;
    }

    public ITextureFormat getTextureFormat() {
        return this.textureFormat;
    }

    public IColorBlender getShadersColorBlender(ShadersTextureType shadersTextureType) {
        if (shadersTextureType == null) {
            return null;
        }
        return this.textureFormat != null ? this.textureFormat.getColorBlender(shadersTextureType) : new ColorBlenderLinear();
    }

    public boolean isTextureBlend(ShadersTextureType shadersTextureType) {
        if (shadersTextureType == null) {
            return false;
        }
        return this.textureFormat != null ? this.textureFormat.isTextureBlend(shadersTextureType) : true;
    }

    public boolean isNormalBlend() {
        return this.isTextureBlend(ShadersTextureType.NORMAL);
    }

    public boolean isSpecularBlend() {
        return this.isTextureBlend(ShadersTextureType.SPECULAR);
    }

    public String toString() {
        return "" + this.textureLocation;
    }

    private void lambda$getStitchedSprites$4(int n, IResourceManager iResourceManager, ConcurrentLinkedQueue concurrentLinkedQueue, List list, TextureAtlasSprite.Info info, int n2, int n3, int n4, int n5) {
        if (info.getSpriteLocation().equals(MissingTextureSprite.getSpriteInfo().getSpriteLocation())) {
            MissingTextureSprite missingTextureSprite = new MissingTextureSprite(this, info, n, n2, n3, n4, n5);
            missingTextureSprite.update(iResourceManager);
            concurrentLinkedQueue.add(missingTextureSprite);
        } else {
            list.add(CompletableFuture.runAsync(() -> this.lambda$getStitchedSprites$3(iResourceManager, info, n2, n3, n, n4, n5, concurrentLinkedQueue), Util.getServerExecutor()));
        }
    }

    private void lambda$getStitchedSprites$3(IResourceManager iResourceManager, TextureAtlasSprite.Info info, int n, int n2, int n3, int n4, int n5, ConcurrentLinkedQueue concurrentLinkedQueue) {
        TextureAtlasSprite textureAtlasSprite = this.loadSprite(iResourceManager, info, n, n2, n3, n4, n5);
        if (textureAtlasSprite != null) {
            concurrentLinkedQueue.add(textureAtlasSprite);
        }
    }

    private void lambda$makeSprites$2(ResourceLocation resourceLocation, IResourceManager iResourceManager, ConcurrentLinkedQueue concurrentLinkedQueue) {
        TextureAtlasSprite.Info info;
        ResourceLocation resourceLocation2 = this.getSpritePath(resourceLocation);
        try (IResource iResource = iResourceManager.getResource(resourceLocation2);){
            PngSizeInfo pngSizeInfo = new PngSizeInfo(iResource.toString(), iResource.getInputStream());
            AnimationMetadataSection animationMetadataSection = iResource.getMetadata(AnimationMetadataSection.SERIALIZER);
            if (animationMetadataSection == null) {
                animationMetadataSection = AnimationMetadataSection.EMPTY;
            }
            Pair<Integer, Integer> pair = animationMetadataSection.getSpriteSize(pngSizeInfo.width, pngSizeInfo.height);
            info = new TextureAtlasSprite.Info(resourceLocation, pair.getFirst(), pair.getSecond(), animationMetadataSection);
        } catch (RuntimeException runtimeException) {
            LOGGER.error("Unable to parse metadata from {} : {}", (Object)resourceLocation2, (Object)runtimeException);
            this.onSpriteMissing(resourceLocation);
            return;
        } catch (IOException iOException) {
            LOGGER.error("Using missing texture, unable to load {} : {}", (Object)resourceLocation2, (Object)iOException);
            this.onSpriteMissing(resourceLocation);
            return;
        }
        concurrentLinkedQueue.add(info);
    }

    private static String lambda$stitch$1(TextureAtlasSprite.Info info) {
        return String.format("%s[%dx%d]", info.getSpriteLocation(), info.getSpriteWidth(), info.getSpriteHeight());
    }

    private static void lambda$stitch$0(ResourceLocation resourceLocation) {
        if (resourceLocation == null) {
            throw new IllegalArgumentException("Location cannot be null!");
        }
    }

    public static class SheetData {
        final Set<ResourceLocation> spriteLocations;
        final int width;
        final int height;
        final int mipmapLevel;
        final List<TextureAtlasSprite> sprites;

        public SheetData(Set<ResourceLocation> set, int n, int n2, int n3, List<TextureAtlasSprite> list) {
            this.spriteLocations = set;
            this.width = n;
            this.height = n2;
            this.mipmapLevel = n3;
            this.sprites = list;
        }
    }
}

