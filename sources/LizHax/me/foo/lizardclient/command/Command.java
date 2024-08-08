package me.foo.lizardclient.command;

public abstract class Command {
  public abstract String getAlias();
  
  public abstract String getDescription();
  
  public abstract String getSyntax();
  
  public abstract void onCommand(String paramString, String[] paramArrayOfString) throws Exception;
}