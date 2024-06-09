/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.client.renderer.texture;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import optifine.Config;
import shadersmod.client.ShadersTex;

public class TextureCompass
extends TextureAtlasSprite {
    public double currentAngle;
    public double angleDelta;
    public static String field_176608_l;
    private static final String __OBFID = "CL_00001071";

    public TextureCompass(String p_i1286_1_) {
        super(p_i1286_1_);
        field_176608_l = p_i1286_1_;
    }

    @Override
    public void updateAnimation() {
        Minecraft var1 = Minecraft.getMinecraft();
        if (var1.theWorld != null && var1.thePlayer != null) {
            this.updateCompass(var1.theWorld, var1.thePlayer.posX, var1.thePlayer.posZ, var1.thePlayer.rotationYaw, false, false);
        } else {
            this.updateCompass(null, 0.0, 0.0, 0.0, true, false);
        }
    }

    public void updateCompass(World worldIn, double p_94241_2_, double p_94241_4_, double p_94241_6_, boolean p_94241_8_, boolean p_94241_9_) {
        if (!this.framesTextureData.isEmpty()) {
            double var10 = 0.0;
            if (worldIn != null && !p_94241_8_) {
                BlockPos var18 = worldIn.getSpawnPoint();
                double var13 = (double)var18.getX() - p_94241_2_;
                double var15 = (double)var18.getZ() - p_94241_4_;
                var10 = - ((p_94241_6_ %= 360.0) - 90.0) * 3.141592653589793 / 180.0 - Math.atan2(var15, var13);
                if (!worldIn.provider.isSurfaceWorld()) {
                    var10 = Math.random() * 3.141592653589793 * 2.0;
                }
            }
            if (p_94241_9_) {
                this.currentAngle = var10;
            } else {
                double var181 = var10 - this.currentAngle;
                while (var181 < -3.141592653589793) {
                    var181 += 6.283185307179586;
                }
                while (var181 >= 3.141592653589793) {
                    var181 -= 6.283185307179586;
                }
                var181 = MathHelper.clamp_double(var181, -1.0, 1.0);
                this.angleDelta += var181 * 0.1;
                this.angleDelta *= 0.8;
                this.currentAngle += this.angleDelta;
            }
            int var182 = (int)((this.currentAngle / 6.283185307179586 + 1.0) * (double)this.framesTextureData.size()) % this.framesTextureData.size();
            while (var182 < 0) {
                var182 = (var182 + this.framesTextureData.size()) % this.framesTextureData.size();
            }
            if (var182 != this.frameCounter) {
                this.frameCounter = var182;
                if (Config.isShaders()) {
                    ShadersTex.uploadTexSub((int[][])this.framesTextureData.get(this.frameCounter), this.width, this.height, this.originX, this.originY, false, false);
                } else {
                    TextureUtil.uploadTextureMipmap((int[][])this.framesTextureData.get(this.frameCounter), this.width, this.height, this.originX, this.originY, false, false);
                }
            }
        }
    }
}

