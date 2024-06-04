package org.spongycastle.x509;

import java.util.Date;



class CertStatus
{
  public static final int UNREVOKED = 11;
  public static final int UNDETERMINED = 12;
  int certStatus = 11;
  
  Date revocationDate = null;
  

  CertStatus() {}
  
  public Date getRevocationDate()
  {
    return revocationDate;
  }
  



  public void setRevocationDate(Date revocationDate)
  {
    this.revocationDate = revocationDate;
  }
  



  public int getCertStatus()
  {
    return certStatus;
  }
  



  public void setCertStatus(int certStatus)
  {
    this.certStatus = certStatus;
  }
}
