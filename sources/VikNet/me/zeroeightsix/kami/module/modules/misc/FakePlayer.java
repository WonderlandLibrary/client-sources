package me.zeroeightsix.kami.module.modules.misc;

import com.mojang.authlib.GameProfile;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.client.entity.EntityOtherPlayerMP;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created 10 August 2019 by hub
 * Updated 8 December 2019 by hub
 */
@Module.Info(name = "FakePlayer", category = Module.Category.MISC, description = "Spawns a fake Player")
public class FakePlayer extends Module {

    private Setting<SpawnMode> spawnMode = register(Settings.e("Spawn Mode", SpawnMode.SINGLE));

    private List<Integer> fakePlayerIdList = null;

    private enum SpawnMode {
        SINGLE, MULTI
    }

    private static final String[][] fakePlayerInfo =
            {
                    {"66666666-6666-6666-6666-666666666600", "derp0", "-3", "0"},
                    {"66666666-6666-6666-6666-666666666601", "derp1", "0", "-3"},
                    {"66666666-6666-6666-6666-666666666602", "derp2", "3", "0"},
                    {"66666666-6666-6666-6666-666666666603", "derp3", "0", "3"},
                    {"66666666-6666-6666-6666-666666666604", "derp4", "-6", "0"},
                    {"66666666-6666-6666-6666-666666666605", "derp5", "0", "-6"},
                    {"66666666-6666-6666-6666-666666666606", "derp6", "6", "0"},
                    {"66666666-6666-6666-6666-666666666607", "derp7", "0", "6"},
                    {"66666666-6666-6666-6666-666666666608", "derp8", "-9", "0"},
                    {"66666666-6666-6666-6666-666666666609", "derp9", "0", "-9"},
                    {"66666666-6666-6666-6666-666666666610", "derp10", "9", "0"},
                    {"66666666-6666-6666-6666-666666666611", "derp11", "0", "9"}
            };

    @Override
    protected void onEnable() {

        if (mc.player == null || mc.world == null) {
            this.disable();
            return;
        }

        fakePlayerIdList = new ArrayList<>();

        int entityId = -101;

        for (String[] data : fakePlayerInfo) {

            if (spawnMode.getValue().equals(SpawnMode.SINGLE)) {
                addFakePlayer(data[0], data[1], entityId, 0, 0);
                break;
            } else {
                addFakePlayer(data[0], data[1], entityId, Integer.parseInt(data[2]), Integer.parseInt(data[3]));
            }

            entityId--;

        }

    }

    private void addFakePlayer(String uuid, String name, int entityId, int offsetX, int offsetZ) {

        EntityOtherPlayerMP fakePlayer = new EntityOtherPlayerMP(mc.world, new GameProfile(UUID.fromString(uuid), name));
        fakePlayer.copyLocationAndAnglesFrom(mc.player);
        fakePlayer.posX = fakePlayer.posX + offsetX;
        fakePlayer.posZ = fakePlayer.posZ + offsetZ;
        mc.world.addEntityToWorld(entityId, fakePlayer);
        fakePlayerIdList.add(entityId);

    }

    @Override
    public void onUpdate() {

        if (fakePlayerIdList == null || fakePlayerIdList.isEmpty() ) {
            this.disable();
        }

    }

    @Override
    protected void onDisable() {

        if (mc.player == null || mc.world == null) {
            return;
        }

        if (fakePlayerIdList != null) {
            for (int id : fakePlayerIdList) {
                mc.world.removeEntityFromWorld(id);
            }
        }

    }

}
