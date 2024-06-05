package net.minecraft.realms;

import java.util.Iterator;
import java.util.List;
import net.minecraft.util.IChatComponent;

public class DisconnectedRealmsScreen extends RealmsScreen {
   private final RealmsScreen parent;
   private static final String __OBFID = "CL_00002145";
   private IChatComponent reason;
   private List lines;
   private String title;

   public void keyPressed(char var1, int var2) {
      if (var2 == 1) {
         Realms.setScreen(this.parent);
      }

   }

   public DisconnectedRealmsScreen(RealmsScreen var1, String var2, IChatComponent var3) {
      this.parent = var1;
      this.title = getLocalizedString(var2);
      this.reason = var3;
   }

   public void init() {
      this.buttonsClear();
      this.buttonsAdd(newButton(0, this.width() / 2 - 100, this.height() / 4 + 120 + 12, getLocalizedString("gui.back")));
      this.lines = this.fontSplit(this.reason.getFormattedText(), this.width() - 50);
   }

   public void render(int var1, int var2, float var3) {
      this.renderBackground();
      this.drawCenteredString(this.title, this.width() / 2, this.height() / 2 - 50, 11184810);
      int var4 = this.height() / 2 - 30;
      if (this.lines != null) {
         for(Iterator var5 = this.lines.iterator(); var5.hasNext(); var4 += this.fontLineHeight()) {
            String var6 = (String)var5.next();
            this.drawCenteredString(var6, this.width() / 2, var4, 16777215);
         }
      }

      super.render(var1, var2, var3);
   }

   public void buttonClicked(RealmsButton var1) {
      if (var1.id() == 0) {
         Realms.setScreen(this.parent);
      }

   }
}
