package org.silvertunnel_ng.netlib.layer.tor.common;

import java.util.HashSet;
import java.util.Set;
import org.silvertunnel_ng.netlib.layer.tor.api.Fingerprint;
import org.silvertunnel_ng.netlib.layer.tor.directory.FingerprintImpl;
import org.silvertunnel_ng.netlib.util.DatatypeConverter;
import org.silvertunnel_ng.netlib.util.SystemPropertiesHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

































































public final class TorConfig
{
  private static final Logger LOG = LoggerFactory.getLogger(TorConfig.class);
  



  private static TorConfig instance;
  



  private boolean useCreateFastCells = true;
  


  public static boolean useCreateFastCells()
  {
    return getInstanceuseCreateFastCells;
  }
  





  public static void setUseCreateFastCells(boolean useIt)
  {
    getInstanceuseCreateFastCells = useIt;
  }
  





  private int startupDelaySeconds = 20;
  



  private static final int[] DEFAULT_LONG_LIVED_PORTS = { 21, 22, 706, 1863, 5050, 5190, 5222, 5223, 6667, 6697, 8300 };
  



  private Set<Integer> longLivedPorts = new HashSet();
  




  public static void addLongLivedPort(int port)
  {
    getInstancelongLivedPorts.add(Integer.valueOf(port));
  }
  




  public static void setLongLivedPorts(Set<Integer> list)
  {
    getInstancelongLivedPorts = list;
  }
  




  public static Set<Integer> getLongLivedPorts()
  {
    return getInstancelongLivedPorts;
  }
  


  private static synchronized TorConfig getInstance()
  {
    if (instance == null) {
      instance = new TorConfig();
    }
    return instance;
  }
  




  public static int getStartupDelay()
  {
    return getInstancestartupDelaySeconds;
  }
  




  public static void setStartupDelay(int delay)
  {
    getInstancestartupDelaySeconds = delay;
  }
  




  private int retriesConnect = 1;
  


  private int reconnectCircuit = 1;
  public static int retriesStreamBuildup = 5;
  







  private static final int MAXIMUM_IDLE_CIRCUITS = 20;
  






  private int minimumIdleCircuits = 3;
  public static final String SYSTEMPROPERTY_TOR_PREFIX = "silvertunnel-ng.tor.";
  public static final String SYSTEMPROPERTY_TOR_MINIMUM_IDLE_CIRCUITS = "silvertunnel-ng.tor.minimumIdleCircuits";
  public static final String SYSTEMPROPERTY_TOR_MINIMUM_ROUTE_LENGTH = "silvertunnel-ng.tor.minimumRouteLength";
  
  public static int getRetriesConnect() {
    return getInstanceretriesConnect;
  }
  







  public static void setRetriesConnect(int retries)
  {
    if (retries <= 0) {
      LOG.warn("setRetriesConnect : wrong value for retriesConnect found!");
      return;
    }
    if (retries > 20) {
      LOG.warn("setRetriesConnect : number of retries could be to high.");
    }
    getInstanceretriesConnect = retries;
  }
  


  public static int getReconnectCircuit()
  {
    return getInstancereconnectCircuit;
  }
  






  public static void setReconnectCircuit(int reconnects)
  {
    if (reconnects <= 0) {
      LOG.warn("setReconnectCircuit : wrong value for reconnectCircuit found!");
      return;
    }
    if (reconnects > 10) {
      LOG.warn("setReconnectCircuit : number of reconnects could be to high.");
    }
    getInstancereconnectCircuit = reconnects;
  }
  


  public static int getMinimumIdleCircuits()
  {
    return getInstanceminimumIdleCircuits;
  }
  

















  public static void setMinimumIdleCircuits(int nrOfCircuits)
  {
    if (nrOfCircuits < 0) {
      LOG.warn("setMinimumIdleCircuits : value should not be lower than 0. setting minimumIdleCircuits to 0!");
      setMinimumIdleCircuits(0);
    }
    if (nrOfCircuits > 20) {
      LOG.warn("setMinimumIdleCircuits : value should not be greater than 20. setting minimumIdleCircuits to 20!");
      
      setMinimumIdleCircuits(20);
    }
    getInstanceminimumIdleCircuits = nrOfCircuits;
  }
  







  public static final String SYSTEMPROPERTY_TOR_MAXIMUM_ROUTE_LENGTH = "silvertunnel-ng.tor.maximumRouteLength";
  





