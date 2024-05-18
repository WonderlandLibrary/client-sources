package org.newdawn.slick.tests.xml;

import java.io.PrintStream;
import java.util.ArrayList;





public class GameData
{
  private ArrayList entities = new ArrayList();
  

  public GameData() {}
  

  private void add(Entity entity)
  {
    entities.add(entity);
  }
  




  public void dump(String prefix)
  {
    System.out.println(prefix + "GameData");
    for (int i = 0; i < entities.size(); i++) {
      ((Entity)entities.get(i)).dump(prefix + "\t");
    }
  }
}
