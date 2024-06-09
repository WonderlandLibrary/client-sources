package exhibition.module.impl.hud;

import exhibition.event.Event;
import exhibition.event.EventListener;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventRenderGui;
import exhibition.event.impl.EventTick;
import exhibition.management.ColorManager;
import exhibition.management.friend.FriendManager;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Setting;
import exhibition.util.RenderingUtil;
import exhibition.util.StringConversions;
import exhibition.util.render.Colors;
import java.awt.Color;
import java.util.Iterator;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Mouse;

public class Radar extends Module {
   private static final String SCALE = "SCALE";
   private static final String X = "X";
   private static final String Y = "Y";
   private String SIZE = "SIZE";
   private boolean dragging;
   float hue;

   public Radar(ModuleData data) {
      super(data);
      this.settings.put("SCALE", new Setting("SCALE", 2.0D, "Scales the radar.", 0.1D, 0.1D, 5.0D));
      this.settings.put("X", new Setting("X", Integer.valueOf(1000), "X position for radar.", 5.0D, 1.0D, 1920.0D));
      this.settings.put("Y", new Setting("Y", Integer.valueOf(2), "Y position for radar.", 5.0D, 1.0D, 1080.0D));
      this.settings.put(this.SIZE, new Setting(this.SIZE, Integer.valueOf(125), "Size of the radar.", 5.0D, 50.0D, 500.0D));
   }

   public EventListener.Priority getPriority() {
      return EventListener.Priority.HIGH;
   }

