package org.newdawn.slick.tests.xml;

import java.io.PrintStream;








public class Item
{
  protected String name;
  protected int condition;
  
  public Item() {}
  
  public void dump(String prefix)
  {
    System.out.println(prefix + "Item " + name + "," + condition);
  }
}
