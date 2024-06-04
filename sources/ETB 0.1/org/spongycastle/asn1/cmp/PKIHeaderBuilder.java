package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1GeneralizedTime;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.GeneralName;




public class PKIHeaderBuilder
{
  private ASN1Integer pvno;
  private GeneralName sender;
  private GeneralName recipient;
  private ASN1GeneralizedTime messageTime;
  private AlgorithmIdentifier protectionAlg;
  private ASN1OctetString senderKID;
  private ASN1OctetString recipKID;
  private ASN1OctetString transactionID;
  private ASN1OctetString senderNonce;
  private ASN1OctetString recipNonce;
  private PKIFreeText freeText;
  private ASN1Sequence generalInfo;
  
  public PKIHeaderBuilder(int pvno, GeneralName sender, GeneralName recipient)
  {
    this(new ASN1Integer(pvno), sender, recipient);
  }
  



  private PKIHeaderBuilder(ASN1Integer pvno, GeneralName sender, GeneralName recipient)
  {
    this.pvno = pvno;
    this.sender = sender;
    this.recipient = recipient;
  }
  
  public PKIHeaderBuilder setMessageTime(ASN1GeneralizedTime time)
  {
    messageTime = time;
    
    return this;
  }
  
  public PKIHeaderBuilder setProtectionAlg(AlgorithmIdentifier aid)
  {
    protectionAlg = aid;
    
    return this;
  }
  
  public PKIHeaderBuilder setSenderKID(byte[] kid)
  {
    return setSenderKID(kid == null ? null : new DEROctetString(kid));
  }
  
  public PKIHeaderBuilder setSenderKID(ASN1OctetString kid)
  {
    senderKID = kid;
    
    return this;
  }
  
  public PKIHeaderBuilder setRecipKID(byte[] kid)
  {
    return setRecipKID(kid == null ? null : new DEROctetString(kid));
  }
  
  public PKIHeaderBuilder setRecipKID(DEROctetString kid)
  {
    recipKID = kid;
    
    return this;
  }
  
  public PKIHeaderBuilder setTransactionID(byte[] tid)
  {
    return setTransactionID(tid == null ? null : new DEROctetString(tid));
  }
  
  public PKIHeaderBuilder setTransactionID(ASN1OctetString tid)
  {
    transactionID = tid;
    
    return this;
  }
  
  public PKIHeaderBuilder setSenderNonce(byte[] nonce)
  {
    return setSenderNonce(nonce == null ? null : new DEROctetString(nonce));
  }
  
  public PKIHeaderBuilder setSenderNonce(ASN1OctetString nonce)
  {
    senderNonce = nonce;
    
    return this;
  }
  
  public PKIHeaderBuilder setRecipNonce(byte[] nonce)
  {
    return setRecipNonce(nonce == null ? null : new DEROctetString(nonce));
  }
  
  public PKIHeaderBuilder setRecipNonce(ASN1OctetString nonce)
  {
    recipNonce = nonce;
    
    return this;
  }
  
  public PKIHeaderBuilder setFreeText(PKIFreeText text)
  {
    freeText = text;
    
    return this;
  }
  
  public PKIHeaderBuilder setGeneralInfo(InfoTypeAndValue genInfo)
  {
    return setGeneralInfo(makeGeneralInfoSeq(genInfo));
  }
  
  public PKIHeaderBuilder setGeneralInfo(InfoTypeAndValue[] genInfos)
  {
    return setGeneralInfo(makeGeneralInfoSeq(genInfos));
  }
  
  public PKIHeaderBuilder setGeneralInfo(ASN1Sequence seqOfInfoTypeAndValue)
  {
    generalInfo = seqOfInfoTypeAndValue;
    
    return this;
  }
  

  private static ASN1Sequence makeGeneralInfoSeq(InfoTypeAndValue generalInfo)
  {
    return new DERSequence(generalInfo);
  }
  

  private static ASN1Sequence makeGeneralInfoSeq(InfoTypeAndValue[] generalInfos)
  {
    ASN1Sequence genInfoSeq = null;
    if (generalInfos != null)
    {
      ASN1EncodableVector v = new ASN1EncodableVector();
      for (int i = 0; i < generalInfos.length; i++)
      {
        v.add(generalInfos[i]);
      }
      genInfoSeq = new DERSequence(v);
    }
    return genInfoSeq;
  }
  






































  public PKIHeader build()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(pvno);
    v.add(sender);
    v.add(recipient);
    addOptional(v, 0, messageTime);
    addOptional(v, 1, protectionAlg);
    addOptional(v, 2, senderKID);
    addOptional(v, 3, recipKID);
    addOptional(v, 4, transactionID);
    addOptional(v, 5, senderNonce);
    addOptional(v, 6, recipNonce);
    addOptional(v, 7, freeText);
    addOptional(v, 8, generalInfo);
    
    messageTime = null;
    protectionAlg = null;
    senderKID = null;
    recipKID = null;
    transactionID = null;
    senderNonce = null;
    recipNonce = null;
    freeText = null;
    generalInfo = null;
    
    return PKIHeader.getInstance(new DERSequence(v));
  }
  
  private void addOptional(ASN1EncodableVector v, int tagNo, ASN1Encodable obj)
  {
    if (obj != null)
    {
      v.add(new DERTaggedObject(true, tagNo, obj));
    }
  }
}
