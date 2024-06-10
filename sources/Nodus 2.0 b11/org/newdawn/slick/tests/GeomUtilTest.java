/*   1:    */ package org.newdawn.slick.tests;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import org.newdawn.slick.AppGameContainer;
/*   5:    */ import org.newdawn.slick.BasicGame;
/*   6:    */ import org.newdawn.slick.Color;
/*   7:    */ import org.newdawn.slick.GameContainer;
/*   8:    */ import org.newdawn.slick.Graphics;
/*   9:    */ import org.newdawn.slick.Input;
/*  10:    */ import org.newdawn.slick.SlickException;
/*  11:    */ import org.newdawn.slick.geom.Circle;
/*  12:    */ import org.newdawn.slick.geom.GeomUtil;
/*  13:    */ import org.newdawn.slick.geom.GeomUtilListener;
/*  14:    */ import org.newdawn.slick.geom.Polygon;
/*  15:    */ import org.newdawn.slick.geom.Rectangle;
/*  16:    */ import org.newdawn.slick.geom.Shape;
/*  17:    */ import org.newdawn.slick.geom.Transform;
/*  18:    */ import org.newdawn.slick.geom.Vector2f;
/*  19:    */ 
/*  20:    */ public class GeomUtilTest
/*  21:    */   extends BasicGame
/*  22:    */   implements GeomUtilListener
/*  23:    */ {
/*  24:    */   private Shape source;
/*  25:    */   private Shape cut;
/*  26:    */   private Shape[] result;
/*  27: 35 */   private ArrayList points = new ArrayList();
/*  28: 37 */   private ArrayList marks = new ArrayList();
/*  29: 39 */   private ArrayList exclude = new ArrayList();
/*  30:    */   private boolean dynamic;
/*  31: 44 */   private GeomUtil util = new GeomUtil();
/*  32:    */   private int xp;
/*  33:    */   private int yp;
/*  34:    */   private Circle circle;
/*  35:    */   private Shape rect;
/*  36:    */   private Polygon star;
/*  37:    */   private boolean union;
/*  38:    */   
/*  39:    */   public GeomUtilTest()
/*  40:    */   {
/*  41: 63 */     super("GeomUtilTest");
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void init()
/*  45:    */   {
/*  46: 70 */     Polygon source = new Polygon();
/*  47: 71 */     source.addPoint(100.0F, 100.0F);
/*  48: 72 */     source.addPoint(150.0F, 80.0F);
/*  49: 73 */     source.addPoint(210.0F, 120.0F);
/*  50: 74 */     source.addPoint(340.0F, 150.0F);
/*  51: 75 */     source.addPoint(150.0F, 200.0F);
/*  52: 76 */     source.addPoint(120.0F, 250.0F);
/*  53: 77 */     this.source = source;
/*  54:    */     
/*  55: 79 */     this.circle = new Circle(0.0F, 0.0F, 50.0F);
/*  56: 80 */     this.rect = new Rectangle(-100.0F, -40.0F, 200.0F, 80.0F);
/*  57: 81 */     this.star = new Polygon();
/*  58:    */     
/*  59: 83 */     float dis = 40.0F;
/*  60: 84 */     for (int i = 0; i < 360; i += 30)
/*  61:    */     {
/*  62: 85 */       dis = dis == 40.0F ? 60 : 40;
/*  63: 86 */       double x = Math.cos(Math.toRadians(i)) * dis;
/*  64: 87 */       double y = Math.sin(Math.toRadians(i)) * dis;
/*  65: 88 */       this.star.addPoint((float)x, (float)y);
/*  66:    */     }
/*  67: 91 */     this.cut = this.circle;
/*  68: 92 */     this.cut.setLocation(203.0F, 78.0F);
/*  69: 93 */     this.xp = ((int)this.cut.getCenterX());
/*  70: 94 */     this.yp = ((int)this.cut.getCenterY());
/*  71: 95 */     makeBoolean();
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void init(GameContainer container)
/*  75:    */     throws SlickException
/*  76:    */   {
/*  77:102 */     this.util.setListener(this);
/*  78:103 */     init();
/*  79:104 */     container.setVSync(true);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void update(GameContainer container, int delta)
/*  83:    */     throws SlickException
/*  84:    */   {
/*  85:112 */     if (container.getInput().isKeyPressed(57)) {
/*  86:113 */       this.dynamic = (!this.dynamic);
/*  87:    */     }
/*  88:115 */     if (container.getInput().isKeyPressed(28))
/*  89:    */     {
/*  90:116 */       this.union = (!this.union);
/*  91:117 */       makeBoolean();
/*  92:    */     }
/*  93:119 */     if (container.getInput().isKeyPressed(2))
/*  94:    */     {
/*  95:120 */       this.cut = this.circle;
/*  96:121 */       this.circle.setCenterX(this.xp);
/*  97:122 */       this.circle.setCenterY(this.yp);
/*  98:123 */       makeBoolean();
/*  99:    */     }
/* 100:125 */     if (container.getInput().isKeyPressed(3))
/* 101:    */     {
/* 102:126 */       this.cut = this.rect;
/* 103:127 */       this.rect.setCenterX(this.xp);
/* 104:128 */       this.rect.setCenterY(this.yp);
/* 105:129 */       makeBoolean();
/* 106:    */     }
/* 107:131 */     if (container.getInput().isKeyPressed(4))
/* 108:    */     {
/* 109:132 */       this.cut = this.star;
/* 110:133 */       this.star.setCenterX(this.xp);
/* 111:134 */       this.star.setCenterY(this.yp);
/* 112:135 */       makeBoolean();
/* 113:    */     }
/* 114:138 */     if (this.dynamic)
/* 115:    */     {
/* 116:139 */       this.xp = container.getInput().getMouseX();
/* 117:140 */       this.yp = container.getInput().getMouseY();
/* 118:141 */       makeBoolean();
/* 119:    */     }
/* 120:    */   }
/* 121:    */   
/* 122:    */   private void makeBoolean()
/* 123:    */   {
/* 124:149 */     this.marks.clear();
/* 125:150 */     this.points.clear();
/* 126:151 */     this.exclude.clear();
/* 127:152 */     this.cut.setCenterX(this.xp);
/* 128:153 */     this.cut.setCenterY(this.yp);
/* 129:154 */     if (this.union) {
/* 130:155 */       this.result = this.util.union(this.source, this.cut);
/* 131:    */     } else {
/* 132:157 */       this.result = this.util.subtract(this.source, this.cut);
/* 133:    */     }
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void render(GameContainer container, Graphics g)
/* 137:    */     throws SlickException
/* 138:    */   {
/* 139:166 */     g.drawString("Space - toggle movement of cutting shape", 530.0F, 10.0F);
/* 140:167 */     g.drawString("1,2,3 - select cutting shape", 530.0F, 30.0F);
/* 141:168 */     g.drawString("Mouse wheel - rotate shape", 530.0F, 50.0F);
/* 142:169 */     g.drawString("Enter - toggle union/subtract", 530.0F, 70.0F);
/* 143:170 */     g.drawString("MODE: " + (this.union ? "Union" : "Cut"), 530.0F, 200.0F);
/* 144:    */     
/* 145:172 */     g.setColor(Color.green);
/* 146:173 */     g.draw(this.source);
/* 147:174 */     g.setColor(Color.red);
/* 148:175 */     g.draw(this.cut);
/* 149:    */     
/* 150:177 */     g.setColor(Color.white);
/* 151:178 */     for (int i = 0; i < this.exclude.size(); i++)
/* 152:    */     {
/* 153:179 */       Vector2f pt = (Vector2f)this.exclude.get(i);
/* 154:180 */       g.drawOval(pt.x - 3.0F, pt.y - 3.0F, 7.0F, 7.0F);
/* 155:    */     }
/* 156:182 */     g.setColor(Color.yellow);
/* 157:183 */     for (int i = 0; i < this.points.size(); i++)
/* 158:    */     {
/* 159:184 */       Vector2f pt = (Vector2f)this.points.get(i);
/* 160:185 */       g.fillOval(pt.x - 1.0F, pt.y - 1.0F, 3.0F, 3.0F);
/* 161:    */     }
/* 162:187 */     g.setColor(Color.white);
/* 163:188 */     for (int i = 0; i < this.marks.size(); i++)
/* 164:    */     {
/* 165:189 */       Vector2f pt = (Vector2f)this.marks.get(i);
/* 166:190 */       g.fillOval(pt.x - 1.0F, pt.y - 1.0F, 3.0F, 3.0F);
/* 167:    */     }
/* 168:193 */     g.translate(0.0F, 300.0F);
/* 169:194 */     g.setColor(Color.white);
/* 170:195 */     if (this.result != null)
/* 171:    */     {
/* 172:196 */       for (int i = 0; i < this.result.length; i++) {
/* 173:197 */         g.draw(this.result[i]);
/* 174:    */       }
/* 175:200 */       g.drawString("Polys:" + this.result.length, 10.0F, 100.0F);
/* 176:201 */       g.drawString("X:" + this.xp, 10.0F, 120.0F);
/* 177:202 */       g.drawString("Y:" + this.yp, 10.0F, 130.0F);
/* 178:    */     }
/* 179:    */   }
/* 180:    */   
/* 181:    */   public static void main(String[] argv)
/* 182:    */   {
/* 183:    */     try
/* 184:    */     {
/* 185:214 */       AppGameContainer container = new AppGameContainer(new GeomUtilTest());
/* 186:215 */       container.setDisplayMode(800, 600, false);
/* 187:216 */       container.start();
/* 188:    */     }
/* 189:    */     catch (SlickException e)
/* 190:    */     {
/* 191:218 */       e.printStackTrace();
/* 192:    */     }
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void pointExcluded(float x, float y)
/* 196:    */   {
/* 197:223 */     this.exclude.add(new Vector2f(x, y));
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void pointIntersected(float x, float y)
/* 201:    */   {
/* 202:227 */     this.marks.add(new Vector2f(x, y));
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void pointUsed(float x, float y)
/* 206:    */   {
/* 207:231 */     this.points.add(new Vector2f(x, y));
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void mouseWheelMoved(int change)
/* 211:    */   {
/* 212:235 */     if (this.dynamic) {
/* 213:236 */       if (change < 0) {
/* 214:237 */         this.cut = this.cut.transform(Transform.createRotateTransform((float)Math.toRadians(10.0D), this.cut.getCenterX(), this.cut.getCenterY()));
/* 215:    */       } else {
/* 216:239 */         this.cut = this.cut.transform(Transform.createRotateTransform((float)Math.toRadians(-10.0D), this.cut.getCenterX(), this.cut.getCenterY()));
/* 217:    */       }
/* 218:    */     }
/* 219:    */   }
/* 220:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.GeomUtilTest
 * JD-Core Version:    0.7.0.1
 */