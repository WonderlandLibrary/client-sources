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
import org.spongycastle.pqc.math.linearalgebra.PolynomialRingGF2m;































public class McElieceKeyPairGenerator
  implements AsymmetricCipherKeyPairGenerator
{
  private static final String OID = "1.3.6.1.4.1.8301.3.1.3.4.1";
  private McElieceKeyGenerationParameters mcElieceParams;
  private int m;
  private int n;
  private int t;
  private int fieldPoly;
  private SecureRandom random;
  private boolean initialized = false;
  

  public McElieceKeyPairGenerator() {}
  

  private void initializeDefault()
  {
    McElieceKeyGenerationParameters mcParams = new McElieceKeyGenerationParameters(new SecureRandom(), new McElieceParameters());
    initialize(mcParams);
  }
  

  private void initialize(KeyGenerationParameters param)
  {
    mcElieceParams = ((McElieceKeyGenerationParameters)param);
    

    random = new SecureRandom();
    
    m = mcElieceParams.getParameters().getM();
    n = mcElieceParams.getParameters().getN();
    t = mcElieceParams.getParameters().getT();
    fieldPoly = mcElieceParams.getParameters().getFieldPoly();
    initialized = true;
  }
  


  private AsymmetricCipherKeyPair genKeyPair()
  {
    if (!initialized)
    {
      initializeDefault();
    }
    

    GF2mField field = new GF2mField(m, fieldPoly);
    

    PolynomialGF2mSmallM gp = new PolynomialGF2mSmallM(field, t, 'I', random);
    
    PolynomialRingGF2m ring = new PolynomialRingGF2m(field, gp);
    

    PolynomialGF2mSmallM[] sqRootMatrix = ring.getSquareRootMatrix();
    

    GF2Matrix h = GoppaCode.createCanonicalCheckMatrix(field, gp);
    

    GoppaCode.MaMaPe mmp = GoppaCode.computeSystematicForm(h, random);
    GF2Matrix shortH = mmp.getSecondMatrix();
    Permutation p1 = mmp.getPermutation();
    

    GF2Matrix shortG = (GF2Matrix)shortH.computeTranspose();
    

    GF2Matrix gPrime = shortG.extendLeftCompactForm();
    

    int k = shortG.getNumRows();
    


    GF2Matrix[] matrixSandInverse = GF2Matrix.createRandomRegularMatrixAndItsInverse(k, random);
    

    Permutation p2 = new Permutation(n, random);
    

    GF2Matrix g = (GF2Matrix)matrixSandInverse[0].rightMultiply(gPrime);
    g = (GF2Matrix)g.rightMultiply(p2);
    


    McEliecePublicKeyParameters pubKey = new McEliecePublicKeyParameters(n, t, g);
    McEliecePrivateKeyParameters privKey = new McEliecePrivateKeyParameters(n, k, field, gp, p1, p2, matrixSandInverse[1]);
    

    return new AsymmetricCipherKeyPair(pubKey, privKey);
  }
  
  public void init(KeyGenerationParameters param)
  {
    initialize(param);
  }
  

  public AsymmetricCipherKeyPair generateKeyPair()
  {
    return genKeyPair();
  }
}
