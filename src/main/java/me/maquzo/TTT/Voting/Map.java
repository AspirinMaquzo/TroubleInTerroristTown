package me.maquzo.TTT.Voting;

import me.maquzo.TTT.Tester.Tester;
import me.maquzo.TTT.Manager.gamestates.LobbyState.LobbyState;
import me.maquzo.TTT.TTT;
import me.maquzo.TTT.utils.ConfigLocation;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

public class Map {

    private TTT plugin;
    private String mapname;
    private String builder;
    private Location[] spawnLocations = new Location[LobbyState.MAX_PLAYERS];
    private Location spectatorLocation;
    private int votes;
    private Tester tester;

    public Map(TTT plugin, String mapname) {
        this.plugin = plugin;
        this.mapname = mapname.toUpperCase();
        this.tester = new Tester(this, plugin);

        if (exists()) {
            builder = plugin.getConfig().getString("Maps." + mapname + ".Builder");

        }

    }

    public void createMap(String builder) {
        this.builder = builder.toUpperCase();
        plugin.getConfig().set("Maps." + mapname + ".Builder", builder);
        plugin.saveConfig();
    }


    public void loadMap() {
        for (int i = 0; i < spawnLocations.length; i++)
            spawnLocations[i] = new ConfigLocation(plugin, "Maps." + mapname + "." + (i + 1)).loadLocation();

        if (tester.exists())
            tester.load();
    }

    public boolean exists() {
        return (plugin.getConfig().getString("Maps." + mapname + ".Builder") != null);
    }


    public boolean mapReady() {
        ConfigurationSection configSection = plugin.getConfig().getConfigurationSection("Maps." + mapname);
        if (!configSection.contains("Builder")) return false;
        for (int i = 1; i < LobbyState.MAX_PLAYERS + 1; i++) {
            if (!configSection.contains(Integer.toString(i))) return false;
        }
        return true;

    }


    public void setSpawnLocation(int spawnValue, Location location) {
        spawnLocations[spawnValue - 1] = location;
        new ConfigLocation(plugin, location, "Maps." + mapname + "." + spawnValue).setLocation();
    }





    public void addMapVote() {
        votes++;
    }

    public void removeMapVote() {
        votes--;
    }


    public String getMapname() {
        return mapname;
    }

    public String getBuilder() {
        return builder;
    }

    public Location[] getSpawnLocations() {
        return spawnLocations;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public Tester getTester() {
        return tester;
    }

}
