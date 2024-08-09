//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package src.Wiksi.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.EventKey;
import src.Wiksi.events.WorldEvent;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.settings.Setting;
import src.Wiksi.functions.settings.impl.BindSetting;
import src.Wiksi.functions.settings.impl.BooleanSetting;
import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.minecart.ChestMinecartEntity;
import net.minecraft.tileentity.BarrelTileEntity;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TextFormatting;
import net.optifine.render.RenderUtils;

@FunctionRegister(
        name = "BaseFinder",
        type = Category.Misc
)
public class BaseFinder extends Function {
    private BindSetting active = new BindSetting("Кнопка поиска", 0);
    public BooleanSetting notif = new BooleanSetting("Смс об сундуке", true);
    public BooleanSetting gps = new BooleanSetting("GPS на сундук", true);
    public BooleanSetting barrel = new BooleanSetting("GPS на бочку", true);
    private final Map<TileEntityType<?>, Integer> tiles = new HashMap(Map.of((new ChestTileEntity()).getType(), (new Color(0, 187, 8)).getRGB(), (new BarrelTileEntity()).getType(), (new Color(159, 39, 192)).getRGB()));

    public BaseFinder() {
        this.addSettings(new Setting[]{this.active, this.notif, this.gps, this.barrel});
    }

    @Subscribe
    public void onKey(EventKey e) {
        if (e.getKey() == (Integer)this.active.get()) {
            boolean foundChest = false;
            boolean foundBarrel = false;
            Iterator var4 = mc.world.loadedTileEntityList.iterator();

            while(var4.hasNext()) {
                TileEntity t = (TileEntity)var4.next();
                int x;
                int y;
                int z;
                double distanceSq;
                BlockRayTraceResult rayTraceResult;
                if (t instanceof ChestTileEntity) {
                    x = t.getPos().getX();
                    y = t.getPos().getY();
                    z = t.getPos().getZ();
                    distanceSq = mc.player.getDistanceSq((double)x, (double)y, (double)z);
                    if (distanceSq < 20000.0) {
                        foundChest = true;
                        if ((Boolean)this.notif.get()) {
                            this.print(TextFormatting.BLUE + "(BASEFINDER) обнаружен СУНДУК - .gps add Сундук \" + x + \" \" + y + \" \" + z");
                            this.print(TextFormatting.BLUE + "(BASEFINDER) обнаружен СУНДУК - .gps add Сундук \" + x + \" \" + y + \" \" + z");
                            this.print(TextFormatting.BLUE + "(BASEFINDER) обнаружен СУНДУК - .gps add Сундук \" + x + \" \" + y + \" \" + z");
                            this.print(TextFormatting.BLUE + "(BASEFINDER) обнаружен СУНДУК - .gps add Сундук \" + x + \" \" + y + \" \" + z");
                            this.print(TextFormatting.BLUE + "(BASEFINDER) обнаружен СУНДУК - .gps add Сундук \" + x + \" \" + y + \" \" + z");
                        }

                        rayTraceResult = new BlockRayTraceResult(new Vector3d(mc.player.getPosX(), mc.player.getPosY() - 1.0, mc.player.getPosZ()), Direction.UP, new BlockPos(x, y, z), false);
                        mc.playerController.processRightClickBlock(mc.player, mc.world, Hand.MAIN_HAND, rayTraceResult);
                        if ((Boolean)this.gps.get()) {
                        }
                    }
                }

                if (t instanceof BarrelTileEntity) {
                    x = t.getPos().getX();
                    y = t.getPos().getY();
                    z = t.getPos().getZ();
                    distanceSq = mc.player.getDistanceSq((double)x, (double)y, (double)z);
                    if (distanceSq < 20000.0) {
                        foundBarrel = true;
                        if ((Boolean)this.notif.get()) {
                            this.print(TextFormatting.BLUE + "(BASEFINDER) обнаружена БОЧКА - .gps add Бочка \" + x + \" \" + y + \" \" + z");
                            this.print(TextFormatting.BLUE + "(BASEFINDER) обнаружена БОЧКА - .gps add Бочка \" + x + \" \" + y + \" \" + z");
                            this.print(TextFormatting.BLUE + "(BASEFINDER) обнаружена БОЧКА - .gps add Бочка \" + x + \" \" + y + \" \" + z");
                            this.print(TextFormatting.BLUE + "(BASEFINDER) обнаружена БОЧКА - .gps add Бочка \" + x + \" \" + y + \" \" + z");
                            this.print(TextFormatting.BLUE + "(BASEFINDER) обнаружена БОЧКА - .gps add Бочка \" + x + \" \" + y + \" \" + z");
                        }

                        rayTraceResult = new BlockRayTraceResult(new Vector3d(mc.player.getPosX(), mc.player.getPosY() - 1.0, mc.player.getPosZ()), Direction.UP, new BlockPos(x, y, z), false);
                        mc.playerController.processRightClickBlock(mc.player, mc.world, Hand.MAIN_HAND, rayTraceResult);
                        if ((Boolean)this.barrel.get()) {
                        }

                        if (!foundChest && !foundBarrel) {
                            this.print(TextFormatting.RED + "(BASEFINDER) Баз НЕ найдено(");
                        }
                    }
                }
            }
        }

    }

    @Subscribe
    private void onRender(WorldEvent e) {
        Iterator var2 = mc.world.loadedTileEntityList.iterator();

        while(var2.hasNext()) {
            TileEntity tile = (TileEntity)var2.next();
            if (this.tiles.containsKey(tile.getType())) {
                BlockPos pos = tile.getPos();
                RenderUtils.drawBlockBox(pos, (Integer)this.tiles.get(tile.getType()));
            }
        }

        var2 = mc.world.getAllEntities().iterator();

        while(var2.hasNext()) {
            Entity entity = (Entity)var2.next();
            if (entity instanceof ChestMinecartEntity) {
                RenderUtils.drawBlockBox(entity.getPosition(), -1);
            }
        }

    }

    public void onDisable() {
    }
}
