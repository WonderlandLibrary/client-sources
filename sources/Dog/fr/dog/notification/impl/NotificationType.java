package fr.dog.notification.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.Color;

@AllArgsConstructor
@Getter
public enum NotificationType {
    INFO(new Color(-1)),
    WARNING(new Color(55, 150, 0)),
    ERROR(new Color(150, 0, 0));

    private final Color color;
}
