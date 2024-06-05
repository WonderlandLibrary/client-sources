package net.shoreline.client.api.config.setting;

import net.shoreline.client.Shoreline;
import net.shoreline.client.api.config.ConfigContainer;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.api.render.anim.Animation;

/**
 *
 *
 * @author linus
 * @since 1.0
 *
 * @see BooleanConfig
 */
public class ToggleConfig extends BooleanConfig
{
    public ToggleConfig(String name, String desc, Boolean val)
    {
        super(name, desc, val);
    }

    /**
     *
     * @param val The param value
     */
    @Override
    public void setValue(Boolean val)
    {
        super.setValue(val);
        ConfigContainer container = getContainer();
        if (container instanceof ToggleModule toggle)
        {
            Animation anim = toggle.getAnimation();
            anim.setState(val);
            if (val)
            {
                Shoreline.EVENT_HANDLER.subscribe(toggle);
            }
            else
            {
                Shoreline.EVENT_HANDLER.unsubscribe(toggle);
            }
        }
    }
}
