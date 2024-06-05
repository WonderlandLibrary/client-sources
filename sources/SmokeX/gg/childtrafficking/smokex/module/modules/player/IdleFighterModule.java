// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.player;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import gg.childtrafficking.smokex.utils.pathfinding.AStarCustomPathFinder;
import net.minecraft.util.BlockPos;
import java.util.Iterator;
import gg.childtrafficking.smokex.SmokeXClient;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.entity.Entity;
import gg.childtrafficking.smokex.utils.player.PlayerUtils;
import net.minecraft.item.ItemSkull;
import net.minecraft.entity.player.EntityPlayer;
import gg.childtrafficking.smokex.utils.pathfinding.BlockAndFacing;
import net.minecraft.util.EnumFacing;
import net.minecraft.potion.Potion;
import gg.childtrafficking.smokex.module.ModuleManager;
import gg.childtrafficking.smokex.module.modules.movement.SpeedModule;
import gg.childtrafficking.smokex.utils.player.PitUtils;
import gg.childtrafficking.smokex.event.events.player.EventMove;
import gg.childtrafficking.smokex.event.events.network.EventReceivePacket;
import gg.childtrafficking.smokex.event.events.player.EventUpdate;
import gg.childtrafficking.smokex.event.EventListener;
import net.minecraft.entity.EntityLivingBase;
import gg.childtrafficking.smokex.utils.system.TimerUtil;
import gg.childtrafficking.smokex.property.properties.BooleanProperty;
import gg.childtrafficking.smokex.property.properties.NumberProperty;
import gg.childtrafficking.smokex.property.properties.EnumProperty;
import gg.childtrafficking.smokex.utils.pathfinding.Vec3;
import java.util.ArrayList;
import gg.childtrafficking.smokex.module.ModuleCategory;
import gg.childtrafficking.smokex.module.ModuleInfo;
import gg.childtrafficking.smokex.module.Module;

@ModuleInfo(name = "IdleFighter", renderName = "Idle Fighter", description = "Automatically fights for you", category = ModuleCategory.PLAYER)
public final class IdleFighterModule extends Module
{
    private ArrayList<Vec3> path;
    private final EnumProperty<Mode> modeEnumProperty;
    private final NumberProperty<Integer> range;
    private final NumberProperty<Integer> smoothing;
    private final BooleanProperty rage;
    private final TimerUtil timerDelay;
    private final TimerUtil speedDelay;
    private int i;
    public EntityLivingBase target;
    private final TimerUtil timerUtil;
    private final EventListener<EventUpdate> updateEventListener;
    private final EventListener<EventReceivePacket> receivePacketEventListener;
    private final EventListener<EventMove> moveEventListener;
    private final double dashDistance = 5.0;
    
