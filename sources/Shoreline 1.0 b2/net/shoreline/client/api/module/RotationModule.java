package net.shoreline.client.api.module;

import net.shoreline.client.init.Managers;

/**
 *
 *
 * @author linus
 * @since 1.0
 *
 * @see net.shoreline.client.api.manager.player.rotation.RotationManager
 */
public class RotationModule extends ToggleModule
{
    /**
     *
     * @param name     The module unique identifier
     * @param desc     The module description
     * @param category The module category
     */
    public RotationModule(String name, String desc, ModuleCategory category)
    {
        super(name, desc, category);
    }

    /**
     *
     * @param yaw
     * @param pitch
     */
    protected void setRotation(float yaw, float pitch)
    {
        Managers.ROTATION.setRotation(this, yaw, pitch);
    }

    /**
     *
     * @return
     */
    public boolean isRotationBlocked()
    {
        RotationModule head = Managers.ROTATION.getRotatingModule();
        if (head != null)
        {
            return head != this;
        }
        return false;
    }
}
