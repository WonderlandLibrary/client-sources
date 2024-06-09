package org.newdawn.slick.tests.xml;

import java.io.PrintStream;









public class Entity
{
  private float x;
  private float y;
  private Inventory invent;
  private Stats stats;
  
  public Entity() {}
  
  private void add(Inventory inventory)
  {
    invent = inventory;
  }
  




  private void add(Stats stats)
  {
    this.stats = stats;
  }
  




  public void dump(String prefix)
  {
    System.out.println(prefix + "Entity " + x + "," + y);
    invent.dump(prefix + "\t");
    stats.dump(prefix + "\t");
  }
}
