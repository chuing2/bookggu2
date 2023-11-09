package com.bada.west.model

import java.time.LocalDateTime

class DepotSsModel (
    var id         : String = "",
    var groupId    : String = "",
    var levelCode  : String = "",
    var ssContent  : String? = null,
    var ssAaZ00    : String? = null,
    var ssBbZ01    : String? = null,
    var ssCcZ02    : String? = null,
    var ssDdZ03    : String? = null,
    var ssEeZ04    : String? = null,
    var ssFfZ05    : String? = null,
    var ssGgZ06    : String? = null,
    var ssHhZ07    : String? = null,
    var ssIiZ08    : String? = null,
    var ssJjZ09    : String? = null,
    var createdBy  : String? = null,
    var updatedBy  : String? = null,
    var createdAt  : LocalDateTime? = null,
    var updatedAt  : LocalDateTime? = null,

)

data class DepotSsResponse(

    var id         : String,
    var groupId    : String,
    var levelCode  : String,
    var ssContent  : String? = null,
    var ssAaZ00    : String? = null,
    var ssBbZ01    : String? = null,
    var ssCcZ02    : String? = null,
    var ssDdZ03    : String? = null,
    var ssEeZ04    : String? = null,
    var ssFfZ05    : String? = null,
    var ssGgZ06    : String? = null,
    var ssHhZ07    : String? = null,
    var ssIiZ08    : String? = null,
    var ssJjZ09    : String? = null,
    var createdBy  : String? = null,
    var updatedBy  : String? = null,
    var createdAt  : LocalDateTime? = null,
    var updatedAt  : LocalDateTime? = null,
) {

    companion object {

        operator fun invoke(depot: DepotSsModel) = with(depot) {
            DepotSsResponse(
                id = id!!,
                groupId= groupId!!,
                levelCode=levelCode!!,
                ssContent = ssContent?: "",
                ssAaZ00 = ssAaZ00?: "",
                ssBbZ01 = ssBbZ01?: "",
                ssCcZ02 = ssCcZ02?: "",
                ssDdZ03 = ssDdZ03?: "",
                ssEeZ04 = ssEeZ04?: "",
                ssFfZ05 = ssFfZ05?: "",
                ssGgZ06 = ssGgZ06?: "",
                ssHhZ07 = ssHhZ07?: "",
                ssIiZ08 = ssIiZ08?: "",
                ssJjZ09 = ssJjZ09?: "",
                createdBy = createdBy?: "1000",
                updatedBy = updatedBy?: "1000",
                createdAt = createdAt?: LocalDateTime.now(),
                updatedAt = updatedAt?: LocalDateTime.now(),
            )
        }

    }
}


class DepotMmModel (
    var id         : String = "",
    var groupId    : String = "",
    var levelCode  : String = "",
    var mmContent  : String? = null,
    var mmAaZ00    : String? = null,
    var mmBbZ01    : String? = null,
    var mmCcZ02    : String? = null,
    var mmDdZ03    : String? = null,
    var mmEeZ04    : String? = null,
    var createdBy  : String? = null,
    var updatedBy  : String? = null,
    var createdAt  : LocalDateTime? = null,
    var updatedAt  : LocalDateTime? = null,
    )

data class DepotMmResponse(

    var id         : String,
    var groupId    : String,
    var levelCode  : String,
    var mmContent  : String? = null,
    var mmAaZ00    : String? = null,
    var mmBbZ01    : String? = null,
    var mmCcZ02    : String? = null,
    var mmDdZ03    : String? = null,
    var mmEeZ04    : String? = null,
    var createdBy  : String? = null,
    var updatedBy  : String? = null,
    var createdAt  : LocalDateTime? = null,
    var updatedAt  : LocalDateTime? = null,
) {

    companion object {

        operator fun invoke(depot: DepotMmModel) = with(depot) {
            DepotMmResponse(
                id        = id!!,
                groupId   = groupId!!,
                levelCode = levelCode!!,
                mmContent = mmContent?: "",
                mmAaZ00   = mmAaZ00?: "",
                mmBbZ01   = mmBbZ01?: "",
                mmCcZ02   = mmCcZ02?: "",
                mmDdZ03   = mmDdZ03?: "",
                mmEeZ04   = mmEeZ04?: "",
                createdBy = createdBy?: "1000",
                updatedBy = updatedBy?: "1000",
                createdAt = createdAt?: LocalDateTime.now(),
                updatedAt = updatedAt?: LocalDateTime.now(),
            )
        }
    }
}

