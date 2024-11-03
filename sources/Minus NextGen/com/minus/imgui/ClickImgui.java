package com.minus.imgui;

import com.minus.Client;
import com.minus.module.Category;
import com.minus.module.Module;
import com.minus.module.setting.BooleanSetting;
import com.minus.module.setting.ModeSetting;
import com.minus.module.setting.NumberSetting;
import com.minus.module.setting.Setting;
import imgui.ImGui;
import imgui.flag.ImGuiInputTextFlags;
import imgui.type.ImFloat;
import imgui.type.ImString;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class ClickImgui extends Screen {
    private final ImString searchText = new ImString(500);
    private Category category;
    private Module module;
    private boolean shouldSetKey = false;

    public ClickImgui() {
        super(Text.empty());
        Client.INSTANCE.getFontManager().initialize();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        ImGuiImpl.render(io -> {
            ImGui.setNextWindowSize(1000, 500);
            if (ImGui.begin("Minus-NEXTGEN")) {
                for (Category category1 : Category.values()) {
                    ImGui.beginTabBar("Main");

                    if (ImGui.beginTabItem(category1.name + "##tab")) {
                        category = category1;
                        ImGui.endTabItem();
                    }

                    ImGui.endTabBar();
                }

                for (Module module : Client.INSTANCE.getModuleManager().getModulesInCategory(category)) {
                    ImGui.setCursorPosX(ImGui.getCursorPosX() + 20);
                    if (ImGui.collapsingHeader(module.getName())) {
                        drawToggle(module);
                        this.module = module;

                        ImGui.text(module.getDescription());

                        ImGui.sameLine();
                        if (ImGui.button(shouldSetKey ? "Listening..." : "Key " + GLFW.glfwGetKeyName(module.getKey(), 0))) {
                            shouldSetKey = true;
                        }

                        ImGui.separator();
                        for (Setting property : module.getSettings()) {
                            if (property.getDependencyBoolSetting() != null) {
                                Setting dependency = property.getDependencyBoolSetting();
                                if (dependency instanceof BooleanSetting && ((BooleanSetting) dependency).getValue() != property.getDependencyBool()) {
                                    continue;
                                }
                            }

                            if (property.dependencyModeSetting() != null) {
                                Setting dependency = property.dependencyModeSetting();
                                if (!(dependency instanceof ModeSetting && ((ModeSetting) dependency).isMode(property.dependencyMode()))) {
                                    continue;
                                }
                            }

                            if (property instanceof BooleanSetting booleanSetting) {
                                if (ImGui.checkbox(property.getName(), booleanSetting.getValue())) {
                                    booleanSetting.setValue(!booleanSetting.getValue());
                                }
                            }

                            if (property instanceof NumberSetting numberProperty) {
                                ImFloat imFloat = new ImFloat((float) numberProperty.getValue());

                                if (ImGui.sliderFloat("##" + numberProperty.getName(), imFloat.getData(), (float) numberProperty.getMin(), (float) numberProperty.getMax(), numberProperty.getName() + " " + imFloat.getData()[0])) {
                                    numberProperty.setValue(imFloat.get());
                                }

                                imFloat.getData()[0] = (float) numberProperty.getValue();
                            }

                            if (property instanceof ModeSetting modeProperty) {
                                if (ImGui.beginCombo(modeProperty.getName(), modeProperty.getMode())) {
                                    ImGui.inputTextWithHint("##" + modeProperty.getMode(), "Search For Modes.", searchText, ImGuiInputTextFlags.None);
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

        if (keycode == GLFW.GLFW_KEY_ESCAPE && !shouldSetKey) {
            MinecraftClient.getInstance().setScreen(null);
        }

        if (shouldSetKey) {
            module.setKey(keycode == GLFW.GLFW_KEY_ESCAPE ? 0 : keycode);
            shouldSetKey = false;
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