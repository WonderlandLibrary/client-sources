package src.Wiksi.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.WorldEvent;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.minecart.ChestMinecartEntity;
import net.minecraft.tileentity.*;
import net.minecraft.util.math.BlockPos;
import net.optifine.render.RenderUtils;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@FunctionRegister(name = "BlockESP", type = Category.Render)
public class StorageESP extends Function {

    private final Map<TileEntityType<?>, Integer> tiles = new HashMap<>(Map.of(
            new ChestTileEntity().getType(), new Color(170, 0, 0).getRGB(),
            new BarrelTileEntity().getType(), new Color(255, 255, 0).getRGB(),
            new EnderChestTileEntity().getType(), new Color(82, 49, 238).getRGB(),
            new ShulkerBoxTileEntity().getType(), new Color(0, 255, 0).getRGB(),
            new MobSpawnerTileEntity().getType(), new Color(112, 236, 166).getRGB()
    ));

    @Subscribe
    private void onRender(WorldEvent e) {
        for (TileEntity tile : mc.world.loadedTileEntityList) {
            if (!tiles.containsKey(tile.getType())) continue;

            BlockPos pos = tile.getPos();

            RenderUtils.drawBlockBox(pos, tiles.get(tile.getType()));

        }

        for (Entity entity : mc.world.getAllEntities()) {
            if (entity instanceof ChestMinecartEntity) {
                RenderUtils.drawBlockBox(entity.getPosition(), -1);
            }
        }
    }

}
