package koks.modules.impl.visuals;

import koks.Koks;
import koks.event.Event;
import koks.event.impl.EventRender3D;
import koks.modules.Module;
import koks.utilities.BoxUtil;
import koks.utilities.CornerESPUtil;
import koks.utilities.value.values.BooleanValue;
import koks.utilities.value.values.ModeValue;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;

import java.awt.*;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 09:58
 */
public class ChestESP extends Module {

    public ModeValue<String> theme = new ModeValue<>("Theme", "Box", new String[]{"2D", "Box"}, this);
    public BoxUtil boxUtil = new BoxUtil();
    public CornerESPUtil cornerESPUtil = new CornerESPUtil();

    public ChestESP() {
        super("ChestESP", "Its mark all chests", Category.VISUALS);

        Koks.getKoks().valueManager.addValue(theme);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventRender3D) {
            setModuleInfo(theme.getSelectedMode());
            for (TileEntity e : mc.theWorld.loadedTileEntityList) {
                if (e instanceof TileEntityChest) {
                    mc.theWorld.getBlockState(e.getPos()).getBlock();
                    double x = (e.getPos().getX() - mc.getRenderManager().renderPosX);
                    double y = (e.getPos().getY() - mc.getRenderManager().renderPosY);
                    double z = (e.getPos().getZ() - mc.getRenderManager().renderPosZ);

                    if (theme.getSelectedMode().equals("2D")) {
                        cornerESPUtil.drawCorners(x + 0.5, y + 0.5, z + 0.5, 16, 16, 8, 3.0F);
                    }

                    if (theme.getSelectedMode().equals("Box")) {
                        AxisAlignedBB axisalignedbb = new AxisAlignedBB(
                                x,
                                y,
                                z,
                                x + 1,
                                y + 1,
                                z + 1);

                        boxUtil.renderOutline(axisalignedbb);
                    }
                }
            }
        }
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

}
