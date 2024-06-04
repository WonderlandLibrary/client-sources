package org.spongycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;
import org.spongycastle.asn1.ocsp.ResponderID;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.util.io.Streams;














public class OCSPStatusRequest
{
  protected Vector responderIDList;
  protected Extensions requestExtensions;
  
  public OCSPStatusRequest(Vector responderIDList, Extensions requestExtensions)
  {
    this.responderIDList = responderIDList;
    this.requestExtensions = requestExtensions;
  }
  



  public Vector getResponderIDList()
  {
    return responderIDList;
  }
  



  public Extensions getRequestExtensions()
  {
    return requestExtensions;
  }
  






  public void encode(OutputStream output)
    throws IOException
  {
    if ((responderIDList == null) || (responderIDList.isEmpty()))
    {
      TlsUtils.writeUint16(0, output);
    }
    else
    {
      ByteArrayOutputStream buf = new ByteArrayOutputStream();
      for (int i = 0; i < responderIDList.size(); i++)
      {
        ResponderID responderID = (ResponderID)responderIDList.elementAt(i);
        byte[] derEncoding = responderID.getEncoded("DER");
        TlsUtils.writeOpaque16(derEncoding, buf);
      }
      TlsUtils.checkUint16(buf.size());
      TlsUtils.writeUint16(buf.size(), output);
      Streams.writeBufTo(buf, output);
    }
    
    if (requestExtensions == null)
    {
      TlsUtils.writeUint16(0, output);
    }
    else
    {
      byte[] derEncoding = requestExtensions.getEncoded("DER");
      TlsUtils.checkUint16(derEncoding.length);
      TlsUtils.writeUint16(derEncoding.length, output);
      output.write(derEncoding);
    }
  }
  







  public static OCSPStatusRequest parse(InputStream input)
    throws IOException
  {
    Vector responderIDList = new Vector();
    
    int length = TlsUtils.readUint16(input);
    if (length > 0)
    {
      byte[] data = TlsUtils.readFully(length, input);
      ByteArrayInputStream buf = new ByteArrayInputStream(data);
      do
      {
        byte[] derEncoding = TlsUtils.readOpaque16(buf);
        ResponderID responderID = ResponderID.getInstance(TlsUtils.readDERObject(derEncoding));
        responderIDList.addElement(responderID);
      }
      while (buf.available() > 0);
    }
    

    Extensions requestExtensions = null;
    
    int length = TlsUtils.readUint16(input);
    if (length > 0)
    {
      byte[] derEncoding = TlsUtils.readFully(length, input);
      requestExtensions = Extensions.getInstance(TlsUtils.readDERObject(derEncoding));
    }
    

    return new OCSPStatusRequest(responderIDList, requestExtensions);
  }
}
