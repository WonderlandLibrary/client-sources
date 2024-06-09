// 
// Decompiled by Procyon v0.5.36
// 

package de.liquiddev.ircclient.api;

public abstract class SimpleIrcApi implements IrcApi
{
    static final String DEFAULT_PREFIX;
    private String prefix;
    
    static {
        DEFAULT_PREFIX = String.valueOf(new SimpleIrcApi$1().toString()) + "§" + new SimpleIrcApi$2().toString() + "§" + new SimpleIrcApi$3().toString() + "§" + new SimpleIrcApi$4().toString() + "§" + new SimpleIrcApi$5().toString();
    }
    
    public SimpleIrcApi() {
        this.prefix = SimpleIrcApi.DEFAULT_PREFIX;
    }
    
    public abstract void addChat(final String p0);
    
    @Override
    public void onChatMessage(final String chatMessage) {
        this.addChat(String.valueOf(this.getPrefix()) + chatMessage);
    }
    
    @Override
    public void onPlayerChatMessage(final String player, final String client, final String message) {
        this.addChat(String.valueOf(new SimpleIrcApi$6(this).toString()) + "§" + new SimpleIrcApi$7(this).toString() + client + new SimpleIrcApi$8(this).toString() + player + new SimpleIrcApi$9(this).toString() + "§" + new SimpleIrcApi$10(this).toString() + "§" + new SimpleIrcApi$11(this).toString() + message);
    }
    
    @Override
    public void onLocalPlayerChatMessage(final String player, final String client, final String message) {
        this.addChat(String.valueOf(new SimpleIrcApi$12(this).toString()) + "§" + new SimpleIrcApi$13(this).toString() + "§" + new SimpleIrcApi$14(this).toString() + "§" + new SimpleIrcApi$15(this).toString() + "§" + new SimpleIrcApi$16(this).toString() + client + new SimpleIrcApi$17(this).toString() + player + new SimpleIrcApi$18(this).toString() + "§" + new SimpleIrcApi$19(this).toString() + "§" + new SimpleIrcApi$20(this).toString() + message);
    }
    
    @Override
    public void onWhisperMessage(final String player, final String message, final boolean isFromMe) {
        if (isFromMe) {
            this.addChat(String.valueOf(this.getPrefix()) + new SimpleIrcApi$21(this).toString() + "§" + new SimpleIrcApi$22(this).toString() + "§" + new SimpleIrcApi$23(this).toString() + "§" + new SimpleIrcApi$24(this).toString() + player + new SimpleIrcApi$25(this).toString() + "§" + new SimpleIrcApi$26(this).toString() + message);
            return;
        }
        this.addChat(String.valueOf(this.getPrefix()) + new SimpleIrcApi$27(this).toString() + "§" + new SimpleIrcApi$28(this).toString() + player + new SimpleIrcApi$29(this).toString() + "§" + new SimpleIrcApi$30(this).toString() + "§" + new SimpleIrcApi$31(this).toString() + "§" + new SimpleIrcApi$32(this).toString() + message);
    }
    
    public String getPrefix() {
        return this.prefix;
    }
    
    public SimpleIrcApi setPrefix(final String prefix) {
        this.prefix = prefix;
        return this;
    }
}
