package org.silvertunnel_ng.netlib.layer.tor.circuit;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.silvertunnel_ng.netlib.layer.tor.common.TCPStreamProperties;

























public final class CircuitHistory
{
  private int countInternal = 0;
  
  private int countExternal = 0;
  
  private final Map<Long, Integer> mapCountInternal = new HashMap();
  
  private final Map<Long, Integer> mapCountExternal = new HashMap();
  
  private static final long MAX_TIMEFRAME_IN_MINUTES = 10L;
  
  private final Map<Integer, Integer> mapHistoricPorts = new HashMap();
  
  private final Map<Long, Map<Integer, Integer>> mapCurrentHistoricPorts = new HashMap();
  





  public CircuitHistory() {}
  




  public void addCircuit(TCPStreamProperties streamProperties)
  {
    checkTimeframe();
    Long crrTime = Long.valueOf(System.currentTimeMillis() / 60000L);
    if (streamProperties != null) { Integer count;
      Object ports;
      if (streamProperties.isExitPolicyRequired()) {
        Integer localInteger2;
        synchronized (mapCountExternal)
        {
          countExternal += 1;
          Integer count = (Integer)mapCountExternal.get(crrTime);
          if (count == null)
          {
            count = Integer.valueOf(0);
          }
          Integer localInteger1 = count;localInteger2 = count = Integer.valueOf(count.intValue() + 1);
          mapCountExternal.put(crrTime, count);
        }
        synchronized (mapCurrentHistoricPorts)
        {
          Integer port = Integer.valueOf(streamProperties.getPort());
          count = (Integer)mapHistoricPorts.get(port);
          if (count == null)
          {
            count = Integer.valueOf(0);
          }
          localInteger2 = count;Integer localInteger3 = count = Integer.valueOf(count.intValue() + 1);
          mapHistoricPorts.put(port, count);
          ports = (Map)mapCurrentHistoricPorts.get(crrTime);
          if (ports == null)
          {
            ports = new HashMap();
            mapCurrentHistoricPorts.put(crrTime, ports);
          }
          count = (Integer)((Map)ports).get(port);
          if (count == null)
          {
            count = Integer.valueOf(0);
          }
          localInteger3 = count;Integer localInteger4 = count = Integer.valueOf(count.intValue() + 1);
          ((Map)ports).put(port, count);
        }
      }
      else
      {
        synchronized (mapCountInternal)
        {
          countInternal += 1;
          Integer count = (Integer)mapCountInternal.get(crrTime);
          if (count == null)
          {
            count = Integer.valueOf(0);
          }
          count = count;ports = count = Integer.valueOf(count.intValue() + 1);
          mapCountInternal.put(crrTime, count);
        }
      }
    }
  }
  


  public int getCountInternal()
  {
    checkTimeframe();
    return countInternal;
  }
  


  public int getCountExternal()
  {
    checkTimeframe();
    return countExternal;
  }
  


  public Map<Long, Integer> getMapCountInternal()
  {
    checkTimeframe();
    return mapCountInternal;
  }
  


  public Map<Long, Integer> getMapCountExternal()
  {
    checkTimeframe();
    return mapCountExternal;
  }
  


  public Map<Integer, Integer> getMapHistoricPorts()
  {
    checkTimeframe();
    return mapHistoricPorts;
  }
  


  public Map<Long, Map<Integer, Integer>> getMapCurrentHistoricPorts()
  {
    checkTimeframe();
    return mapCurrentHistoricPorts;
  }
  
  private long minTSInternal = 0L;
  
  private long minTSExternal = 0L;
  
  private long minTSPorts = 0L;
  
  private void checkTimeframe() {
    long crrTime = System.currentTimeMillis() / 60000L;
    if (minTSInternal + 10L < crrTime)
    {
      minTSInternal = crrTime;
      Iterator<Map.Entry<Long, Integer>> itEntry = mapCountInternal.entrySet().iterator();
      while (itEntry.hasNext())
      {
        Map.Entry<Long, Integer> entry = (Map.Entry)itEntry.next();
        if (((Long)entry.getKey()).longValue() + 10L < crrTime)
        {
          itEntry.remove();


        }
        else if (((Long)entry.getKey()).longValue() < minTSInternal)
        {
          minTSInternal = ((Long)entry.getKey()).longValue();
        }
      }
      
      if (mapCountInternal.isEmpty())
      {
        minTSInternal = 9223372036854775797L;
      }
    }
    if (minTSExternal + 10L < crrTime)
    {
      minTSExternal = crrTime;
      Iterator<Map.Entry<Long, Integer>> itEntry = mapCountExternal.entrySet().iterator();
      while (itEntry.hasNext())
      {
        Map.Entry<Long, Integer> entry = (Map.Entry)itEntry.next();
        if (((Long)entry.getKey()).longValue() + 10L < crrTime)
        {
          itEntry.remove();


        }
        else if (((Long)entry.getKey()).longValue() < minTSExternal)
        {
          minTSExternal = ((Long)entry.getKey()).longValue();
        }
      }
      
      if (mapCountExternal.isEmpty())
      {
        minTSExternal = 9223372036854775797L;
      }
    }
    if (minTSPorts + 10L < crrTime)
    {
      minTSPorts = crrTime;
      Iterator<Map.Entry<Long, Map<Integer, Integer>>> itEntryPorts = mapCurrentHistoricPorts.entrySet().iterator();
      while (itEntryPorts.hasNext())
      {
        Map.Entry<Long, Map<Integer, Integer>> entry = (Map.Entry)itEntryPorts.next();
        if (((Long)entry.getKey()).longValue() + 10L < crrTime)
        {
          itEntryPorts.remove();


        }
        else if (((Long)entry.getKey()).longValue() < minTSPorts)
        {
          minTSPorts = ((Long)entry.getKey()).longValue();
        }
      }
      
      if (mapCurrentHistoricPorts.isEmpty())
      {
        minTSPorts = 9223372036854775797L;
      }
    }
  }
}
