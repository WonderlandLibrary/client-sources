package org.spongycastle.crypto.agreement.jpake;

import java.math.BigInteger;
import org.spongycastle.util.Arrays;
































public class JPAKERound2Payload
{
  private final String participantId;
  private final BigInteger a;
  private final BigInteger[] knowledgeProofForX2s;
  
  public JPAKERound2Payload(String participantId, BigInteger a, BigInteger[] knowledgeProofForX2s)
  {
    JPAKEUtil.validateNotNull(participantId, "participantId");
    JPAKEUtil.validateNotNull(a, "a");
    JPAKEUtil.validateNotNull(knowledgeProofForX2s, "knowledgeProofForX2s");
    
    this.participantId = participantId;
    this.a = a;
    this.knowledgeProofForX2s = Arrays.copyOf(knowledgeProofForX2s, knowledgeProofForX2s.length);
  }
  
  public String getParticipantId()
  {
    return participantId;
  }
  
  public BigInteger getA()
  {
    return a;
  }
  
  public BigInteger[] getKnowledgeProofForX2s()
  {
    return Arrays.copyOf(knowledgeProofForX2s, knowledgeProofForX2s.length);
  }
}
