package cc.novoline.gui.group;

import cc.novoline.gui.group.GroupLine;
import cc.novoline.gui.group.GroupSupplierLine;
import cc.novoline.gui.group.GuiRoundedGroupWithLines;
import cc.novoline.gui.screen.alt.repository.GuiAddAlt;
import cc.novoline.utils.RenderUtils;
import cc.novoline.utils.fonts.impl.Fonts$SFBOLD$SFBOLD_22;
import cc.novoline.utils.fonts.impl.Fonts$SFTHIN$SFTHIN_20;
import java.awt.Color;
import java.util.function.Function;
import java.util.function.Supplier;
import net.acE;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;

public final class GuiGroupAltLogin extends GuiRoundedGroupWithLines {
   private String status;

   public GuiGroupAltLogin(GuiAddAlt var1, String var2) {
      GroupSupplierLine.b();
      super(var2, 0, 15, 200, 30, 15, GuiGroupAltLogin::lambda$new$0, Fonts$SFTHIN$SFTHIN_20.SFTHIN_20, Fonts$SFBOLD$SFBOLD_22.SFBOLD_22);
      this.status = EnumChatFormatting.GRAY + "Idling...";
      this.xPosition = (int)((float)(var1.width - this.width) / 2.0F);
      this.addLine(this::lambda$new$1);
      if(acE.b() == null) {
         GroupSupplierLine.b("Qj7N1");
      }

   }

   public void drawGroup(Minecraft var1, int var2, int var3) {
      String var4 = GroupSupplierLine.b();
      if(!this.hidden) {
         RenderUtils.drawRoundedRect((float)this.xPosition, (float)this.yPosition, (float)this.width, (float)this.height, (float)this.radius, (new Color(48, 49, 54)).getRGB());
         if(this.title != null) {
            this.titleFontRenderer.drawString(this.title, (float)this.xPosition + (float)(this.width - this.titleFontRenderer.stringWidth(this.title)) / 2.0F, (float)(this.yPosition + 5), (new Color(198, 198, 198)).getRGB());
         }

         this.drawLines();
      }
   }

   protected void drawLines() {
      String var2 = ((GroupLine)this.lines.get(0)).getText((Object)null);
      GroupSupplierLine.b();
      this.lineFontRenderer.drawString(var2, (float)this.xPosition + (float)(this.width - this.lineFontRenderer.stringWidth(var2)) / 2.0F, (float)(this.yPosition + this.lineFontRenderer.getHeight() + 11), (new Color(198, 198, 198)).getRGB());
   }

   public void updateStatus(String var1) {
      this.status = var1;
   }

   private String lambda$new$1(Object var1) {
      return this.status;
   }

   private static Object lambda$new$0() {
      return null;
   }
}
