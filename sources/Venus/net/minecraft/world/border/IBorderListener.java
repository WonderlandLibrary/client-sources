/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.border;

import net.minecraft.world.border.WorldBorder;

public interface IBorderListener {
    public void onSizeChanged(WorldBorder var1, double var2);

    public void onTransitionStarted(WorldBorder var1, double var2, double var4, long var6);

    public void onCenterChanged(WorldBorder var1, double var2, double var4);

    public void onWarningTimeChanged(WorldBorder var1, int var2);

    public void onWarningDistanceChanged(WorldBorder var1, int var2);

    public void onDamageAmountChanged(WorldBorder var1, double var2);

    public void onDamageBufferChanged(WorldBorder var1, double var2);

    public static class Impl
    implements IBorderListener {
        private final WorldBorder worldBorder;

        public Impl(WorldBorder worldBorder) {
            this.worldBorder = worldBorder;
        }

        @Override
        public void onSizeChanged(WorldBorder worldBorder, double d) {
            this.worldBorder.setTransition(d);
        }

        @Override
        public void onTransitionStarted(WorldBorder worldBorder, double d, double d2, long l) {
            this.worldBorder.setTransition(d, d2, l);
        }

        @Override
        public void onCenterChanged(WorldBorder worldBorder, double d, double d2) {
            this.worldBorder.setCenter(d, d2);
        }

        @Override
        public void onWarningTimeChanged(WorldBorder worldBorder, int n) {
            this.worldBorder.setWarningTime(n);
        }

        @Override
        public void onWarningDistanceChanged(WorldBorder worldBorder, int n) {
            this.worldBorder.setWarningDistance(n);
        }

        @Override
        public void onDamageAmountChanged(WorldBorder worldBorder, double d) {
            this.worldBorder.setDamagePerBlock(d);
        }

        @Override
        public void onDamageBufferChanged(WorldBorder worldBorder, double d) {
            this.worldBorder.setDamageBuffer(d);
        }
    }
}

