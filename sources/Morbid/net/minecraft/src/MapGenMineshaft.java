package net.minecraft.src;

import java.util.*;

public class MapGenMineshaft extends MapGenStructure
{
    private double field_82673_e;
    
    public MapGenMineshaft() {
        this.field_82673_e = 0.01;
    }
    
    public MapGenMineshaft(final Map par1Map) {
        this.field_82673_e = 0.01;
        for (final Map.Entry var3 : par1Map.entrySet()) {
            if (var3.getKey().equals("chance")) {
                this.field_82673_e = MathHelper.parseDoubleWithDefault(var3.getValue(), this.field_82673_e);
            }
        }
    }
    
    @Override
    protected boolean canSpawnStructureAtCoords(final int par1, final int par2) {
        return this.rand.nextDouble() < this.field_82673_e && this.rand.nextInt(80) < Math.max(Math.abs(par1), Math.abs(par2));
    }
    
    @Override
    protected StructureStart getStructureStart(final int par1, final int par2) {
        return new StructureMineshaftStart(this.worldObj, this.rand, par1, par2);
    }
}
