package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.aNh;
import net.af_;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiResourcePackAvailable;
import net.minecraft.client.gui.GuiResourcePackSelected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.ResourcePackListEntry;
import net.minecraft.client.resources.ResourcePackListEntryDefault;
import net.minecraft.client.resources.ResourcePackListEntryFound;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.client.resources.ResourcePackRepository$Entry;
import net.minecraft.util.Util$EnumOS;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.Sys;

public class GuiScreenResourcePacks extends GuiScreen {
   private static final Logger LOGGER = LogManager.getLogger();
   private final GuiScreen parentScreen;
   private List availableResourcePacks;
   private List selectedResourcePacks;
   private GuiResourcePackAvailable availableResourcePacksList;
   private GuiResourcePackSelected selectedResourcePacksList;
   private boolean changed = false;

   public GuiScreenResourcePacks(GuiScreen var1) {
      this.parentScreen = var1;
   }

   public void initGui() {
      this.buttonList.add(new GuiOptionButton(2, this.width / 2 - 154, this.height - 48, I18n.format("resourcePack.openFolder", new Object[0])));
      this.buttonList.add(new GuiOptionButton(1, this.width / 2 + 4, this.height - 48, I18n.format("gui.done", new Object[0])));
      if(!this.changed) {
         this.availableResourcePacks = Lists.newArrayList();
         this.selectedResourcePacks = Lists.newArrayList();
         ResourcePackRepository var1 = this.mc.getResourcePackRepository();
         var1.updateRepositoryEntriesAll();
         ArrayList var2 = Lists.newArrayList(var1.getRepositoryEntriesAll());
         var2.removeAll(var1.getRepositoryEntries());

         for(ResourcePackRepository$Entry var4 : var2) {
            this.availableResourcePacks.add(new ResourcePackListEntryFound(this, var4));
         }

         for(ResourcePackRepository$Entry var6 : Lists.reverse(var1.getRepositoryEntries())) {
            this.selectedResourcePacks.add(new ResourcePackListEntryFound(this, var6));
         }

         this.selectedResourcePacks.add(new ResourcePackListEntryDefault(this));
      }

      this.availableResourcePacksList = new GuiResourcePackAvailable(this.mc, 200, this.height, this.availableResourcePacks);
      this.availableResourcePacksList.setSlotXBoundsFromLeft(this.width / 2 - 4 - 200);
      this.availableResourcePacksList.registerScrollButtons(7, 8);
      this.selectedResourcePacksList = new GuiResourcePackSelected(this.mc, 200, this.height, this.selectedResourcePacks);
      this.selectedResourcePacksList.setSlotXBoundsFromLeft(this.width / 2 + 4);
      this.selectedResourcePacksList.registerScrollButtons(7, 8);
   }

   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      this.selectedResourcePacksList.handleMouseInput();
      this.availableResourcePacksList.handleMouseInput();
   }

   public boolean hasResourcePackEntry(ResourcePackListEntry var1) {
      return this.selectedResourcePacks.contains(var1);
   }

   public List getListContaining(ResourcePackListEntry var1) {
      return this.hasResourcePackEntry(var1)?this.selectedResourcePacks:this.availableResourcePacks;
   }

   public List getAvailableResourcePacks() {
      return this.availableResourcePacks;
   }

   public List getSelectedResourcePacks() {
      return this.selectedResourcePacks;
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      if(var1.enabled) {
         if(var1.id == 2) {
            File var2 = this.mc.getResourcePackRepository().getDirResourcepacks();
            String var3 = var2.getAbsolutePath();
            if(aNh.a() == Util$EnumOS.OSX) {
               try {
                  LOGGER.info(var3);
                  Runtime.getRuntime().exec(new String[]{"/usr/bin/open", var3});
                  return;
               } catch (IOException var9) {
                  LOGGER.error("Couldn\'t open file", var9);
               }
            } else if(aNh.a() == Util$EnumOS.WINDOWS) {
               String var4 = String.format("cmd.exe /C start \"Open file\" \"%s\"", new Object[]{var3});

               try {
                  Runtime.getRuntime().exec(var4);
                  return;
               } catch (IOException var8) {
                  LOGGER.error("Couldn\'t open file", var8);
               }
            }

            boolean var13 = false;
            String var10000 = "java.awt.Desktop";

            try {
               Class var5 = af_.a(var10000);
               Object var6 = af_.b(var5, "getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
               af_.b(var5, "browse", new Class[]{URI.class}).invoke(var6, new Object[]{var2.toURI()});
            } catch (Throwable var7) {
               LOGGER.error("Couldn\'t open link", var7);
               var13 = true;
            }

            LOGGER.info("Opening via system class!");
            Sys.openURL("file://" + var3);
         } else if(var1.id == 1) {
            if(this.changed) {
               ArrayList var10 = Lists.newArrayList();

               for(ResourcePackListEntry var15 : this.selectedResourcePacks) {
                  if(var15 instanceof ResourcePackListEntryFound) {
                     var10.add(((ResourcePackListEntryFound)var15).func_148318_i());
                  }
               }

               Collections.reverse(var10);
               this.mc.getResourcePackRepository().setRepositories(var10);
               this.mc.gameSettings.resourcePacks.clear();
               this.mc.gameSettings.field_183018_l.clear();

               for(ResourcePackRepository$Entry var16 : var10) {
                  this.mc.gameSettings.resourcePacks.add(var16.getResourcePackName());
                  if(var16.func_183027_f() != 1) {
                     this.mc.gameSettings.field_183018_l.add(var16.getResourcePackName());
                  }
               }

               this.mc.gameSettings.saveOptions();
               this.mc.refreshResources();
            }

            this.mc.displayGuiScreen(this.parentScreen);
         }
      }

   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      super.mouseClicked(var1, var2, var3);
      this.availableResourcePacksList.mouseClicked(var1, var2, var3);
      this.selectedResourcePacksList.mouseClicked(var1, var2, var3);
   }

   protected void mouseReleased(int var1, int var2, int var3) {
      super.mouseReleased(var1, var2, var3);
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.drawBackground(0);
      this.availableResourcePacksList.drawScreen(var1, var2, var3);
      this.selectedResourcePacksList.drawScreen(var1, var2, var3);
      this.drawCenteredString(this.fontRendererObj, I18n.format("resourcePack.title", new Object[0]), this.width / 2, 16, 16777215);
      this.drawCenteredString(this.fontRendererObj, I18n.format("resourcePack.folderInfo", new Object[0]), this.width / 2 - 77, this.height - 26, 8421504);
      super.drawScreen(var1, var2, var3);
   }

   public void markChanged() {
      this.changed = true;
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
