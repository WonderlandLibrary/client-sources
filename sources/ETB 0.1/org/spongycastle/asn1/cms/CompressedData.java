package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.BERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;














public class CompressedData
  extends ASN1Object
{
  private ASN1Integer version;
  private AlgorithmIdentifier compressionAlgorithm;
  private ContentInfo encapContentInfo;
  
  public CompressedData(AlgorithmIdentifier compressionAlgorithm, ContentInfo encapContentInfo)
  {
    version = new ASN1Integer(0L);
    this.compressionAlgorithm = compressionAlgorithm;
    this.encapContentInfo = encapContentInfo;
  }
  

  private CompressedData(ASN1Sequence seq)
  {
    version = ((ASN1Integer)seq.getObjectAt(0));
    compressionAlgorithm = AlgorithmIdentifier.getInstance(seq.getObjectAt(1));
    encapContentInfo = ContentInfo.getInstance(seq.getObjectAt(2));
  }
  











  public static CompressedData getInstance(ASN1TaggedObject ato, boolean isExplicit)
  {
    return getInstance(ASN1Sequence.getInstance(ato, isExplicit));
  }
  














  public static CompressedData getInstance(Object obj)
  {
    if ((obj instanceof CompressedData))
    {
      return (CompressedData)obj;
    }
    
    if (obj != null)
    {
      return new CompressedData(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  
  public ASN1Integer getVersion()
  {
    return version;
  }
  
  public AlgorithmIdentifier getCompressionAlgorithmIdentifier()
  {
    return compressionAlgorithm;
  }
  
  public ContentInfo getEncapContentInfo()
  {
    return encapContentInfo;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(version);
    v.add(compressionAlgorithm);
    v.add(encapContentInfo);
    
    return new BERSequence(v);
  }
}
