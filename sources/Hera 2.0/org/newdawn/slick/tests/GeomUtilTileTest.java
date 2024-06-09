/*     */ package org.newdawn.slick.tests;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import org.newdawn.slick.AppGameContainer;
/*     */ import org.newdawn.slick.BasicGame;
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.Game;
/*     */ import org.newdawn.slick.GameContainer;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.SlickException;
/*     */ import org.newdawn.slick.geom.Circle;
/*     */ import org.newdawn.slick.geom.GeomUtil;
/*     */ import org.newdawn.slick.geom.GeomUtilListener;
/*     */ import org.newdawn.slick.geom.Polygon;
/*     */ import org.newdawn.slick.geom.Shape;
/*     */ import org.newdawn.slick.geom.Vector2f;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GeomUtilTileTest
/*     */   extends BasicGame
/*     */   implements GeomUtilListener
/*     */ {
/*     */   private Shape source;
/*     */   private Shape cut;
/*     */   private Shape[] result;
/*  33 */   private GeomUtil util = new GeomUtil();
/*     */ 
/*     */   
/*  36 */   private ArrayList original = new ArrayList();
/*     */   
/*  38 */   private ArrayList combined = new ArrayList();
/*     */ 
/*     */   
/*  41 */   private ArrayList intersections = new ArrayList();
/*     */   
/*  43 */   private ArrayList used = new ArrayList();
/*     */ 
/*     */   
/*     */   private ArrayList[][] quadSpace;
/*     */ 
/*     */   
/*     */   private Shape[][] quadSpaceShapes;
/*     */ 
/*     */ 
/*     */   
/*     */   public GeomUtilTileTest() {
/*  54 */     super("GeomUtilTileTest");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void generateSpace(ArrayList<Shape> shapes, float minx, float miny, float maxx, float maxy, int segments) {
/*  70 */     this.quadSpace = new ArrayList[segments][segments];
/*  71 */     this.quadSpaceShapes = new Shape[segments][segments];
/*     */     
/*  73 */     float dx = (maxx - minx) / segments;
/*  74 */     float dy = (maxy - miny) / segments;
/*     */     
/*  76 */     for (int x = 0; x < segments; x++) {
/*  77 */       for (int y = 0; y < segments; y++) {
/*  78 */         this.quadSpace[x][y] = new ArrayList();
/*     */ 
/*     */         
/*  81 */         Polygon segmentPolygon = new Polygon();
/*  82 */         segmentPolygon.addPoint(minx + dx * x, miny + dy * y);
/*  83 */         segmentPolygon.addPoint(minx + dx * x + dx, miny + dy * y);
/*  84 */         segmentPolygon.addPoint(minx + dx * x + dx, miny + dy * y + dy);
/*  85 */         segmentPolygon.addPoint(minx + dx * x, miny + dy * y + dy);
/*     */         
/*  87 */         for (int i = 0; i < shapes.size(); i++) {
/*  88 */           Shape shape = shapes.get(i);
/*     */           
/*  90 */           if (collides(shape, (Shape)segmentPolygon)) {
/*  91 */             this.quadSpace[x][y].add(shape);
/*     */           }
/*     */         } 
/*     */         
/*  95 */         this.quadSpaceShapes[x][y] = (Shape)segmentPolygon;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void removeFromQuadSpace(Shape shape) {
/* 106 */     int segments = this.quadSpace.length;
/*     */     
/* 108 */     for (int x = 0; x < segments; x++) {
/* 109 */       for (int y = 0; y < segments; y++) {
/* 110 */         this.quadSpace[x][y].remove(shape);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addToQuadSpace(Shape shape) {
/* 121 */     int segments = this.quadSpace.length;
/*     */     
/* 123 */     for (int x = 0; x < segments; x++) {
/* 124 */       for (int y = 0; y < segments; y++) {
/* 125 */         if (collides(shape, this.quadSpaceShapes[x][y])) {
/* 126 */           this.quadSpace[x][y].add(shape);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/* 136 */     int size = 10;
/* 137 */     int[][] map = { { 0, 0, 0, 0, 0, 0, 0, 3, 0, 0 }, { 0, 1, 1, 1, 0, 0, 1, 1, 1, 0 }, { 0, 1, 1, 0, 0, 0, 5, 1, 6, 0 }, { 0, 1, 2, 0, 0, 0, 4, 1, 1, 0 }, { 0, 1, 1, 0, 0, 0, 1, 1, 0, 0 }, { 0, 0, 0, 0, 3, 0, 1, 1, 0, 0 }, { 0, 0, 0, 1, 1, 0, 0, 0, 1, 0 }, { 0, 0, 0, 1, 1, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 160 */     for (int x = 0; x < (map[0]).length; x++) {
/* 161 */       for (int y = 0; y < map.length; y++) {
/* 162 */         if (map[y][x] != 0) {
/* 163 */           Polygon p2; Polygon poly; Circle ellipse; Polygon p; Polygon p3; Polygon p4; switch (map[y][x]) {
/*     */             case 1:
/* 165 */               p2 = new Polygon();
/* 166 */               p2.addPoint((x * 32), (y * 32));
/* 167 */               p2.addPoint((x * 32 + 32), (y * 32));
/* 168 */               p2.addPoint((x * 32 + 32), (y * 32 + 32));
/* 169 */               p2.addPoint((x * 32), (y * 32 + 32));
/* 170 */               this.original.add(p2);
/*     */               break;
/*     */             case 2:
/* 173 */               poly = new Polygon();
/* 174 */               poly.addPoint((x * 32), (y * 32));
/* 175 */               poly.addPoint((x * 32 + 32), (y * 32));
/* 176 */               poly.addPoint((x * 32), (y * 32 + 32));
/* 177 */               this.original.add(poly);
/*     */               break;
/*     */             case 3:
/* 180 */               ellipse = new Circle((x * 32 + 16), (y * 32 + 32), 16.0F, 16);
/* 181 */               this.original.add(ellipse);
/*     */               break;
/*     */             case 4:
/* 184 */               p = new Polygon();
/* 185 */               p.addPoint((x * 32 + 32), (y * 32));
/* 186 */               p.addPoint((x * 32 + 32), (y * 32 + 32));
/* 187 */               p.addPoint((x * 32), (y * 32 + 32));
/* 188 */               this.original.add(p);
/*     */               break;
/*     */             case 5:
/* 191 */               p3 = new Polygon();
/* 192 */               p3.addPoint((x * 32), (y * 32));
/* 193 */               p3.addPoint((x * 32 + 32), (y * 32));
/* 194 */               p3.addPoint((x * 32 + 32), (y * 32 + 32));
/* 195 */               this.original.add(p3);
/*     */               break;
/*     */             case 6:
/* 198 */               p4 = new Polygon();
/* 199 */               p4.addPoint((x * 32), (y * 32));
/* 200 */               p4.addPoint((x * 32 + 32), (y * 32));
/* 201 */               p4.addPoint((x * 32), (y * 32 + 32));
/* 202 */               this.original.add(p4);
/*     */               break;
/*     */           } 
/*     */         
/*     */         } 
/*     */       } 
/*     */     } 
/* 209 */     long before = System.currentTimeMillis();
/*     */ 
/*     */     
/* 212 */     generateSpace(this.original, 0.0F, 0.0F, ((size + 1) * 32), ((size + 1) * 32), 8);
/* 213 */     this.combined = combineQuadSpace();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 218 */     long after = System.currentTimeMillis();
/* 219 */     System.out.println("Combine took: " + (after - before));
/* 220 */     System.out.println("Combine result: " + this.combined.size());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ArrayList combineQuadSpace() {
/* 229 */     boolean updated = true;
/* 230 */     while (updated) {
/* 231 */       updated = false;
/*     */       
/* 233 */       for (int i = 0; i < this.quadSpace.length; i++) {
/* 234 */         for (int y = 0; y < this.quadSpace.length; y++) {
/* 235 */           ArrayList shapes = this.quadSpace[i][y];
/* 236 */           int before = shapes.size();
/* 237 */           combine(shapes);
/* 238 */           int after = shapes.size();
/*     */           
/* 240 */           int j = updated | ((before != after) ? 1 : 0);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 247 */     HashSet<?> result = new HashSet();
/*     */     
/* 249 */     for (int x = 0; x < this.quadSpace.length; x++) {
/* 250 */       for (int y = 0; y < this.quadSpace.length; y++) {
/* 251 */         result.addAll(this.quadSpace[x][y]);
/*     */       }
/*     */     } 
/*     */     
/* 255 */     return new ArrayList(result);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ArrayList combine(ArrayList<Shape> shapes) {
/* 266 */     ArrayList<Shape> last = shapes;
/* 267 */     ArrayList<Shape> current = shapes;
/* 268 */     boolean first = true;
/*     */     
/* 270 */     while (current.size() != last.size() || first) {
/* 271 */       first = false;
/* 272 */       last = current;
/* 273 */       current = combineImpl(current);
/*     */     } 
/*     */     
/* 276 */     ArrayList<Shape> pruned = new ArrayList();
/* 277 */     for (int i = 0; i < current.size(); i++) {
/* 278 */       pruned.add(((Shape)current.get(i)).prune());
/*     */     }
/* 280 */     return pruned;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ArrayList combineImpl(ArrayList<?> shapes) {
/* 292 */     ArrayList<?> result = new ArrayList(shapes);
/* 293 */     if (this.quadSpace != null) {
/* 294 */       result = shapes;
/*     */     }
/*     */     
/* 297 */     for (int i = 0; i < shapes.size(); i++) {
/* 298 */       Shape first = (Shape)shapes.get(i);
/* 299 */       for (int j = i + 1; j < shapes.size(); j++) {
/* 300 */         Shape second = (Shape)shapes.get(j);
/*     */         
/* 302 */         if (first.intersects(second)) {
/*     */ 
/*     */ 
/*     */           
/* 306 */           Shape[] joined = this.util.union(first, second);
/* 307 */           if (joined.length == 1) {
/* 308 */             if (this.quadSpace != null) {
/* 309 */               removeFromQuadSpace(first);
/* 310 */               removeFromQuadSpace(second);
/* 311 */               addToQuadSpace(joined[0]);
/*     */             } else {
/* 313 */               result.remove(first);
/* 314 */               result.remove(second);
/* 315 */               result.add(joined[0]);
/*     */             } 
/* 317 */             return result;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 322 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean collides(Shape shape1, Shape shape2) {
/* 333 */     if (shape1.intersects(shape2))
/* 334 */       return true; 
/*     */     int i;
/* 336 */     for (i = 0; i < shape1.getPointCount(); i++) {
/* 337 */       float[] pt = shape1.getPoint(i);
/* 338 */       if (shape2.contains(pt[0], pt[1])) {
/* 339 */         return true;
/*     */       }
/*     */     } 
/* 342 */     for (i = 0; i < shape2.getPointCount(); i++) {
/* 343 */       float[] pt = shape2.getPoint(i);
/* 344 */       if (shape1.contains(pt[0], pt[1])) {
/* 345 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 349 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GameContainer container) throws SlickException {
/* 356 */     this.util.setListener(this);
/* 357 */     init();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(GameContainer container, int delta) throws SlickException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(GameContainer container, Graphics g) throws SlickException {
/* 373 */     g.setColor(Color.green); int i;
/* 374 */     for (i = 0; i < this.original.size(); i++) {
/* 375 */       Shape shape = this.original.get(i);
/* 376 */       g.draw(shape);
/*     */     } 
/*     */     
/* 379 */     g.setColor(Color.white);
/* 380 */     if (this.quadSpaceShapes != null) {
/* 381 */       g.draw(this.quadSpaceShapes[0][0]);
/*     */     }
/*     */     
/* 384 */     g.translate(0.0F, 320.0F);
/*     */     
/* 386 */     for (i = 0; i < this.combined.size(); i++) {
/* 387 */       g.setColor(Color.white);
/* 388 */       Shape shape = this.combined.get(i);
/* 389 */       g.draw(shape);
/* 390 */       for (int j = 0; j < shape.getPointCount(); j++) {
/* 391 */         g.setColor(Color.yellow);
/* 392 */         float[] pt = shape.getPoint(j);
/* 393 */         g.fillOval(pt[0] - 1.0F, pt[1] - 1.0F, 3.0F, 3.0F);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] argv) {
/*     */     try {
/* 407 */       AppGameContainer container = new AppGameContainer((Game)new GeomUtilTileTest());
/*     */       
/* 409 */       container.setDisplayMode(800, 600, false);
/* 410 */       container.start();
/* 411 */     } catch (SlickException e) {
/* 412 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void pointExcluded(float x, float y) {}
/*     */   
/*     */   public void pointIntersected(float x, float y) {
/* 420 */     this.intersections.add(new Vector2f(x, y));
/*     */   }
/*     */   
/*     */   public void pointUsed(float x, float y) {
/* 424 */     this.used.add(new Vector2f(x, y));
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\GeomUtilTileTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */