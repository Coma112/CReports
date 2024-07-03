package coma112.creports.enums.keys;

import coma112.creports.CReports;
import coma112.creports.processor.MessageProcessor;
import org.jetbrains.annotations.NotNull;

public enum ConfigKeys {
    LANGUAGE("language"),
    DATABASE("database.type"),
    DATEFORMAT("dateformat"),

    UNCLAIMED_MENU_TICK("unclaimed-menu.update-tick"),
    UNCLAIMED_MENU_TITLE("unclaimed-menu.title"),
    UNCLAIMED_BACK_SLOT("unclaimed-menu.back-item.slot"),
    UNCLAIMED_FORWARD_SLOT("unclaimed-menu.forward-item.slot"),
    UNCLAIMED_MENU_SIZE("unclaimed-menu.size"),

    CLAIMED_MENU_TICK("claimed-menu.update-tick"),
    CLAIMED_MENU_TITLE("claimed-menu.title"),
    CLAIMED_BACK_SLOT("claimed-menu.back-item.slot"),
    CLAIMED_FORWARD_SLOT("claimed-menu.forward-item.slot"),
    CLAIMED_MENU_SIZE("claimed-menu.size"),

    COMBINED_MENU_TICK("combined-menu.update-tick"),
    COMBINED_MENU_TITLE("combined-menu.title"),
    COMBINED_BACK_SLOT("combined-menu.back-item.slot"),
    COMBINED_FORWARD_SLOT("combined-menu.forward-item.slot"),
    COMBINED_MENU_SIZE("combined-menu.size"),

    MAIN_MENU_TITLE("main-menu.title"),
    MAIN_CLAIMED_MENU_SLOT("main-menu.claimed-menu-item.slot"),
    MAIN_UNCLAIMED_MENU_SLOT("main-menu.unclaimed-menu-item.slot"),
    MAIN_COMBINED_MENU_SLOT("main-menu.combined-menu-item.slot"),
    MAIN_MENU_SIZE("main-menu.size"),
    MAIN_MENU_FILLER_GLASS("main-menu.filler-glass"),

    WEBHOOK_REPORT_CLAIMED_EMBED_URL("webhook.report-claim-embed.url"),
    WEBHOOK_REPORT_CLAIMED_EMBED_ENABLED("webhook.report-claim-embed.enabled"),
    WEBHOOK_REPORT_CLAIMED_EMBED_TITLE("webhook.report-claim-embed.title"),
    WEBHOOK_REPORT_CLAIMED_EMBED_DESCRIPTION("webhook.report-claim-embed.description"),
    WEBHOOK_REPORT_CLAIMED_EMBED_COLOR("webhook.report-claim-embed.color"),
    WEBHOOK_REPORT_CLAIMED_EMBED_AUTHOR_NAME("webhook.report-claim-embed.author-name"),
    WEBHOOK_REPORT_CLAIMED_EMBED_AUTHOR_URL("webhook.report-claim-embed.author-url"),
    WEBHOOK_REPORT_CLAIMED_EMBED_AUTHOR_ICON("webhook.report-claim-embed.author-icon"),
    WEBHOOK_REPORT_CLAIMED_EMBED_FOOTER_TEXT("webhook.report-claim-embed.footer-text"),
    WEBHOOK_REPORT_CLAIMED_EMBED_FOOTER_ICON("webhook.report-claim-embed.footer-icon"),
    WEBHOOK_REPORT_CLAIMED_EMBED_THUMBNAIL("webhook.report-claim-embed.thumbnail"),
    WEBHOOK_REPORT_CLAIMED_EMBED_IMAGE("webhook.report-claim-embed.image"),

    WEBHOOK_REPORT_CREATED_EMBED_URL("webhook.report-created-embed.url"),
    WEBHOOK_REPORT_CREATED_EMBED_ENABLED("webhook.report-created-embed.enabled"),
    WEBHOOK_REPORT_CREATED_EMBED_TITLE("webhook.report-created-embed.title"),
    WEBHOOK_REPORT_CREATED_EMBED_DESCRIPTION("webhook.report-created-embed.description"),
    WEBHOOK_REPORT_CREATED_EMBED_COLOR("webhook.report-created-embed.color"),
    WEBHOOK_REPORT_CREATED_EMBED_AUTHOR_NAME("webhook.report-created-embed.author-name"),
    WEBHOOK_REPORT_CREATED_EMBED_AUTHOR_URL("webhook.report-created-embed.author-url"),
    WEBHOOK_REPORT_CREATED_EMBED_AUTHOR_ICON("webhook.report-created-embed.author-icon"),
    WEBHOOK_REPORT_CREATED_EMBED_FOOTER_TEXT("webhook.report-created-embed.footer-text"),
    WEBHOOK_REPORT_CREATED_EMBED_FOOTER_ICON("webhook.report-created-embed.footer-icon"),
    WEBHOOK_REPORT_CREATED_EMBED_THUMBNAIL("webhook.report-created-embed.thumbnail"),
    WEBHOOK_REPORT_CREATED_EMBED_IMAGE("webhook.report-created-embed.image");

    private final String path;

    ConfigKeys(@NotNull final String path) {
        this.path = path;
    }

    public String getString() {
        return MessageProcessor.process(CReports.getInstance().getConfiguration().getString(path));
    }

    public boolean getBoolean() {
        return CReports.getInstance().getConfiguration().getBoolean(path);
    }

    public int getInt() {
        return CReports.getInstance().getConfiguration().getInt(path);
    }

}
