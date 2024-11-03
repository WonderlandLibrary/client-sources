package net.augustus.modules.combat;

import java.awt.Color;
import java.util.ArrayList;
import net.augustus.events.EventTick;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.StringValue;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;

public class Teams extends Module {
   private final StringValue modes = new StringValue(1, "Modes", this, "Color", new String[]{"Name", "Color"});
   private ArrayList<EntityPlayer> teammates = new ArrayList<>();

   public Teams() {
      super("Teams", Color.red, Categorys.COMBAT);
   }

   @EventTarget
   public void onEventTick(EventTick eventTick) {
      this.teammates = new ArrayList<>();

      for(Entity entity : mc.theWorld.loadedEntityList) {
         if (this.isTeammate(entity)) {
            this.teammates.add((EntityPlayer)entity);
         }
      }
   }

   private boolean isTeammate(Entity entity) {
      if (entity instanceof EntityLivingBase && entity instanceof EntityPlayer) {
         ScorePlayerTeam entityTeam = mc.theWorld.getScoreboard().getPlayersTeam(entity.getName());
         ScorePlayerTeam myTeam = mc.theWorld.getScoreboard().getPlayersTeam(mc.thePlayer.getName());
         if (entityTeam != null && myTeam != null && entityTeam.getColorPrefix().equals(myTeam.getColorPrefix()) && this.modes.getSelected().equals("Color")) {
            return true;
         } else {
            return entityTeam != null && myTeam != null && entityTeam.getTeamName().equals(myTeam.getTeamName()) && this.modes.getSelected().equals("Name");
         }
      } else {
         return false;
      }
   }

   public ArrayList<EntityPlayer> getTeammates() {
      return this.teammates;
   }

   public void setTeammates(ArrayList<EntityPlayer> teammates) {
      this.teammates = teammates;
   }
}
