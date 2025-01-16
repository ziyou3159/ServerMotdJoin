package ruge.profiles

import org.bukkit.entity.Player
import ruge.packs.NewPacks
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.function.console
import taboolib.module.chat.colored
import taboolib.module.configuration.Config
import taboolib.module.configuration.ConfigFile

object Profiles {

    @Config("config.yml", autoReload = true)
    lateinit var config: ConfigFile

    @Config("data.yml", autoReload = true)
    lateinit var datauser: ConfigFile

    @Awake(LifeCycle.ACTIVE)
    fun autoReload(){
        config.onReload{
            console().sendMessage("&3配置文件 config.yml 已自动重载".colored())
        }
    }
    var newPacks: NewPacks? = null
    var playerjoin : Player? = null
    var playerquit : Player? = null
}