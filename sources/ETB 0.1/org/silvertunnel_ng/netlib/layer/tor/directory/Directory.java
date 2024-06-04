package org.silvertunnel_ng.netlib.layer.tor.directory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipException;
import org.apache.http.conn.util.InetAddressUtils;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.util.IpNetAddress;
import org.silvertunnel_ng.netlib.api.util.TcpipNetAddress;
import org.silvertunnel_ng.netlib.layer.control.ControlNetLayer;
import org.silvertunnel_ng.netlib.layer.control.ControlParameters;
import org.silvertunnel_ng.netlib.layer.tor.api.Fingerprint;
import org.silvertunnel_ng.netlib.layer.tor.api.Router;
import org.silvertunnel_ng.netlib.layer.tor.api.TorNetLayerStatus;
import org.silvertunnel_ng.netlib.layer.tor.common.TCPStreamProperties;
import org.silvertunnel_ng.netlib.layer.tor.common.TorConfig;
import org.silvertunnel_ng.netlib.layer.tor.util.NetLayerStatusAdmin;
import org.silvertunnel_ng.netlib.layer.tor.util.Parsing;
import org.silvertunnel_ng.netlib.layer.tor.util.TorException;
import org.silvertunnel_ng.netlib.tool.ConvenientStreamReader;
import org.silvertunnel_ng.netlib.tool.ConvenientStreamWriter;
import org.silvertunnel_ng.netlib.tool.SimpleHttpClient;
import org.silvertunnel_ng.netlib.tool.SimpleHttpClientCompressed;
import org.silvertunnel_ng.netlib.util.StringStorage;
import org.silvertunnel_ng.netlib.util.TempfileStringStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;






















































public final class Directory
{
  private static final Logger LOG = LoggerFactory.getLogger(Directory.class);
  



  public static final int RETRIES_ON_RECURSIVE_ROUTE_BUILD = 10;
  



  private static final String STORAGEKEY_AUTHORITY_KEY_CERTIFICATES_TXT = "authority-key-certificates.txt";
  



  private static final String STORAGEKEY_DIRECTORY_CACHED_CONSENSUS_TXT = "directory-cached-consensus.txt";
  



  private static final String DIRECTORY_CACHED_ROUTER_DESCRIPTORS = "directory-router-descriptors.cache";
  



  private final StringStorage stringStorage;
  


  private NetLayer lowerDirConnectionNetLayer;
  


  private final Map<Fingerprint, Router> allFingerprintsRouters = Collections.synchronizedMap(new HashMap());
  


  private DirectoryConsensus directoryConsensus;
  


  private GuardList guardList;
  


  private int numOfRunningRoutersInDirectoryConsensus = 0;
  





  private Map<Fingerprint, Router> validRoutersByFingerprint = new HashMap();
  


  private final Map<String, HashSet<Fingerprint>> addressNeighbours;
  


  private final Map<String, HashSet<Fingerprint>> countryNeighbours;
  


  private final HashSet<Fingerprint> excludedNodesByConfig;
  


  private final SecureRandom rnd;
  


  private volatile boolean updateRunning = false;
  
  private AuthorityKeyCertificates authorityKeyCertificates;
  
  private final NetLayerStatusAdmin statusAdmin;
  
  private static final long ONE_DAY_IN_MS = 86400000L;
  
  private static final Pattern IPCLASSC_PATTERN = Parsing.compileRegexPattern("(.*)\\.");
  
  private static final int MIN_NUM_OF_DIRS = 5;
  
  private static final int MIN_NUM_OF_CACHE_DIRS = 5;
  
  public GuardList getGuardList()
  {
    return guardList;
  }
  





  public Directory(StringStorage stringStorage, NetLayer lowerDirConnectionNetLayer, NetLayerStatusAdmin statusAdmin)
  {
    this.stringStorage = stringStorage;
    this.lowerDirConnectionNetLayer = lowerDirConnectionNetLayer;
    this.statusAdmin = statusAdmin;
    

    ControlParameters cp = ControlParameters.createTypicalFileTransferParameters();
    cp.setConnectTimeoutMillis(TorConfig.getDirConnectTimeout());
    cp.setOverallTimeoutMillis(TorConfig.getDirOverallTimeout());
    cp.setInputMaxBytes(52428800L);
    cp.setThroughputTimeframeMinBytes(15360L);
    cp.setThroughputTimeframeMillis(15000L);
    this.lowerDirConnectionNetLayer = new ControlNetLayer(lowerDirConnectionNetLayer, cp);
    

    addressNeighbours = new HashMap();
    countryNeighbours = new HashMap();
    rnd = new SecureRandom();
    excludedNodesByConfig = new HashSet(TorConfig.getAvoidedNodeFingerprints());
    guardList = new GuardList(this);
  }
  