class DepotXlModel (
    var id         : String = "",
    var groupId    : String = "",
    var levelCode  : String = "",
    var xlContent  : String? = null,
    var xlAaZ00    : String? = null,
    var xlBbZ01    : String? = null,
    var xlCcZ02    : String? = null,
    var xlDdZ03    : String? = null,
    var xlEeZ04    : String? = null,
    var createdBy  : String? = null,
    var updatedBy  : String? = null,
    var createdAt  : LocalDateTime? = null,
    var updatedAt  : LocalDateTime? = null,
)

data class DepotXlResponse(

    var id         : String,
    var groupId    : String,
    var levelCode  : String,
    var xlContent  : String? = null,
    var xlAaZ00    : String? = null,
    var xlBbZ01    : String? = null,
    var xlCcZ02    : String? = null,
    var xlDdZ03    : String? = null,
    var xlEeZ04    : String? = null,
    var createdBy  : String? = null,
    var updatedBy  : String? = null,
    var createdAt  : LocalDateTime? = null,
    var updatedAt  : LocalDateTime? = null,
) {

    companion object {

        operator fun invoke(depot: DepotXlModel) = with(depot) {
            DepotXlResponse(
                id        = id!!,
                groupId   = groupId!!,
                levelCode = levelCode!!,
                xlContent = xlContent?: "",
                xlAaZ00   = xlAaZ00?: "",
                xlBbZ01   = xlBbZ01?: "",
                xlCcZ02   = xlCcZ02?: "",
                xlDdZ03   = xlDdZ03?: "",
                xlEeZ04   = xlEeZ04?: "",
                createdBy = createdBy?: "1000",
                updatedBy = updatedBy?: "1000",
                createdAt = createdAt?: LocalDateTime.now(),
                updatedAt = updatedAt?: LocalDateTime.now(),
            )
        }
    }
}

class DepotXxModel (
    var id         : String = "",
    var groupId    : String = "",
    var levelCode  : String = "",
    var xxContent  : String? = null,
    var xxAaZ00    : String? = null,
    var xxBbZ01    : String? = null,
    var xxCcZ02    : String? = null,
    var xxDdZ03    : String? = null,
    var xxEeZ04    : String? = null,
    var createdBy  : String? = null,
    var updatedBy  : String? = null,
    var createdAt  : LocalDateTime? = null,
    var updatedAt  : LocalDateTime? = null,
)

data class DepotXxResponse(

    var id         : String,
    var groupId    : String,
    var levelCode  : String,
    var xxContent  : String? = null,
    var xxAaZ00    : String? = null,
    var xxBbZ01    : String? = null,
    var xxCcZ02    : String? = null,
    var xxDdZ03    : String? = null,
    var xxEeZ04    : String? = null,
    var createdBy  : String? = null,
    var updatedBy  : String? = null,
    var createdAt  : LocalDateTime? = null,
    var updatedAt  : LocalDateTime? = null,
) {

    companion object {

        operator fun invoke(depot: DepotXxModel) = with(depot) {
            DepotXxResponse(
                id        = id!!,
                groupId   = groupId!!,
                levelCode = levelCode!!,
                xxContent = xxContent?: "",
                xxAaZ00   = xxAaZ00?: "",
                xxBbZ01   = xxBbZ01?: "",
                xxCcZ02   = xxCcZ02?: "",
                xxDdZ03   = xxDdZ03?: "",
                xxEeZ04   = xxEeZ04?: "",
                createdBy = createdBy?: "1000",
                updatedBy = updatedBy?: "1000",
                createdAt = createdAt?: LocalDateTime.now(),
                updatedAt = updatedAt?: LocalDateTime.now(),
            )
        }
    }
}


