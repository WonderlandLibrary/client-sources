package org.silvertunnel_ng.netlib.layer.tor.circuit;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.silvertunnel_ng.netlib.layer.tor.api.Fingerprint;
import org.silvertunnel_ng.netlib.layer.tor.api.Router;
import org.silvertunnel_ng.netlib.layer.tor.common.TCPStreamProperties;
import org.silvertunnel_ng.netlib.layer.tor.common.TorConfig;
import org.silvertunnel_ng.netlib.layer.tor.common.TorEventService;
import org.silvertunnel_ng.netlib.layer.tor.directory.Directory;
import org.silvertunnel_ng.netlib.layer.tor.directory.GuardList;
import org.silvertunnel_ng.netlib.layer.tor.directory.RouterFlags;
import org.silvertunnel_ng.netlib.layer.tor.util.NodeType;
import org.silvertunnel_ng.netlib.layer.tor.util.TorException;
import org.silvertunnel_ng.netlib.layer.tor.util.TorServerNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




































public class CircuitAdmin
{
  private static final Logger LOG = LoggerFactory.getLogger(CircuitAdmin.class);
  




  static Map<String, Circuit[]> suitableCircuitsCache = Collections.synchronizedMap(new HashMap());
  



  private static CircuitHistory circuitHistory = new CircuitHistory();
  



  private static Map<Fingerprint, Integer> currentlyUsedNodes = Collections.synchronizedMap(new HashMap());
  
  private static SecureRandom rnd = new SecureRandom();
  




  public CircuitAdmin() {}
  




  static Circuit provideSuitableNewCircuit(TLSConnectionAdmin tlsConnectionAdmin, final Directory dir, final TCPStreamProperties sp, final TorEventService torEventService)
    throws Throwable
  {
    ExecutorService executor = Executors.newCachedThreadPool();
    Collection<Callable<Circuit>> allTasks = new ArrayList();
    for (int i = 0; i < TorConfig.getParallelCircuitBuilds(); i++) {
      Callable<Circuit> callable = new Callable()
      {
        public Circuit call() throws TorServerNotFoundException, ExecutionException
        {
          Circuit result = null;
          for (int retries = 0; retries < TorConfig.getRetriesConnect(); retries++) {
            try {
              result = new Circuit(val$tlsConnectionAdmin, dir, sp, torEventService, CircuitAdmin.circuitHistory);
            }
            catch (InterruptedException e) {
              CircuitAdmin.LOG.debug("got InterruptedException : {}", e.getMessage(), e);
            } catch (TorServerNotFoundException e) {
              if ((e.getNodeType() == NodeType.EXIT) && (e.getFingerprint().equals(sp.getCustomExitpoint()))) {
                CircuitAdmin.LOG.error("the chosen exit node could not be found", e);
                throw e;
              }
              CircuitAdmin.LOG.debug("got TorServerNotFoundException but ignoring it", e);
            }
            catch (TorException e) {
              CircuitAdmin.LOG.debug("got TorException : {}", e.getMessage(), e);
            }
            catch (IOException e) {
              CircuitAdmin.LOG.debug("got IOException : {}", e.getMessage(), e);
            }
            CircuitAdmin.LOG.debug("provideSuitableNewCircuit retry {} from {}", new Object[] { Integer.valueOf(retries + 1), Integer.valueOf(TorConfig.getRetriesConnect()) });
          }
          return result;
        }
      };
      allTasks.add(callable);
    }
    try {
      return (Circuit)executor.invokeAny(allTasks);
    } catch (InterruptedException exception) {
      LOG.debug("got Exception while executing tasks", exception);
    } catch (ExecutionException exception) {
      LOG.debug("got Exception while executing tasks", exception);
      throw exception.getCause();
    }
    return null;
  }
  










