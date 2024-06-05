package net.shoreline.client.impl.module.render;

import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.BooleanConfig;
import net.shoreline.client.api.config.setting.EnumConfig;
import net.shoreline.client.api.config.setting.NumberConfig;
import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.impl.event.ScreenOpenEvent;
import net.shoreline.client.impl.event.entity.player.PlayerMoveEvent;
import net.shoreline.client.impl.event.network.DisconnectEvent;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.HungerManager;

import java.util.Collection;
import java.util.Map;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class FreecamModule extends ToggleModule
{
    //
    Config<Float> speedConfig = new NumberConfig<>("Speed", "The move speed " +
            "of the camera", 0.1f, 1.0f, 2.0f);
    Config<Interact> interactConfig = new EnumConfig<>("Interact", "The " +
            "interaction type of the camera", Interact.CAMERA, Interact.values());
    Config<Boolean> rotateConfig = new BooleanConfig("Rotate", "Rotate to the" +
            " point of interaction", false);
    //
    private Input freecamInput;
    //
    private Entity playerEntity;
    private FreecamEntity freecamEntity;

    /**
     *
     */
    public FreecamModule()
    {
        super("Freecam", "Allows you to control the camera separately from the player",
                ModuleCategory.RENDER);
    }

    /**
     *
     */
    @Override
    public void onEnable()
    {
        if (mc.player == null || mc.world == null)
        {
            return;
        }
        freecamEntity = new FreecamEntity(mc.world);
        freecamEntity.setHealth(mc.player.getHealth());
        freecamEntity.setAbsorptionAmount(mc.player.getAbsorptionAmount());
        freecamEntity.setBoundingBox(mc.player.getBoundingBox());
        freecamEntity.setPosition(mc.player.getPos());
        freecamEntity.prevX = mc.player.prevX;
        freecamEntity.prevY = mc.player.prevY;
        freecamEntity.prevZ = mc.player.prevZ;
        freecamEntity.prevYaw = mc.player.prevYaw;
        freecamEntity.prevPitch = mc.player.prevPitch;
        freecamEntity.prevHeadYaw = mc.player.prevHeadYaw;
        freecamEntity.prevBodyYaw = mc.player.prevBodyYaw;
        freecamEntity.getInventory().clone(mc.player.getInventory());
        freecamEntity.hurtTime = mc.player.hurtTime;
        freecamEntity.maxHurtTime = mc.player.maxHurtTime;
        Input input = new KeyboardInput(mc.options);
        input.tick(false, 0.3f);
        freecamInput = input;
        playerEntity = mc.getCameraEntity();
        mc.setCameraEntity(freecamEntity);
    }

    /**
     *
     */
    @Override
    public void onDisable()
    {
        freecamEntity = null;
        mc.setCameraEntity(mc.player);
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onDisconnect(DisconnectEvent event)
    {
        disable();
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onScreenOpen(ScreenOpenEvent event)
    {
        if (event.getScreen() instanceof DeathScreen)
        {
            disable();
        }
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onPlayerMove(PlayerMoveEvent event)
    {
        freecamInput.tick(false, 0.3f);
        freecamEntity.setHealth(mc.player.getHealth());
        freecamEntity.setAbsorptionAmount(mc.player.getAbsorptionAmount());
        freecamEntity.noClip = true;



        freecamEntity.resetPosition();
        freecamEntity.getInventory().clone(mc.player.getInventory());
        freecamEntity.hurtTime = mc.player.hurtTime;
        freecamEntity.maxHurtTime = mc.player.maxHurtTime;
    }

    public enum Interact
    {
        CAMERA
    }

    //
    // TODO: FIX
    //
    //
    public static class FreecamEntity extends ClientPlayerEntity
    {
        /**
         *
         * @param world
         */
        public FreecamEntity(ClientWorld world)
        {
            super(mc, world, mc.player.networkHandler, mc.player.getStatHandler(),
                    mc.player.getRecipeBook(), false, false);
        }

        @Override
        public boolean hasStatusEffect(StatusEffect statusEffect)
        {
            return mc.player.hasStatusEffect(statusEffect);
        }

        @Override
        public StatusEffectInstance getStatusEffect(StatusEffect statusEffect)
        {
            return mc.player.getStatusEffect(statusEffect);
        }

        @Override
        public Collection<StatusEffectInstance> getStatusEffects()
        {
            return mc.player.getStatusEffects();
        }

        @Override
        public Map<StatusEffect, StatusEffectInstance> getActiveStatusEffects()
        {
            return mc.player.getActiveStatusEffects();
        }

        @Override
        public float getAbsorptionAmount()
        {
            return mc.player.getAbsorptionAmount();
        }

        @Override
        public int getArmor()
        {
            return mc.player.getArmor();
        }

        @Override
        public HungerManager getHungerManager()
        {
            return mc.player.getHungerManager();
        }
    }
}
