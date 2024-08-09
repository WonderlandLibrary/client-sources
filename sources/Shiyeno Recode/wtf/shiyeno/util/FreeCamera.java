package wtf.shiyeno.util;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.util.MovementInputFromOptions;

import java.util.UUID;

public class FreeCamera extends ClientPlayerEntity implements IMinecraft {

    private static final ClientPlayNetHandler NETWORK_HANDLER = new ClientPlayNetHandler(IMinecraft.mc, IMinecraft.mc.currentScreen, IMinecraft.mc.getConnection().getNetworkManager(), new GameProfile(UUID.randomUUID(), "user001")) {
        @Override
        public void sendPacket(IPacket<?> packetIn) {
            super.sendPacket(packetIn);
        }
    };

    public FreeCamera(int i) {
        super(IMinecraft.mc, IMinecraft.mc.world, NETWORK_HANDLER, IMinecraft.mc.player.getStats(), IMinecraft.mc.player.getRecipeBook(), false, false);
        
        setEntityId(i);
        movementInput = new MovementInputFromOptions(IMinecraft.mc.gameSettings);
    }

    public void spawn() {
        if (world != null) {
            world.addEntity(this);
        }
    }

    @Override
    public void livingTick() {
        super.livingTick();
    }

    @Override
    public void rotateTowards(double yaw, double pitch) {
        super.rotateTowards(yaw, pitch);
    }
}