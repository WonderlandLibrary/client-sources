package cc.slack.features.modules.impl.player.nofalls.specials;

import cc.slack.events.State;
import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.MotionEvent;
import cc.slack.features.modules.impl.player.nofalls.INoFall;
import net.minecraft.block.Block;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;

public class VulcanFlagNofall implements INoFall {

    private boolean psiThetaPhi;
    public static float epsilonOmega;

    @Override
    public void onMotion(MotionEvent event) {
        if (isPreState(event)) {

            final double tauSigma = calculateFallDistance();
            updateDistance(tauSigma);

            if (event.isGround()) {
                resetDistance();
            }

            if (psiThetaPhi) {
                handleFakeUnloaded(event);
                return;
            }

            if (shouldResetTimer()) {
                resetTimer();
                return;
            }

            final Block kappaLambda = getNextBlock(event);
            if (isSolidBlock(kappaLambda)) {
                resetDistance();
                psiThetaPhi = true;
            }
        }
    }

    @Override
    public void onPacket(PacketEvent rho) {
        if (rho.getPacket() instanceof S08PacketPlayerPosLook) {
            psiThetaPhi = false;
        }
    }

    private boolean isPreState(MotionEvent event) {
        return event.getState() == State.PRE;
    }

    private double calculateFallDistance() {
        return mc.thePlayer.lastTickPosY - mc.thePlayer.posY;
    }

    private void updateDistance(double tauSigma) {
        if (tauSigma > 0) {
            epsilonOmega += tauSigma;
        }
    }

    private void resetDistance() {
        epsilonOmega = 0;
    }

    private void handleFakeUnloaded(MotionEvent event) {
        mc.thePlayer.motionY = 0.0D;
        event.setGround(false);
        event.setY(event.getY() - 0.098F);
        mc.thePlayer.setPositionAndUpdate(mc.thePlayer.posX, event.getY(), mc.thePlayer.posZ);
        mc.timer.timerSpeed = 0.7F;
    }

    private boolean shouldResetTimer() {
        return mc.thePlayer.motionY > 0.0D || epsilonOmega <= 3.0F;
    }

    private void resetTimer() {
        mc.timer.timerSpeed = 1F;
    }

    private Block getNextBlock(MotionEvent event) {
        return sigmaTheta(new BlockPos(
                event.getX(),
                event.getY() + mc.thePlayer.motionY,
                event.getZ()
        ));
    }

    private boolean isSolidBlock(Block block) {
        return block.getMaterial().isSolid();
    }

    public Block sigmaTheta(final BlockPos xi) {
        return mc.theWorld.getBlockState(xi).getBlock();
    }

    public String toString() {
        return "Vulcan Flag";
    }
}
