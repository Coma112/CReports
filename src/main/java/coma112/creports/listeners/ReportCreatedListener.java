package coma112.creports.listeners;

import coma112.creports.enums.keys.ConfigKeys;
import coma112.creports.events.ReportCreatedEvent;
import coma112.creports.hooks.Webhook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.IOException;

import static coma112.creports.hooks.Webhook.replacePlaceholdersReportCreated;

public class ReportCreatedListener implements Listener {
    @EventHandler
    public void onCreate(final ReportCreatedEvent event) throws IOException, NoSuchFieldException, IllegalAccessException {
        Webhook.sendWebhook(
                ConfigKeys.WEBHOOK_REPORT_CREATED_EMBED_URL.getString(),
                ConfigKeys.WEBHOOK_REPORT_CREATED_EMBED_ENABLED.getBoolean(),
                replacePlaceholdersReportCreated(ConfigKeys.WEBHOOK_REPORT_CREATED_EMBED_DESCRIPTION.getString(), event),
                ConfigKeys.WEBHOOK_REPORT_CREATED_EMBED_COLOR.getString(),
                replacePlaceholdersReportCreated(ConfigKeys.WEBHOOK_REPORT_CREATED_EMBED_AUTHOR_NAME.getString(), event),
                replacePlaceholdersReportCreated(ConfigKeys.WEBHOOK_REPORT_CREATED_EMBED_AUTHOR_URL.getString(), event),
                replacePlaceholdersReportCreated(ConfigKeys.WEBHOOK_REPORT_CREATED_EMBED_AUTHOR_ICON.getString(), event),
                replacePlaceholdersReportCreated(ConfigKeys.WEBHOOK_REPORT_CREATED_EMBED_FOOTER_TEXT.getString(), event),
                replacePlaceholdersReportCreated(ConfigKeys.WEBHOOK_REPORT_CREATED_EMBED_FOOTER_ICON.getString(), event),
                replacePlaceholdersReportCreated(ConfigKeys.WEBHOOK_REPORT_CREATED_EMBED_THUMBNAIL.getString(), event),
                replacePlaceholdersReportCreated(ConfigKeys.WEBHOOK_REPORT_CREATED_EMBED_TITLE.getString(), event),
                replacePlaceholdersReportCreated(ConfigKeys.WEBHOOK_REPORT_CREATED_EMBED_IMAGE.getString(), event)
        );
    }
}
