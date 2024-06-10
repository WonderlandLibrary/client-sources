/*     */ package nightmare.gui.window;
/*     */ 
/*     */ public class Window {
/*     */   private String name;
/*     */   private int x;
/*     */   private int y;
/*     */   private int width;
/*     */   private int height;
/*     */   private int draggingX;
/*     */   
/*     */   public Window(String name, int x, int y, int width, int height, boolean drag, boolean showWindow) {
/*  12 */     this.name = name;
/*  13 */     this.x = x;
/*  14 */     this.y = y;
/*  15 */     this.width = x + width;
/*  16 */     this.height = y + height;
/*  17 */     this.drag = drag;
/*  18 */     this.dragging = false;
/*  19 */     this.showWindow = showWindow;
/*     */   }
/*     */   private int draggingY; private int draggingWidth; private int draggingHeight; private boolean drag; private boolean dragging; private boolean showWindow;
/*     */   public void onRender() {}
/*     */   
/*     */   public String getName() {
/*  25 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/*  29 */     this.name = name;
/*     */   }
/*     */   
/*     */   public int getX() {
/*  33 */     return this.x;
/*     */   }
/*     */   
/*     */   public void setX(int x) {
/*  37 */     this.x = x;
/*     */   }
/*     */   
/*     */   public int getY() {
/*  41 */     return this.y;
/*     */   }
/*     */   
/*     */   public void setY(int y) {
/*  45 */     this.y = y;
/*     */   }
/*     */   
/*     */   public int getWidth() {
/*  49 */     return this.width;
/*     */   }
/*     */   
/*     */   public void setWidth(int width) {
/*  53 */     this.width = width;
/*     */   }
/*     */   
/*     */   public int getHeight() {
/*  57 */     return this.height;
/*     */   }
/*     */   
/*     */   public void setHeight(int height) {
/*  61 */     this.height = height;
/*     */   }
/*     */   
/*     */   public int getDraggingX() {
/*  65 */     return this.draggingX;
/*     */   }
/*     */   
/*     */   public void setDraggingX(int draggingX) {
/*  69 */     this.draggingX = draggingX;
/*     */   }
/*     */   
/*     */   public int getDraggingY() {
/*  73 */     return this.draggingY;
/*     */   }
/*     */   
/*     */   public void setDraggingY(int draggingY) {
/*  77 */     this.draggingY = draggingY;
/*     */   }
/*     */   
/*     */   public int getDraggingWidth() {
/*  81 */     return this.draggingWidth;
/*     */   }
/*     */   
/*     */   public void setDraggingWidth(int draggingWidth) {
/*  85 */     this.draggingWidth = draggingWidth;
/*     */   }
/*     */   
/*     */   public int getDraggingHeight() {
/*  89 */     return this.draggingHeight;
/*     */   }
/*     */   
/*     */   public void setDraggingHeight(int draggingHeight) {
/*  93 */     this.draggingHeight = draggingHeight;
/*     */   }
/*     */   
/*     */   public boolean isDrag() {
/*  97 */     return this.drag;
/*     */   }
/*     */   
/*     */   public void setDrag(boolean drag) {
/* 101 */     this.drag = drag;
/*     */   }
/*     */   
/*     */   public boolean isDragging() {
/* 105 */     return this.dragging;
/*     */   }
/*     */   
/*     */   public void setDragging(boolean dragging) {
/* 109 */     this.dragging = dragging;
/*     */   }
/*     */   
/*     */   public boolean isShowWindow() {
/* 113 */     return this.showWindow;
/*     */   }
/*     */   
/*     */   public void setShowWindow(boolean showWindow) {
/* 117 */     this.showWindow = showWindow;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\gui\window\Window.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */