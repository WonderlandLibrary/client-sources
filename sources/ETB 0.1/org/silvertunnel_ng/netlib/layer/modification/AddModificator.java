package org.silvertunnel_ng.netlib.layer.modification;










public class AddModificator
  implements ByteModificator
{
  private final byte offset;
  








  public AddModificator(int offset)
  {
    this.offset = ((byte)offset);
  }
  

  public byte modify(byte in)
  {
    return (byte)(in + offset);
  }
}
