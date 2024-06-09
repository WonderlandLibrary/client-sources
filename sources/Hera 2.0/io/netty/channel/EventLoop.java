package io.netty.channel;

import io.netty.util.concurrent.EventExecutor;

public interface EventLoop extends EventExecutor, EventLoopGroup {
  EventLoopGroup parent();
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\EventLoop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */