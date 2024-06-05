package de.dietrichpaul.clientbase.feature.hack.combat;

import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.event.KeyPressedStateListener;
import de.dietrichpaul.clientbase.event.TargetPickListener;
import de.dietrichpaul.clientbase.event.UpdateListener;
import de.dietrichpaul.clientbase.feature.hack.Hack;
import de.dietrichpaul.clientbase.feature.hack.HackCategory;
import de.dietrichpaul.clientbase.property.impl.BooleanProperty;
import de.dietrichpaul.clientbase.property.impl.IntProperty;
import de.dietrichpaul.clientbase.util.math.MathUtil;
import de.dietrichpaul.clientbase.util.minecraft.ChatUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class SuperKnockbackHack extends Hack implements KeyPressedStateListener, UpdateListener {

    private final IntProperty hurtTimeProperty = new IntProperty("HurtTime", 7, 0, 10);
    private final BooleanProperty debugProperty = new BooleanProperty("Debug", false);

    public SuperKnockbackHack() {
        super("SuperKnockback", HackCategory.COMBAT);
        addProperty(hurtTimeProperty);
        addProperty(debugProperty);
    }

    @Override
    protected void onEnable() {
        ClientBase.INSTANCE.getEventDispatcher().subscribe(KeyPressedStateListener.class, this);
        ClientBase.INSTANCE.getEventDispatcher().subscribe(UpdateListener.class, this);
    }

    @Override
    protected void onDisable() {
        ClientBase.INSTANCE.getEventDispatcher().unsubscribeInternal(KeyPressedStateListener.class, this);
        ClientBase.INSTANCE.getEventDispatcher().unsubscribeInternal(UpdateListener.class, this);
    }

    private int releaseW;

    @Override
    public void onKeyPressedState(KeyPressedStateEvent event) {
        if (event.keyBinding != mc.options.forwardKey)
            return;

        boolean canSprint = mc.options.sprintKey.isPressed();
        Entity target = ClientBase.INSTANCE.getEventDispatcher()
                .post(new TargetPickListener.TargetPickEvent(null)).getTarget();
        if (target instanceof LivingEntity living) {
            Vec3d camera = mc.player.getCameraPosVec(1.0F);
            Box dimension = living.getBoundingBox(EntityPose.STANDING);
            Box now = dimension.offset(target.getPos());
            Box nextTick = dimension.offset(living.serverX, living.serverY, living.serverZ);

            Vec3d closestNow = MathUtil.clamp(camera, now);
            Vec3d closestNextTick = MathUtil.clamp(camera, nextTick);

            boolean isNextTickFurtherAway = camera.distanceTo(closestNextTick)
                    > camera.distanceTo(closestNow);

            if (releaseW == 0 && canSprint && event.pressed && living.hurtTime >= hurtTimeProperty.getValue() && isNextTickFurtherAway) {
                releaseW = 1;
                if (debugProperty.getState())
                    ChatUtil.sendChatMessage(Text.of("WTAP"));
            }
            if (releaseW != 0) {
                event.pressed = false;
            }
        }
    }

    @Override
    public void onUpdate() {
        if (releaseW > 0)
            releaseW--;
    }
}
