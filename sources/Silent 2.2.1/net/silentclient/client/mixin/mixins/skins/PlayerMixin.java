package net.silentclient.client.mixin.mixins.skins;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.silentclient.client.mixin.accessors.skins.PlayerSettings;
import net.silentclient.client.mods.render.skins.render.CustomizableModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

/**
 * Keep player specific settings, data and modifies the eye location when enabled
 *
 */
@Mixin(EntityPlayer.class)
public abstract class PlayerMixin extends EntityLivingBase implements PlayerSettings {
    
	public PlayerMixin(World p_i1594_1_) {
        super(p_i1594_1_);
    }

    @Unique
	private CustomizableModelPart headLayer;
	@Unique
	private CustomizableModelPart[] skinLayer;

	@Override
	public CustomizableModelPart[] client$getSkinLayers() {
		return skinLayer;
	}
	
	@Override
	public void client$setupSkinLayers(CustomizableModelPart[] box) {
		this.skinLayer = box;
	}
	
	@Override
	public CustomizableModelPart client$getHeadLayers() {
		return headLayer;
	}
	
	@Override
	public void client$setupHeadLayers(CustomizableModelPart box) {
		this.headLayer = box;
	}
	
}
