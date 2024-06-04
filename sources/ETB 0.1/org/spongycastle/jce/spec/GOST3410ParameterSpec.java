package org.spongycastle.jce.spec;

import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.cryptopro.GOST3410NamedParameters;
import org.spongycastle.asn1.cryptopro.GOST3410ParamSetParameters;
import org.spongycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters;
import org.spongycastle.jce.interfaces.GOST3410Params;








public class GOST3410ParameterSpec
  implements AlgorithmParameterSpec, GOST3410Params
{
  private GOST3410PublicKeyParameterSetSpec keyParameters;
  private String keyParamSetOID;
  private String digestParamSetOID;
  private String encryptionParamSetOID;
  
  public GOST3410ParameterSpec(String keyParamSetID, String digestParamSetOID, String encryptionParamSetOID)
  {
    GOST3410ParamSetParameters ecP = null;
    
    try
    {
      ecP = GOST3410NamedParameters.getByOID(new ASN1ObjectIdentifier(keyParamSetID));
    }
    catch (IllegalArgumentException e)
    {
      ASN1ObjectIdentifier oid = GOST3410NamedParameters.getOID(keyParamSetID);
      if (oid != null)
      {
        keyParamSetID = oid.getId();
        ecP = GOST3410NamedParameters.getByOID(oid);
      }
    }
    
    if (ecP == null)
    {
      throw new IllegalArgumentException("no key parameter set for passed in name/OID.");
    }
    



    keyParameters = new GOST3410PublicKeyParameterSetSpec(ecP.getP(), ecP.getQ(), ecP.getA());
    
    keyParamSetOID = keyParamSetID;
    this.digestParamSetOID = digestParamSetOID;
    this.encryptionParamSetOID = encryptionParamSetOID;
  }
  


  public GOST3410ParameterSpec(String keyParamSetID, String digestParamSetOID)
  {
    this(keyParamSetID, digestParamSetOID, null);
  }
  

  public GOST3410ParameterSpec(String keyParamSetID)
  {
    this(keyParamSetID, CryptoProObjectIdentifiers.gostR3411_94_CryptoProParamSet.getId(), null);
  }
  

  public GOST3410ParameterSpec(GOST3410PublicKeyParameterSetSpec spec)
  {
    keyParameters = spec;
    digestParamSetOID = CryptoProObjectIdentifiers.gostR3411_94_CryptoProParamSet.getId();
    encryptionParamSetOID = null;
  }
  
  public String getPublicKeyParamSetOID()
  {
    return keyParamSetOID;
  }
  
  public GOST3410PublicKeyParameterSetSpec getPublicKeyParameters()
  {
    return keyParameters;
  }
  
  public String getDigestParamSetOID()
  {
    return digestParamSetOID;
  }
  
  public String getEncryptionParamSetOID()
  {
    return encryptionParamSetOID;
  }
  
  public boolean equals(Object o)
  {
    if ((o instanceof GOST3410ParameterSpec))
    {
      GOST3410ParameterSpec other = (GOST3410ParameterSpec)o;
      
      if ((keyParameters.equals(keyParameters)) && 
        (digestParamSetOID.equals(digestParamSetOID))) if (encryptionParamSetOID != encryptionParamSetOID) if (encryptionParamSetOID == null) {
            break label76;
          }
      label76:
      return 
        encryptionParamSetOID
        
        .equals(encryptionParamSetOID);
    }
    
    return false;
  }
  
  public int hashCode()
  {
    return 
      keyParameters.hashCode() ^ digestParamSetOID.hashCode() ^ (encryptionParamSetOID != null ? encryptionParamSetOID.hashCode() : 0);
  }
  

  public static GOST3410ParameterSpec fromPublicKeyAlg(GOST3410PublicKeyAlgParameters params)
  {
    if (params.getEncryptionParamSet() != null)
    {
      return new GOST3410ParameterSpec(params.getPublicKeyParamSet().getId(), params.getDigestParamSet().getId(), params.getEncryptionParamSet().getId());
    }
    

    return new GOST3410ParameterSpec(params.getPublicKeyParamSet().getId(), params.getDigestParamSet().getId());
  }
}
