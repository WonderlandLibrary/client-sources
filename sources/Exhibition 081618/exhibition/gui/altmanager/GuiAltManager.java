package exhibition.gui.altmanager;

import exhibition.Client;
import exhibition.gui.generators.gui.AltGeneratorGUI;
import exhibition.util.RenderingUtil;
import exhibition.util.render.Colors;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiAltManager extends GuiScreen {
   private GuiButton login;
   private GuiButton remove;
   private GuiButton rename;
   private AltLoginThread loginThread;
   private int offset;
   public Alt selectedAlt = null;
   private String status;
   private GuiTextField seatchField;

   public GuiAltManager() {
      this.status = EnumChatFormatting.GRAY + "Idle...";
   }

   public void actionPerformed(GuiButton button) {
      switch(button.id) {
      case 0:
         this.mc.displayGuiScreen(new AltGeneratorGUI(this));
         break;
      case 1:
         (this.loginThread = new AltLoginThread(this.selectedAlt)).start();
         break;
      case 2:
         if (this.loginThread != null) {
            this.loginThread = null;
         }

         AltManager.registry.remove(this.selectedAlt);
         this.status = "§aRemoved.";

         try {
            Client.getFileManager().getFile(Alts.class).saveFile();
         } catch (Exception var6) {
            ;
         }

         this.selectedAlt = null;
         break;
      case 3:
         this.mc.displayGuiScreen(new GuiAddAlt(this));
         break;
      case 4:
         this.mc.displayGuiScreen(new GuiAltLogin(this));
         break;
      case 5:
         ArrayList registry = AltManager.registry;
         Random random = new Random();
         Alt randomAlt = (Alt)registry.get(random.nextInt(AltManager.registry.size()));
         (this.loginThread = new AltLoginThread(randomAlt)).start();
         break;
      case 6:
         this.mc.displayGuiScreen(new GuiRenameAlt(this));
         break;
      case 7:
         this.mc.displayGuiScreen((GuiScreen)null);
         break;
      case 8:
         try {
            AltManager.registry.clear();
            Client.getFileManager().getFile(Alts.class).loadFile();
         } catch (IOException var5) {
            var5.printStackTrace();
         }

         this.status = "§bReloaded!";
      }

   }

   public void drawScreen(int par1, int par2, float par3) {
      RenderingUtil.rectangle(0.0D, 0.0D, (double)this.width, (double)this.height, Colors.getColor(0, 255));
      if (Mouse.hasWheel()) {
         int wheel = Mouse.getDWheel();
         if (wheel < 0) {
            this.offset += 26;
            if (this.offset < 0) {
               this.offset = 0;
            }
         } else if (wheel > 0) {
            this.offset -= 26;
            if (this.offset < 0) {
               this.offset = 0;
            }
         }
      }

      this.drawString(this.fontRendererObj, this.mc.session.getUsername(), 10, 10, 14540253);
      FontRenderer fontRendererObj = this.fontRendererObj;
      StringBuilder sb = new StringBuilder("Account Manager - ");
      sb.append(AltManager.registry.size()).append(" alts").append(" | Banned: " + ((ArrayList) AltManager.registry.stream().filter(o -> ((Alt) o).getStatus().equals(Alt.Status.Banned)).collect(Collectors.toList())).size());
      this.drawCenteredString(fontRendererObj, sb.toString(), this.width / 2, 10, -1);
      this.drawCenteredString(this.fontRendererObj, this.loginThread == null ? this.status : this.loginThread.getStatus(), this.width / 2, 20, -1);
      RenderingUtil.rectangleBordered(50.0D, 33.0D, (double)(this.width - 50), (double)(this.height - 50), 1.0D, Colors.getColor(225, 50), Colors.getColor(160, 150));
      GL11.glPushMatrix();
      this.prepareScissorBox(0.0F, 33.0F, (float)this.width, (float)(this.height - 50));
      GL11.glEnable(3089);
      int y = 38;
      int number = 0;
      Iterator var8 = this.getAlts().iterator();

      while(true) {
         Alt alt;
         do {
            if (!var8.hasNext()) {
               GL11.glDisable(3089);
               GL11.glPopMatrix();
               super.drawScreen(par1, par2, par3);
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
               } else if (Keyboard.isKeyDown(208)) {
                  this.offset += 26;
               }

               if (this.offset < 0) {
                  this.offset = 0;
               }

               this.seatchField.drawTextBox();
               if (this.seatchField.getText().isEmpty() && !this.seatchField.isFocused()) {
                  this.drawString(this.mc.fontRendererObj, "Search Alt", this.width / 2 + 120, this.height - 18, Colors.getColor(180));
               }

               return;
            }

            alt = (Alt)var8.next();
         } while(!this.isAltInArea(y));

         ++number;
         String name;
         if (alt.getMask().equals("")) {
            name = alt.getUsername();
         } else {
            name = alt.getMask();
         }

         if (name.equalsIgnoreCase(this.mc.session.getUsername())) {
            name = "§n" + name;
         }

         String prefix = alt.getStatus().equals(Alt.Status.Banned) ? "§c" : (alt.getStatus().equals(Alt.Status.NotWorking) ? "§m" : "");
         name = prefix + name + "§r §7| " + alt.getStatus().toFormatted();
         String pass;
         if (alt.getPassword().equals("")) {
            pass = "§cCracked";
         } else {
            pass = alt.getPassword().replaceAll(".", "*");
         }

         if (alt != this.selectedAlt) {
            if (this.isMouseOverAlt(par1, par2, y - this.offset) && Mouse.isButtonDown(0)) {
               RenderingUtil.rectangleBordered(52.0D, (double)(y - this.offset - 4), (double)(this.width - 52), (double)(y - this.offset + 20), 1.0D, -Colors.getColor(145, 50), -2146101995);
            } else if (this.isMouseOverAlt(par1, par2, y - this.offset)) {
               RenderingUtil.rectangleBordered(52.0D, (double)(y - this.offset - 4), (double)(this.width - 52), (double)(y - this.offset + 20), 1.0D, Colors.getColor(145, 50), -2145180893);
            }
         } else {
            if (this.isMouseOverAlt(par1, par2, y - this.offset) && Mouse.isButtonDown(0)) {
               RenderingUtil.rectangleBordered(52.0D, (double)(y - this.offset - 4), (double)(this.width - 77), (double)(y - this.offset + 20), 1.0D, Colors.getColor(145, 50), -2142943931);
            } else if (this.isMouseOverAlt(par1, par2, y - this.offset)) {
               RenderingUtil.rectangleBordered(52.0D, (double)(y - this.offset - 4), (double)(this.width - 77), (double)(y - this.offset + 20), 1.0D, Colors.getColor(145, 50), -2142088622);
            } else {
               RenderingUtil.rectangleBordered(52.0D, (double)(y - this.offset - 4), (double)(this.width - 77), (double)(y - this.offset + 20), 1.0D, Colors.getColor(145, 50), -2144259791);
            }

            boolean hovering = par1 >= this.width - 76 && par1 <= this.width - 52 && par2 >= y - this.offset - 4 && par2 <= y - this.offset + 20;
            RenderingUtil.rectangleBordered((double)(this.width - 76), (double)(y - this.offset - 4), (double)(this.width - 52), (double)(y - this.offset + 20), 1.0D, Colors.getColor(80, 255), hovering ? -1 : Colors.getColor(20, 255));
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)(this.width - 74 + 10), (float)(y - this.offset), 0.0F);
            GlStateManager.scale(0.5D, 0.5D, 0.5D);
            this.mc.fontRendererObj.drawStringWithShadow("Change", (float)(0 - fontRendererObj.getStringWidth("Change") / 2), 0.0F, Colors.getColor(230, 255));
            this.mc.fontRendererObj.drawStringWithShadow("Account", (float)(0 - fontRendererObj.getStringWidth("Account") / 2), 12.0F, Colors.getColor(230, 255));
            this.mc.fontRendererObj.drawStringWithShadow("Status", (float)(0 - fontRendererObj.getStringWidth("Status") / 2), 24.0F, Colors.getColor(230, 255));
            GlStateManager.popMatrix();
         }

         String numberP = "§7" + number + ". §f";
         this.drawCenteredString(this.fontRendererObj, numberP + name, this.width / 2, y - this.offset, -1);
         this.drawCenteredString(this.fontRendererObj, (alt.getStatus().equals(Alt.Status.NotWorking) ? "§m" : "") + pass, this.width / 2, y - this.offset + 10, Colors.getColor(110));
         y += 26;
      }
   }

   public void initGui() {
      this.seatchField = new GuiTextField(this.eventButton, this.mc.fontRendererObj, this.width / 2 + 116, this.height - 22, 72, 16);
      this.buttonList.add(new GuiButton(0, this.width / 2 + 105, 5, 60, 20, "Alt-Gen"));
      this.buttonList.add(this.login = new GuiButton(1, this.width / 2 - 122, this.height - 48, 100, 20, "Login"));
      this.buttonList.add(this.remove = new GuiButton(2, this.width / 2 - 40, this.height - 24, 70, 20, "Remove"));
      this.buttonList.add(new GuiButton(3, this.width / 2 + 4 + 86, this.height - 48, 100, 20, "Add"));
      this.buttonList.add(new GuiButton(4, this.width / 2 - 16, this.height - 48, 100, 20, "Direct Login"));
      this.buttonList.add(new GuiButton(5, this.width / 2 - 122, this.height - 24, 78, 20, "Random"));
      this.buttonList.add(this.rename = new GuiButton(6, this.width / 2 + 38, this.height - 24, 70, 20, "Edit"));
      this.buttonList.add(new GuiButton(7, this.width / 2 - 190, this.height - 24, 60, 20, "Back"));
      this.buttonList.add(new GuiButton(8, this.width / 2 - 190, this.height - 48, 60, 20, "Reload"));
      this.login.enabled = false;
      this.remove.enabled = false;
      this.rename.enabled = false;
   }

   protected void keyTyped(char par1, int par2) {
      this.seatchField.textboxKeyTyped(par1, par2);
      if ((par1 == '\t' || par1 == '\r') && this.seatchField.isFocused()) {
         this.seatchField.setFocused(!this.seatchField.isFocused());
      }

      try {
         super.keyTyped(par1, par2);
      } catch (IOException var4) {
         var4.printStackTrace();
      }

   }

   private boolean isAltInArea(int y) {
      return y - this.offset <= this.height - 50;
   }

   private boolean isMouseOverAlt(int x, int y, int y1) {
      return x >= 52 && y >= y1 - 4 && x <= this.width - 77 && y <= y1 + 20 && x >= 0 && y >= 33 && x <= this.width && y <= this.height - 50;
   }

   protected void mouseClicked(int par1, int par2, int par3) {
      this.seatchField.mouseClicked(par1, par2, par3);
      if (this.offset < 0) {
         this.offset = 0;
      }

      int y = 38 - this.offset;

      for(Iterator var5 = this.getAlts().iterator(); var5.hasNext(); y += 26) {
         Alt alt = (Alt)var5.next();
         if (this.isMouseOverAlt(par1, par2, y)) {
            if (alt == this.selectedAlt) {
               this.actionPerformed((GuiButton)this.buttonList.get(1));
               return;
            }

            this.selectedAlt = alt;
         }

         boolean hovering = par1 >= this.width - 76 && par1 <= this.width - 52 && par2 >= y - 4 && par2 <= y + 20;
         if (hovering && alt == this.selectedAlt) {
            switch(alt.getStatus()) {
            case Unchecked:
               alt.setStatus(Alt.Status.Working);
               break;
            case Working:
               alt.setStatus(Alt.Status.Banned);
               break;
            case Banned:
               alt.setStatus(Alt.Status.NotWorking);
               break;
            case NotWorking:
               alt.setStatus(Alt.Status.Unchecked);
            }

            try {
               Client.getFileManager().getFile(Alts.class).saveFile();
            } catch (IOException var10) {
               var10.printStackTrace();
            }
         }
      }

      try {
         super.mouseClicked(par1, par2, par3);
      } catch (IOException var9) {
         var9.printStackTrace();
      }

   }

   private List getAlts() {
      List altList = new ArrayList();
      Iterator var2 = AltManager.registry.iterator();

      while(true) {
         Alt alt;
         do {
            if (!var2.hasNext()) {
               return altList;
            }

            alt = (Alt)var2.next();
         } while(!this.seatchField.getText().isEmpty() && !alt.getMask().toLowerCase().contains(this.seatchField.getText().toLowerCase()) && !alt.getUsername().toLowerCase().contains(this.seatchField.getText().toLowerCase()));

         altList.add(alt);
      }
   }

   private void prepareScissorBox(float x, float y, float x2, float y2) {
      ScaledResolution scale = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
      int factor = scale.getScaleFactor();
      GL11.glScissor((int)(x * (float)factor), (int)(((float)scale.getScaledHeight() - y2) * (float)factor), (int)((x2 - x) * (float)factor), (int)((y2 - y) * (float)factor));
   }
}
