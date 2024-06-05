package cc.novoline.gui.group;

import cc.novoline.gui.group.GroupConstantLine;
import cc.novoline.gui.group.GroupLine;
import cc.novoline.gui.group.GroupNullableLine;
import cc.novoline.gui.group.GroupSupplierLine;
import cc.novoline.gui.group.GuiRoundedGroup;
import cc.novoline.utils.fonts.api.FontRenderer;
import cc.novoline.utils.fonts.impl.Fonts$SFTHIN$SFTHIN_28;
import java.awt.Color;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;

public class GuiRoundedGroupWithLines extends GuiRoundedGroup {
   protected final CopyOnWriteArrayList lines = new CopyOnWriteArrayList();
   protected final List linesView;
   protected final Supplier supplier;
   protected final FontRenderer lineFontRenderer;

   public GuiRoundedGroupWithLines(String var1, int var2, int var3, int var4, int var5, int var6, Supplier var7, FontRenderer var8, FontRenderer var9) {
      super(var1, var2, var3, var4, var5, var6, var9);
      this.linesView = Collections.unmodifiableList(this.lines);
      this.supplier = var7;
      this.lineFontRenderer = var8;
   }

   public GuiRoundedGroupWithLines(String var1, int var2, int var3, int var4, int var5, int var6, Supplier var7, FontRenderer var8) {
      super(var1, var2, var3, var4, var5, var6);
      this.linesView = Collections.unmodifiableList(this.lines);
      this.supplier = var7;
      this.lineFontRenderer = var8;
   }

   public GuiRoundedGroupWithLines(String var1, int var2, int var3, int var4, int var5, int var6, Supplier var7) {
      super(var1, var2, var3, var4, var5, var6);
      this.linesView = Collections.unmodifiableList(this.lines);
      this.supplier = var7;
      this.lineFontRenderer = Fonts$SFTHIN$SFTHIN_28.SFTHIN_28;
   }

   public void drawGroup(Minecraft var1, int var2, int var3) {
      super.drawGroup(var1, var2, var3);
      this.drawLines();
   }

   protected void superDrawGroup(Minecraft var1, int var2, int var3) {
      super.drawGroup(var1, var2, var3);
   }

   protected void drawLines() {
      int var2 = this.lineFontRenderer.getHeight() + 4;
      GroupSupplierLine.b();
      int var3 = this.yPosition + 4;
      Object var4 = this.supplier.get();
      Iterator var5 = this.lines.iterator();
      if(var5.hasNext()) {
         GroupLine var6 = (GroupLine)var5.next();
         String var7 = var6.getText(var4);
         if(var6 instanceof GroupNullableLine) {
            ;
         }

         var3 = var3 + var2;
         this.lineFontRenderer.drawString(var7, (float)(this.xPosition + 6), (float)(this.yPosition + var3), (new Color(198, 198, 198)).getRGB());
      }

   }

   public void addNullableLine(Function var1) {
      this.lines.add(GroupNullableLine.of(var1));
   }

   public void addLine(Function var1) {
      this.lines.add(GroupSupplierLine.of(var1));
   }

   public void addLine(String var1) {
      this.lines.add(GroupConstantLine.of(var1));
   }

   public List getLines() {
      return this.linesView;
   }

   public FontRenderer getLineFontRenderer() {
      return this.lineFontRenderer;
   }
}