  private void addToNeighbours(Router r)
  {
    String ipClassCString = Parsing.parseStringByRE(r.getAddress().getHostAddress(), IPCLASSC_PATTERN, "");
    

    HashSet<Fingerprint> neighbours = (HashSet)addressNeighbours.get(ipClassCString);
    if (neighbours == null)
    {
      neighbours = new HashSet();
      addressNeighbours.put(ipClassCString, neighbours);
    }
    neighbours.add(r.getFingerprint());
    

    neighbours = (HashSet)countryNeighbours.get(r.getCountryCode());
    if (neighbours == null)
    {
      neighbours = new HashSet();
      countryNeighbours.put(r.getCountryCode(), neighbours);
    }
    neighbours.add(r.getFingerprint());
  }
  


  public boolean isDirectoryReady()
  {
    if (numOfRunningRoutersInDirectoryConsensus > 0) {
      long minDescriptors = Math.max(Math.round(TorConfig.getMinDescriptorsPercentage() * numOfRunningRoutersInDirectoryConsensus), 
        TorConfig.getMinDescriptors());
      return validRoutersByFingerprint.size() > Math.max(minDescriptors, TorConfig.getRouteMinLength());
    }
    
    return false;
  }
  



  private Collection<Router> getDirRouters()
  {
    Collection<Router> cacheDirs;
    


    Collection<Router> authorityDirs;
    

    synchronized (allFingerprintsRouters) {
      cacheDirs = new ArrayList(allFingerprintsRouters.size());
      authorityDirs = new ArrayList();
      for (Router r : allFingerprintsRouters.values()) {
        if ((TorConfig.isCountryAllowed(r.getCountryCode())) && (r.isValid())) {
          if (r.isDirv2Authority())
          {
            authorityDirs.add(r);
          } else if (r.isDirv2V2dir())
          {
            cacheDirs.add(r);
          }
        }
      }
    }
    

    if (cacheDirs.size() >= 5) {
      LOG.debug("using non-authorities");
      return cacheDirs;
    }
    

    if (authorityDirs.size() + cacheDirs.size() >= 5) {
      LOG.debug("using authorities");
      Collection<Router> result = cacheDirs;
      result.addAll(authorityDirs);
      return result;
    }
    LOG.debug("using hard-coded authorities");
    
    return AuthorityServers.getAuthorityRouters();
  }
  









  public int refreshListOfServers()
  {
    synchronized (this) {
      if (updateRunning) {
        LOG.debug("Directory.refreshListOfServers: update already running...");
        return 0;
      }
      updateRunning = true;
      try
      {
        updateNetworkStatusNew();
        
        if (isDirectoryReady()) {
          updateRunning = false;
          i = 3;
          






          updateRunning = false;return i;
        }
        int i = 0;
        




        updateRunning = false;return i;
      }
      catch (Exception e)
      {
        LOG.warn("Directory.refreshListOfServers", e);
        int j = 0;
        

        updateRunning = false;return j; } finally { updateRunning = false;
      }
    }
  }
  
  private final int MIN_LENGTH_OF_CONSENSUS_STR = 100;
  





