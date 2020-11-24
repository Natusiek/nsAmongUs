package pl.natusiek.amongus.game

import pl.natusiek.amongus.common.builder.LocationBuilder

object LocationAPI {

    fun centerLocation(id: Int) = LocationBuilder("world", 0.0, 80.0, 0.0).toBukkitLocation()


}