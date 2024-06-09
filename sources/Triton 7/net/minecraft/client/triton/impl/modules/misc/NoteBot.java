package net.minecraft.client.triton.impl.modules.misc;

import java.util.ArrayList;
import java.util.List;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import net.minecraft.block.BlockNote;
import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.client.triton.management.event.EventTarget;
import net.minecraft.client.triton.management.event.events.UpdateEvent;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.module.Module.Mod;
import net.minecraft.client.triton.management.option.Option.Op;
import net.minecraft.client.triton.utils.ClientUtils;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;

@Mod
public class NoteBot extends Module {
	@Op
	boolean repeat;
	@Op
	boolean extraLook;

	public static GroovyShell groovyShell;

	private class ChordFunction {
		private ChordFunction() {
		}

		private void play(int i) {
			NoteBot.this.notes.add(Integer.valueOf(i));
		}

		public void call(int i) {
			switch (i) {
			case 1:
				play(1);
				play(5);
				play(8);
				break;
			case 2:
				play(2);
				play(6);
				play(9);
				break;
			case 3:
				play(3);
				play(6);
				play(10);
				break;
			case 4:
				play(4);
				play(6);
				play(10);
				break;
			case 5:
				play(5);
				play(7);
				play(12);
				break;
			case 6:
				play(6);
				play(10);
				play(13);
				break;
			case 7:
				play(7);
				play(10);
				play(14);
				break;
			case 8:
				play(8);
				play(12);
				play(15);
				break;
			case 9:
				play(9);
				play(13);
				play(16);
				break;
			case 10:
				play(10);
				play(13);
				play(17);
				break;
			case 11:
				play(11);
				play(15);
				play(18);
				break;
			case 12:
				play(12);
				play(16);
				play(19);
				break;
			case 13:
				play(13);
				play(17);
				play(20);
			}
		}
	}

	private class PlayNoteFunction {
		private PlayNoteFunction() {
		}

		public void call(int i) {
			NoteBot.this.notes.add(Integer.valueOf(i));
		}
	}

	private class RestFunction {
		private RestFunction() {
		}

		public void call(int integer) {
			for (int i = 0; i < integer; i++) {
				NoteBot.this.notes.add(Integer.valueOf(-1));
			}
		}
	}

	public static List<Integer> notes = new ArrayList();
	public static int note;

	public NoteBot() {
		this.notes = new ArrayList();
		this.note = 0;
		this.repeat = true;
		this.extraLook = true;

		Binding binding = new Binding();
		binding.setProperty("play", new PlayNoteFunction());
		binding.setProperty("rest", new RestFunction());
		binding.setProperty("chord", new ChordFunction());
		this.groovyShell = new GroovyShell(binding);
	}

	public void enable() {
		this.note = 0;
		super.enable();
	}

	@EventTarget
	public void onEvent(UpdateEvent event) {
		if (event.getState() == event.getState().PRE) {
			if (this.notes.isEmpty()) {
				return;
			}
			try {
				int noteToPlay = ((Integer) this.notes.get(this.note)).intValue();
				if (noteToPlay != -1) {
					int startX = (int) Math.floor(ClientUtils.mc().thePlayer.posX) - 2;
					int startY = (int) Math.floor(ClientUtils.mc().thePlayer.posY) - 1;
					int startZ = (int) Math.floor(ClientUtils.mc().thePlayer.posZ) - 2;
					int x = startX + noteToPlay % 5;
					int z = startZ + noteToPlay / 5;

					float[] values = BlockHelper.getFacingRotations(x, startY, z,
							BlockHelper.getFacing(new BlockPos(x, startY, z)));
					event.setYaw(values[0]);
					event.setPitch(values[1]);
				}
			} catch (IndexOutOfBoundsException localIndexOutOfBoundsException) {
			}
		}
		if (event.getState() == event.getState().POST) {
			{
				int loops = 0;
				if (this.notes.isEmpty()) {
					return;
				}
				for (;;) {
					if (this.note >= this.notes.size()) {
						this.note = 0;
						if (!this.repeat) {
							super.disable();
						}
					}
					try {
						int noteToPlay = ((Integer) this.notes.get(this.note)).intValue();

						this.note += 1;
						BlockPos pos;
						if (noteToPlay != -1) {
							int startX = (int) Math.floor(ClientUtils.mc().thePlayer.posX) - 2;
							int startY = (int) Math.floor(ClientUtils.mc().thePlayer.posY) - 1;
							int startZ = (int) Math.floor(ClientUtils.mc().thePlayer.posZ) - 2;
							int x = startX + noteToPlay % 5;
							int z = startZ + noteToPlay / 5;

							pos = new BlockPos(x, startY, z);
						} else {
							return;
						}
						if ((ClientUtils.mc().theWorld.getBlockState(pos).getBlock() instanceof BlockNote)) {
							if (this.repeat && (loops != 0)) {
								float[] values = BlockHelper.getFacingRotations(pos.getX(), pos.getY(), pos.getZ(),
										BlockHelper.getFacing(pos));
								ClientUtils.mc().getNetHandler().addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(
										values[0], values[1], ClientUtils.mc().thePlayer.onGround));
							}
							ClientUtils.mc().getNetHandler()
									.addToSendQueue(new C07PacketPlayerDigging(
											C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, pos,
											BlockHelper.getFacing(pos)));
							ClientUtils.mc().getNetHandler().addToSendQueue(new C0APacketAnimation());
							ClientUtils.mc().getNetHandler()
									.addToSendQueue(new C07PacketPlayerDigging(
											C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, pos,
											BlockHelper.getFacing(pos)));
						}
						loops++;
					} catch (IndexOutOfBoundsException localIndexOutOfBoundsException1) {
					}
				}
			}
		}
	}
}
