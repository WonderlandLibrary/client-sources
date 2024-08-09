package wtf.shiyeno.modules.impl.player;

import net.minecraft.block.Blocks;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.player.EventMove;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.ModeSetting;

@FunctionAnnotation(
        name = "NoClip",
        type = Type.Player
)
public class NoClip extends Function {
    private final ModeSetting actions = new ModeSetting("Выбор ноуклипа", "Matrix", new String[]{"default", "Matrix", "AirZoneRW"});

    public NoClip() {
        this.addSettings(new Setting[]{this.actions});
    }

    public void onEvent(Event event) {
        if (event instanceof EventMove move) {
            if (this.actions.is("default") && !this.collisionPredict(move.to())) {
                if (move.isCollidedHorizontal()) {
                    move.setIgnoreHorizontalCollision();
                }

                if (move.motion().y > 0.0 || mc.player.isSneaking()) {
                    move.setIgnoreVerticalCollision();
                }

                move.motion().y = Math.min(move.motion().y, 99999.0);
            }

            if (this.actions.is("AirZoneRW") && mc.gameSettings.keyBindSneak.isKeyDown()) {
                mc.playerController.onPlayerDamageBlock(new BlockPos(mc.player.getPosX(), mc.player.getPosY() - 1.0, mc.player.getPosZ()), mc.player.getHorizontalFacing());
                mc.player.swingArm(Hand.MAIN_HAND);
            }

            if (this.actions.is("Matrix") && mc.world.getBlockState(new BlockPos(mc.player.getPosX(), mc.player.getPosY() + 1.0, mc.player.getPosZ())).getBlock() != Blocks.AIR && mc.world.getBlockState(new BlockPos(mc.player.getPosX(), mc.player.getPosY(), mc.player.getPosZ())).getBlock() != Blocks.WATER && mc.world.getBlockState(new BlockPos(mc.player.getPosX(), mc.player.getPosY(), mc.player.getPosZ())).getBlock() != Blocks.LAVA) {
                mc.player.abilities.isFlying = false;
                if (mc.player.isOnGround()) {
                    mc.player.motion.y = 0.2;
                }

                if (!mc.player.isOnGround()) {
                    mc.player.motion.y = -1.0E-5;
                }

                ClientPlayerEntity var10000 = mc.player;
                var10000.rotationYaw = (float)((double)var10000.rotationYaw + 0.1);
                var10000 = mc.player;
                var10000.rotationYaw = (float)((double)var10000.rotationYaw - 0.11);
            }
        }
    }

    public boolean collisionPredict(Vector3d to) {
        boolean prevCollision = mc.world.getCollisionShapes(mc.player, mc.player.getBoundingBox().shrink(0.0625)).toList().isEmpty();
        Vector3d backUp = new Vector3d(mc.player.getPosX(), mc.player.getPosY(), mc.player.getPosZ());
        mc.player.setPosition(to.x, to.y, to.z);
        boolean collision = mc.world.getCollisionShapes(mc.player, mc.player.getBoundingBox().shrink(0.0625)).toList().isEmpty() && prevCollision;
        mc.player.setPosition(backUp.x, backUp.y, backUp.z);
        return collision;
    }

    public void onDisable() {
        mc.player.abilities.isFlying = false;
    }
}