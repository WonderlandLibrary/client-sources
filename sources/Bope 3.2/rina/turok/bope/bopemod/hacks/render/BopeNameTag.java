package rina.turok.bope.bopemod.hacks.render;

import java.util.Comparator;
import java.util.Iterator;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.BopeModule;
import rina.turok.bope.bopemod.events.BopeEventRender;
import rina.turok.bope.bopemod.guiscreen.render.BopeDraw;
import rina.turok.bope.bopemod.guiscreen.settings.BopeSetting;
import rina.turok.bope.bopemod.hacks.BopeCategory;
import rina.turok.bope.bopemod.util.BopeUtilEntity;

public class BopeNameTag extends BopeModule {
   BopeSetting name_ = this.create("Name", "NameTagName", true);
   BopeSetting life_ = this.create("Health", "NameTagHealth", true);
   BopeSetting ping_ = this.create("Ping", "NameTagPing", false);
   BopeSetting armor = this.create("Armor", "NameTagArmor", true);
   BopeSetting main_ = this.create("Main Hand", "NameTagMainHand", true);
   BopeSetting off_h = this.create("Off Hand", "NameTagOffHand", true);
   BopeSetting smot = this.create("Smooth", "NameTagSmooth", false);
   BopeSetting range = this.create("Range", "NameTagRange", 200, 0, 200);
   BopeSetting size = this.create("Size", "NameTagSize", 4.0D, 1.0D, 4.0D);
   String totems_left_string = "";
   float partial_ticks = 0.0F;
   float uhadajsndiupa = 0.1F;
   BopeDraw font = new BopeDraw(1.0F);
   int CLEAR = 256;
   int MASK = 2929;

   public BopeNameTag() {
      super(BopeCategory.BOPE_RENDER);
      this.name = "Name Tag";
      this.tag = "NameTag";
      this.description = "For see the items using of others players.";
      this.release("B.O.P.E - module - B.O.P.E");
   }

   public void render(BopeEventRender event) {
      if (this.mc.player != null && this.mc.world != null) {
         this.partial_ticks = event.get_partial_ticks();
         GlStateManager.enableTexture2D();
         GlStateManager.disableLighting();
         GlStateManager.disableDepth();
         this.mc.world.loadedEntityList.stream().filter((entity) -> {
            return entity instanceof EntityLivingBase;
         }).filter((entity) -> {
            return entity != this.mc.player;
         }).map((entity) -> {
            return (EntityLivingBase)entity;
         }).filter((entity) -> {
            return !entity.isDead;
         }).filter((entity) -> {
            return entity instanceof EntityPlayer;
         }).filter((entity) -> {
            return this.mc.player.getDistance(entity) < (float)this.range.get_value(1);
         }).sorted(Comparator.comparing((entity) -> {
            return -this.mc.player.getDistance(entity);
         })).forEach(this::draw);
      }

      GlStateManager.disableTexture2D();
      RenderHelper.disableStandardItemLighting();
      GlStateManager.enableLighting();
      GlStateManager.enableDepth();
   }

