package org.spongycastle.pqc.crypto.mceliece;

import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.pqc.math.linearalgebra.GF2Matrix;
import org.spongycastle.pqc.math.linearalgebra.GF2mField;
import org.spongycastle.pqc.math.linearalgebra.GoppaCode;
import org.spongycastle.pqc.math.linearalgebra.GoppaCode.MaMaPe;
import org.spongycastle.pqc.math.linearalgebra.Permutation;
import org.spongycastle.pqc.math.linearalgebra.PolynomialGF2mSmallM;


























public class McElieceCCA2KeyPairGenerator
  implements AsymmetricCipherKeyPairGenerator
{
  public static final String OID = "1.3.6.1.4.1.8301.3.1.3.4.2";
  private McElieceCCA2KeyGenerationParameters mcElieceCCA2Params;
  private int m;
  private int n;
  private int t;
  private int fieldPoly;
  private SecureRandom random;
  private boolean initialized = false;
  

  public McElieceCCA2KeyPairGenerator() {}
  
  private void initializeDefault()
  {
    McElieceCCA2KeyGenerationParameters mcCCA2Params = new McElieceCCA2KeyGenerationParameters(new SecureRandom(), new McElieceCCA2Parameters());
    init(mcCCA2Params);
  }
  


  public void init(KeyGenerationParameters param)
  {
    mcElieceCCA2Params = ((McElieceCCA2KeyGenerationParameters)param);
    

    random = new SecureRandom();
    
    m = mcElieceCCA2Params.getParameters().getM();
    n = mcElieceCCA2Params.getParameters().getN();
    t = mcElieceCCA2Params.getParameters().getT();
    fieldPoly = mcElieceCCA2Params.getParameters().getFieldPoly();
    initialized = true;
  }
  


  public AsymmetricCipherKeyPair generateKeyPair()
  {
    if (!initialized)
    {
      initializeDefault();
    }
    

    GF2mField field = new GF2mField(m, fieldPoly);
    

    PolynomialGF2mSmallM gp = new PolynomialGF2mSmallM(field, t, 'I', random);
    


    GF2Matrix h = GoppaCode.createCanonicalCheckMatrix(field, gp);
    

    GoppaCode.MaMaPe mmp = GoppaCode.computeSystematicForm(h, random);
    GF2Matrix shortH = mmp.getSecondMatrix();
    Permutation p = mmp.getPermutation();
    

    GF2Matrix shortG = (GF2Matrix)shortH.computeTranspose();
    

    int k = shortG.getNumRows();
    

    McElieceCCA2PublicKeyParameters pubKey = new McElieceCCA2PublicKeyParameters(n, t, shortG, mcElieceCCA2Params.getParameters().getDigest());
    McElieceCCA2PrivateKeyParameters privKey = new McElieceCCA2PrivateKeyParameters(n, k, field, gp, p, mcElieceCCA2Params.getParameters().getDigest());
    

    return new AsymmetricCipherKeyPair(pubKey, privKey);
  }
}
