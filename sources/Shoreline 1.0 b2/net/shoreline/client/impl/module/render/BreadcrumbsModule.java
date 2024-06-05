package net.shoreline.client.impl.module.render;

import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class BreadcrumbsModule extends ToggleModule
{
    /**
     *
     */
    public BreadcrumbsModule()
    {
        super("Breadcrumbs", "Renders a line connecting all previous positions",
                ModuleCategory.RENDER);
    }
}
