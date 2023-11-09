package com.bada.west.service

import com.auth0.jwt.interfaces.DecodedJWT
import com.bada.west.config.JWTProperties
import com.bada.west.domain.entity.User
import com.bada.west.domain.repository.FlUserRepository
import com.bada.west.domain.repository.UserRepository
import com.bada.west.exception.InvalidJwtTokenException
import com.bada.west.exception.UserExistsException
import com.bada.west.exception.UserNotFoundException
import com.bada.west.model.DepotSsModel
import com.bada.west.model.SignInRequest
import com.bada.west.model.SignInResponse
import com.bada.west.model.SignUpRequest
import com.bada.west.utils.BCryptUtils
import com.bada.west.utils.JWTClaim
import com.bada.west.utils.JWTUtils
import kotlinx.coroutines.flow.toList
import mu.KotlinLogging
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.await
import org.springframework.r2dbc.core.flow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.HashMap


private val logger = KotlinLogging.logger {  }

@Service
class UserService(
    private val userRepository: UserRepository,
    private val flUserRepository: FlUserRepository,

    private val jwtProperties: JWTProperties,
    private val cacheManager: CoroutineCacheManager<User>,


    private val dbclient: DatabaseClient

) {

    companion object {
        /* 60분 */
        private val CACHE_TTL = Duration.ofMinutes(60)
    }

    @Transactional
    suspend fun signUp(signUpRequest: SignUpRequest) {
        logger.debug { "1111111111111111111111" }
        with(signUpRequest) {
            userRepository.findByEmail(email)?.let {
                throw UserExistsException()
            }
            val user = User(
                email = email,
                password = BCryptUtils.hash(password),
                username = username,
                team = team,
            )
            userRepository.save(user)
        }


        logger.debug { "1111111111111111111111222222222222222" }

        val uuid = UUID.randomUUID()

        val param = HashMap<String, Any>().apply {
            put("id",uuid.toString())
            put("groupId",uuid.toString())
            put("levelCode","A")
            put("ssBbZ01",signUpRequest.username)
            put("ssCcZ02",BCryptUtils.hash(signUpRequest.password))
            put("ssDdZ03","DIRECT")
            put("ssEeZ04",signUpRequest.email)
        }

        var sql = dbclient.sql("""
                INSERT INTO fl_user_tbl_ss 
                (
                    id,
                    group_id,
                    level_code,
                    ss_bb_z01,
                    ss_cc_z02,
                    ss_dd_z03,
                    ss_ee_z04,
                    created_by,
                    updated_by
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
                    now(),
                    now()
                )
        """.trimIndent())

        param.forEach{ key,value -> sql = sql.bind(key,value) }

        sql.await()

        logger.debug { "11111111111111111111112222222222222223333333333333333333" }

    }

    suspend fun signIn2(signInRequest: SignInRequest) : SignInResponse {

        val param = HashMap<String, Any>().apply {
            put("ssEeZ04",signInRequest.email)
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
            from fl_user_tbl_ss
            where 1=1
              and ss_ee_z04 = :ssEeZ04
            """);

        param.forEach{ key,value -> sql = sql.bind(key,value) }

        val sqlRst = sql.map { row ->
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


        logger.debug { "==============================" }
        logger.debug { sqlRst.toString() }
        logger.debug { "==============================" }

        val jwtClaim = JWTClaim(
            userId = 1000L,
            email  = sqlRst[0].ssEeZ04?:"",
            profileUrl = sqlRst[0].ssFfZ05?:"",
            username = sqlRst[0].ssBbZ01?:""
        )

        val token = JWTUtils.createToken(jwtClaim, jwtProperties)

        logger.debug { "token ==============================" }
        logger.debug { token }
        logger.debug { "token ==============================" }

        val user = User(id=1000L, email = sqlRst[0].ssEeZ04?:"", username = sqlRst[0].ssBbZ01?:"" , password = sqlRst[0].ssCcZ02?:"" );


        logger.debug { "user ====================================" }
        logger.debug { user.toString() }
        logger.debug { "user ====================================" }

        cacheManager.awaitPut(key = token, value = user, ttl = CACHE_TTL)


        val rsp =  SignInResponse(
                    email = sqlRst[0].ssEeZ04?:"",
                    username = sqlRst[0].ssBbZ01?:"" ,
                    token = token
                );

        logger.debug { "rsp ==========================" }
        logger.debug { rsp }
        logger.debug { "rsp ==========================" }

        return rsp

    }

    suspend fun signIn(signInRequest: SignInRequest): SignInResponse {


        logger.debug { "3434 5566 11" }
        return signIn2(signInRequest)
        //logger.debug { "3434 5566 22" }

//        return with(userRepository.findByEmail(signInRequest.email) ?: throw UserNotFoundException()) {
//            val verified = BCryptUtils.verify(signInRequest.password, password)
//            if (!verified) {
//                throw PasswordNotMatchedException()
//            }
//
//            val jwtClaim = JWTClaim(
//                userId = id!!,
//                email = email,
//                profileUrl = profileUrl,
//                username = username
//            )
//
//            val token = JWTUtils.createToken(jwtClaim, jwtProperties)
//
//            cacheManager.awaitPut(key = token, value = this, ttl = CACHE_TTL)
//
//            SignInResponse(
//                email = email,
//                username = username,
//                token = token
//            )
//        }

    }



    suspend fun logout(token: String) {
        cacheManager.awaitEvict(token)
    }

    suspend fun getByToken(token: String): User {
        val cachedUser = cacheManager.awaitGetOrPut(key = token, ttl = CACHE_TTL) {
            // 캐시가 유효하지 않은 경우 동작
            val decodedJWT: DecodedJWT = JWTUtils.decode(token, jwtProperties.secret, jwtProperties.issuer)

            val userId: Long = decodedJWT.claims["userId"]?.asLong() ?: throw InvalidJwtTokenException()
            get(userId)
        }
        return cachedUser
    }

    suspend fun get(userId: Long): User {
        return userRepository.findById(userId) ?: throw UserNotFoundException()
    }

    suspend fun edit(token: String, username: String, profileUrl: String?): User {
        val user = getByToken(token)
        val newUser = user.copy(username = username, profileUrl = profileUrl ?: user.profileUrl)

        return userRepository.save(newUser).also {
            cacheManager.awaitPut(key = token, value = it, ttl = CACHE_TTL)
        }
    }


    suspend fun getAll(): List<DepotSsModel> {


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
            from fl_user_tbl_ss
            where 1=1
            """);

       // param.forEach{ key,value -> sql = sql.bind(key,value) }

        //var xx = DepotModel(id="1111",groupId="2222",levelCode="A",ssAaZ00="3333",ssBbZ01="4444",ssCcZ02="5555",ssDdZ03="6666",ssEeZ04="7777",ssFfZ05="8888",ssGgZ06="9999",ssHhZ07="0000",ssIiZ08="1111",ssJjZ09="2222",createdBy="3333",updatedBy="4444",createdAt=LocalDateTime.now(),updatedAt=LocalDateTime.now()

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
}