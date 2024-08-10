package cc.slack.features.commands.api;

import lombok.Getter;

@Getter
public abstract class CMD {

    final CMDInfo cmdInfo = getClass().getAnnotation(CMDInfo.class);
    private final String name = cmdInfo.name();
    private final String description = cmdInfo.description();
    private final String alias = cmdInfo.alias();

    public abstract void onCommand(String[] args, String cmd);
}
