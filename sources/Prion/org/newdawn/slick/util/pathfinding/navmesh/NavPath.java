package org.newdawn.slick.util.pathfinding.navmesh;

import java.util.ArrayList;






public class NavPath
{
  private ArrayList links = new ArrayList();
  




  public NavPath() {}
  




  public void push(Link link)
  {
    links.add(link);
  }
  




  public int length()
  {
    return links.size();
  }
  





  public float getX(int step)
  {
    return ((Link)links.get(step)).getX();
  }
  





  public float getY(int step)
  {
    return ((Link)links.get(step)).getY();
  }
  




  public String toString()
  {
    return "[Path length=" + length() + "]";
  }
  




  public void remove(int i)
  {
    links.remove(i);
  }
}
