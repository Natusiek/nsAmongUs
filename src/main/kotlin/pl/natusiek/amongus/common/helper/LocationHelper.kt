package pl.natusiek.amongus.common.helper

import org.bukkit.Location
import kotlin.math.abs

object LocationHelper {

    fun inLocation(center: Location, toLocation: Location, distance: Int) =
            abs(center.blockX - toLocation.blockX) <= distance && abs(center.blockZ - toLocation.blockZ) <= distance

}