package dev.excellent.client.module.impl.misc;

import dev.excellent.api.event.impl.funs.*;
import dev.excellent.api.event.impl.player.UpdateEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.util.other.SoundUtil;
import dev.excellent.impl.value.impl.BooleanValue;
import dev.excellent.impl.value.impl.NumberValue;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3f;

@ModuleInfo(name = "Bad Trip", description = "Человек-яйца. Больше мне добавить нечего :)", category = Category.MISC)
public class BadTrip extends Module {

    public final BooleanValue playersWobble = new BooleanValue("Флекс игроков", this, true);
    public final BooleanValue wobbleMusicWibe = new BooleanValue("Музыка для флекса", this, true, () -> !playersWobble.getValue());
    private final NumberValue wobbleMusicVolume = new NumberValue("Громкость музыки", this, .3F, .05F, 1.F, .025F, () -> !playersWobble.getValue() || !wobbleMusicWibe.getValue());
    public final BooleanValue derpCreeper = new BooleanValue("Крейзи крипер", this, true);
    public final BooleanValue spiderIsAWorm = new BooleanValue("Паук это червь", this, true);
    public final BooleanValue crystalIsABlock = new BooleanValue("Кристалл это блок", this, true);
    public final BooleanValue armPose = new BooleanValue("Руки вверх", this, true);
    private final String roflMusic = "chelegg";
    private final SoundUtil.AudioClipPlayController wobbleMusicTuner = SoundUtil.AudioClipPlayController.build(SoundUtil.AudioClip.build(roflMusic + ".wav", true), () -> playersWobble.getValue() && wobbleMusicWibe.getValue() && isEnabled(), true);
    private final Listener<PlayerArmPoseEvent> onArmPose = event -> {
        if (armPose.getValue()) {
            event.setArmPose(BipedModel.ArmPose.THROW_SPEAR);
        }
    };
    private final Listener<EntityRenderMatrixEvent> listener = event -> {
        if (playersWobble.getValue() && event.getEntity() instanceof PlayerEntity) {
            float wobble = ((System.currentTimeMillis() + event.getEntity().getEntityId() * 100) % 400) / 400F;
            wobble = (wobble > .5 ? 1 - wobble : wobble) * 2F;
            wobble = Mathf.clamp01(wobble);
            event.getMatrix().scale(wobble * 2F + 1, 1 - .5f * wobble, wobble * 2F + 1);
        }

    };
    private final Listener<CreeperResizerPopEvent> listener2 = event -> {
        if (derpCreeper.getValue()) {
            event.setResize(8);
            event.getMatrix().rotate(Vector3f.YP.rotation((float) Math.toRadians(
                    event.getCreeperEntity().getCreeperFlashIntensity(mc.getRenderPartialTicks()) * 1800F
            )));
        }
    };
    private final Listener<RenderSpiderFootEvent> listener3 = event -> {
        if (spiderIsAWorm.getValue()) event.setCancelled();
    };
    private final Listener<CrystalRemodellingEvent> listener4 = event -> {
        if (crystalIsABlock.getValue()) event.setCancelled();
    };
    private final Listener<UpdateEvent> listener5 = event -> {
        updateMusic();
    };

    @Override
    public void onEnable() {
        updateMusic();
        super.onEnable();
    }

    private void updateMusic() {
        wobbleMusicTuner.updatePlayingStatus();
        if (wobbleMusicTuner.isSucessPlaying())
            wobbleMusicTuner.getAudioClip().setVolume(wobbleMusicVolume.getValue().floatValue());
    }

    @Override
    public void onDisable() {
        wobbleMusicTuner.updatePlayingStatus();
        super.onEnable();
    }
}
