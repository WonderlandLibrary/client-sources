/*   1:    */ package org.newdawn.slick;
/*   2:    */ 
/*   3:    */ import java.awt.Canvas;
/*   4:    */ import javax.swing.SwingUtilities;
/*   5:    */ import org.lwjgl.LWJGLException;
/*   6:    */ import org.lwjgl.opengl.Display;
/*   7:    */ import org.newdawn.slick.util.Log;
/*   8:    */ 
/*   9:    */ public class CanvasGameContainer
/*  10:    */   extends Canvas
/*  11:    */ {
/*  12:    */   protected Container container;
/*  13:    */   protected Game game;
/*  14:    */   
/*  15:    */   public CanvasGameContainer(Game game)
/*  16:    */     throws SlickException
/*  17:    */   {
/*  18: 29 */     this(game, false);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public CanvasGameContainer(Game game, boolean shared)
/*  22:    */     throws SlickException
/*  23:    */   {
/*  24: 43 */     this.game = game;
/*  25: 44 */     setIgnoreRepaint(true);
/*  26: 45 */     requestFocus();
/*  27: 46 */     setSize(500, 500);
/*  28:    */     
/*  29: 48 */     this.container = new Container(game, shared);
/*  30: 49 */     this.container.setForceExit(false);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void start()
/*  34:    */     throws SlickException
/*  35:    */   {
/*  36: 58 */     SwingUtilities.invokeLater(new Runnable()
/*  37:    */     {
/*  38:    */       public void run()
/*  39:    */       {
/*  40:    */         try
/*  41:    */         {
/*  42:    */           
/*  43:    */           try
/*  44:    */           {
/*  45: 64 */             Display.setParent(CanvasGameContainer.this);
/*  46:    */           }
/*  47:    */           catch (LWJGLException e)
/*  48:    */           {
/*  49: 66 */             throw new SlickException("Failed to setParent of canvas", e);
/*  50:    */           }
/*  51: 69 */           CanvasGameContainer.this.container.setup();
/*  52: 70 */           CanvasGameContainer.this.scheduleUpdate();
/*  53:    */         }
/*  54:    */         catch (SlickException e)
/*  55:    */         {
/*  56: 72 */           e.printStackTrace();
/*  57: 73 */           System.exit(0);
/*  58:    */         }
/*  59:    */       }
/*  60:    */     });
/*  61:    */   }
/*  62:    */   
/*  63:    */   private void scheduleUpdate()
/*  64:    */   {
/*  65: 83 */     if (!isVisible()) {
/*  66: 84 */       return;
/*  67:    */     }
/*  68: 87 */     SwingUtilities.invokeLater(new Runnable()
/*  69:    */     {
/*  70:    */       public void run()
/*  71:    */       {
/*  72:    */         try
/*  73:    */         {
/*  74: 90 */           CanvasGameContainer.this.container.gameLoop();
/*  75:    */         }
/*  76:    */         catch (SlickException e)
/*  77:    */         {
/*  78: 92 */           e.printStackTrace();
/*  79:    */         }
/*  80: 94 */         CanvasGameContainer.this.container.checkDimensions();
/*  81: 95 */         CanvasGameContainer.this.scheduleUpdate();
/*  82:    */       }
/*  83:    */     });
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void dispose() {}
/*  87:    */   
/*  88:    */   public GameContainer getContainer()
/*  89:    */   {
/*  90:111 */     return this.container;
/*  91:    */   }
/*  92:    */   
/*  93:    */   private class Container
/*  94:    */     extends AppGameContainer
/*  95:    */   {
/*  96:    */     public Container(Game game, boolean shared)
/*  97:    */       throws SlickException
/*  98:    */     {
/*  99:130 */       super(CanvasGameContainer.this.getWidth(), CanvasGameContainer.this.getHeight(), false);
/* 100:    */       
/* 101:132 */       this.width = CanvasGameContainer.this.getWidth();
/* 102:133 */       this.height = CanvasGameContainer.this.getHeight();
/* 103:135 */       if (shared) {
/* 104:136 */         enableSharedContext();
/* 105:    */       }
/* 106:    */     }
/* 107:    */     
/* 108:    */     protected void updateFPS()
/* 109:    */     {
/* 110:144 */       super.updateFPS();
/* 111:    */     }
/* 112:    */     
/* 113:    */     protected boolean running()
/* 114:    */     {
/* 115:151 */       return (super.running()) && (CanvasGameContainer.this.isDisplayable());
/* 116:    */     }
/* 117:    */     
/* 118:    */     public int getHeight()
/* 119:    */     {
/* 120:158 */       return CanvasGameContainer.this.getHeight();
/* 121:    */     }
/* 122:    */     
/* 123:    */     public int getWidth()
/* 124:    */     {
/* 125:165 */       return CanvasGameContainer.this.getWidth();
/* 126:    */     }
/* 127:    */     
/* 128:    */     public void checkDimensions()
/* 129:    */     {
/* 130:172 */       if ((this.width != CanvasGameContainer.this.getWidth()) || 
/* 131:173 */         (this.height != CanvasGameContainer.this.getHeight())) {
/* 132:    */         try
/* 133:    */         {
/* 134:176 */           setDisplayMode(CanvasGameContainer.this.getWidth(), 
/* 135:177 */             CanvasGameContainer.this.getHeight(), false);
/* 136:    */         }
/* 137:    */         catch (SlickException e)
/* 138:    */         {
/* 139:179 */           Log.error(e);
/* 140:    */         }
/* 141:    */       }
/* 142:    */     }
/* 143:    */   }
/* 144:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.CanvasGameContainer
 * JD-Core Version:    0.7.0.1
 */