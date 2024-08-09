package dev.darkmoon.client.manager.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotificationType {
    SUCCESS("A"),
    ERROR("A"),
    INFO("A"),
    WARNING("A");
    private final String icon;
}
