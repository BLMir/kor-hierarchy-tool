package dev.biel.application.common

fun Map<String, String>.getRoot(): MutableList<String> {
    val rootCount = mutableListOf<String>()

    this.map { rowElement ->
        if (!this.containsKey(rowElement.value)) {
            rootCount.add(rowElement.value)
        }
    }

    return rootCount
}