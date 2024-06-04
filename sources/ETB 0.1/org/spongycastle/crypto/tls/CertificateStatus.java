package org.spongycastle.crypto.tls;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.spongycastle.asn1.ocsp.OCSPResponse;



public class CertificateStatus
{
  protected short statusType;
  protected Object response;
  
  public CertificateStatus(short statusType, Object response)
  {
    if (!isCorrectType(statusType, response))
    {
      throw new IllegalArgumentException("'response' is not an instance of the correct type");
    }
    
    this.statusType = statusType;
    this.response = response;
  }
  
  public short getStatusType()
  {
    return statusType;
  }
  
  public Object getResponse()
  {
    return response;
  }
  
  public OCSPResponse getOCSPResponse()
  {
    if (!isCorrectType(, response))
    {
      throw new IllegalStateException("'response' is not an OCSPResponse");
    }
    return (OCSPResponse)response;
  }
  






  public void encode(OutputStream output)
    throws IOException
  {
    TlsUtils.writeUint8(statusType, output);
    
    switch (statusType)
    {
    case 1: 
      byte[] derEncoding = ((OCSPResponse)response).getEncoded("DER");
      TlsUtils.writeOpaque24(derEncoding, output);
      break;
    default: 
      throw new TlsFatalAlert((short)80);
    }
    
  }
  






  public static CertificateStatus parse(InputStream input)
    throws IOException
  {
    short status_type = TlsUtils.readUint8(input);
    
    Object response;
    switch (status_type)
    {

    case 1: 
      byte[] derEncoding = TlsUtils.readOpaque24(input);
      response = OCSPResponse.getInstance(TlsUtils.readDERObject(derEncoding));
      break;
    
    default: 
      throw new TlsFatalAlert((short)50); }
    
    Object response;
    return new CertificateStatus(status_type, response);
  }
  
  protected static boolean isCorrectType(short statusType, Object response)
  {
    switch (statusType)
    {
    case 1: 
      return response instanceof OCSPResponse;
    }
    throw new IllegalArgumentException("'statusType' is an unsupported CertificateStatusType");
  }
}
