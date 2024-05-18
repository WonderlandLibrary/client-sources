/*     */ package org.newdawn.slick.tests;
/*     */ 
/*     */ import org.newdawn.slick.AppGameContainer;
/*     */ import org.newdawn.slick.BasicGame;
/*     */ import org.newdawn.slick.Game;
/*     */ import org.newdawn.slick.GameContainer;
/*     */ import org.newdawn.slick.Graphics;
/*     */ import org.newdawn.slick.SlickException;
/*     */ import org.newdawn.slick.tiled.TiledMap;
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
/*     */ public class TileMapTest
/*     */   extends BasicGame
/*     */ {
/*     */   private TiledMap map;
/*     */   private String mapName;
/*     */   private String monsterDifficulty;
/*     */   private String nonExistingMapProperty;
/*     */   private String nonExistingLayerProperty;
/*  33 */   private int updateCounter = 0;
/*     */ 
/*     */   
/*  36 */   private static int UPDATE_TIME = 1000;
/*     */ 
/*     */   
/*  39 */   private int originalTileID = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileMapTest() {
/*  45 */     super("Tile Map Test");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GameContainer container) throws SlickException {
/*  52 */     this.map = new TiledMap("testdata/testmap.tmx", "testdata");
/*     */     
/*  54 */     this.mapName = this.map.getMapProperty("name", "Unknown map name");
/*  55 */     this.monsterDifficulty = this.map.getLayerProperty(0, "monsters", "easy peasy");
/*  56 */     this.nonExistingMapProperty = this.map.getMapProperty("zaphod", "Undefined map property");
/*  57 */     this.nonExistingLayerProperty = this.map.getLayerProperty(1, "beeblebrox", "Undefined layer property");
/*     */ 
/*     */     
/*  60 */     this.originalTileID = this.map.getTileId(10, 10, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(GameContainer container, Graphics g) {
/*  67 */     this.map.render(10, 10, 4, 4, 15, 15);
/*     */     
/*  69 */     g.scale(0.35F, 0.35F);
/*  70 */     this.map.render(1400, 0);
/*  71 */     g.resetTransform();
/*     */     
/*  73 */     g.drawString("map name: " + this.mapName, 10.0F, 500.0F);
/*  74 */     g.drawString("monster difficulty: " + this.monsterDifficulty, 10.0F, 550.0F);
/*     */     
/*  76 */     g.drawString("non existing map property: " + this.nonExistingMapProperty, 10.0F, 525.0F);
/*  77 */     g.drawString("non existing layer property: " + this.nonExistingLayerProperty, 10.0F, 575.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(GameContainer container, int delta) {
/*  84 */     this.updateCounter += delta;
/*  85 */     if (this.updateCounter > UPDATE_TIME) {
/*     */       
/*  87 */       this.updateCounter -= UPDATE_TIME;
/*  88 */       int currentTileID = this.map.getTileId(10, 10, 0);
/*  89 */       if (currentTileID != this.originalTileID) {
/*  90 */         this.map.setTileId(10, 10, 0, this.originalTileID);
/*     */       } else {
/*  92 */         this.map.setTileId(10, 10, 0, 1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyPressed(int key, char c) {
/* 100 */     if (key == 1) {
/* 101 */       System.exit(0);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] argv) {
/*     */     try {
/* 112 */       AppGameContainer container = new AppGameContainer((Game)new TileMapTest());
/* 113 */       container.setDisplayMode(800, 600, false);
/* 114 */       container.start();
/* 115 */     } catch (SlickException e) {
/* 116 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\TileMapTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */