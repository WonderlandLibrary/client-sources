/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  org.apache.commons.io.FileUtils
 *  org.apache.commons.io.LineIterator
 */
package pw.vertexcode.util.module.types;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Timer;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import pw.vertexcode.nemphis.Nemphis;
import pw.vertexcode.nemphis.module.Category;
import pw.vertexcode.nemphis.value.Value;
import pw.vertexcode.util.ClientHelper;
import pw.vertexcode.util.ILoadable;
import pw.vertexcode.util.ISerializable;
import pw.vertexcode.util.event.EventManager;
import pw.vertexcode.util.module.Module;
import pw.vertexcode.util.module.ModuleInformation;

public class ToggleableModule
implements Module,
ISerializable,
ILoadable {
    private boolean enabled;
    private int keybind;
    private List<Value> values = new ArrayList<Value>();
    public String renderMode = ".";
    public List<String> modes = new ArrayList<String>();
    public boolean clicked = false;

    public ModuleInformation getInformation() {
        if (this.getClass().isAnnotationPresent(ModuleInformation.class)) {
            return this.getClass().getAnnotation(ModuleInformation.class);
        }
        ClientHelper.crash("Module class <" + this.getClass().getSimpleName() + "> doesn't got the required information");
        return null;
    }

    @Override
    public String getName() {
        return this.getInformation().name();
    }

    public int getColor() {
        return this.getInformation().color();
    }

    public Category getCategory() {
        return this.getInformation().category();
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public int getKeybind() {
        return this.keybind;
    }

    public void setKeybind(int keybind) {
        this.keybind = keybind;
    }

    @Override
    public void onEnabled() {
    }

    @Override
    public void onDisable() {
    }

    public void toggleModule() {
        this.enabled = !this.isEnabled();
        System.out.println(this.enabled);
        if (this.isEnabled()) {
            EventManager.registerClass(this);
            this.onEnabled();
        } else {
            ToggleableModule.mc.timer.timerSpeed = 1.0f;
            EventManager.unregisterClass(this);
            this.onDisable();
        }
    }

    public String getRenderMode() {
        return this.renderMode;
    }

    public void setRenderMode(String renderMode) {
        this.renderMode = renderMode;
    }

    @Override
    public String serialize() {
        String serialized = "";
        serialized = String.valueOf(serialized) + this.getName() + ":";
        serialized = String.valueOf(serialized) + this.isEnabled() + ":";
        serialized = String.valueOf(serialized) + this.getKeybind() + ":";
        serialized = String.valueOf(serialized) + "[";
        for (Value value : this.getValues()) {
            serialized = String.valueOf(serialized) + value.getName() + "=";
            serialized = String.valueOf(serialized) + new Gson().toJson(value.getValue()) + "=";
            serialized = String.valueOf(serialized) + value.getValue().getClass().toString().replace("class ", "");
            serialized = String.valueOf(serialized) + ";";
        }
        serialized = String.valueOf(serialized) + "]";
        return serialized;
    }

    @Override
    public void save() throws IOException {
        StringBuilder builder;
        File moduleFile;
        File moduleDir;
        File clientDir = Nemphis.instance.directory;
        if (!clientDir.exists()) {
            clientDir.mkdir();
        }
        if (!(moduleDir = new File(clientDir, "Modules")).exists()) {
            moduleDir.mkdir();
        }
        moduleFile = new File(moduleDir, "modules.cfg");
        builder = new StringBuilder();
        if (moduleFile.exists()) {
            LineIterator it = FileUtils.lineIterator((File)moduleFile, (String)"UTF-8");
            try {
                while (it.hasNext()) {
                    String line = it.nextLine();
                    if (line.startsWith(this.getName())) continue;
                    builder.append(line).append(System.lineSeparator());
                }
            }
            finally {
                LineIterator.closeQuietly((LineIterator)it);
            }
        }
        builder.append(this.serialize()).append(System.lineSeparator());
        BufferedWriter writer = new BufferedWriter(new FileWriter(moduleFile));
        writer.write(builder.toString());
        writer.close();
    }

    @Override
    public void load() throws IOException, ClassNotFoundException {
        String line;
        File clientDir = Nemphis.instance.directory;
        if (!clientDir.exists()) {
            return;
        }
        File moduleDir = new File(clientDir, "Modules");
        if (!moduleDir.exists()) {
            return;
        }
        File moduleFile = new File(moduleDir, "modules.cfg");
        BufferedReader reader = new BufferedReader(new FileReader(moduleFile));
        while ((line = reader.readLine()) != null) {
            String[] values;
            String[] splitted = line.split(":");
            String moduleLabel = splitted[0];
            if (!moduleLabel.equals(this.getName())) continue;
            boolean enabled = Boolean.parseBoolean(splitted[1]);
            if (enabled) {
                this.toggleModule();
            }
            int bind = Integer.parseInt(splitted[2].toUpperCase());
            this.setKeybind(bind);
            String vals = splitted[3];
            vals = vals.replace("[", "");
            vals = vals.replace("]", "");
            if (vals.equals("")) continue;
            String[] arrstring = values = vals.split(";");
            int n = arrstring.length;
            int n2 = 0;
            while (n2 < n) {
                String value = arrstring[n2];
                String[] splittedValue = value.split("=");
                if (splittedValue.length == 3) {
                    String valueLabel = splittedValue[0];
                    String valueClass = splittedValue[2];
                    Object valueObject = new Gson().fromJson(splittedValue[1], Class.forName(valueClass));
                    this.getValue(valueLabel).setValue(valueObject);
                }
                ++n2;
            }
        }
    }

    public void sendMessage(String text, boolean prefix) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(prefix ? String.valueOf(String.valueOf(Nemphis.instance.getPrefix())) + " " + text : text));
    }

    public void setSpeed(float speed) {
        ToggleableModule.mc.thePlayer.motionX = - Math.sin(this.getDirection()) * (double)speed;
        ToggleableModule.mc.thePlayer.motionZ = Math.cos(this.getDirection()) * (double)speed;
    }

    public float getSpeed() {
        float vel = (float)Math.sqrt(ToggleableModule.mc.thePlayer.motionX * ToggleableModule.mc.thePlayer.motionX + ToggleableModule.mc.thePlayer.motionZ * ToggleableModule.mc.thePlayer.motionZ);
        return vel;
    }

    public float getDirection() {
        float var1 = ToggleableModule.mc.thePlayer.rotationYaw;
        if (ToggleableModule.mc.thePlayer.moveForward < 0.0f) {
            var1 += 180.0f;
        }
        float forward = 1.0f;
        if (ToggleableModule.mc.thePlayer.moveForward < 0.0f) {
            forward = -0.5f;
        } else if (ToggleableModule.mc.thePlayer.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (ToggleableModule.mc.thePlayer.moveStrafing > 0.0f) {
            var1 -= 90.0f * forward;
        }
        if (ToggleableModule.mc.thePlayer.moveStrafing < 0.0f) {
            var1 += 90.0f * forward;
        }
        return var1 *= 0.017453292f;
    }

    public List<Value> getValues() {
        return this.values;
    }

    public void addValue(Value value) {
        this.values.add(value);
    }

    public Value getValue(String label) {
        for (Value value : this.getValues()) {
            if (!value.getName().equalsIgnoreCase(label)) continue;
            return value;
        }
        return null;
    }
}

