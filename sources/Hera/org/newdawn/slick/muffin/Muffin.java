package org.newdawn.slick.muffin;

import java.io.IOException;
import java.util.HashMap;

public interface Muffin {
  void saveFile(HashMap paramHashMap, String paramString) throws IOException;
  
  HashMap loadFile(String paramString) throws IOException;
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\muffin\Muffin.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */