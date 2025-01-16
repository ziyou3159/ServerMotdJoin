package ruge.utility

import org.bukkit.entity.Player
import taboolib.common.platform.function.adaptCommandSender
import taboolib.module.kether.KetherShell
import taboolib.module.kether.ScriptOptions

object RunKether {

    fun runKethers(script: List<String>, player: Player) {
        KetherShell.eval(
            script, options = ScriptOptions(
                sender = adaptCommandSender(player)
            )
        )
    }
}