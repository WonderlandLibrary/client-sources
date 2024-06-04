package org.spongycastle.asn1.bc;

import java.io.IOException;
import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;










public class ObjectStoreIntegrityCheck
  extends ASN1Object
  implements ASN1Choice
{
  public static final int PBKD_MAC_CHECK = 0;
  private final int type;
  private final ASN1Object integrityCheck;
  
  public ObjectStoreIntegrityCheck(PbkdMacIntegrityCheck macIntegrityCheck)
  {
    this(macIntegrityCheck);
  }
  
  private ObjectStoreIntegrityCheck(ASN1Encodable obj)
  {
    if (((obj instanceof ASN1Sequence)) || ((obj instanceof PbkdMacIntegrityCheck)))
    {
      type = 0;
      integrityCheck = PbkdMacIntegrityCheck.getInstance(obj);
    }
    else
    {
      throw new IllegalArgumentException("Unknown check object in integrity check.");
    }
  }
  
  public static ObjectStoreIntegrityCheck getInstance(Object o)
  {
    if ((o instanceof ObjectStoreIntegrityCheck))
    {
      return (ObjectStoreIntegrityCheck)o;
    }
    if ((o instanceof byte[]))
    {
      try
      {
        return new ObjectStoreIntegrityCheck(ASN1Primitive.fromByteArray((byte[])o));
      }
      catch (IOException e)
      {
        throw new IllegalArgumentException("Unable to parse integrity check details.");
      }
    }
    if (o != null)
    {
      return new ObjectStoreIntegrityCheck((ASN1Encodable)o);
    }
    
    return null;
  }
  

  public int getType()
  {
    return type;
  }
  
  public ASN1Object getIntegrityCheck()
  {
    return integrityCheck;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return integrityCheck.toASN1Primitive();
  }
}
