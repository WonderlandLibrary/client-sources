/*   1:    */ package org.newdawn.slick.tests;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.HashSet;
/*   6:    */ import org.newdawn.slick.AppGameContainer;
/*   7:    */ import org.newdawn.slick.BasicGame;
/*   8:    */ import org.newdawn.slick.Color;
/*   9:    */ import org.newdawn.slick.GameContainer;
/*  10:    */ import org.newdawn.slick.Graphics;
/*  11:    */ import org.newdawn.slick.SlickException;
/*  12:    */ import org.newdawn.slick.geom.Circle;
/*  13:    */ import org.newdawn.slick.geom.GeomUtil;
/*  14:    */ import org.newdawn.slick.geom.GeomUtilListener;
/*  15:    */ import org.newdawn.slick.geom.Polygon;
/*  16:    */ import org.newdawn.slick.geom.Shape;
/*  17:    */ import org.newdawn.slick.geom.Vector2f;
/*  18:    */ 
/*  19:    */ public class GeomUtilTileTest
/*  20:    */   extends BasicGame
/*  21:    */   implements GeomUtilListener
/*  22:    */ {
/*  23:    */   private Shape source;
/*  24:    */   private Shape cut;
/*  25:    */   private Shape[] result;
/*  26: 33 */   private GeomUtil util = new GeomUtil();
/*  27: 36 */   private ArrayList original = new ArrayList();
/*  28: 38 */   private ArrayList combined = new ArrayList();
/*  29: 41 */   private ArrayList intersections = new ArrayList();
/*  30: 43 */   private ArrayList used = new ArrayList();
/*  31:    */   private ArrayList[][] quadSpace;
/*  32:    */   private Shape[][] quadSpaceShapes;
/*  33:    */   
/*  34:    */   public GeomUtilTileTest()
/*  35:    */   {
/*  36: 54 */     super("GeomUtilTileTest");
/*  37:    */   }
/*  38:    */   
/*  39:    */   private void generateSpace(ArrayList shapes, float minx, float miny, float maxx, float maxy, int segments)
/*  40:    */   {
/*  41: 70 */     this.quadSpace = new ArrayList[segments][segments];
/*  42: 71 */     this.quadSpaceShapes = new Shape[segments][segments];
/*  43:    */     
/*  44: 73 */     float dx = (maxx - minx) / segments;
/*  45: 74 */     float dy = (maxy - miny) / segments;
/*  46: 76 */     for (int x = 0; x < segments; x++) {
/*  47: 77 */       for (int y = 0; y < segments; y++)
/*  48:    */       {
/*  49: 78 */         this.quadSpace[x][y] = new ArrayList();
/*  50:    */         
/*  51:    */ 
/*  52: 81 */         Polygon segmentPolygon = new Polygon();
/*  53: 82 */         segmentPolygon.addPoint(minx + dx * x, miny + dy * y);
/*  54: 83 */         segmentPolygon.addPoint(minx + dx * x + dx, miny + dy * y);
/*  55: 84 */         segmentPolygon.addPoint(minx + dx * x + dx, miny + dy * y + dy);
/*  56: 85 */         segmentPolygon.addPoint(minx + dx * x, miny + dy * y + dy);
/*  57: 87 */         for (int i = 0; i < shapes.size(); i++)
/*  58:    */         {
/*  59: 88 */           Shape shape = (Shape)shapes.get(i);
/*  60: 90 */           if (collides(shape, segmentPolygon)) {
/*  61: 91 */             this.quadSpace[x][y].add(shape);
/*  62:    */           }
/*  63:    */         }
/*  64: 95 */         this.quadSpaceShapes[x][y] = segmentPolygon;
/*  65:    */       }
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   private void removeFromQuadSpace(Shape shape)
/*  70:    */   {
/*  71:106 */     int segments = this.quadSpace.length;
/*  72:108 */     for (int x = 0; x < segments; x++) {
/*  73:109 */       for (int y = 0; y < segments; y++) {
/*  74:110 */         this.quadSpace[x][y].remove(shape);
/*  75:    */       }
/*  76:    */     }
/*  77:    */   }
/*  78:    */   
/*  79:    */   private void addToQuadSpace(Shape shape)
/*  80:    */   {
/*  81:121 */     int segments = this.quadSpace.length;
/*  82:123 */     for (int x = 0; x < segments; x++) {
/*  83:124 */       for (int y = 0; y < segments; y++) {
/*  84:125 */         if (collides(shape, this.quadSpaceShapes[x][y])) {
/*  85:126 */           this.quadSpace[x][y].add(shape);
/*  86:    */         }
/*  87:    */       }
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void init()
/*  92:    */   {
/*  93:136 */     int size = 10;
/*  94:137 */     int[][] map = {
/*  95:138 */       { 0, 0, 0, 0, 0, 0, 0, 3 }, 
/*  96:139 */       { 0, 1, 1, 1, 0, 0, 1, 1, 1 }, 
/*  97:140 */       { 0, 1, 1, 0, 0, 0, 5, 1, 6 }, 
/*  98:141 */       { 0, 1, 2, 0, 0, 0, 4, 1, 1 }, 
/*  99:142 */       { 0, 1, 1, 0, 0, 0, 1, 1 }, 
/* 100:143 */       { 0, 0, 0, 0, 3, 0, 1, 1 }, 
/* 101:144 */       { 0, 0, 0, 1, 1, 0, 0, 0, 1 }, 
/* 102:145 */       { 0, 0, 0, 1, 1 }, 
/* 103:146 */       new int[10], 
/* 104:147 */       new int[10] };
/* 105:160 */     for (int x = 0; x < map[0].length; x++) {
/* 106:161 */       for (int y = 0; y < map.length; y++) {
/* 107:162 */         if (map[y][x] != 0) {
/* 108:163 */           switch (map[y][x])
/* 109:    */           {
/* 110:    */           case 1: 
/* 111:165 */             Polygon p2 = new Polygon();
/* 112:166 */             p2.addPoint(x * 32, y * 32);
/* 113:167 */             p2.addPoint(x * 32 + 32, y * 32);
/* 114:168 */             p2.addPoint(x * 32 + 32, y * 32 + 32);
/* 115:169 */             p2.addPoint(x * 32, y * 32 + 32);
/* 116:170 */             this.original.add(p2);
/* 117:171 */             break;
/* 118:    */           case 2: 
/* 119:173 */             Polygon poly = new Polygon();
/* 120:174 */             poly.addPoint(x * 32, y * 32);
/* 121:175 */             poly.addPoint(x * 32 + 32, y * 32);
/* 122:176 */             poly.addPoint(x * 32, y * 32 + 32);
/* 123:177 */             this.original.add(poly);
/* 124:178 */             break;
/* 125:    */           case 3: 
/* 126:180 */             Circle ellipse = new Circle(x * 32 + 16, y * 32 + 32, 16.0F, 16);
/* 127:181 */             this.original.add(ellipse);
/* 128:182 */             break;
/* 129:    */           case 4: 
/* 130:184 */             Polygon p = new Polygon();
/* 131:185 */             p.addPoint(x * 32 + 32, y * 32);
/* 132:186 */             p.addPoint(x * 32 + 32, y * 32 + 32);
/* 133:187 */             p.addPoint(x * 32, y * 32 + 32);
/* 134:188 */             this.original.add(p);
/* 135:189 */             break;
/* 136:    */           case 5: 
/* 137:191 */             Polygon p3 = new Polygon();
/* 138:192 */             p3.addPoint(x * 32, y * 32);
/* 139:193 */             p3.addPoint(x * 32 + 32, y * 32);
/* 140:194 */             p3.addPoint(x * 32 + 32, y * 32 + 32);
/* 141:195 */             this.original.add(p3);
/* 142:196 */             break;
/* 143:    */           case 6: 
/* 144:198 */             Polygon p4 = new Polygon();
/* 145:199 */             p4.addPoint(x * 32, y * 32);
/* 146:200 */             p4.addPoint(x * 32 + 32, y * 32);
/* 147:201 */             p4.addPoint(x * 32, y * 32 + 32);
/* 148:202 */             this.original.add(p4);
/* 149:    */           }
/* 150:    */         }
/* 151:    */       }
/* 152:    */     }
/* 153:209 */     long before = System.currentTimeMillis();
/* 154:    */     
/* 155:    */ 
/* 156:212 */     generateSpace(this.original, 0.0F, 0.0F, (size + 1) * 32, (size + 1) * 32, 8);
/* 157:213 */     this.combined = combineQuadSpace();
/* 158:    */     
/* 159:    */ 
/* 160:    */ 
/* 161:    */ 
/* 162:218 */     long after = System.currentTimeMillis();
/* 163:219 */     System.out.println("Combine took: " + (after - before));
/* 164:220 */     System.out.println("Combine result: " + this.combined.size());
/* 165:    */   }
/* 166:    */   
/* 167:    */   private ArrayList combineQuadSpace()
/* 168:    */   {
/* 169:229 */     boolean updated = true;
/* 170:    */     int x;
/* 171:230 */     for (; updated; x < this.quadSpace.length)
/* 172:    */     {
/* 173:231 */       updated = false;
/* 174:    */       
/* 175:233 */       x = 0; continue;
/* 176:234 */       for (int y = 0; y < this.quadSpace.length; y++)
/* 177:    */       {
/* 178:235 */         ArrayList shapes = this.quadSpace[x][y];
/* 179:236 */         int before = shapes.size();
/* 180:237 */         combine(shapes);
/* 181:238 */         int after = shapes.size();
/* 182:    */         
/* 183:240 */         updated |= before != after;
/* 184:    */       }
/* 185:233 */       x++;
/* 186:    */     }
/* 187:247 */     HashSet result = new HashSet();
/* 188:249 */     for (int x = 0; x < this.quadSpace.length; x++) {
/* 189:250 */       for (int y = 0; y < this.quadSpace.length; y++) {
/* 190:251 */         result.addAll(this.quadSpace[x][y]);
/* 191:    */       }
/* 192:    */     }
/* 193:255 */     return new ArrayList(result);
/* 194:    */   }
/* 195:    */   
/* 196:    */   private ArrayList combine(ArrayList shapes)
/* 197:    */   {
/* 198:266 */     ArrayList last = shapes;
/* 199:267 */     ArrayList current = shapes;
/* 200:268 */     boolean first = true;
/* 201:270 */     while ((current.size() != last.size()) || (first))
/* 202:    */     {
/* 203:271 */       first = false;
/* 204:272 */       last = current;
/* 205:273 */       current = combineImpl(current);
/* 206:    */     }
/* 207:276 */     ArrayList pruned = new ArrayList();
/* 208:277 */     for (int i = 0; i < current.size(); i++) {
/* 209:278 */       pruned.add(((Shape)current.get(i)).prune());
/* 210:    */     }
/* 211:280 */     return pruned;
/* 212:    */   }
/* 213:    */   
/* 214:    */   private ArrayList combineImpl(ArrayList shapes)
/* 215:    */   {
/* 216:292 */     ArrayList result = new ArrayList(shapes);
/* 217:293 */     if (this.quadSpace != null) {
/* 218:294 */       result = shapes;
/* 219:    */     }
/* 220:297 */     for (int i = 0; i < shapes.size(); i++)
/* 221:    */     {
/* 222:298 */       Shape first = (Shape)shapes.get(i);
/* 223:299 */       for (int j = i + 1; j < shapes.size(); j++)
/* 224:    */       {
/* 225:300 */         Shape second = (Shape)shapes.get(j);
/* 226:302 */         if (first.intersects(second))
/* 227:    */         {
/* 228:306 */           Shape[] joined = this.util.union(first, second);
/* 229:307 */           if (joined.length == 1)
/* 230:    */           {
/* 231:308 */             if (this.quadSpace != null)
/* 232:    */             {
/* 233:309 */               removeFromQuadSpace(first);
/* 234:310 */               removeFromQuadSpace(second);
/* 235:311 */               addToQuadSpace(joined[0]);
/* 236:    */             }
/* 237:    */             else
/* 238:    */             {
/* 239:313 */               result.remove(first);
/* 240:314 */               result.remove(second);
/* 241:315 */               result.add(joined[0]);
/* 242:    */             }
/* 243:317 */             return result;
/* 244:    */           }
/* 245:    */         }
/* 246:    */       }
/* 247:    */     }
/* 248:322 */     return result;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public boolean collides(Shape shape1, Shape shape2)
/* 252:    */   {
/* 253:333 */     if (shape1.intersects(shape2)) {
/* 254:334 */       return true;
/* 255:    */     }
/* 256:336 */     for (int i = 0; i < shape1.getPointCount(); i++)
/* 257:    */     {
/* 258:337 */       float[] pt = shape1.getPoint(i);
/* 259:338 */       if (shape2.contains(pt[0], pt[1])) {
/* 260:339 */         return true;
/* 261:    */       }
/* 262:    */     }
/* 263:342 */     for (int i = 0; i < shape2.getPointCount(); i++)
/* 264:    */     {
/* 265:343 */       float[] pt = shape2.getPoint(i);
/* 266:344 */       if (shape1.contains(pt[0], pt[1])) {
/* 267:345 */         return true;
/* 268:    */       }
/* 269:    */     }
/* 270:349 */     return false;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public void init(GameContainer container)
/* 274:    */     throws SlickException
/* 275:    */   {
/* 276:356 */     this.util.setListener(this);
/* 277:357 */     init();
/* 278:    */   }
/* 279:    */   
/* 280:    */   public void update(GameContainer container, int delta)
/* 281:    */     throws SlickException
/* 282:    */   {}
/* 283:    */   
/* 284:    */   public void render(GameContainer container, Graphics g)
/* 285:    */     throws SlickException
/* 286:    */   {
/* 287:373 */     g.setColor(Color.green);
/* 288:374 */     for (int i = 0; i < this.original.size(); i++)
/* 289:    */     {
/* 290:375 */       Shape shape = (Shape)this.original.get(i);
/* 291:376 */       g.draw(shape);
/* 292:    */     }
/* 293:379 */     g.setColor(Color.white);
/* 294:380 */     if (this.quadSpaceShapes != null) {
/* 295:381 */       g.draw(this.quadSpaceShapes[0][0]);
/* 296:    */     }
/* 297:384 */     g.translate(0.0F, 320.0F);
/* 298:386 */     for (int i = 0; i < this.combined.size(); i++)
/* 299:    */     {
/* 300:387 */       g.setColor(Color.white);
/* 301:388 */       Shape shape = (Shape)this.combined.get(i);
/* 302:389 */       g.draw(shape);
/* 303:390 */       for (int j = 0; j < shape.getPointCount(); j++)
/* 304:    */       {
/* 305:391 */         g.setColor(Color.yellow);
/* 306:392 */         float[] pt = shape.getPoint(j);
/* 307:393 */         g.fillOval(pt[0] - 1.0F, pt[1] - 1.0F, 3.0F, 3.0F);
/* 308:    */       }
/* 309:    */     }
/* 310:    */   }
/* 311:    */   
/* 312:    */   public static void main(String[] argv)
/* 313:    */   {
/* 314:    */     try
/* 315:    */     {
/* 316:407 */       AppGameContainer container = new AppGameContainer(
/* 317:408 */         new GeomUtilTileTest());
/* 318:409 */       container.setDisplayMode(800, 600, false);
/* 319:410 */       container.start();
/* 320:    */     }
/* 321:    */     catch (SlickException e)
/* 322:    */     {
/* 323:412 */       e.printStackTrace();
/* 324:    */     }
/* 325:    */   }
/* 326:    */   
/* 327:    */   public void pointExcluded(float x, float y) {}
/* 328:    */   
/* 329:    */   public void pointIntersected(float x, float y)
/* 330:    */   {
/* 331:420 */     this.intersections.add(new Vector2f(x, y));
/* 332:    */   }
/* 333:    */   
/* 334:    */   public void pointUsed(float x, float y)
/* 335:    */   {
/* 336:424 */     this.used.add(new Vector2f(x, y));
/* 337:    */   }
/* 338:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.GeomUtilTileTest
 * JD-Core Version:    0.7.0.1
 */