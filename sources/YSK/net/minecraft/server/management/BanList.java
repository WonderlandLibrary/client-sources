package net.minecraft.server.management;

import java.net.*;
import com.google.gson.*;
import java.io.*;

public class BanList extends UserList<String, IPBanEntry>
{
    private static final String[] I;
    
    public boolean isBanned(final SocketAddress socketAddress) {
        return ((UserList<String, V>)this).hasEntry(this.addressToString(socketAddress));
    }
    
    @Override
    protected UserListEntry<String> createEntry(final JsonObject jsonObject) {
        return new IPBanEntry(jsonObject);
    }
    
    static {
        I();
    }
    
    private String addressToString(final SocketAddress socketAddress) {
        String s = socketAddress.toString();
        if (s.contains(BanList.I["".length()])) {
            s = s.substring(s.indexOf(0xAF ^ 0x80) + " ".length());
        }
        if (s.contains(BanList.I[" ".length()])) {
            s = s.substring("".length(), s.indexOf(0x3 ^ 0x39));
        }
        return s;
    }
    
    public IPBanEntry getBanEntry(final SocketAddress socketAddress) {
        return this.getEntry(this.addressToString(socketAddress));
    }
    
    public BanList(final File file) {
        super(file);
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("z", "UpVch");
        BanList.I[" ".length()] = I("q", "KxQuK");
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
}
