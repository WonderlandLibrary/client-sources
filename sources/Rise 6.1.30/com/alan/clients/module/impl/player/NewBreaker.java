package com.alan.clients.module.impl.player;

import com.alan.clients.Client;
import com.alan.clients.component.impl.player.RotationComponent;
import com.alan.clients.component.impl.player.rotationcomponent.MovementFix;
import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.event.impl.other.BlockDamageEvent;
import com.alan.clients.event.impl.other.TeleportEvent;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.event.impl.render.MouseOverEvent;
import com.alan.clients.event.impl.render.Render3DEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.util.RayCastUtil;
import com.alan.clients.util.animation.Animation;
import com.alan.clients.util.animation.Easing;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.util.render.RenderUtil;
import com.alan.clients.util.rotation.RotationUtil;
import com.alan.clients.util.vector.Vector2f;
import com.alan.clients.util.vector.Vector3d;
import com.alan.clients.util.vector.Vector3i;
import com.alan.clients.value.impl.BooleanValue;
import com.alan.clients.value.impl.ListValue;
import com.alan.clients.value.impl.ModeValue;
import com.alan.clients.value.impl.SubMode;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.*;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

import static com.alan.clients.layer.Layers.BLOOM;

@ModuleInfo(aliases = {"module.player.breaker.name"}, description = "module.player.breaker.description", category = Category.PLAYER)
public class NewBreaker extends Module {
    public final ModeValue mode = new ModeValue("Mode", this).add(new SubMode("Normal")).add(new SubMode("Instant")).setDefault("Normal");

    public final BooleanValue bed = new BooleanValue("Bed", this, true);
    public final BooleanValue keep = new BooleanValue("Keep Break Progress When Out Of Range", this, true);
    public final BooleanValue velocity = new BooleanValue("Cancel velocity whilst breaking, so you don't slow down in air", this, true);

    public final BooleanValue throughWalls = new BooleanValue("Through Walls", this, true);
    private final BooleanValue emptySurrounding = new BooleanValue("Empty Surrounding", this, false, () -> !throughWalls.getValue());

    public final BooleanValue rotations = new BooleanValue("Rotate", this, true);
    public final BooleanValue importantRotationsOnly = new BooleanValue("Only Rotate at Start and Stop", this, true);
    public final BooleanValue whiteListOwnBed = new BooleanValue("Whitelist Own Bed", this, true);
    public final BooleanValue slowDownInAir = new BooleanValue("Slow Down In Air", this, true);
    private final ListValue<MovementFix> movementCorrection = new ListValue<>("Movement Correction", this);
    private Vector3d block, lastBlock, home;
    private int delay;
    private boolean down;
    private float damage;
    private Animation damageAnimation = new Animation(Easing.LINEAR, 50);

    public NewBreaker() {
        for (MovementFix movementFix : MovementFix.values()) {
            movementCorrection.add(movementFix);
        }

        movementCorrection.setDefault(MovementFix.OFF);
    }

    @Override
    public void onEnable() {
        block = null;
        damage = 0;
        delay = 0;
        down = false;
        lastBlock = null;
    }

    @Override
    public void onDisable() {
        block = null;

        if (down) {
            mc.gameSettings.keyBindAttack.setPressed(false);
            down = false;
        }
    }

    @EventLink()
    public final Listener<Render3DEvent> onRender3D = event -> {
        if (block == null) return;

        Vector3i pos = new Vector3i((int) Math.floor(block.getX()), (int) Math.floor(block.getY()), (int) Math.floor(block.getZ()));
        damageAnimation.run(damage);

        getLayer(BLOOM).add(() -> {
            GlStateManager.pushMatrix();
            GlStateManager.pushAttrib();
            GlStateManager.enableBlend();
            GlStateManager.disableTexture2D();
            GlStateManager.disableLighting();
            GL11.glDepthMask(false);

            RenderUtil.color(getTheme().getFirstColor());
            RenderUtil.drawBoundingBox(new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1 * damageAnimation.getValue(), pos.getZ() + 1));

            GlStateManager.enableTexture2D();
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GL11.glDepthMask(true);
            GlStateManager.popAttrib();
            GlStateManager.popMatrix();
            GlStateManager.resetColor();
        });
    };

    @EventLink(value = Priorities.VERY_HIGH)
    public final Listener<PacketReceiveEvent> onPacketReceive = event -> {
        if (!velocity.getValue() || block == null) return;

        final Packet<?> p = event.getPacket();

        if (p instanceof S12PacketEntityVelocity) {
            final S12PacketEntityVelocity wrapper = (S12PacketEntityVelocity) p;

            if (wrapper.getEntityID() == mc.thePlayer.getEntityId()) {
                event.setCancelled();
                wrapper.motionY = 0;
                wrapper.motionX = 0;
                wrapper.motionZ = 0;
            }
        }
    };

    @EventLink(value = Priorities.VERY_HIGH)
    public final Listener<BlockDamageEvent> onBlockDamage = event -> {
        damage = mc.playerController.curBlockDamageMP;
        ChatUtil.display("Updated Damage");
        ChatUtil.display(event.getBlockPos());
        ChatUtil.display(this.block.getX() + ", " + this.block.getY() + ", " + this.block.getZ());
    };

    @EventLink(value = Priorities.VERY_HIGH)
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        delay--;
        if (delay > 0) return;

        if (mc.playerController.curBlockDamageMP == 0 || mc.playerController.curBlockDamageMP >= 1) {
            damage = 0;
        }

        if (block == null || mc.thePlayer.getDistance(block.getX(), block.getY(), block.getZ()) > 4 || PlayerUtil.block(block.getX(), block.getY(), block.getZ()) instanceof BlockAir) {
            this.updateBlock();

            if (down) {
                mc.gameSettings.keyBindAttack.setPressed(false);
                down = false;
            }

            if (block == null) return;
        }

        this.destroy();
    };

    public void updateBlock() {
        if (!(this.block == null || PlayerUtil.block(this.block.x, this.block.y, this.block.z) instanceof BlockAir || mc.thePlayer.getDistance(this.block.x, this.block.y - mc.thePlayer.getEyeHeight(), this.block.z) > 4.5)) {
            return;
        }
        if (this.lastBlock != null && !keep.getValue()) {
            mc.playerController.curBlockDamageMP = 0;
        }

        lastBlock = block;
        block = this.block();
    }

    public void rotate() {
        BlockPos blockPos = new BlockPos(Math.floor(block.getX()), Math.floor(block.getY()), Math.floor(block.getZ()));
        float blockHardness = PlayerUtil.block(blockPos).getPlayerRelativeBlockHardness(mc.thePlayer, mc.theWorld, blockPos);

        if (importantRotationsOnly.getValue() && !(mc.playerController.curBlockDamageMP == 0 || mc.playerController.curBlockDamageMP >= 1 - blockHardness - 0.001)) {
            return;
        }

        if (!this.rotations.getValue()) return;
        RotationComponent.setRotations(getRotations(), 10, movementCorrection.getValue());
    }

    public Vector2f getRotations() {
        return RotationUtil.calculate(new Vector3d(Math.floor(block.getX()) + 0.5 + (Math.random() - 0.5) / 4, Math.floor(block.getY()) + 0.1, Math.floor(block.getZ()) + 0.5 + (Math.random() - 0.5) / 4));
    }

    public Vector3d block() {
        if (home != null && mc.thePlayer.getDistanceSq(home.getX(), home.getY(), home.getZ()) < 35 * 35 && whiteListOwnBed.getValue()) {
            return null;
        }

        int beds = 0;

        for (int x = -5; x <= 5; x++) {
            for (int y = -5; y <= 5; y++) {
                for (int z = -5; z <= 5; z++) {

                    final Block block = PlayerUtil.blockRelativeToPlayer(x, y, z);
                    final Vector3d position = new Vector3d(mc.thePlayer.posX + x, mc.thePlayer.posY + y, mc.thePlayer.posZ + z);

                    if (!(block instanceof BlockBed)) {
                        continue;
                    }

                    beds++;

                    if (beds <= 1) continue;

                    /* Grab moving object position */
                    final MovingObjectPosition movingObjectPosition = RayCastUtil.rayCast(RotationUtil.calculate(position), 4.5f);
                    if (movingObjectPosition == null || movingObjectPosition.hitVec.distanceTo(new Vec3(mc.thePlayer.posX, mc.thePlayer.posY - mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ)) > 4.5) {
                        continue;
                    }

                    if (!throughWalls.getValue()) {
                        final BlockPos blockPos = movingObjectPosition.getBlockPos();
                        if (!blockPos.equalsVector(position)) {
                            continue;
                        }
                    } else if (emptySurrounding.getValue()) {
                        Vector3d addVec = position;
                        double hardness = Double.MAX_VALUE;
                        boolean empty = false;

                        for (int addX = -4; addX <= 4; addX++) {
                            for (int addY = 0; addY <= 1; addY++) {
                                for (int addZ = -4; addZ <= 4; addZ++) {
                                    Block possibleBlock = PlayerUtil.block(position.getX() + addX, position.getY() + addY, position.getZ() + addZ);

                                    if (possibleBlock instanceof BlockBed) {
                                        continue;
                                    }

                                    if (empty || (mc.thePlayer.getDistance(position.getX() + addX, position.getY() + addY, position.getZ()) + addZ) > 4.5)
                                        continue;

                                    if (getNeighbours(position.add(addX, addY, addZ)).stream().noneMatch(neighbour -> neighbour instanceof BlockBed)) {
                                        continue;
                                    }

                                    if (possibleBlock instanceof BlockAir || possibleBlock instanceof BlockLiquid) {
                                        empty = true;
                                        continue;
                                    }

                                    if (mc.thePlayer.getDistance(position.getX() + addX, position.getY() + addY - mc.thePlayer.getEyeHeight(), position.getZ() + addZ) > 4.5) {
                                        continue;
                                    }

                                    double possibleHardness = possibleBlock.getBlockHardness();

                                    if (possibleHardness < hardness) {
                                        hardness = possibleHardness;
                                        addVec = position.add(addX, addY, addZ);
                                    }
                                }
                            }
                        }

                        if (!empty) {
                            if (addVec.equals(position)) {
                                return null;
                            } else {
                                return addVec;
                            }
                        }
                    }

                    return position;
                }
            }
        }

        return null;
    }

    public List<Block> getNeighbours(Vector3d blockPos) {
        List<Block> neighbours = new ArrayList<>();
        for (EnumFacing enumFacing : EnumFacing.values()) {
            if (enumFacing == EnumFacing.UP) continue;
            Vector3d neighbourPos = blockPos.add(new Vector3d(enumFacing.getDirectionVec().getX(), enumFacing.getDirectionVec().getY(), enumFacing.getDirectionVec().getZ()));
            neighbours.add(PlayerUtil.block(neighbourPos));
        }
        return neighbours;
    }

    public void updateHardnessAndCallEvent(BlockPos blockPos) {
        final BlockDamageEvent bdEvent = new BlockDamageEvent(this.mc.thePlayer, this.mc.thePlayer.worldObj, blockPos);
        Client.INSTANCE.getEventBus().handle(bdEvent);
    }

    public void destroy() {
        boolean slowDownInAir = this.slowDownInAir.getValue();
        boolean ground = mc.thePlayer.onGround;
        if (!slowDownInAir) mc.thePlayer.onGround = true;
        BlockPos blockPos = new BlockPos(block.getX(), block.getY(), block.getZ());

        mc.objectMouseOver = rayCast();
        mc.playerController.curBlockDamageMP = damage;

        switch (mode.getValue().getName()) {
            case "Instant":
                this.rotate();
                this.updateHardnessAndCallEvent(blockPos);

                mc.thePlayer.swingItem();
                PacketUtil.send(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.UP));
                mc.thePlayer.swingItem();
                PacketUtil.send(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.UP));
                block = null;
                delay = 20;

                mc.playerController.onPlayerDestroyBlock(blockPos, EnumFacing.DOWN);
                break;

            case "Normal":
                this.updateHardnessAndCallEvent(blockPos);

                this.rotate();

                mc.gameSettings.keyBindAttack.setPressed(true);
                down = true;
                break;
        }

        mc.thePlayer.onGround = ground;
    }

    @EventLink
    public final Listener<MouseOverEvent> onMouseOver = event -> {
        if (block == null) return;

        MovingObjectPosition movingObjectPosition = rayCast();

        if (!isTarget(movingObjectPosition)) {
            ChatUtil.display("Not Target");
            return;
        }

        event.setMovingObjectPosition(movingObjectPosition);
    };

    private MovingObjectPosition rayCast() {
        MovingObjectPosition movingObjectPosition = rayCast(RotationComponent.rotations);

        if (!isTarget(movingObjectPosition)) {
            movingObjectPosition = rayCast(getRotations());
        }

        if (!isTarget(movingObjectPosition)) {
            movingObjectPosition = new MovingObjectPosition(new Vec3(block.getX() + Math.random(), block.getY() + 1, block.getZ() + Math.random()), EnumFacing.UP, new BlockPos(block.getX(), block.getY(), block.getZ()));
        }

        return movingObjectPosition;
    }

    @SuppressWarnings("All")
    private boolean isTarget(MovingObjectPosition movingObjectPosition) {
        return !(movingObjectPosition == null || movingObjectPosition.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK || !movingObjectPosition.getBlockPos().equalsVector(new Vector3d(block.getX(), block.getY(), block.getZ())));
    }

    private MovingObjectPosition rayCast(Vector2f rotations) {
        Block block = PlayerUtil.block(this.block);
        AxisAlignedBB axisAlignedBB = block.getCollisionBoundingBox(mc.theWorld, new BlockPos(this.block.getX(), this.block.getY(), this.block.getZ()), block.getDefaultState());
        final Vec3 eyes = mc.thePlayer.getPositionEyes(1);
        final Vec3 vec31 = mc.thePlayer.getVectorForRotation(rotations.getY(), rotations.getX());
        final double range = 4.5;
        final Vec3 vec32 = eyes.addVector(vec31.xCoord * range, vec31.yCoord * range, vec31.zCoord * range);
        MovingObjectPosition movingObjectPosition = axisAlignedBB.calculateIntercept(eyes, vec32);
        if (movingObjectPosition != null) {
            movingObjectPosition.setBlockPos(new BlockPos(this.block.getX(), this.block.getY(), this.block.getZ()));
//            movingObjectPosition.setBlockPos(new BlockPos(Math.floor(movingObjectPosition.hitVec.xCoord + movingObjectPosition.sideHit.getFrontOffsetZ() * 0.00001), Math.floor(movingObjectPosition.hitVec.yCoord + movingObjectPosition.sideHit.getFrontOffsetY() * 0.00001), Math.floor(movingObjectPosition.hitVec.zCoord + movingObjectPosition.sideHit.getFrontOffsetZ() * 0.00001)));
        }

        return movingObjectPosition;
    }

    @EventLink
    public final Listener<TeleportEvent> onTeleport = event -> {
        final double distance = mc.thePlayer.getDistance(event.getPosX(), event.getPosY(), event.getPosZ());

        if (distance > 40) {
            home = new Vector3d(event.getPosX(), event.getPosY(), event.getPosZ());
        }
    };
}