package net.minecraft.entity;

public static class WatchableObject
{
    private final int objectType;
    private final int dataValueId;
    private Object watchedObject;
    private boolean watched;
    
    public WatchableObject(final int type, final int id, final Object object) {
        this.dataValueId = id;
        this.watchedObject = object;
        this.objectType = type;
        this.watched = true;
    }
    
    public int getDataValueId() {
        return this.dataValueId;
    }
    
    public Object getObject() {
        return this.watchedObject;
    }
    
    public void setObject(final Object object) {
        this.watchedObject = object;
    }
    
    public int getObjectType() {
        return this.objectType;
    }
    
    public boolean isWatched() {
        return this.watched;
    }
    
    public void setWatched(final boolean watched) {
        this.watched = watched;
    }
}
