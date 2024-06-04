package org.spongycastle.asn1.cmc;

import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERUTF8String;


public class CMCStatusInfoV2Builder
{
  private final CMCStatus cMCStatus;
  private final ASN1Sequence bodyList;
  private DERUTF8String statusString;
  private OtherStatusInfo otherInfo;
  
  public CMCStatusInfoV2Builder(CMCStatus cMCStatus, BodyPartID bodyPartID)
  {
    this.cMCStatus = cMCStatus;
    bodyList = new DERSequence(bodyPartID);
  }
  
  public CMCStatusInfoV2Builder(CMCStatus cMCStatus, BodyPartID[] bodyList)
  {
    this.cMCStatus = cMCStatus;
    this.bodyList = new DERSequence(bodyList);
  }
  
  public CMCStatusInfoV2Builder setStatusString(String statusString)
  {
    this.statusString = new DERUTF8String(statusString);
    
    return this;
  }
  
  public CMCStatusInfoV2Builder setOtherInfo(CMCFailInfo failInfo)
  {
    otherInfo = new OtherStatusInfo(failInfo);
    
    return this;
  }
  
  public CMCStatusInfoV2Builder setOtherInfo(ExtendedFailInfo extendedFailInfo)
  {
    otherInfo = new OtherStatusInfo(extendedFailInfo);
    
    return this;
  }
  
  public CMCStatusInfoV2Builder setOtherInfo(PendInfo pendInfo)
  {
    otherInfo = new OtherStatusInfo(pendInfo);
    
    return this;
  }
  
  public CMCStatusInfoV2 build()
  {
    return new CMCStatusInfoV2(cMCStatus, bodyList, statusString, otherInfo);
  }
}
