package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.opengl.GL11;

@ModInfo(Ø­áŒŠá = Category.DISPLAY, Ý = 0, Â = "See where your Arrow flyes", HorizonCode_Horizon_È = "Trajectories")
public class Trajectories extends Mod
{
    @Handler
    public void HorizonCode_Horizon_È(final EventRender3D _event) {
        boolean bow = false;
        if (this.Â.á.Çª() != null) {
            if ((this.Â.á.Çª().HorizonCode_Horizon_È() instanceof ItemBow && this.Â.á.Ñ¢Ó()) || this.Â.á.Çª().HorizonCode_Horizon_È() instanceof ItemSnowball || this.Â.á.Çª().HorizonCode_Horizon_È() instanceof ItemEnderPearl || this.Â.á.Çª().HorizonCode_Horizon_È() instanceof ItemEgg || this.Â.á.Çª().HorizonCode_Horizon_È() instanceof ItemFishingRod) {
                this.HorizonCode_Horizon_È(true);
                bow = (this.Â.á.Çª().HorizonCode_Horizon_È() instanceof ItemBow);
                this.Â.ÇªÓ();
                double posX = RenderManager.HorizonCode_Horizon_È - MathHelper.Â(this.Â.á.É / 180.0f * 3.141593f) * 0.16f;
                this.Â.ÇªÓ();
                double posY = RenderManager.Â + this.Â.á.Ðƒáƒ() - 0.1000000014901161;
                this.Â.ÇªÓ();
                double posZ = RenderManager.Ý - MathHelper.HorizonCode_Horizon_È(this.Â.á.É / 180.0f * 3.141593f) * 0.16f;
                double motionX = -MathHelper.HorizonCode_Horizon_È(this.Â.á.É / 180.0f * 3.141593f) * MathHelper.Â(this.Â.á.áƒ / 180.0f * 3.141593f) * (bow ? 1.0 : 0.4);
                double motionY = -MathHelper.HorizonCode_Horizon_È(this.Â.á.áƒ / 180.0f * 3.141593f) * (bow ? 1.0 : 0.4);
                double motionZ = MathHelper.Â(this.Â.á.É / 180.0f * 3.141593f) * MathHelper.Â(this.Â.á.áƒ / 180.0f * 3.141593f) * (bow ? 1.0 : 0.4);
                final int var6 = 72000 - this.Â.á.Ø­Ñ¢á€();
                float power = var6 / 20.0f;
                power = (power * power + power * 2.0f) / 3.0f;
                if (power >= 0.1) {
                    if (power > 1.0f) {
                        power = 1.0f;
                    }
                    final float distance = MathHelper.HorizonCode_Horizon_È(motionX * motionX + motionY * motionY + motionZ * motionZ);
                    motionX /= distance;
                    motionY /= distance;
                    motionZ /= distance;
                    motionX *= (bow ? (power * 2.0f) : 1.0f) * 1.5;
                    motionY *= (bow ? (power * 2.0f) : 1.0f) * 1.5;
                    motionZ *= (bow ? (power * 2.0f) : 1.0f) * 1.5;
                    OpenGlHelper.ÂµÈ(OpenGlHelper.µà);
                    GL11.glEnable(3553);
                    OpenGlHelper.ÂµÈ(OpenGlHelper.£à);
                    OpenGlHelper.HorizonCode_Horizon_È(OpenGlHelper.µà, 200.0f, 0.0f);
                    GL11.glDisable(2896);
                    GL11.glEnable(2848);
                    GL11.glDisable(2929);
                    GL11.glPushMatrix();
                    if (Horizon.Âµá€.equalsIgnoreCase("red")) {
                        GL11.glColor4f(0.7f, 0.3f, 0.3f, 1.0f);
                    }
                    else if (Horizon.Âµá€.equalsIgnoreCase("green")) {
                        GL11.glColor4f(0.3f, 0.7f, 0.3f, 1.0f);
                    }
                    else if (Horizon.Âµá€.equalsIgnoreCase("blue")) {
                        GL11.glColor4f(0.3f, 0.3f, 0.7f, 1.0f);
                    }
                    else if (Horizon.Âµá€.equalsIgnoreCase("rainbow")) {
                        GL11.glColor4f(LSD.HorizonCode_Horizon_È, LSD.Â, LSD.Ý, 1.0f);
                    }
                    GL11.glDisable(3553);
                    GL11.glDepthMask(false);
                    GL11.glBlendFunc(770, 771);
                    GL11.glEnable(3042);
                    GL11.glLineWidth(2.0f);
                    GL11.glBegin(3);
                    boolean hasLanded = false;
                    final Object hitEntity = null;
                    MovingObjectPosition landingPosition = null;
                    while (!hasLanded && posY > 0.0) {
                        final Vec3 kek = new Vec3(posX, posY, posZ);
                        final Vec3 future = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
                        final MovingObjectPosition possibleLandingStrip = this.Â.áŒŠÆ.HorizonCode_Horizon_È(kek, future, false, true, false);
                        if (possibleLandingStrip != null && possibleLandingStrip.HorizonCode_Horizon_È != MovingObjectPosition.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
                            landingPosition = possibleLandingStrip;
                            hasLanded = true;
                        }
                        posX += motionX;
                        posY += motionY;
                        posZ += motionZ;
                        final float motionAdjustment = 0.99f;
                        motionX *= motionAdjustment;
                        motionY *= motionAdjustment;
                        motionZ *= motionAdjustment;
                        motionY -= (bow ? 0.05 : 0.03);
                        final double n = posX;
                        this.Â.ÇªÓ();
                        final double n2 = n - RenderManager.HorizonCode_Horizon_È;
                        final double n3 = posY;
                        this.Â.ÇªÓ();
                        final double n4 = n3 - RenderManager.Â;
                        final double n5 = posZ;
                        this.Â.ÇªÓ();
                        GL11.glVertex3d(n2, n4, n5 - RenderManager.Ý);
                    }
                    GL11.glEnd();
                    GL11.glPushMatrix();
                    final double n6 = posX;
                    this.Â.ÇªÓ();
                    final double n7 = n6 - RenderManager.HorizonCode_Horizon_È;
                    final double n8 = posY;
                    this.Â.ÇªÓ();
                    final double n9 = n8 - RenderManager.Â;
                    final double n10 = posZ;
                    this.Â.ÇªÓ();
                    GL11.glTranslated(n7, n9, n10 - RenderManager.Ý);
                    if (landingPosition != null && landingPosition.HorizonCode_Horizon_È == MovingObjectPosition.HorizonCode_Horizon_È.Â) {
                        final int kek2 = landingPosition.Â.Â();
                        if (kek2 == 2) {
                            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
                        }
                        else if (kek2 == 3) {
                            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
                        }
                        else if (kek2 == 4) {
                            GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
                        }
                        else if (kek2 == 5) {
                            GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
                        }
                        GL11.glBegin(1);
                        GL11.glVertex3d(-0.4, 0.0, 0.4);
                        GL11.glVertex3d(0.4, 0.0, 0.4);
                        GL11.glVertex3d(0.4, 0.0, -0.4);
                        GL11.glVertex3d(0.4, 0.0, 0.4);
                        GL11.glVertex3d(-0.4, 0.0, -0.4);
                        GL11.glVertex3d(-0.4, 0.0, 0.4);
                        GL11.glVertex3d(-0.4, 0.0, -0.4);
                        GL11.glVertex3d(0.4, 0.0, -0.4);
                        GL11.glEnd();
                        GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
                    }
                    GL11.glPopMatrix();
                    GL11.glDisable(3042);
                    GL11.glDepthMask(true);
                    GL11.glEnable(3553);
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    GL11.glEnable(2929);
                    GL11.glDisable(2848);
                    GL11.glPopMatrix();
                }
            }
            else {
                this.HorizonCode_Horizon_È(false);
            }
        }
    }
}
