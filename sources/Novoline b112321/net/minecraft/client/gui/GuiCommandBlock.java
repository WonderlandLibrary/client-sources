package net.minecraft.client.gui;

import io.netty.buffer.Unpooled;
import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.IChatComponent;
import org.lwjgl.input.Keyboard;

public class GuiCommandBlock extends GuiScreen {
   private GuiTextField commandTextField;
   private GuiTextField previousOutputTextField;
   private final CommandBlockLogic localCommandBlock;
   private GuiButton doneBtn;
   private GuiButton cancelBtn;
   private GuiButton field_175390_s;
   private boolean field_175389_t;

   public GuiCommandBlock(CommandBlockLogic var1) {
      this.localCommandBlock = var1;
   }

   public void updateScreen() {
      this.commandTextField.updateCursorCounter();
   }

   public void initGui() {
      Keyboard.enableRepeatEvents(true);
      this.buttonList.clear();
      this.buttonList.add(this.doneBtn = new GuiButton(0, this.width / 2 - 4 - 150, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.done", new Object[0])));
      this.buttonList.add(this.cancelBtn = new GuiButton(1, this.width / 2 + 4, this.height / 4 + 120 + 12, 150, 20, I18n.format("gui.cancel", new Object[0])));
      this.buttonList.add(this.field_175390_s = new GuiButton(4, this.width / 2 + 150 - 20, 150, 20, 20, "O"));
      this.commandTextField = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 150, 50, 300, 20);
      this.commandTextField.setMaxStringLength(32767);
      this.commandTextField.setFocused(true);
      this.commandTextField.setText(this.localCommandBlock.getCommand());
      this.previousOutputTextField = new GuiTextField(3, this.fontRendererObj, this.width / 2 - 150, 150, 276, 20);
      this.previousOutputTextField.setMaxStringLength(32767);
      this.previousOutputTextField.setEnabled(false);
      this.previousOutputTextField.setText("-");
      this.field_175389_t = this.localCommandBlock.shouldTrackOutput();
      this.func_175388_a();
      this.doneBtn.enabled = !this.commandTextField.getText().trim().isEmpty();
   }

   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      if(var1.enabled) {
         switch(var1.id) {
         case 0:
            PacketBuffer var2 = new PacketBuffer(Unpooled.buffer());
            var2.writeByte(this.localCommandBlock.func_145751_f());
            this.localCommandBlock.func_145757_a(var2);
            var2.writeString(this.commandTextField.getText());
            var2.writeBoolean(this.localCommandBlock.shouldTrackOutput());
            this.mc.getNetHandler().b(new C17PacketCustomPayload("MC|AdvCdm", var2));
            if(!this.localCommandBlock.shouldTrackOutput()) {
               this.localCommandBlock.setLastOutput((IChatComponent)null);
            }

            this.mc.displayGuiScreen((GuiScreen)null);
            break;
         case 1:
            this.localCommandBlock.setTrackOutput(this.field_175389_t);
            this.mc.displayGuiScreen((GuiScreen)null);
         case 2:
         case 3:
         default:
            break;
         case 4:
            this.localCommandBlock.setTrackOutput(!this.localCommandBlock.shouldTrackOutput());
            this.func_175388_a();
         }
      }

   }

   protected void keyTyped(char var1, int var2) throws IOException {
      this.commandTextField.textboxKeyTyped(var1, var2);
      this.previousOutputTextField.textboxKeyTyped(var1, var2);
      this.doneBtn.enabled = !this.commandTextField.getText().trim().isEmpty();
      if(var2 != 28 && var2 != 156) {
         if(var2 == 1) {
            this.actionPerformed(this.cancelBtn);
         }
      } else {
         this.actionPerformed(this.doneBtn);
      }

   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      super.mouseClicked(var1, var2, var3);
      this.commandTextField.mouseClicked(var1, var2, var3);
      this.previousOutputTextField.mouseClicked(var1, var2, var3);
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRendererObj, I18n.format("advMode.setCommand", new Object[0]), this.width / 2, 20, 16777215);
      this.drawString(this.fontRendererObj, I18n.format("advMode.command", new Object[0]), this.width / 2 - 150, 37, 10526880);
      this.commandTextField.drawTextBox();
      int var4 = 75;
      int var5 = 0;
      this.drawString(this.fontRendererObj, I18n.format("advMode.nearestPlayer", new Object[0]), this.width / 2 - 150, var4 + var5++ * this.fontRendererObj.getHeight(), 10526880);
      this.drawString(this.fontRendererObj, I18n.format("advMode.randomPlayer", new Object[0]), this.width / 2 - 150, var4 + var5++ * this.fontRendererObj.getHeight(), 10526880);
      this.drawString(this.fontRendererObj, I18n.format("advMode.allPlayers", new Object[0]), this.width / 2 - 150, var4 + var5++ * this.fontRendererObj.getHeight(), 10526880);
      this.drawString(this.fontRendererObj, I18n.format("advMode.allEntities", new Object[0]), this.width / 2 - 150, var4 + var5++ * this.fontRendererObj.getHeight(), 10526880);
      this.drawString(this.fontRendererObj, "", this.width / 2 - 150, var4 + var5++ * this.fontRendererObj.getHeight(), 10526880);
      if(!this.previousOutputTextField.getText().isEmpty()) {
         var4 = var4 + var5 * this.fontRendererObj.getHeight() + 16;
         this.drawString(this.fontRendererObj, I18n.format("advMode.previousOutput", new Object[0]), this.width / 2 - 150, var4, 10526880);
         this.previousOutputTextField.drawTextBox();
      }

      super.drawScreen(var1, var2, var3);
   }

   private void func_175388_a() {
      if(this.localCommandBlock.shouldTrackOutput()) {
         this.field_175390_s.displayString = "O";
         if(this.localCommandBlock.getLastOutput() != null) {
            this.previousOutputTextField.setText(this.localCommandBlock.getLastOutput().getUnformattedText());
         }
      } else {
         this.field_175390_s.displayString = "X";
         this.previousOutputTextField.setText("-");
      }

   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
