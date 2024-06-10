package net.minecraft.profiler;

public abstract interface IPlayerUsage
{
  public abstract void addServerStatsToSnooper(PlayerUsageSnooper paramPlayerUsageSnooper);
  
  public abstract void addServerTypeToSnooper(PlayerUsageSnooper paramPlayerUsageSnooper);
  
  public abstract boolean isSnooperEnabled();
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.profiler.IPlayerUsage
 * JD-Core Version:    0.7.0.1
 */