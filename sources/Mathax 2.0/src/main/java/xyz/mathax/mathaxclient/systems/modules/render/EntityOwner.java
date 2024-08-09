package xyz.mathax.mathaxclient.systems.modules.render;

import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.render.Render2DEvent;
import xyz.mathax.mathaxclient.mixin.ProjectileEntityAccessor;
import xyz.mathax.mathaxclient.renderer.Renderer2D;
import xyz.mathax.mathaxclient.renderer.text.TextRenderer;
import xyz.mathax.mathaxclient.settings.BoolSetting;
import xyz.mathax.mathaxclient.settings.DoubleSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.network.Executor;
import xyz.mathax.mathaxclient.utils.network.Http;
import xyz.mathax.mathaxclient.utils.render.NametagUtils;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import org.joml.Vector3d;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EntityOwner extends Module {
    private final Map<UUID, String> uuidToName = new HashMap<>();

    private final Vector3d pos = new Vector3d();

    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    private final Setting<Double> scaleSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Scale")
            .description("The scale of the text.")
            .defaultValue(1)
            .min(0)
            .build()
    );

    private final Setting<Boolean> projectilesSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Projectiles")
            .description("Display owner names of projectiles.")
            .defaultValue(false)
            .build()
    );

    public EntityOwner(Category category) {
        super(category, "Entity Owner", "Displays the name of the player who owns the entity you're looking at.");
    }

    @Override
    public void onDisable() {
        uuidToName.clear();
    }

    @EventHandler
    private void onRender2D(Render2DEvent event) {
        for (Entity entity : mc.world.getEntities()) {
            UUID ownerUuid;
            if (entity instanceof TameableEntity tameable) {
                ownerUuid = tameable.getOwnerUuid();
            } else if (entity instanceof AbstractHorseEntity horse) {
                ownerUuid = horse.getOwnerUuid();
            } else if (entity instanceof ProjectileEntity && projectilesSetting.get()) {
                ownerUuid = ((ProjectileEntityAccessor) entity).getOwnerUuid();
            } else {
                continue;
            }

            if (ownerUuid != null) {
                Utils.set(pos, entity, event.tickDelta);
                pos.add(0, entity.getEyeHeight(entity.getPose()) + 0.75, 0);

                if (NametagUtils.to2D(pos, scaleSetting.get())) {
                    renderNametag(getOwnerName(ownerUuid));
                }
            }
        }
    }

    private void renderNametag(String name) {
        TextRenderer text = TextRenderer.get();

        NametagUtils.begin(pos);
        text.beginBig();

        double w = text.getWidth(name);

        double x = -w / 2;
        double y = -text.getHeight();

        Renderer2D.COLOR.begin();
        Renderer2D.COLOR.quad(x - 1, y - 1, w + 2, text.getHeight() + 2, new Color(0, 0, 0, 75));
        Renderer2D.COLOR.render(null);

        text.render(name, x, y, Color.WHITE);

        text.end();
        NametagUtils.end();
    }

    private String getOwnerName(UUID uuid) {
        PlayerEntity player = mc.world.getPlayerByUuid(uuid);
        if (player != null) {
            return player.getEntityName();
        }

        String name = uuidToName.get(uuid);
        if (name != null) {
            return name;
        }

        Executor.execute(() -> {
            if (isEnabled()) {
                String response = Http.get("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString().replace("-", "")).sendString();
                if (response == null) {
                    uuidToName.put(uuid, "Failed to get name");
                    return;
                }

                JSONObject json = new JSONObject(response);
                uuidToName.put(uuid, json.getString("name"));
            }
        });

        name = "Retrieving";
        uuidToName.put(uuid, name);
        return name;
    }
}