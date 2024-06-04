package org.spongycastle.crypto.tls;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

public abstract interface TlsClient
  extends TlsPeer
{
  public abstract void init(TlsClientContext paramTlsClientContext);
  
  public abstract TlsSession getSessionToResume();
  
  public abstract ProtocolVersion getClientHelloRecordLayerVersion();
  
  public abstract ProtocolVersion getClientVersion();
  
  public abstract boolean isFallback();
  
  public abstract int[] getCipherSuites();
  
  public abstract short[] getCompressionMethods();
  
  public abstract Hashtable getClientExtensions()
    throws IOException;
  
  public abstract void notifyServerVersion(ProtocolVersion paramProtocolVersion)
    throws IOException;
  
  public abstract void notifySessionID(byte[] paramArrayOfByte);
  
  public abstract void notifySelectedCipherSuite(int paramInt);
  
  public abstract void notifySelectedCompressionMethod(short paramShort);
  
  public abstract void processServerExtensions(Hashtable paramHashtable)
    throws IOException;
  
  public abstract void processServerSupplementalData(Vector paramVector)
    throws IOException;
  
  public abstract TlsKeyExchange getKeyExchange()
    throws IOException;
  
  public abstract TlsAuthentication getAuthentication()
    throws IOException;
  
  public abstract Vector getClientSupplementalData()
    throws IOException;
  
  public abstract void notifyNewSessionTicket(NewSessionTicket paramNewSessionTicket)
    throws IOException;
}