  public static Circuit provideSuitableExclusiveCircuit(TLSConnectionAdmin tlsConnectionAdmin, Directory dir, TCPStreamProperties sp, TorEventService torEventService)
    throws Throwable
  {
    try
    {
      for (TLSConnection tls : tlsConnectionAdmin.getConnections()) {
        for (Circuit circuit : tls.getCircuits()) {
          if (circuit.isUnused()) {
            if (sp.getCustomExitpoint() == null) {
              circuit.setUnused(false);
              if (LOG.isDebugEnabled()) {
                LOG.debug("we successfully used an unused Circuit! Id : {}", Integer.valueOf(circuit.getId()));
              }
              return circuit;
            }
            if (circuit.getRelayEarlyCellsRemaining() > 0)
            {
              circuit.extend(sp.getCustomExitpoint());
              circuit.setUnused(false);
              if (LOG.isDebugEnabled()) {
                LOG.debug("we successfully extended and used an unused Circuit! Id : {}", Integer.valueOf(circuit.getId()));
              }
              return circuit;
            }
          }
        }
      }
    } catch (Exception exception) {
      LOG.debug("we got an exception while finding a already established Circuit. using new one.", exception);
    }
    Circuit result = provideSuitableNewCircuit(tlsConnectionAdmin, dir, sp, torEventService);
    result.setUnused(false);
    return result;
  }
  










  public static Circuit[] provideSuitableCircuits(TLSConnectionAdmin tlsConnectionAdmin, Directory dir, TCPStreamProperties sp, TorEventService torEventService, boolean forHiddenService)
    throws Throwable
  {
    LOG.debug("TLSConnectionAdmin.provideSuitableCircuits: called for {}", sp.getHostname());
    

    Circuit[] cachedResults = (Circuit[])suitableCircuitsCache.get(sp.getHostname());
    if (cachedResults != null)
    {
      LOG.debug("return chachedResults");
    }
    

    int numberOfExistingCircuits = 0;
    List<Circuit> allCircs = new ArrayList(10);
    int rankingSum = 0;
    for (TLSConnection tls : tlsConnectionAdmin.getConnections()) {
      for (Circuit circuit : tls.getCircuits()) {
        try {
          numberOfExistingCircuits++;
          if ((circuit.isEstablished()) && 
            (!circuit.isClosed()) && 
            (DirectoryService.isCompatible(dir, circuit, sp, forHiddenService))) {
            allCircs.add(circuit);
            rankingSum += circuit.getRanking();
          }
        } catch (TorException e) {
          LOG.debug("got TorException : {}", e.getMessage(), e);
        }
      }
    }
    



    for (int i = 0; i < allCircs.size() - 1; i++) {
      Circuit c1 = (Circuit)allCircs.get(i);
      int min = i;
      int minRanking = c1.getRanking();
      if (minRanking == 0) {
        minRanking = 1;
      }
      boolean minPointsToAddr = c1.getStreamHistory().contains(sp.getHostname());
      for (int j = i + 1; j < allCircs.size(); j++) {
        Circuit thisCirc = (Circuit)allCircs.get(j);
        int thisRanking = thisCirc.getRanking();
        if (thisRanking == 0) {
          thisRanking = 1;
        }
        boolean thisPointsToAddr = thisCirc.getStreamHistory().contains(sp.getHostname());
        float rankingQuota = thisRanking / minRanking;
        if (((thisPointsToAddr) && (!minPointsToAddr)) || (rnd.nextFloat() > Math.exp(-rankingQuota)))
        {
          min = j;
          minRanking = thisRanking;
        }
      }
      if (min > i) {
        Circuit temp = (Circuit)allCircs.set(i, allCircs.get(min));
        allCircs.set(min, temp);
      }
    }
    

    int returnValues = sp.getConnectRetries();
    if (allCircs.size() < returnValues) {
      returnValues = allCircs.size();
    }
    if ((returnValues == 1) && (numberOfExistingCircuits < TorConfig.circuitsMaximumNumber))
    {

      LOG.debug("TLSConnectionAdmin.provideSuitableCircuits: spawning circuit to {} in background", sp.getHostname());
      Thread spawnInBackground = new NewCircuitThread(tlsConnectionAdmin, dir, sp, torEventService);
      spawnInBackground.setName("CuircuitAdmin.provideSuitableCircuits");
      spawnInBackground.start();
    } else if ((returnValues == 0) && (numberOfExistingCircuits < TorConfig.circuitsMaximumNumber))
    {
      LOG.debug("TLSConnectionAdmin.provideSuitableCircuits: spawning circuit to {}", sp.getHostname());
      Circuit single = provideSuitableNewCircuit(tlsConnectionAdmin, dir, sp, torEventService);
      if (single != null) {
        returnValues = 1;
        allCircs.add(single);
      }
    }
    
    Circuit[] results = new Circuit[returnValues];
    for (int i = 0; i < returnValues; i++) {
      results[i] = ((Circuit)allCircs.get(i));
      if (LOG.isDebugEnabled()) {
        LOG.debug("TLSConnectionAdmin.provideSuitableCircuits: Choose Circuit ranking " + results[i]
          .getRanking() + ":" + results[i].toString());
      }
    }
    

    suitableCircuitsCache.put(sp.getHostname(), results);
    
    return results;
  }
  


















