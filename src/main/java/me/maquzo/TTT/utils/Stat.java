package me.maquzo.TTT.utils;

public class Stat {

    private int karma;
    private int kills;
    private int wins;
    private int testerUsed;
    private int gamesPlayed;
    private int passUsed;
    private int chestClicked;
    private int deaths;
    private int passTraitor;
    private int passDetective;

    private boolean traitorPassActive;
    private boolean detectivePassActive;


    private boolean FirstKillDone;
    private boolean FirstWinDone;
    private boolean FIRST_DET_WIN_Done;
    private boolean FIRST_TRAITOR_WIN_Done;
    private boolean KILLS_1_Done;
    private boolean KILLS_2_Done;
    private boolean KILLS_3_Done;
    private boolean KILLS_4_Done;
    private boolean KILLS_5_Done;
    private boolean WINS_1_Done;
    private boolean WINS_2_Done;
    private boolean WINS_3_Done;
    private boolean WINS_4_Done;
    private boolean WINS_5_Done;
    private boolean tester_used_done;
    private boolean traitor_tester_used;
    private boolean KARMA_1;
    private boolean KARMA_2;
    private boolean KARMA_3;


    //Karma
    public void increaseKarma(int karma) {
        this.karma = this.karma + karma;
    }

    public void decreseKarma(int karma) {
        this.karma = this.karma - karma;
    }

    public int getKarma() {
        return this.karma;
    }

    public void setKarma(int karma) {
        this.karma = karma;
    }


    //Challenges
    public void increaseKills(int kills) {
        this.kills = this.kills + kills;
    }

