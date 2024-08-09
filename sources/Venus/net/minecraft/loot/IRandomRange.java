/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot;

import java.util.Random;
import net.minecraft.util.ResourceLocation;

public interface IRandomRange {
    public static final ResourceLocation CONSTANT = new ResourceLocation("constant");
    public static final ResourceLocation UNIFORM = new ResourceLocation("uniform");
    public static final ResourceLocation BINOMIAL = new ResourceLocation("binomial");

    public int generateInt(Random var1);

    public ResourceLocation getType();
}

