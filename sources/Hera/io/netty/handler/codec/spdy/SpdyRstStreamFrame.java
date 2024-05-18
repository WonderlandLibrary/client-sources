package io.netty.handler.codec.spdy;

public interface SpdyRstStreamFrame extends SpdyStreamFrame {
  SpdyStreamStatus status();
  
  SpdyRstStreamFrame setStatus(SpdyStreamStatus paramSpdyStreamStatus);
  
  SpdyRstStreamFrame setStreamId(int paramInt);
  
  SpdyRstStreamFrame setLast(boolean paramBoolean);
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\spdy\SpdyRstStreamFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */