package exhibition.module.impl.render;

import exhibition.event.Event;
import exhibition.event.EventListener;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventRender3D;
import exhibition.event.impl.EventRenderEntity;
import exhibition.management.ColorManager;
import exhibition.management.ColorObject;
import exhibition.management.friend.FriendManager;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Options;
import exhibition.module.data.Setting;
import exhibition.util.RenderingUtil;
import exhibition.util.TeamUtils;
import exhibition.util.render.Stencil;
import java.util.Iterator;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

public class Outline extends Module {
   private Options colors = new Options("Color", "Friend", new String[]{"Friend", "TeamColor", "Team", "Custom"});
   private boolean draw = false;
   private int draws = 0;

   public Outline(ModuleData data) {
      super(data);
      this.settings.put("COLOR", new Setting("COLOR", this.colors, "Sets the color for chams."));
   }

   public EventListener.Priority getPriority() {
      return EventListener.Priority.MEDIUM;
   }

   @RegisterEvent(
      events = {EventRender3D.class, EventRenderEntity.class}
   )
   public void onEvent(Event event) {
      if (event instanceof EventRender3D) {
         if (!this.draw || this.draws < 10) {
            return;
         }

         Stencil.checkSetupFBO();
         int list = GL11.glGenLists(1);
         Stencil.getInstance().startLayer();
         GL11.glPushMatrix();
         mc.entityRenderer.setupCameraTransform(mc.timer.renderPartialTicks, 0);
         GL11.glDisable(2929);
         Stencil.getInstance().setBuffer(true);
         GL11.glNewList(list, 4864);
         GlStateManager.enableLighting();
         Iterator var3 = mc.theWorld.loadedEntityList.iterator();

         while(true) {
            Entity entity;
            do {
               do {
                  if (!var3.hasNext()) {
                     GL11.glEndList();
                     GL11.glPolygonMode(1032, 6913);
                     GL11.glCallList(list);
                     GL11.glPolygonMode(1032, 6912);
                     GL11.glCallList(list);
                     Stencil.getInstance().setBuffer(false);
                     GL11.glPolygonMode(1032, 6914);
                     GL11.glCallList(list);
                     Stencil.getInstance().cropInside();
                     GL11.glPolygonMode(1032, 6913);
                     GL11.glCallList(list);
                     GL11.glPolygonMode(1032, 6912);
                     GL11.glCallList(list);
                     GL11.glPolygonMode(1032, 6914);
                     Stencil.getInstance().stopLayer();
                     GL11.glEnable(2929);
                     GL11.glDeleteLists(list, 1);
                     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                     GL11.glPopMatrix();
                     return;
                  }

                  Object obj = var3.next();
                  entity = (Entity)obj;
               } while(entity == mc.thePlayer);
            } while(!(entity instanceof EntityPlayer));

            if (entity instanceof EntityPlayer) {
               String var6 = this.colors.getSelected();
               byte var7 = -1;
               switch(var6.hashCode()) {
               case 2602621:
                  if (var6.equals("Team")) {
                     var7 = 2;
                  }
                  break;
               case 1802243814:
                  if (var6.equals("TeamColor")) {
                     var7 = 1;
                  }
                  break;
               case 2029746065:
                  if (var6.equals("Custom")) {
                     var7 = 3;
                  }
                  break;
               case 2112550590:
                  if (var6.equals("Friend")) {
                     var7 = 0;
                  }
               }

               ColorObject c;
               label96:
               switch(var7) {
               case 0:
                  if (FriendManager.isFriend(entity.getName())) {
                     RenderingUtil.glColor(ColorManager.getFriendlyVisible().getColorInt());
                  } else {
                     RenderingUtil.glColor(ColorManager.getEnemyVisible().getColorInt());
                  }
                  break;
               case 1:
                  String text = entity.getDisplayName().getFormattedText();
                  int i = 0;

                  while(true) {
                     if (i >= text.length()) {
                        break label96;
                     }

                     if (text.charAt(i) == '§' && i + 1 < text.length()) {
                        char oneMore = Character.toLowerCase(text.charAt(i + 1));
                        int colorCode = "0123456789abcdefklmnorg".indexOf(oneMore);
                        if (colorCode < 16) {
                           try {
                              int newColor = mc.fontRendererObj.colorCode[colorCode];
                              RenderingUtil.glColor(255.0F, (int)((double)(newColor >> 16) / 1.5D), (int)((double)(newColor >> 8 & 255) / 1.5D), (int)((double)(newColor & 255) / 1.5D));
                           } catch (ArrayIndexOutOfBoundsException var13) {
                              ;
                           }
                        }
                     }

                     ++i;
                  }
               case 2:
                  if (TeamUtils.isTeam(mc.thePlayer, (EntityPlayer)entity)) {
                     c = ColorManager.fTeam;
                     RenderingUtil.glColor(1.0F, c.getRed(), c.getGreen(), c.getBlue());
                  } else {
                     c = ColorManager.eTeam;
                     RenderingUtil.glColor(1.0F, c.getRed(), c.getGreen(), c.getBlue());
                  }
                  break;
               case 3:
                  c = ColorManager.glowColor;
                  RenderingUtil.glColor(c.getColorInt());
               }
            } else {
               RenderingUtil.glColor(-1);
            }

            GL11.glPushMatrix();
            GL11.glLineWidth(3.0F);
            GL11.glEnable(3042);
            GL11.glEnable(2848);
            GL11.glDisable(3008);
            GL11.glDisable(3553);
            double posX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)mc.timer.renderPartialTicks;
            double posY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)mc.timer.renderPartialTicks;
            double posZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)mc.timer.renderPartialTicks;
            GL11.glTranslated(posX - RenderManager.renderPosX, posY - RenderManager.renderPosY, posZ - RenderManager.renderPosZ);
            Render entityRender = mc.getRenderManager().getEntityRenderObject(entity);
            if (entityRender != null) {
               GlStateManager.disableLighting();
               if (entity instanceof EntityPlayer) {
                  RendererLivingEntity.renderLayers = false;
               }

               RendererLivingEntity.ignoreChams = true;
               entityRender.doRender(entity, 0.0D, 0.0D, 0.0D, mc.timer.renderPartialTicks, mc.timer.renderPartialTicks);
               if (entity instanceof EntityPlayer) {
                  RendererLivingEntity.renderLayers = true;
               }

               RendererLivingEntity.ignoreChams = false;
               GlStateManager.enableLighting();
            }

            GL11.glDisable(3042);
            GL11.glEnable(3553);
            GL11.glEnable(3008);
            GL11.glPopMatrix();
         }
      } else if (event instanceof EventRenderEntity) {
         EventRenderEntity err = (EventRenderEntity)event;
         if (err.getEntity() != mc.thePlayer && err.getEntity() instanceof EntityPlayer) {
            this.draw = true;
            ++this.draws;
         }
      }

   }
}