  public static final String SYSTEMPROPERTY_TOR_CACHE_HS_DESCRIPTOR = "silvertunnel-ng.tor.cacheHiddenServiceDescriptor";
  





  public static final String SYSTEMPROPERTY_TOR_MAX_ALLOWED_SETUP_DURATION_MS = "silvertunnel-ng.tor.maxAllowedSetupDurationMs";
  





  public static int numEntryGuards = 3;
  public static boolean allowNonGuardEntry = false;
  
  public static int queueTimeoutCircuit = 10;
  public static int queueTimeoutResolve = 5;
  
  public static int queueTimeoutStreamBuildup = 5;
  





  private int circuitClosesOnFailures = 3;
  




  public static int getCircuitClosesOnFailures()
  {
    return getInstancecircuitClosesOnFailures;
  }
  






  public static void setCircuitClosesOnFailures(int circuitClosesOnFailures)
  {
    getInstancecircuitClosesOnFailures = circuitClosesOnFailures;
  }
  
  public static int circuitsMaximumNumber = 30;
  public static long maxAllowedSetupDurationMs = 10000L;
  



  public static float rankingTransferPerServerUpdate = 0.95F;
  



  private boolean veryAggressiveStreamBuilding = false;
  


  public static boolean isVeryAggressiveStreamBuilding()
  {
    return getInstanceveryAggressiveStreamBuilding;
  }
  


  public static void setVeryAggressiveStreamBuilding(boolean veryAggressiveStreamBuilding)
  {
    getInstanceveryAggressiveStreamBuilding = veryAggressiveStreamBuilding;
  }
  




  private int intervalDirectoryRefresh = 5;
  




  public static int getIntervalDirectoryRefresh()
  {
    return getInstanceintervalDirectoryRefresh;
  }
  


  public static void setIntervalDirectoryRefresh(int intervalDirectoryRefresh)
  {
    getInstanceintervalDirectoryRefresh = intervalDirectoryRefresh;
  }
  



  private long dirConnectTimeout = 10000L;
  



  public static long getDirConnectTimeout()
  {
    return getInstancedirConnectTimeout;
  }
  




  public static void setDirConnectTimeout(long millis)
  {
    getInstancedirConnectTimeout = millis;
  }
  



  private long dirOverallTimeout = 30000L;
  public static final long DIR_MAX_FILETRANSFER_BYTES = 52428800L;
  public static final long DIR_THROUGPUT_TIMEFRAME_MIN_BYTES = 15360L;
  public static final long DIR_THROUGPUT_TIMEFRAME_MILLIS = 15000L;
  public static final int CIRCUIT_ESTABLISHMENT_TIME_IMPACT = 5;
  
  public static long getDirOverallTimeout() {
    return getInstancedirOverallTimeout;
  }
  




  public static void setDirOverallTimeout(long millis)
  {
    getInstancedirOverallTimeout = millis;
  }
  


























  private int streamsPerCircuit = 65535;
  




  public static int getStreamsPerCircuit()
  {
    return getInstancestreamsPerCircuit;
  }
  




  public static void setStreamsPerCircuit(int streams)
  {
    if (streams <= 0) {
      LOG.error("it is not allowed to set the number of streams in a circuit lower than 1!");
    } else if (streams >= 65536) {
      LOG.error("the maximum allowed number of streams per circuit is 2^16 = 65536");
    } else {
      LOG.debug("setting streamsPerCircuit from {} to {}", new Object[] { Integer.valueOf(getInstancestreamsPerCircuit), Integer.valueOf(streams) });
      getInstancestreamsPerCircuit = streams;
    }
  }
  



  public static float rankingIndexEffect = 0.9F;
  






  private static final int MINIMUM_ROUTE_LENGTH = 2;
  





  private static final int MAXIMUM_ROUTE_LENGTH = 8;
  





  private static final int DEFAULT_ROUTE_LENGTH = 3;
  





  private int routeMinLength = 3;
  


  public static int getRouteMinLength()
  {
    return getInstancerouteMinLength;
  }
  

















