package de.resourcepacks24.utils;

import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.net.URI;

public class Utils
{
    public static void openWebpage(URI uri)
    {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;

        if (desktop != null && desktop.isSupported(Action.BROWSE))
        {
            try
            {
                desktop.browse(uri);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }
}
