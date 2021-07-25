package me.maquzo.TTT.utils;

import lombok.Getter;
import lombok.Setter;
import me.maquzo.TTT.TTT;

import java.util.UUID;

@Getter
@Setter
public class Profile {

    private TTT plugin = TTT.getPlugin();

    private PlayerData data;
    private java.util.UUID UUID;
    private String playerName;


    public Profile(UUID uuid, String name) {
        this.UUID = uuid;
        this.playerName = name;
        this.data = new PlayerData(uuid, name);
    }

}
