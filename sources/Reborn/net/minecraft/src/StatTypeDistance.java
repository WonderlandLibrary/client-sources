package net.minecraft.src;

final class StatTypeDistance implements IStatType
{
    @Override
    public String format(final int par1) {
        final double var2 = par1 / 100.0;
        final double var3 = var2 / 1000.0;
        return (var3 > 0.5) ? (String.valueOf(StatBase.getDecimalFormat().format(var3)) + " km") : ((var2 > 0.5) ? (String.valueOf(StatBase.getDecimalFormat().format(var2)) + " m") : (String.valueOf(par1) + " cm"));
    }
}
