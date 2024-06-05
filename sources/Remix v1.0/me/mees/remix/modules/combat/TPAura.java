package me.mees.remix.modules.combat;

import me.satisfactory.base.utils.timer.*;
import me.mees.remix.pathfinder.*;
import net.minecraft.pathfinding.*;
import me.satisfactory.base.module.*;
import net.minecraft.world.pathfinder.*;
import net.minecraft.entity.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.player.*;
import me.satisfactory.base.relations.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import java.util.*;
import net.minecraft.network.*;
import net.minecraft.world.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import me.satisfactory.base.utils.aura.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import pw.stamina.causam.scan.method.model.*;
import net.minecraft.network.play.client.*;
import net.minecraft.enchantment.*;
import net.minecraft.potion.*;
import me.satisfactory.base.*;
import me.satisfactory.base.events.*;

public class TPAura extends Module
{
    private static final Random rand;
    TimerUtil timerInf;
    private List<Vec3> positions;
    private List<Vec3> positionsBack;
    private List<Node> triedPaths;
    private EntityLivingBase en;
    private boolean HasTPED;
    private PathFinder pathFinder;
    private TimerUtil timer;
    
    public TPAura() {
        super("TPAura", 0, Category.COMBAT);
        this.timerInf = new TimerUtil();
        this.positions = new ArrayList<Vec3>();
        this.positionsBack = new ArrayList<Vec3>();
        this.triedPaths = new ArrayList<Node>();
        this.en = null;
        this.HasTPED = false;
        this.pathFinder = new PathFinder(new WalkNodeProcessor());
        this.timer = new TimerUtil();
    }
    
    public boolean qualifies(final Entity e2) {
        return !(e2 instanceof EntityPlayerSP) && !(e2 instanceof EntityVillager) && e2 instanceof EntityLivingBase && (!e2.isInvisible() || this.getSettingByModule(this, "Invisibles").booleanValue()) && !e2.isDead && e2 != TPAura.mc.thePlayer && (!(e2 instanceof EntityPlayer) || !FriendManager.isFriend(e2.getName())) && (!(e2 instanceof EntityPlayer) || !FriendManager.isFriend(e2.getName())) && (!this.getSettingByModule(this, "Teams").booleanValue() || !TPAura.mc.thePlayer.isOnSameTeam((EntityLivingBase)e2)) && ((e2 instanceof EntityPlayer && this.getSettingByModule(this, "Players").booleanValue()) || ((e2 instanceof EntityMob || e2 instanceof EntitySlime || e2 instanceof EntityGhast) && this.getSettingByModule(this, "Mobs").booleanValue()) || ((e2 instanceof EntityAnimal || e2 instanceof EntitySquid || e2 instanceof EntityBat) && this.getSettingByModule(this, "Animals").booleanValue()));
    }
    
    public EntityLivingBase getClosestEntity(final float range) {
        EntityLivingBase closestEntity = null;
        float mindistance = range;
        for (final Object o : TPAura.mc.theWorld.loadedEntityList) {
            if (this.isNotItem(o) && !(o instanceof EntityPlayerSP)) {
                final EntityLivingBase en = (EntityLivingBase)o;
                if (!this.qualifies(en)) {
                    continue;
                }
                if (TPAura.mc.thePlayer.getDistanceToEntity(en) >= mindistance) {
                    continue;
                }
                mindistance = TPAura.mc.thePlayer.getDistanceToEntity(en);
                closestEntity = en;
            }
        }
        return closestEntity;
    }
    
    public boolean isNotItem(final Object o) {
        return o instanceof EntityLivingBase;
    }
    
    public boolean validEntity(final EntityLivingBase en) {
        return !en.isEntityEqual(TPAura.mc.thePlayer) && en instanceof EntityPlayer;
    }
    
