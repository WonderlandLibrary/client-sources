
package Focus.Beta.UTILS.render;


public final class LockedResolution
 {
   public static final int SCALE_FACTOR = 2;
   private final int width;
  private final int height;

   public LockedResolution(int width, int height) {
  this.width = width;
   this.height = height;
 }

  public int getWidth() {
/* 16 */     return this.width;
/*    */   }

 public int getHeight() {
/* 20 */     return this.height;
/*    */   }
 }