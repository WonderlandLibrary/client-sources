package me.mees.remix.irc;

public class IrcChatLine
{
    private int index;
    private String line;
    private String sender;
    private boolean read;
    
    public IrcChatLine(final int index, final String line, final String sender, final boolean read) {
        this.index = index;
        this.line = line;
        this.sender = sender;
        this.read = read;
    }
    
    public int getIndex() {
        return this.index;
    }
    
    public void setIndex(final int index) {
        this.index = index;
    }
    
    public String getLine() {
        return this.line;
    }
    
    public void setLine(final String line) {
        this.line = line;
    }
    
    public String getSender() {
        return this.sender;
    }
    
    public void setSender(final String sender) {
        this.sender = sender;
    }
    
    public boolean isRead() {
        return this.read;
    }
    
    public void setRead(final boolean read) {
        this.read = read;
    }
}
