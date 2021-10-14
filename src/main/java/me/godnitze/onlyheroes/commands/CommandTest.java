package me.godnitze.onlyheroes.commands;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import me.godnitze.onlyheroes.manager.Command;
import me.godnitze.onlyheroes.manager.CommandManager.Requirement;

public class CommandTest extends Command {

	public CommandTest(JavaPlugin m) {
		super(m);
		this.addAlias("Test");
		this.setDescription("Test Command");
		this.setSyntax("/test");
		this.addRequirement(Requirement.ADMIN);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) throws Exception {
		sender.sendMessage("Hello Admin Or Console");
		return false;
	}

}
