// 
// Decompiled by Procyon v0.5.36
// 

package net.arikia.dev.drpc;

import java.util.Arrays;
import java.util.List;
import com.sun.jna.Structure;

public class DiscordUser extends Structure
{
    public String userId;
    public String username;
    public String discriminator;
    public String avatar;
    
    public List<String> getFieldOrder() {
        return Arrays.asList("userId", "username", "discriminator", "avatar");
    }
}
