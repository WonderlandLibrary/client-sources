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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;

import java.awt.*;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 09:58
 */
public class PlayerESP extends Module {

    public ModeValue<String> theme = new ModeValue<>("Theme", "Box", new String[]{"2D", "Box"}, this);
    public BoxUtil boxUtil = new BoxUtil();
    public CornerESPUtil cornerESPUtil = new CornerESPUtil();

    public PlayerESP() {
        super("PlayerESP", "Its mark all players", Category.VISUALS);

        Koks.getKoks().valueManager.addValue(theme);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventRender3D) {
            setModuleInfo(theme.getSelectedMode());
            float partialTicks = ((EventRender3D) event).getPartialTicks();
            for (Entity entity : mc.theWorld.loadedEntityList) {
                if (Koks.getKoks().moduleManager.getModule(NameTags.class).isValid(entity)) {
                    double x = (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks) - mc.getRenderManager().renderPosX;
                    double y = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks) - mc.getRenderManager().renderPosY;
                    double z = (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks) - mc.getRenderManager().renderPosZ;

                    if (theme.getSelectedMode().equals("2D")) {
                        cornerESPUtil.drawCorners(x, y + 0.9, z, 20, 40, 10, 3.0F);
                    }

                    if (theme.getSelectedMode().equals("Box")) {
                        float width = 0.16F;

                        AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox();
                        AxisAlignedBB axisalignedbb1 = new AxisAlignedBB(
                                axisalignedbb.minX - entity.posX + x - width,
                                axisalignedbb.minY - entity.posY + y + 0.01,
                                axisalignedbb.minZ - entity.posZ + z - width,
                                axisalignedbb.maxX - entity.posX + x + width,
                                axisalignedbb.maxY - entity.posY + y + 0.19 - (entity.isSneaking() ? 0.25 : 0),
                                axisalignedbb.maxZ - entity.posZ + z + width);

                        boxUtil.renderOutline(axisalignedbb1);
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