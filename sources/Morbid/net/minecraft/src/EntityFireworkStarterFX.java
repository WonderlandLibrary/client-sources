package net.minecraft.src;

import net.minecraft.client.*;

public class EntityFireworkStarterFX extends EntityFX
{
    private int field_92042_ax;
    private final EffectRenderer field_92040_ay;
    private NBTTagList fireworkExplosions;
    boolean field_92041_a;
    
    public EntityFireworkStarterFX(final World par1World, final double par2, final double par4, final double par6, final double par8, final double par10, final double par12, final EffectRenderer par14EffectRenderer, final NBTTagCompound par15NBTTagCompound) {
        super(par1World, par2, par4, par6, 0.0, 0.0, 0.0);
        this.field_92042_ax = 0;
        this.motionX = par8;
        this.motionY = par10;
        this.motionZ = par12;
        this.field_92040_ay = par14EffectRenderer;
        this.particleMaxAge = 8;
        if (par15NBTTagCompound != null) {
            this.fireworkExplosions = par15NBTTagCompound.getTagList("Explosions");
            if (this.fireworkExplosions.tagCount() == 0) {
                this.fireworkExplosions = null;
            }
            else {
                this.particleMaxAge = this.fireworkExplosions.tagCount() * 2 - 1;
                for (int var16 = 0; var16 < this.fireworkExplosions.tagCount(); ++var16) {
                    final NBTTagCompound var17 = (NBTTagCompound)this.fireworkExplosions.tagAt(var16);
                    if (var17.getBoolean("Flicker")) {
                        this.field_92041_a = true;
                        this.particleMaxAge += 15;
                        break;
                    }
                }
            }
        }
    }
    
    @Override
    public void renderParticle(final Tessellator par1Tessellator, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
    }
    
    @Override
    public void onUpdate() {
        if (this.field_92042_ax == 0 && this.fireworkExplosions != null) {
            final boolean var1 = this.func_92037_i();
            boolean var2 = false;
            if (this.fireworkExplosions.tagCount() >= 3) {
                var2 = true;
            }
            else {
                for (int var3 = 0; var3 < this.fireworkExplosions.tagCount(); ++var3) {
                    final NBTTagCompound var4 = (NBTTagCompound)this.fireworkExplosions.tagAt(var3);
                    if (var4.getByte("Type") == 1) {
                        var2 = true;
                        break;
                    }
                }
            }
            final String var5 = "fireworks." + (var2 ? "largeBlast" : "blast") + (var1 ? "_far" : "");
            this.worldObj.playSound(this.posX, this.posY, this.posZ, var5, 20.0f, 0.95f + this.rand.nextFloat() * 0.1f, true);
        }
        if (this.field_92042_ax % 2 == 0 && this.fireworkExplosions != null && this.field_92042_ax / 2 < this.fireworkExplosions.tagCount()) {
            final int var6 = this.field_92042_ax / 2;
            final NBTTagCompound var7 = (NBTTagCompound)this.fireworkExplosions.tagAt(var6);
            final byte var8 = var7.getByte("Type");
            final boolean var9 = var7.getBoolean("Trail");
            final boolean var10 = var7.getBoolean("Flicker");
            final int[] var11 = var7.getIntArray("Colors");
            final int[] var12 = var7.getIntArray("FadeColors");
            if (var8 == 1) {
                this.func_92035_a(0.5, 4, var11, var12, var9, var10);
            }
            else if (var8 == 2) {
                this.func_92038_a(0.5, new double[][] { { 0.0, 1.0 }, { 0.3455, 0.309 }, { 0.9511, 0.309 }, { 0.3795918367346939, -0.12653061224489795 }, { 0.6122448979591837, -0.8040816326530612 }, { 0.0, -0.35918367346938773 } }, var11, var12, var9, var10, false);
            }
            else if (var8 == 3) {
                this.func_92038_a(0.5, new double[][] { { 0.0, 0.2 }, { 0.2, 0.2 }, { 0.2, 0.6 }, { 0.6, 0.6 }, { 0.6, 0.2 }, { 0.2, 0.2 }, { 0.2, 0.0 }, { 0.4, 0.0 }, { 0.4, -0.6 }, { 0.2, -0.6 }, { 0.2, -0.4 }, { 0.0, -0.4 } }, var11, var12, var9, var10, true);
            }
            else if (var8 == 4) {
                this.func_92036_a(var11, var12, var9, var10);
            }
            else {
                this.func_92035_a(0.25, 2, var11, var12, var9, var10);
            }
            final int var13 = var11[0];
            final float var14 = ((var13 & 0xFF0000) >> 16) / 255.0f;
            final float var15 = ((var13 & 0xFF00) >> 8) / 255.0f;
            final float var16 = ((var13 & 0xFF) >> 0) / 255.0f;
            final EntityFireworkOverlayFX var17 = new EntityFireworkOverlayFX(this.worldObj, this.posX, this.posY, this.posZ);
            var17.setRBGColorF(var14, var15, var16);
            this.field_92040_ay.addEffect(var17);
        }
        ++this.field_92042_ax;
        if (this.field_92042_ax > this.particleMaxAge) {
            if (this.field_92041_a) {
                final boolean var1 = this.func_92037_i();
                final String var18 = "fireworks." + (var1 ? "twinkle_far" : "twinkle");
                this.worldObj.playSound(this.posX, this.posY, this.posZ, var18, 20.0f, 0.9f + this.rand.nextFloat() * 0.15f, true);
            }
            this.setDead();
        }
    }
    
