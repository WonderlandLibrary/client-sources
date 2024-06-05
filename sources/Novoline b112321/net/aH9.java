package net;

import cc.novoline.Novoline;
import cc.novoline.gui.screen.click.DiscordGUI;
import cc.novoline.gui.screen.click.Scroll;
import cc.novoline.utils.ScaleUtils;
import cc.novoline.utils.notifications.NotificationType;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;
import net.IN;
import net.acE;
import net.afu;
import net.uj;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;

public class aH9 extends GuiScreen {
   private List y = new CopyOnWriteArrayList();
   private List v = new CopyOnWriteArrayList();
   private int x = 1;
   private afu w;

   public void initGui() {
      uj.b();
      Minecraft var2 = Minecraft.getInstance();
      ScaledResolution var3 = new ScaledResolution(var2);
      float var4 = (float)(var3.getScaledWidthStatic(var2) / 2);
      float var5 = (float)(var3.getScaledHeightStatic(var2) / 2);
      if(!Files.exists(Paths.get(Novoline.getInstance().getPathString() + "item_preferences.txt", new String[0]), new LinkOption[0])) {
         try {
            Files.createFile(Paths.get(Novoline.getInstance().getPathString() + "item_preferences.txt", new String[0]), new FileAttribute[0]);
         } catch (IOException var8) {
            var8.printStackTrace();
         }
      }

      this.y.clear();
      Iterator var6 = this.b().iterator();
      if(var6.hasNext()) {
         String var7 = (String)var6.next();
         this.y.add(Integer.valueOf(Integer.parseInt(var7)));
      }

      this.v.clear();
      this.x = 1;
      var6 = Item.itemRegistry.iterator();
      if(var6.hasNext()) {
         Item var10 = (Item)var6.next();
         this.v.add(new uj(this, var10));
      }

      this.w = new afu(this, 0, Minecraft.getInstance().fontRendererCrack, (int)(var4 + 20.0F), (int)(var5 - 109.0F + 2.0F), 78, 20);
      Novoline.getInstance().getNotificationManager().pop("The changes would apply only after you re-enable the module!", 5000, NotificationType.INFO);
      super.initGui();
      if(acE.b() == null) {
         uj.b(new int[3]);
      }

   }

   public void drawScreen(int var1, int var2, float var3) {
      uj.b();
      Minecraft var5 = Minecraft.getInstance();
      ScaleUtils.scale(var5);
      ScaledResolution var6 = new ScaledResolution(var5);
      float var7 = (float)(var6.getScaledWidthStatic(var5) / 2);
      float var8 = (float)(var6.getScaledHeightStatic(var5) / 2);
      float var9 = var7 - 100.0F;
      float var10 = var8 - 90.0F;
      Scroll var11 = DiscordGUI.scroll();
      switch(IN.a[var11.ordinal()]) {
      case 1:
         if(this.x - 10 <= 0) {
            break;
         }

         this.x -= 10;
      case 2:
         if(this.x + 90 <= this.v.size()) {
            this.x += 10;
         }
      }

      Gui.drawRect((double)(var7 - 100.0F) - 0.5D, (double)var8 - 104.0D, (double)(var7 + 100.0F) + 0.5D, (double)(var8 - 90.0F), (new Color(29, 29, 29, 255)).getRGB());
      Minecraft.getInstance().fontRendererCrack.drawString("Item Whitelist", var7 - 100.0F + 2.0F, var8 - 104.0F + 3.0F, -1, true);
      int var12 = this.x;
      if(var12 < MathHelper.clamp_int(this.x + 90, this.x, this.v.size() + 1)) {
         uj var13 = (uj)this.v.get(var12 - 1);
         int var14 = ScaleUtils.getScaledMouseCoordinates(this.mc, var1, var2)[0];
         int var15 = ScaleUtils.getScaledMouseCoordinates(this.mc, var1, var2)[1];
         var13.a((float)var14, (float)var15, var9, var10);
         var9 = var9 + 20.0F;
         if(var12 % 10 == 0) {
            var9 = var7 - 100.0F;
            var10 = var10 + 20.0F;
         }

         ++var12;
      }

      this.w.drawTextBox();
      super.drawScreen(var1, var2, var3);
   }

