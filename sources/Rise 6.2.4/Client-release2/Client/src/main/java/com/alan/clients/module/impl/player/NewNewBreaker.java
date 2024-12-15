package com.alan.clients.module.impl.player;

import com.alan.clients.component.impl.player.RotationComponent;
import com.alan.clients.component.impl.player.Slot;
import com.alan.clients.component.impl.player.rotationcomponent.MovementFix;
import com.alan.clients.component.impl.render.NotificationComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.event.impl.other.AttackEvent;
import com.alan.clients.event.impl.other.TeleportEvent;
import com.alan.clients.event.impl.other.WorldChangeEvent;
import com.alan.clients.event.impl.render.Render2DEvent;
import com.alan.clients.event.impl.render.Render3DEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.util.player.SlotUtil;
import com.alan.clients.util.render.RenderUtil;
import com.alan.clients.util.rotation.RotationUtil;
import com.alan.clients.util.vector.Vector2f;
import com.alan.clients.value.impl.BooleanValue;
import com.alan.clients.value.impl.ModeValue;
import com.alan.clients.value.impl.NumberValue;
import com.alan.clients.value.impl.SubMode;
import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockBed;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;

@ModuleInfo(aliases = {"module.player.breaker.name"}, description = "module.player.breaker.description", category = Category.PLAYER)
public class NewNewBreaker extends Module {
    public final ModeValue mode = new ModeValue("Mode", this)
            .add(new SubMode("Through Walls"))
            .add(new SubMode("Surroundings"))
            .setDefault("Through Walls");
    private final NumberValue Range = new NumberValue("Range", this, 4, 1, 5, 0.1);

    public final BooleanValue rotate = new BooleanValue("Rotate", this, true);
    public final BooleanValue movementCorrection = new BooleanValue("Movement Correction", this, false);
    public final BooleanValue whitelistFriendlyBed = new BooleanValue("Whitelist Friendly Bed", this, false);

    public final BooleanValue hitCheck = new BooleanValue("Aura Check", this, false);
    private final NumberValue fastBreakNormal = new NumberValue("FastBreak", this, 0, 0, 1, 0.1);
    private final NumberValue fastBreakBed = new NumberValue("FastBreak bed", this, 0, 0, 1, 0.1);
    private final NumberValue airMultipalyer = new NumberValue("Air Multiplier", this, 1, 0, 3, 0.1);

    private float hardness;
    private float damagetoblock;

    private int attackTicks;
    private boolean notify = true;
    private int usedItem, delay, rotateTime;
    private Vec3 teleport;

    private BlockPos coordsBed;
    private BlockPos blockToBreak;

    @Getter
    boolean breaking = false;
    @Override
    public void onDisable() {
        mc.playerController.curBlockDamageMP = 0;
    }
    @Override
    public void onEnable() {
        hardness = 0;
        damagetoblock = 0;
    }
    @EventLink(value = Priorities.VERY_HIGH)
    public final Listener<WorldChangeEvent> onWorldChange = event -> {
        notify = true;
    };
    @EventLink(value = Priorities.VERY_HIGH)
    public final Listener<TeleportEvent> onTeleport = event -> {
        if (mc.thePlayer.getDistance(event.getPosX(), event.getPosY(), event.getPosZ()) > 30) {
            if (notify) {
                notify = false;
                NotificationComponent.post("Breaker","Whitelisted bed");
            }
            teleport = new Vec3(event.getPosX(), event.getPosY(), event.getPosZ());
        }
    };

    @EventLink(value = Priorities.VERY_HIGH)
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        Module scaffold = getModule(Scaffold.class);
        if (scaffold == null || scaffold.isEnabled()) {
            return;
        }
        EntityPlayerSP player = mc.thePlayer;
        attackTicks++;
        delay--;
        if (delay > 0 || (whitelistFriendlyBed.getValue() && teleport != null && player.getDistanceSq(teleport.xCoord, teleport.yCoord, teleport.zCoord) < 1500)) {
            return;
        }

        boolean foundbed = false;
        int bedX = 0;
        int bedY = 0;
        int bedZ = 0;
        for (int x = -Range.getValue().intValue()+1; x <= Range.getValue().intValue()+1; x++) {
            for (int y = -Range.getValue().intValue()+1; y <= Range.getValue().intValue()+1; y++) {
                for (int z = -Range.getValue().intValue()+1; z <= Range.getValue().intValue()+1; z++) {
                    Block block = PlayerUtil.blockRelativeToPlayer(x, y, z);
                    if (block instanceof BlockBed) {
                        foundbed = true;
                        bedX = x;
                        bedY = y;
                        bedZ = z;
                        BlockPos blockPos = new BlockPos(player.posX + x, player.posY + y, player.posZ + z);
                        if (damagetoblock <= 0) {
                            if (coordsBed != null) {
                                if (blockPos.distanceSq(mc.thePlayer.getPosition()) < coordsBed.distanceSq(mc.thePlayer.getPosition())) {
                                    coordsBed = blockPos;
                                }
                            } else {
                                coordsBed = blockPos;
                            }
                        }
                    }
                }
            }
        }
        ArrayList<BlockPos> nearblocks = new ArrayList<>();

        if (foundbed) {
            breaking = true;
            int airblocks = 0;
            nearblocks.add(new BlockPos(bedX + 1, bedY, bedZ));
            nearblocks.add(new BlockPos(bedX - 1, bedY, bedZ));
            nearblocks.add(new BlockPos(bedX, bedY, bedZ + 1));
            nearblocks.add(new BlockPos(bedX, bedY, bedZ - 1));
            nearblocks.add(new BlockPos(bedX, bedY + 1, bedZ));

            for (int i = 0; i < nearblocks.size(); i++) {
                BlockPos blockPos = nearblocks.get(i);
                Block block = PlayerUtil.blockRelativeToPlayer(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                if (block instanceof BlockBed) {
                    nearblocks.remove(i);
                    nearblocks.add(new BlockPos(blockPos.getX() + 1, blockPos.getY(), blockPos.getZ()));
                    nearblocks.add(new BlockPos(blockPos.getX() - 1, blockPos.getY(), blockPos.getZ()));
                    nearblocks.add(new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ() + 1));
                    nearblocks.add(new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ() - 1));
                    nearblocks.add(new BlockPos(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ()));
                    break;
                }
            }
            for (int i = 0; i < nearblocks.size(); i++) {
                BlockPos blockPos = nearblocks.get(i);
                Block block = PlayerUtil.blockRelativeToPlayer(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                if (block instanceof BlockAir) {
                    airblocks++;
                }
            }
            if (airblocks > 0 || !this.mode.getValue().getName().equals("Surroundings")) {
                blockToBreak = coordsBed;
            } else {
                float minHardness = 99999999;

                for (BlockPos blockPos : nearblocks) {
                    Block block = PlayerUtil.blockRelativeToPlayer(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                    if (block.getBlockHardness() < minHardness && !(block instanceof BlockBed)) {
                        minHardness = block.getBlockHardness();
                        if (damagetoblock <= 0) {
                            blockToBreak = new BlockPos(player.posX + blockPos.getX(), player.posY + blockPos.getY(), player.posZ + blockPos.getZ());
                        }
                    }
                }
                for (int i = 0; i < nearblocks.size(); i++) {
                    BlockPos blockPos = nearblocks.get(i);
                    Block block = PlayerUtil.blockRelativeToPlayer(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                    if (minHardness == block.getBlockHardness() && blockPos.add(player.getPosition()).distance(player.getPosition()) < blockToBreak.distance(player.getPosition())) {
                        if (damagetoblock <= 0) {
                            blockToBreak = new BlockPos(player.posX + blockPos.getX(), player.posY + blockPos.getY(), player.posZ + blockPos.getZ());
                        }
                    }
                }
            }
            if (blockToBreak.distance(player.getPosition()) <= Range.getValue().floatValue()) {
                if (rotate.getValue()) {
                    rotate(blockToBreak);
                }
//                player.swingItem();
                int slot = SlotUtil.findTool(blockToBreak);
//                final BlockDamageEvent bdEvent = new BlockDamageEvent(this.mc.thePlayer, this.mc.thePlayer.worldObj, blockToBreak);
//                Client.INSTANCE.getEventBus().handle(bdEvent);
                if (slot != -1) PacketUtil.send(new C09PacketHeldItemChange(slot));
                if (slot != -1) hardness = SlotUtil.getPlayerRelativeBlockHardness(player, mc.theWorld, blockToBreak, slot);
                else hardness = SlotUtil.getPlayerRelativeBlockHardness(player, mc.theWorld, blockToBreak, getComponent(Slot.class).getItemIndex());
                if (!mc.thePlayer.onGround) hardness *= airMultipalyer.getValue().floatValue();

                if (damagetoblock == 0) {
                    PacketUtil.send(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockToBreak, EnumFacing.DOWN));
                }
                mc.thePlayer.swingItem();
                damagetoblock += hardness;
                mc.theWorld.sendBlockBreakProgress(player.getEntityId(), blockToBreak, (int) (damagetoblock * 10 - 1));
                if (damagetoblock >= (PlayerUtil.blockRelativeToPlayer(blockToBreak.getX(), blockToBreak.getY(), blockToBreak.getZ()) instanceof BlockBed ? 1-fastBreakBed.getValue().floatValue() : 1-fastBreakNormal.getValue().floatValue())) {
                    damagetoblock = 0;

                    PacketUtil.send(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockToBreak, EnumFacing.DOWN));
                    mc.playerController.onPlayerDestroyBlock(blockToBreak, EnumFacing.UP);
                }
                if (slot != -1) PacketUtil.send(new C09PacketHeldItemChange(getComponent(Slot.class).getItemIndex()));

//                final BlockDamageEvent bdEvent = new BlockDamageEvent(this.mc.thePlayer, this.mc.thePlayer.worldObj, blockToBreak);
//                Client.INSTANCE.getEventBus().handle(bdEvent);
//                float xd = SlotUtil.getPlayerRelativeBlockHardness(mc.thePlayer, mc.theWorld, blockToBreak, getComponent(SlotComponent.class).getItemIndex());
//                usedItem = getComponent(SlotComponent.class).getItemIndex();
//                if (!mc.thePlayer.onGround) xd *= airMultipalyer.getValue().floatValue();
//                mc.playerController.curBlockDamageMP += xd;
//                mc.theWorld.sendBlockBreakProgress(mc.thePlayer.getEntityId(), blockToBreak, (int) (mc.playerController.curBlockDamageMP * 10 - 1));
//
//                if (mc.playerController.curBlockDamageMP >= 1) {
//                    mc.thePlayer.swingItem();
//                    PacketUtil.send(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockToBreak, EnumFacing.DOWN));
//                    mc.playerController.onPlayerDestroyBlock(blockToBreak, EnumFacing.DOWN);
//                    mc.playerController.curBlockDamageMP = 0;
//                }
            }
        } else {
            if (breaking) {
                damagetoblock = 0;
            }
            breaking = false;
        }

