package coma112.creports.language;

import coma112.creports.CReports;
import coma112.creports.processor.MessageProcessor;
import org.jetbrains.annotations.NotNull;

public class MessageKeys {
    public static String PREFIX = getString("prefix");
    public static String NO_PERMISSION = PREFIX + getString("messages.no-permission");
    public static String RELOAD = PREFIX + getString("messages.reload");
    public static String PLAYER_REQUIRED = PREFIX + getString("messages.player-required");
    public static String REPORT_RIGHT_USAGE = PREFIX + getString("messages.report-right-usage");
    public static String CANT_REPORT_YOURSELF = PREFIX + getString("messages.can't-report-yourself");
    public static String ADMIN_MESSAGE = PREFIX + getString("messages.admin-message");
    public static String SUCCESSFUL_REPORT = PREFIX + getString("messages.successful-report");
    public static String OFFLINE_PLAYER = PREFIX + getString("messages.player-is-offline");
    public static String FIRST_PAGE = PREFIX + getString("messages.first-page");
    public static String LAST_PAGE = PREFIX + getString("messages.last-page");

    private static String getString(@NotNull String path) {
        return MessageProcessor.process(CReports.getInstance().getLanguage().getString(path));
    }
}


