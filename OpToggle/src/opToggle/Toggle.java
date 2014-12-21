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

		if (command.getName().equalsIgnoreCase("opToggle")) {
			if (sender instanceof Player) {
				if (sender.hasPermission("optoggle.toggle")) {
					if (sender.isOp()) {
						sender.setOp(false);
						sender.sendMessage(ChatColor.GREEN
								+ "You are nolonger op");
						log.info(sender.getName() + " is nolonger Op");
					} else {
						sender.setOp(true);
						log.info(sender.getName() + " is now Op");
						sender.sendMessage(ChatColor.GREEN + "You are now op");
					}
				} else {
					sender.sendMessage(ChatColor.RED + "You don't have optoggle.toggle");
				}
			} else {
				sender.sendMessage("Only players can use this command");
			}
		}

		return false;
	}

}
