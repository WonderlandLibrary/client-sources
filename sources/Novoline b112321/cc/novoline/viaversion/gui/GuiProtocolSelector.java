package cc.novoline.viaversion.gui;

import cc.novoline.viaversion.gui.GuiProtocolSelector$SlotList;
import java.io.IOException;
import net.acE;
import net.j5;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;

public class GuiProtocolSelector extends GuiScreen {
   public GuiProtocolSelector$SlotList list;
   private GuiScreen parent;

   public GuiProtocolSelector(GuiScreen var1) {
      this.parent = var1;
   }

   public void initGui() {
      j5.b();
      super.initGui();
      this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height - 27, 200, 20, "Back"));
      this.list = new GuiProtocolSelector$SlotList(this, this.mc, this.width, this.height, 32, this.height - 32, 10);
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      String[] var2 = j5.b();
      this.list.actionPerformed(var1);
      if(var1.id == 1) {
         this.mc.displayGuiScreen(this.parent);
      }

   }

   public void handleMouseInput() throws IOException {
      this.list.handleMouseInput();
      super.handleMouseInput();
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.list.drawScreen(var1, var2, var3);
      j5.b();
      GL11.glPushMatrix();
      GL11.glScalef(2.0F, 2.0F, 2.0F);
      this.drawCenteredString(this.fontRendererObj, EnumChatFormatting.BOLD.toString() + "ViaMCP", this.width / 4, 6, 16777215);
      GL11.glPopMatrix();
      super.drawScreen(var1, var2, var3);
      if(acE.b() == null) {
         j5.b(new String[4]);
      }

   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
