package net.minecraft.src;

public abstract interface IFileUploadListener
{
  public abstract void fileUploadFinished(String paramString, byte[] paramArrayOfByte, Throwable paramThrowable);
}
