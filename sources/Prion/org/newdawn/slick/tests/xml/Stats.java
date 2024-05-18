package org.newdawn.slick.tests.xml;

import java.io.PrintStream;









public class Stats
{
  private int hp;
  private int mp;
  private float age;
  private int exp;
  
  public Stats() {}
  
  public void dump(String prefix)
  {
    System.out.println(prefix + "Stats " + hp + "," + mp + "," + age + "," + exp);
  }
}
