package org.spongycastle.jcajce.provider.asymmetric.elgamal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.DHPublicKeySpec;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.oiw.ElGamalParameter;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.crypto.params.ElGamalParameters;
import org.spongycastle.crypto.params.ElGamalPublicKeyParameters;
import org.spongycastle.jce.interfaces.ElGamalPublicKey;
import org.spongycastle.jce.spec.ElGamalParameterSpec;
import org.spongycastle.jce.spec.ElGamalPublicKeySpec;





public class BCElGamalPublicKey
  implements ElGamalPublicKey, DHPublicKey
{
  static final long serialVersionUID = 8712728417091216948L;
  private BigInteger y;
  private transient ElGamalParameterSpec elSpec;
  
  BCElGamalPublicKey(ElGamalPublicKeySpec spec)
  {
    y = spec.getY();
    elSpec = new ElGamalParameterSpec(spec.getParams().getP(), spec.getParams().getG());
  }
  

  BCElGamalPublicKey(DHPublicKeySpec spec)
  {
    y = spec.getY();
    elSpec = new ElGamalParameterSpec(spec.getP(), spec.getG());
  }
  

  BCElGamalPublicKey(ElGamalPublicKey key)
  {
    y = key.getY();
    elSpec = key.getParameters();
  }
  

  BCElGamalPublicKey(DHPublicKey key)
  {
    y = key.getY();
    elSpec = new ElGamalParameterSpec(key.getParams().getP(), key.getParams().getG());
  }
  

  BCElGamalPublicKey(ElGamalPublicKeyParameters params)
  {
    y = params.getY();
    elSpec = new ElGamalParameterSpec(params.getParameters().getP(), params.getParameters().getG());
  }
  


  BCElGamalPublicKey(BigInteger y, ElGamalParameterSpec elSpec)
  {
    this.y = y;
    this.elSpec = elSpec;
  }
  

  BCElGamalPublicKey(SubjectPublicKeyInfo info)
  {
    ElGamalParameter params = ElGamalParameter.getInstance(info.getAlgorithm().getParameters());
    ASN1Integer derY = null;
    
    try
    {
      derY = (ASN1Integer)info.parsePublicKey();
    }
    catch (IOException e)
    {
      throw new IllegalArgumentException("invalid info structure in DSA public key");
    }
    
    y = derY.getValue();
    elSpec = new ElGamalParameterSpec(params.getP(), params.getG());
  }
  
  public String getAlgorithm()
  {
    return "ElGamal";
  }
  
  public String getFormat()
  {
    return "X.509";
  }
  
  public byte[] getEncoded()
  {
    try
    {
      SubjectPublicKeyInfo info = new SubjectPublicKeyInfo(new AlgorithmIdentifier(OIWObjectIdentifiers.elGamalAlgorithm, new ElGamalParameter(elSpec.getP(), elSpec.getG())), new ASN1Integer(y));
      
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
  
  public BigInteger getY()
  {
    return y;
  }
  
  public int hashCode()
  {
    return 
      getY().hashCode() ^ getParams().getG().hashCode() ^ getParams().getP().hashCode() ^ getParams().getL();
  }
  

  public boolean equals(Object o)
  {
    if (!(o instanceof DHPublicKey))
    {
      return false;
    }
    
    DHPublicKey other = (DHPublicKey)o;
    
    return (getY().equals(other.getY())) && 
      (getParams().getG().equals(other.getParams().getG())) && 
      (getParams().getP().equals(other.getParams().getP())) && 
      (getParams().getL() == other.getParams().getL());
  }
  

  private void readObject(ObjectInputStream in)
    throws IOException, ClassNotFoundException
  {
    in.defaultReadObject();
    
    elSpec = new ElGamalParameterSpec((BigInteger)in.readObject(), (BigInteger)in.readObject());
  }
  

  private void writeObject(ObjectOutputStream out)
    throws IOException
  {
    out.defaultWriteObject();
    
    out.writeObject(elSpec.getP());
    out.writeObject(elSpec.getG());
  }
}