  private void updateDirectoryConsensus()
  {
    statusAdmin.updateStatus(TorNetLayerStatus.CONSENSUS_LOADING);
    

    Date now = new Date();
    if ((directoryConsensus != null) && (!directoryConsensus.needsToBeRefreshed(now))) {
      LOG.debug("no consensus update necessary ...");
    } else {
      AuthorityKeyCertificates authorityKeyCertificates = getAuthorityKeyCertificates();
      



      LOG.debug("consensus first initialization attempt: try to use document from local cache ...");
      DirectoryConsensus newDirectoryConsensus = null;
      if ((directoryConsensus == null) || (directoryConsensus.getFingerprintsNetworkStatusDescriptors().size() == 0))
      {
        String newDirectoryConsensusStr = stringStorage.get("directory-cached-consensus.txt");
        if ((newDirectoryConsensusStr != null) && (newDirectoryConsensusStr.length() > 100)) {
          try {
            newDirectoryConsensus = new DirectoryConsensus(newDirectoryConsensusStr, authorityKeyCertificates, now);
            if ((newDirectoryConsensus == null) || (!newDirectoryConsensus.isValid(now)))
            {
              newDirectoryConsensus = null;
              LOG.debug("consensus from local cache (is too small and) could not be used");
            } else {
              LOG.debug("use consensus from local cache");
            }
          } catch (TorException e) {
            newDirectoryConsensus = null;
            LOG.debug("consensus from local cache is not valid (e.g. too old) and could not be used");
          } catch (Exception e) {
            newDirectoryConsensus = null;
            LOG.debug("error while loading consensus from local cache: {}", e, e);
          }
        } else {
          newDirectoryConsensus = null;
          LOG.debug("consensus from local cache (is null or invalid and) could not be used");
        }
      }
      



      LOG.debug("load consensus from Tor network");
      if (newDirectoryConsensus == null)
      {
        List<Router> dirRouters = new ArrayList(getDirRouters());
        

        while (dirRouters.size() > 0) {
          int index = rnd.nextInt(dirRouters.size());
          Router dirRouter = (Router)dirRouters.get(index);
          if (LOG.isDebugEnabled()) {
            LOG.debug("Directory.updateNetworkStatusNew: Randomly chosen dirRouter to fetch consensus document: " + dirRouter
              .getFingerprint() + " (" + dirRouter
              .getNickname() + ")");
          }
          try
          {
            String path = "/tor/status-vote/current/consensus";
            String newDirectoryConsensusStr;
            try
            {
              newDirectoryConsensusStr = SimpleHttpClientCompressed.getInstance().get(lowerDirConnectionNetLayer, dirRouter.getDirAddress(), "/tor/status-vote/current/consensus");
            } catch (ZipException e) { String newDirectoryConsensusStr;
              LOG.debug("got ZipException while downloading DirectoryConsensus trying to fetch it uncompressed.");
              newDirectoryConsensusStr = SimpleHttpClient.getInstance().get(lowerDirConnectionNetLayer, dirRouter.getDirAddress(), "/tor/status-vote/current/consensus");
            }
            

            newDirectoryConsensus = new DirectoryConsensus(newDirectoryConsensusStr, authorityKeyCertificates, now);
            if (!newDirectoryConsensus.needsToBeRefreshed(now))
            {
              LOG.debug("use new consensus");
              

              stringStorage.put("directory-cached-consensus.txt", newDirectoryConsensusStr);
              break;
            }
            newDirectoryConsensus = null;
          } catch (Exception e) {
            LOG.warn("Directory.updateNetworkStatusNew Exception", e);
            dirRouters.remove(index);
            newDirectoryConsensus = null;
          }
        }
      }
      

      if (newDirectoryConsensus != null) {
        directoryConsensus = newDirectoryConsensus;
      }
    }
    
    if (directoryConsensus == null) {
      LOG.error("no old or new directory consensus available");
      return;
    }
  }
  






