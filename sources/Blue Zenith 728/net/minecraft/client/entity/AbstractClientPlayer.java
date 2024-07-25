package net.minecraft.client.entity;

import club.bluezenith.BlueZenith;
import club.bluezenith.module.modules.render.CustomCape;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;
import net.optifine.player.CapeUtils;
import net.optifine.player.PlayerConfigurations;
import net.optifine.reflect.Reflector;

public abstract class AbstractClientPlayer extends EntityPlayer {
	private NetworkPlayerInfo playerInfo;
	private ResourceLocation locationOfCape = null;
	private long reloadCapeTimeMs = 0L;
	private boolean elytraOfCape = false;
	private String nameClear;

	public AbstractClientPlayer(World worldIn, GameProfile playerProfile) {
		super(worldIn, playerProfile);
		this.nameClear = playerProfile.getName();

		if (this.nameClear != null && !this.nameClear.isEmpty()) {
			this.nameClear = StringUtils.stripControlCodes(this.nameClear);
		}

		CapeUtils.downloadCape(this);
		PlayerConfigurations.getPlayerConfiguration(this);
	}

	public static ThreadDownloadImageData getDownloadImageHead(ResourceLocation resourceLocationIn, String username) {
		TextureManager var2 = Minecraft.getMinecraft().getTextureManager();
		Object var3 = var2.getTexture(resourceLocationIn);

		if (var3 == null) {
			//System.out.println(String.format("https://crafatar.com/avatars/%s", new Object[] {StringUtils.stripControlCodes(username)}) + " " + DefaultPlayerSkin.func_177334_a(func_175147_b(username)));
			var3 = new ThreadDownloadImageData(null, String.format("https://crafatar.com/avatars/%s", StringUtils.stripControlCodes(username)), DefaultPlayerSkin.getDefaultSkin(getOfflineUUID(username)), new ImageBufferDownload());
			System.out.println(var3);

			var2.loadTexture(resourceLocationIn, (ITextureObject) var3);
		}

		return (ThreadDownloadImageData) var3;
	}

	/**
	 * Returns true if the player is in spectator mode.
	 */
	public boolean isSpectator() {
		NetworkPlayerInfo networkplayerinfo = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(this.getGameProfile().getId());
		return networkplayerinfo != null && networkplayerinfo.getGameType() == WorldSettings.GameType.SPECTATOR;
	}

	/**
	 * Checks if this instance of AbstractClientPlayer has any associated player data.
	 */
	public boolean hasPlayerInfo() {
		return this.getPlayerInfo() != null;
	}

	protected NetworkPlayerInfo getPlayerInfo() {
		if (this.playerInfo == null) {
			this.playerInfo = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(this.getUniqueID());
		}

		return this.playerInfo;
	}

	/**
	 * Returns true if the player has an associated skin.
	 */
	public boolean hasSkin() {
		NetworkPlayerInfo networkplayerinfo = this.getPlayerInfo();
		return networkplayerinfo != null && networkplayerinfo.hasLocationSkin();
	}

	/**
	 * Returns true if the player instance has an associated skin.
	 */
	public ResourceLocation getLocationSkin() {
		NetworkPlayerInfo networkplayerinfo = this.getPlayerInfo();
		return networkplayerinfo == null ? DefaultPlayerSkin.getDefaultSkin(this.getUniqueID()) : networkplayerinfo.getLocationSkin();
	}

	public ResourceLocation getLocationCape() {
		if (this == Minecraft.getMinecraft().thePlayer && BlueZenith.getBlueZenith().getModuleManager().getModule(CustomCape.class).getState()) {
			return ((CustomCape) BlueZenith.getBlueZenith().getModuleManager().getModule(CustomCape.class)).getCapeLocation();
		}

		if (!Config.isShowCapes()) {
			return null;
		} else {
			if (this.reloadCapeTimeMs != 0L && System.currentTimeMillis() > this.reloadCapeTimeMs) {
				CapeUtils.reloadCape(this);
				this.reloadCapeTimeMs = 0L;
			}

			if (this.locationOfCape != null) {
				return this.locationOfCape;
			} else {
				NetworkPlayerInfo networkplayerinfo = this.getPlayerInfo();
				return networkplayerinfo == null ? null : networkplayerinfo.getLocationCape();
			}
		}
	}

	public static void getDownloadImageSkin(ResourceLocation resourceLocationIn, String username) {
		TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
		ITextureObject itextureobject = texturemanager.getTexture(resourceLocationIn);

		if (itextureobject == null) {
			itextureobject = new ThreadDownloadImageData(null, String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", StringUtils.stripControlCodes(username)), DefaultPlayerSkin.getDefaultSkin(getOfflineUUID(username)), new ImageBufferDownload());
			texturemanager.loadTexture(resourceLocationIn, itextureobject);
		}

	}

	/**
	 * Returns true if the username has an associated skin.
	 *
	 * @param username The username of the player being checked.
	 */
	public static ResourceLocation getLocationSkin(String username) {
		return new ResourceLocation("skins/" + StringUtils.stripControlCodes(username));
	}

	public String getSkinType() {
		NetworkPlayerInfo networkplayerinfo = this.getPlayerInfo();
		return networkplayerinfo == null ? DefaultPlayerSkin.getSkinType(this.getUniqueID()) : networkplayerinfo.getSkinType();
	}

	public float getFovModifier() {
		float f = 1.0F;

		if (this.capabilities.isFlying) {
			f *= 1.1F;
		}

		IAttributeInstance iattributeinstance = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
		f = (float) ((double) f * ((iattributeinstance.getAttributeValue() / (double) this.capabilities.getWalkSpeed() + 1.0D) / 2.0D));

		if (this.capabilities.getWalkSpeed() == 0.0F || Float.isNaN(f) || Float.isInfinite(f)) {
			f = 1.0F;
		}

		if (this.isUsingItem() && this.getItemInUse().getItem() == Items.bow) {
			int i = this.getItemInUseDuration();
			float f1 = (float) i / 20.0F;

			if (f1 > 1.0F) {
				f1 = 1.0F;
			} else {
				f1 = f1 * f1;
			}

			f *= 1.0F - f1 * 0.15F;
		}

		return Reflector.ForgeHooksClient_getOffsetFOV.exists() ? Reflector.callFloat(Reflector.ForgeHooksClient_getOffsetFOV, this, f) : f;
	}

	public String getNameClear() {
		return this.nameClear;
	}

	public void setLocationOfCape(ResourceLocation p_setLocationOfCape_1_) {
		this.locationOfCape = p_setLocationOfCape_1_;
	}

	public void setElytraOfCape(boolean p_setElytraOfCape_1_) {
		this.elytraOfCape = p_setElytraOfCape_1_;
	}

	public void setReloadCapeTimeMs(long p_setReloadCapeTimeMs_1_) {
		this.reloadCapeTimeMs = p_setReloadCapeTimeMs_1_;
	}

	/**
	 * interpolated look vector
	 */
	public Vec3 getLook(float partialTicks) {
		return this.getVectorForRotation(this.rotationPitch, this.rotationYaw);
	}
}