  public static void setRouteMinLength(int length)
  {
    if (length < 2) {
      LOG.warn("route length has to be at least {}", Integer.valueOf(2));
      return;
    }
    if (length > 8) {
      LOG.warn("route length should not exceed {}", Integer.valueOf(8));
      return;
    }
    if (length > getInstancerouteMaxLength) {
      LOG.info("setRouteMinLength: length ({}) is smaller than current maxlen ({}). Setting maxlen to given value.", new Object[] { Integer.valueOf(length), 
        Integer.valueOf(getInstancerouteMaxLength) });
      getInstancerouteMaxLength = length;
    }
    getInstancerouteMinLength = length;
  }
  















  private int routeMaxLength = 3;
  


  public static int getRouteMaxLength()
  {
    return getInstancerouteMaxLength;
  }
  

















  public static void setRouteMaxLength(int length)
  {
    if (length < 2) {
      LOG.warn("route length has to be at least 2!");
      return;
    }
    if (length > 8) {
      LOG.warn("route length should not exceed 8");
      return;
    }
    if (length < getInstancerouteMinLength) {
      LOG.info("setRouteMaxLength: length (" + length + ") is smaller than current minlen. Setting minlen to given value.");
      getInstancerouteMinLength = length;
    }
    getInstancerouteMaxLength = length;
  }
  




  private double minDescriptorsPercentage = 0.1D;
  
  private Integer minDescriptors;
  
  public static double getMinDescriptorsPercentage()
  {
    return getInstanceminDescriptorsPercentage;
  }
  







  public static void setMinDescriptorsPercentage(double percent)
  {
    if ((percent < 0.0D) || (percent > 100.0D))
    {
      LOG.warn("setMinDescriptorsPercentage: value {} out of range (0.0 - 100.0)", Double.valueOf(percent));
    }
    if (percent == 0.0D) {
      LOG.warn("setMinDescriptorsPercentage: setting this value to 0 is discouraged");
    }
    getInstanceminDescriptorsPercentage = percent;
  }
  










  public static void setMinDescriptors(Integer minDescriptors)
  {
    getInstanceminDescriptors = minDescriptors;
  }
  


  public static int getMinDescriptors()
  {
    if (getInstanceminDescriptors == null) {
      return 10 * getInstancerouteMinLength;
    }
    return getInstanceminDescriptors.intValue();
  }
  



  private boolean routeUniqueClassC = true;
  


  public static boolean isRouteUniqueClassC()
  {
    return getInstancerouteUniqueClassC;
  }
  




  public static void setRouteUniqueClassC(boolean value)
  {
    getInstancerouteUniqueClassC = value;
  }
  





  private boolean routeUniqueCountry = true;
  


  public static boolean isRouteUniqueCountry()
  {
    return getInstancerouteUniqueCountry;
  }
  





  public static void setRouteUniqueCountry(boolean routeUniqueCountry)
  {
    getInstancerouteUniqueCountry = routeUniqueCountry;
  }
  



  public static int allowModeMultipleCircuits = 3;
  




  private Set<String> avoidedCountries = new HashSet();
  







  public static boolean isCountryAllowed(String countryCode)
  {
    if (getInstanceavoidedCountries.isEmpty()) {
      return true;
    }
    if (getInstanceavoidedCountries.contains(countryCode)) {
      return false;
    }
    return true;
  }
  




  public static synchronized void setCountryAllowed(Set<String> countryCodes)
  {
    getInstanceavoidedCountries = countryCodes;
  }
  






  public static synchronized void setCountryAllowed(String countryCode, boolean allowed)
  {
    if (allowed) {
      getInstanceavoidedCountries.remove(countryCode);
    } else {
      getInstanceavoidedCountries.add(countryCode);
    }
  }
  



  private Set<Fingerprint> avoidedNodeFingerprints = new HashSet();
  
  public static final String TOR_GEOIPCITY_PATH = "/com/maxmind/geoip/GeoIP.dat";
  public static final int TOR_GEOIPCITY_MAX_FILE_SIZE = 2000000;
  
  public static Set<Fingerprint> getAvoidedNodeFingerprints()
  {
    return getInstanceavoidedNodeFingerprints;
  }
  





  public static synchronized void setAvoidedNodeFingerprints(Set<byte[]> fingerprints)
  {
    getInstanceavoidedNodeFingerprints.clear();
    for (byte[] fingerprint : fingerprints)
    {
      getInstanceavoidedNodeFingerprints.add(new FingerprintImpl(fingerprint));
    }
  }
  





