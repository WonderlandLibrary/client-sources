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
/*  12:    */ public class SelectTransition
/*  13:    */   implements Transition
/*  14:    */ {
/*  15: 23 */   protected static SGL GL = ;
/*  16:    */   private GameState prev;
/*  17:    */   private boolean finish;
/*  18:    */   private Color background;
/*  19: 33 */   private float scale1 = 1.0F;
/*  20: 35 */   private float xp1 = 0.0F;
/*  21: 37 */   private float yp1 = 0.0F;
/*  22: 39 */   private float scale2 = 0.4F;
/*  23: 41 */   private float xp2 = 0.0F;
/*  24: 43 */   private float yp2 = 0.0F;
/*  25: 45 */   private boolean init = false;
/*  26: 48 */   private boolean moveBackDone = false;
/*  27: 50 */   private int pause = 300;
/*  28:    */   
/*  29:    */   public SelectTransition() {}
/*  30:    */   
/*  31:    */   public SelectTransition(Color background)
/*  32:    */   {
/*  33: 65 */     this.background = background;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void init(GameState firstState, GameState secondState)
/*  37:    */   {
/*  38: 72 */     this.prev = secondState;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public boolean isComplete()
/*  42:    */   {
/*  43: 79 */     return this.finish;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void postRender(StateBasedGame game, GameContainer container, Graphics g)
/*  47:    */     throws SlickException
/*  48:    */   {
/*  49: 86 */     g.resetTransform();
/*  50: 88 */     if (!this.moveBackDone)
/*  51:    */     {
/*  52: 89 */       g.translate(this.xp1, this.yp1);
/*  53: 90 */       g.scale(this.scale1, this.scale1);
/*  54: 91 */       g.setClip((int)this.xp1, (int)this.yp1, (int)(this.scale1 * container.getWidth()), (int)(this.scale1 * container.getHeight()));
/*  55: 92 */       this.prev.render(container, game, g);
/*  56: 93 */       g.resetTransform();
/*  57: 94 */       g.clearClip();
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void preRender(StateBasedGame game, GameContainer container, Graphics g)
/*  62:    */     throws SlickException
/*  63:    */   {
/*  64:103 */     if (this.moveBackDone)
/*  65:    */     {
/*  66:104 */       g.translate(this.xp1, this.yp1);
/*  67:105 */       g.scale(this.scale1, this.scale1);
/*  68:106 */       g.setClip((int)this.xp1, (int)this.yp1, (int)(this.scale1 * container.getWidth()), (int)(this.scale1 * container.getHeight()));
/*  69:107 */       this.prev.render(container, game, g);
/*  70:108 */       g.resetTransform();
/*  71:109 */       g.clearClip();
/*  72:    */     }
/*  73:112 */     g.translate(this.xp2, this.yp2);
/*  74:113 */     g.scale(this.scale2, this.scale2);
/*  75:114 */     g.setClip((int)this.xp2, (int)this.yp2, (int)(this.scale2 * container.getWidth()), (int)(this.scale2 * container.getHeight()));
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void update(StateBasedGame game, GameContainer container, int delta)
/*  79:    */     throws SlickException
/*  80:    */   {
/*  81:122 */     if (!this.init)
/*  82:    */     {
/*  83:123 */       this.init = true;
/*  84:124 */       this.xp2 = (container.getWidth() / 2 + 50);
/*  85:125 */       this.yp2 = (container.getHeight() / 4);
/*  86:    */     }
/*  87:128 */     if (!this.moveBackDone)
/*  88:    */     {
/*  89:129 */       if (this.scale1 > 0.4F)
/*  90:    */       {
/*  91:130 */         this.scale1 -= delta * 0.002F;
/*  92:131 */         if (this.scale1 <= 0.4F) {
/*  93:132 */           this.scale1 = 0.4F;
/*  94:    */         }
/*  95:134 */         this.xp1 += delta * 0.3F;
/*  96:135 */         if (this.xp1 > 50.0F) {
/*  97:136 */           this.xp1 = 50.0F;
/*  98:    */         }
/*  99:138 */         this.yp1 += delta * 0.5F;
/* 100:139 */         if (this.yp1 > container.getHeight() / 4) {
/* 101:140 */           this.yp1 = (container.getHeight() / 4);
/* 102:    */         }
/* 103:    */       }
/* 104:    */       else
/* 105:    */       {
/* 106:143 */         this.moveBackDone = true;
/* 107:    */       }
/* 108:    */     }
/* 109:    */     else
/* 110:    */     {
/* 111:146 */       this.pause -= delta;
/* 112:147 */       if (this.pause > 0) {
/* 113:148 */         return;
/* 114:    */       }
/* 115:150 */       if (this.scale2 < 1.0F)
/* 116:    */       {
/* 117:151 */         this.scale2 += delta * 0.002F;
/* 118:152 */         if (this.scale2 >= 1.0F) {
/* 119:153 */           this.scale2 = 1.0F;
/* 120:    */         }
/* 121:155 */         this.xp2 -= delta * 1.5F;
/* 122:156 */         if (this.xp2 < 0.0F) {
/* 123:157 */           this.xp2 = 0.0F;
/* 124:    */         }
/* 125:159 */         this.yp2 -= delta * 0.5F;
/* 126:160 */         if (this.yp2 < 0.0F) {
/* 127:161 */           this.yp2 = 0.0F;
/* 128:    */         }
/* 129:    */       }
/* 130:    */       else
/* 131:    */       {
/* 132:164 */         this.finish = true;
/* 133:    */       }
/* 134:    */     }
/* 135:    */   }
/* 136:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.state.transition.SelectTransition
 * JD-Core Version:    0.7.0.1
 */