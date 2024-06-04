package org.spongycastle.asn1.cmc;

import java.util.HashMap;
import java.util.Map;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;






















public class CMCFailInfo
  extends ASN1Object
{
  public static final CMCFailInfo badAlg = new CMCFailInfo(new ASN1Integer(0L));
  public static final CMCFailInfo badMessageCheck = new CMCFailInfo(new ASN1Integer(1L));
  public static final CMCFailInfo badRequest = new CMCFailInfo(new ASN1Integer(2L));
  public static final CMCFailInfo badTime = new CMCFailInfo(new ASN1Integer(3L));
  public static final CMCFailInfo badCertId = new CMCFailInfo(new ASN1Integer(4L));
  public static final CMCFailInfo unsupportedExt = new CMCFailInfo(new ASN1Integer(5L));
  public static final CMCFailInfo mustArchiveKeys = new CMCFailInfo(new ASN1Integer(6L));
  public static final CMCFailInfo badIdentity = new CMCFailInfo(new ASN1Integer(7L));
  public static final CMCFailInfo popRequired = new CMCFailInfo(new ASN1Integer(8L));
  public static final CMCFailInfo popFailed = new CMCFailInfo(new ASN1Integer(9L));
  public static final CMCFailInfo noKeyReuse = new CMCFailInfo(new ASN1Integer(10L));
  public static final CMCFailInfo internalCAError = new CMCFailInfo(new ASN1Integer(11L));
  public static final CMCFailInfo tryLater = new CMCFailInfo(new ASN1Integer(12L));
  public static final CMCFailInfo authDataFail = new CMCFailInfo(new ASN1Integer(13L));
  
  private static Map range = new HashMap();
  private final ASN1Integer value;
  
  static {
    range.put(badAlgvalue, badAlg);
    range.put(badMessageCheckvalue, badMessageCheck);
    range.put(badRequestvalue, badRequest);
    range.put(badTimevalue, badTime);
    range.put(badCertIdvalue, badCertId);
    range.put(popRequiredvalue, popRequired);
    range.put(unsupportedExtvalue, unsupportedExt);
    range.put(mustArchiveKeysvalue, mustArchiveKeys);
    range.put(badIdentityvalue, badIdentity);
    range.put(popRequiredvalue, popRequired);
    range.put(popFailedvalue, popFailed);
    range.put(badCertIdvalue, badCertId);
    range.put(popRequiredvalue, popRequired);
    range.put(noKeyReusevalue, noKeyReuse);
    range.put(internalCAErrorvalue, internalCAError);
    range.put(tryLatervalue, tryLater);
    range.put(authDataFailvalue, authDataFail);
  }
  


  private CMCFailInfo(ASN1Integer value)
  {
    this.value = value;
  }
  
  public static CMCFailInfo getInstance(Object o)
  {
    if ((o instanceof CMCFailInfo))
    {
      return (CMCFailInfo)o;
    }
    
    if (o != null)
    {
      CMCFailInfo status = (CMCFailInfo)range.get(ASN1Integer.getInstance(o));
      
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
