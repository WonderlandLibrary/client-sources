package cc.slack.features.modules.impl.render;

import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.BooleanValue;

@ModuleInfo(
        name = "Camera",
        category = Category.RENDER
)
public class Camera extends Module {

    public final BooleanValue nohurtcam = new BooleanValue("No Hurt Cam", false);
    public final BooleanValue noclip = new BooleanValue("No Clip", false);
    public final BooleanValue nofire = new BooleanValue("No Fire", false);


    public Camera() {
        addSettings(nohurtcam, noclip, nofire);
    }

}
