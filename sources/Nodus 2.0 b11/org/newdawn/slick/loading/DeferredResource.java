package org.newdawn.slick.loading;

import java.io.IOException;

public abstract interface DeferredResource
{
  public abstract void load()
    throws IOException;
  
  public abstract String getDescription();
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.loading.DeferredResource
 * JD-Core Version:    0.7.0.1
 */