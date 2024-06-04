package org.spongycastle.jcajce.provider.util;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;

public abstract interface AsymmetricKeyInfoConverter
{
  public abstract PrivateKey generatePrivate(PrivateKeyInfo paramPrivateKeyInfo)
    throws IOException;
  
  public abstract PublicKey generatePublic(SubjectPublicKeyInfo paramSubjectPublicKeyInfo)
    throws IOException;
}
