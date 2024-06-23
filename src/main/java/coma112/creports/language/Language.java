package coma112.creports.language;

import coma112.creports.CReports;
import coma112.creports.utils.ConfigUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class Language extends ConfigUtils {
    public Language(@NotNull String name) {
        super(CReports.getInstance().getDataFolder().getPath() + File.separator + "locales", name);
        save();
    }
}
