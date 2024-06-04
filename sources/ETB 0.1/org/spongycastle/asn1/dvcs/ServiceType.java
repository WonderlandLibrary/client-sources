package org.spongycastle.asn1.dvcs;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Enumerated;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1TaggedObject;










public class ServiceType
  extends ASN1Object
{
  public static final ServiceType CPD = new ServiceType(1);
  



  public static final ServiceType VSD = new ServiceType(2);
  



  public static final ServiceType VPKC = new ServiceType(3);
  



  public static final ServiceType CCPD = new ServiceType(4);
  
  private ASN1Enumerated value;
  
  public ServiceType(int value)
  {
    this.value = new ASN1Enumerated(value);
  }
  
  private ServiceType(ASN1Enumerated value)
  {
    this.value = value;
  }
  
  public static ServiceType getInstance(Object obj)
  {
    if ((obj instanceof ServiceType))
    {
      return (ServiceType)obj;
    }
    if (obj != null)
    {
      return new ServiceType(ASN1Enumerated.getInstance(obj));
    }
    
    return null;
  }
  


  public static ServiceType getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    return getInstance(ASN1Enumerated.getInstance(obj, explicit));
  }
  
  public BigInteger getValue()
  {
    return value.getValue();
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return value;
  }
  
  public String toString()
  {
    int num = value.getValue().intValue();
    return "" + num + (
    

      num == CCPD
      .getValue().intValue() ? "(CCPD)" : num == VPKC.getValue().intValue() ? "(VPKC)" : num == VSD.getValue().intValue() ? "(VSD)" : num == CPD.getValue().intValue() ? "(CPD)" : "?");
  }
}
