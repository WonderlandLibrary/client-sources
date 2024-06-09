/*     */ package org.newdawn.slick.tests;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.newdawn.slick.BasicGame;
/*     */ import org.newdawn.slick.Color;
/*     */ import org.newdawn.slick.Game;
/*     */ import org.newdawn.slick.GameContainer;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.SlickException;
/*     */ import org.newdawn.slick.util.Bootstrap;
/*     */ import org.newdawn.slick.util.ResourceLoader;
/*     */ import org.newdawn.slick.util.pathfinding.Mover;
/*     */ import org.newdawn.slick.util.pathfinding.PathFindingContext;
/*     */ import org.newdawn.slick.util.pathfinding.TileBasedMap;
/*     */ import org.newdawn.slick.util.pathfinding.navmesh.Link;
/*     */ import org.newdawn.slick.util.pathfinding.navmesh.NavMesh;
/*     */ import org.newdawn.slick.util.pathfinding.navmesh.NavMeshBuilder;
/*     */ import org.newdawn.slick.util.pathfinding.navmesh.NavPath;
/*     */ import org.newdawn.slick.util.pathfinding.navmesh.Space;
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
/*     */ public class NavMeshTest
/*     */   extends BasicGame
/*     */   implements PathFindingContext
/*     */ {
/*     */   private NavMesh navMesh;
/*     */   private NavMeshBuilder builder;
/*     */   private boolean showSpaces = true;
/*     */   private boolean showLinks = true;
/*     */   private NavPath path;
/*     */   private float sx;
/*     */   private float sy;
/*     */   private float ex;
/*     */   private float ey;
/*     */   private DataMap dataMap;
/*     */   
/*     */   public NavMeshTest() {
/*  54 */     super("Nav-mesh Test");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GameContainer container) throws SlickException {
/*  63 */     container.setShowFPS(false);
/*     */     
/*     */     try {
/*  66 */       this.dataMap = new DataMap("testdata/map.dat");
/*  67 */     } catch (IOException e) {
/*  68 */       throw new SlickException("Failed to load map data", e);
/*     */     } 
/*  70 */     this.builder = new NavMeshBuilder();
/*  71 */     this.navMesh = this.builder.build(this.dataMap);
/*     */     
/*  73 */     System.out.println("Navmesh shapes: " + this.navMesh.getSpaceCount());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(GameContainer container, int delta) throws SlickException {
/*  81 */     if (container.getInput().isKeyPressed(2)) {
/*  82 */       this.showLinks = !this.showLinks;
/*     */     }
/*  84 */     if (container.getInput().isKeyPressed(3)) {
/*  85 */       this.showSpaces = !this.showSpaces;
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
/*     */   public void render(GameContainer container, Graphics g) throws SlickException {
/*  97 */     g.translate(50.0F, 50.0F);
/*  98 */     for (int x = 0; x < 50; x++) {
/*  99 */       for (int y = 0; y < 50; y++) {
/* 100 */         if (this.dataMap.blocked(this, x, y)) {
/* 101 */           g.setColor(Color.gray);
/* 102 */           g.fillRect((x * 10 + 1), (y * 10 + 1), 8.0F, 8.0F);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 107 */     if (this.showSpaces) {
/* 108 */       for (int i = 0; i < this.navMesh.getSpaceCount(); i++) {
/* 109 */         Space space = this.navMesh.getSpace(i);
/* 110 */         if (this.builder.clear(this.dataMap, space)) {
/* 111 */           g.setColor(new Color(1.0F, 1.0F, 0.0F, 0.5F));
/* 112 */           g.fillRect(space.getX() * 10.0F, space.getY() * 10.0F, space.getWidth() * 10.0F, space.getHeight() * 10.0F);
/*     */         } 
/* 114 */         g.setColor(Color.yellow);
/* 115 */         g.drawRect(space.getX() * 10.0F, space.getY() * 10.0F, space.getWidth() * 10.0F, space.getHeight() * 10.0F);
/*     */         
/* 117 */         if (this.showLinks) {
/* 118 */           int links = space.getLinkCount();
/* 119 */           for (int j = 0; j < links; j++) {
/* 120 */             Link link = space.getLink(j);
/* 121 */             g.setColor(Color.red);
/* 122 */             g.fillRect(link.getX() * 10.0F - 2.0F, link.getY() * 10.0F - 2.0F, 5.0F, 5.0F);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 128 */     if (this.path != null) {
/* 129 */       g.setColor(Color.white);
/* 130 */       for (int i = 0; i < this.path.length() - 1; i++) {
/* 131 */         g.drawLine(this.path.getX(i) * 10.0F, this.path.getY(i) * 10.0F, this.path.getX(i + 1) * 10.0F, this.path.getY(i + 1) * 10.0F);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Mover getMover() {
/* 141 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSearchDistance() {
/* 149 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSourceX() {
/* 157 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSourceY() {
/* 165 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mousePressed(int button, int x, int y) {
/* 173 */     float mx = (x - 50) / 10.0F;
/* 174 */     float my = (y - 50) / 10.0F;
/*     */     
/* 176 */     if (button == 0) {
/* 177 */       this.sx = mx;
/* 178 */       this.sy = my;
/*     */     } else {
/* 180 */       this.ex = mx;
/* 181 */       this.ey = my;
/*     */     } 
/*     */     
/* 184 */     this.path = this.navMesh.findPath(this.sx, this.sy, this.ex, this.ey, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class DataMap
/*     */     implements TileBasedMap
/*     */   {
/* 194 */     private byte[] map = new byte[2500];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DataMap(String ref) throws IOException {
/* 203 */       ResourceLoader.getResourceAsStream(ref).read(this.map);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean blocked(PathFindingContext context, int tx, int ty) {
/* 211 */       if (tx < 0 || ty < 0 || tx >= 50 || ty >= 50) {
/* 212 */         return false;
/*     */       }
/*     */       
/* 215 */       return (this.map[tx + ty * 50] != 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public float getCost(PathFindingContext context, int tx, int ty) {
/* 223 */       return 1.0F;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getHeightInTiles() {
/* 231 */       return 50;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getWidthInTiles() {
/* 239 */       return 50;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void pathFinderVisited(int x, int y) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] argv) {
/* 256 */     Bootstrap.runAsApplication((Game)new NavMeshTest(), 600, 600, false);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\NavMeshTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */