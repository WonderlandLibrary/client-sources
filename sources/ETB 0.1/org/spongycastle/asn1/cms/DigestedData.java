package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.BERSequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;














public class DigestedData
  extends ASN1Object
{
  private ASN1Integer version;
  private AlgorithmIdentifier digestAlgorithm;
  private ContentInfo encapContentInfo;
  private ASN1OctetString digest;
  
  public DigestedData(AlgorithmIdentifier digestAlgorithm, ContentInfo encapContentInfo, byte[] digest)
  {
    version = new ASN1Integer(0L);
    this.digestAlgorithm = digestAlgorithm;
    this.encapContentInfo = encapContentInfo;
    this.digest = new DEROctetString(digest);
  }
  

  private DigestedData(ASN1Sequence seq)
  {
    version = ((ASN1Integer)seq.getObjectAt(0));
    digestAlgorithm = AlgorithmIdentifier.getInstance(seq.getObjectAt(1));
    encapContentInfo = ContentInfo.getInstance(seq.getObjectAt(2));
    digest = ASN1OctetString.getInstance(seq.getObjectAt(3));
  }
  











  public static DigestedData getInstance(ASN1TaggedObject ato, boolean isExplicit)
  {
    return getInstance(ASN1Sequence.getInstance(ato, isExplicit));
  }
  














  public static DigestedData getInstance(Object obj)
  {
    if ((obj instanceof DigestedData))
    {
      return (DigestedData)obj;
    }
    
    if (obj != null)
    {
      return new DigestedData(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  
  public ASN1Integer getVersion()
  {
    return version;
  }
  
  public AlgorithmIdentifier getDigestAlgorithm()
  {
    return digestAlgorithm;
  }
  
  public ContentInfo getEncapContentInfo()
  {
    return encapContentInfo;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(version);
    v.add(digestAlgorithm);
    v.add(encapContentInfo);
    v.add(digest);
    
    return new BERSequence(v);
  }
  
  public byte[] getDigest()
  {
    return digest.getOctets();
  }
}
