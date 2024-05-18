package net.minecraft.client.particle;

import net.minecraft.entity.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.world.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;

public class EntityPickupFX extends EntityFX
{
    private Entity field_174840_a;
    private Entity field_174843_ax;
    private int maxAge;
    private float field_174841_aA;
    private RenderManager field_174842_aB;
    private int age;
    
    public EntityPickupFX(final World world, final Entity field_174840_a, final Entity field_174843_ax, final float field_174841_aA) {
        super(world, field_174840_a.posX, field_174840_a.posY, field_174840_a.posZ, field_174840_a.motionX, field_174840_a.motionY, field_174840_a.motionZ);
        this.field_174842_aB = Minecraft.getMinecraft().getRenderManager();
        this.field_174840_a = field_174840_a;
        this.field_174843_ax = field_174843_ax;
        this.maxAge = "   ".length();
        this.field_174841_aA = field_174841_aA;
    }
    
    @Override
    public void onUpdate() {
        this.age += " ".length();
        if (this.age == this.maxAge) {
            this.setDead();
        }
    }
    
    @Override
    public void renderParticle(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        final float n7 = (this.age + n) / this.maxAge;
        final float n8 = n7 * n7;
        final double posX = this.field_174840_a.posX;
        final double posY = this.field_174840_a.posY;
        final double posZ = this.field_174840_a.posZ;
        final double n9 = this.field_174843_ax.lastTickPosX + (this.field_174843_ax.posX - this.field_174843_ax.lastTickPosX) * n;
        final double n10 = this.field_174843_ax.lastTickPosY + (this.field_174843_ax.posY - this.field_174843_ax.lastTickPosY) * n + this.field_174841_aA;
        final double n11 = this.field_174843_ax.lastTickPosZ + (this.field_174843_ax.posZ - this.field_174843_ax.lastTickPosZ) * n;
        final double n12 = posX + (n9 - posX) * n8;
        final double n13 = posY + (n10 - posY) * n8;
        final double n14 = posZ + (n11 - posZ) * n8;
        final int brightnessForRender = this.getBrightnessForRender(n);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightnessForRender % (30578 + 27259 - 50955 + 58654) / 1.0f, brightnessForRender / (62343 + 37471 - 54882 + 20604) / 1.0f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.field_174842_aB.renderEntityWithPosYaw(this.field_174840_a, (float)(n12 - EntityPickupFX.interpPosX), (float)(n13 - EntityPickupFX.interpPosY), (float)(n14 - EntityPickupFX.interpPosZ), this.field_174840_a.rotationYaw, n);
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getFXLayer() {
        return "   ".length();
    }
}
