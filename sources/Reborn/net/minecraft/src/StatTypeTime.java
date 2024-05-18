package net.minecraft.src;

final class StatTypeTime implements IStatType
{
    @Override
    public String format(final int par1) {
        final double var2 = par1 / 20.0;
        final double var3 = var2 / 60.0;
        final double var4 = var3 / 60.0;
        final double var5 = var4 / 24.0;
        final double var6 = var5 / 365.0;
        return (var6 > 0.5) ? (String.valueOf(StatBase.getDecimalFormat().format(var6)) + " y") : ((var5 > 0.5) ? (String.valueOf(StatBase.getDecimalFormat().format(var5)) + " d") : ((var4 > 0.5) ? (String.valueOf(StatBase.getDecimalFormat().format(var4)) + " h") : ((var3 > 0.5) ? (String.valueOf(StatBase.getDecimalFormat().format(var3)) + " m") : (String.valueOf(var2) + " s"))));
    }
}
