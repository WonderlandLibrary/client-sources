package exhibition.module.impl.render;

import exhibition.event.Event;
import exhibition.event.EventListener;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventRender3D;
import exhibition.event.impl.EventRenderEntity;
import exhibition.event.impl.EventTick;
import exhibition.management.ColorManager;
import exhibition.management.ColorObject;
import exhibition.management.animate.Opacity;
import exhibition.management.friend.FriendManager;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Options;
import exhibition.module.data.Setting;
import exhibition.util.RenderingUtil;
import exhibition.util.TeamUtils;
import java.awt.Color;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

public class Chams extends Module {
   private Opacity hue = new Opacity(0);
   private float hueTarget;

   public Chams(ModuleData data) {
      super(data);
      this.settings.put("COLORED", new Setting("COLORED", true, "Colors those niggers."));
      this.settings.put("FLAT", new Setting("FLAT", true, "Flat chams for you niggers."));
      this.settings.put("COLOR", new Setting("COLOR", new Options("Color", "Friend", new String[]{"Friend", "TeamColor", "Team", "Custom", "Rainbow"}), "Sets the color for chams."));
   }

   public EventListener.Priority getPriority() {
      return EventListener.Priority.HIGH;
   }

   @RegisterEvent(
      events = {EventRenderEntity.class, EventTick.class, EventRender3D.class}
   )
   public void onEvent(Event event) {
      if (event instanceof EventTick) {
         ;
      }

      if (event instanceof EventRender3D) {
         this.hue.interp(255.0F, 1);
         if (this.hue.getOpacity() >= 255.0F) {
            this.hue.setOpacity(0.0F);
         }
      }

      if (event instanceof EventRenderEntity) {
         EventRenderEntity er = (EventRenderEntity)event;
         boolean colored = ((Boolean)((Setting)this.settings.get("COLORED")).getValue()).booleanValue();
         if (er.getEntity() instanceof EntityPlayer && er.isPre()) {
            if (colored) {
               event.setCancelled(true);

               try {
                  Render var11 = mc.getRenderManager().getEntityRenderObject(er.getEntity());
                  if (var11 != null && mc.getRenderManager().renderEngine != null && var11 instanceof RendererLivingEntity) {
                     GL11.glPushMatrix();
                     GL11.glDisable(2929);
                     GL11.glDisable(3553);
                     GL11.glEnable(3042);
                     String selected = ((Options)((Setting)this.settings.get("COLOR")).getValue()).getSelected();
                     if (((Boolean)((Setting)this.settings.get("FLAT")).getValue()).booleanValue()) {
                        GlStateManager.disableLighting();
                     }

                     byte var7 = -1;
                     switch(selected.hashCode()) {
                     case -1656737386:
                        if (selected.equals("Rainbow")) {
                           var7 = 4;
                        }
                        break;
                     case 2602621:
                        if (selected.equals("Team")) {
                           var7 = 2;
                        }
                        break;
                     case 1802243814:
                        if (selected.equals("TeamColor")) {
                           var7 = 1;
                        }
                        break;
                     case 2029746065:
                        if (selected.equals("Custom")) {
                           var7 = 3;
                        }
                        break;
                     case 2112550590:
                        if (selected.equals("Friend")) {
                           var7 = 0;
                        }
                     }

                     String text;
                     ColorObject c;
                     ColorObject c1;
                     int newColor;
                     int i;
                     char oneMore;
                     int colorCode;
                     label157:
                     switch(var7) {
                     case 0:
                        if (FriendManager.isFriend(er.getEntity().getName())) {
                           RenderingUtil.glColor(ColorManager.getFriendlyInvisible().getColorInt());
                        } else {
                           RenderingUtil.glColor(ColorManager.getEnemyInvisible().getColorInt());
                        }
                        break;
                     case 1:
                        text = er.getEntity().getDisplayName().getFormattedText();
                        i = 0;

                        while(true) {
                           if (i >= text.length()) {
                              break label157;
                           }

                           if (text.charAt(i) == '§' && i + 1 < text.length()) {
                              oneMore = Character.toLowerCase(text.charAt(i + 1));
                              colorCode = "0123456789abcdefklmnorg".indexOf(oneMore);
                              if (colorCode < 16) {
                                 try {
                                    newColor = mc.fontRendererObj.colorCode[colorCode];
                                    RenderingUtil.glColor(255.0F, (int)((double)(newColor >> 16) / 1.5D), (int)((double)(newColor >> 8 & 255) / 1.5D), (int)((double)(newColor & 255) / 1.5D));
                                 } catch (ArrayIndexOutOfBoundsException var14) {
                                    ;
                                 }
                              }
                           }

                           ++i;
                        }
                     case 2:
                        if (er.getEntity() instanceof EntityPlayer) {
                           if (TeamUtils.isTeam(mc.thePlayer, (EntityPlayer)er.getEntity())) {
                              c = ColorManager.fTeam;
                              RenderingUtil.glColor(1.0F, (int)((double)c.getRed() / 1.5D), (int)((double)c.getGreen() / 1.5D), (int)((double)c.getBlue() / 1.5D));
                           } else {
                              c = ColorManager.eTeam;
                              RenderingUtil.glColor(1.0F, (int)((double)c.getRed() / 1.5D), (int)((double)c.getGreen() / 1.5D), (int)((double)c.getBlue() / 1.5D));
                           }
                        }
                        break;
                     case 3:
                        c = ColorManager.chamsInvis;
                        RenderingUtil.glColor(c.getColorInt());
                        break;
                     case 4:
                        c1 = ColorManager.chamsInvis;
                        Color color = Color.getHSBColor(this.hue.getOpacity() / 255.0F, 0.8F, 1.0F);
                        RenderingUtil.glColor((float)c1.getAlpha() / 255.0F, color.getRed(), color.getGreen(), color.getBlue());
                     }

                     ((RendererLivingEntity)var11).renderModel(er.getEntity(), er.getLimbSwing(), er.getLimbSwingAmount(), er.getAgeInTicks(), er.getRotationYawHead(), er.getRotationPitch(), er.getOffset());
                     GL11.glEnable(2929);
                     var7 = -1;
                     switch(selected.hashCode()) {
                     case -1656737386:
                        if (selected.equals("Rainbow")) {
                           var7 = 4;
                        }
                        break;
                     case 2602621:
                        if (selected.equals("Team")) {
                           var7 = 2;
                        }
                        break;
                     case 1802243814:
                        if (selected.equals("TeamColor")) {
                           var7 = 1;
                        }
                        break;
                     case 2029746065:
                        if (selected.equals("Custom")) {
                           var7 = 3;
                        }
                        break;
                     case 2112550590:
                        if (selected.equals("Friend")) {
                           var7 = 0;
                        }
                     }

                     label147:
                     switch(var7) {
                     case 0:
                        if (FriendManager.isFriend(er.getEntity().getName())) {
                           RenderingUtil.glColor(ColorManager.getFriendlyVisible().getColorInt());
                        } else {
                           RenderingUtil.glColor(ColorManager.getEnemyVisible().getColorInt());
                        }
                        break;
                     case 1:
                        text = er.getEntity().getDisplayName().getFormattedText();
                        i = 0;

                        while(true) {
                           if (i >= text.length()) {
                              break label147;
                           }

                           if (text.charAt(i) == '§' && i + 1 < text.length()) {
                              oneMore = Character.toLowerCase(text.charAt(i + 1));
                              colorCode = "0123456789abcdefklmnorg".indexOf(oneMore);
                              if (colorCode < 16) {
                                 try {
                                    newColor = mc.fontRendererObj.colorCode[colorCode];
                                    RenderingUtil.glColor(1.0F, newColor >> 16, newColor >> 8 & 255, newColor & 255);
                                 } catch (ArrayIndexOutOfBoundsException var13) {
                                    ;
                                 }
                              }
                           }

                           ++i;
                        }
                     case 2:
                        if (er.getEntity() instanceof EntityPlayer) {
                           if (TeamUtils.isTeam(mc.thePlayer, (EntityPlayer)er.getEntity())) {
                              c = ColorManager.fTeam;
                              RenderingUtil.glColor(1.0F, c.getRed(), c.getGreen(), c.getBlue());
                           } else {
                              c = ColorManager.eTeam;
                              RenderingUtil.glColor(1.0F, c.getRed(), c.getGreen(), c.getBlue());
                           }
                        }
                        break;
                     case 3:
                        c = ColorManager.chamsVis;
                        RenderingUtil.glColor(c.getColorInt());
                        break;
                     case 4:
                        c1 = ColorManager.chamsVis;
                        float hue = 255.0F - this.hue.getOpacity();
                        if (hue > 255.0F) {
                           hue = 0.0F;
                        }

                        if (hue < 0.0F) {
                           hue = 255.0F;
                        }

                        Color color = Color.getHSBColor(hue / 255.0F, 0.8F, 1.0F);
                        RenderingUtil.glColor((float)c1.getAlpha() / 255.0F, color.getRed(), color.getGreen(), color.getBlue());
                     }

                     ((RendererLivingEntity)var11).renderModel(er.getEntity(), er.getLimbSwing(), er.getLimbSwingAmount(), er.getAgeInTicks(), er.getRotationYawHead(), er.getRotationPitch(), er.getOffset());
                     GL11.glEnable(3553);
                     GL11.glDisable(3042);
                     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                     if (((Boolean)((Setting)this.settings.get("FLAT")).getValue()).booleanValue()) {
                        GlStateManager.enableLighting();
                     }

                     GL11.glPopMatrix();
                     ((RendererLivingEntity)var11).func_177093_a(er.getEntity(), er.getLimbSwing(), er.getLimbSwingAmount(), mc.timer.renderPartialTicks, er.getAgeInTicks(), er.getRotationYawHead(), er.getRotationPitch(), er.getOffset());
                     GL11.glPopMatrix();
                  }
               } catch (Exception var15) {
                  ;
               }
            } else {
               GL11.glEnable(32823);
               GL11.glPolygonOffset(1.0F, -1100000.0F);
            }
         } else if (!colored && er.getEntity() instanceof EntityPlayer && er.isPost()) {
            GL11.glDisable(32823);
            GL11.glPolygonOffset(1.0F, 1100000.0F);
         }
      }

   }
}
