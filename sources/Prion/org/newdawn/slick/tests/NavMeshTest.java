package org.newdawn.slick.tests;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Bootstrap;
import org.newdawn.slick.util.ResourceLoader;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;
import org.newdawn.slick.util.pathfinding.navmesh.Link;
import org.newdawn.slick.util.pathfinding.navmesh.NavMesh;
import org.newdawn.slick.util.pathfinding.navmesh.NavMeshBuilder;
import org.newdawn.slick.util.pathfinding.navmesh.NavPath;
import org.newdawn.slick.util.pathfinding.navmesh.Space;





public class NavMeshTest
  extends BasicGame
  implements PathFindingContext
{
  private NavMesh navMesh;
  private NavMeshBuilder builder;
  private boolean showSpaces = true;
  
  private boolean showLinks = true;
  

  private NavPath path;
  

  private float sx;
  
  private float sy;
  
  private float ex;
  
  private float ey;
  
  private DataMap dataMap;
  

  public NavMeshTest()
  {
    super("Nav-mesh Test");
  }
  



  public void init(GameContainer container)
    throws SlickException
  {
    container.setShowFPS(false);
    try
    {
      dataMap = new DataMap("testdata/map.dat");
    } catch (IOException e) {
      throw new SlickException("Failed to load map data", e);
    }
    builder = new NavMeshBuilder();
    navMesh = builder.build(dataMap);
    
    System.out.println("Navmesh shapes: " + navMesh.getSpaceCount());
  }
  


  public void update(GameContainer container, int delta)
    throws SlickException
  {
    if (container.getInput().isKeyPressed(2)) {
      showLinks = (!showLinks);
    }
    if (container.getInput().isKeyPressed(3)) {
      showSpaces = (!showSpaces);
    }
  }
  





  public void render(GameContainer container, Graphics g)
    throws SlickException
  {
    g.translate(50.0F, 50.0F);
    for (int x = 0; x < 50; x++) {
      for (int y = 0; y < 50; y++) {
        if (dataMap.blocked(this, x, y)) {
          g.setColor(Color.gray);
          g.fillRect(x * 10 + 1, y * 10 + 1, 8.0F, 8.0F);
        }
      }
    }
    
    if (showSpaces) {
      for (int i = 0; i < navMesh.getSpaceCount(); i++) {
        Space space = navMesh.getSpace(i);
        if (builder.clear(dataMap, space)) {
          g.setColor(new Color(1.0F, 1.0F, 0.0F, 0.5F));
          g.fillRect(space.getX() * 10.0F, space.getY() * 10.0F, space.getWidth() * 10.0F, space.getHeight() * 10.0F);
        }
        g.setColor(Color.yellow);
        g.drawRect(space.getX() * 10.0F, space.getY() * 10.0F, space.getWidth() * 10.0F, space.getHeight() * 10.0F);
        
        if (showLinks) {
          int links = space.getLinkCount();
          for (int j = 0; j < links; j++) {
            Link link = space.getLink(j);
            g.setColor(Color.red);
            g.fillRect(link.getX() * 10.0F - 2.0F, link.getY() * 10.0F - 2.0F, 5.0F, 5.0F);
          }
        }
      }
    }
    
    if (path != null) {
      g.setColor(Color.white);
      for (int i = 0; i < path.length() - 1; i++) {
        g.drawLine(path.getX(i) * 10.0F, path.getY(i) * 10.0F, path.getX(i + 1) * 10.0F, path.getY(i + 1) * 10.0F);
      }
    }
  }
  



  public Mover getMover()
  {
    return null;
  }
  



  public int getSearchDistance()
  {
    return 0;
  }
  



  public int getSourceX()
  {
    return 0;
  }
  



  public int getSourceY()
  {
    return 0;
  }
  



  public void mousePressed(int button, int x, int y)
  {
    float mx = (x - 50) / 10.0F;
    float my = (y - 50) / 10.0F;
    
    if (button == 0) {
      sx = mx;
      sy = my;
    } else {
      ex = mx;
      ey = my;
    }
    
    path = navMesh.findPath(sx, sy, ex, ey, true);
  }
  




  private class DataMap
    implements TileBasedMap
  {
    private byte[] map = new byte['ৄ'];
    




    public DataMap(String ref)
      throws IOException
    {
      ResourceLoader.getResourceAsStream(ref).read(map);
    }
    



    public boolean blocked(PathFindingContext context, int tx, int ty)
    {
      if ((tx < 0) || (ty < 0) || (tx >= 50) || (ty >= 50)) {
        return false;
      }
      
      return map[(tx + ty * 50)] != 0;
    }
    



    public float getCost(PathFindingContext context, int tx, int ty)
    {
      return 1.0F;
    }
    



    public int getHeightInTiles()
    {
      return 50;
    }
    



    public int getWidthInTiles()
    {
      return 50;
    }
    





    public void pathFinderVisited(int x, int y) {}
  }
  




  public static void main(String[] argv)
  {
    Bootstrap.runAsApplication(new NavMeshTest(), 600, 600, false);
  }
}
