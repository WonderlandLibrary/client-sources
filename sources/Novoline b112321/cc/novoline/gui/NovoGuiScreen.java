package cc.novoline.gui;

import cc.novoline.Novoline;
import cc.novoline.gui.AbstractElement;
import cc.novoline.gui.Element;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.awt.Color;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.client.gui.GuiScreen;

public abstract class NovoGuiScreen extends GuiScreen {
   private final List elements;
   private long lastMessageTimestamp;

   public NovoGuiScreen(Collection var1) {
      this.elements = new ObjectArrayList();
   }

   public NovoGuiScreen() {
      this((Collection)null);
   }

   protected void register(Element var1) {
      this.elements.add(var1);
   }

   public void drawScreen(int var1, int var2, float var3) {
      AbstractElement.b();
      this.drawGradientRect(0.0F, 0.0F, (float)this.width, (float)this.height, (new Color(100, 200, 200)).getRGB(), (new Color(100, 100, 200)).getRGB());
      int var5 = 0;
      if(var5 < this.elements.size()) {
         ((Element)this.elements.get(var5)).draw(var1, var2);
         ++var5;
      }

      if(System.currentTimeMillis() - this.lastMessageTimestamp >= 1000L) {
         Novoline.getLogger().info("Drew: {}", new Object[]{this.elements.stream().map(NovoGuiScreen::lambda$drawScreen$0).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))});
         this.lastMessageTimestamp = System.currentTimeMillis();
      }

      super.drawScreen(var1, var2, var3);
   }

   protected void onInitialize() {
   }

   protected void onClosing() {
   }

   public final void initGui() {
      this.elements.clear();
      this.onInitialize();
      super.initGui();
   }

   public void onGuiClosed() {
      this.onClosing();
      super.onGuiClosed();
   }

   private static String lambda$drawScreen$0(Element var0) {
      return var0.getClass().getSimpleName();
   }
}
