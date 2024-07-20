/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.util.Objects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.Event3D;
import ru.govno.client.event.events.EventMove2;
import ru.govno.client.event.events.EventPlayerMotionUpdate;
import ru.govno.client.event.events.EventReceivePacket;
import ru.govno.client.event.events.EventSendPacket;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.ElytraBoost;
import ru.govno.client.module.modules.GameSyncTPS;
import ru.govno.client.module.modules.PlayerHelper;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Command.impl.Clip;
import ru.govno.client.utils.InventoryUtil;
import ru.govno.client.utils.Math.BlockUtils;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Movement.MoveMeHelp;
import ru.govno.client.utils.Render.AnimationUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;
import ru.govno.client.utils.TimerUtils;

public class NoClip
extends Module {
    public static NoClip get;
    public Settings Mode;
    public Settings DoorPhase;
    public Settings MatrixLift;
    public Settings MatrixTP;
    public Settings TPRange;
    public Settings SpeedY;
    public Settings SpeedF;
    public Settings TimerBoost;
    public Settings Boost;
    public Settings YMoveBrains;
    boolean flag = false;
    float colorEx = 0.0f;
    TimerUtils timer = new TimerUtils();
    boolean tickGo = false;
    float ticker = 0.0f;
    AnimationUtils animSHKALE = new AnimationUtils(0.0f, 0.0f, 0.08f);

    public NoClip() {
        super("NoClip", 0, Module.Category.MOVEMENT);
        get = this;
        this.Mode = new Settings("Mode", "Vanilla", (Module)this, new String[]{"Vanilla", "Packet", "Packet2", "Matrix", "Sunrise", "Reallyworld"});
        this.settings.add(this.Mode);
        this.DoorPhase = new Settings("DoorPhase", true, (Module)this, () -> this.Mode.currentMode.equalsIgnoreCase("Vanilla") || this.Mode.currentMode.equalsIgnoreCase("Matrix"));
        this.settings.add(this.DoorPhase);
        this.MatrixLift = new Settings("MatrixLift", false, (Module)this, () -> this.Mode.currentMode.equalsIgnoreCase("Vanilla") || this.Mode.currentMode.equalsIgnoreCase("Matrix"));
        this.settings.add(this.MatrixLift);
        this.MatrixTP = new Settings("MatrixTP", true, (Module)this, () -> this.Mode.currentMode.equalsIgnoreCase("Vanilla") || this.Mode.currentMode.equalsIgnoreCase("Matrix") || this.Mode.currentMode.equalsIgnoreCase("Sunrise"));
        this.settings.add(this.MatrixTP);
        this.TPRange = new Settings("TPRange", 100.0f, 300.0f, 0.0f, this, () -> (this.Mode.currentMode.equalsIgnoreCase("Vanilla") || this.Mode.currentMode.equalsIgnoreCase("Matrix") || this.Mode.currentMode.equalsIgnoreCase("Sunrise")) && this.MatrixTP.bValue);
        this.settings.add(this.TPRange);
        this.SpeedY = new Settings("SpeedY", 0.2f, 1.0f, 0.0f, this, () -> this.Mode.currentMode.equalsIgnoreCase("Vanilla") || this.Mode.currentMode.equalsIgnoreCase("Matrix"));
        this.settings.add(this.SpeedY);
        this.SpeedF = new Settings("Speed", 1.0f, 1.0f, 0.01f, this, () -> this.Mode.currentMode.equalsIgnoreCase("Vanilla") || this.Mode.currentMode.equalsIgnoreCase("Matrix"));
        this.settings.add(this.SpeedF);
        this.TimerBoost = new Settings("TimerBoost", true, (Module)this, () -> !this.Mode.currentMode.equalsIgnoreCase("Reallyworld") && !this.Mode.currentMode.equalsIgnoreCase("Packet") && !this.Mode.currentMode.equalsIgnoreCase("Packet2"));
        this.settings.add(this.TimerBoost);
        this.Boost = new Settings("Boost", 0.4f, 1.0f, 0.0f, this, () -> !this.Mode.currentMode.equalsIgnoreCase("Reallyworld") && !this.Mode.currentMode.equalsIgnoreCase("Packet") && !this.Mode.currentMode.equalsIgnoreCase("Packet2") && this.TimerBoost.bValue);
        this.settings.add(this.Boost);
        this.YMoveBrains = new Settings("YMoveBrains", true, (Module)this, () -> this.Mode.currentMode.equalsIgnoreCase("Sunrise"));
        this.settings.add(this.YMoveBrains);
    }

    /*
     * Unable to fully structure code
     */
    @EventTarget
    public void onMove(EventMove2 move) {
        block8: {
            if (this.actived && this.Mode.currentMode.equalsIgnoreCase("Reallyworld") && this.tickGo) {
                if (Minecraft.player.isCollidedHorizontally) {
                    move.setIgnoreHorizontalCollision();
                }
                if (Minecraft.player.isSneaking()) {
                    move.setIgnoreVerticalCollision();
                }
            }
            if (this.MatrixLift.bValue && NoClip.isNoClip(Minecraft.player) && !this.Mode.currentMode.equalsIgnoreCase("Sunrise") && !this.Mode.currentMode.equalsIgnoreCase("Packet") && !this.Mode.currentMode.equalsIgnoreCase("Packet2") && !Minecraft.player.isInWater() && NoClip.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - 0.5, Minecraft.player.posZ)).getBlock() != Blocks.WATER && Minecraft.player.isJumping()) {
                return;
            }
            if (!this.actived || !this.Mode.currentMode.equalsIgnoreCase("Sunrise") && !this.Mode.currentMode.equalsIgnoreCase("Matrix") && !this.Mode.currentMode.equalsIgnoreCase("Vanilla")) break block8;
            x = Minecraft.player.posX;
            y = Minecraft.player.posY;
            z = Minecraft.player.posZ;
            isInBlock = false;
            w = Minecraft.player.width / 2.0f - 0.05f;
            i = 0.0f;
            while (true) {
                block9: {
                    block11: {
                        block10: {
                            v0 = i;
                            v1 = Minecraft.player.isSneaking() != false ? 1.6 : 1.8;
                            if (!(v0 < v1)) break;
                            if (!NoClip.posBlock(x, y + (double)i, z) && !NoClip.posBlock(x + (double)w, y + (double)i, z + (double)w) && !NoClip.posBlock(x - (double)w, y + (double)i, z - (double)w) && !NoClip.posBlock(x + (double)w, y + (double)i, z) && !NoClip.posBlock(x - (double)w, y + (double)i, z) && !NoClip.posBlock(x, y + (double)i, z + (double)w) && !NoClip.posBlock(x, y + (double)i, z - (double)w) && !NoClip.posBlock(x + (double)w, y + (double)i, z - (double)w) && !NoClip.posBlock(x - (double)w, y + (double)i, z + (double)w)) break block9;
                            smartYHops = this.YMoveBrains.bValue;
                            stabbleCheckFlot = move.toGround() != false && (double)Minecraft.player.fallDistance < 0.3 && Minecraft.player.isJumping() != false || MathUtils.getDifferenceOf(Minecraft.player.motionY, 0.42) < 0.01;
                            stabbleCheckUpWard = move.toGround() == false;
                            stabbleCheckDownWard = move.toGround() == false || (double)Minecraft.player.fallDistance < 1.5 && Minecraft.player.isJumping() != false;
                            rotY = Minecraft.player.rotationPitch;
                            if (!(rotY > 65.0f)) break block10;
                            if (!stabbleCheckDownWard) break block11;
                            ** GOTO lbl-1000
                        }
                        if (!(rotY < 5.0f) && MoveMeHelp.moveKeysPressed() != false ? stabbleCheckFlot != false : stabbleCheckUpWard != false) ** GOTO lbl-1000
                    }
                    if (Minecraft.player.isSneaking() && (double)Minecraft.player.fallDistance < 3.5 - Math.abs(Entity.Getmotiony)) lbl-1000:
                    // 3 sources

                    {
                        v2 = true;
                    } else {
                        v2 = yCancel = false;
                    }
                    if ((move.toGround() == false && Minecraft.player.motionY > 0.0 && Minecraft.player.isJumping() != false || Minecraft.player.isSneaking() != false) && smartYHops == false || smartYHops && yCancel) {
                        move.setIgnoreVerticalCollision();
                    }
                    move.setIgnoreHorizontalCollision();
                }
                i += 1.0f;
            }
        }
    }

    public float blockBreakSpeed(IBlockState blockMaterial, ItemStack tool) {
        float mineSpeed = tool.getStrVsBlock(blockMaterial);
        int efficiencyFactor = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, tool);
        mineSpeed = (float)((double)mineSpeed > 1.0 && efficiencyFactor > 0 ? (double)((float)(efficiencyFactor * efficiencyFactor) + mineSpeed) + 1.0 : (double)mineSpeed);
        if (Minecraft.player.getActivePotionEffect(MobEffects.HASTE) != null) {
            mineSpeed *= 1.0f + (float)Objects.requireNonNull(Minecraft.player.getActivePotionEffect(MobEffects.HASTE)).getAmplifier() * 0.2f;
        }
        return mineSpeed;
    }

    public double blockBrokenTime(BlockPos pos, ItemStack tool) {
        if (pos == null || tool == null) {
            return 0.0;
        }
        IBlockState blockMaterial = NoClip.mc.world.getBlockState(pos);
        float damageTicks = this.blockBreakSpeed(blockMaterial, tool) / blockMaterial.getBlockHardness(NoClip.mc.world, pos) / 30.0f;
        return Math.ceil(1.0f / damageTicks) * GameSyncTPS.getConpenseMath(1.0, 0.8f);
    }

    ItemStack getBestStack(BlockPos pos) {
        int bestSlot = -1;
        if (pos == null) {
            return Minecraft.player.inventory.getCurrentItem();
        }
        for (int i = 0; i < 9; ++i) {
            float speed;
            ItemStack stack = Minecraft.player.inventory.getStackInSlot(i);
            double max = 0.0;
            if (stack.isEmpty() || !((speed = stack.getStrVsBlock(NoClip.mc.world.getBlockState(pos))) > 1.0f)) continue;
            int eff = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);
            if ((double)(speed = (float)((double)speed + (eff > 0 ? Math.pow(eff, 2.0) + 1.0 : 0.0))) > max) {
                max = speed;
                bestSlot = i;
            }
            if (NoClip.mc.world.getBlockState(pos).getBlock() != Blocks.WEB || InventoryUtil.getItemInHotbar(Items.SHEARS) == -1) continue;
            bestSlot = InventoryUtil.getItemInHotbar(Items.SHEARS);
        }
        return bestSlot >= 0 && bestSlot <= 8 ? Minecraft.player.inventory.getStackInSlot(bestSlot) : Minecraft.player.inventory.getCurrentItem();
    }

    @Override
    public void onRender2D(ScaledResolution sr) {
        if (this.actived && this.Mode.currentMode.equalsIgnoreCase("Reallyworld")) {
            float x = sr.getScaledWidth() / 2;
            float y = 50.0f;
            float w = 30.0f;
            float h = 1.0f;
            x -= w;
            y -= h;
            float x2 = x + (w *= 2.0f);
            float y2 = y + (h *= 2.0f);
            int c1 = -1;
            int c2 = -1;
            int cBG = ColorUtils.getColor(0, 0, 0, 130);
            RenderUtils.drawAlphedRect(x, y, x2, y2, cBG);
            RenderUtils.drawLightContureRectFullGradient(x, y, x2, y2, c1, c2, false);
            RenderUtils.drawRect(x, y, x + (x2 - x) * this.animSHKALE.getAnim(), y2, c1);
        }
    }

    @EventTarget
    public void onPlayerMotionUpdate(EventPlayerMotionUpdate e) {
        float sp = 0.023f;
        double yaw = (double)MoveMeHelp.moveYaw(Minecraft.player.rotationYaw) * 0.017453292;
        if (this.Mode.currentMode.equalsIgnoreCase("Packet2") && this.actived) {
            Minecraft.player.onGround = false;
            e.setCancelled(true);
            float speedY = 0.0f;
            if (Minecraft.player.movementInput.jump) {
                speedY = 0.031f;
            } else {
                Minecraft.player.motionY = Minecraft.player.movementInput.sneak ? (Minecraft.player.ticksExisted % 7 == 1 ? -0.05 : -100.0) : (double)speedY;
            }
            MoveMeHelp.setSpeed(0.23);
            if (Minecraft.player.isCollidedHorizontally && NoClip.mc.gameSettings.keyBindForward.isKeyDown() && (double)Minecraft.player.hurtTime < 3.5 && Minecraft.player.isCollidedHorizontally && (Minecraft.player.hurtTime > 2 || Minecraft.player.hurtTime == 0) || Minecraft.player.movementInput.sneak && Minecraft.player.ticksExisted % 7 == 1 || Minecraft.player.movementInput.jump) {
                if (!Minecraft.player.movementInput.sneak && !Minecraft.player.movementInput.jump) {
                    Minecraft.player.motionX = -Math.sin(yaw) * (double)sp;
                    Minecraft.player.motionZ = Math.cos(yaw) * (double)sp;
                }
                double x = Minecraft.player.posX + Minecraft.player.motionX;
                double y = Minecraft.player.posY + Minecraft.player.motionY;
                double z = Minecraft.player.posZ + Minecraft.player.motionZ;
                Minecraft.player.connection.sendPacket(new CPacketPlayer.PositionRotation(Minecraft.player.posX + Minecraft.player.motionX, Minecraft.player.posY + Minecraft.player.motionY, Minecraft.player.posZ + Minecraft.player.motionZ, Minecraft.player.rotationYaw, Minecraft.player.rotationPitch, Minecraft.player.onGround));
                Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(x, y -= -1337.0, z, Minecraft.player.onGround));
                Minecraft.player.connection.sendPacket(new CPacketPlayer.PositionRotation(Minecraft.player.posX + Minecraft.player.motionX, Minecraft.player.posY + Minecraft.player.motionY, Minecraft.player.posZ + Minecraft.player.motionZ, Minecraft.player.rotationYaw, Minecraft.player.rotationPitch, Minecraft.player.onGround));
                Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(x, y -= -1337.0, z, Minecraft.player.onGround));
            }
        }
    }

    public static double[] forward(double speed) {
        float forward = MovementInput.moveForward;
        float side = MovementInput.moveStrafe;
        float yaw = Minecraft.player.prevRotationYaw + (Minecraft.player.rotationYaw - Minecraft.player.prevRotationYaw) * mc.getRenderPartialTicks();
        if (forward != 0.0f) {
            if (side > 0.0f) {
                yaw += (float)(forward > 0.0f ? -45 : 45);
            } else if (side < 0.0f) {
                yaw += (float)(forward > 0.0f ? 45 : -45);
            }
            side = 0.0f;
            if (forward > 0.0f) {
                forward = 1.0f;
            } else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        double posX = (double)forward * speed * cos + (double)side * speed * sin;
        double posZ = (double)forward * speed * sin - (double)side * speed * cos;
        return new double[]{posX, posZ};
    }

    @EventTarget
    public void onPacket(EventReceivePacket event) {
        Packet packet;
        if (this.Mode.currentMode.equalsIgnoreCase("Packet2") && this.actived && (packet = event.getPacket()) instanceof SPacketPlayerPosLook) {
            SPacketPlayerPosLook packet2 = (SPacketPlayerPosLook)packet;
            Minecraft.player.connection.sendPacket(new CPacketConfirmTeleport(packet2.getTeleportId()));
            Minecraft.player.connection.sendPacket(new CPacketPlayer.PositionRotation(packet2.getX(), Minecraft.player.posY, packet2.getZ(), packet2.getYaw(), packet2.getPitch(), false));
            Minecraft.player.setPosition(packet2.getX(), Minecraft.player.posY, packet2.getZ());
            event.setCancelled(true);
        }
    }

    @EventTarget
    public void onPacket(EventSendPacket event) {
        if (Minecraft.player != null && NoClip.mc.world != null && !Minecraft.player.isDead && this.actived && event.getPacket() instanceof CPacketConfirmTeleport) {
            this.flag = true;
        }
    }

    @EventTarget
    public void onRender3D(Event3D event) {
        if (!this.flag) {
            this.colorEx = MathUtils.lerp(this.colorEx, 0.0f, 0.05f);
            this.timer.reset();
        } else {
            this.colorEx = MathUtils.lerp(this.colorEx, 1.0f, 0.2f);
            if (this.timer.hasReached(300.0)) {
                this.flag = false;
            }
        }
        if (!this.actived) {
            return;
        }
        int color = ColorUtils.getProgressColor(1.0f - this.colorEx).getRGB();
        EntityPlayerSP entity = Minecraft.player;
        double x = 0.0;
        double y = 0.0;
        double z = 0.0;
        GL11.glPushMatrix();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        NoClip.mc.entityRenderer.disableLightmap();
        GL11.glEnable(3042);
        GL11.glLineWidth(1.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glShadeModel(7425);
        RenderUtils.setupColor(color, Minecraft.player.isCollidedHorizontally ? 255.0f : 75.0f);
        GL11.glBegin(3);
        GL11.glVertex3d(-0.3, 0.0, -0.3);
        GL11.glVertex3d(-0.3, 0.0, 0.3);
        GL11.glVertex3d(0.3, 0.0, 0.3);
        GL11.glVertex3d(0.3, 0.0, -0.3);
        GL11.glVertex3d(-0.3, 0.0, -0.3);
        GL11.glEnd();
        GL11.glBegin(3);
        GL11.glVertex3d(-0.29, 0.0, -0.29);
        GL11.glVertex3d(-0.29, 0.0, 0.29);
        GL11.glVertex3d(0.29, 0.0, 0.29);
        GL11.glVertex3d(0.29, 0.0, -0.29);
        GL11.glVertex3d(-0.29, 0.0, -0.29);
        GL11.glEnd();
        GL11.glBegin(3);
        GL11.glVertex3d(-0.28, 0.0, -0.28);
        GL11.glVertex3d(-0.28, 0.0, 0.28);
        GL11.glVertex3d(0.28, 0.0, 0.28);
        GL11.glVertex3d(0.28, 0.0, -0.28);
        GL11.glVertex3d(-0.28, 0.0, -0.28);
        GL11.glEnd();
        GL11.glBegin(3);
        GL11.glVertex3d(-0.27, 0.0, -0.27);
        GL11.glVertex3d(-0.27, 0.0, 0.27);
        GL11.glVertex3d(0.27, 0.0, 0.27);
        GL11.glVertex3d(0.27, 0.0, -0.27);
        GL11.glVertex3d(-0.27, 0.0, -0.27);
        GL11.glEnd();
        NoClip.mc.entityRenderer.enableLightmap();
        GL11.glLineWidth(1.0f);
        GL11.glShadeModel(7424);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.resetColor();
        GL11.glPopMatrix();
    }

    @Override
    public void onUpdate() {
        if (this.actived && this.Mode.currentMode.equalsIgnoreCase("Reallyworld")) {
            float moveYaw = (float)Math.toRadians(MoveMeHelp.moveYaw(Minecraft.player.rotationYaw - 15.0f));
            float rs = MoveMeHelp.moveKeysPressed() ? 1.0f : 0.0f;
            double sin = -Math.sin(moveYaw) * (double)rs;
            double cos = Math.cos(moveYaw) * (double)rs;
            BlockPos pos = BlockUtils.getEntityBlockPos(Minecraft.player).add(sin, -0.0, cos);
            Block und = NoClip.mc.world.getBlockState(pos).getBlock();
            if (und == Blocks.AIR) {
                pos = pos.down(2);
            }
            this.ticker -= 1.0f;
            if (pos != null) {
                PlayerHelper.currentBlock = pos;
                ItemStack stack = this.getBestStack(pos);
                double time = this.blockBrokenTime(pos, stack) * 4.0;
                int item = InventoryUtil.getItemInHotbar(stack.getItem());
                if (item != -1) {
                    EnumFacing face = BlockUtils.getPlaceableSide(pos);
                    if (face == null) {
                        face = EnumFacing.UP;
                    }
                    int slot = Minecraft.player.inventory.currentItem;
                    Minecraft.player.inventory.currentItem = item;
                    NoClip.mc.playerController.blockHitDelay = 0;
                    NoClip.mc.playerController.onPlayerDamageBlock(pos, face);
                    if (Minecraft.player.ticksExisted % 3 == 0) {
                        Minecraft.player.connection.sendPacket(new CPacketAnimation(EnumHand.OFF_HAND));
                    }
                    if (NoClip.mc.world.getBlockState(pos).getBlock() == Blocks.AIR) {
                        this.ticker = 7.0f;
                    }
                    if (this.ticker == 6.0f && Minecraft.player.isJumping() && MoveMeHelp.moveKeysPressed()) {
                        Minecraft.player.multiplyMotionXZ(1.5f);
                    }
                    Minecraft.player.inventory.currentItem = slot;
                    this.animSHKALE.to = MathUtils.clamp(NoClip.mc.playerController.curBlockDamageMP * 1.05f, 0.0f, 1.0f);
                    this.animSHKALE.speed = 0.125f;
                }
            }
            boolean bl = this.tickGo = this.ticker > 0.0f || Minecraft.player.ticksExisted <= 80;
        }
        if (this.flag && !this.timer.hasReached(50.0)) {
            Minecraft.player.motionX = -Minecraft.player.motionX * 2.0;
            Minecraft.player.motionZ = -Minecraft.player.motionZ * 2.0;
        }
        Minecraft.player.noClip = true;
        Minecraft.player.stepHeight = 0.0f;
        double x = Minecraft.player.posX;
        double y = Minecraft.player.posY;
        double z = Minecraft.player.posZ;
        boolean isInBlock = false;
        float i = 0.0f;
        while (true) {
            double d = i;
            double d2 = Minecraft.player.isSneaking() ? 1.6 : 1.8;
            if (!(d < d2)) break;
            if (NoClip.posBlock(x, y + (double)i, z) || NoClip.posBlock(x + (double)0.275f, y + (double)i, z + (double)0.275f) || NoClip.posBlock(x - (double)0.275f, y + (double)i, z - (double)0.275f) || NoClip.posBlock(x + (double)0.275f, y + (double)i, z) || NoClip.posBlock(x - (double)0.275f, y + (double)i, z) || NoClip.posBlock(x, y + (double)i, z + (double)0.275f) || NoClip.posBlock(x, y + (double)i, z - (double)0.275f) || NoClip.posBlock(x + (double)0.275f, y + (double)i, z - (double)0.275f) || NoClip.posBlock(x - (double)0.275f, y + (double)i, z + (double)0.275f)) {
                isInBlock = true;
            }
            i += 1.0f;
        }
        double yaw = (double)Minecraft.player.rotationYaw * 0.017453292;
        float speed = 1.0E-7f;
        float TMSpeed = this.Boost.fValue;
        if (this.Mode.currentMode.equalsIgnoreCase("Packet") && isInBlock) {
            Minecraft.player.motionY = 0.0;
            Minecraft.player.motionX = 0.0;
            Minecraft.player.motionZ = 0.0;
            Minecraft.player.onGround = false;
            Minecraft.player.jumpMovementFactor = 0.0f;
            if (NoClip.mc.gameSettings.keyBindForward.pressed) {
                Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX - Math.sin(Math.toRadians(Minecraft.player.rotationYaw)) * 15.0, Minecraft.player.posY, Minecraft.player.posZ + Math.cos(Math.toRadians(Minecraft.player.rotationYaw)) * 15.0, true));
                Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 15.0, Minecraft.player.posZ, true));
                if (Minecraft.player.ticksExisted % 9 == 0) {
                    Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY + 1.0, Minecraft.player.posZ, true));
                }
            }
            if (NoClip.mc.gameSettings.keyBindSneak.pressed) {
                Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY - 2.0, Minecraft.player.posZ, true));
            }
        }
        if (this.TimerBoost.bValue && !this.Mode.currentMode.equalsIgnoreCase("Packet") && !this.Mode.currentMode.equalsIgnoreCase("Packet2")) {
            if ((NoClip.mc.gameSettings.keyBindForward.isKeyDown() || NoClip.mc.gameSettings.keyBindBack.isKeyDown() || NoClip.mc.gameSettings.keyBindSneak.isKeyDown() || Minecraft.player.isMoving()) && isInBlock && (!this.Mode.currentMode.equalsIgnoreCase("Matrix") || !Minecraft.player.isSneaking())) {
                NoClip.mc.timer.speed = TMSpeed + 1.0f;
            } else if (NoClip.mc.timer.speed == (double)(TMSpeed + 1.0f)) {
                NoClip.mc.timer.speed = 1.0;
            }
        }
        if (this.Mode.currentMode.equalsIgnoreCase("Matrix")) {
            if (isInBlock) {
                Minecraft.player.isEntityInsideOpaqueBlock = true;
                double d = NoClip.mc.timer.speed = Minecraft.player.ticksExisted % 2 == 0 ? 8.0 : 0.5;
                double d3 = Minecraft.player.isSneaking() ? -0.7 : (Minecraft.player.motionY = Minecraft.player.isJumping() ? 0.42 : Minecraft.player.motionY);
                if (Minecraft.player.isJumping()) {
                    Minecraft.player.setPosY(Minecraft.player.posY + 1.0E-10);
                }
            } else if (NoClip.mc.timer.speed == 8.0 || NoClip.mc.timer.speed == 0.5) {
                NoClip.mc.timer.speed = 1.0;
            }
        }
        if (this.MatrixLift.bValue && isInBlock && !this.Mode.currentMode.equalsIgnoreCase("Sunrise") && !this.Mode.currentMode.equalsIgnoreCase("Packet") && !this.Mode.currentMode.equalsIgnoreCase("Packet2") && !Minecraft.player.isInWater() && NoClip.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - 0.5, Minecraft.player.posZ)).getBlock() != Blocks.WATER && Minecraft.player.isJumping()) {
            Minecraft.player.onGround = true;
            Entity.motionx = -0.05 + 0.1 * Math.random();
            Entity.motionz = -0.05 + 0.1 * Math.random();
            Minecraft.player.connection.sendPacket(new CPacketPlayer(false));
        }
        boolean nocull = false;
        if (this.MatrixTP.bValue && !this.Mode.currentMode.equalsIgnoreCase("Packet") && !this.Mode.currentMode.equalsIgnoreCase("Packet2")) {
            if (!Minecraft.player.isInWater() && NoClip.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - 0.5, Minecraft.player.posZ)).getBlock() != Blocks.WATER && Minecraft.player.isEntityInsideOpaqueBlock()) {
                Minecraft.player.motionY = 0.0;
            }
            int VerticalRange = (int)this.TPRange.fValue;
            for (float i2 = 0.0f; i2 < (float)VerticalRange; i2 += 0.005f) {
                double clip;
                float o;
                float f = o = NoClip.mc.gameSettings.keyBindJump.isKeyDown() && !NoClip.mc.gameSettings.keyBindSneak.isKeyDown() ? i2 : -i2;
                if (NoClip.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY + (double)o + 1.0, Minecraft.player.posZ)).getBlock() != Blocks.AIR || NoClip.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY + (double)o + 0.005, Minecraft.player.posZ)).getBlock() != Blocks.AIR && NoClip.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY + (double)o + 0.005, Minecraft.player.posZ)).getBlock() != Blocks.WATER && NoClip.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY + (double)o + 0.005, Minecraft.player.posZ)).getBlock() != Blocks.LAVA && NoClip.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY + (double)o + 0.005, Minecraft.player.posZ)).getBlock() != Blocks.WEB && NoClip.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY + (double)o, Minecraft.player.posZ)).getBlock() != Blocks.TRAPDOOR && NoClip.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY + (double)o, Minecraft.player.posZ)).getBlock() != Blocks.IRON_TRAPDOOR || NoClip.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY + (double)o - 0.002, Minecraft.player.posZ)).getBlock() == Blocks.AIR || !(i2 > 2.0f)) continue;
                if (NoClip.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - 0.1, Minecraft.player.posZ)).getBlock() != Blocks.WATER && NoClip.mc.gameSettings.keyBindJump.isKeyDown()) {
                    Minecraft.player.onGround = true;
                    Minecraft.player.motionY = 0.0;
                }
                if (NoClip.mc.gameSettings.keyBindSneak.isKeyDown()) {
                    Minecraft.player.onGround = true;
                    NoClip.mc.gameSettings.keyBindJump.pressed = false;
                }
                if (!NoClip.mc.gameSettings.keyBindJump.isKeyDown() && !NoClip.mc.gameSettings.keyBindSneak.isKeyDown()) continue;
                Minecraft.player.fallDistance = 4.5682973E-5f;
                nocull = true;
                double d = clip = NoClip.mc.gameSettings.keyBindJump.isKeyDown() && !NoClip.mc.gameSettings.keyBindSneak.isKeyDown() ? (double)i2 + 0.05 : (double)(-i2) + 0.002;
                if (NoClip.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY + (double)o - 1.0, Minecraft.player.posZ)).getBlock() == Blocks.WATER) {
                    clip -= 1.0;
                }
                if (Minecraft.player.ticksExisted % 3 != 0) continue;
                if (NoClip.mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - 0.1, Minecraft.player.posZ)).getBlock() != Blocks.WATER) {
                    Minecraft.player.onGround = true;
                }
                Clip.goClip(clip, 0.0, ElytraBoost.canElytra());
            }
        }
        float speedY = this.SpeedY.fValue;
        if ((this.Mode.currentMode.equalsIgnoreCase("Vanilla") || this.Mode.currentMode.equalsIgnoreCase("Matrix")) && isInBlock) {
            if (!this.Mode.currentMode.equalsIgnoreCase("Matrix") || !Minecraft.player.isSneaking()) {
                Minecraft.player.motionY = 0.0;
            }
            MoveMeHelp.setSpeed(MoveMeHelp.getSpeed() * (double)this.SpeedF.fValue);
            if (NoClip.mc.gameSettings.keyBindJump.isKeyDown()) {
                Minecraft.player.motionY = Minecraft.player.motionY + (this.Mode.currentMode.equalsIgnoreCase("Matrix") ? 0.42 : (double)speedY);
            }
            if (NoClip.mc.gameSettings.keyBindSneak.isKeyDown()) {
                Minecraft.player.motionY = Minecraft.player.motionY - (this.Mode.currentMode.equalsIgnoreCase("Matrix") ? -0.5 : (double)speedY);
            }
        }
        if (this.DoorPhase.bValue && !this.Mode.currentMode.equalsIgnoreCase("Sunrise") && !this.Mode.currentMode.equalsIgnoreCase("Packet") && !this.Mode.currentMode.equalsIgnoreCase("Packet2")) {
            float val = 1.0f;
            double dx = x - (double)(MathHelper.sin((float)yaw) * val);
            double dx2 = x - (double)MathHelper.sin((float)yaw) * 0.3001;
            double dy = y + (double)Minecraft.player.height - 1.0E-5;
            double dz = z + (double)(MathHelper.cos((float)yaw) * val);
            double dz2 = z + (double)MathHelper.cos((float)yaw) * 0.3001;
            if (NoClip.mc.world.getBlockState(new BlockPos(dx2, dy, dz2)).getBlock() instanceof BlockDoor && Minecraft.player.isCollidedHorizontally && Minecraft.player.isCollidedHorizontally) {
                boolean bl = Minecraft.player.isSneaking = !isInBlock;
                if (Minecraft.player.motionY < 0.2 && Minecraft.player.ticksExisted % 2 == 0) {
                    Minecraft.player.onGround = true;
                    Minecraft.player.motionY = 0.42f;
                }
                if (Minecraft.player.hurtTime > 0) {
                    Minecraft.player.setPosition(dx, dy, dz);
                    mc.getConnection().sendPacket(new CPacketPlayer.Position(x, y, z, false));
                }
            }
        }
    }

    public static boolean posBlock(double x, double y, double z) {
        return NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.AIR && NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.WATER && NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.LAVA && NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.BED && NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.CAKE && NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.TALLGRASS && NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.GRASS_PATH && NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.FLOWER_POT && NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.CHORUS_FLOWER && NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.RED_FLOWER && NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.YELLOW_FLOWER && NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.SAPLING && NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.VINE && NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.ACACIA_FENCE && NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.ACACIA_FENCE_GATE && NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.BIRCH_FENCE && NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.BIRCH_FENCE_GATE && NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.DARK_OAK_FENCE && NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.DARK_OAK_FENCE_GATE && NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.JUNGLE_FENCE && NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.JUNGLE_FENCE_GATE && NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.NETHER_BRICK_FENCE && NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.OAK_FENCE && NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.OAK_FENCE_GATE && NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.SPRUCE_FENCE && NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.SPRUCE_FENCE_GATE && NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.ENCHANTING_TABLE && NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.END_PORTAL_FRAME && NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.DOUBLE_PLANT && NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.STANDING_SIGN && NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.WALL_SIGN && NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.SKULL && NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.DAYLIGHT_DETECTOR && NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.DAYLIGHT_DETECTOR_INVERTED && NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.PURPUR_SLAB && NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.STONE_SLAB && NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.WOODEN_SLAB && NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.CARPET && NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.DEADBUSH && NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.REDSTONE_WIRE && NoClip.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.SNOW_LAYER;
    }

    public static boolean isNoClip(Entity entity) {
        if (Minecraft.player != null) {
            double x = Minecraft.player.posX;
            double y = Minecraft.player.posY;
            double z = Minecraft.player.posZ;
            double yaw = (double)Minecraft.player.rotationYaw * 0.017453292;
            double dx = x - Math.sin(yaw) * (double)0.05f;
            double dy = y + (double)Minecraft.player.height;
            double dz = z + Math.cos(yaw) * (double)0.05f;
            if (!(NoClip.get.Mode.currentMode.equalsIgnoreCase("Sunrise") || NoClip.get.Mode.currentMode.equalsIgnoreCase("Packet") || NoClip.get.Mode.currentMode.equalsIgnoreCase("Packet2") || !NoClip.get.actived || Minecraft.player == null || Minecraft.player.ridingEntity != null && entity != Minecraft.player.ridingEntity)) {
                float i = 0.0f;
                while (true) {
                    double d = i;
                    double d2 = Minecraft.player.isSneaking() ? 1.6 : 1.8;
                    if (!(d < d2)) break;
                    if (NoClip.posBlock(x, y + (double)i, z) || NoClip.posBlock(x + (double)0.275f, y + (double)i, z + (double)0.275f) || NoClip.posBlock(x - (double)0.275f, y + (double)i, z - (double)0.275f) || NoClip.posBlock(x + (double)0.275f, y + (double)i, z) || NoClip.posBlock(x - (double)0.275f, y + (double)i, z) || NoClip.posBlock(x, y + (double)i, z + (double)0.275f) || NoClip.posBlock(x, y + (double)i, z - (double)0.275f) || NoClip.posBlock(x + (double)0.275f, y + (double)i, z - (double)0.275f) || NoClip.posBlock(x - (double)0.275f, y + (double)i, z + (double)0.275f)) {
                        return true;
                    }
                    i += 1.0f;
                }
                if (NoClip.mc.world.getBlockState(new BlockPos(dx, dy, dz)) instanceof BlockDoor && Minecraft.player.isCollidedHorizontally) {
                    return false;
                }
            }
            return entity.noClip;
        }
        return false;
    }

    @Override
    public String getDisplayName() {
        return this.getDisplayByMode(this.Mode.currentMode);
    }

    @Override
    public void onToggled(boolean actived) {
        if (!actived) {
            this.colorEx = 0.0f;
            this.timer.reset();
            NoClip.mc.timer.speed = 1.0;
            Minecraft.player.stepHeight = 0.6f;
        }
        super.onToggled(actived);
    }
}

