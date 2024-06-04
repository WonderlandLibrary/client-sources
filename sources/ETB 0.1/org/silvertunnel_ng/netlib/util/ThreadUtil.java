package org.silvertunnel_ng.netlib.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




























public class ThreadUtil
{
  private static final Logger LOG = LoggerFactory.getLogger(ThreadUtil.class);
  

  public ThreadUtil() {}
  
  public static void logAllRunningThreads()
  {
    logAllRunningThreads(LOG);
  }
  





  public static void logAllRunningThreads(Logger log)
  {
    ThreadGroup root = Thread.currentThread().getThreadGroup().getParent();
    while (root.getParent() != null)
    {
      root = root.getParent();
    }
    

    logThreadGroup(log, root, 0);
  }
  







  public static void logThreadGroup(Logger log, ThreadGroup group, int level)
  {
    int numThreads = group.activeCount();
    Thread[] threads = new Thread[numThreads * 2];
    numThreads = group.enumerate(threads, false);
    

    for (int i = 0; i < numThreads; i++)
    {

      Thread thread = threads[i];
      log.info(thread.toString());
    }
    

    int numGroups = group.activeGroupCount();
    ThreadGroup[] groups = new ThreadGroup[numGroups * 2];
    numGroups = group.enumerate(groups, false);
    

    for (int i = 0; i < numGroups; i++)
    {
      logThreadGroup(log, groups[i], level + 1);
    }
  }
}
