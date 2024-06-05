
package Focus.Beta.API.GUI.skeet.comp;

import Focus.Beta.API.GUI.skeet.SkeetUI;
import Focus.Beta.UTILS.render.LockedResolution;

/*    */
/*    */ public abstract class TabComponent
/*    */   extends Component {
/*    */   private final String name;
/*    */   
/*    */   public TabComponent(Component parent, String name, float x, float y, float width, float height) {
/* 11 */     super(parent, x, y, width, height);
/* 12 */     setupChildren();
/* 13 */     this.name = name;
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract void setupChildren();
/*    */   
/*    */   public void drawComponent(LockedResolution resolution, int mouseX, int mouseY) {
/* 20 */     SkeetUI.FONT_RENDERER.drawStringWithShadow(this.name, getX() + 8.0F,
/* 21 */         getY() + 8.0F - 3.0F, SkeetUI.getColor(16777215));
/*    */     
/* 23 */     float x = 8.0F;
/* 24 */     for (int i = 0; i < this.children.size(); i++) {
/* 25 */       Component child = this.children.get(i);
/*    */       
/* 27 */       child.setX(x);
/* 28 */       if (i < 3)
/* 29 */         child.setY(14.0F); 
/* 30 */       child.drawComponent(resolution, mouseX, mouseY);
/*    */       
/* 32 */       x += 102.333336F;
/*    */       
/* 34 */       if (x + 8.0F + 94.333336F > 315.0F) {
/* 35 */         x = 8.0F;
/*    */       }
/* 37 */       if (i > 2) {
/*    */         
/* 39 */         int above = i - 3;
/*    */         
/* 41 */         int totalY = 14;
/*    */         
/*    */         do {
/* 44 */           Component componentAbove = getChildren().get(above);
/* 45 */           totalY = (int)(totalY + componentAbove.getHeight() + 8.0F);
/* 46 */           above -= 3;
/* 47 */         } while (above >= 0);
/*    */         
/* 49 */         child.setY(totalY);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Adam\Desktop\Radium\Radium.jar!\vip\radium\gui\csgo\component\TabComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */