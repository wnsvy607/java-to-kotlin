package com.makers.princemaker.dto

import com.makers.princemaker.entity.Prince
import com.makers.princemaker.type.PrinceLevel
import com.makers.princemaker.type.SkillType
import lombok.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

/**
 * 1. nullable 에 대해 체크 주의가 필요하다.
 * - DB, 외부 API 요청/응답 등 외부 환경과 연관된 DTO 는 주의하자.
 *
 * 2. Lombok으로 생성되었던 getter/setter/생성자/toString/equals 등은 상황에 맞춰 수정 필요
 * - getter/setter: 코틀린은 기본적으로 getter/setter를 제공핳는 프로퍼티로 클래스가 작성됨
 * - 생성자: intelliJ 코틀린 변환기로 변환 후 코틀린 주 생성자 스타일로 바꿔주는 것 추천
 * - toString/equals: data class로 변환하는 것이 좋다.
 */
class CreatePrince {

    data class Request(
        @NotNull
        val princeLevel: PrinceLevel? = null,
        @NotNull
        val skillType: SkillType? = null,
        @NotNull @Min(0)
        val experienceYears: Int? = null,
        @NotNull @Size(min = 3, max = 50, message = "invalid princeId")
        val princeId: String? = null,
        @NotNull @Size(min = 2, max = 50, message = "invalid name")
        val name: String? = null,
        @NotNull @Min(18)
        val age: Int? = null
    )

    data class Response(
        val princeLevel: PrinceLevel? = null,
        val skillType: SkillType? = null,
        val experienceYears: Int? = null,
        val princeId: String? = null,
        val name: String? = null,
        val age: Int? = null
    ) {
        companion object {
            @JvmStatic
            fun fromEntity(prince: Prince): Response {
                return Response(
                    princeLevel = prince.princeLevel,
                    skillType = prince.skillType,
                    experienceYears = prince.experienceYears,
                    princeId = prince.princeId,
                    name = prince.name,
                    age = prince.age
                )
            }
        }
    }
}