  private static synchronized Router[] createNewRoute(Directory directory, TCPStreamProperties sp, Fingerprint[] proposedRoute, HashSet<Fingerprint> excludedServerFingerprints, Router[] route, int i, int maxIterations)
    throws TorException
  {
    float rankingInfluenceIndex = sp.getRankingInfluenceIndex();
    HashSet<Fingerprint> previousExcludedServerFingerprints = new HashSet();
    
    Map<Fingerprint, Router> validRoutersByFingerprint = directory.getValidRoutersByFingerprint();
    for (Router r : validRoutersByFingerprint.values())
    {
      allowedCircuitsWithNode = (Integer)currentlyUsedNodes.get(r.getFingerprint());
      
      if ((allowedCircuitsWithNode != null) && (allowedCircuitsWithNode.intValue() > TorConfig.allowModeMultipleCircuits)) {
        excludedServerFingerprints.add(r.getFingerprint());
      }
    }
    Integer allowedCircuitsWithNode;
    if ((proposedRoute != null) && (i < proposedRoute.length) && (proposedRoute[i] != null))
    {
      route[i] = ((Router)validRoutersByFingerprint.get(proposedRoute[i]));
      if (route[i] == null) {
        NodeType nodeType = NodeType.MIDDLE;
        if (i == 0) {
          nodeType = NodeType.ENTRY;
        } else if (i == route.length - 1) {
          nodeType = NodeType.EXIT;
        }
        throw new TorServerNotFoundException(proposedRoute[i], i, nodeType);
      }
    }
    else {
      if (i == route.length - 1)
      {


        Object suitableServerFingerprints = new HashMap();
        RouterFlags flags = new RouterFlags();
        if (sp.isFastRoute()) {
          flags.setFast(Boolean.valueOf(true));
        }
        if (sp.isStableRoute()) {
          flags.setStable(Boolean.valueOf(true));
        }
        flags.setExit(Boolean.valueOf(true));
        for (Router r : directory.getValidRoutersByFlags(flags).values())
        {
          if ((r.exitPolicyAccepts(sp.getAddr(), sp.getPort())) && ((sp.isUntrustedExitAllowed()) || (r.isDirv2Exit()))) {
            ((Map)suitableServerFingerprints).put(r.getFingerprint(), r);
          }
        }
        
        HashSet<Fingerprint> x = new HashSet(excludedServerFingerprints);
        

        route[i] = directory.selectRandomNode((Map)suitableServerFingerprints, x, rankingInfluenceIndex, sp
        

          .isFastRoute(), sp
          .isStableRoute());
      }
      else if ((i == 0) && (!sp.isNonGuardEntryAllowed()))
      {
        route[i] = directory.getGuardList().getGuard(excludedServerFingerprints, sp);
      } else {
        route[i] = directory.selectRandomNode(validRoutersByFingerprint, excludedServerFingerprints, rankingInfluenceIndex, sp
          .isFastRoute(), sp.isStableRoute());
      }
      
      if (route[i] == null) {
        return null;
      }
      previousExcludedServerFingerprints.addAll(excludedServerFingerprints);
      excludedServerFingerprints.addAll(directory.excludeRelatedNodes(route[i]));
      

      Integer allowedCircuitsWithNode = (Integer)currentlyUsedNodes.get(route[i].getNickname());
      int numberOfNodeOccurances; int numberOfNodeOccurances; if (allowedCircuitsWithNode != null) {
        numberOfNodeOccurances = allowedCircuitsWithNode.intValue() + 1;
      } else {
        numberOfNodeOccurances = 0;
      }
      currentlyUsedNodes.put(route[i].getFingerprint(), Integer.valueOf(numberOfNodeOccurances));
    }
    
    if (i > 0) {
      Router[] aRoute = createNewRoute(directory, sp, proposedRoute, excludedServerFingerprints, route, i - 1, -1);
      if (aRoute == null)
      {
        previousExcludedServerFingerprints.add(route[(i - 1)].getFingerprint());
        if (maxIterations > -1) {
          maxIterations = Math.min(maxIterations, 10) - 1;
        } else {
          maxIterations = 9;
        }
        if (maxIterations < 0) {
          return null;
        }
        route = createNewRoute(directory, sp, proposedRoute, previousExcludedServerFingerprints, route, i, maxIterations);

      }
      else
      {
        route = aRoute;
      }
    }
    
    return route;
  }
  









