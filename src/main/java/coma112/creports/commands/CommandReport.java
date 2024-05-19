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

import java.text.SimpleDateFormat;
import java.util.Objects;

@Command({"report", "creports"})
public class CommandReport {

    @Subcommand("newReport")
    public void newReport(CommandSender sender, OfflinePlayer target, @NotNull String reason) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(MessageKeys.PLAYER_REQUIRED.getMessage());
            return;
        }

        if (!target.isOnline()) {
            player.sendMessage(MessageKeys.OFFLINE_PLAYER.getMessage());
            return;
        }

        if (target == player) {
            player.sendMessage(MessageKeys.CANT_REPORT_YOURSELF.getMessage());
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
    public void reload(CommandSender sender) {
        if (!sender.hasPermission("creports.reload") || !sender.hasPermission("creports.admin")) {
            sender.sendMessage(MessageKeys.NO_PERMISSION.getMessage());
            return;
        }

        CReports.getInstance().getLanguage().reload();
        CReports.getInstance().getConfiguration().reload();
        CReports.getDatabaseManager().reconnect(Objects.requireNonNull(CReports.getInstance().getConfiguration().getSection("database.mysql")));
    }

    @Subcommand("menu")
    public void menu(CommandSender sender) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(MessageKeys.PLAYER_REQUIRED.getMessage());
            return;
        }

        new ReportMenu(MenuUtils.getMenuUtils(player)).open();
    }
}
