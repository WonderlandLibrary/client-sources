package net.minecraft.loot;

import net.minecraft.util.ResourceLocation;

import java.util.Random;

public interface IRandomRange
{
    ResourceLocation CONSTANT = new ResourceLocation("constant");
    ResourceLocation UNIFORM = new ResourceLocation("uniform");
    ResourceLocation BINOMIAL = new ResourceLocation("binomial");

    int generateInt(Random rand);

    ResourceLocation getType();
}
