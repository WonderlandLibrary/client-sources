package org.silvertunnel_ng.netlib.layer.tor.directory;

import java.io.IOException;
import org.silvertunnel_ng.netlib.layer.tor.api.Fingerprint;
import org.silvertunnel_ng.netlib.tool.ConvenientStreamReader;
import org.silvertunnel_ng.netlib.tool.DynByteBuffer;























public class GuardEntry
{
  protected Fingerprint fingerprint;
  protected long firstDiscard = 0L;
  
  protected int unsuccessfulConnect = 0;
  
  protected long lastUnsuccessfulConnect = 0L;
  


  public GuardEntry(ConvenientStreamReader convenientStreamReader)
    throws IOException
  {
    fingerprint = new FingerprintImpl(convenientStreamReader.readByteArray());
    firstDiscard = convenientStreamReader.readLong();
    unsuccessfulConnect = convenientStreamReader.readInt();
    lastUnsuccessfulConnect = convenientStreamReader.readLong();
  }
  

  public GuardEntry() {}
  

  public void save(DynByteBuffer buffer)
  {
    buffer.append(fingerprint.getBytes(), true);
    buffer.append(firstDiscard);
    buffer.append(unsuccessfulConnect);
    buffer.append(lastUnsuccessfulConnect);
  }
}
