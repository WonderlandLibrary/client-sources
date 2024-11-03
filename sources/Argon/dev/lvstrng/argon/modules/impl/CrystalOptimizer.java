package dev.lvstrng.argon.modules.impl;

import dev.lvstrng.argon.event.events.PacketSendEvent;
import dev.lvstrng.argon.event.listeners.PacketSendListener;
import dev.lvstrng.argon.modules.Category;
import dev.lvstrng.argon.modules.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;

public final class CrystalOptimizer extends Module implements PacketSendListener {
    public CrystalOptimizer() {
        super("Crystal Optimizer", "Makes your crystals disappear faster client-side so you can place crystals faster", 0, Category.MISC);
    }

    static MinecraftClient getMinecraftClient(CrystalOptimizer optimizer) {
        return optimizer.mc;
    }

    @Override
    public void onEnable() {
        this.eventBus.registerPriorityListener(PacketSendListener.class, this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.eventBus.unregister(PacketSendListener.class, this);
        super.onDisable();
    }

    @Override
    public void onPacketSend(PacketSendEvent event) {
        Packet packet = event.packet;
        if (packet instanceof PlayerInteractEntityC2SPacket) {
            ((PlayerInteractEntityC2SPacket) packet).handle(new CrystalOptimizerAttackHandler(this));
        }
    }

    public boolean isToolValid(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof ToolItem)) {
            return false;
        }
        ToolMaterial material = ((ToolItem) itemStack.getItem()).getMaterial();
        return material == ToolMaterials.DIAMOND || material == ToolMaterials.NETHERITE;
    }
}