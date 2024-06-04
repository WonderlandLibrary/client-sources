package org.spongycastle.pqc.crypto.gmss;

import java.io.PrintStream;
import java.util.Vector;
import org.spongycastle.crypto.Digest;
import org.spongycastle.pqc.crypto.gmss.util.GMSSRandom;
import org.spongycastle.util.Integers;
import org.spongycastle.util.encoders.Hex;

































































public class Treehash
{
  private int maxHeight;
  private Vector tailStack;
  private Vector heightOfNodes;
  private byte[] firstNode;
  private byte[] seedActive;
  private byte[] seedNext;
  private int tailLength;
  private int firstNodeHeight;
  private boolean isInitialized;
  private boolean isFinished;
  private boolean seedInitialized;
  private Digest messDigestTree;
  
  public Treehash(Digest name, byte[][] statByte, int[] statInt)
  {
    messDigestTree = name;
    

    maxHeight = statInt[0];
    tailLength = statInt[1];
    firstNodeHeight = statInt[2];
    
    if (statInt[3] == 1)
    {
      isFinished = true;
    }
    else
    {
      isFinished = false;
    }
    if (statInt[4] == 1)
    {
      isInitialized = true;
    }
    else
    {
      isInitialized = false;
    }
    if (statInt[5] == 1)
    {
      seedInitialized = true;
    }
    else
    {
      seedInitialized = false;
    }
    
    heightOfNodes = new Vector();
    for (int i = 0; i < tailLength; i++)
    {
      heightOfNodes.addElement(Integers.valueOf(statInt[(6 + i)]));
    }
    

    firstNode = statByte[0];
    seedActive = statByte[1];
    seedNext = statByte[2];
    
    tailStack = new Vector();
    for (int i = 0; i < tailLength; i++)
    {
      tailStack.addElement(statByte[(3 + i)]);
    }
  }
  








  public Treehash(Vector tailStack, int maxHeight, Digest digest)
  {
    this.tailStack = tailStack;
    this.maxHeight = maxHeight;
    firstNode = null;
    isInitialized = false;
    isFinished = false;
    seedInitialized = false;
    messDigestTree = digest;
    
    seedNext = new byte[messDigestTree.getDigestSize()];
    seedActive = new byte[messDigestTree.getDigestSize()];
  }
  






  public void initializeSeed(byte[] seedIn)
  {
    System.arraycopy(seedIn, 0, seedNext, 0, messDigestTree
      .getDigestSize());
    seedInitialized = true;
  }
  




  public void initialize()
  {
    if (!seedInitialized)
    {
      System.err.println("Seed " + maxHeight + " not initialized");
      return;
    }
    
    heightOfNodes = new Vector();
    tailLength = 0;
    firstNode = null;
    firstNodeHeight = -1;
    isInitialized = true;
    System.arraycopy(seedNext, 0, seedActive, 0, messDigestTree
      .getDigestSize());
  }
  








  public void update(GMSSRandom gmssRandom, byte[] leaf)
  {
    if (isFinished)
    {

      System.err.println("No more update possible for treehash instance!");
      return;
    }
    if (!isInitialized)
    {

      System.err.println("Treehash instance not initialized before update");
      return;
    }
    
    byte[] help = new byte[messDigestTree.getDigestSize()];
    int helpHeight = -1;
    
    gmssRandom.nextSeed(seedActive);
    

    if (firstNode == null)
    {
      firstNode = leaf;
      firstNodeHeight = 0;

    }
    else
    {
      help = leaf;
      helpHeight = 0;
      

      while (tailLength > 0)
      {
        if (helpHeight != ((Integer)heightOfNodes.lastElement()).intValue()) {
          break;
        }
        


        byte[] toBeHashed = new byte[messDigestTree.getDigestSize() << 1];
        

        System.arraycopy(tailStack.lastElement(), 0, toBeHashed, 0, messDigestTree
          .getDigestSize());
        tailStack.removeElementAt(tailStack.size() - 1);
        heightOfNodes
          .removeElementAt(heightOfNodes.size() - 1);
        
        System.arraycopy(help, 0, toBeHashed, messDigestTree
          .getDigestSize(), messDigestTree
          .getDigestSize());
        messDigestTree.update(toBeHashed, 0, toBeHashed.length);
        help = new byte[messDigestTree.getDigestSize()];
        messDigestTree.doFinal(help, 0);
        

        helpHeight++;
        tailLength -= 1;
      }
      

      tailStack.addElement(help);
      heightOfNodes.addElement(Integers.valueOf(helpHeight));
      tailLength += 1;
      



      if (((Integer)heightOfNodes.lastElement()).intValue() == firstNodeHeight)
      {

        byte[] toBeHashed = new byte[messDigestTree.getDigestSize() << 1];
        System.arraycopy(firstNode, 0, toBeHashed, 0, messDigestTree
          .getDigestSize());
        

        System.arraycopy(tailStack.lastElement(), 0, toBeHashed, messDigestTree
          .getDigestSize(), messDigestTree
          .getDigestSize());
        tailStack.removeElementAt(tailStack.size() - 1);
        heightOfNodes
          .removeElementAt(heightOfNodes.size() - 1);
        

        messDigestTree.update(toBeHashed, 0, toBeHashed.length);
        firstNode = new byte[messDigestTree.getDigestSize()];
        messDigestTree.doFinal(firstNode, 0);
        firstNodeHeight += 1;
        

        tailLength = 0;
      }
    }
    

    if (firstNodeHeight == maxHeight)
    {
      isFinished = true;
    }
  }
  




  public void destroy()
  {
    isInitialized = false;
    isFinished = false;
    firstNode = null;
    tailLength = 0;
    firstNodeHeight = -1;
  }
  








  public int getLowestNodeHeight()
  {
    if (firstNode == null)
    {
      return maxHeight;
    }
    if (tailLength == 0)
    {
      return firstNodeHeight;
    }
    

    return Math.min(firstNodeHeight, 
      ((Integer)heightOfNodes.lastElement()).intValue());
  }
  






  public int getFirstNodeHeight()
  {
    if (firstNode == null)
    {
      return maxHeight;
    }
    return firstNodeHeight;
  }
  





  public boolean wasInitialized()
  {
    return isInitialized;
  }
  





  public boolean wasFinished()
  {
    return isFinished;
  }
  





  public byte[] getFirstNode()
  {
    return firstNode;
  }
  





  public byte[] getSeedActive()
  {
    return seedActive;
  }
  





  public void setFirstNode(byte[] hash)
  {
    if (!isInitialized)
    {
      initialize();
    }
    firstNode = hash;
    firstNodeHeight = maxHeight;
    isFinished = true;
  }
  






  public void updateNextSeed(GMSSRandom gmssRandom)
  {
    gmssRandom.nextSeed(seedNext);
  }
  





  public Vector getTailStack()
  {
    return tailStack;
  }
  







  public byte[][] getStatByte()
  {
    byte[][] statByte = new byte[3 + tailLength][messDigestTree.getDigestSize()];
    statByte[0] = firstNode;
    statByte[1] = seedActive;
    statByte[2] = seedNext;
    for (int i = 0; i < tailLength; i++)
    {
      statByte[(3 + i)] = ((byte[])(byte[])tailStack.elementAt(i));
    }
    return statByte;
  }
  






  public int[] getStatInt()
  {
    int[] statInt = new int[6 + tailLength];
    statInt[0] = maxHeight;
    statInt[1] = tailLength;
    statInt[2] = firstNodeHeight;
    if (isFinished)
    {
      statInt[3] = 1;
    }
    else
    {
      statInt[3] = 0;
    }
    if (isInitialized)
    {
      statInt[4] = 1;
    }
    else
    {
      statInt[4] = 0;
    }
    if (seedInitialized)
    {
      statInt[5] = 1;
    }
    else
    {
      statInt[5] = 0;
    }
    for (int i = 0; i < tailLength; i++)
    {
      statInt[(6 + i)] = ((Integer)heightOfNodes.elementAt(i)).intValue();
    }
    return statInt;
  }
  



  public String toString()
  {
    String out = "Treehash    : ";
    for (int i = 0; i < 6 + tailLength; i++)
    {
      out = out + getStatInt()[i] + " ";
    }
    for (int i = 0; i < 3 + tailLength; i++)
    {
      if (getStatByte()[i] != null)
      {
        out = out + new String(Hex.encode(getStatByte()[i])) + " ";
      }
      else
      {
        out = out + "null ";
      }
    }
    out = out + "  " + messDigestTree.getDigestSize();
    return out;
  }
}