        mc.playerController.curBlockDamageMP = damagetoblock;
    };

    @EventLink
    public final Listener<AttackEvent> onAttackEvent = event -> {
        if(whitelistFriendlyBed.getValue() ){
            attackTicks = 0;
        } else if (attackTicks < 10) {
            attackTicks++;
        }

        if (attackTicks < 10) {
            breaking = true;
        } else {
            breaking = false;
        }

    };

    @EventLink
    public final Listener<Render2DEvent> onRender2D = event -> {
        if (breaking) {
            final ScaledResolution scaledResolution = event.getScaledResolution();
            final double y = scaledResolution.getScaledHeight() * 0.80;

            RenderUtil.drawRoundedGradientRect((scaledResolution.getScaledWidth() - (mc.playerController.curBlockDamageMP * 100)) / 2, y, mc.playerController.curBlockDamageMP * 100, 10, 4,
                    getTheme().getFirstColor(), getTheme().getSecondColor(), true);
            RenderUtil.color(Color.WHITE);
        }
    };

    @EventLink
    public final Listener<Render3DEvent> onRender3D = event -> {
        if (breaking) {
            try {
                double x1 = blockToBreak.getX() - mc.getRenderManager().renderPosX;
                double y1 = blockToBreak.getY() - mc.getRenderManager().renderPosY;
                double z1 = blockToBreak.getZ() - mc.getRenderManager().renderPosZ;

                double x2 = x1 + 1.0;
                double y2 = y1 + 1.0;
                double z2 = z1 + 1.0;

                renderBoundingBox(x1, y1, z1, x2, y2, z2);

            } catch (NullPointerException e) {

            }
        }
    };
    private void renderBoundingBox(double x1, double y1, double z1, double x2, double y2, double z2) {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glLineWidth(2.0f);
        GL11.glBegin(GL11.GL_LINES);
        if (coordsBed == blockToBreak) {
            GL11.glColor3f(255, 0, 0);
        } else {
            GL11.glColor3f(255, 255, 255);
        }

        GL11.glVertex3d(x1, y1, z1);
        GL11.glVertex3d(x2, y1, z1);

        GL11.glVertex3d(x2, y1, z1);
        GL11.glVertex3d(x2, y2, z1);

        GL11.glVertex3d(x2, y2, z1);
        GL11.glVertex3d(x1, y2, z1);

        GL11.glVertex3d(x1, y2, z1);
        GL11.glVertex3d(x1, y1, z1);

        GL11.glVertex3d(x1, y1, z1);
        GL11.glVertex3d(x1, y1, z2);

        GL11.glVertex3d(x2, y1, z1);
        GL11.glVertex3d(x2, y1, z2);

        GL11.glVertex3d(x2, y2, z1);
        GL11.glVertex3d(x2, y2, z2);

        GL11.glVertex3d(x1, y2, z1);
        GL11.glVertex3d(x1, y2, z2);

        GL11.glVertex3d(x1, y1, z2);
        GL11.glVertex3d(x2, y1, z2);

        GL11.glVertex3d(x2, y1, z2);
        GL11.glVertex3d(x2, y2, z2);

        GL11.glVertex3d(x2, y2, z2);
        GL11.glVertex3d(x1, y2, z2);

        GL11.glVertex3d(x1, y2, z2);
        GL11.glVertex3d(x1, y1, z2);

        GL11.glEnd();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glPopMatrix();
    }
    public void rotate(BlockPos block) {
        if (!this.rotate.getValue()) return;
        final Vector2f rotations = RotationUtil.calculate(block);
        RotationComponent.setRotations(rotations, 10, movementCorrection.getValue() ? MovementFix.NORMAL : MovementFix.OFF);
        mc.objectMouseOver.setBlockPos(block);
        mc.objectMouseOver.sideHit = EnumFacing.UP;
        mc.objectMouseOver.hitVec = new Vec3(Math.random(), 1, Math.random());
        mc.objectMouseOver.typeOfHit = MovingObjectPosition.MovingObjectType.BLOCK;
    }
}

