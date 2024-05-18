package org.newdawn.slick.tests.xml;

import java.io.PrintStream;
import java.util.ArrayList;





public class Inventory
{
  private ArrayList items = new ArrayList();
  

  public Inventory() {}
  

  private void add(Item item)
  {
    items.add(item);
  }
  




  public void dump(String prefix)
  {
    System.out.println(prefix + "Inventory");
    for (int i = 0; i < items.size(); i++) {
      ((Item)items.get(i)).dump(prefix + "\t");
    }
  }
}
