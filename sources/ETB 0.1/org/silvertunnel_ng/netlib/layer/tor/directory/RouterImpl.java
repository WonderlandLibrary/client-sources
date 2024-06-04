package org.silvertunnel_ng.netlib.layer.tor.directory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.silvertunnel_ng.netlib.api.util.TcpipNetAddress;
import org.silvertunnel_ng.netlib.layer.tor.api.Fingerprint;
import org.silvertunnel_ng.netlib.layer.tor.api.Router;
import org.silvertunnel_ng.netlib.layer.tor.api.RouterExitPolicy;
import org.silvertunnel_ng.netlib.layer.tor.common.LookupServiceUtil;
import org.silvertunnel_ng.netlib.layer.tor.common.TorConfig;
import org.silvertunnel_ng.netlib.layer.tor.util.Encoding;
import org.silvertunnel_ng.netlib.layer.tor.util.Encryption;
import org.silvertunnel_ng.netlib.layer.tor.util.TorException;
import org.silvertunnel_ng.netlib.layer.tor.util.Util;
import org.silvertunnel_ng.netlib.tool.ConvenientStreamReader;
import org.silvertunnel_ng.netlib.tool.ConvenientStreamWriter;
import org.silvertunnel_ng.netlib.util.DatatypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;








































public final class RouterImpl
  implements Router, Cloneable
{
  private static final Logger LOG = LoggerFactory.getLogger(RouterImpl.class);
  

  private String nickname;
  

  private String hostname;
  

  private InetAddress address;
  

  private String countryCode;
  

  private int orPort;
  

  private int socksPort;
  

  private int dirPort;
  

  private int bandwidthAvg;
  

  private int bandwidthBurst;
  

  private int bandwidthObserved;
  

  private String platform;
  

  private long published;
  

  private Fingerprint fingerprint;
  

  private Fingerprint v3ident;
  

  private int uptime;
  

  private RSAPublicKey onionKey;
  

  private RSAPublicKey signingKey;
  
  private RouterExitPolicy[] exitpolicy;
  
  private byte[] routerSignature;
  
  private String contact;
  
  private Set<Fingerprint> family = new HashSet();
  


  private Set<String> familyNames = new HashSet();
  



  private long validUntil;
  



  private static final int MAX_EXITPOLICY_ITEMS = 300;
  


  private long lastUpdate;
  


  private RouterFlags routerFlags = new RouterFlags();
  


  private float rankingIndex;
  


  private static final int HIGH_BANDWIDTH = 2097152;
  


  private static final float ALPHA = 0.6F;
  

  private static final float punishmentFactor = 0.75F;
  

  private static final int MAX_ROUTERDESCRIPTOR_LENGTH = 10000;
  

  private static final byte CURRENT_BINARY_VERSION = 1;
  


  public RouterImpl(String routerDescriptor)
    throws TorException
  {
    if (routerDescriptor.length() > 10000) {
      throw new TorException("skipped router with routerDescriptor of length=" + routerDescriptor.length());
    }
    
    init();
    parseRouterDescriptor(routerDescriptor);
    updateServerRanking();
    
    countryCode = LookupServiceUtil.getCountryCodeOfIpAddress(address);
  }
  





  public RouterImpl(RSAPublicKey publicKey)
  {
    init();
    onionKey = publicKey;
    

    countryCode = "--";
  }
  














  RouterImpl(String nickname, InetAddress address, int orPort, int dirPort, Fingerprint v3ident, Fingerprint fingerprint)
  {
    this.nickname = nickname;
    this.address = address;
    hostname = address.getHostAddress();
    
    this.orPort = orPort;
    this.dirPort = dirPort;
    this.fingerprint = fingerprint.cloneReliable();
    this.v3ident = (v3ident == null ? null : v3ident.cloneReliable());
  }
  



  private void init()
  {
    rankingIndex = -1.0F;
  }
  




  public void updateServerStatus(RouterStatusDescription statusDescription)
  {
    routerFlags = statusDescription.getRouterFlags();
  }
  









  protected RouterImpl(ConvenientStreamReader convenientStreamReader)
    throws IOException, TorException
  {
    if (convenientStreamReader.readByte() != 1) {
      throw new TorException("the saved binary version identifier doesnt match the current! Cannot parse the object.");
    }
    nickname = convenientStreamReader.readString();
    hostname = convenientStreamReader.readString();
    int len = convenientStreamReader.readInt();
    try {
      if (len == 0) {
        address = InetAddress.getByName(hostname);
      } else {
        address = InetAddress.getByAddress(convenientStreamReader.readByteArray(len));
      }
    } catch (UnknownHostException exception) {
      throw new TorException("error while parsing address field.", exception);
    }
    countryCode = convenientStreamReader.readString();
    orPort = convenientStreamReader.readInt();
    socksPort = convenientStreamReader.readInt();
    dirPort = convenientStreamReader.readInt();
    bandwidthAvg = convenientStreamReader.readInt();
    bandwidthBurst = convenientStreamReader.readInt();
    bandwidthObserved = convenientStreamReader.readInt();
    platform = convenientStreamReader.readString();
    published = convenientStreamReader.readLong();
    int count = convenientStreamReader.readInt();
    if (count == 0) {
      fingerprint = null;
    } else {
      fingerprint = new FingerprintImpl(convenientStreamReader.readByteArray(count));
    }
    count = convenientStreamReader.readInt();
    if (count == 0) {
      v3ident = null;
    } else {
      v3ident = new FingerprintImpl(convenientStreamReader.readByteArray(count));
    }
    uptime = convenientStreamReader.readInt();
    onionKey = Encryption.extractBinaryRSAKey(convenientStreamReader.readByteArray());
    signingKey = Encryption.extractBinaryRSAKey(convenientStreamReader.readByteArray());
    count = convenientStreamReader.readInt();
    if (count == 0) {
      exitpolicy = null;
    } else {
      exitpolicy = new RouterExitPolicy[count];
      for (int i = 0; i < count; i++) {
        exitpolicy[i] = RouterExitPolicyImpl.parseFrom(convenientStreamReader);
      }
    }
    routerSignature = convenientStreamReader.readByteArray();
    contact = convenientStreamReader.readString();
    count = convenientStreamReader.readInt();
    family.clear();
    if (count > 0) {
      for (int i = 0; i < count; i++) {
        family.add(new FingerprintImpl(convenientStreamReader.readByteArray()));
      }
    }
    validUntil = convenientStreamReader.readLong();
    lastUpdate = convenientStreamReader.readLong();
    routerFlags = new RouterFlags(convenientStreamReader);
    rankingIndex = convenientStreamReader.readFloat();
    count = convenientStreamReader.readInt();
    familyNames.clear();
    if (count > 0) {
      for (int i = 0; i < count; i++) {
        familyNames.add(new String(convenientStreamReader.readByteArray()));
      }
    }
  }
  


  public void save(ConvenientStreamWriter convenientStreamWriter)
    throws IOException
  {
    convenientStreamWriter.writeByte((byte)1);
    convenientStreamWriter.writeString(nickname);
    convenientStreamWriter.writeString(hostname);
    convenientStreamWriter.writeByteArray(address.getAddress(), true);
    convenientStreamWriter.writeString(countryCode);
    convenientStreamWriter.writeInt(orPort);
    convenientStreamWriter.writeInt(socksPort);
    convenientStreamWriter.writeInt(dirPort);
    convenientStreamWriter.writeInt(bandwidthAvg);
    convenientStreamWriter.writeInt(bandwidthBurst);
    convenientStreamWriter.writeInt(bandwidthObserved);
    convenientStreamWriter.writeString(platform);
    convenientStreamWriter.writeLong(published);
    convenientStreamWriter.writeByteArray(fingerprint.getBytes(), true);
    if (v3ident == null) {
      convenientStreamWriter.writeInt(0);
    } else {
      convenientStreamWriter.writeByteArray(v3ident.getBytes(), true);
    }
    convenientStreamWriter.writeInt(uptime);
    convenientStreamWriter.writeByteArray(Encryption.getPKCS1EncodingFromRSAPublicKey(onionKey), true);
    convenientStreamWriter.writeByteArray(Encryption.getPKCS1EncodingFromRSAPublicKey(signingKey), true);
    convenientStreamWriter.writeInt(exitpolicy.length);
    for (RouterExitPolicy exitPolicy : exitpolicy) {
      exitPolicy.save(convenientStreamWriter);
    }
    convenientStreamWriter.writeByteArray(routerSignature, true);
    convenientStreamWriter.writeString(contact);
    convenientStreamWriter.writeInt(family.size());
    for (??? = family.iterator(); ((Iterator)???).hasNext();) { Fingerprint member = (Fingerprint)((Iterator)???).next();
      convenientStreamWriter.writeByteArray(member.getBytes(), true);
    }
    convenientStreamWriter.writeLong(validUntil);
    convenientStreamWriter.writeLong(lastUpdate);
    routerFlags.save(convenientStreamWriter);
    convenientStreamWriter.writeFloat(rankingIndex);
    convenientStreamWriter.writeInt(familyNames.size());
    for (??? = familyNames.iterator(); ((Iterator)???).hasNext();) { String member = (String)((Iterator)???).next();
      convenientStreamWriter.writeByteArray(member.getBytes(), true);
    }
  }
  
  public Router cloneReliable()
    throws RuntimeException
  {
    try
    {
      return (Router)clone();
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
  }
  
  private static final Pattern EXIT_POLICY_PATTERN = Pattern.compile("^(accept|reject) (.*?):(.*?)$", 43);
  






  private RouterExitPolicy[] parseExitPolicy(String routerDescriptor)
  {
    ArrayList<RouterExitPolicy> epList = new ArrayList(30);
    
    Matcher matcher = EXIT_POLICY_PATTERN.matcher(routerDescriptor);
    

    int nr = 0;
    while ((matcher.find()) && (nr < 300))
    {




      boolean epAccept = matcher.group(1).equals("accept");
      
      String network = matcher.group(2);
      long epIp = 0L;
      long epNetmask = 0L;
      if (!network.equals("*")) {
        int slash = network.indexOf('/');
        if (slash >= 0) {
          epIp = Encoding.dottedNotationToBinary(network.substring(0, slash));
          String netmask = network.substring(slash + 1);
          if (netmask.indexOf('.') > -1) {
            epNetmask = Encoding.dottedNotationToBinary(netmask);
          } else {
            epNetmask = 4294967295L << 32 - Integer.parseInt(netmask) & 0xFFFFFFFF;
          }
        } else {
          epIp = Encoding.dottedNotationToBinary(network);
          epNetmask = -1L;
        }
      }
      epIp &= epNetmask;
      int epHiPort;
      int epLoPort; int epHiPort; if (matcher.group(3).equals("*")) {
        int epLoPort = 0;
        epHiPort = 65535;
      } else {
        int dash = matcher.group(3).indexOf("-");
        int epHiPort; if (dash > 0) {
          int epLoPort = Integer.parseInt(matcher.group(3).substring(0, dash));
          epHiPort = Integer.parseInt(matcher.group(3).substring(dash + 1));
        } else {
          epLoPort = Integer.parseInt(matcher.group(3));
          epHiPort = epLoPort;
        }
      }
      nr++;
      epList.add(new RouterExitPolicyImpl(epAccept, epIp, epNetmask, epLoPort, epHiPort));
    }
    if ((LOG.isDebugEnabled()) && (nr >= 300)) {
      LOG.debug("Router has more than {} exitpolicy items", Integer.valueOf(300));
    }
    return (RouterExitPolicy[])epList.toArray(new RouterExitPolicy[epList.size()]);
  }
  




  private void parseRouterDescriptor(String routerDescriptor)
    throws TorException
  {
    long timeStart = System.currentTimeMillis();
    String[] tmpLine = routerDescriptor.split("\n");
    
    Map<RouterDescriptorFormatKeys, Integer> keysToFind = RouterDescriptorFormatKeys.getAllKeysAsMap();
    MessageDigest mdMessage = null;
    boolean runMd = false;
    

    StringBuffer exitPolicyString = new StringBuffer();
    String[] tmpElements; Iterator<Map.Entry<RouterDescriptorFormatKeys, Integer>> it; for (int i = 0; i < tmpLine.length; i++) {
      if ((mdMessage == null) && (tmpLine[i].startsWith("router "))) {
        mdMessage = Encryption.getMessagesDigest();
        runMd = true;
      }
      if (runMd) {
        mdMessage.update((tmpLine[i] + "\n").getBytes());
        if ("router-signature".equals(tmpLine[i])) {
          runMd = false;
        }
      }
      if (tmpLine[i].startsWith("opt"))
      {
        tmpLine[i] = tmpLine[i].substring(4);
      }
      tmpElements = tmpLine[i].split(" ");
      for (it = keysToFind.entrySet().iterator(); it.hasNext();) {
        Map.Entry<RouterDescriptorFormatKeys, Integer> entry = (Map.Entry)it.next();
        RouterDescriptorFormatKeys key = (RouterDescriptorFormatKeys)entry.getKey();
        if (tmpLine[i].startsWith(key.getValue())) {
          if (((Integer)entry.getValue()).intValue() == 1) {
            it.remove();
          }
          else {
            entry.setValue(Integer.valueOf(((Integer)entry.getValue()).intValue() - 1));
          }
          switch (1.$SwitchMap$org$silvertunnel_ng$netlib$layer$tor$directory$RouterDescriptorFormatKeys[key.ordinal()]) {
          case 1: 
            nickname = tmpElements[1];
            hostname = tmpElements[2];
            orPort = Integer.parseInt(tmpElements[3]);
            socksPort = Integer.parseInt(tmpElements[4]);
            dirPort = Integer.parseInt(tmpElements[5]);
            break;
          case 2: 
            platform = tmpLine[i].substring("platform".length() + 1);
            break;
          case 3: 
            try {
              fingerprint = new FingerprintImpl(DatatypeConverter.parseHexBinary(tmpLine[i].substring("fingerprint".length())
                .replaceAll(" ", "")));
            } catch (Exception e) {
              LOG.debug("got Exception while parsing fingerprint : {}", e, e);
              throw new TorException("Server " + nickname + " skipped as router", e);
            }
          
          case 4: 
            published = Util.parseUtcTimestamp(tmpElements[1] + " " + tmpElements[2]).getTime();
            validUntil = (published + 86400000L);
            break;
          case 5: 
            uptime = Integer.parseInt(tmpElements[1]);
            break;
          case 6: 
            bandwidthAvg = Integer.parseInt(tmpElements[1]);
            bandwidthBurst = Integer.parseInt(tmpElements[2]);
            bandwidthObserved = Integer.parseInt(tmpElements[3]);
            break;
          case 7: 
            contact = tmpLine[i].substring("contact".length() + 1);
            break;
          case 8: 
            for (int n = 1; n < tmpElements.length; n++) {
              if (tmpElements[n].startsWith("$")) {
                family.add(new FingerprintImpl(DatatypeConverter.parseHexBinary(tmpElements[n].substring(1, 41))));
              } else {
                familyNames.add(tmpElements[n]);
              }
            }
          case 9: 
            routerFlags.setHibernating(Boolean.valueOf(true));
            break;
          case 10: 
            routerFlags.setHSDir(Boolean.valueOf(true));
            break;
          case 11: 
            break;
          
          case 12: 
            break;
          
          case 13: 
            break;
          
          case 14: 
            break;
          
          case 15: 
            StringBuffer tmpOnionKey = new StringBuffer();
            i++;
            while (!tmpLine[i].contains("END RSA PUBLIC KEY")) {
              tmpOnionKey.append(tmpLine[i]).append('\n');
              i++;
            }
            tmpOnionKey.append(tmpLine[i]).append('\n');
            mdMessage.update(tmpOnionKey.toString().getBytes());
            onionKey = Encryption.extractPublicRSAKey(tmpOnionKey.toString());
            break;
          case 16: 
            StringBuffer tmpSigningKey = new StringBuffer();
            i++;
            while (!tmpLine[i].contains("END RSA PUBLIC KEY")) {
              tmpSigningKey.append(tmpLine[i]).append('\n');
              i++;
            }
            tmpSigningKey.append(tmpLine[i]).append('\n');
            mdMessage.update(tmpSigningKey.toString().getBytes());
            signingKey = Encryption.extractPublicRSAKey(tmpSigningKey.toString());
            break;
          case 17: 
            StringBuffer tmpSignature = new StringBuffer();
            i += 2;
            while (!tmpLine[i].contains("END SIGNATURE")) {
              tmpSignature.append(tmpLine[i]);
              i++;
            }
            while (tmpSignature.length() % 4 != 0) {
              tmpSignature.append('=');
            }
            routerSignature = DatatypeConverter.parseBase64Binary(tmpSignature.toString());
            break;
          case 18: 
            exitPolicyString.append(tmpLine[i]).append('\n');
            break;
          case 19: 
            exitPolicyString.append(tmpLine[i]).append('\n');
            break;
          case 20: 
            break;
          
          case 21: 
            break;
          
          case 22: 
            break;
          
          default: 
            LOG.debug("it seems that we are not reading the following key : {}", key.getValue());
          }
          
        }
      }
    }
    


    try
    {
      byte[] pkcs = Encryption.getPKCS1EncodingFromRSAPublicKey(signingKey);
      byte[] keyHash = Encryption.getDigest(pkcs);
      if (!new FingerprintImpl(keyHash).equals(fingerprint)) {
        throw new TorException("Server " + nickname + " doesn't verify signature vs fingerprint");
      }
    } catch (TorException e) {
      throw e;
    } catch (Exception e) {
      throw new TorException("Server " + nickname + " doesn't verify signature vs fingerprint", e);
    }
    

    byte[] sha1Digest = mdMessage.digest();
    if (!Encryption.verifySignatureWithHash(routerSignature, signingKey, sha1Digest)) {
      LOG.info("Server -> router-signature check failed for " + nickname);
      throw new TorException("Server " + nickname + ": description signature verification failed");
    }
    

    exitpolicy = parseExitPolicy(exitPolicyString.toString());
    
    try
    {
      address = InetAddress.getByName(hostname);
    } catch (UnknownHostException e) {
      throw new TorException("Server.ParseRouterDescriptor: Unresolvable hostname " + hostname);
    }
    if (LOG.isDebugEnabled()) {
      LOG.debug("RouterImpl.parseRouterDescriptor took " + (System.currentTimeMillis() - timeStart) + " ms");
    }
  }
  










  private void updateServerRanking()
  {
    float rankingFromDirectory = (Math.min(1, uptime / 86400) + Math.min(1.0F, (bandwidthAvg * 0.6F + bandwidthObserved * 0.39999998F) / 2097152.0F)) / 2.0F;
    

    if (rankingIndex < 0.0F) {
      rankingIndex = rankingFromDirectory;
    } else {
      rankingIndex = (rankingFromDirectory * (1.0F - TorConfig.rankingTransferPerServerUpdate) + rankingIndex * TorConfig.rankingTransferPerServerUpdate);
    }
  }
  













  public float getRefinedRankingIndex(float p)
  {
    return rankingIndex * p + TorConfig.rankingIndexEffect * (1.0F - p);
  }
  


  public void punishRanking()
  {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Punishing " + toLongString());
    }
    rankingIndex *= 0.75F;
  }
  



  public boolean exitPolicyAccepts(InetAddress addr, int port)
  {
    long ip;
    


    long ip;
    

    if (addr != null) {
      byte[] temp1 = addr.getAddress();
      long[] temp = new long[4];
      for (int i = 0; i < 4; i++) {
        temp[i] = temp1[i];
        if (temp[i] < 0L) {
          temp[i] = (256L + temp[i]);
        }
      }
      ip = temp[0] << 24 | temp[1] << 16 | temp[2] << 8 | temp[3];
    }
    else {
      if (port == 0) {
        return true;
      }
      

      ip = 4294967295L;
    }
    
    for (int i = 0; i < exitpolicy.length; i++) {
      if ((exitpolicy[i].getLoPort() <= port) && (exitpolicy[i].getHiPort() >= port) && 
        (exitpolicy[i].getIp() == (ip & exitpolicy[i].getNetmask()))) {
        return exitpolicy[i].isAccept();
      }
    }
    return false;
  }
  


  protected boolean isDirServer()
  {
    return dirPort > 0;
  }
  



  public boolean isExitNode()
  {
    if ((!routerFlags.isBadExit()) && (routerFlags.isExit())) {
      for (RouterExitPolicy singleExitPolicy : exitpolicy) {
        if (singleExitPolicy.isAccept()) {
          return true;
        }
      }
    }
    return false;
  }
  



  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append("router=" + nickname);
    sb.append("," + hostname);
    sb.append("," + fingerprint);
    sb.append("," + platform);
    return sb.toString();
  }
  


  public String toLongString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append("---- ").append(nickname).append(" (").append(contact).append(")\n");
    sb.append("hostname:").append(hostname).append('\n');
    sb.append("or port:").append(orPort).append('\n');
    sb.append("socks port:").append(socksPort).append('\n');
    sb.append("dirserver port:").append(dirPort).append('\n');
    sb.append("platform:").append(platform).append('\n');
    sb.append("published:").append(new Date(published)).append('\n');
    sb.append("uptime:").append(uptime).append('\n');
    sb.append("rankingIndex:").append(rankingIndex).append('\n');
    sb.append("bandwidth: ").append(bandwidthAvg).append(' ').append(bandwidthBurst).append(' ').append(bandwidthObserved).append('\n');
    sb.append("fingerprint:").append(fingerprint).append('\n');
    sb.append("validUntil:").append(new Date(validUntil)).append('\n');
    sb.append("onion key:").append(onionKey).append('\n');
    sb.append("signing key:").append(signingKey).append('\n');
    sb.append("signature:").append(DatatypeConverter.printHexBinary(routerSignature)).append('\n');
    sb.append("exit policies:").append('\n');
    for (int i = 0; i < exitpolicy.length; i++) {
      sb.append("  ").append(exitpolicy[i]).append('\n');
    }
    return sb.toString();
  }
  




  public boolean isValid()
  {
    return validUntil > System.currentTimeMillis();
  }
  


  public TcpipNetAddress getDirAddress()
  {
    byte[] ipaddress = address.getAddress();
    if (ipaddress != null) {
      return new TcpipNetAddress(ipaddress, dirPort);
    }
    return new TcpipNetAddress(address.getHostName(), dirPort);
  }
  



  public TcpipNetAddress getOrAddress()
  {
    byte[] ipaddress = address.getAddress();
    if (ipaddress != null) {
      return new TcpipNetAddress(ipaddress, orPort);
    }
    return new TcpipNetAddress(address.getHostName(), orPort);
  }
  





  public String getNickname()
  {
    return nickname;
  }
  
  public String getHostname()
  {
    return hostname;
  }
  
  public InetAddress getAddress()
  {
    return address;
  }
  
  public String getCountryCode()
  {
    return countryCode;
  }
  
  public int getOrPort()
  {
    return orPort;
  }
  
  public int getSocksPort()
  {
    return socksPort;
  }
  
  public int getDirPort()
  {
    return dirPort;
  }
  
  public int getBandwidthAvg()
  {
    return bandwidthAvg;
  }
  
  public int getBandwidthBurst()
  {
    return bandwidthBurst;
  }
  
  public int getBandwidthObserved()
  {
    return bandwidthObserved;
  }
  
  public String getPlatform()
  {
    return platform;
  }
  
  public long getPublished()
  {
    return published;
  }
  
  public Fingerprint getFingerprint()
  {
    return fingerprint;
  }
  
  public Fingerprint getV3Ident() {
    return v3ident;
  }
  
  public int getUptime()
  {
    return uptime;
  }
  
  public RSAPublicKey getOnionKey()
  {
    return onionKey;
  }
  
  public RSAPublicKey getSigningKey()
  {
    return signingKey;
  }
  
  public String getContact()
  {
    return contact;
  }
  
  public Set<Fingerprint> getFamily()
  {
    return family;
  }
  
  public Set<String> getFamilyNames()
  {
    return familyNames;
  }
  
  public long getValidUntil()
  {
    return validUntil;
  }
  
  public long getLastUpdate()
  {
    return lastUpdate;
  }
  
  public boolean isDirv2Authority()
  {
    return routerFlags.isAuthority();
  }
  
  public boolean isDirv2Exit()
  {
    return (routerFlags.isExit()) && (!routerFlags.isBadExit());
  }
  
  public boolean isDirv2Fast()
  {
    return routerFlags.isFast();
  }
  
  public boolean isDirv2Guard()
  {
    return routerFlags.isGuard();
  }
  
  public boolean isDirv2Named()
  {
    return routerFlags.isNamed();
  }
  
  public boolean isDirv2Stable()
  {
    return routerFlags.isStable();
  }
  
  public boolean isDirv2Running()
  {
    return (routerFlags.isRunning()) && (!routerFlags.isHibernating());
  }
  
  public boolean isDirv2Valid()
  {
    return routerFlags.isValid();
  }
  
  public boolean isDirv2V2dir()
  {
    return routerFlags.isV2Dir();
  }
  


  public boolean isDirv2HSDir()
  {
    return routerFlags.isHSDir();
  }
  
  public float getRankingIndex()
  {
    return rankingIndex;
  }
  





  public int hashCode()
  {
    int prime = 31;
    int result = 1;
    result = 31 * result + (address == null ? 0 : address.hashCode());
    result = 31 * result + bandwidthAvg;
    result = 31 * result + bandwidthBurst;
    result = 31 * result + bandwidthObserved;
    result = 31 * result + (contact == null ? 0 : contact.hashCode());
    result = 31 * result + (countryCode == null ? 0 : countryCode.hashCode());
    result = 31 * result + dirPort;
    result = 31 * result + routerFlags.hashCode();
    result = 31 * result + Arrays.hashCode(exitpolicy);
    result = 31 * result + (family == null ? 0 : family.hashCode());
    result = 31 * result + (familyNames == null ? 0 : familyNames.hashCode());
    result = 31 * result + (fingerprint == null ? 0 : fingerprint.hashCode());
    result = 31 * result + (hostname == null ? 0 : hostname.hashCode());
    result = 31 * result + (int)lastUpdate;
    result = 31 * result + (nickname == null ? 0 : nickname.hashCode());
    result = 31 * result + (onionKey == null ? 0 : onionKey.hashCode());
    result = 31 * result + orPort;
    result = 31 * result + (platform == null ? 0 : platform.hashCode());
    result = 31 * result + (int)published;
    result = 31 * result + Float.floatToIntBits(rankingIndex);
    result = 31 * result + Arrays.hashCode(routerSignature);
    result = 31 * result + (signingKey == null ? 0 : signingKey.hashCode());
    result = 31 * result + socksPort;
    result = 31 * result + uptime;
    result = 31 * result + (v3ident == null ? 0 : v3ident.hashCode());
    result = (int)(31 * result + validUntil);
    return result;
  }
  





  public boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof RouterImpl)) {
      return false;
    }
    RouterImpl other = (RouterImpl)obj;
    if (address == null) {
      if (address != null) {
        return false;
      }
    } else if (!address.equals(address)) {
      return false;
    }
    if (bandwidthAvg != bandwidthAvg) {
      return false;
    }
    if (bandwidthBurst != bandwidthBurst) {
      return false;
    }
    if (bandwidthObserved != bandwidthObserved) {
      return false;
    }
    if (contact == null) {
      if (contact != null) {
        return false;
      }
    } else if (!contact.equals(contact)) {
      return false;
    }
    if (countryCode == null) {
      if (countryCode != null) {
        return false;
      }
    } else if (!countryCode.equals(countryCode)) {
      return false;
    }
    if (dirPort != dirPort) {
      return false;
    }
    if (!routerFlags.equals(routerFlags)) {
      return false;
    }
    if (!Arrays.equals(exitpolicy, exitpolicy)) {
      return false;
    }
    if (family == null) {
      if (family != null) {
        return false;
      }
    } else if (!family.equals(family)) {
      return false;
    }
    if (familyNames == null) {
      if (familyNames != null) {
        return false;
      }
    } else if (!familyNames.equals(familyNames)) {
      return false;
    }
    if (fingerprint == null) {
      if (fingerprint != null) {
        return false;
      }
    } else if (!fingerprint.equals(fingerprint)) {
      return false;
    }
    if (hostname == null) {
      if (hostname != null) {
        return false;
      }
    } else if (!hostname.equals(hostname)) {
      return false;
    }
    if (lastUpdate != lastUpdate) {
      return false;
    }
    if (nickname == null) {
      if (nickname != null) {
        return false;
      }
    } else if (!nickname.equals(nickname)) {
      return false;
    }
    if (onionKey == null) {
      if (onionKey != null) {
        return false;
      }
    } else if (!onionKey.equals(onionKey)) {
      return false;
    }
    if (orPort != orPort) {
      return false;
    }
    if (platform == null) {
      if (platform != null) {
        return false;
      }
    } else if (!platform.equals(platform)) {
      return false;
    }
    if (published != published) {
      return false;
    }
    if (Float.floatToIntBits(rankingIndex) != Float.floatToIntBits(rankingIndex)) {
      return false;
    }
    if (!Arrays.equals(routerSignature, routerSignature)) {
      return false;
    }
    if (signingKey == null) {
      if (signingKey != null) {
        return false;
      }
    } else if (!Arrays.equals(signingKey.getEncoded(), signingKey.getEncoded())) {
      return false;
    }
    if (socksPort != socksPort) {
      return false;
    }
    if (uptime != uptime) {
      return false;
    }
    if (v3ident == null) {
      if (v3ident != null) {
        return false;
      }
    } else if (!v3ident.equals(v3ident)) {
      return false;
    }
    if (validUntil != validUntil) {
      return false;
    }
    return true;
  }
  
  public RouterFlags getRouterFlags()
  {
    return routerFlags;
  }
}
