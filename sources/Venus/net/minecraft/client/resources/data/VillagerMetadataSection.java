/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.resources.data;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.client.renderer.texture.TextureAtlasSpriteStitcher;

public class VillagerMetadataSection {
    public static final TextureAtlasSpriteStitcher field_217827_a = new TextureAtlasSpriteStitcher();
    private final HatType field_217828_b;

    public VillagerMetadataSection(HatType hatType) {
        this.field_217828_b = hatType;
    }

    public HatType func_217826_a() {
        return this.field_217828_b;
    }

    public static enum HatType {
        NONE("none"),
        PARTIAL("partial"),
        FULL("full");

        private static final Map<String, HatType> field_217824_d;
        private final String field_217825_e;

        private HatType(String string2) {
            this.field_217825_e = string2;
        }

        public String func_217823_a() {
            return this.field_217825_e;
        }

        public static HatType func_217821_a(String string) {
            return field_217824_d.getOrDefault(string, NONE);
        }

        private static HatType lambda$static$0(HatType hatType) {
            return hatType;
        }

        static {
            field_217824_d = Arrays.stream(HatType.values()).collect(Collectors.toMap(HatType::func_217823_a, HatType::lambda$static$0));
        }
    }
}

