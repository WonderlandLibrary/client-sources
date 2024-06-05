package net.minecraft.src;

import net.minecraft.client.*;

public class TextureCompass extends TextureStitched
{
    public static TextureCompass compassTexture;
    public double currentAngle;
    public double angleDelta;
    
    public TextureCompass() {
        super("compass");
        TextureCompass.compassTexture = this;
    }
    
    @Override
    public void updateAnimation() {
        final Minecraft var1 = Minecraft.getMinecraft();
        if (Minecraft.theWorld != null && Minecraft.thePlayer != null) {
            this.updateCompass(Minecraft.theWorld, Minecraft.thePlayer.posX, Minecraft.thePlayer.posZ, Minecraft.thePlayer.rotationYaw, false, false);
        }
        else {
            this.updateCompass(null, 0.0, 0.0, 0.0, true, false);
        }
    }
    
    public void updateCompass(final World par1World, final double par2, final double par4, double par6, final boolean par8, final boolean par9) {
        double var10 = 0.0;
        if (par1World != null && !par8) {
            final ChunkCoordinates var11 = par1World.getSpawnPoint();
            final double var12 = var11.posX - par2;
            final double var13 = var11.posZ - par4;
            par6 %= 360.0;
            var10 = -((par6 - 90.0) * 3.141592653589793 / 180.0 - Math.atan2(var13, var12));
            if (!par1World.provider.isSurfaceWorld()) {
                var10 = Math.random() * 3.141592653589793 * 2.0;
            }
        }
        if (par9) {
            this.currentAngle = var10;
        }
        else {
            double var14;
            for (var14 = var10 - this.currentAngle; var14 < -3.141592653589793; var14 += 6.283185307179586) {}
            while (var14 >= 3.141592653589793) {
                var14 -= 6.283185307179586;
            }
            if (var14 < -1.0) {
                var14 = -1.0;
            }
            if (var14 > 1.0) {
                var14 = 1.0;
            }
            this.angleDelta += var14 * 0.1;
            this.angleDelta *= 0.8;
            this.currentAngle += this.angleDelta;
        }
        int var15;
        for (var15 = (int)((this.currentAngle / 6.283185307179586 + 1.0) * this.textureList.size()) % this.textureList.size(); var15 < 0; var15 = (var15 + this.textureList.size()) % this.textureList.size()) {}
        if (var15 != this.frameCounter) {
            this.frameCounter = var15;
            this.textureSheet.copyFrom(this.originX, this.originY, this.textureList.get(this.frameCounter), this.rotated);
        }
    }
}
