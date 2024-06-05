package net.minecraft.client.gui.spectator.categories;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSpectator;
import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.ISpectatorMenuView;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.gui.spectator.categories.TeleportToTeam$TeamSelectionObject;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class TeleportToTeam implements ISpectatorMenuView, ISpectatorMenuObject {
   private final List field_178672_a = Lists.newArrayList();

   public TeleportToTeam() {
      Minecraft var1 = Minecraft.getInstance();

      for(ScorePlayerTeam var3 : var1.world.getScoreboard().getTeams()) {
         this.field_178672_a.add(new TeleportToTeam$TeamSelectionObject(var3));
      }

   }

   public List func_178669_a() {
      return this.field_178672_a;
   }

   public IChatComponent func_178670_b() {
      return new ChatComponentText("Select a team to teleport to");
   }

   public void func_178661_a(SpectatorMenu var1) {
      var1.func_178647_a(this);
   }

   public IChatComponent getSpectatorName() {
      return new ChatComponentText("Teleport to team member");
   }

   public void func_178663_a(float var1, int var2) {
      Minecraft.getInstance().getTextureManager().bindTexture(GuiSpectator.field_175269_a);
      Gui.drawModalRectWithCustomSizedTexture(0, 0, 16.0F, 0.0F, 16, 16, 256.0F, 256.0F);
   }

   public boolean func_178662_A_() {
      for(ISpectatorMenuObject var2 : this.field_178672_a) {
         if(var2.func_178662_A_()) {
            return true;
         }
      }

      return false;
   }
}
