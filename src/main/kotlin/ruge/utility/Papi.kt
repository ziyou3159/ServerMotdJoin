package ruge.utility

import org.bukkit.entity.Player
import ruge.profiles.Profiles.datauser
import ruge.profiles.Profiles.playerjoin
import ruge.profiles.Profiles.playerquit
import taboolib.platform.compat.PlaceholderExpansion

object Papi: PlaceholderExpansion {
    override val identifier: String
        get() = "smj"


    override fun onPlaceholderRequest(player: Player?, args: String): String {
        if (args.equals("join", ignoreCase = true)){
            return playerjoin?.name ?: ""
        }
        else if (args.equals("quit", ignoreCase = true)){
            return playerquit?.name ?: ""
        }
        else if (args.equals("welcomes", ignoreCase = true)){
            return datauser.getString("welcomes.${player?.name}")?:"0"
        }
        return "null"
    }
}