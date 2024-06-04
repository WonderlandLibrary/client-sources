package org.spongycastle.asn1.pkcs;

import java.math.BigInteger;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.util.Arrays;














public class PBKDF2Params
  extends ASN1Object
{
  private static final AlgorithmIdentifier algid_hmacWithSHA1 = new AlgorithmIdentifier(PKCSObjectIdentifiers.id_hmacWithSHA1, DERNull.INSTANCE);
  

  private final ASN1OctetString octStr;
  

  private final ASN1Integer iterationCount;
  

  private final ASN1Integer keyLength;
  
  private final AlgorithmIdentifier prf;
  

  public static PBKDF2Params getInstance(Object obj)
  {
    if ((obj instanceof PBKDF2Params))
    {
      return (PBKDF2Params)obj;
    }
    
    if (obj != null)
    {
      return new PBKDF2Params(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  








  public PBKDF2Params(byte[] salt, int iterationCount)
  {
    this(salt, iterationCount, 0);
  }
  










  public PBKDF2Params(byte[] salt, int iterationCount, int keyLength)
  {
    this(salt, iterationCount, keyLength, null);
  }
  












  public PBKDF2Params(byte[] salt, int iterationCount, int keyLength, AlgorithmIdentifier prf)
  {
    octStr = new DEROctetString(Arrays.clone(salt));
    this.iterationCount = new ASN1Integer(iterationCount);
    
    if (keyLength > 0)
    {
      this.keyLength = new ASN1Integer(keyLength);
    }
    else
    {
      this.keyLength = null;
    }
    
    this.prf = prf;
  }
  










  public PBKDF2Params(byte[] salt, int iterationCount, AlgorithmIdentifier prf)
  {
    this(salt, iterationCount, 0, prf);
  }
  

  private PBKDF2Params(ASN1Sequence seq)
  {
    Enumeration e = seq.getObjects();
    
    octStr = ((ASN1OctetString)e.nextElement());
    iterationCount = ((ASN1Integer)e.nextElement());
    
    if (e.hasMoreElements())
    {
      Object o = e.nextElement();
      
      if ((o instanceof ASN1Integer))
      {
        keyLength = ASN1Integer.getInstance(o);
        if (e.hasMoreElements())
        {
          o = e.nextElement();
        }
        else
        {
          o = null;
        }
      }
      else
      {
        keyLength = null;
      }
      
      if (o != null)
      {
        prf = AlgorithmIdentifier.getInstance(o);
      }
      else
      {
        prf = null;
      }
    }
    else
    {
      keyLength = null;
      prf = null;
    }
  }
  





  public byte[] getSalt()
  {
    return octStr.getOctets();
  }
  





  public BigInteger getIterationCount()
  {
    return iterationCount.getValue();
  }
  





  public BigInteger getKeyLength()
  {
    if (keyLength != null)
    {
      return keyLength.getValue();
    }
    
    return null;
  }
  





  public boolean isDefaultPrf()
  {
    return (prf == null) || (prf.equals(algid_hmacWithSHA1));
  }
  





  public AlgorithmIdentifier getPrf()
  {
    if (prf != null)
    {
      return prf;
    }
    
    return algid_hmacWithSHA1;
  }
  





  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(octStr);
    v.add(iterationCount);
    
    if (keyLength != null)
    {
      v.add(keyLength);
    }
    
    if ((prf != null) && (!prf.equals(algid_hmacWithSHA1)))
    {
      v.add(prf);
    }
    
    return new DERSequence(v);
  }
}
