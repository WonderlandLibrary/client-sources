package org.silvertunnel_ng.netlib.layer.tor.hiddenservice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import org.silvertunnel_ng.netlib.layer.tor.common.TorConfig;
import org.silvertunnel_ng.netlib.layer.tor.directory.RendezvousServiceDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;







































public final class HiddenServiceDescriptorCache
{
  private static final Logger LOG = LoggerFactory.getLogger(HiddenServiceDescriptorCache.class);
  


  private static HiddenServiceDescriptorCache instance;
  


  private HiddenServiceDescriptorCache() {}
  


  public static synchronized HiddenServiceDescriptorCache getInstance()
  {
    if (instance == null)
    {
      instance = new HiddenServiceDescriptorCache();
      instance.init();
    }
    
    return instance;
  }
  
  private static Map<String, RendezvousServiceDescriptor> cachedRendezvousServiceDescriptors = new HashMap();
  






  public synchronized void init()
  {
    cachedRendezvousServiceDescriptors.clear();
    if (TorConfig.isCacheHiddenServiceDescriptor())
    {
      try
      {
        FileInputStream fileInputStream = new FileInputStream(TorConfig.getTempDirectory() + File.separator + "st-" + "hidden_service_descriptor.cache");
        


        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        cachedRendezvousServiceDescriptors = (Map)objectInputStream.readObject();
        objectInputStream.close();
      }
      catch (FileNotFoundException exception)
      {
        LOG.info("no cached hiddenservice descriptors found");
      }
      catch (Exception exception)
      {
        LOG.warn("could not load cached hiddenservice descriptors because of exception", exception);
      }
    }
  }
  


  public synchronized void saveCacheToDisk()
  {
    if (!TorConfig.isCacheHiddenServiceDescriptor())
    {
      return;
    }
    LOG.debug("saving {} cached hiddenservice descriptors to disk", Integer.valueOf(cachedRendezvousServiceDescriptors.size()));
    try
    {
      FileOutputStream fileOutputStream = new FileOutputStream(TorConfig.getTempDirectory() + File.separator + "st-" + "hidden_service_descriptor.cache");
      


      ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
      objectOutputStream.writeObject(cachedRendezvousServiceDescriptors);
      objectOutputStream.close();
    }
    catch (Exception exception)
    {
      LOG.warn("cant save hiddenservice descriptor cache due to exception", exception);
    }
  }
  




  public void put(String z, RendezvousServiceDescriptor descriptor)
  {
    LOG.debug("adding {} to cache", z);
    cachedRendezvousServiceDescriptors.put(z, descriptor);
    saveCacheToDisk();
  }
  





  public RendezvousServiceDescriptor get(String z)
  {
    RendezvousServiceDescriptor result = (RendezvousServiceDescriptor)cachedRendezvousServiceDescriptors.get(z);
    if (result == null)
    {
      return null;
    }
    if (result.isPublicationTimeValid())
    {
      LOG.debug("found cached descriptor for {}", z);
      return result;
    }
    
    LOG.debug("removing {} because its too old", z);
    synchronized (cachedRendezvousServiceDescriptors)
    {
      cachedRendezvousServiceDescriptors.remove(z);
    }
    return null;
  }
}
