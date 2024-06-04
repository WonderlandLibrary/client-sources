package org.spongycastle.crypto.tls;

import java.io.IOException;

public abstract interface TlsAuthentication
{
  public abstract void notifyServerCertificate(Certificate paramCertificate)
    throws IOException;
  
  public abstract TlsCredentials getClientCredentials(CertificateRequest paramCertificateRequest)
    throws IOException;
}
