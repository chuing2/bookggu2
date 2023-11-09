package com.bada.west.controller

import com.bada.west.config.AuthUser
import com.bada.west.model.CommentRequest
import com.bada.west.model.CommentResponse
import com.bada.west.service.CommentService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/issues/{issueId}/comments")
class CommentController (private val commentService: CommentService,){

    @PostMapping
    suspend fun create(
        authUser: AuthUser,
        @PathVariable issueId: String,
        @RequestBody request: CommentRequest,
    ): CommentResponse {
        return commentService.create(authUser, issueId, request)
    }

    @PutMapping("/{id}")
    suspend fun edit(
        authUser: AuthUser,
        @PathVariable id: String,
        @RequestBody request: CommentRequest,
    ) = commentService.edit(authUser, id, request)
//
//    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    fun delete(
//        authUser: AuthUser,
//        @PathVariable issueId: Long,
//        @PathVariable id: Long,
//    ) {
//        commentService.delete(issueId, id, authUser.userId)
//    }


}