package net.minecraftforge.client.model;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.data.IModelData;

import java.util.Collections;
import java.util.Map;

public class ModelDataManager {
    public static Map<BlockPos, IModelData> getModelData(World world, ChunkPos pos) {
        return Collections.emptyMap();
    }
}
