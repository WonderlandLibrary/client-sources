package dev.excellent.client.script.entity;

import dev.excellent.client.script.ScriptConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Script {
    private final String name;
    private final ScriptConstructor scriptConstructor = ScriptConstructor.create();
}
