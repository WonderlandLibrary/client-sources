package net.minecraft.client.gui;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.SaveFormatComparator;
import net.minecraft.world.storage.WorldInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiSelectWorld extends GuiScreen implements GuiYesNoCallback {
   protected GuiScreen field_146632_a;
   private static final String __OBFID = "CL_00000711";
   private boolean field_146634_i;
   private static final Logger logger = LogManager.getLogger();
   private String[] field_146635_w = new String[4];
   private java.util.List field_146639_s;
   private GuiButton field_146630_A;
   private final DateFormat field_146633_h = new SimpleDateFormat();
   private GuiButton field_146641_z;
   private String field_146637_u;
   protected String field_146628_f = "Select world";
   private boolean field_146643_x;
   private GuiButton field_146642_y;
   private String field_146636_v;
   private GuiButton field_146631_B;
   private int field_146640_r;
   private GuiSelectWorld.List field_146638_t;

   public static GuiYesNo func_152129_a(GuiYesNoCallback var0, String var1, int var2) {
      String var3 = I18n.format("selectWorld.deleteQuestion");
      String var4 = String.valueOf((new StringBuilder("'")).append(var1).append("' ").append(I18n.format("selectWorld.deleteWarning")));
      String var5 = I18n.format("selectWorld.deleteButton");
      String var6 = I18n.format("gui.cancel");
      GuiYesNo var7 = new GuiYesNo(var0, var3, var4, var5, var6, var2);
      return var7;
   }

   static GuiButton access$6(GuiSelectWorld var0) {
      return var0.field_146631_B;
   }

   static java.util.List access$0(GuiSelectWorld var0) {
      return var0.field_146639_s;
   }

   static GuiButton access$5(GuiSelectWorld var0) {
      return var0.field_146630_A;
   }

   private void func_146627_h() throws AnvilConverterException {
      ISaveFormat var1 = this.mc.getSaveLoader();
      this.field_146639_s = var1.getSaveList();
      Collections.sort(this.field_146639_s);
      this.field_146640_r = -1;
   }

   static int access$2(GuiSelectWorld var0) {
      return var0.field_146640_r;
   }

   static String access$7(GuiSelectWorld var0) {
      return var0.field_146637_u;
   }

   static void access$1(GuiSelectWorld var0, int var1) {
      var0.field_146640_r = var1;
   }

   static String[] access$10(GuiSelectWorld var0) {
      return var0.field_146635_w;
   }

   public GuiSelectWorld(GuiScreen var1) {
      this.field_146632_a = var1;
   }

   static String access$9(GuiSelectWorld var0) {
      return var0.field_146636_v;
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      if (var1.enabled) {
         if (var1.id == 2) {
            String var2 = this.func_146614_d(this.field_146640_r);
            if (var2 != null) {
               this.field_146643_x = true;
               GuiYesNo var3 = func_152129_a(this, var2, this.field_146640_r);
               this.mc.displayGuiScreen(var3);
            }
         } else if (var1.id == 1) {
            this.func_146615_e(this.field_146640_r);
         } else if (var1.id == 3) {
            this.mc.displayGuiScreen(new GuiCreateWorld(this));
         } else if (var1.id == 6) {
            this.mc.displayGuiScreen(new GuiRenameWorld(this, this.func_146621_a(this.field_146640_r)));
         } else if (var1.id == 0) {
            this.mc.displayGuiScreen(this.field_146632_a);
         } else if (var1.id == 7) {
            GuiCreateWorld var5 = new GuiCreateWorld(this);
            ISaveHandler var6 = this.mc.getSaveLoader().getSaveLoader(this.func_146621_a(this.field_146640_r), false);
            WorldInfo var4 = var6.loadWorldInfo();
            var6.flush();
            var5.func_146318_a(var4);
            this.mc.displayGuiScreen(var5);
         } else {
            this.field_146638_t.actionPerformed(var1);
         }
      }

   }

   static DateFormat access$8(GuiSelectWorld var0) {
      return var0.field_146633_h;
   }

   public void func_146618_g() {
      this.buttonList.add(this.field_146641_z = new GuiButton(1, this.width / 2 - 154, this.height - 52, 150, 20, I18n.format("selectWorld.select")));
      this.buttonList.add(new GuiButton(3, this.width / 2 + 4, this.height - 52, 150, 20, I18n.format("selectWorld.create")));
      this.buttonList.add(this.field_146630_A = new GuiButton(6, this.width / 2 - 154, this.height - 28, 72, 20, I18n.format("selectWorld.rename")));
      this.buttonList.add(this.field_146642_y = new GuiButton(2, this.width / 2 - 76, this.height - 28, 72, 20, I18n.format("selectWorld.delete")));
      this.buttonList.add(this.field_146631_B = new GuiButton(7, this.width / 2 + 4, this.height - 28, 72, 20, I18n.format("selectWorld.recreate")));
      this.buttonList.add(new GuiButton(0, this.width / 2 + 82, this.height - 28, 72, 20, I18n.format("gui.cancel")));
      this.field_146641_z.enabled = false;
      this.field_146642_y.enabled = false;
      this.field_146630_A.enabled = false;
      this.field_146631_B.enabled = false;
   }

   public void initGui() {
      this.field_146628_f = I18n.format("selectWorld.title");

      try {
         this.func_146627_h();
      } catch (AnvilConverterException var2) {
         logger.error("Couldn't load level list", var2);
         this.mc.displayGuiScreen(new GuiErrorScreen("Unable to load worlds", var2.getMessage()));
         return;
      }

      this.field_146637_u = I18n.format("selectWorld.world");
      this.field_146636_v = I18n.format("selectWorld.conversion");
      this.field_146635_w[WorldSettings.GameType.SURVIVAL.getID()] = I18n.format("gameMode.survival");
      this.field_146635_w[WorldSettings.GameType.CREATIVE.getID()] = I18n.format("gameMode.creative");
      this.field_146635_w[WorldSettings.GameType.ADVENTURE.getID()] = I18n.format("gameMode.adventure");
      this.field_146635_w[WorldSettings.GameType.SPECTATOR.getID()] = I18n.format("gameMode.spectator");
      this.field_146638_t = new GuiSelectWorld.List(this, this.mc);
      this.field_146638_t.registerScrollButtons(4, 5);
      this.func_146618_g();
   }

   protected String func_146614_d(int var1) {
      String var2 = ((SaveFormatComparator)this.field_146639_s.get(var1)).getDisplayName();
      if (StringUtils.isEmpty(var2)) {
         var2 = String.valueOf((new StringBuilder(String.valueOf(I18n.format("selectWorld.world")))).append(" ").append(var1 + 1));
      }

      return var2;
   }

   public void confirmClicked(boolean var1, int var2) {
      if (this.field_146643_x) {
         this.field_146643_x = false;
         if (var1) {
            ISaveFormat var3 = this.mc.getSaveLoader();
            var3.flushCache();
            var3.deleteWorldDirectory(this.func_146621_a(var2));

            try {
               this.func_146627_h();
            } catch (AnvilConverterException var5) {
               logger.error("Couldn't load level list", var5);
            }
         }

         this.mc.displayGuiScreen(this);
      }

   }

   static GuiButton access$3(GuiSelectWorld var0) {
      return var0.field_146641_z;
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.field_146638_t.drawScreen(var1, var2, var3);
      this.drawCenteredString(this.fontRendererObj, this.field_146628_f, this.width / 2, 20, 16777215);
      super.drawScreen(var1, var2, var3);
   }

   static GuiButton access$4(GuiSelectWorld var0) {
      return var0.field_146642_y;
   }

   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      this.field_146638_t.func_178039_p();
   }

   public void func_146615_e(int var1) {
      this.mc.displayGuiScreen((GuiScreen)null);
      if (!this.field_146634_i) {
         this.field_146634_i = true;
         String var2 = this.func_146621_a(var1);
         if (var2 == null) {
            var2 = String.valueOf((new StringBuilder("World")).append(var1));
         }

         String var3 = this.func_146614_d(var1);
         if (var3 == null) {
            var3 = String.valueOf((new StringBuilder("World")).append(var1));
         }

         if (this.mc.getSaveLoader().canLoadWorld(var2)) {
            this.mc.launchIntegratedServer(var2, var3, (WorldSettings)null);
         }
      }

   }

   protected String func_146621_a(int var1) {
      return ((SaveFormatComparator)this.field_146639_s.get(var1)).getFileName();
   }

   class List extends GuiSlot {
      final GuiSelectWorld this$0;
      private static final String __OBFID = "CL_00000712";

      protected int getContentHeight() {
         return GuiSelectWorld.access$0(this.this$0).size() * 36;
      }

      protected void drawBackground() {
         this.this$0.drawDefaultBackground();
      }

      public List(GuiSelectWorld var1, Minecraft var2) {
         super(var2, var1.width, var1.height, 32, var1.height - 64, 36);
         this.this$0 = var1;
      }

      protected void drawSlot(int var1, int var2, int var3, int var4, int var5, int var6) {
         SaveFormatComparator var7 = (SaveFormatComparator)GuiSelectWorld.access$0(this.this$0).get(var1);
         String var8 = var7.getDisplayName();
         if (StringUtils.isEmpty(var8)) {
            var8 = String.valueOf((new StringBuilder(String.valueOf(GuiSelectWorld.access$7(this.this$0)))).append(" ").append(var1 + 1));
         }

         String var9 = var7.getFileName();
         var9 = String.valueOf((new StringBuilder(String.valueOf(var9))).append(" (").append(GuiSelectWorld.access$8(this.this$0).format(new Date(var7.getLastTimePlayed()))));
         var9 = String.valueOf((new StringBuilder(String.valueOf(var9))).append(")"));
         String var10 = "";
         if (var7.requiresConversion()) {
            var10 = String.valueOf((new StringBuilder(String.valueOf(GuiSelectWorld.access$9(this.this$0)))).append(" ").append(var10));
         } else {
            var10 = GuiSelectWorld.access$10(this.this$0)[var7.getEnumGameType().getID()];
            if (var7.isHardcoreModeEnabled()) {
               var10 = String.valueOf((new StringBuilder()).append(EnumChatFormatting.DARK_RED).append(I18n.format("gameMode.hardcore")).append(EnumChatFormatting.RESET));
            }

            if (var7.getCheatsEnabled()) {
               var10 = String.valueOf((new StringBuilder(String.valueOf(var10))).append(", ").append(I18n.format("selectWorld.cheats")));
            }
         }

         this.this$0.drawString(this.this$0.fontRendererObj, var8, var2 + 2, var3 + 1, 16777215);
         this.this$0.drawString(this.this$0.fontRendererObj, var9, var2 + 2, var3 + 12, 8421504);
         this.this$0.drawString(this.this$0.fontRendererObj, var10, var2 + 2, var3 + 12 + 10, 8421504);
      }

      protected void elementClicked(int var1, boolean var2, int var3, int var4) {
         GuiSelectWorld.access$1(this.this$0, var1);
         boolean var5 = GuiSelectWorld.access$2(this.this$0) >= 0 && GuiSelectWorld.access$2(this.this$0) < this.getSize();
         GuiSelectWorld.access$3(this.this$0).enabled = var5;
         GuiSelectWorld.access$4(this.this$0).enabled = var5;
         GuiSelectWorld.access$5(this.this$0).enabled = var5;
         GuiSelectWorld.access$6(this.this$0).enabled = var5;
         if (var2 && var5) {
            this.this$0.func_146615_e(var1);
         }

      }

      protected int getSize() {
         return GuiSelectWorld.access$0(this.this$0).size();
      }

      protected boolean isSelected(int var1) {
         return var1 == GuiSelectWorld.access$2(this.this$0);
      }
   }
}