  public static Router[] createNewRoute(Directory directory, TCPStreamProperties sp)
    throws TorException
  {
    if (directory.getValidRoutersByFingerprint().size() < 1)
    {
      throw new TorException("directory is empty");
    }
    
    int len;
    
    int len;
    if (sp.getMinRouteLength() == sp.getMinRouteLength())
    {
      len = sp.getMaxRouteLength();
    }
    else {
      len = sp.getMinRouteLength() + rnd.nextInt(sp.getMaxRouteLength() - sp.getMinRouteLength() + 1);
    }
    

    Router[] route = new Router[len];
    
    HashSet<Fingerprint> excludedServerFingerprints = new HashSet();
    

    Fingerprint[] proposedRoute = sp.getProposedRouteFingerprints();
    if (proposedRoute != null) {
      for (int j = 0; j < proposedRoute.length; j++) {
        if (proposedRoute[j] != null) {
          Router s = (Router)directory.getValidRoutersByFingerprint().get(proposedRoute[j]);
          if (s != null) {
            excludedServerFingerprints.addAll(directory.excludeRelatedNodes(s));
          }
        }
      }
    }
    Router[] result = createNewRoute(directory, sp, proposedRoute, excludedServerFingerprints, route, len - 1, -1);
    







    if (result == null) {
      LOG.warn("result new route is null");
    }
    else if (LOG.isDebugEnabled()) {
      StringBuffer sb = new StringBuffer(50);
      for (Router server : result) {
        sb.append("server(or=" + server.getHostname() + ":" + server
          .getOrPort() + "(" + server.getNickname() + "), fp=" + server
          .getFingerprint() + ") ");
      }
      LOG.debug("result new route: {}", sb.toString());
    }
    
    return result;
  }
  












  public static Router[] restoreCircuit(Directory directory, TCPStreamProperties sp, Router[] route, int failedNode)
    throws TorException
  {
    Fingerprint[] customRoute = new Fingerprint[route.length];
    

    if (sp == null) {
      sp = new TCPStreamProperties();
    }
    


    sp.setMinRouteLength(route.length);
    
    sp.setMaxRouteLength(route.length);
    
    sp.setRankingInfluenceIndex(1.0F);
    

    route[failedNode].punishRanking();
    

    if (sp.getRouteFingerprints() != null) {
      for (int i = 0; (i < sp.getRouteFingerprints().length) && (i < customRoute.length); i++) {
        customRoute[i] = sp.getRouteFingerprints()[i];
      }
    }
    
    for (int i = 0; i < failedNode; i++) {
      customRoute[i] = route[i].getFingerprint();
    }
    
    sp.setCustomRoute(customRoute);
    try
    {
      route = createNewRoute(directory, sp);
    }
    catch (TorException te) {
      LOG.warn("Directory.restoreCircuit: failed");
    }
    
    return route;
  }
  
  public static Integer getCurrentlyUsedNode(Fingerprint fingerprint) {
    return (Integer)currentlyUsedNodes.get(fingerprint);
  }
  
  public static void putCurrentlyUsedNodeNumber(Fingerprint fingerprint, Integer value) {
    currentlyUsedNodes.put(fingerprint, value);
  }
  


  public static void clear(TLSConnectionAdmin tlsConnectionAdmin)
  {
    suitableCircuitsCache.clear();
    

    for (TLSConnection tls : tlsConnectionAdmin.getConnections()) {
      for (Circuit circuit : tls.getCircuits()) {
        if ((circuit.isEstablished()) || (circuit.getStreamHistory().size() > 0)) {
          circuit.close(true);
        }
      }
    }
  }
}
