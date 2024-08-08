package lol.point.returnclient.module.managers.impl;

import lol.point.Return;
import lol.point.returnclient.events.impl.player.EventJoinServer;
import lol.point.returnclient.events.impl.player.EventLeaveServer;
import lol.point.returnclient.events.impl.update.EventUpdate;
import lol.point.returnclient.module.impl.misc.Alerts;
import lol.point.returnclient.module.managers.Manager;
import lol.point.returnclient.util.minecraft.ChatUtil;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;

import java.util.*;

public class BedwarsManager extends Manager {

    public Object parent;

    public BedwarsManager(Object parent) {
        Return.BUS.subscribe(this);
        this.parent = parent;
    }

    //Weapons & tools
    private final Collection<EntityPlayer> ironSword = new HashSet<>();
    private final Collection<EntityPlayer> diamondSword = new HashSet<>();
    private final Collection<EntityPlayer> stoneSword = new HashSet<>();
    private final Collection<EntityPlayer> bows = new HashSet<>();

    //Consumables & effects
    private final Collection<EntityPlayer> invisibles = new HashSet<>();
    private final Collection<EntityPlayer> speeds = new HashSet<>();
    private final Collection<EntityPlayer> jumps = new HashSet<>();
    private final Collection<EntityPlayer> enderpearls = new HashSet<>();

    //Armor
    private final Collection<EntityPlayer> diamondArmor = new HashSet<>();
    private final Collection<EntityPlayer> chainArmor = new HashSet<>();
    private final Collection<EntityPlayer> ironArmor = new HashSet<>();

    private Alerts alertModule;

    @Subscribe
    private final Listener<EventJoinServer> onJoin = new Listener<>(eventJoinServer -> {
        stoneSword.clear();
        ironSword.clear();
        diamondSword.clear();
        chainArmor.clear();
        ironArmor.clear();
        diamondArmor.clear();
        enderpearls.clear();
        jumps.clear();
        speeds.clear();
        invisibles.clear();
        bows.clear();
    });

    @Subscribe
    private final Listener<EventLeaveServer> onLeave = new Listener<>(eventLeaveServer -> {
        stoneSword.clear();
        ironSword.clear();
        diamondSword.clear();
        chainArmor.clear();
        ironArmor.clear();
        diamondArmor.clear();
        enderpearls.clear();
        jumps.clear();
        speeds.clear();
        invisibles.clear();
        bows.clear();
    });

    @Subscribe
    private final Listener<EventUpdate> onUpdate = new Listener<>(eventUpdate -> {
        if (alertModule == null) {
            alertModule = Return.INSTANCE.moduleManager.getByClass(Alerts.class);
        }

        if (mc.thePlayer == null || mc.theWorld == null || mc.getCurrentServerData() == null || !alertModule.enabled) {
            return;
        }

        boolean _swords = alertModule.swords.value;
        boolean _armor = alertModule.armor.value;
        boolean _bows = alertModule.bows.value;
        boolean _enderPearl = alertModule.enderPearl.value;
        boolean _speedEffects = alertModule.speedEffects.value;
        boolean _jumpEffects = alertModule.jumpEffects.value;
        boolean _invisibilityEffects = alertModule.invisibilityEffects.value;

        for (final EntityPlayer entity : mc.theWorld.playerEntities) {
            if (entity == mc.thePlayer) {
                return;
            }

            speeds.removeIf(e -> e.getActivePotionEffect(Potion.moveSpeed) == null);
            jumps.removeIf(e -> e.getActivePotionEffect(Potion.jump) == null);
            invisibles.removeIf(e -> e.getActivePotionEffect(Potion.invisibility) == null);
            bows.removeIf(EntityPlayer::isDead);
            stoneSword.removeIf(EntityPlayer::isDead);
            ironSword.removeIf(EntityPlayer::isDead);
            diamondSword.removeIf(EntityPlayer::isDead);
            chainArmor.removeIf(EntityPlayer::isDead);
            ironArmor.removeIf(EntityPlayer::isDead);
            diamondArmor.removeIf(EntityPlayer::isDead);

            if (entity.getHeldItem() != null) {
                final Item heldItem = entity.getHeldItem().getItem();

                if (heldItem instanceof ItemBow && !bows.contains(entity) && _bows) {
                    double distance = Math.round(mc.thePlayer.getDistanceToEntity(entity));
                    bows.add(entity);
                    ChatUtil.addChatMessage("Player: §c" + entity.getCommandSenderName() + "§r has a §bBow§c (" + distance + " m)");
                }

                if (heldItem instanceof ItemEnderPearl && !enderpearls.contains(entity) && _enderPearl) {
                    double distance = Math.round(mc.thePlayer.getDistanceToEntity(entity));
                    enderpearls.add(entity);
                    ChatUtil.addChatMessage("Player: §c" + entity.getCommandSenderName() + "§r has an §bEnder pearl§c (" + distance + " m)");
                }

                if (heldItem instanceof ItemSword && _swords) {
                    final String type = ((ItemSword) heldItem).getToolMaterialName().toLowerCase();

                    if (type.contains("iron") && !ironSword.contains(entity)) {
                        double distance = Math.round(mc.thePlayer.getDistanceToEntity(entity));
                        ironSword.add(entity);
                        ChatUtil.addChatMessage("Player: §c" + entity.getCommandSenderName() + "§r has an §bIron Sword§c (" + distance + " m)");
                    }

                    if (type.contains("emerald") && !diamondSword.contains(entity)) {
                        double distance = Math.round(mc.thePlayer.getDistanceToEntity(entity));
                        diamondSword.add(entity);
                        ChatUtil.addChatMessage("Player: §c" + entity.getCommandSenderName() + "§r has a §bDiamond Sword§c (" + distance + " m)");
                    }

                    if (type.contains("stone") && !stoneSword.contains(entity)) {
                        double distance = Math.round(mc.thePlayer.getDistanceToEntity(entity));
                        stoneSword.add(entity);
                        ChatUtil.addChatMessage("Player: §c" + entity.getCommandSenderName() + "§r has a §bStone Sword§c (" + distance + " m)");
                    }

                    if (type.contains("wood")) {
                        stoneSword.remove(entity);
                        ironSword.remove(entity);
                        diamondSword.remove(entity);
                    }
                }
            }

            if (entity.isInvisible() && !invisibles.contains(entity) && _invisibilityEffects) {
                double distance = Math.round(mc.thePlayer.getDistanceToEntity(entity));
                invisibles.add(entity);
                ChatUtil.addChatMessage("Player: §c" + entity.getCommandSenderName() + "§r is §dinvisible§c (" + distance + " m)");
            }

            if (entity.isPotionActive(Potion.moveSpeed) && !speeds.contains(entity) && _speedEffects) {
                double distance = Math.round(mc.thePlayer.getDistanceToEntity(entity));
                speeds.add(entity);
                ChatUtil.addChatMessage("Player: §c" + entity.getCommandSenderName() + "§r has a §dspeed potion§r active§c (" + distance + " m)");
            }

            if (entity.isPotionActive(Potion.jump) && !jumps.contains(entity) && _jumpEffects) {
                double distance = Math.round(mc.thePlayer.getDistanceToEntity(entity));
                jumps.add(entity);
                ChatUtil.addChatMessage("Player: §c" + entity.getCommandSenderName() + "§r has a §djump potion§r active§c (" + distance + " m)");
            }

            for (Entity entity1 : invisibles) {
                if (!entity1.isInvisible() && entity1 instanceof EntityPlayer) {
                    invisibles.remove(entity1);
                }
            }

            ItemStack entityCurrentArmor = entity.getCurrentArmor(1);

            if (entityCurrentArmor != null && entityCurrentArmor.getItem() instanceof ItemArmor itemArmor && _armor) {
                ItemArmor.ArmorMaterial armorMaterial = itemArmor.getArmorMaterial();

                if (armorMaterial == ItemArmor.ArmorMaterial.CHAIN && !chainArmor.contains(entity)) {
                    double distance = Math.round(mc.thePlayer.getDistanceToEntity(entity));
                    chainArmor.add(entity);
                    ChatUtil.addChatMessage("Player: §c" + entity.getCommandSenderName() + "§r has §dChain Armor§c (" + distance + " m)");
                }

                if (armorMaterial == ItemArmor.ArmorMaterial.IRON && !ironArmor.contains(entity)) {
                    double distance = Math.round(mc.thePlayer.getDistanceToEntity(entity));
                    ironArmor.add(entity);
                    ChatUtil.addChatMessage("Player: §c" + entity.getCommandSenderName() + "§r has §dIron Armor§c (" + distance + " m)");
                }

                if (armorMaterial == ItemArmor.ArmorMaterial.DIAMOND && !diamondArmor.contains(entity)) {
                    double distance = Math.round(mc.thePlayer.getDistanceToEntity(entity));
                    diamondArmor.add(entity);
                    ChatUtil.addChatMessage("Player: §c" + entity.getCommandSenderName() + "§r has §dDiamond Armor§c (" + distance + " m)");
                }

                if (armorMaterial == ItemArmor.ArmorMaterial.LEATHER) {
                    chainArmor.remove(entity);
                    ironArmor.remove(entity);
                    diamondArmor.remove(entity);
                }
            }
        }
    });

