package org.silvertunnel_ng.netlib.layer.tor.directory;

import java.util.List;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.layer.tor.api.Router;
import org.silvertunnel_ng.netlib.tool.SimpleHttpClientCompressed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




























public final class DescriptorFetcher
{
  private static final Logger LOG = LoggerFactory.getLogger(DescriptorFetcher.class);
  



  public static final int MAXIMUM_ALLOWED_DIGESTS = 96;
  



  public DescriptorFetcher() {}
  



  public static String downloadDescriptorsByDigest(List<String> nodesDigestsToLoad, Router directoryServer, NetLayer dirConnectionNetLayer)
  {
    if ((nodesDigestsToLoad == null) || (nodesDigestsToLoad.isEmpty())) {
      LOG.warn("executing downloadDescriptorsByDigest without descriptors doesnt make sense.");
      return null;
    }
    if (nodesDigestsToLoad.size() > 96) {
      LOG.error("only {} digests can be downloaded at once", Integer.valueOf(96));
      return null;
    }
    StringBuilder builder = new StringBuilder();
    for (String digest : nodesDigestsToLoad) {
      builder.append(digest).append('+');
    }
    
    try
    {
      String path = "/tor/server/d/" + builder.substring(0, builder.length() - 1);
      
      return SimpleHttpClientCompressed.getInstance().get(dirConnectionNetLayer, directoryServer.getDirAddress(), path);
    } catch (Exception e) {
      if (LOG.isDebugEnabled())
        LOG.debug("downloadSingleDescriptor() from " + directoryServer
          .getNickname() + " failed: " + e
          .getMessage(), e);
    }
    return null;
  }
  








  public static String downloadAllDescriptors(Router directoryServer, NetLayer dirConnectionNetLayer)
  {
    try
    {
      String path = "/tor/server/all";
      
      return SimpleHttpClientCompressed.getInstance().get(dirConnectionNetLayer, directoryServer
        .getDirAddress(), "/tor/server/all");

    }
    catch (Exception e)
    {
      if (LOG.isDebugEnabled())
        LOG.debug("downloadAllDescriptors() from " + directoryServer
          .getNickname() + " failed: " + e
          .getMessage(), e);
    }
    return null;
  }
}
