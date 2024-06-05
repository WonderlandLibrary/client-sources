package cc.novoline.viaversion.gui;

import cc.novoline.viaversion.gui.GuiProtocolSelector;
import cc.novoline.viaversion.utils.ProtocolSorter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.util.EnumChatFormatting;
import viaversion.viafabric.ViaFabric;
import viaversion.viafabric.util.ProtocolUtils;
import viaversion.viaversion.api.protocol.ProtocolVersion;

class GuiProtocolSelector$SlotList extends GuiSlot {
   final GuiProtocolSelector this$0;

   public GuiProtocolSelector$SlotList(GuiProtocolSelector var1, Minecraft var2, int var3, int var4, int var5, int var6, int var7) {
      super(var2, var3, var4, var5, var6, var7);
      this.this$0 = var1;
   }

   protected int getSize() {
      return ProtocolSorter.getProtocolVersions().size();
   }

   protected void elementClicked(int var1, boolean var2, int var3, int var4) {
      ViaFabric.clientSideVersion = ((ProtocolVersion)ProtocolSorter.getProtocolVersions().get(var1)).getVersion();
   }

   protected boolean isSelected(int var1) {
      return false;
   }

   protected void drawBackground() {
      this.this$0.drawDefaultBackground();
   }

   protected void drawSlot(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.this$0.drawCenteredString(this.mc.fontRendererObj, (ViaFabric.clientSideVersion == ((ProtocolVersion)ProtocolSorter.getProtocolVersions().get(var1)).getVersion()?EnumChatFormatting.GREEN.toString():EnumChatFormatting.WHITE.toString()) + ProtocolUtils.getProtocolName(((ProtocolVersion)ProtocolSorter.getProtocolVersions().get(var1)).getVersion()), this.width / 2, var3 + 2, -1);
   }
}
