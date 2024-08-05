package fr.dog.ui;

import fr.dog.Dog;
import fr.dog.imgui.ImGuiRendererImpl;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.property.Property;
import fr.dog.property.impl.BooleanProperty;
import fr.dog.property.impl.ColorProperty;
import fr.dog.property.impl.ModeProperty;
import fr.dog.property.impl.NumberProperty;
import fr.dog.util.render.ColorUtil;
import imgui.ImGui;
import imgui.type.ImInt;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;

public class ImGuiScreen extends GuiScreen {

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ImGuiRendererImpl.render(io -> {
            ImGui.begin("Dog Client");
            {
                if (ImGui.beginTabBar("##category")) {
                    for (ModuleCategory category : ModuleCategory.values()) {
                        if (ImGui.beginTabItem(category.getName())) {
                            for (Module module : Dog.getInstance().getModuleManager().getModulesFromCategory(category)) {
                                ImGui.pushID(module.getName());
                                boolean opened = ImGui.collapsingHeader(module.getName());

                                if (opened) {
                                    ImGui.indent();

                                    if (ImGui.checkbox("Enabled", module.isEnabled())) {
                                        module.toggle();
                                    }

                                    if (!module.propertyList.isEmpty()) {
                                        ImGui.separator();

                                        for (Property<?> v : module.propertyList) {
                                            if (v.isVisible()) {
                                                if (v instanceof BooleanProperty) {
                                                    if (ImGui.checkbox(v.getLabel(), ((BooleanProperty) v).getValue()))
                                                        ((BooleanProperty) v).setValue(!((BooleanProperty) v).getValue());
                                                }

                                                if (v instanceof NumberProperty) {
                                                    if (ImGui.sliderFloat(v.getLabel(), ((NumberProperty) v).getFloats(), ((NumberProperty) v).getMinimumValue(), ((NumberProperty) v).getMaximumValue()))
                                                        ((NumberProperty) v).setValue(((NumberProperty) v).floats[0]);

                                                    ((NumberProperty) v).floats[0] = ((NumberProperty) v).getValue();
                                                }

                                                if (v instanceof ModeProperty) {
                                                    ImInt currentIndex = ((ModeProperty) v).getImGuiIndex();

                                                    if (ImGui.combo(v.getLabel(), currentIndex, ((ModeProperty) v).getValues()))
                                                        ((ModeProperty) v).setIndexValue(currentIndex.intValue());
                                                }

                                                if (v instanceof ColorProperty) {
                                                    float[] colorFloat = ColorUtil.toGLColor(((Color) v.getValue()).getRGB());

                                                    if (ImGui.colorEdit4(v.getLabel(), colorFloat))
                                                        ((ColorProperty) v).setValue(ColorUtil.toColor(colorFloat));
                                                }
                                            }
                                        }
                                    }
                                    ImGui.unindent();
                                }
                                ImGui.popID();
                            }
                            ImGui.endTabItem();
                        }
                    }
                    ImGui.endTabBar();
                }
            }
            ImGui.end();
        });
    }
}
