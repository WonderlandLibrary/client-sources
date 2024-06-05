package net.minecraft.network.play.client;

public enum C07PacketPlayerDigging$Action {
   START_DESTROY_BLOCK,
   ABORT_DESTROY_BLOCK,
   STOP_DESTROY_BLOCK,
   DROP_ALL_ITEMS,
   DROP_ITEM,
   RELEASE_USE_ITEM;

   private static final C07PacketPlayerDigging$Action[] $VALUES = new C07PacketPlayerDigging$Action[]{START_DESTROY_BLOCK, ABORT_DESTROY_BLOCK, STOP_DESTROY_BLOCK, DROP_ALL_ITEMS, DROP_ITEM, RELEASE_USE_ITEM};
}
