package org.newdawn.slick.util.pathfinding.navmesh;





public class Link
{
  private float px;
  


  private float py;
  


  private Space target;
  



  public Link(float px, float py, Space target)
  {
    this.px = px;
    this.py = py;
    this.target = target;
  }
  






  public float distance2(float tx, float ty)
  {
    float dx = tx - px;
    float dy = ty - py;
    
    return dx * dx + dy * dy;
  }
  




  public float getX()
  {
    return px;
  }
  




  public float getY()
  {
    return py;
  }
  




  public Space getTarget()
  {
    return target;
  }
}
