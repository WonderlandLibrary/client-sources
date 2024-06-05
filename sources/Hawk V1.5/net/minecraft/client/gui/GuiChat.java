package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class GuiChat extends GuiScreen {
   private static final Logger logger = LogManager.getLogger();
   protected GuiTextField inputField;
   private String historyBuffer = "";
   private boolean waitingOnAutocomplete;
   private int autocompleteIndex;
   private static final String __OBFID = "CL_00000682";
   private List foundPlayerNames = Lists.newArrayList();
   private String defaultInputFieldText = "";
   private boolean playerNamesFound;
   private int sentHistoryCursor = -1;

   public void initGui() {
      Keyboard.enableRepeatEvents(true);
      this.sentHistoryCursor = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
      this.inputField = new GuiTextField(0, this.fontRendererObj, 4, this.height - 12, this.width - 4, 12);
      this.inputField.setMaxStringLength(100);
      this.inputField.setEnableBackgroundDrawing(false);
      this.inputField.setFocused(true);
      this.inputField.setText(this.defaultInputFieldText);
      this.inputField.setCanLoseFocus(false);
   }

   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
      this.mc.ingameGUI.getChatGUI().resetScroll();
   }

   public void getSentHistory(int var1) {
      int var2 = this.sentHistoryCursor + var1;
      int var3 = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
      var2 = MathHelper.clamp_int(var2, 0, var3);
      if (var2 != this.sentHistoryCursor) {
         if (var2 == var3) {
            this.sentHistoryCursor = var3;
            this.inputField.setText(this.historyBuffer);
         } else {
            if (this.sentHistoryCursor == var3) {
               this.historyBuffer = this.inputField.getText();
            }

            this.inputField.setText((String)this.mc.ingameGUI.getChatGUI().getSentMessages().get(var2));
            this.sentHistoryCursor = var2;
         }
      }

   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   private void sendAutocompleteRequest(String var1, String var2) {
      if (var1.length() >= 1) {
         BlockPos var3 = null;
         if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            var3 = this.mc.objectMouseOver.func_178782_a();
         }

         this.mc.thePlayer.sendQueue.addToSendQueue(new C14PacketTabComplete(var1, var3));
         this.waitingOnAutocomplete = true;
      }

   }

   public GuiChat(String var1) {
      this.defaultInputFieldText = var1;
   }

   protected void keyTyped(char var1, int var2) throws IOException {
      this.waitingOnAutocomplete = false;
      if (var2 == 15) {
         this.autocompletePlayerNames();
      } else {
         this.playerNamesFound = false;
      }

      if (var2 == 1) {
         this.mc.displayGuiScreen((GuiScreen)null);
      } else if (var2 != 28 && var2 != 156) {
         if (var2 == 200) {
            this.getSentHistory(-1);
         } else if (var2 == 208) {
            this.getSentHistory(1);
         } else if (var2 == 201) {
            this.mc.ingameGUI.getChatGUI().scroll(this.mc.ingameGUI.getChatGUI().getLineCount() - 1);
         } else if (var2 == 209) {
            this.mc.ingameGUI.getChatGUI().scroll(-this.mc.ingameGUI.getChatGUI().getLineCount() + 1);
         } else {
            this.inputField.textboxKeyTyped(var1, var2);
         }
      } else {
         String var3 = this.inputField.getText().trim();
         if (var3.length() > 0) {
            this.func_175275_f(var3);
         }

         this.mc.displayGuiScreen((GuiScreen)null);
      }

   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      if (var3 == 0) {
         IChatComponent var4 = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
         if (this.func_175276_a(var4)) {
            return;
         }
      }

      this.inputField.mouseClicked(var1, var2, var3);
      super.mouseClicked(var1, var2, var3);
   }

   public GuiChat() {
   }

   public void updateScreen() {
      this.inputField.updateCursorCounter();
   }

   protected void func_175274_a(String var1, boolean var2) {
      if (var2) {
         this.inputField.setText(var1);
      } else {
         this.inputField.writeText(var1);
      }

   }

   public void drawScreen(int var1, int var2, float var3) {
      drawRect(2.0D, (double)(this.height - 14), (double)(this.width - 2), (double)(this.height - 2), Integer.MIN_VALUE);
      this.inputField.drawTextBox();
      IChatComponent var4 = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
      if (var4 != null && var4.getChatStyle().getChatHoverEvent() != null) {
         this.func_175272_a(var4, var1, var2);
      }

      super.drawScreen(var1, var2, var3);
   }

   public void onAutocompleteResponse(String[] var1) {
      if (this.waitingOnAutocomplete) {
         this.playerNamesFound = false;
         this.foundPlayerNames.clear();
         String[] var2 = var1;
         int var3 = var1.length;

         String var5;
         for(int var4 = 0; var4 < var3; ++var4) {
            var5 = var2[var4];
            if (var5.length() > 0) {
               this.foundPlayerNames.add(var5);
            }
         }

         String var6 = this.inputField.getText().substring(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false));
         var5 = StringUtils.getCommonPrefix(var1);
         if (var5.length() > 0 && !var6.equalsIgnoreCase(var5)) {
            this.inputField.deleteFromCursor(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false) - this.inputField.getCursorPosition());
            this.inputField.writeText(var5);
         } else if (this.foundPlayerNames.size() > 0) {
            this.playerNamesFound = true;
            this.autocompletePlayerNames();
         }
      }

   }

   public void autocompletePlayerNames() {
      String var1;
      if (this.playerNamesFound) {
         this.inputField.deleteFromCursor(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false) - this.inputField.getCursorPosition());
         if (this.autocompleteIndex >= this.foundPlayerNames.size()) {
            this.autocompleteIndex = 0;
         }
      } else {
         int var2 = this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false);
         this.foundPlayerNames.clear();
         this.autocompleteIndex = 0;
         String var3 = this.inputField.getText().substring(var2).toLowerCase();
         var1 = this.inputField.getText().substring(0, this.inputField.getCursorPosition());
         this.sendAutocompleteRequest(var1, var3);
         if (this.foundPlayerNames.isEmpty()) {
            return;
         }

         this.playerNamesFound = true;
         this.inputField.deleteFromCursor(var2 - this.inputField.getCursorPosition());
      }

      if (this.foundPlayerNames.size() > 1) {
         StringBuilder var4 = new StringBuilder();

         for(Iterator var5 = this.foundPlayerNames.iterator(); var5.hasNext(); var4.append(var1)) {
            var1 = (String)var5.next();
            if (var4.length() > 0) {
               var4.append(", ");
            }
         }

         this.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new ChatComponentText(String.valueOf(var4)), 1);
      }

      this.inputField.writeText((String)this.foundPlayerNames.get(this.autocompleteIndex++));
   }

   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      int var1 = Mouse.getEventDWheel();
      if (var1 != 0) {
         if (var1 > 1) {
            var1 = 1;
         }

         if (var1 < -1) {
            var1 = -1;
         }

         if (!isShiftKeyDown()) {
            var1 *= 7;
         }

         this.mc.ingameGUI.getChatGUI().scroll(var1);
      }

   }
}
