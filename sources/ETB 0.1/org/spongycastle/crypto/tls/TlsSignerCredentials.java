package org.spongycastle.crypto.tls;

import java.io.IOException;

public abstract interface TlsSignerCredentials
  extends TlsCredentials
{
  public abstract byte[] generateCertificateSignature(byte[] paramArrayOfByte)
    throws IOException;
  
  public abstract SignatureAndHashAlgorithm getSignatureAndHashAlgorithm();
}
