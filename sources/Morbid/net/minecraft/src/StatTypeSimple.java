package net.minecraft.src;

final class StatTypeSimple implements IStatType
{
    @Override
    public String format(final int par1) {
        return StatBase.getNumberFormat().format(par1);
    }
}
