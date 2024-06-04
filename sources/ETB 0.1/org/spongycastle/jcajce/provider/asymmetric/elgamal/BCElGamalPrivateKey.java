package org.spongycastle.jcajce.provider.asymmetric.elgamal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.util.Enumeration;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.DHPrivateKeySpec;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.oiw.ElGamalParameter;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.crypto.params.ElGamalParameters;
import org.spongycastle.crypto.params.ElGamalPrivateKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import org.spongycastle.jce.interfaces.ElGamalPrivateKey;
import org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.spongycastle.jce.spec.ElGamalParameterSpec;
import org.spongycastle.jce.spec.ElGamalPrivateKeySpec;





public class BCElGamalPrivateKey
  implements ElGamalPrivateKey, DHPrivateKey, PKCS12BagAttributeCarrier
{
  static final long serialVersionUID = 4819350091141529678L;
  private BigInteger x;
  private transient ElGamalParameterSpec elSpec;
  private transient PKCS12BagAttributeCarrierImpl attrCarrier = new PKCS12BagAttributeCarrierImpl();
  


  protected BCElGamalPrivateKey() {}
  

  BCElGamalPrivateKey(ElGamalPrivateKey key)
  {
    x = key.getX();
    elSpec = key.getParameters();
  }
  

  BCElGamalPrivateKey(DHPrivateKey key)
  {
    x = key.getX();
    elSpec = new ElGamalParameterSpec(key.getParams().getP(), key.getParams().getG());
  }
  

  BCElGamalPrivateKey(ElGamalPrivateKeySpec spec)
  {
    x = spec.getX();
    elSpec = new ElGamalParameterSpec(spec.getParams().getP(), spec.getParams().getG());
  }
  

  BCElGamalPrivateKey(DHPrivateKeySpec spec)
  {
    x = spec.getX();
    elSpec = new ElGamalParameterSpec(spec.getP(), spec.getG());
  }
  

  BCElGamalPrivateKey(PrivateKeyInfo info)
    throws IOException
  {
    ElGamalParameter params = ElGamalParameter.getInstance(info.getPrivateKeyAlgorithm().getParameters());
    ASN1Integer derX = ASN1Integer.getInstance(info.parsePrivateKey());
    
    x = derX.getValue();
    elSpec = new ElGamalParameterSpec(params.getP(), params.getG());
  }
  

  BCElGamalPrivateKey(ElGamalPrivateKeyParameters params)
  {
    x = params.getX();
    elSpec = new ElGamalParameterSpec(params.getParameters().getP(), params.getParameters().getG());
  }
  
  public String getAlgorithm()
  {
    return "ElGamal";
  }
  





  public String getFormat()
  {
    return "PKCS#8";
  }
  






  public byte[] getEncoded()
  {
    try
    {
      PrivateKeyInfo info = new PrivateKeyInfo(new AlgorithmIdentifier(OIWObjectIdentifiers.elGamalAlgorithm, new ElGamalParameter(elSpec.getP(), elSpec.getG())), new ASN1Integer(getX()));
      
      return info.getEncoded("DER");
    }
    catch (IOException e) {}
    
    return null;
  }
  

  public ElGamalParameterSpec getParameters()
  {
    return elSpec;
  }
  
  public DHParameterSpec getParams()
  {
    return new DHParameterSpec(elSpec.getP(), elSpec.getG());
  }
  
  public BigInteger getX()
  {
    return x;
  }
  

  public boolean equals(Object o)
  {
    if (!(o instanceof DHPrivateKey))
    {
      return false;
    }
    
    DHPrivateKey other = (DHPrivateKey)o;
    
    return (getX().equals(other.getX())) && 
      (getParams().getG().equals(other.getParams().getG())) && 
      (getParams().getP().equals(other.getParams().getP())) && 
      (getParams().getL() == other.getParams().getL());
  }
  
  public int hashCode()
  {
    return 
      getX().hashCode() ^ getParams().getG().hashCode() ^ getParams().getP().hashCode() ^ getParams().getL();
  }
  

  private void readObject(ObjectInputStream in)
    throws IOException, ClassNotFoundException
  {
    in.defaultReadObject();
    
    elSpec = new ElGamalParameterSpec((BigInteger)in.readObject(), (BigInteger)in.readObject());
    attrCarrier = new PKCS12BagAttributeCarrierImpl();
  }
  

  private void writeObject(ObjectOutputStream out)
    throws IOException
  {
    out.defaultWriteObject();
    
    out.writeObject(elSpec.getP());
    out.writeObject(elSpec.getG());
  }
  


  public void setBagAttribute(ASN1ObjectIdentifier oid, ASN1Encodable attribute)
  {
    attrCarrier.setBagAttribute(oid, attribute);
  }
  

  public ASN1Encodable getBagAttribute(ASN1ObjectIdentifier oid)
  {
    return attrCarrier.getBagAttribute(oid);
  }
  
  public Enumeration getBagAttributeKeys()
  {
    return attrCarrier.getBagAttributeKeys();
  }
}
