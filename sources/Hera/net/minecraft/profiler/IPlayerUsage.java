package net.minecraft.profiler;

public interface IPlayerUsage {
  void addServerStatsToSnooper(PlayerUsageSnooper paramPlayerUsageSnooper);
  
  void addServerTypeToSnooper(PlayerUsageSnooper paramPlayerUsageSnooper);
  
  boolean isSnooperEnabled();
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\profiler\IPlayerUsage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */