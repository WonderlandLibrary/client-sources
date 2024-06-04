package org.silvertunnel_ng.netlib.layer.tor.api;

import java.io.IOException;
import java.net.InetAddress;
import java.security.interfaces.RSAPublicKey;
import java.util.Set;
import org.silvertunnel_ng.netlib.api.util.TcpipNetAddress;
import org.silvertunnel_ng.netlib.layer.tor.directory.RouterFlags;
import org.silvertunnel_ng.netlib.layer.tor.directory.RouterStatusDescription;
import org.silvertunnel_ng.netlib.tool.ConvenientStreamWriter;

public abstract interface Router
{
  public abstract String getNickname();
  
  public abstract String getHostname();
  
  public abstract InetAddress getAddress();
  
  public abstract String getCountryCode();
  
  public abstract String toLongString();
  
  public abstract TcpipNetAddress getDirAddress();
  
  public abstract void updateServerStatus(RouterStatusDescription paramRouterStatusDescription);
  
  public abstract int getOrPort();
  
  public abstract int getSocksPort();
  
  public abstract int getDirPort();
  
  public abstract int getBandwidthAvg();
  
  public abstract int getBandwidthBurst();
  
  public abstract int getBandwidthObserved();
  
  public abstract String getPlatform();
  
  public abstract long getPublished();
  
  public abstract Fingerprint getFingerprint();
  
  public abstract int getUptime();
  
  public abstract RSAPublicKey getOnionKey();
  
  public abstract RSAPublicKey getSigningKey();
  
  public abstract String getContact();
  
  public abstract Set<Fingerprint> getFamily();
  
  public abstract Set<String> getFamilyNames();
  
  public abstract long getValidUntil();
  
  public abstract long getLastUpdate();
  
  public abstract boolean isDirv2Authority();
  
  public abstract boolean isDirv2Exit();
  
  public abstract boolean isDirv2Fast();
  
  public abstract boolean isDirv2Guard();
  
  public abstract boolean isDirv2Named();
  
  public abstract boolean isDirv2Stable();
  
  public abstract boolean isDirv2Running();
  
  public abstract boolean isDirv2Valid();
  
  public abstract boolean isValid();
  
  public abstract boolean isDirv2V2dir();
  
  public abstract boolean isDirv2HSDir();
  
  public abstract boolean isExitNode();
  
  public abstract Fingerprint getV3Ident();
  
  public abstract void punishRanking();
  
  public abstract Router cloneReliable();
  
  public abstract boolean exitPolicyAccepts(InetAddress paramInetAddress, int paramInt);
  
  public abstract void save(ConvenientStreamWriter paramConvenientStreamWriter)
    throws IOException;
  
  public abstract float getRefinedRankingIndex(float paramFloat);
  
  public abstract TcpipNetAddress getOrAddress();
  
  public abstract RouterFlags getRouterFlags();
  
  public abstract float getRankingIndex();
}
