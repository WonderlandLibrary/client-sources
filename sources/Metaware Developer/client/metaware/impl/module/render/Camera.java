package client.metaware.impl.module.render;

import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.api.properties.property.Property;

@ModuleInfo(name = "Camera", renderName = "Camera", category = Category.VISUALS)
public class Camera extends Module {

    public Property<Boolean> viewClip = new Property<>("View Clip", true);
    public Property<Boolean> noHurtCam = new Property<>("NoHurtCam", true);
    public Property<Boolean> containerAnimation = new Property<>("Container Animations", true);

}
