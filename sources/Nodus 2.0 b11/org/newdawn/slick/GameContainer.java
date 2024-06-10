/*   1:    */ package org.newdawn.slick;
/*   2:    */ 
/*   3:    */ import org.lwjgl.input.Cursor;
/*   4:    */ import org.lwjgl.opengl.Drawable;
/*   5:    */ import org.newdawn.slick.gui.GUIContext;
/*   6:    */ import org.newdawn.slick.opengl.ImageData;
/*   7:    */ import org.newdawn.slick.opengl.renderer.SGL;
/*   8:    */ 
/*   9:    */ public abstract class GameContainer
/*  10:    */   implements GUIContext
/*  11:    */ {
/*  12:    */   protected static SGL GL;
/*  13:    */   protected static Drawable SHARED_DRAWABLE;
/*  14:    */   protected long lastFrame;
/*  15:    */   protected long lastFPS;
/*  16:    */   protected int recordedFPS;
/*  17:    */   protected int fps;
/*  18:    */   protected boolean running;
/*  19:    */   protected int width;
/*  20:    */   protected int height;
/*  21:    */   protected Game game;
/*  22:    */   private Font defaultFont;
/*  23:    */   private Graphics graphics;
/*  24:    */   protected Input input;
/*  25:    */   protected int targetFPS;
/*  26:    */   private boolean showFPS;
/*  27:    */   protected long minimumLogicInterval;
/*  28:    */   protected long storedDelta;
/*  29:    */   protected long maximumLogicInterval;
/*  30:    */   protected Game lastGame;
/*  31:    */   protected boolean clearEachFrame;
/*  32:    */   protected boolean paused;
/*  33:    */   protected boolean forceExit;
/*  34:    */   protected boolean vsync;
/*  35:    */   protected boolean smoothDeltas;
/*  36:    */   protected int samples;
/*  37:    */   protected boolean supportsMultiSample;
/*  38:    */   protected boolean alwaysRender;
/*  39:    */   protected static boolean stencil;
/*  40:    */   
/*  41:    */   protected GameContainer(Game paramGame) {}
/*  42:    */   
/*  43:    */   public static void enableStencil()
/*  44:    */   {
/*  45:106 */     throw new Error("Unresolved compilation problem: \n");
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setDefaultFont(Font paramFont)
/*  49:    */   {
/*  50:115 */     throw new Error("Unresolved compilation problem: \n");
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setMultiSample(int paramInt)
/*  54:    */   {
/*  55:129 */     throw new Error("Unresolved compilation problem: \n");
/*  56:    */   }
/*  57:    */   
/*  58:    */   public boolean supportsMultiSample()
/*  59:    */   {
/*  60:138 */     throw new Error("Unresolved compilation problem: \n");
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int getSamples()
/*  64:    */   {
/*  65:148 */     throw new Error("Unresolved compilation problem: \n");
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setForceExit(boolean paramBoolean)
/*  69:    */   {
/*  70:158 */     throw new Error("Unresolved compilation problem: \n");
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setSmoothDeltas(boolean paramBoolean)
/*  74:    */   {
/*  75:169 */     throw new Error("Unresolved compilation problem: \n");
/*  76:    */   }
/*  77:    */   
/*  78:    */   public boolean isFullscreen()
/*  79:    */   {
/*  80:178 */     throw new Error("Unresolved compilation problem: \n");
/*  81:    */   }
/*  82:    */   
/*  83:    */   public float getAspectRatio()
/*  84:    */   {
/*  85:187 */     throw new Error("Unresolved compilation problem: \n");
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setFullscreen(boolean paramBoolean)
/*  89:    */     throws SlickException
/*  90:    */   {
/*  91:198 */     throw new Error("Unresolved compilation problem: \n");
/*  92:    */   }
/*  93:    */   
/*  94:    */   public static void enableSharedContext()
/*  95:    */     throws SlickException
/*  96:    */   {
/*  97:207 */     throw new Error("Unresolved compilation problem: \n");
/*  98:    */   }
/*  99:    */   
/* 100:    */   public static Drawable getSharedContext()
/* 101:    */   {
/* 102:220 */     throw new Error("Unresolved compilation problem: \n");
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setClearEachFrame(boolean paramBoolean)
/* 106:    */   {
/* 107:231 */     throw new Error("Unresolved compilation problem: \n");
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void reinit()
/* 111:    */     throws SlickException
/* 112:    */   {
/* 113:240 */     throw new Error("Unresolved compilation problem: \n");
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void pause()
/* 117:    */   {
/* 118:246 */     throw new Error("Unresolved compilation problem: \n");
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void resume()
/* 122:    */   {
/* 123:254 */     throw new Error("Unresolved compilation problem: \n");
/* 124:    */   }
/* 125:    */   
/* 126:    */   public boolean isPaused()
/* 127:    */   {
/* 128:264 */     throw new Error("Unresolved compilation problem: \n");
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setPaused(boolean paramBoolean)
/* 132:    */   {
/* 133:274 */     throw new Error("Unresolved compilation problem: \n");
/* 134:    */   }
/* 135:    */   
/* 136:    */   public boolean getAlwaysRender()
/* 137:    */   {
/* 138:284 */     throw new Error("Unresolved compilation problem: \n");
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setAlwaysRender(boolean paramBoolean)
/* 142:    */   {
/* 143:293 */     throw new Error("Unresolved compilation problem: \n");
/* 144:    */   }
/* 145:    */   
/* 146:    */   public static int getBuildVersion()
/* 147:    */   {
/* 148:302 */     throw new Error("Unresolved compilation problem: \n");
/* 149:    */   }
/* 150:    */   
/* 151:    */   public Font getDefaultFont()
/* 152:    */   {
/* 153:322 */     throw new Error("Unresolved compilation problem: \n");
/* 154:    */   }
/* 155:    */   
/* 156:    */   public boolean isSoundOn()
/* 157:    */   {
/* 158:332 */     throw new Error("Unresolved compilation problem: \n\tSoundStore cannot be resolved\n");
/* 159:    */   }
/* 160:    */   
/* 161:    */   public boolean isMusicOn()
/* 162:    */   {
/* 163:341 */     throw new Error("Unresolved compilation problem: \n\tSoundStore cannot be resolved\n");
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setMusicOn(boolean paramBoolean)
/* 167:    */   {
/* 168:350 */     throw new Error("Unresolved compilation problem: \n\tSoundStore cannot be resolved\n");
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setSoundOn(boolean paramBoolean)
/* 172:    */   {
/* 173:359 */     throw new Error("Unresolved compilation problem: \n\tSoundStore cannot be resolved\n");
/* 174:    */   }
/* 175:    */   
/* 176:    */   public float getMusicVolume()
/* 177:    */   {
/* 178:367 */     throw new Error("Unresolved compilation problem: \n\tSoundStore cannot be resolved\n");
/* 179:    */   }
/* 180:    */   
/* 181:    */   public float getSoundVolume()
/* 182:    */   {
/* 183:375 */     throw new Error("Unresolved compilation problem: \n\tSoundStore cannot be resolved\n");
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setSoundVolume(float paramFloat)
/* 187:    */   {
/* 188:383 */     throw new Error("Unresolved compilation problem: \n\tSoundStore cannot be resolved\n");
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setMusicVolume(float paramFloat)
/* 192:    */   {
/* 193:391 */     throw new Error("Unresolved compilation problem: \n\tSoundStore cannot be resolved\n");
/* 194:    */   }
/* 195:    */   
/* 196:    */   public abstract int getScreenWidth();
/* 197:    */   
/* 198:    */   public abstract int getScreenHeight();
/* 199:    */   
/* 200:    */   public int getWidth()
/* 201:    */   {
/* 202:413 */     throw new Error("Unresolved compilation problem: \n");
/* 203:    */   }
/* 204:    */   
/* 205:    */   public int getHeight()
/* 206:    */   {
/* 207:422 */     throw new Error("Unresolved compilation problem: \n");
/* 208:    */   }
/* 209:    */   
/* 210:    */   public abstract void setIcon(String paramString)
/* 211:    */     throws SlickException;
/* 212:    */   
/* 213:    */   public abstract void setIcons(String[] paramArrayOfString)
/* 214:    */     throws SlickException;
/* 215:    */   
/* 216:    */   public long getTime()
/* 217:    */   {
/* 218:452 */     throw new Error("Unresolved compilation problem: \n");
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void sleep(int paramInt)
/* 222:    */   {
/* 223:461 */     throw new Error("Unresolved compilation problem: \n");
/* 224:    */   }
/* 225:    */   
/* 226:    */   public abstract void setMouseCursor(String paramString, int paramInt1, int paramInt2)
/* 227:    */     throws SlickException;
/* 228:    */   
/* 229:    */   public abstract void setMouseCursor(ImageData paramImageData, int paramInt1, int paramInt2)
/* 230:    */     throws SlickException;
/* 231:    */   
/* 232:    */   public abstract void setMouseCursor(Image paramImage, int paramInt1, int paramInt2)
/* 233:    */     throws SlickException;
/* 234:    */   
/* 235:    */   public abstract void setMouseCursor(Cursor paramCursor, int paramInt1, int paramInt2)
/* 236:    */     throws SlickException;
/* 237:    */   
/* 238:    */   public void setAnimatedMouseCursor(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt)
/* 239:    */     throws SlickException
/* 240:    */   {
/* 241:529 */     throw new Error("Unresolved compilation problem: \n");
/* 242:    */   }
/* 243:    */   
/* 244:    */   public abstract void setDefaultMouseCursor();
/* 245:    */   
/* 246:    */   public Input getInput()
/* 247:    */   {
/* 248:553 */     throw new Error("Unresolved compilation problem: \n");
/* 249:    */   }
/* 250:    */   
/* 251:    */   public int getFPS()
/* 252:    */   {
/* 253:562 */     throw new Error("Unresolved compilation problem: \n");
/* 254:    */   }
/* 255:    */   
/* 256:    */   public abstract void setMouseGrabbed(boolean paramBoolean);
/* 257:    */   
/* 258:    */   public abstract boolean isMouseGrabbed();
/* 259:    */   
/* 260:    */   protected int getDelta()
/* 261:    */   {
/* 262:586 */     throw new Error("Unresolved compilation problem: \n");
/* 263:    */   }
/* 264:    */   
/* 265:    */   protected void updateFPS()
/* 266:    */   {
/* 267:597 */     throw new Error("Unresolved compilation problem: \n");
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void setMinimumLogicUpdateInterval(int paramInt)
/* 271:    */   {
/* 272:613 */     throw new Error("Unresolved compilation problem: \n");
/* 273:    */   }
/* 274:    */   
/* 275:    */   public void setMaximumLogicUpdateInterval(int paramInt)
/* 276:    */   {
/* 277:624 */     throw new Error("Unresolved compilation problem: \n");
/* 278:    */   }
/* 279:    */   
/* 280:    */   protected void updateAndRender(int paramInt)
/* 281:    */     throws SlickException
/* 282:    */   {
/* 283:634 */     throw new Error("Unresolved compilation problem: \n");
/* 284:    */   }
/* 285:    */   
/* 286:    */   public void setUpdateOnlyWhenVisible(boolean paramBoolean)
/* 287:    */   {
/* 288:713 */     throw new Error("Unresolved compilation problem: \n");
/* 289:    */   }
/* 290:    */   
/* 291:    */   public boolean isUpdatingOnlyWhenVisible()
/* 292:    */   {
/* 293:721 */     throw new Error("Unresolved compilation problem: \n");
/* 294:    */   }
/* 295:    */   
/* 296:    */   protected void initGL()
/* 297:    */   {
/* 298:728 */     throw new Error("Unresolved compilation problem: \n");
/* 299:    */   }
/* 300:    */   
/* 301:    */   protected void initSystem()
/* 302:    */     throws SlickException
/* 303:    */   {
/* 304:754 */     throw new Error("Unresolved compilation problem: \n");
/* 305:    */   }
/* 306:    */   
/* 307:    */   protected void enterOrtho()
/* 308:    */   {
/* 309:766 */     throw new Error("Unresolved compilation problem: \n");
/* 310:    */   }
/* 311:    */   
/* 312:    */   public void setShowFPS(boolean paramBoolean)
/* 313:    */   {
/* 314:775 */     throw new Error("Unresolved compilation problem: \n");
/* 315:    */   }
/* 316:    */   
/* 317:    */   public boolean isShowingFPS()
/* 318:    */   {
/* 319:784 */     throw new Error("Unresolved compilation problem: \n");
/* 320:    */   }
/* 321:    */   
/* 322:    */   public void setTargetFrameRate(int paramInt)
/* 323:    */   {
/* 324:793 */     throw new Error("Unresolved compilation problem: \n");
/* 325:    */   }
/* 326:    */   
/* 327:    */   public void setVSync(boolean paramBoolean)
/* 328:    */   {
/* 329:803 */     throw new Error("Unresolved compilation problem: \n");
/* 330:    */   }
/* 331:    */   
/* 332:    */   public boolean isVSyncRequested()
/* 333:    */   {
/* 334:813 */     throw new Error("Unresolved compilation problem: \n");
/* 335:    */   }
/* 336:    */   
/* 337:    */   protected boolean running()
/* 338:    */   {
/* 339:822 */     throw new Error("Unresolved compilation problem: \n");
/* 340:    */   }
/* 341:    */   
/* 342:    */   public void setVerbose(boolean paramBoolean)
/* 343:    */   {
/* 344:831 */     throw new Error("Unresolved compilation problem: \n");
/* 345:    */   }
/* 346:    */   
/* 347:    */   public void exit()
/* 348:    */   {
/* 349:838 */     throw new Error("Unresolved compilation problem: \n");
/* 350:    */   }
/* 351:    */   
/* 352:    */   public abstract boolean hasFocus();
/* 353:    */   
/* 354:    */   public Graphics getGraphics()
/* 355:    */   {
/* 356:855 */     throw new Error("Unresolved compilation problem: \n");
/* 357:    */   }
/* 358:    */   
/* 359:    */   protected void enterOrtho(int paramInt1, int paramInt2)
/* 360:    */   {
/* 361:865 */     throw new Error("Unresolved compilation problem: \n");
/* 362:    */   }
/* 363:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.GameContainer
 * JD-Core Version:    0.7.0.1
 */