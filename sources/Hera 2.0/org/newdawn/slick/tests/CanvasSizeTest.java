/*    */ package org.newdawn.slick.tests;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.awt.Frame;
/*    */ import java.awt.GridLayout;
/*    */ import java.awt.event.WindowAdapter;
/*    */ import java.awt.event.WindowEvent;
/*    */ import org.newdawn.slick.BasicGame;
/*    */ import org.newdawn.slick.CanvasGameContainer;
/*    */ import org.newdawn.slick.Game;
/*    */ import org.newdawn.slick.GameContainer;
/*    */ import org.newdawn.slick.Graphics;
/*    */ import org.newdawn.slick.SlickException;
/*    */ import org.newdawn.slick.util.Log;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CanvasSizeTest
/*    */   extends BasicGame
/*    */ {
/*    */   public CanvasSizeTest() {
/* 26 */     super("Test");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(GameContainer container) throws SlickException {
/* 33 */     System.out.println(container.getWidth() + ", " + container.getHeight());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(GameContainer container, Graphics g) throws SlickException {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update(GameContainer container, int delta) throws SlickException {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void main(String[] args) {
/*    */     try {
/* 57 */       CanvasGameContainer container = new CanvasGameContainer((Game)new CanvasSizeTest());
/*    */       
/* 59 */       container.setSize(640, 480);
/* 60 */       Frame frame = new Frame("Test");
/* 61 */       frame.setLayout(new GridLayout(1, 2));
/* 62 */       frame.add((Component)container);
/* 63 */       frame.pack();
/* 64 */       frame.addWindowListener(new WindowAdapter() {
/*    */             public void windowClosing(WindowEvent e) {
/* 66 */               System.exit(0);
/*    */             }
/*    */           });
/* 69 */       frame.setVisible(true);
/*    */       
/* 71 */       container.start();
/* 72 */     } catch (Exception e) {
/* 73 */       Log.error(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\CanvasSizeTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */