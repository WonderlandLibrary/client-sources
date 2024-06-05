package net.minecraft.tileentity;

public class TileEntityDropper extends TileEntityDispenser
{
    @Override
    public String getName() {
        return this.hasCustomName() ? this.field_146020_a : "container.dropper";
    }
    
    @Override
    public String getGuiID() {
        return "minecraft:dropper";
    }
}
