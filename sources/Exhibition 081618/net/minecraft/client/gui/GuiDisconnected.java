package net.minecraft.client.gui;

import exhibition.Client;
import exhibition.gui.altmanager.Alt;
import exhibition.gui.altmanager.AltLoginThread;
import exhibition.gui.altmanager.AltManager;
import exhibition.gui.altmanager.Alts;
import exhibition.module.impl.other.StreamerMode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.IChatComponent;

public class GuiDisconnected extends GuiScreen {
   private String reason;
   private IChatComponent message;
   private List multilineMessage;
   private final GuiScreen parentScreen;
   private int field_175353_i;

   public GuiDisconnected(GuiScreen p_i45020_1_, String p_i45020_2_, IChatComponent p_i45020_3_) {
      this.parentScreen = p_i45020_1_;
      this.reason = I18n.format(p_i45020_2_);
      this.message = p_i45020_3_;
   }

   protected void keyTyped(char typedChar, int keyCode) throws IOException {
   }

   public void initGui() {
      this.buttonList.clear();
      this.multilineMessage = this.fontRendererObj.listFormattedStringToWidth(this.message.getFormattedText(), this.width - 50);
      this.field_175353_i = this.multilineMessage.size() * this.fontRendererObj.FONT_HEIGHT;
      this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT, I18n.format("gui.toMenu")));
      this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT + 25, 100, 20, "Reconnect"));
      this.buttonList.add(new GuiButton(2, this.width / 2, this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT + 50, 100, 20, "Delete Alt"));
      this.buttonList.add(new GuiButton(3, this.width / 2, this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT + 25, 100, 20, "Random Alt"));
      this.buttonList.add(new GuiButton(4, this.width / 2 + 100, this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT + 25, 100, 20, "Random Unbanned"));
      this.buttonList.add(new GuiButton(5, this.width / 2 + 100, this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT + 50, 100, 20, "Set Banned"));
   }

   protected void actionPerformed(GuiButton button) throws IOException {
      if (button.id == 0) {
         this.mc.displayGuiScreen(this.parentScreen);
      } else if (button.id == 1) {
         try {
            GuiMultiplayer gui = (GuiMultiplayer)this.parentScreen;
            gui.connectToSelected();
         } catch (Exception var11) {
            ;
         }
      } else {
         Alt alt;
         Iterator var12;
         if (button.id == 2) {
            var12 = AltManager.registry.iterator();

            label64: {
               do {
                  if (!var12.hasNext()) {
                     break label64;
                  }

                  alt = (Alt)var12.next();
               } while(!alt.getUsername().equalsIgnoreCase(this.mc.session.getUsername()) && !alt.getMask().equalsIgnoreCase(this.mc.session.getUsername()));

               System.out.println("Removed.");
               AltManager.registry.remove(alt);
            }

            try {
               Client.getFileManager().getFile(Alts.class).saveFile();
            } catch (Exception var10) {
               ;
            }
         } else {
            ArrayList registry;
            Random random;
            if (button.id == 3) {
               registry = AltManager.registry;
               random = new Random();
               Alt randomAlt = (Alt)registry.get(random.nextInt(AltManager.registry.size()));

               try {
                  (new AltLoginThread(randomAlt)).start();
               } catch (Exception var9) {
                  ;
               }
            } else if (button.id == 4) {
               registry = AltManager.registry;
               random = new Random();
               List unbanned = (List)registry.stream().filter((o) -> {
                  return ((Alt) o).getStatus() != Alt.Status.Banned && ((Alt) o).getStatus() != Alt.Status.NotWorking;
               }).collect(Collectors.toList());
               Alt randomAlt = (Alt)unbanned.get(random.nextInt(unbanned.size()));

               try {
                  (new AltLoginThread(randomAlt)).start();
               } catch (Exception var8) {
                  ;
               }
            } else if (button.id == 5) {
               var12 = AltManager.registry.iterator();

               label75: {
                  do {
                     if (!var12.hasNext()) {
                        break label75;
                     }

                     alt = (Alt)var12.next();
                  } while(!alt.getUsername().equalsIgnoreCase(this.mc.session.getUsername()) && !alt.getMask().equalsIgnoreCase(this.mc.session.getUsername()));

                  alt.setStatus(Alt.Status.Banned);
               }

               try {
                  Client.getFileManager().getFile(Alts.class).saveFile();
               } catch (Exception var7) {
                  ;
               }
            }
         }
      }

   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRendererObj, this.reason, this.width / 2, this.height / 2 - this.field_175353_i / 2 - this.fontRendererObj.FONT_HEIGHT * 2, 11184810);
      int var4 = this.height / 2 - this.field_175353_i / 2;
      if (this.multilineMessage != null) {
         for(Iterator var5 = this.multilineMessage.iterator(); var5.hasNext(); var4 += this.fontRendererObj.FONT_HEIGHT) {
            String var6 = (String)var5.next();
            this.drawCenteredString(this.fontRendererObj, var6, this.width / 2, var4, 16777215);
         }
      }

      String str = "Current Alt: " + (Client.getModuleManager().isEnabled(StreamerMode.class) ? this.mc.session.getUsername().substring(0, 3) : this.mc.session.getUsername());
      this.fontRendererObj.drawStringWithShadow(str, (float)(this.width / 2 - this.fontRendererObj.getStringWidth(str) - 5), (float)(this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT + 56), -1);
      super.drawScreen(mouseX, mouseY, partialTicks);
   }
}
