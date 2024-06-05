package net.minecraft.client.gui.spectator.categories;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSpectator;
import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
import net.minecraft.client.gui.spectator.ISpectatorMenuView;
import net.minecraft.client.gui.spectator.PlayerMenuObject;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.WorldSettings$GameType;

public class TeleportToPlayer implements ISpectatorMenuView, ISpectatorMenuObject {
   private static final Ordering field_178674_a = Ordering.from(TeleportToPlayer::lambda$static$0);
   private final List field_178673_b;

   public TeleportToPlayer() {
      this(field_178674_a.sortedCopy(Minecraft.getInstance().getNetHandler().getPlayerInfoMap()));
   }

   public TeleportToPlayer(Collection var1) {
      this.field_178673_b = Lists.newArrayList();

      for(NetworkPlayerInfo var3 : field_178674_a.sortedCopy(var1)) {
         if(var3.getGameType() != WorldSettings$GameType.SPECTATOR) {
            this.field_178673_b.add(new PlayerMenuObject(var3.getGameProfile()));
         }
      }

   }

   public List func_178669_a() {
      return this.field_178673_b;
   }

   public IChatComponent func_178670_b() {
      return new ChatComponentText("Select a player to teleport to");
   }

   public void func_178661_a(SpectatorMenu var1) {
      var1.func_178647_a(this);
   }

   public IChatComponent getSpectatorName() {
      return new ChatComponentText("Teleport to player");
   }

   public void func_178663_a(float var1, int var2) {
      Minecraft.getInstance().getTextureManager().bindTexture(GuiSpectator.field_175269_a);
      Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0F, 0.0F, 16, 16, 256.0F, 256.0F);
   }

   public boolean func_178662_A_() {
      return !this.field_178673_b.isEmpty();
   }

   private static int lambda$static$0(NetworkPlayerInfo var0, NetworkPlayerInfo var1) {
      return ComparisonChain.start().compare(var0.getGameProfile().getId(), var1.getGameProfile().getId()).result();
   }
}