   public void draw(Entity entity) {
      if (this.mc.getRenderManager().options != null) {
         boolean smooth = this.smot.get_value(true);
         boolean person_view = this.mc.getRenderManager().options.thirdPersonView == 2;
         float viewer_pitch = this.mc.getRenderManager().playerViewX;
         float viewer_yaw = this.mc.getRenderManager().playerViewY;
         String spac = " ";
         String name = this.name_.get_value(true) ? entity.getName() : "";
         String life = this.life_.get_value(true) ? (this.name_.get_value(true) ? spac : "") + Bope.re + Integer.toString(Math.round(((EntityLivingBase)entity).getHealth() + (entity instanceof EntityPlayer ? ((EntityPlayer)entity).getAbsorptionAmount() : 0.0F))) + Bope.r : "";
         String ping = this.ping_.get_value(true) ? Bope.bl + this.get_ping(entity) + Bope.r + spac : "";
         String tag = ping + name + life;
         GlStateManager.pushMatrix();
         Vec3d pos = BopeUtilEntity.get_interpolated_render_pos(entity, this.partial_ticks);
         double x = pos.x;
         double y = pos.y + (double)(entity.height + this.uhadajsndiupa - (entity.isSneaking() ? 0.25F : 0.0F));
         double z = pos.z;
         GlStateManager.translate(x, y, z);
         GlStateManager.rotate(-viewer_yaw, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate((float)(person_view ? -1 : 1) * viewer_pitch, 1.0F, 0.0F, 0.0F);
         float distance = this.mc.player.getDistance(entity);
         float scaling = distance >= 4.0F ? distance / 8.0F * (float)Math.pow(1.258925437927246D, (double)this.size.get_value(1)) : (float)Math.pow(1.258925437927246D, 0.5D);
         GlStateManager.scale(scaling, scaling, scaling);
         GlStateManager.scale(-0.025F, -0.025F, 0.025F);
         GlStateManager.disableLighting();
         GlStateManager.depthMask(false);
         GL11.glDisable(this.MASK);
         int colapse_x = this.font.get_string_width(tag, smooth) / 2;
         this.draw_background(colapse_x);
         GlStateManager.enableTexture2D();
         int r = Bope.client_r;
         int g = Bope.client_g;
         int b = Bope.client_b;
         BopeDraw var10000;
         if (Bope.get_friend_manager().is_friend(entity.getName())) {
            var10000 = this.font;
            BopeDraw.draw_string(tag, -colapse_x, 10, r, g, b, true, smooth);
         } else {
            var10000 = this.font;
            BopeDraw.draw_string(tag, -colapse_x, 10, 190, 190, 190, true, smooth);
         }

         if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entity;
            int off_set_x = -10;
            int off_set_y = -5;
            boolean separator = true;
            Iterator var28 = player.inventory.armorInventory.iterator();

            ItemStack armor_slot_item;
            while(var28.hasNext()) {
               armor_slot_item = (ItemStack)var28.next();
               if (armor_slot_item != null && this.armor.get_value(true)) {
                  off_set_x -= 8;
               }
            }

            ItemStack main_hand;
            if (player.getHeldItemOffhand() != null && this.off_h.get_value(true)) {
               off_set_x -= 8;
               main_hand = player.getHeldItemOffhand();
               this.render_item(main_hand, off_set_x, -off_set_y);
               off_set_x += 16;
            }

            for(int i = 0; i < 4; ++i) {
               armor_slot_item = (ItemStack)player.inventory.armorInventory.get(i);
               if (armor_slot_item != null && this.armor.get_value(true)) {
                  this.render_item(armor_slot_item, off_set_x, -off_set_y);
                  off_set_x += 16;
               }
            }

            if (player.getHeldItemMainhand() != null && this.main_.get_value(true)) {
               off_set_x += 0;
               main_hand = player.getHeldItemMainhand();
               this.render_item(main_hand, off_set_x, -off_set_y);
               off_set_x += 8;
            }
         }

         GlStateManager.glNormal3f(0.0F, 0.0F, 0.0F);
         GL11.glTranslatef(0.0F, 20.0F, 0.0F);
         GL11.glEnable(3553);
         GL11.glEnable(2929);
         GL11.glDepthMask(true);
         GL11.glDisable(3042);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.scale(-40.0F, -40.0F, 40.0F);
         GlStateManager.enableDepth();
         GlStateManager.popMatrix();
      }

   }

   public void render_item(ItemStack item, int x, int y) {
      GL11.glPushMatrix();
      GL11.glDepthMask(true);
      GlStateManager.clear(this.CLEAR);
      GlStateManager.disableDepth();
      GlStateManager.enableDepth();
      RenderHelper.enableStandardItemLighting();
      this.mc.getRenderItem().zLevel = -200.0F;
      GlStateManager.scale(1.0F, 1.0F, 0.01F);
      this.mc.getRenderItem().renderItemAndEffectIntoGUI(item, x, y / 2 - 12);
      this.mc.getRenderItem().renderItemOverlays(this.mc.fontRenderer, item, x, y / 2 - 12 + 2);
      this.mc.getRenderItem().zLevel = 0.0F;
      GlStateManager.scale(1.0F, 1.0F, 1.0F);
      RenderHelper.disableStandardItemLighting();
      GlStateManager.enableAlpha();
      GlStateManager.disableBlend();
      GlStateManager.disableLighting();
      GlStateManager.scale(0.5D, 0.5D, 0.5D);
      GlStateManager.disableDepth();
      GlStateManager.enableDepth();
      GlStateManager.scale(2.0F, 2.0F, 2.0F);
      GL11.glPopMatrix();
   }

   public void draw_background(int colapse_x) {
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
      GlStateManager.disableTexture2D();
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      GL11.glTranslatef(0.0F, -20.0F, 0.0F);
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
      bufferbuilder.pos((double)(-colapse_x - 1), 8.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.5F).endVertex();
      bufferbuilder.pos((double)(-colapse_x - 1), 19.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.5F).endVertex();
      bufferbuilder.pos((double)(colapse_x + 1), 19.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.5F).endVertex();
      bufferbuilder.pos((double)(colapse_x + 1), 8.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.5F).endVertex();
      tessellator.draw();
      bufferbuilder.begin(2, DefaultVertexFormats.POSITION_COLOR);
      bufferbuilder.pos((double)(-colapse_x - 1), 8.0D, 0.0D).color(0.1F, 0.1F, 0.1F, 0.1F).endVertex();
      bufferbuilder.pos((double)(-colapse_x - 1), 19.0D, 0.0D).color(0.1F, 0.1F, 0.1F, 0.1F).endVertex();
      bufferbuilder.pos((double)(colapse_x + 1), 19.0D, 0.0D).color(0.1F, 0.1F, 0.1F, 0.1F).endVertex();
      bufferbuilder.pos((double)(colapse_x + 1), 8.0D, 0.0D).color(0.1F, 0.1F, 0.1F, 0.1F).endVertex();
      tessellator.draw();
   }

   public String get_ping(Entity entity) {
      String ping = "";
      if (entity instanceof EntityPlayer) {
         EntityPlayer player = (EntityPlayer)entity;

         try {
            ping = Integer.toString(this.mc.getConnection().getPlayerInfo(player.getUniqueID()).getResponseTime());
         } catch (Exception var5) {
         }
      }

      return ping;
   }
}
