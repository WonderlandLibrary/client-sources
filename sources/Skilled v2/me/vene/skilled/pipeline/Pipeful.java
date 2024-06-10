 package me.vene.skilled.pipeline;
 
 import io.netty.channel.ChannelDuplexHandler;
 import io.netty.channel.ChannelHandlerContext;
 import io.netty.channel.ChannelPromise;
 import me.vene.skilled.SkilledClient;
 import me.vene.skilled.modules.Module;
 import me.vene.skilled.modules.ModuleManager;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.entity.EntityOtherPlayerMP;
 import net.minecraft.entity.Entity;
 import net.minecraft.network.play.server.S18PacketEntityTeleport;
 import net.minecraft.network.play.server.S19PacketEntityStatus;
 import net.minecraft.world.World;
 
public class Pipeful extends ChannelDuplexHandler
{
    private Minecraft mc;
    
    public Pipeful() {
        this.mc = Minecraft.getMinecraft();
    }
    
    public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof S19PacketEntityStatus) {
            final S19PacketEntityStatus packet = (S19PacketEntityStatus)msg;
            if (packet.getOpCode() == 2) {
                final Entity entity = packet.getEntity((World)this.mc.theWorld);
                if (entity != null && entity instanceof EntityOtherPlayerMP && System.currentTimeMillis() - SkilledClient.getInstance().getEventManager().getLastAttack() <= 500L && ((EntityOtherPlayerMP)entity).getDisplayNameString().equals(SkilledClient.getInstance().getEventManager().getLastAttackedEntityName())) {
                    for (final Module module : ModuleManager.getModules()) {
                        if (module.getState()) {
                            module.onHurtAnimation();
                        }
                    }
                }
            }
        }
        else if (msg instanceof S18PacketEntityTeleport) {
            S18PacketEntityTeleport packet2 = (S18PacketEntityTeleport)msg;
            for (final Module module2 : ModuleManager.getModules()) {
                if (module2.getState()) {
                    try {
                        packet2 = module2.onEntityTeleport(packet2);
                    }
                    catch (Exception ex) {}
                }
            }
            msg = packet2;
        }
        super.channelRead(ctx, msg);
    }
    
    public void write(final ChannelHandlerContext ctx, final Object msg, final ChannelPromise promise) throws Exception {
        super.write(ctx, msg, promise);
    }
}
