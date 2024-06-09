/*     */ package me.eagler.clickgui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import me.eagler.Client;
/*     */ import me.eagler.clickgui.stuff.Element;
/*     */ import me.eagler.clickgui.stuff.ModuleButton;
/*     */ import me.eagler.clickgui.stuff.ValueSlider;
/*     */ import me.eagler.file.FileManager;
/*     */ import me.eagler.module.Category;
/*     */ import me.eagler.module.Module;
/*     */ import me.eagler.setting.SettingManager;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ 
/*     */ public class ClickGUI
/*     */   extends GuiScreen
/*     */ {
/*     */   public static ArrayList<Panel> panels;
/*     */   public static ArrayList<Panel> rpanels;
/*  23 */   public ModuleButton moduleButton = null;
/*     */   
/*     */   public SettingManager settingmanager;
/*     */   
/*     */   public ClickGUI() {
/*  28 */     this.settingmanager = Client.instance.getSettingManager();
/*  29 */     panels = new ArrayList<Panel>();
/*  30 */     double panelwidth = 80.0D;
/*  31 */     double panelheight = 15.0D;
/*  32 */     double panelx = 10.0D;
/*  33 */     double panely = 10.0D;
/*  34 */     double panelyplus = panelheight + 10.0D;
/*     */     byte b;
/*     */     int i;
/*     */     Category[] arrayOfCategory;
/*  38 */     for (i = (arrayOfCategory = Category.values()).length, b = 0; b < i; ) {
/*  39 */       final Category category = arrayOfCategory[b];
/*  40 */       String categoryname = String.valueOf(String.valueOf(Character.toUpperCase(category.name().toLowerCase().charAt(0)))) + 
/*  41 */         category.name().toLowerCase().substring(1);
/*  42 */       panels.add(new Panel(categoryname, panelx, panely, panelwidth, panelheight, false, this) {
/*     */             public void setup() {
/*  44 */               for (Module module : Client.instance.getModuleManager().getModules()) {
/*  45 */                 if (!module.getCategory().equals(category))
/*     */                   continue; 
/*  47 */                 this.elements.add(new ModuleButton(module, this));
/*     */               } 
/*     */             }
/*     */           });
/*  51 */       panely += panelyplus;
/*  52 */       b = (byte)(b + 1);
/*     */     } 
/*     */     try {
/*  55 */       for (Panel p : panels) {
/*  56 */         if (p.panelname.equalsIgnoreCase("Combat")) {
/*  57 */           if (FileManager.getPanel("combat", "x") != 0.0D && FileManager.getPanel("combat", "y") != 0.0D) {
/*  58 */             p.x = FileManager.getPanel("combat", "x");
/*  59 */             p.y = FileManager.getPanel("combat", "y");
/*  60 */             p.extended = FileManager.wasPanelExtended("combat");
/*     */           } 
/*     */           continue;
/*     */         } 
/*  64 */         if (p.panelname.equalsIgnoreCase("Movement")) {
/*  65 */           if (FileManager.getPanel("combat", "x") != 0.0D && FileManager.getPanel("movement", "y") != 0.0D) {
/*  66 */             p.x = FileManager.getPanel("movement", "x");
/*  67 */             p.y = FileManager.getPanel("movement", "y");
/*  68 */             p.extended = FileManager.wasPanelExtended("movement");
/*     */           } 
/*     */           continue;
/*     */         } 
/*  72 */         if (p.panelname.equalsIgnoreCase("Player")) {
/*  73 */           if (FileManager.getPanel("combat", "x") != 0.0D && FileManager.getPanel("player", "y") != 0.0D) {
/*  74 */             p.x = FileManager.getPanel("player", "x");
/*  75 */             p.y = FileManager.getPanel("player", "y");
/*  76 */             p.extended = FileManager.wasPanelExtended("player");
/*     */           } 
/*     */           continue;
/*     */         } 
/*  80 */         if (p.panelname.equalsIgnoreCase("Render")) {
/*  81 */           if (FileManager.getPanel("combat", "x") != 0.0D && FileManager.getPanel("render", "y") != 0.0D) {
/*  82 */             p.x = FileManager.getPanel("render", "x");
/*  83 */             p.y = FileManager.getPanel("render", "y");
/*  84 */             p.extended = FileManager.wasPanelExtended("render");
/*     */           } 
/*     */           continue;
/*     */         } 
/*  88 */         if (p.panelname.equalsIgnoreCase("Gui")) {
/*  89 */           if (FileManager.getPanel("combat", "x") != 0.0D && FileManager.getPanel("gui", "y") != 0.0D) {
/*  90 */             p.x = FileManager.getPanel("gui", "x");
/*  91 */             p.y = FileManager.getPanel("gui", "y");
/*  92 */             p.extended = FileManager.wasPanelExtended("gui");
/*     */           } 
/*     */           continue;
/*     */         } 
/*  96 */         if (p.panelname.equalsIgnoreCase("Misc") && FileManager.getPanel("misc", "x") != 0.0D && 
/*  97 */           FileManager.getPanel("misc", "y") != 0.0D) {
/*  98 */           p.x = FileManager.getPanel("misc", "x");
/*  99 */           p.y = FileManager.getPanel("misc", "y");
/* 100 */           p.extended = FileManager.wasPanelExtended("misc");
/*     */         } 
/* 102 */         if (p.panelname.equalsIgnoreCase("Fun") && FileManager.getPanel("fun", "x") != 0.0D && 
/* 103 */           FileManager.getPanel("fun", "y") != 0.0D) {
/* 104 */           p.x = FileManager.getPanel("fun", "x");
/* 105 */           p.y = FileManager.getPanel("fun", "y");
/* 106 */           p.extended = FileManager.wasPanelExtended("fun");
/*     */         } 
/*     */       } 
/* 109 */     } catch (Exception exception) {}
/*     */     
/* 111 */     rpanels = new ArrayList<Panel>();
/* 112 */     for (Panel p : panels)
/* 113 */       rpanels.add(p); 
/* 114 */     Collections.reverse(rpanels);
/*     */   }
/*     */   
/*     */   public void initGui() {
/* 118 */     ScaledResolution sr = new ScaledResolution(this.mc);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {}
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 127 */     for (Panel panel : panels)
/* 128 */       panel.drawScreen(mouseX, mouseY, partialTicks); 
/* 129 */     this.moduleButton = null;
/* 130 */     label46: for (Panel panel : panels) {
/* 131 */       if (panel != null && panel.visible && panel.extended && panel.elements != null && panel.elements.size() > 0)
/* 132 */         for (ModuleButton modulebutton : panel.elements) {
/* 133 */           if (modulebutton.listening) {
/* 134 */             this.moduleButton = modulebutton;
/*     */             break label46;
/*     */           } 
/*     */         }  
/*     */     } 
/* 139 */     for (Panel panel : panels) {
/* 140 */       if (panel.extended && panel.visible && panel.elements != null)
/* 141 */         for (ModuleButton b : panel.elements) {
/* 142 */           if (b.extended && b.elements != null && !b.elements.isEmpty()) {
/* 143 */             double off = 0.0D;
/* 144 */             for (Element e : b.elements) {
/* 145 */               e.offset = off;
/* 146 */               e.onUpdate();
/* 147 */               e.drawScreen(mouseX, mouseY, partialTicks);
/* 148 */               off += e.height - 1.0D;
/*     */             } 
/*     */           } 
/*     */         }  
/*     */     } 
/* 153 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */   
/*     */   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
/* 157 */     if (this.moduleButton != null)
/*     */       return; 
/* 159 */     for (Panel panel : rpanels) {
/* 160 */       if (panel.extended && panel.visible && panel.elements != null)
/* 161 */         for (ModuleButton b : panel.elements) {
/* 162 */           if (b.extended)
/* 163 */             for (Element e : b.elements) {
/* 164 */               if (e.mouseClicked(mouseX, mouseY, mouseButton))
/*     */                 return; 
/*     */             }  
/*     */         }  
/*     */     } 
/* 169 */     for (Panel p : rpanels) {
/* 170 */       if (p.mouseClicked(mouseX, mouseY, mouseButton))
/*     */         return; 
/*     */     } 
/*     */     try {
/* 174 */       super.mouseClicked(mouseX, mouseY, mouseButton);
/* 175 */     } catch (IOException e) {
/* 176 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void mouseReleased(int mouseX, int mouseY, int state) {
/* 181 */     if (this.moduleButton != null)
/*     */       return; 
/* 183 */     for (Panel panel : rpanels) {
/* 184 */       if (panel.extended && panel.visible && panel.elements != null)
/* 185 */         for (ModuleButton b : panel.elements) {
/* 186 */           if (b.extended)
/* 187 */             for (Element e : b.elements)
/* 188 */               e.mouseReleased(mouseX, mouseY, state);  
/*     */         }  
/*     */     } 
/* 191 */     for (Panel p : rpanels)
/* 192 */       p.mouseReleased(mouseX, mouseY, state); 
/* 193 */     super.mouseReleased(mouseX, mouseY, state);
/*     */   }
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) {
/*     */     try {
/* 198 */       super.keyTyped(typedChar, keyCode);
/* 199 */     } catch (IOException e2) {
/* 200 */       e2.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onGuiClosed() {
/* 205 */     for (Panel p : panels) {
/* 206 */       if (p.panelname.equalsIgnoreCase("Combat")) {
/* 207 */         FileManager.savePanel("combat", p.x, p.y, p.extended);
/*     */         continue;
/*     */       } 
/* 210 */       if (p.panelname.equalsIgnoreCase("Movement")) {
/* 211 */         FileManager.savePanel("movement", p.x, p.y, p.extended);
/*     */         continue;
/*     */       } 
/* 214 */       if (p.panelname.equalsIgnoreCase("Player")) {
/* 215 */         FileManager.savePanel("player", p.x, p.y, p.extended);
/*     */         continue;
/*     */       } 
/* 218 */       if (p.panelname.equalsIgnoreCase("Render")) {
/* 219 */         FileManager.savePanel("render", p.x, p.y, p.extended);
/*     */         continue;
/*     */       } 
/* 222 */       if (p.panelname.equalsIgnoreCase("Gui")) {
/* 223 */         FileManager.savePanel("gui", p.x, p.y, p.extended);
/*     */         continue;
/*     */       } 
/* 226 */       if (p.panelname.equalsIgnoreCase("Misc")) {
/* 227 */         FileManager.savePanel("misc", p.x, p.y, p.extended);
/*     */         continue;
/*     */       } 
/* 230 */       if (p.panelname.equalsIgnoreCase("Fun"))
/* 231 */         FileManager.savePanel("fun", p.x, p.y, p.extended); 
/*     */     } 
/* 233 */     for (Panel panel : rpanels) {
/* 234 */       if (panel.extended && panel.visible && panel.elements != null)
/* 235 */         for (ModuleButton b : panel.elements) {
/* 236 */           if (b.extended)
/* 237 */             for (Element e : b.elements) {
/* 238 */               if (e instanceof ValueSlider)
/* 239 */                 ((ValueSlider)e).dragging = false; 
/*     */             }  
/*     */         }  
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\clickgui\ClickGUI.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */