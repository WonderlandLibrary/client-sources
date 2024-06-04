package org.spongycastle.crypto.tls;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CertificateStatusRequest
{
  protected short statusType;
  protected Object request;
  
  public CertificateStatusRequest(short statusType, Object request)
  {
    if (!isCorrectType(statusType, request))
    {
      throw new IllegalArgumentException("'request' is not an instance of the correct type");
    }
    
    this.statusType = statusType;
    this.request = request;
  }
  
  public short getStatusType()
  {
    return statusType;
  }
  
  public Object getRequest()
  {
    return request;
  }
  
  public OCSPStatusRequest getOCSPStatusRequest()
  {
    if (!isCorrectType(, request))
    {
      throw new IllegalStateException("'request' is not an OCSPStatusRequest");
    }
    return (OCSPStatusRequest)request;
  }
  






  public void encode(OutputStream output)
    throws IOException
  {
    TlsUtils.writeUint8(statusType, output);
    
    switch (statusType)
    {
    case 1: 
      ((OCSPStatusRequest)request).encode(output);
      break;
    default: 
      throw new TlsFatalAlert((short)80);
    }
    
  }
  






  public static CertificateStatusRequest parse(InputStream input)
    throws IOException
  {
    short status_type = TlsUtils.readUint8(input);
    
    Object result;
    switch (status_type)
    {
    case 1: 
      result = OCSPStatusRequest.parse(input);
      break;
    default: 
      throw new TlsFatalAlert((short)50);
    }
    Object result;
    return new CertificateStatusRequest(status_type, result);
  }
  
  protected static boolean isCorrectType(short statusType, Object request)
  {
    switch (statusType)
    {
    case 1: 
      return request instanceof OCSPStatusRequest;
    }
    throw new IllegalArgumentException("'statusType' is an unsupported CertificateStatusType");
  }
}
