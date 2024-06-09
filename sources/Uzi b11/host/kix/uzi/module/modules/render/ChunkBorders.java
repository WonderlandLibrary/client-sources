package host.kix.uzi.module.modules.render;

import com.darkmagician6.eventapi.SubscribeEvent;
import host.kix.uzi.events.RenderWorldEvent;
import host.kix.uzi.module.Module;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.chunk.Chunk;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Kix on 5/30/2017.
 * Made for the eclipse project.
 */
public class ChunkBorders extends Module {

    public boolean spawnChunks;
    public double[][] spawnChunkCorners;
    private Chunk[] borderizedChunks;
    private int prevChunkX;
    private int prevChunkZ;
    private boolean updateChunks;

    public ChunkBorders() {
        super("ChunkBorders", 0, Category.RENDER);
        this.spawnChunks = false;
        this.spawnChunkCorners = new double[4][2];
        this.prevChunkX = 0;
        this.prevChunkZ = 0;
        this.updateChunks = false;
    }

    @SubscribeEvent()
    public void renderWorld(RenderWorldEvent event) {
        final EntityPlayer player = mc.thePlayer;
        final WorldClient world = mc.theWorld;
        if (this.prevChunkX != player.chunkCoordX || this.prevChunkZ != player.chunkCoordZ || this.borderizedChunks == null || this.updateChunks) {
            this.updateChunks = false;
            this.prevChunkX = player.chunkCoordX;
            this.prevChunkZ = player.chunkCoordZ;
            this.updateChunkList(world, mc.thePlayer, 2);
        }
        double renderY = (64 - mc.getRenderManager().viewerPosY);

        glPushMatrix();
        GlStateManager.color(218f, 118f, 42f);
        glDisable(GL11.GL_TEXTURE_2D);
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        for (final Chunk chunk : this.borderizedChunks) {
            if (chunk == null) {
                this.updateChunks = true;
            } else {
                double chunkX = ((double) chunk.xPosition * 16D) - mc.getRenderManager().viewerPosX;
                double chunkZ = ((double) chunk.zPosition * 16D) - mc.getRenderManager().viewerPosZ;
                worldRenderer.startDrawing(GL11.GL_LINE_LOOP);
                worldRenderer.addVertex(chunkX, renderY, chunkZ);
                worldRenderer.addVertex(chunkX + 16D, renderY, chunkZ);
                worldRenderer.addVertex(chunkX + 16D, renderY, chunkZ + 16D);
                worldRenderer.addVertex(chunkX, renderY, chunkZ + 16D);
                tessellator.draw();
            }
        }
        if (this.spawnChunks && this.isPosInRenderableArea(player, world.getSpawnPoint().getX(), world.getSpawnPoint().getZ())) {
            this.renderSpawnArea(world, renderY);
            this.renderSpawnChunks(world, renderY);
        }
        glPopMatrix();
    }

    private void renderSpawnArea(WorldClient world, double renderY) {
        double spawnX = world.getSpawnPoint().getX() - mc.getRenderManager().viewerPosX;
        double spawnZ = world.getSpawnPoint().getZ() - mc.getRenderManager().viewerPosZ;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        GlStateManager.color(0F, 1F, 0F, 1F);
        worldRenderer.startDrawing(GL_LINE_LOOP);
        worldRenderer.addVertex((double) (spawnX + 10D), renderY + 0.1D, (double) (spawnZ + 10D));
        worldRenderer.addVertex((double) (spawnX + 10D), renderY + 0.1D, (double) (spawnZ - 10D));
        worldRenderer.addVertex((double) (spawnX - 10D), renderY + 0.1D, (double) (spawnZ - 10D));
        worldRenderer.addVertex((double) (spawnX - 10D), renderY + 0.1D, (double) (spawnZ + 10D));
        tessellator.draw();
    }

    private void renderSpawnChunks(WorldClient world, double renderY) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        GlStateManager.color(0F, 1F, 1F, 1F);
        worldRenderer.startDrawing(GL_LINE_LOOP);
        worldRenderer.addVertex(this.spawnChunkCorners[0][0] - mc.getRenderManager().viewerPosX, renderY + 0.1D, this.spawnChunkCorners[0][1] - mc.getRenderManager().viewerPosZ);
        worldRenderer.addVertex(this.spawnChunkCorners[1][0] - mc.getRenderManager().viewerPosX, renderY + 0.1D, this.spawnChunkCorners[1][1] - mc.getRenderManager().viewerPosZ);
        worldRenderer.addVertex(this.spawnChunkCorners[2][0] - mc.getRenderManager().viewerPosX, renderY + 0.1D, this.spawnChunkCorners[2][1] - mc.getRenderManager().viewerPosZ);
        worldRenderer.addVertex(this.spawnChunkCorners[3][0] - mc.getRenderManager().viewerPosX, renderY + 0.1D, this.spawnChunkCorners[3][1] - mc.getRenderManager().viewerPosZ);
        tessellator.draw();
    }

    private void updateChunkList(WorldClient world, EntityPlayer player, int radius) {
        int startChunkX = player.chunkCoordX - radius;
        int startChunkZ = player.chunkCoordZ - radius;
        int sideLength = radius * 2 + 1;
        this.borderizedChunks = new Chunk[sideLength * sideLength];
        int currentIndex = 0;
        for (int row = 0; row < sideLength; ++row) {
            for (int column = 0; column < sideLength; ++column) {
                Chunk chunk = world.getChunkFromChunkCoords(startChunkX + row, startChunkZ + column);
                if (chunk.isLoaded()) {
                    this.borderizedChunks[currentIndex] = chunk;
                    ++currentIndex;
                }
            }
        }
    }

    /**
     * this function needs to invoked when loading a new world.
     */
    private void updateSpawnChunks(WorldClient world) {
        boolean centerChunk = false;
        if (world.getSpawnPoint().getX() / 16.0 % 0.5 == 0.0 && world.getSpawnPoint().getZ() / 16.0 % 0.5 == 0.0) {
            centerChunk = true;
        }
        int midX = Math.round(world.getSpawnPoint().getX() / 16) * 16;
        int midZ = Math.round(world.getSpawnPoint().getZ() / 16) * 16;
        this.spawnChunkCorners[0][0] = midX + 96 + (centerChunk ? 16 : 0);
        this.spawnChunkCorners[0][1] = midZ + 96 + (centerChunk ? 16 : 0);
        this.spawnChunkCorners[1][0] = midX + 96 + (centerChunk ? 16 : 0);
        this.spawnChunkCorners[1][1] = midZ - 96;
        this.spawnChunkCorners[2][0] = midX - 96;
        this.spawnChunkCorners[2][1] = midZ - 96;
        this.spawnChunkCorners[3][0] = midX - 96;
        this.spawnChunkCorners[3][1] = midZ + 96 + (centerChunk ? 16 : 0);
    }

    private boolean isPosInRenderableArea(EntityPlayer player, int posX, int posZ) {
        return getDistanceSq(player.posX, player.posZ, posX, posZ) < 36864.0;
    }

    private double getDistanceSq(double x1, double z1, double x2, double z2) {
        double xs = x1 - x2;
        double zs = z1 - z2;
        return xs * xs + zs * zs;
    }

}
