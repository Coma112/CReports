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
import java.util.Arrays;

@CommandInfo(name = "report", requiresPlayer = true)
public class CommandReport extends PluginCommand {

    public CommandReport() {
        super("report");
        //report [player] [reason]
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

        java.util.Date currentDate = new java.util.Date();
        java.sql.Date sqlCurrentDate = new java.sql.Date(currentDate.getTime());

        player.sendMessage("A játékos sikeresen fel lett jelentve az indokkal: " + reason);

        CReports.getDatabaseManager().createReport(player, target, reason, sqlCurrentDate);
        return true;
    }
}
