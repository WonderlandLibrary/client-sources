/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.layer;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import net.minecraft.util.Util;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.LayerUtil;
import net.minecraft.world.gen.layer.traits.IAreaTransformer2;
import net.minecraft.world.gen.layer.traits.IDimOffset1Transformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum HillsLayer implements IAreaTransformer2,
IDimOffset1Transformer
{
    INSTANCE;

    private static final Logger LOGGER;
    private static final Int2IntMap field_242940_c;

    @Override
    public int apply(INoiseRandom iNoiseRandom, IArea iArea, IArea iArea2, int n, int n2) {
        int n3 = iArea.getValue(this.getOffsetX(n + 1), this.getOffsetZ(n2 + 1));
        int n4 = iArea2.getValue(this.getOffsetX(n + 1), this.getOffsetZ(n2 + 1));
        if (n3 > 255) {
            LOGGER.debug("old! {}", (Object)n3);
        }
        int n5 = (n4 - 2) % 29;
        if (!LayerUtil.isShallowOcean(n3) && n4 >= 2 && n5 == 1) {
            return field_242940_c.getOrDefault(n3, n3);
        }
        if (iNoiseRandom.random(3) == 0 || n5 == 0) {
            int n6 = n3;
            if (n3 == 2) {
                n6 = 17;
            } else if (n3 == 4) {
                n6 = 18;
            } else if (n3 == 27) {
                n6 = 28;
            } else if (n3 == 29) {
                n6 = 1;
            } else if (n3 == 5) {
                n6 = 19;
            } else if (n3 == 32) {
                n6 = 33;
            } else if (n3 == 30) {
                n6 = 31;
            } else if (n3 == 1) {
                n6 = iNoiseRandom.random(3) == 0 ? 18 : 4;
            } else if (n3 == 12) {
                n6 = 13;
            } else if (n3 == 21) {
                n6 = 22;
            } else if (n3 == 168) {
                n6 = 169;
            } else if (n3 == 0) {
                n6 = 24;
            } else if (n3 == 45) {
                n6 = 48;
            } else if (n3 == 46) {
                n6 = 49;
            } else if (n3 == 10) {
                n6 = 50;
            } else if (n3 == 3) {
                n6 = 34;
            } else if (n3 == 35) {
                n6 = 36;
            } else if (LayerUtil.areBiomesSimilar(n3, 38)) {
                n6 = 37;
            } else if ((n3 == 24 || n3 == 48 || n3 == 49 || n3 == 50) && iNoiseRandom.random(3) == 0) {
                int n7 = n6 = iNoiseRandom.random(2) == 0 ? 1 : 4;
            }
            if (n5 == 0 && n6 != n3) {
                n6 = field_242940_c.getOrDefault(n6, n3);
            }
            if (n6 != n3) {
                int n8 = 0;
                if (LayerUtil.areBiomesSimilar(iArea.getValue(this.getOffsetX(n + 1), this.getOffsetZ(n2 + 0)), n3)) {
                    ++n8;
                }
                if (LayerUtil.areBiomesSimilar(iArea.getValue(this.getOffsetX(n + 2), this.getOffsetZ(n2 + 1)), n3)) {
                    ++n8;
                }
                if (LayerUtil.areBiomesSimilar(iArea.getValue(this.getOffsetX(n + 0), this.getOffsetZ(n2 + 1)), n3)) {
                    ++n8;
                }
                if (LayerUtil.areBiomesSimilar(iArea.getValue(this.getOffsetX(n + 1), this.getOffsetZ(n2 + 2)), n3)) {
                    ++n8;
                }
                if (n8 >= 3) {
                    return n6;
                }
            }
        }
        return n3;
    }

    private static void lambda$static$0(Int2IntOpenHashMap int2IntOpenHashMap) {
        int2IntOpenHashMap.put(1, 129);
        int2IntOpenHashMap.put(2, 130);
        int2IntOpenHashMap.put(3, 131);
        int2IntOpenHashMap.put(4, 132);
        int2IntOpenHashMap.put(5, 133);
        int2IntOpenHashMap.put(6, 134);
        int2IntOpenHashMap.put(12, 140);
        int2IntOpenHashMap.put(21, 149);
        int2IntOpenHashMap.put(23, 151);
        int2IntOpenHashMap.put(27, 155);
        int2IntOpenHashMap.put(28, 156);
        int2IntOpenHashMap.put(29, 157);
        int2IntOpenHashMap.put(30, 158);
        int2IntOpenHashMap.put(32, 160);
        int2IntOpenHashMap.put(33, 161);
        int2IntOpenHashMap.put(34, 162);
        int2IntOpenHashMap.put(35, 163);
        int2IntOpenHashMap.put(36, 164);
        int2IntOpenHashMap.put(37, 165);
        int2IntOpenHashMap.put(38, 166);
        int2IntOpenHashMap.put(39, 167);
    }

    static {
        LOGGER = LogManager.getLogger();
        field_242940_c = Util.make(new Int2IntOpenHashMap(), HillsLayer::lambda$static$0);
    }
}

