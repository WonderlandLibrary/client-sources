package net.minecraft.src;

import java.util.*;

public class ComponentStrongholdStairs2 extends ComponentStrongholdStairs
{
    public StructureStrongholdPieceWeight strongholdPieceWeight;
    public ComponentStrongholdPortalRoom strongholdPortalRoom;
    public ArrayList field_75026_c;
    
    public ComponentStrongholdStairs2(final int par1, final Random par2Random, final int par3, final int par4) {
        super(0, par2Random, par3, par4);
        this.field_75026_c = new ArrayList();
    }
    
    @Override
    public ChunkPosition getCenter() {
        return (this.strongholdPortalRoom != null) ? this.strongholdPortalRoom.getCenter() : super.getCenter();
    }
}
