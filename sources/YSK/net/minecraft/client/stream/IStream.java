package net.minecraft.client.stream;

import tv.twitch.chat.*;
import tv.twitch.broadcast.*;
import tv.twitch.*;

public interface IStream
{
    void func_152935_j();
    
    String func_152921_C();
    
    void stopBroadcasting();
    
    boolean isPaused();
    
    boolean func_152927_B();
    
    void requestCommercial();
    
    ChatUserInfo func_152926_a(final String p0);
    
    void func_152930_t();
    
    IngestServer[] func_152925_v();
    
    ErrorCode func_152912_E();
    
    boolean func_152913_F();
    
    IngestServerTester func_152932_y();
    
    int func_152920_A();
    
    void func_176026_a(final Metadata p0, final long p1, final long p2);
    
    void updateStreamVolume();
    
    void shutdownStream();
    
    boolean func_152908_z();
    
    boolean isBroadcasting();
    
    void pause();
    
    void func_152909_x();
    
    boolean isReadyToBroadcast();
    
    void muteMicrophone(final boolean p0);
    
    void func_152917_b(final String p0);
    
    boolean func_152928_D();
    
    void func_152911_a(final Metadata p0, final long p1);
    
    boolean func_152929_G();
    
    AuthFailureReason func_152918_H();
    
    void unpause();
    
    boolean func_152936_l();
    
    void func_152922_k();
    
    public enum AuthFailureReason
    {
        private static final AuthFailureReason[] ENUM$VALUES;
        private static final String[] I;
        
        INVALID_TOKEN(AuthFailureReason.I[" ".length()], " ".length()), 
        ERROR(AuthFailureReason.I["".length()], "".length());
        
        private AuthFailureReason(final String s, final int n) {
        }
        
        private static void I() {
            (I = new String["  ".length()])["".length()] = I("7(\u00079\u0014", "rzUvF");
            AuthFailureReason.I[" ".length()] = I("9\u00172%\u001f9\u001d;0\u001c;\u001c*", "pYddS");
        }
        
        static {
            I();
            final AuthFailureReason[] enum$VALUES = new AuthFailureReason["  ".length()];
            enum$VALUES["".length()] = AuthFailureReason.ERROR;
            enum$VALUES[" ".length()] = AuthFailureReason.INVALID_TOKEN;
            ENUM$VALUES = enum$VALUES;
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
                if (1 == 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
