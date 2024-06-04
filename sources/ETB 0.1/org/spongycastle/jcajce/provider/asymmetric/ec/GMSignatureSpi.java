package org.spongycastle.jcajce.provider.asymmetric.ec;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DSA;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.SM3Digest;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.signers.SM2Signer;
import org.spongycastle.jcajce.provider.asymmetric.util.DSABase;
import org.spongycastle.jcajce.provider.asymmetric.util.DSAEncoder;
import org.spongycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.spongycastle.util.Arrays;



public class GMSignatureSpi
  extends DSABase
{
  GMSignatureSpi(Digest digest, DSA signer, DSAEncoder encoder)
  {
    super(digest, signer, encoder);
  }
  
  protected void engineInitVerify(PublicKey publicKey)
    throws InvalidKeyException
  {
    CipherParameters param = ECUtils.generatePublicKeyParameter(publicKey);
    
    digest.reset();
    signer.init(false, param);
  }
  

  protected void engineInitSign(PrivateKey privateKey)
    throws InvalidKeyException
  {
    CipherParameters param = ECUtil.generatePrivateKeyParameter(privateKey);
    
    digest.reset();
    
    if (appRandom != null)
    {
      signer.init(true, new ParametersWithRandom(param, appRandom));
    }
    else
    {
      signer.init(true, param);
    }
  }
  
  public static class sm3WithSM2
    extends GMSignatureSpi
  {
    public sm3WithSM2()
    {
      super(new SM2Signer(), new GMSignatureSpi.StdDSAEncoder(null));
    }
  }
  
  private static class StdDSAEncoder
    implements DSAEncoder
  {
    private StdDSAEncoder() {}
    
    public byte[] encode(BigInteger r, BigInteger s)
      throws IOException
    {
      ASN1EncodableVector v = new ASN1EncodableVector();
      
      v.add(new ASN1Integer(r));
      v.add(new ASN1Integer(s));
      
      return new DERSequence(v).getEncoded("DER");
    }
    

    public BigInteger[] decode(byte[] encoding)
      throws IOException
    {
      ASN1Sequence s = (ASN1Sequence)ASN1Primitive.fromByteArray(encoding);
      if (s.size() != 2)
      {
        throw new IOException("malformed signature");
      }
      if (!Arrays.areEqual(encoding, s.getEncoded("DER")))
      {
        throw new IOException("malformed signature");
      }
      
      BigInteger[] sig = new BigInteger[2];
      

      sig[0] = ASN1Integer.getInstance(s.getObjectAt(0)).getValue();
      sig[1] = ASN1Integer.getInstance(s.getObjectAt(1)).getValue();
      
      return sig;
    }
  }
}
