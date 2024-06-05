package net.minecraft.stats;

static final class StatBase$3 implements IStatType {
    @Override
    public String format(final int p_75843_1_) {
        final double var2 = p_75843_1_ / 100.0;
        final double var3 = var2 / 1000.0;
        return (var3 > 0.5) ? (StatBase.access$100().format(var3) + " km") : ((var2 > 0.5) ? (StatBase.access$100().format(var2) + " m") : (p_75843_1_ + " cm"));
    }
}