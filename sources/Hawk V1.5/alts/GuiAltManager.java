package alts;

import com.thealtening.auth.TheAlteningAuthentication;
import com.thealtening.auth.service.AlteningServiceType;
import java.io.IOException;
import java.util.Iterator;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiAltManager extends GuiScreen {
   private int offset;
   private AltLoginThread loginThread;
   public Alt selectedAlt = null;
   private String status;
   private GuiButton rename;
   private GuiButton remove;
   private TheAlteningAuthentication serviceSwitcher;
   private GuiButton login;

   private boolean isAltInArea(int var1) {
      return var1 - this.offset <= this.height - 50;
   }

   public GuiAltManager() {
      this.status = String.valueOf((new StringBuilder()).append(EnumChatFormatting.GRAY).append("No alts selected"));
      this.serviceSwitcher = TheAlteningAuthentication.mojang();
   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      if (this.offset < 0) {
         this.offset = 0;
      }

      int var4 = 38 - this.offset;

      for(Iterator var6 = AltManager.registry.iterator(); var6.hasNext(); var4 += 26) {
         Alt var5 = (Alt)var6.next();
         if (this.isMouseOverAlt(var1, var2, var4)) {
            if (var5 == this.selectedAlt) {
               this.actionPerformed((GuiButton)this.buttonList.get(1));
               return;
            }

            this.selectedAlt = var5;
         }
      }

      try {
         super.mouseClicked(var1, var2, var3);
      } catch (IOException var7) {
         var7.printStackTrace();
      }

   }

   public void actionPerformed(GuiButton var1) throws IOException {
      switch(var1.id) {
      case 0:
         if (this.loginThread == null) {
            this.mc.displayGuiScreen((GuiScreen)null);
         } else if (!this.loginThread.getStatus().equals(String.valueOf((new StringBuilder()).append(EnumChatFormatting.YELLOW).append("Attempting to log in"))) && !this.loginThread.getStatus().equals(String.valueOf((new StringBuilder()).append(EnumChatFormatting.RED).append("Do not hit back!").append(EnumChatFormatting.YELLOW).append(" Logging in...")))) {
            this.mc.displayGuiScreen((GuiScreen)null);
         } else {
            this.loginThread.setStatus(String.valueOf((new StringBuilder()).append(EnumChatFormatting.RED).append("Failed to login! Please try again!").append(EnumChatFormatting.YELLOW).append(" Logging in...")));
         }
         break;
      case 1:
         String var2 = this.selectedAlt.getUsername();
         String var3 = this.selectedAlt.getPassword();
         this.loginThread = new AltLoginThread(var2, var3);
         this.loginThread.start();
         break;
      case 2:
         if (this.loginThread != null) {
            this.loginThread = null;
         }

         AltManager.registry.remove(this.selectedAlt);
         this.status = "§aRemoved.";
         this.selectedAlt = null;
         break;
      case 3:
         this.mc.displayGuiScreen(new GuiAddAlt(this));
         break;
      case 4:
         this.mc.displayGuiScreen(new GuiAltLogin(this));
      case 5:
      default:
         break;
      case 6:
         this.mc.displayGuiScreen(new GuiRenameAlt(this));
         break;
      case 7:
         this.serviceSwitcher.updateService(AlteningServiceType.MOJANG);
         break;
      case 8:
         this.serviceSwitcher.updateService(AlteningServiceType.THEALTENING);
      }

   }

   public void drawScreen(int var1, int var2, float var3) {
      if (Mouse.hasWheel()) {
         int var4 = Mouse.getDWheel();
         if (var4 < 0) {
            this.offset += 26;
            if (this.offset < 0) {
               this.offset = 0;
            }
         } else if (var4 > 0) {
            this.offset -= 26;
            if (this.offset < 0) {
               this.offset = 0;
            }
         }
      }

      this.drawDefaultBackground();
      this.drawString(this.fontRendererObj, this.mc.session.getUsername(), 10, 10, -7829368);
      FontRenderer var11 = this.fontRendererObj;
      StringBuilder var5 = new StringBuilder("Account Manager - ");
      this.drawCenteredString(var11, String.valueOf(var5.append(AltManager.registry.size()).append(" alts")), this.width / 2, 10, -1);
      this.drawCenteredString(this.fontRendererObj, this.loginThread == null ? this.status : this.loginThread.getStatus(), this.width / 2, 20, -1);
      Gui.drawRect(50.0D, 33.0D, (double)(this.width - 50), (double)(this.height - 50), -16777216);
      GL11.glPushMatrix();
      this.prepareScissorBox(0.0F, 33.0F, (float)this.width, (float)(this.height - 50));
      GL11.glEnable(3089);
      int var6 = 38;
      Iterator var8 = AltManager.registry.iterator();

      while(true) {
         Alt var7;
         do {
            if (!var8.hasNext()) {
               GL11.glDisable(3089);
               GL11.glPopMatrix();
               super.drawScreen(var1, var2, var3);
               if (this.selectedAlt == null) {
                  this.login.enabled = false;
                  this.remove.enabled = false;
                  this.rename.enabled = false;
               } else {
                  this.login.enabled = true;
                  this.remove.enabled = true;
                  this.rename.enabled = true;
               }

               if (Keyboard.isKeyDown(200)) {
                  this.offset -= 26;
                  if (this.offset < 0) {
                     this.offset = 0;
                  }
               } else if (Keyboard.isKeyDown(208)) {
                  this.offset += 26;
                  if (this.offset < 0) {
                     this.offset = 0;
                  }
               }

               return;
            }

            var7 = (Alt)var8.next();
         } while(!this.isAltInArea(var6));

         String var9 = var7.getMask().equals("") ? var7.getUsername() : var7.getMask();
         String var10 = var7.getPassword().equals("") ? "§cCracked" : var7.getPassword().replaceAll(".", "*");
         if (var7 == this.selectedAlt) {
            if (this.isMouseOverAlt(var1, var2, var6 - this.offset) && Mouse.isButtonDown(0)) {
               Gui.drawRect(52.0D, (double)(var6 - this.offset - 4), (double)(this.width - 52), (double)(var6 - this.offset + 20), -2142943931);
            } else if (this.isMouseOverAlt(var1, var2, var6 - this.offset)) {
               Gui.drawRect(52.0D, (double)(var6 - this.offset - 4), (double)(this.width - 52), (double)(var6 - this.offset + 20), -2142088622);
            } else {
               Gui.drawRect(52.0D, (double)(var6 - this.offset - 4), (double)(this.width - 52), (double)(var6 - this.offset + 20), -2144259791);
            }
         } else if (this.isMouseOverAlt(var1, var2, var6 - this.offset) && Mouse.isButtonDown(0)) {
            Gui.drawRect(52.0D, (double)(var6 - this.offset - 4), (double)(this.width - 52), (double)(var6 - this.offset + 20), -16777216);
         } else if (this.isMouseOverAlt(var1, var2, var6 - this.offset)) {
            Gui.drawRect(52.0D, (double)(var6 - this.offset - 4), (double)(this.width - 52), (double)(var6 - this.offset + 20), -16777216);
         }

         this.drawCenteredString(this.fontRendererObj, var9, this.width / 2, var6 - this.offset, -1);
         this.drawCenteredString(this.fontRendererObj, var10, this.width / 2, var6 - this.offset + 10, 5592405);
         var6 += 26;
      }
   }

   public void prepareScissorBox(float var1, float var2, float var3, float var4) {
      ScaledResolution var5 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
      int var6 = var5.getScaleFactor();
      GL11.glScissor((int)(var1 * (float)var6), (int)(((float)var5.getScaledHeight() - var4) * (float)var6), (int)((var3 - var1) * (float)var6), (int)((var4 - var2) * (float)var6));
   }

   private boolean isMouseOverAlt(int var1, int var2, int var3) {
      return var1 >= 52 && var2 >= var3 - 4 && var1 <= this.width - 52 && var2 <= var3 + 20 && var1 >= 0 && var2 >= 33 && var1 <= this.width && var2 <= this.height - 50;
   }

   public void initGui() {
      this.buttonList.add(new GuiButton(0, this.width / 2 + 4 + 50, this.height - 24, 100, 20, "Cancel"));
      this.login = new GuiButton(1, this.width / 2 - 154, this.height - 48, 100, 20, "Login");
      this.buttonList.add(this.login);
      this.remove = new GuiButton(2, this.width / 2 - 154, this.height - 24, 100, 20, "Remove");
      this.buttonList.add(this.remove);
      this.buttonList.add(new GuiButton(3, this.width / 2 + 4 + 50, this.height - 48, 100, 20, "Add"));
      this.buttonList.add(new GuiButton(4, this.width / 2 - 50, this.height - 48, 100, 20, "Direct Login"));
      this.rename = new GuiButton(6, this.width / 2 - 50, this.height - 24, 100, 20, "Edit");
      this.buttonList.add(this.rename);
      this.buttonList.add(new GuiButton(7, this.width - 100, 0, 100, 20, "Use Mojang"));
      this.buttonList.add(new GuiButton(8, this.width - 200, 0, 100, 20, "Use TheAltening"));
      this.login.enabled = false;
      this.remove.enabled = false;
      this.rename.enabled = false;
   }
}
