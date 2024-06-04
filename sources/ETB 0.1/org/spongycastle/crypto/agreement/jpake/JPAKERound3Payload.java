package org.spongycastle.crypto.agreement.jpake;

import java.math.BigInteger;
























public class JPAKERound3Payload
{
  private final String participantId;
  private final BigInteger macTag;
  
  public JPAKERound3Payload(String participantId, BigInteger magTag)
  {
    this.participantId = participantId;
    macTag = magTag;
  }
  
  public String getParticipantId()
  {
    return participantId;
  }
  
  public BigInteger getMacTag()
  {
    return macTag;
  }
}