   protected void keyTyped(char var1, int var2) throws IOException {
      uj.b();
      this.w.textboxKeyTyped(var1, var2);
      if(!this.w.getText().isEmpty()) {
         this.v.clear();
         this.x = 1;
         Iterator var4 = Item.itemRegistry.iterator();
         if(var4.hasNext()) {
            Item var5 = (Item)var4.next();
            ItemStack var6 = new ItemStack(var5);
            List var7 = var6.getTooltip(this.mc.player, this.mc.gameSettings.advancedItemTooltips);
            String var8 = this.w.getText().toLowerCase();
            boolean var9 = false;
            Iterator var10 = var7.iterator();
            if(var10.hasNext()) {
               String var11 = (String)var10.next();
               String var12 = EnumChatFormatting.a(var11).toLowerCase();
               if(var12.contains(var8)) {
                  var9 = true;
               }
            }

            if(var9) {
               this.v.add(new uj(this, var5));
            }
         }
      }

      this.v.clear();
      this.x = 1;
      Iterator var13 = Item.itemRegistry.iterator();
      if(var13.hasNext()) {
         Item var14 = (Item)var13.next();
         this.v.add(new uj(this, var14));
      }

      super.keyTyped(var1, var2);
   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      uj.b();
      int var5 = ScaleUtils.getScaledMouseCoordinates(this.mc, var1, var2)[0];
      int var6 = ScaleUtils.getScaledMouseCoordinates(this.mc, var1, var2)[1];
      this.w.mouseClicked(var5, var6, var3);
      Iterator var7 = this.v.iterator();
      if(var7.hasNext()) {
         uj var8 = (uj)var7.next();
         var8.a((float)var5, (float)var6, var3);
      }

      this.a();
      super.mouseClicked(var5, var6, var3);
   }

   public List b() {
      uj.b();
      CopyOnWriteArrayList var2 = new CopyOnWriteArrayList();
      Scanner var3 = null;

      try {
         var3 = new Scanner(new File(Novoline.getInstance().getPathString() + "item_preferences.txt"));
      } catch (FileNotFoundException var5) {
         var5.printStackTrace();
      }

      if(var3 != null && var3.hasNext()) {
         var2.add(var3.nextLine());
      }

      return var2;
   }

   public void a() throws IOException {
      List var2 = this.b();
      FileWriter var3 = new FileWriter(Novoline.getInstance().getPathString() + "item_preferences.txt");
      uj.b();
      var3.write("");
      var3.flush();
      var3.close();
      FileWriter var4 = new FileWriter(Novoline.getInstance().getPathString() + "item_preferences.txt");
      BufferedWriter var5 = new BufferedWriter(var4);
      Iterator var6 = this.y.iterator();
      if(var6.hasNext()) {
         Integer var7 = (Integer)var6.next();
         var5.write(var7 + "\n");
      }

      var5.close();
   }

   static List f(aH9 var0) {
      return var0.y;
   }

   static Minecraft a(aH9 var0) {
      return var0.mc;
   }

   static FontRenderer e(aH9 var0) {
      return var0.fontRendererObj;
   }

   static FontRenderer b(aH9 var0) {
      return var0.fontRendererObj;
   }

   static FontRenderer d(aH9 var0) {
      return var0.fontRendererObj;
   }

   static FontRenderer g(aH9 var0) {
      return var0.fontRendererObj;
   }

   static FontRenderer h(aH9 var0) {
      return var0.fontRendererObj;
   }

   static FontRenderer c(aH9 var0) {
      return var0.fontRendererObj;
   }

   static FontRenderer i(aH9 var0) {
      return var0.fontRendererObj;
   }

   static FontRenderer j(aH9 var0) {
      return var0.fontRendererObj;
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
