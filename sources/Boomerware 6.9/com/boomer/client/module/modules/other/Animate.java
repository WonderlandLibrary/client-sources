package com.boomer.client.module.modules.other;

import com.boomer.client.module.Module;
import com.boomer.client.utils.value.impl.NumberValue;

import java.awt.*;

/**
 * @author Xen for BoomerWare
 * @since 8/11/2019
 **/
public class Animate extends Module {
    public NumberValue<Integer> speed = new NumberValue<Integer>("Speed",8,1,10,1);

    public Animate() {
        super("Animate", Category.OTHER, new Color(0).getRGB());
        addValues(speed);
        setHidden(true);
    }


}
