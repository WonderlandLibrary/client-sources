package org.spongycastle.jce.provider;

import org.spongycastle.asn1.x509.ReasonFlags;










class ReasonsMask
{
  private int _reasons;
  
  ReasonsMask(ReasonFlags reasons)
  {
    _reasons = reasons.intValue();
  }
  
  private ReasonsMask(int reasons)
  {
    _reasons = reasons;
  }
  




  ReasonsMask()
  {
    this(0);
  }
  



  static final ReasonsMask allReasons = new ReasonsMask(33023);
  









  void addReasons(ReasonsMask mask)
  {
    _reasons |= mask.getReasons();
  }
  







  boolean isAllReasons()
  {
    return _reasons == allReasons_reasons;
  }
  






  ReasonsMask intersect(ReasonsMask mask)
  {
    ReasonsMask _mask = new ReasonsMask();
    _mask.addReasons(new ReasonsMask(_reasons & mask.getReasons()));
    return _mask;
  }
  






  boolean hasNewReasons(ReasonsMask mask)
  {
    return (_reasons | mask.getReasons() ^ _reasons) != 0;
  }
  





  int getReasons()
  {
    return _reasons;
  }
}
