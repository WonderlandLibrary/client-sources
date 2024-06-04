package org.spongycastle.pqc.crypto.rainbow;

import java.security.SecureRandom;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.pqc.crypto.MessageSigner;
import org.spongycastle.pqc.crypto.rainbow.util.ComputeInField;
import org.spongycastle.pqc.crypto.rainbow.util.GF2Field;



















public class RainbowSigner
  implements MessageSigner
{
  private static final int MAXITS = 65536;
  private SecureRandom random;
  int signableDocumentLength;
  private short[] x;
  private ComputeInField cf = new ComputeInField();
  RainbowKeyParameters key;
  
  public RainbowSigner() {}
  
  public void init(boolean forSigning, CipherParameters param)
  {
    if (forSigning)
    {
      if ((param instanceof ParametersWithRandom))
      {
        ParametersWithRandom rParam = (ParametersWithRandom)param;
        
        random = rParam.getRandom();
        key = ((RainbowPrivateKeyParameters)rParam.getParameters());

      }
      else
      {

        random = new SecureRandom();
        key = ((RainbowPrivateKeyParameters)param);
      }
      
    }
    else {
      key = ((RainbowPublicKeyParameters)param);
    }
    
    signableDocumentLength = key.getDocLength();
  }
  












  private short[] initSign(Layer[] layer, short[] msg)
  {
    short[] tmpVec = new short[msg.length];
    
    tmpVec = cf.addVect(((RainbowPrivateKeyParameters)key).getB1(), msg);
    

    short[] Y_ = cf.multiplyMatrix(((RainbowPrivateKeyParameters)key).getInvA1(), tmpVec);
    

    for (int i = 0; i < layer[0].getVi(); i++)
    {
      x[i] = ((short)random.nextInt());
      x[i] = ((short)(x[i] & 0xFF));
    }
    
    return Y_;
  }
  












  public byte[] generateSignature(byte[] message)
  {
    Layer[] layer = ((RainbowPrivateKeyParameters)key).getLayers();
    int numberOfLayers = layer.length;
    
    x = new short[((RainbowPrivateKeyParameters)key).getInvA2().length];
    










    byte[] S = new byte[layer[(numberOfLayers - 1)].getViNext()];
    
    short[] msgHashVals = makeMessageRepresentative(message);
    int itCount = 0;
    


    do
    {
      boolean ok = true;
      int counter = 0;
      try
      {
        short[] Y_ = initSign(layer, msgHashVals);
        
        for (int i = 0; i < numberOfLayers; i++)
        {

          short[] y_i = new short[layer[i].getOi()];
          short[] solVec = new short[layer[i].getOi()];
          

          for (int k = 0; k < layer[i].getOi(); k++)
          {
            y_i[k] = Y_[counter];
            counter++;
          }
          




          solVec = cf.solveEquation(layer[i].plugInVinegars(x), y_i);
          
          if (solVec == null)
          {
            throw new Exception("LES is not solveable!");
          }
          

          for (int j = 0; j < solVec.length; j++)
          {
            x[(layer[i].getVi() + j)] = solVec[j];
          }
        }
        

        short[] tmpVec = cf.addVect(((RainbowPrivateKeyParameters)key).getB2(), x);
        short[] signature = cf.multiplyMatrix(((RainbowPrivateKeyParameters)key).getInvA2(), tmpVec);
        

        for (int i = 0; i < S.length; i++)
        {
          S[i] = ((byte)signature[i]);
        }
        
      }
      catch (Exception se)
      {
        ok = false;
      }
      
      if (ok) break; itCount++; } while (itCount < 65536);
    

    if (itCount == 65536)
    {
      throw new IllegalStateException("unable to generate signature - LES not solvable");
    }
    
    return S;
  }
  








  public boolean verifySignature(byte[] message, byte[] signature)
  {
    short[] sigInt = new short[signature.length];
    

    for (int i = 0; i < signature.length; i++)
    {
      short tmp = (short)signature[i];
      tmp = (short)(tmp & 0xFF);
      sigInt[i] = tmp;
    }
    
    short[] msgHashVal = makeMessageRepresentative(message);
    

    short[] verificationResult = verifySignatureIntern(sigInt);
    

    boolean verified = true;
    if (msgHashVal.length != verificationResult.length)
    {
      return false;
    }
    for (int i = 0; i < msgHashVal.length; i++)
    {
      verified = (verified) && (msgHashVal[i] == verificationResult[i]);
    }
    
    return verified;
  }
  







  private short[] verifySignatureIntern(short[] signature)
  {
    short[][] coeff_quadratic = ((RainbowPublicKeyParameters)key).getCoeffQuadratic();
    short[][] coeff_singular = ((RainbowPublicKeyParameters)key).getCoeffSingular();
    short[] coeff_scalar = ((RainbowPublicKeyParameters)key).getCoeffScalar();
    
    short[] rslt = new short[coeff_quadratic.length];
    int n = coeff_singular[0].length;
    int offset = 0;
    short tmp = 0;
    
    for (int p = 0; p < coeff_quadratic.length; p++)
    {
      offset = 0;
      for (int x = 0; x < n; x++)
      {

        for (int y = x; y < n; y++)
        {
          tmp = GF2Field.multElem(coeff_quadratic[p][offset], 
            GF2Field.multElem(signature[x], signature[y]));
          rslt[p] = GF2Field.addElem(rslt[p], tmp);
          offset++;
        }
        
        tmp = GF2Field.multElem(coeff_singular[p][x], signature[x]);
        rslt[p] = GF2Field.addElem(rslt[p], tmp);
      }
      
      rslt[p] = GF2Field.addElem(rslt[p], coeff_scalar[p]);
    }
    
    return rslt;
  }
  








  private short[] makeMessageRepresentative(byte[] message)
  {
    short[] output = new short[signableDocumentLength];
    
    int h = 0;
    int i = 0;
    do
    {
      if (i >= message.length) {
        break;
      }
      
      output[i] = ((short)message[h]); int 
        tmp33_31 = i; short[] tmp33_30 = output;tmp33_30[tmp33_31] = ((short)(tmp33_30[tmp33_31] & 0xFF));
      h++;
      i++;
    }
    while (i < output.length);
    
    return output;
  }
}
