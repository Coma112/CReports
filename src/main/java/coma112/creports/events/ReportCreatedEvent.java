package coma112.creports.events;

import lombok.Getter;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
public final class ReportCreatedEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final OfflinePlayer sender;
    private final OfflinePlayer target;
    private final String reason;
    private final String date;

    public ReportCreatedEvent(@Nullable OfflinePlayer sender, @NotNull OfflinePlayer target, @NotNull String reason, @NotNull String date) {
        this.sender = sender;
        this.target = target;
        this.reason = reason;
        this.date = date;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

}

