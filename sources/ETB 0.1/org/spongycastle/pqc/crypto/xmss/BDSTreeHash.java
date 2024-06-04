package org.spongycastle.pqc.crypto.xmss;

import java.io.Serializable;
import java.util.Stack;




class BDSTreeHash
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private XMSSNode tailNode;
  private final int initialHeight;
  private int height;
  private int nextIndex;
  private boolean initialized;
  private boolean finished;
  
  BDSTreeHash(int initialHeight)
  {
    this.initialHeight = initialHeight;
    initialized = false;
    finished = false;
  }
  
  void initialize(int nextIndex)
  {
    tailNode = null;
    height = initialHeight;
    this.nextIndex = nextIndex;
    initialized = true;
    finished = false;
  }
  
  void update(Stack<XMSSNode> stack, WOTSPlus wotsPlus, byte[] publicSeed, byte[] secretSeed, OTSHashAddress otsHashAddress)
  {
    if (otsHashAddress == null)
    {
      throw new NullPointerException("otsHashAddress == null");
    }
    if ((finished) || (!initialized))
    {
      throw new IllegalStateException("finished or not initialized");
    }
    




    otsHashAddress = (OTSHashAddress)((OTSHashAddress.Builder)((OTSHashAddress.Builder)((OTSHashAddress.Builder)new OTSHashAddress.Builder().withLayerAddress(otsHashAddress.getLayerAddress())).withTreeAddress(otsHashAddress.getTreeAddress())).withOTSAddress(nextIndex).withChainAddress(otsHashAddress.getChainAddress()).withHashAddress(otsHashAddress.getHashAddress()).withKeyAndMask(otsHashAddress.getKeyAndMask())).build();
    

    LTreeAddress lTreeAddress = (LTreeAddress)((LTreeAddress.Builder)((LTreeAddress.Builder)new LTreeAddress.Builder().withLayerAddress(otsHashAddress.getLayerAddress())).withTreeAddress(otsHashAddress.getTreeAddress())).withLTreeAddress(nextIndex).build();
    

    HashTreeAddress hashTreeAddress = (HashTreeAddress)((HashTreeAddress.Builder)((HashTreeAddress.Builder)new HashTreeAddress.Builder().withLayerAddress(otsHashAddress.getLayerAddress())).withTreeAddress(otsHashAddress.getTreeAddress())).withTreeIndex(nextIndex).build();
    
    wotsPlus.importKeys(wotsPlus.getWOTSPlusSecretKey(secretSeed, otsHashAddress), publicSeed);
    WOTSPlusPublicKeyParameters wotsPlusPublicKey = wotsPlus.getPublicKey(otsHashAddress);
    XMSSNode node = XMSSNodeUtil.lTree(wotsPlus, wotsPlusPublicKey, lTreeAddress);
    
    while ((!stack.isEmpty()) && (((XMSSNode)stack.peek()).getHeight() == node.getHeight()) && 
      (((XMSSNode)stack.peek()).getHeight() != initialHeight))
    {





      hashTreeAddress = (HashTreeAddress)((HashTreeAddress.Builder)((HashTreeAddress.Builder)((HashTreeAddress.Builder)new HashTreeAddress.Builder().withLayerAddress(hashTreeAddress.getLayerAddress())).withTreeAddress(hashTreeAddress.getTreeAddress())).withTreeHeight(hashTreeAddress.getTreeHeight()).withTreeIndex((hashTreeAddress.getTreeIndex() - 1) / 2).withKeyAndMask(hashTreeAddress.getKeyAndMask())).build();
      node = XMSSNodeUtil.randomizeHash(wotsPlus, (XMSSNode)stack.pop(), node, hashTreeAddress);
      node = new XMSSNode(node.getHeight() + 1, node.getValue());
      




      hashTreeAddress = (HashTreeAddress)((HashTreeAddress.Builder)((HashTreeAddress.Builder)((HashTreeAddress.Builder)new HashTreeAddress.Builder().withLayerAddress(hashTreeAddress.getLayerAddress())).withTreeAddress(hashTreeAddress.getTreeAddress())).withTreeHeight(hashTreeAddress.getTreeHeight() + 1).withTreeIndex(hashTreeAddress.getTreeIndex()).withKeyAndMask(hashTreeAddress.getKeyAndMask())).build();
    }
    
    if (tailNode == null)
    {
      tailNode = node;


    }
    else if (tailNode.getHeight() == node.getHeight())
    {





      hashTreeAddress = (HashTreeAddress)((HashTreeAddress.Builder)((HashTreeAddress.Builder)((HashTreeAddress.Builder)new HashTreeAddress.Builder().withLayerAddress(hashTreeAddress.getLayerAddress())).withTreeAddress(hashTreeAddress.getTreeAddress())).withTreeHeight(hashTreeAddress.getTreeHeight()).withTreeIndex((hashTreeAddress.getTreeIndex() - 1) / 2).withKeyAndMask(hashTreeAddress.getKeyAndMask())).build();
      node = XMSSNodeUtil.randomizeHash(wotsPlus, tailNode, node, hashTreeAddress);
      node = new XMSSNode(tailNode.getHeight() + 1, node.getValue());
      tailNode = node;
      




      hashTreeAddress = (HashTreeAddress)((HashTreeAddress.Builder)((HashTreeAddress.Builder)((HashTreeAddress.Builder)new HashTreeAddress.Builder().withLayerAddress(hashTreeAddress.getLayerAddress())).withTreeAddress(hashTreeAddress.getTreeAddress())).withTreeHeight(hashTreeAddress.getTreeHeight() + 1).withTreeIndex(hashTreeAddress.getTreeIndex()).withKeyAndMask(hashTreeAddress.getKeyAndMask())).build();
    }
    else
    {
      stack.push(node);
    }
    

    if (tailNode.getHeight() == initialHeight)
    {
      finished = true;
    }
    else
    {
      height = node.getHeight();
      nextIndex += 1;
    }
  }
  
  int getHeight()
  {
    if ((!initialized) || (finished))
    {
      return Integer.MAX_VALUE;
    }
    return height;
  }
  
  int getIndexLeaf()
  {
    return nextIndex;
  }
  
  void setNode(XMSSNode node)
  {
    tailNode = node;
    height = node.getHeight();
    if (height == initialHeight)
    {
      finished = true;
    }
  }
  
  boolean isFinished()
  {
    return finished;
  }
  
  boolean isInitialized()
  {
    return initialized;
  }
  
  public XMSSNode getTailNode()
  {
    return tailNode.clone();
  }
}
