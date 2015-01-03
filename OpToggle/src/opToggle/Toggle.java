package opToggle;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Toggle extends JavaPlugin implements Listener {
	Logger log;
	String ver;
	boolean hasUpdate = false;

	@Override
	public void onEnable() {
		// make logger thing
		log = Bukkit.getLogger();
		log.info("Mvv OpToggle starting");

		// register the player join event thingy
		getServer().getPluginManager().registerEvents(this, this);
		
		//Get version from plugin.yml
		PluginDescriptionFile pdf = this.getDescription();
		ver = pdf.getVersion();

		// check if there are updates
		UpdateCheck();
	}

	@Override
	public void onDisable() {
		log.info("Mvv opToggle shutting down");
	}

	// when player logs in
	@EventHandler
	public void onLogin(PlayerLoginEvent event) {
		// get player from event
		Player player = event.getPlayer();
		// check if player is op
		if (player.isOp()) {
			// check for update
			if (hasUpdate) {
				// tell OP player that there is an update
				player.sendMessage(ChatColor.RED + "OpToggle Has an update");
				player.sendMessage(ChatColor.RED + "Get it at:");
				player.sendMessage(ChatColor.GREEN
						+ "https://github.com/Michaelmvv/OpToggle");

			}
		}

	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		// See if the command is opToggle
		if (command.getName().equalsIgnoreCase("ToggleOp")) {
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
					sender.sendMessage(ChatColor.RED + "You dont get OP");
				}
			} else {
				// tell non-player that they cant use the command
				sender.sendMessage("Only players can use this command");
			}
		}
		if (command.getName().equalsIgnoreCase("OpToggleUpdateCheck")) {
			if (sender instanceof Player) {
				if (sender.hasPermission("optoggle.update")) {
					UpdateCheck();
					if (hasUpdate) {
						sender.sendMessage(ChatColor.GREEN
								+ "There is an update!");
						sender.sendMessage(ChatColor.GREEN + "Get it at: "
								+ ChatColor.RED
								+ "https://github.com/Michaelmvv/OpToggle");
					}
				}
			}
		}

		return false;
	}

	public void UpdateCheck() {
		ArrayList<String> information = new ArrayList<String>();
		try {
			URL remoteFile = new URL(
					"https://raw.githubusercontent.com/Michaelmvv/OpToggle/master/Update/update.txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					remoteFile.openStream()));
			for (int i = 0; i < 3; i++) {
				information.add(reader.readLine());
			}
		} catch (Exception e) {
			log.info("Could not get update info, lets assume that there is no update");
		}

		if (information.get(0).equalsIgnoreCase(ver)) {
			log.info("No update found");
		} else {
			log.info(ChatColor.RED + "OpToggle (Ver. " + ver
					+ " ) is not in sync with github (Ver. "
					+ information.get(0) + ")");
			log.info("Get the update at: " + ChatColor.GREEN
					+ "https://github.com/Michaelmvv/OpToggle");
			hasUpdate = true;
		}

	}

}
