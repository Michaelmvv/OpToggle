package opToggle;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Toggle extends JavaPlugin {
	Logger log;

	@Override
	public void onEnable() {
		log = Bukkit.getLogger();
		log.info("Mvv OpToggle starting");
		super.onEnable();
	}

	@Override
	public void onDisable() {
		log.info("Mvv opToggle shutting down");
		super.onDisable();
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		// See if the command is opToggle
		if (command.getName().equalsIgnoreCase("opToggle")) {
			// see if a player sent it
			if (sender instanceof Player) {
				// check perms
				if (sender.hasPermission("optoggle.toggle")) {
					// check if player is Op
					if (sender.isOp()) {
						// if they are Op, de-op them
						sender.setOp(false);
						sender.sendMessage(ChatColor.GREEN
								+ "You are nolonger op");
						log.info(sender.getName() + " is nolonger Op");
					} else {
						// if player is not op, give them op
						sender.setOp(true);
						log.info(sender.getName() + " is now Op");
						sender.sendMessage(ChatColor.GREEN + "You are now op");
					}
				} else {
					// tell player they don't have perms
					sender.sendMessage(ChatColor.RED
							+ "You don't have optoggle.toggle");
				}
			} else {
				// tell non-player that they cant use the command
				sender.sendMessage("Only players can use this command");
			}
		}

		return false;
	}

}
