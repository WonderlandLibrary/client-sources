/*   1:    */ package org.newdawn.slick.state.transition;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import org.newdawn.slick.Color;
/*   5:    */ import org.newdawn.slick.GameContainer;
/*   6:    */ import org.newdawn.slick.Graphics;
/*   7:    */ import org.newdawn.slick.SlickException;
/*   8:    */ import org.newdawn.slick.opengl.renderer.Renderer;
/*   9:    */ import org.newdawn.slick.opengl.renderer.SGL;
/*  10:    */ import org.newdawn.slick.state.GameState;
/*  11:    */ import org.newdawn.slick.state.StateBasedGame;
/*  12:    */ import org.newdawn.slick.util.MaskUtil;
/*  13:    */ 
/*  14:    */ public class BlobbyTransition
/*  15:    */   implements Transition
/*  16:    */ {
/*  17: 25 */   protected static SGL GL = ;
/*  18:    */   private GameState prev;
/*  19:    */   private boolean finish;
/*  20:    */   private Color background;
/*  21: 34 */   private ArrayList blobs = new ArrayList();
/*  22: 36 */   private int timer = 1000;
/*  23: 38 */   private int blobCount = 10;
/*  24:    */   
/*  25:    */   public BlobbyTransition() {}
/*  26:    */   
/*  27:    */   public BlobbyTransition(Color background)
/*  28:    */   {
/*  29: 53 */     this.background = background;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void init(GameState firstState, GameState secondState)
/*  33:    */   {
/*  34: 60 */     this.prev = secondState;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public boolean isComplete()
/*  38:    */   {
/*  39: 67 */     return this.finish;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void postRender(StateBasedGame game, GameContainer container, Graphics g)
/*  43:    */     throws SlickException
/*  44:    */   {}
/*  45:    */   
/*  46:    */   public void preRender(StateBasedGame game, GameContainer container, Graphics g)
/*  47:    */     throws SlickException
/*  48:    */   {
/*  49: 82 */     this.prev.render(container, game, g);
/*  50:    */     
/*  51: 84 */     MaskUtil.defineMask();
/*  52: 85 */     for (int i = 0; i < this.blobs.size(); i++) {
/*  53: 86 */       ((Blob)this.blobs.get(i)).render(g);
/*  54:    */     }
/*  55: 88 */     MaskUtil.finishDefineMask();
/*  56:    */     
/*  57: 90 */     MaskUtil.drawOnMask();
/*  58: 91 */     if (this.background != null)
/*  59:    */     {
/*  60: 92 */       Color c = g.getColor();
/*  61: 93 */       g.setColor(this.background);
/*  62: 94 */       g.fillRect(0.0F, 0.0F, container.getWidth(), container.getHeight());
/*  63: 95 */       g.setColor(c);
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void update(StateBasedGame game, GameContainer container, int delta)
/*  68:    */     throws SlickException
/*  69:    */   {
/*  70:104 */     if (this.blobs.size() == 0) {
/*  71:105 */       for (int i = 0; i < this.blobCount; i++) {
/*  72:106 */         this.blobs.add(new Blob(container));
/*  73:    */       }
/*  74:    */     }
/*  75:110 */     for (int i = 0; i < this.blobs.size(); i++) {
/*  76:111 */       ((Blob)this.blobs.get(i)).update(delta);
/*  77:    */     }
/*  78:114 */     this.timer -= delta;
/*  79:115 */     if (this.timer < 0) {
/*  80:116 */       this.finish = true;
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   private class Blob
/*  85:    */   {
/*  86:    */     private float x;
/*  87:    */     private float y;
/*  88:    */     private float growSpeed;
/*  89:    */     private float rad;
/*  90:    */     
/*  91:    */     public Blob(GameContainer container)
/*  92:    */     {
/*  93:141 */       this.x = ((float)(Math.random() * container.getWidth()));
/*  94:142 */       this.y = ((float)(Math.random() * container.getWidth()));
/*  95:143 */       this.growSpeed = ((float)(1.0D + Math.random() * 1.0D));
/*  96:    */     }
/*  97:    */     
/*  98:    */     public void update(int delta)
/*  99:    */     {
/* 100:152 */       this.rad += this.growSpeed * delta * 0.4F;
/* 101:    */     }
/* 102:    */     
/* 103:    */     public void render(Graphics g)
/* 104:    */     {
/* 105:161 */       g.fillOval(this.x - this.rad, this.y - this.rad, this.rad * 2.0F, this.rad * 2.0F);
/* 106:    */     }
/* 107:    */   }
/* 108:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.state.transition.BlobbyTransition
 * JD-Core Version:    0.7.0.1
 */