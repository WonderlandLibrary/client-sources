package cn.Hanabi.modules.Combat;

import cn.Hanabi.value.*;
import net.minecraft.entity.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import cn.Hanabi.*;
import net.minecraft.client.renderer.*;
import cn.Hanabi.utils.fontmanager.*;
import net.minecraft.network.play.server.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import cn.Hanabi.injection.interfaces.*;
import cn.Hanabi.events.*;
import com.darkmagician6.eventapi.*;
import com.darkmagician6.eventapi.events.*;
import net.minecraft.network.play.client.*;
import cn.Hanabi.modules.*;
import java.util.*;
import cn.Hanabi.modules.World.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.*;
import cn.Hanabi.modules.Player.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import javax.vecmath.*;
import net.minecraft.util.*;
import ClassSub.*;

public class KillAura_ extends Mod
{
    public Value<Boolean> aac;
    public Value<Boolean> targetHUD;
    public Value<Boolean> esp;
    public Value<Boolean> autoblock;
    public Value<Boolean> attackPlayers;
    public Value<Boolean> attackAnimals;
    public Value<Boolean> attackMobs;
    public Value<Boolean> throughblock;
    public Value<Boolean> invisible;
    public Value<Double> range;
    public Value<Double> blockRange;
    public Value<Double> cps;
    public Value<Double> maxTarget;
    public Value<Double> switchDelay;
    public Value<Double> switchHurttime;
    public Value<Double> turnspeed;
    public static EntityLivingBase attackingEntity;
    public int targetIndex;
    public Class205 attackTimer;
    public Class205 switchTimer;
    public float[] lastRotations;
    public boolean isBlocking;
    public static ArrayList<EntityLivingBase> attackingEntityList;
    public ArrayList<EntityLivingBase> attackedEntity;
    
    
    public KillAura_() {
        super("KillAura", Category.COMBAT);
        this.aac = new Value<Boolean>("KillAura_AAC", false);
        this.targetHUD = new Value<Boolean>("KillAura_ShowTarget", true);
        this.esp = new Value<Boolean>("KillAura_ESP", true);
        this.autoblock = new Value<Boolean>("KillAura_AutoBlock", true);
        this.attackPlayers = new Value<Boolean>("KillAura_Players", true);
        this.attackAnimals = new Value<Boolean>("KillAura_Animals", false);
        this.attackMobs = new Value<Boolean>("KillAura_Mobs", false);
        this.throughblock = new Value<Boolean>("KillAura_ThroughBlock", true);
        this.invisible = new Value<Boolean>("KillAura_Invisibles", false);
        this.range = new Value<Double>("KillAura_Range", 4.2, 3.0, 6.0, 0.1);
        this.blockRange = new Value<Double>("KillAura_BlockRange", 0.5, 0.0, 3.0, 0.1);
        this.cps = new Value<Double>("KillAura_CPS", 10.0, 1.0, 20.0, 0.1);
        this.maxTarget = new Value<Double>("KillAura_MaxTarget", 3.0, 1.0, 8.0, 1.0);
        this.switchDelay = new Value<Double>("KillAura_SwitchDelay", 350.0, 10.0, 1000.0, 10.0);
        this.switchHurttime = new Value<Double>("KillAura_SwitchHurttime", 10.0, 1.0, 10.0, 1.0);
        this.turnspeed = new Value<Double>("KillAura_TurnHeadSpeed", 90.0, 60.0, 120.0, 1.0);
        this.attackTimer = new Class205();
        this.switchTimer = new Class205();
        this.isBlocking = false;
        this.attackedEntity = new ArrayList<EntityLivingBase>();
    }
    
    @Override
    protected void onEnable() {
        KillAura_.attackingEntity = null;
        this.targetIndex = 0;
        this.isBlocking = false;
        this.lastRotations = new float[] { KillAura_.mc.thePlayer.rotationYaw, KillAura_.mc.thePlayer.rotationPitch };
        this.attackedEntity = new ArrayList<EntityLivingBase>();
        super.onEnable();
    }
    
    @Override
    protected void onDisable() {
        this.unBlock();
        super.onDisable();
    }
    
    @EventTarget
    public void targetHud(final EventRender2D eventRender2D) {
        if (this.targetHUD.getValueState() && this.isValidEntity((Entity)KillAura_.attackingEntity)) {
            final ScaledResolution scaledResolution = new ScaledResolution(KillAura_.mc);
            if (KillAura_.attackingEntity != null) {
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                final UnicodeFontRenderer wqy18 = Hanabi.INSTANCE.fontManager.wqy18;
                wqy18.drawStringWithShadow(KillAura_.attackingEntity.getName(), scaledResolution.getScaledWidth() / 2.0f - wqy18.getStringWidth(KillAura_.attackingEntity.getName()) / 2.0f, scaledResolution.getScaledHeight() / 2.0f - 33.0f, 16777215);
                RenderHelper.enableGUIStandardItemLighting();
                KillAura_.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/icons.png"));
                GL11.glDisable(2929);
                GL11.glEnable(3042);
                GL11.glDepthMask(false);
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                for (int n = 0; n < KillAura_.attackingEntity.getMaxHealth() / 2.0f; ++n) {
                    KillAura_.mc.ingameGUI.drawTexturedModalRect(scaledResolution.getScaledWidth() / 2 - KillAura_.attackingEntity.getMaxHealth() / 2.0f * 10.0f / 2.0f + n * 10, (float)(scaledResolution.getScaledHeight() / 2 - 20), 16, 0, 9, 9);
                }
                for (int n2 = 0; n2 < KillAura_.attackingEntity.getHealth() / 2.0f; ++n2) {
                    KillAura_.mc.ingameGUI.drawTexturedModalRect(scaledResolution.getScaledWidth() / 2 - KillAura_.attackingEntity.getMaxHealth() / 2.0f * 10.0f / 2.0f + n2 * 10, (float)(scaledResolution.getScaledHeight() / 2 - 20), 52, 0, 9, 9);
                }
                GL11.glDepthMask(true);
                GL11.glDisable(3042);
                GL11.glEnable(2929);
                GlStateManager.disableBlend();
                GlStateManager.color(1.0f, 1.0f, 1.0f);
                RenderHelper.disableStandardItemLighting();
            }
        }
    }
    
    @EventTarget
    public void onRender(final EventRender eventRender) {
        if (this.esp.getValueState()) {
            for (final EntityLivingBase entityLivingBase : KillAura_.attackingEntityList) {
                KillAura_.mc.getRenderManager();
                final double n = entityLivingBase.lastTickPosX + (entityLivingBase.posX - entityLivingBase.lastTickPosX) * Class211.getTimer().renderPartialTicks - ((IRenderManager)KillAura_.mc.getRenderManager()).getRenderPosX();
                KillAura_.mc.getRenderManager();
                final double n2 = entityLivingBase.lastTickPosY + (entityLivingBase.posY - entityLivingBase.lastTickPosY) * Class211.getTimer().renderPartialTicks - ((IRenderManager)KillAura_.mc.getRenderManager()).getRenderPosY();
                KillAura_.mc.getRenderManager();
                Class246.drawEntityESP(n, n2, entityLivingBase.lastTickPosZ + (entityLivingBase.posZ - entityLivingBase.lastTickPosZ) * Class211.getTimer().renderPartialTicks - ((IRenderManager)KillAura_.mc.getRenderManager()).getRenderPosZ(), entityLivingBase.getEntityBoundingBox().maxX - entityLivingBase.getEntityBoundingBox().minX, entityLivingBase.getEntityBoundingBox().maxY - entityLivingBase.getEntityBoundingBox().minY + 0.25, (entityLivingBase.hurtTime > 1) ? 1.0f : 0.0f, (entityLivingBase.hurtTime > 1) ? 0.0f : 1.0f, 0.0f, 0.2f, (entityLivingBase.hurtTime > 1) ? 1.0f : 0.0f, (entityLivingBase.hurtTime > 1) ? 0.0f : 1.0f, 0.0f, 1.0f, 2.0f);
            }
        }
    }
    
    @EventTarget
    public void onPre(final EventPreMotion eventPreMotion) {
        while (this.canSwitch(this.getTarget())) {
            ++this.targetIndex;
        }
        KillAura_.attackingEntity = this.getTarget();
        if (KillAura_.attackingEntity != null) {
            if (this.autoblock.getValueState()) {
                this.doBlock();
            }
            final float[] array = getEntityRotations(KillAura_.attackingEntity, this.lastRotations, this.aac.getValueState(), (int)(Object)this.turnspeed.getValueState());
            this.lastRotations = new float[] { array[0], array[1] };
            eventPreMotion.setYaw(array[0]);
            eventPreMotion.setPitch(array[1]);
            KillAura_.mc.thePlayer.rotationYawHead = eventPreMotion.getYaw();
        }
        else {
            this.unBlock();
        }
        if (this.aac.getValueState()) {
            this.attackEntity();
        }
    }
    
    @EventTarget
    public void onPost(final EventPostMotion eventPostMotion) {
        if (!this.aac.getValueState()) {
            this.attackEntity();
        }
    }
    
    @EventTarget
    private void onPacket(final EventPacket eventPacket) {
        if (eventPacket.getPacket() instanceof S08PacketPlayerPosLook) {
            final IS08PacketPlayerPosLook is08PacketPlayerPosLook = (IS08PacketPlayerPosLook)eventPacket.getPacket();
            is08PacketPlayerPosLook.setYaw(KillAura_.mc.thePlayer.rotationYaw);
            is08PacketPlayerPosLook.setPitch(KillAura_.mc.thePlayer.rotationPitch);
        }
    }
    
    public void doBlock() {
        if (KillAura_.mc.thePlayer.isBlocking() || (KillAura_.mc.thePlayer.getHeldItem() != null && KillAura_.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword && !this.isBlocking)) {
            KillAura_.mc.getNetHandler().addToSendQueue((Packet)new C08PacketPlayerBlockPlacement(new BlockPos(0, 0, 0), 255, KillAura_.mc.thePlayer.inventory.getCurrentItem(), 0.0f, 0.0f, 0.0f));
            ((IEntityPlayer)KillAura_.mc.thePlayer).setItemInUseCount(KillAura_.mc.thePlayer.getHeldItem().getMaxItemUseDuration());
            this.isBlocking = true;
        }
    }
    
    public void unBlock() {
        if (((KillAura_.mc.thePlayer.getHeldItem() != null && KillAura_.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) || KillAura_.mc.thePlayer.isBlocking()) && this.isBlocking) {
            ((IEntityPlayer)KillAura_.mc.thePlayer).setItemInUseCount(0);
            KillAura_.mc.getNetHandler().addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            this.isBlocking = false;
        }
    }
    
    public void attackEntity() {
        if (KillAura_.attackingEntity != null && this.isValidEntity((Entity)KillAura_.attackingEntity) && KillAura_.mc.thePlayer.getDistanceToEntity((Entity)KillAura_.attackingEntity) < this.range.getValueState() && this.attackTimer.isDelayComplete(Double.valueOf(this.cpsToDelay(this.cps.getValueState())))) {
            if (this.autoblock.getValueState()) {
                this.unBlock();
            }
            KillAura_.mc.thePlayer.onCriticalHit((Entity)KillAura_.attackingEntity);
            KillAura_.mc.thePlayer.onEnchantmentCritical((Entity)KillAura_.attackingEntity);
            KillAura_.mc.thePlayer.swingItem();
            EventManager.call(new EventAttack((Entity)KillAura_.attackingEntity));
            KillAura_.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C02PacketUseEntity((Entity)KillAura_.attackingEntity, C02PacketUseEntity.Action.ATTACK));
            if (!this.attackedEntity.contains(KillAura_.attackingEntity)) {
                this.attackedEntity.add(KillAura_.attackingEntity);
            }
            if (this.autoblock.getValueState()) {
                this.doBlock();
            }
            this.attackTimer.reset();
        }
    }
    
    public boolean canSwitch(final EntityLivingBase entityLivingBase) {
        if (KillAura_.attackingEntityList.size() <= 1 || this.maxTarget.getValueState() <= 1.0) {
            return false;
        }
        if (entityLivingBase.hurtTime <= this.switchHurttime.getValueState() && this.switchTimer.isDelayComplete(this.switchDelay.getValueState()) && KillAura_.attackingEntityList.size() > this.targetIndex) {
            this.switchTimer.reset();
            return true;
        }
        return false;
    }
    
    public double cpsToDelay(final double n) {
        return 1000.0 / n;
    }
    
    public EntityLivingBase getTarget() {
        try {
            final ArrayList<EntityLivingBase> validEntityList = this.getValidEntityList();
            if (validEntityList.size() <= 0) {
                return null;
            }
            if (this.targetIndex > validEntityList.size() - 1) {
                this.targetIndex = 0;
            }
            return this.getValidEntityList().get(this.targetIndex);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public ArrayList<EntityLivingBase> getValidEntityList() {
        final ArrayList<EntityLivingBase> attackingEntityList = new ArrayList<EntityLivingBase>();
        final ArrayList<Entity> list = new ArrayList<Entity>(KillAura_.mc.theWorld.loadedEntityList);
        list.sort(KillAura_::lambda$getValidEntityList$0);
        for (final Entity entity : list) {
            if (this.isValidEntity(entity) && attackingEntityList.size() < this.maxTarget.getValueState()) {
                attackingEntityList.add((EntityLivingBase)entity);
            }
        }
        return KillAura_.attackingEntityList = attackingEntityList;
    }
    
    private boolean isValidEntity(final Entity entity) {
        if (entity != null && entity instanceof EntityLivingBase) {
            if (entity.isDead || ((EntityLivingBase)entity).getHealth() <= 0.0f) {
                if (ModManager.getModule("AutoL").isEnabled() && this.attackedEntity.contains(entity)) {
                    this.attackedEntity.remove(entity);
                    String s = "";
                    final int nextInt = new Random().nextInt(6);
                    if (nextInt == 0) {
                        s = "我四川广安白治军当场屠杀绿色玩家婊子亲妈";
                    }
                    else if (nextInt == 1) {
                        s = "我在四川广安，四川广安特产Power Client屠杀绿色玩家�?";
                    }
                    else if (nextInt == 2) {
                        s = "你好！我叫白治军四川特产Power�?绿色玩家亲妈昂�??";
                    }
                    else if (nextInt == 3) {
                        s = "对不起我叫白治军来自四川广安我今年当兵兵�?没过我只能上完初中回家辍学写端圈钱求求你们可怜可怜我�?";
                    }
                    else if (nextInt == 4) {
                        s = "电话预定购买四川广安特产Power客户端请拨打15397699681�?";
                    }
                    else if (nextInt == 5) {
                        s = "我叫白治军我在四川广安用邻水脐橙给你妈妈洗B呢购买Power拨打热线15397699681";
                    }
                    if (AutoL.wdr.getValueState() && !AutoL.wdred.contains(KillAura_.attackingEntity.getName())) {
                        AutoL.wdred.add(KillAura_.attackingEntity.getName());
                        KillAura_.mc.thePlayer.sendChatMessage("/wdr " + KillAura_.attackingEntity.getName() + " ka fly reach nokb jesus ac");
                    }
                    KillAura_.mc.thePlayer.sendChatMessage(AutoAbuse.prefix + entity.getName() + " L" + (AutoL.abuse.getValueState() ? (" " + s) : "") + (AutoL.ad.getValueState() ? " Buy Hanabi at mcheika.com" : ""));
                }
                return false;
            }
            if (KillAura_.mc.thePlayer.getDistanceToEntity(entity) < this.range.getValueState() + this.blockRange.getValueState() && entity != KillAura_.mc.thePlayer && !KillAura_.mc.thePlayer.isDead && !(entity instanceof EntityArmorStand) && !(entity instanceof EntitySnowman)) {
                if (entity instanceof EntityPlayer && this.attackPlayers.getValueState()) {
                    return entity.ticksExisted >= 30 && (KillAura_.mc.thePlayer.canEntityBeSeen(entity) || this.throughblock.getValueState()) && (!entity.isInvisible() || this.invisible.getValueState()) && !AntiBot.isBot(entity) && !Teams.isOnSameTeam(entity);
                }
                if (entity instanceof EntityMob && this.attackMobs.getValueState()) {
                    return !AntiBot.isBot(entity);
                }
                if ((entity instanceof EntityAnimal || entity instanceof EntityVillager) && this.attackAnimals.getValueState()) {
                    return !AntiBot.isBot(entity);
                }
            }
        }
        return false;
    }
    
    public static float[] getEntityRotations(final EntityLivingBase entityLivingBase, final float[] array, final boolean b, final int n) {
        final Class128 class128 = new Class128(b, n);
        final Class94 smoothAngle = class128.smoothAngle(class128.calculateAngle(new Vector3d(entityLivingBase.posX, entityLivingBase.posY + entityLivingBase.getEyeHeight(), entityLivingBase.posZ), new Vector3d(KillAura_.mc.thePlayer.posX, KillAura_.mc.thePlayer.posY + KillAura_.mc.thePlayer.getEyeHeight(), KillAura_.mc.thePlayer.posZ)), new Class94(array[0], array[1]));
        return new float[] { KillAura_.mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(smoothAngle.getYaw() - KillAura_.mc.thePlayer.rotationYaw), smoothAngle.getPitch() };
    }
    
    private static int lambda$getValidEntityList$0(final Entity entity, final Entity entity2) {
        return (int)(KillAura_.mc.thePlayer.rotationYaw - Class45.getRotations(entity)[0] - (KillAura_.mc.thePlayer.rotationYaw - Class45.getRotations(entity2)[0]));
    }
}
