package org.spongycastle.asn1.bc;

import java.math.BigInteger;
import java.util.Date;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1GeneralizedTime;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERGeneralizedTime;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERUTF8String;
import org.spongycastle.util.Arrays;














public class ObjectData
  extends ASN1Object
{
  private final BigInteger type;
  private final String identifier;
  private final ASN1GeneralizedTime creationDate;
  private final ASN1GeneralizedTime lastModifiedDate;
  private final ASN1OctetString data;
  private final String comment;
  
  private ObjectData(ASN1Sequence seq)
  {
    type = ASN1Integer.getInstance(seq.getObjectAt(0)).getValue();
    identifier = DERUTF8String.getInstance(seq.getObjectAt(1)).getString();
    creationDate = ASN1GeneralizedTime.getInstance(seq.getObjectAt(2));
    lastModifiedDate = ASN1GeneralizedTime.getInstance(seq.getObjectAt(3));
    data = ASN1OctetString.getInstance(seq.getObjectAt(4));
    comment = (seq.size() == 6 ? DERUTF8String.getInstance(seq.getObjectAt(5)).getString() : null);
  }
  
  public ObjectData(BigInteger type, String identifier, Date creationDate, Date lastModifiedDate, byte[] data, String comment)
  {
    this.type = type;
    this.identifier = identifier;
    this.creationDate = new DERGeneralizedTime(creationDate);
    this.lastModifiedDate = new DERGeneralizedTime(lastModifiedDate);
    this.data = new DEROctetString(Arrays.clone(data));
    this.comment = comment;
  }
  

  public static ObjectData getInstance(Object obj)
  {
    if ((obj instanceof ObjectData))
    {
      return (ObjectData)obj;
    }
    if (obj != null)
    {
      return new ObjectData(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  
  public String getComment()
  {
    return comment;
  }
  
  public ASN1GeneralizedTime getCreationDate()
  {
    return creationDate;
  }
  
  public byte[] getData()
  {
    return Arrays.clone(data.getOctets());
  }
  
  public String getIdentifier()
  {
    return identifier;
  }
  
  public ASN1GeneralizedTime getLastModifiedDate()
  {
    return lastModifiedDate;
  }
  
  public BigInteger getType()
  {
    return type;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(new ASN1Integer(type));
    v.add(new DERUTF8String(identifier));
    v.add(creationDate);
    v.add(lastModifiedDate);
    v.add(data);
    
    if (comment != null)
    {
      v.add(new DERUTF8String(comment));
    }
    
    return new DERSequence(v);
  }
}
