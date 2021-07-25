package me.maquzo.TTT.utils;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import lombok.Getter;
import me.maquzo.TTT.TTT;
import org.bson.Document;

@Getter
public class PlayerData {

    private TTT plugin = TTT.getPlugin();

    private java.util.UUID UUID;
    private String playerName;


    private Stat stats = new Stat();

    public PlayerData(java.util.UUID uuid, String name) {
        this.UUID = uuid;
        this.playerName = name;
    }

    public void resetData() {
        this.stats.setKarma(0);
        this.stats.setGamesPlayed(0);
        this.stats.setChestClicked(0);
        this.stats.setKills(0);
        this.stats.setTesterUsed(0);
        this.stats.setPassUsed(0);
        this.stats.setWins(0);
        this.stats.setPassTraitor(0);
        this.stats.setPassDetective(0);
        this.stats.setDetectivePassActive(false);
        this.stats.setTraitorPassActive(false);
        this.stats.setFirstKillDone(false);
        this.stats.setFirstWinDone(false);
        this.stats.setFIRST_TRAITOR_WIN_Done(false);
        this.stats.setFIRST_DET_WIN_Done(false);
        this.stats.setKILLS_1_Done(false);
        this.stats.setKILLS_2_Done(false);
        this.stats.setKILLS_3_Done(false);
        this.stats.setKILLS_4_Done(false);
        this.stats.setWINS_1_Done(false);
        this.stats.setWINS_2_Done(false);
        this.stats.setWINS_3_Done(false);
        this.stats.setWINS_4_Done(false);
        this.stats.setWINS_5_Done(false);
        this.stats.setKARMA_1(false);
        this.stats.setKARMA_2(false);
        this.stats.setKARMA_3(false);
        this.stats.setTester_used_done(false);
        this.stats.setTraitor_tester_used(false);

    }

    public void load() {
        Document document = plugin.getMongoManager().getServerCollection().find(Filters.eq("uuid", getUUID().toString())).first();
        if (document != null) {
            this.stats.setKarma(document.getInteger("Karma"));
            this.stats.setGamesPlayed(document.getInteger("GamesPlayed"));
            this.stats.setChestClicked(document.getInteger("ChestOpened"));
            this.stats.setKills(document.getInteger("Kills"));
            this.stats.setTesterUsed(document.getInteger("TesterUsed"));
            this.stats.setPassUsed(document.getInteger("PassUsed"));
            this.stats.setWins(document.getInteger("Wins"));
            this.stats.setDeaths(document.getInteger("Deaths"));
            this.stats.setPassTraitor(document.getInteger("TraitorPass"));
            this.stats.setPassDetective(document.getInteger("DetectivePass"));
            this.stats.setDetectivePassActive(document.getBoolean("detectiveActive"));
            this.stats.setTraitorPassActive(document.getBoolean("traitorActive"));
            this.stats.setFirstKillDone(document.getBoolean("firstKillDone"));
            this.stats.setFirstWinDone(document.getBoolean("firstWinDone"));
            this.stats.setFIRST_TRAITOR_WIN_Done(document.getBoolean("firstTraitorWinDone"));
            this.stats.setFIRST_DET_WIN_Done(document.getBoolean("firstDetectiveWinDone"));
            this.stats.setKILLS_1_Done(document.getBoolean("kill1Done"));
            this.stats.setKILLS_2_Done(document.getBoolean("kill2Done"));
            this.stats.setKILLS_3_Done(document.getBoolean("kill3Done"));
            this.stats.setKILLS_4_Done(document.getBoolean("kill4Done"));
            this.stats.setKILLS_5_Done(document.getBoolean("kill5Done"));
            this.stats.setWINS_1_Done(document.getBoolean("wins1Done"));
            this.stats.setWINS_2_Done(document.getBoolean("wins2Done"));
            this.stats.setWINS_3_Done(document.getBoolean("wins3Done"));
            this.stats.setWINS_4_Done(document.getBoolean("wins4Done"));
            this.stats.setWINS_5_Done(document.getBoolean("wins5Done"));
            this.stats.setKARMA_1(document.getBoolean("karma1Done"));
            this.stats.setKARMA_2(document.getBoolean("karma2Done"));
            this.stats.setKARMA_3(document.getBoolean("karma3Done"));
            this.stats.setTester_used_done(document.getBoolean("testerUsedDone"));
            this.stats.setTraitor_tester_used(document.getBoolean("traitorTesterUsedDone"));




        }
    }

    public void save() {
        Document document = new Document();
        document.put("name", getPlayerName().toLowerCase());
        document.put("realName", getPlayerName());
        document.put("uuid", getUUID().toString());
        document.put("Karma", this.stats.getKarma());
        document.put("GamesPlayed", this.stats.getGamesPlayed());
        document.put("ChestOpened", this.stats.getChestClicked());
        document.put("Kills", this.stats.getKills());
        document.put("TesterUsed", this.stats.getTesterUsed());
        document.put("PassUsed", this.stats.getPassUsed());
        document.put("Wins", this.stats.getWins());
        document.put("Deaths", this.stats.getDeaths());
        document.put("TraitorPass", this.stats.getPassTraitor());
        document.put("DetectivePass", this.stats.getPassDetective());
        document.put("detectiveActive", this.stats.isDetectivePassActive());
        document.put("traitorActive", this.stats.isTraitorPassActive());
        document.put("firstTraitorWinDone", this.stats.isFIRST_TRAITOR_WIN_Done());
        document.put("firstDetectiveWinDone", this.stats.isFIRST_DET_WIN_Done());
        document.put("firstKillDone", this.stats.isFirstKillDone());
        document.put("firstWinDone", this.stats.isFirstWinDone());
        document.put("kill1Done", this.stats.isKILLS_1_Done());
        document.put("kill2Done", this.stats.isKILLS_2_Done());
        document.put("kill3Done", this.stats.isKILLS_3_Done());
        document.put("kill4Done", this.stats.isKILLS_4_Done());
        document.put("kill5Done", this.stats.isKILLS_5_Done());
        document.put("wins1Done", this.stats.isWINS_1_Done());
        document.put("wins2Done", this.stats.isWINS_2_Done());
        document.put("wins3Done", this.stats.isWINS_3_Done());
        document.put("wins4Done", this.stats.isWINS_4_Done());
        document.put("wins5Done", this.stats.isWINS_5_Done());
        document.put("karma1Done", this.stats.isKARMA_1());
        document.put("karma2Done", this.stats.isKARMA_2());
        document.put("karma3Done", this.stats.isKARMA_3());
        document.put("traitorTesterUsedDone", this.stats.isTraitor_tester_used());
        document.put("testerUsedDone", this.stats.isTester_used_done());
        plugin.getMongoManager().getServerCollection().replaceOne(Filters.eq("uuid", getUUID().toString()), document, new UpdateOptions().upsert(true));
    }

}
