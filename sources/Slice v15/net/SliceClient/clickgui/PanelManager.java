package net.SliceClient.clickgui;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

public class PanelManager
{
  public PanelManager() {}
  
  public void savePanels()
  {
    if (net.SliceClient.Slice.gui == null) {
      return;
    }
    try {
      File f = new File(net.SliceClient.Slice.directory, "panel.c");
      if (f.exists()) {
        f.delete();
      }
      f.createNewFile();
      PrintWriter output = new PrintWriter(new java.io.FileWriter(f, true));
      for (Panel panel : ClickGui.getPanels()) {
        output.println(String.valueOf(panel.getTitle()) + ":" + panel.getX() + "-" + panel.getY() + "-" + panel.isOpen());
      }
      output.close();
    }
    catch (Exception localException) {}
  }
  
  public void setPanels() {
    try {
      int n;
      int n2;
      for (java.util.Iterator localIterator = ClickGui.getPanels().iterator(); localIterator.hasNext(); 
          


          n2 < n)
      {
        Panel m = (Panel)localIterator.next();
        String[] arrstring = readPanels();
        n = arrstring.length;
        n2 = 0;
        continue;
        String bind = arrstring[n2];
        String[] splitted = bind.split(":");
        if (m.getTitle().equalsIgnoreCase(splitted[0])) {
          String[] bound = splitted[1].split("-");
          m.setX(Integer.valueOf(bound[0]).intValue());
          m.setY(Integer.valueOf(bound[1]).intValue());
          m.setOpen(Boolean.valueOf(bound[2]).booleanValue());
        }
        n2++;
      }
    }
    catch (Exception localException) {}
  }
  

  public String[] readPanels()
  {
    try
    {
      File f = new File(net.SliceClient.Slice.directory, "panel.txt");
      if (!f.exists()) {
        f.createNewFile();
      }
      java.io.FileReader fileReader = new java.io.FileReader(f);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      ArrayList<String> lines = new ArrayList();
      String line = null;
      while ((line = bufferedReader.readLine()) != null) {
        lines.add(line);
      }
      bufferedReader.close();
      return (String[])lines.toArray(new String[lines.size()]);
    }
    catch (Exception f) {}
    return tmp102_99;
  }
}
