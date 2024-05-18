package net.minecraft.entity;

public interface IEntityOwnable
{
    Entity getOwner();
    
    String getOwnerId();
}
