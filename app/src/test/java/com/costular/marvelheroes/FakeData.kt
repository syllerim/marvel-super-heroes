package com.costular.marvelheroes

import com.costular.marvelheroes.data.model.MarvelHeroEntity

object FakeData {
    val IRON_MAN = MarvelHeroEntity(1,"Iron Man", "",  "Anthony Edward \"Tony\" Stark", "1.85m", "Half man half robot", "Tony has a genius level intellect that allows him to invent a wide range of sophisticated devices, specializing in advanced weapons and armor. He possesses a keen business mind.", arrayOf(), false, 1)
    val ARROW = MarvelHeroEntity(2, "Flecha Verde", "", "Oliver Jonas Queen", "1.87m", "Oliver está considerado uno de los mejores arqueros del mundo.", "", arrayOf(), true, 2)
}
