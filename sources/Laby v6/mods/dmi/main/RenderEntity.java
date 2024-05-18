package mods.dmi.main;

import de.labystudio.labymod.LabyMod;
import de.labystudio.utils.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import org.lwjgl.opengl.GL11;

public class RenderEntity
{
    public static void renderNameTag(EntityLivingBase entity, double x, double y, double z)
    {
        if (Settings.settings.enabled && DamageIndicator.allowed(LabyMod.getInstance().ip))
        {
            int i = Settings.settings.scale;

            if (entity instanceof AbstractClientPlayer)
            {
                AbstractClientPlayer abstractclientplayer = (AbstractClientPlayer)entity;

                if (abstractclientplayer.isInvisible())
                {
                    return;
                }
            }

            if (!(entity instanceof EntityArmorStand) && !entity.isSneaking())
            {
                RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
                FontRenderer fontrenderer = rendermanager.getFontRenderer();
                GlStateManager.pushMatrix();
                GlStateManager.translate((float)x, (float)y + entity.height + 0.5F - (entity.isChild() ? entity.height / 2.0F : 0.0F), (float)z);
                GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(-rendermanager.playerViewY, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(rendermanager.playerViewX, 1.0F, 0.0F, 0.0F);
                GlStateManager.scale(-0.02666667F, -0.02666667F, 0.02666667F);
                GlStateManager.disableLighting();
                GlStateManager.depthMask(false);
                GlStateManager.disableDepth();
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                int j = -10;

                if ((double)entity.height < 100.0D && entity instanceof AbstractClientPlayer)
                {
                    Scoreboard scoreboard = ((AbstractClientPlayer)entity).getWorldScoreboard();
                    ScoreObjective scoreobjective = scoreboard.getObjectiveInDisplaySlot(2);

                    if (scoreobjective != null)
                    {
                        j = (int)((double)j - (25.0D - LabyMod.getInstance().draw.getScale(i) * 10.0D));
                    }
                }

                GlStateManager.disableTexture2D();
                Tessellator tessellator = Tessellator.getInstance();
                WorldRenderer worldrenderer = tessellator.getWorldRenderer();
                worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
                String s = "";
                int k = 0;
                double d0 = (double)entity.getHealth();

                if (!Settings.settings.DMILayout)
                {
                    s = Math.ceil(d0) / 2.0D + Color.cl("c") + " \u2764";
                    k = 16;
                }
                else
                {
                    String s1 = Color.cl("4");
                    String s2 = Color.cl("7");
                    String s3 = Color.cl("c");
                    int l = (int)(entity.getMaxHealth() / 2.0F);

                    if (d0 == 0.0D)
                    {
                        for (int i1 = 0; i1 < l; ++i1)
                        {
                            s = s + s2 + "\u2764";
                        }
                    }
                    else
                    {
                        for (int k1 = 1; k1 < (int)Math.ceil(d0 / 2.0D); ++k1)
                        {
                            s = s + s1 + "\u2764";
                        }

                        if (d0 % 2.0D != 0.0D)
                        {
                            s = s + s3 + "\u2764";
                        }
                        else
                        {
                            s = s + s1 + "\u2764";
                        }

                        for (int l1 = (int)Math.ceil(d0 / 2.0D); l1 < l; ++l1)
                        {
                            s = s + s2 + "\u2764";
                        }

                        k = 40;
                    }
                }

                double d1 = LabyMod.getInstance().draw.getScale(i);
                GL11.glScaled(d1, d1, d1);
                int j1 = LabyMod.getInstance().draw.getStringWidth(s) / 2;
                worldrenderer.pos((double)(-j1 - 1), (double)j, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                worldrenderer.pos((double)(-j1 - 1), (double)(j + 8), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                worldrenderer.pos((double)(j1 + 1), (double)(j + 8), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                worldrenderer.pos((double)(j1 + 1), (double)j, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                tessellator.draw();
                GlStateManager.enableTexture2D();
                GlStateManager.depthMask(true);
                GlStateManager.enableDepth();
                fontrenderer.drawString(s, -LabyMod.getInstance().draw.getStringWidth(s) / 2 + 2, j, -1);
                GlStateManager.enableLighting();
                GlStateManager.disableBlend();
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.popMatrix();
            }
        }
    }
}
