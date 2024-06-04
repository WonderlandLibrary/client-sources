package org.silvertunnel_ng.netlib.layer.tor.directory;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.silvertunnel_ng.netlib.api.util.TcpipNetAddress;
import org.silvertunnel_ng.netlib.layer.tor.api.Fingerprint;
import org.silvertunnel_ng.netlib.layer.tor.api.Router;
import org.silvertunnel_ng.netlib.util.DatatypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
























public class AuthorityServers
{
  private static final Logger LOG = LoggerFactory.getLogger(AuthorityServers.class);
  














































  private static final String[] RAW_DATA = { "moria1 orport=9101 v3ident=D586D18309DED4CD6D57C18FDB97EFA96D330566 128.31.0.39:9131 9695 DFC3 5FFE B861 329B 9F1A B04C 4639 7020 CE31", "tor26 orport=443 v3ident=14C131DFC5C6F93646BE72FA1401C02A8DF2E8B4 86.59.21.38:80 847B 1F85 0344 D787 6491 A548 92F9 0493 4E4E B85D", "dizum orport=443 v3ident=E8A9C45EDE6D711294FADF8E7951F4DE6CA56B58 194.109.206.212:80 7EA6 EAD6 FD83 083C 538F 4403 8BBF A077 587D D755", "Tonga orport=443 bridge 82.94.251.203:80 4A0C CD2D DC79 9508 3D73 F5D6 6710 0C8A 5831 F16D", "gabelmoo orport=443 v3ident=ED03BB616EB2F60BEC80151114BB25CEF515B226 131.188.40.189:80 F204 4413 DAC2 E02E 3D6B CF47 35A1 9BCA 1DE9 7281", "dannenberg orport=443 v3ident=585769C78764D58426B8B52B6651A5A71137189A 193.23.244.244:80 7BE6 83E6 5D48 1413 21C5 ED92 F075 C553 64AC 7123", "urras orport=80 v3ident=80550987E1D626E3EBA5E5E75A458DE0626D088C 208.83.223.34:443 0AD3 FA88 4D18 F89E EA2D 89C0 1937 9E0E 7FD9 4417", "maatuska orport=80 v3ident=49015F787433103580E3B66A1707A00E60F2D15B 171.25.193.9:443 BD6A 8292 55CB 08E6 6FBE 7D37 4836 3586 E46B 3810", "Faravahar orport=443 v3ident=EFCBE720AB3A82B99F9E953CD5BF50F7EEFC7B97 154.35.175.225:80 CF6D 0AAF B385 BE71 B8E1 11FC 5CFF 4B47 9237 33BC", "longclaw orport=443 v3ident=23D15D965BC35114467363C165C4F724B64B4F66 199.254.238.52:80 74A9 1064 6BCE EFBC D2E8 74FC 1DC9 9743 0F96 8145" };
  











  private static Collection<Router> parsedAuthorityRouters;
  











  private static Pattern pattern;
  












  static
  {
    try
    {
      pattern = Pattern.compile("^(\\w+) .*?orport=(\\d+) .*?(?:v3ident=(\\w+) .*?)?([0-9\\.]+):(\\d+) ((\\w{4} ){9}(\\w{4}))", 43);

    }
    catch (Exception e)
    {

      LOG.error("could not initialze class AuthorityKeyCertificate", e);
    }
  }
  


  public static Collection<Router> getAuthorityRouters()
  {
    if (parsedAuthorityRouters == null)
    {
      parsedAuthorityRouters = parseAuthorityRouters();
    }
    
    return parsedAuthorityRouters;
  }
  




  private static Collection<Router> parseAuthorityRouters()
  {
    Collection<Router> result = new ArrayList();
    
    for (String singleRawData : RAW_DATA) {
      try
      {
        Matcher m = pattern.matcher(singleRawData);
        if (m.find())
        {
          String nickname = m.group(1);
          int orPort = Integer.parseInt(m.group(2));
          String v3IdentStr = m.group(3);
          
          Fingerprint v3Ident = v3IdentStr == null ? null : new FingerprintImpl(DatatypeConverter.parseHexBinary(v3IdentStr));
          TcpipNetAddress netAddress = new TcpipNetAddress(m.group(4) + ":0");
          int dirPort = Integer.parseInt(m.group(5));
          String fingerprintStr = m.group(6).replaceAll(" ", "");
          Fingerprint fingerprint = new FingerprintImpl(DatatypeConverter.parseHexBinary(fingerprintStr));
          


          Router router = new RouterImpl(nickname, InetAddress.getByAddress(netAddress.getIpaddress()), orPort, dirPort, v3Ident, fingerprint);
          



          result.add(router);
        } else {
          LOG.warn("Did not match to pattern: \"" + singleRawData + "\"");
        }
      } catch (Exception e) {
        LOG.info("problem while parsing data, skip: " + singleRawData, e);
      }
    }
    

    return result;
  }
  


  public static Collection<String> getAuthorityIpAndPorts()
  {
    Collection<String> result = new ArrayList();
    

    Collection<Router> authorityRouters = getAuthorityRouters();
    for (Router router : authorityRouters)
    {
      String ipAndPort = router.getAddress().getHostAddress() + ":" + router.getDirPort();
      result.add(ipAndPort);
    }
    
    return result;
  }
  


  static Collection<Fingerprint> getAuthorityDirIdentityKeyDigests()
  {
    Collection<Fingerprint> result = new ArrayList();
    

    Collection<Router> authorityRouters = getAuthorityRouters();
    for (Router router : authorityRouters) {
      Fingerprint v3Ident = router.getV3Ident();
      if (v3Ident != null) {
        result.add(router.getV3Ident());
      }
    }
    
    return result;
  }
  
  public AuthorityServers() {}
}
