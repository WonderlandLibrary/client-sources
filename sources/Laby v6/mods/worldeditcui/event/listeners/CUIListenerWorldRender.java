package mods.worldeditcui.event.listeners;

import mods.worldeditcui.WorldEditCUI;
import mods.worldeditcui.util.Vector3;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;

public class CUIListenerWorldRender
{
    private WorldEditCUI controller;
    private Minecraft minecraft;

    public CUIListenerWorldRender(WorldEditCUI controller, Minecraft minecraft)
    {
        this.controller = controller;
        this.minecraft = minecraft;
    }

    public void onRender(double partialTicks)
    {
        try
        {
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.0F);
            GlStateManager.disableTexture2D();
            GlStateManager.enableDepth();
            GL11.glDepthMask(false);
            GL11.glPushMatrix();
            GlStateManager.disableFog();

            try
            {
                Vector3 vector3 = new Vector3(this.minecraft.getRenderViewEntity(), partialTicks);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);

                if (this.controller.getSelection() != null)
                {
                    this.controller.getSelection().render(vector3);
                }
            }
            catch (Exception var4)
            {
                ;
            }

            GL11.glDepthFunc(GL11.GL_LEQUAL);
            GL11.glPopMatrix();
            GL11.glDepthMask(true);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
        }
        catch (Exception var5)
        {
            ;
        }
    }
}
