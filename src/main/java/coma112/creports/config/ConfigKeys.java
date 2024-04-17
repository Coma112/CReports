package coma112.creports.config;

import coma112.creports.CReports;
import coma112.creports.processor.MessageProcessor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ConfigKeys {
    public static String REPORT_ITEM_MATERIAL = getString("report-item.material");
    public static String REPORT_ITEM_NAME = getString("report-item.name");
    public static List<String> REPORT_ITEM_LORE = getLoreList();

    private static String getString(@NotNull String path) {
        return MessageProcessor.process(CReports.getInstance().getReportsYML().getString(path));
    }

    private static List<String> getLoreList() {
        List<String> originalList = CReports.getInstance().getReportsYML().getLoreList("report-item.lore");
        List<String> processedList = new ArrayList<>();

        originalList.forEach(line -> processedList.add(MessageProcessor.process(line)));

        return processedList;
    }
}
