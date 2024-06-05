package net.minecraft.client.stream;

import tv.twitch.*;
import tv.twitch.chat.*;

public interface ChatListener
{
    void func_176023_d(final ErrorCode p0);
    
    void func_176022_e(final ErrorCode p0);
    
    void func_176021_d();
    
    void func_176024_e();
    
    void func_176017_a(final ChatState p0);
    
    void func_176025_a(final String p0, final ChatTokenizedMessage[] p1);
    
    void func_180605_a(final String p0, final ChatRawMessage[] p1);
    
    void func_176018_a(final String p0, final ChatUserInfo[] p1, final ChatUserInfo[] p2, final ChatUserInfo[] p3);
    
    void func_180606_a(final String p0);
    
    void func_180607_b(final String p0);
    
    void func_176019_a(final String p0, final String p1);
    
    void func_176016_c(final String p0);
    
    void func_176020_d(final String p0);
}
