package org.spongycastle.x509;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Extension;
import java.util.Date;

/**
 * @deprecated
 */
public abstract interface X509AttributeCertificate
  extends X509Extension
{
  public abstract int getVersion();
  
  public abstract BigInteger getSerialNumber();
  
  public abstract Date getNotBefore();
  
  public abstract Date getNotAfter();
  
  public abstract AttributeCertificateHolder getHolder();
  
  public abstract AttributeCertificateIssuer getIssuer();
  
  public abstract X509Attribute[] getAttributes();
  
  public abstract X509Attribute[] getAttributes(String paramString);
  
  public abstract boolean[] getIssuerUniqueID();
  
  public abstract void checkValidity()
    throws CertificateExpiredException, CertificateNotYetValidException;
  
  public abstract void checkValidity(Date paramDate)
    throws CertificateExpiredException, CertificateNotYetValidException;
  
  public abstract byte[] getSignature();
  
  public abstract void verify(PublicKey paramPublicKey, String paramString)
    throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException;
  
  public abstract byte[] getEncoded()
    throws IOException;
}
