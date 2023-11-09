package com.bada.west.domain.entity

import jakarta.annotation.Generated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime


@Table("fl_user_tbl_ss")
data class FlUserAto (

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String? = null,               /* PK */

    var groupId: String? = null,     /* GROUP ID , Default Is PK*/
    var levelCode: String? = null ,       /* A,B,C,D */

    var ssAaZ00: String? = null,          /* user_id , chuing */
    var ssBbZ01: String? = null,          /* user_name, 김태준 */
    var ssCcZ02: String? = null,         /* password */

    var ssDdZ03: String? = null,          /* auth_provider , kakao */
    var ssEeZ04: String? = null,         /* e_mail , chuing@daum.net */
    var ssFfZ05: String? = null,         /* my_village_name */

    var ssGgZ06: String? = null,          /* my_nick, 동해바다 */
    var ssHhZ07: String? = null,        /* my_mbti, INFP */
    var ssIiZ08: String? = null,          /* my_img , kakao profile */

    var ssJjZ09: String? = null,          /* my_stamp */


//    created_by varchar(100) DEFAULT NULL,
//    updated_by varchar(100) DEFAULT NULL,

    var createdBy: String = "",        /* 생성자 */
    var updatedBy: String = "",        /* 수정자 */


    @CreatedDate
    @Column("created_at")
    val createdAt: LocalDateTime? = null,

    @LastModifiedDate
    @Column("updated_at")
    val updatedAt: LocalDateTime? = null,

)