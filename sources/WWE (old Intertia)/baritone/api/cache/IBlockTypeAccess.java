package baritone.api.cache;

public interface IBlockTypeAccess
{
    awt getBlock(final int p0, final int p1, final int p2);
    
    default awt getBlock(final et et) {
        return this.getBlock(et.p(), et.q(), et.r());
    }
}
