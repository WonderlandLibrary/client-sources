package ru.FecuritySQ.utils.chunkAnimator;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import ru.FecuritySQ.FecuritySQ;
import ru.FecuritySQ.module.визуальные.ChunkAnimator;
import ru.FecuritySQ.utils.chunkAnimator.easing.*;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.WeakHashMap;

/**
 * This class handles setting up and rendering the animations.
 *
 * @author lumien231
 */
public class AnimationHandler {

	public int mode;

	private final Minecraft mc = Minecraft.getInstance();
	private final WeakHashMap<ChunkRenderDispatcher.ChunkRender, AnimationData> timeStamps = new WeakHashMap<>();

	public void preRender(ChunkRenderDispatcher.ChunkRender chunkRender, @Nullable final MatrixStack matrixStack, double x, double y, double z) {
		ChunkAnimator chunkAnimator = (ChunkAnimator) FecuritySQ.get().getModule(ChunkAnimator.class);

		if(!chunkAnimator.isEnabled()) return;

		final AnimationData animationData = timeStamps.get(chunkRender);

		if (animationData == null)
			return;

		int animationDuration = (int) chunkAnimator.time.get();

		this.mode = chunkAnimator.mode.index;

		long time = animationData.timeStamp;

		// If preRender hasn't been called on this chunk yet, prepare to start the animation.
		if (time == -1L) {
			time = System.currentTimeMillis();

			animationData.timeStamp = time;

			// If using mode 4, set chunkFacing.
			if (mode == 4) {
				animationData.chunkFacing = this.mc.player != null ?
						this.getChunkFacing(this.getZeroedPlayerPos(this.mc.player).subtract(this.getZeroedCenteredChunkPos(chunkRender.getPosition()))) : Direction.NORTH;
			}
		}

		final long timeDif = System.currentTimeMillis() - time;

		if (timeDif < animationDuration) {
			final int chunkY = chunkRender.getPosition().getY();
			final int animationMode = mode == 2 ? (chunkY < Objects.requireNonNull(this.mc.world).getWorldInfo().getVoidFogHeight() ? 0 : 1) : mode == 4 ? 3 : mode;

			switch (animationMode) {
				case 0:
					this.translate(matrixStack, 0, -chunkY + this.getFunctionValue(timeDif, 0, chunkY, animationDuration), 0);
					break;
				case 1:
					this.translate(matrixStack, 0, 256 - chunkY - this.getFunctionValue(timeDif, 0, 256 - chunkY, animationDuration), 0);
					break;
				case 3:
					Direction chunkFacing = animationData.chunkFacing;

					if (chunkFacing != null) {
						final Vector3i vec = chunkFacing.getDirectionVec();
						final double mod = -(200 - this.getFunctionValue(timeDif, 0, 200, animationDuration));

						this.translate(matrixStack, vec.getX() * mod, 0, vec.getZ() * mod);
					}
					break;
			}
		} else {
			this.timeStamps.remove(chunkRender);
		}
	}

	/**
	 * Translates with correct method, depending on whether OptiFine is installed ({@link MatrixStack}
	 * not used so set to null), or not.
	 *
	 * @param matrixStack The {@link MatrixStack} object, or null if OptiFine is loaded.
	 * @param x The x to translate by.
	 * @param y The y to translate by.
	 * @param z The z to translate by.
	 */
	@SuppressWarnings("deprecation")
	private void translate (@Nullable final MatrixStack matrixStack, final double x, final double y, final double z) {
		if (matrixStack == null) {
			GlStateManager.translated(x, y, z); // OptiFine still uses GlStateManager.
		} else {
			matrixStack.translate(x, y, z);
		}
	}

