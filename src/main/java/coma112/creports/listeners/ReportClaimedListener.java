package coma112.creports.listeners;

import coma112.creports.enums.keys.ConfigKeys;
import coma112.creports.events.ReportClaimedEvent;
import coma112.creports.hooks.Webhook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.IOException;

import static coma112.creports.hooks.Webhook.replacePlaceholdersReportClaimed;

public class ReportClaimedListener implements Listener {
    @EventHandler
    public void onClaim(final ReportClaimedEvent event) throws IOException, NoSuchFieldException, IllegalAccessException {
        Webhook.sendWebhook(
                ConfigKeys.WEBHOOK_REPORT_CLAIMED_EMBED_URL.getString(),
                ConfigKeys.WEBHOOK_REPORT_CLAIMED_EMBED_ENABLED.getBoolean(),
                replacePlaceholdersReportClaimed(ConfigKeys.WEBHOOK_REPORT_CLAIMED_EMBED_DESCRIPTION.getString(), event),
                ConfigKeys.WEBHOOK_REPORT_CLAIMED_EMBED_COLOR.getString(),
                replacePlaceholdersReportClaimed(ConfigKeys.WEBHOOK_REPORT_CLAIMED_EMBED_AUTHOR_NAME.getString(), event),
                replacePlaceholdersReportClaimed(ConfigKeys.WEBHOOK_REPORT_CLAIMED_EMBED_AUTHOR_URL.getString(), event),
                replacePlaceholdersReportClaimed(ConfigKeys.WEBHOOK_REPORT_CLAIMED_EMBED_AUTHOR_ICON.getString(), event),
                replacePlaceholdersReportClaimed(ConfigKeys.WEBHOOK_REPORT_CLAIMED_EMBED_FOOTER_TEXT.getString(), event),
                replacePlaceholdersReportClaimed(ConfigKeys.WEBHOOK_REPORT_CLAIMED_EMBED_FOOTER_ICON.getString(), event),
                replacePlaceholdersReportClaimed(ConfigKeys.WEBHOOK_REPORT_CLAIMED_EMBED_THUMBNAIL.getString(), event),
                replacePlaceholdersReportClaimed(ConfigKeys.WEBHOOK_REPORT_CLAIMED_EMBED_TITLE.getString(), event),
                replacePlaceholdersReportClaimed(ConfigKeys.WEBHOOK_REPORT_CLAIMED_EMBED_IMAGE.getString(), event)
        );
    }
}
