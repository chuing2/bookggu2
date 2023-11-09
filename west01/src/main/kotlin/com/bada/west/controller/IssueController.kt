package com.bada.west.controller

import com.bada.west.config.AuthUser
import com.bada.west.domain.enums.IssueStatus
import com.bada.west.model.IssueRequest
import com.bada.west.service.IssueService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/issues")
class IssueController (private  val issueService: IssueService,
    ){

//    @PostMapping
//    fun create(
//        authUser: AuthUser,
//        @RequestBody request: IssueRequest,
//    ) = issueService.create(authUser.userId, request)

    @PostMapping
    suspend fun create(
        authUser: AuthUser,
        @RequestBody request: IssueRequest,
    ) = issueService.create(authUser, request)

    @GetMapping
    suspend fun getAll(
        authUser: AuthUser,
       @RequestParam(required = false, defaultValue = "TODO") status : IssueStatus,
//        @RequestParam("status") status : IssueStatus,
    ) = issueService.getAll(status)


    @GetMapping("/{id}")
    suspend fun get(
        authUser: AuthUser,
        @PathVariable id: String,
    ) = issueService.get(id)


    @PutMapping("/{id}")
    suspend fun edit(
        authUser: AuthUser,
        @PathVariable id : String,
        @RequestBody request: IssueRequest,
    ) = issueService.edit(authUser.userId, id, request)


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun delete (
        authUser: AuthUser,
        @PathVariable id: String,
    ) {
        issueService.delete(id)
    }

}