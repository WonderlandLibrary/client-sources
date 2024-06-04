package org.spongycastle.pqc.jcajce.interfaces;

import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.cert.Certificate;

public abstract interface StateAwareSignature
{
  public abstract void initVerify(PublicKey paramPublicKey)
    throws InvalidKeyException;
  
  public abstract void initVerify(Certificate paramCertificate)
    throws InvalidKeyException;
  
  public abstract void initSign(PrivateKey paramPrivateKey)
    throws InvalidKeyException;
  
  public abstract void initSign(PrivateKey paramPrivateKey, SecureRandom paramSecureRandom)
    throws InvalidKeyException;
  
  public abstract byte[] sign()
    throws SignatureException;
  
  public abstract int sign(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws SignatureException;
  
  public abstract boolean verify(byte[] paramArrayOfByte)
    throws SignatureException;
  
  public abstract boolean verify(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws SignatureException;
  
  public abstract void update(byte paramByte)
    throws SignatureException;
  
  public abstract void update(byte[] paramArrayOfByte)
    throws SignatureException;
  
  public abstract void update(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws SignatureException;
  
  public abstract void update(ByteBuffer paramByteBuffer)
    throws SignatureException;
  
  public abstract String getAlgorithm();
  
  public abstract PrivateKey getUpdatedPrivateKey();
}
