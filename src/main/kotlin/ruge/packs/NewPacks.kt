package ruge.packs


import org.bukkit.entity.Player
import java.util.concurrent.ConcurrentHashMap
abstract class NewPacks {

    abstract var player: Player
    abstract var createtime: Long
    private var claimedPlayers: ConcurrentHashMap<String, Boolean> = ConcurrentHashMap()

    fun getplayer(): Player {
        return this.player
    }

    fun getclaimedPlayers(): ConcurrentHashMap<String, Boolean> {
        return this.claimedPlayers
    }

    fun getclaimed(name:String): Boolean {
        return this.claimedPlayers[name] ?: false
    }

    fun setclaimed(name: String, value: Boolean) {
        this.claimedPlayers[name] = value
    }

    fun removeclaimed(name: String) {
        this.claimedPlayers.remove(name)
    }
}