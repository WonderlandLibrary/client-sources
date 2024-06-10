/*   1:    */ package org.newdawn.slick.tests;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import org.newdawn.slick.AppGameContainer;
/*   6:    */ import org.newdawn.slick.BasicGame;
/*   7:    */ import org.newdawn.slick.Color;
/*   8:    */ import org.newdawn.slick.GameContainer;
/*   9:    */ import org.newdawn.slick.Graphics;
/*  10:    */ import org.newdawn.slick.Input;
/*  11:    */ import org.newdawn.slick.SlickException;
/*  12:    */ import org.newdawn.slick.util.Log;
/*  13:    */ 
/*  14:    */ public class InputTest
/*  15:    */   extends BasicGame
/*  16:    */ {
/*  17: 21 */   private String message = "Press any key, mouse button, or drag the mouse";
/*  18: 23 */   private ArrayList lines = new ArrayList();
/*  19:    */   private boolean buttonDown;
/*  20:    */   private float x;
/*  21:    */   private float y;
/*  22: 31 */   private Color[] cols = { Color.red, Color.green, Color.blue, Color.white, Color.magenta, Color.cyan };
/*  23:    */   private int index;
/*  24:    */   private Input input;
/*  25:    */   private int ypos;
/*  26:    */   private AppGameContainer app;
/*  27:    */   private boolean space;
/*  28:    */   private boolean lshift;
/*  29:    */   private boolean rshift;
/*  30:    */   
/*  31:    */   public InputTest()
/*  32:    */   {
/*  33: 52 */     super("Input Test");
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void init(GameContainer container)
/*  37:    */     throws SlickException
/*  38:    */   {
/*  39: 59 */     if ((container instanceof AppGameContainer)) {
/*  40: 60 */       this.app = ((AppGameContainer)container);
/*  41:    */     }
/*  42: 63 */     this.input = container.getInput();
/*  43: 64 */     this.x = 300.0F;
/*  44: 65 */     this.y = 300.0F;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void render(GameContainer container, Graphics g)
/*  48:    */   {
/*  49: 72 */     g.drawString("left shift down: " + this.lshift, 100.0F, 240.0F);
/*  50: 73 */     g.drawString("right shift down: " + this.rshift, 100.0F, 260.0F);
/*  51: 74 */     g.drawString("space down: " + this.space, 100.0F, 280.0F);
/*  52:    */     
/*  53: 76 */     g.setColor(Color.white);
/*  54: 77 */     g.drawString(this.message, 10.0F, 50.0F);
/*  55: 78 */     g.drawString(container.getInput().getMouseY(), 10.0F, 400.0F);
/*  56: 79 */     g.drawString("Use the primary gamepad to control the blob, and hit a gamepad button to change the color", 10.0F, 90.0F);
/*  57: 81 */     for (int i = 0; i < this.lines.size(); i++)
/*  58:    */     {
/*  59: 82 */       Line line = (Line)this.lines.get(i);
/*  60: 83 */       line.draw(g);
/*  61:    */     }
/*  62: 86 */     g.setColor(this.cols[this.index]);
/*  63: 87 */     g.fillOval((int)this.x, (int)this.y, 50.0F, 50.0F);
/*  64: 88 */     g.setColor(Color.yellow);
/*  65: 89 */     g.fillRect(50.0F, 200 + this.ypos, 40.0F, 40.0F);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void update(GameContainer container, int delta)
/*  69:    */   {
/*  70: 96 */     this.lshift = container.getInput().isKeyDown(42);
/*  71: 97 */     this.rshift = container.getInput().isKeyDown(54);
/*  72: 98 */     this.space = container.getInput().isKeyDown(57);
/*  73:100 */     if (this.controllerLeft[0] != 0) {
/*  74:101 */       this.x -= delta * 0.1F;
/*  75:    */     }
/*  76:103 */     if (this.controllerRight[0] != 0) {
/*  77:104 */       this.x += delta * 0.1F;
/*  78:    */     }
/*  79:106 */     if (this.controllerUp[0] != 0) {
/*  80:107 */       this.y -= delta * 0.1F;
/*  81:    */     }
/*  82:109 */     if (this.controllerDown[0] != 0) {
/*  83:110 */       this.y += delta * 0.1F;
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void keyPressed(int key, char c)
/*  88:    */   {
/*  89:118 */     if (key == 1) {
/*  90:119 */       System.exit(0);
/*  91:    */     }
/*  92:121 */     if ((key == 59) && 
/*  93:122 */       (this.app != null)) {
/*  94:    */       try
/*  95:    */       {
/*  96:124 */         this.app.setDisplayMode(600, 600, false);
/*  97:125 */         this.app.reinit();
/*  98:    */       }
/*  99:    */       catch (Exception e)
/* 100:    */       {
/* 101:126 */         Log.error(e);
/* 102:    */       }
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void keyReleased(int key, char c)
/* 107:    */   {
/* 108:135 */     this.message = ("You pressed key code " + key + " (character = " + c + ")");
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void mousePressed(int button, int x, int y)
/* 112:    */   {
/* 113:142 */     if (button == 0) {
/* 114:143 */       this.buttonDown = true;
/* 115:    */     }
/* 116:146 */     this.message = ("Mouse pressed " + button + " " + x + "," + y);
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void mouseReleased(int button, int x, int y)
/* 120:    */   {
/* 121:153 */     if (button == 0) {
/* 122:154 */       this.buttonDown = false;
/* 123:    */     }
/* 124:157 */     this.message = ("Mouse released " + button + " " + x + "," + y);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void mouseClicked(int button, int x, int y, int clickCount)
/* 128:    */   {
/* 129:164 */     System.out.println("CLICKED:" + x + "," + y + " " + clickCount);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void mouseWheelMoved(int change)
/* 133:    */   {
/* 134:171 */     this.message = ("Mouse wheel moved: " + change);
/* 135:173 */     if (change < 0) {
/* 136:174 */       this.ypos -= 10;
/* 137:    */     }
/* 138:176 */     if (change > 0) {
/* 139:177 */       this.ypos += 10;
/* 140:    */     }
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void mouseMoved(int oldx, int oldy, int newx, int newy)
/* 144:    */   {
/* 145:185 */     if (this.buttonDown) {
/* 146:186 */       this.lines.add(new Line(oldx, oldy, newx, newy));
/* 147:    */     }
/* 148:    */   }
/* 149:    */   
/* 150:    */   private class Line
/* 151:    */   {
/* 152:    */     private int oldx;
/* 153:    */     private int oldy;
/* 154:    */     private int newx;
/* 155:    */     private int newy;
/* 156:    */     
/* 157:    */     public Line(int oldx, int oldy, int newx, int newy)
/* 158:    */     {
/* 159:214 */       this.oldx = oldx;
/* 160:215 */       this.oldy = oldy;
/* 161:216 */       this.newx = newx;
/* 162:217 */       this.newy = newy;
/* 163:    */     }
/* 164:    */     
/* 165:    */     public void draw(Graphics g)
/* 166:    */     {
/* 167:226 */       g.drawLine(this.oldx, this.oldy, this.newx, this.newy);
/* 168:    */     }
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void controllerButtonPressed(int controller, int button)
/* 172:    */   {
/* 173:234 */     super.controllerButtonPressed(controller, button);
/* 174:    */     
/* 175:236 */     this.index += 1;
/* 176:237 */     this.index %= this.cols.length;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public static void main(String[] argv)
/* 180:    */   {
/* 181:    */     try
/* 182:    */     {
/* 183:247 */       AppGameContainer container = new AppGameContainer(new InputTest());
/* 184:248 */       container.setDisplayMode(800, 600, false);
/* 185:249 */       container.start();
/* 186:    */     }
/* 187:    */     catch (SlickException e)
/* 188:    */     {
/* 189:251 */       e.printStackTrace();
/* 190:    */     }
/* 191:    */   }
/* 192:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.InputTest
 * JD-Core Version:    0.7.0.1
 */