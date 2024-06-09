package org.newdawn.slick.loading;

import java.io.IOException;

public abstract interface DeferredResource
{
  public abstract void load()
    throws IOException;
  
  public abstract String getDescription();
}
