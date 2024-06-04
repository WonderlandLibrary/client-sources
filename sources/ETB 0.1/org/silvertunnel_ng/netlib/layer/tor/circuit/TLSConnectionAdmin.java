package org.silvertunnel_ng.netlib.layer.tor.circuit;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.layer.tor.api.Fingerprint;
import org.silvertunnel_ng.netlib.layer.tor.api.Router;
import org.silvertunnel_ng.netlib.layer.tor.util.TorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;












































public final class TLSConnectionAdmin
{
  private static final Logger LOG = LoggerFactory.getLogger(TLSConnectionAdmin.class);
  
  protected static final SecureRandom RANDOM = new SecureRandom();
  


  private final Map<Fingerprint, WeakReference<TLSConnection>> connectionMap = Collections.synchronizedMap(new HashMap());
  





  private static Map<Fingerprint, WeakReference<TLSConnection>> connectionMapAll = Collections.synchronizedMap(new HashMap());
  



  private final NetLayer lowerTlsConnectionNetLayer;
  



  public TLSConnectionAdmin(NetLayer lowerTlsConnectionNetLayer)
    throws IOException
  {
    this.lowerTlsConnectionNetLayer = lowerTlsConnectionNetLayer;
  }
  








  TLSConnection getConnection(Router router)
    throws IOException, TorException
  {
    if (router == null)
    {
      throw new TorException("TLSConnectionAdmin: server is NULL");
    }
    
    WeakReference<TLSConnection> weakConn = (WeakReference)connectionMap.get(router.getFingerprint());
    TLSConnection conn = null;
    if (weakConn != null)
    {
      conn = (TLSConnection)weakConn.get();
    }
    if (conn == null)
    {

      LOG.debug("TLSConnectionAdmin: TLS connection to {}", router.getNickname());
      conn = new TLSConnection(router, lowerTlsConnectionNetLayer);
      weakConn = new WeakReference(conn);
      connectionMap.put(router.getFingerprint(), weakConn);
      connectionMapAll.put(router.getFingerprint(), weakConn);
    }
    return conn;
  }
  





  public void removeConnection(TLSConnection conn)
  {
    connectionMap.remove(conn.getRouter().getFingerprint());
  }
  






  static void closeAllTlsConnections()
  {
    synchronized (connectionMapAll)
    {
      for (WeakReference<TLSConnection> w : connectionMapAll.values())
      {
        TLSConnection t = (TLSConnection)w.get();
        if (t != null)
        {
          t.close(true);
        }
      }
      connectionMapAll.clear();
    }
  }
  






  public void close(boolean force)
  {
    synchronized (connectionMap)
    {
      for (WeakReference<TLSConnection> w : connectionMap.values())
      {
        TLSConnection t = (TLSConnection)w.get();
        if (t != null)
        {
          t.close(force);
        }
      }
      connectionMap.clear();
    }
  }
  






  public Collection<TLSConnection> getConnections()
  {
    Collection<Fingerprint> entriesToRemove = new ArrayList(connectionMap.size());
    Collection<TLSConnection> result = new ArrayList(connectionMap.size());
    synchronized (connectionMap)
    {
      for (Fingerprint fingerprint : connectionMap.keySet())
      {
        WeakReference<TLSConnection> weakReference = (WeakReference)connectionMap.get(fingerprint);
        if (weakReference != null)
        {
          TLSConnection tlsConnection = (TLSConnection)weakReference.get();
          if (tlsConnection != null)
          {

            result.add(tlsConnection);

          }
          else
          {
            entriesToRemove.add(fingerprint);
          }
        }
      }
      
      for (Fingerprint f : entriesToRemove)
      {
        connectionMap.remove(f);
      }
    }
    synchronized (connectionMapAll)
    {

      for (Fingerprint f : entriesToRemove)
      {
        connectionMapAll.remove(f);
      }
    }
    return result;
  }
}
