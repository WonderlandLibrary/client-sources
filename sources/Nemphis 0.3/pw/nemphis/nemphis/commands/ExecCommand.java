/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.nemphis.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import pw.vertexcode.nemphis.Nemphis;
import pw.vertexcode.nemphis.command.Command;
import pw.vertexcode.nemphis.command.CommandManager;
import pw.vertexcode.nemphis.friend.FriendManager;
import pw.vertexcode.nemphis.module.ModuleManager;
import pw.vertexcode.nemphis.modules.Speed;
import pw.vertexcode.nemphis.ui.ingame.Messagebox;
import pw.vertexcode.nemphis.value.Value;
import pw.vertexcode.util.module.types.ToggleableModule;

public class ExecCommand
extends Command {
    @Override
    public String getName() {
        return "exec";
    }

    @Override
    public String getDescription() {
        return "Execute commands.";
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 1) {
            this.sendMessage("\u00a74Executer \u00a77-> I see you want to use the exec command. Please look here to see a list to exec. > \u00a73exec ldexec", false);
        }
        if (args.length >= 3 && args[1].equalsIgnoreCase("speed")) {
            ToggleableModule module = Nemphis.instance.modulemanager.getModule(Speed.class);
            module.getValue("mode").setValue(args[2]);
            this.sendMessage("Speedmode changed to " + args[2], true);
        }
        if (args.length >= 2) {
            if (args[1].equalsIgnoreCase("afkspam")) {
                int i = 0;
                while (i < 15) {
                    this.mc.thePlayer.sendChatMessage("/afk");
                    ++i;
                }
            }
            if (args[1].equalsIgnoreCase("spamCrash")) {
                int i = 0;
                while (i < 3) {
                    this.mc.thePlayer.sendChatMessage("PEW PEW");
                    ++i;
                }
            }
            if (args[1].equalsIgnoreCase("enchck")) {
                if (this.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) {
                    this.mc.thePlayer.getCurrentEquippedItem().addEnchantment(Enchantment.field_180314_l, 10);
                    this.mc.thePlayer.getCurrentEquippedItem().addEnchantment(Enchantment.fireAspect, 10);
                    this.mc.thePlayer.getCurrentEquippedItem().addEnchantment(Enchantment.unbreaking, 10);
                }
                if (this.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemPotion && this.mc.thePlayer.onGround) {
                    NBTTagList newEffects = new NBTTagList();
                    NBTTagCompound effect = new NBTTagCompound();
                    effect.setInteger("Id", Potion.moveSlowdown.id);
                    effect.setInteger("Amplifier", 155);
                    effect.setInteger("Duration", 1000000);
                    newEffects.appendTag(effect);
                    this.mc.thePlayer.getCurrentEquippedItem().setTagInfo("CustomPotionEffects", effect);
                }
            }
            if (args[1].equalsIgnoreCase("ldexec")) {
                this.sendMessage("\u00a74Executer \u00a77-> Here is a list ok Executeable Commands: ", false);
                this.sendMessage("\u00a74-> Exec: \u00a74ldcfg \u00a77Description: Setting up your Client perfectly for you selected Server.", false);
                this.sendMessage("\u00a74-> Exec: \u00a74reload \u00a77Description: Reloading all or only one Manager \u00a74[WARNING: Maybe modes keybinds or stuff like that are crash].", false);
                this.sendMessage("\u00a74-> Exec: \u00a74aviable \u00a77Description: Checking if a website is online or not.", false);
            }
            if (args[1].equalsIgnoreCase("ldcfg")) {
                this.sendMessage("\u00a74Executer \u00a77-> I see you want to change your Config. Please look here to see a Configlist. > \u00a74https://razex.pw/nemphis/configs.txt", false);
                Messagebox.addMessage(args.length == 3 ? args[2] : "Hello");
            }
            if (args[1].equalsIgnoreCase("reload")) {
                this.sendMessage("\u00a74Executer \u00a77-> Reloading managers....", false);
                Nemphis.instance.modulemanager = new ModuleManager();
                this.sendMessage("\u00a74Executer \u00a77-> ModuleManager Reloaded", false);
                Nemphis.instance.commandManager = new CommandManager();
                this.sendMessage("\u00a74Executer \u00a77-> CommandManager Reloaded", false);
                Nemphis.instance.friendManager = new FriendManager();
                this.sendMessage("\u00a74Executer \u00a77-> FriendManager Reloaded", false);
            }
        }
        if (args.length == 3) {
            args[2].equalsIgnoreCase("aviable");
        }
    }
}

