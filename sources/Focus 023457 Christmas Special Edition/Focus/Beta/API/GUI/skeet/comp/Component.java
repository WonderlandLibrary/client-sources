/*     */
package Focus.Beta.API.GUI.skeet.comp;
/*     */ 
/*     */ import Focus.Beta.UTILS.render.LockedResolution;

import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Component
/*     */ {
/*  11 */   protected final List<Component> children = new ArrayList<>();
/*     */   
/*     */   private final Component parent;
/*     */   
/*     */   private float x;
/*     */   
/*     */   private float y;
/*     */   
/*     */   private float width;
/*     */   private float height;
/*     */   
/*     */   public Component(Component parent, float x, float y, float width, float height) {
/*  23 */     this.parent = parent;
/*  24 */     this.x = x;
/*  25 */     this.y = y;
/*  26 */     this.width = width;
/*  27 */     this.height = height;
/*     */   }
/*     */   
/*     */   public Component getParent() {
/*  31 */     return this.parent;
/*     */   }
/*     */   
/*     */   public void addChild(Component child) {
/*  35 */     this.children.add(child);
/*     */   }
/*     */ 
/*     */

/*     */   
/*     */   public void drawComponent(LockedResolution lockedResolution, int mouseX, int mouseY) {
/*  41 */     for (Component child : this.children) {
/*  42 */       child.drawComponent(lockedResolution, mouseX, mouseY);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onMouseClick(int mouseX, int mouseY, int button) {
/*  49 */     for (Component child : this.children) {
/*  50 */       child.onMouseClick(mouseX, mouseY, button);
/*     */     }
/*     */   }
/*     */   
/*     */   public void onMouseRelease(int button) {
/*  55 */     for (Component child : this.children) {
/*  56 */       child.onMouseRelease(button);
/*     */     }
/*     */   }
/*     */   
/*     */   public void onKeyPress(int keyCode) {
/*  61 */     for (Component child : this.children) {
/*  62 */       child.onKeyPress(keyCode);
/*     */     }
/*     */   }
/*     */   
/*     */   public float getX() {
/*  67 */     Component familyMember = this.parent;
/*  68 */     float familyTreeX = this.x;
/*     */     
/*  70 */     while (familyMember != null) {
/*  71 */       familyTreeX += familyMember.x;
/*  72 */       familyMember = familyMember.parent;
/*     */     } 
/*     */     
/*  75 */     return familyTreeX;
/*     */   }
/*     */   
/*     */   public void setX(float x) {
/*  79 */     this.x = x;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHovered(int mouseX, int mouseY) {
/*     */     float x;
/*     */     float y;
/*  86 */     return (mouseX >= (x = getX()) && mouseY >= (y = getY()) && mouseX <= x + getWidth() && mouseY <= y + getHeight());
/*     */   }
/*     */   
/*     */   public float getY() {
/*  90 */     Component familyMember = this.parent;
/*  91 */     float familyTreeY = this.y;
/*     */     
/*  93 */     while (familyMember != null) {
/*  94 */       familyTreeY += familyMember.y;
/*  95 */       familyMember = familyMember.parent;
/*     */     } 
/*     */     
/*  98 */     return familyTreeY;
/*     */   }
/*     */   
/*     */   public void setY(float y) {
/* 102 */     this.y = y;
/*     */   }
/*     */   
/*     */   public float getWidth() {
/* 106 */     return this.width;
/*     */   }
/*     */   
/*     */   public void setWidth(float width) {
/* 110 */     this.width = width;
/*     */   }
/*     */   
/*     */   public float getHeight() {
/* 114 */     return this.height;
/*     */   }
/*     */   
/*     */   public void setHeight(float height) {
/* 118 */     this.height = height;
/*     */   }
/*     */   
/*     */   public List<Component> getChildren() {
/* 122 */     return this.children;
/*     */   }
/*     */ }


/* Location:              C:\Users\Adam\Desktop\Radium\Radium.jar!\vip\radium\gui\csgo\component\Component.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */