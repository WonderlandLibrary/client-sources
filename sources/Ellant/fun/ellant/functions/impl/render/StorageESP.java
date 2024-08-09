package fun.ellant.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import fun.ellant.events.WorldEvent;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.text.GradientUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.minecart.ChestMinecartEntity;
import net.minecraft.tileentity.*;
import net.minecraft.util.math.BlockPos;
import net.optifine.render.RenderUtils;
import fun.ellant.functions.impl.hud.HUD;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@FunctionRegister(name = "StorageESP", type = Category.RENDER, desc = "Отображает хранилище ")
public class StorageESP extends Function {

    private final Map<TileEntityType<?>, Integer> tiles = new HashMap<>(Map.of(
            new ChestTileEntity().getType(), new Color(230, 145, 82).getRGB(),
            new TrappedChestTileEntity().getType(), new Color(143, 109, 62).getRGB(),
            new BarrelTileEntity().getType(), new Color(250, 250, 62).getRGB(),

            new HopperTileEntity().getType(), new Color(62, 137, 250).getRGB(),
            new DispenserTileEntity().getType(), new Color(27, 64, 250).getRGB(),
            new DropperTileEntity().getType(), new Color(0, 23, 255).getRGB(),

            new FurnaceTileEntity().getType(), new Color(115, 115, 115).getRGB(),
            new EnderChestTileEntity().getType(), new Color(82, 49, 238).getRGB(),
            new ShulkerBoxTileEntity().getType(), new Color(204, 85,105).getRGB(),
            new MobSpawnerTileEntity().getType(), new Color(89,255,255).getRGB()
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