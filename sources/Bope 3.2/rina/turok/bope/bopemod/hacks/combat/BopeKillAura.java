package rina.turok.bope.bopemod.hacks.combat;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerDigging.Action;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.BopeModule;
import rina.turok.bope.bopemod.events.BopeEventRender;
import rina.turok.bope.bopemod.guiscreen.settings.BopeSetting;
import rina.turok.bope.bopemod.hacks.BopeCategory;
import rina.turok.bope.bopemod.util.BopeUtilRenderer;

public class BopeKillAura extends BopeModule {
   BopeSetting player = this.create("Player", "KillAuraPlayer", true);
   BopeSetting hostile = this.create("Hostile", "KillAuraHostile", false);
   BopeSetting sword = this.create("Sword", "KillAuraSword", true);
   BopeSetting esp = this.create("Render Entity Mode", "KillAuraRenderEntityMode", "CSGO", this.combobox(new String[]{"CSGO", "Rect", "Disabled"}));
   BopeSetting range = this.create("Range", "KillAuraRange", 6, 1, 10);
   boolean with_sword = true;
   EnumHand actual_hand;
   double tick;
   List entities;

   public BopeKillAura() {
      super(BopeCategory.BOPE_COMBAT);
      this.actual_hand = EnumHand.MAIN_HAND;
      this.tick = 0.0D;
      this.entities = null;
      this.name = "Kill Aura";
      this.tag = "KillAura";
      this.description = "To able hit enemies in a range.";
      this.release("B.O.P.E - Module - B.O.P.E");
   }

   public void update() {
      if (this.mc.player != null && this.mc.world != null) {
         this.entities = (List)this.mc.world.loadedEntityList.stream().filter((entity) -> {
            return entity != this.mc.player;
         }).filter((entity) -> {
            return this.mc.player.getDistance(entity) <= (float)this.range.get_value(1);
         }).filter((entity) -> {
            return !entity.isDead;
         }).filter((entity) -> {
            return !Bope.get_friend_manager().is_friend(entity.getName());
         }).filter((entity) -> {
            return entity instanceof EntityPlayer && this.player.get_value(true) || entity instanceof IMob && this.hostile.get_value(true);
         }).filter((entity) -> {
            return !entity.isDead;
         }).sorted(Comparator.comparing((distance) -> {
            return this.mc.player.getDistance(distance);
         })).collect(Collectors.toList());
      }

   }

   public void render(BopeEventRender event) {
      if (this.entities != null) {
         this.entities.forEach(entity -> {
            if (this.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword || !this.sword.get_value(true)) {
               if (this.esp.in("CSGO")) {
                  BopeUtilRenderer.EntityCSGOESP((Entity) entity, 190, 190, 190, Math.round(this.mc.player.getDistance((Entity) entity) * 25.5F));
               }

               if (this.esp.in("Rect")) {
                  BopeUtilRenderer.EntityRectESP((Entity) entity, 190, 190, 190, Math.round(this.mc.player.getDistance((Entity) entity) * 25.5F));
               }

               this.attack_entity((Entity) entity);
            }
         });
      }

   }

   public void attack_entity(Entity entity) {
      if (this.mc.player.getCooledAttackStrength(0.0F) >= 1.0F) {
         ItemStack off_hand_item = this.mc.player.getHeldItemOffhand();
         if (off_hand_item != null && off_hand_item.getItem() == Items.SHIELD) {
            this.mc.player.connection.sendPacket(new CPacketPlayerDigging(Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, this.mc.player.getHorizontalFacing()));
         }

         this.mc.playerController.attackEntity(this.mc.player, entity);
         this.mc.player.swingArm(this.actual_hand);
      }

   }
}
