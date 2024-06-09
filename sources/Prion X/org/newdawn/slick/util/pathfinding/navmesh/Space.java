package org.newdawn.slick.util.pathfinding.navmesh;

import java.util.ArrayList;
import java.util.HashMap;











public class Space
{
  private float x;
  private float y;
  private float width;
  private float height;
  private HashMap links = new HashMap();
  
  private ArrayList linksList = new ArrayList();
  



  private float cost;
  




  public Space(float x, float y, float width, float height)
  {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }
  




  public float getWidth()
  {
    return width;
  }
  




  public float getHeight()
  {
    return height;
  }
  




  public float getX()
  {
    return x;
  }
  




  public float getY()
  {
    return y;
  }
  






  public void link(Space other)
  {
    if ((inTolerance(x, x + width)) || (inTolerance(x + width, x))) {
      float linkx = x;
      if (x + width == x) {
        linkx = x + width;
      }
      
      float top = Math.max(y, y);
      float bottom = Math.min(y + height, y + height);
      float linky = top + (bottom - top) / 2.0F;
      
      Link link = new Link(linkx, linky, other);
      links.put(other, link);
      linksList.add(link);
    }
    
    if ((inTolerance(y, y + height)) || (inTolerance(y + height, y))) {
      float linky = y;
      if (y + height == y) {
        linky = y + height;
      }
      
      float left = Math.max(x, x);
      float right = Math.min(x + width, x + width);
      float linkx = left + (right - left) / 2.0F;
      
      Link link = new Link(linkx, linky, other);
      links.put(other, link);
      linksList.add(link);
    }
  }
  







  private boolean inTolerance(float a, float b)
  {
    return a == b;
  }
  






  public boolean hasJoinedEdge(Space other)
  {
    if ((inTolerance(x, x + width)) || (inTolerance(x + width, x))) {
      if ((y >= y) && (y <= y + height)) {
        return true;
      }
      if ((y + height >= y) && (y + height <= y + height)) {
        return true;
      }
      if ((y >= y) && (y <= y + height)) {
        return true;
      }
      if ((y + height >= y) && (y + height <= y + height)) {
        return true;
      }
    }
    
    if ((inTolerance(y, y + height)) || (inTolerance(y + height, y))) {
      if ((x >= x) && (x <= x + width)) {
        return true;
      }
      if ((x + width >= x) && (x + width <= x + width)) {
        return true;
      }
      if ((x >= x) && (x <= x + width)) {
        return true;
      }
      if ((x + width >= x) && (x + width <= x + width)) {
        return true;
      }
    }
    
    return false;
  }
  





  public Space merge(Space other)
  {
    float minx = Math.min(x, x);
    float miny = Math.min(y, y);
    
    float newwidth = width + width;
    float newheight = height + height;
    if (x == x) {
      newwidth = width;
    } else {
      newheight = height;
    }
    return new Space(minx, miny, newwidth, newheight);
  }
  






  public boolean canMerge(Space other)
  {
    if (!hasJoinedEdge(other)) {
      return false;
    }
    
    if ((x == x) && (width == width)) {
      return true;
    }
    if ((y == y) && (height == height)) {
      return true;
    }
    
    return false;
  }
  




  public int getLinkCount()
  {
    return linksList.size();
  }
  





  public Link getLink(int index)
  {
    return (Link)linksList.get(index);
  }
  






  public boolean contains(float xp, float yp)
  {
    return (xp >= x) && (xp < x + width) && (yp >= y) && (yp < y + height);
  }
  







  public void fill(Space target, float sx, float sy, float cost)
  {
    if (cost >= this.cost) {
      return;
    }
    this.cost = cost;
    if (target == this) {
      return;
    }
    
    for (int i = 0; i < getLinkCount(); i++) {
      Link link = getLink(i);
      float extraCost = link.distance2(sx, sy);
      float nextCost = cost + extraCost;
      link.getTarget().fill(target, link.getX(), link.getY(), nextCost);
    }
  }
  


  public void clearCost()
  {
    cost = Float.MAX_VALUE;
  }
  




  public float getCost()
  {
    return cost;
  }
  






  public boolean pickLowestCost(Space target, NavPath path)
  {
    if (target == this) {
      return true;
    }
    if (links.size() == 0) {
      return false;
    }
    
    Link bestLink = null;
    for (int i = 0; i < getLinkCount(); i++) {
      Link link = getLink(i);
      if ((bestLink == null) || (link.getTarget().getCost() < bestLink.getTarget().getCost())) {
        bestLink = link;
      }
    }
    
    path.push(bestLink);
    return bestLink.getTarget().pickLowestCost(target, path);
  }
  




  public String toString()
  {
    return "[Space " + x + "," + y + " " + width + "," + height + "]";
  }
}
