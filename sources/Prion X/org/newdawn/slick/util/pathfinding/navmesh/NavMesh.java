package org.newdawn.slick.util.pathfinding.navmesh;

import java.util.ArrayList;










public class NavMesh
{
  private ArrayList spaces = new ArrayList();
  





  public NavMesh() {}
  




  public NavMesh(ArrayList spaces)
  {
    this.spaces.addAll(spaces);
  }
  




  public int getSpaceCount()
  {
    return spaces.size();
  }
  





  public Space getSpace(int index)
  {
    return (Space)spaces.get(index);
  }
  




  public void addSpace(Space space)
  {
    spaces.add(space);
  }
  






  public Space findSpace(float x, float y)
  {
    for (int i = 0; i < spaces.size(); i++) {
      Space space = getSpace(i);
      if (space.contains(x, y)) {
        return space;
      }
    }
    
    return null;
  }
  









  public NavPath findPath(float sx, float sy, float tx, float ty, boolean optimize)
  {
    Space source = findSpace(sx, sy);
    Space target = findSpace(tx, ty);
    
    if ((source == null) || (target == null)) {
      return null;
    }
    
    for (int i = 0; i < spaces.size(); i++) {
      ((Space)spaces.get(i)).clearCost();
    }
    target.fill(source, tx, ty, 0.0F);
    if (target.getCost() == Float.MAX_VALUE) {
      return null;
    }
    if (source.getCost() == Float.MAX_VALUE) {
      return null;
    }
    
    NavPath path = new NavPath();
    path.push(new Link(sx, sy, null));
    if (source.pickLowestCost(target, path)) {
      path.push(new Link(tx, ty, null));
      if (optimize) {
        optimize(path);
      }
      return path;
    }
    
    return null;
  }
  









  private boolean isClear(float x1, float y1, float x2, float y2, float step)
  {
    float dx = x2 - x1;
    float dy = y2 - y1;
    float len = (float)Math.sqrt(dx * dx + dy * dy);
    dx *= step;
    dx /= len;
    dy *= step;
    dy /= len;
    int steps = (int)(len / step);
    
    for (int i = 0; i < steps; i++) {
      float x = x1 + dx * i;
      float y = y1 + dy * i;
      
      if (findSpace(x, y) == null) {
        return false;
      }
    }
    
    return true;
  }
  





  private void optimize(NavPath path)
  {
    int pt = 0;
    
    while (pt < path.length() - 2) {
      float sx = path.getX(pt);
      float sy = path.getY(pt);
      float nx = path.getX(pt + 2);
      float ny = path.getY(pt + 2);
      
      if (isClear(sx, sy, nx, ny, 0.1F)) {
        path.remove(pt + 1);
      } else {
        pt++;
      }
    }
  }
}
