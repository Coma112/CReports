package coma112.creports.managers;

import org.jetbrains.annotations.NotNull;

public record Report(int id, @NotNull String player, @NotNull String target, @NotNull String reason, @NotNull String date) {}
