package space.lunaclient.luna.impl.irc;

public class IrcChatLine
{
  private int index;
  private String line;
  private String sender;
  private boolean read;
  
  public IrcChatLine(int index, String line, String sender, boolean read)
  {
    this.index = index;
    this.line = line;
    this.sender = sender;
    this.read = read;
  }
  
  public int getIndex()
  {
    return this.index;
  }
  
  public void setIndex(int index)
  {
    this.index = index;
  }
  
  public String getLine()
  {
    return this.line;
  }
  
  public void setLine(String line)
  {
    this.line = line;
  }
  
  public String getSender()
  {
    return this.sender;
  }
  
  public void setSender(String sender)
  {
    this.sender = sender;
  }
  
  public boolean isRead()
  {
    return this.read;
  }
  
  public void setRead(boolean read)
  {
    this.read = read;
  }
}
