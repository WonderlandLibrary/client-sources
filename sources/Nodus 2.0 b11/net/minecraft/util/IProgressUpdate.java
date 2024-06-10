package net.minecraft.util;

public abstract interface IProgressUpdate
{
  public abstract void displayProgressMessage(String paramString);
  
  public abstract void resetProgressAndMessage(String paramString);
  
  public abstract void resetProgresAndWorkingMessage(String paramString);
  
  public abstract void setLoadingProgress(int paramInt);
  
  public abstract void func_146586_a();
}


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.IProgressUpdate
 * JD-Core Version:    0.7.0.1
 */