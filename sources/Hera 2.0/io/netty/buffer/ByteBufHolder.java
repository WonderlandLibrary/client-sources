package io.netty.buffer;

import io.netty.util.ReferenceCounted;

public interface ByteBufHolder extends ReferenceCounted {
  ByteBuf content();
  
  ByteBufHolder copy();
  
  ByteBufHolder duplicate();
  
  ByteBufHolder retain();
  
  ByteBufHolder retain(int paramInt);
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\buffer\ByteBufHolder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */