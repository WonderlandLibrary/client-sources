/*     */ package org.newdawn.slick.util.pathfinding.navmesh;
/*     */ 
/*     */ import java.util.ArrayList;
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
/*     */ public class NavMesh
/*     */ {
/*  16 */   private ArrayList spaces = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NavMesh() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NavMesh(ArrayList spaces) {
/*  31 */     this.spaces.addAll(spaces);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSpaceCount() {
/*  40 */     return this.spaces.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Space getSpace(int index) {
/*  50 */     return this.spaces.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSpace(Space space) {
/*  59 */     this.spaces.add(space);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Space findSpace(float x, float y) {
/*  70 */     for (int i = 0; i < this.spaces.size(); i++) {
/*  71 */       Space space = getSpace(i);
/*  72 */       if (space.contains(x, y)) {
/*  73 */         return space;
/*     */       }
/*     */     } 
/*     */     
/*  77 */     return null;
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
/*     */   public NavPath findPath(float sx, float sy, float tx, float ty, boolean optimize) {
/*  91 */     Space source = findSpace(sx, sy);
/*  92 */     Space target = findSpace(tx, ty);
/*     */     
/*  94 */     if (source == null || target == null) {
/*  95 */       return null;
/*     */     }
/*     */     
/*  98 */     for (int i = 0; i < this.spaces.size(); i++) {
/*  99 */       ((Space)this.spaces.get(i)).clearCost();
/*     */     }
/* 101 */     target.fill(source, tx, ty, 0.0F);
/* 102 */     if (target.getCost() == Float.MAX_VALUE) {
/* 103 */       return null;
/*     */     }
/* 105 */     if (source.getCost() == Float.MAX_VALUE) {
/* 106 */       return null;
/*     */     }
/*     */     
/* 109 */     NavPath path = new NavPath();
/* 110 */     path.push(new Link(sx, sy, null));
/* 111 */     if (source.pickLowestCost(target, path)) {
/* 112 */       path.push(new Link(tx, ty, null));
/* 113 */       if (optimize) {
/* 114 */         optimize(path);
/*     */       }
/* 116 */       return path;
/*     */     } 
/*     */     
/* 119 */     return null;
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
/*     */   private boolean isClear(float x1, float y1, float x2, float y2, float step) {
/* 133 */     float dx = x2 - x1;
/* 134 */     float dy = y2 - y1;
/* 135 */     float len = (float)Math.sqrt((dx * dx + dy * dy));
/* 136 */     dx *= step;
/* 137 */     dx /= len;
/* 138 */     dy *= step;
/* 139 */     dy /= len;
/* 140 */     int steps = (int)(len / step);
/*     */     
/* 142 */     for (int i = 0; i < steps; i++) {
/* 143 */       float x = x1 + dx * i;
/* 144 */       float y = y1 + dy * i;
/*     */       
/* 146 */       if (findSpace(x, y) == null) {
/* 147 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 151 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void optimize(NavPath path) {
/* 161 */     int pt = 0;
/*     */     
/* 163 */     while (pt < path.length() - 2) {
/* 164 */       float sx = path.getX(pt);
/* 165 */       float sy = path.getY(pt);
/* 166 */       float nx = path.getX(pt + 2);
/* 167 */       float ny = path.getY(pt + 2);
/*     */       
/* 169 */       if (isClear(sx, sy, nx, ny, 0.1F)) {
/* 170 */         path.remove(pt + 1); continue;
/*     */       } 
/* 172 */       pt++;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slic\\util\pathfinding\navmesh\NavMesh.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */