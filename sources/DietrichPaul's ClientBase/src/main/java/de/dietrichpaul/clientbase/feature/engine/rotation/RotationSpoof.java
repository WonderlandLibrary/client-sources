/*
 * This file is part of Clientbase - https://github.com/DietrichPaul/Clientbase
 * by DietrichPaul, FlorianMichael and contributors
 *
 * To the extent possible under law, the person who associated CC0 with
 * Clientbase has waived all copyright and related or neighboring rights
 * to Clientbase.
 *
 * You should have received a copy of the CC0 legalcode along with this
 * work.  If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package de.dietrichpaul.clientbase.feature.engine.rotation;

import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.feature.engine.rotation.strafe.StrafeMode;
import de.dietrichpaul.clientbase.property.PropertyGroup;
import de.dietrichpaul.clientbase.property.impl.BooleanProperty;
import de.dietrichpaul.clientbase.property.impl.EnumProperty;
import de.dietrichpaul.clientbase.property.impl.FloatProperty;
import de.dietrichpaul.clientbase.util.minecraft.rtx.Raytrace;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.MathHelper;

public abstract class RotationSpoof {

    public final static RotationEngine engine = ClientBase.INSTANCE.getRotationEngine();

    protected MinecraftClient mc = MinecraftClient.getInstance();

    private final FloatProperty minYawSpeed = new FloatProperty("Min Yaw Speed", 20, 1, 180);
    private final FloatProperty maxYawSpeed = new FloatProperty("Max Yaw Speed", 30, 1, 180);
    private final FloatProperty minPitchSpeed = new FloatProperty("Min Pitch Speed", 15, 1, 180);
    private final FloatProperty maxPitchSpeed = new FloatProperty("Max Pitch Speed", 20, 1, 180);
    private final BooleanProperty rotateBack = new BooleanProperty("Rotate Back", false);
    private final BooleanProperty lockView = new BooleanProperty("Lock View", false);
    private final BooleanProperty raytraceProperty = new BooleanProperty("Raytrace", true);
    private final EnumProperty<SensitivityFix> sensitivityFixProperty = new EnumProperty<>("SensitivityFix",
            SensitivityFix.APPROXIMATE, SensitivityFix.values(), SensitivityFix.class);
    private final EnumProperty<StrafeMode> strafeModeProperty = new EnumProperty<>("Strafe",
            StrafeMode.SILENT, StrafeMode.values(), StrafeMode.class);

    boolean hasTarget;

    public RotationSpoof(PropertyGroup propertyGroup) {
        propertyGroup.addProperty(minYawSpeed);
        propertyGroup.addProperty(maxYawSpeed);
        propertyGroup.addProperty(minPitchSpeed);
        propertyGroup.addProperty(maxPitchSpeed);
        propertyGroup.addProperty(raytraceProperty);
        propertyGroup.addProperty(rotateBack);
        propertyGroup.addProperty(lockView);
        propertyGroup.addProperty(sensitivityFixProperty);
        propertyGroup.addProperty(strafeModeProperty);
    }

    /**
     * Sucht target und gibt zurück, ob eins gefunden wurde.
     * Für die implementation: Es sei schlau, würde das Target gespeichert werden,
     * da folgend die #rotate(float[])-Methode aufgerufen wird.
     *
     * @return ob ein Target gefunden wurde
     */
    public abstract boolean pickTarget();

    public abstract void rotate(float[] rotations, float[] prevRotations, boolean tick, float partialTicks);

    public abstract boolean isToggled();

    public abstract int getPriority();

    public void tick() {
    }

    public float getYawSpeed() {
        return MathHelper.lerp((float) Math.random(), minYawSpeed.getValue(), maxYawSpeed.getValue());
    }

    public float getPitchSpeed() {
        return MathHelper.lerp((float) Math.random(), minPitchSpeed.getValue(), maxPitchSpeed.getValue());
    }

    public boolean hasTarget() {
        return hasTarget;
    }

    public boolean rotateBack() {
        return rotateBack.getState();
    }

    public boolean lockView() {
        return lockView.getState();
    }

    public SensitivityFix getSensitivityFix() {
        return sensitivityFixProperty.getValue();
    }

    public StrafeMode getStrafeMode() {
        return strafeModeProperty.getValue();
    }

    public abstract Raytrace getTarget();

    public float getRange() {
        return 3.0F;
    }

    public boolean raytrace() {
        return raytraceProperty.getState();
    }
}
