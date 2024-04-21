package com.makers.princemaker.entity

import com.makers.princemaker.code.StatusCode
import com.makers.princemaker.code.StatusCode.HEALTHY
import com.makers.princemaker.type.PrinceLevel
import com.makers.princemaker.type.PrinceLevel.JUNIOR_PRINCE
import com.makers.princemaker.type.SkillType
import com.makers.princemaker.type.SkillType.INTELLECTUAL
import java.time.LocalDateTime

fun dummyPrince(
    id: Long? = 1L,
    princeLevel: PrinceLevel = JUNIOR_PRINCE,
    skillType: SkillType = INTELLECTUAL,
    status: StatusCode = HEALTHY,
    experienceYears: Int = 23,
    princeId: String = "princeId",
    name: String = "name",
    age: Int = 28,
    createdAt: LocalDateTime? = LocalDateTime.now(),
    updatedAt: LocalDateTime? = LocalDateTime.now()
) = Prince(
    id = id,
    princeLevel = princeLevel,
    skillType = skillType,
    status = status,
    experienceYears = experienceYears,
    princeId = princeId,
    name = name,
    age = age,
    createdAt = createdAt,
    updatedAt = updatedAt
)
