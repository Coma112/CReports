package coma112.creports.commands;

import coma112.creports.CReports;
import coma112.creports.enums.keys.MessageKeys;
import coma112.creports.menu.menus.ReportMenu;
import coma112.creports.utils.MenuUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.annotation.CommandPermission;

import java.text.SimpleDateFormat;
import java.util.Objects;

@Command({"report", "creports"})
public class CommandReport {

    @Subcommand("new")
    @CommandPermission("creports.use")
    public void newReport(@NotNull Player player, OfflinePlayer target, @NotNull String reason) {
        if (!target.isOnline()) {
            player.sendMessage(MessageKeys.OFFLINE_PLAYER.getMessage());
            return;
        }

        if (target == player) {
            player.sendMessage(MessageKeys.CANT_REPORT_YOURSELF.getMessage());
            return;
        }

        if (CReports.getDatabaseManager().alreadyReported(target)) {
            player.sendMessage(MessageKeys.ALREADY_REPORTED.getMessage());
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = dateFormat.format(new java.util.Date());

        Bukkit.getOnlinePlayers().forEach(onlinePlayers -> {
            if (onlinePlayers.hasPermission("creports.admin")) {
                onlinePlayers.sendMessage(MessageKeys.ADMIN_MESSAGE.getMessage()
                        .replace("{reason}", reason)
                        .replace("{date}", formattedDateTime)
                        .replace("{target}", Objects.requireNonNull(target.getName()))
                        .replace("{sender}", player.getName()));
            }
        });

        player.sendMessage(MessageKeys.SUCCESSFUL_REPORT.getMessage()
                .replace("{target}", Objects.requireNonNull(target.getName())));

        CReports.getDatabaseManager().createReport(player, target, (reason + " ").trim(), formattedDateTime);
    }

    @Subcommand("reload")
    @CommandPermission("creports.reload")
    public void reload(@NotNull CommandSender sender) {
        CReports.getInstance().getLanguage().reload();
        CReports.getInstance().getConfiguration().reload();
        CReports.getDatabaseManager().reconnect();
        sender.sendMessage(MessageKeys.RELOAD.getMessage());
    }

    @Subcommand("menu")
    @CommandPermission("creports.menu")
    public void menu(@NotNull Player player) {
        new ReportMenu(MenuUtils.getMenuUtils(player)).open();
    }
}
