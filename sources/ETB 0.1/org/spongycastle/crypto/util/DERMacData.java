package org.spongycastle.crypto.util;

import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Strings;



public final class DERMacData
{
  private final byte[] macData;
  
  public static enum Type
  {
    UNILATERALU("KC_1_U"), 
    UNILATERALV("KC_1_V"), 
    BILATERALU("KC_2_U"), 
    BILATERALV("KC_2_V");
    
    private final String enc;
    
    private Type(String enc)
    {
      this.enc = enc;
    }
    
    public byte[] getHeader()
    {
      return Strings.toByteArray(enc);
    }
  }
  


  public static final class Builder
  {
    private final DERMacData.Type type;
    

    private ASN1OctetString idU;
    

    private ASN1OctetString idV;
    

    private ASN1OctetString ephemDataU;
    

    private ASN1OctetString ephemDataV;
    

    private byte[] text;
    

    public Builder(DERMacData.Type type, byte[] idU, byte[] idV, byte[] ephemDataU, byte[] ephemDataV)
    {
      this.type = type;
      this.idU = DerUtil.getOctetString(idU);
      this.idV = DerUtil.getOctetString(idV);
      this.ephemDataU = DerUtil.getOctetString(ephemDataU);
      this.ephemDataV = DerUtil.getOctetString(ephemDataV);
    }
    






    public Builder withText(byte[] text)
    {
      this.text = DerUtil.toByteArray(new DERTaggedObject(false, 0, DerUtil.getOctetString(text)));
      
      return this;
    }
    
    public DERMacData build()
    {
      switch (DERMacData.1.$SwitchMap$org$spongycastle$crypto$util$DERMacData$Type[type.ordinal()])
      {
      case 1: 
      case 2: 
        return new DERMacData(concatenate(type.getHeader(), 
          DerUtil.toByteArray(idU), DerUtil.toByteArray(idV), 
          DerUtil.toByteArray(ephemDataU), DerUtil.toByteArray(ephemDataV), text), null);
      case 3: 
      case 4: 
        return new DERMacData(concatenate(type.getHeader(), 
          DerUtil.toByteArray(idV), DerUtil.toByteArray(idU), 
          DerUtil.toByteArray(ephemDataV), DerUtil.toByteArray(ephemDataU), text), null);
      }
      
      throw new IllegalStateException("Unknown type encountered in build");
    }
    
    private byte[] concatenate(byte[] header, byte[] id1, byte[] id2, byte[] ed1, byte[] ed2, byte[] text)
    {
      return Arrays.concatenate(Arrays.concatenate(header, id1, id2), Arrays.concatenate(ed1, ed2, text));
    }
  }
  


  private DERMacData(byte[] macData)
  {
    this.macData = macData;
  }
  
  public byte[] getMacData()
  {
    return Arrays.clone(macData);
  }
}