  private void updateRouterList()
    throws TorException
  {
    statusAdmin.updateStatus(TorNetLayerStatus.ROUTER_DESCRIPTORS_LOADING);
    if (directoryConsensus != null)
    {
      fetchDescriptors(allFingerprintsRouters, directoryConsensus);
      


      Map<Fingerprint, Router> newValidRoutersByfingerprint = new HashMap();
      Map<Fingerprint, Router> newExitnodeRouters = new HashMap();
      Map<Fingerprint, Router> newFastRouters = new HashMap();
      Map<Fingerprint, Router> newGuardRouters = new HashMap();
      Map<Fingerprint, Router> newStableRouters = new HashMap();
      Map<Fingerprint, Router> newStableAndFastRouters = new HashMap();
      int newNumOfRunningRoutersInDirectoryConsensus = 0;
      for (RouterStatusDescription networkStatusDescription : directoryConsensus.getFingerprintsNetworkStatusDescriptors().values())
      {
        Fingerprint fingerprint = networkStatusDescription.getFingerprint();
        Router r = (Router)allFingerprintsRouters.get(fingerprint);
        if ((r != null) && (r.isValid()))
        {
          r.updateServerStatus(networkStatusDescription);
          newValidRoutersByfingerprint.put(fingerprint, r);
          addToNeighbours(r);
          if ((r.isDirv2Exit()) || (r.isExitNode())) {
            newExitnodeRouters.put(fingerprint, r);
          }
          if (r.isDirv2Fast()) {
            newFastRouters.put(fingerprint, r);
          }
          if (r.isDirv2Guard()) {
            newGuardRouters.put(fingerprint, r);
          }
          if (r.isDirv2Stable()) {
            newStableRouters.put(fingerprint, r);
          }
          if ((r.isDirv2Fast()) && (r.isDirv2Stable())) {
            newStableAndFastRouters.put(fingerprint, r);
          }
        }
        if (networkStatusDescription.getRouterFlags().isRunning()) {
          newNumOfRunningRoutersInDirectoryConsensus++;
        }
      }
      validRoutersByFingerprint = newValidRoutersByfingerprint;
      
      numOfRunningRoutersInDirectoryConsensus = newNumOfRunningRoutersInDirectoryConsensus;
      
      if (LOG.isDebugEnabled()) {
        LOG.debug("updated torServers, new size=" + validRoutersByFingerprint.size());
        LOG.debug("number of exit routers : " + newExitnodeRouters.size());
        LOG.debug("number of fast routers : " + newFastRouters.size());
        LOG.debug("number of stable routers : " + newStableRouters.size());
        LOG.debug("number of stable&fast routers : " + newStableAndFastRouters.size());
        LOG.debug("number of guard routers : " + newGuardRouters.size());
      }
      try
      {
        long startWriteCache = System.currentTimeMillis();
        
        FileOutputStream fileOutputStream = new FileOutputStream(TempfileStringStorage.getTempfileFile("directory-router-descriptors.cache"));
        ConvenientStreamWriter convenientStreamWriter = new ConvenientStreamWriter(fileOutputStream);
        convenientStreamWriter.writeInt(validRoutersByFingerprint.size());
        for (Router router : validRoutersByFingerprint.values()) {
          router.save(convenientStreamWriter);
        }
        fileOutputStream.close();
        LOG.debug("wrote router descriptors to local cache in {} ms", Long.valueOf(System.currentTimeMillis() - startWriteCache));
      } catch (Exception exception) {
        LOG.warn("Could not cache routers due to exception {}", exception, exception);
      }
    }
  }
  




  private synchronized void updateNetworkStatusNew()
    throws TorException
  {
    updateDirectoryConsensus();
    updateRouterList();
  }
  
  private final int MIN_LENGTH_OF_AUTHORITY_KEY_CERTS_STR = 100;
  
