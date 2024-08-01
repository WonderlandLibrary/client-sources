package wtf.diablo.client.module.api.management.repository;

import wtf.diablo.client.module.api.management.IModule;

import java.util.Collection;

public interface IModuleRepository {
    Collection<IModule> getModules();
    IModule getModuleInstance(final Class<? extends IModule> clazz);
}