package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiListExtended$IGuiListEntry;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.ServerListEntryLanDetected;
import net.minecraft.client.gui.ServerListEntryLanScan;
import net.minecraft.client.gui.ServerListEntryNormal;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.network.LanServerDetector$LanServer;

public class ServerSelectionList extends GuiListExtended {
   private final GuiMultiplayer owner;
   private final List field_148198_l = Lists.newArrayList();
   private final List field_148199_m = Lists.newArrayList();
   private final GuiListExtended$IGuiListEntry lanScanEntry = new ServerListEntryLanScan();
   private int selectedSlotIndex = -1;

   public ServerSelectionList(GuiMultiplayer var1, Minecraft var2, int var3, int var4, int var5, int var6, int var7) {
      super(var2, var3, var4, var5, var6, var7);
      this.owner = var1;
   }

   public GuiListExtended$IGuiListEntry getListEntry(int var1) {
      if(var1 < this.field_148198_l.size()) {
         return (GuiListExtended$IGuiListEntry)this.field_148198_l.get(var1);
      } else {
         var1 = var1 - this.field_148198_l.size();
         return this.lanScanEntry;
      }
   }

   protected int getSize() {
      return this.field_148198_l.size() + 1 + this.field_148199_m.size();
   }

   public void setSelectedSlotIndex(int var1) {
      this.selectedSlotIndex = var1;
   }

   protected boolean isSelected(int var1) {
      return var1 == this.selectedSlotIndex;
   }

   public int func_148193_k() {
      return this.selectedSlotIndex;
   }

   public void func_148195_a(ServerList var1) {
      this.field_148198_l.clear();

      for(int var2 = 0; var2 < var1.countServers(); ++var2) {
         this.field_148198_l.add(new ServerListEntryNormal(this.owner, var1.getServerData(var2)));
      }

   }

   public void func_148194_a(List var1) {
      this.field_148199_m.clear();

      for(LanServerDetector$LanServer var3 : var1) {
         this.field_148199_m.add(new ServerListEntryLanDetected(this.owner, var3));
      }

   }

   protected int getScrollBarX() {
      return super.getScrollBarX() + 30;
   }

   public int getListWidth() {
      return super.getListWidth() + 85;
   }
}
