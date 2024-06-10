/*   1:    */ package org.newdawn.slick.tests;
/*   2:    */ 
/*   3:    */ import org.newdawn.slick.AppGameContainer;
/*   4:    */ import org.newdawn.slick.BasicGame;
/*   5:    */ import org.newdawn.slick.GameContainer;
/*   6:    */ import org.newdawn.slick.Graphics;
/*   7:    */ import org.newdawn.slick.Music;
/*   8:    */ import org.newdawn.slick.MusicListener;
/*   9:    */ import org.newdawn.slick.SlickException;
/*  10:    */ 
/*  11:    */ public class MusicListenerTest
/*  12:    */   extends BasicGame
/*  13:    */   implements MusicListener
/*  14:    */ {
/*  15: 19 */   private boolean musicEnded = false;
/*  16: 21 */   private boolean musicSwapped = false;
/*  17:    */   private Music music;
/*  18:    */   private Music stream;
/*  19:    */   
/*  20:    */   public MusicListenerTest()
/*  21:    */   {
/*  22: 31 */     super("Music Listener Test");
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void init(GameContainer container)
/*  26:    */     throws SlickException
/*  27:    */   {
/*  28: 38 */     this.music = new Music("testdata/restart.ogg", false);
/*  29: 39 */     this.stream = new Music("testdata/restart.ogg", false);
/*  30:    */     
/*  31: 41 */     this.music.addListener(this);
/*  32: 42 */     this.stream.addListener(this);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void update(GameContainer container, int delta)
/*  36:    */     throws SlickException
/*  37:    */   {}
/*  38:    */   
/*  39:    */   public void musicEnded(Music music)
/*  40:    */   {
/*  41: 55 */     this.musicEnded = true;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void musicSwapped(Music music, Music newMusic)
/*  45:    */   {
/*  46: 62 */     this.musicSwapped = true;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void render(GameContainer container, Graphics g)
/*  50:    */     throws SlickException
/*  51:    */   {
/*  52: 69 */     g.drawString("Press M to play music", 100.0F, 100.0F);
/*  53: 70 */     g.drawString("Press S to stream music", 100.0F, 150.0F);
/*  54: 71 */     if (this.musicEnded) {
/*  55: 72 */       g.drawString("Music Ended", 100.0F, 200.0F);
/*  56:    */     }
/*  57: 74 */     if (this.musicSwapped) {
/*  58: 75 */       g.drawString("Music Swapped", 100.0F, 250.0F);
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void keyPressed(int key, char c)
/*  63:    */   {
/*  64: 83 */     if (key == 50)
/*  65:    */     {
/*  66: 84 */       this.musicEnded = false;
/*  67: 85 */       this.musicSwapped = false;
/*  68: 86 */       this.music.play();
/*  69:    */     }
/*  70: 88 */     if (key == 31)
/*  71:    */     {
/*  72: 89 */       this.musicEnded = false;
/*  73: 90 */       this.musicSwapped = false;
/*  74: 91 */       this.stream.play();
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   public static void main(String[] argv)
/*  79:    */   {
/*  80:    */     try
/*  81:    */     {
/*  82:102 */       AppGameContainer container = new AppGameContainer(new MusicListenerTest());
/*  83:103 */       container.setDisplayMode(800, 600, false);
/*  84:104 */       container.start();
/*  85:    */     }
/*  86:    */     catch (SlickException e)
/*  87:    */     {
/*  88:106 */       e.printStackTrace();
/*  89:    */     }
/*  90:    */   }
/*  91:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.MusicListenerTest
 * JD-Core Version:    0.7.0.1
 */