  private AuthorityKeyCertificates getAuthorityKeyCertificates()
  {
    Date now = new Date();
    Date minValidUntil = new Date(now.getTime() + 86400000L);
    
    if (authorityKeyCertificates == null)
    {

      LOG.debug("getAuthorityKeyCertificates(): try to load from local cache ...");
      String authorityKeyCertificatesStr = stringStorage.get("authority-key-certificates.txt");
      if ((authorityKeyCertificatesStr != null) && (authorityKeyCertificatesStr.length() > 100)) {
        try
        {
          AuthorityKeyCertificates newAuthorityKeyCertificates = new AuthorityKeyCertificates(authorityKeyCertificatesStr, minValidUntil);
          


          if (newAuthorityKeyCertificates.isValid(minValidUntil)) {
            LOG.debug("getAuthorityKeyCertificates(): successfully loaded from local cache");
            authorityKeyCertificates = newAuthorityKeyCertificates;
            return authorityKeyCertificates;
          }
          

          LOG.debug("getAuthorityKeyCertificates(): loaded from local cache - but not valid: try (re)load from remote site now");
        }
        catch (TorException e)
        {
          LOG.warn("getAuthorityKeyCertificates(): could not parse from local cache: try (re)load from remote site now", e);
        }
      }
      LOG.debug("getAuthorityKeyCertificates(): no data in cache: try (re)load from remote site now");
    }
    

    if ((authorityKeyCertificates == null) || (!authorityKeyCertificates.isValid(minValidUntil)))
    {
      LOG.debug("getAuthorityKeyCertificates(): load and parse authorityKeyCertificates...");
      List<String> authServerIpAndPorts = new ArrayList(AuthorityServers.getAuthorityIpAndPorts());
      Collections.shuffle(authServerIpAndPorts);
      String httpResponse = null;
      for (String authServerIpAndPort : authServerIpAndPorts) {
        try
        {
          TcpipNetAddress hostAndPort = new TcpipNetAddress(authServerIpAndPort);
          String path = "/tor/keys/all";
          try {
            httpResponse = SimpleHttpClientCompressed.getInstance().get(lowerDirConnectionNetLayer, hostAndPort, "/tor/keys/all");
          } catch (ZipException e) {
            LOG.debug("got ZipException trying to get data uncompressed");
            httpResponse = SimpleHttpClient.getInstance().get(lowerDirConnectionNetLayer, hostAndPort, "/tor/keys/all");
          }
          
          AuthorityKeyCertificates newAuthorityKeyCertificates = new AuthorityKeyCertificates(httpResponse, minValidUntil);
          

          if (newAuthorityKeyCertificates.isValid(minValidUntil)) {
            LOG.debug("getAuthorityKeyCertificates(): successfully loaded from {}", authServerIpAndPort);
            
            stringStorage.put("authority-key-certificates.txt", httpResponse);
            
            authorityKeyCertificates = newAuthorityKeyCertificates;
            return authorityKeyCertificates;
          }
          LOG.debug("getAuthorityKeyCertificates(): loaded from {} - but not valid: try next", authServerIpAndPort);
        }
        catch (TorException e) {
          LOG.warn("getAuthorityKeyCertificates(): could not parse from " + authServerIpAndPort + " result=" + httpResponse + ", try next", e);
        }
        catch (Exception e) {
          if (LOG.isDebugEnabled()) {
            LOG.debug("getAuthorityKeyCertificates(): error while loading from {}, try next", authServerIpAndPort, e);
          }
        }
      }
      LOG.error("getAuthorityKeyCertificates(): could NOT load and parse authorityKeyCertificates");
    }
    

    return authorityKeyCertificates;
  }
  



  private static final Pattern ROUTER_DESCRIPTORS_PATTERN = Pattern.compile("^(router.*?END SIGNATURE-----)", 43);
  


  private static final int ALL_DESCRIPTORS_STR_MIN_LEN = 1000;
  


  private static final int THRESHOLD_TO_LOAD_SINGE_ROUTER_DESCRITPTORS = 96;
  


  protected Map<Fingerprint, Router> parseRouterDescriptors(String routerDescriptors)
  {
    long timeStart = System.currentTimeMillis();
    Map<Fingerprint, Router> result = new HashMap();
    
    Matcher m = ROUTER_DESCRIPTORS_PATTERN.matcher(routerDescriptors);
    
    ExecutorService executor = Executors.newFixedThreadPool(5);
    
    Collection<RouterParserCallable> allTasks = new ArrayList();
    
    while (m.find()) {
      allTasks.add(new RouterParserCallable(m.group(1)));
    }
    List<Future<Router>> results = null;
    try {
      results = executor.invokeAll(allTasks);
    } catch (InterruptedException exception) {
      LOG.warn("error while parsing the router descriptors in parallel", exception);
    }
    if ((results != null) && (!results.isEmpty())) {
      for (Future<Router> item : results) {
        Router router = null;
        try {
          router = (Router)item.get();
        } catch (InterruptedException exception) {
          LOG.warn("error while parsing the router descriptors in parallel", exception);
        } catch (ExecutionException exception) {
          LOG.warn("error while parsing the router descriptors in parallel", exception);
        }
        if (router != null) {
          result.put(router.getFingerprint(), router);
        }
      }
    }
    if (LOG.isDebugEnabled()) {
      LOG.debug("parseRouterDescriptors took " + (System.currentTimeMillis() - timeStart) + " ms");
    }
    return result;
  }
  















