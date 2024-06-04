package org.silvertunnel_ng.netlib.layer.tor.directory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.silvertunnel_ng.netlib.layer.tor.api.Fingerprint;
import org.silvertunnel_ng.netlib.layer.tor.api.Router;
import org.silvertunnel_ng.netlib.layer.tor.common.TCPStreamProperties;
import org.silvertunnel_ng.netlib.layer.tor.common.TorConfig;
import org.silvertunnel_ng.netlib.tool.ConvenientStreamReader;
import org.silvertunnel_ng.netlib.tool.DynByteBuffer;
import org.silvertunnel_ng.netlib.util.TempfileStringStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


















public class GuardList
{
  private static final Logger LOG = LoggerFactory.getLogger(GuardList.class);
  private static final String GUARDLIST_LOCATION = "guards.cache";
  private static final SecureRandom RND = new SecureRandom();
  private List<GuardEntry> guardNodes = new ArrayList(5);
  private HashSet<Fingerprint> candidates = new HashSet();
  
  private Directory directory;
  
  private static final long TIMEOUT_TILL_REMOVE = 2592000000L;
  

  protected GuardList(Directory directory)
  {
    this.directory = directory;
    load();
  }
  



  private void load()
  {
    try
    {
      FileInputStream fileInputStream = new FileInputStream(TempfileStringStorage.getTempfileFile("guards.cache"));
      ConvenientStreamReader convenientStreamReader = new ConvenientStreamReader(fileInputStream);
      int count = convenientStreamReader.readInt();
      for (int i = 0; i < count; i++) {
        GuardEntry entry = new GuardEntry(convenientStreamReader);
        guardNodes.add(entry);
        LOG.debug("guard loaded from cache {}", fingerprint.getHex());
      }
      fileInputStream.close();
    } catch (FileNotFoundException exception) {
      LOG.debug("no guard nodes found");
    } catch (Exception exception) {
      LOG.warn("could not load guard nodes due to exception", exception);
    }
  }
  


  protected void save()
  {
    try
    {
      FileOutputStream fileOutputStream = new FileOutputStream(TempfileStringStorage.getTempfileFile("guards.cache"));
      DynByteBuffer buffer = new DynByteBuffer();
      buffer.append(guardNodes.size());
      for (GuardEntry entry : guardNodes) {
        entry.save(buffer);
      }
      fileOutputStream.write(buffer.toArray());
      fileOutputStream.close();
      LOG.debug("wrote guard list to local cache");
    } catch (Exception exception) {
      LOG.warn("Could not write guard list due to exception", exception);
    }
  }
  





  public Router getGuard(HashSet<Fingerprint> excluded, TCPStreamProperties props)
  {
    List<Router> routers = getUsableRouter(excluded);
    while (routers.size() <= 2) {
      addGuardsToList(routers, excluded, props);
    }
    int max = Math.min(routers.size(), TorConfig.numEntryGuards);
    Router candidate = (Router)routers.get(RND.nextInt(max));
    if (!isGuardInList(candidate.getFingerprint())) {
      candidates.add(candidate.getFingerprint());
    }
    LOG.debug("returning guard {}", candidate.getFingerprint().getHex());
    return candidate;
  }
  





  private boolean isGuardInList(Fingerprint fingerprint)
  {
    for (GuardEntry entry : guardNodes) {
      if (fingerprint.equals(fingerprint)) return true;
    }
    return false;
  }
  




  private List<Router> getUsableRouter(HashSet<Fingerprint> excluded)
  {
    List<Router> result = new ArrayList();
    List<GuardEntry> tmpList = new ArrayList(guardNodes);
    for (GuardEntry guardEntry : tmpList) {
      if (isRouterUsable(guardEntry, excluded)) {
        result.add(directory.getRouterByFingerprint(fingerprint));
      }
    }
    return result;
  }
  









  private boolean isRouterUsable(GuardEntry guardEntry, HashSet<Fingerprint> excluded)
  {
    if (excluded.contains(fingerprint)) {
      return false;
    }
    Router routerFromDirectory = directory.getRouterByFingerprint(fingerprint);
    
    if ((routerFromDirectory == null) || (!routerFromDirectory.isDirv2Guard()) || (!routerFromDirectory.isDirv2Running()) || (!routerFromDirectory.isDirv2Valid())) {
      if (firstDiscard > 0L) {
        if (System.currentTimeMillis() - firstDiscard > 2592000000L) {
          guardNodes.remove(guardEntry);
          LOG.debug("Guard {} has been removed because it was excluded for more than 30 days because of its status", fingerprint.getHex());
        }
      } else {
        firstDiscard = System.currentTimeMillis();
      }
      return false;
    }
    

    if (unsuccessfulConnect > 0) {
      long timeout = 129600000L;
      if (unsuccessfulConnect <= 6) {
        timeout = 3600000L;
      }
      else if (unsuccessfulConnect <= 24) {
        timeout = 14400000L;
      }
      else if (unsuccessfulConnect <= 33) {
        timeout = 64800000L;
      }
      


      if (System.currentTimeMillis() - lastUnsuccessfulConnect < timeout) {
        return false;
      }
    }
    
    return true;
  }
  
  private void addGuardsToList(List<Router> routerList, HashSet<Fingerprint> excluded, TCPStreamProperties prop) {
    RouterFlags flags = new RouterFlags();
    if (prop.isFastRoute()) flags.setFast(Boolean.valueOf(true));
    if (prop.isStableRoute()) flags.setStable(Boolean.valueOf(true));
    flags.setGuard(Boolean.valueOf(true));
    flags.setRunning(Boolean.valueOf(true));
    flags.setValid(Boolean.valueOf(true));
    
    Map<Fingerprint, Router> guards = directory.getValidRoutersByFlags(flags);
    Router guard = directory.selectRandomNode(guards, excluded, prop.getRankingInfluenceIndex(), prop.isFastRoute(), prop.isStableRoute());
    routerList.add(guard);
    LOG.debug("adding guard {} to list", guard.getFingerprint().getHex());
  }
  


  public void successful(Fingerprint fingerprint)
  {
    GuardEntry entry;
    
    if (candidates.contains(fingerprint)) {
      entry = new GuardEntry();
      fingerprint = fingerprint;
      candidates.remove(fingerprint);
      guardNodes.add(entry);
      save();
    } else {
      for (GuardEntry entry : guardNodes) {
        if (fingerprint.equals(fingerprint)) {
          lastUnsuccessfulConnect = 0L;
          unsuccessfulConnect = 0;
        }
      }
    }
  }
  




  public void unsuccessful(Fingerprint fingerprint)
  {
    if (candidates.contains(fingerprint)) {
      candidates.remove(fingerprint);
    } else {
      for (GuardEntry entry : guardNodes) {
        if (fingerprint.equals(fingerprint)) {
          lastUnsuccessfulConnect = System.currentTimeMillis();
          unsuccessfulConnect += 1;
        }
      }
    }
  }
}
