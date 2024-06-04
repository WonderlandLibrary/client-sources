package org.spongycastle.pqc.asn1;

import java.math.BigInteger;
import java.util.Vector;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.pqc.crypto.gmss.GMSSLeaf;
import org.spongycastle.pqc.crypto.gmss.GMSSParameters;
import org.spongycastle.pqc.crypto.gmss.GMSSRootCalc;
import org.spongycastle.pqc.crypto.gmss.GMSSRootSig;
import org.spongycastle.pqc.crypto.gmss.Treehash;



public class GMSSPrivateKey
  extends ASN1Object
{
  private ASN1Primitive primitive;
  
  private GMSSPrivateKey(ASN1Sequence mtsPrivateKey)
  {
    ASN1Sequence indexPart = (ASN1Sequence)mtsPrivateKey.getObjectAt(0);
    int[] index = new int[indexPart.size()];
    for (int i = 0; i < indexPart.size(); i++)
    {
      index[i] = checkBigIntegerInIntRange(indexPart.getObjectAt(i));
    }
    

    ASN1Sequence curSeedsPart = (ASN1Sequence)mtsPrivateKey.getObjectAt(1);
    byte[][] curSeeds = new byte[curSeedsPart.size()][];
    for (int i = 0; i < curSeeds.length; i++)
    {
      curSeeds[i] = ((DEROctetString)curSeedsPart.getObjectAt(i)).getOctets();
    }
    

    ASN1Sequence nextNextSeedsPart = (ASN1Sequence)mtsPrivateKey.getObjectAt(2);
    byte[][] nextNextSeeds = new byte[nextNextSeedsPart.size()][];
    for (int i = 0; i < nextNextSeeds.length; i++)
    {
      nextNextSeeds[i] = ((DEROctetString)nextNextSeedsPart.getObjectAt(i)).getOctets();
    }
    

    ASN1Sequence curAuthPart0 = (ASN1Sequence)mtsPrivateKey.getObjectAt(3);
    

    byte[][][] curAuth = new byte[curAuthPart0.size()][][];
    for (int i = 0; i < curAuth.length; i++)
    {
      ASN1Sequence curAuthPart1 = (ASN1Sequence)curAuthPart0.getObjectAt(i);
      curAuth[i] = new byte[curAuthPart1.size()][];
      for (int j = 0; j < curAuth[i].length; j++)
      {
        curAuth[i][j] = ((DEROctetString)curAuthPart1.getObjectAt(j)).getOctets();
      }
    }
    

    ASN1Sequence nextAuthPart0 = (ASN1Sequence)mtsPrivateKey.getObjectAt(4);
    

    byte[][][] nextAuth = new byte[nextAuthPart0.size()][][];
    for (int i = 0; i < nextAuth.length; i++)
    {
      ASN1Sequence nextAuthPart1 = (ASN1Sequence)nextAuthPart0.getObjectAt(i);
      nextAuth[i] = new byte[nextAuthPart1.size()][];
      for (int j = 0; j < nextAuth[i].length; j++)
      {
        nextAuth[i][j] = ((DEROctetString)nextAuthPart1.getObjectAt(j)).getOctets();
      }
    }
    

    ASN1Sequence seqOfcurTreehash0 = (ASN1Sequence)mtsPrivateKey.getObjectAt(5);
    





    Treehash[][] curTreehash = new Treehash[seqOfcurTreehash0.size()][];
  }
  

























































































































































































































































































































































































































































































































































































  public GMSSPrivateKey(int[] index, byte[][] currentSeed, byte[][] nextNextSeed, byte[][][] currentAuthPath, byte[][][] nextAuthPath, Treehash[][] currentTreehash, Treehash[][] nextTreehash, Vector[] currentStack, Vector[] nextStack, Vector[][] currentRetain, Vector[][] nextRetain, byte[][][] keep, GMSSLeaf[] nextNextLeaf, GMSSLeaf[] upperLeaf, GMSSLeaf[] upperTreehashLeaf, int[] minTreehash, byte[][] nextRoot, GMSSRootCalc[] nextNextRoot, byte[][] currentRootSig, GMSSRootSig[] nextRootSig, GMSSParameters gmssParameterset, AlgorithmIdentifier digestAlg)
  {
    AlgorithmIdentifier[] names = { digestAlg };
    primitive = encode(index, currentSeed, nextNextSeed, currentAuthPath, nextAuthPath, keep, currentTreehash, nextTreehash, currentStack, nextStack, currentRetain, nextRetain, nextNextLeaf, upperLeaf, upperTreehashLeaf, minTreehash, nextRoot, nextNextRoot, currentRootSig, nextRootSig, gmssParameterset, names);
  }
  











































  private ASN1Primitive encode(int[] index, byte[][] currentSeeds, byte[][] nextNextSeeds, byte[][][] currentAuthPaths, byte[][][] nextAuthPaths, byte[][][] keep, Treehash[][] currentTreehash, Treehash[][] nextTreehash, Vector[] currentStack, Vector[] nextStack, Vector[][] currentRetain, Vector[][] nextRetain, GMSSLeaf[] nextNextLeaf, GMSSLeaf[] upperLeaf, GMSSLeaf[] upperTreehashLeaf, int[] minTreehash, byte[][] nextRoot, GMSSRootCalc[] nextNextRoot, byte[][] currentRootSig, GMSSRootSig[] nextRootSig, GMSSParameters gmssParameterset, AlgorithmIdentifier[] algorithms)
  {
    ASN1EncodableVector result = new ASN1EncodableVector();
    

    ASN1EncodableVector indexPart = new ASN1EncodableVector();
    for (int i = 0; i < index.length; i++)
    {
      indexPart.add(new ASN1Integer(index[i]));
    }
    result.add(new DERSequence(indexPart));
    

    ASN1EncodableVector curSeedsPart = new ASN1EncodableVector();
    for (int i = 0; i < currentSeeds.length; i++)
    {
      curSeedsPart.add(new DEROctetString(currentSeeds[i]));
    }
    result.add(new DERSequence(curSeedsPart));
    

    ASN1EncodableVector nextNextSeedsPart = new ASN1EncodableVector();
    for (int i = 0; i < nextNextSeeds.length; i++)
    {
      nextNextSeedsPart.add(new DEROctetString(nextNextSeeds[i]));
    }
    result.add(new DERSequence(nextNextSeedsPart));
    

    ASN1EncodableVector curAuthPart0 = new ASN1EncodableVector();
    ASN1EncodableVector curAuthPart1 = new ASN1EncodableVector();
    for (int i = 0; i < currentAuthPaths.length; i++)
    {
      for (int j = 0; j < currentAuthPaths[i].length; j++)
      {
        curAuthPart0.add(new DEROctetString(currentAuthPaths[i][j]));
      }
      curAuthPart1.add(new DERSequence(curAuthPart0));
      curAuthPart0 = new ASN1EncodableVector();
    }
    result.add(new DERSequence(curAuthPart1));
    

    ASN1EncodableVector nextAuthPart0 = new ASN1EncodableVector();
    ASN1EncodableVector nextAuthPart1 = new ASN1EncodableVector();
    for (int i = 0; i < nextAuthPaths.length; i++)
    {
      for (int j = 0; j < nextAuthPaths[i].length; j++)
      {
        nextAuthPart0.add(new DEROctetString(nextAuthPaths[i][j]));
      }
      nextAuthPart1.add(new DERSequence(nextAuthPart0));
      nextAuthPart0 = new ASN1EncodableVector();
    }
    result.add(new DERSequence(nextAuthPart1));
    

    ASN1EncodableVector seqOfTreehash0 = new ASN1EncodableVector();
    ASN1EncodableVector seqOfTreehash1 = new ASN1EncodableVector();
    ASN1EncodableVector seqOfStat = new ASN1EncodableVector();
    ASN1EncodableVector seqOfByte = new ASN1EncodableVector();
    ASN1EncodableVector seqOfInt = new ASN1EncodableVector();
    
    for (int i = 0; i < currentTreehash.length; i++)
    {
      for (int j = 0; j < currentTreehash[i].length; j++)
      {
        seqOfStat.add(new DERSequence(algorithms[0]));
        
        int tailLength = currentTreehash[i][j].getStatInt()[1];
        
        seqOfByte.add(new DEROctetString(currentTreehash[i][j]
          .getStatByte()[0]));
        seqOfByte.add(new DEROctetString(currentTreehash[i][j]
          .getStatByte()[1]));
        seqOfByte.add(new DEROctetString(currentTreehash[i][j]
          .getStatByte()[2]));
        for (int k = 0; k < tailLength; k++)
        {
          seqOfByte.add(new DEROctetString(currentTreehash[i][j]
            .getStatByte()[(3 + k)]));
        }
        seqOfStat.add(new DERSequence(seqOfByte));
        seqOfByte = new ASN1EncodableVector();
        
        seqOfInt.add(new ASN1Integer(currentTreehash[i][j]
          .getStatInt()[0]));
        seqOfInt.add(new ASN1Integer(tailLength));
        seqOfInt.add(new ASN1Integer(currentTreehash[i][j]
          .getStatInt()[2]));
        seqOfInt.add(new ASN1Integer(currentTreehash[i][j]
          .getStatInt()[3]));
        seqOfInt.add(new ASN1Integer(currentTreehash[i][j]
          .getStatInt()[4]));
        seqOfInt.add(new ASN1Integer(currentTreehash[i][j]
          .getStatInt()[5]));
        for (int k = 0; k < tailLength; k++)
        {
          seqOfInt.add(new ASN1Integer(currentTreehash[i][j]
            .getStatInt()[(6 + k)]));
        }
        seqOfStat.add(new DERSequence(seqOfInt));
        seqOfInt = new ASN1EncodableVector();
        
        seqOfTreehash1.add(new DERSequence(seqOfStat));
        seqOfStat = new ASN1EncodableVector();
      }
      seqOfTreehash0.add(new DERSequence(seqOfTreehash1));
      seqOfTreehash1 = new ASN1EncodableVector();
    }
    result.add(new DERSequence(seqOfTreehash0));
    

    seqOfTreehash0 = new ASN1EncodableVector();
    seqOfTreehash1 = new ASN1EncodableVector();
    seqOfStat = new ASN1EncodableVector();
    seqOfByte = new ASN1EncodableVector();
    seqOfInt = new ASN1EncodableVector();
    
    for (int i = 0; i < nextTreehash.length; i++)
    {
      for (int j = 0; j < nextTreehash[i].length; j++)
      {
        seqOfStat.add(new DERSequence(algorithms[0]));
        
        int tailLength = nextTreehash[i][j].getStatInt()[1];
        
        seqOfByte.add(new DEROctetString(nextTreehash[i][j]
          .getStatByte()[0]));
        seqOfByte.add(new DEROctetString(nextTreehash[i][j]
          .getStatByte()[1]));
        seqOfByte.add(new DEROctetString(nextTreehash[i][j]
          .getStatByte()[2]));
        for (int k = 0; k < tailLength; k++)
        {
          seqOfByte.add(new DEROctetString(nextTreehash[i][j]
            .getStatByte()[(3 + k)]));
        }
        seqOfStat.add(new DERSequence(seqOfByte));
        seqOfByte = new ASN1EncodableVector();
        
        seqOfInt
          .add(new ASN1Integer(nextTreehash[i][j].getStatInt()[0]));
        seqOfInt.add(new ASN1Integer(tailLength));
        seqOfInt
          .add(new ASN1Integer(nextTreehash[i][j].getStatInt()[2]));
        seqOfInt
          .add(new ASN1Integer(nextTreehash[i][j].getStatInt()[3]));
        seqOfInt
          .add(new ASN1Integer(nextTreehash[i][j].getStatInt()[4]));
        seqOfInt
          .add(new ASN1Integer(nextTreehash[i][j].getStatInt()[5]));
        for (int k = 0; k < tailLength; k++)
        {
          seqOfInt.add(new ASN1Integer(nextTreehash[i][j]
            .getStatInt()[(6 + k)]));
        }
        seqOfStat.add(new DERSequence(seqOfInt));
        seqOfInt = new ASN1EncodableVector();
        
        seqOfTreehash1.add(new DERSequence(seqOfStat));
        seqOfStat = new ASN1EncodableVector();
      }
      seqOfTreehash0.add(new DERSequence(new DERSequence(seqOfTreehash1)));
      seqOfTreehash1 = new ASN1EncodableVector();
    }
    result.add(new DERSequence(seqOfTreehash0));
    

    ASN1EncodableVector keepPart0 = new ASN1EncodableVector();
    ASN1EncodableVector keepPart1 = new ASN1EncodableVector();
    for (int i = 0; i < keep.length; i++)
    {
      for (int j = 0; j < keep[i].length; j++)
      {
        keepPart0.add(new DEROctetString(keep[i][j]));
      }
      keepPart1.add(new DERSequence(keepPart0));
      keepPart0 = new ASN1EncodableVector();
    }
    result.add(new DERSequence(keepPart1));
    

    ASN1EncodableVector curStackPart0 = new ASN1EncodableVector();
    ASN1EncodableVector curStackPart1 = new ASN1EncodableVector();
    for (int i = 0; i < currentStack.length; i++)
    {
      for (int j = 0; j < currentStack[i].size(); j++)
      {
        curStackPart0.add(new DEROctetString(
          (byte[])currentStack[i].elementAt(j)));
      }
      curStackPart1.add(new DERSequence(curStackPart0));
      curStackPart0 = new ASN1EncodableVector();
    }
    result.add(new DERSequence(curStackPart1));
    

    ASN1EncodableVector nextStackPart0 = new ASN1EncodableVector();
    ASN1EncodableVector nextStackPart1 = new ASN1EncodableVector();
    for (int i = 0; i < nextStack.length; i++)
    {
      for (int j = 0; j < nextStack[i].size(); j++)
      {
        nextStackPart0.add(new DEROctetString(
          (byte[])nextStack[i].elementAt(j)));
      }
      nextStackPart1.add(new DERSequence(nextStackPart0));
      nextStackPart0 = new ASN1EncodableVector();
    }
    result.add(new DERSequence(nextStackPart1));
    

    ASN1EncodableVector currentRetainPart0 = new ASN1EncodableVector();
    ASN1EncodableVector currentRetainPart1 = new ASN1EncodableVector();
    ASN1EncodableVector currentRetainPart2 = new ASN1EncodableVector();
    for (int i = 0; i < currentRetain.length; i++)
    {
      for (int j = 0; j < currentRetain[i].length; j++)
      {
        for (int k = 0; k < currentRetain[i][j].size(); k++)
        {
          currentRetainPart0.add(new DEROctetString(
            (byte[])currentRetain[i][j].elementAt(k)));
        }
        currentRetainPart1.add(new DERSequence(currentRetainPart0));
        currentRetainPart0 = new ASN1EncodableVector();
      }
      currentRetainPart2.add(new DERSequence(currentRetainPart1));
      currentRetainPart1 = new ASN1EncodableVector();
    }
    result.add(new DERSequence(currentRetainPart2));
    

    ASN1EncodableVector nextRetainPart0 = new ASN1EncodableVector();
    ASN1EncodableVector nextRetainPart1 = new ASN1EncodableVector();
    ASN1EncodableVector nextRetainPart2 = new ASN1EncodableVector();
    for (int i = 0; i < nextRetain.length; i++)
    {
      for (int j = 0; j < nextRetain[i].length; j++)
      {
        for (int k = 0; k < nextRetain[i][j].size(); k++)
        {
          nextRetainPart0.add(new DEROctetString(
            (byte[])nextRetain[i][j].elementAt(k)));
        }
        nextRetainPart1.add(new DERSequence(nextRetainPart0));
        nextRetainPart0 = new ASN1EncodableVector();
      }
      nextRetainPart2.add(new DERSequence(nextRetainPart1));
      nextRetainPart1 = new ASN1EncodableVector();
    }
    result.add(new DERSequence(nextRetainPart2));
    

    ASN1EncodableVector seqOfLeaf = new ASN1EncodableVector();
    seqOfStat = new ASN1EncodableVector();
    seqOfByte = new ASN1EncodableVector();
    seqOfInt = new ASN1EncodableVector();
    
    for (int i = 0; i < nextNextLeaf.length; i++)
    {
      seqOfStat.add(new DERSequence(algorithms[0]));
      
      byte[][] tempByte = nextNextLeaf[i].getStatByte();
      seqOfByte.add(new DEROctetString(tempByte[0]));
      seqOfByte.add(new DEROctetString(tempByte[1]));
      seqOfByte.add(new DEROctetString(tempByte[2]));
      seqOfByte.add(new DEROctetString(tempByte[3]));
      seqOfStat.add(new DERSequence(seqOfByte));
      seqOfByte = new ASN1EncodableVector();
      
      int[] tempInt = nextNextLeaf[i].getStatInt();
      seqOfInt.add(new ASN1Integer(tempInt[0]));
      seqOfInt.add(new ASN1Integer(tempInt[1]));
      seqOfInt.add(new ASN1Integer(tempInt[2]));
      seqOfInt.add(new ASN1Integer(tempInt[3]));
      seqOfStat.add(new DERSequence(seqOfInt));
      seqOfInt = new ASN1EncodableVector();
      
      seqOfLeaf.add(new DERSequence(seqOfStat));
      seqOfStat = new ASN1EncodableVector();
    }
    result.add(new DERSequence(seqOfLeaf));
    

    ASN1EncodableVector seqOfUpperLeaf = new ASN1EncodableVector();
    seqOfStat = new ASN1EncodableVector();
    seqOfByte = new ASN1EncodableVector();
    seqOfInt = new ASN1EncodableVector();
    
    for (int i = 0; i < upperLeaf.length; i++)
    {
      seqOfStat.add(new DERSequence(algorithms[0]));
      
      byte[][] tempByte = upperLeaf[i].getStatByte();
      seqOfByte.add(new DEROctetString(tempByte[0]));
      seqOfByte.add(new DEROctetString(tempByte[1]));
      seqOfByte.add(new DEROctetString(tempByte[2]));
      seqOfByte.add(new DEROctetString(tempByte[3]));
      seqOfStat.add(new DERSequence(seqOfByte));
      seqOfByte = new ASN1EncodableVector();
      
      int[] tempInt = upperLeaf[i].getStatInt();
      seqOfInt.add(new ASN1Integer(tempInt[0]));
      seqOfInt.add(new ASN1Integer(tempInt[1]));
      seqOfInt.add(new ASN1Integer(tempInt[2]));
      seqOfInt.add(new ASN1Integer(tempInt[3]));
      seqOfStat.add(new DERSequence(seqOfInt));
      seqOfInt = new ASN1EncodableVector();
      
      seqOfUpperLeaf.add(new DERSequence(seqOfStat));
      seqOfStat = new ASN1EncodableVector();
    }
    result.add(new DERSequence(seqOfUpperLeaf));
    

    ASN1EncodableVector seqOfUpperTreehashLeaf = new ASN1EncodableVector();
    seqOfStat = new ASN1EncodableVector();
    seqOfByte = new ASN1EncodableVector();
    seqOfInt = new ASN1EncodableVector();
    
    for (int i = 0; i < upperTreehashLeaf.length; i++)
    {
      seqOfStat.add(new DERSequence(algorithms[0]));
      
      byte[][] tempByte = upperTreehashLeaf[i].getStatByte();
      seqOfByte.add(new DEROctetString(tempByte[0]));
      seqOfByte.add(new DEROctetString(tempByte[1]));
      seqOfByte.add(new DEROctetString(tempByte[2]));
      seqOfByte.add(new DEROctetString(tempByte[3]));
      seqOfStat.add(new DERSequence(seqOfByte));
      seqOfByte = new ASN1EncodableVector();
      
      int[] tempInt = upperTreehashLeaf[i].getStatInt();
      seqOfInt.add(new ASN1Integer(tempInt[0]));
      seqOfInt.add(new ASN1Integer(tempInt[1]));
      seqOfInt.add(new ASN1Integer(tempInt[2]));
      seqOfInt.add(new ASN1Integer(tempInt[3]));
      seqOfStat.add(new DERSequence(seqOfInt));
      seqOfInt = new ASN1EncodableVector();
      
      seqOfUpperTreehashLeaf.add(new DERSequence(seqOfStat));
      seqOfStat = new ASN1EncodableVector();
    }
    result.add(new DERSequence(seqOfUpperTreehashLeaf));
    

    ASN1EncodableVector minTreehashPart = new ASN1EncodableVector();
    for (int i = 0; i < minTreehash.length; i++)
    {
      minTreehashPart.add(new ASN1Integer(minTreehash[i]));
    }
    result.add(new DERSequence(minTreehashPart));
    

    ASN1EncodableVector nextRootPart = new ASN1EncodableVector();
    for (int i = 0; i < nextRoot.length; i++)
    {
      nextRootPart.add(new DEROctetString(nextRoot[i]));
    }
    result.add(new DERSequence(nextRootPart));
    

    ASN1EncodableVector seqOfnextNextRoot = new ASN1EncodableVector();
    ASN1EncodableVector seqOfnnRStats = new ASN1EncodableVector();
    ASN1EncodableVector seqOfnnRStrings = new ASN1EncodableVector();
    ASN1EncodableVector seqOfnnRBytes = new ASN1EncodableVector();
    ASN1EncodableVector seqOfnnRInts = new ASN1EncodableVector();
    ASN1EncodableVector seqOfnnRTreehash = new ASN1EncodableVector();
    ASN1EncodableVector seqOfnnRRetain = new ASN1EncodableVector();
    
    for (int i = 0; i < nextNextRoot.length; i++)
    {
      seqOfnnRStats.add(new DERSequence(algorithms[0]));
      seqOfnnRStrings = new ASN1EncodableVector();
      
      int heightOfTree = nextNextRoot[i].getStatInt()[0];
      int tailLength = nextNextRoot[i].getStatInt()[7];
      
      seqOfnnRBytes.add(new DEROctetString(nextNextRoot[i]
        .getStatByte()[0]));
      for (int j = 0; j < heightOfTree; j++)
      {
        seqOfnnRBytes.add(new DEROctetString(nextNextRoot[i]
          .getStatByte()[(1 + j)]));
      }
      for (int j = 0; j < tailLength; j++)
      {
        seqOfnnRBytes.add(new DEROctetString(nextNextRoot[i]
          .getStatByte()[(1 + heightOfTree + j)]));
      }
      
      seqOfnnRStats.add(new DERSequence(seqOfnnRBytes));
      seqOfnnRBytes = new ASN1EncodableVector();
      
      seqOfnnRInts.add(new ASN1Integer(heightOfTree));
      seqOfnnRInts.add(new ASN1Integer(nextNextRoot[i].getStatInt()[1]));
      seqOfnnRInts.add(new ASN1Integer(nextNextRoot[i].getStatInt()[2]));
      seqOfnnRInts.add(new ASN1Integer(nextNextRoot[i].getStatInt()[3]));
      seqOfnnRInts.add(new ASN1Integer(nextNextRoot[i].getStatInt()[4]));
      seqOfnnRInts.add(new ASN1Integer(nextNextRoot[i].getStatInt()[5]));
      seqOfnnRInts.add(new ASN1Integer(nextNextRoot[i].getStatInt()[6]));
      seqOfnnRInts.add(new ASN1Integer(tailLength));
      for (int j = 0; j < heightOfTree; j++)
      {
        seqOfnnRInts.add(new ASN1Integer(nextNextRoot[i]
          .getStatInt()[(8 + j)]));
      }
      for (int j = 0; j < tailLength; j++)
      {
        seqOfnnRInts.add(new ASN1Integer(nextNextRoot[i].getStatInt()[(8 + heightOfTree + j)]));
      }
      

      seqOfnnRStats.add(new DERSequence(seqOfnnRInts));
      seqOfnnRInts = new ASN1EncodableVector();
      


      seqOfStat = new ASN1EncodableVector();
      seqOfByte = new ASN1EncodableVector();
      seqOfInt = new ASN1EncodableVector();
      
      if (nextNextRoot[i].getTreehash() != null)
      {
        for (int j = 0; j < nextNextRoot[i].getTreehash().length; j++)
        {
          seqOfStat.add(new DERSequence(algorithms[0]));
          
          tailLength = nextNextRoot[i].getTreehash()[j].getStatInt()[1];
          
          seqOfByte.add(new DEROctetString(nextNextRoot[i]
            .getTreehash()[j].getStatByte()[0]));
          seqOfByte.add(new DEROctetString(nextNextRoot[i]
            .getTreehash()[j].getStatByte()[1]));
          seqOfByte.add(new DEROctetString(nextNextRoot[i]
            .getTreehash()[j].getStatByte()[2]));
          for (int k = 0; k < tailLength; k++)
          {
            seqOfByte.add(new DEROctetString(nextNextRoot[i]
              .getTreehash()[j].getStatByte()[(3 + k)]));
          }
          seqOfStat.add(new DERSequence(seqOfByte));
          seqOfByte = new ASN1EncodableVector();
          
          seqOfInt.add(new ASN1Integer(nextNextRoot[i]
            .getTreehash()[j].getStatInt()[0]));
          seqOfInt.add(new ASN1Integer(tailLength));
          seqOfInt.add(new ASN1Integer(nextNextRoot[i]
            .getTreehash()[j].getStatInt()[2]));
          seqOfInt.add(new ASN1Integer(nextNextRoot[i]
            .getTreehash()[j].getStatInt()[3]));
          seqOfInt.add(new ASN1Integer(nextNextRoot[i]
            .getTreehash()[j].getStatInt()[4]));
          seqOfInt.add(new ASN1Integer(nextNextRoot[i]
            .getTreehash()[j].getStatInt()[5]));
          for (int k = 0; k < tailLength; k++)
          {
            seqOfInt.add(new ASN1Integer(nextNextRoot[i]
              .getTreehash()[j].getStatInt()[(6 + k)]));
          }
          seqOfStat.add(new DERSequence(seqOfInt));
          seqOfInt = new ASN1EncodableVector();
          
          seqOfnnRTreehash.add(new DERSequence(seqOfStat));
          seqOfStat = new ASN1EncodableVector();
        }
      }
      
      seqOfnnRStats.add(new DERSequence(seqOfnnRTreehash));
      seqOfnnRTreehash = new ASN1EncodableVector();
      



      currentRetainPart0 = new ASN1EncodableVector();
      if (nextNextRoot[i].getRetain() != null)
      {
        for (int j = 0; j < nextNextRoot[i].getRetain().length; j++)
        {
          for (int k = 0; k < nextNextRoot[i].getRetain()[j].size(); k++)
          {
            currentRetainPart0.add(new DEROctetString(
            
              (byte[])nextNextRoot[i].getRetain()[j].elementAt(k)));
          }
          seqOfnnRRetain.add(new DERSequence(currentRetainPart0));
          currentRetainPart0 = new ASN1EncodableVector();
        }
      }
      
      seqOfnnRStats.add(new DERSequence(seqOfnnRRetain));
      seqOfnnRRetain = new ASN1EncodableVector();
      
      seqOfnextNextRoot.add(new DERSequence(seqOfnnRStats));
      seqOfnnRStats = new ASN1EncodableVector();
    }
    result.add(new DERSequence(seqOfnextNextRoot));
    

    ASN1EncodableVector curRootSigPart = new ASN1EncodableVector();
    for (int i = 0; i < currentRootSig.length; i++)
    {
      curRootSigPart.add(new DEROctetString(currentRootSig[i]));
    }
    result.add(new DERSequence(curRootSigPart));
    

    ASN1EncodableVector seqOfnextRootSigs = new ASN1EncodableVector();
    ASN1EncodableVector seqOfnRSStats = new ASN1EncodableVector();
    ASN1EncodableVector seqOfnRSStrings = new ASN1EncodableVector();
    ASN1EncodableVector seqOfnRSBytes = new ASN1EncodableVector();
    ASN1EncodableVector seqOfnRSInts = new ASN1EncodableVector();
    
    for (int i = 0; i < nextRootSig.length; i++)
    {
      seqOfnRSStats.add(new DERSequence(algorithms[0]));
      seqOfnRSStrings = new ASN1EncodableVector();
      
      seqOfnRSBytes.add(new DEROctetString(nextRootSig[i]
        .getStatByte()[0]));
      seqOfnRSBytes.add(new DEROctetString(nextRootSig[i]
        .getStatByte()[1]));
      seqOfnRSBytes.add(new DEROctetString(nextRootSig[i]
        .getStatByte()[2]));
      seqOfnRSBytes.add(new DEROctetString(nextRootSig[i]
        .getStatByte()[3]));
      seqOfnRSBytes.add(new DEROctetString(nextRootSig[i]
        .getStatByte()[4]));
      
      seqOfnRSStats.add(new DERSequence(seqOfnRSBytes));
      seqOfnRSBytes = new ASN1EncodableVector();
      
      seqOfnRSInts.add(new ASN1Integer(nextRootSig[i].getStatInt()[0]));
      seqOfnRSInts.add(new ASN1Integer(nextRootSig[i].getStatInt()[1]));
      seqOfnRSInts.add(new ASN1Integer(nextRootSig[i].getStatInt()[2]));
      seqOfnRSInts.add(new ASN1Integer(nextRootSig[i].getStatInt()[3]));
      seqOfnRSInts.add(new ASN1Integer(nextRootSig[i].getStatInt()[4]));
      seqOfnRSInts.add(new ASN1Integer(nextRootSig[i].getStatInt()[5]));
      seqOfnRSInts.add(new ASN1Integer(nextRootSig[i].getStatInt()[6]));
      seqOfnRSInts.add(new ASN1Integer(nextRootSig[i].getStatInt()[7]));
      seqOfnRSInts.add(new ASN1Integer(nextRootSig[i].getStatInt()[8]));
      
      seqOfnRSStats.add(new DERSequence(seqOfnRSInts));
      seqOfnRSInts = new ASN1EncodableVector();
      
      seqOfnextRootSigs.add(new DERSequence(seqOfnRSStats));
      seqOfnRSStats = new ASN1EncodableVector();
    }
    result.add(new DERSequence(seqOfnextRootSigs));
    

    ASN1EncodableVector parSetPart0 = new ASN1EncodableVector();
    ASN1EncodableVector parSetPart1 = new ASN1EncodableVector();
    ASN1EncodableVector parSetPart2 = new ASN1EncodableVector();
    ASN1EncodableVector parSetPart3 = new ASN1EncodableVector();
    
    for (int i = 0; i < gmssParameterset.getHeightOfTrees().length; i++)
    {
      parSetPart1.add(new ASN1Integer(gmssParameterset
        .getHeightOfTrees()[i]));
      parSetPart2.add(new ASN1Integer(gmssParameterset
        .getWinternitzParameter()[i]));
      parSetPart3.add(new ASN1Integer(gmssParameterset.getK()[i]));
    }
    parSetPart0.add(new ASN1Integer(gmssParameterset.getNumOfLayers()));
    parSetPart0.add(new DERSequence(parSetPart1));
    parSetPart0.add(new DERSequence(parSetPart2));
    parSetPart0.add(new DERSequence(parSetPart3));
    result.add(new DERSequence(parSetPart0));
    

    ASN1EncodableVector namesPart = new ASN1EncodableVector();
    
    for (int i = 0; i < algorithms.length; i++)
    {
      namesPart.add(algorithms[i]);
    }
    
    result.add(new DERSequence(namesPart));
    return new DERSequence(result);
  }
  

  private static int checkBigIntegerInIntRange(ASN1Encodable a)
  {
    BigInteger b = ((ASN1Integer)a).getValue();
    if ((b.compareTo(BigInteger.valueOf(2147483647L)) > 0) || 
      (b.compareTo(BigInteger.valueOf(-2147483648L)) < 0))
    {
      throw new IllegalArgumentException("BigInteger not in Range: " + b.toString());
    }
    return b.intValue();
  }
  

  public ASN1Primitive toASN1Primitive()
  {
    return primitive;
  }
}
