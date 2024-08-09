package dev.luvbeeq.baritone.utils.schematic.format.defaults;

import dev.luvbeeq.baritone.utils.schematic.StaticSchematic;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.fixes.ItemIntIDToString;
import net.minecraft.util.registry.Registry;

/**
 * @author Brady
 * @since 12/27/2019
 */
public final class MCEditSchematic extends StaticSchematic {

    public MCEditSchematic(CompoundNBT schematic) {
        String type = schematic.getString("Materials");
        if (!type.equals("Alpha")) {
            throw new IllegalStateException("bad schematic " + type);
        }
        this.x = schematic.getInt("Width");
        this.y = schematic.getInt("Height");
        this.z = schematic.getInt("Length");
        byte[] blocks = schematic.getByteArray("Blocks");
//        byte[] metadata = schematic.getByteArray("Data");

        byte[] additional = null;
        if (schematic.contains("AddBlocks")) {
            byte[] addBlocks = schematic.getByteArray("AddBlocks");
            additional = new byte[addBlocks.length * 2];
            for (int i = 0; i < addBlocks.length; i++) {
                additional[i * 2 + 0] = (byte) ((addBlocks[i] >> 4) & 0xF); // lower nibble
                additional[i * 2 + 1] = (byte) ((addBlocks[i] >> 0) & 0xF); // upper nibble
            }
        }
        this.states = new BlockState[this.x][this.z][this.y];
        for (int y = 0; y < this.y; y++) {
            for (int z = 0; z < this.z; z++) {
                for (int x = 0; x < this.x; x++) {
                    int blockInd = (y * this.z + z) * this.x + x;

                    int blockID = blocks[blockInd] & 0xFF;
                    if (additional != null) {
                        // additional is 0 through 15 inclusive since it's & 0xF above
                        blockID |= additional[blockInd] << 8;
                    }
                    Block block = Registry.BLOCK.getOrDefault(ResourceLocation.tryCreate(ItemIntIDToString.getItem(blockID)));
//                    int meta = metadata[blockInd] & 0xFF;
//                    this.states[x][z][y] = block.getStateFromMeta(meta);
                    this.states[x][z][y] = block.getDefaultState();
                }
            }
        }
    }
}
