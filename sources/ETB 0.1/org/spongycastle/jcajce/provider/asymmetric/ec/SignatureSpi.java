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
import org.spongycastle.crypto.digests.NullDigest;
import org.spongycastle.crypto.digests.RIPEMD160Digest;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.signers.ECDSASigner;
import org.spongycastle.crypto.signers.ECNRSigner;
import org.spongycastle.crypto.signers.HMacDSAKCalculator;
import org.spongycastle.crypto.util.DigestFactory;
import org.spongycastle.jcajce.provider.asymmetric.util.DSABase;
import org.spongycastle.jcajce.provider.asymmetric.util.DSAEncoder;
import org.spongycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.spongycastle.util.Arrays;



public class SignatureSpi
  extends DSABase
{
  SignatureSpi(Digest digest, DSA signer, DSAEncoder encoder)
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
  
  public static class ecDSA
    extends SignatureSpi
  {
    public ecDSA()
    {
      super(new ECDSASigner(), new SignatureSpi.StdDSAEncoder(null));
    }
  }
  
  public static class ecDetDSA
    extends SignatureSpi
  {
    public ecDetDSA()
    {
      super(new ECDSASigner(new HMacDSAKCalculator(DigestFactory.createSHA1())), new SignatureSpi.StdDSAEncoder(null));
    }
  }
  
  public static class ecDSAnone
    extends SignatureSpi
  {
    public ecDSAnone()
    {
      super(new ECDSASigner(), new SignatureSpi.StdDSAEncoder(null));
    }
  }
  
  public static class ecDSA224
    extends SignatureSpi
  {
    public ecDSA224()
    {
      super(new ECDSASigner(), new SignatureSpi.StdDSAEncoder(null));
    }
  }
  
  public static class ecDetDSA224
    extends SignatureSpi
  {
    public ecDetDSA224()
    {
      super(new ECDSASigner(new HMacDSAKCalculator(DigestFactory.createSHA224())), new SignatureSpi.StdDSAEncoder(null));
    }
  }
  
  public static class ecDSA256
    extends SignatureSpi
  {
    public ecDSA256()
    {
      super(new ECDSASigner(), new SignatureSpi.StdDSAEncoder(null));
    }
  }
  
  public static class ecDetDSA256
    extends SignatureSpi
  {
    public ecDetDSA256()
    {
      super(new ECDSASigner(new HMacDSAKCalculator(DigestFactory.createSHA256())), new SignatureSpi.StdDSAEncoder(null));
    }
  }
  
  public static class ecDSA384
    extends SignatureSpi
  {
    public ecDSA384()
    {
      super(new ECDSASigner(), new SignatureSpi.StdDSAEncoder(null));
    }
  }
  
  public static class ecDetDSA384
    extends SignatureSpi
  {
    public ecDetDSA384()
    {
      super(new ECDSASigner(new HMacDSAKCalculator(DigestFactory.createSHA384())), new SignatureSpi.StdDSAEncoder(null));
    }
  }
  
  public static class ecDSA512
    extends SignatureSpi
  {
    public ecDSA512()
    {
      super(new ECDSASigner(), new SignatureSpi.StdDSAEncoder(null));
    }
  }
  
  public static class ecDetDSA512
    extends SignatureSpi
  {
    public ecDetDSA512()
    {
      super(new ECDSASigner(new HMacDSAKCalculator(DigestFactory.createSHA512())), new SignatureSpi.StdDSAEncoder(null));
    }
  }
  
  public static class ecDSASha3_224
    extends SignatureSpi
  {
    public ecDSASha3_224()
    {
      super(new ECDSASigner(), new SignatureSpi.StdDSAEncoder(null));
    }
  }
  
  public static class ecDetDSASha3_224
    extends SignatureSpi
  {
    public ecDetDSASha3_224()
    {
      super(new ECDSASigner(new HMacDSAKCalculator(DigestFactory.createSHA3_224())), new SignatureSpi.StdDSAEncoder(null));
    }
  }
  
  public static class ecDSASha3_256
    extends SignatureSpi
  {
    public ecDSASha3_256()
    {
      super(new ECDSASigner(), new SignatureSpi.StdDSAEncoder(null));
    }
  }
  
  public static class ecDetDSASha3_256
    extends SignatureSpi
  {
    public ecDetDSASha3_256()
    {
      super(new ECDSASigner(new HMacDSAKCalculator(DigestFactory.createSHA3_256())), new SignatureSpi.StdDSAEncoder(null));
    }
  }
  
  public static class ecDSASha3_384
    extends SignatureSpi
  {
    public ecDSASha3_384()
    {
      super(new ECDSASigner(), new SignatureSpi.StdDSAEncoder(null));
    }
  }
  
  public static class ecDetDSASha3_384
    extends SignatureSpi
  {
    public ecDetDSASha3_384()
    {
      super(new ECDSASigner(new HMacDSAKCalculator(DigestFactory.createSHA3_384())), new SignatureSpi.StdDSAEncoder(null));
    }
  }
  
  public static class ecDSASha3_512
    extends SignatureSpi
  {
    public ecDSASha3_512()
    {
      super(new ECDSASigner(), new SignatureSpi.StdDSAEncoder(null));
    }
  }
  
  public static class ecDetDSASha3_512
    extends SignatureSpi
  {
    public ecDetDSASha3_512()
    {
      super(new ECDSASigner(new HMacDSAKCalculator(DigestFactory.createSHA3_512())), new SignatureSpi.StdDSAEncoder(null));
    }
  }
  
  public static class ecDSARipeMD160
    extends SignatureSpi
  {
    public ecDSARipeMD160()
    {
      super(new ECDSASigner(), new SignatureSpi.StdDSAEncoder(null));
    }
  }
  
  public static class ecNR
    extends SignatureSpi
  {
    public ecNR()
    {
      super(new ECNRSigner(), new SignatureSpi.StdDSAEncoder(null));
    }
  }
  
  public static class ecNR224
    extends SignatureSpi
  {
    public ecNR224()
    {
      super(new ECNRSigner(), new SignatureSpi.StdDSAEncoder(null));
    }
  }
  
  public static class ecNR256
    extends SignatureSpi
  {
    public ecNR256()
    {
      super(new ECNRSigner(), new SignatureSpi.StdDSAEncoder(null));
    }
  }
  
  public static class ecNR384
    extends SignatureSpi
  {
    public ecNR384()
    {
      super(new ECNRSigner(), new SignatureSpi.StdDSAEncoder(null));
    }
  }
  
  public static class ecNR512
    extends SignatureSpi
  {
    public ecNR512()
    {
      super(new ECNRSigner(), new SignatureSpi.StdDSAEncoder(null));
    }
  }
  
  public static class ecCVCDSA
    extends SignatureSpi
  {
    public ecCVCDSA()
    {
      super(new ECDSASigner(), new SignatureSpi.PlainDSAEncoder(null));
    }
  }
  
  public static class ecCVCDSA224
    extends SignatureSpi
  {
    public ecCVCDSA224()
    {
      super(new ECDSASigner(), new SignatureSpi.PlainDSAEncoder(null));
    }
  }
  
  public static class ecCVCDSA256
    extends SignatureSpi
  {
    public ecCVCDSA256()
    {
      super(new ECDSASigner(), new SignatureSpi.PlainDSAEncoder(null));
    }
  }
  
  public static class ecCVCDSA384
    extends SignatureSpi
  {
    public ecCVCDSA384()
    {
      super(new ECDSASigner(), new SignatureSpi.PlainDSAEncoder(null));
    }
  }
  
  public static class ecCVCDSA512
    extends SignatureSpi
  {
    public ecCVCDSA512()
    {
      super(new ECDSASigner(), new SignatureSpi.PlainDSAEncoder(null));
    }
  }
  
  public static class ecPlainDSARP160
    extends SignatureSpi
  {
    public ecPlainDSARP160()
    {
      super(new ECDSASigner(), new SignatureSpi.PlainDSAEncoder(null));
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
  
  private static class PlainDSAEncoder
    implements DSAEncoder
  {
    private PlainDSAEncoder() {}
    
    public byte[] encode(BigInteger r, BigInteger s)
      throws IOException
    {
      byte[] first = makeUnsigned(r);
      byte[] second = makeUnsigned(s);
      byte[] res;
      byte[] res;
      if (first.length > second.length)
      {
        res = new byte[first.length * 2];
      }
      else
      {
        res = new byte[second.length * 2];
      }
      
      System.arraycopy(first, 0, res, res.length / 2 - first.length, first.length);
      System.arraycopy(second, 0, res, res.length - second.length, second.length);
      
      return res;
    }
    

    private byte[] makeUnsigned(BigInteger val)
    {
      byte[] res = val.toByteArray();
      
      if (res[0] == 0)
      {
        byte[] tmp = new byte[res.length - 1];
        
        System.arraycopy(res, 1, tmp, 0, tmp.length);
        
        return tmp;
      }
      
      return res;
    }
    

    public BigInteger[] decode(byte[] encoding)
      throws IOException
    {
      BigInteger[] sig = new BigInteger[2];
      
      byte[] first = new byte[encoding.length / 2];
      byte[] second = new byte[encoding.length / 2];
      
      System.arraycopy(encoding, 0, first, 0, first.length);
      System.arraycopy(encoding, first.length, second, 0, second.length);
      
      sig[0] = new BigInteger(1, first);
      sig[1] = new BigInteger(1, second);
      
      return sig;
    }
  }
}
