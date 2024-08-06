package com.shroomclient.shroomclientnextgen.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.network.SequencedPacketCreator;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ClientPlayerInteractionManager.class)
public interface ClientPlayerInteractionManagerAccessor {
    @Invoker("sendSequencedPacket")
    void sendSequencedPacket_(
        ClientWorld world,
        SequencedPacketCreator packetCreator
    );

    @Invoker("syncSelectedSlot")
    void syncSelectedSlot_();

    @Invoker("interactBlockInternal")
    ActionResult interactBlockInternal_(
        ClientPlayerEntity player,
        Hand hand,
        BlockHitResult hitResult
    );
}
