package wtf.shiyeno.modules.impl.util;

import java.util.Iterator;
import net.minecraft.tileentity.BarrelTileEntity;
import net.minecraft.tileentity.EnderChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.game.EventKey;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.BindSetting;
import wtf.shiyeno.util.misc.TimerUtil;

@FunctionAnnotation(
        name = "BindBlock",
        type = Type.Util
)
public class BindBlock extends Function {
    private final TimerUtil timerUtil = new TimerUtil();
    private BindSetting EC = new BindSetting("Кнопка открытия эндер сундука", 0);
    private BindSetting barrel = new BindSetting("Кнопка открытия Бочки", 0);

    public BindBlock() {
        this.addSettings(new Setting[]{this.EC, this.barrel});
    }

    public void onEvent(Event event) {
        if (event instanceof EventKey e) {
            Iterator var3;
            TileEntity t;
            int x;
            int y;
            int z;
            BlockRayTraceResult rayTraceResult;
            if (e.key == this.EC.getKey()) {
                var3 = mc.world.loadedTileEntityList.iterator();

                while(var3.hasNext()) {
                    t = (TileEntity)var3.next();
                    if (t instanceof EnderChestTileEntity) {
                        x = t.getPos().getX();
                        y = t.getPos().getY();
                        z = t.getPos().getZ();
                        if (mc.player.getDistanceSq((double)x, (double)y, (double)z) < 35.0) {
                            rayTraceResult = new BlockRayTraceResult(new Vector3d(mc.player.getPosX(), mc.player.getPosY() - 1.0, mc.player.getPosZ()), Direction.UP, new BlockPos(x, y, z), false);
                            mc.playerController.processRightClickBlock(mc.player, mc.world, Hand.MAIN_HAND, rayTraceResult);
                        }
                    }
                }
            }

            if (e.key == this.barrel.getKey()) {
                var3 = mc.world.loadedTileEntityList.iterator();

                while(var3.hasNext()) {
                    t = (TileEntity)var3.next();
                    if (t instanceof BarrelTileEntity) {
                        x = t.getPos().getX();
                        y = t.getPos().getY();
                        z = t.getPos().getZ();
                        if (mc.player.getDistanceSq((double)x, (double)y, (double)z) < 35.0) {
                            rayTraceResult = new BlockRayTraceResult(new Vector3d(mc.player.getPosX(), mc.player.getPosY() - 1.0, mc.player.getPosZ()), Direction.UP, new BlockPos(x, y, z), false);
                            mc.playerController.processRightClickBlock(mc.player, mc.world, Hand.MAIN_HAND, rayTraceResult);
                        }
                    }
                }
            }
        }
    }

    public void onDisable() {
        mc.gameSettings.keyBindUseItem.setPressed(false);
        super.onDisable();
    }
}