package net.minecraft.src;

public abstract interface IFileDownloadListener
{
  public abstract void fileDownloadFinished(String paramString, byte[] paramArrayOfByte, Throwable paramThrowable);
}
