// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Mod.Collection.Combat;

import net.minecraft.entity.player.EntityPlayer;
import java.util.Comparator;
import net.minecraft.client.network.badlion.Utils.ClientUtils;
import java.util.List;
import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.entity.RenderManager;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.network.badlion.Events.EventRender3D;
import net.minecraft.potion.Potion;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.badlion.Utils.EntityUtils;
import net.minecraft.Badlion;
import net.minecraft.client.network.badlion.Utils.RotationUtils;
import net.minecraft.item.ItemSword;
import net.minecraft.client.network.badlion.Utils.ModeUtils;
import net.minecraft.client.network.badlion.Events.EventState;
import net.minecraft.client.network.badlion.Events.EventUpdate;
import net.minecraft.client.network.badlion.memes.EventTarget;
import net.minecraft.client.network.badlion.Events.EventTick;
import net.minecraft.client.network.badlion.memes.EventManager;
import org.darkstorm.minecraft.gui.component.BoundedRangeComponent;
import net.minecraft.client.network.badlion.Mod.Category;
import net.minecraft.client.network.badlion.Utils.TimeMeme;
import net.minecraft.entity.EntityLivingBase;
import java.util.ArrayList;
import net.minecraft.client.network.badlion.Mod.NumValue;
import net.minecraft.client.network.badlion.Mod.Mod;

public class Aura extends Mod
{
    private boolean Tags;
    private boolean setupTick;
    private boolean crit;
    private boolean switchingTargets;
    private NumValue auraRange;
    private ArrayList<EntityLivingBase> targets;
    private int index;
    private TimeMeme timer;
    public int random;
    
    public Aura() {
        super("KillAura", Category.COMBAT);
        this.auraRange = new NumValue("Aura reach", 4.2, 1.0, 8.0, BoundedRangeComponent.ValueDisplay.DECIMAL);
    }
    
    @Override
    public void onEnable() {
        this.timer = new TimeMeme();
        this.crit = true;
        EventManager.register(this);
    }
    
    @Override
    public void onDisable() {
        EventManager.unregister(this);
    }
    
    @EventTarget
    public void onTick(final EventTick event) {
        this.setRenderName(String.format("%s", "KillAura§7 " + (Object)this.targets.size()));
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate event) {
        if (event.state == EventState.PRE) {
            this.targets = (ArrayList<EntityLivingBase>)(ArrayList)this.getTargets();
            if (this.index >= this.targets.size()) {
                this.index = 0;
            }
            if (this.targets.size() > 0) {
                final EntityLivingBase target = this.targets.get(this.index);
                if (target != null) {
                    if (ModeUtils.bHit && this.mc.thePlayer.inventory.getCurrentItem() != null && !this.mc.thePlayer.isBlocking() && this.mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword) {
                        this.mc.thePlayer.setItemInUse(this.mc.thePlayer.inventory.getCurrentItem(), this.mc.thePlayer.inventory.getCurrentItem().getMaxItemUseDuration());
                    }
                    else if (ModeUtils.bHit && this.mc.thePlayer.inventory.getCurrentItem() != null && !this.mc.thePlayer.isBlocking() && this.mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword && !this.mc.thePlayer.isOnSameTeam(target)) {
                        this.mc.thePlayer.setItemInUse(this.mc.thePlayer.inventory.getCurrentItem(), this.mc.thePlayer.inventory.getCurrentItem().getMaxItemUseDuration());
                    }
                    event.yaw = RotationUtils.getRotations(target)[0];
                    event.pitch = RotationUtils.getRotations(target)[1];
                }
                if (this.setupTick) {
                    if (this.targets.size() > 0 && this.mc.thePlayer.onGround) {
                        event.onGround = false;
                        event.alwaysSend = true;
                    }
                    if (this.timer.hasPassed(400.0)) {
                        this.incrementIndex();
                        this.switchingTargets = true;
                        this.timer.reset();
                    }
                }
            }
            this.setupTick = !this.setupTick;
        }
        if (event.state == EventState.POST) {
            final EntityLivingBase target = this.targets.get(this.index);
            if (!this.setupTick || this.targets.size() <= 0 || this.targets.get(this.index) == null || this.targets.size() <= 0) {
                return;
            }
            if (this.switchingTargets) {
                this.switchingTargets = false;
            }
            else {
                if (!this.mc.thePlayer.isOnSameTeam(target)) {
                    if (Badlion.getWinter().theMods.getMod(Criticals.class).isEnabled()) {
                        EntityUtils.attackEntity(target, true);
                    }
                    else if (!Badlion.getWinter().theMods.getMod(Criticals.class).isEnabled()) {
                        EntityUtils.attackEntity(target, false);
                    }
                    this.crit = !this.crit;
                }
                else if (!this.mc.thePlayer.isOnSameTeam(target)) {
                    if (Badlion.getWinter().theMods.getMod(Criticals.class).isEnabled()) {
                        EntityUtils.attackEntity(target, true);
                    }
                    else if (!Badlion.getWinter().theMods.getMod(Criticals.class).isEnabled()) {
                        EntityUtils.attackEntity(target, false);
                    }
                    this.crit = !this.crit;
                }
                final float sharpLevel = EnchantmentHelper.func_152377_a(Minecraft.getMinecraft().thePlayer.getHeldItem(), this.mc.thePlayer.getCreatureAttribute());
                final boolean b = Minecraft.getMinecraft().thePlayer.fallDistance > 0.0f && !Minecraft.getMinecraft().thePlayer.onGround && !Minecraft.getMinecraft().thePlayer.isOnLadder() && !Minecraft.getMinecraft().thePlayer.isInWater() && !Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.blindness) && Minecraft.getMinecraft().thePlayer.ridingEntity == null;
            }
        }
    }
    
    @EventTarget(1)
    public void onRender3D(final EventRender3D event) {
        for (final Object obj : this.mc.theWorld.loadedEntityList) {
            if (obj instanceof EntityLivingBase) {
                final EntityLivingBase entity = (EntityLivingBase)obj;
                final EntityLivingBase target = this.targets.get(this.index);
                GL11.glLoadIdentity();
                this.mc.entityRenderer.orientCamera(event.partialTicks);
                this.mc.getRenderManager();
                final double posX = entity.posX - RenderManager.renderPosX;
                this.mc.getRenderManager();
                final double posY = entity.posY - RenderManager.renderPosY;
                this.mc.getRenderManager();
                final double posZ = entity.posZ - RenderManager.renderPosZ;
                if (entity == this.mc.thePlayer) {
                    continue;
                }
                this.mc.thePlayer.getDistanceToEntity(entity);
                this.auraRange.getValue();
            }
        }
    }
    
    private void incrementIndex() {
        ++this.index;
        if (this.index >= this.targets.size()) {
            this.index = 0;
        }
    }
    
    private List<EntityLivingBase> getTargets() {
        final List<EntityLivingBase> targets = new ArrayList<EntityLivingBase>();
        for (final Entity entity : ClientUtils.loadedEntityList()) {
            if (entity instanceof EntityLivingBase && ModeUtils.isValidForAura(entity) && this.mc.thePlayer.getDistanceToEntity(entity) <= this.auraRange.getValue()) {
                targets.add((EntityLivingBase)entity);
            }
        }
        targets.sort(new Comparator() {
            public int compare(final EntityLivingBase target1, final EntityLivingBase target2) {
                return Math.round(target2.getHealth() - target1.getHealth());
            }
            
            @Override
            public int compare(final Object arg0, final Object arg1) {
                return 0;
            }
        });
        return targets;
    }
    
    private void swap(final int slot, final int hotbarNum) {
        ClientUtils.mc().playerController.windowClick(ClientUtils.mc().thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2, ClientUtils.mc().thePlayer);
    }
}
