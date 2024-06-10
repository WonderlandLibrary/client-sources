/*  1:   */ package org.newdawn.slick.tests;
/*  2:   */ 
/*  3:   */ import java.awt.Frame;
/*  4:   */ import java.awt.GridLayout;
/*  5:   */ import java.awt.event.WindowAdapter;
/*  6:   */ import java.awt.event.WindowEvent;
/*  7:   */ import java.io.PrintStream;
/*  8:   */ import org.newdawn.slick.BasicGame;
/*  9:   */ import org.newdawn.slick.CanvasGameContainer;
/* 10:   */ import org.newdawn.slick.GameContainer;
/* 11:   */ import org.newdawn.slick.Graphics;
/* 12:   */ import org.newdawn.slick.SlickException;
/* 13:   */ import org.newdawn.slick.util.Log;
/* 14:   */ 
/* 15:   */ public class CanvasSizeTest
/* 16:   */   extends BasicGame
/* 17:   */ {
/* 18:   */   public CanvasSizeTest()
/* 19:   */   {
/* 20:26 */     super("Test");
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void init(GameContainer container)
/* 24:   */     throws SlickException
/* 25:   */   {
/* 26:33 */     System.out.println(container.getWidth() + ", " + container.getHeight());
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void render(GameContainer container, Graphics g)
/* 30:   */     throws SlickException
/* 31:   */   {}
/* 32:   */   
/* 33:   */   public void update(GameContainer container, int delta)
/* 34:   */     throws SlickException
/* 35:   */   {}
/* 36:   */   
/* 37:   */   public static void main(String[] args)
/* 38:   */   {
/* 39:   */     try
/* 40:   */     {
/* 41:57 */       CanvasGameContainer container = new CanvasGameContainer(
/* 42:58 */         new CanvasSizeTest());
/* 43:59 */       container.setSize(640, 480);
/* 44:60 */       Frame frame = new Frame("Test");
/* 45:61 */       frame.setLayout(new GridLayout(1, 2));
/* 46:62 */       frame.add(container);
/* 47:63 */       frame.pack();
/* 48:64 */       frame.addWindowListener(new WindowAdapter()
/* 49:   */       {
/* 50:   */         public void windowClosing(WindowEvent e)
/* 51:   */         {
/* 52:66 */           System.exit(0);
/* 53:   */         }
/* 54:68 */       });
/* 55:69 */       frame.setVisible(true);
/* 56:   */       
/* 57:71 */       container.start();
/* 58:   */     }
/* 59:   */     catch (Exception e)
/* 60:   */     {
/* 61:73 */       Log.error(e);
/* 62:   */     }
/* 63:   */   }
/* 64:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.CanvasSizeTest
 * JD-Core Version:    0.7.0.1
 */