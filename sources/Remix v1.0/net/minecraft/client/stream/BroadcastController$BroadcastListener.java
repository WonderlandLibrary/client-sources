package net.minecraft.client.stream;

import tv.twitch.*;
import tv.twitch.broadcast.*;

public interface BroadcastListener
{
    void func_152900_a(final ErrorCode p0, final AuthToken p1);
    
    void func_152897_a(final ErrorCode p0);
    
    void func_152898_a(final ErrorCode p0, final GameInfo[] p1);
    
    void func_152891_a(final BroadcastState p0);
    
    void func_152895_a();
    
    void func_152894_a(final StreamInfo p0);
    
    void func_152896_a(final IngestList p0);
    
    void func_152893_b(final ErrorCode p0);
    
    void func_152899_b();
    
    void func_152901_c();
    
    void func_152892_c(final ErrorCode p0);
}
