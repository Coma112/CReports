package coma112.creports.events;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
public final class ReportClaimedEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Player sender;
    private final Player target;
    private final Player claimer;

    public ReportClaimedEvent(@Nullable Player sender, @NotNull Player target, @NotNull Player claimer) {
        this.sender = sender;
        this.target = target;
        this.claimer = claimer;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

}
