package ruge.event

import me.arasple.mc.trchat.api.event.TrChatEvent
import org.bukkit.Bukkit
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.server.ServerListPingEvent
import ruge.packs.NewPacks
import ruge.profiles.Profiles.config
import ruge.profiles.Profiles.datauser
import ruge.profiles.Profiles.newPacks
import ruge.profiles.Profiles.playerjoin
import ruge.profiles.Profiles.playerquit
import ruge.utility.RunKether.runKethers
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.adaptPlayer
import taboolib.common.platform.function.info
import taboolib.module.chat.colored
import taboolib.module.chat.component
import taboolib.platform.compat.replacePlaceholder
import java.time.Instant

object Event {

    @SubscribeEvent
    fun serverList(event: ServerListPingEvent){
        listOf("MaxPlayers","ServerMOTD").forEach { str ->
            val select = config.getBoolean("motd.${str}.Enabled")
            if (select) {
                if (str == "MaxPlayers"){
                    event.maxPlayers = config.getInt("motd.${str}.MaxPlayers")
                }else{
                    val index = config.getConfigurationSection("motd.${str}.motds")?.getKeys(false)?.random()
                    val motds = config.getStringList("motd.${str}.motds.${index}").colored()
                    event.motd = motds[0]+"\n"+ motds[1]
                }
            }
        }
    }
    @SubscribeEvent
    fun playerJoin(event: PlayerJoinEvent){
        val player = event.player
        if (config.getBoolean("playerJoin.Enabled")) {
            event.joinMessage = null
            playerjoin = player
            val joinaction = config.getStringList("playerJoin.actions")
            Bukkit.getOnlinePlayers().forEach { y ->
                runKethers(joinaction,y)
            }
        }
        val state = datauser.getBoolean("users.${player.name}")
        if (state) {
            return
        }
        if (!config.getBoolean("NotNewJoinPlayers.Enabled")){
            return
        }

        datauser["users.${player.name}"] = true
        datauser.saveToFile()

        val actions = config.getStringList("NotNewJoinPlayers.actions")
        Bukkit.getOnlinePlayers().forEach { p ->
            if (p != player) {
                config.getStringList("NotNewJoinPlayers.messages").forEach{ message ->
                    message.replacePlaceholder(player).component().buildColored().sendTo(adaptPlayer(p))
                }
                runKethers(actions,p)
            }
        }

        newPacks = object :NewPacks(){
            override var player = player
            override var createtime = Instant.now().epochSecond
        }
    }
    @SubscribeEvent
    fun trchatplayer(event: TrChatEvent){
        if (!config.getBoolean("ChatMention.Enabled")){
            return
        }
        if (newPacks == null){
            return
        }
        val duration = config.getInt("ChatMention.duration")
        if (duration != -1){
            val time = Instant.now().epochSecond
            if (time - newPacks!!.createtime > config.getInt("ChatMention.duration")) {
                return
            }
        }
        val message = event.message
        val newplay = newPacks?.player
        val chatplay = event.player
        if (newplay != null) {
            if ( newplay != chatplay && message.contains("@${newplay.name}") && !newPacks!!.getclaimed(chatplay.name)) {

                val actions = config.getStringList("ChatMention.actions")
                runKethers(actions,chatplay)
                newPacks?.setclaimed(chatplay.name, true)
                val count = datauser.getInt("welcomes.${chatplay.name}")
                datauser["welcomes.${chatplay.name}"] = count + 1
            }
        }
    }
    @SubscribeEvent
    fun onPlayerQuit(event: PlayerQuitEvent){
        if (config.getBoolean("onPlayerQuit.Enabled")){
            event.quitMessage = null
            playerquit = event.player
            val onplayerquit = config.getStringList("onPlayerQuit.actions")
            Bukkit.getOnlinePlayers().forEach { y ->
                runKethers(onplayerquit,y)
            }
        }
    }
}