package org.silvertunnel_ng.netlib.layer.tor.api;

public abstract interface Fingerprint
  extends Comparable<Fingerprint>
{
  public abstract String getHex();
  
  public abstract String getHexWithSpaces();
  
  public abstract byte[] getBytes();
  
  public abstract String toString();
  
  public abstract int hashCode();
  
  public abstract boolean equals(Object paramObject);
  
  public abstract int compareTo(Fingerprint paramFingerprint);
  
  public abstract Fingerprint cloneReliable()
    throws RuntimeException;
}
