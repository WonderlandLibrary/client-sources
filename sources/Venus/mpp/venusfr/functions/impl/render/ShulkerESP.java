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
import net.minecraft.tileentity.ShulkerBoxTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.optifine.render.RenderUtils;

@FunctionRegister(name="ShulkerESP", type=Category.Visual)
public class ShulkerESP
extends Function {
    private final Map<TileEntityType<?>, Integer> tiles = new HashMap(Map.of(new ShulkerBoxTileEntity().getType(), (Object)new Color(143, 5, 255).getRGB()));

    @Subscribe
    private void onRender(WorldEvent worldEvent) {
        for (TileEntity object : ShulkerESP.mc.world.loadedTileEntityList) {
            if (!this.tiles.containsKey(object.getType())) continue;
            BlockPos blockPos = object.getPos();
            RenderUtils.drawBlockBox(blockPos, this.tiles.get(object.getType()));
        }
        for (Entity entity2 : ShulkerESP.mc.world.getAllEntities()) {
            if (!(entity2 instanceof ChestMinecartEntity)) continue;
            RenderUtils.drawBlockBox(entity2.getPosition(), -1);
        }
    }
}

