package org.newdawn.slick.util.pathfinding.navmesh;

import java.util.ArrayList;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;









public class NavMeshBuilder
  implements PathFindingContext
{
  private int sx;
  private int sy;
  private float smallestSpace = 0.2F;
  

  private boolean tileBased;
  


  public NavMeshBuilder() {}
  

  public NavMesh build(TileBasedMap map)
  {
    return build(map, true);
  }
  







  public NavMesh build(TileBasedMap map, boolean tileBased)
  {
    this.tileBased = tileBased;
    
    ArrayList spaces = new ArrayList();
    Space space;
    if (tileBased) {
      for (int x = 0; x < map.getWidthInTiles(); x++) {
        for (int y = 0; y < map.getHeightInTiles(); y++) {
          if (!map.blocked(this, x, y)) {
            spaces.add(new Space(x, y, 1.0F, 1.0F));
          }
        }
      }
    } else {
      space = new Space(0.0F, 0.0F, map.getWidthInTiles(), map.getHeightInTiles());
    }
    


    while (mergeSpaces(spaces)) {}
    linkSpaces(spaces);
    
    return new NavMesh(spaces);
  }
  







  private boolean mergeSpaces(ArrayList spaces)
  {
    for (int source = 0; source < spaces.size(); source++) {
      Space a = (Space)spaces.get(source);
      
      for (int target = source + 1; target < spaces.size(); target++) {
        Space b = (Space)spaces.get(target);
        
        if (a.canMerge(b)) {
          spaces.remove(a);
          spaces.remove(b);
          spaces.add(a.merge(b));
          return true;
        }
      }
    }
    
    return false;
  }
  




  private void linkSpaces(ArrayList spaces)
  {
    for (int source = 0; source < spaces.size(); source++) {
      Space a = (Space)spaces.get(source);
      
      for (int target = source + 1; target < spaces.size(); target++) {
        Space b = (Space)spaces.get(target);
        
        if (a.hasJoinedEdge(b)) {
          a.link(b);
          b.link(a);
        }
      }
    }
  }
  






  public boolean clear(TileBasedMap map, Space space)
  {
    if (tileBased) {
      return true;
    }
    
    float x = 0.0F;
    boolean donex = false;
    
    while (x < space.getWidth()) {
      float y = 0.0F;
      boolean doney = false;
      
      while (y < space.getHeight()) {
        sx = ((int)(space.getX() + x));
        sy = ((int)(space.getY() + y));
        
        if (map.blocked(this, sx, sy)) {
          return false;
        }
        
        y += 0.1F;
        if ((y > space.getHeight()) && (!doney)) {
          y = space.getHeight();
          doney = true;
        }
      }
      

      x += 0.1F;
      if ((x > space.getWidth()) && (!donex)) {
        x = space.getWidth();
        donex = true;
      }
    }
    
    return true;
  }
  







  private void subsection(TileBasedMap map, Space space, ArrayList spaces)
  {
    if (!clear(map, space)) {
      float width2 = space.getWidth() / 2.0F;
      float height2 = space.getHeight() / 2.0F;
      
      if ((width2 < smallestSpace) && (height2 < smallestSpace)) {
        return;
      }
      
      subsection(map, new Space(space.getX(), space.getY(), width2, height2), spaces);
      subsection(map, new Space(space.getX(), space.getY() + height2, width2, height2), spaces);
      subsection(map, new Space(space.getX() + width2, space.getY(), width2, height2), spaces);
      subsection(map, new Space(space.getX() + width2, space.getY() + height2, width2, height2), spaces);
    } else {
      spaces.add(space);
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
    return sx;
  }
  




  public int getSourceY()
  {
    return sy;
  }
}
