package coma112.creports.commands;

import coma112.creports.CReports;
import coma112.creports.language.MessageKeys;
import coma112.creports.menu.menus.ReportMenu;
import coma112.creports.subcommand.CommandInfo;
import coma112.creports.subcommand.PluginCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@CommandInfo(name = "reports", requiresPlayer = true, permission = "creports.admin")
public class CommandReportMenu extends PluginCommand {

    public CommandReportMenu() {
        super("reports");
    }

    @Override
    public boolean run(@NotNull Player player, @NotNull String[] args) {
        new ReportMenu(CReports.getInstance().getMenuUtils(player)).open();
        return true;
    }
}
