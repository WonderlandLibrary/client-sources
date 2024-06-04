package org.spongycastle.asn1;

import java.io.IOException;






public abstract class ASN1TaggedObject
  extends ASN1Primitive
  implements ASN1TaggedObjectParser
{
  int tagNo;
  boolean empty = false;
  boolean explicit = true;
  ASN1Encodable obj = null;
  


  public static ASN1TaggedObject getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    if (explicit)
    {
      return (ASN1TaggedObject)obj.getObject();
    }
    
    throw new IllegalArgumentException("implicitly tagged tagged object");
  }
  

  public static ASN1TaggedObject getInstance(Object obj)
  {
    if ((obj == null) || ((obj instanceof ASN1TaggedObject)))
    {
      return (ASN1TaggedObject)obj;
    }
    if ((obj instanceof byte[]))
    {
      try
      {
        return getInstance(fromByteArray((byte[])obj));
      }
      catch (IOException e)
      {
        throw new IllegalArgumentException("failed to construct tagged object from byte[]: " + e.getMessage());
      }
    }
    
    throw new IllegalArgumentException("unknown object in getInstance: " + obj.getClass().getName());
  }
  













  public ASN1TaggedObject(boolean explicit, int tagNo, ASN1Encodable obj)
  {
    if ((obj instanceof ASN1Choice))
    {
      this.explicit = true;
    }
    else
    {
      this.explicit = explicit;
    }
    
    this.tagNo = tagNo;
    
    if (this.explicit)
    {
      this.obj = obj;
    }
    else
    {
      ASN1Primitive prim = obj.toASN1Primitive();
      
      if ((prim instanceof ASN1Set))
      {
        Object localObject = null;
      }
      
      this.obj = obj;
    }
  }
  

  boolean asn1Equals(ASN1Primitive o)
  {
    if (!(o instanceof ASN1TaggedObject))
    {
      return false;
    }
    
    ASN1TaggedObject other = (ASN1TaggedObject)o;
    
    if ((tagNo != tagNo) || (empty != empty) || (explicit != explicit))
    {
      return false;
    }
    
    if (obj == null)
    {
      if (obj != null)
      {
        return false;
      }
      

    }
    else if (!obj.toASN1Primitive().equals(obj.toASN1Primitive()))
    {
      return false;
    }
    

    return true;
  }
  
  public int hashCode()
  {
    int code = tagNo;
    





    if (obj != null)
    {
      code ^= obj.hashCode();
    }
    
    return code;
  }
  





  public int getTagNo()
  {
    return tagNo;
  }
  









  public boolean isExplicit()
  {
    return explicit;
  }
  
  public boolean isEmpty()
  {
    return empty;
  }
  







  public ASN1Primitive getObject()
  {
    if (obj != null)
    {
      return obj.toASN1Primitive();
    }
    
    return null;
  }
  







  public ASN1Encodable getObjectParser(int tag, boolean isExplicit)
    throws IOException
  {
    switch (tag)
    {
    case 17: 
      return ASN1Set.getInstance(this, isExplicit).parser();
    case 16: 
      return ASN1Sequence.getInstance(this, isExplicit).parser();
    case 4: 
      return ASN1OctetString.getInstance(this, isExplicit).parser();
    }
    
    if (isExplicit)
    {
      return getObject();
    }
    
    throw new ASN1Exception("implicit tagging not implemented for tag: " + tag);
  }
  
  public ASN1Primitive getLoadedObject()
  {
    return toASN1Primitive();
  }
  
  ASN1Primitive toDERObject()
  {
    return new DERTaggedObject(explicit, tagNo, obj);
  }
  
  ASN1Primitive toDLObject()
  {
    return new DLTaggedObject(explicit, tagNo, obj);
  }
  
  abstract void encode(ASN1OutputStream paramASN1OutputStream)
    throws IOException;
  
  public String toString()
  {
    return "[" + tagNo + "]" + obj;
  }
}
