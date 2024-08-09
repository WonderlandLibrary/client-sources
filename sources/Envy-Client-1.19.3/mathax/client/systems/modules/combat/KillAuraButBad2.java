package mathax.client.systems.modules.combat;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.render.Render3DEvent;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.*;
import mathax.client.systems.friends.Friends;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.player.Rotations;
import mathax.client.utils.render.OLEPOSSUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;

public class KillAuraButBad2 extends Module {
    public KillAuraButBad2() {
        super(Categories.Combat, Items.AIR, "kill-aura-but-bad-2", "Killaura.");
    }

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Double> range = sgGeneral.add(new DoubleSetting.Builder()
        .name("Range")
        .description("Range to hit in")
        .defaultValue(3)
        .range(0, 6)
        .sliderMax(6)
        .build()
    );

    private final Setting<Boolean> iFriends = sgGeneral.add(new BoolSetting.Builder()
        .name("Ignore Friends")
        .description(".")
        .defaultValue(true)
        .build()
    );

    private final Setting<Double> delay = sgGeneral.add(new DoubleSetting.Builder()
        .name("Delay")
        .description("Delay that will be used for hits")
        .defaultValue(0.500)
        .range(0, 2)
        .sliderMax(2)
        .build()
    );

    private final Setting<Boolean> debug = sgGeneral.add(new BoolSetting.Builder()
        .name("Debug")
        .description("Prints debug stuff")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> rotate = sgGeneral.add(new BoolSetting.Builder()
        .name("Rotate")
        .description("Rotates towards the enemy before attacking")
        .defaultValue(true)
        .build()
    );
    private final Setting<RotationMode> rotMode = sgGeneral.add(new EnumSetting.Builder<RotationMode>()
        .name("Rotation mode")
        .description(".")
        .defaultValue(RotationMode.Constant)
        .visible(rotate::get)
        .build()
    );


    private final Setting<Boolean> noCA = sgGeneral.add(new BoolSetting.Builder()
        .name("Stop on CA")
        .description("Does not hit while a CA is enabled")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> noMenu = sgGeneral.add(new BoolSetting.Builder()
        .name("Don't hit in menus")
        .description("Does not hit in menus")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> autoBlock = sgGeneral.add(new BoolSetting.Builder()
        .name("AutoBlock")
        .description("1.8 module on 1.9+ wow")
        .defaultValue(false)
        .build()
    );
    private final Setting<BlockMode> blockMode = sgGeneral.add(new EnumSetting.Builder<BlockMode>()
        .name("Rotation mode")
        .description(".")
        .defaultValue(BlockMode.Constant)
        .visible(autoBlock::get)
        .build()
    );

    private final Setting<Boolean> onlyHurt = sgGeneral.add(new BoolSetting.Builder()
        .name("Only hit damaged")
        .description("Only hits enemies whose health is below the set threshold ")
        .defaultValue(false)
        .build()
    );
    private final Setting<Integer> healthRange = sgGeneral.add(new IntSetting.Builder()
        .name("Health Threshold")
        .description("Name says it all")
        .defaultValue(2)
        .range(1, 36)
        .sliderMax(36)
        .visible(onlyHurt::get)
        .build()
    );

    public enum RotationMode {
        Snap,
        Constant,
    }
    public enum BlockMode{
        Constant,
        NotOnHit,
    }

    double timer = 0;
    int health = 0;

    PlayerEntity target = null;

    @EventHandler
    private void onRender(Render3DEvent event){
        timer = Math.min(delay.get(),timer + event.frameTime);
    }

    @EventHandler
    private void onTick(TickEvent.Pre event){
        if (mc.player != null && mc.world != null){
            if (debug.get()){info("timer" + timer);}
            target = getClosest();
            if (target != null) {
                double rotYaw = Rotations.getYaw(target.getEyePos());
                double rotPitch = Rotations.getPitch(target.getEyePos());
                if (rotate.get() && rotMode.get().equals(RotationMode.Constant)){
                    Rotations.rotate(rotYaw, rotPitch);
                }
                if (autoBlock.get()){
                    if (mc.player.getOffHandStack().getItem().equals(Items.SHIELD)){
                        mc.options.useKey.setPressed(true);
                    }
                }

                if (timer >= delay.get()) {
                    if (noMenu.get() && mc.currentScreen != null) {
                        return;
                    }
                    if (onlyHurt.get() && health <= healthRange.get()) {
                        return;
                    }
                    health = (int) (target.getHealth() + target.getAbsorptionAmount());
                    timer = 0;
                    if (debug.get()) {
                        info("Timer has passed");
                    }
                    if (rotate.get() && rotMode.get().equals(RotationMode.Snap)) {
                        Rotations.rotate(rotYaw, rotPitch);
                        if (debug.get()) {
                            info("Tried to rotate to target!");
                        }
                    }
                    if (blockMode.get().equals(BlockMode.NotOnHit))
                        mc.options.useKey.setPressed(false);
                    mc.interactionManager.attackEntity(mc.player, target);
                    mc.player.swingHand(Hand.MAIN_HAND);
                    if (debug.get()) {
                        info("Tried to hit target");
                    }
                }
            }
            else {
                if (autoBlock.get()) {mc.options.useKey.setPressed(false);}
            }
        }
    }
    @Override
    public void onDeactivate() {
        if (mc.player != null && mc.world != null){
            if (autoBlock.get())
                mc.options.useKey.setPressed(false);
        }
    }


    PlayerEntity getClosest() {
        PlayerEntity closest = null;
        float distance = -1;
        if (!mc.world.getPlayers().isEmpty()) {
            for (PlayerEntity player : mc.world.getPlayers()) {
                if (player != mc.player && (!iFriends.get() || !Friends.get().isFriend(player))) {
                    float dist = (float) OLEPOSSUtils.distance(mc.player.getEyePos(), player.getPos());
                    if ((closest == null || OLEPOSSUtils.distance(mc.player.getPos(), player.getPos()) < distance) && dist <= range.get()) {
                        closest = player;
                        distance = (float) OLEPOSSUtils.distance(mc.player.getPos(), player.getPos());
                    }
                }
            }
        }
        return closest;
    }
}

