package io.github.liticane.clients.ui.imgui;

import imgui.flag.ImGuiInputTextFlags;
import io.github.liticane.clients.feature.property.impl.*;
import io.github.liticane.clients.ui.imgui.renderer.ImImpl;
import io.github.liticane.clients.Client;
import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.feature.property.Property;
import imgui.ImGui;
import imgui.type.ImInt;
import io.github.liticane.clients.util.player.ColorUtil;
import net.minecraft.client.gui.GuiScreen;

public class ImGuiScreen extends GuiScreen {

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ImImpl.render(io -> {
            ImGui.begin(Client.NAME + " v" + Client.VERSION);

            if (ImGui.beginTabBar("##category")) {
                for (Module.Category category : Module.Category.values()) {
                    if (ImGui.beginTabItem(category.getName())) {

                        for (Module module : Client.INSTANCE.getModuleManager().getModulesFromCategory(category)) {
                            if (ImGui.collapsingHeader(module.getName())) {
                                if (ImGui.checkbox("Enabled", module.isToggled())) {
                                    module.toggle();
                                }

                                if (!module.getProperties().isEmpty())
                                    ImGui.separator();

                                for (Property property : module.getProperties()) {
                                    if (property.isVisible()) {
                                        if (property instanceof BooleanProperty) {
                                            if (ImGui.checkbox(property.getName(), ((BooleanProperty) property).isToggled()))
                                                ((BooleanProperty) property).setToggled(!((BooleanProperty) property).isToggled());
                                        }

                                        if (property instanceof NumberProperty) {
                                            if (ImGui.sliderFloat(property.getName(), ((NumberProperty) property).getFlt(), (float) ((NumberProperty) property).getMin(), (float) ((NumberProperty) property).getMax())) {
                                                ((NumberProperty) property).setValue(((NumberProperty) property).getFlt()[0]);
                                            }

                                            ((NumberProperty) property).flt[0] = (float) ((NumberProperty) property).getValue();
                                        }

                                        if (property instanceof ColorProperty color) {
                                            float[] glColor = ColorUtil.toGLColor(color.getColor().getRGB());
                                            if (ImGui.colorEdit4(color.getName(), glColor)) {
                                                color.setColor(ColorUtil.toColor(glColor));
                                            }
                                        }

                                        if (property instanceof InputProperty input) {
                                            if (ImGui.inputTextWithHint(input.getName(), "Type here.", input.getImString(), ImGuiInputTextFlags.None)) {
                                                input.setString(input.getImString().get());
                                            }
                                        }

                                        if (property instanceof StringProperty string) {
                                            if (ImGui.beginCombo(string.getName(), string.getMode())) {
                                                for (String mode : string.getModes()) {
                                                    if (ImGui.selectable(mode)) {
                                                        string.setMode(mode);
                                                    }
                                                }
                                                ImGui.endCombo();
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        ImGui.endTabItem();
                    }
                }

                ImGui.endTabBar();
            }

            ImGui.end();
        });
    }

}