    public void stopBlock() {
        if (TPAura.mc.thePlayer.isBlocking()) {
            TPAura.mc.thePlayer.sendQueue.sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.UP));
        }
    }
    
    public void startBlock() {
        if (TPAura.mc.thePlayer.isBlocking()) {
            TPAura.mc.playerController.sendUseItem(TPAura.mc.thePlayer, TPAura.mc.theWorld, TPAura.mc.thePlayer.getHeldItem());
            TPAura.mc.thePlayer.setItemInUse(TPAura.mc.thePlayer.getHeldItem(), TPAura.mc.thePlayer.getHeldItem().getMaxItemUseDuration());
            this.stopBlock();
        }
    }
    
    public void putVertex3d(final Vec3 vec) {
        GL11.glVertex3d(vec.xCoord, vec.yCoord, vec.zCoord);
    }
    
    public void drawOutlinedEntityESP(final double x, final double y, final double z, final double width, final double height, final float red, final float green, final float blue, final float alpha) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glLineWidth(1.0f);
        GL11.glColor4f(red, green, blue, alpha);
        this.drawOutlinedBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public void drawOutlinedBoundingBox(final AxisAlignedBB aa) {
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.startDrawing(3);
        worldRenderer.addVertex(aa.minX, aa.minY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.minY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.minY, aa.maxZ);
        worldRenderer.addVertex(aa.minX, aa.minY, aa.maxZ);
        worldRenderer.addVertex(aa.minX, aa.minY, aa.minZ);
        tessellator.draw();
        worldRenderer.startDrawing(3);
        worldRenderer.addVertex(aa.minX, aa.maxY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.maxY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        worldRenderer.addVertex(aa.minX, aa.maxY, aa.maxZ);
        worldRenderer.addVertex(aa.minX, aa.maxY, aa.minZ);
        tessellator.draw();
        worldRenderer.startDrawing(1);
        worldRenderer.addVertex(aa.minX, aa.minY, aa.minZ);
        worldRenderer.addVertex(aa.minX, aa.maxY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.minY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.maxY, aa.minZ);
        worldRenderer.addVertex(aa.maxX, aa.minY, aa.maxZ);
        worldRenderer.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        worldRenderer.addVertex(aa.minX, aa.minY, aa.maxZ);
        worldRenderer.addVertex(aa.minX, aa.maxY, aa.maxZ);
        tessellator.draw();
    }
    
    public Vec3 getRenderPos(double x, double y, double z) {
        x -= TPAura.mc.getRenderManager().renderPosX;
        y -= TPAura.mc.getRenderManager().renderPosY;
        z -= TPAura.mc.getRenderManager().renderPosZ;
        return new Vec3(x, y, z);
    }
    
    @Override
    public void onEnable() {
        AuraUtils.targets.clear();
        this.positions.clear();
        this.positionsBack.clear();
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        AuraUtils.targets.clear();
        this.positions.clear();
        this.positionsBack.clear();
        super.onDisable();
    }
    
    @Subscriber
    public void onUpdate(final EventPlayerUpdate event) {
        if (this.timer.hasTimeElapsed(2500.0, true)) {
            this.timer.reset();
        }
        AuraUtils.targets.clear();
        this.en = this.getClosestEntity(200.0f);
        if (this.en == null) {
            return;
        }
        if (!AuraUtils.hasEntity(this.en)) {
            AuraUtils.targets.add(this.en);
        }
        if (!this.timerInf.hasTimeElapsed(333.0, true)) {
            final Timer timer = TPAura.mc.timer;
            Timer.timerSpeed = 1.0f;
            return;
        }
        final Timer timer2 = TPAura.mc.timer;
        Timer.timerSpeed = 1.0f;
        for (final Vec3 vec : this.positions) {
            this.HasTPED = true;
            TPAura.mc.playerController.onPlayerRightClick(TPAura.mc.thePlayer, TPAura.mc.theWorld, null, new BlockPos(TPAura.mc.thePlayer.posX, TPAura.mc.thePlayer.posY - 1.0, TPAura.mc.thePlayer.posZ), EnumFacing.UP, new Vec3(TPAura.mc.thePlayer.posX, TPAura.mc.thePlayer.posY - 1.0, TPAura.mc.thePlayer.posZ));
            TPAura.mc.thePlayer.sendQueue.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(vec.xCoord, vec.yCoord, vec.zCoord, TPAura.mc.thePlayer.rotationYaw, TPAura.mc.thePlayer.rotationPitch, true));
        }
        if (this.HasTPED) {
            this.attack(this.en);
            this.HasTPED = false;
        }
        for (final Vec3 vec : this.positionsBack) {
            TPAura.mc.playerController.onPlayerRightClick(TPAura.mc.thePlayer, TPAura.mc.theWorld, null, new BlockPos(TPAura.mc.thePlayer.posX, TPAura.mc.thePlayer.posY - 1.0, TPAura.mc.thePlayer.posZ), EnumFacing.UP, new Vec3(TPAura.mc.thePlayer.posX, TPAura.mc.thePlayer.posY - 1.0, TPAura.mc.thePlayer.posZ));
            TPAura.mc.thePlayer.sendQueue.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(vec.xCoord, vec.yCoord, vec.zCoord, TPAura.mc.thePlayer.rotationYaw, TPAura.mc.thePlayer.rotationPitch, true));
        }
        this.updateStages();
    }
    
    public void updateStages() {
        if (AuraUtils.hasEntity(this.en)) {
            final me.mees.remix.pathfinder.NodeProcessor processor = new me.mees.remix.pathfinder.NodeProcessor();
            this.positions.clear();
            this.positionsBack.clear();
            processor.getPath(new BlockPos(TPAura.mc.thePlayer.posX, TPAura.mc.thePlayer.posY, TPAura.mc.thePlayer.posZ), new BlockPos(this.en.posX, this.en.posY, this.en.posZ));
            this.triedPaths = processor.triedPaths;
            for (final Node node : processor.path) {
                final BlockPos pos = node.getBlockpos();
                this.positions.add(new Vec3(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5));
                AuraUtils.targets.clear();
            }
            for (int i = this.positions.size() - 1; i > -1; --i) {
                this.positionsBack.add(this.positions.get(i));
            }
        }
    }
    
    private void attack(final EntityLivingBase en) {
        this.stopBlock();
        TPAura.mc.thePlayer.swingItem();
        TPAura.mc.thePlayer.sendQueue.sendPacketNoEvent(new C02PacketUseEntity(en, C02PacketUseEntity.Action.ATTACK));
        this.startBlock();
        final float sharpLevel = EnchantmentHelper.func_152377_a(TPAura.mc.thePlayer.getHeldItem(), en.getCreatureAttribute());
        final boolean vanillaCrit = TPAura.mc.thePlayer.fallDistance > 0.0f && !TPAura.mc.thePlayer.onGround && !TPAura.mc.thePlayer.isOnLadder() && !TPAura.mc.thePlayer.isInWater() && !TPAura.mc.thePlayer.isPotionActive(Potion.blindness) && TPAura.mc.thePlayer.ridingEntity == null;
        if (Base.INSTANCE.getModuleManager().getModByName("Criticals").isEnabled() || vanillaCrit) {
            TPAura.mc.thePlayer.onCriticalHit(en);
        }
        if (sharpLevel > 0.0f) {
            TPAura.mc.thePlayer.onEnchantmentCritical(en);
        }
    }
    
    @Subscriber
    public void event2DRender(final Event2DRender event2DRender) {
    }
    
    public void drawESP(final float red, final float green, final float blue, final float alpha, final double x, final double y, final double z) {
        final double xPos = x - TPAura.mc.getRenderManager().renderPosX;
        final double yPos = y - TPAura.mc.getRenderManager().renderPosY;
        final double zPos = z - TPAura.mc.getRenderManager().renderPosZ;
        this.drawOutlinedEntityESP(xPos, yPos, zPos, TPAura.mc.thePlayer.width / 2.0f, TPAura.mc.thePlayer.height, red, green, blue, alpha);
    }
    
    static {
        rand = new Random();
    }
}
