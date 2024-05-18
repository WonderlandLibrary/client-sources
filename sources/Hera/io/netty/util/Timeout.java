package io.netty.util;

public interface Timeout {
  Timer timer();
  
  TimerTask task();
  
  boolean isExpired();
  
  boolean isCancelled();
  
  boolean cancel();
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\nett\\util\Timeout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */