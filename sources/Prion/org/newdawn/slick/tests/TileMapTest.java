package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
















public class TileMapTest
  extends BasicGame
{
  private TiledMap map;
  private String mapName;
  private String monsterDifficulty;
  private String nonExistingMapProperty;
  private String nonExistingLayerProperty;
  private int updateCounter = 0;
  

  private static int UPDATE_TIME = 1000;
  

  private int originalTileID = 0;
  


  public TileMapTest()
  {
    super("Tile Map Test");
  }
  

  public void init(GameContainer container)
    throws SlickException
  {
    map = new TiledMap("testdata/testmap.tmx", "testdata");
    
    mapName = map.getMapProperty("name", "Unknown map name");
    monsterDifficulty = map.getLayerProperty(0, "monsters", "easy peasy");
    nonExistingMapProperty = map.getMapProperty("zaphod", "Undefined map property");
    nonExistingLayerProperty = map.getLayerProperty(1, "beeblebrox", "Undefined layer property");
    

    originalTileID = map.getTileId(10, 10, 0);
  }
  


  public void render(GameContainer container, Graphics g)
  {
    map.render(10, 10, 4, 4, 15, 15);
    
    g.scale(0.35F, 0.35F);
    map.render(1400, 0);
    g.resetTransform();
    
    g.drawString("map name: " + mapName, 10.0F, 500.0F);
    g.drawString("monster difficulty: " + monsterDifficulty, 10.0F, 550.0F);
    
    g.drawString("non existing map property: " + nonExistingMapProperty, 10.0F, 525.0F);
    g.drawString("non existing layer property: " + nonExistingLayerProperty, 10.0F, 575.0F);
  }
  


  public void update(GameContainer container, int delta)
  {
    updateCounter += delta;
    if (updateCounter > UPDATE_TIME)
    {
      updateCounter -= UPDATE_TIME;
      int currentTileID = map.getTileId(10, 10, 0);
      if (currentTileID != originalTileID) {
        map.setTileId(10, 10, 0, originalTileID);
      } else {
        map.setTileId(10, 10, 0, 1);
      }
    }
  }
  

  public void keyPressed(int key, char c)
  {
    if (key == 1) {
      System.exit(0);
    }
  }
  



  public static void main(String[] argv)
  {
    try
    {
      AppGameContainer container = new AppGameContainer(new TileMapTest());
      container.setDisplayMode(800, 600, false);
      container.start();
    } catch (SlickException e) {
      e.printStackTrace();
    }
  }
}