  public static synchronized void setAvoidedNodeFingerprintsHex(Set<String> fingerprints)
  {
    getInstanceavoidedNodeFingerprints.clear();
    for (String fingerprint : fingerprints)
    {
      getInstanceavoidedNodeFingerprints.add(new FingerprintImpl(DatatypeConverter.parseHexBinary(fingerprint)));
    }
  }
  





  public static synchronized void addAvoidedNodeFingerprint(byte[] fingerprint)
  {
    getInstanceavoidedNodeFingerprints.add(new FingerprintImpl(fingerprint));
  }
  





  public static synchronized void addAvoidedNodeFingerprint(String hexFingerprint)
  {
    getInstanceavoidedNodeFingerprints.add(new FingerprintImpl(DatatypeConverter.parseHexBinary(hexFingerprint)));
  }
  











  private boolean cacheHiddenServiceDescriptor = true;
  


  public static final int MIN_NUMBER_OF_ROUTERS_IN_CONSENSUS = 50;
  


  public static final long ROUTER_DESCRIPTION_VALID_PERIOD_MS = 86400000L;
  



  public static void reloadConfigFromProperties()
  {
    try
    {
      setMinimumIdleCircuits(SystemPropertiesHelper.getSystemProperty("silvertunnel-ng.tor.minimumIdleCircuits", 
        getInstanceminimumIdleCircuits));
      setRouteMinLength(SystemPropertiesHelper.getSystemProperty("silvertunnel-ng.tor.minimumRouteLength", getRouteMinLength()));
      setRouteMaxLength(SystemPropertiesHelper.getSystemProperty("silvertunnel-ng.tor.maximumRouteLength", getRouteMaxLength()));
      setCacheHiddenServiceDescriptor(SystemPropertiesHelper.getSystemProperty("silvertunnel-ng.tor.cacheHiddenServiceDescriptor", 
        isCacheHiddenServiceDescriptor()));
      maxAllowedSetupDurationMs = SystemPropertiesHelper.getSystemProperty("silvertunnel-ng.tor.maxAllowedSetupDurationMs", (int)maxAllowedSetupDurationMs);
    }
    catch (Exception e) {
      LOG.error("config could not be loaded from properties", e);
    }
  }
  
  static
  {
    reset();
    reloadConfigFromProperties();
  }
  





  public static boolean isCacheHiddenServiceDescriptor()
  {
    return getInstancecacheHiddenServiceDescriptor;
  }
  


  public static void setCacheHiddenServiceDescriptor(boolean cacheHiddenServiceDescriptor)
  {
    getInstancecacheHiddenServiceDescriptor = cacheHiddenServiceDescriptor;
  }
  





  public static boolean isSaveCircuitHistory()
  {
    return getInstancesaveCircuitHistory;
  }
  




  public void setSaveCircuitHistory(boolean saveCircuitHistory)
  {
    getInstancesaveCircuitHistory = saveCircuitHistory;
  }
  





  private boolean saveCircuitHistory = true;
  



  private int parallelCircuitBuilds = 1;
  
  private String tempDirectory;
  
  public static final String FILENAME_PREFIX = "st-";
  
  public static int getParallelCircuitBuilds()
  {
    return getInstanceparallelCircuitBuilds;
  }
  




  public static void setParallelCircuitBuilds(int number)
  {
    if (number < 1) {
      LOG.error("setParallelCircuitBuilds should not be less than 1");
    } else {
      getInstanceparallelCircuitBuilds = number;
    }
  }
  


  public static void reset()
  {
    TorConfig config = getInstance();
    avoidedCountries.clear();
    avoidedNodeFingerprints.clear();
    cacheHiddenServiceDescriptor = true;
    circuitClosesOnFailures = 3;
    minimumIdleCircuits = 3;
    parallelCircuitBuilds = 1;
    routeMaxLength = 3;
    routeMinLength = 3;
    routeUniqueClassC = true;
    routeUniqueCountry = true;
    saveCircuitHistory = true;
    veryAggressiveStreamBuilding = false;
    longLivedPorts.clear();
    for (int tmp : DEFAULT_LONG_LIVED_PORTS) {
      longLivedPorts.add(Integer.valueOf(tmp));
    }
    tempDirectory = System.getProperty("java.io.tmpdir");
  }
  










  public static void setTempDirectory(String directory)
  {
    getInstancetempDirectory = directory;
  }
  




  public static String getTempDirectory()
  {
    return getInstancetempDirectory;
  }
  
  private TorConfig() {}
}