  private void fetchDescriptors(Map<Fingerprint, Router> fingerprintsRouters, DirectoryConsensus directoryConsensus)
    throws TorException
  {
    Set<Fingerprint> fingerprintsOfRoutersToLoad = new HashSet();
    
    for (RouterStatusDescription networkStatusDescription : directoryConsensus.getFingerprintsNetworkStatusDescriptors().values())
    {
      Router r = (Router)fingerprintsRouters.get(networkStatusDescription.getFingerprint());
      if ((r == null) || (!r.isValid()))
      {
        fingerprintsOfRoutersToLoad.add(networkStatusDescription.getFingerprint());
      }
    }
    


    Set<Fingerprint> fingerprintsOfRoutersToLoadCopy;
    


    if (fingerprintsRouters.size() == 0) {
      try
      {
        long startLoadCached = System.currentTimeMillis();
        
        FileInputStream fileInputStream = new FileInputStream(TempfileStringStorage.getTempfileFile("directory-router-descriptors.cache"));
        ConvenientStreamReader convenientStreamReader = new ConvenientStreamReader(fileInputStream);
        int count = convenientStreamReader.readInt();
        Map<Fingerprint, Router> parsedServers = new HashMap(count);
        Router router; for (int i = 0; i < count; i++) {
          router = new RouterImpl(convenientStreamReader);
          parsedServers.put(router.getFingerprint(), router);
        }
        fileInputStream.close();
        fingerprintsOfRoutersToLoadCopy = new HashSet(fingerprintsOfRoutersToLoad);
        for (Fingerprint fingerprint : fingerprintsOfRoutersToLoadCopy)
        {
          Router r = (Router)parsedServers.get(fingerprint);
          if ((r != null) && (r.isValid()))
          {
            fingerprintsRouters.put(fingerprint, r);
            fingerprintsOfRoutersToLoad.remove(fingerprint);
          }
        }
        LOG.debug("loaded {} routers from local cache in {} ms", new Object[] {
          Integer.valueOf(fingerprintsRouters.size()), Long.valueOf(System.currentTimeMillis() - startLoadCached) });
      } catch (FileNotFoundException exception) {
        LOG.debug("no cached routers found");
      } catch (Exception exception) {
        LOG.warn("could not load cached routers due to exception {}", exception, exception);
      }
    }
    

    LOG.debug("load {} routers from dir server(s) - start", Integer.valueOf(fingerprintsOfRoutersToLoad.size()));
    int successes = 0;
    if (fingerprintsOfRoutersToLoad.size() <= 96)
    {

      int attempts = fingerprintsOfRoutersToLoad.size();
      LOG.debug("loaded {} of {} missing routers from directory server(s) with multiple requests", Integer.valueOf(successes), Integer.valueOf(attempts));
    }
    else {
      List<Router> dirRouters = new ArrayList(getDirRouters());
      while (dirRouters.size() > 0) {
        int i = rnd.nextInt(dirRouters.size());
        Router directoryServer = (Router)dirRouters.get(i);
        dirRouters.remove(i);
        if (directoryServer.getDirPort() >= 1)
        {


          String allDescriptors = DescriptorFetcher.downloadAllDescriptors(directoryServer, lowerDirConnectionNetLayer);
          

          if ((allDescriptors != null) && (allDescriptors.length() >= 1000)) {
            Map<Fingerprint, Router> parsedServers = parseRouterDescriptors(allDescriptors);
            int attempts = 0;
            for (Fingerprint fingerprint : fingerprintsOfRoutersToLoad)
            {
              Router r = (Router)parsedServers.get(fingerprint);
              attempts++;
              if (r != null)
              {
                fingerprintsRouters.put(fingerprint, r);
                successes++;
              }
            }
            if (!LOG.isDebugEnabled()) break;
            LOG.debug("loaded " + successes + " of " + attempts + " missing routers from directory server \"" + directoryServer
            
              .getNickname() + "\" with single request"); break;
          }
        }
      }
    }
    

    LOG.debug("load routers from dir server(s), loaded {} routers - finished", Integer.valueOf(successes));
  }
  







  public boolean isCompatible(Router[] route, TCPStreamProperties sp, boolean forHiddenService)
    throws TorException
  {
    if (route == null) {
      throw new TorException("received NULL-route");
    }
    if (sp == null) {
      throw new TorException("received NULL-sp");
    }
    if (route[(route.length - 1)] == null) {
      throw new TorException("route contains NULL at position " + (route.length - 1));
    }
    
    if (route.length < 1) {
      return false;
    }
    
    if (route.length < sp.getMinRouteLength()) {
      return false;
    }
    
    if (route.length > sp.getMaxRouteLength()) {
      return false;
    }
    

    Fingerprint[] proposedRoute = sp.getProposedRouteFingerprints();
    if (proposedRoute != null) {
      for (int i = 0; (i < proposedRoute.length) && (i < route.length); i++) {
        if ((proposedRoute[i] != null) && 
          (!route[i].getFingerprint().equals(proposedRoute[i]))) {
          return false;
        }
      }
    }
    

    if ((!forHiddenService) && (sp.isExitPolicyRequired()))
    {
      return route[(route.length - 1)].exitPolicyAccepts(sp.getAddr(), sp.getPort());
    }
    return true;
  }
  







