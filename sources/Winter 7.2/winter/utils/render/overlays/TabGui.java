/*
 * Decompiled with CFR 0_122.
 */
package winter.utils.render.overlays;

import java.io.PrintStream;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import winter.module.Module;

public class TabGui {
    public static Minecraft mc = Minecraft.getMinecraft();
    public static ArrayList<TabModule> modules;
    public static ArrayList<TabCategory> types;
    public static boolean isAKeyPressed;
    public static int selectedCat;
    public static int selectedMod;
    private static int multiplier;
    private static int trans;
    private static final ResourceLocation sel;

    static {
        multiplier = 11;
        trans = 0;
        sel = new ResourceLocation("textures/rocket/tab.png");
    }

    public static void setup() {
        System.out.println("Setting up tabui...");
        modules = new ArrayList();
        types = new ArrayList();
        selectedCat = 0;
        selectedMod = 0;
        Module.Category[] arrcategory = Module.Category.values();
        int n = arrcategory.length;
        int n2 = 0;
        while (n2 < n) {
            Module.Category type = arrcategory[n2];
            types.add(new TabCategory(type));
            ++n2;
        }
        System.out.println("TabGui setup finished!");
    }

    public static int getOffsetY() {
        return 22;
    }

    public static int getOffsetX() {
        return 13;
    }

    public static void renderTabGui() {
    }

    public static void transition(String direction) {
        if (direction.equals("down")) {
            trans = -6;
        }
        if (direction.equals("up")) {
            trans = 6;
        }
    }

    public static class TabCategory {
        Module.Category t;
        Boolean expanded = false;

        public TabCategory(Module.Category type) {
            this.t = type;
        }

        public Module.Category getCat() {
            return this.t;
        }

        public boolean isExpanded() {
            return this.expanded;
        }

        public void setExpanded(Boolean expanded) {
            this.expanded = expanded;
        }
    }

    public static class TabModule {
        Module m;

        public TabModule(Module mod) {
            this.m = mod;
        }

        public Module getModule() {
            return this.m;
        }
    }

}

