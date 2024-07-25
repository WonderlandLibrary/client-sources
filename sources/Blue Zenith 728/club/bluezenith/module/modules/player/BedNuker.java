package club.bluezenith.module.modules.player;

import club.bluezenith.BlueZenith;
import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.PacketEvent;
import club.bluezenith.events.impl.SpawnPlayerEvent;
import club.bluezenith.events.impl.UpdatePlayerEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.modules.combat.Aura;
import club.bluezenith.module.value.types.ActionValue;
import club.bluezenith.module.value.types.BooleanValue;
import club.bluezenith.module.value.types.FloatValue;
import club.bluezenith.util.player.PacketUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;

import static net.minecraft.util.EnumFacing.DOWN;

@SuppressWarnings("unused")
public class BedNuker extends Module {
    private final FloatValue range = new FloatValue("Range", 3f, 1f, 5f, 1f, true, null).setIndex(1);
    private final BooleanValue swing = new BooleanValue("Swing", true, true, null).setIndex(2);
    private final BooleanValue findOwn = new BooleanValue("Whitelist own bed", false, true).setIndex(3);
    private final BooleanValue fast = new BooleanValue("Fast", false, true).setIndex(4);
    private BlockPos ownPos = null;
    private final ActionValue invalidate = new ActionValue("Remove whitelist").setIndex(5).setOnClickListener(() ->
    {
      if(ownPos != null) {
          ownPos = null;
          BlueZenith.getBlueZenith().getNotificationPublisher().postSuccess(displayName, "Removed the bed from whitelist!", 2000);
      } else {
          BlueZenith.getBlueZenith().getNotificationPublisher().postError(displayName, "No bed is whitelisted", 2000);
      }
    });
    private final ActionValue resetTimer = new ActionValue("Reset whitelist timer").setIndex(6).setOnClickListener(() -> {
       tpSpawn = 0;
       BlueZenith.getBlueZenith().getNotificationPublisher().postSuccess(displayName, "Reset whitelist timer. \n Go near the bed you want to whitelist.", 2000);
    });
    public BedNuker() {
        super("BedNuker", ModuleCategory.PLAYER);
    }

    private BlockPos pos = null;
    private Block block = null;
    private float blockDamage = 0;
    private int ticksSinceLastBroken;
    int tpSpawn;

    @Override
    public void onEnable(){
        pos = null;
        block = null;
        blockDamage = 0;
    }

    @Listener
    public void onRespawn(SpawnPlayerEvent event) {
        ownPos = null;
        ticksSinceLastBroken = 999;
    }

    @Listener
    public void onPlayerUpdate(UpdatePlayerEvent event) {
      final Aura aura = getCastedModule(Aura.class);
      if(aura.getState() && aura.getTarget() != null) return;
      final float r = ownPos == null && findOwn.get() && tpSpawn < 40 ? range.get() * range.get() * range.get() : range.get();
      tpSpawn++;
        if(pos == null) {
          for (double x = (player.posX - r); x < (player.posX + r); x++) {
              for (double y = (player.posY - r); y < (player.posY + r); y++) {
                  for (double z = (player.posZ - r); z < (player.posZ + r); z++) {
                      final BlockPos blockAtPos = new BlockPos((int) x, (int) y, (int) z);
                      if (world.getBlockState(blockAtPos).getBlock() instanceof BlockBed) {
                          if (ownPos == null && findOwn.get() && (tpSpawn < 40 || player.ticksExisted < 10) && event.isPre()) {
                              this.ownPos = blockAtPos;
                              this.pos = blockAtPos;
                              BlueZenith.getBlueZenith().getNotificationPublisher().postSuccess(displayName, "Your bed has been whitelisted.", 2000);
                              return;
                          } else {
                              this.pos = blockAtPos;
                          }
                      }
                  }
              }
          }
      } else {
          if(player != null) {
              if(player.getDistanceSqToCenter(pos) > range.get() * range.get()) {
                  pos = null;
              }
          }
      }
          if(!findOwn.get()) {
              ownPos = null;
          }
          if(pos != null) {
              if(ownPos != null) {
                  final boolean eq = ownPos.getX() == pos.getX() && ownPos.getY() == pos.getY() && ownPos.getZ() == pos.getZ();
                  if(eq) {
                      return;
                  }
              }
              if(world == null || player == null || player.ticksExisted % 5 != 0 && !fast.get()) return;
              //totally coded by me
              if(block == null) {
                  block = world.getBlockState(pos).getBlock();
              }
              if (blockDamage <= 0) {
                  PacketUtil.send(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, DOWN));
              }
              if (swing.get()) mc.thePlayer.swingItem();
              else PacketUtil.sendSilent(new C0APacketAnimation());
              blockDamage += block.getPlayerRelativeBlockHardness(mc.thePlayer, mc.theWorld, pos);
              mc.theWorld.sendBlockBreakProgress(mc.thePlayer.getEntityId(), pos, (int) (blockDamage * 10 - 1));
              if (blockDamage >= 1f) {
                  PacketUtil.send(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, DOWN));
                  mc.playerController.onPlayerDestroyBlock(pos, DOWN);
                  blockDamage = 0;
                  pos = null;
                  block = null;
              }
          }
      }

    @Listener
    public void onPacket(PacketEvent event) {
        if(event.packet instanceof S08PacketPlayerPosLook) {
            if(player == null) return;
            final S08PacketPlayerPosLook s = (S08PacketPlayerPosLook) event.packet;
            double x = s.getX() - player.posX;
            double y = s.getY() - player.posY;
            double z = s.getZ() - player.posZ;

            if((x*x + y*y + z*z) > 200) {
                tpSpawn = 0;
            }
        }
    }
    //np
}
