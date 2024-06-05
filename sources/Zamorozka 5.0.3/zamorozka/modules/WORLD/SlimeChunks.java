package zamorozka.modules.WORLD;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.network.play.server.SPacketChunkData;
import net.minecraft.util.math.AxisAlignedBB;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventReceivePacket;
import zamorozka.event.events.EventRender;
import zamorozka.event.events.EventRender3D;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.RendUtil;
import zamorozka.ui.WorldManager;

public class SlimeChunks extends Module {

	private ICamera frustum = new Frustum();

    private List<SlimeChunk> slimeChunkList = new ArrayList<>();
	
	public SlimeChunks() {
		super("SlimeChunks", 0, Category.WORLD);
	}
	
	@EventTarget
	public void onRender(EventRender3D event) {
	        for (SlimeChunk slimeChunk : this.slimeChunkList) {
	            if (slimeChunk != null) {
	                this.frustum.setPosition(Minecraft.getMinecraft().getRenderViewEntity().posX, Minecraft.getMinecraft().getRenderViewEntity().posY, Minecraft.getMinecraft().getRenderViewEntity().posZ);

	                final AxisAlignedBB bb = new AxisAlignedBB(slimeChunk.x, 0, slimeChunk.z, slimeChunk.x + 16, 1, slimeChunk.z + 16);

	                if (frustum.isBoundingBoxInFrustum(bb)) {
	                    RendUtil.drawPlane(slimeChunk.x - Minecraft.getMinecraft().getRenderManager().viewerPosX, -Minecraft.getMinecraft().getRenderManager().viewerPosY, slimeChunk.z - Minecraft.getMinecraft().getRenderManager().viewerPosZ, new AxisAlignedBB(0, 0, 0, 16, 1, 16), 2, 0xFF00FF00);
	                }
	            }
	        }
	}
	
	@EventTarget
	public void receivePacket(EventReceivePacket event) {
		if (event.getPacket() instanceof SPacketChunkData) {
            final SPacketChunkData packet = (SPacketChunkData) event.getPacket();
            final SlimeChunk chunk = new SlimeChunk(packet.getChunkX() * 16, packet.getChunkZ() * 16);

            final ServerData serverData = Minecraft.getMinecraft().getCurrentServerData();
            if (serverData != null) {
                final WorldManager.WorldData worldData = Zamorozka.instance.getWorldManager().find(serverData.serverIP);
                if (worldData != null) {
                    if (!this.slimeChunkList.contains(chunk) && this.isSlimeChunk(worldData.getSeed(), packet.getChunkX(), packet.getChunkZ())) {
                        this.slimeChunkList.add(chunk);
                    }
                }
            }
		}
	}
	
	private boolean isSlimeChunk(final long seed, int x, int z) {
        final Random rand = new Random(seed +
                (long) (x * x * 0x4c1906) +
                (long) (x * 0x5ac0db) +
                (long) (z * z) * 0x4307a7L +
                (long) (z * 0x5f24f) ^ 0x3ad8025f);
        return rand.nextInt(10) == 0;
    }

    public static class SlimeChunk {
        private int x;
        private int z;

        public SlimeChunk(int x, int z) {
            this.x = x;
            this.z = z;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getZ() {
            return z;
        }

        public void setZ(int z) {
            this.z = z;
        }
    }
	
}