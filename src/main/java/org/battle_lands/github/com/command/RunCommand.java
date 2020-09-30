package org.battle_lands.github.com.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class RunCommand extends Command {

    public RunCommand(String name) {
        super(name);
    }

    public abstract boolean run(Executor executor);

    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        return false;
    }
}
