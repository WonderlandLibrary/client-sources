package org.lwjgl.util;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.opengl.DisplayMode;




















































public final class Display
{
  private static final boolean DEBUG = false;
  
  public Display() {}
  
  public static DisplayMode[] getAvailableDisplayModes(int minWidth, int minHeight, int maxWidth, int maxHeight, int minBPP, int maxBPP, int minFreq, int maxFreq)
    throws LWJGLException
  {
    DisplayMode[] modes = org.lwjgl.opengl.Display.getAvailableDisplayModes();
    
    if (LWJGLUtil.DEBUG) {
      System.out.println("Available screen modes:");
      for (DisplayMode mode : modes) {
        System.out.println(mode);
      }
    }
    
    ArrayList<DisplayMode> matches = new ArrayList(modes.length);
    
    for (int i = 0; i < modes.length; i++) {
      assert (modes[i] != null) : ("" + i + " " + modes.length);
      if ((minWidth == -1) || (modes[i].getWidth() >= minWidth))
      {
        if ((maxWidth == -1) || (modes[i].getWidth() <= maxWidth))
        {
          if ((minHeight == -1) || (modes[i].getHeight() >= minHeight))
          {
            if ((maxHeight == -1) || (modes[i].getHeight() <= maxHeight))
            {
              if ((minBPP == -1) || (modes[i].getBitsPerPixel() >= minBPP))
              {
                if ((maxBPP == -1) || (modes[i].getBitsPerPixel() <= maxBPP))
                {


                  if (modes[i].getFrequency() != 0) {
                    if ((minFreq == -1) || (modes[i].getFrequency() >= minFreq))
                    {
                      if ((maxFreq != -1) && (modes[i].getFrequency() > maxFreq)) {}
                    }
                  } else
                    matches.add(modes[i]); } } } } }
      }
    }
    DisplayMode[] ret = new DisplayMode[matches.size()];
    matches.toArray(ret);
    if (LWJGLUtil.DEBUG) {}
    





    return ret;
  }
  































































































  public static DisplayMode setDisplayMode(DisplayMode[] dm, String[] param)
    throws Exception
  {
    Arrays.sort(dm, new Comparator()
    {
      final Display.1FieldAccessor[] accessors;
      
      public int compare(DisplayMode dm1, DisplayMode dm2)
      {
        for (Display.1FieldAccessor accessor : accessors) {
          int f1 = accessor.getInt(dm1);
          int f2 = accessor.getInt(dm2);
          
          if ((usePreferred) && (f1 != f2)) {
            if (f1 == preferred)
              return -1;
            if (f2 == preferred) {
              return 1;
            }
            
            int absf1 = Math.abs(f1 - preferred);
            int absf2 = Math.abs(f2 - preferred);
            if (absf1 < absf2)
              return -1;
            if (absf1 > absf2) {
              return 1;
            }
          }
          else {
            if (f1 < f2)
              return order;
            if (f1 != f2)
            {

              return -order; }
          }
        }
        return 0;
      }
    });
    




    if (LWJGLUtil.DEBUG) {
      System.out.println("Sorted display modes:");
      for (DisplayMode aDm : dm) {
        System.out.println(aDm);
      }
    }
    for (DisplayMode aDm : dm) {
      try {
        if (LWJGLUtil.DEBUG)
          System.out.println("Attempting to set displaymode: " + aDm);
        org.lwjgl.opengl.Display.setDisplayMode(aDm);
        return aDm;
      } catch (Exception e) {
        if (LWJGLUtil.DEBUG) {
          System.out.println("Failed to set display mode to " + aDm);
          e.printStackTrace();
        }
      }
    }
    
    throw new Exception("Failed to set display mode.");
  }
}
