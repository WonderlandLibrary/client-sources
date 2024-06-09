package net.minecraft.world.border;

public interface IBorderListener {
  void onSizeChanged(WorldBorder paramWorldBorder, double paramDouble);
  
  void onTransitionStarted(WorldBorder paramWorldBorder, double paramDouble1, double paramDouble2, long paramLong);
  
  void onCenterChanged(WorldBorder paramWorldBorder, double paramDouble1, double paramDouble2);
  
  void onWarningTimeChanged(WorldBorder paramWorldBorder, int paramInt);
  
  void onWarningDistanceChanged(WorldBorder paramWorldBorder, int paramInt);
  
  void onDamageAmountChanged(WorldBorder paramWorldBorder, double paramDouble);
  
  void onDamageBufferChanged(WorldBorder paramWorldBorder, double paramDouble);
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\world\border\IBorderListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */