/*   1:    */ package org.newdawn.slick.tests;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import org.newdawn.slick.AngelCodeFont;
/*   5:    */ import org.newdawn.slick.AppGameContainer;
/*   6:    */ import org.newdawn.slick.BasicGame;
/*   7:    */ import org.newdawn.slick.Color;
/*   8:    */ import org.newdawn.slick.Font;
/*   9:    */ import org.newdawn.slick.GameContainer;
/*  10:    */ import org.newdawn.slick.Graphics;
/*  11:    */ import org.newdawn.slick.Image;
/*  12:    */ import org.newdawn.slick.SlickException;
/*  13:    */ import org.newdawn.slick.gui.AbstractComponent;
/*  14:    */ import org.newdawn.slick.gui.ComponentListener;
/*  15:    */ import org.newdawn.slick.gui.MouseOverArea;
/*  16:    */ import org.newdawn.slick.gui.TextField;
/*  17:    */ import org.newdawn.slick.util.Log;
/*  18:    */ 
/*  19:    */ public class GUITest
/*  20:    */   extends BasicGame
/*  21:    */   implements ComponentListener
/*  22:    */ {
/*  23:    */   private Image image;
/*  24: 28 */   private MouseOverArea[] areas = new MouseOverArea[4];
/*  25:    */   private GameContainer container;
/*  26: 32 */   private String message = "Demo Menu System with stock images";
/*  27:    */   private TextField field;
/*  28:    */   private TextField field2;
/*  29:    */   private Image background;
/*  30:    */   private Font font;
/*  31:    */   private AppGameContainer app;
/*  32:    */   
/*  33:    */   public GUITest()
/*  34:    */   {
/*  35: 48 */     super("GUI Test");
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void init(GameContainer container)
/*  39:    */     throws SlickException
/*  40:    */   {
/*  41: 55 */     if ((container instanceof AppGameContainer))
/*  42:    */     {
/*  43: 56 */       this.app = ((AppGameContainer)container);
/*  44: 57 */       this.app.setIcon("testdata/icon.tga");
/*  45:    */     }
/*  46: 60 */     this.font = new AngelCodeFont("testdata/demo2.fnt", "testdata/demo2_00.tga");
/*  47: 61 */     this.field = new TextField(container, this.font, 150, 20, 500, 35, new ComponentListener()
/*  48:    */     {
/*  49:    */       public void componentActivated(AbstractComponent source)
/*  50:    */       {
/*  51: 63 */         GUITest.this.message = ("Entered1: " + GUITest.this.field.getText());
/*  52: 64 */         GUITest.this.field2.setFocus(true);
/*  53:    */       }
/*  54: 66 */     });
/*  55: 67 */     this.field2 = new TextField(container, this.font, 150, 70, 500, 35, new ComponentListener()
/*  56:    */     {
/*  57:    */       public void componentActivated(AbstractComponent source)
/*  58:    */       {
/*  59: 69 */         GUITest.this.message = ("Entered2: " + GUITest.this.field2.getText());
/*  60: 70 */         GUITest.this.field.setFocus(true);
/*  61:    */       }
/*  62: 72 */     });
/*  63: 73 */     this.field2.setBorderColor(Color.red);
/*  64:    */     
/*  65: 75 */     this.container = container;
/*  66:    */     
/*  67: 77 */     this.image = new Image("testdata/logo.tga");
/*  68: 78 */     this.background = new Image("testdata/dungeontiles.gif");
/*  69: 79 */     container.setMouseCursor("testdata/cursor.tga", 0, 0);
/*  70: 81 */     for (int i = 0; i < 4; i++)
/*  71:    */     {
/*  72: 82 */       this.areas[i] = new MouseOverArea(container, this.image, 300, 100 + i * 100, 200, 90, this);
/*  73: 83 */       this.areas[i].setNormalColor(new Color(1.0F, 1.0F, 1.0F, 0.8F));
/*  74: 84 */       this.areas[i].setMouseOverColor(new Color(1.0F, 1.0F, 1.0F, 0.9F));
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void render(GameContainer container, Graphics g)
/*  79:    */   {
/*  80: 92 */     this.background.draw(0.0F, 0.0F, 800.0F, 500.0F);
/*  81: 94 */     for (int i = 0; i < 4; i++) {
/*  82: 95 */       this.areas[i].render(container, g);
/*  83:    */     }
/*  84: 97 */     this.field.render(container, g);
/*  85: 98 */     this.field2.render(container, g);
/*  86:    */     
/*  87:100 */     g.setFont(this.font);
/*  88:101 */     g.drawString(this.message, 200.0F, 550.0F);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void update(GameContainer container, int delta) {}
/*  92:    */   
/*  93:    */   public void keyPressed(int key, char c)
/*  94:    */   {
/*  95:114 */     if (key == 1) {
/*  96:115 */       System.exit(0);
/*  97:    */     }
/*  98:117 */     if (key == 60) {
/*  99:118 */       this.app.setDefaultMouseCursor();
/* 100:    */     }
/* 101:120 */     if ((key == 59) && 
/* 102:121 */       (this.app != null)) {
/* 103:    */       try
/* 104:    */       {
/* 105:123 */         this.app.setDisplayMode(640, 480, false);
/* 106:    */       }
/* 107:    */       catch (SlickException e)
/* 108:    */       {
/* 109:125 */         Log.error(e);
/* 110:    */       }
/* 111:    */     }
/* 112:    */   }
/* 113:    */   
/* 114:    */   public static void main(String[] argv)
/* 115:    */   {
/* 116:    */     try
/* 117:    */     {
/* 118:138 */       AppGameContainer container = new AppGameContainer(new GUITest());
/* 119:139 */       container.setDisplayMode(800, 600, false);
/* 120:140 */       container.start();
/* 121:    */     }
/* 122:    */     catch (SlickException e)
/* 123:    */     {
/* 124:142 */       e.printStackTrace();
/* 125:    */     }
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void componentActivated(AbstractComponent source)
/* 129:    */   {
/* 130:150 */     System.out.println("ACTIVL : " + source);
/* 131:151 */     for (int i = 0; i < 4; i++) {
/* 132:152 */       if (source == this.areas[i]) {
/* 133:153 */         this.message = ("Option " + (i + 1) + " pressed!");
/* 134:    */       }
/* 135:    */     }
/* 136:    */   }
/* 137:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.GUITest
 * JD-Core Version:    0.7.0.1
 */