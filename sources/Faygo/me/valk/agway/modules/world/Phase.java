package me.valk.agway.modules.world;

import java.awt.*;

import me.valk.agway.modes.phase.Old;
import me.valk.agway.modes.phase.SkipMode;
import me.valk.event.EventListener;
import me.valk.event.events.entity.EventEntityCollision;
import me.valk.event.events.player.EventMotion;
import me.valk.event.events.player.EventPlayerUpdate;
import me.valk.event.events.player.EventPushOut;
import me.valk.module.ModData;
import me.valk.module.ModType;
import me.valk.module.Module;
import me.valk.utils.TimerUtils;
import me.valk.utils.Wrapper;
import net.minecraft.client.*;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.block.*;

public class Phase extends Module {
	private int resetNext;
	private boolean vanilla = true;
	private TimerUtils timer = new TimerUtils();

	public Phase() {
		super(new ModData("Phase", 47, new Color(137, 137, 137)), ModType.OTHER);
		addMode(new SkipMode(this));
		addMode(new Old(this));
	}



	public static boolean isInsideBlock() {
		for (int x = MathHelper.floor_double(p.boundingBox.minX); x < MathHelper
				.floor_double(p.boundingBox.maxX) + 1; ++x) {
			for (int y = MathHelper.floor_double(p.boundingBox.minY); y < MathHelper
					.floor_double(p.boundingBox.maxY) + 1; ++y) {
				for (int z = MathHelper.floor_double(p.boundingBox.minZ); z < MathHelper
						.floor_double(p.boundingBox.maxZ) + 1; ++z) {
					final Block block = Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z))
							.getBlock();
					if (block != null && !(block instanceof BlockAir)) {
						AxisAlignedBB boundingBox = block.getCollisionBoundingBox(Wrapper.getWorld(),
								new BlockPos(x, y, z), Wrapper.getWorld().getBlockState(new BlockPos(x, y, z)));
						if (block instanceof BlockHopper) {
							boundingBox = new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1);
						}
						if (boundingBox != null && p.boundingBox.intersectsWith(boundingBox)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
