package club.bluezenith.module.modules.render.targethud;

import club.bluezenith.BlueZenith;
import club.bluezenith.events.impl.Render2DEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.modules.combat.Aura;
import club.bluezenith.module.modules.combat.TPAura;
import club.bluezenith.module.modules.render.targethud.impl.Autumn;
import club.bluezenith.module.value.Value;
import club.bluezenith.module.value.types.ExtendedModeValue;
import club.bluezenith.module.value.types.ExtendedModeValue.Mode;
import club.bluezenith.ui.clickgui.ClickGui;
import club.bluezenith.ui.draggables.Draggable;
import club.bluezenith.util.client.Pair;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.optifine.gui.GuiChatOF;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static club.bluezenith.util.client.ClientUtils.runCatching;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;

public class TargetHUD extends Module implements Draggable {

    private final ExtendedModeValue modes = new ExtendedModeValue(this, "Style", new Pair<>("Autumn", new Autumn())).setIndex(Integer.MIN_VALUE);

    private float x, y, width, height;

    private Aura killAura;
    private TPAura teleportAura;

    private final Map<EntityPlayer, TargetHudMode> currentTargetHUDs = new HashMap<>();
    private final List<TargetHudMode> validTargetHUDs = new ArrayList<>();

    public TargetHUD() {
        super("SecondTargetHUD", ModuleCategory.RENDER);

        for (Pair<String, ? extends Mode> mode : this.modes.getAllValues()) {
            this.values.addAll(asList(((TargetHudMode) mode.getValue()).addSettings()));
        }
        this.values.sort(comparingInt(Value::getIndex));

        BlueZenith.getBlueZenith().registerStartupTask(bz -> {
            killAura = bz.getModuleManager().getAndCast(Aura.class);
            teleportAura = bz.getModuleManager().getAndCast(TPAura.class);
        });
    }

    @Override
    public void draw(Render2DEvent event) {
        final TargetHudMode mode = ((TargetHudMode) modes.get().getValue());

        GlStateManager.pushMatrix();

        if(mode.translateToPos())
            GlStateManager.translate(x, y, 0);
        //else //RenderUtil.rect(x, y, x + width, y + height, -1);


        validTargetHUDs.clear();

        AtomicReference<Float> y = new AtomicReference<>(this.y);

        final List<EntityPlayer> targets = getTargets();
        if(targets != null) {
            targets.forEach(target -> {
                TargetHudMode targetHud = currentTargetHUDs.get(target);

                if (targetHud == null) {
                    targetHud = createTargetHud(mode);
                    currentTargetHUDs.put(target, targetHud);
                }
                targetHud.stopDisappearing();

                validTargetHUDs.add(targetHud);
                targetHud.render(event, target, this);
                GlStateManager.translate(0, -(height + 5), 0);
                this.y -= (height + 5);
            });
        }

        final List<EntityPlayer> toRemove = new ArrayList<>();

        currentTargetHUDs.forEach((entity, targetHud) -> {
            if(validTargetHUDs.contains(targetHud)) return;

            targetHud.startDisappearing();
            targetHud.render(event, entity, this);
            GlStateManager.translate(0, this.y -= (height + 5), 0);
            this.y -= (height + 5);

            if(targetHud.doneDisappearing())
                toRemove.add(entity);
        });

        toRemove.forEach(currentTargetHUDs::remove);

        this.y = y.get();
        GlStateManager.popMatrix();
    }

    private TargetHudMode createTargetHud(TargetHudMode currentMode) {
        return runCatching(() -> currentMode.getClass().newInstance());
    }

    private List<EntityPlayer> getTargets() {
        EntityLivingBase target = null;

        if(teleportAura.getState()) {
            if(teleportAura.getTarget() != null)
                target = teleportAura.getTarget();
        }

        if(killAura.getState()) {
            if(killAura.mode.is("Single")) {
                target = killAura.getTarget();
            } else {
                List<EntityLivingBase> auraTargets = killAura.getTargetsOrNull();
                if(auraTargets != null)
                    return auraTargets
                            .stream()
                            .filter(entity -> entity instanceof EntityPlayer)
                            .map(entity -> (EntityPlayer) entity)
                            .collect(toList());
                else target = killAura.getTarget();
            }
        }

        if(target == null && mc.currentScreen instanceof GuiChatOF)
            target = mc.thePlayer;

        return target instanceof EntityPlayer ? newArrayList((EntityPlayer) target) : null;
    }

    @Override
    protected void addDataToConfig(JsonObject configObject) {
        configObject.add("x", new JsonPrimitive(x));
        configObject.add("y", new JsonPrimitive(y));
    }

    @Override
    protected void getDataFromConfig(JsonObject configObject) {
        if(configObject.has("x") && configObject.has("y")) {
            x = configObject.get("x").getAsFloat();
            y = configObject.get("y").getAsFloat();
        }
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    @Override
    public boolean shouldBeRendered() {
        return getState();
    }

    @Override
    public boolean isMouseOver(int mouseX, int mouseY) {
        return ClickGui.i(mouseX, mouseY, x, y, x + width, y + height);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {

    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void moveTo(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String getIdentifier() {
        return "targethud";
    }
}
