package com.bada.east.domain.enums

enum class IssuePriority {

    LOW, MEDIUM, HIGH;

    companion object {

        operator fun invoke(priority: String) = valueOf(priority.uppercase())
    }

}
