package com.mojang.authlib.yggdrasil.response;

import java.util.*;
import com.mojang.authlib.properties.*;

public class HasJoinedMinecraftServerResponse extends Response
{
    private UUID id;
    private PropertyMap properties;
    
    public UUID getId() {
        return this.id;
    }
    
    public PropertyMap getProperties() {
        return this.properties;
    }
}
