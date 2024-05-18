package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.client.*;
import com.google.common.collect.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import org.spongepowered.asm.mixin.injection.*;
import wwe.modules.render.*;
import wwe.*;
import net.minecraft.util.math.*;

@Mixin(value = { GuiOverlayDebug.class }, priority = 9999)
public class MixinGuiOverlayDebug
{
    @Final
    @Shadow
    private Minecraft field_175242_a;
    
    public MixinGuiOverlayDebug() {
        super();
    }
    
    @Inject(method = { "call" }, at = { @At("HEAD") }, cancellable = true)
    protected void call(final CallbackInfoReturnable<List<String>> callbackInfoReturnable) {
        final BlockPos blockPos = new BlockPos(this.mc.getRenderViewEntity().posX, this.mc.getRenderViewEntity().getEntityBoundingBox().minY, this.mc.getRenderViewEntity().posZ);
        if (this.mc.isReducedDebug()) {
            callbackInfoReturnable.setReturnValue(Lists.newArrayList(this.getValue(0, "Minecraft 1.12.2") + " (" + this.getValue(1, this.mc.getVersion()) + "/" + this.getValue(2, ClientBrandRetriever.getClientModName()) + ")", this.mc.debug, this.mc.renderGlobal.getDebugInfoRenders(), this.mc.renderGlobal.getDebugInfoEntities(), "P: " + this.mc.effectRenderer.getStatistics() + ". T: " + this.mc.world.getDebugLoadedEntities(), this.mc.world.getProviderName(), "", String.format("Chunk-relative: %d %d %d", blockPos.getX() & 0xF, blockPos.getY() & 0xF, blockPos.getZ() & 0xF)));
        }
        else {
            final Entity renderViewEntity = this.mc.getRenderViewEntity();
            final EnumFacing horizontalFacing = renderViewEntity.getHorizontalFacing();
            String s = "Invalid";
            switch (horizontalFacing) {
                case NORTH: {
                    s = "Towards negative Z";
                    break;
                }
                case SOUTH: {
                    s = "Towards positive Z";
                    break;
                }
                case WEST: {
                    s = "Towards negative X";
                    break;
                }
                case EAST: {
                    s = "Towards positive X";
                    break;
                }
            }
            final ArrayList<String> arrayList = Lists.newArrayList(this.getValue(0, "Minecraft 1.12.2") + " (" + this.getValue(1, this.mc.getVersion()) + "/" + this.getValue(2, ClientBrandRetriever.getClientModName()) + ("release".equalsIgnoreCase(this.mc.getVersionType()) ? "" : ("/" + this.mc.getVersionType())) + ")", this.mc.debug, this.mc.renderGlobal.getDebugInfoRenders(), this.mc.renderGlobal.getDebugInfoEntities(), "P: " + this.mc.effectRenderer.getStatistics() + ". T: " + this.mc.world.getDebugLoadedEntities(), this.mc.world.getProviderName(), "", (String)this.formatCoords(3, 4, 5), (String)this.formatBlockCoords(blockPos, 6, 7, 8), (String)this.formatChunkCoords(blockPos, 9, 10, 11, 12, 13, 14), (String)this.formatFacing(horizontalFacing, s, renderViewEntity, 15, 16, 17, 18));
            if (this.mc.world != null) {
                final Chunk chunk = this.mc.world.getChunk(blockPos);
                if (this.mc.world.isBlockLoaded(blockPos) && blockPos.getY() >= 0 && blockPos.getY() < 256) {
                    if (!chunk.isEmpty()) {
                        arrayList.add("Biome: " + this.getValue(19, chunk.getBiome(blockPos, this.mc.world.getBiomeProvider()).getBiomeName()));
                        arrayList.add("Light: " + this.getValue(20, chunk.getLightSubtracted(blockPos, 0) + " (" + chunk.getLightFor(EnumSkyBlock.SKY, blockPos) + " sky, " + chunk.getLightFor(EnumSkyBlock.BLOCK, blockPos) + " block)"));
                        DifficultyInstance difficultyInstance = this.mc.world.getDifficultyForLocation(blockPos);
                        if (this.mc.isIntegratedServerRunning() && this.mc.getIntegratedServer() != null) {
                            final EntityPlayerMP playerByUUID = this.mc.getIntegratedServer().getPlayerList().getPlayerByUUID(this.mc.player.getUniqueID());
                            if (playerByUUID != null) {
                                difficultyInstance = playerByUUID.world.getDifficultyForLocation(new BlockPos((Entity)playerByUUID));
                            }
                        }
                        arrayList.add((String)this.formatDifficulty(difficultyInstance, blockPos, 21, 22, 23));
                    }
                    else {
                        arrayList.add("Waiting for chunk...");
                    }
                }
                else {
                    arrayList.add("Outside of world...");
                }
            }
            if (this.mc.entityRenderer != null && this.mc.entityRenderer.isShaderActive()) {
                arrayList.add("Shader: " + this.mc.entityRenderer.getShaderGroup().getShaderGroupName());
            }
            if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK && this.mc.objectMouseOver.getBlockPos() != null) {
                arrayList.add((String)this.formatLookingAt(this.mc.objectMouseOver.getBlockPos(), 24, 25, 26));
            }
            callbackInfoReturnable.setReturnValue(arrayList);
        }
        callbackInfoReturnable.cancel();
    }
    
    public boolean shouldChange(final int n) {
        return vlwv.hNxW.qHPF((Class<F3Spoofer>)FZwI.class).IuiN() && !F3Spoofer.HHRM.get(n).wgqj;
    }
    
    public String getValue(final int n, final String s) {
        return this.shouldChange(n) ? F3Spoofer.HHRM.get(n).Plwx : s;
    }
    
    public Object formatCoords(final int... array) {
        return "XYZ: " + (this.shouldChange(array[0]) ? F3Spoofer.HHRM.get(array[0]).Plwx : String.format("%.3f", this.mc.getRenderViewEntity().posX)) + " / " + (this.shouldChange(array[1]) ? F3Spoofer.HHRM.get(array[1]).Plwx : String.format("%.5f", this.mc.getRenderViewEntity().getEntityBoundingBox().minY)) + " / " + (this.shouldChange(array[2]) ? F3Spoofer.HHRM.get(array[2]).Plwx : String.format("%.3f", this.mc.getRenderViewEntity().posZ));
    }
    
    public Object formatBlockCoords(final BlockPos blockPos, final int... array) {
        return "Block: " + (this.shouldChange(array[0]) ? F3Spoofer.HHRM.get(array[0]).Plwx : String.format("%d", blockPos.getX())) + " " + (this.shouldChange(array[1]) ? F3Spoofer.HHRM.get(array[1]).Plwx : String.format("%d", blockPos.getY())) + " " + (this.shouldChange(array[2]) ? F3Spoofer.HHRM.get(array[2]).Plwx : String.format("%d", blockPos.getZ()));
    }
    
    public Object formatChunkCoords(final BlockPos blockPos, final int... array) {
        return "Chunk: " + ((this.shouldChange(array[0]) ? F3Spoofer.HHRM.get(array[0]).Plwx : String.format("%d", blockPos.getX() & 0xF)) + " ") + ((this.shouldChange(array[1]) ? F3Spoofer.HHRM.get(array[1]).Plwx : String.format("%d", blockPos.getY() & 0xF)) + " ") + ((this.shouldChange(array[2]) ? F3Spoofer.HHRM.get(array[2]).Plwx : String.format("%d", blockPos.getZ() & 0xF)) + " ") + ((this.shouldChange(array[3]) ? F3Spoofer.HHRM.get(array[3]).Plwx : String.format("%d", blockPos.getX() >> 4)) + " ") + ((this.shouldChange(array[4]) ? F3Spoofer.HHRM.get(array[4]).Plwx : String.format("%d", blockPos.getY() >> 4)) + " ") + (this.shouldChange(array[5]) ? F3Spoofer.HHRM.get(array[5]).Plwx : String.format("%d", blockPos.getZ() >> 4));
    }
    
    public Object formatDifficulty(final DifficultyInstance difficultyInstance, final BlockPos blockPos, final int... array) {
        return "Local Difficulty: " + (this.shouldChange(array[0]) ? F3Spoofer.HHRM.get(array[0]).Plwx : String.format("%.2f", difficultyInstance.getAdditionalDifficulty())) + " // " + ((this.shouldChange(array[1]) ? F3Spoofer.HHRM.get(array[1]).Plwx : String.format("%.2f", difficultyInstance.getClampedAdditionalDifficulty())) + " ") + "(Day " + (this.shouldChange(array[2]) ? F3Spoofer.HHRM.get(array[2]).Plwx : String.format("%d", this.mc.world.getWorldTime() / 24000L)) + ")";
    }
    
    public Object formatLookingAt(final BlockPos blockPos, final int... array) {
        return "Looking at: " + ((this.shouldChange(array[0]) ? F3Spoofer.HHRM.get(array[0]).Plwx : String.format("%d", blockPos.getX())) + " ") + ((this.shouldChange(array[1]) ? F3Spoofer.HHRM.get(array[1]).Plwx : String.format("%d", blockPos.getY())) + " ") + (this.shouldChange(array[2]) ? F3Spoofer.HHRM.get(array[2]).Plwx : String.format("%d", blockPos.getZ()));
    }
    
    public Object formatFacing(final EnumFacing enumFacing, final String s, final Entity entity, final int... array) {
        return "Facing: " + ((this.shouldChange(array[0]) ? F3Spoofer.HHRM.get(array[0]).Plwx : enumFacing) + " ") + ((this.shouldChange(array[1]) ? F3Spoofer.HHRM.get(array[1]).Plwx : ("(" + s + ")")) + " ") + (this.shouldChange(array[2]) ? F3Spoofer.HHRM.get(array[2]).Plwx : ("(" + String.format("%.1f", MathHelper.wrapDegrees(entity.rotationYaw)) + " / ")) + (this.shouldChange(array[3]) ? F3Spoofer.HHRM.get(array[3]).Plwx : (String.format("%.1f", MathHelper.wrapDegrees(entity.rotationPitch)) + ")"));
    }
}
