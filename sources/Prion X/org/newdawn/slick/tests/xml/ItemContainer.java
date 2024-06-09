package org.newdawn.slick.tests.xml;

import java.io.PrintStream;
import java.util.ArrayList;




public class ItemContainer
  extends Item
{
  private ArrayList items = new ArrayList();
  

  public ItemContainer() {}
  

  private void add(Item item)
  {
    items.add(item);
  }
  






  private void setName(String name)
  {
    this.name = name;
  }
  






  private void setCondition(int condition)
  {
    this.condition = condition;
  }
  




  public void dump(String prefix)
  {
    System.out.println(prefix + "Item Container " + name + "," + condition);
    for (int i = 0; i < items.size(); i++) {
      ((Item)items.get(i)).dump(prefix + "\t");
    }
  }
}