    public int getKills() {
        return this.kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void increaseWins(int wins) {
        this.wins = this.wins + wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getWins() {
        return this.wins;
    }

    public void increaseTesterUsed(int testerUsed) {
        this.testerUsed = this.testerUsed + testerUsed;
    }

    public void setTesterUsed(int testerUsed) {
        this.testerUsed = testerUsed;
    }

    public int getTesterUsed() {
        return this.testerUsed;
    }

    public void increaseGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = this.gamesPlayed + gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getGamesPlayed() {
        return this.gamesPlayed;
    }

    public void increasePassUsed(int passUsed) {
        this.passUsed = this.passUsed + passUsed;
    }

    public void setPassUsed(int passUsed) {
        this.passUsed = passUsed;
    }

    public int getPassUsed() {
        return this.passUsed;
    }

    public void increaseTraitorPass(int passTraitor) {
        this.passTraitor = this.passTraitor + passTraitor;
    }

    public void decreaseTraitorPass(int passTraitor) {
        this.passTraitor = this.passTraitor - passTraitor;
    }

    public void setPassTraitor(int passTraitor) {
        this.passTraitor = passTraitor;
    }

    public int getPassTraitor() {
        return this.passTraitor;
    }

    public void increaseDetectivePass(int passDetective) {
        this.passDetective = this.passDetective + passDetective;
    }

    public void decreaseDetectivePass(int passDetective) {
        this.passDetective = this.passDetective - passDetective;
    }

    public void setPassDetective(int passDetective) {
        this.passDetective = passDetective;
    }

    public int getPassDetective() {
        return this.passDetective;
    }

    public void increaseChestClicked(int chestClicked) {
        this.chestClicked = this.chestClicked + chestClicked;
    }

    public void setChestClicked(int chestClicked) {
        this.chestClicked = chestClicked;
    }

    public int getChestClicked() {
        return this.getGamesPlayed();
    }

    public void increaseDeaths(int deaths) {
        this.deaths = this.deaths + deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getDeaths() {
        return this.deaths;
    }


    public boolean isTraitorPassActive() {
        return traitorPassActive;
    }

    public boolean isDetectivePassActive() {
        return detectivePassActive;
    }

    public void setTraitorPassActive(boolean traitorPassActive) {
        this.traitorPassActive = traitorPassActive;
    }

    public void setDetectivePassActive(boolean detectivePassActive) {
        this.detectivePassActive = detectivePassActive;
    }

    public boolean isFIRST_DET_WIN_Done() {
        return FIRST_DET_WIN_Done;
    }

    public boolean isFIRST_TRAITOR_WIN_Done() {
        return FIRST_TRAITOR_WIN_Done;
    }

    public boolean isFirstKillDone() {
        return FirstKillDone;
    }

    public boolean isFirstWinDone() {
        return FirstWinDone;
    }

    public boolean isKILLS_1_Done() {
        return KILLS_1_Done;
    }

    public boolean isKILLS_2_Done() {
        return KILLS_2_Done;
    }

    public boolean isKILLS_3_Done() {
        return KILLS_3_Done;
    }

    public boolean isKILLS_4_Done() {
        return KILLS_4_Done;
    }

    public boolean isKILLS_5_Done() {
        return KILLS_5_Done;
    }

    public boolean isWINS_1_Done() {
        return WINS_1_Done;
    }

    public boolean isWINS_2_Done() {
        return WINS_2_Done;
    }

    public boolean isWINS_3_Done() {
        return WINS_3_Done;
    }

    public boolean isWINS_4_Done() {
        return WINS_4_Done;
    }

    public boolean isWINS_5_Done() {
        return WINS_5_Done;
    }

    public void setFIRST_DET_WIN_Done(boolean FIRST_DET_WIN_Done) {
        this.FIRST_DET_WIN_Done = FIRST_DET_WIN_Done;
    }

    public void setFIRST_TRAITOR_WIN_Done(boolean FIRST_TRAITOR_WIN_Done) {
        this.FIRST_TRAITOR_WIN_Done = FIRST_TRAITOR_WIN_Done;
    }

    public void setFirstKillDone(boolean firstKillDone) {
        FirstKillDone = firstKillDone;
    }

    public void setFirstWinDone(boolean firstWinDone) {
        FirstWinDone = firstWinDone;
    }

    public void setKILLS_1_Done(boolean KILLS_1_Done) {
        this.KILLS_1_Done = KILLS_1_Done;
    }

    public void setKILLS_2_Done(boolean KILLS_2_Done) {
        this.KILLS_2_Done = KILLS_2_Done;
    }

    public void setKILLS_3_Done(boolean KILLS_3_Done) {
        this.KILLS_3_Done = KILLS_3_Done;
    }

    public void setKILLS_4_Done(boolean KILLS_4_Done) {
        this.KILLS_4_Done = KILLS_4_Done;
    }

    public void setKILLS_5_Done(boolean KILLS_5_Done) {
        this.KILLS_5_Done = KILLS_5_Done;
    }

    public void setWINS_1_Done(boolean WINS_1_Done) {
        this.WINS_1_Done = WINS_1_Done;
    }

    public void setWINS_2_Done(boolean WINS_2_Done) {
        this.WINS_2_Done = WINS_2_Done;
    }

    public void setWINS_3_Done(boolean WINS_3_Done) {
        this.WINS_3_Done = WINS_3_Done;
    }

    public void setWINS_4_Done(boolean WINS_4_Done) {
        this.WINS_4_Done = WINS_4_Done;
    }

    public void setWINS_5_Done(boolean WINS_5_Done) {
        this.WINS_5_Done = WINS_5_Done;
    }

    public void setKARMA_1(boolean KARMA_1) {
        this.KARMA_1 = KARMA_1;
    }

    public void setKARMA_2(boolean KARMA_2) {
        this.KARMA_2 = KARMA_2;
    }

    public void setKARMA_3(boolean KARMA_3) {
        this.KARMA_3 = KARMA_3;
    }

    public void setTester_used_done(boolean tester_used_done) {
        this.tester_used_done = tester_used_done;
    }

    public void setTraitor_tester_used(boolean traitor_tester_used) {
        this.traitor_tester_used = traitor_tester_used;
    }

    public boolean isKARMA_1() {
        return KARMA_1;
    }

    public boolean isKARMA_2() {
        return KARMA_2;
    }

    public boolean isKARMA_3() {
        return KARMA_3;
    }

    public boolean isTester_used_done() {
        return tester_used_done;
    }

    public boolean isTraitor_tester_used() {
        return traitor_tester_used;
    }
}
