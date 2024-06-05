package net.minecraft.src;

public interface IMob extends IAnimals
{
    public static final IEntitySelector mobSelector = new FilterIMob();
}
