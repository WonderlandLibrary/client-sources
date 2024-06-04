package org.spongycastle.pqc.crypto.gmss;

import java.io.PrintStream;
import java.security.SecureRandom;
import java.util.Vector;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.pqc.crypto.gmss.util.GMSSRandom;
import org.spongycastle.pqc.crypto.gmss.util.WinternitzOTSVerify;
import org.spongycastle.pqc.crypto.gmss.util.WinternitzOTSignature;













































public class GMSSKeyPairGenerator
  implements AsymmetricCipherKeyPairGenerator
{
  private GMSSRandom gmssRandom;
  private Digest messDigestTree;
  private byte[][] currentSeeds;
  private byte[][] nextNextSeeds;
  private byte[][] currentRootSigs;
  private GMSSDigestProvider digestProvider;
  private int mdLength;
  private int numLayer;
  private boolean initialized = false;
  



  private GMSSParameters gmssPS;
  



  private int[] heightOfTrees;
  



  private int[] otsIndex;
  



  private int[] K;
  



  private GMSSKeyGenerationParameters gmssParams;
  



  public static final String OID = "1.3.6.1.4.1.8301.3.1.3.3";
  



  public GMSSKeyPairGenerator(GMSSDigestProvider digestProvider)
  {
    this.digestProvider = digestProvider;
    messDigestTree = digestProvider.get();
    

    mdLength = messDigestTree.getDigestSize();
    
    gmssRandom = new GMSSRandom(messDigestTree);
  }
  







  private AsymmetricCipherKeyPair genKeyPair()
  {
    if (!initialized)
    {
      initializeDefault();
    }
    

    byte[][][] currentAuthPaths = new byte[numLayer][][];
    byte[][][] nextAuthPaths = new byte[numLayer - 1][][];
    Treehash[][] currentTreehash = new Treehash[numLayer][];
    Treehash[][] nextTreehash = new Treehash[numLayer - 1][];
    
    Vector[] currentStack = new Vector[numLayer];
    Vector[] nextStack = new Vector[numLayer - 1];
    
    Vector[][] currentRetain = new Vector[numLayer][];
    Vector[][] nextRetain = new Vector[numLayer - 1][];
    
    for (int i = 0; i < numLayer; i++)
    {
      currentAuthPaths[i] = new byte[heightOfTrees[i]][mdLength];
      currentTreehash[i] = new Treehash[heightOfTrees[i] - K[i]];
      
      if (i > 0)
      {
        nextAuthPaths[(i - 1)] = new byte[heightOfTrees[i]][mdLength];
        nextTreehash[(i - 1)] = new Treehash[heightOfTrees[i] - K[i]];
      }
      
      currentStack[i] = new Vector();
      if (i > 0)
      {
        nextStack[(i - 1)] = new Vector();
      }
    }
    

    byte[][] currentRoots = new byte[numLayer][mdLength];
    byte[][] nextRoots = new byte[numLayer - 1][mdLength];
    
    byte[][] seeds = new byte[numLayer][mdLength];
    

    for (int i = 0; i < numLayer; i++)
    {
      System.arraycopy(currentSeeds[i], 0, seeds[i], 0, mdLength);
    }
    

    currentRootSigs = new byte[numLayer - 1][mdLength];
    





    for (int h = numLayer - 1; h >= 0; h--)
    {
      GMSSRootCalc tree = new GMSSRootCalc(heightOfTrees[h], K[h], digestProvider);
      

      try
      {
        if (h == numLayer - 1)
        {
          tree = generateCurrentAuthpathAndRoot(null, currentStack[h], seeds[h], h);

        }
        else
        {

          tree = generateCurrentAuthpathAndRoot(currentRoots[(h + 1)], currentStack[h], seeds[h], h);
        }
        
      }
      catch (Exception e1)
      {
        e1.printStackTrace();
      }
      

      for (int i = 0; i < heightOfTrees[h]; i++)
      {
        System.arraycopy(tree.getAuthPath()[i], 0, currentAuthPaths[h][i], 0, mdLength);
      }
      currentRetain[h] = tree.getRetain();
      currentTreehash[h] = tree.getTreehash();
      System.arraycopy(tree.getRoot(), 0, currentRoots[h], 0, mdLength);
    }
    


    for (int h = numLayer - 2; h >= 0; h--)
    {
      GMSSRootCalc tree = generateNextAuthpathAndRoot(nextStack[h], seeds[(h + 1)], h + 1);
      

      for (int i = 0; i < heightOfTrees[(h + 1)]; i++)
      {
        System.arraycopy(tree.getAuthPath()[i], 0, nextAuthPaths[h][i], 0, mdLength);
      }
      nextRetain[h] = tree.getRetain();
      nextTreehash[h] = tree.getTreehash();
      System.arraycopy(tree.getRoot(), 0, nextRoots[h], 0, mdLength);
      


      System.arraycopy(seeds[(h + 1)], 0, nextNextSeeds[h], 0, mdLength);
    }
    


    GMSSPublicKeyParameters publicKey = new GMSSPublicKeyParameters(currentRoots[0], gmssPS);
    

    GMSSPrivateKeyParameters privateKey = new GMSSPrivateKeyParameters(currentSeeds, nextNextSeeds, currentAuthPaths, nextAuthPaths, currentTreehash, nextTreehash, currentStack, nextStack, currentRetain, nextRetain, nextRoots, currentRootSigs, gmssPS, digestProvider);
    


    return new AsymmetricCipherKeyPair(publicKey, privateKey);
  }
  









  private GMSSRootCalc generateCurrentAuthpathAndRoot(byte[] lowerRoot, Vector currentStack, byte[] seed, int h)
  {
    byte[] help = new byte[mdLength];
    
    byte[] OTSseed = new byte[mdLength];
    OTSseed = gmssRandom.nextSeed(seed);
    




    GMSSRootCalc treeToConstruct = new GMSSRootCalc(heightOfTrees[h], K[h], digestProvider);
    
    treeToConstruct.initialize(currentStack);
    
    WinternitzOTSignature ots;
    if (h == numLayer - 1)
    {
      WinternitzOTSignature ots = new WinternitzOTSignature(OTSseed, digestProvider.get(), otsIndex[h]);
      help = ots.getPublicKey();


    }
    else
    {


      ots = new WinternitzOTSignature(OTSseed, digestProvider.get(), otsIndex[h]);
      currentRootSigs[h] = ots.getSignature(lowerRoot);
      WinternitzOTSVerify otsver = new WinternitzOTSVerify(digestProvider.get(), otsIndex[h]);
      help = otsver.Verify(lowerRoot, currentRootSigs[h]);
    }
    
    treeToConstruct.update(help);
    
    int seedForTreehashIndex = 3;
    int count = 0;
    

    for (int i = 1; i < 1 << heightOfTrees[h]; i++)
    {

      if ((i == seedForTreehashIndex) && (count < heightOfTrees[h] - K[h]))
      {
        treeToConstruct.initializeTreehashSeed(seed, count);
        seedForTreehashIndex *= 2;
        count++;
      }
      
      OTSseed = gmssRandom.nextSeed(seed);
      ots = new WinternitzOTSignature(OTSseed, digestProvider.get(), otsIndex[h]);
      treeToConstruct.update(ots.getPublicKey());
    }
    
    if (treeToConstruct.wasFinished())
    {
      return treeToConstruct;
    }
    System.err.println("Baum noch nicht fertig konstruiert!!!");
    return null;
  }
  








  private GMSSRootCalc generateNextAuthpathAndRoot(Vector nextStack, byte[] seed, int h)
  {
    byte[] OTSseed = new byte[numLayer];
    



    GMSSRootCalc treeToConstruct = new GMSSRootCalc(heightOfTrees[h], K[h], digestProvider);
    treeToConstruct.initialize(nextStack);
    
    int seedForTreehashIndex = 3;
    int count = 0;
    

    for (int i = 0; i < 1 << heightOfTrees[h]; i++)
    {

      if ((i == seedForTreehashIndex) && (count < heightOfTrees[h] - K[h]))
      {
        treeToConstruct.initializeTreehashSeed(seed, count);
        seedForTreehashIndex *= 2;
        count++;
      }
      
      OTSseed = gmssRandom.nextSeed(seed);
      WinternitzOTSignature ots = new WinternitzOTSignature(OTSseed, digestProvider.get(), otsIndex[h]);
      treeToConstruct.update(ots.getPublicKey());
    }
    
    if (treeToConstruct.wasFinished())
    {
      return treeToConstruct;
    }
    System.err.println("N�chster Baum noch nicht fertig konstruiert!!!");
    return null;
  }
  








  public void initialize(int keySize, SecureRandom secureRandom)
  {
    KeyGenerationParameters kgp;
    







    KeyGenerationParameters kgp;
    






    if (keySize <= 10)
    {
      int[] defh = { 10 };
      int[] defw = { 3 };
      int[] defk = { 2 };
      
      kgp = new GMSSKeyGenerationParameters(secureRandom, new GMSSParameters(defh.length, defh, defw, defk));
    } else { KeyGenerationParameters kgp;
      if (keySize <= 20)
      {
        int[] defh = { 10, 10 };
        int[] defw = { 5, 4 };
        int[] defk = { 2, 2 };
        kgp = new GMSSKeyGenerationParameters(secureRandom, new GMSSParameters(defh.length, defh, defw, defk));
      }
      else
      {
        int[] defh = { 10, 10, 10, 10 };
        int[] defw = { 9, 9, 9, 3 };
        int[] defk = { 2, 2, 2, 2 };
        kgp = new GMSSKeyGenerationParameters(secureRandom, new GMSSParameters(defh.length, defh, defw, defk));
      }
    }
    
    initialize(kgp);
  }
  






  public void initialize(KeyGenerationParameters param)
  {
    gmssParams = ((GMSSKeyGenerationParameters)param);
    


    gmssPS = new GMSSParameters(gmssParams.getParameters().getNumOfLayers(), gmssParams.getParameters().getHeightOfTrees(), gmssParams.getParameters().getWinternitzParameter(), gmssParams.getParameters().getK());
    
    numLayer = gmssPS.getNumOfLayers();
    heightOfTrees = gmssPS.getHeightOfTrees();
    otsIndex = gmssPS.getWinternitzParameter();
    K = gmssPS.getK();
    

    currentSeeds = new byte[numLayer][mdLength];
    nextNextSeeds = new byte[numLayer - 1][mdLength];
    

    SecureRandom secRan = new SecureRandom();
    

    for (int i = 0; i < numLayer; i++)
    {
      secRan.nextBytes(currentSeeds[i]);
      gmssRandom.nextSeed(currentSeeds[i]);
    }
    
    initialized = true;
  }
  




  private void initializeDefault()
  {
    int[] defh = { 10, 10, 10, 10 };
    int[] defw = { 3, 3, 3, 3 };
    int[] defk = { 2, 2, 2, 2 };
    
    KeyGenerationParameters kgp = new GMSSKeyGenerationParameters(new SecureRandom(), new GMSSParameters(defh.length, defh, defw, defk));
    initialize(kgp);
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
