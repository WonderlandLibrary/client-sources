package com.alan.clients.module.impl.other;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.event.impl.other.WorldChangeEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.value.impl.BooleanValue;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumChatFormatting;

import java.util.Collection;
import java.util.HashSet;

@ModuleInfo(aliases = {"module.other.bwutils.name"}, description = "module.other.bwutils.description", category = Category.PLAYER)
public final class BedWarsUtils extends Module {

    private final Collection<EntityPlayer> ironSword = new HashSet<>();

    private final Collection<EntityPlayer> diamondSword = new HashSet<>();

    private final Collection<EntityPlayer> stoneSword = new HashSet<>();

    private final Collection<EntityPlayer> diamondArmor = new HashSet<>();

    private final Collection<EntityPlayer> chainArmor = new HashSet<>();

    private final Collection<EntityPlayer> ironArmor = new HashSet<>();

    private final Collection<EntityPlayer> invisible = new HashSet<>();

    private final BooleanValue swords = new BooleanValue("Sword Reveal", this, true);

    private final BooleanValue includeStone = new BooleanValue("Include Stone", this, false, () -> !swords.getValue());

    private final BooleanValue armor = new BooleanValue("Armor Reveal", this, true);

    private final BooleanValue invisibleCheck = new BooleanValue("Invisible Check", this, true);

    private final BooleanValue potionInvis = new BooleanValue("Invisibility Status", this, false);

    private boolean wasThePlayerInvis = false;

    @EventLink
    private final Listener<PreUpdateEvent> eventListener = preUpdateEvent -> {
        for (final EntityPlayer entity : this.mc.theWorld.playerEntities) {
            if (this.mc.thePlayer != null || this.mc.theWorld != null) {
                if (entity.getHeldItem() != null) {
                    final Item heldItem = entity.getHeldItem().getItem();

                    if (this.swords.getValue()) {
                        if (heldItem instanceof ItemSword) {
                            final String type = ((ItemSword) heldItem).getToolMaterialName().toLowerCase();

                            if (type.contains("iron")) {
                                if (!this.ironSword.contains(entity)) {
                                    this.ironSword.add(entity);
                                    ChatUtil.display("Player " + EnumChatFormatting.RED + entity.getCommandSenderName() + EnumChatFormatting.WHITE + " has an " + EnumChatFormatting.AQUA + "Iron Sword");

                                }
                            }

                            if (type.contains("emerald")) {
                                if (!this.diamondSword.contains(entity)) {
                                    this.diamondSword.add(entity);
                                    ChatUtil.display("Player " + EnumChatFormatting.RED + entity.getCommandSenderName() + EnumChatFormatting.WHITE + " has a " + EnumChatFormatting.AQUA + "Diamond Sword");
                                }
                            }

                            if (type.contains("stone")) {
                                if (!stoneSword.contains(entity)) {
                                    this.stoneSword.add(entity);
                                    if (this.includeStone.getValue()) {
                                        ChatUtil.display("Player " + EnumChatFormatting.RED + entity.getCommandSenderName() + EnumChatFormatting.WHITE + " has a " + EnumChatFormatting.AQUA + "Stone Sword");
                                    }
                                }
                            }

                            if (type.contains("wood")) {
                                this.stoneSword.remove(entity);
                                this.ironSword.remove(entity);
                                diamondSword.remove(entity);
                            }
                        }
                    }
                }
                if (this.armor.getValue()) {
                    ItemStack entityCurrentArmor = entity.getCurrentArmor(1);

                    if (entityCurrentArmor != null && entityCurrentArmor.getItem() instanceof ItemArmor) {

                        if (((ItemArmor) entityCurrentArmor.getItem()).getArmorMaterial().equals(ItemArmor.ArmorMaterial.CHAIN)) {
                            if (!chainArmor.contains(entity)) {
                                chainArmor.add(entity);
                                ChatUtil.display("Player " + EnumChatFormatting.RED + entity.getCommandSenderName() + EnumChatFormatting.WHITE + " has " + EnumChatFormatting.LIGHT_PURPLE + "Chain Armor");
                            }
                        }

                        if (((ItemArmor) entityCurrentArmor.getItem()).getArmorMaterial().equals(ItemArmor.ArmorMaterial.IRON)) {
                            if (!this.ironArmor.contains(entity)) {
                                this.ironArmor.add(entity);
                                ChatUtil.display("Player " + EnumChatFormatting.RED + entity.getCommandSenderName() + EnumChatFormatting.WHITE + " has " + EnumChatFormatting.LIGHT_PURPLE + "Iron Armor");
                            }
                        }

                        if (((ItemArmor) entityCurrentArmor.getItem()).getArmorMaterial().equals(ItemArmor.ArmorMaterial.DIAMOND)) {
                            if (!this.diamondArmor.contains(entity)) {
                                this.diamondArmor.add(entity);
                                ChatUtil.display("Player " + EnumChatFormatting.RED + entity.getCommandSenderName() + EnumChatFormatting.WHITE + " has " + EnumChatFormatting.LIGHT_PURPLE + "Diamond Armor");
                            }
                        }

                        if (((ItemArmor) entityCurrentArmor.getItem()).getArmorMaterial().equals(ItemArmor.ArmorMaterial.LEATHER)) {
                            this.diamondArmor.remove(entity);
                            this.ironArmor.remove(entity);
                            this.chainArmor.remove(entity);
                        }
                    }
                }

                if (this.invisibleCheck.getValue()) {
                    if (entity.getActivePotionEffect(Potion.invisibility) != null) {
                        if (!this.invisible.contains(entity)) {
                            this.invisible.add(entity);
                            ChatUtil.display("Player " + EnumChatFormatting.RED + entity.getCommandSenderName() + EnumChatFormatting.WHITE + " is now " + EnumChatFormatting.GOLD + "Invisible");
                        }
                    } else if (this.invisible.contains(entity)) {
                        this.invisible.remove(entity);
                        ChatUtil.display("Player " + EnumChatFormatting.RED + entity.getCommandSenderName() + EnumChatFormatting.WHITE + " is now " + EnumChatFormatting.GOLD + "Visible");
                    }
                }

                if (this.potionInvis.getValue()) {
                    if (mc.thePlayer.getActivePotionEffect(Potion.invisibility) != null) {
                        wasThePlayerInvis = true;
                        if (mc.thePlayer.ticksExisted % 200 == 0) {
                            ChatUtil.display("Your Invisibility" + EnumChatFormatting.RED + " expires " + EnumChatFormatting.RESET + "in " + EnumChatFormatting.RED + mc.thePlayer.getActivePotionEffect(Potion.invisibility).getDuration() / 20 + EnumChatFormatting.RESET + " second(s)");
                        }
                    }
                } else if (wasThePlayerInvis) {
                    ChatUtil.display("Invisibility" + EnumChatFormatting.RED + " Expired");
                    wasThePlayerInvis = false;
                }

            } else {
                this.diamondSword.clear();
                this.ironSword.clear();
                this.stoneSword.clear();
                this.diamondArmor.clear();
                this.ironArmor.clear();
                this.chainArmor.clear();
                this.invisible.clear();
            }
        }
    };

    @EventLink
    private final Listener<WorldChangeEvent> event = event -> {
        this.diamondSword.clear();
        this.ironSword.clear();
        this.stoneSword.clear();
        this.chainArmor.clear();
        this.ironArmor.clear();
        this.diamondArmor.clear();
        this.invisible.clear();
    };
}
