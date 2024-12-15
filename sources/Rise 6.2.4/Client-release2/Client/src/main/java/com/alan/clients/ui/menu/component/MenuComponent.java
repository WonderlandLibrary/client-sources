package com.alan.clients.ui.menu.component;

import com.alan.clients.ui.menu.MenuColors;
import com.alan.clients.util.Accessor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuComponent implements Accessor, MenuColors {

    private double x;
    private double y;
    private double width;
    private double height;

    public MenuComponent(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
