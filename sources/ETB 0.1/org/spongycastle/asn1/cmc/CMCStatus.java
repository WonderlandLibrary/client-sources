package org.spongycastle.asn1.cmc;

import java.util.HashMap;
import java.util.Map;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
















public class CMCStatus
  extends ASN1Object
{
  public static final CMCStatus success = new CMCStatus(new ASN1Integer(0L));
  public static final CMCStatus failed = new CMCStatus(new ASN1Integer(2L));
  public static final CMCStatus pending = new CMCStatus(new ASN1Integer(3L));
  public static final CMCStatus noSupport = new CMCStatus(new ASN1Integer(4L));
  public static final CMCStatus confirmRequired = new CMCStatus(new ASN1Integer(5L));
  public static final CMCStatus popRequired = new CMCStatus(new ASN1Integer(6L));
  public static final CMCStatus partial = new CMCStatus(new ASN1Integer(7L));
  
  private static Map range = new HashMap();
  private final ASN1Integer value;
  
  static {
    range.put(successvalue, success);
    range.put(failedvalue, failed);
    range.put(pendingvalue, pending);
    range.put(noSupportvalue, noSupport);
    range.put(confirmRequiredvalue, confirmRequired);
    range.put(popRequiredvalue, popRequired);
    range.put(partialvalue, partial);
  }
  


  private CMCStatus(ASN1Integer value)
  {
    this.value = value;
  }
  
  public static CMCStatus getInstance(Object o)
  {
    if ((o instanceof CMCStatus))
    {
      return (CMCStatus)o;
    }
    
    if (o != null)
    {
      CMCStatus status = (CMCStatus)range.get(ASN1Integer.getInstance(o));
      
      if (status != null)
      {
        return status;
      }
      
      throw new IllegalArgumentException("unknown object in getInstance(): " + o.getClass().getName());
    }
    
    return null;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return value;
  }
}
