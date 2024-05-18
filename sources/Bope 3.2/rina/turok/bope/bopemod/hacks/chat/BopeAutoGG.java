package rina.turok.bope.bopemod.hacks.chat;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.entity.player.EntityPlayer;
import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.BopeModule;
import rina.turok.bope.bopemod.hacks.BopeCategory;

public class BopeAutoGG extends BopeModule {
   public BopeAutoGG() {
      super(BopeCategory.BOPE_CHAT);
      this.name = "Auto GG";
      this.tag = "AutoGG";
      this.description = "Automaticaly say good game paceaful or no!";
      this.release("B.O.P.E - Module - B.O.P.E");
   }

   public void update() {
      if (this.mc.player != null && this.mc.world != null) {
         List entities = (List)this.mc.world.loadedEntityList.stream().filter((entity) -> {
            return entity != this.mc.player;
         }).filter((entity) -> {
            return this.mc.player.getDistance(entity) <= 10.0F;
         }).filter((entity) -> {
            return !Bope.get_friend_manager().is_friend(entity.getName());
         }).filter((entity) -> {
            return entity instanceof EntityPlayer;
         }).sorted(Comparator.comparing((distance) -> {
            return this.mc.player.getDistance(distance);
         })).collect(Collectors.toList());
         entities.forEach((entity) -> {
            EntityPlayer entity_player = (EntityPlayer)entity;
            if (entity_player.isDead || entity_player.getHealth() < 0.0F) {
               Bope.dev(entity_player.getName() + " died in your range");
            }

         });
      }

   }
}
