// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.combat;

import java.util.Iterator;
import intent.AquaDev.aqua.utils.FriendSystem;
import net.minecraft.entity.Entity;
import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import java.util.Random;
import events.listeners.EventUpdate;
import intent.AquaDev.aqua.utils.RotationUtil;
import net.minecraft.block.BlockBed;
import events.listeners.EventPreMotion;
import events.listeners.EventSilentMove;
import events.Event;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import intent.AquaDev.aqua.utils.TimeUtil;
import intent.AquaDev.aqua.modules.Module;

public class BedAura extends Module
{
    public TimeUtil timeUtil;
    public static BlockPos pos;
    public static boolean rotating;
    public static EntityPlayer target;
    float[] rota;
    
    public BedAura() {
        super("BedAura", Type.Combat, "BedAura", 0, Category.Combat);
        this.timeUtil = new TimeUtil();
        this.rota = null;
        Aqua.setmgr.register(new Setting("Range", this, 6.0, 3.0, 6.0, false));
        Aqua.setmgr.register(new Setting("minCPS", this, 17.0, 1.0, 20.0, false));
        Aqua.setmgr.register(new Setting("maxCPS", this, 19.0, 1.0, 20.0, false));
        Aqua.setmgr.register(new Setting("CorrectMovement", this, false));
        Aqua.setmgr.register(new Setting("MoveFixMode", this, "Silent", new String[] { "Silent", "Normal" }));
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        BedAura.rotating = false;
        super.onDisable();
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventSilentMove && Aqua.setmgr.getSetting("BedAuraMoveFixMode").getCurrentMode().equalsIgnoreCase("Silent") && BedAura.rotating && Aqua.setmgr.getSetting("BedAuraCorrectMovement").isState()) {
            ((EventSilentMove)event).setSilent(true);
        }
        if (event instanceof EventPreMotion && BedAura.target == null) {
            for (int y = 3; y >= -3.2f; --y) {
                for (int x = -3; x <= 3.2f; ++x) {
                    for (int z = -3; z <= 3.2f; ++z) {
                        BedAura.rotating = false;
                    }
                }
            }
            for (int y = 3; y >= -3; --y) {
                for (int x = -3; x <= 3.0f; ++x) {
                    for (int z = -3; z <= 3.0f; ++z) {
                        final int posX = (int)(BedAura.mc.thePlayer.posX - 0.5 + x);
                        final int posZ = (int)(BedAura.mc.thePlayer.posZ - 0.5 + z);
                        final int posY = (int)(BedAura.mc.thePlayer.posY - 0.5 + y);
                        BedAura.pos = new BlockPos(posX, posY, posZ);
                        final Block block = BedAura.mc.theWorld.getBlockState(BedAura.pos).getBlock();
                        if (block instanceof BlockBed) {
                            BedAura.rotating = true;
                            this.rota = RotationUtil.lookAtPosBed(BedAura.pos.getX() + 0.5, BedAura.pos.getY() - 0.5, BedAura.pos.getZ() + 0.5);
                        }
                    }
                }
            }
            if (BedAura.rotating) {
                ((EventPreMotion)event).setPitch(RotationUtil.pitch);
                ((EventPreMotion)event).setYaw(RotationUtil.yaw);
                RotationUtil.setYaw(this.rota[0], 180.0f);
                RotationUtil.setPitch(this.rota[1], 8.0f);
            }
        }
        if (event instanceof EventUpdate) {
            BedAura.target = this.searchTargets();
            if (BedAura.target == null && !BedAura.mc.thePlayer.isSwingInProgress) {
                BedAura.mc.gameSettings.keyBindAttack.pressed = false;
            }
            if (BedAura.target != null) {
                Aqua.moduleManager.getModuleByName("Killaura").setState(true);
            }
            if (BedAura.target == null) {
                final float minCPS = (float)Aqua.setmgr.getSetting("BedAuraminCPS").getCurrentNumber();
                final float maxCPS = (float)Aqua.setmgr.getSetting("BedAuramaxCPS").getCurrentNumber();
                final float CPS = (float)MathHelper.getRandomDoubleInRange(new Random(), minCPS, maxCPS);
                final int radius = 3;
                for (int y2 = -3; y2 <= 3; ++y2) {
                    for (int x2 = -3; x2 <= 3; ++x2) {
                        for (int z2 = -3; z2 <= 3; ++z2) {
                            final BlockPos blockPos = new BlockPos(BedAura.mc.thePlayer.posX + x2, BedAura.mc.thePlayer.posY + y2, BedAura.mc.thePlayer.posZ + z2);
                            final Block block2 = BedAura.mc.theWorld.getBlockState(blockPos).getBlock();
                            if (block2 instanceof BlockBed) {
                                if (this.timeUtil.hasReached((long)(1000.0f / CPS))) {
                                    if (BedAura.rotating) {
                                        BedAura.mc.thePlayer.setSprinting(false);
                                        BedAura.mc.gameSettings.keyBindAttack.pressed = true;
                                    }
                                    else {
                                        BedAura.mc.gameSettings.keyBindAttack.pressed = false;
                                    }
                                    BedAura.mc.thePlayer.swingItem();
                                    BedAura.mc.clickMouse();
                                    this.timeUtil.reset();
                                }
                                Aqua.moduleManager.getModuleByName("Killaura").setState(false);
                            }
                        }
                    }
                }
            }
        }
    }
    
    public EntityPlayer searchTargets() {
        final float range = (float)Aqua.setmgr.getSetting("KillauraRange").getCurrentNumber();
        EntityPlayer player = null;
        double closestDist = 100000.0;
        for (final Entity o : BedAura.mc.theWorld.loadedEntityList) {
            if (!o.getName().equals(BedAura.mc.thePlayer.getName()) && o instanceof EntityPlayer && !FriendSystem.isFriend(o.getName()) && !Antibot.bots.contains(o) && !BedAura.mc.session.getUsername().equalsIgnoreCase("Administradora") && BedAura.mc.thePlayer.getDistanceToEntity(o) < range) {
                final double dist = BedAura.mc.thePlayer.getDistanceToEntity(o);
                if (dist >= closestDist) {
                    continue;
                }
                closestDist = dist;
                player = (EntityPlayer)o;
            }
        }
        return player;
    }
    
    static {
        BedAura.rotating = false;
        BedAura.target = null;
    }
}
