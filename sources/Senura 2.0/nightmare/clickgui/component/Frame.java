/*     */ package nightmare.clickgui.component;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import nightmare.Nightmare;
/*     */ import nightmare.clickgui.component.components.Button;
/*     */ import nightmare.fonts.impl.Fonts;
/*     */ import nightmare.module.Category;
/*     */ import nightmare.module.Module;
/*     */ import nightmare.utils.ColorUtils;
/*     */ 
/*     */ 
/*     */ public class Frame
/*     */ {
/*     */   public ArrayList<Component> components;
/*     */   public Category category;
/*     */   private boolean open;
/*     */   private int width;
/*     */   private int y;
/*     */   private int x;
/*     */   private int barHeight;
/*     */   public boolean isDragging;
/*     */   public int dragX;
/*     */   public int dragY;
/*     */   
/*     */   public Frame(Category cat) {
/*  28 */     this.components = new ArrayList<>();
/*  29 */     this.category = cat;
/*  30 */     this.width = 99;
/*  31 */     this.x = 20;
/*  32 */     this.y = 20;
/*  33 */     this.barHeight = 13;
/*  34 */     this.dragX = 0;
/*  35 */     this.open = true;
/*  36 */     this.isDragging = false;
/*  37 */     int tY = this.barHeight;
/*     */     
/*  39 */     for (Module mod : Nightmare.instance.moduleManager.getModulesInCategory(this.category)) {
/*  40 */       Button modButton = new Button(mod, this, tY);
/*  41 */       this.components.add(modButton);
/*  42 */       tY += 18;
/*     */     } 
/*     */   }
/*     */   
/*     */   public ArrayList<Component> getComponents() {
/*  47 */     return this.components;
/*     */   }
/*     */   
/*     */   public void setX(int newX) {
/*  51 */     this.x = newX;
/*     */   }
/*     */   
/*     */   public void setY(int newY) {
/*  55 */     this.y = newY;
/*     */   }
/*     */   
/*     */   public void setDrag(boolean drag) {
/*  59 */     this.isDragging = drag;
/*     */   }
/*     */   
/*     */   public boolean isOpen() {
/*  63 */     return this.open;
/*     */   }
/*     */   
/*     */   public void setOpen(boolean open) {
/*  67 */     this.open = open;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderFrame(FontRenderer fontRenderer) {
/*  72 */     String icon = "";
/*     */     
/*  74 */     Gui.func_73734_a(this.x, this.y - 4, this.x + this.width, this.y + this.barHeight, ColorUtils.getClientColor());
/*     */     
/*  76 */     Fonts.REGULAR.REGULAR_20.REGULAR_20.drawString(this.category.name().replace("COMBAT", "Combat").replace("MOVEMENT", "Movement").replace("RENDER", "Render").replace("PLAYER", "Player").replace("WORLD", "World").replace("MISC", "Misc"), (this.x + 14), (this.y + 2), -1);
/*  77 */     Fonts.REGULAR.REGULAR_20.REGULAR_20.drawString(this.open ? "-" : "+", (this.x + 89), (this.y + 1), -1);
/*     */     
/*  79 */     if (this.category.equals(Category.COMBAT)) {
/*  80 */       icon = "G";
/*  81 */     } else if (this.category.equals(Category.MOVEMENT)) {
/*  82 */       icon = "H";
/*  83 */     } else if (this.category.equals(Category.RENDER)) {
/*  84 */       icon = "J";
/*  85 */     } else if (this.category.equals(Category.PLAYER)) {
/*  86 */       icon = "I";
/*  87 */     } else if (this.category.equals(Category.WORLD)) {
/*  88 */       icon = "K";
/*  89 */     } else if (this.category.equals(Category.MISC)) {
/*  90 */       icon = "L";
/*     */     } 
/*     */     
/*  93 */     Fonts.ICON.ICON_20.ICON_20.drawString(icon, (this.x + 2), this.y + 2.6F, -1);
/*     */     
/*  95 */     if (this.open && 
/*  96 */       !this.components.isEmpty()) {
/*  97 */       for (Component component : this.components) {
/*  98 */         component.renderComponent();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void refresh() {
/* 105 */     int off = this.barHeight;
/* 106 */     for (Component comp : this.components) {
/* 107 */       comp.setOff(off);
/* 108 */       off += comp.getHeight();
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getX() {
/* 113 */     return this.x;
/*     */   }
/*     */   
/*     */   public int getY() {
/* 117 */     return this.y;
/*     */   }
/*     */   
/*     */   public int getWidth() {
/* 121 */     return this.width;
/*     */   }
/*     */   
/*     */   public void updatePosition(int mouseX, int mouseY) {
/* 125 */     if (this.isDragging) {
/* 126 */       setX(mouseX - this.dragX);
/* 127 */       setY(mouseY - this.dragY);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isWithinHeader(int x, int y) {
/* 132 */     if (x >= this.x && x <= this.x + this.width && y >= this.y - 4 && y <= this.y + this.barHeight) {
/* 133 */       return true;
/*     */     }
/* 135 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\clickgui\component\Frame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */