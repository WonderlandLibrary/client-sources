package org.spongycastle.crypto.tls;

public abstract class ServerOnlyTlsAuthentication implements TlsAuthentication
{
  public ServerOnlyTlsAuthentication() {}
  
  public final TlsCredentials getClientCredentials(CertificateRequest certificateRequest) {
    return null;
  }
}
