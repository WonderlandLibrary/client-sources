package net.minecraft.network;

static final class PacketThreadUtil$1 implements Runnable {
    final /* synthetic */ Packet val$p_180031_0_;
    final /* synthetic */ INetHandler val$p_180031_1_;
    
    @Override
    public void run() {
        this.val$p_180031_0_.processPacket(this.val$p_180031_1_);
    }
}