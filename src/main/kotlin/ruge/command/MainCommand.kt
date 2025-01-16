package ruge.command

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import ruge.profiles.Profiles.datauser
import ruge.profiles.Profiles.newPacks
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.subCommand

@CommandHeader(name = "ServerMotdJoin", aliases = ["smj"], permission = "ServerMotdJoin.user")
object MainCommand {

    @CommandBody(permission = "ServerMotdJoin.reset")
    val reset = subCommand {
        dynamic(optional = true, comment = "name") {
            val playername = mutableListOf<String>()
            val players = Bukkit.getOnlinePlayers()
            suggestion<CommandSender> { _, _ ->
                players.forEach { player ->
                    playername.add(player.name)
                }
                playername
            }
            execute<CommandSender> { sender, context, _ ->
                newPacks?.removeclaimed(context["name"])
            }
        }
    }

    @CommandBody(permission = "ServerMotdJoin.resetjoin")
    val resetjoin = subCommand {
        dynamic(optional = true, comment = "name") {
            val playername = mutableListOf<String>()
            val players = Bukkit.getOnlinePlayers()
            suggestion<CommandSender> { _, _ ->
                players.forEach { player ->
                    playername.add(player.name)
                }
                playername
            }
            execute<CommandSender> { sender, context, _ ->
                datauser["users.${context["name"]}"] = false
                datauser.saveToFile()
            }
        }
    }
}