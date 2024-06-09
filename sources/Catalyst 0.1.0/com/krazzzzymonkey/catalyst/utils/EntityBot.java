// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.utils;

import net.minecraft.entity.player.EntityPlayer;
import java.util.UUID;

public class EntityBot
{
    private /* synthetic */ boolean invisible;
    private /* synthetic */ UUID uuid;
    private /* synthetic */ String name;
    private /* synthetic */ int id;
    private /* synthetic */ boolean ground;
    
    public UUID getUuid() {
        return this.uuid;
    }
    
    public int getId() {
        return this.id;
    }
    
    public boolean isInvisible() {
        return this.invisible;
    }
    
    public String getName() {
        return this.name;
    }
    
    public boolean isGround() {
        return this.ground;
    }
    
    public EntityBot(final EntityPlayer llIIIlIIllllIIl) {
        this.name = String.valueOf(llIIIlIIllllIIl.getGameProfile().getName());
        this.id = llIIIlIIllllIIl.getEntityId();
        this.uuid = llIIIlIIllllIIl.getGameProfile().getId();
        this.invisible = llIIIlIIllllIIl.isInvisible();
        this.ground = llIIIlIIllllIIl.onGround;
    }
}
