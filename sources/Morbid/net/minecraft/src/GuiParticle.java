package net.minecraft.src;

import net.minecraft.client.*;
import java.util.*;
import org.lwjgl.opengl.*;

public class GuiParticle extends Gui
{
    private List particles;
    private Minecraft mc;
    
    public GuiParticle(final Minecraft par1Minecraft) {
        this.particles = new ArrayList();
        this.mc = par1Minecraft;
    }
    
    public void update() {
        for (int var1 = 0; var1 < this.particles.size(); ++var1) {
            final Particle var2 = this.particles.get(var1);
            var2.preUpdate();
            var2.update(this);
            if (var2.isDead) {
                this.particles.remove(var1--);
            }
        }
    }
    
    public void draw(final float par1) {
        this.mc.renderEngine.bindTexture("/gui/particles.png");
        for (int var2 = 0; var2 < this.particles.size(); ++var2) {
            final Particle var3 = this.particles.get(var2);
            final int var4 = (int)(var3.prevPosX + (var3.posX - var3.prevPosX) * par1 - 4.0);
            final int var5 = (int)(var3.prevPosY + (var3.posY - var3.prevPosY) * par1 - 4.0);
            final float var6 = (float)(var3.prevTintAlpha + (var3.tintAlpha - var3.prevTintAlpha) * par1);
            final float var7 = (float)(var3.prevTintRed + (var3.tintRed - var3.prevTintRed) * par1);
            final float var8 = (float)(var3.prevTintGreen + (var3.tintGreen - var3.prevTintGreen) * par1);
            final float var9 = (float)(var3.prevTintBlue + (var3.tintBlue - var3.prevTintBlue) * par1);
            GL11.glColor4f(var7, var8, var9, var6);
            this.drawTexturedModalRect(var4, var5, 40, 0, 8, 8);
        }
    }
}