    public IdleFighterModule() {
        this.path = new ArrayList<Vec3>();
        this.modeEnumProperty = new EnumProperty<Mode>("Mode", Mode.PIT);
        this.range = new NumberProperty<Integer>("Range", 105, 10, 105, 1);
        this.smoothing = new NumberProperty<Integer>("Smooth", 7, 1, 25, 1);
        this.rage = new BooleanProperty("Rage", false);
        this.timerDelay = new TimerUtil();
        this.speedDelay = new TimerUtil();
        this.i = 0;
        this.timerUtil = new TimerUtil();
        this.updateEventListener = (event -> {
            this.target = this.getClosest(this.range.getValue());
            final Entity upgradesNPC = this.mc.theWorld.getEntityByID(5);
            if (this.modeEnumProperty.getValue() == Mode.PIT && event.isPre() && this.mc.thePlayer.ticksExisted % 15000 == 0) {
                this.mc.thePlayer.sendChatMessage("/play pit");
            }
            if (PitUtils.isInPit()) {
                if (this.mc.theWorld.playerEntities.size() < 50 && this.mc.thePlayer.ticksExisted % 100 == 0) {
                    this.mc.thePlayer.sendChatMessage("/play pit");
                }
                this.timerDelay.reset();
            }
            else if (this.timerDelay.hasElapsed(5000.0)) {
                this.mc.thePlayer.sendChatMessage("/play pit");
                this.timerDelay.reset();
            }
            this.mc.gameSettings.keyBindForward.pressed = true;
            this.mc.gameSettings.keyBindSprint.pressed = true;
            if (!ModuleManager.getInstance(SpeedModule.class).isToggled() && !this.rage.getValue()) {
                if (this.mc.thePlayer.ticksExisted % 15 == 0 && !this.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                    this.mc.gameSettings.keyBindJump.pressed = true;
                }
                else {
                    this.mc.gameSettings.keyBindJump.pressed = false;
                }
            }
            else {
                this.mc.gameSettings.keyBindJump.pressed = true;
            }
            if (PitUtils.getLevel() >= 10 && PitUtils.getGold() >= 500.0) {
                if (this.mc.thePlayer.getDistanceToEntity(upgradesNPC) > 4.0f) {
                    this.mc.gameSettings.keyBindForward.pressed = true;
                    this.mc.gameSettings.keyBindJump.pressed = true;
                    this.mc.gameSettings.keyBindSprint.pressed = false;
                    this.mc.thePlayer.setSprinting(false);
                    final Vec3 topFrom = new Vec3(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ);
                    final Vec3 to = new Vec3(upgradesNPC.posX, upgradesNPC.posY, upgradesNPC.posZ);
                    this.path = this.computePath(topFrom, to);
                    final int x = (int)upgradesNPC.posX;
                    final int y = (int)upgradesNPC.posY;
                    final int z = (int)upgradesNPC.posZ;
                    final Vec3 vec = this.path.get(1);
                    int x2 = (int)Math.floor(vec.getX());
                    int y2 = (int)Math.floor(vec.getY());
                    int z2 = (int)Math.floor(vec.getZ());
                    if ((int)Math.floor(this.mc.thePlayer.posX) == x2 && (int)Math.floor(this.mc.thePlayer.posY) == y2 && (int)Math.floor(this.mc.thePlayer.posZ) == z2) {
                        final Vec3 vec2 = this.path.get(2);
                        x2 = (int)Math.floor(vec2.getX());
                        y2 = (int)Math.floor(vec2.getY());
                        z2 = (int)Math.floor(vec2.getZ());
                    }
                    final float[] rots = BlockAndFacing.BlockUtil.getDirectionToBlock(x2, y2, z2, EnumFacing.UP);
                    this.mc.thePlayer.rotationYaw = rots[0];
                }
                else {
                    this.mc.gameSettings.keyBindForward.pressed = false;
                    this.mc.gameSettings.keyBindJump.pressed = false;
                    this.mc.gameSettings.keyBindSprint.pressed = false;
                    if (this.mc.currentScreen == null) {
                        this.mc.playerController.interactWithEntitySendPacket(this.mc.thePlayer, upgradesNPC);
                    }
                    else if (!(this.mc.thePlayer.openContainer.getSlot(30).getStack().getItem() instanceof ItemSkull)) {
                        PlayerUtils.windowClick(30, 0, PlayerUtils.ClickType.CLICK);
                        PlayerUtils.windowClick(19, 0, PlayerUtils.ClickType.CLICK);
                    }
                }
            }
            if (this.mc.thePlayer.posY >= ((PitUtils.getCurrentEvent() == PitUtils.MajorEvent.SPIRE) ? 256 : 105)) {
                final Entity prestigeNPC = this.mc.theWorld.getEntityByID(8);
                if (PitUtils.getLevel() == 120 && prestigeNPC != null) {
                    if (this.mc.thePlayer.getDistanceToEntity(prestigeNPC) > 4.0f) {
                        this.mc.gameSettings.keyBindForward.pressed = true;
                        this.mc.gameSettings.keyBindJump.pressed = true;
                        this.mc.gameSettings.keyBindSprint.pressed = false;
                        this.mc.thePlayer.setSprinting(false);
                        final Vec3 topFrom2 = new Vec3(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ);
                        final Vec3 to2 = new Vec3(prestigeNPC.posX, prestigeNPC.posY, prestigeNPC.posZ);
                        this.path = this.computePath(topFrom2, to2);
                        final int x3 = (int)prestigeNPC.posX;
                        final int y3 = (int)prestigeNPC.posY;
                        final int z3 = (int)prestigeNPC.posZ;
                        final Vec3 vec3 = this.path.get(1);
                        int x4 = (int)Math.floor(vec3.getX());
                        int y4 = (int)Math.floor(vec3.getY());
                        int z4 = (int)Math.floor(vec3.getZ());
                        if ((int)Math.floor(this.mc.thePlayer.posX) == x4 && (int)Math.floor(this.mc.thePlayer.posY) == y4 && (int)Math.floor(this.mc.thePlayer.posZ) == z4) {
                            final Vec3 vec4 = this.path.get(2);
                            x4 = (int)Math.floor(vec4.getX());
                            y4 = (int)Math.floor(vec4.getY());
                            z4 = (int)Math.floor(vec4.getZ());
                        }
                        final float[] rots2 = BlockAndFacing.BlockUtil.getDirectionToBlock(x4, y4, z4, EnumFacing.UP);
                        this.mc.thePlayer.rotationYaw = rots2[0];
                    }
                    else {
                        this.mc.gameSettings.keyBindForward.pressed = false;
                        this.mc.gameSettings.keyBindJump.pressed = false;
                        this.mc.gameSettings.keyBindSprint.pressed = false;
                        if (this.mc.currentScreen == null) {
                            this.mc.playerController.interactWithEntitySendPacket(this.mc.thePlayer, prestigeNPC);
                        }
                        else {
                            PlayerUtils.windowClick(11, 0, PlayerUtils.ClickType.CLICK);
                        }
                    }
                }
                else {
                    if (this.mc.thePlayer.getDistance(0.0, this.mc.thePlayer.posY, 0.0) < 5.0) {
                        this.mc.gameSettings.keyBindForward.pressed = false;
                        this.mc.gameSettings.keyBindJump.pressed = false;
                        this.mc.gameSettings.keyBindSprint.pressed = false;
                    }
                    final float[] angle = PlayerUtils.getRotationFromPosition(0.0, 0.0, 105.0);
                    this.mc.thePlayer.rotationYaw += (angle[0] - this.mc.thePlayer.rotationYaw) / this.smoothing.getValue();
                }
            }
            else if (this.target != null) {
                final float[] rotations = PlayerUtils.getRotations(this.target);
                if (this.mc.thePlayer.getDistanceToEntity(this.target) <= 3.4 && ModuleManager.getInstance(SpeedModule.class).isToggled() && !this.rage.getValue()) {
                    if (this.mc.thePlayer.ticksExisted % 3 == 0) {
                        this.mc.gameSettings.keyBindRight.pressed = true;
                        this.mc.gameSettings.keyBindLeft.pressed = false;
                    }
                    else if (this.mc.thePlayer.ticksExisted % 4 == 0) {
                        this.mc.gameSettings.keyBindRight.pressed = false;
                        this.mc.gameSettings.keyBindLeft.pressed = true;
                    }
                    if (this.mc.thePlayer.ticksExisted % 2 == 0) {
                        this.mc.gameSettings.keyBindForward.pressed = true;
                        this.timerUtil.reset();
                    }
                    else {
                        this.mc.gameSettings.keyBindForward.pressed = false;
                    }
                }
                else {
                    final Vec3 topFrom3 = new Vec3(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ);
                    final Vec3 to3 = new Vec3(this.target.posX, this.target.posY, this.target.posZ);
                    this.path = this.computePath(topFrom3, to3);
                    int x5 = (int)this.target.posX;
                    int y5 = (int)this.target.posY;
                    int z5 = (int)this.target.posZ;
                    try {
                        final Vec3 vec5 = this.path.get(this.i);
                        x5 = (int)vec5.getX();
                        y5 = (int)vec5.getY();
                        z5 = (int)vec5.getZ();
                    }
                    catch (final Exception e) {
                        final Vec3 vec6 = null;
                    }
                    final float[] rots3 = BlockAndFacing.BlockUtil.getDirectionToBlock(x5, y5, z5, EnumFacing.UP);
                    this.mc.thePlayer.rotationYaw += (rots3[0] - this.mc.thePlayer.rotationYaw) / this.smoothing.getValue();
                    if (this.mc.thePlayer.isCollidedHorizontally && this.mc.thePlayer.isCollidedVertically && this.mc.thePlayer.onGround) {
                        this.mc.thePlayer.jump();
                    }
                    if ((int)this.mc.thePlayer.posX == x5 && (int)this.mc.thePlayer.posY == y5 && (int)this.mc.thePlayer.posZ == z5) {
                        ++this.i;
                    }
                }
            }
            return;
        });
        this.receivePacketEventListener = (event -> {
            if (event.getPacket() instanceof S08PacketPlayerPosLook) {
                this.speedDelay.reset();
            }
            if (event.getPacket() instanceof S02PacketChat) {
                final String message = ((S02PacketChat)event.getPacket()).getChatComponent().getUnformattedText();
                if (message.contains("Limbo")) {
                    this.mc.thePlayer.sendChatMessage("/l");
                }
            }
            return;
        });
        this.moveEventListener = (event -> {});
    }
    
