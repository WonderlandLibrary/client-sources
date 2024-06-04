package org.silvertunnel_ng.netlib.layer.tor.directory;

import java.util.Map;
import org.silvertunnel_ng.netlib.layer.tor.common.TorConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;






















public final class DirectoryManagerThread
  extends Thread
{
  private static final Logger LOG = LoggerFactory.getLogger(DirectoryManagerThread.class);
  

  private static final int MILLISEC = 1000;
  

  static final int INTERVAL_S = 3;
  
  private boolean stopped = false;
  
  private final Directory directory;
  
  private long currentTimeMillis;
  
  private long dirNextUpdateTimeMillis;
  
  public DirectoryManagerThread(Directory directory)
  {
    this.directory = directory;
    dirNextUpdateTimeMillis = currentTimeMillis;
    setName(getClass().getName());
    setDaemon(true);
    start();
  }
  



  private void updateDirectory()
  {
    currentTimeMillis = System.currentTimeMillis();
    if ((currentTimeMillis > dirNextUpdateTimeMillis) || (directory.getValidRoutersByFingerprint().isEmpty()))
    {
      LOG.debug("DirectoryManagerThread.updateDirectory: updating directory");
      dirNextUpdateTimeMillis = (currentTimeMillis + TorConfig.getIntervalDirectoryRefresh() * 60 * 1000);
      directory.refreshListOfServers();
    }
  }
  


  public void run()
  {
    while (!stopped)
    {
      try
      {

        updateDirectory();
        
        long sleepTime = dirNextUpdateTimeMillis - System.currentTimeMillis();
        if (sleepTime <= 0L)
        {
          sleepTime = 3000L;
        }
        sleep(sleepTime);
      }
      catch (Exception e)
      {
        stopped = true;
        LOG.debug("got Exception : {}", e.getMessage(), e);
      }
    }
  }
  






  public void setStopped()
  {
    stopped = true;
  }
}
