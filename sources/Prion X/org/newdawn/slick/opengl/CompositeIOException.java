package org.newdawn.slick.opengl;

import java.io.IOException;
import java.util.ArrayList;





public class CompositeIOException
  extends IOException
{
  private ArrayList exceptions = new ArrayList();
  





  public CompositeIOException() {}
  




  public void addException(Exception e)
  {
    exceptions.add(e);
  }
  


  public String getMessage()
  {
    String msg = "Composite Exception: \n";
    for (int i = 0; i < exceptions.size(); i++) {
      msg = msg + "\t" + ((IOException)exceptions.get(i)).getMessage() + "\n";
    }
    
    return msg;
  }
}
