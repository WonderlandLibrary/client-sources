package org.silvertunnel_ng.netlib.api;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;











































public class NetFactory
  implements NetLayerFactory
{
  private static final Logger LOG = LoggerFactory.getLogger(NetFactory.class);
  

  public static final String NETFACTORY_MAPPING_PROPERTIES = "/org/silvertunnel_ng/netlib/api/netfactory_mapping.properties";
  
  private final Map<NetLayerIDs, NetLayer> netLayerRepository = new HashMap();
  
  private static NetFactory instance = new NetFactory();
  
  public NetFactory() {}
  
  public static NetFactory getInstance() { return instance; }
  







  public final synchronized void registerNetLayer(NetLayerIDs netLayerId, NetLayer netLayer)
  {
    netLayerRepository.put(netLayerId, netLayer);
    LOG.debug("registerNetLayer with netLayerId={}", netLayerId);
  }
  



  public final synchronized void clearRegisteredNetLayers()
  {
    netLayerRepository.clear();
  }
  



  public synchronized NetLayer getNetLayerById(NetLayerIDs netLayerId)
  {
    NetLayer result = (NetLayer)netLayerRepository.get(netLayerId);
    if (result == null)
    {
      try
      {

        NetLayerFactory factory = getNetLayerFactoryByNetLayerID(netLayerId);
        if (factory != null)
        {
          result = factory.getNetLayerById(netLayerId);
          if (result != null)
          {

            registerNetLayer(netLayerId, result);
          }
          
        }
      }
      catch (Exception e)
      {
        LOG.error("could not create NetLayer of {}", netLayerId, e);
      }
    }
    
    return result;
  }
  







  private NetLayerFactory getNetLayerFactoryByNetLayerID(NetLayerIDs netLayerId)
  {
    try
    {
      InputStream in = getClass().getResourceAsStream("/org/silvertunnel_ng/netlib/api/netfactory_mapping.properties");
      Properties mapping = new Properties();
      mapping.load(in);
      
      String netLayerFactoryClassName = mapping.getProperty(netLayerId.getValue());
      

      Class<?> clazz = Class.forName(netLayerFactoryClassName);
      Constructor<?> c = clazz.getConstructor(new Class[0]);
      return (NetLayerFactory)c.newInstance(new Object[0]);


    }
    catch (Exception e)
    {

      LOG.error("could not create NetLayerFactory of {}", netLayerId, e); }
    return null;
  }
}
