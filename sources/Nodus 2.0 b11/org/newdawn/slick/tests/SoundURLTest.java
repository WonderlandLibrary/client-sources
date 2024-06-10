/*   1:    */ package org.newdawn.slick.tests;
/*   2:    */ 
/*   3:    */ import org.newdawn.slick.AppGameContainer;
/*   4:    */ import org.newdawn.slick.BasicGame;
/*   5:    */ import org.newdawn.slick.Color;
/*   6:    */ import org.newdawn.slick.GameContainer;
/*   7:    */ import org.newdawn.slick.Graphics;
/*   8:    */ import org.newdawn.slick.Music;
/*   9:    */ import org.newdawn.slick.SlickException;
/*  10:    */ import org.newdawn.slick.Sound;
/*  11:    */ import org.newdawn.slick.util.ResourceLoader;
/*  12:    */ 
/*  13:    */ public class SoundURLTest
/*  14:    */   extends BasicGame
/*  15:    */ {
/*  16:    */   private Sound sound;
/*  17:    */   private Sound charlie;
/*  18:    */   private Sound burp;
/*  19:    */   private Music music;
/*  20:    */   private Music musica;
/*  21:    */   private Music musicb;
/*  22:    */   private Sound engine;
/*  23: 36 */   private int volume = 1;
/*  24:    */   
/*  25:    */   public SoundURLTest()
/*  26:    */   {
/*  27: 42 */     super("Sound URL Test");
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void init(GameContainer container)
/*  31:    */     throws SlickException
/*  32:    */   {
/*  33: 49 */     this.sound = new Sound(ResourceLoader.getResource("testdata/restart.ogg"));
/*  34: 50 */     this.charlie = new Sound(ResourceLoader.getResource("testdata/cbrown01.wav"));
/*  35: 51 */     this.engine = new Sound(ResourceLoader.getResource("testdata/engine.wav"));
/*  36:    */     
/*  37: 53 */     this.music = (this.musica = new Music(ResourceLoader.getResource("testdata/restart.ogg"), false));
/*  38: 54 */     this.musicb = new Music(ResourceLoader.getResource("testdata/kirby.ogg"), false);
/*  39: 55 */     this.burp = new Sound(ResourceLoader.getResource("testdata/burp.aif"));
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void render(GameContainer container, Graphics g)
/*  43:    */   {
/*  44: 62 */     g.setColor(Color.white);
/*  45: 63 */     g.drawString("The OGG loop is now streaming from the file, woot.", 100.0F, 60.0F);
/*  46: 64 */     g.drawString("Press space for sound effect (OGG)", 100.0F, 100.0F);
/*  47: 65 */     g.drawString("Press P to pause/resume music (XM)", 100.0F, 130.0F);
/*  48: 66 */     g.drawString("Press E to pause/resume engine sound (WAV)", 100.0F, 190.0F);
/*  49: 67 */     g.drawString("Press enter for charlie (WAV)", 100.0F, 160.0F);
/*  50: 68 */     g.drawString("Press C to change music", 100.0F, 210.0F);
/*  51: 69 */     g.drawString("Press B to burp (AIF)", 100.0F, 240.0F);
/*  52: 70 */     g.drawString("Press + or - to change volume of music", 100.0F, 270.0F);
/*  53: 71 */     g.setColor(Color.blue);
/*  54: 72 */     g.drawString("Music Volume Level: " + this.volume / 10.0F, 150.0F, 300.0F);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void update(GameContainer container, int delta) {}
/*  58:    */   
/*  59:    */   public void keyPressed(int key, char c)
/*  60:    */   {
/*  61: 85 */     if (key == 1) {
/*  62: 86 */       System.exit(0);
/*  63:    */     }
/*  64: 88 */     if (key == 57) {
/*  65: 89 */       this.sound.play();
/*  66:    */     }
/*  67: 91 */     if (key == 48) {
/*  68: 92 */       this.burp.play();
/*  69:    */     }
/*  70: 94 */     if (key == 30) {
/*  71: 95 */       this.sound.playAt(-1.0F, 0.0F, 0.0F);
/*  72:    */     }
/*  73: 97 */     if (key == 38) {
/*  74: 98 */       this.sound.playAt(1.0F, 0.0F, 0.0F);
/*  75:    */     }
/*  76:100 */     if (key == 28) {
/*  77:101 */       this.charlie.play(1.0F, 1.0F);
/*  78:    */     }
/*  79:103 */     if (key == 25) {
/*  80:104 */       if (this.music.playing()) {
/*  81:105 */         this.music.pause();
/*  82:    */       } else {
/*  83:107 */         this.music.resume();
/*  84:    */       }
/*  85:    */     }
/*  86:110 */     if (key == 46)
/*  87:    */     {
/*  88:111 */       this.music.stop();
/*  89:112 */       if (this.music == this.musica) {
/*  90:113 */         this.music = this.musicb;
/*  91:    */       } else {
/*  92:115 */         this.music = this.musica;
/*  93:    */       }
/*  94:118 */       this.music.loop();
/*  95:    */     }
/*  96:120 */     if (key == 18) {
/*  97:121 */       if (this.engine.playing()) {
/*  98:122 */         this.engine.stop();
/*  99:    */       } else {
/* 100:124 */         this.engine.loop();
/* 101:    */       }
/* 102:    */     }
/* 103:128 */     if (c == '+')
/* 104:    */     {
/* 105:129 */       this.volume += 1;
/* 106:130 */       setVolume();
/* 107:    */     }
/* 108:133 */     if (c == '-')
/* 109:    */     {
/* 110:134 */       this.volume -= 1;
/* 111:135 */       setVolume();
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   private void setVolume()
/* 116:    */   {
/* 117:145 */     if (this.volume > 10) {
/* 118:146 */       this.volume = 10;
/* 119:147 */     } else if (this.volume < 0) {
/* 120:148 */       this.volume = 0;
/* 121:    */     }
/* 122:151 */     this.music.setVolume(this.volume / 10.0F);
/* 123:    */   }
/* 124:    */   
/* 125:    */   public static void main(String[] argv)
/* 126:    */   {
/* 127:    */     try
/* 128:    */     {
/* 129:161 */       AppGameContainer container = new AppGameContainer(new SoundURLTest());
/* 130:162 */       container.setDisplayMode(800, 600, false);
/* 131:163 */       container.start();
/* 132:    */     }
/* 133:    */     catch (SlickException e)
/* 134:    */     {
/* 135:165 */       e.printStackTrace();
/* 136:    */     }
/* 137:    */   }
/* 138:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.SoundURLTest
 * JD-Core Version:    0.7.0.1
 */