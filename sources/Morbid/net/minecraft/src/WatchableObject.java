package net.minecraft.src;

public class WatchableObject
{
    private final int objectType;
    private final int dataValueId;
    private Object watchedObject;
    private boolean watched;
    
    public WatchableObject(final int par1, final int par2, final Object par3Obj) {
        this.dataValueId = par2;
        this.watchedObject = par3Obj;
        this.objectType = par1;
        this.watched = true;
    }
    
    public int getDataValueId() {
        return this.dataValueId;
    }
    
    public void setObject(final Object par1Obj) {
        this.watchedObject = par1Obj;
    }
    
    public Object getObject() {
        return this.watchedObject;
    }
    
    public int getObjectType() {
        return this.objectType;
    }
    
    public boolean isWatched() {
        return this.watched;
    }
    
    public void setWatched(final boolean par1) {
        this.watched = par1;
    }
    
    static boolean setWatchableObjectWatched(final WatchableObject par0WatchableObject, final boolean par1) {
        return par0WatchableObject.watched = par1;
    }
}
