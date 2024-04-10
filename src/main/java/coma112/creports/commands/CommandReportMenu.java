package coma112.creports.commands;

import coma112.creports.language.MessageKeys;
import coma112.creports.menu.ReportMenu;
import coma112.creports.subcommand.CommandInfo;
import coma112.creports.subcommand.PluginCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@CommandInfo(name = "reports", requiresPlayer = false, permission = "creports.admin")
public class CommandReportMenu extends PluginCommand {

    public CommandReportMenu() {
        super("reports");
    }

    @Override
    public boolean run(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(MessageKeys.PLAYER_REQUIRED);
            return true;
        }

        ReportMenu.open(player);
        return true;

    }
}
