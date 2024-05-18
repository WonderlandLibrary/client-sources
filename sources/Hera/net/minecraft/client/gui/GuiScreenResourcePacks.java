/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.resources.ResourcePackListEntry;
/*     */ import net.minecraft.client.resources.ResourcePackListEntryDefault;
/*     */ import net.minecraft.client.resources.ResourcePackListEntryFound;
/*     */ import net.minecraft.client.resources.ResourcePackRepository;
/*     */ import net.minecraft.util.Util;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.Sys;
/*     */ 
/*     */ public class GuiScreenResourcePacks
/*     */   extends GuiScreen {
/*  21 */   private static final Logger logger = LogManager.getLogger();
/*     */   
/*     */   private final GuiScreen parentScreen;
/*     */   
/*     */   private List<ResourcePackListEntry> availableResourcePacks;
/*     */   
/*     */   private List<ResourcePackListEntry> selectedResourcePacks;
/*     */   
/*     */   private GuiResourcePackAvailable availableResourcePacksList;
/*     */   
/*     */   private GuiResourcePackSelected selectedResourcePacksList;
/*     */   private boolean changed = false;
/*     */   
/*     */   public GuiScreenResourcePacks(GuiScreen parentScreenIn) {
/*  35 */     this.parentScreen = parentScreenIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*     */     try {
/*  46 */       this.buttonList.add(new GuiOptionButton(2, this.width / 2 - 154, this.height - 48, I18n.format("resourcePack.openFolder", new Object[0])));
/*  47 */       this.buttonList.add(new GuiOptionButton(1, this.width / 2 + 4, this.height - 48, I18n.format("gui.done", new Object[0])));
/*     */       
/*  49 */       if (!this.changed) {
/*     */         
/*  51 */         this.availableResourcePacks = Lists.newArrayList();
/*  52 */         this.selectedResourcePacks = Lists.newArrayList();
/*  53 */         ResourcePackRepository resourcepackrepository = this.mc.getResourcePackRepository();
/*  54 */         resourcepackrepository.updateRepositoryEntriesAll();
/*  55 */         List<ResourcePackRepository.Entry> list = Lists.newArrayList(resourcepackrepository.getRepositoryEntriesAll());
/*  56 */         list.removeAll(resourcepackrepository.getRepositoryEntries());
/*     */         
/*  58 */         for (ResourcePackRepository.Entry resourcepackrepository$entry : list)
/*     */         {
/*  60 */           this.availableResourcePacks.add(new ResourcePackListEntryFound(this, resourcepackrepository$entry));
/*     */         }
/*     */         
/*  63 */         for (ResourcePackRepository.Entry resourcepackrepository$entry1 : Lists.reverse(resourcepackrepository.getRepositoryEntries()))
/*     */         {
/*  65 */           this.selectedResourcePacks.add(new ResourcePackListEntryFound(this, resourcepackrepository$entry1));
/*     */         }
/*     */         
/*  68 */         this.selectedResourcePacks.add(new ResourcePackListEntryDefault(this));
/*     */       } 
/*     */       
/*  71 */       this.availableResourcePacksList = new GuiResourcePackAvailable(this.mc, 200, this.height, this.availableResourcePacks);
/*  72 */       this.availableResourcePacksList.setSlotXBoundsFromLeft(this.width / 2 - 4 - 200);
/*  73 */       this.availableResourcePacksList.registerScrollButtons(7, 8);
/*  74 */       this.selectedResourcePacksList = new GuiResourcePackSelected(this.mc, 200, this.height, this.selectedResourcePacks);
/*  75 */       this.selectedResourcePacksList.setSlotXBoundsFromLeft(this.width / 2 + 4);
/*  76 */       this.selectedResourcePacksList.registerScrollButtons(7, 8);
/*     */     }
/*  78 */     catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/*     */     try {
/*  88 */       super.handleMouseInput();
/*  89 */       this.selectedResourcePacksList.handleMouseInput();
/*  90 */       this.availableResourcePacksList.handleMouseInput();
/*     */     }
/*  92 */     catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasResourcePackEntry(ResourcePackListEntry p_146961_1_) {
/*  97 */     return this.selectedResourcePacks.contains(p_146961_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ResourcePackListEntry> getListContaining(ResourcePackListEntry p_146962_1_) {
/* 102 */     return hasResourcePackEntry(p_146962_1_) ? this.selectedResourcePacks : this.availableResourcePacks;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ResourcePackListEntry> getAvailableResourcePacks() {
/* 107 */     return this.availableResourcePacks;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ResourcePackListEntry> getSelectedResourcePacks() {
/* 112 */     return this.selectedResourcePacks;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*     */     try {
/* 122 */       if (button.enabled)
/*     */       {
/* 124 */         if (button.id == 2) {
/*     */           
/* 126 */           File file1 = this.mc.getResourcePackRepository().getDirResourcepacks();
/* 127 */           String s = file1.getAbsolutePath();
/*     */           
/* 129 */           if (Util.getOSType() == Util.EnumOS.OSX) {
/*     */ 
/*     */             
/*     */             try {
/* 133 */               logger.info(s);
/* 134 */               Runtime.getRuntime().exec(new String[] { "/usr/bin/open", s });
/*     */               
/*     */               return;
/* 137 */             } catch (IOException ioexception1) {
/*     */               
/* 139 */               logger.error("Couldn't open file", ioexception1);
/*     */             }
/*     */           
/* 142 */           } else if (Util.getOSType() == Util.EnumOS.WINDOWS) {
/*     */             
/* 144 */             String s1 = String.format("cmd.exe /C start \"Open file\" \"%s\"", new Object[] { s });
/*     */ 
/*     */             
/*     */             try {
/* 148 */               Runtime.getRuntime().exec(s1);
/*     */               
/*     */               return;
/* 151 */             } catch (IOException ioexception) {
/*     */               
/* 153 */               logger.error("Couldn't open file", ioexception);
/*     */             } 
/*     */           } 
/*     */           
/* 157 */           boolean flag = false;
/*     */ 
/*     */           
/*     */           try {
/* 161 */             Class<?> oclass = Class.forName("java.awt.Desktop");
/* 162 */             Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
/* 163 */             oclass.getMethod("browse", new Class[] { URI.class }).invoke(object, new Object[] { file1.toURI() });
/*     */           }
/* 165 */           catch (Throwable throwable) {
/*     */             
/* 167 */             logger.error("Couldn't open link", throwable);
/* 168 */             flag = true;
/*     */           } 
/*     */           
/* 171 */           if (flag)
/*     */           {
/* 173 */             logger.info("Opening via system class!");
/* 174 */             Sys.openURL("file://" + s);
/*     */           }
/*     */         
/* 177 */         } else if (button.id == 1) {
/*     */           
/* 179 */           if (this.changed) {
/*     */             
/* 181 */             List<ResourcePackRepository.Entry> list = Lists.newArrayList();
/*     */             
/* 183 */             for (ResourcePackListEntry resourcepacklistentry : this.selectedResourcePacks) {
/*     */               
/* 185 */               if (resourcepacklistentry instanceof ResourcePackListEntryFound)
/*     */               {
/* 187 */                 list.add(((ResourcePackListEntryFound)resourcepacklistentry).func_148318_i());
/*     */               }
/*     */             } 
/*     */             
/* 191 */             Collections.reverse(list);
/* 192 */             this.mc.getResourcePackRepository().setRepositories(list);
/* 193 */             this.mc.gameSettings.resourcePacks.clear();
/* 194 */             this.mc.gameSettings.field_183018_l.clear();
/*     */             
/* 196 */             for (ResourcePackRepository.Entry resourcepackrepository$entry : list) {
/*     */               
/* 198 */               this.mc.gameSettings.resourcePacks.add(resourcepackrepository$entry.getResourcePackName());
/*     */               
/* 200 */               if (resourcepackrepository$entry.func_183027_f() != 1)
/*     */               {
/* 202 */                 this.mc.gameSettings.field_183018_l.add(resourcepackrepository$entry.getResourcePackName());
/*     */               }
/*     */             } 
/*     */             
/* 206 */             this.mc.gameSettings.saveOptions();
/* 207 */             this.mc.refreshResources();
/*     */           } 
/*     */           
/* 210 */           this.mc.displayGuiScreen(this.parentScreen);
/*     */         }
/*     */       
/*     */       }
/* 214 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/*     */     try {
/* 224 */       super.mouseClicked(mouseX, mouseY, mouseButton);
/* 225 */       this.availableResourcePacksList.mouseClicked(mouseX, mouseY, mouseButton);
/* 226 */       this.selectedResourcePacksList.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     }
/* 228 */     catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseReleased(int mouseX, int mouseY, int state) {
/*     */     try {
/* 238 */       super.mouseReleased(mouseX, mouseY, state);
/*     */     }
/* 240 */     catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*     */     try {
/* 250 */       drawBackground(0);
/* 251 */       this.availableResourcePacksList.drawScreen(mouseX, mouseY, partialTicks);
/* 252 */       this.selectedResourcePacksList.drawScreen(mouseX, mouseY, partialTicks);
/* 253 */       drawCenteredString(this.fontRendererObj, I18n.format("resourcePack.title", new Object[0]), this.width / 2, 16, 16777215);
/* 254 */       drawCenteredString(this.fontRendererObj, I18n.format("resourcePack.folderInfo", new Object[0]), this.width / 2 - 77, this.height - 26, 8421504);
/* 255 */       super.drawScreen(mouseX, mouseY, partialTicks);
/*     */     }
/* 257 */     catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void markChanged() {
/* 265 */     this.changed = true;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\GuiScreenResourcePacks.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */