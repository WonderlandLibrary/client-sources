package club.strifeclient.ui.implementations.imgui.theme;

import imgui.ImFontConfig;
import imgui.ImGuiIO;
import imgui.ImGuiStyle;
import org.apache.commons.io.IOUtils;

import java.io.IOException;

public interface IImGuiTheme {
    void preRender();
    void postRender();
    ImGuiStyle applyStyle();
    ImGuiIO applyConfig(final ImGuiIO io);
    ImFontConfig applyFontConfig(final ImGuiIO io);

    default byte[] loadFromResources(String path) {
        try {
            return IOUtils.toByteArray(this.getClass().getClassLoader().getResourceAsStream(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
