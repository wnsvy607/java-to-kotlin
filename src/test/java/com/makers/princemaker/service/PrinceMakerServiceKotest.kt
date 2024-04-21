package com.makers.princemaker.service

import com.makers.princemaker.code.PrinceMakerErrorCode.DUPLICATED_PRINCE_ID
import com.makers.princemaker.constant.PrinceMakerConstant
import com.makers.princemaker.controller.CreatePrince
import com.makers.princemaker.dto.dummyCreatePrinceRequest
import com.makers.princemaker.entity.PrinceMock
import com.makers.princemaker.entity.dummyPrince
import com.makers.princemaker.exception.PrinceMakerException
import com.makers.princemaker.repository.PrinceRepository
import com.makers.princemaker.repository.WoundedPrinceRepository
import com.makers.princemaker.type.PrinceLevel.JUNIOR_PRINCE
import com.makers.princemaker.type.PrinceLevel.MIDDLE_PRINCE
import com.makers.princemaker.type.SkillType.INTELLECTUAL
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.util.*

class PrinceMakerServiceKotest : BehaviorSpec({
    val princeRepository: PrinceRepository = mockk()
    val woundedPrinceRepository: WoundedPrinceRepository = mockk()

    val princeMakerService = PrinceMakerService(princeRepository, woundedPrinceRepository)

    Given("프린스 생성을 진행할 때") {
        val request = dummyCreatePrinceRequest().copy(
            skillType = INTELLECTUAL,
            experienceYears = 3
        )

        val juniorPrince = dummyPrince()
        every { princeRepository.save(any()) } returns juniorPrince

        When("princeId가 중복되지 않고 정상 요청이 오면") {
            every { princeRepository.findByPrinceId(any()) } returns Optional.empty()
            val result = princeMakerService.createPrince(request)
            Then("정상 응답") {
                assertSoftly(result) {
                    princeLevel shouldBe JUNIOR_PRINCE
                    skillType shouldBe INTELLECTUAL
                    experienceYears shouldBe 23
                }
            }
        }

        When("princeId가 중복되고 정상 요청이 오면") {
            every { princeRepository.findByPrinceId(any()) } returns Optional.of(juniorPrince)

            val ex = shouldThrow<PrinceMakerException> {
                princeMakerService.createPrince(request)
            }
            Then("중복 오류 응답") {
                ex.princeMakerErrorCode shouldBe DUPLICATED_PRINCE_ID
            }
        }
    }
})
