/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.resources;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.resources.data.IMetadataSectionSerializer;
import net.minecraft.state.properties.ChestType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;

public class LegacyResourcePackWrapperV4
implements IResourcePack {
    private static final Map<String, Pair<ChestType, ResourceLocation>> field_239475_d_ = Util.make(Maps.newHashMap(), LegacyResourcePackWrapperV4::lambda$static$0);
    private static final List<String> PATTERNS = Lists.newArrayList("base", "border", "bricks", "circle", "creeper", "cross", "curly_border", "diagonal_left", "diagonal_right", "diagonal_up_left", "diagonal_up_right", "flower", "globe", "gradient", "gradient_up", "half_horizontal", "half_horizontal_bottom", "half_vertical", "half_vertical_right", "mojang", "rhombus", "skull", "small_stripes", "square_bottom_left", "square_bottom_right", "square_top_left", "square_top_right", "straight_cross", "stripe_bottom", "stripe_center", "stripe_downleft", "stripe_downright", "stripe_left", "stripe_middle", "stripe_right", "stripe_top", "triangle_bottom", "triangle_top", "triangles_bottom", "triangles_top");
    private static final Set<String> SHIELDS = PATTERNS.stream().map(LegacyResourcePackWrapperV4::lambda$static$1).collect(Collectors.toSet());
    private static final Set<String> BANNERS = PATTERNS.stream().map(LegacyResourcePackWrapperV4::lambda$static$2).collect(Collectors.toSet());
    public static final ResourceLocation SHIELD_BASE = new ResourceLocation("textures/entity/shield_base.png");
    public static final ResourceLocation BANNER_BASE = new ResourceLocation("textures/entity/banner_base.png");
    public static final ResourceLocation OLD_IRON_GOLEM_LOCATION = new ResourceLocation("textures/entity/iron_golem.png");
    private final IResourcePack field_239479_h_;

    public LegacyResourcePackWrapperV4(IResourcePack iResourcePack) {
        this.field_239479_h_ = iResourcePack;
    }

    @Override
    public InputStream getRootResourceStream(String string) throws IOException {
        return this.field_239479_h_.getRootResourceStream(string);
    }

    @Override
    public boolean resourceExists(ResourcePackType resourcePackType, ResourceLocation resourceLocation) {
        if (!"minecraft".equals(resourceLocation.getNamespace())) {
            return this.field_239479_h_.resourceExists(resourcePackType, resourceLocation);
        }
        String string = resourceLocation.getPath();
        if ("textures/misc/enchanted_item_glint.png".equals(string)) {
            return true;
        }
        if ("textures/entity/iron_golem/iron_golem.png".equals(string)) {
            return this.field_239479_h_.resourceExists(resourcePackType, OLD_IRON_GOLEM_LOCATION);
        }
        if (!"textures/entity/conduit/wind.png".equals(string) && !"textures/entity/conduit/wind_vertical.png".equals(string)) {
            if (SHIELDS.contains(string)) {
                return this.field_239479_h_.resourceExists(resourcePackType, SHIELD_BASE) && this.field_239479_h_.resourceExists(resourcePackType, resourceLocation);
            }
            if (!BANNERS.contains(string)) {
                Pair<ChestType, ResourceLocation> pair = field_239475_d_.get(string);
                return pair != null && this.field_239479_h_.resourceExists(resourcePackType, pair.getSecond()) ? true : this.field_239479_h_.resourceExists(resourcePackType, resourceLocation);
            }
            return this.field_239479_h_.resourceExists(resourcePackType, BANNER_BASE) && this.field_239479_h_.resourceExists(resourcePackType, resourceLocation);
        }
        return true;
    }

    @Override
    public InputStream getResourceStream(ResourcePackType resourcePackType, ResourceLocation resourceLocation) throws IOException {
        if (!"minecraft".equals(resourceLocation.getNamespace())) {
            return this.field_239479_h_.getResourceStream(resourcePackType, resourceLocation);
        }
        String string = resourceLocation.getPath();
        if ("textures/entity/iron_golem/iron_golem.png".equals(string)) {
            return this.field_239479_h_.getResourceStream(resourcePackType, OLD_IRON_GOLEM_LOCATION);
        }
        if (SHIELDS.contains(string)) {
            InputStream inputStream = LegacyResourcePackWrapperV4.func_229286_a_(this.field_239479_h_.getResourceStream(resourcePackType, SHIELD_BASE), this.field_239479_h_.getResourceStream(resourcePackType, resourceLocation), 64, 2, 2, 12, 22);
            if (inputStream != null) {
                return inputStream;
            }
        } else if (BANNERS.contains(string)) {
            InputStream inputStream = LegacyResourcePackWrapperV4.func_229286_a_(this.field_239479_h_.getResourceStream(resourcePackType, BANNER_BASE), this.field_239479_h_.getResourceStream(resourcePackType, resourceLocation), 64, 0, 0, 42, 41);
            if (inputStream != null) {
                return inputStream;
            }
        } else {
            if ("textures/entity/enderdragon/dragon.png".equals(string) || "textures/entity/enderdragon/dragon_exploding.png".equals(string)) {
                ByteArrayInputStream byteArrayInputStream;
                try (NativeImage nativeImage = NativeImage.read(this.field_239479_h_.getResourceStream(resourcePackType, resourceLocation));){
                    int n = nativeImage.getWidth() / 256;
                    for (int i = 88 * n; i < 200 * n; ++i) {
                        for (int j = 56 * n; j < 112 * n; ++j) {
                            nativeImage.setPixelRGBA(j, i, 0);
                        }
                    }
                    byteArrayInputStream = new ByteArrayInputStream(nativeImage.getBytes());
                }
                return byteArrayInputStream;
            }
            if ("textures/entity/conduit/closed_eye.png".equals(string) || "textures/entity/conduit/open_eye.png".equals(string)) {
                return LegacyResourcePackWrapperV4.func_229285_a_(this.field_239479_h_.getResourceStream(resourcePackType, resourceLocation));
            }
            Pair<ChestType, ResourceLocation> pair = field_239475_d_.get(string);
            if (pair != null) {
                ChestType chestType = pair.getFirst();
                InputStream inputStream = this.field_239479_h_.getResourceStream(resourcePackType, pair.getSecond());
                if (chestType == ChestType.SINGLE) {
                    return LegacyResourcePackWrapperV4.func_229292_d_(inputStream);
                }
                if (chestType == ChestType.LEFT) {
                    return LegacyResourcePackWrapperV4.func_229289_b_(inputStream);
                }
                if (chestType == ChestType.RIGHT) {
                    return LegacyResourcePackWrapperV4.func_229290_c_(inputStream);
                }
            }
        }
        return this.field_239479_h_.getResourceStream(resourcePackType, resourceLocation);
    }

    @Nullable
    public static InputStream func_229286_a_(InputStream inputStream, InputStream inputStream2, int n, int n2, int n3, int n4, int n5) throws IOException {
        ByteArrayInputStream byteArrayInputStream;
        try (NativeImage nativeImage = NativeImage.read(inputStream2);
             NativeImage nativeImage2 = NativeImage.read(inputStream);){
            int n6 = nativeImage2.getWidth();
            int n7 = nativeImage2.getHeight();
            if (n6 != nativeImage.getWidth() || n7 != nativeImage.getHeight()) {
                InputStream inputStream3 = null;
                return inputStream3;
            }
            try (NativeImage nativeImage3 = new NativeImage(n6, n7, true);){
                int n8 = n6 / n;
                for (int i = n3 * n8; i < n5 * n8; ++i) {
                    for (int j = n2 * n8; j < n4 * n8; ++j) {
                        int n9 = NativeImage.getRed(nativeImage.getPixelRGBA(j, i));
                        int n10 = nativeImage2.getPixelRGBA(j, i);
                        nativeImage3.setPixelRGBA(j, i, NativeImage.getCombined(n9, NativeImage.getBlue(n10), NativeImage.getGreen(n10), NativeImage.getRed(n10)));
                    }
                }
                byteArrayInputStream = new ByteArrayInputStream(nativeImage3.getBytes());
            }
        }
        return byteArrayInputStream;
    }

    public static InputStream func_229285_a_(InputStream inputStream) throws IOException {
        ByteArrayInputStream byteArrayInputStream;
        try (NativeImage nativeImage = NativeImage.read(inputStream);){
            int n = nativeImage.getWidth();
            int n2 = nativeImage.getHeight();
            try (NativeImage nativeImage2 = new NativeImage(2 * n, 2 * n2, true);){
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 0, 0, 0, 0, n, n2, 1, false, false);
                byteArrayInputStream = new ByteArrayInputStream(nativeImage2.getBytes());
            }
        }
        return byteArrayInputStream;
    }

    public static InputStream func_229289_b_(InputStream inputStream) throws IOException {
        ByteArrayInputStream byteArrayInputStream;
        try (NativeImage nativeImage = NativeImage.read(inputStream);){
            int n = nativeImage.getWidth();
            int n2 = nativeImage.getHeight();
            try (NativeImage nativeImage2 = new NativeImage(n / 2, n2, true);){
                int n3 = n2 / 64;
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 29, 0, 29, 0, 15, 14, n3, false, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 59, 0, 14, 0, 15, 14, n3, false, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 29, 14, 43, 14, 15, 5, n3, true, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 44, 14, 29, 14, 14, 5, n3, true, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 58, 14, 14, 14, 15, 5, n3, true, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 29, 19, 29, 19, 15, 14, n3, false, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 59, 19, 14, 19, 15, 14, n3, false, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 29, 33, 43, 33, 15, 10, n3, true, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 44, 33, 29, 33, 14, 10, n3, true, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 58, 33, 14, 33, 15, 10, n3, true, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 2, 0, 2, 0, 1, 1, n3, false, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 4, 0, 1, 0, 1, 1, n3, false, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 2, 1, 3, 1, 1, 4, n3, true, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 3, 1, 2, 1, 1, 4, n3, true, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 4, 1, 1, 1, 1, 4, n3, true, true);
                byteArrayInputStream = new ByteArrayInputStream(nativeImage2.getBytes());
            }
        }
        return byteArrayInputStream;
    }

    public static InputStream func_229290_c_(InputStream inputStream) throws IOException {
        ByteArrayInputStream byteArrayInputStream;
        try (NativeImage nativeImage = NativeImage.read(inputStream);){
            int n = nativeImage.getWidth();
            int n2 = nativeImage.getHeight();
            try (NativeImage nativeImage2 = new NativeImage(n / 2, n2, true);){
                int n3 = n2 / 64;
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 14, 0, 29, 0, 15, 14, n3, false, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 44, 0, 14, 0, 15, 14, n3, false, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 0, 14, 0, 14, 14, 5, n3, true, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 14, 14, 43, 14, 15, 5, n3, true, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 73, 14, 14, 14, 15, 5, n3, true, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 14, 19, 29, 19, 15, 14, n3, false, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 44, 19, 14, 19, 15, 14, n3, false, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 0, 33, 0, 33, 14, 10, n3, true, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 14, 33, 43, 33, 15, 10, n3, true, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 73, 33, 14, 33, 15, 10, n3, true, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 1, 0, 2, 0, 1, 1, n3, false, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 3, 0, 1, 0, 1, 1, n3, false, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 0, 1, 0, 1, 1, 4, n3, true, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 1, 1, 3, 1, 1, 4, n3, true, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 5, 1, 1, 1, 1, 4, n3, true, true);
                byteArrayInputStream = new ByteArrayInputStream(nativeImage2.getBytes());
            }
        }
        return byteArrayInputStream;
    }

    public static InputStream func_229292_d_(InputStream inputStream) throws IOException {
        ByteArrayInputStream byteArrayInputStream;
        try (NativeImage nativeImage = NativeImage.read(inputStream);){
            int n = nativeImage.getWidth();
            int n2 = nativeImage.getHeight();
            try (NativeImage nativeImage2 = new NativeImage(n, n2, true);){
                int n3 = n2 / 64;
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 14, 0, 28, 0, 14, 14, n3, false, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 28, 0, 14, 0, 14, 14, n3, false, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 0, 14, 0, 14, 14, 5, n3, true, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 14, 14, 42, 14, 14, 5, n3, true, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 28, 14, 28, 14, 14, 5, n3, true, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 42, 14, 14, 14, 14, 5, n3, true, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 14, 19, 28, 19, 14, 14, n3, false, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 28, 19, 14, 19, 14, 14, n3, false, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 0, 33, 0, 33, 14, 10, n3, true, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 14, 33, 42, 33, 14, 10, n3, true, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 28, 33, 28, 33, 14, 10, n3, true, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 42, 33, 14, 33, 14, 10, n3, true, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 1, 0, 3, 0, 2, 1, n3, false, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 3, 0, 1, 0, 2, 1, n3, false, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 0, 1, 0, 1, 1, 4, n3, true, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 1, 1, 4, 1, 2, 4, n3, true, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 3, 1, 3, 1, 1, 4, n3, true, true);
                LegacyResourcePackWrapperV4.func_229284_a_(nativeImage, nativeImage2, 4, 1, 1, 1, 2, 4, n3, true, true);
                byteArrayInputStream = new ByteArrayInputStream(nativeImage2.getBytes());
            }
        }
        return byteArrayInputStream;
    }

    @Override
    public Collection<ResourceLocation> getAllResourceLocations(ResourcePackType resourcePackType, String string, String string2, int n, Predicate<String> predicate) {
        return this.field_239479_h_.getAllResourceLocations(resourcePackType, string, string2, n, predicate);
    }

    @Override
    public Set<String> getResourceNamespaces(ResourcePackType resourcePackType) {
        return this.field_239479_h_.getResourceNamespaces(resourcePackType);
    }

    @Override
    @Nullable
    public <T> T getMetadata(IMetadataSectionSerializer<T> iMetadataSectionSerializer) throws IOException {
        return this.field_239479_h_.getMetadata(iMetadataSectionSerializer);
    }

    @Override
    public String getName() {
        return this.field_239479_h_.getName();
    }

    @Override
    public void close() {
        this.field_239479_h_.close();
    }

    private static void func_229284_a_(NativeImage nativeImage, NativeImage nativeImage2, int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl, boolean bl2) {
        n6 *= n7;
        n5 *= n7;
        n3 *= n7;
        n4 *= n7;
        n *= n7;
        n2 *= n7;
        for (int i = 0; i < n6; ++i) {
            for (int j = 0; j < n5; ++j) {
                nativeImage2.setPixelRGBA(n3 + j, n4 + i, nativeImage.getPixelRGBA(n + (bl ? n5 - 1 - j : j), n2 + (bl2 ? n6 - 1 - i : i)));
            }
        }
    }

    private static String lambda$static$2(String string) {
        return "textures/entity/banner/" + string + ".png";
    }

    private static String lambda$static$1(String string) {
        return "textures/entity/shield/" + string + ".png";
    }

    private static void lambda$static$0(HashMap hashMap) {
        hashMap.put("textures/entity/chest/normal_left.png", new Pair<ChestType, ResourceLocation>(ChestType.LEFT, new ResourceLocation("textures/entity/chest/normal_double.png")));
        hashMap.put("textures/entity/chest/normal_right.png", new Pair<ChestType, ResourceLocation>(ChestType.RIGHT, new ResourceLocation("textures/entity/chest/normal_double.png")));
        hashMap.put("textures/entity/chest/normal.png", new Pair<ChestType, ResourceLocation>(ChestType.SINGLE, new ResourceLocation("textures/entity/chest/normal.png")));
        hashMap.put("textures/entity/chest/trapped_left.png", new Pair<ChestType, ResourceLocation>(ChestType.LEFT, new ResourceLocation("textures/entity/chest/trapped_double.png")));
        hashMap.put("textures/entity/chest/trapped_right.png", new Pair<ChestType, ResourceLocation>(ChestType.RIGHT, new ResourceLocation("textures/entity/chest/trapped_double.png")));
        hashMap.put("textures/entity/chest/trapped.png", new Pair<ChestType, ResourceLocation>(ChestType.SINGLE, new ResourceLocation("textures/entity/chest/trapped.png")));
        hashMap.put("textures/entity/chest/christmas_left.png", new Pair<ChestType, ResourceLocation>(ChestType.LEFT, new ResourceLocation("textures/entity/chest/christmas_double.png")));
        hashMap.put("textures/entity/chest/christmas_right.png", new Pair<ChestType, ResourceLocation>(ChestType.RIGHT, new ResourceLocation("textures/entity/chest/christmas_double.png")));
        hashMap.put("textures/entity/chest/christmas.png", new Pair<ChestType, ResourceLocation>(ChestType.SINGLE, new ResourceLocation("textures/entity/chest/christmas.png")));
        hashMap.put("textures/entity/chest/ender.png", new Pair<ChestType, ResourceLocation>(ChestType.SINGLE, new ResourceLocation("textures/entity/chest/ender.png")));
    }
}