    /*
    ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡀⠄⠒⠒⠀⠀⠒⠂⠠⢀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡠⠂⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠀⢄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠑⢄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡠⠀⠀⣐⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣠⠀⠡⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡐⠀⠀⠈⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠁⠀⠀⠐⠄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⢀⠄⠂⠠⢀⡀⠀⠀⠀⠀⠔⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠨⡄⠀⠀⠀⠀⢀⠠⠐⠒⠐⠄
⢠⠁⠀⠀⠀⠀⠀⠈⠉⠉⠉⠀⠀⠀⠀⠀⣀⣤⣶⣿⣿⣿⣿⣿⣿⣶⣦⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⡤⡀⠁⠉⠀⠀⠀⠀⠀⠈
⢸⠀⠀⠀⠀⠀⠀⠀⠀⠐⠀⠀⠀⠀⣠⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣆⠀⠀⠀⠀⠀⠀⠀⠀⠀⢱⠰⠀⠀⠀⠀⠀⠀⠀⢨
⠘⠄⠤⠀⠀⠀⠀⠀⠀⡆⠀⠀⢀⣼⣿⣿⠿⠿⠛⠻⠛⠛⠛⠙⠛⠛⠋⠩⠝⣿⣷⡄⠀⠀⠀⠀⠀⠀⠀⢘⣿⡶⣶⠲⢶⣴⣦⡄⠁
⠀⠀⠇⠀⠀⠀⠠⠔⢈⠁⠁⠀⣾⣿⡏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢃⡹⣿⣿⣷⠀⠀⠀⠀⠀⠀⠀⠀⣿⣝⡾⣑⢎⡷⣏⡇⠀
⠀⠀⠏⠛⠓⠻⠷⠿⢿⠀⠀⠀⣿⣿⠇⠀⠀⠀⠀⣀⣠⣄⣤⣄⣀⣀⣀⠀⠸⣽⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⣽⡾⣝⣳⢎⡿⡥⠂⠀
⠀⠀⢰⠀⠀⠀⠀⠀⢘⠀⠀⠀⢿⣿⢰⣤⣴⣶⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠀⠀⠀⠀⠀⠀⠀⠀⣿⡽⣯⡽⢾⣹⡓⠀⠀
⠀⠀⢸⠀⠀⠀⠀⠀⠨⠀⡀⢠⡘⣿⠸⣿⣿⡟⣋⢿⣿⡿⠙⣿⣿⡿⣽⢻⣿⡿⢽⣷⡇⠀⠀⠀⠀⠀⠀⢠⣿⣟⣷⡻⣝⣧⢹⠀⠀
⠀⠀⠘⡀⠀⠀⠀⠀⠀⡄⠙⠀⣿⣿⠀⠈⠛⠂⠡⠾⠟⠁⠀⡨⠟⠓⣖⣋⡁⣤⣿⢿⡇⠀⠀⠀⠀⠀⠀⢸⣿⣞⣷⡻⣽⡺⡥⠀⠀
⠀⠀⠀⡄⠀⠀⠀⠀⠀⡇⠀⠀⢹⣯⡦⣤⣦⣤⡄⣃⠀⠀⠀⢴⣛⣶⢏⣾⣿⣿⣿⣻⠁⠀⠀⠀⠀⠀⠀⣸⣿⢾⣳⣟⡷⡽⡁⠀⠀
⠀⠀⠀⠇⠀⠀⠀⠀⠀⢡⢸⣡⡍⢳⣿⣳⣭⡷⡤⠝⣤⣁⣀⣺⣿⢻⢾⣭⣿⣿⡿⠃⠀⠀⠀⠰⣭⣱⢀⣿⣿⣻⡽⣾⣝⣷⠁⠀⠀
⠀⠀⠀⢰⠀⠀⢀⢀⠀⢈⡆⠁⠀⠀⠸⣷⣻⢿⣿⣶⣼⣿⣿⣿⣯⣿⣿⣿⣿⣿⠇⠀⠀⠀⠀⠀⠉⠀⣼⣿⡷⣿⣽⣳⢯⠸⠀⠀⠀
⠀⠀⠀⠈⡄⠀⠀⢢⠳⡠⢼⠀⠀⠀⠀⢿⣶⣂⢿⣿⣏⢙⠈⠙⢻⣿⣿⣿⣿⡟⠀⠀⠀⠀⠀⠀⠀⢰⣿⣿⣽⡷⣯⣟⠯⡆⠀⠀⠀
⠀⠀⠀⠀⠰⠀⠀⠀⡑⢝⢦⣇⠀⠀⠀⠘⣿⣿⣦⡹⢿⣶⣶⣶⢿⣿⣿⣿⣿⠁⠀⠀⠀⠀⠀⠀⢠⣿⣿⣟⣾⣿⣻⣞⢷⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠇⠀⠶⡈⡎⣷⡾⣷⡀⠀⠀⣿⣿⣿⣿⣆⠈⣀⣈⣿⣿⣿⣿⡇⠀⠀⠀⠀⠀⠀⣰⣿⣿⣿⣿⡿⣷⣿⡎⡆⠀⠀⠀⠀
⠀⠀⠀⠀⢀⠈⠮⠵⠵⠎⠵⠛⠛⠛⠛⠻⢻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠷⠶⠶⠶⠾⠿⠿⠿⠿⠿⠿⠽⣿⣟⢗⠜⠀⠀⠀⠀⠀
⠀⠀⠀⠀⣅⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠁⠀⠀⠀⠀⠀⠀⢀⠀⣀⣀⣀⣀⡈⠎⠁⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠈⠀⠀⠉⠉⠉⠀⠀⠀⠀⠒⠒⠒⠒⠒⠒⠒⠒⠒⠒⠒⠒⠒⠒⠂⠐⠀⠈⠀⠉⠉⠉⠉⠀⠀⠀⠀⠐⢰⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡀⢄⡰⢠⢒⡰⢂⡖⣐⠺⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⢀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⠠⢄⢢⡱⣘⢦⡜⣣⢮⡵⣫⡼⣧⢹⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⢱⡰⢶⡖⣦⢤⣤⣤⣤⣤⣄⣀⣀⣀⣀⣀⣀⣀⣄⣤⣔⣦⣳⢦⣟⣮⣷⣻⣽⣞⣿⡽⣯⣿⣟⣿⠫⠁⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⢇⠢⡙⢤⠛⣼⢳⣻⢮⣝⡯⣽⣹⢮⡽⣭⣛⢮⣗⡻⣞⡽⣯⣻⠷⣯⢷⣻⢾⣽⣛⣷⣻⣞⢧⠁⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠘⡵⣹⢦⣛⢦⣏⣷⣻⢮⡽⣶⣛⣮⢗⡷⣫⣟⡼⣝⣧⠿⣵⢯⡿⣭⢿⣭⣟⡾⣽⡞⣷⢏⠆⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠸⣟⣯⣟⡿⣞⡷⣯⢿⡽⣞⣳⣭⢿⣹⣗⡾⣝⡾⣞⣻⡽⣾⡽⢯⣷⣛⡾⣽⣳⢿⢙⠊⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠈⠪⠿⣽⣯⢿⡽⣯⢿⣽⣳⢯⣟⡷⡾⣝⣻⡼⣯⢷⣻⣗⣿⣻⢾⣽⣻⠷⡫⠂⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠑⠩⢟⢿⣽⣟⣾⣽⣟⣾⣽⡿⣽⣷⣻⣽⣯⣷⣻⣾⡽⡛⠎⠃⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⠀⠀⠀⠉⠉⠉⠙⠛⠛⡙⢩⠩⣅⠫⢖⡭⣿⢺⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠸⠀⠀⠀⠀⠀⠀⠀⠀⠀⠐⠀⠣⡐⢍⠎⡼⣻⡟⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠄⠱⡈⢎⡱⣝⣏⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠠⢑⠢⣑⢮⢿⣠⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠸⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠡⠌⡒⡡⢞⣯⢯⠱⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡐⢢⠁⢧⡙⣮⣟⣾⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢂⠍⢦⡙⣮⢿⡈⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡌⡘⢦⣙⣾⣋⠁⠀  ,⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
     */
}