    private boolean func_92037_i() {
        final Minecraft var1 = Minecraft.getMinecraft();
        return var1 == null || var1.renderViewEntity == null || var1.renderViewEntity.getDistanceSq(this.posX, this.posY, this.posZ) >= 256.0;
    }
    
    private void func_92034_a(final double par1, final double par3, final double par5, final double par7, final double par9, final double par11, final int[] par13ArrayOfInteger, final int[] par14ArrayOfInteger, final boolean par15, final boolean par16) {
        final EntityFireworkSparkFX var17 = new EntityFireworkSparkFX(this.worldObj, par1, par3, par5, par7, par9, par11, this.field_92040_ay);
        var17.func_92045_e(par15);
        var17.func_92043_f(par16);
        final int var18 = this.rand.nextInt(par13ArrayOfInteger.length);
        var17.func_92044_a(par13ArrayOfInteger[var18]);
        if (par14ArrayOfInteger != null && par14ArrayOfInteger.length > 0) {
            var17.func_92046_g(par14ArrayOfInteger[this.rand.nextInt(par14ArrayOfInteger.length)]);
        }
        this.field_92040_ay.addEffect(var17);
    }
    
    private void func_92035_a(final double par1, final int par3, final int[] par4ArrayOfInteger, final int[] par5ArrayOfInteger, final boolean par6, final boolean par7) {
        final double var8 = this.posX;
        final double var9 = this.posY;
        final double var10 = this.posZ;
        for (int var11 = -par3; var11 <= par3; ++var11) {
            for (int var12 = -par3; var12 <= par3; ++var12) {
                for (int var13 = -par3; var13 <= par3; ++var13) {
                    final double var14 = var12 + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5;
                    final double var15 = var11 + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5;
                    final double var16 = var13 + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5;
                    final double var17 = MathHelper.sqrt_double(var14 * var14 + var15 * var15 + var16 * var16) / par1 + this.rand.nextGaussian() * 0.05;
                    this.func_92034_a(var8, var9, var10, var14 / var17, var15 / var17, var16 / var17, par4ArrayOfInteger, par5ArrayOfInteger, par6, par7);
                    if (var11 != -par3 && var11 != par3 && var12 != -par3 && var12 != par3) {
                        var13 += par3 * 2 - 1;
                    }
                }
            }
        }
    }
    
    private void func_92038_a(final double par1, final double[][] par3ArrayOfDouble, final int[] par4ArrayOfInteger, final int[] par5ArrayOfInteger, final boolean par6, final boolean par7, final boolean par8) {
        final double var9 = par3ArrayOfDouble[0][0];
        final double var10 = par3ArrayOfDouble[0][1];
        this.func_92034_a(this.posX, this.posY, this.posZ, var9 * par1, var10 * par1, 0.0, par4ArrayOfInteger, par5ArrayOfInteger, par6, par7);
        final float var11 = this.rand.nextFloat() * 3.1415927f;
        final double var12 = par8 ? 0.034 : 0.34;
        for (int var13 = 0; var13 < 3; ++var13) {
            final double var14 = var11 + var13 * 3.1415927f * var12;
            double var15 = var9;
            double var16 = var10;
            for (int var17 = 1; var17 < par3ArrayOfDouble.length; ++var17) {
                final double var18 = par3ArrayOfDouble[var17][0];
                final double var19 = par3ArrayOfDouble[var17][1];
                for (double var20 = 0.25; var20 <= 1.0; var20 += 0.25) {
                    double var21 = (var15 + (var18 - var15) * var20) * par1;
                    final double var22 = (var16 + (var19 - var16) * var20) * par1;
                    final double var23 = var21 * Math.sin(var14);
                    var21 *= Math.cos(var14);
                    for (double var24 = -1.0; var24 <= 1.0; var24 += 2.0) {
                        this.func_92034_a(this.posX, this.posY, this.posZ, var21 * var24, var22, var23 * var24, par4ArrayOfInteger, par5ArrayOfInteger, par6, par7);
                    }
                }
                var15 = var18;
                var16 = var19;
            }
        }
    }
    
    private void func_92036_a(final int[] par1ArrayOfInteger, final int[] par2ArrayOfInteger, final boolean par3, final boolean par4) {
        final double var5 = this.rand.nextGaussian() * 0.05;
        final double var6 = this.rand.nextGaussian() * 0.05;
        for (int var7 = 0; var7 < 70; ++var7) {
            final double var8 = this.motionX * 0.5 + this.rand.nextGaussian() * 0.15 + var5;
            final double var9 = this.motionZ * 0.5 + this.rand.nextGaussian() * 0.15 + var6;
            final double var10 = this.motionY * 0.5 + this.rand.nextDouble() * 0.5;
            this.func_92034_a(this.posX, this.posY, this.posZ, var8, var10, var9, par1ArrayOfInteger, par2ArrayOfInteger, par3, par4);
        }
    }
    
    @Override
    public int getFXLayer() {
        return 0;
    }
}
