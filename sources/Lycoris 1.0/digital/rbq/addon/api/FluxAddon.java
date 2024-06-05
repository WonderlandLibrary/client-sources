package digital.rbq.addon.api;

import digital.rbq.addon.LycorisAPI;
import digital.rbq.addon.api.command.AddonCommand;
import digital.rbq.addon.api.module.AddonModule;

import java.util.List;

public abstract class LycorisAddon {
    /**
     *
     * @return 插件名称
     */
    public abstract String getAPIName();

    /**
     *
     * @return 插件版本号
     */
    public abstract float getVersion();

    /**
     *
     * @param api LycorisAPI对象
     */
    public abstract void initAPI(LycorisAPI api);

    /**
     *
     * @return 获取作者信息
     */
    public abstract String getAuthor();
    /**
     *
     * @return 获取Module列表
     */
    public abstract List<AddonModule> getModules();

    /**
     *
     * @return 获取Module列表
     */
    public abstract List<AddonCommand> getCommands();
}
