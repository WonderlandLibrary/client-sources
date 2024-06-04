package org.silvertunnel_ng.netlib.layer.tor.directory;

import java.util.HashMap;
import java.util.Map;







































public enum RouterDescriptorFormatKeys
{
  ROUTER_INFO("router", Integer.valueOf(1), Integer.valueOf(1)), 
  













  BANDWIDTH("bandwidth", Integer.valueOf(1), Integer.valueOf(1)), 
  








  PLATFORM("platform", Integer.valueOf(0), Integer.valueOf(1)), 
  







  PUBLISHED("published", Integer.valueOf(1), Integer.valueOf(1)), 
  









  FINGERPRINT("fingerprint", Integer.valueOf(0), Integer.valueOf(1)), 
  







  HIBERNATING("hibernating", Integer.valueOf(0), Integer.valueOf(1)), 
  






  UPTIME("uptime", Integer.valueOf(0), Integer.valueOf(1)), 
  








  ONION_KEY("onion-key", Integer.valueOf(1), Integer.valueOf(1)), 
  










  NTOR_ONION_KEY("ntor-onion-key", Integer.valueOf(0), Integer.valueOf(1)), 
  






  SIGNING_KEY("signing-key", Integer.valueOf(1), Integer.valueOf(1)), 
  











  ACCEPT("accept", Integer.valueOf(0), Integer.valueOf(Integer.MAX_VALUE)), 
  











  REJECT("reject", Integer.valueOf(0), Integer.valueOf(Integer.MAX_VALUE)), 
  








  IPV6_POLICY("ipv6-policy", Integer.valueOf(0), Integer.valueOf(1)), 
  










  ROUTER_SIGNATURE("router-signature", Integer.valueOf(1), Integer.valueOf(1)), 
  







  CONTACT("contact", Integer.valueOf(0), Integer.valueOf(1)), 
  












  FAMILY("family", Integer.valueOf(0), Integer.valueOf(1)), 
  







  CACHES_EXTRA_INFO("caches-extra-info", Integer.valueOf(0), Integer.valueOf(1)), 
  









  EXTRA_INFO_DIGEST("extra-info-digest", Integer.valueOf(0), Integer.valueOf(1)), 
  








  HIDDEN_SERVICE_DIR("hidden-service-dir", Integer.valueOf(0), Integer.valueOf(1)), 
  










  PROTOCOLS("protocols", Integer.valueOf(0), Integer.valueOf(1)), 
  








  ALLOW_SINGLE_HOP_EXITS("allow-single-hop-exits", Integer.valueOf(0), Integer.valueOf(1)), 
  





















  OR_ADDRESS("or-address", Integer.valueOf(0), Integer.valueOf(Integer.MAX_VALUE));
  



  private String internalValue;
  


  private Integer min;
  

  private Integer max;
  

  private static Map<RouterDescriptorFormatKeys, Integer> keysToFind;
  


  private RouterDescriptorFormatKeys(String value, Integer rangeMin, Integer rangeMax)
  {
    internalValue = value;
    min = rangeMin;
    max = rangeMax;
  }
  



  public String getValue()
  {
    return internalValue;
  }
  



  public int getMin()
  {
    return min.intValue();
  }
  



  public int getMax()
  {
    return max.intValue();
  }
  



  static
  {
    keysToFind = new HashMap();
    keysToFind.put(ROUTER_INFO, Integer.valueOf(ROUTER_INFO.getMax()));
    keysToFind.put(PLATFORM, Integer.valueOf(PLATFORM.getMax()));
    keysToFind.put(PROTOCOLS, Integer.valueOf(PROTOCOLS.getMax()));
    keysToFind.put(PUBLISHED, Integer.valueOf(PUBLISHED.getMax()));
    keysToFind.put(FINGERPRINT, Integer.valueOf(FINGERPRINT.getMax()));
    keysToFind.put(UPTIME, Integer.valueOf(UPTIME.getMax()));
    keysToFind.put(BANDWIDTH, Integer.valueOf(BANDWIDTH.getMax()));
    keysToFind.put(EXTRA_INFO_DIGEST, Integer.valueOf(EXTRA_INFO_DIGEST.getMax()));
    keysToFind.put(ONION_KEY, Integer.valueOf(ONION_KEY.getMax()));
    keysToFind.put(NTOR_ONION_KEY, Integer.valueOf(NTOR_ONION_KEY.getMax()));
    keysToFind.put(SIGNING_KEY, Integer.valueOf(SIGNING_KEY.getMax()));
    keysToFind.put(HIDDEN_SERVICE_DIR, Integer.valueOf(HIDDEN_SERVICE_DIR.getMax()));
    keysToFind.put(HIBERNATING, Integer.valueOf(HIBERNATING.getMax()));
    keysToFind.put(REJECT, Integer.valueOf(REJECT.getMax()));
    keysToFind.put(ACCEPT, Integer.valueOf(ACCEPT.getMax()));
    keysToFind.put(CONTACT, Integer.valueOf(CONTACT.getMax()));
    keysToFind.put(CACHES_EXTRA_INFO, Integer.valueOf(CACHES_EXTRA_INFO.getMax()));
    keysToFind.put(ALLOW_SINGLE_HOP_EXITS, Integer.valueOf(ALLOW_SINGLE_HOP_EXITS.getMax()));
    keysToFind.put(OR_ADDRESS, Integer.valueOf(OR_ADDRESS.getMax()));
    keysToFind.put(FAMILY, Integer.valueOf(FAMILY.getMax()));
    keysToFind.put(IPV6_POLICY, Integer.valueOf(IPV6_POLICY.getMax()));
    keysToFind.put(ROUTER_SIGNATURE, Integer.valueOf(ROUTER_SIGNATURE.getMax()));
  }
  


  public static Map<RouterDescriptorFormatKeys, Integer> getAllKeysAsMap()
  {
    return new HashMap(keysToFind);
  }
}
