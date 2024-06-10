/*   1:    */ package org.newdawn.slick.util.pathfinding;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.LinkedList;
/*   5:    */ import java.util.List;
/*   6:    */ import org.newdawn.slick.util.pathfinding.heuristics.ClosestHeuristic;
/*   7:    */ 
/*   8:    */ public class AStarPathFinder
/*   9:    */   implements PathFinder, PathFindingContext
/*  10:    */ {
/*  11: 17 */   private ArrayList closed = new ArrayList();
/*  12: 19 */   private PriorityList open = new PriorityList(null);
/*  13:    */   private TileBasedMap map;
/*  14:    */   private int maxSearchDistance;
/*  15:    */   private Node[][] nodes;
/*  16:    */   private boolean allowDiagMovement;
/*  17:    */   private AStarHeuristic heuristic;
/*  18:    */   private Node current;
/*  19:    */   private Mover mover;
/*  20:    */   private int sourceX;
/*  21:    */   private int sourceY;
/*  22:    */   private int distance;
/*  23:    */   
/*  24:    */   public AStarPathFinder(TileBasedMap map, int maxSearchDistance, boolean allowDiagMovement)
/*  25:    */   {
/*  26: 52 */     this(map, maxSearchDistance, allowDiagMovement, new ClosestHeuristic());
/*  27:    */   }
/*  28:    */   
/*  29:    */   public AStarPathFinder(TileBasedMap map, int maxSearchDistance, boolean allowDiagMovement, AStarHeuristic heuristic)
/*  30:    */   {
/*  31: 65 */     this.heuristic = heuristic;
/*  32: 66 */     this.map = map;
/*  33: 67 */     this.maxSearchDistance = maxSearchDistance;
/*  34: 68 */     this.allowDiagMovement = allowDiagMovement;
/*  35:    */     
/*  36: 70 */     this.nodes = new Node[map.getWidthInTiles()][map.getHeightInTiles()];
/*  37: 71 */     for (int x = 0; x < map.getWidthInTiles(); x++) {
/*  38: 72 */       for (int y = 0; y < map.getHeightInTiles(); y++) {
/*  39: 73 */         this.nodes[x][y] = new Node(x, y);
/*  40:    */       }
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Path findPath(Mover mover, int sx, int sy, int tx, int ty)
/*  45:    */   {
/*  46: 82 */     this.current = null;
/*  47:    */     
/*  48:    */ 
/*  49: 85 */     this.mover = mover;
/*  50: 86 */     this.sourceX = tx;
/*  51: 87 */     this.sourceY = ty;
/*  52: 88 */     this.distance = 0;
/*  53: 90 */     if (this.map.blocked(this, tx, ty)) {
/*  54: 91 */       return null;
/*  55:    */     }
/*  56: 94 */     for (int x = 0; x < this.map.getWidthInTiles(); x++) {
/*  57: 95 */       for (int y = 0; y < this.map.getHeightInTiles(); y++) {
/*  58: 96 */         this.nodes[x][y].reset();
/*  59:    */       }
/*  60:    */     }
/*  61:102 */     this.nodes[sx][sy].cost = 0.0F;
/*  62:103 */     this.nodes[sx][sy].depth = 0;
/*  63:104 */     this.closed.clear();
/*  64:105 */     this.open.clear();
/*  65:106 */     addToOpen(this.nodes[sx][sy]);
/*  66:    */     
/*  67:108 */     this.nodes[tx][ty].parent = null;
/*  68:    */     
/*  69:    */ 
/*  70:111 */     int maxDepth = 0;
/*  71:    */     int x;
/*  72:112 */     for (; (maxDepth < this.maxSearchDistance) && (this.open.size() != 0); x < 2)
/*  73:    */     {
/*  74:115 */       int lx = sx;
/*  75:116 */       int ly = sy;
/*  76:117 */       if (this.current != null)
/*  77:    */       {
/*  78:118 */         lx = this.current.x;
/*  79:119 */         ly = this.current.y;
/*  80:    */       }
/*  81:122 */       this.current = getFirstInOpen();
/*  82:123 */       this.distance = this.current.depth;
/*  83:125 */       if ((this.current == this.nodes[tx][ty]) && 
/*  84:126 */         (isValidLocation(mover, lx, ly, tx, ty))) {
/*  85:    */         break;
/*  86:    */       }
/*  87:131 */       removeFromOpen(this.current);
/*  88:132 */       addToClosed(this.current);
/*  89:    */       
/*  90:    */ 
/*  91:    */ 
/*  92:136 */       x = -1; continue;
/*  93:137 */       for (int y = -1; y < 2; y++) {
/*  94:139 */         if ((x != 0) || (y != 0)) {
/*  95:145 */           if ((this.allowDiagMovement) || 
/*  96:146 */             (x == 0) || (y == 0))
/*  97:    */           {
/*  98:152 */             int xp = x + this.current.x;
/*  99:153 */             int yp = y + this.current.y;
/* 100:155 */             if (isValidLocation(mover, this.current.x, this.current.y, xp, yp))
/* 101:    */             {
/* 102:159 */               float nextStepCost = this.current.cost + getMovementCost(mover, this.current.x, this.current.y, xp, yp);
/* 103:160 */               Node neighbour = this.nodes[xp][yp];
/* 104:161 */               this.map.pathFinderVisited(xp, yp);
/* 105:167 */               if (nextStepCost < neighbour.cost)
/* 106:    */               {
/* 107:168 */                 if (inOpenList(neighbour)) {
/* 108:169 */                   removeFromOpen(neighbour);
/* 109:    */                 }
/* 110:171 */                 if (inClosedList(neighbour)) {
/* 111:172 */                   removeFromClosed(neighbour);
/* 112:    */                 }
/* 113:    */               }
/* 114:179 */               if ((!inOpenList(neighbour)) && (!inClosedList(neighbour)))
/* 115:    */               {
/* 116:180 */                 neighbour.cost = nextStepCost;
/* 117:181 */                 neighbour.heuristic = getHeuristicCost(mover, xp, yp, tx, ty);
/* 118:182 */                 maxDepth = Math.max(maxDepth, neighbour.setParent(this.current));
/* 119:183 */                 addToOpen(neighbour);
/* 120:    */               }
/* 121:    */             }
/* 122:    */           }
/* 123:    */         }
/* 124:    */       }
/* 125:136 */       x++;
/* 126:    */     }
/* 127:192 */     if (this.nodes[tx][ty].parent == null) {
/* 128:193 */       return null;
/* 129:    */     }
/* 130:199 */     Path path = new Path();
/* 131:200 */     Node target = this.nodes[tx][ty];
/* 132:201 */     while (target != this.nodes[sx][sy])
/* 133:    */     {
/* 134:202 */       path.prependStep(target.x, target.y);
/* 135:203 */       target = target.parent;
/* 136:    */     }
/* 137:205 */     path.prependStep(sx, sy);
/* 138:    */     
/* 139:    */ 
/* 140:208 */     return path;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public int getCurrentX()
/* 144:    */   {
/* 145:217 */     if (this.current == null) {
/* 146:218 */       return -1;
/* 147:    */     }
/* 148:221 */     return this.current.x;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public int getCurrentY()
/* 152:    */   {
/* 153:230 */     if (this.current == null) {
/* 154:231 */       return -1;
/* 155:    */     }
/* 156:234 */     return this.current.y;
/* 157:    */   }
/* 158:    */   
/* 159:    */   protected Node getFirstInOpen()
/* 160:    */   {
/* 161:244 */     return (Node)this.open.first();
/* 162:    */   }
/* 163:    */   
/* 164:    */   protected void addToOpen(Node node)
/* 165:    */   {
/* 166:253 */     node.setOpen(true);
/* 167:254 */     this.open.add(node);
/* 168:    */   }
/* 169:    */   
/* 170:    */   protected boolean inOpenList(Node node)
/* 171:    */   {
/* 172:264 */     return node.isOpen();
/* 173:    */   }
/* 174:    */   
/* 175:    */   protected void removeFromOpen(Node node)
/* 176:    */   {
/* 177:273 */     node.setOpen(false);
/* 178:274 */     this.open.remove(node);
/* 179:    */   }
/* 180:    */   
/* 181:    */   protected void addToClosed(Node node)
/* 182:    */   {
/* 183:283 */     node.setClosed(true);
/* 184:284 */     this.closed.add(node);
/* 185:    */   }
/* 186:    */   
/* 187:    */   protected boolean inClosedList(Node node)
/* 188:    */   {
/* 189:294 */     return node.isClosed();
/* 190:    */   }
/* 191:    */   
/* 192:    */   protected void removeFromClosed(Node node)
/* 193:    */   {
/* 194:303 */     node.setClosed(false);
/* 195:304 */     this.closed.remove(node);
/* 196:    */   }
/* 197:    */   
/* 198:    */   protected boolean isValidLocation(Mover mover, int sx, int sy, int x, int y)
/* 199:    */   {
/* 200:318 */     boolean invalid = (x < 0) || (y < 0) || (x >= this.map.getWidthInTiles()) || (y >= this.map.getHeightInTiles());
/* 201:320 */     if ((!invalid) && ((sx != x) || (sy != y)))
/* 202:    */     {
/* 203:321 */       this.mover = mover;
/* 204:322 */       this.sourceX = sx;
/* 205:323 */       this.sourceY = sy;
/* 206:324 */       invalid = this.map.blocked(this, x, y);
/* 207:    */     }
/* 208:327 */     return !invalid;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public float getMovementCost(Mover mover, int sx, int sy, int tx, int ty)
/* 212:    */   {
/* 213:341 */     this.mover = mover;
/* 214:342 */     this.sourceX = sx;
/* 215:343 */     this.sourceY = sy;
/* 216:    */     
/* 217:345 */     return this.map.getCost(this, tx, ty);
/* 218:    */   }
/* 219:    */   
/* 220:    */   public float getHeuristicCost(Mover mover, int x, int y, int tx, int ty)
/* 221:    */   {
/* 222:360 */     return this.heuristic.getCost(this.map, mover, x, y, tx, ty);
/* 223:    */   }
/* 224:    */   
/* 225:    */   private class PriorityList
/* 226:    */   {
/* 227:370 */     private List list = new LinkedList();
/* 228:    */     
/* 229:    */     private PriorityList() {}
/* 230:    */     
/* 231:    */     public Object first()
/* 232:    */     {
/* 233:378 */       return this.list.get(0);
/* 234:    */     }
/* 235:    */     
/* 236:    */     public void clear()
/* 237:    */     {
/* 238:385 */       this.list.clear();
/* 239:    */     }
/* 240:    */     
/* 241:    */     public void add(Object o)
/* 242:    */     {
/* 243:395 */       for (int i = 0; i < this.list.size(); i++) {
/* 244:396 */         if (((Comparable)this.list.get(i)).compareTo(o) > 0)
/* 245:    */         {
/* 246:397 */           this.list.add(i, o);
/* 247:398 */           break;
/* 248:    */         }
/* 249:    */       }
/* 250:401 */       if (!this.list.contains(o)) {
/* 251:402 */         this.list.add(o);
/* 252:    */       }
/* 253:    */     }
/* 254:    */     
/* 255:    */     public void remove(Object o)
/* 256:    */     {
/* 257:413 */       this.list.remove(o);
/* 258:    */     }
/* 259:    */     
/* 260:    */     public int size()
/* 261:    */     {
/* 262:422 */       return this.list.size();
/* 263:    */     }
/* 264:    */     
/* 265:    */     public boolean contains(Object o)
/* 266:    */     {
/* 267:432 */       return this.list.contains(o);
/* 268:    */     }
/* 269:    */     
/* 270:    */     public String toString()
/* 271:    */     {
/* 272:436 */       String temp = "{";
/* 273:437 */       for (int i = 0; i < size(); i++) {
/* 274:438 */         temp = temp + this.list.get(i).toString() + ",";
/* 275:    */       }
/* 276:440 */       temp = temp + "}";
/* 277:    */       
/* 278:442 */       return temp;
/* 279:    */     }
/* 280:    */   }
/* 281:    */   
/* 282:    */   private class Node
/* 283:    */     implements Comparable
/* 284:    */   {
/* 285:    */     private int x;
/* 286:    */     private int y;
/* 287:    */     private float cost;
/* 288:    */     private Node parent;
/* 289:    */     private float heuristic;
/* 290:    */     private int depth;
/* 291:    */     private boolean open;
/* 292:    */     private boolean closed;
/* 293:    */     
/* 294:    */     public Node(int x, int y)
/* 295:    */     {
/* 296:474 */       this.x = x;
/* 297:475 */       this.y = y;
/* 298:    */     }
/* 299:    */     
/* 300:    */     public int setParent(Node parent)
/* 301:    */     {
/* 302:485 */       parent.depth += 1;
/* 303:486 */       this.parent = parent;
/* 304:    */       
/* 305:488 */       return this.depth;
/* 306:    */     }
/* 307:    */     
/* 308:    */     public int compareTo(Object other)
/* 309:    */     {
/* 310:495 */       Node o = (Node)other;
/* 311:    */       
/* 312:497 */       float f = this.heuristic + this.cost;
/* 313:498 */       float of = o.heuristic + o.cost;
/* 314:500 */       if (f < of) {
/* 315:501 */         return -1;
/* 316:    */       }
/* 317:502 */       if (f > of) {
/* 318:503 */         return 1;
/* 319:    */       }
/* 320:505 */       return 0;
/* 321:    */     }
/* 322:    */     
/* 323:    */     public void setOpen(boolean open)
/* 324:    */     {
/* 325:515 */       this.open = open;
/* 326:    */     }
/* 327:    */     
/* 328:    */     public boolean isOpen()
/* 329:    */     {
/* 330:524 */       return this.open;
/* 331:    */     }
/* 332:    */     
/* 333:    */     public void setClosed(boolean closed)
/* 334:    */     {
/* 335:533 */       this.closed = closed;
/* 336:    */     }
/* 337:    */     
/* 338:    */     public boolean isClosed()
/* 339:    */     {
/* 340:542 */       return this.closed;
/* 341:    */     }
/* 342:    */     
/* 343:    */     public void reset()
/* 344:    */     {
/* 345:549 */       this.closed = false;
/* 346:550 */       this.open = false;
/* 347:551 */       this.cost = 0.0F;
/* 348:552 */       this.depth = 0;
/* 349:    */     }
/* 350:    */     
/* 351:    */     public String toString()
/* 352:    */     {
/* 353:559 */       return "[Node " + this.x + "," + this.y + "]";
/* 354:    */     }
/* 355:    */   }
/* 356:    */   
/* 357:    */   public Mover getMover()
/* 358:    */   {
/* 359:567 */     return this.mover;
/* 360:    */   }
/* 361:    */   
/* 362:    */   public int getSearchDistance()
/* 363:    */   {
/* 364:574 */     return this.distance;
/* 365:    */   }
/* 366:    */   
/* 367:    */   public int getSourceX()
/* 368:    */   {
/* 369:581 */     return this.sourceX;
/* 370:    */   }
/* 371:    */   
/* 372:    */   public int getSourceY()
/* 373:    */   {
/* 374:588 */     return this.sourceY;
/* 375:    */   }
/* 376:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.util.pathfinding.AStarPathFinder
 * JD-Core Version:    0.7.0.1
 */