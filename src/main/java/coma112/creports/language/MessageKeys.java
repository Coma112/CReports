package coma112.creports.language;

import coma112.creports.CReports;
import coma112.creports.processor.MessageProcessor;
import org.jetbrains.annotations.NotNull;

public class MessageKeys {
    public static String PREFIX = getString("prefix");
    public static String NO_PERMISSION = PREFIX + getString("messages.no-permission");
    public static String RELOAD = PREFIX + getString("messages.reload");
    public static String PLAYER_REQUIRED = PREFIX + getString("messages.player-required");

    private static String getString(@NotNull String path) {
        return MessageProcessor.process(CReports.getInstance().getLanguage().getString(path));
    }
}


