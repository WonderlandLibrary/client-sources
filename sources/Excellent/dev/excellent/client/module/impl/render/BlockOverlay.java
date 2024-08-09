package dev.excellent.client.module.impl.render;

import dev.excellent.api.event.impl.render.Render2DEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.font.Fonts;
import dev.excellent.impl.util.animation.Animation;
import dev.excellent.impl.util.animation.Easing;
import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.render.RenderUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import dev.excellent.impl.value.impl.BooleanValue;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import org.joml.Vector2d;

@ModuleInfo(name = "Block Overlay", description = "Отображает процесс ломания блока.", category = Category.RENDER)
public class BlockOverlay extends Module {
    public final BooleanValue alwaysVisible = new BooleanValue("Видеть всегда", this, false);
    private final Animation barAnimation = new Animation(Easing.EASE_OUT_QUAD, 50);

    public final Listener<Render2DEvent> onRender2D = event -> {
        BlockPos blockPos = findBlockToDisplay();
        if (blockPos != null) {
            BlockState blockState = mc.world.getBlockState(blockPos);
            if (blockState.getBlock() != Blocks.AIR) {
                Vector3d vec = new Vector3d(blockPos.getX() + 0.5, blockPos.getY() + 0.7, blockPos.getZ() + 0.5);
                Vector2d vector2f = RenderUtil.project2D(vec.x, vec.y, vec.z);
                if (alwaysVisible.getValue() || (vector2f != null && mc.playerController.curBlockDamageMP > 0)) {
                    float textWidth = Fonts.INTER_BOLD.get(20).getWidth(blockState.getBlock().asItem().getName().getString());
                    float width = textWidth + 20;
                    float height = 25;
                    float centerX = (float) (vector2f != null ? vector2f.x - (width) / 2F : mc.getMainWindow().getScaledWidth() / 2F - width / 2F);
                    barAnimation.run(width * (1 - mc.playerController.curBlockDamageMP));
                    RenderUtil.renderClientRect(event.getMatrix(), centerX, (float) (vector2f != null ? vector2f.y + 10 : mc.getMainWindow().getScaledHeight() / 2F - height / 2F), width, height, false, 0);
                    RectUtil.drawRect(event.getMatrix(), centerX, (float) (vector2f != null ? vector2f.y + 10 + height : mc.getMainWindow().getScaledHeight() / 2F - height / 2F), (float) (centerX + barAnimation.getValue()), (float) (vector2f != null ? vector2f.y + 10 : mc.getMainWindow().getScaledHeight() / 2F - height / 2F), ColorUtil.getColor(255, 255, 255, 32));
                    Fonts.INTER_BOLD.get(20).drawCenter(event.getMatrix(), blockState.getBlock().asItem().getName().getString(), centerX + width / 2F, vector2f != null ? vector2f.y + 17 : mc.getMainWindow().getScaledHeight() / 2F - height / 2F + 7, -1);
                }
            }
        }
    };

    private BlockPos findBlockToDisplay() {
        RayTraceResult result = mc.objectMouseOver;
        if (result instanceof BlockRayTraceResult overlay) {
            BlockPos pos = overlay.getPos();
            PlayerEntity player = mc.player;
            if (player != null) {
                double reachDistance = 6.0;
                double distanceSq = player.getDistanceSq(pos.getX(), pos.getY(), pos.getZ());
                if (distanceSq <= reachDistance * reachDistance) {
                    return pos;
                }
            }
        }
        return null;
    }
}







