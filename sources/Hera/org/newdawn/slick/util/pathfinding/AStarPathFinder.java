/*     */ package org.newdawn.slick.util.pathfinding;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.newdawn.slick.util.pathfinding.heuristics.ClosestHeuristic;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AStarPathFinder
/*     */   implements PathFinder, PathFindingContext
/*     */ {
/*  17 */   private ArrayList closed = new ArrayList();
/*     */   
/*  19 */   private PriorityList open = new PriorityList();
/*     */ 
/*     */   
/*     */   private TileBasedMap map;
/*     */ 
/*     */   
/*     */   private int maxSearchDistance;
/*     */ 
/*     */   
/*     */   private Node[][] nodes;
/*     */ 
/*     */   
/*     */   private boolean allowDiagMovement;
/*     */ 
/*     */   
/*     */   private AStarHeuristic heuristic;
/*     */ 
/*     */   
/*     */   private Node current;
/*     */ 
/*     */   
/*     */   private Mover mover;
/*     */ 
/*     */   
/*     */   private int sourceX;
/*     */ 
/*     */   
/*     */   private int sourceY;
/*     */   
/*     */   private int distance;
/*     */ 
/*     */   
/*     */   public AStarPathFinder(TileBasedMap map, int maxSearchDistance, boolean allowDiagMovement) {
/*  52 */     this(map, maxSearchDistance, allowDiagMovement, (AStarHeuristic)new ClosestHeuristic());
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
/*     */   public AStarPathFinder(TileBasedMap map, int maxSearchDistance, boolean allowDiagMovement, AStarHeuristic heuristic) {
/*  65 */     this.heuristic = heuristic;
/*  66 */     this.map = map;
/*  67 */     this.maxSearchDistance = maxSearchDistance;
/*  68 */     this.allowDiagMovement = allowDiagMovement;
/*     */     
/*  70 */     this.nodes = new Node[map.getWidthInTiles()][map.getHeightInTiles()];
/*  71 */     for (int x = 0; x < map.getWidthInTiles(); x++) {
/*  72 */       for (int y = 0; y < map.getHeightInTiles(); y++) {
/*  73 */         this.nodes[x][y] = new Node(x, y);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Path findPath(Mover mover, int sx, int sy, int tx, int ty) {
/*  82 */     this.current = null;
/*     */ 
/*     */     
/*  85 */     this.mover = mover;
/*  86 */     this.sourceX = tx;
/*  87 */     this.sourceY = ty;
/*  88 */     this.distance = 0;
/*     */     
/*  90 */     if (this.map.blocked(this, tx, ty)) {
/*  91 */       return null;
/*     */     }
/*     */     
/*  94 */     for (int x = 0; x < this.map.getWidthInTiles(); x++) {
/*  95 */       for (int y = 0; y < this.map.getHeightInTiles(); y++) {
/*  96 */         this.nodes[x][y].reset();
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 102 */     (this.nodes[sx][sy]).cost = 0.0F;
/* 103 */     (this.nodes[sx][sy]).depth = 0;
/* 104 */     this.closed.clear();
/* 105 */     this.open.clear();
/* 106 */     addToOpen(this.nodes[sx][sy]);
/*     */     
/* 108 */     (this.nodes[tx][ty]).parent = null;
/*     */ 
/*     */     
/* 111 */     int maxDepth = 0;
/* 112 */     while (maxDepth < this.maxSearchDistance && this.open.size() != 0) {
/*     */ 
/*     */       
/* 115 */       int lx = sx;
/* 116 */       int ly = sy;
/* 117 */       if (this.current != null) {
/* 118 */         lx = this.current.x;
/* 119 */         ly = this.current.y;
/*     */       } 
/*     */       
/* 122 */       this.current = getFirstInOpen();
/* 123 */       this.distance = this.current.depth;
/*     */       
/* 125 */       if (this.current == this.nodes[tx][ty] && 
/* 126 */         isValidLocation(mover, lx, ly, tx, ty)) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/* 131 */       removeFromOpen(this.current);
/* 132 */       addToClosed(this.current);
/*     */ 
/*     */ 
/*     */       
/* 136 */       for (int i = -1; i < 2; i++) {
/* 137 */         for (int y = -1; y < 2; y++) {
/*     */           
/* 139 */           if (i != 0 || y != 0)
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 145 */             if (this.allowDiagMovement || 
/* 146 */               i == 0 || y == 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 152 */               int xp = i + this.current.x;
/* 153 */               int yp = y + this.current.y;
/*     */               
/* 155 */               if (isValidLocation(mover, this.current.x, this.current.y, xp, yp)) {
/*     */ 
/*     */ 
/*     */                 
/* 159 */                 float nextStepCost = this.current.cost + getMovementCost(mover, this.current.x, this.current.y, xp, yp);
/* 160 */                 Node neighbour = this.nodes[xp][yp];
/* 161 */                 this.map.pathFinderVisited(xp, yp);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 167 */                 if (nextStepCost < neighbour.cost) {
/* 168 */                   if (inOpenList(neighbour)) {
/* 169 */                     removeFromOpen(neighbour);
/*     */                   }
/* 171 */                   if (inClosedList(neighbour)) {
/* 172 */                     removeFromClosed(neighbour);
/*     */                   }
/*     */                 } 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 179 */                 if (!inOpenList(neighbour) && !inClosedList(neighbour)) {
/* 180 */                   neighbour.cost = nextStepCost;
/* 181 */                   neighbour.heuristic = getHeuristicCost(mover, xp, yp, tx, ty);
/* 182 */                   maxDepth = Math.max(maxDepth, neighbour.setParent(this.current));
/* 183 */                   addToOpen(neighbour);
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 192 */     if ((this.nodes[tx][ty]).parent == null) {
/* 193 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 199 */     Path path = new Path();
/* 200 */     Node target = this.nodes[tx][ty];
/* 201 */     while (target != this.nodes[sx][sy]) {
/* 202 */       path.prependStep(target.x, target.y);
/* 203 */       target = target.parent;
/*     */     } 
/* 205 */     path.prependStep(sx, sy);
/*     */ 
/*     */     
/* 208 */     return path;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCurrentX() {
/* 217 */     if (this.current == null) {
/* 218 */       return -1;
/*     */     }
/*     */     
/* 221 */     return this.current.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCurrentY() {
/* 230 */     if (this.current == null) {
/* 231 */       return -1;
/*     */     }
/*     */     
/* 234 */     return this.current.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Node getFirstInOpen() {
/* 244 */     return (Node)this.open.first();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addToOpen(Node node) {
/* 253 */     node.setOpen(true);
/* 254 */     this.open.add(node);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean inOpenList(Node node) {
/* 264 */     return node.isOpen();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void removeFromOpen(Node node) {
/* 273 */     node.setOpen(false);
/* 274 */     this.open.remove(node);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addToClosed(Node node) {
/* 283 */     node.setClosed(true);
/* 284 */     this.closed.add(node);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean inClosedList(Node node) {
/* 294 */     return node.isClosed();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void removeFromClosed(Node node) {
/* 303 */     node.setClosed(false);
/* 304 */     this.closed.remove(node);
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
/*     */   protected boolean isValidLocation(Mover mover, int sx, int sy, int x, int y) {
/* 318 */     boolean invalid = (x < 0 || y < 0 || x >= this.map.getWidthInTiles() || y >= this.map.getHeightInTiles());
/*     */     
/* 320 */     if (!invalid && (sx != x || sy != y)) {
/* 321 */       this.mover = mover;
/* 322 */       this.sourceX = sx;
/* 323 */       this.sourceY = sy;
/* 324 */       invalid = this.map.blocked(this, x, y);
/*     */     } 
/*     */     
/* 327 */     return !invalid;
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
/*     */   public float getMovementCost(Mover mover, int sx, int sy, int tx, int ty) {
/* 341 */     this.mover = mover;
/* 342 */     this.sourceX = sx;
/* 343 */     this.sourceY = sy;
/*     */     
/* 345 */     return this.map.getCost(this, tx, ty);
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
/*     */   public float getHeuristicCost(Mover mover, int x, int y, int tx, int ty) {
/* 360 */     return this.heuristic.getCost(this.map, mover, x, y, tx, ty);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class PriorityList
/*     */   {
/* 370 */     private List list = new LinkedList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object first() {
/* 378 */       return this.list.get(0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void clear() {
/* 385 */       this.list.clear();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void add(Object o) {
/* 395 */       for (int i = 0; i < this.list.size(); i++) {
/* 396 */         if (((Comparable<Object>)this.list.get(i)).compareTo(o) > 0) {
/* 397 */           this.list.add(i, o);
/*     */           break;
/*     */         } 
/*     */       } 
/* 401 */       if (!this.list.contains(o)) {
/* 402 */         this.list.add(o);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void remove(Object o) {
/* 413 */       this.list.remove(o);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int size() {
/* 422 */       return this.list.size();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 432 */       return this.list.contains(o);
/*     */     }
/*     */     
/*     */     public String toString() {
/* 436 */       String temp = "{";
/* 437 */       for (int i = 0; i < size(); i++) {
/* 438 */         temp = temp + this.list.get(i).toString() + ",";
/*     */       }
/* 440 */       temp = temp + "}";
/*     */       
/* 442 */       return temp;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private PriorityList() {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class Node
/*     */     implements Comparable
/*     */   {
/*     */     private int x;
/*     */ 
/*     */     
/*     */     private int y;
/*     */     
/*     */     private float cost;
/*     */     
/*     */     private Node parent;
/*     */     
/*     */     private float heuristic;
/*     */     
/*     */     private int depth;
/*     */     
/*     */     private boolean open;
/*     */     
/*     */     private boolean closed;
/*     */ 
/*     */     
/*     */     public Node(int x, int y) {
/* 474 */       this.x = x;
/* 475 */       this.y = y;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int setParent(Node parent) {
/* 485 */       parent.depth++;
/* 486 */       this.parent = parent;
/*     */       
/* 488 */       return this.depth;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int compareTo(Object other) {
/* 495 */       Node o = (Node)other;
/*     */       
/* 497 */       float f = this.heuristic + this.cost;
/* 498 */       float of = o.heuristic + o.cost;
/*     */       
/* 500 */       if (f < of)
/* 501 */         return -1; 
/* 502 */       if (f > of) {
/* 503 */         return 1;
/*     */       }
/* 505 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setOpen(boolean open) {
/* 515 */       this.open = open;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isOpen() {
/* 524 */       return this.open;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setClosed(boolean closed) {
/* 533 */       this.closed = closed;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isClosed() {
/* 542 */       return this.closed;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void reset() {
/* 549 */       this.closed = false;
/* 550 */       this.open = false;
/* 551 */       this.cost = 0.0F;
/* 552 */       this.depth = 0;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 559 */       return "[Node " + this.x + "," + this.y + "]";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Mover getMover() {
/* 567 */     return this.mover;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSearchDistance() {
/* 574 */     return this.distance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSourceX() {
/* 581 */     return this.sourceX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSourceY() {
/* 588 */     return this.sourceY;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slic\\util\pathfinding\AStarPathFinder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */