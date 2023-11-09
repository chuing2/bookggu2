package com.bada.west.service

import com.bada.west.config.AuthUser
import com.bada.west.domain.Issue
import com.bada.west.domain.enums.IssueStatus
import com.bada.west.exception.NotFoundException
import com.bada.west.model.DepotSsModel
import com.bada.west.model.IssueRequest
import com.bada.west.model.IssueResponse
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactor.awaitSingle
import mu.KotlinLogging
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.await
import org.springframework.r2dbc.core.flow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.HashMap

private val logger = KotlinLogging.logger {  }

@Service
class IssueService (  private val dbclient: DatabaseClient){

    suspend fun create(authUser: AuthUser,  request: IssueRequest): IssueResponse {

        logger.debug { "authUser: {}" }

        logger.debug { authUser }

        logger.debug { "==============================" }


        logger.debug { "============================== 1" }
        val userId = 1000L
        val uuid = UUID.randomUUID()

        val param = HashMap<String, Any>().apply {
            put("id",uuid.toString())
            put("groupId",uuid.toString())
            put("levelCode","A")
            put("ssBbZ01",request.summary)
            put("ssCcZ02", request.description)
            put("ssDdZ03", userId)
            put("ssEeZ04",request.type)
            put("ssFfZ05",request.priority)
            put("ssGgZ06",request.status)
        }

        logger.debug { "============================== 2 " }
        var sql = dbclient.sql("""
                INSERT INTO fl_issue_tbl_ss 
                (
                    id,
                    group_id,
                    level_code,
                    ss_bb_z01,
                    ss_cc_z02,
                    ss_dd_z03,
                    ss_ee_z04,
                    ss_ff_z05,
                    ss_gg_z06,
                    
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
                    :ssBbZ01,
                    :ssCcZ02,
                    :ssDdZ03,
                    :ssEeZ04,
                    :ssFfZ05,
                    :ssGgZ06,
                    :ssDdZ03,
                    :ssDdZ03,
                    now(),
                    now()
                )
        """.trimIndent())

        logger.debug { "============================== 3" }

        param.forEach{ key,value -> sql = sql.bind(key,value) }

        sql.await()

        val issue = Issue(
            id = 1000L,
            summary = request.summary,
            description = request.description,
            userId = userId,
            type = request.type,
            priority = request.priority,
            status = request.status,
        )

        return IssueResponse(issue)

    }

    fun <T> T?.query(f:(T) -> String): String {
        return when {
            this == null -> ""
            this is String && this.isBlank() -> ""
            this is Collection<*> && this.isEmpty() -> ""
            this is Array<*> && this.isEmpty() -> ""
            else -> f.invoke(this)
        }
    }

    /**
     * aaa
     */
    suspend fun getAll(status: IssueStatus): List<DepotSsModel> {

        logger.debug { "start get All issue" }
        logger.debug { "=====" }
        logger.debug { status.name }
        logger.debug { "=====" }
        val param = HashMap<String, Any>().apply {
            put("status",status.name)
        }

        val statusParm = status.name;
        var sql = dbclient.sql("""
            select
                 id,
                 group_id,
                 level_code,
                 ss_aa_z00,
                 ss_bb_z01,  /* user_name */
                 ss_cc_z02,  /* password */
                 ss_dd_z03,  /* auth_provider */
                 ss_ee_z04,  /* e_mail */
                 ss_ff_z05,
                 ss_gg_z06,
                 ss_hh_z07,
                 ss_ii_z08,
                 ss_jj_z09,
                 created_by,
                 updated_by,
                 created_at,
                 updated_at
            from fl_issue_tbl_ss
            where 1=1
            ${
                statusParm.query{
                    param["status"] = it
                    "and ss_gg_z06 = :status" 
                }
            }
            """);

        param.forEach{ key,value -> sql = sql.bind(key,value) }

      //  return sql.fetch().all().collectList()

        return sql.map { row ->
            DepotSsModel(
                id = row.get("id") as String,
                groupId = row.get("group_id") as String,
                levelCode = row.get("level_code") as String,
                ssAaZ00 = row.get("ss_aa_z00") as? String,
                ssBbZ01 = row.get("ss_bb_z01") as? String,
                ssCcZ02 = row.get("ss_cc_z02") as? String,
                ssDdZ03 = row.get("ss_dd_z03") as? String,
                ssEeZ04 = row.get("ss_ee_z04") as? String,
                ssFfZ05 = row.get("ss_ff_z05") as? String,
                ssGgZ06 = row.get("ss_gg_z06") as? String,
                ssHhZ07 = row.get("ss_hh_z07") as? String,
                ssIiZ08 = row.get("ss_ii_z08") as? String,
                ssJjZ09 = row.get("ss_jj_z09") as? String,
                createdBy = row.get("created_by") as? String,
                updatedBy = row.get("updated_by") as? String,
                createdAt = row.get("created_at") as? LocalDateTime,
                updatedAt = row.get("updated_at") as? LocalDateTime,
            )
        }.flow().toList()

    }


    /**
     *
     */
    suspend fun get(id: String): DepotSsModel? {
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
                 ss_aa_z00,
                 ss_bb_z01,  /* user_name */
                 ss_cc_z02,  /* password */
                 ss_dd_z03,  /* auth_provider */
                 ss_ee_z04,  /* e_mail */
                 ss_ff_z05,
                 ss_gg_z06,
                 ss_hh_z07,
                 ss_ii_z08,
                 ss_jj_z09,
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
            DepotSsModel(
                id = row.get("id") as String,
                groupId = row.get("group_id") as String,
                levelCode = row.get("level_code") as String,
                ssAaZ00 = row.get("ss_aa_z00") as? String,
                ssBbZ01 = row.get("ss_bb_z01") as? String,
                ssCcZ02 = row.get("ss_cc_z02") as? String,
                ssDdZ03 = row.get("ss_dd_z03") as? String,
                ssEeZ04 = row.get("ss_ee_z04") as? String,
                ssFfZ05 = row.get("ss_ff_z05") as? String,
                ssGgZ06 = row.get("ss_gg_z06") as? String,
                ssHhZ07 = row.get("ss_hh_z07") as? String,
                ssIiZ08 = row.get("ss_ii_z08") as? String,
                ssJjZ09 = row.get("ss_jj_z09") as? String,
                createdBy = row.get("created_by") as? String,
                updatedBy = row.get("updated_by") as? String,
                createdAt = row.get("created_at") as? LocalDateTime,
                updatedAt = row.get("updated_at") as? LocalDateTime,
            )
        }.first().awaitSingle()
    }


    @Transactional
    suspend fun edit(userId: Long, id: String, request: IssueRequest) : IssueResponse {

        logger.debug { "============> edit" }
        logger.debug {  "id:" + id}
        logger.debug { "userId:" + userId }
        logger.debug { "request:{}" + request.toString() }
        logger.debug { "============> edit" }
        val issue: DepotSsModel = get(id)?: throw NotFoundException("이슈가 존재하지 않습니다")


        var updateSql = dbclient.sql("""
             UPDATE  fl_issue_tbl_ss  x SET 
                    ss_bb_z01 = :ssBbZ01
                  , ss_cc_z02 = :ssCcZ02
                  , ss_dd_z03 = :ssDdZ03
                  , ss_ee_z04 = :ssEeZ04
                  , ss_ff_z05 = :ssFfZ05
                  , ss_gg_z06 = :ssGgZ06
                  , updated_by = 1000
                  , updated_at = now()
             WHERE x.id = :id
        """.trimIndent())

         with(issue) {

             val param = HashMap<String, Any>().apply {
                 put("id",id)
                 put("ssBbZ01",request.summary)
                 put("ssCcZ02", request.description)
                 put("ssDdZ03", userId)
                 put("ssEeZ04",request.type)
                 put("ssFfZ05",request.priority)
                 put("ssGgZ06",request.status)
             }

             param.forEach{ key,value -> updateSql = updateSql.bind(key,value) }

             updateSql.await()
        }



        // id = id!!,
        // comments = comments.sortedByDescending(Comment::id).map(Comment::toResponse),
        // summary = summary,
        // description = description,
        // userId = userId,
        // type = type,
        // priority = priority,
        // status = status,
        // createdAt = createdAt,
        // updatedAt = updatedAt,

        return IssueResponse(Issue(id = 1000L, summary = request.summary, description = request.description, userId = userId, type = request.type, priority = request.priority, status = request.status))
    }


    suspend fun delete(id: String) {
        val param = HashMap<String, Any>().apply{
            put("id",id)
        }

        //issueRepository.deleteById(id)
        var updateSql = dbclient.sql("""
             DELETE  FROM fl_issue_tbl_ss  x 
             WHERE x.id = :id
        """.trimIndent())

        param.forEach{ key,value -> updateSql = updateSql.bind(key,value) }

        updateSql.await()
    }


}