package com.bada.west.service

import com.bada.west.config.AuthUser
import com.bada.west.exception.NotFoundException
import com.bada.west.model.CommentRequest
import com.bada.west.model.CommentResponse
import com.bada.west.model.DepotMmModel
import com.bada.west.model.DepotSsModel
import kotlinx.coroutines.reactor.awaitSingle
import mu.KotlinLogging
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.await
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.HashMap

private val logger = KotlinLogging.logger {  }


@Service
class CommentService (private val dbclient: DatabaseClient){

    /**
     *
     */
    suspend fun get(id: String): DepotMmModel? {
        logger.debug { "start get All issue" }
        logger.debug { "=====" }
        logger.debug { id }
        logger.debug { "=====" }
        val param = HashMap<String, Any>().apply{
            put("id",id)
        }

        var sql = dbclient.sql("""
            select
                 id,
                 group_id,
                 level_code,
                 mm_content,
                 mm_aa_z00,  /* user_name */
                 mm_bb_z01,  /* password */
                 mm_cc_z02,  /* auth_provider */
                 mm_dd_z03,  /* e_mail */
                 mm_ee_z04,  /* e_mail */
                 created_by,
                 updated_by,
                 created_at,
                 updated_at
            from fl_issue_tbl_ss
            where 1=1
              and id = :id
            """);
        param.forEach{ key,value -> sql = sql.bind(key,value) }
        //  return sql.fetch().all().collectList()
        return sql.map { row ->
            DepotMmModel(
                id = row.get("id") as String,
                groupId = row.get("group_id") as String,
                levelCode = row.get("level_code") as String,
                mmContent = row.get("mm_content") as String,
                mmAaZ00 = row.get("mm_aa_z00") as? String,
                mmBbZ01 = row.get("mm_bb_z01") as? String,
                mmCcZ02 = row.get("mm_cc_z02") as? String,
                mmDdZ03 = row.get("mm_dd_z03") as? String,
                mmEeZ04 = row.get("mm_ee_z04") as? String,
                createdBy = row.get("created_by") as? String,
                updatedBy = row.get("updated_by") as? String,
                createdAt = row.get("created_at") as? LocalDateTime,
                updatedAt = row.get("updated_at") as? LocalDateTime,
            )
        }.first().awaitSingle()
    }

    //authUser, issueId, request
    @Transactional
    suspend fun create(authUser: AuthUser, groupId: String, request: CommentRequest) : CommentResponse {

        logger.debug { "authUser: {}" }

        logger.debug { authUser }

        logger.debug { "==============================" }


        logger.debug { "============================== 1" }
        val userId = 1000L
        val uuid = UUID.randomUUID()

        val param = HashMap<String, Any>().apply {
            put("id",uuid.toString())
            put("groupId",groupId)
            put("levelCode","B")
            put("mmContent",request.body)
            put("userId",userId)
        }


        logger.debug { "============================== 2 " }
        var sql = dbclient.sql("""
                INSERT INTO fl_issue_comment_tbl_mm 
                (
                    id,
                    group_id,
                    level_code,
                    mm_content,
                    created_by,
                    updated_by,
                    created_at,
                    updated_at
                )
                VALUES
                (
                    :id,
                    :groupId,
                    :levelCode,
                    :mmContent,
                    :userId,
                    :userId,
                    now(),
                    now()
                )
        """.trimIndent())

        logger.debug { "============================== 3" }

        param.forEach{ key,value -> sql = sql.bind(key,value) }

        sql.await()

        logger.debug { "============================== 4" }

        val comment = CommentResponse(
            id = uuid.toString(),
            groupId = groupId,
            userId = userId.toString(),
            body = request.body,
            username = authUser.username?:"",
        )

        return comment
    }




    @Transactional
    suspend fun edit(authUser: AuthUser , id: String, request: CommentRequest): CommentResponse? {

        logger.debug { "============> edit" }

        logger.debug {  "id:" + id}
        logger.debug { "userId:" + authUser.username }
        logger.debug { "request:{}" + request.toString() }
        logger.debug { "============> edit" }
        val comment: DepotMmModel = get(id)?: throw NotFoundException("댓글이 존재하지 않습니다")

        var updateSql = dbclient.sql("""
             UPDATE  fl_issue_comment_tbl_mm  x SET 
                    mm_content = :mmContent
                  , updated_by = 1000
                  , updated_at = now()
             WHERE x.id = :id
        """.trimIndent())

        with(comment) {

            val param = HashMap<String, Any>().apply {
                put("id",id)
                put("mmContent",request.body)
            }

            param.forEach{ key,value -> updateSql = updateSql.bind(key,value) }

            updateSql.await()
        }

        return CommentResponse(
            id = id,
            groupId = comment.groupId,
            userId = authUser.username?:"",
            body = request.body,
            username = authUser.username?:"",
        )
    }
}