// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.modules.combat;

import net.minecraft.util.EnumHand;
import me.perry.mcdonalds.util.MathUtil;
import me.perry.mcdonalds.McDonalds;
import me.perry.mcdonalds.features.command.Command;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.perry.mcdonalds.util.InventoryUtil;
import net.minecraft.block.BlockWeb;
import java.util.Iterator;
import me.perry.mcdonalds.util.BlockUtil;
import java.util.Comparator;
import java.util.ArrayList;
import net.minecraft.util.math.Vec3d;
import java.util.List;
import net.minecraft.entity.Entity;
import me.perry.mcdonalds.util.EntityUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.player.EntityPlayer;
import me.perry.mcdonalds.util.Timer;
import me.perry.mcdonalds.features.setting.Setting;
import me.perry.mcdonalds.features.modules.Module;

public class AutoWeb extends Module
{
    public static boolean isPlacing;
    private final Setting<Integer> delay;
    private final Setting<Integer> blocksPerPlace;
    private final Setting<Boolean> packet;
    private final Setting<Boolean> disable;
    private final Setting<Boolean> rotate;
    private final Setting<Boolean> raytrace;
    private final Setting<Boolean> lowerbody;
    private final Setting<Boolean> upperBody;
    private final Timer timer;
    public EntityPlayer target;
    private boolean didPlace;
    private boolean switchedItem;
    private boolean isSneaking;
    private int lastHotbarSlot;
    private int placements;
    private boolean smartRotate;
    private BlockPos startPos;
    
    public AutoWeb() {
        super("AutoWeb", "Traps other players in webs", Category.COMBAT, true, false, false);
        this.delay = (Setting<Integer>)this.register(new Setting("Delay", (T)50, (T)0, (T)250));
        this.blocksPerPlace = (Setting<Integer>)this.register(new Setting("BlocksPerTick", (T)8, (T)1, (T)30));
        this.packet = (Setting<Boolean>)this.register(new Setting("Packet", (T)false));
        this.disable = (Setting<Boolean>)this.register(new Setting("AutoDisable", (T)false));
        this.rotate = (Setting<Boolean>)this.register(new Setting("Rotate", (T)true));
        this.raytrace = (Setting<Boolean>)this.register(new Setting("Raytrace", (T)false));
        this.lowerbody = (Setting<Boolean>)this.register(new Setting("Feet", (T)true));
        this.upperBody = (Setting<Boolean>)this.register(new Setting("Face", (T)false));
        this.timer = new Timer();
        this.didPlace = false;
        this.placements = 0;
        this.smartRotate = false;
        this.startPos = null;
    }
    
    @Override
    public void onEnable() {
        if (fullNullCheck()) {
            return;
        }
        this.startPos = EntityUtil.getRoundedBlockPos((Entity)AutoWeb.mc.player);
        this.lastHotbarSlot = AutoWeb.mc.player.inventory.currentItem;
    }
    
    @Override
    public void onTick() {
        this.smartRotate = false;
        this.doTrap();
    }
    
    @Override
    public String getDisplayInfo() {
        if (this.target != null) {
            return this.target.getName();
        }
        return null;
    }
    
    @Override
    public void onDisable() {
        AutoWeb.isPlacing = false;
        this.isSneaking = EntityUtil.stopSneaking(this.isSneaking);
        this.switchItem(true);
    }
    
    private void doTrap() {
        if (this.check()) {
            return;
        }
        this.doWebTrap();
        if (this.didPlace) {
            this.timer.reset();
        }
    }
    
    private void doWebTrap() {
        final List<Vec3d> placeTargets = this.getPlacements();
        this.placeList(placeTargets);
    }
    
    private List<Vec3d> getPlacements() {
        final ArrayList<Vec3d> list = new ArrayList<Vec3d>();
        final Vec3d baseVec = this.target.getPositionVector();
        if (this.lowerbody.getValue()) {
            list.add(baseVec);
        }
        if (this.upperBody.getValue()) {
            list.add(baseVec.add(0.0, 1.0, 0.0));
        }
        return list;
    }
    
    private void placeList(final List<Vec3d> list) {
        list.sort((vec3d, vec3d2) -> Double.compare(AutoWeb.mc.player.getDistanceSq(vec3d2.x, vec3d2.y, vec3d2.z), AutoWeb.mc.player.getDistanceSq(vec3d.x, vec3d.y, vec3d.z)));
        list.sort(Comparator.comparingDouble(vec3d -> vec3d.y));
        for (final Vec3d vec3d3 : list) {
            final BlockPos position = new BlockPos(vec3d3);
            final int placeability = BlockUtil.isPositionPlaceable(position, this.raytrace.getValue());
            if (placeability != 3 && placeability != 1) {
                continue;
            }
            this.placeBlock(position);
        }
    }
    
    private boolean check() {
        AutoWeb.isPlacing = false;
        this.didPlace = false;
        this.placements = 0;
        final int obbySlot = InventoryUtil.findHotbarBlock(BlockWeb.class);
        if (this.isOff()) {
            return true;
        }
        if (this.disable.getValue() && !this.startPos.equals((Object)EntityUtil.getRoundedBlockPos((Entity)AutoWeb.mc.player))) {
            this.disable();
            return true;
        }
        if (obbySlot == -1) {
            Command.sendMessage("<" + this.getDisplayName() + "> " + ChatFormatting.RED + "No Webs in hotbar disabling...");
            this.toggle();
            return true;
        }
        if (AutoWeb.mc.player.inventory.currentItem != this.lastHotbarSlot && AutoWeb.mc.player.inventory.currentItem != obbySlot) {
            this.lastHotbarSlot = AutoWeb.mc.player.inventory.currentItem;
        }
        this.switchItem(true);
        this.isSneaking = EntityUtil.stopSneaking(this.isSneaking);
        this.target = this.getTarget(10.0);
        return this.target == null || !this.timer.passedMs(this.delay.getValue());
    }
    
    private EntityPlayer getTarget(final double range) {
        EntityPlayer target = null;
        double distance = Math.pow(range, 2.0) + 1.0;
        for (final EntityPlayer player : AutoWeb.mc.world.playerEntities) {
            if (!EntityUtil.isntValid((Entity)player, range) && !player.isInWeb) {
                if (McDonalds.speedManager.getPlayerSpeed(player) > 30.0) {
                    continue;
                }
                if (target == null) {
                    target = player;
                    distance = AutoWeb.mc.player.getDistanceSq((Entity)player);
                }
                else {
                    if (AutoWeb.mc.player.getDistanceSq((Entity)player) >= distance) {
                        continue;
                    }
                    target = player;
                    distance = AutoWeb.mc.player.getDistanceSq((Entity)player);
                }
            }
        }
        return target;
    }
    
    private void placeBlock(final BlockPos pos) {
        if (this.placements < this.blocksPerPlace.getValue() && AutoWeb.mc.player.getDistanceSq(pos) <= MathUtil.square(6.0) && this.switchItem(false)) {
            AutoWeb.isPlacing = true;
            this.isSneaking = (this.smartRotate ? BlockUtil.placeBlockSmartRotate(pos, EnumHand.MAIN_HAND, true, this.packet.getValue(), this.isSneaking) : BlockUtil.placeBlock(pos, EnumHand.MAIN_HAND, this.rotate.getValue(), this.packet.getValue(), this.isSneaking));
            this.didPlace = true;
            ++this.placements;
        }
    }
    
    private boolean switchItem(final boolean back) {
        final boolean[] value = InventoryUtil.switchItem(back, this.lastHotbarSlot, this.switchedItem, InventoryUtil.Switch.NORMAL, BlockWeb.class);
        this.switchedItem = value[0];
        return value[1];
    }
    
    static {
        AutoWeb.isPlacing = false;
    }
}
