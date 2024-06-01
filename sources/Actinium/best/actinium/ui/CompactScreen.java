package best.actinium.ui;

import best.actinium.module.api.config.Config;
import best.actinium.module.api.config.ConfigManager;
import best.actinium.module.impl.visual.HudModule;
import best.actinium.property.impl.*;
import best.actinium.util.render.ChatUtil;
import best.actinium.util.render.RenderUtil;
import imgui.ImColor;
import imgui.ImGui;
import imgui.ImGuiStyle;
import imgui.ImVec4;
import imgui.flag.*;
import best.actinium.Actinium;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.property.Property;
import best.actinium.ui.renderer.ImGuiImpl;
import best.actinium.util.IAccess;
import best.actinium.util.math.MathUtil;
import best.actinium.util.render.ColorUtil;
import imgui.type.ImString;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import java.util.ArrayList;
import java.util.List;

import java.awt.*;

public class CompactScreen extends GuiScreen implements IAccess {

    private ModuleCategory category;
    private Module module;
    private List<String> chatMessages = new ArrayList<>();
    private ImString userInput = new ImString(200);

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        RenderUtil.image(new ResourceLocation("actinium/anime/aqua 2.png"),700,280,250,250,ColorUtil.withAlpha(Actinium.INSTANCE.getModuleManager().get(HudModule.class).color1.getColor(), 255));

        ImGuiImpl.render(imGuiIO -> {
            ImGui.setNextWindowSize(400, 200, ImGuiCond.None);
            ImGui.setWindowPos(500, 500);

            if (ImGui.begin("Irc Interface", ImGuiWindowFlags.NoResize)) {
                if (ImGui.inputText("##Input", userInput)) {

                }

                ImGui.sameLine();

                if (ImGui.button("Send")) {
                    String currentInput = userInput.get();
                    chatMessages.add("You: " + currentInput);
                    userInput.set("");
                }

                for (String message : chatMessages) {
                    ImGui.textWrapped(message);
                }
            }

            ImGui.end();

            ImGui.setNextWindowSize(1024, 576, ImGuiCond.Always);
            setDarkMode();

            if (ImGui.begin("User Interface", ImGuiWindowFlags.NoResize)) {

                if (ImGui.beginTabBar("Select Category")) {
                    for (ModuleCategory category : ModuleCategory.values()) {
                        if (ImGui.beginTabItem(category.getName() + "##item")) {
                            this.category = category;
                            ImGui.endTabItem();
                        }
                    }
                }

                ImGui.endTabBar();

                if (this.category != null) {
                    ImGui.columns(2);
                    ImGui.setColumnWidth(0, 175);

                    for (Module module : Actinium.INSTANCE.getModuleManager().getGetByCategory(this.category)) {
                        if (ImGui.checkbox("##E" + module.getName(), module.isEnabled())) {
                            module.toggle();
                        }

                        ImGui.sameLine();
                        ImGui.indent(30);

                        if (ImGui.checkbox("##V" + module.getName(), module.isVisible())) {
                            module.setVisible(!module.isVisible());
                        }

                        ImGui.sameLine();
                        ImGui.indent(30);

                        ImGui.beginDisabled(this.module == module);

                        if (ImGui.button(module.getName(), 100, 25)) {
                            this.module = module;
                        }

                        ImGui.endDisabled();

                        if (ImGui.isItemHovered()) {
                            ImGui.setTooltip(module.getDescription());
                        }

                        ImGui.indent(-60);
                    }

                    if (module != null) {
                        ImGui.nextColumn();

                        if (!module.getProperties().isEmpty()) {
                            for (Property property : module.getProperties()) {

                                if (property.isHidden())
                                    continue;

                                if (property instanceof BooleanProperty booleanProperty) {
                                    if (ImGui.checkbox(property.getName(), booleanProperty.isEnabled())) {
                                        booleanProperty.toggle();
                                    }
                                }

                                if (property instanceof NumberProperty numberProperty) {
                                    if (ImGui.sliderFloat(property.getName(), numberProperty.getImFloat().getData(), numberProperty.getMin().floatValue(), numberProperty.getMax().floatValue(), numberProperty.getIncrement() == 1 ? "%.0f" : "%.2f")) {
                                        numberProperty.setValue(MathUtil.round(numberProperty.getImFloat().get(), 2, numberProperty.getIncrement()));
                                        numberProperty.getImFloat().set(numberProperty.getValue().floatValue());
                                    }
                                }

                                if (property instanceof ModeProperty modeProperty) {
                                    if (ImGui.beginCombo(modeProperty.getName(), modeProperty.getMode())) {
                                        for (String mode : modeProperty.getModes()) {
                                            if (ImGui.selectable(mode)) {
                                                modeProperty.setMode(mode);
                                            }
                                        }
                                        ImGui.endCombo();
                                    }
                                }

                                if(property instanceof InfoStringProperty info) {
                                    if(info.isLine()) {
                                        ImGui.newLine();
                                    }

                                    ImGui.text(info.getName());
                                }

                                if (property instanceof ColorProperty colorProperty) {
                                    float[] color = ColorUtil.toGLColor(colorProperty.getColor().getRGB());

                                    if (ImGui.colorEdit4(property.getName(), color,ImGuiColorEditFlags.AlphaBar)) {
                                        colorProperty.setColor(ColorUtil.toColor(color));
                                    }
                                }

                                if (property instanceof InputProperty inputProperty) {
                                    if (ImGui.inputTextWithHint(property.getName(), "You can type whatever you want here.", inputProperty.getImString(), ImGuiInputTextFlags.None)) {
                                        inputProperty.setInput(inputProperty.getImString().get());
                                    }
                                }

                            }

                            ImGui.unindent();
                        }
                    }
                }
            }

            ImGui.end();
        });
    }

    private void setDarkMode() {
        ImGuiStyle im = ImGui.getStyle();

        im.setFrameRounding(5);
        im.setColor(ImGuiCol.Text, new Color(255, 255, 255).getRGB());
        im.setColor(ImGuiCol.TextDisabled, new Color(128, 128, 128).getRGB());
        im.setColor(ImGuiCol.WindowBg, new Color(30, 30, 30).getRGB());
        im.setColor(ImGuiCol.ChildBg, new Color(40, 40, 40).getRGB());
        im.setColor(ImGuiCol.PopupBg, new Color(50, 50, 50).getRGB());
        im.setColor(ImGuiCol.Border, new Color(0, 0, 0).getRGB());
        im.setColor(ImGuiCol.BorderShadow, new Color(0, 0, 0).getRGB());
        im.setColor(ImGuiCol.FrameBg, new Color(40, 40, 40).getRGB());
        im.setColor(ImGuiCol.FrameBgHovered, new Color(60, 60, 60).getRGB());
        im.setColor(ImGuiCol.FrameBgActive, new Color(80, 80, 80).getRGB());
        im.setColor(ImGuiCol.TitleBg, new Color(25, 25, 25).getRGB());
        im.setColor(ImGuiCol.TitleBgActive, new Color(35, 35, 35).getRGB());
        im.setColor(ImGuiCol.TitleBgCollapsed, new Color(15, 15, 15).getRGB());
        im.setColor(ImGuiCol.MenuBarBg, new Color(30, 30, 30).getRGB());
        im.setColor(ImGuiCol.ScrollbarBg, new Color(20, 20, 20).getRGB());
        im.setColor(ImGuiCol.ScrollbarGrab, new Color(60, 60, 60).getRGB());
        im.setColor(ImGuiCol.ScrollbarGrabHovered, new Color(80, 80, 80).getRGB());
        im.setColor(ImGuiCol.ScrollbarGrabActive, new Color(100, 100, 100).getRGB());
        im.setColor(ImGuiCol.CheckMark, new Color(255, 255, 255).getRGB());
        im.setColor(ImGuiCol.SliderGrab, new Color(60, 60, 60).getRGB());
        im.setColor(ImGuiCol.SliderGrabActive, new Color(80, 80, 80).getRGB());
        im.setColor(ImGuiCol.Button, new Color(40, 40, 40).getRGB());
        im.setColor(ImGuiCol.ButtonHovered, new Color(60, 60, 60).getRGB());
        im.setColor(ImGuiCol.ButtonActive, new Color(80, 80, 80).getRGB());
        im.setColor(ImGuiCol.Header, new Color(40, 40, 40).getRGB());
        im.setColor(ImGuiCol.HeaderHovered, new Color(60, 60, 60).getRGB());
        im.setColor(ImGuiCol.HeaderActive, new Color(80, 80, 80).getRGB());
        im.setColor(ImGuiCol.Separator, new Color(255, 255, 255, 50).getRGB());
        im.setColor(ImGuiCol.SeparatorHovered, new Color(255, 255, 255, 100).getRGB());
        im.setColor(ImGuiCol.SeparatorActive, new Color(255, 255, 255, 150).getRGB());
        im.setColor(ImGuiCol.ResizeGrip, new Color(60, 60, 60).getRGB());
        im.setColor(ImGuiCol.ResizeGripHovered, new Color(80, 80, 80).getRGB());
        im.setColor(ImGuiCol.ResizeGripActive, new Color(100, 100, 100).getRGB());
        im.setColor(ImGuiCol.Tab, new Color(40, 40, 40).getRGB());
        im.setColor(ImGuiCol.TabHovered, new Color(60, 60, 60).getRGB());
        im.setColor(ImGuiCol.TabActive, new Color(80, 80, 80).getRGB());
        im.setColor(ImGuiCol.TabUnfocused, new Color(40, 40, 40).getRGB());
        im.setColor(ImGuiCol.TabUnfocusedActive, new Color(60, 60, 60).getRGB());
        im.setColor(ImGuiCol.DockingPreview, new Color(255, 255, 255, 100).getRGB());
        im.setColor(ImGuiCol.DockingEmptyBg, new Color(40, 40, 40).getRGB());
        im.setColor(ImGuiCol.PlotLines, new Color(255, 255, 255).getRGB());
        im.setColor(ImGuiCol.PlotLinesHovered, new Color(255, 255, 255, 100).getRGB());
        im.setColor(ImGuiCol.PlotHistogram, new Color(255, 255, 255).getRGB());
        im.setColor(ImGuiCol.PlotHistogramHovered, new Color(255, 255, 255, 100).getRGB());
        im.setColor(ImGuiCol.TableHeaderBg, new Color(25, 25, 25).getRGB());
        im.setColor(ImGuiCol.TableBorderStrong, new Color(255, 255, 255).getRGB());
        im.setColor(ImGuiCol.TableBorderLight, new Color(255, 255, 255, 50).getRGB());
        im.setColor(ImGuiCol.TableRowBg, new Color(30, 30, 30).getRGB());
        im.setColor(ImGuiCol.TableRowBgAlt, new Color(40, 40, 40).getRGB());
        im.setColor(ImGuiCol.TextSelectedBg, new Color(60, 60, 60, 150).getRGB());
        im.setColor(ImGuiCol.DragDropTarget, new Color(255, 0, 0, 100).getRGB());
        im.setColor(ImGuiCol.NavHighlight, new Color(255, 255, 255, 100).getRGB());
        im.setColor(ImGuiCol.NavWindowingHighlight, new Color(255, 255, 255, 50).getRGB());
        im.setColor(ImGuiCol.NavWindowingDimBg, new Color(20, 20, 20, 150).getRGB());
        im.setColor(ImGuiCol.ModalWindowDimBg, new Color(20, 20, 20, 150).getRGB());
    }
}