  public Set<Fingerprint> excludeRelatedNodes(Router r)
  {
    HashSet<Fingerprint> excludedServerfingerprints = new HashSet();
    

    if (TorConfig.isRouteUniqueClassC()) {
      HashSet<Fingerprint> myAddressNeighbours = getAddressNeighbours(r.getAddress().getHostAddress());
      if (myAddressNeighbours != null) {
        excludedServerfingerprints.addAll(myAddressNeighbours);
      }
    } else {
      excludedServerfingerprints.add(r.getFingerprint());
    }
    

    if (TorConfig.isRouteUniqueCountry()) {
      HashSet<Fingerprint> myCountryNeighbours = (HashSet)countryNeighbours.get(r.getCountryCode());
      if (myCountryNeighbours != null) {
        excludedServerfingerprints.addAll(myCountryNeighbours);
      }
    }
    
    excludedServerfingerprints.addAll(r.getFamily());
    
    return excludedServerfingerprints;
  }
  












  public Router selectRandomNode(Map<Fingerprint, Router> torRouters, HashSet<Fingerprint> excludedServerFingerprints, float rankingInfluenceIndex, boolean onlyFast, boolean onlyStable)
  {
    Map<Fingerprint, Router> routersToChooseFrom = new HashMap(torRouters);
    Set<Fingerprint> listOfExcludedRouters = new HashSet(excludedServerFingerprints);
    if (onlyFast) {
      for (Router router : routersToChooseFrom.values()) {
        if (!router.isDirv2Fast()) {
          listOfExcludedRouters.add(router.getFingerprint());
        }
      }
    }
    if (onlyStable) {
      for (Router router : routersToChooseFrom.values()) {
        if (!router.isDirv2Stable()) {
          listOfExcludedRouters.add(router.getFingerprint());
        }
      }
    }
    float rankingSum = 0.0F;
    
    listOfExcludedRouters.addAll(excludedNodesByConfig);
    
    Iterator<Router> it = routersToChooseFrom.values().iterator();
    while (it.hasNext()) {
      Router myServer = (Router)it.next();
      if ((!listOfExcludedRouters.contains(myServer.getFingerprint())) && (myServer.isDirv2Running())) {
        rankingSum += myServer.getRefinedRankingIndex(rankingInfluenceIndex);
      }
    }
    
    float serverRandom = rnd.nextFloat() * rankingSum;
    
    it = routersToChooseFrom.values().iterator();
    while (it.hasNext()) {
      Router myServer = (Router)it.next();
      if ((!listOfExcludedRouters.contains(myServer.getFingerprint())) && (myServer.isDirv2Running())) {
        serverRandom -= myServer.getRefinedRankingIndex(rankingInfluenceIndex);
        if (serverRandom <= 0.0F) {
          return myServer;
        }
      }
    }
    return null;
  }
  






  public Router getValidRouterByIpAddressAndOnionPort(IpNetAddress ipNetAddress, int onionPort)
  {
    TcpipNetAddress check = new TcpipNetAddress(ipNetAddress, onionPort);
    return getValidRouterByIpAddressAndOnionPort(check);
  }
  





  public Router getValidRouterByIpAddressAndOnionPort(TcpipNetAddress tcpipNetAddress)
  {
    for (Router router : getValidRoutersByFingerprint().values()) {
      if (router.getOrAddress().equals(tcpipNetAddress))
      {
        return router;
      }
    }
    
    return null;
  }
  





  public Router getValidRouterByIpAddressAndDirPort(TcpipNetAddress tcpipNetAddress)
  {
    for (Router router : getValidRoutersByFingerprint().values()) {
      if (router.getDirAddress().equals(tcpipNetAddress))
      {
        return router;
      }
    }
    
    return null;
  }
  


