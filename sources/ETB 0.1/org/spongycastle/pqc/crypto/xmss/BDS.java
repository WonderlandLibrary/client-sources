package org.spongycastle.pqc.crypto.xmss;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;













public final class BDS
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private transient WOTSPlus wotsPlus;
  private final int treeHeight;
  private final List<BDSTreeHash> treeHashInstances;
  private int k;
  private XMSSNode root;
  private List<XMSSNode> authenticationPath;
  private Map<Integer, LinkedList<XMSSNode>> retain;
  private Stack<XMSSNode> stack;
  private Map<Integer, XMSSNode> keep;
  private int index;
  private boolean used;
  
  BDS(XMSSParameters params, int index)
  {
    this(params.getWOTSPlus(), params.getHeight(), params.getK());
    this.index = index;
    used = true;
  }
  








  BDS(XMSSParameters params, byte[] publicSeed, byte[] secretKeySeed, OTSHashAddress otsHashAddress)
  {
    this(params.getWOTSPlus(), params.getHeight(), params.getK());
    initialize(publicSeed, secretKeySeed, otsHashAddress);
  }
  









  BDS(XMSSParameters params, byte[] publicSeed, byte[] secretKeySeed, OTSHashAddress otsHashAddress, int index)
  {
    this(params.getWOTSPlus(), params.getHeight(), params.getK());
    
    initialize(publicSeed, secretKeySeed, otsHashAddress);
    
    while (this.index < index)
    {
      nextAuthenticationPath(publicSeed, secretKeySeed, otsHashAddress);
      used = false;
    }
  }
  
  private BDS(WOTSPlus wotsPlus, int treeHeight, int k)
  {
    this.wotsPlus = wotsPlus;
    this.treeHeight = treeHeight;
    this.k = k;
    if ((k > treeHeight) || (k < 2) || ((treeHeight - k) % 2 != 0))
    {
      throw new IllegalArgumentException("illegal value for BDS parameter k");
    }
    authenticationPath = new ArrayList();
    retain = new TreeMap();
    stack = new Stack();
    
    treeHashInstances = new ArrayList();
    for (int height = 0; height < treeHeight - k; height++)
    {
      treeHashInstances.add(new BDSTreeHash(height));
    }
    
    keep = new TreeMap();
    index = 0;
    used = false;
  }
  
  private BDS(BDS last, byte[] publicSeed, byte[] secretKeySeed, OTSHashAddress otsHashAddress)
  {
    wotsPlus = wotsPlus;
    treeHeight = treeHeight;
    k = k;
    root = root;
    authenticationPath = new ArrayList(authenticationPath);
    retain = retain;
    stack = ((Stack)stack.clone());
    treeHashInstances = treeHashInstances;
    keep = new TreeMap(keep);
    index = index;
    
    nextAuthenticationPath(publicSeed, secretKeySeed, otsHashAddress);
    
    used = true;
  }
  
  public BDS getNextState(byte[] publicSeed, byte[] secretKeySeed, OTSHashAddress otsHashAddress)
  {
    return new BDS(this, publicSeed, secretKeySeed, otsHashAddress);
  }
  
  private void initialize(byte[] publicSeed, byte[] secretSeed, OTSHashAddress otsHashAddress)
  {
    if (otsHashAddress == null)
    {
      throw new NullPointerException("otsHashAddress == null");
    }
    


    LTreeAddress lTreeAddress = (LTreeAddress)((LTreeAddress.Builder)((LTreeAddress.Builder)new LTreeAddress.Builder().withLayerAddress(otsHashAddress.getLayerAddress())).withTreeAddress(otsHashAddress.getTreeAddress())).build();
    

    HashTreeAddress hashTreeAddress = (HashTreeAddress)((HashTreeAddress.Builder)((HashTreeAddress.Builder)new HashTreeAddress.Builder().withLayerAddress(otsHashAddress.getLayerAddress())).withTreeAddress(otsHashAddress.getTreeAddress())).build();
    

    for (int indexLeaf = 0; indexLeaf < 1 << treeHeight; indexLeaf++)
    {





      otsHashAddress = (OTSHashAddress)((OTSHashAddress.Builder)((OTSHashAddress.Builder)((OTSHashAddress.Builder)new OTSHashAddress.Builder().withLayerAddress(otsHashAddress.getLayerAddress())).withTreeAddress(otsHashAddress.getTreeAddress())).withOTSAddress(indexLeaf).withChainAddress(otsHashAddress.getChainAddress()).withHashAddress(otsHashAddress.getHashAddress()).withKeyAndMask(otsHashAddress.getKeyAndMask())).build();
      



      wotsPlus.importKeys(wotsPlus.getWOTSPlusSecretKey(secretSeed, otsHashAddress), publicSeed);
      WOTSPlusPublicKeyParameters wotsPlusPublicKey = wotsPlus.getPublicKey(otsHashAddress);
      


      lTreeAddress = (LTreeAddress)((LTreeAddress.Builder)((LTreeAddress.Builder)((LTreeAddress.Builder)new LTreeAddress.Builder().withLayerAddress(lTreeAddress.getLayerAddress())).withTreeAddress(lTreeAddress.getTreeAddress())).withLTreeAddress(indexLeaf).withTreeHeight(lTreeAddress.getTreeHeight()).withTreeIndex(lTreeAddress.getTreeIndex()).withKeyAndMask(lTreeAddress.getKeyAndMask())).build();
      XMSSNode node = XMSSNodeUtil.lTree(wotsPlus, wotsPlusPublicKey, lTreeAddress);
      



      hashTreeAddress = (HashTreeAddress)((HashTreeAddress.Builder)((HashTreeAddress.Builder)((HashTreeAddress.Builder)new HashTreeAddress.Builder().withLayerAddress(hashTreeAddress.getLayerAddress())).withTreeAddress(hashTreeAddress.getTreeAddress())).withTreeIndex(indexLeaf).withKeyAndMask(hashTreeAddress.getKeyAndMask())).build();
      while ((!stack.isEmpty()) && (((XMSSNode)stack.peek()).getHeight() == node.getHeight()))
      {

        int indexOnHeight = (int)Math.floor(indexLeaf / (1 << node.getHeight()));
        if (indexOnHeight == 1)
        {
          authenticationPath.add(node.clone());
        }
        
        if ((indexOnHeight == 3) && (node.getHeight() < treeHeight - k))
        {
          ((BDSTreeHash)treeHashInstances.get(node.getHeight())).setNode(node.clone());
        }
        if ((indexOnHeight >= 3) && ((indexOnHeight & 0x1) == 1) && (node.getHeight() >= treeHeight - k) && 
          (node.getHeight() <= treeHeight - 2))
        {
          if (retain.get(Integer.valueOf(node.getHeight())) == null)
          {
            LinkedList<XMSSNode> queue = new LinkedList();
            queue.add(node.clone());
            retain.put(Integer.valueOf(node.getHeight()), queue);
          }
          else
          {
            ((LinkedList)retain.get(Integer.valueOf(node.getHeight()))).add(node.clone());
          }
        }
        




        hashTreeAddress = (HashTreeAddress)((HashTreeAddress.Builder)((HashTreeAddress.Builder)((HashTreeAddress.Builder)new HashTreeAddress.Builder().withLayerAddress(hashTreeAddress.getLayerAddress())).withTreeAddress(hashTreeAddress.getTreeAddress())).withTreeHeight(hashTreeAddress.getTreeHeight()).withTreeIndex((hashTreeAddress.getTreeIndex() - 1) / 2).withKeyAndMask(hashTreeAddress.getKeyAndMask())).build();
        node = XMSSNodeUtil.randomizeHash(wotsPlus, (XMSSNode)stack.pop(), node, hashTreeAddress);
        node = new XMSSNode(node.getHeight() + 1, node.getValue());
        




        hashTreeAddress = (HashTreeAddress)((HashTreeAddress.Builder)((HashTreeAddress.Builder)((HashTreeAddress.Builder)new HashTreeAddress.Builder().withLayerAddress(hashTreeAddress.getLayerAddress())).withTreeAddress(hashTreeAddress.getTreeAddress())).withTreeHeight(hashTreeAddress.getTreeHeight() + 1).withTreeIndex(hashTreeAddress.getTreeIndex()).withKeyAndMask(hashTreeAddress.getKeyAndMask())).build();
      }
      
      stack.push(node);
    }
    root = ((XMSSNode)stack.pop());
  }
  
  private void nextAuthenticationPath(byte[] publicSeed, byte[] secretSeed, OTSHashAddress otsHashAddress)
  {
    if (otsHashAddress == null)
    {
      throw new NullPointerException("otsHashAddress == null");
    }
    if (used)
    {
      throw new IllegalStateException("index already used");
    }
    if (index > (1 << treeHeight) - 2)
    {
      throw new IllegalStateException("index out of bounds");
    }
    


    LTreeAddress lTreeAddress = (LTreeAddress)((LTreeAddress.Builder)((LTreeAddress.Builder)new LTreeAddress.Builder().withLayerAddress(otsHashAddress.getLayerAddress())).withTreeAddress(otsHashAddress.getTreeAddress())).build();
    

    HashTreeAddress hashTreeAddress = (HashTreeAddress)((HashTreeAddress.Builder)((HashTreeAddress.Builder)new HashTreeAddress.Builder().withLayerAddress(otsHashAddress.getLayerAddress())).withTreeAddress(otsHashAddress.getTreeAddress())).build();
    

    int tau = XMSSUtil.calculateTau(index, treeHeight);
    
    if (((index >> tau + 1 & 0x1) == 0) && (tau < treeHeight - 1))
    {
      keep.put(Integer.valueOf(tau), ((XMSSNode)authenticationPath.get(tau)).clone());
    }
    
    if (tau == 0)
    {




      otsHashAddress = (OTSHashAddress)((OTSHashAddress.Builder)((OTSHashAddress.Builder)((OTSHashAddress.Builder)new OTSHashAddress.Builder().withLayerAddress(otsHashAddress.getLayerAddress())).withTreeAddress(otsHashAddress.getTreeAddress())).withOTSAddress(index).withChainAddress(otsHashAddress.getChainAddress()).withHashAddress(otsHashAddress.getHashAddress()).withKeyAndMask(otsHashAddress.getKeyAndMask())).build();
      



      wotsPlus.importKeys(wotsPlus.getWOTSPlusSecretKey(secretSeed, otsHashAddress), publicSeed);
      WOTSPlusPublicKeyParameters wotsPlusPublicKey = wotsPlus.getPublicKey(otsHashAddress);
      


      lTreeAddress = (LTreeAddress)((LTreeAddress.Builder)((LTreeAddress.Builder)((LTreeAddress.Builder)new LTreeAddress.Builder().withLayerAddress(lTreeAddress.getLayerAddress())).withTreeAddress(lTreeAddress.getTreeAddress())).withLTreeAddress(index).withTreeHeight(lTreeAddress.getTreeHeight()).withTreeIndex(lTreeAddress.getTreeIndex()).withKeyAndMask(lTreeAddress.getKeyAndMask())).build();
      XMSSNode node = XMSSNodeUtil.lTree(wotsPlus, wotsPlusPublicKey, lTreeAddress);
      authenticationPath.set(0, node);


    }
    else
    {


      hashTreeAddress = (HashTreeAddress)((HashTreeAddress.Builder)((HashTreeAddress.Builder)((HashTreeAddress.Builder)new HashTreeAddress.Builder().withLayerAddress(hashTreeAddress.getLayerAddress())).withTreeAddress(hashTreeAddress.getTreeAddress())).withTreeHeight(tau - 1).withTreeIndex(index >> tau).withKeyAndMask(hashTreeAddress.getKeyAndMask())).build();
      XMSSNode node = XMSSNodeUtil.randomizeHash(wotsPlus, (XMSSNode)authenticationPath.get(tau - 1), (XMSSNode)keep.get(Integer.valueOf(tau - 1)), hashTreeAddress);
      node = new XMSSNode(node.getHeight() + 1, node.getValue());
      authenticationPath.set(tau, node);
      keep.remove(Integer.valueOf(tau - 1));
      

      for (int height = 0; height < tau; height++)
      {
        if (height < treeHeight - k)
        {
          authenticationPath.set(height, ((BDSTreeHash)treeHashInstances.get(height)).getTailNode());
        }
        else
        {
          authenticationPath.set(height, ((LinkedList)retain.get(Integer.valueOf(height))).removeFirst());
        }
      }
      

      int minHeight = Math.min(tau, treeHeight - k);
      for (int height = 0; height < minHeight; height++)
      {
        int startIndex = index + 1 + 3 * (1 << height);
        if (startIndex < 1 << treeHeight)
        {
          ((BDSTreeHash)treeHashInstances.get(height)).initialize(startIndex);
        }
      }
    }
    

    for (int i = 0; i < treeHeight - k >> 1; i++)
    {
      BDSTreeHash treeHash = getBDSTreeHashInstanceForUpdate();
      if (treeHash != null)
      {
        treeHash.update(stack, wotsPlus, publicSeed, secretSeed, otsHashAddress);
      }
    }
    
    index += 1;
  }
  
  boolean isUsed()
  {
    return used;
  }
  
  private BDSTreeHash getBDSTreeHashInstanceForUpdate()
  {
    BDSTreeHash ret = null;
    for (BDSTreeHash treeHash : treeHashInstances)
    {
      if ((!treeHash.isFinished()) && (treeHash.isInitialized()))
      {


        if (ret == null)
        {
          ret = treeHash;

        }
        else if (treeHash.getHeight() < ret.getHeight())
        {
          ret = treeHash;

        }
        else if (treeHash.getHeight() == ret.getHeight())
        {
          if (treeHash.getIndexLeaf() < ret.getIndexLeaf())
          {
            ret = treeHash; }
        }
      }
    }
    return ret;
  }
  
  protected void validate()
  {
    if (authenticationPath == null)
    {
      throw new IllegalStateException("authenticationPath == null");
    }
    if (retain == null)
    {
      throw new IllegalStateException("retain == null");
    }
    if (stack == null)
    {
      throw new IllegalStateException("stack == null");
    }
    if (treeHashInstances == null)
    {
      throw new IllegalStateException("treeHashInstances == null");
    }
    if (keep == null)
    {
      throw new IllegalStateException("keep == null");
    }
    if (!XMSSUtil.isIndexValid(treeHeight, index))
    {
      throw new IllegalStateException("index in BDS state out of bounds");
    }
  }
  
  protected int getTreeHeight()
  {
    return treeHeight;
  }
  
  protected XMSSNode getRoot()
  {
    return root.clone();
  }
  
  protected List<XMSSNode> getAuthenticationPath()
  {
    List<XMSSNode> authenticationPath = new ArrayList();
    for (XMSSNode node : this.authenticationPath)
    {
      authenticationPath.add(node.clone());
    }
    return authenticationPath;
  }
  
  protected void setXMSS(XMSSParameters xmss)
  {
    if (treeHeight != xmss.getHeight())
    {
      throw new IllegalStateException("wrong height");
    }
    
    wotsPlus = xmss.getWOTSPlus();
  }
  
  protected int getIndex()
  {
    return index;
  }
}
