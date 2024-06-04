package org.spongycastle.jcajce.util;

import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;

public abstract interface JcaJceHelper
{
  public abstract Cipher createCipher(String paramString)
    throws NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException;
  
  public abstract Mac createMac(String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException;
  
  public abstract KeyAgreement createKeyAgreement(String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException;
  
  public abstract AlgorithmParameterGenerator createAlgorithmParameterGenerator(String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException;
  
  public abstract AlgorithmParameters createAlgorithmParameters(String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException;
  
  public abstract KeyGenerator createKeyGenerator(String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException;
  
  public abstract KeyFactory createKeyFactory(String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException;
  
  public abstract SecretKeyFactory createSecretKeyFactory(String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException;
  
  public abstract KeyPairGenerator createKeyPairGenerator(String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException;
  
  public abstract MessageDigest createDigest(String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException;
  
  public abstract Signature createSignature(String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException;
  
  public abstract CertificateFactory createCertificateFactory(String paramString)
    throws NoSuchProviderException, CertificateException;
  
  public abstract SecureRandom createSecureRandom(String paramString)
    throws NoSuchAlgorithmException, NoSuchProviderException;
}
