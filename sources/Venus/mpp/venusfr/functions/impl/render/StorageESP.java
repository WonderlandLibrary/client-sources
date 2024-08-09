/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import mpp.venusfr.events.WorldEvent;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.minecart.ChestMinecartEntity;
import net.minecraft.tileentity.BarrelTileEntity;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.DispenserTileEntity;
import net.minecraft.tileentity.DropperTileEntity;
import net.minecraft.tileentity.EnderChestTileEntity;
import net.minecraft.tileentity.FurnaceTileEntity;
import net.minecraft.tileentity.HopperTileEntity;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.ShulkerBoxTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.tileentity.TrappedChestTileEntity;
import net.minecraft.util.math.BlockPos;
import net.optifine.render.RenderUtils;

@FunctionRegister(name="StorageESP", type=Category.Visual)
public class StorageESP
extends Function {
    private final Map<TileEntityType<?>, Integer> tiles = new HashMap(Map.of(new ChestTileEntity().getType(), (Object)new Color(243, 172, 82).getRGB(), new TrappedChestTileEntity().getType(), (Object)new Color(143, 109, 62).getRGB(), new BarrelTileEntity().getType(), (Object)new Color(250, 225, 62).getRGB(), new HopperTileEntity().getType(), (Object)new Color(62, 137, 250).getRGB(), new DispenserTileEntity().getType(), (Object)new Color(27, 64, 250).getRGB(), new DropperTileEntity().getType(), (Object)new Color(0, 23, 255).getRGB(), new FurnaceTileEntity().getType(), (Object)new Color(115, 115, 115).getRGB(), new EnderChestTileEntity().getType(), (Object)new Color(82, 49, 238).getRGB(), new ShulkerBoxTileEntity().getType(), (Object)new Color(246, 123, 123).getRGB(), new MobSpawnerTileEntity().getType(), (Object)new Color(112, 236, 166).getRGB()));

    @Subscribe
    private void onRender(WorldEvent worldEvent) {
        for (TileEntity object : StorageESP.mc.world.loadedTileEntityList) {
            if (!this.tiles.containsKey(object.getType())) continue;
            BlockPos blockPos = object.getPos();
            RenderUtils.drawBlockBox(blockPos, this.tiles.get(object.getType()));
        }
        for (Entity entity2 : StorageESP.mc.world.getAllEntities()) {
            if (!(entity2 instanceof ChestMinecartEntity)) continue;
            RenderUtils.drawBlockBox(entity2.getPosition(), -1);
        }
    }
}

