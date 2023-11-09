package com.bada.west.config

import com.auth0.jwt.interfaces.DecodedJWT
import com.bada.west.domain.entity.User
import com.bada.west.exception.InvalidJwtTokenException
import com.bada.west.model.AuthToken
import com.bada.west.service.CoroutineCacheManager
import com.bada.west.service.UserService
import com.bada.west.utils.JWTUtils
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.annotation.Nullable
import kotlinx.coroutines.runBlocking
import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.ModelAndViewContainer
import org.springframework.web.reactive.BindingContext
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.time.Duration

@Configuration
class WebConfig(
    private val authTokenResolver: AuthTokenResolver,

    //private val authUserHandlerArgumentResolver: AuthUserHandlerArgumentResolver,
    private val userInfoArgumentResolver: UserInfoArgumentResolver
) : WebFluxConfigurer  {


//    override fun addArgumentResolvers(argumentResolvers: MutableList<org.springframework.web.method.support.HandlerMethodArgumentResolver>) {
//        argumentResolvers.apply {
//            add(authUserHandlerArgumentResolver)
//        }
//    }

//    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
//        resolvers.add(userInfoArgumentResolver)
//    }
//

//    override  fun addArgumentResolvers(argumentResolvers: MutableList<HandlerMethodArgumentResolver>) {
//        argumentResolvers.add(userInfoArgumentResolver)
//    }




    override fun configureArgumentResolvers(configurer: ArgumentResolverConfigurer) {
        super.configureArgumentResolvers(configurer)
        configurer.addCustomResolver(authTokenResolver)
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .maxAge(3600)
    }
}

@Component
class AuthTokenResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(AuthToken::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        bindingContext: BindingContext,
        exchange: ServerWebExchange
    ): Mono<Any> {

        val authHeader = exchange.request.headers["Authorization"]?.first()
        checkNotNull(authHeader)

        val token = authHeader.split(" ")[1]
        return token.toMono()
    }

}


/////////////////////////////////////////////////////// east... ///////////////////////////////////////////////////////

//@Component
//class AuthUserHandlerArgumentResolver(
//    @Value("\${auth.url}") val authUrl: String,
//) : org.springframework.web.method.support.HandlerMethodArgumentResolver {
//
//    override fun supportsParameter(parameter: MethodParameter): Boolean =
//        AuthUser::class.java.isAssignableFrom(parameter.parameterType)
//
//    override fun resolveArgument(
//        parameter: MethodParameter,
//        mavContainer: ModelAndViewContainer?,
//        webRequest: NativeWebRequest,
//        binderFactory: WebDataBinderFactory?
//    ): Any? {
//
//        val authHeader = webRequest.getHeader("Authorization") ?: throw UnauthorizedException()
//
//        return runBlocking {
//            WebClient.create()
//                .get()
//                .uri(authUrl)
//                .header("Authorization", authHeader)
//                .retrieve()
//                .awaitBody<AuthUser>()
//        }
//    }
//
//}


interface HandlerMethodArgumentResolver {
    fun supportsParameter(parameter: MethodParameter?): Boolean

    @Nullable
    @Throws(Exception::class)
    fun resolveArgument(
        parameter: MethodParameter?, @Nullable mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest?, @Nullable binderFactory: WebDataBinderFactory?
    ): Any?
}


@Component
class UserInfoArgumentResolver(
   // val userRepository: UserRepository
      private val userService: UserService,
//    private val cacheManager: CoroutineCacheManager<User>,
//    private val jwtProperties: JWTProperties,
): HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean =
        AuthUser::class.java.isAssignableFrom(parameter.parameterType)

    override fun resolveArgument(
        parameter: MethodParameter,
        bindingContext: BindingContext,
        exchange: ServerWebExchange,
        //webRequest: NativeWebRequest?
    ): Mono<Any> {
//       val token =  exchange.request.headers["Authorization"]?.first()
//        println("token:"+token)
//        checkNotNull(token)

        val authHeader = exchange.request.headers["Authorization"]?.first()
        checkNotNull(authHeader)

        val token = authHeader.split(" ")[1]
        println("token:"+token)

        var returnValue: AuthUser? =  null
        runBlocking {
            val user =   userService.getByToken(token);
            returnValue = AuthUser(1000L,user.username,user.email,"")
        }

        //val token = webRequest.getHeader("token") as String
  //      val token =
        //      webRequest.getHeader("token") as String
 //       val user = userRepository.findByToken(token)
 //       val token = webRequest.getHeader("token") as String
//        val user = userRepository.findByToken(token)
//
//        return UserInfoResponse(
//            id = user.id,
//            name = user.name
//        )

      //  return AuthUser(1000L,"bada","chuing@daum.net","").toMono()

        return returnValue.toMono()
    }
}




data class AuthUser(
    @JsonProperty("id")
    val userId: Long,
    val username: String,
    val email: String,
    val profileUrl: String? = null,
)