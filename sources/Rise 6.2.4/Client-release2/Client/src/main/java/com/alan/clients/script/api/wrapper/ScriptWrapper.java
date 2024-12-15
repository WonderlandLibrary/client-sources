package com.alan.clients.script.api.wrapper;

import com.alan.clients.script.api.API;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class ScriptWrapper<T> extends API {
    protected T wrapped;
}
