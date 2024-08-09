package ru.FecuritySQ.module;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import ru.FecuritySQ.FecuritySQ;
import ru.FecuritySQ.clickgui.UIPanel;
import ru.FecuritySQ.clickgui.elements.*;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.module.дисплей.ClickGui;
import ru.FecuritySQ.module.игрок.Freecam;
import ru.FecuritySQ.option.Option;
import ru.FecuritySQ.option.imp.*;

import java.util.ArrayList;
import java.util.List;

public class Module {
    private String name;
    private Category category;

    public static Minecraft mc = Minecraft.getInstance();
    private boolean enabled;
    public double keybindScale = 0;

    public float slide;
    private int key;
    public List<Option> optionList = new ArrayList<Option>();

    public Module(Category category, int key){
        this.name = this.getClass().getSimpleName();
        this.category = category;
        this.key = key;
    }

    public void addOption(Option option){
        getOptionList().add(option);
    }
    public void event(Event e){}

    public void enable(){}
    public void disable(){}

    public void toggle(){
        this.enabled = !this.enabled;
        if(enabled) enable(); else disable();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }


    public JsonObject save() {
        JsonObject object = new JsonObject();
        object.addProperty("state", this.isEnabled());
        object.addProperty("keyIndex", this.getKey());
        JsonObject propertiesObject = new JsonObject();
        for (Option set : getOptionList()) {
            if (set instanceof OptionBoolean) {
                propertiesObject.addProperty(set.getName(), ((OptionBoolean) set).get());
            } else if (set instanceof OptionMode mode) {
                propertiesObject.addProperty(set.getName(), ((OptionMode) set).getIndex());
            } else if (set instanceof OptionNumric) {
                propertiesObject.addProperty(set.getName(), ((OptionNumric) set).get());
            } else if (set instanceof OptionColor) {
                propertiesObject.addProperty(set.getName(), ((OptionColor) set).get().getRGB());
            } else if (set instanceof OptionBoolList) {
                StringBuilder builder = new StringBuilder();
                for (OptionBoolean s : ((OptionBoolList) set).list) {
                        if(s.get())
                        builder.append(s.getName() + "\n");
                }
                propertiesObject.addProperty(set.getName(), builder.toString());
            }
        }
        object.add("Settings", propertiesObject);
        return object;
    }


    public void load(JsonObject object) {
        if (object != null && !(this instanceof Freecam)) {

            if (object.has("state")) {
                this.setEnabled(object.get("state").getAsBoolean());
            }

            if (object.has("keyIndex")) {
                setKey((object.get("keyIndex").getAsInt()));
            }

            for (Option set : getOptionList()) {
                JsonObject propertiesObject = object.getAsJsonObject("Settings");
                if (set == null)
                    continue;
                if (propertiesObject == null)
                    continue;
                if (!propertiesObject.has(set.getName()))
                    continue;
                if (set instanceof OptionBoolean) {
                    ((OptionBoolean) set).set((propertiesObject.get(set.getName()).getAsBoolean()));
                } else if (set instanceof OptionMode mode) {
                    mode.setIndex(propertiesObject.get(set.getName()).getAsInt());
                } else if (set instanceof OptionNumric) {
                    ((OptionNumric) set).current = (propertiesObject.get(set.getName()).getAsFloat());
                } else if (set instanceof OptionColor color) {
                    color.setColorValue((propertiesObject.get(set.getName()).getAsInt()));
                } else if (set instanceof OptionBoolList boolList) {

                    for(OptionBoolean optionBoolean : boolList.list){
                        optionBoolean.set(false);
                    }

                    String[] strs = propertiesObject.get(set.getName()).getAsString().split("\n");
                    for(OptionBoolean bool : boolList.list){
                        for(String s : strs) {
                            if (s.equalsIgnoreCase(bool.getName())) {
                                bool.set(true);
                            }
                        }
                    }
                }
            }
        }
    }

    public List<Option> getOptionList(){
        return this.optionList;
    }



    public enum Category {

        Сражение("Combat", 6),
        Передвижение("Movement", 5),
        Игрок("Player", 3),

        Визуальные("Visual", 1),
        Общее("Other", 4),
        Дисплей("Display", 2),
        Центр("Темы", 7);

        private final int index;
        private final String displayName;

        Category(String displayName, int index) {
            this.index = index;
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return this.displayName;
        }

        public int getIndex() {
            return index;
        }
    }


}
