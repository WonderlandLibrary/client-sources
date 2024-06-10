/*   1:    */ package org.newdawn.slick;
/*   2:    */ 
/*   3:    */ import org.newdawn.slick.opengl.SlickCallable;
/*   4:    */ import org.newdawn.slick.opengl.renderer.Renderer;
/*   5:    */ import org.newdawn.slick.opengl.renderer.SGL;
/*   6:    */ 
/*   7:    */ public class ScalableGame
/*   8:    */   implements Game
/*   9:    */ {
/*  10: 19 */   private static SGL GL = ;
/*  11:    */   private float normalWidth;
/*  12:    */   private float normalHeight;
/*  13:    */   private Game held;
/*  14:    */   private boolean maintainAspect;
/*  15:    */   private int targetWidth;
/*  16:    */   private int targetHeight;
/*  17:    */   private GameContainer container;
/*  18:    */   
/*  19:    */   public ScalableGame(Game held, int normalWidth, int normalHeight)
/*  20:    */   {
/*  21: 44 */     this(held, normalWidth, normalHeight, false);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public ScalableGame(Game held, int normalWidth, int normalHeight, boolean maintainAspect)
/*  25:    */   {
/*  26: 56 */     this.held = held;
/*  27: 57 */     this.normalWidth = normalWidth;
/*  28: 58 */     this.normalHeight = normalHeight;
/*  29: 59 */     this.maintainAspect = maintainAspect;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void init(GameContainer container)
/*  33:    */     throws SlickException
/*  34:    */   {
/*  35: 66 */     this.container = container;
/*  36:    */     
/*  37: 68 */     recalculateScale();
/*  38: 69 */     this.held.init(container);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void recalculateScale()
/*  42:    */     throws SlickException
/*  43:    */   {
/*  44: 78 */     this.targetWidth = this.container.getWidth();
/*  45: 79 */     this.targetHeight = this.container.getHeight();
/*  46: 81 */     if (this.maintainAspect)
/*  47:    */     {
/*  48: 82 */       boolean normalIsWide = this.normalWidth / this.normalHeight > 1.6D;
/*  49: 83 */       boolean containerIsWide = this.targetWidth / this.targetHeight > 1.6D;
/*  50: 84 */       float wScale = this.targetWidth / this.normalWidth;
/*  51: 85 */       float hScale = this.targetHeight / this.normalHeight;
/*  52: 87 */       if ((normalIsWide & containerIsWide))
/*  53:    */       {
/*  54: 88 */         float scale = wScale < hScale ? wScale : hScale;
/*  55: 89 */         this.targetWidth = ((int)(this.normalWidth * scale));
/*  56: 90 */         this.targetHeight = ((int)(this.normalHeight * scale));
/*  57:    */       }
/*  58: 91 */       else if ((normalIsWide & !containerIsWide))
/*  59:    */       {
/*  60: 92 */         this.targetWidth = ((int)(this.normalWidth * wScale));
/*  61: 93 */         this.targetHeight = ((int)(this.normalHeight * wScale));
/*  62:    */       }
/*  63: 94 */       else if ((!normalIsWide & containerIsWide))
/*  64:    */       {
/*  65: 95 */         this.targetWidth = ((int)(this.normalWidth * hScale));
/*  66: 96 */         this.targetHeight = ((int)(this.normalHeight * hScale));
/*  67:    */       }
/*  68:    */       else
/*  69:    */       {
/*  70: 98 */         float scale = wScale < hScale ? wScale : hScale;
/*  71: 99 */         this.targetWidth = ((int)(this.normalWidth * scale));
/*  72:100 */         this.targetHeight = ((int)(this.normalHeight * scale));
/*  73:    */       }
/*  74:    */     }
/*  75:105 */     if ((this.held instanceof InputListener)) {
/*  76:106 */       this.container.getInput().addListener((InputListener)this.held);
/*  77:    */     }
/*  78:108 */     this.container.getInput().setScale(this.normalWidth / this.targetWidth, 
/*  79:109 */       this.normalHeight / this.targetHeight);
/*  80:    */     
/*  81:    */ 
/*  82:112 */     int yoffset = 0;
/*  83:113 */     int xoffset = 0;
/*  84:115 */     if (this.targetHeight < this.container.getHeight()) {
/*  85:116 */       yoffset = (this.container.getHeight() - this.targetHeight) / 2;
/*  86:    */     }
/*  87:118 */     if (this.targetWidth < this.container.getWidth()) {
/*  88:119 */       xoffset = (this.container.getWidth() - this.targetWidth) / 2;
/*  89:    */     }
/*  90:121 */     this.container.getInput().setOffset(-xoffset / (this.targetWidth / this.normalWidth), 
/*  91:122 */       -yoffset / (this.targetHeight / this.normalHeight));
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void update(GameContainer container, int delta)
/*  95:    */     throws SlickException
/*  96:    */   {
/*  97:130 */     if ((this.targetHeight != container.getHeight()) || 
/*  98:131 */       (this.targetWidth != container.getWidth())) {
/*  99:132 */       recalculateScale();
/* 100:    */     }
/* 101:135 */     this.held.update(container, delta);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public final void render(GameContainer container, Graphics g)
/* 105:    */     throws SlickException
/* 106:    */   {
/* 107:143 */     int yoffset = 0;
/* 108:144 */     int xoffset = 0;
/* 109:146 */     if (this.targetHeight < container.getHeight()) {
/* 110:147 */       yoffset = (container.getHeight() - this.targetHeight) / 2;
/* 111:    */     }
/* 112:149 */     if (this.targetWidth < container.getWidth()) {
/* 113:150 */       xoffset = (container.getWidth() - this.targetWidth) / 2;
/* 114:    */     }
/* 115:153 */     SlickCallable.enterSafeBlock();
/* 116:154 */     g.setClip(xoffset, yoffset, this.targetWidth, this.targetHeight);
/* 117:155 */     GL.glTranslatef(xoffset, yoffset, 0.0F);
/* 118:156 */     g.scale(this.targetWidth / this.normalWidth, this.targetHeight / this.normalHeight);
/* 119:157 */     GL.glPushMatrix();
/* 120:158 */     this.held.render(container, g);
/* 121:159 */     GL.glPopMatrix();
/* 122:160 */     g.clearClip();
/* 123:161 */     SlickCallable.leaveSafeBlock();
/* 124:    */     
/* 125:163 */     renderOverlay(container, g);
/* 126:    */   }
/* 127:    */   
/* 128:    */   protected void renderOverlay(GameContainer container, Graphics g) {}
/* 129:    */   
/* 130:    */   public boolean closeRequested()
/* 131:    */   {
/* 132:179 */     return this.held.closeRequested();
/* 133:    */   }
/* 134:    */   
/* 135:    */   public String getTitle()
/* 136:    */   {
/* 137:186 */     return this.held.getTitle();
/* 138:    */   }
/* 139:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.ScalableGame
 * JD-Core Version:    0.7.0.1
 */