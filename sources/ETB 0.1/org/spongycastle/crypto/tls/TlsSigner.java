package org.spongycastle.crypto.tls;

import org.spongycastle.crypto.CryptoException;
import org.spongycastle.crypto.Signer;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;

public abstract interface TlsSigner
{
  public abstract void init(TlsContext paramTlsContext);
  
  public abstract byte[] generateRawSignature(AsymmetricKeyParameter paramAsymmetricKeyParameter, byte[] paramArrayOfByte)
    throws CryptoException;
  
  public abstract byte[] generateRawSignature(SignatureAndHashAlgorithm paramSignatureAndHashAlgorithm, AsymmetricKeyParameter paramAsymmetricKeyParameter, byte[] paramArrayOfByte)
    throws CryptoException;
  
  public abstract boolean verifyRawSignature(byte[] paramArrayOfByte1, AsymmetricKeyParameter paramAsymmetricKeyParameter, byte[] paramArrayOfByte2)
    throws CryptoException;
  
  public abstract boolean verifyRawSignature(SignatureAndHashAlgorithm paramSignatureAndHashAlgorithm, byte[] paramArrayOfByte1, AsymmetricKeyParameter paramAsymmetricKeyParameter, byte[] paramArrayOfByte2)
    throws CryptoException;
  
  public abstract Signer createSigner(AsymmetricKeyParameter paramAsymmetricKeyParameter);
  
  public abstract Signer createSigner(SignatureAndHashAlgorithm paramSignatureAndHashAlgorithm, AsymmetricKeyParameter paramAsymmetricKeyParameter);
  
  public abstract Signer createVerifyer(AsymmetricKeyParameter paramAsymmetricKeyParameter);
  
  public abstract Signer createVerifyer(SignatureAndHashAlgorithm paramSignatureAndHashAlgorithm, AsymmetricKeyParameter paramAsymmetricKeyParameter);
  
  public abstract boolean isValidPublicKey(AsymmetricKeyParameter paramAsymmetricKeyParameter);
}