	/**
	 * Gets the function value for the given parameters based on {@link ChunkAnimatorConfig#EASING_FUNCTION}.
	 *
	 * @param t The first function argument.
	 * @param b The second function argument.
	 * @param c The third function argument.
	 * @param d The fourth function argument.
	 * @return The return value of the function.
	 */
	private float getFunctionValue(final float t, @SuppressWarnings("SameParameterValue") final float b, final float c, final float d) {
		switch (mode) {
			case 0: // Linear
				return Linear.easeOut(t, b, c, d);
			case 1: // Quadratic Out
				return Quad.easeOut(t, b, c, d);
			case 2: // Cubic Out
				return Cubic.easeOut(t, b, c, d);
			case 3: // Quartic Out
				return Quart.easeOut(t, b, c, d);
			case 4: // Quintic Out
				return Quint.easeOut(t, b, c, d);
			case 5: // Expo Out
				return Expo.easeOut(t, b, c, d);
			case 6: // Sin Out
				return Sine.easeOut(t, b, c, d);
			case 7: // Circle Out
				return Circ.easeOut(t, b, c, d);
			case 8: // Back
				return Back.easeOut(t, b, c, d);
			case 9: // Bounce
				return Bounce.easeOut(t, b, c, d);
			case 10: // Elastic
				return Elastic.easeOut(t, b, c, d);
		}

		return Sine.easeOut(t, b, c, d);
	}

	public void setPosition(final ChunkRenderDispatcher.ChunkRender renderChunk, final BlockPos position) {
		if (this.mc.player == null)
			return;

		final BlockPos zeroedPlayerPos = this.getZeroedPlayerPos(this.mc.player);
		final BlockPos zeroedCenteredChunkPos = this.getZeroedCenteredChunkPos(position);

		if (zeroedPlayerPos.distanceSq(zeroedCenteredChunkPos) > (32 * 32)) {
			timeStamps.put(renderChunk, new AnimationData(-1L,mode == 3 ?
					this.getChunkFacing(zeroedPlayerPos.subtract(zeroedCenteredChunkPos)) : null));
		} else {
			timeStamps.remove(renderChunk);
		}
	}

	/**
	 * Gets the given player's position, setting their {@code y-coordinate} to {@code 0}.
	 *
	 * @param player The {@link ClientPlayerEntity} instance.
	 * @return The zeroed {@link BlockPos}.
	 */
	private BlockPos getZeroedPlayerPos (final ClientPlayerEntity player) {
		final BlockPos playerPos = player.getPosition();
		return playerPos.add(0, -playerPos.getY(), 0);
	}

	/**
	 * Gets the given {@link BlockPos} for the chunk, setting its {@code y-coordinate} to
	 * {@code 0} and offsetting its {@code x} and {@code y-coordinate} to by {@code 8}.
	 *
	 * @param position The {@link BlockPos} of the chunk.
	 * @return The zeroed, centered {@link BlockPos}.
	 */
	private BlockPos getZeroedCenteredChunkPos(final BlockPos position) {
		return position.add(8, -position.getY(), 8);
	}

	/**
	 * Gets the direction the chunk is facing based on the given {@link Vector3i}
	 * from the relevant position to the chunk.
	 *
	 * @param dif The {@link Vector3i} distance from the relevant position to the chunk.
	 * @return The {@link Direction} of the chunk relative to the {@code dif}.
	 */
	private Direction getChunkFacing(final Vector3i dif) {
		int difX = Math.abs(dif.getX());
		int difZ = Math.abs(dif.getZ());

		return difX > difZ ? dif.getX() > 0 ? Direction.EAST : Direction.WEST : dif.getZ() > 0 ? Direction.SOUTH : Direction.NORTH;
	}

	public void clear () {
		// These should be cleared by GC, but just in case.
		this.timeStamps.clear();
	}

	private static class AnimationData {
		public long timeStamp;
		public Direction chunkFacing;

		public AnimationData(final long timeStamp, final Direction chunkFacing) {
			this.timeStamp = timeStamp;
			this.chunkFacing = chunkFacing;
		}
	}

}