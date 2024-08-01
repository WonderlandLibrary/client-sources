package wtf.diablo.client.module.impl.render;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.util.BlockPos;
import wtf.diablo.client.event.impl.client.renderering.Render3DEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.util.render.RenderUtil;

import java.util.ArrayList;

@ModuleMetaData(name = "Trail", description = "Draws a trail behind you", category = ModuleCategoryEnum.RENDER)
public final class TrailModule extends AbstractModule {
    private PositionList positionList;

    @EventHandler
    private final Listener<Render3DEvent> render3DEventListener = event -> {
        if (this.positionList == null)
            this.positionList = new PositionList();

        this.positionList.addPosition(new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ));
        for (int i = 0; i < this.positionList.size() - 1; ++i) {
            final BlockPos blockPos = this.positionList.get(i);
            final BlockPos blockPos2 = this.positionList.get(i + 1);
            RenderUtil.drawLineToPosition(blockPos.getX(), blockPos.getY(), blockPos.getZ(),blockPos.getX(),blockPos.getY(),blockPos.getZ(),1, -1);
        }
    };

    private static class PositionList extends ArrayList<BlockPos> {
        private PositionList() {
            super();
        }

        public void addPosition(final BlockPos blockPos) {
            this.add(blockPos);
            if (this.size() > 15)
                this.remove(0);
        }

    }
}
