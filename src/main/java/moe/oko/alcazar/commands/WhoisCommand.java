package moe.oko.alcazar.commands;

import com.bobacadodl.imgmessage.ImageChar;
import com.bobacadodl.imgmessage.ImageMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.imageio.ImageIO;
import java.net.URL;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static moe.oko.alcazar.Alcazar.instance;

public class WhoisCommand implements CommandExecutor {

    private static void lookup (Player player, CommandSender sender) {

        // Fetch player head from a generic api
        BufferedImage head = null;
        try {
            URL playerHead = new URL("https://crafthead.net/helm/" + player.getUniqueId() + "/8.png");
            head = ImageIO.read(playerHead);
        } catch (IOException e) {
            try {
                head = ImageIO.read(instance.getResource("missing.png"));
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }

        // Converts time played from ticks into hours
        final int timePlayed = player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20 / 60 / 60;

        String address = "";
        if (sender.hasPermission("alcazar.admin")) {
            address = String.valueOf(player.getAddress());
        } else {
            // TODO: Eventually set this to Fusee/Namelayer default group tag
            address = "@" + Bukkit.getName();
        }

        final String user = PlainTextComponentSerializer.plainText().serialize(player.displayName()) + " is " + player.getName() + "@" + ChatColor.GREEN + address.substring(1);
        final String stats = "Time played: " + ChatColor.GREEN + timePlayed + "h";

        new ImageMessage(head, 8, ImageChar.BLOCK.getChar()).appendText("", user, stats).send((Player) sender);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

    if (!(sender instanceof Player)) {
        sender.sendMessage("You must be a player to run this command!");
        return true;
    }

    if (args.length < 1) {
        lookup((Player) sender, sender);
        return true;
    }

    // Fetch player
    Player target = Bukkit.getPlayer(args[0]); // TODO: allow use of cached player in whois
    if (target == null) {
        sender.sendMessage(ChatColor.RED + "No player was found");
        return true;
    }
        lookup(target, sender);
        return true;
    }
}