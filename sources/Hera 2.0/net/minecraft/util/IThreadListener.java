package net.minecraft.util;

import com.google.common.util.concurrent.ListenableFuture;

public interface IThreadListener {
  ListenableFuture<Object> addScheduledTask(Runnable paramRunnable);
  
  boolean isCallingFromMinecraftThread();
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraf\\util\IThreadListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */