package net.minecraft.world;

import net.minecraft.world.border.*;

class WorldServerMulti$1 implements IBorderListener {
    @Override
    public void onSizeChanged(final WorldBorder border, final double newSize) {
        WorldServerMulti.this.getWorldBorder().setTransition(newSize);
    }
    
    @Override
    public void func_177692_a(final WorldBorder border, final double p_177692_2_, final double p_177692_4_, final long p_177692_6_) {
        WorldServerMulti.this.getWorldBorder().setTransition(p_177692_2_, p_177692_4_, p_177692_6_);
    }
    
    @Override
    public void onCenterChanged(final WorldBorder border, final double x, final double z) {
        WorldServerMulti.this.getWorldBorder().setCenter(x, z);
    }
    
    @Override
    public void onWarningTimeChanged(final WorldBorder border, final int p_177691_2_) {
        WorldServerMulti.this.getWorldBorder().setWarningTime(p_177691_2_);
    }
    
    @Override
    public void onWarningDistanceChanged(final WorldBorder border, final int p_177690_2_) {
        WorldServerMulti.this.getWorldBorder().setWarningDistance(p_177690_2_);
    }
    
    @Override
    public void func_177696_b(final WorldBorder border, final double p_177696_2_) {
        WorldServerMulti.this.getWorldBorder().func_177744_c(p_177696_2_);
    }
    
    @Override
    public void func_177695_c(final WorldBorder border, final double p_177695_2_) {
        WorldServerMulti.this.getWorldBorder().setDamageBuffer(p_177695_2_);
    }
}