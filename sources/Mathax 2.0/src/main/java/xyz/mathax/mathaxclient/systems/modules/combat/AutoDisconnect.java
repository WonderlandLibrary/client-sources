package xyz.mathax.mathaxclient.systems.modules.combat;

import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.settings.BoolSetting;
import xyz.mathax.mathaxclient.settings.IntSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.systems.friends.Friends;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.player.DamageUtils;
import xyz.mathax.mathaxclient.utils.player.InvUtils;
import xyz.mathax.mathaxclient.utils.player.PlayerUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.DisconnectS2CPacket;
import net.minecraft.text.Text;

public class AutoDisconnect extends Module {
    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    private final Setting<Boolean> healthSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Health")
            .description("Automatically disconnect when health is lower or equal to this value.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Integer> healthAmountSetting = generalSettings.add(new IntSetting.Builder()
            .name("Health amount")
            .description("Health disconnect value.")
            .defaultValue(6)
            .range(0, 36)
            .sliderRange(0, 36)
            .build()
    );

    private final Setting<Boolean> totemsSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Totems")
            .description("Automatically disconnect when totem count is lower or equal to this value.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Integer> totemsCountSetting = generalSettings.add(new IntSetting.Builder()
            .name("Totem count")
            .description("Totem disconnect count.")
            .defaultValue(1)
            .range(0, 37)
            .sliderRange(0, 37)
            .build()
    );

    private final Setting<Boolean> smartSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Smart")
            .description("Disconnect when you're about to take enough damage to kill you.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> onlyTrustedSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Only trusted")
            .description("Disconnect when a player not on your friends list appears in render distance.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> instantDeathSetting = generalSettings.add(new BoolSetting.Builder()
            .name("32K")
            .description("Disconnect when a player near you can instantly kill you.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> crystalNearbySetting = generalSettings.add(new BoolSetting.Builder()
            .name("Crystal nearby")
            .description("Disconnect when a crystal appears near you.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Integer> crystalRangeSetting = generalSettings.add(new IntSetting.Builder()
            .name("Crystal range")
            .description("How close a crystal has to be to you before you disconnect.")
            .defaultValue(4)
            .range(1, 10)
            .sliderRange(1, 5)
            .visible(crystalNearbySetting::get)
            .build()
    );

    private final Setting<Boolean> smartToggleSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Smart toggle")
            .description("Disable Auto Log after a low-health logout. Will re-enable once you heal.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> toggleOffSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Toggle off")
            .description("Disable Auto Log after usage.")
            .defaultValue(true)
            .build()
    );

    public AutoDisconnect(Category category) {
        super(category, "Auto Disconnect", "Automatically disconnects you in specific situations.");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        float playerHealth = mc.player.getHealth();
        if (healthSetting.get() && playerHealth <= 0) {
            this.forceToggle(false);
            return;
        }

        if (healthSetting.get() && playerHealth <= healthAmountSetting.get()) {
            mc.player.networkHandler.onDisconnect(new DisconnectS2CPacket(Text.literal("[AutoLog] Health was lower than " + healthAmountSetting.get() + ".")));
            if (smartToggleSetting.get()) {
                this.forceToggle(false);
                enableHealthListener();
            }
        }

        if (smartSetting.get() && healthSetting.get() && playerHealth + mc.player.getAbsorptionAmount() - PlayerUtils.possibleHealthReductions() < healthAmountSetting.get()){
            mc.player.networkHandler.onDisconnect(new DisconnectS2CPacket(Text.literal("[AutoLog] Health was going to be lower than " + healthAmountSetting.get() + ".")));
            if (toggleOffSetting.get()) {
                this.forceToggle(false);
            }
        }

        if (totemsSetting.get() && InvUtils.find(Items.TOTEM_OF_UNDYING).count() <= totemsCountSetting.get()) {
            mc.player.networkHandler.onDisconnect(new DisconnectS2CPacket(Text.literal("[AutoLog] Totem count was lower than " + totemsCountSetting.get() + ".")));
            if (toggleOffSetting.get()) {
                this.forceToggle(false);
            }
        }

        for (Entity entity : mc.world.getEntities()) {
            if (entity instanceof PlayerEntity && entity.getUuid() != mc.player.getUuid()) {
                if (onlyTrustedSetting.get() && entity != mc.player && !Friends.get().contains((PlayerEntity) entity)) {
                    mc.player.networkHandler.onDisconnect(new DisconnectS2CPacket(Text.literal("[AutoLog] A non-trusted player appeared in your render distance.")));
                    if (toggleOffSetting.get()) {
                        this.forceToggle(false);
                    }

                    break;
                }

                if (mc.player.distanceTo(entity) < 8 && instantDeathSetting.get() && DamageUtils.getSwordDamage((PlayerEntity) entity, true) > playerHealth + mc.player.getAbsorptionAmount()) {
                    mc.player.networkHandler.onDisconnect(new DisconnectS2CPacket(Text.literal("[AutoLog] Anti-32K measures.")));
                    if (toggleOffSetting.get()) {
                        this.forceToggle(false);
                    }

                    break;
                }
            }

            if (entity instanceof EndCrystalEntity && mc.player.distanceTo(entity) < crystalRangeSetting.get() && crystalNearbySetting.get()) {
                mc.player.networkHandler.onDisconnect(new DisconnectS2CPacket(Text.literal("[AutoLog] End Crystal appeared within specified range.")));
                if (toggleOffSetting.get()) {
                    this.forceToggle(false);
                }
            }
        }
    }

    private class StaticListener {
        @EventHandler
        private void healthListener(TickEvent.Post event) {
            if (isEnabled() || !healthSetting.get()) {
                disableHealthListener();
            } else if (Utils.canUpdate() && !mc.player.isDead() && mc.player.getHealth() > healthAmountSetting.get()) {
                forceToggle(true);
                disableHealthListener();
            }
        }
    }

    private final StaticListener staticListener = new StaticListener();

    private void enableHealthListener(){
        MatHax.EVENT_BUS.subscribe(staticListener);
    }

    private void disableHealthListener(){
        MatHax.EVENT_BUS.unsubscribe(staticListener);
    }
}
