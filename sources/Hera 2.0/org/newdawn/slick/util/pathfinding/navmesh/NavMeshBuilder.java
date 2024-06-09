/*     */ package org.newdawn.slick.util.pathfinding.navmesh;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import org.newdawn.slick.util.pathfinding.Mover;
/*     */ import org.newdawn.slick.util.pathfinding.PathFindingContext;
/*     */ import org.newdawn.slick.util.pathfinding.TileBasedMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NavMeshBuilder
/*     */   implements PathFindingContext
/*     */ {
/*     */   private int sx;
/*     */   private int sy;
/*  21 */   private float smallestSpace = 0.2F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean tileBased;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NavMesh build(TileBasedMap map) {
/*  33 */     return build(map, true);
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
/*     */   public NavMesh build(TileBasedMap map, boolean tileBased) {
/*  45 */     this.tileBased = tileBased;
/*     */     
/*  47 */     ArrayList<Space> spaces = new ArrayList();
/*     */     
/*  49 */     if (tileBased) {
/*  50 */       for (int x = 0; x < map.getWidthInTiles(); x++) {
/*  51 */         for (int y = 0; y < map.getHeightInTiles(); y++) {
/*  52 */           if (!map.blocked(this, x, y)) {
/*  53 */             spaces.add(new Space(x, y, 1.0F, 1.0F));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } else {
/*  58 */       Space space = new Space(0.0F, 0.0F, map.getWidthInTiles(), map.getHeightInTiles());
/*     */       
/*  60 */       subsection(map, space, spaces);
/*     */     } 
/*     */     
/*  63 */     while (mergeSpaces(spaces));
/*  64 */     linkSpaces(spaces);
/*     */     
/*  66 */     return new NavMesh(spaces);
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
/*     */   private boolean mergeSpaces(ArrayList<Space> spaces) {
/*  78 */     for (int source = 0; source < spaces.size(); source++) {
/*  79 */       Space a = spaces.get(source);
/*     */       
/*  81 */       for (int target = source + 1; target < spaces.size(); target++) {
/*  82 */         Space b = spaces.get(target);
/*     */         
/*  84 */         if (a.canMerge(b)) {
/*  85 */           spaces.remove(a);
/*  86 */           spaces.remove(b);
/*  87 */           spaces.add(a.merge(b));
/*  88 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  93 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void linkSpaces(ArrayList<Space> spaces) {
/* 102 */     for (int source = 0; source < spaces.size(); source++) {
/* 103 */       Space a = spaces.get(source);
/*     */       
/* 105 */       for (int target = source + 1; target < spaces.size(); target++) {
/* 106 */         Space b = spaces.get(target);
/*     */         
/* 108 */         if (a.hasJoinedEdge(b)) {
/* 109 */           a.link(b);
/* 110 */           b.link(a);
/*     */         } 
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
/*     */   public boolean clear(TileBasedMap map, Space space) {
/* 124 */     if (this.tileBased) {
/* 125 */       return true;
/*     */     }
/*     */     
/* 128 */     float x = 0.0F;
/* 129 */     boolean donex = false;
/*     */     
/* 131 */     while (x < space.getWidth()) {
/* 132 */       float y = 0.0F;
/* 133 */       boolean doney = false;
/*     */       
/* 135 */       while (y < space.getHeight()) {
/* 136 */         this.sx = (int)(space.getX() + x);
/* 137 */         this.sy = (int)(space.getY() + y);
/*     */         
/* 139 */         if (map.blocked(this, this.sx, this.sy)) {
/* 140 */           return false;
/*     */         }
/*     */         
/* 143 */         y += 0.1F;
/* 144 */         if (y > space.getHeight() && !doney) {
/* 145 */           y = space.getHeight();
/* 146 */           doney = true;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 151 */       x += 0.1F;
/* 152 */       if (x > space.getWidth() && !donex) {
/* 153 */         x = space.getWidth();
/* 154 */         donex = true;
/*     */       } 
/*     */     } 
/*     */     
/* 158 */     return true;
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
/*     */   private void subsection(TileBasedMap map, Space space, ArrayList<Space> spaces) {
/* 170 */     if (!clear(map, space)) {
/* 171 */       float width2 = space.getWidth() / 2.0F;
/* 172 */       float height2 = space.getHeight() / 2.0F;
/*     */       
/* 174 */       if (width2 < this.smallestSpace && height2 < this.smallestSpace) {
/*     */         return;
/*     */       }
/*     */       
/* 178 */       subsection(map, new Space(space.getX(), space.getY(), width2, height2), spaces);
/* 179 */       subsection(map, new Space(space.getX(), space.getY() + height2, width2, height2), spaces);
/* 180 */       subsection(map, new Space(space.getX() + width2, space.getY(), width2, height2), spaces);
/* 181 */       subsection(map, new Space(space.getX() + width2, space.getY() + height2, width2, height2), spaces);
/*     */     } else {
/* 183 */       spaces.add(space);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Mover getMover() {
/* 193 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSearchDistance() {
/* 202 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSourceX() {
/* 211 */     return this.sx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSourceY() {
/* 220 */     return this.sy;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slic\\util\pathfinding\navmesh\NavMeshBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */