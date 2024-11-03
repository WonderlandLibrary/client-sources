package dev.stephen.nexus.gui.clickgui.imgui;

import dev.stephen.nexus.Client;
import dev.stephen.nexus.module.ModuleCategory;
import dev.stephen.nexus.module.Module;
import dev.stephen.nexus.module.setting.*;
import dev.stephen.nexus.module.setting.impl.*;
import dev.stephen.nexus.module.setting.impl.newmodesetting.NewModeSetting;
import imgui.ImGui;
import imgui.flag.ImGuiInputTextFlags;
import imgui.flag.ImGuiMouseButton;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImFloat;
import imgui.type.ImString;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;


public class ClickGui extends Screen {
    private final ImString searchText = new ImString(500);
    private ModuleCategory moduleCategory;
    private Module keyBindingModule = null;
    private Module module;

    public ClickGui() {
        super(Text.empty());
    }

    @Override
    public void close() {
        super.close();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        ImGuiImpl.render(io -> {
            int windowFlags = ImGuiWindowFlags.NoCollapse | ImGuiWindowFlags.NoScrollbar;

            if (ImGui.begin("Nexus", windowFlags)) {
                for (ModuleCategory moduleCategory1 : ModuleCategory.values()) {
                    ImGui.beginTabBar("Main");

                    if (ImGui.beginTabItem(moduleCategory1.name + "##tab")) {
                        moduleCategory = moduleCategory1;
                        ImGui.endTabItem();
                    }

                    ImGui.endTabBar();
                }

                for (Module module : Client.INSTANCE.getModuleManager().getModulesInCategory(moduleCategory)) {
                    ImGui.setCursorPosX(ImGui.getCursorPosX() + 25);
                    if (ImGui.collapsingHeader(module.getName())) {
                        drawToggle(module);

                        this.module = module;

                        ImGui.text(module.getDescription());

                        ImGui.sameLine();

                        if (ImGui.button(keyBindingModule == module ? "Listening..." : "Key " + GLFW.glfwGetKeyName(module.getKey(), 0))) {
                            keyBindingModule = (keyBindingModule == module) ? null : module;
                        }

                        ImGui.separator();

                        for (Setting property : module.getSettings()) {
                            boolean shouldContinue = false;

                            if (!property.getDependencyBoolSettings().isEmpty()) {
                                for (int i = 0; i < property.getDependencyBoolSettings().size(); i++) {
                                    Setting dependency = property.getDependencyBoolSettings().get(i);
                                    boolean expectedValue = property.getDependencyBools().get(i);

                                    if (dependency instanceof BooleanSetting && ((BooleanSetting) dependency).getValue() != expectedValue) {
                                        shouldContinue = true;
                                        break;
                                    }
                                }
                            }

                            if (!property.getDependencyModeSettings().isEmpty()) {
                                for (int i = 0; i < property.getDependencyModeSettings().size(); i++) {
                                    Setting dependency = property.getDependencyModeSettings().get(i);
                                    String expectedMode = property.getDependencyModes().get(i);

                                    if (!(dependency instanceof ModeSetting && ((ModeSetting) dependency).isMode(expectedMode))) {
                                        shouldContinue = true;
                                        break;
                                    }
                                }
                            }

                            if (!property.getDependencyMultiModeSettings().isEmpty()) {
                                for (int i = 0; i < property.getDependencyMultiModeSettings().size(); i++) {
                                    Setting dependency = property.getDependencyMultiModeSettings().get(i);
                                    String expectedMode = property.getDependencyMultiModes().get(i);

                                    if (!(dependency instanceof MultiModeSetting && ((MultiModeSetting) dependency).isModeSelected(expectedMode))) {
                                        shouldContinue = true;
                                        break;
                                    }
                                }
                            }

                            if (!property.getDependencyNewModeSettings().isEmpty()) {
                                for (int i = 0; i < property.getDependencyNewModeSettings().size(); i++) {
                                    Setting dependency = property.getDependencyNewModeSettings().get(i);
                                    String expectedMode = property.getDependencyNewModes().get(i);

                                    if (!(dependency instanceof NewModeSetting && ((NewModeSetting) dependency).isMode(expectedMode))) {
                                        shouldContinue = true;
                                        break;
                                    }
                                }
                            }

                            if (shouldContinue) {
                                continue;
                            }

                            if (property instanceof BooleanSetting booleanSetting) {
                                if (ImGui.checkbox(property.getName(), booleanSetting.getValue())) {
                                    booleanSetting.setValue(!booleanSetting.getValue());
                                }
                            }

                            if (property instanceof NumberSetting numberProperty) {
                                ImFloat imFloat = new ImFloat((float) numberProperty.getValue());

                                if (ImGui.sliderFloat("##" + numberProperty.getName(), imFloat.getData(), (float) numberProperty.getMin(), (float) numberProperty.getMax())) {
                                    numberProperty.setValue(imFloat.get());
                                }

                                ImGui.sameLine();
                                ImGui.text(numberProperty.getName());

                                imFloat.getData()[0] = (float) numberProperty.getValue();
                            }

                            if (property instanceof RangeSetting rangeProperty) {
                                float[] imFloats = new float[]{(float) rangeProperty.getValueMin(), (float) rangeProperty.getValueMax()};

                                if (ImGui.sliderFloat2("##" + rangeProperty.getName(), imFloats, (float) rangeProperty.getMin(), (float) rangeProperty.getMax())) {
                                    rangeProperty.setValueMin(imFloats[0]);
                                    rangeProperty.setValueMax(imFloats[1]);
                                }

                                ImGui.sameLine();
                                ImGui.text(rangeProperty.getName());

                                imFloats[0] = (float) rangeProperty.getValueMin();
                                imFloats[1] = (float) rangeProperty.getValueMax();
                            }

                            if (property instanceof StringSetting stringSetting) {
                                ImString imString = new ImString(stringSetting.getValue(), 500);

                                if (ImGui.inputText("##" + stringSetting.getName(), imString, ImGuiInputTextFlags.None)) {
                                    stringSetting.setValue(imString.get());
                                }

                                ImGui.sameLine();
                                ImGui.text(stringSetting.getName());
                            }

                            if (property instanceof ModeSetting modeProperty) {
                                String comboId = "##" + modeProperty.getName() + "_" + module.getName();

                                if (ImGui.beginCombo(comboId, modeProperty.getMode())) {
                                    ImGui.inputTextWithHint(comboId + "_search", "Search For Modes.", searchText, ImGuiInputTextFlags.None);
                                    String search = searchText.get().toLowerCase();

                                    for (String mode : modeProperty.getModes()) {
                                        if (search.isEmpty() || mode.toLowerCase().contains(search)) {
                                            if (ImGui.selectable(mode)) {
                                                modeProperty.setMode(mode);
                                                searchText.set(new ImString(500));
                                            }
                                        }
                                    }

                                    ImGui.endCombo();
                                }
                                ImGui.sameLine();
                                ImGui.text(modeProperty.getName());
                            }

                            if (property instanceof NewModeSetting newModeProperty) {
                                String comboId = "##" + newModeProperty.getName() + "_" + module.getName();

                                if (ImGui.beginCombo(comboId, newModeProperty.getCurrentMode().getName())) {
                                    ImGui.inputTextWithHint(comboId + "_search", "Search For Modes.", searchText, ImGuiInputTextFlags.None);
                                    String search = searchText.get().toLowerCase();

                                    for (String mode : newModeProperty.getModeNames()) {
                                        if (search.isEmpty() || mode.toLowerCase().contains(search)) {
                                            if (ImGui.selectable(mode)) {
                                                newModeProperty.setMode(mode);
                                                searchText.set(new ImString(500));
                                            }
                                        }
                                    }

                                    ImGui.endCombo();
                                }
                                ImGui.sameLine();
                                ImGui.text(newModeProperty.getName());
                            }

                            if (property instanceof MultiModeSetting multiModeProperty) {
                                String comboId = "##" + multiModeProperty.getName() + "_" + module.getName();

                                if (ImGui.beginCombo(comboId, "Select Modes")) {
                                    ImGui.inputTextWithHint(comboId + "_search", "Search For Modes.", searchText, ImGuiInputTextFlags.None);
                                    String search = searchText.get().toLowerCase();

                                    for (String mode : multiModeProperty.getModes()) {
                                        if (search.isEmpty() || mode.toLowerCase().contains(search)) {
                                            boolean isSelected = multiModeProperty.isModeSelected(mode);
                                            ImGui.selectable(mode, isSelected);

                                            if (ImGui.isItemClicked(ImGuiMouseButton.Left)) {
                                                if (isSelected) {
                                                    multiModeProperty.deselectMode(mode);
                                                } else {
                                                    multiModeProperty.selectMode(mode);
                                                }
                                            }
                                        }
                                    }

                                    ImGui.endCombo();
                                }
                                ImGui.sameLine();
                                ImGui.text(multiModeProperty.getName());
                            }

                        }
                    } else {
                        drawToggle(module);
                    }
                }
            }
            ImGui.end();
        });
    }

    @Override
    public boolean charTyped(char typedChar, int modifiers) {
        int keycode = charToKey(typedChar);

        if (keycode == GLFW.GLFW_KEY_ESCAPE && keyBindingModule == null) {
            MinecraftClient.getInstance().setScreen(null);
        }

        if (keyBindingModule != null) {
            keyBindingModule.setKey(keycode == GLFW.GLFW_KEY_ESCAPE ? 0 : keycode);
            keyBindingModule = null;
        }

        return false;
    }

    public void drawToggle(Module module) {
        ImGui.sameLine(-16);

        ImGui.setCursorPosX(ImGui.getCursorPosX() + 20);
        if (ImGui.checkbox("##T" + module.getName(), module.isEnabled())) {
            module.toggle();
        }
    }

    private int charToKey(char character) {
        return switch (character) {
            case 'a' -> GLFW.GLFW_KEY_A;
            case 'b' -> GLFW.GLFW_KEY_B;
            case 'c' -> GLFW.GLFW_KEY_C;
            case 'd' -> GLFW.GLFW_KEY_D;
            case 'e' -> GLFW.GLFW_KEY_E;
            case 'f' -> GLFW.GLFW_KEY_F;
            case 'g' -> GLFW.GLFW_KEY_G;
            case 'h' -> GLFW.GLFW_KEY_H;
            case 'i' -> GLFW.GLFW_KEY_I;
            case 'j' -> GLFW.GLFW_KEY_J;
            case 'k' -> GLFW.GLFW_KEY_K;
            case 'l' -> GLFW.GLFW_KEY_L;
            case 'm' -> GLFW.GLFW_KEY_M;
            case 'n' -> GLFW.GLFW_KEY_N;
            case 'o' -> GLFW.GLFW_KEY_O;
            case 'p' -> GLFW.GLFW_KEY_P;
            case 'q' -> GLFW.GLFW_KEY_Q;
            case 'r' -> GLFW.GLFW_KEY_R;
            case 's' -> GLFW.GLFW_KEY_S;
            case 't' -> GLFW.GLFW_KEY_T;
            case 'u' -> GLFW.GLFW_KEY_U;
            case 'v' -> GLFW.GLFW_KEY_V;
            case 'w' -> GLFW.GLFW_KEY_W;
            case 'x' -> GLFW.GLFW_KEY_X;
            case 'y' -> GLFW.GLFW_KEY_Y;
            case 'z' -> GLFW.GLFW_KEY_Z;
            default -> 0;
        };
    }
}