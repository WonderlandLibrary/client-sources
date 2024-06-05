package digital.rbq.module.implement.Render;

import digital.rbq.module.Category;
import digital.rbq.module.Module;
import digital.rbq.module.value.FloatValue;

/**
 * Created by John on 2016/10/20.
 */
public class NoFov extends Module {
	public static FloatValue fov = new FloatValue("NoFov", "Fov", 1.0f, 0.1f, 1.5f, 0.01f);
	public NoFov() {
		super("NoFov", Category.Render, false);
	}

}
