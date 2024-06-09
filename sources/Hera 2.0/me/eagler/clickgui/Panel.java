/*     */ package me.eagler.clickgui;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import me.eagler.clickgui.stuff.ModuleButton;
/*     */ import me.eagler.font.FontHelper;
/*     */ import me.eagler.utils.ColorUtil;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Panel
/*     */ {
/*     */   public String panelname;
/*     */   public double x;
/*     */   public double y;
/*     */   public double x2;
/*     */   public double y2;
/*     */   public double width;
/*     */   public double height;
/*     */   public boolean dragging;
/*     */   public boolean extended;
/*     */   public boolean visible;
/*  33 */   public ArrayList<ModuleButton> elements = new ArrayList<ModuleButton>();
/*     */   
/*     */   public ClickGUI clickgui;
/*     */   
/*     */   public Panel(String name, double x, double y, double width, double height, boolean extended, ClickGUI clickgui) {
/*  38 */     this.panelname = name;
/*  39 */     this.x = x;
/*  40 */     this.y = y;
/*  41 */     this.width = width;
/*  42 */     this.height = height;
/*  43 */     this.extended = extended;
/*  44 */     this.dragging = false;
/*  45 */     this.visible = true;
/*  46 */     this.clickgui = clickgui;
/*  47 */     setup();
/*     */   }
/*     */   
/*  50 */   public static Color cc = Colors.getPrimary();
/*     */   
/*  52 */   public static Color ccdarker = cc.darker().darker().darker().darker();
/*     */   
/*  54 */   public static Color ccdarker2 = ccdarker.darker();
/*     */ 
/*     */   
/*     */   public void setup() {}
/*     */   
/*  59 */   public static Color gold = new Color(218, 165, 32);
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  62 */     if (!this.visible)
/*     */       return; 
/*  64 */     if (this.dragging) {
/*  65 */       this.x = this.x2 + mouseX;
/*  66 */       this.y = this.y2 + mouseY;
/*     */     } 
/*  68 */     Gui.drawRect(this.x + 5.0D, this.y - 4.0D, this.x + this.width - 1.0D, this.y + this.height - 2.0D, 
/*  69 */         Color.black.getRGB());
/*  70 */     Gui.drawRect(this.x + 3.0D, this.y - 6.0D, this.x + this.width - 3.0D, this.y + this.height - 4.0D, 
/*  71 */         Colors.getPrimary().getRGB());
/*  72 */     if (this.extended && !this.elements.isEmpty()) {
/*  73 */       Gui.drawRect(this.x - 2.0D, this.y + this.height - 1.0D, this.x + this.width + 2.0D, 
/*  74 */           this.y + this.height - 1.0D, Color.black.getRGB());
/*     */     }
/*  76 */     FontHelper.cfClickGui.drawCenteredString(this.panelname, this.x + this.width / 2.0D, 
/*  77 */         this.y + this.height / 2.0D - 12.0D, Colors.getText().getRGB(), true);
/*     */     
/*  79 */     if (!this.elements.isEmpty())
/*     */     {
/*  81 */       if (this.extended) {
/*     */         
/*  83 */         double y = ((ModuleButton)this.elements.get(0)).y;
/*  84 */         double x = ((ModuleButton)this.elements.get(0)).x;
/*     */         
/*  86 */         double y2 = (this.elements.size() * 15);
/*     */         
/*  88 */         Color c2 = new Color(0, 0, 0, 244);
/*     */         
/*  90 */         Gui.drawRect(x, y, x + 81.0D, y + y2 + 4.0D, c2.getRGB());
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  96 */     if (this.extended && !this.elements.isEmpty()) {
/*  97 */       double startY = this.y + this.height;
/*  98 */       for (ModuleButton moduleButton : this.elements) {
/*  99 */         Gui.drawRect(this.x, startY, this.x + this.width, startY + moduleButton.height + 1.0D, 
/* 100 */             ColorUtil.getClickGuiColor().getRGB());
/* 101 */         moduleButton.x = this.x + 2.0D;
/* 102 */         moduleButton.y = startY;
/* 103 */         moduleButton.width = this.width - 4.0D;
/* 104 */         moduleButton.drawScreen(mouseX, mouseY, partialTicks);
/* 105 */         startY += moduleButton.height;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
/* 112 */     if (!this.visible)
/* 113 */       return false; 
/* 114 */     if (mouseButton == 0 && hovered(mouseX, mouseY)) {
/* 115 */       this.x2 = this.x - mouseX;
/* 116 */       this.y2 = this.y - mouseY;
/* 117 */       this.dragging = true;
/* 118 */       return true;
/*     */     } 
/* 120 */     if (mouseButton == 1 && hovered(mouseX, mouseY)) {
/* 121 */       this.extended = !this.extended;
/* 122 */       return true;
/*     */     } 
/* 124 */     if (this.extended)
/* 125 */       for (ModuleButton moduleButton : this.elements) {
/* 126 */         if (moduleButton.mouseClicked(mouseX, mouseY, mouseButton))
/* 127 */           return true; 
/*     */       }  
/* 129 */     return false;
/*     */   }
/*     */   
/*     */   public void mouseReleased(int mouseX, int mouseY, int state) {
/* 133 */     if (!this.visible)
/*     */       return; 
/* 135 */     if (state == 0)
/* 136 */       this.dragging = false; 
/*     */   }
/*     */   
/*     */   public boolean hovered(int mouseX, int mouseY) {
/* 140 */     return (mouseX >= this.x + 2.0D && mouseX <= this.x + this.width - 2.0D && mouseY >= this.y - 7.0D && 
/* 141 */       mouseY <= this.y + this.height - 3.0D);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\clickgui\Panel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */