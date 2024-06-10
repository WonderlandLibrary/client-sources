package me.kaimson.melonclient.config;

import com.google.common.collect.*;
import me.kaimson.melonclient.features.*;
import me.kaimson.melonclient.utils.*;
import com.google.gson.*;
import java.io.*;
import java.util.*;
import me.kaimson.melonclient.*;
import me.kaimson.melonclient.config.utils.*;
import java.awt.geom.*;

public class ModuleConfig extends Config
{
    public static final ModuleConfig INSTANCE;
    private final Set<Module> enabled;
    private final Map<Module, Position> positions;
    private final Map<Module, Float> scales;
    
    public ModuleConfig() {
        super("modules", "json", 0.1);
        this.enabled = (Set<Module>)Sets.newLinkedHashSet();
        this.positions = (Map<Module, Position>)Maps.newHashMap();
        this.scales = (Map<Module, Float>)Maps.newHashMap();
    }
    
    @Override
    public void saveConfig() {
        this.createStructure();
        final JsonObject configFileJson = new JsonObject();
        configFileJson.addProperty("version", this.version);
        try {
            final BufferedWriter writer = new BufferedWriter(new FileWriter(this.configFile));
            try {
                for (final Module module : ModuleManager.INSTANCE.modules) {
                    final JsonObject moduleObject = new JsonObject();
                    moduleObject.addProperty("enabled", this.isEnabled(module));
                    if (module.isRender()) {
                        moduleObject.addProperty("posX", this.getPosition(module).getX());
                        moduleObject.addProperty("posY", this.getPosition(module).getY());
                        moduleObject.addProperty("anchorPoint", this.getPosition(module).getAnchorPoint().name());
                    }
                    if (module.settings.size() > 0) {
                        final JsonObject propertiesObject = new JsonObject();
                        for (final Setting setting : module.settings) {
                            if (!setting.hasValue()) {
                                continue;
                            }
                            SettingWrapper.addSettingKey(propertiesObject, setting, setting.getObject());
                        }
                        moduleObject.add("properties", propertiesObject);
                    }
                    configFileJson.add(module.getKey(), moduleObject);
                }
                writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(new JsonParser().parse(configFileJson.toString())));
            }
            finally {
                if (Collections.singletonList(writer).get(0) != null) {
                    writer.close();
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void loadConfig() {
        this.createStructure();
        final JsonObject configFileJson = this.loadJsonFile(this.configFile);
        this.getNonNull(configFileJson, "version", jsonElement -> Client.info("Detected " + this.name + " version: " + this.version + " => " + jsonElement.getAsDouble(), new Object[0]));
        for (final Module module : ModuleManager.INSTANCE.modules) {
            final JsonObject moduleObject;
            final Module module2;
            final Module module3;
            final Module module4;
            final Module module5;
            JsonObject propertiesObject;
            final Iterator<Setting> iterator2;
            Setting setting;
            this.getNonNull(configFileJson, module.getKey(), jsonElement -> {
                moduleObject = jsonElement.getAsJsonObject();
                this.getNonNull(moduleObject, "enabled", element -> this.setEnabled(module2, element.getAsBoolean()));
                if (module2.isRender()) {
                    this.getNonNull(moduleObject, "posX", element -> this.setPosition(module3, this.getPosition(module3).getAnchorPoint(), (float)element.getAsInt(), this.getPosition(module3).getY()));
                    this.getNonNull(moduleObject, "posY", element -> this.setPosition(module4, this.getPosition(module4).getAnchorPoint(), this.getPosition(module4).getX(), (float)element.getAsInt()));
                    this.getNonNull(moduleObject, "anchorPoint", element -> this.setPosition(module5, AnchorPoint.valueOf(element.getAsString()), this.getPosition(module5).getX(), this.getPosition(module5).getY()));
                }
                if (module2.settings.size() > 0) {
                    propertiesObject = moduleObject.getAsJsonObject("properties");
                    module2.settings.iterator();
                    while (iterator2.hasNext()) {
                        setting = iterator2.next();
                        this.getNonNull(propertiesObject, setting.getKey(), element -> SettingWrapper.setValue(setting, element));
                    }
                }
            });
        }
    }
    
    public void setEnabled(final Module module, final boolean enabled) {
        if (enabled) {
            this.enabled.add(module);
        }
        else {
            this.enabled.remove(module);
        }
    }
    
    public boolean isEnabled(final Module module) {
        return this.enabled.contains(module);
    }
    
    public void setPosition(final Module module, final AnchorPoint anchorPoint, final float x, final float y) {
        this.positions.put(module, new Position(anchorPoint, x, y));
    }
    
    public Position getPosition(final Module module) {
        return this.positions.getOrDefault(module, new Position(AnchorPoint.TOP_CENTER, 0.0f, 0.0f));
    }
    
    public float getActualX(final Module module) {
        return this.getPosition(module).getAnchorPoint().getX(new avr(ave.A()).a()) + this.getPosition(module).getX();
    }
    
    public float getActualY(final Module module) {
        return this.getPosition(module).getAnchorPoint().getY(new avr(ave.A()).b()) + this.getPosition(module).getY();
    }
    
    public void setClosestAnchorPoint(final Module module) {
        final float actualX = this.getActualX(module);
        final float actualY = this.getActualY(module);
        final avr sr = new avr(ave.A());
        final int maxX = sr.a();
        final int maxY = sr.b();
        double shortestDistance = -1.0;
        AnchorPoint closestAnchorPoint = AnchorPoint.CENTER;
        for (final AnchorPoint anchorPoint : AnchorPoint.values()) {
            final double distance = Point2D.distance(actualX, actualY, anchorPoint.getX(maxX), anchorPoint.getY(maxY));
            if (shortestDistance == -1.0 || distance < shortestDistance) {
                closestAnchorPoint = anchorPoint;
                shortestDistance = distance;
            }
        }
        final float x = actualX - closestAnchorPoint.getX(maxX);
        final float y = actualY - closestAnchorPoint.getY(maxY);
        this.setPosition(module, closestAnchorPoint, x, y);
    }
    
    public Float getScale(final Module module) {
        return this.scales.getOrDefault(module, 1.0f);
    }
    
    static {
        INSTANCE = new ModuleConfig();
    }
}
