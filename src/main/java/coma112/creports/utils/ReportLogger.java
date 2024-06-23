package coma112.creports.utils;

import coma112.creports.processor.MessageProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.ParameterizedMessage;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;


public class ReportLogger {
    static final Logger logger = LogManager.getLogger("CReports");

    public static void info(@NotNull String msg, @NotNull Object... objs) {
        logger.info(msg, objs);
    }

    public static void warn(@NotNull String msg, @NotNull Object... objs) {
        logger.warn(msg, objs);
    }

    public static void error(@NotNull String msg, @NotNull Object... objs) {
        logger.error(msg, objs);
    }

    public static void colored(@NotNull String msg, @NotNull Object... objs) {
        final Message message = new ParameterizedMessage(msg, objs);
        Bukkit.getConsoleSender().sendMessage(MessageProcessor.process(message.getFormattedMessage()));
    }

}
