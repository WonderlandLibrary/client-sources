package org.spongycastle.pqc.crypto.rainbow;

import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.pqc.crypto.rainbow.util.ComputeInField;
import org.spongycastle.pqc.crypto.rainbow.util.GF2Field;











public class RainbowKeyPairGenerator
  implements AsymmetricCipherKeyPairGenerator
{
  private boolean initialized = false;
  

  private SecureRandom sr;
  

  private RainbowKeyGenerationParameters rainbowParams;
  

  private short[][] A1;
  

  private short[][] A1inv;
  

  private short[] b1;
  

  private short[][] A2;
  

  private short[][] A2inv;
  

  private short[] b2;
  

  private int numOfLayers;
  
  private Layer[] layers;
  
  private int[] vi;
  
  private short[][] pub_quadratic;
  
  private short[][] pub_singular;
  
  private short[] pub_scalar;
  

  public RainbowKeyPairGenerator() {}
  

  public AsymmetricCipherKeyPair genKeyPair()
  {
    if (!initialized)
    {
      initializeDefault();
    }
    

    keygen();
    

    RainbowPrivateKeyParameters privKey = new RainbowPrivateKeyParameters(A1inv, b1, A2inv, b2, vi, layers);
    


    RainbowPublicKeyParameters pubKey = new RainbowPublicKeyParameters(vi[(vi.length - 1)] - vi[0], pub_quadratic, pub_singular, pub_scalar);
    
    return new AsymmetricCipherKeyPair(pubKey, privKey);
  }
  


  public void initialize(KeyGenerationParameters param)
  {
    rainbowParams = ((RainbowKeyGenerationParameters)param);
    

    sr = rainbowParams.getRandom();
    

    vi = rainbowParams.getParameters().getVi();
    numOfLayers = rainbowParams.getParameters().getNumOfLayers();
    
    initialized = true;
  }
  
  private void initializeDefault()
  {
    RainbowKeyGenerationParameters rbKGParams = new RainbowKeyGenerationParameters(new SecureRandom(), new RainbowParameters());
    initialize(rbKGParams);
  }
  




  private void keygen()
  {
    generateL1();
    generateL2();
    generateF();
    computePublicKey();
  }
  











  private void generateL1()
  {
    int dim = vi[(vi.length - 1)] - vi[0];
    A1 = new short[dim][dim];
    A1inv = ((short[][])null);
    ComputeInField c = new ComputeInField();
    

    while (A1inv == null)
    {
      for (int i = 0; i < dim; i++)
      {
        for (int j = 0; j < dim; j++)
        {
          A1[i][j] = ((short)(sr.nextInt() & 0xFF));
        }
      }
      A1inv = c.inverse(A1);
    }
    

    b1 = new short[dim];
    for (int i = 0; i < dim; i++)
    {
      b1[i] = ((short)(sr.nextInt() & 0xFF));
    }
  }
  











  private void generateL2()
  {
    int dim = vi[(vi.length - 1)];
    A2 = new short[dim][dim];
    A2inv = ((short[][])null);
    ComputeInField c = new ComputeInField();
    

    while (A2inv == null)
    {
      for (int i = 0; i < dim; i++)
      {
        for (int j = 0; j < dim; j++)
        {
          A2[i][j] = ((short)(sr.nextInt() & 0xFF));
        }
      }
      A2inv = c.inverse(A2);
    }
    
    b2 = new short[dim];
    for (int i = 0; i < dim; i++)
    {
      b2[i] = ((short)(sr.nextInt() & 0xFF));
    }
  }
  










  private void generateF()
  {
    layers = new Layer[numOfLayers];
    for (int i = 0; i < numOfLayers; i++)
    {
      layers[i] = new Layer(vi[i], vi[(i + 1)], sr);
    }
  }
  










  private void computePublicKey()
  {
    ComputeInField c = new ComputeInField();
    int rows = vi[(vi.length - 1)] - vi[0];
    int vars = vi[(vi.length - 1)];
    
    short[][][] coeff_quadratic_3dim = new short[rows][vars][vars];
    pub_singular = new short[rows][vars];
    pub_scalar = new short[rows];
    







    int oils = 0;
    int vins = 0;
    int crnt_row = 0;
    
    short[] vect_tmp = new short[vars];
    short sclr_tmp = 0;
    

    for (int l = 0; l < layers.length; l++)
    {

      short[][][] coeff_alpha = layers[l].getCoeffAlpha();
      short[][][] coeff_beta = layers[l].getCoeffBeta();
      short[][] coeff_gamma = layers[l].getCoeffGamma();
      short[] coeff_eta = layers[l].getCoeffEta();
      oils = coeff_alpha[0].length;
      vins = coeff_beta[0].length;
      
      for (int p = 0; p < oils; p++)
      {

        for (int x1 = 0; x1 < oils; x1++)
        {
          for (int x2 = 0; x2 < vins; x2++)
          {

            vect_tmp = c.multVect(coeff_alpha[p][x1][x2], A2[(x1 + vins)]);
            
            coeff_quadratic_3dim[(crnt_row + p)] = c.addSquareMatrix(coeff_quadratic_3dim[(crnt_row + p)], c
            
              .multVects(vect_tmp, A2[x2]));
            
            vect_tmp = c.multVect(b2[x2], vect_tmp);
            pub_singular[(crnt_row + p)] = c.addVect(vect_tmp, pub_singular[(crnt_row + p)]);
            

            vect_tmp = c.multVect(coeff_alpha[p][x1][x2], A2[x2]);
            
            vect_tmp = c.multVect(b2[(x1 + vins)], vect_tmp);
            pub_singular[(crnt_row + p)] = c.addVect(vect_tmp, pub_singular[(crnt_row + p)]);
            

            sclr_tmp = GF2Field.multElem(coeff_alpha[p][x1][x2], b2[(x1 + vins)]);
            
            pub_scalar[(crnt_row + p)] = GF2Field.addElem(pub_scalar[(crnt_row + p)], 
            
              GF2Field.multElem(sclr_tmp, b2[x2]));
          }
        }
        
        for (int x1 = 0; x1 < vins; x1++)
        {
          for (int x2 = 0; x2 < vins; x2++)
          {

            vect_tmp = c.multVect(coeff_beta[p][x1][x2], A2[x1]);
            
            coeff_quadratic_3dim[(crnt_row + p)] = c.addSquareMatrix(coeff_quadratic_3dim[(crnt_row + p)], c
            
              .multVects(vect_tmp, A2[x2]));
            
            vect_tmp = c.multVect(b2[x2], vect_tmp);
            pub_singular[(crnt_row + p)] = c.addVect(vect_tmp, pub_singular[(crnt_row + p)]);
            

            vect_tmp = c.multVect(coeff_beta[p][x1][x2], A2[x2]);
            
            vect_tmp = c.multVect(b2[x1], vect_tmp);
            pub_singular[(crnt_row + p)] = c.addVect(vect_tmp, pub_singular[(crnt_row + p)]);
            

            sclr_tmp = GF2Field.multElem(coeff_beta[p][x1][x2], b2[x1]);
            
            pub_scalar[(crnt_row + p)] = GF2Field.addElem(pub_scalar[(crnt_row + p)], 
            
              GF2Field.multElem(sclr_tmp, b2[x2]));
          }
        }
        
        for (int n = 0; n < vins + oils; n++)
        {

          vect_tmp = c.multVect(coeff_gamma[p][n], A2[n]);
          pub_singular[(crnt_row + p)] = c.addVect(vect_tmp, pub_singular[(crnt_row + p)]);
          

          pub_scalar[(crnt_row + p)] = GF2Field.addElem(pub_scalar[(crnt_row + p)], 
            GF2Field.multElem(coeff_gamma[p][n], b2[n]));
        }
        

        pub_scalar[(crnt_row + p)] = GF2Field.addElem(pub_scalar[(crnt_row + p)], coeff_eta[p]);
      }
      
      crnt_row += oils;
    }
    



    short[][][] tmp_c_quad = new short[rows][vars][vars];
    short[][] tmp_c_sing = new short[rows][vars];
    short[] tmp_c_scal = new short[rows];
    for (int r = 0; r < rows; r++)
    {
      for (int q = 0; q < A1.length; q++)
      {
        tmp_c_quad[r] = c.addSquareMatrix(tmp_c_quad[r], c
          .multMatrix(A1[r][q], coeff_quadratic_3dim[q]));
        tmp_c_sing[r] = c.addVect(tmp_c_sing[r], c.multVect(A1[r][q], pub_singular[q]));
        
        tmp_c_scal[r] = GF2Field.addElem(tmp_c_scal[r], 
          GF2Field.multElem(A1[r][q], pub_scalar[q]));
      }
      tmp_c_scal[r] = GF2Field.addElem(tmp_c_scal[r], b1[r]);
    }
    
    coeff_quadratic_3dim = tmp_c_quad;
    pub_singular = tmp_c_sing;
    pub_scalar = tmp_c_scal;
    
    compactPublicKey(coeff_quadratic_3dim);
  }
  










  private void compactPublicKey(short[][][] coeff_quadratic_to_compact)
  {
    int polynomials = coeff_quadratic_to_compact.length;
    int n = coeff_quadratic_to_compact[0].length;
    int entries = n * (n + 1) / 2;
    pub_quadratic = new short[polynomials][entries];
    int offset = 0;
    
    for (int p = 0; p < polynomials; p++)
    {
      offset = 0;
      for (int x = 0; x < n; x++)
      {
        for (int y = x; y < n; y++)
        {
          if (y == x)
          {
            pub_quadratic[p][offset] = coeff_quadratic_to_compact[p][x][y];
          }
          else
          {
            pub_quadratic[p][offset] = GF2Field.addElem(coeff_quadratic_to_compact[p][x][y], coeff_quadratic_to_compact[p][y][x]);
          }
          

          offset++;
        }
      }
    }
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
