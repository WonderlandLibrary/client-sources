package wtf.shiyeno.modules.impl.combat;

import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.render.EventRender;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.BooleanOption;
import wtf.shiyeno.modules.settings.imp.SliderSetting;

@FunctionAnnotation(
        name = "HitBoxes",
        type = Type.Combat
)
public class HitBoxFunction extends Function {
    public final SliderSetting size = new SliderSetting("Размер", 0.2F, 0.0F, 3.0F, 0.05F);
    public final BooleanOption invisible = new BooleanOption("Невидимые", false);

    public HitBoxFunction() {
        this.addSettings(new Setting[]{this.size, this.invisible});
    }

    public void onEvent(Event event) {
        this.handleEvent(event);
    }

    private void handleEvent(Event event) {
        if (event instanceof EventRender && ((EventRender)event).isRender3D()) {
            if (!this.invisible.get()) {
                this.adjustBoundingBoxesForPlayers();
            }
        }
    }

    private void adjustBoundingBoxesForPlayers() {
        Iterator var1 = mc.world.getPlayers().iterator();

        while(var1.hasNext()) {
            PlayerEntity player = (PlayerEntity)var1.next();
            if (!this.shouldSkipPlayer(player)) {
                float sizeMultiplier = this.size.getValue().floatValue() * 2.5F;
                this.setBoundingBox(player, sizeMultiplier);
            }
        }
    }

    private boolean shouldSkipPlayer(PlayerEntity player) {
        return player == mc.player || !player.isAlive();
    }

    private void setBoundingBox(Entity entity, float size) {
        AxisAlignedBB newBoundingBox = this.calculateBoundingBox(entity, size);
        entity.setBoundingBox(newBoundingBox);
    }

    private AxisAlignedBB calculateBoundingBox(Entity entity, float size) {
        double minX = entity.getPosX() - (double)size;
        double minY = entity.getBoundingBox().minY;
        double minZ = entity.getPosZ() - (double)size;
        double maxX = entity.getPosX() + (double)size;
        double maxY = entity.getBoundingBox().maxY;
        double maxZ = entity.getPosZ() + (double)size;
        return new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
    }
}