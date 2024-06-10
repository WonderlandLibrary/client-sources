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
/*  12:    */ public class VerticalSplitTransition
/*  13:    */   implements Transition
/*  14:    */ {
/*  15: 22 */   protected static SGL GL = ;
/*  16:    */   private GameState prev;
/*  17:    */   private float offset;
/*  18:    */   private boolean finish;
/*  19:    */   private Color background;
/*  20:    */   
/*  21:    */   public VerticalSplitTransition() {}
/*  22:    */   
/*  23:    */   public VerticalSplitTransition(Color background)
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
/*  41: 67 */     g.translate(0.0F, -this.offset);
/*  42: 68 */     g.setClip(0, (int)-this.offset, container.getWidth(), container.getHeight() / 2);
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
/*  54: 79 */     g.resetTransform();
/*  55:    */     
/*  56: 81 */     g.translate(0.0F, this.offset);
/*  57: 82 */     g.setClip(0, (int)(container.getHeight() / 2 + this.offset), container.getWidth(), container.getHeight() / 2);
/*  58: 83 */     if (this.background != null)
/*  59:    */     {
/*  60: 84 */       Color c = g.getColor();
/*  61: 85 */       g.setColor(this.background);
/*  62: 86 */       g.fillRect(0.0F, 0.0F, container.getWidth(), container.getHeight());
/*  63: 87 */       g.setColor(c);
/*  64:    */     }
/*  65: 89 */     GL.glPushMatrix();
/*  66: 90 */     this.prev.render(container, game, g);
/*  67: 91 */     GL.glPopMatrix();
/*  68: 92 */     g.clearClip();
/*  69: 93 */     g.translate(0.0F, -this.offset);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void preRender(StateBasedGame game, GameContainer container, Graphics g)
/*  73:    */     throws SlickException
/*  74:    */   {}
/*  75:    */   
/*  76:    */   public void update(StateBasedGame game, GameContainer container, int delta)
/*  77:    */     throws SlickException
/*  78:    */   {
/*  79:108 */     this.offset += delta * 1.0F;
/*  80:109 */     if (this.offset > container.getHeight() / 2) {
/*  81:110 */       this.finish = true;
/*  82:    */     }
/*  83:    */   }
/*  84:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.state.transition.VerticalSplitTransition
 * JD-Core Version:    0.7.0.1
 */