   @RegisterEvent(
      events = {EventRenderGui.class, EventTick.class}
   )
   public void onEvent(Event event) {
      if (event instanceof EventRenderGui) {
         EventRenderGui er = (EventRenderGui)event;
         int size = ((Number)((Setting)this.settings.get(this.SIZE)).getValue()).intValue();
         float xOffset = ((Number)((Setting)this.settings.get("X")).getValue()).floatValue();
         float yOffset = ((Number)((Setting)this.settings.get("Y")).getValue()).floatValue();
         float playerOffsetX = (float)mc.thePlayer.posX;
         float playerOffSetZ = (float)mc.thePlayer.posZ;
         int var141 = er.getResolution().getScaledWidth();
         int var151 = er.getResolution().getScaledHeight();
         int mouseX = Mouse.getX() * var141 / mc.displayWidth;
         int mouseY = var151 - Mouse.getY() * var151 / mc.displayHeight - 1;
         if ((float)mouseX >= xOffset && (float)mouseX <= xOffset + (float)size && (float)mouseY >= yOffset - 3.0F && (float)mouseY <= yOffset + 10.0F && Mouse.getEventButton() == 0) {
            this.dragging = !this.dragging;
         }

         if (this.dragging && mc.currentScreen instanceof GuiChat) {
            Object newValue = StringConversions.castNumber(Double.toString((double)(mouseX - size / 2)), Integer.valueOf(5));
            ((Setting)this.settings.get("X")).setValue(newValue);
            Object newValueY = StringConversions.castNumber(Double.toString((double)(mouseY - 2)), Integer.valueOf(5));
            ((Setting)this.settings.get("Y")).setValue(newValueY);
         } else {
            this.dragging = false;
         }

         if (this.hue > 255.0F) {
            this.hue = 0.0F;
         }

         float h = this.hue;
         float h2 = this.hue + 85.0F;
         float h3 = this.hue + 170.0F;
         if (h > 255.0F) {
            h = 0.0F;
         }

         if (h2 > 255.0F) {
            h2 -= 255.0F;
         }

         if (h3 > 255.0F) {
            h3 -= 255.0F;
         }

         Color color33 = Color.getHSBColor(h / 255.0F, 0.9F, 1.0F);
         Color color332 = Color.getHSBColor(h2 / 255.0F, 0.9F, 1.0F);
         Color color333 = Color.getHSBColor(h3 / 255.0F, 0.9F, 1.0F);
         int color1 = color33.getRGB();
         int color2 = color332.getRGB();
         int color3 = color333.getRGB();
         this.hue = (float)((double)this.hue + 0.1D);
         RenderingUtil.rectangleBordered((double)xOffset, (double)yOffset, (double)(xOffset + (float)size), (double)(yOffset + (float)size), 0.5D, Colors.getColor(90), Colors.getColor(0));
         RenderingUtil.rectangleBordered((double)(xOffset + 1.0F), (double)(yOffset + 1.0F), (double)(xOffset + (float)size - 1.0F), (double)(yOffset + (float)size - 1.0F), 1.0D, Colors.getColor(90), Colors.getColor(61));
         RenderingUtil.rectangleBordered((double)xOffset + 2.5D, (double)yOffset + 2.5D, (double)(xOffset + (float)size) - 2.5D, (double)(yOffset + (float)size) - 2.5D, 0.5D, Colors.getColor(61), Colors.getColor(0));
         RenderingUtil.rectangleBordered((double)(xOffset + 3.0F), (double)(yOffset + 3.0F), (double)(xOffset + (float)size - 3.0F), (double)(yOffset + (float)size - 3.0F), 0.5D, Colors.getColor(27), Colors.getColor(61));
         RenderingUtil.drawGradientSideways((double)(xOffset + 3.0F), (double)(yOffset + 3.0F), (double)(xOffset + (float)(size / 2)), (double)yOffset + 3.6D, color1, color2);
         RenderingUtil.drawGradientSideways((double)(xOffset + (float)(size / 2)), (double)(yOffset + 3.0F), (double)(xOffset + (float)size - 3.0F), (double)yOffset + 3.6D, color2, color3);
         RenderingUtil.rectangle((double)xOffset + ((double)(size / 2) - 0.5D), (double)yOffset + 3.5D, (double)xOffset + (double)(size / 2) + 0.5D, (double)(yOffset + (float)size) - 3.5D, Colors.getColor(255, 80));
         RenderingUtil.rectangle((double)xOffset + 3.5D, (double)yOffset + ((double)(size / 2) - 0.5D), (double)(xOffset + (float)size) - 3.5D, (double)yOffset + (double)(size / 2) + 0.5D, Colors.getColor(255, 80));
         Iterator var21 = mc.theWorld.getLoadedEntityList().iterator();

         while(var21.hasNext()) {
            Object o = var21.next();
            if (o instanceof EntityPlayer) {
               EntityPlayer ent = (EntityPlayer)o;
               if (ent.isEntityAlive() && ent != mc.thePlayer && !ent.isInvisible() && !ent.isInvisibleToPlayer(mc.thePlayer)) {
                  float pTicks = mc.timer.renderPartialTicks;
                  float posX = (float)((ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * (double)pTicks - (double)playerOffsetX) * ((Number)((Setting)this.settings.get("SCALE")).getValue()).doubleValue());
                  float posZ = (float)((ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * (double)pTicks - (double)playerOffSetZ) * ((Number)((Setting)this.settings.get("SCALE")).getValue()).doubleValue());
                  int color;
                  if (FriendManager.isFriend(ent.getName())) {
                     color = mc.thePlayer.canEntityBeSeen(ent) ? Colors.getColor(0, 195, 255) : Colors.getColor(0, 195, 255);
                  } else {
                     color = mc.thePlayer.canEntityBeSeen(ent) ? ColorManager.getEnemyVisible().getColorInt() : ColorManager.getEnemyInvisible().getColorInt();
                  }

                  float cos = (float)Math.cos((double)mc.thePlayer.rotationYaw * 0.017453292519943295D);
                  float sin = (float)Math.sin((double)mc.thePlayer.rotationYaw * 0.017453292519943295D);
                  float rotY = -(posZ * cos - posX * sin);
                  float rotX = -(posX * cos + posZ * sin);
                  if (rotY > (float)(size / 2 - 5)) {
                     rotY = (float)(size / 2) - 5.0F;
                  } else if (rotY < (float)(-(size / 2 - 5))) {
                     rotY = (float)(-(size / 2 - 5));
                  }

                  if (rotX > (float)(size / 2) - 5.0F) {
                     rotX = (float)(size / 2 - 5);
                  } else if (rotX < (float)(-(size / 2 - 5))) {
                     rotX = -((float)(size / 2) - 5.0F);
                  }

                  RenderingUtil.rectangleBordered((double)(xOffset + (float)(size / 2) + rotX) - 1.5D, (double)(yOffset + (float)(size / 2) + rotY) - 1.5D, (double)(xOffset + (float)(size / 2) + rotX) + 1.5D, (double)(yOffset + (float)(size / 2) + rotY) + 1.5D, 0.5D, color, Colors.getColor(0));
               }
            }
         }
      }

   }
}
