package dev.excellent.client.macros;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Macro {
    private String name;
    private int keyCode;
    private String message;
}