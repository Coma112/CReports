package coma112.creports.managers;

import coma112.creports.interfaces.IReport;
import org.jetbrains.annotations.NotNull;

public class Report implements IReport {
    private final int id;
    private final String player;
    private final String target;
    private final String reason;
    private final String date;

    public Report(int id, @NotNull String player, @NotNull String target, @NotNull String reason, @NotNull String date) {
        this.id = id;
        this.player = player;
        this.target = target;
        this.reason = reason;
        this.date = date;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getPlayer() {
        return player;
    }

    @Override
    public String getTarget() {
        return target;
    }

    @Override
    public String getReason() {
        return reason;
    }

    @Override
    public String getDate() {
        return date;
    }
}
