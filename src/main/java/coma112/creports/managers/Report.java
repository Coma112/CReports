package coma112.creports.managers;

import coma112.creports.interfaces.IReport;
import org.jetbrains.annotations.NotNull;

public record Report(int id, @NotNull String player, @NotNull String target, @NotNull String reason, @NotNull String date) implements IReport {
}
