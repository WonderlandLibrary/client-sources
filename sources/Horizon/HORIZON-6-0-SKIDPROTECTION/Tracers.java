package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import org.lwjgl.opengl.GL11;

@ModInfo(Ø­áŒŠá = Category.DISPLAY, Ý = -1, Â = "FOLLOW THE Hazes.", HorizonCode_Horizon_È = "Tracers")
public class Tracers extends Mod
{
    private boolean Ý;
    private boolean Ø­áŒŠá;
    private boolean Âµá€;
    private boolean Ó;
    private boolean à;
    
    public Tracers() {
        this.Ý = true;
        this.Ø­áŒŠá = true;
        this.Âµá€ = false;
        this.Ó = false;
        this.à = true;
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventRender3D e) {
        this.Â(e);
    }
    
    public void Â(final EventRender3D e) {
        for (final EntityPlayer player : Minecraft.áŒŠà().áŒŠÆ.Ó) {
            if (player.Œ() && !(player instanceof EntityPlayerSP)) {
                final double x = player.ŒÏ - RenderManager.HorizonCode_Horizon_È;
                final double y = player.Çªà¢ - RenderManager.Â;
                final double z = player.Ê - RenderManager.Ý;
                if (player.Ï­Ä() <= 0.5) {
                    return;
                }
                if (FriendManager.HorizonCode_Horizon_È.containsKey(player.v_())) {
                    int color = ColorUtil.HorizonCode_Horizon_È(200000000L, 1.0f).getRGB();
                    color = ColorUtil.HorizonCode_Horizon_È(color, 0.8);
                    OGLManager.Ø­áŒŠá(color);
                    GL11.glPushMatrix();
                    GL11.glLoadIdentity();
                    final boolean bobbing1 = this.Â.ŠÄ.Ø­áŒŠá;
                    this.Â.ŠÄ.Ø­áŒŠá = false;
                    this.Â.µÕ.Â(e.Ý());
                    GL11.glBegin(1);
                    GL11.glVertex3d(0.0, (double)this.Â.á.Ðƒáƒ(), 0.0);
                    GL11.glVertex3d(x, y, z);
                    GL11.glEnd();
                    GL11.glBegin(1);
                    GL11.glVertex3d(x, y, z);
                    GL11.glVertex3d(x, y + player.£ÂµÄ, z);
                    GL11.glEnd();
                    this.Â.ŠÄ.Ø­áŒŠá = bobbing1;
                    GL11.glPopMatrix();
                }
                else {
                    final float bobbing2 = this.Â.á.Ø­áŒŠá(player);
                    if (bobbing2 <= 32.0f) {
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
                        GL11.glPushMatrix();
                        GL11.glLoadIdentity();
                        final boolean bobbing1 = this.Â.ŠÄ.Ø­áŒŠá;
                        this.Â.ŠÄ.Ø­áŒŠá = false;
                        this.Â.µÕ.Â(e.Ý());
                        GL11.glBegin(1);
                        GL11.glVertex3d(0.0, (double)this.Â.á.Ðƒáƒ(), 0.0);
                        GL11.glVertex3d(x, y, z);
                        GL11.glEnd();
                        GL11.glBegin(1);
                        GL11.glVertex3d(x, y, z);
                        GL11.glVertex3d(x, y + player.£ÂµÄ, z);
                        GL11.glEnd();
                        this.Â.ŠÄ.Ø­áŒŠá = bobbing1;
                        GL11.glPopMatrix();
                    }
                    else if (bobbing2 <= 16.0f) {
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.75f);
                        GL11.glPushMatrix();
                        GL11.glLoadIdentity();
                        final boolean bobbing1 = this.Â.ŠÄ.Ø­áŒŠá;
                        this.Â.ŠÄ.Ø­áŒŠá = false;
                        this.Â.µÕ.Â(e.Ý());
                        GL11.glBegin(1);
                        GL11.glVertex3d(0.0, (double)this.Â.á.Ðƒáƒ(), 0.0);
                        GL11.glVertex3d(x, y, z);
                        GL11.glEnd();
                        GL11.glBegin(1);
                        GL11.glVertex3d(x, y, z);
                        GL11.glVertex3d(x, y + player.£ÂµÄ, z);
                        GL11.glEnd();
                        this.Â.ŠÄ.Ø­áŒŠá = bobbing1;
                        GL11.glPopMatrix();
                    }
                    else {
                        if (bobbing2 > 7.0f) {
                            continue;
                        }
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                        GL11.glPushMatrix();
                        GL11.glLoadIdentity();
                        final boolean bobbing1 = this.Â.ŠÄ.Ø­áŒŠá;
                        this.Â.ŠÄ.Ø­áŒŠá = false;
                        this.Â.µÕ.Â(e.Ý());
                        GL11.glBegin(1);
                        GL11.glVertex3d(0.0, (double)this.Â.á.Ðƒáƒ(), 0.0);
                        GL11.glVertex3d(x, y, z);
                        GL11.glEnd();
                        GL11.glBegin(1);
                        GL11.glVertex3d(x, y, z);
                        GL11.glVertex3d(x, y + player.£ÂµÄ, z);
                        GL11.glEnd();
                        this.Â.ŠÄ.Ø­áŒŠá = bobbing1;
                        GL11.glPopMatrix();
                    }
                }
            }
        }
    }
}
