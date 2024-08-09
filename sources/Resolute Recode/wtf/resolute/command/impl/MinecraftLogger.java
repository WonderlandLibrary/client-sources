package wtf.resolute.command.impl;

import wtf.resolute.command.Logger;
import wtf.resolute.utiled.client.IMinecraft;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class MinecraftLogger implements Logger, IMinecraft {
    @Override
    public void log(String message) {
        print(message);
    }
}
