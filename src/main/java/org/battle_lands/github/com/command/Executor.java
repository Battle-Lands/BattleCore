package org.battle_lands.github.com.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Executor {

    private final CommandSender sender;
    private final String[] args;

    public Executor(CommandSender sender, String[] args) {
        this.sender = sender;
        this.args = args;
    }

    public boolean lengthEquals(int length) {
        return args.length == length;
    }

    public CommandSender getSender() {
        return sender;
    }

    public boolean isPlayer() {
        return sender instanceof Player;
    }

    public Player getPlayer() {
        return (Player) sender;
    }
}
