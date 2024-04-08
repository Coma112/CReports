package coma112.creports.language;

import coma112.creports.CReports;
import coma112.creports.utils.ConfigUtils;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Language extends ConfigUtils {
    public Language() {
        super(CReports.getInstance().getDataFolder().getPath() + File.separator + "locales", "messages_en");

        YamlConfiguration yml = getYml();

        yml.addDefault("prefix", "&b&lREPORTS &8| ");
        yml.addDefault("messages.no-permission", "&cYou do not have permission to do this!");
        yml.addDefault("messages.reload", "&aI have successfully reloaded the files!");
        yml.addDefault("messages.player-required", "&cPlayer is required!");

        yml.options().copyDefaults(true);
        save();
    }
}
