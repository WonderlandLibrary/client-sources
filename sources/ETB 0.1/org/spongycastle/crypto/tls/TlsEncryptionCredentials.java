package org.spongycastle.crypto.tls;

import java.io.IOException;

public abstract interface TlsEncryptionCredentials
  extends TlsCredentials
{
  public abstract byte[] decryptPreMasterSecret(byte[] paramArrayOfByte)
    throws IOException;
}
