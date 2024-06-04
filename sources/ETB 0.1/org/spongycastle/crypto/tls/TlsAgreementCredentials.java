package org.spongycastle.crypto.tls;

import java.io.IOException;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;

public abstract interface TlsAgreementCredentials
  extends TlsCredentials
{
  public abstract byte[] generateAgreement(AsymmetricKeyParameter paramAsymmetricKeyParameter)
    throws IOException;
}
