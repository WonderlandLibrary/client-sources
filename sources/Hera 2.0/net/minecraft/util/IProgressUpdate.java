package net.minecraft.util;

public interface IProgressUpdate {
  void displaySavingString(String paramString);
  
  void resetProgressAndMessage(String paramString);
  
  void displayLoadingString(String paramString);
  
  void setLoadingProgress(int paramInt);
  
  void setDoneWorking();
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraf\\util\IProgressUpdate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */