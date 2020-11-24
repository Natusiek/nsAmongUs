package pl.natusiek.amongus.common.extension

import org.bukkit.ChatColor

fun String.fixColor(): String = ChatColor.translateAlternateColorCodes('&', this)