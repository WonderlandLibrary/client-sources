package pw.stamina.causam.scan.method.config;

import java.lang.reflect.*;

public interface ConfigurationLocatorService
{
    ListenerConfiguration locateConfiguration(final Method p0);
}
