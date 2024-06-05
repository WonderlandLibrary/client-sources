package koks.modules.impl.visuals;

import koks.Koks;
import koks.event.Event;
import koks.event.impl.EventRender3D;
import koks.modules.Module;
import koks.utilities.BoxUtil;
import koks.utilities.CornerESPUtil;
import koks.utilities.value.values.BooleanValue;
import koks.utilities.value.values.ModeValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.AxisAlignedBB;

import java.awt.*;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 09:58
 */
public class ItemESP extends Module {

    public ModeValue<String> theme = new ModeValue<>("Theme", "Box", new String[]{"2D", "Box"}, this);
    public BoxUtil boxUtil = new BoxUtil();
    public CornerESPUtil cornerESPUtil = new CornerESPUtil();

    public ItemESP() {
        super("ItemESP", "Its mark all Items", Category.VISUALS);

        Koks.getKoks().valueManager.addValue(theme);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventRender3D) {
            setModuleInfo(theme.getSelectedMode());
            for (Entity entity : mc.theWorld.loadedEntityList) {
                if (entity instanceof EntityItem) {
                    EntityItem e = (EntityItem) entity;
                    double x = (e.lastTickPosX + (e.posX - e.lastTickPosX) * ((EventRender3D) event).getPartialTicks()) - mc.getRenderManager().renderPosX;
                    double y = (e.lastTickPosY + (e.posY - e.lastTickPosY) * ((EventRender3D) event).getPartialTicks()) - mc.getRenderManager().renderPosY;
                    double z = (e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * ((EventRender3D) event).getPartialTicks()) - mc.getRenderManager().renderPosZ;

                    if (theme.getSelectedMode().equals("2D")) {
                        cornerESPUtil.drawCorners(x, y + 0.30, z, 5, 5, 1, 3.0F);
                    }

                    if (theme.getSelectedMode().equals("Box")) {
                        double size = 0.25;

                        AxisAlignedBB axisalignedbb = new AxisAlignedBB(
                                x - size,
                                y,
                                z - size,
                                x + size,
                                y + size * 2,
                                z + size);

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