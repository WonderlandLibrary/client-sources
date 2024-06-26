package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

















public class CMSAlgorithmProtection
  extends ASN1Object
{
  public static final int SIGNATURE = 1;
  public static final int MAC = 2;
  private final AlgorithmIdentifier digestAlgorithm;
  private final AlgorithmIdentifier signatureAlgorithm;
  private final AlgorithmIdentifier macAlgorithm;
  
  public CMSAlgorithmProtection(AlgorithmIdentifier digestAlgorithm, int type, AlgorithmIdentifier algorithmIdentifier)
  {
    if ((digestAlgorithm == null) || (algorithmIdentifier == null))
    {
      throw new NullPointerException("AlgorithmIdentifiers cannot be null");
    }
    
    this.digestAlgorithm = digestAlgorithm;
    
    if (type == 1)
    {
      signatureAlgorithm = algorithmIdentifier;
      macAlgorithm = null;
    }
    else if (type == 2)
    {
      signatureAlgorithm = null;
      macAlgorithm = algorithmIdentifier;
    }
    else
    {
      throw new IllegalArgumentException("Unknown type: " + type);
    }
  }
  
  private CMSAlgorithmProtection(ASN1Sequence sequence)
  {
    if (sequence.size() != 2)
    {
      throw new IllegalArgumentException("Sequence wrong size: One of signatureAlgorithm or macAlgorithm must be present");
    }
    
    digestAlgorithm = AlgorithmIdentifier.getInstance(sequence.getObjectAt(0));
    
    ASN1TaggedObject tagged = ASN1TaggedObject.getInstance(sequence.getObjectAt(1));
    if (tagged.getTagNo() == 1)
    {
      signatureAlgorithm = AlgorithmIdentifier.getInstance(tagged, false);
      macAlgorithm = null;
    }
    else if (tagged.getTagNo() == 2)
    {
      signatureAlgorithm = null;
      
      macAlgorithm = AlgorithmIdentifier.getInstance(tagged, false);
    }
    else
    {
      throw new IllegalArgumentException("Unknown tag found: " + tagged.getTagNo());
    }
  }
  

  public static CMSAlgorithmProtection getInstance(Object obj)
  {
    if ((obj instanceof CMSAlgorithmProtection))
    {
      return (CMSAlgorithmProtection)obj;
    }
    if (obj != null)
    {
      return new CMSAlgorithmProtection(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  

  public AlgorithmIdentifier getDigestAlgorithm()
  {
    return digestAlgorithm;
  }
  
  public AlgorithmIdentifier getMacAlgorithm()
  {
    return macAlgorithm;
  }
  
  public AlgorithmIdentifier getSignatureAlgorithm()
  {
    return signatureAlgorithm;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(digestAlgorithm);
    if (signatureAlgorithm != null)
    {
      v.add(new DERTaggedObject(false, 1, signatureAlgorithm));
    }
    if (macAlgorithm != null)
    {
      v.add(new DERTaggedObject(false, 2, macAlgorithm));
    }
    
    return new DERSequence(v);
  }
}
