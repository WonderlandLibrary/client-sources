package rina.turok.bope.bopemod.hacks.render;

import java.util.Comparator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.BopeModule;
import rina.turok.bope.bopemod.events.BopeEventRender;
import rina.turok.bope.bopemod.guiscreen.settings.BopeSetting;
import rina.turok.bope.bopemod.hacks.BopeCategory;
import rina.turok.bope.bopemod.util.BopeUtilRenderer;

public class BopePlayerESP extends BopeModule {
   BopeSetting render_1 = this.create("Render Entity", "PlayerESPRenderEntity", "Chams", this.combobox(new String[]{"Chams", "Outline", "Disabled"}));
   BopeSetting render_2 = this.create("Render Entity 2D", "PlayerESPRenderEntity2D", "CSGO", this.combobox(new String[]{"CSGO", "Rect", "Disabled"}));
   BopeSetting disp = this.create("Distance Render", "PlayerESPDistanceRender", 6, 0, 10);
   BopeSetting range = this.create("Range", "PlayerESPRange", 200, 0, 200);
   public static float distance_player = 0.0F;

   public BopePlayerESP() {
      super(BopeCategory.BOPE_RENDER);
      this.name = "Player ESP";
      this.tag = "PlayerESP";
      this.description = "Player ESP - Extra Sensory Perception.";
      this.release("B.O.P.E - Module - B.O.P.E");
   }

   public void render(BopeEventRender event) {
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
      }).filter((entity) -> {
         return distance_player > (float)this.disp.get_value(1);
      }).sorted(Comparator.comparing((entity) -> {
         return -this.mc.player.getDistance(entity);
      })).forEach((entities) -> {
         EntityPlayer player_entities = (EntityPlayer)entities;
         if (this.render_2.in("CSGO")) {
            if (Bope.get_friend_manager().is_friend(player_entities.getName())) {
               BopeUtilRenderer.EntityCSGOESP(entities, Bope.client_r, Bope.client_g, Bope.client_b, Math.round(this.mc.player.getDistance(entities) * 25.5F));
            } else {
               BopeUtilRenderer.EntityCSGOESP(entities, 190, 190, 190, Math.round(this.mc.player.getDistance(entities) * 25.5F));
            }
         }

         if (this.render_2.in("Rect")) {
            if (Bope.get_friend_manager().is_friend(player_entities.getName())) {
               BopeUtilRenderer.EntityRectESP(entities, Bope.client_r, Bope.client_g, Bope.client_b, Math.round(this.mc.player.getDistance(entities) * 25.5F));
            } else {
               BopeUtilRenderer.EntityRectESP(entities, 190, 190, 190, Math.round(this.mc.player.getDistance(entities) * 25.5F));
            }
         }

      });
   }
}
