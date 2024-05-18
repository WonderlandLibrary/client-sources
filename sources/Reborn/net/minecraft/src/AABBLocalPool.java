package net.minecraft.src;

final class AABBLocalPool extends ThreadLocal
{
    protected AABBPool createNewDefaultPool() {
        return new AABBPool(300, 2000);
    }
    
    @Override
    protected Object initialValue() {
        return this.createNewDefaultPool();
    }
}
