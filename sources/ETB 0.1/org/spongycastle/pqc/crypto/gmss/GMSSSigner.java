package org.spongycastle.pqc.crypto.gmss;

import java.io.PrintStream;
import java.security.SecureRandom;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.pqc.crypto.MessageSigner;
import org.spongycastle.pqc.crypto.gmss.util.GMSSRandom;
import org.spongycastle.pqc.crypto.gmss.util.GMSSUtil;
import org.spongycastle.pqc.crypto.gmss.util.WinternitzOTSVerify;
import org.spongycastle.pqc.crypto.gmss.util.WinternitzOTSignature;
import org.spongycastle.util.Arrays;













public class GMSSSigner
  implements MessageSigner
{
  private GMSSUtil gmssUtil = new GMSSUtil();
  




  private byte[] pubKeyBytes;
  




  private Digest messDigestTrees;
  



  private int mdLength;
  



  private int numLayer;
  



  private Digest messDigestOTS;
  



  private WinternitzOTSignature ots;
  



  private GMSSDigestProvider digestProvider;
  



  private int[] index;
  



  private byte[][][] currentAuthPaths;
  



  private byte[][] subtreeRootSig;
  



  private GMSSParameters gmssPS;
  



  private GMSSRandom gmssRandom;
  



  GMSSKeyParameters key;
  



  private SecureRandom random;
  




  public GMSSSigner(GMSSDigestProvider digest)
  {
    digestProvider = digest;
    messDigestTrees = digest.get();
    messDigestOTS = messDigestTrees;
    mdLength = messDigestTrees.getDigestSize();
    gmssRandom = new GMSSRandom(messDigestTrees);
  }
  


  public void init(boolean forSigning, CipherParameters param)
  {
    if (forSigning)
    {
      if ((param instanceof ParametersWithRandom))
      {
        ParametersWithRandom rParam = (ParametersWithRandom)param;
        

        random = rParam.getRandom();
        key = ((GMSSPrivateKeyParameters)rParam.getParameters());
        initSign();

      }
      else
      {

        random = new SecureRandom();
        key = ((GMSSPrivateKeyParameters)param);
        initSign();
      }
    }
    else
    {
      key = ((GMSSPublicKeyParameters)param);
      initVerify();
    }
  }
  






  private void initSign()
  {
    messDigestTrees.reset();
    

    GMSSPrivateKeyParameters gmssPrivateKey = (GMSSPrivateKeyParameters)key;
    
    if (gmssPrivateKey.isUsed())
    {
      throw new IllegalStateException("Private key already used");
    }
    

    if (gmssPrivateKey.getIndex(0) >= gmssPrivateKey.getNumLeafs(0))
    {
      throw new IllegalStateException("No more signatures can be generated");
    }
    

    gmssPS = gmssPrivateKey.getParameters();
    
    numLayer = gmssPS.getNumOfLayers();
    

    byte[] seed = gmssPrivateKey.getCurrentSeeds()[(numLayer - 1)];
    byte[] OTSSeed = new byte[mdLength];
    byte[] dummy = new byte[mdLength];
    System.arraycopy(seed, 0, dummy, 0, mdLength);
    OTSSeed = gmssRandom.nextSeed(dummy);
    ots = new WinternitzOTSignature(OTSSeed, digestProvider.get(), gmssPS.getWinternitzParameter()[(numLayer - 1)]);
    
    byte[][][] helpCurrentAuthPaths = gmssPrivateKey.getCurrentAuthPaths();
    currentAuthPaths = new byte[numLayer][][];
    

    for (int j = 0; j < numLayer; j++)
    {
      currentAuthPaths[j] = new byte[helpCurrentAuthPaths[j].length][mdLength];
      for (int i = 0; i < helpCurrentAuthPaths[j].length; i++)
      {
        System.arraycopy(helpCurrentAuthPaths[j][i], 0, currentAuthPaths[j][i], 0, mdLength);
      }
    }
    

    index = new int[numLayer];
    System.arraycopy(gmssPrivateKey.getIndex(), 0, index, 0, numLayer);
    


    subtreeRootSig = new byte[numLayer - 1][];
    for (int i = 0; i < numLayer - 1; i++)
    {
      byte[] helpSubtreeRootSig = gmssPrivateKey.getSubtreeRootSig(i);
      subtreeRootSig[i] = new byte[helpSubtreeRootSig.length];
      System.arraycopy(helpSubtreeRootSig, 0, subtreeRootSig[i], 0, helpSubtreeRootSig.length);
    }
    
    gmssPrivateKey.markUsed();
  }
  






  public byte[] generateSignature(byte[] message)
  {
    byte[] otsSig = new byte[mdLength];
    


    otsSig = ots.getSignature(message);
    

    byte[] authPathBytes = gmssUtil.concatenateArray(currentAuthPaths[(numLayer - 1)]);
    

    byte[] indexBytes = gmssUtil.intToBytesLittleEndian(index[(numLayer - 1)]);
    

    byte[] gmssSigFirstPart = new byte[indexBytes.length + otsSig.length + authPathBytes.length];
    System.arraycopy(indexBytes, 0, gmssSigFirstPart, 0, indexBytes.length);
    System.arraycopy(otsSig, 0, gmssSigFirstPart, indexBytes.length, otsSig.length);
    System.arraycopy(authPathBytes, 0, gmssSigFirstPart, indexBytes.length + otsSig.length, authPathBytes.length);
    



    byte[] gmssSigNextPart = new byte[0];
    
    for (int i = numLayer - 1 - 1; i >= 0; i--)
    {


      authPathBytes = gmssUtil.concatenateArray(currentAuthPaths[i]);
      

      indexBytes = gmssUtil.intToBytesLittleEndian(index[i]);
      



      byte[] helpGmssSig = new byte[gmssSigNextPart.length];
      System.arraycopy(gmssSigNextPart, 0, helpGmssSig, 0, gmssSigNextPart.length);
      
      gmssSigNextPart = new byte[helpGmssSig.length + indexBytes.length + subtreeRootSig[i].length + authPathBytes.length];
      

      System.arraycopy(helpGmssSig, 0, gmssSigNextPart, 0, helpGmssSig.length);
      System.arraycopy(indexBytes, 0, gmssSigNextPart, helpGmssSig.length, indexBytes.length);
      System.arraycopy(subtreeRootSig[i], 0, gmssSigNextPart, helpGmssSig.length + indexBytes.length, subtreeRootSig[i].length);
      System.arraycopy(authPathBytes, 0, gmssSigNextPart, helpGmssSig.length + indexBytes.length + subtreeRootSig[i].length, authPathBytes.length);
    }
    



    byte[] gmssSig = new byte[gmssSigFirstPart.length + gmssSigNextPart.length];
    System.arraycopy(gmssSigFirstPart, 0, gmssSig, 0, gmssSigFirstPart.length);
    System.arraycopy(gmssSigNextPart, 0, gmssSig, gmssSigFirstPart.length, gmssSigNextPart.length);
    

    return gmssSig;
  }
  



  private void initVerify()
  {
    messDigestTrees.reset();
    
    GMSSPublicKeyParameters gmssPublicKey = (GMSSPublicKeyParameters)key;
    pubKeyBytes = gmssPublicKey.getPublicKey();
    gmssPS = gmssPublicKey.getParameters();
    
    numLayer = gmssPS.getNumOfLayers();
  }
  











  public boolean verifySignature(byte[] message, byte[] signature)
  {
    boolean success = false;
    
    messDigestOTS.reset();
    


    byte[] help = message;
    




    int nextEntry = 0;
    




    for (int j = numLayer - 1; j >= 0; j--)
    {
      WinternitzOTSVerify otsVerify = new WinternitzOTSVerify(digestProvider.get(), gmssPS.getWinternitzParameter()[j]);
      int otsSigLength = otsVerify.getSignatureLength();
      
      message = help;
      
      int index = gmssUtil.bytesToIntLittleEndian(signature, nextEntry);
      

      nextEntry += 4;
      

      byte[] otsSig = new byte[otsSigLength];
      System.arraycopy(signature, nextEntry, otsSig, 0, otsSigLength);
      nextEntry += otsSigLength;
      

      byte[] otsPublicKey = otsVerify.Verify(message, otsSig);
      

      if (otsPublicKey == null)
      {
        System.err.println("OTS Public Key is null in GMSSSignature.verify");
        return false;
      }
      

      byte[][] authPath = new byte[gmssPS.getHeightOfTrees()[j]][mdLength];
      for (int i = 0; i < authPath.length; i++)
      {
        System.arraycopy(signature, nextEntry, authPath[i], 0, mdLength);
        nextEntry += mdLength;
      }
      

      help = new byte[mdLength];
      
      help = otsPublicKey;
      
      int count = 1 << authPath.length;
      count += index;
      
      for (int i = 0; i < authPath.length; i++)
      {
        byte[] dest = new byte[mdLength << 1];
        
        if (count % 2 == 0)
        {
          System.arraycopy(help, 0, dest, 0, mdLength);
          System.arraycopy(authPath[i], 0, dest, mdLength, mdLength);
          count /= 2;
        }
        else
        {
          System.arraycopy(authPath[i], 0, dest, 0, mdLength);
          System.arraycopy(help, 0, dest, mdLength, help.length);
          count = (count - 1) / 2;
        }
        messDigestTrees.update(dest, 0, dest.length);
        help = new byte[messDigestTrees.getDigestSize()];
        messDigestTrees.doFinal(help, 0);
      }
    }
    



    if (Arrays.areEqual(pubKeyBytes, help))
    {
      success = true;
    }
    
    return success;
  }
}
