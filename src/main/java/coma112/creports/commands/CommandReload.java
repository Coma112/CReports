package coma112.creports.commands;

import coma112.creports.CReports;
import coma112.creports.language.MessageKeys;
import coma112.creports.subcommand.CommandInfo;
import coma112.creports.subcommand.PluginCommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

@CommandInfo(name = "reportsreload", requiresPlayer = false, permission = "creports.reload")
public class CommandReload extends PluginCommand {

    public CommandReload() {
        super("reportsreload");
    }

    @Override
    public boolean run(@NotNull CommandSender sender, @NotNull String[] args) {

        CReports.getInstance().getLanguage().reload();
        CReports.getInstance().getReportsYML().reload();
        sender.sendMessage(MessageKeys.RELOAD);
        return true;

    }
}

