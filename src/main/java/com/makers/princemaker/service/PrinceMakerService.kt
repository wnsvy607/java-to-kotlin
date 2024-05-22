package com.makers.princemaker.service

import com.makers.princemaker.code.PrinceMakerErrorCode.*
import com.makers.princemaker.code.StatusCode
import com.makers.princemaker.constant.PrinceMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS
import com.makers.princemaker.constant.PrinceMakerConstant.MIN_KING_EXPERIENCE_YEARS
import com.makers.princemaker.controller.CreatePrince
import com.makers.princemaker.controller.toCreatePrinceResponse
import com.makers.princemaker.dto.EditPrince
import com.makers.princemaker.dto.PrinceDetailDto
import com.makers.princemaker.dto.PrinceDto
import com.makers.princemaker.dto.toPrinceDetailDto
import com.makers.princemaker.entity.Prince
import com.makers.princemaker.entity.WoundedPrince
import com.makers.princemaker.exception.PrinceMakerException
import com.makers.princemaker.repository.PrinceRepository
import com.makers.princemaker.repository.WoundedPrinceRepository
import com.makers.princemaker.type.PrinceLevel
import com.makers.princemaker.util.shouldNotTrue
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author Snow
 */
@Service
class PrinceMakerService(
    val princeRepository: PrinceRepository,
    val woundedPrinceRepository: WoundedPrinceRepository
) {

    @Transactional
    fun createPrince(request: CreatePrince.Request): CreatePrince.Response {
        validateCreatePrinceRequest(request)
        val prince = Prince(
            null,
            request.princeLevel!!,
            request.skillType!!, StatusCode.HEALTHY,
            request.experienceYears!!,
            request.princeId!!,
            request.name!!,
            request.age!!,
            null, null
        ).also {
            princeRepository.save(it)
        }
        return prince.toCreatePrinceResponse()
    }

    private fun validateCreatePrinceRequest(request: CreatePrince.Request) {
        (princeRepository.findByPrinceId(request.princeId!!) != null)
            .shouldNotTrue(DUPLICATED_PRINCE_ID)

        (request.princeLevel == PrinceLevel.KING
                && request.experienceYears!! < MIN_KING_EXPERIENCE_YEARS
                ).shouldNotTrue(LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH)

        (request.princeLevel == PrinceLevel.MIDDLE_PRINCE && (request.experienceYears!! > MIN_KING_EXPERIENCE_YEARS
                || request.experienceYears < MAX_JUNIOR_EXPERIENCE_YEARS)
                ).shouldNotTrue(LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH)

        (request.princeLevel == PrinceLevel.JUNIOR_PRINCE
                && request.experienceYears!! > MAX_JUNIOR_EXPERIENCE_YEARS
                ).shouldNotTrue(LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH)
    }

    @get:Transactional
    val allPrince: List<PrinceDto>
        get() = princeRepository.findByStatusEquals(StatusCode.HEALTHY)
            .map { PrinceDto.fromEntity(it) }

    @Transactional
    fun getPrince(princeId: String): PrinceDetailDto {
        return princeRepository.findByPrinceId(princeId)
            ?.toPrinceDetailDto()
            ?: throw PrinceMakerException(NO_SUCH_PRINCE)
    }

    @Transactional
    fun editPrince(
        princeId: String, request: EditPrince.Request
    ): PrinceDetailDto {
        val prince =
            princeRepository.findByPrinceId(princeId) ?: throw PrinceMakerException(NO_SUCH_PRINCE)

        prince.apply {
            this.princeLevel = request.princeLevel
            this.princeLevel = request.princeLevel
            this.skillType = request.skillType
            this.experienceYears = request.experienceYears
            this.name = request.name
            this.age = request.age
        }
        return prince.toPrinceDetailDto()
    }

    @Transactional
    fun woundPrince(
        princeId: String
    ): PrinceDetailDto {
        return with(
            princeRepository.findByPrinceId(princeId)
                ?: throw PrinceMakerException(NO_SUCH_PRINCE)
        ) {
            this.status = StatusCode.WOUNDED

            val woundedPrince = WoundedPrince.builder()
                .princeId(this.princeId)
                .name(this.name)
                .build()
            woundedPrinceRepository.save(woundedPrince)
            return this.toPrinceDetailDto()
        }
    }
}
