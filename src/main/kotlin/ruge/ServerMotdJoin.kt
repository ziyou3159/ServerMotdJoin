package ruge

import taboolib.common.platform.Plugin
import taboolib.common.platform.function.console
import taboolib.module.chat.colored

object ServerMotdJoin : Plugin() {

    override fun onEnable() {
        console().sendMessage("&7[&aServerMotdJoin&7] &3插件已成功启动!".colored())
    }
}