  public Router[] getValidHiddenDirectoryServersOrderedByFingerprint()
  {
    List<Router> routersList;
    

    synchronized (allFingerprintsRouters) {
      routersList = new ArrayList(allFingerprintsRouters.values());
    }
    for (Iterator<Router> i = routersList.iterator(); i.hasNext();) {
      Router r = (Router)i.next();
      if ((!r.isDirv2HSDir()) || (r.getDirPort() < 1))
      {


        i.remove();
      }
    }
    

    Router[] routers = (Router[])routersList.toArray(new Router[routersList.size()]);
    

    Object comp = new Comparator()
    {
      public int compare(Router o1, Router o2) {
        return o1.getFingerprint().compareTo(o2.getFingerprint());
      }
    };
    Arrays.sort(routers, (Comparator)comp);
    
    return routers;
  }
  







  public Collection<Router> getThreeHiddenDirectoryServersWithFingerprintGreaterThan(Fingerprint f)
  {
    Router[] routers = getValidHiddenDirectoryServersOrderedByFingerprint();
    
    int REQUESTED_NUM_OF_ROUTERS = 3;
    int numOfRoutersToFind = Math.min(3, routers.length);
    Collection<Router> result = new ArrayList(numOfRoutersToFind);
    

    boolean takeNextRouters = false;
    for (int i = 0; i < 2 * routers.length; i++) {
      Router r = routers[(i % routers.length)];
      

      if ((!takeNextRouters) && (r.getFingerprint().compareTo(f) >= 0))
      {
        takeNextRouters = true;
      }
      

      if (takeNextRouters)
      {
        result.add(r);
        numOfRoutersToFind--;
        if (numOfRoutersToFind <= 0) {
          break;
        }
      }
    }
    


    return result;
  }
  



  private HashSet<Fingerprint> getAddressNeighbours(String address)
  {
    return (HashSet)addressNeighbours.get(Parsing.parseStringByRE(address, IPCLASSC_PATTERN, ""));
  }
  



  public void close() {}
  



  void print()
  {
    if (LOG.isDebugEnabled()) {
      for (Router r : validRoutersByFingerprint.values()) {
        LOG.debug(r.toString());
      }
    }
  }
  




  public Map<Fingerprint, Router> getValidRoutersByFingerprint()
  {
    HashMap<Fingerprint, Router> result = new HashMap(validRoutersByFingerprint);
    Iterator<Map.Entry<Fingerprint, Router>> itRouter = result.entrySet().iterator();
    while (itRouter.hasNext()) {
      if (!TorConfig.isCountryAllowed(((Router)((Map.Entry)itRouter.next()).getValue()).getCountryCode())) {
        itRouter.remove();
      }
    }
    return result;
  }
  





  public Router getRouterByFingerprint(Fingerprint fingerprint)
  {
    return (Router)allFingerprintsRouters.get(fingerprint);
  }
  




  public Map<Fingerprint, Router> getValidRoutersByFlags(RouterFlags flags)
  {
    HashMap<Fingerprint, Router> result = new HashMap(validRoutersByFingerprint);
    Iterator<Map.Entry<Fingerprint, Router>> itRouter = result.entrySet().iterator();
    while (itRouter.hasNext()) {
      Router router = (Router)((Map.Entry)itRouter.next()).getValue();
      if (!TorConfig.isCountryAllowed(router.getCountryCode())) {
        itRouter.remove();
      }
      else if (!router.getRouterFlags().match(flags)) {
        itRouter.remove();
      }
    }
    
    LOG.debug("routers found for given flags (" + flags.toString() + ") {}", Integer.valueOf(result.size()));
    return result;
  }
  





  public boolean isDirServer(TCPStreamProperties sp)
  {
    if ((sp.getHostname() != null) && (InetAddressUtils.isIPv4Address(sp.getHostname()))) {
      String[] octets = sp.getHostname().split("\\.");
      byte[] ip = new byte[4];
      ip[0] = ((byte)Integer.parseInt(octets[0]));
      ip[1] = ((byte)Integer.parseInt(octets[1]));
      ip[2] = ((byte)Integer.parseInt(octets[2]));
      ip[3] = ((byte)Integer.parseInt(octets[3]));
      
      Router router = getValidRouterByIpAddressAndDirPort(new TcpipNetAddress(ip, sp.getPort()));
      if ((router != null) && ((router.isDirv2HSDir()) || (router.isDirv2V2dir()))) {
        return true;
      }
    }
    return false;
  }
}
