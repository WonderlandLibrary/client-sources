/*   1:    */ package org.newdawn.slick.tests;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import org.newdawn.slick.AppGameContainer;
/*   5:    */ import org.newdawn.slick.BasicGame;
/*   6:    */ import org.newdawn.slick.Color;
/*   7:    */ import org.newdawn.slick.GameContainer;
/*   8:    */ import org.newdawn.slick.Graphics;
/*   9:    */ import org.newdawn.slick.SlickException;
/*  10:    */ import org.newdawn.slick.opengl.SlickCallable;
/*  11:    */ import org.newdawn.slick.util.Log;
/*  12:    */ 
/*  13:    */ public class TestBox
/*  14:    */   extends BasicGame
/*  15:    */ {
/*  16: 23 */   private ArrayList games = new ArrayList();
/*  17:    */   private BasicGame currentGame;
/*  18:    */   private int index;
/*  19:    */   private AppGameContainer container;
/*  20:    */   
/*  21:    */   public TestBox()
/*  22:    */   {
/*  23: 35 */     super("Test Box");
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void addGame(Class game)
/*  27:    */   {
/*  28: 44 */     this.games.add(game);
/*  29:    */   }
/*  30:    */   
/*  31:    */   private void nextGame()
/*  32:    */   {
/*  33: 51 */     if (this.index == -1) {
/*  34: 52 */       return;
/*  35:    */     }
/*  36: 55 */     this.index += 1;
/*  37: 56 */     if (this.index >= this.games.size()) {
/*  38: 57 */       this.index = 0;
/*  39:    */     }
/*  40: 60 */     startGame();
/*  41:    */   }
/*  42:    */   
/*  43:    */   private void startGame()
/*  44:    */   {
/*  45:    */     try
/*  46:    */     {
/*  47: 68 */       this.currentGame = ((BasicGame)((Class)this.games.get(this.index)).newInstance());
/*  48: 69 */       this.container.getGraphics().setBackground(Color.black);
/*  49: 70 */       this.currentGame.init(this.container);
/*  50: 71 */       this.currentGame.render(this.container, this.container.getGraphics());
/*  51:    */     }
/*  52:    */     catch (Exception e)
/*  53:    */     {
/*  54: 73 */       Log.error(e);
/*  55:    */     }
/*  56: 76 */     this.container.setTitle(this.currentGame.getTitle());
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void init(GameContainer c)
/*  60:    */     throws SlickException
/*  61:    */   {
/*  62: 83 */     if (this.games.size() == 0)
/*  63:    */     {
/*  64: 84 */       this.currentGame = new BasicGame("NULL")
/*  65:    */       {
/*  66:    */         public void init(GameContainer container)
/*  67:    */           throws SlickException
/*  68:    */         {}
/*  69:    */         
/*  70:    */         public void update(GameContainer container, int delta)
/*  71:    */           throws SlickException
/*  72:    */         {}
/*  73:    */         
/*  74:    */         public void render(GameContainer container, Graphics g)
/*  75:    */           throws SlickException
/*  76:    */         {}
/*  77: 93 */       };
/*  78: 94 */       this.currentGame.init(c);
/*  79: 95 */       this.index = -1;
/*  80:    */     }
/*  81:    */     else
/*  82:    */     {
/*  83: 97 */       this.index = 0;
/*  84: 98 */       this.container = ((AppGameContainer)c);
/*  85: 99 */       startGame();
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void update(GameContainer container, int delta)
/*  90:    */     throws SlickException
/*  91:    */   {
/*  92:107 */     this.currentGame.update(container, delta);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void render(GameContainer container, Graphics g)
/*  96:    */     throws SlickException
/*  97:    */   {
/*  98:114 */     SlickCallable.enterSafeBlock();
/*  99:115 */     this.currentGame.render(container, g);
/* 100:116 */     SlickCallable.leaveSafeBlock();
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void controllerButtonPressed(int controller, int button)
/* 104:    */   {
/* 105:123 */     this.currentGame.controllerButtonPressed(controller, button);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void controllerButtonReleased(int controller, int button)
/* 109:    */   {
/* 110:130 */     this.currentGame.controllerButtonReleased(controller, button);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void controllerDownPressed(int controller)
/* 114:    */   {
/* 115:137 */     this.currentGame.controllerDownPressed(controller);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void controllerDownReleased(int controller)
/* 119:    */   {
/* 120:144 */     this.currentGame.controllerDownReleased(controller);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void controllerLeftPressed(int controller)
/* 124:    */   {
/* 125:151 */     this.currentGame.controllerLeftPressed(controller);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void controllerLeftReleased(int controller)
/* 129:    */   {
/* 130:158 */     this.currentGame.controllerLeftReleased(controller);
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void controllerRightPressed(int controller)
/* 134:    */   {
/* 135:165 */     this.currentGame.controllerRightPressed(controller);
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void controllerRightReleased(int controller)
/* 139:    */   {
/* 140:172 */     this.currentGame.controllerRightReleased(controller);
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void controllerUpPressed(int controller)
/* 144:    */   {
/* 145:179 */     this.currentGame.controllerUpPressed(controller);
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void controllerUpReleased(int controller)
/* 149:    */   {
/* 150:186 */     this.currentGame.controllerUpReleased(controller);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void keyPressed(int key, char c)
/* 154:    */   {
/* 155:193 */     this.currentGame.keyPressed(key, c);
/* 156:195 */     if (key == 28) {
/* 157:196 */       nextGame();
/* 158:    */     }
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void keyReleased(int key, char c)
/* 162:    */   {
/* 163:204 */     this.currentGame.keyReleased(key, c);
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void mouseMoved(int oldx, int oldy, int newx, int newy)
/* 167:    */   {
/* 168:211 */     this.currentGame.mouseMoved(oldx, oldy, newx, newy);
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void mousePressed(int button, int x, int y)
/* 172:    */   {
/* 173:218 */     this.currentGame.mousePressed(button, x, y);
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void mouseReleased(int button, int x, int y)
/* 177:    */   {
/* 178:225 */     this.currentGame.mouseReleased(button, x, y);
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void mouseWheelMoved(int change)
/* 182:    */   {
/* 183:232 */     this.currentGame.mouseWheelMoved(change);
/* 184:    */   }
/* 185:    */   
/* 186:    */   public static void main(String[] argv)
/* 187:    */   {
/* 188:    */     try
/* 189:    */     {
/* 190:242 */       TestBox box = new TestBox();
/* 191:243 */       box.addGame(AnimationTest.class);
/* 192:244 */       box.addGame(AntiAliasTest.class);
/* 193:245 */       box.addGame(BigImageTest.class);
/* 194:246 */       box.addGame(ClipTest.class);
/* 195:247 */       box.addGame(DuplicateEmitterTest.class);
/* 196:248 */       box.addGame(FlashTest.class);
/* 197:249 */       box.addGame(FontPerformanceTest.class);
/* 198:250 */       box.addGame(FontTest.class);
/* 199:251 */       box.addGame(GeomTest.class);
/* 200:252 */       box.addGame(GradientTest.class);
/* 201:253 */       box.addGame(GraphicsTest.class);
/* 202:254 */       box.addGame(ImageBufferTest.class);
/* 203:255 */       box.addGame(ImageReadTest.class);
/* 204:256 */       box.addGame(ImageTest.class);
/* 205:257 */       box.addGame(KeyRepeatTest.class);
/* 206:258 */       box.addGame(MusicListenerTest.class);
/* 207:259 */       box.addGame(PackedSheetTest.class);
/* 208:260 */       box.addGame(PedigreeTest.class);
/* 209:261 */       box.addGame(PureFontTest.class);
/* 210:262 */       box.addGame(ShapeTest.class);
/* 211:263 */       box.addGame(SoundTest.class);
/* 212:264 */       box.addGame(SpriteSheetFontTest.class);
/* 213:265 */       box.addGame(TransparentColorTest.class);
/* 214:    */       
/* 215:267 */       AppGameContainer container = new AppGameContainer(box);
/* 216:268 */       container.setDisplayMode(800, 600, false);
/* 217:269 */       container.start();
/* 218:    */     }
/* 219:    */     catch (SlickException e)
/* 220:    */     {
/* 221:271 */       e.printStackTrace();
/* 222:    */     }
/* 223:    */   }
/* 224:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.TestBox
 * JD-Core Version:    0.7.0.1
 */