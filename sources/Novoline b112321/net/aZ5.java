package net;

import cc.novoline.utils.fonts.impl.Fonts$SF$SF_18;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import net.aHM;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer$EnumChatVisibility;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class aZ5 extends Gui {
   private static final Logger k = LogManager.getLogger();
   private final Minecraft j;
   private final List h = Lists.newArrayList();
   private final List i = Lists.newArrayList();
   private final List f = Lists.newArrayList();
   private int g;
   private boolean l;

   public aZ5(Minecraft var1) {
      this.j = var1;
   }

   public void c(int var1) {
      if(this.j.gameSettings.chatVisibility != EntityPlayer$EnumChatVisibility.HIDDEN) {
         int var2 = this.c();
         boolean var3 = false;
         boolean var4 = false;
         int var5 = this.f.size();
         float var6 = this.j.gameSettings.chatOpacity * 0.9F + 0.1F;
      }
   }

   public List a() {
      return this.i;
   }

   public List o() {
      return this.f;
   }

   public void m() {
      this.f.clear();
      this.i.clear();
      this.h.clear();
   }

   public void a(IChatComponent var1) {
      this.b(var1, 0);
   }

   public void b(@NotNull IChatComponent var1, int var2) {
      this.a(var1, var2, this.j.ingameGUI.getUpdateCounter(), false);
      k.info("[CHAT] " + var1.getUnformattedText());
   }

   private void a(IChatComponent var1, int var2, int var3, boolean var4) {
      this.b(var2);
      int var5 = MathHelper.floor_float((float)this.l() / this.k());
      List var6 = this.a(var1, var5);
      boolean var7 = this.d();

      for(IChatComponent var9 : var6) {
         if(this.g > 0) {
            this.l = true;
            this.a(1);
         }

         this.f.add(0, new ChatLine(var3, var9, var2));
      }

      while(this.f.size() > 1000) {
         this.f.remove(this.f.size() - 1);
      }

      this.i.add(0, new ChatLine(var3, var1, var2));

      while(this.i.size() > 1000) {
         this.i.remove(this.i.size() - 1);
      }

   }

   public void f() {
      this.f.clear();
      this.e();
      int var1 = this.i.size() - 1;

      while(true) {
         ChatLine var2 = (ChatLine)this.i.get(var1);
         this.a(var2.getChatComponent(), var2.getChatLineID(), var2.getUpdatedCounter(), true);
         --var1;
      }
   }

   public List h() {
      return this.h;
   }

   public void a(String var1) {
      if(this.h.isEmpty() || !((String)this.h.get(this.h.size() - 1)).equals(var1)) {
         this.h.add(var1);
      }

   }

   public void e() {
      this.g = 0;
      this.l = false;
   }

   public void a(int var1) {
      this.g += var1;
      int var2 = this.f.size();
      if(this.g > var2 - this.c()) {
         this.g = var2 - this.c();
      }

      if(this.g <= 0) {
         this.g = 0;
         this.l = false;
      }

   }

   public IChatComponent a(int var1, int var2) {
      if(!this.d()) {
         return null;
      } else {
         ScaledResolution var3 = new ScaledResolution(this.j);
         int var4 = var3.getScaleFactor();
         float var5 = this.k();
         int var6 = var1 / var4 - 3;
         int var7 = var2 / var4 - 27;
         var6 = MathHelper.floor_float((float)var6 / var5);
         var7 = MathHelper.floor_float((float)var7 / var5);
         return null;
      }
   }

   public boolean d() {
      return this.j.currentScreen instanceof aHM;
   }

   public void b(int var1) {
      Iterator var2 = this.f.iterator();

      while(var2.hasNext()) {
         ChatLine var3 = (ChatLine)var2.next();
         if(var3.getChatLineID() == var1) {
            var2.remove();
         }
      }

      var2 = this.i.iterator();

      while(var2.hasNext()) {
         ChatLine var5 = (ChatLine)var2.next();
         if(var5.getChatLineID() == var1) {
            var2.remove();
            break;
         }
      }

   }

   public int l() {
      return b(this.j.gameSettings.chatWidth);
   }

   public int b() {
      return a(this.d()?this.j.gameSettings.chatHeightFocused:this.j.gameSettings.chatHeightUnfocused);
   }

   public float k() {
      return this.j.gameSettings.chatScale;
   }

   public static int b(float var0) {
      boolean var1 = true;
      boolean var2 = true;
      return MathHelper.floor_float(var0 * 280.0F + 40.0F);
   }

   public static int a(float var0) {
      boolean var1 = true;
      boolean var2 = true;
      return MathHelper.floor_float(var0 * 160.0F + 20.0F);
   }

   public int c() {
      return this.b() / 9;
   }

   private boolean g() {
      return false;
   }

   private List a(IChatComponent var1, int var2) {
      return this.g()?GuiUtilRenderComponents.func_178908_a(var1, var2, this.j(), false, false):GuiUtilRenderComponents.func_178908_a(var1, var2, this.n(), false, false);
   }

   private int i() {
      return this.g()?this.j().getHeight():this.n().getHeight();
   }

   private float b(String var1) {
      return this.g()?(float)this.j().stringWidth(var1):(float)this.n().d(var1);
   }

   private void a(String var1, float var2, float var3, int var4) {
      if(this.g()) {
         this.j().drawString(var1, (double)var2, (double)var3, var4, true);
      } else {
         this.n().drawString(var1, var2, var3, var4, true);
      }

   }

   private FontRenderer n() {
      return Minecraft.getInstance().fontRendererObj;
   }

   private cc.novoline.utils.fonts.api.FontRenderer j() {
      return Fonts$SF$SF_18.SF_18;
   }
}
