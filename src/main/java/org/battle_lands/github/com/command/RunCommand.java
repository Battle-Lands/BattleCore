package org.battle_lands.github.com.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class RunCommand extends Command {

    private CommandSender sender;
    private String[] args;

    public RunCommand(String name) {
        super(name);
    }

    public abstract boolean run();

    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        this.sender = commandSender;
        this.args = strings;
        return run();
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
