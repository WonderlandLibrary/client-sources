package net.minecraft.client.gui;

import io.netty.buffer.Unpooled;
import java.io.IOException;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.IChatComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

public class GuiCommandBlock extends GuiScreen {
   private GuiTextField commandTextField;
   private GuiButton field_175390_s;
   private GuiButton doneBtn;
   private boolean field_175389_t;
   private final CommandBlockLogic localCommandBlock;
   private GuiTextField field_146486_g;
   private GuiButton cancelBtn;
   private static final String __OBFID = "CL_00000748";
   private static final Logger field_146488_a = LogManager.getLogger();

   public void drawScreen(int var1, int var2, float var3) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRendererObj, I18n.format("advMode.setCommand"), this.width / 2, 20, 16777215);
      this.drawString(this.fontRendererObj, I18n.format("advMode.command"), this.width / 2 - 150, 37, 10526880);
      this.commandTextField.drawTextBox();
      byte var4 = 75;
      byte var5 = 0;
      FontRenderer var6 = this.fontRendererObj;
      String var7 = I18n.format("advMode.nearestPlayer");
      int var8 = this.width / 2 - 150;
      int var9 = var5 + 1;
      this.drawString(var6, var7, var8, var4 + var5 * this.fontRendererObj.FONT_HEIGHT, 10526880);
      this.drawString(this.fontRendererObj, I18n.format("advMode.randomPlayer"), this.width / 2 - 150, var4 + var9++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
      this.drawString(this.fontRendererObj, I18n.format("advMode.allPlayers"), this.width / 2 - 150, var4 + var9++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
      this.drawString(this.fontRendererObj, I18n.format("advMode.allEntities"), this.width / 2 - 150, var4 + var9++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
      this.drawString(this.fontRendererObj, "", this.width / 2 - 150, var4 + var9++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
      if (this.field_146486_g.getText().length() > 0) {
         int var10 = var4 + var9 * this.fontRendererObj.FONT_HEIGHT + 16;
         this.drawString(this.fontRendererObj, I18n.format("advMode.previousOutput"), this.width / 2 - 150, var10, 10526880);
         this.field_146486_g.drawTextBox();
      }

      super.drawScreen(var1, var2, var3);
   }

   protected void keyTyped(char var1, int var2) throws IOException {
      this.commandTextField.textboxKeyTyped(var1, var2);
      this.field_146486_g.textboxKeyTyped(var1, var2);
      this.doneBtn.enabled = this.commandTextField.getText().trim().length() > 0;
      if (var2 != 28 && var2 != 156) {
         if (var2 == 1) {
            this.actionPerformed(this.cancelBtn);
         }
      } else {
         this.actionPerformed(this.doneBtn);
      }

   }

   public GuiCommandBlock(CommandBlockLogic var1) {
      this.localCommandBlock = var1;
   }

   private void func_175388_a() {
      if (this.localCommandBlock.func_175571_m()) {
         this.field_175390_s.displayString = "O";
         if (this.localCommandBlock.getLastOutput() != null) {
            this.field_146486_g.setText(this.localCommandBlock.getLastOutput().getUnformattedText());
         }
      } else {
         this.field_175390_s.displayString = "X";
         this.field_146486_g.setText("-");
      }

   }

   public void updateScreen() {
      this.commandTextField.updateCursorCounter();
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      if (var1.enabled) {
         if (var1.id == 1) {
            this.localCommandBlock.func_175573_a(this.field_175389_t);
            this.mc.displayGuiScreen((GuiScreen)null);
         } else if (var1.id == 0) {
            PacketBuffer var2 = new PacketBuffer(Unpooled.buffer());
            var2.writeByte(this.localCommandBlock.func_145751_f());
            this.localCommandBlock.func_145757_a(var2);
            var2.writeString(this.commandTextField.getText());
            var2.writeBoolean(this.localCommandBlock.func_175571_m());
            this.mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload("MC|AdvCdm", var2));
            if (!this.localCommandBlock.func_175571_m()) {
               this.localCommandBlock.func_145750_b((IChatComponent)null);
            }

            this.mc.displayGuiScreen((GuiScreen)null);
         } else if (var1.id == 4) {
            this.localCommandBlock.func_175573_a(!this.localCommandBlock.func_175571_m());
            this.func_175388_a();
         }
      }

   }

   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
   }

   public void initGui() {
      Keyboard.enableRepeatEvents(true);
      this.buttonList.clear();
      this.buttonList.add(this.doneBtn = new GuiButton(0, this.width / 2 - 4 - 150, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.done")));
      this.buttonList.add(this.cancelBtn = new GuiButton(1, this.width / 2 + 4, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.cancel")));
      this.buttonList.add(this.field_175390_s = new GuiButton(4, this.width / 2 + 150 - 20, 150, 20, 20, "O"));
      this.commandTextField = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 150, 50, 300, 20);
      this.commandTextField.setMaxStringLength(32767);
      this.commandTextField.setFocused(true);
      this.commandTextField.setText(this.localCommandBlock.getCustomName());
      this.field_146486_g = new GuiTextField(3, this.fontRendererObj, this.width / 2 - 150, 150, 276, 20);
      this.field_146486_g.setMaxStringLength(32767);
      this.field_146486_g.setEnabled(false);
      this.field_146486_g.setText("-");
      this.field_175389_t = this.localCommandBlock.func_175571_m();
      this.func_175388_a();
      this.doneBtn.enabled = this.commandTextField.getText().trim().length() > 0;
   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      super.mouseClicked(var1, var2, var3);
      this.commandTextField.mouseClicked(var1, var2, var3);
      this.field_146486_g.mouseClicked(var1, var2, var3);
   }
}
