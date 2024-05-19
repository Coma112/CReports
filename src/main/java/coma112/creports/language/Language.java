package coma112.creports.language;

import coma112.creports.CReports;
import coma112.creports.utils.ConfigUtils;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Language extends ConfigUtils {
    public Language() {
        super(CReports.getInstance().getDataFolder().getPath() + File.separator + "locales", "messages_en");

        YamlConfiguration yml = getYml();

        yml.addDefault("messages.no-permission", "&b&lREPORTS &8| &cYou do not have permission to do this!");
        yml.addDefault("messages.reload", "&b&lREPORTS &8| &aI have successfully reloaded the files!");
        yml.addDefault("messages.player-required", "&b&lREPORTS &8| &cPlayer is required!");
        yml.addDefault("messages.report-right-usage", "&b&lREPORTS &8| &c/report [player] [reason]");
        yml.addDefault("messages.can't-report-yourself", "&b&lREPORTS &8| &cYou can't report yourself!");
        yml.addDefault("messages.admin-message", "&b&lREPORTS &8| &2{sender} &areported &2{target} &a@ &2{date} &afor &2{reason}&a.");
        yml.addDefault("messages.successful-report", "&b&lREPORTS &8| &aYou have successfully reported &2{target}&a!");
        yml.addDefault("messages.player-is-offline", "&b&lREPORTS &8| &cPlayer is offline!");
        yml.addDefault("messages.first-page", "&b&lREPORTS &8| &cYou are already on the first page!");
        yml.addDefault("messages.last-page", "&b&lREPORTS &8| &cYou are already on the last page!");

        yml.options().copyDefaults(true);
        save();
    }
}
