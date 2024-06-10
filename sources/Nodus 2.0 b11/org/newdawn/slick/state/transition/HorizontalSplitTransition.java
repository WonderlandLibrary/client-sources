/*   1:    */ package org.newdawn.slick.state.transition;
/*   2:    */ 
/*   3:    */ import org.newdawn.slick.Color;
/*   4:    */ import org.newdawn.slick.GameContainer;
/*   5:    */ import org.newdawn.slick.Graphics;
/*   6:    */ import org.newdawn.slick.SlickException;
/*   7:    */ import org.newdawn.slick.opengl.renderer.Renderer;
/*   8:    */ import org.newdawn.slick.opengl.renderer.SGL;
/*   9:    */ import org.newdawn.slick.state.GameState;
/*  10:    */ import org.newdawn.slick.state.StateBasedGame;
/*  11:    */ 
/*  12:    */ public class HorizontalSplitTransition
/*  13:    */   implements Transition
/*  14:    */ {
/*  15: 22 */   protected static SGL GL = ;
/*  16:    */   private GameState prev;
/*  17:    */   private float offset;
/*  18:    */   private boolean finish;
/*  19:    */   private Color background;
/*  20:    */   
/*  21:    */   public HorizontalSplitTransition() {}
/*  22:    */   
/*  23:    */   public HorizontalSplitTransition(Color background)
/*  24:    */   {
/*  25: 46 */     this.background = background;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void init(GameState firstState, GameState secondState)
/*  29:    */   {
/*  30: 53 */     this.prev = secondState;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public boolean isComplete()
/*  34:    */   {
/*  35: 60 */     return this.finish;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void postRender(StateBasedGame game, GameContainer container, Graphics g)
/*  39:    */     throws SlickException
/*  40:    */   {
/*  41: 67 */     g.translate(-this.offset, 0.0F);
/*  42: 68 */     g.setClip((int)-this.offset, 0, container.getWidth() / 2, container.getHeight());
/*  43: 69 */     if (this.background != null)
/*  44:    */     {
/*  45: 70 */       Color c = g.getColor();
/*  46: 71 */       g.setColor(this.background);
/*  47: 72 */       g.fillRect(0.0F, 0.0F, container.getWidth(), container.getHeight());
/*  48: 73 */       g.setColor(c);
/*  49:    */     }
/*  50: 75 */     GL.glPushMatrix();
/*  51: 76 */     this.prev.render(container, game, g);
/*  52: 77 */     GL.glPopMatrix();
/*  53: 78 */     g.clearClip();
/*  54:    */     
/*  55: 80 */     g.translate(this.offset * 2.0F, 0.0F);
/*  56: 81 */     g.setClip((int)(container.getWidth() / 2 + this.offset), 0, container.getWidth() / 2, container.getHeight());
/*  57: 82 */     if (this.background != null)
/*  58:    */     {
/*  59: 83 */       Color c = g.getColor();
/*  60: 84 */       g.setColor(this.background);
/*  61: 85 */       g.fillRect(0.0F, 0.0F, container.getWidth(), container.getHeight());
/*  62: 86 */       g.setColor(c);
/*  63:    */     }
/*  64: 88 */     GL.glPushMatrix();
/*  65: 89 */     this.prev.render(container, game, g);
/*  66: 90 */     GL.glPopMatrix();
/*  67: 91 */     g.clearClip();
/*  68: 92 */     g.translate(-this.offset, 0.0F);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void preRender(StateBasedGame game, GameContainer container, Graphics g)
/*  72:    */     throws SlickException
/*  73:    */   {}
/*  74:    */   
/*  75:    */   public void update(StateBasedGame game, GameContainer container, int delta)
/*  76:    */     throws SlickException
/*  77:    */   {
/*  78:107 */     this.offset += delta * 1.0F;
/*  79:108 */     if (this.offset > container.getWidth() / 2) {
/*  80:109 */       this.finish = true;
/*  81:    */     }
/*  82:    */   }
/*  83:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.state.transition.HorizontalSplitTransition
 * JD-Core Version:    0.7.0.1
 */