package net.minecraft.client.gui.spectator.categories;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
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
import net.minecraft.world.WorldSettings;

public class TeleportToPlayer implements ISpectatorMenuView, ISpectatorMenuObject {
   private final List field_178673_b;
   private static final Ordering field_178674_a = Ordering.from(new Comparator() {
      private static final String __OBFID = "CL_00001921";

      public int compare(Object var1, Object var2) {
         return this.func_178746_a((NetworkPlayerInfo)var1, (NetworkPlayerInfo)var2);
      }

      public int func_178746_a(NetworkPlayerInfo var1, NetworkPlayerInfo var2) {
         return ComparisonChain.start().compare(var1.func_178845_a().getId(), var2.func_178845_a().getId()).result();
      }
   });
   private static final String __OBFID = "CL_00001922";

   public IChatComponent func_178670_b() {
      return new ChatComponentText("Select a player to teleport to");
   }

   public List func_178669_a() {
      return this.field_178673_b;
   }

   public void func_178661_a(SpectatorMenu var1) {
      var1.func_178647_a(this);
   }

   public TeleportToPlayer() {
      this(field_178674_a.sortedCopy(Minecraft.getMinecraft().getNetHandler().func_175106_d()));
   }

   public IChatComponent func_178664_z_() {
      return new ChatComponentText("Teleport to player");
   }

   public void func_178663_a(float var1, int var2) {
      Minecraft.getMinecraft().getTextureManager().bindTexture(GuiSpectator.field_175269_a);
      Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0F, 0.0F, 16, 16, 256.0F, 256.0F);
   }

   public boolean func_178662_A_() {
      return !this.field_178673_b.isEmpty();
   }

   public TeleportToPlayer(Collection var1) {
      this.field_178673_b = Lists.newArrayList();
      Iterator var2 = field_178674_a.sortedCopy(var1).iterator();

      while(var2.hasNext()) {
         NetworkPlayerInfo var3 = (NetworkPlayerInfo)var2.next();
         if (var3.getGameType() != WorldSettings.GameType.SPECTATOR) {
            this.field_178673_b.add(new PlayerMenuObject(var3.func_178845_a()));
         }
      }

   }
}
