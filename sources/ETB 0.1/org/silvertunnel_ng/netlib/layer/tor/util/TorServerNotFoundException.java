package org.silvertunnel_ng.netlib.layer.tor.util;

import org.silvertunnel_ng.netlib.layer.tor.api.Fingerprint;



























public class TorServerNotFoundException
  extends TorException
{
  private int pos;
  private NodeType nodeType;
  private Fingerprint fingerprint;
  
  public TorServerNotFoundException() {}
  
  public TorServerNotFoundException(String message)
  {
    super(message);
  }
  






  public TorServerNotFoundException(Fingerprint fingerprint, int pos, NodeType nodeType)
  {
    super("couldn't find server " + fingerprint + " for position " + pos);
    this.fingerprint = fingerprint;
    this.pos = pos;
    this.nodeType = nodeType;
  }
  




  public TorServerNotFoundException(Throwable cause)
  {
    super(cause);
  }
  




  public TorServerNotFoundException(String message, Throwable cause)
  {
    super(message, cause);
  }
  


  public int getPos()
  {
    return pos;
  }
  
  public Fingerprint getFingerprint() { return fingerprint; }
  
  public NodeType getNodeType() {
    return nodeType;
  }
}
