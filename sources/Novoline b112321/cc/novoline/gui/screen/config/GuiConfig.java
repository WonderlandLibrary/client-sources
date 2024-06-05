package cc.novoline.gui.screen.config;

import cc.novoline.Novoline;
import cc.novoline.gui.screen.click.DiscordGUI;
import cc.novoline.gui.screen.config.ConfigMenu;
import cc.novoline.modules.configurations.ConfigManager;
import cc.novoline.utils.fonts.impl.Fonts$SFTHIN$SFTHIN_17;
import cc.novoline.utils.java.Checks;
import java.nio.file.Path;
import net.acE;
import net.minecraft.client.gui.Gui;
import org.jetbrains.annotations.NotNull;

public class GuiConfig {
   private final String name;
   private boolean selected;
   private int y = 0;
   private int offset = 30;

   private GuiConfig(@NotNull String var1) {
      this.name = var1;
   }

   @NotNull
   public static GuiConfig of(@NotNull Path var0) {
      Checks.notNull(var0, "path");
      Checks.check(var0.toString().endsWith(ConfigManager.getExtension()), "not config path:");
      String var1 = var0.getFileName().toString();
      String var2 = var1.substring(0, var1.length() - ConfigManager.getExtension().length());
      return new GuiConfig(var2);
   }

   public void update() {
      DiscordGUI var1 = Novoline.getInstance().getDiscordGUI();
      this.y = var1.getYCoordinate() + this.offset + var1.getConfigs().indexOf(this) * 20;
   }

   public void drawScreen(int var1, int var2) {
      ConfigMenu.b();
      DiscordGUI var4 = Novoline.getInstance().getDiscordGUI();
      int var5 = var4.getXCoordinate();
      int var6 = var4.getWidth();
      if(this.isHovered(var1, var2)) {
         Gui.drawRect(var5 + 45 + 110, this.y - 6, var5 + 45 + 105 + var6, this.y + Fonts$SFTHIN$SFTHIN_17.SFTHIN_17.getHeight() + 5, -13684426);
      }

      Fonts$SFTHIN$SFTHIN_17.SFTHIN_17.drawCenteredString(this.name.replace(".txt", ""), (float)(var5 + 45 + 110) + (float)var6 / 2.0F, (float)this.y, this.selected?-1:-7961722);
      if(acE.b() == null) {
         ConfigMenu.b(new acE[4]);
      }

   }

   public boolean isHovered(int var1, int var2) {
      ConfigMenu.b();
      DiscordGUI var4 = Novoline.getInstance().getDiscordGUI();
      int var5 = var4.getXCoordinate();
      int var6 = var4.getWidth();
      return var1 >= var5 + 45 + 110 && var1 <= var5 + 45 + 110 + var6 && var2 >= this.y - 6 && var2 <= this.y + Fonts$SFTHIN$SFTHIN_17.SFTHIN_17.getHeight() + 5;
   }

   public boolean isOutsideOfMenu() {
      DiscordGUI var2 = Novoline.getInstance().getDiscordGUI();
      ConfigMenu.b();
      int var3 = var2.getYCoordinate();
      int var4 = var3 + var2.getHeight();
      return this.y < var3 + 30 || this.y > var4 - 9;
   }

   public boolean isSelected() {
      return this.selected;
   }

   public void setSelected(boolean var1) {
      this.selected = var1;
   }

   public int getOffset() {
      return this.offset;
   }

   public void setOffset(int var1) {
      this.offset = var1;
   }

   public String getName() {
      return this.name;
   }

   public int getY() {
      return this.y;
   }
}
