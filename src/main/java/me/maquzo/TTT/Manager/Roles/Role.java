package me.maquzo.TTT.Manager.Roles;

import org.bukkit.ChatColor;

public enum Role {

    INNOCENT("Innocent", ChatColor.GREEN),
    TRAITOR("Traitor", ChatColor.DARK_RED),
    DETECTIVE("Detective", ChatColor.AQUA);

    private Role(String name, ChatColor roleColor) {
        this.name = name;
        this.roleColor = roleColor;
    }

    private String name;
    private ChatColor roleColor;


    public ChatColor getRoleColor() {
        return roleColor;
    }

    public String getName() {
        return name;
    }
}
