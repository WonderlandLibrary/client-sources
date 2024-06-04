package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1Boolean;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERIA5String;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERUTF8String;


















public class MetaData
  extends ASN1Object
{
  private ASN1Boolean hashProtected;
  private DERUTF8String fileName;
  private DERIA5String mediaType;
  private Attributes otherMetaData;
  
  public MetaData(ASN1Boolean hashProtected, DERUTF8String fileName, DERIA5String mediaType, Attributes otherMetaData)
  {
    this.hashProtected = hashProtected;
    this.fileName = fileName;
    this.mediaType = mediaType;
    this.otherMetaData = otherMetaData;
  }
  
  private MetaData(ASN1Sequence seq)
  {
    hashProtected = ASN1Boolean.getInstance(seq.getObjectAt(0));
    
    int index = 1;
    
    if ((index < seq.size()) && ((seq.getObjectAt(index) instanceof DERUTF8String)))
    {
      fileName = DERUTF8String.getInstance(seq.getObjectAt(index++));
    }
    if ((index < seq.size()) && ((seq.getObjectAt(index) instanceof DERIA5String)))
    {
      mediaType = DERIA5String.getInstance(seq.getObjectAt(index++));
    }
    if (index < seq.size())
    {
      otherMetaData = Attributes.getInstance(seq.getObjectAt(index++));
    }
  }
  













  public static MetaData getInstance(Object obj)
  {
    if ((obj instanceof MetaData))
    {
      return (MetaData)obj;
    }
    if (obj != null)
    {
      return new MetaData(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(hashProtected);
    
    if (fileName != null)
    {
      v.add(fileName);
    }
    
    if (mediaType != null)
    {
      v.add(mediaType);
    }
    
    if (otherMetaData != null)
    {
      v.add(otherMetaData);
    }
    
    return new DERSequence(v);
  }
  
  public boolean isHashProtected()
  {
    return hashProtected.isTrue();
  }
  
  public DERUTF8String getFileName()
  {
    return fileName;
  }
  
  public DERIA5String getMediaType()
  {
    return mediaType;
  }
  
  public Attributes getOtherMetaData()
  {
    return otherMetaData;
  }
}
