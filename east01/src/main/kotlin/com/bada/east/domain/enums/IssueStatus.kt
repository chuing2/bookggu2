package com.bada.east.domain.enums

enum class IssueStatus {

    TODO, IN_PROGRESS, RESOLVED;

    companion object {

        operator fun invoke(status: String) = valueOf(status.uppercase())

    }
}
