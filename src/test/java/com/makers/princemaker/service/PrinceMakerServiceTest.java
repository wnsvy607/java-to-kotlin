package com.makers.princemaker.service;

import static com.makers.princemaker.constant.PrinceMakerConstant.*;
import static com.makers.princemaker.entity.PrinceMock.*;
import static com.makers.princemaker.type.PrinceLevel.*;
import static com.makers.princemaker.type.SkillType.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.makers.princemaker.code.PrinceMakerErrorCode;
import com.makers.princemaker.dto.CreatePrince;
import com.makers.princemaker.dto.PrinceDetailDto;
import com.makers.princemaker.entity.Prince;
import com.makers.princemaker.exception.PrinceMakerException;
import com.makers.princemaker.repository.PrinceRepository;

/**
 * @author Snow
 */
@ExtendWith(MockitoExtension.class)
class PrinceMakerServiceTest {
	@Mock
	private PrinceRepository princeRepository;

	@InjectMocks
	private PrinceMakerService princeMakerService;

	@Test
	void getPrinceTest() {
		//given
		Prince juniorPrince =
			createPrince(JUNIOR_PRINCE, INTELLECTUAL,
				MAX_JUNIOR_EXPERIENCE_YEARS, "princeId");
		given(princeRepository.findByPrinceId(anyString()))
			.willReturn(Optional.of(juniorPrince));

		//when
		PrinceDetailDto prince = princeMakerService.getPrince("princeId");

		//then
		assertEquals(JUNIOR_PRINCE, prince.getPrinceLevel());
		assertEquals(INTELLECTUAL, prince.getSkillType());
		assertEquals(MAX_JUNIOR_EXPERIENCE_YEARS, prince.getExperienceYears());
	}

	@Test
	void createPrinceTest_success() {
		//given
		CreatePrince.Request request = new CreatePrince.Request(
			MIDDLE_PRINCE,
			INTELLECTUAL,
			7,
			"princeId",
			"name",
			28
		);

		ArgumentCaptor<Prince> captor =
			ArgumentCaptor.forClass(Prince.class);

		//when
		CreatePrince.Response response = princeMakerService.createPrince(request);

		//then
		verify(princeRepository, times(1))
			.save(captor.capture());
		Prince savedPrince = captor.getValue();
		assertEquals(MIDDLE_PRINCE, savedPrince.getPrinceLevel());
		assertEquals(INTELLECTUAL, savedPrince.getSkillType());
		assertEquals(7, savedPrince.getExperienceYears().intValue());

		assertEquals(MIDDLE_PRINCE, response.getPrinceLevel());
		assertEquals(INTELLECTUAL, response.getSkillType());
		assertEquals(7, response.getExperienceYears().intValue());
	}

	@Test
	void createPrinceTest_failed_with_duplicated() {
		//given
		Prince juniorPrince =
			createPrince(JUNIOR_PRINCE, INTELLECTUAL, MAX_JUNIOR_EXPERIENCE_YEARS, "princeId");
		CreatePrince.Request request = new CreatePrince.Request(
			JUNIOR_PRINCE,
			INTELLECTUAL,
			3,
			"princeId",
			"name",
			28);
		given(princeRepository.findByPrinceId(anyString()))
			.willReturn(Optional.of(juniorPrince));

		//when
		PrinceMakerException exception =
			assertThrows(PrinceMakerException.class, () -> princeMakerService.createPrince(request));
		//then
		assertEquals(PrinceMakerErrorCode.DUPLICATED_PRINCE_ID, exception.getPrinceMakerErrorCode());
	}

	@Test
	void createPrinceTest_failed_with_invalid_experience() {
		//given
		CreatePrince.Request request = new CreatePrince.Request(
			KING,
			INTELLECTUAL,
			MIN_KING_EXPERIENCE_YEARS - 3,
			"princeId",
			"name",
			28
		);
		given(princeRepository.findByPrinceId(anyString()))
			.willReturn(Optional.empty());

		//when
		PrinceMakerException exception =
			assertThrows(PrinceMakerException.class, () -> princeMakerService.createPrince(request));
		//then
		assertEquals(PrinceMakerErrorCode.LEVEL_AND_EXPERIENCE_YEARS_NOT_MATCH, exception.getPrinceMakerErrorCode());
	}
}
