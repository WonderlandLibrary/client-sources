package net.minecraft.client.stream;

import tv.twitch.broadcast.*;
import tv.twitch.chat.*;
import tv.twitch.*;

public class NullStream implements IStream
{
    private final Throwable field_152938_a;
    
    @Override
    public IngestServerTester func_152932_y() {
        return null;
    }
    
    @Override
    public boolean isReadyToBroadcast() {
        return "".length() != 0;
    }
    
    @Override
    public void func_152922_k() {
    }
    
    @Override
    public boolean func_152928_D() {
        return "".length() != 0;
    }
    
    @Override
    public boolean func_152936_l() {
        return "".length() != 0;
    }
    
    @Override
    public void unpause() {
    }
    
    public Throwable func_152937_a() {
        return this.field_152938_a;
    }
    
    @Override
    public void updateStreamVolume() {
    }
    
    @Override
    public void func_152911_a(final Metadata metadata, final long n) {
    }
    
    public NullStream(final Throwable field_152938_a) {
        this.field_152938_a = field_152938_a;
    }
    
    @Override
    public boolean isBroadcasting() {
        return "".length() != 0;
    }
    
    @Override
    public boolean func_152908_z() {
        return "".length() != 0;
    }
    
    @Override
    public void pause() {
    }
    
    @Override
    public AuthFailureReason func_152918_H() {
        return AuthFailureReason.ERROR;
    }
    
    @Override
    public void func_152935_j() {
    }
    
    @Override
    public void func_152930_t() {
    }
    
    @Override
    public boolean func_152927_B() {
        return "".length() != 0;
    }
    
    @Override
    public void shutdownStream() {
    }
    
    @Override
    public void stopBroadcasting() {
    }
    
    @Override
    public IngestServer[] func_152925_v() {
        return new IngestServer["".length()];
    }
    
    @Override
    public void requestCommercial() {
    }
    
    @Override
    public String func_152921_C() {
        return null;
    }
    
    @Override
    public ChatUserInfo func_152926_a(final String s) {
        return null;
    }
    
    @Override
    public boolean func_152929_G() {
        return "".length() != 0;
    }
    
    @Override
    public void muteMicrophone(final boolean b) {
    }
    
    @Override
    public ErrorCode func_152912_E() {
        return null;
    }
    
    @Override
    public void func_176026_a(final Metadata metadata, final long n, final long n2) {
    }
    
    @Override
    public int func_152920_A() {
        return "".length();
    }
    
    @Override
    public void func_152909_x() {
    }
    
    @Override
    public boolean isPaused() {
        return "".length() != 0;
    }
    
    @Override
    public boolean func_152913_F() {
        return "".length() != 0;
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
            if (4 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void func_152917_b(final String s) {
    }
}
