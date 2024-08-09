package im.expensive.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import im.expensive.events.WorldEvent;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.minecart.ChestMinecartEntity;
import net.minecraft.tileentity.*;
import net.minecraft.util.math.BlockPos;
import net.optifine.render.RenderUtils;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@FunctionRegister(name = "StorageESP", type = Category.Render)
public class StorageESP extends Function {

    private final Map<TileEntityType<?>, Integer> tiles = new HashMap<>(Map.of(
            new ChestTileEntity().getType(), new Color(243, 172, 82).getRGB(),
            new TrappedChestTileEntity().getType(), new Color(143, 109, 62).getRGB(),
            new BarrelTileEntity().getType(), new Color(250, 225, 62).getRGB(),

            new HopperTileEntity().getType(), new Color(62, 137, 250).getRGB(),
            new DispenserTileEntity().getType(), new Color(27, 64, 250).getRGB(),
            new DropperTileEntity().getType(), new Color(0, 23, 255).getRGB(),

            new FurnaceTileEntity().getType(), new Color(115, 115, 115).getRGB(),
            new EnderChestTileEntity().getType(), new Color(82, 49, 238).getRGB(),
            new ShulkerBoxTileEntity().getType(), new Color(246, 123, 123).getRGB(),
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
