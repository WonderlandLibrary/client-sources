package org.newdawn.slick.util;

public interface LogSystem {
  void error(String paramString, Throwable paramThrowable);
  
  void error(Throwable paramThrowable);
  
  void error(String paramString);
  
  void warn(String paramString);
  
  void warn(String paramString, Throwable paramThrowable);
  
  void info(String paramString);
  
  void debug(String paramString);
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\org\newdawn\slic\\util\LogSystem.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */