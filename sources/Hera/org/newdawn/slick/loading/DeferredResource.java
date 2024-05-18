package org.newdawn.slick.loading;

import java.io.IOException;

public interface DeferredResource {
  void load() throws IOException;
  
  String getDescription();
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slick\loading\DeferredResource.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */