/*     */ package org.newdawn.slick;
/*     */ 
/*     */ import java.awt.Canvas;
/*     */ import javax.swing.SwingUtilities;
/*     */ import org.lwjgl.LWJGLException;
/*     */ import org.lwjgl.opengl.Display;
/*     */ import org.newdawn.slick.util.Log;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CanvasGameContainer
/*     */   extends Canvas
/*     */ {
/*     */   protected Container container;
/*     */   protected Game game;
/*     */   
/*     */   public CanvasGameContainer(Game game) throws SlickException {
/*  29 */     this(game, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CanvasGameContainer(Game game, boolean shared) throws SlickException {
/*  43 */     this.game = game;
/*  44 */     setIgnoreRepaint(true);
/*  45 */     requestFocus();
/*  46 */     setSize(500, 500);
/*     */     
/*  48 */     this.container = new Container(game, shared);
/*  49 */     this.container.setForceExit(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() throws SlickException {
/*  58 */     SwingUtilities.invokeLater(new Runnable() {
/*     */           public void run() {
/*     */             try {
/*  61 */               Input.disableControllers();
/*     */               
/*     */               try {
/*  64 */                 Display.setParent(CanvasGameContainer.this);
/*  65 */               } catch (LWJGLException e) {
/*  66 */                 throw new SlickException("Failed to setParent of canvas", e);
/*     */               } 
/*     */               
/*  69 */               CanvasGameContainer.this.container.setup();
/*  70 */               CanvasGameContainer.this.scheduleUpdate();
/*  71 */             } catch (SlickException e) {
/*  72 */               e.printStackTrace();
/*  73 */               System.exit(0);
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void scheduleUpdate() {
/*  83 */     if (!isVisible()) {
/*     */       return;
/*     */     }
/*     */     
/*  87 */     SwingUtilities.invokeLater(new Runnable() {
/*     */           public void run() {
/*     */             try {
/*  90 */               CanvasGameContainer.this.container.gameLoop();
/*  91 */             } catch (SlickException e) {
/*  92 */               e.printStackTrace();
/*     */             } 
/*  94 */             CanvasGameContainer.this.container.checkDimensions();
/*  95 */             CanvasGameContainer.this.scheduleUpdate();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispose() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GameContainer getContainer() {
/* 111 */     return this.container;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class Container
/*     */     extends AppGameContainer
/*     */   {
/*     */     public Container(Game game, boolean shared) throws SlickException {
/* 130 */       super(game, CanvasGameContainer.this.getWidth(), CanvasGameContainer.this.getHeight(), false);
/*     */       
/* 132 */       this.width = CanvasGameContainer.this.getWidth();
/* 133 */       this.height = CanvasGameContainer.this.getHeight();
/*     */       
/* 135 */       if (shared) {
/* 136 */         enableSharedContext();
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void updateFPS() {
/* 144 */       super.updateFPS();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean running() {
/* 151 */       return (super.running() && CanvasGameContainer.this.isDisplayable());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getHeight() {
/* 158 */       return CanvasGameContainer.this.getHeight();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getWidth() {
/* 165 */       return CanvasGameContainer.this.getWidth();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void checkDimensions() {
/* 172 */       if (this.width != CanvasGameContainer.this.getWidth() || this.height != CanvasGameContainer.this.getHeight())
/*     */         
/*     */         try {
/*     */           
/* 176 */           setDisplayMode(CanvasGameContainer.this.getWidth(), CanvasGameContainer.this.getHeight(), false);
/*     */         }
/* 178 */         catch (SlickException e) {
/* 179 */           Log.error(e);
/*     */         }  
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\CanvasGameContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */