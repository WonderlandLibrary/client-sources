/*   1:    */ package org.newdawn.slick.tests;
/*   2:    */ 
/*   3:    */ import org.newdawn.slick.AppGameContainer;
/*   4:    */ import org.newdawn.slick.BasicGame;
/*   5:    */ import org.newdawn.slick.GameContainer;
/*   6:    */ import org.newdawn.slick.Graphics;
/*   7:    */ import org.newdawn.slick.SlickException;
/*   8:    */ import org.newdawn.slick.tiled.TiledMap;
/*   9:    */ 
/*  10:    */ public class TileMapTest
/*  11:    */   extends BasicGame
/*  12:    */ {
/*  13:    */   private TiledMap map;
/*  14:    */   private String mapName;
/*  15:    */   private String monsterDifficulty;
/*  16:    */   private String nonExistingMapProperty;
/*  17:    */   private String nonExistingLayerProperty;
/*  18: 33 */   private int updateCounter = 0;
/*  19: 36 */   private static int UPDATE_TIME = 1000;
/*  20: 39 */   private int originalTileID = 0;
/*  21:    */   
/*  22:    */   public TileMapTest()
/*  23:    */   {
/*  24: 45 */     super("Tile Map Test");
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void init(GameContainer container)
/*  28:    */     throws SlickException
/*  29:    */   {
/*  30: 52 */     this.map = new TiledMap("testdata/testmap.tmx", "testdata");
/*  31:    */     
/*  32: 54 */     this.mapName = this.map.getMapProperty("name", "Unknown map name");
/*  33: 55 */     this.monsterDifficulty = this.map.getLayerProperty(0, "monsters", "easy peasy");
/*  34: 56 */     this.nonExistingMapProperty = this.map.getMapProperty("zaphod", "Undefined map property");
/*  35: 57 */     this.nonExistingLayerProperty = this.map.getLayerProperty(1, "beeblebrox", "Undefined layer property");
/*  36:    */     
/*  37:    */ 
/*  38: 60 */     this.originalTileID = this.map.getTileId(10, 10, 0);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void render(GameContainer container, Graphics g)
/*  42:    */   {
/*  43: 67 */     this.map.render(10, 10, 4, 4, 15, 15);
/*  44:    */     
/*  45: 69 */     g.scale(0.35F, 0.35F);
/*  46: 70 */     this.map.render(1400, 0);
/*  47: 71 */     g.resetTransform();
/*  48:    */     
/*  49: 73 */     g.drawString("map name: " + this.mapName, 10.0F, 500.0F);
/*  50: 74 */     g.drawString("monster difficulty: " + this.monsterDifficulty, 10.0F, 550.0F);
/*  51:    */     
/*  52: 76 */     g.drawString("non existing map property: " + this.nonExistingMapProperty, 10.0F, 525.0F);
/*  53: 77 */     g.drawString("non existing layer property: " + this.nonExistingLayerProperty, 10.0F, 575.0F);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void update(GameContainer container, int delta)
/*  57:    */   {
/*  58: 84 */     this.updateCounter += delta;
/*  59: 85 */     if (this.updateCounter > UPDATE_TIME)
/*  60:    */     {
/*  61: 87 */       this.updateCounter -= UPDATE_TIME;
/*  62: 88 */       int currentTileID = this.map.getTileId(10, 10, 0);
/*  63: 89 */       if (currentTileID != this.originalTileID) {
/*  64: 90 */         this.map.setTileId(10, 10, 0, this.originalTileID);
/*  65:    */       } else {
/*  66: 92 */         this.map.setTileId(10, 10, 0, 1);
/*  67:    */       }
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void keyPressed(int key, char c)
/*  72:    */   {
/*  73:100 */     if (key == 1) {
/*  74:101 */       System.exit(0);
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   public static void main(String[] argv)
/*  79:    */   {
/*  80:    */     try
/*  81:    */     {
/*  82:112 */       AppGameContainer container = new AppGameContainer(new TileMapTest());
/*  83:113 */       container.setDisplayMode(800, 600, false);
/*  84:114 */       container.start();
/*  85:    */     }
/*  86:    */     catch (SlickException e)
/*  87:    */     {
/*  88:116 */       e.printStackTrace();
/*  89:    */     }
/*  90:    */   }
/*  91:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.tests.TileMapTest
 * JD-Core Version:    0.7.0.1
 */