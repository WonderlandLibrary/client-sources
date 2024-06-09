/*    */ package org.newdawn.slick.tests;
/*    */ 
/*    */ import org.newdawn.slick.BasicGame;
/*    */ import org.newdawn.slick.Game;
/*    */ import org.newdawn.slick.GameContainer;
/*    */ import org.newdawn.slick.Graphics;
/*    */ import org.newdawn.slick.SlickException;
/*    */ import org.newdawn.slick.tiled.TiledMap;
/*    */ import org.newdawn.slick.util.Bootstrap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IsoTiledTest
/*    */   extends BasicGame
/*    */ {
/*    */   private TiledMap tilemap;
/*    */   
/*    */   public IsoTiledTest() {
/* 23 */     super("Isometric Tiled Map Test");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void init(GameContainer container) throws SlickException {
/* 31 */     this.tilemap = new TiledMap("testdata/isoexample.tmx", "testdata/");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update(GameContainer container, int delta) throws SlickException {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(GameContainer container, Graphics g) throws SlickException {
/* 48 */     this.tilemap.render(350, 150);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void main(String[] argv) {
/* 57 */     Bootstrap.runAsApplication((Game)new IsoTiledTest(), 800, 600, false);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\tests\IsoTiledTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */