package club.bluezenith.module.modules.render;

import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.Render2DEvent;
import club.bluezenith.events.impl.RenderNameTagEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.modules.combat.Aura;
import club.bluezenith.module.modules.combat.TPAura;
import club.bluezenith.module.modules.render.targethuds.ITargetHUD;
import club.bluezenith.module.modules.render.targethuds.impl.*;
import club.bluezenith.module.value.builders.AbstractBuilder;
import club.bluezenith.module.value.types.*;
import club.bluezenith.ui.clickgui.ClickGui;
import club.bluezenith.ui.draggables.Draggable;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static java.awt.Color.HSBtoRGB;
import static java.lang.Math.min;
import static net.minecraft.client.renderer.GlStateManager.*;


public class TargetHUD extends Module implements Draggable {
	//TODO: Implement most animations lmao
	public double translateX, translateY;
	public boolean held, wait, allowDragging;
	public float width, height;
	private ITargetHUD targetHUD;

	private final Map<EntityPlayer, ITargetHUD> entityMap = new HashMap<>();

	public ModeValue mode = new ModeValue(
			"Mode",
			"LiquidBounce",
			"LiquidBounce", "Novoline", "Old Novoline", "Flux", "Trash", "Autumn", "Ketamine", "New Ketamine", "Old Exhi"
	)
			.setIndex(1)
			.setValueChangeListener((prev, post) -> {
				switch (post) {
					case "Flux":
						targetHUD = Flux.getInstance();
					break;

					case "LiquidBounce":
						targetHUD = LiquidBounce.getInstance();
					break;

					case "Novoline":
						targetHUD = Novoline.getInstance();
					break;

					case "Trash":
						targetHUD = Weird.getInstance();
					break;

					case "Old Novoline":
						targetHUD = OldNovoline.getInstance();
					break;

					case "Ketamine":
						targetHUD = Ketamine.getInstance();
					break;

					case "Autumn":
						targetHUD = Autumn.getInstance();
					break;

					case "New Ketamine":
						targetHUD = NewKetamine.getInstance();
					break;

					case "Old Exhi":
						targetHUD = OldExhibitch.getInstance();
					break;
				}
				return post;
			});

	public FloatValue x = new FloatValue("X", 0, 1, 100, 1, true, null).setIndex(23)
			.setValueChangeListener((prev, post) -> post);
	public FloatValue y = new FloatValue("Y", 0, 1, 100, 1, true, null).setIndex(35)
			.setValueChangeListener((prev, post) -> {
				return post;
			});
	public FloatValue novolineScale = new FloatValue("Scale", 1, 0.6F, 2F, 0.05F).setIndex(4).showIf(() -> mode.is("Novoline"));
	public ListValue outlines = new ListValue("Outline", "Skin", "Rect").showIf(() -> mode.is("Flux")).setIndex(6);
	public FloatValue fluxHollowWidth = AbstractBuilder.createFloat("Outline width").min(0.5F).max(3F).increment(0.5F).index(7).showIf(() -> mode.is("Flux") && (outlines.getOptionState("Skin") || outlines.getOptionState("Rect"))).build();
	public BooleanValue supportMulti = AbstractBuilder.createBoolean("Multiple targets").index(2).defaultOf(false).build();
	public BooleanValue accountProtection = AbstractBuilder.createBoolean("Check enchants").index(8).defaultOf(false).showIf(() -> mode.is("Flux")).build();
	public ModeValue outlineMode = AbstractBuilder.createMode("Outline").range("Match HUD", "Custom").index(9).showIf(() -> mode.is("Flux")).build();
	public ColorValue outlineColor = new ColorValue("Outline color").showIf(() -> mode.is("Flux") && outlineMode.is("Custom")).setIndex(10);
	public ModeValue trashHPMode = AbstractBuilder.createMode("Health").showIf(() -> mode.is("Trash")).range("Percentage", "Points").index(10).build();
	public final ListValue fluxBackgrounds = new ListValue("Background", "Rect", "Blur").showIf(() -> mode.is("Flux")).setIndex(6);
	public final BooleanValue newKetamineBlur = new BooleanValue("Blur", true).showIf(() -> mode.is("New Ketamine")).setIndex(7);

	public TargetHUD() {
		super("TargetHUD", ModuleCategory.RENDER);
		fluxBackgrounds.setOptionState("Rect", true);
	}

	@SuppressWarnings("unused")
	@Override
	public void draw(Render2DEvent e) {
		if(targetHUD == null) {
			mode.set(mode.get());
		}

		Aura aura = getBlueZenith().getModuleManager().getAndCast(Aura.class);
		TPAura tpAura = getBlueZenith().getModuleManager().getAndCast(TPAura.class);
		if(!aura.getState() && !tpAura.getState() && !(mc.currentScreen instanceof GuiChat)) return;

		EntityLivingBase target = aura.getTarget();

		final List<EntityLivingBase> entityList = aura.getTargetsOrNull();
		final boolean hasAnyTargets = (tpAura.getTarget() == null || !tpAura.getState()) && entityList != null && entityList.stream().anyMatch(obj -> obj instanceof EntityPlayer);

		if(target == null) target = tpAura.getTarget(); //i cba to implement this for tpaura

		if (mc.currentScreen instanceof GuiChat)
			target = mc.thePlayer;

		else if (target == null && !hasAnyTargets) return;


		if(!(target instanceof EntityPlayer)) return;
		if(mc.getNetHandler().getPlayerInfo(target.getUniqueID()) == null) return;

		pushMatrix();
		final float mainScale = mode.is("Novoline") ? novolineScale.get() : 1;

		translate(translateX = x.get(), translateY = y.get(), 0);

		if(entityList == null || mc.currentScreen instanceof GuiChat || !supportMulti.get()) { //todo rewrite this bullshit LMAO
			targetHUD.render(e, (EntityPlayer) target, this);
		} else {
			try {
				List<EntityLivingBase> toRemove = new ArrayList<>(); //can't remove stuff from the map while iterating

				for (EntityLivingBase entity : entityList) {
					if (!(entity instanceof EntityPlayer)) continue; //we only need players

					final EntityPlayer player = (EntityPlayer) entity;

					ITargetHUD key = entityMap.get(player);

					if (key == null || key.getClass() != targetHUD.getClass()) { //check if the entity isn't cached or if the cached targethud mode isn't correct
						entityMap.put(player, key = targetHUD.createInstance());
					}

					key.render(e, player, this);
					translate(0, -(height + 5), 0); //move new targethud up
					translateY -= (height + 5);
				}

				for (EntityPlayer entityPlayer : entityMap.keySet()) {
					if(!entityList.contains(entityPlayer)) { //remove entities that aren't being attacked anymore to prevent memory leaks
						toRemove.add(entityPlayer);
					}
				}

				for (EntityLivingBase entityLivingBase : toRemove) {
					entityMap.remove((EntityPlayer) entityLivingBase); //remove entities that aren't being attacked anymore to prevent memory leaks
				}

			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
		translate(-x.get(), -y.get(), 0);

		popMatrix();
	}

	public int getArmorPointsForPlayer(EntityPlayer target) {
       if(accountProtection.get()) {
          int prot = 0;
		   for (ItemStack item : target.inventory.armorInventory) {
			   if(item != null && item.getItem() instanceof ItemArmor) {
                   final ItemArmor armorPiece = (ItemArmor) item.getItem();
				   prot += armorPiece.damageReduceAmount;
				   if(item.isItemEnchanted() && item.getEnchantmentTagList() != null) {
					   final NBTTagCompound nbt = item.getEnchantmentTagList().getCompoundTagAt(0);
					   if(nbt != null) {
						   if(nbt.getInteger("id") == 0) {
							   prot += nbt.getInteger("lvl");
						   }
					   }
				   }
			   }
		   }
		   return prot;
	   } else return target.getTotalArmorValue();
	}

	@Listener
	public void onRenderNameTag(RenderNameTagEvent event) {
		if(getBlueZenith().targetHudEntity == event.getEntity())
			event.cancel();
	}

	public float getMaxArmorPoints() {
       return accountProtection.get() ? 36F : 20F;
	}

	public int getColorForHealth(float maxHealth, float currentHealth) {
		float diff = min(currentHealth, maxHealth) / maxHealth;
		return HSBtoRGB(diff / 3F, 1, 1);
	}

	public int getColorForHealth(float maxHealth, float currentHealth, float saturation, float brightness) {
		float diff = min(currentHealth, maxHealth) / maxHealth;
		return HSBtoRGB(diff / 3F, saturation, brightness);
	}

	@Override
	public boolean shouldBeRendered() {
		return this.getState();
	}

	@Override
	public boolean isMouseOver(int mouseX, int mouseY) {
		return ClickGui.i(mouseX, mouseY, x.get(), y.get(), x.get() + width, y.get() + height);
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {

	}

	@Override
	public float getX() {
		return x.get();
	}

	@Override
	public float getY() {
		return y.get();
	}

	@Override
	public void moveTo(float x, float y) {
		this.x.set(x);
		this.y.set(y);
	}

	@Override
	public int hashCode() {
		return "targetslay".hashCode();
	}
}
