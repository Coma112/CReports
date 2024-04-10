package coma112.creports.commands;

import coma112.creports.CReports;
import coma112.creports.language.MessageKeys;
import coma112.creports.subcommand.CommandInfo;
import coma112.creports.subcommand.PluginCommand;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Objects;

@CommandInfo(name = "report", requiresPlayer = true)
public class CommandReport extends PluginCommand {

    public CommandReport() {
        super("report");
    }

    @Override
    public boolean run(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(MessageKeys.PLAYER_REQUIRED);
            return true;
        }

        if (args.length < 2) {
            player.sendMessage(MessageKeys.REPORT_RIGHT_USAGE);
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        String reason = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = dateFormat.format(new java.util.Date());

        if (target == player) {
            player.sendMessage(MessageKeys.CANT_REPORT_YOURSELF);
            return true;
        }

        Bukkit.getOnlinePlayers().forEach(onlinePlayers -> {
            if (onlinePlayers.hasPermission("creports.admin") || onlinePlayers.isOp()) {
                onlinePlayers.sendMessage(MessageKeys.ADMIN_MESSAGE
                        .replace("{reason}", reason)
                        .replace("{date}", formattedDateTime)
                        .replace("{target}", Objects.requireNonNull(target.getName()))
                        .replace("{sender}", player.getName()));
            }
        });

        player.sendMessage(MessageKeys.SUCCESSFUL_REPORT
                .replace("{target}", Objects.requireNonNull(target.getName())));


        CReports.getDatabaseManager().createReport(player, target, reason, formattedDateTime);
        return true;
    }
}

