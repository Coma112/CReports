package coma112.creports.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import coma112.creports.CReports;
import coma112.creports.events.ReportClaimedEvent;
import coma112.creports.events.ReportCreatedEvent;
import coma112.creports.managers.Report;
import lombok.Getter;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;

@Getter
public class MongoDB extends AbstractDatabase {
    private final MongoClient mongoClient;
    private final MongoDatabase database;
    private final MongoCollection<Document> reportCollection;
    private final MongoCollection<Document> counterCollection;

    public MongoDB(@NotNull ConfigurationSection section) {
        String host = section.getString("host");
        int port = section.getInt("port");
        String databaseName = section.getString("database");

        String username = section.getString("username");
        String password = section.getString("password");
        String connectionString;

        if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) connectionString = String.format("mongodb://%s:%s@%s:%d/%s", username, password, host, port, databaseName);
        else connectionString = String.format("mongodb://%s:%d/%s", host, port, databaseName);

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .build();

        mongoClient = MongoClients.create(settings);
        database = mongoClient.getDatabase(Objects.requireNonNull(databaseName));
        reportCollection = database.getCollection("report");
        counterCollection = database.getCollection("counter");

        initializeCounter();
    }

    public void initializeCounter() {
        if (counterCollection.find(eq("_id", "reportId")).first() == null) counterCollection.insertOne(new Document("_id", "reportId").append("seq", 0));
    }

    public void createCollection() {
        database.getCollection("report");
    }

    @Override
    public boolean isConnected() {
        return mongoClient != null;
    }

    @Override
    public void disconnect() {
        if (isConnected()) {
            mongoClient.close();
        }
    }

    @Override
    public void createReport(@NotNull OfflinePlayer player, @NotNull OfflinePlayer target, @NotNull String reportText, @NotNull String reportDate) {
        int id = getNextId();
        Document report = new Document("ID", id)
                .append("PLAYER", player.getName())
                .append("TARGET", target.getName())
                .append("REPORT_TEXT", reportText)
                .append("REPORT_DATE", reportDate)
                .append("CLAIMER", null);

        reportCollection.insertOne(report);
        CReports.getInstance().getServer().getPluginManager().callEvent(new ReportCreatedEvent(player, target, reportText, reportDate));
    }

    @Override
    public List<Report> getReports() {
        List<Report> reports = new ArrayList<>();

        for (Document document : reportCollection.find()) {
            int id = document.getInteger("ID");
            String player = document.getString("PLAYER");
            String target = document.getString("TARGET");
            String reason = document.getString("REPORT_TEXT");
            String date = document.getString("REPORT_DATE");
            reports.add(new Report(id, player, target, reason, date));
        }

        return reports;
    }

    @Override
    public void claimReport(@NotNull Player player, @NotNull Report report) {
        reportCollection.updateOne(eq("ID", report.id()), Updates.set("CLAIMER", player.getName()));
        CReports.getInstance().getServer().getPluginManager().callEvent(new ReportClaimedEvent(Bukkit.getPlayerExact(report.player()), Objects.requireNonNull(Bukkit.getPlayerExact(report.target())), player));
    }

    @Override
    public String getClaimer(@NotNull Report report) {
        Document result = reportCollection.find(eq("ID", report.id())).first();
        if (result != null) return result.getString("CLAIMER");
        return "";
    }

    @Override
    public String getPlayer(@NotNull Report report) {
        Document result = reportCollection.find(eq("ID", report.id())).first();
        if (result != null) return result.getString("PLAYER");
        return "";
    }

    @Override
    public boolean alreadyReported(@NotNull OfflinePlayer player) {
        Document query = new Document("TARGET", player.getName())
                .append("CLAIMER", null);
        long count = reportCollection.countDocuments(query);
        return count > 0;
    }

    @Override
    public int getClaimedReports(@NotNull OfflinePlayer player) {
        Document query = new Document("CLAIMER", player.getName());
        long count = reportCollection.countDocuments(query);
        return (int) count;
    }

    @Override
    public void reconnect() {
        disconnect();
        new MongoDB(Objects.requireNonNull(CReports.getInstance().getConfiguration().getSection("database.mongodb")));
    }

    private int getNextId() {
        Document result = counterCollection.findOneAndUpdate(
                eq("_id", "reportId"),
                Updates.inc("seq", 1),
                new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)
        );

        return Objects.requireNonNull(result).getInteger("seq");
    }
}
