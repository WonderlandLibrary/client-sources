/*     */ package org.newdawn.slick.util.pathfinding.navmesh;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
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
/*     */ public class Space
/*     */ {
/*     */   private float x;
/*     */   private float y;
/*     */   private float width;
/*     */   private float height;
/*  22 */   private HashMap links = new HashMap<Object, Object>();
/*     */   
/*  24 */   private ArrayList linksList = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float cost;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Space(float x, float y, float width, float height) {
/*  37 */     this.x = x;
/*  38 */     this.y = y;
/*  39 */     this.width = width;
/*  40 */     this.height = height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getWidth() {
/*  49 */     return this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getHeight() {
/*  58 */     return this.height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getX() {
/*  67 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getY() {
/*  76 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void link(Space other) {
/*  87 */     if (inTolerance(this.x, other.x + other.width) || inTolerance(this.x + this.width, other.x)) {
/*  88 */       float linkx = this.x;
/*  89 */       if (this.x + this.width == other.x) {
/*  90 */         linkx = this.x + this.width;
/*     */       }
/*     */       
/*  93 */       float top = Math.max(this.y, other.y);
/*  94 */       float bottom = Math.min(this.y + this.height, other.y + other.height);
/*  95 */       float linky = top + (bottom - top) / 2.0F;
/*     */       
/*  97 */       Link link = new Link(linkx, linky, other);
/*  98 */       this.links.put(other, link);
/*  99 */       this.linksList.add(link);
/*     */     } 
/*     */     
/* 102 */     if (inTolerance(this.y, other.y + other.height) || inTolerance(this.y + this.height, other.y)) {
/* 103 */       float linky = this.y;
/* 104 */       if (this.y + this.height == other.y) {
/* 105 */         linky = this.y + this.height;
/*     */       }
/*     */       
/* 108 */       float left = Math.max(this.x, other.x);
/* 109 */       float right = Math.min(this.x + this.width, other.x + other.width);
/* 110 */       float linkx = left + (right - left) / 2.0F;
/*     */       
/* 112 */       Link link = new Link(linkx, linky, other);
/* 113 */       this.links.put(other, link);
/* 114 */       this.linksList.add(link);
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
/*     */   
/*     */   private boolean inTolerance(float a, float b) {
/* 127 */     return (a == b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasJoinedEdge(Space other) {
/* 138 */     if (inTolerance(this.x, other.x + other.width) || inTolerance(this.x + this.width, other.x)) {
/* 139 */       if (this.y >= other.y && this.y <= other.y + other.height) {
/* 140 */         return true;
/*     */       }
/* 142 */       if (this.y + this.height >= other.y && this.y + this.height <= other.y + other.height) {
/* 143 */         return true;
/*     */       }
/* 145 */       if (other.y >= this.y && other.y <= this.y + this.height) {
/* 146 */         return true;
/*     */       }
/* 148 */       if (other.y + other.height >= this.y && other.y + other.height <= this.y + this.height) {
/* 149 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 153 */     if (inTolerance(this.y, other.y + other.height) || inTolerance(this.y + this.height, other.y)) {
/* 154 */       if (this.x >= other.x && this.x <= other.x + other.width) {
/* 155 */         return true;
/*     */       }
/* 157 */       if (this.x + this.width >= other.x && this.x + this.width <= other.x + other.width) {
/* 158 */         return true;
/*     */       }
/* 160 */       if (other.x >= this.x && other.x <= this.x + this.width) {
/* 161 */         return true;
/*     */       }
/* 163 */       if (other.x + other.width >= this.x && other.x + other.width <= this.x + this.width) {
/* 164 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 168 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Space merge(Space other) {
/* 178 */     float minx = Math.min(this.x, other.x);
/* 179 */     float miny = Math.min(this.y, other.y);
/*     */     
/* 181 */     float newwidth = this.width + other.width;
/* 182 */     float newheight = this.height + other.height;
/* 183 */     if (this.x == other.x) {
/* 184 */       newwidth = this.width;
/*     */     } else {
/* 186 */       newheight = this.height;
/*     */     } 
/* 188 */     return new Space(minx, miny, newwidth, newheight);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canMerge(Space other) {
/* 199 */     if (!hasJoinedEdge(other)) {
/* 200 */       return false;
/*     */     }
/*     */     
/* 203 */     if (this.x == other.x && this.width == other.width) {
/* 204 */       return true;
/*     */     }
/* 206 */     if (this.y == other.y && this.height == other.height) {
/* 207 */       return true;
/*     */     }
/*     */     
/* 210 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLinkCount() {
/* 219 */     return this.linksList.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Link getLink(int index) {
/* 229 */     return this.linksList.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(float xp, float yp) {
/* 240 */     return (xp >= this.x && xp < this.x + this.width && yp >= this.y && yp < this.y + this.height);
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
/*     */   public void fill(Space target, float sx, float sy, float cost) {
/* 252 */     if (cost >= this.cost) {
/*     */       return;
/*     */     }
/* 255 */     this.cost = cost;
/* 256 */     if (target == this) {
/*     */       return;
/*     */     }
/*     */     
/* 260 */     for (int i = 0; i < getLinkCount(); i++) {
/* 261 */       Link link = getLink(i);
/* 262 */       float extraCost = link.distance2(sx, sy);
/* 263 */       float nextCost = cost + extraCost;
/* 264 */       link.getTarget().fill(target, link.getX(), link.getY(), nextCost);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearCost() {
/* 272 */     this.cost = Float.MAX_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getCost() {
/* 281 */     return this.cost;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean pickLowestCost(Space target, NavPath path) {
/* 292 */     if (target == this) {
/* 293 */       return true;
/*     */     }
/* 295 */     if (this.links.size() == 0) {
/* 296 */       return false;
/*     */     }
/*     */     
/* 299 */     Link bestLink = null;
/* 300 */     for (int i = 0; i < getLinkCount(); i++) {
/* 301 */       Link link = getLink(i);
/* 302 */       if (bestLink == null || link.getTarget().getCost() < bestLink.getTarget().getCost()) {
/* 303 */         bestLink = link;
/*     */       }
/*     */     } 
/*     */     
/* 307 */     path.push(bestLink);
/* 308 */     return bestLink.getTarget().pickLowestCost(target, path);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 317 */     return "[Space " + this.x + "," + this.y + " " + this.width + "," + this.height + "]";
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slic\\util\pathfinding\navmesh\Space.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */