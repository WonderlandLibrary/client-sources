package org.newdawn.slick.loading;

import java.util.ArrayList;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.opengl.InternalTextureLoader;
import org.newdawn.slick.util.Log;








public class LoadingList
{
  private static LoadingList single = new LoadingList();
  




  public static LoadingList get()
  {
    return single;
  }
  




  public static void setDeferredLoading(boolean loading)
  {
    single = new LoadingList();
    
    InternalTextureLoader.get().setDeferredLoading(loading);
    SoundStore.get().setDeferredLoading(loading);
  }
  




  public static boolean isDeferredLoading()
  {
    return InternalTextureLoader.get().isDeferredLoading();
  }
  

  private ArrayList deferred = new ArrayList();
  


  private int total;
  



  private LoadingList() {}
  



  public void add(DeferredResource resource)
  {
    total += 1;
    deferred.add(resource);
  }
  





  public void remove(DeferredResource resource)
  {
    Log.info("Early loading of deferred resource due to req: " + resource.getDescription());
    total -= 1;
    deferred.remove(resource);
  }
  




  public int getTotalResources()
  {
    return total;
  }
  




  public int getRemainingResources()
  {
    return deferred.size();
  }
  




  public DeferredResource getNext()
  {
    if (deferred.size() == 0) {
      return null;
    }
    
    return (DeferredResource)deferred.remove(0);
  }
}
