 package me.vene.skilled.modules.mods.combat;
 
 import java.lang.reflect.Field;
 import java.util.ArrayList;
 import me.vene.skilled.modules.Category;
 import me.vene.skilled.modules.Module;
 import me.vene.skilled.utilities.MathUtil;
 import me.vene.skilled.utilities.MouseUtil;
 import me.vene.skilled.utilities.ReflectionUtil;
 import me.vene.skilled.utilities.StringRegistry;
 import me.vene.skilled.values.BooleanValue;
 import me.vene.skilled.values.NumberValue;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.entity.EntityPlayerSP;
 import net.minecraft.client.multiplayer.PlayerControllerMP;
 import net.minecraft.client.settings.KeyBinding;
 import net.minecraft.entity.Entity;
 import net.minecraft.entity.EntityLivingBase;
 import net.minecraft.entity.player.EntityPlayer;
 import net.minecraft.item.ItemSword;
 import net.minecraft.network.Packet;
 import net.minecraft.network.play.client.C02PacketUseEntity;
 import net.minecraft.network.play.client.C03PacketPlayer;
 import net.minecraft.util.MathHelper;
 import net.minecraftforge.fml.common.gameevent.TickEvent;

public class KillAura extends Module
{
    private boolean Blocking;
    private Minecraft mc;
    private BooleanValue mobs;
    private BooleanValue silent;
    private BooleanValue antiBot;
    private BooleanValue teams;
    private BooleanValue autoBlock;
    private Field damnfield;
    private NumberValue reachValue;
    private NumberValue minAps;
    private NumberValue maxAps;
    private ArrayList<EntityLivingBase> entityList;
    protected long lastMS;
    
    public KillAura() {
        super(StringRegistry.register("KillAura"), 0, Category.C);
        this.mc = Minecraft.getMinecraft();
        this.mobs = new BooleanValue("Mobs", false);
        this.silent = new BooleanValue("Silent", true);
        this.antiBot = new BooleanValue("Antibot", true);
        this.teams = new BooleanValue("Teams", true);
        this.autoBlock = new BooleanValue("Autoblock", false);
        this.reachValue = new NumberValue("Reach", 4.25, 3.0, 6.0);
        this.minAps = new NumberValue("Min APS", 7.0, 1.0, 20.0);
        this.maxAps = new NumberValue("Max APS", 14.0, 1.0, 20.0);
        this.entityList = new ArrayList<EntityLivingBase>();
        try {
            final String fieldshit = new String(new char[] { 'f', 'i', 'e', 'l', 'd', '_', '7', '8', '7', '7', '8', '_', 'j' });
            this.damnfield = PlayerControllerMP.class.getDeclaredField(StringRegistry.register(fieldshit));
        }
        catch (NoSuchFieldException ex) {}
        this.addValue(this.reachValue);
        this.addValue(this.maxAps);
        this.addValue(this.minAps);
        this.addOption(this.silent);
        this.addOption(this.autoBlock);
        this.addOption(this.mobs);
        this.addOption(this.teams);
    }
    
    public boolean isOnSameTeam(final EntityLivingBase entity) {
        if (entity.getTeam() != null && this.mc.thePlayer.getTeam() != null) {
            final char c1 = entity.getDisplayName().getFormattedText().charAt(1);
            final char c2 = this.mc.thePlayer.getDisplayName().getFormattedText().charAt(1);
            return c1 == c2;
        }
        return false;
    }
    
    @Override
    public void onEnable() {
        this.updatebefore();
    }
    
    @Override
    public void onDisable() {
        if (this.Blocking && this.autoBlock.getState()) {
            final int useKeyBind = this.mc.gameSettings.keyBindUseItem.getKeyCode();
            KeyBinding.setKeyBindState(useKeyBind, false);
            MouseUtil.sendClick(1, false);
            this.Blocking = false;
        }
    }
    
    private boolean hasTimePassedMS(final long MS) {
        return this.getCurrentMS() >= this.lastMS + MS;
    }
    
    private long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }
    
    private void updatebefore() {
        this.lastMS = this.getCurrentMS();
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        this.entityList.clear();
        if (this.mc.theWorld == null || this.mc.thePlayer == null) {
            return;
        }
        for (final Object object : this.mc.theWorld.loadedEntityList) {
            if (!(object instanceof EntityLivingBase) && this.mobs.getState()) {
                continue;
            }
            if (!(object instanceof EntityPlayer) && !this.mobs.getState()) {
                continue;
            }
            if (((EntityLivingBase)object).getDistanceToEntity((Entity)this.mc.thePlayer) > this.reachValue.getValue()) {
                continue;
            }
            if (object == this.mc.thePlayer || ((EntityLivingBase)object).isDead || ((EntityLivingBase)object).getHealth() <= 0.0f) {
                continue;
            }
            if (((EntityLivingBase)object).isInvisible()) {
                continue;
            }
            if (this.isOnSameTeam((EntityLivingBase)object) && this.teams.getState()) {
                continue;
            }
            this.entityList.add((EntityLivingBase)object);
        }
        if (!this.entityList.isEmpty()) {
            final EntityLivingBase target = this.entityList.get(0);
            if (this.autoBlock.getState() && this.mc.thePlayer.getCurrentEquippedItem() != null && this.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) {
                this.Blocking = true;
            }
            if (this.silent.getState()) {
                this.mc.getNetHandler().addToSendQueue((Packet)new C03PacketPlayer.C05PacketPlayerLook((float)(this.getRotationsNeeded((Entity)target)[0] + MathUtil.random.nextFloat() * 0.95 + 0.05), (float)(this.getRotationsNeeded((Entity)target)[1] + MathUtil.random.nextFloat() * 0.95 + 0.05), true));
            }
            else {
                this.mc.thePlayer.rotationYaw = (float)(this.getRotationsNeeded((Entity)target)[0] + MathUtil.random.nextFloat() * 0.95 + 0.05);
                this.mc.thePlayer.rotationPitch = (float)(this.getRotationsNeeded((Entity)target)[1] + MathUtil.random.nextFloat() * 0.95 + 0.05);
            }
            final double currentAps = MathUtil.random.nextFloat() * (this.maxAps.getValue() - this.minAps.getValue()) + this.minAps.getValue();
            if (this.hasTimePassedMS((int)Math.round(1000.0 / currentAps))) {
                this.mc.thePlayer.swingItem();
                this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C02PacketUseEntity((Entity)target, C02PacketUseEntity.Action.ATTACK));
                this.updatebefore();
            }
        }
        else {
            this.Blocking = false;
        }
        this.checkBlock();
    }
    
    private void checkBlock() {
        if (this.Blocking) {
            final int useKeyBind = this.mc.gameSettings.keyBindUseItem.getKeyCode();
            KeyBinding.setKeyBindState(useKeyBind, true);
            KeyBinding.onTick(useKeyBind);
            MouseUtil.sendClick(1, true);
        }
        else {
            final int useKeyBind = this.mc.gameSettings.keyBindUseItem.getKeyCode();
            KeyBinding.setKeyBindState(useKeyBind, false);
            MouseUtil.sendClick(1, false);
        }
    }
    
    private float[] getRotationsNeeded(final Entity entity) {
        if (entity == null) {
            return null;
        }
        final double diffX = entity.posX - this.mc.thePlayer.posX;
        final double diffZ = entity.posZ - this.mc.thePlayer.posZ;
        double diffY;
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
            diffY = entityLivingBase.posY + entityLivingBase.getEyeHeight() * 0.6 - (this.mc.thePlayer.posY + this.mc.thePlayer.getEyeHeight());
        }
        else {
            diffY = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0 - (this.mc.thePlayer.posY + this.mc.thePlayer.getEyeHeight());
        }
        final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / 3.141592653589793));
        return new float[] { this.mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - this.mc.thePlayer.rotationYaw), this.mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - this.mc.thePlayer.rotationPitch) };
    }
    
    private boolean breakingBlock(final EntityPlayer player) {
        if (player instanceof EntityPlayerSP) {
            final PlayerControllerMP controller = Minecraft.getMinecraft().playerController;
            final String fieldmeme = new String(new char[] { 'f', 'i', 'e', 'l', 'd', '_', '7', '8', '7', '7', '8', '_', 'j' });
            final boolean hasBlock = Boolean.valueOf(ReflectionUtil.getFieldValue(StringRegistry.register(fieldmeme), controller, PlayerControllerMP.class).toString());
            try {
                this.damnfield.setAccessible(true);
                this.damnfield.getBoolean(controller);
                this.damnfield.setAccessible(false);
            }
            catch (Exception ex) {}
            if (hasBlock) {
                return false;
            }
        }
        return true;
    }
}