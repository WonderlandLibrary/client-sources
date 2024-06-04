package org.spongycastle.pqc.crypto.gmss;

import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Vector;
import org.spongycastle.crypto.Digest;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Integers;
import org.spongycastle.util.encoders.Hex;






















































































public class GMSSRootCalc
{
  private int heightOfTree;
  private int mdLength;
  private Treehash[] treehash;
  private Vector[] retain;
  private byte[] root;
  private byte[][] AuthPath;
  private int K;
  private Vector tailStack;
  private Vector heightOfNodes;
  private Digest messDigestTree;
  private GMSSDigestProvider digestProvider;
  private int[] index;
  private boolean isInitialized;
  private boolean isFinished;
  private int indexForNextSeed;
  private int heightOfNextSeed;
  
  public GMSSRootCalc(Digest digest, byte[][] statByte, int[] statInt, Treehash[] treeH, Vector[] ret)
  {
    messDigestTree = digestProvider.get();
    digestProvider = digestProvider;
    
    heightOfTree = statInt[0];
    mdLength = statInt[1];
    K = statInt[2];
    indexForNextSeed = statInt[3];
    heightOfNextSeed = statInt[4];
    if (statInt[5] == 1)
    {
      isFinished = true;
    }
    else
    {
      isFinished = false;
    }
    if (statInt[6] == 1)
    {
      isInitialized = true;
    }
    else
    {
      isInitialized = false;
    }
    
    int tailLength = statInt[7];
    
    index = new int[heightOfTree];
    for (int i = 0; i < heightOfTree; i++)
    {
      index[i] = statInt[(8 + i)];
    }
    
    heightOfNodes = new Vector();
    for (int i = 0; i < tailLength; i++)
    {
      heightOfNodes.addElement(Integers.valueOf(statInt[(8 + heightOfTree + i)]));
    }
    


    root = statByte[0];
    
    AuthPath = new byte[heightOfTree][mdLength];
    for (int i = 0; i < heightOfTree; i++)
    {
      AuthPath[i] = statByte[(1 + i)];
    }
    
    tailStack = new Vector();
    for (int i = 0; i < tailLength; i++)
    {
      tailStack.addElement(statByte[(1 + heightOfTree + i)]);
    }
    

    treehash = GMSSUtils.clone(treeH);
    

    retain = GMSSUtils.clone(ret);
  }
  








  public GMSSRootCalc(int heightOfTree, int K, GMSSDigestProvider digestProvider)
  {
    this.heightOfTree = heightOfTree;
    this.digestProvider = digestProvider;
    messDigestTree = digestProvider.get();
    mdLength = messDigestTree.getDigestSize();
    this.K = K;
    index = new int[heightOfTree];
    AuthPath = new byte[heightOfTree][mdLength];
    root = new byte[mdLength];
    
    retain = new Vector[this.K - 1];
    for (int i = 0; i < K - 1; i++)
    {
      retain[i] = new Vector();
    }
  }
  






  public void initialize(Vector sharedStack)
  {
    treehash = new Treehash[heightOfTree - K];
    for (int i = 0; i < heightOfTree - K; i++)
    {
      treehash[i] = new Treehash(sharedStack, i, digestProvider.get());
    }
    
    index = new int[heightOfTree];
    AuthPath = new byte[heightOfTree][mdLength];
    root = new byte[mdLength];
    
    tailStack = new Vector();
    heightOfNodes = new Vector();
    isInitialized = true;
    isFinished = false;
    
    for (int i = 0; i < heightOfTree; i++)
    {
      index[i] = -1;
    }
    
    retain = new Vector[K - 1];
    for (int i = 0; i < K - 1; i++)
    {
      retain[i] = new Vector();
    }
    
    indexForNextSeed = 3;
    heightOfNextSeed = 0;
  }
  








  public void update(byte[] seed, byte[] leaf)
  {
    if ((heightOfNextSeed < heightOfTree - K) && (indexForNextSeed - 2 == index[0]))
    {

      initializeTreehashSeed(seed, heightOfNextSeed);
      heightOfNextSeed += 1;
      indexForNextSeed *= 2;
    }
    
    update(leaf);
  }
  