    private EntityLivingBase getClosest(final double range) {
        double dist = range;
        EntityLivingBase target = null;
        for (final Object object : this.mc.theWorld.loadedEntityList) {
            final Entity entity = (Entity)object;
            if (entity instanceof EntityLivingBase) {
                final EntityLivingBase player = (EntityLivingBase)entity;
                if (!(player instanceof EntityPlayer) || player == this.mc.thePlayer || !player.isEntityAlive() || player.posY > 105.0 || player.isInvisible() || SmokeXClient.getInstance().getPlayerManager().isFriend(player.getName())) {
                    continue;
                }
                final double currentDist = this.mc.thePlayer.getDistanceToEntity(player);
                if (currentDist > dist) {
                    continue;
                }
                dist = currentDist;
                target = player;
            }
        }
        return target;
    }
    
    private ArrayList<Vec3> computePath(Vec3 topFrom, final Vec3 to) {
        if (!this.isPassable(new BlockPos(topFrom.mc()))) {
            topFrom = topFrom.addVector(0.0, 1.0, 0.0);
        }
        final AStarCustomPathFinder pathfinder = new AStarCustomPathFinder(topFrom, to);
        pathfinder.compute();
        int i = 0;
        Vec3 lastLoc = null;
        Vec3 lastDashLoc = null;
        final ArrayList<Vec3> path = new ArrayList<Vec3>();
        final ArrayList<Vec3> pathFinderPath = pathfinder.getPath();
        for (final Vec3 pathElm : pathFinderPath) {
            if (i == 0 || i == pathFinderPath.size() - 1) {
                if (lastLoc != null) {
                    path.add(lastLoc.addVector(0.5, 0.0, 0.5));
                }
                path.add(pathElm.addVector(0.5, 0.0, 0.5));
                lastDashLoc = pathElm;
            }
            else {
                boolean canContinue = true;
                Label_0350: {
                    if (pathElm.squareDistanceTo(lastDashLoc) > 25.0) {
                        canContinue = false;
                    }
                    else {
                        final double smallX = Math.min(lastDashLoc.getX(), pathElm.getX());
                        final double smallY = Math.min(lastDashLoc.getY(), pathElm.getY());
                        final double smallZ = Math.min(lastDashLoc.getZ(), pathElm.getZ());
                        final double bigX = Math.max(lastDashLoc.getX(), pathElm.getX());
                        final double bigY = Math.max(lastDashLoc.getY(), pathElm.getY());
                        final double bigZ = Math.max(lastDashLoc.getZ(), pathElm.getZ());
                        for (int x = (int)smallX; x <= bigX; ++x) {
                            for (int y = (int)smallY; y <= bigY; ++y) {
                                for (int z = (int)smallZ; z <= bigZ; ++z) {
                                    if (!AStarCustomPathFinder.checkPositionValidity(x, y, z, false)) {
                                        canContinue = false;
                                        break Label_0350;
                                    }
                                }
                            }
                        }
                    }
                }
                if (!canContinue) {
                    path.add(lastLoc.addVector(0.5, 0.0, 0.5));
                    lastDashLoc = lastLoc;
                }
            }
            lastLoc = pathElm;
            ++i;
        }
        return path;
    }
    
    private boolean isPassable(final BlockPos pos) {
        final Block block = Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ())).getBlock();
        return block.getMaterial() == Material.air || block.getMaterial() == Material.plants || block.getMaterial() == Material.vine || block == Blocks.ladder || block == Blocks.water || block == Blocks.flowing_water || block == Blocks.wall_sign || block == Blocks.standing_sign;
    }
    
    private enum Mode
    {
        PIT;
    }
}
