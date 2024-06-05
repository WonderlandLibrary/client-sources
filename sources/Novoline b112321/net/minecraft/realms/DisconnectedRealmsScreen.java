package net.minecraft.realms;

import java.util.List;
import net.minecraft.realms.Realms;
import net.minecraft.realms.RealmsButton;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.IChatComponent;

public class DisconnectedRealmsScreen extends RealmsScreen {
   private String title;
   private IChatComponent reason;
   private List lines;
   private final RealmsScreen parent;
   private int textHeight;

   public DisconnectedRealmsScreen(RealmsScreen var1, String var2, IChatComponent var3) {
      this.parent = var1;
      this.title = getLocalizedString(var2);
      this.reason = var3;
   }

   public void init() {
      Realms.setConnectedToRealms(false);
      this.buttonsClear();
      this.lines = this.fontSplit(this.reason.getFormattedText(), this.width() - 50);
      this.textHeight = this.lines.size() * this.fontLineHeight();
      this.buttonsAdd(newButton(0, this.width() / 2 - 100, this.height() / 2 + this.textHeight / 2 + this.fontLineHeight(), getLocalizedString("gui.back")));
   }

   public void keyPressed(char var1, int var2) {
      if(var2 == 1) {
         Realms.setScreen(this.parent);
      }

   }

   public void buttonClicked(RealmsButton var1) {
      if(var1.id() == 0) {
         Realms.setScreen(this.parent);
      }

   }

   public void render(int var1, int var2, float var3) {
      this.renderBackground();
      this.drawCenteredString(this.title, this.width() / 2, this.height() / 2 - this.textHeight / 2 - this.fontLineHeight() * 2, 11184810);
      int var4 = this.height() / 2 - this.textHeight / 2;
      if(this.lines != null) {
         for(String var6 : this.lines) {
            this.drawCenteredString(var6, this.width() / 2, var4, 16777215);
            var4 += this.fontLineHeight();
         }
      }

      super.render(var1, var2, var3);
   }
}