  public void update(byte[] leaf)
  {
    if (isFinished)
    {
      System.out.print("Too much updates for Tree!!");
      return;
    }
    if (!isInitialized)
    {
      System.err.println("GMSSRootCalc not initialized!");
      return;
    }
    

    index[0] += 1;
    

    if (index[0] == 1)
    {
      System.arraycopy(leaf, 0, AuthPath[0], 0, mdLength);
    }
    else if (index[0] == 3)
    {

      if (heightOfTree > K)
      {
        treehash[0].setFirstNode(leaf);
      }
    }
    
    if (((index[0] - 3) % 2 == 0) && (index[0] >= 3))
    {

      if (heightOfTree == K)
      {

        retain[0].insertElementAt(leaf, 0);
      }
    }
    

    if (index[0] == 0)
    {
      tailStack.addElement(leaf);
      heightOfNodes.addElement(Integers.valueOf(0));

    }
    else
    {
      byte[] help = new byte[mdLength];
      byte[] toBeHashed = new byte[mdLength << 1];
      

      System.arraycopy(leaf, 0, help, 0, mdLength);
      int helpHeight = 0;
      
      while (tailStack.size() > 0)
      {
        if (helpHeight != ((Integer)heightOfNodes.lastElement()).intValue()) {
          break;
        }
        
        System.arraycopy(tailStack.lastElement(), 0, toBeHashed, 0, mdLength);
        
        tailStack.removeElementAt(tailStack.size() - 1);
        heightOfNodes.removeElementAt(heightOfNodes.size() - 1);
        System.arraycopy(help, 0, toBeHashed, mdLength, mdLength);
        
        messDigestTree.update(toBeHashed, 0, toBeHashed.length);
        help = new byte[messDigestTree.getDigestSize()];
        messDigestTree.doFinal(help, 0);
        

        helpHeight++;
        if (helpHeight < heightOfTree)
        {
          index[helpHeight] += 1;
          

          if (index[helpHeight] == 1)
          {
            System.arraycopy(help, 0, AuthPath[helpHeight], 0, mdLength);
          }
          

          if (helpHeight >= heightOfTree - K)
          {
            if (helpHeight == 0)
            {
              System.out.println("M���P");
            }
            


            if (((index[helpHeight] - 3) % 2 == 0) && (index[helpHeight] >= 3))
            {



              retain[(helpHeight - (heightOfTree - K))].insertElementAt(help, 0);

            }
            

          }
          else if (index[helpHeight] == 3)
          {
            treehash[helpHeight].setFirstNode(help);
          }
        }
      }
      

      tailStack.addElement(help);
      heightOfNodes.addElement(Integers.valueOf(helpHeight));
      

      if (helpHeight == heightOfTree)
      {
        isFinished = true;
        isInitialized = false;
        root = ((byte[])tailStack.lastElement());
      }
    }
  }
  








  public void initializeTreehashSeed(byte[] seed, int index)
  {
    treehash[index].initializeSeed(seed);
  }
  





  public boolean wasInitialized()
  {
    return isInitialized;
  }
  





  public boolean wasFinished()
  {
    return isFinished;
  }
  





  public byte[][] getAuthPath()
  {
    return GMSSUtils.clone(AuthPath);
  }
  





  public Treehash[] getTreehash()
  {
    return GMSSUtils.clone(treehash);
  }
  





  public Vector[] getRetain()
  {
    return GMSSUtils.clone(retain);
  }
  





  public byte[] getRoot()
  {
    return Arrays.clone(root);
  }
  





  public Vector getStack()
  {
    Vector copy = new Vector();
    for (Enumeration en = tailStack.elements(); en.hasMoreElements();)
    {
      copy.addElement(en.nextElement());
    }
    return copy;
  }
  


  public byte[][] getStatByte()
  {
    int tailLength;
    

    int tailLength;
    
    if (tailStack == null)
    {
      tailLength = 0;
    }
    else
    {
      tailLength = tailStack.size();
    }
    byte[][] statByte = new byte[1 + heightOfTree + tailLength][64];
    statByte[0] = root;
    
    for (int i = 0; i < heightOfTree; i++)
    {
      statByte[(1 + i)] = AuthPath[i];
    }
    for (int i = 0; i < tailLength; i++)
    {
      statByte[(1 + heightOfTree + i)] = ((byte[])(byte[])tailStack.elementAt(i));
    }
    
    return statByte;
  }
  


  public int[] getStatInt()
  {
    int tailLength;
    

    int tailLength;
    
    if (tailStack == null)
    {
      tailLength = 0;
    }
    else
    {
      tailLength = tailStack.size();
    }
    int[] statInt = new int[8 + heightOfTree + tailLength];
    statInt[0] = heightOfTree;
    statInt[1] = mdLength;
    statInt[2] = K;
    statInt[3] = indexForNextSeed;
    statInt[4] = heightOfNextSeed;
    if (isFinished)
    {
      statInt[5] = 1;
    }
    else
    {
      statInt[5] = 0;
    }
    if (isInitialized)
    {
      statInt[6] = 1;
    }
    else
    {
      statInt[6] = 0;
    }
    statInt[7] = tailLength;
    
    for (int i = 0; i < heightOfTree; i++)
    {
      statInt[(8 + i)] = index[i];
    }
    for (int i = 0; i < tailLength; i++)
    {

      statInt[(8 + heightOfTree + i)] = ((Integer)heightOfNodes.elementAt(i)).intValue();
    }
    
    return statInt;
  }
  



  public String toString()
  {
    String out = "";
    int tailLength;
    int tailLength; if (tailStack == null)
    {
      tailLength = 0;
    }
    else
    {
      tailLength = tailStack.size();
    }
    
    for (int i = 0; i < 8 + heightOfTree + tailLength; i++)
    {
      out = out + getStatInt()[i] + " ";
    }
    for (int i = 0; i < 1 + heightOfTree + tailLength; i++)
    {
      out = out + new String(Hex.encode(getStatByte()[i])) + " ";
    }
    out = out + "  " + digestProvider.get().getDigestSize();
    return out;
  }
}
