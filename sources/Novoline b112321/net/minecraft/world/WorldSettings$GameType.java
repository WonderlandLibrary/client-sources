package net.minecraft.world;

import cc.novoline.Novoline;
import cc.novoline.modules.visual.ClickGUI;
import cc.novoline.utils.ServerUtils;
import cc.novoline.utils.Servers;
import net.minecraft.entity.player.PlayerAbilities;

public enum WorldSettings$GameType {
   NOT_SET(-1, ""),
   SURVIVAL(0, "survival"),
   CREATIVE(1, "creative"),
   ADVENTURE(2, "adventure"),
   SPECTATOR(3, "spectator");

   int id;
   String name;
   private static final WorldSettings$GameType[] $VALUES = new WorldSettings$GameType[]{NOT_SET, SURVIVAL, CREATIVE, ADVENTURE, SPECTATOR};

   private WorldSettings$GameType(int var3, String var4) {
      this.id = var3;
      this.name = var4;
   }

   public int getID() {
      return this.id;
   }

   public String getName() {
      return this.name;
   }

   public void configurePlayerCapabilities(PlayerAbilities var1) {
      if(this == CREATIVE) {
         var1.setAllowFlying(true);
         var1.setCreative(true);
         var1.setDisabledDamage(true);
      } else if(this == SPECTATOR) {
         var1.setAllowFlying(true);
         var1.setCreative(false);
         var1.setDisabledDamage(true);
         var1.setFlying(true);
      } else {
         var1.setAllowFlying(false);
         var1.setCreative(false);
         var1.setDisabledDamage(false);
         var1.setFlying(false);
      }

      var1.setAllowEdit(!this.isAdventure());
      if(ServerUtils.isHypixel() && (ServerUtils.serverIs(Servers.NONE) || ServerUtils.serverIs(Servers.SG)) && !var1.isAllowEdit()) {
         ((ClickGUI)Novoline.getInstance().getModuleManager().getModule(ClickGUI.class)).setCurrentServer(Servers.LOBBY);
      }

   }

   public boolean isAdventure() {
      return this == ADVENTURE || this == SPECTATOR;
   }

   public boolean isCreative() {
      return this == CREATIVE;
   }

   public boolean isSurvivalOrAdventure() {
      return this == SURVIVAL || this == ADVENTURE;
   }

   public static WorldSettings$GameType getByID(int var0) {
      for(WorldSettings$GameType var4 : values()) {
         if(var4.id == var0) {
            return var4;
         }
      }

      return SURVIVAL;
   }

   public static WorldSettings$GameType getByName(String var0) {
      for(WorldSettings$GameType var4 : values()) {
         if(var4.name.equals(var0)) {
            return var4;
         }
      }

      return SURVIVAL;
   }
}
