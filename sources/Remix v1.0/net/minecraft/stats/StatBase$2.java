package net.minecraft.stats;

static final class StatBase$2 implements IStatType {
    @Override
    public String format(final int p_75843_1_) {
        final double var2 = p_75843_1_ / 20.0;
        final double var3 = var2 / 60.0;
        final double var4 = var3 / 60.0;
        final double var5 = var4 / 24.0;
        final double var6 = var5 / 365.0;
        return (var6 > 0.5) ? (StatBase.access$100().format(var6) + " y") : ((var5 > 0.5) ? (StatBase.access$100().format(var5) + " d") : ((var4 > 0.5) ? (StatBase.access$100().format(var4) + " h") : ((var3 > 0.5) ? (StatBase.access$100().format(var3) + " m") : (var2 + " s"))));
    }
}