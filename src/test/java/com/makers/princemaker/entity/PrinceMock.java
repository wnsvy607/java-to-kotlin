package com.makers.princemaker.entity;

import java.time.LocalDateTime;

import com.makers.princemaker.code.StatusCode;
import com.makers.princemaker.type.PrinceLevel;
import com.makers.princemaker.type.SkillType;

/**
 * @author Snow
 */
public class PrinceMock {
	public static Prince createPrince(
		PrinceLevel princeLevel,
		SkillType skillType,
		Integer experienceYears,
		String princeId
	) {
		return new Prince(
            null,
			princeLevel,
			skillType,
			StatusCode.HEALTHY,
			experienceYears,
			princeId,
			"name",
			28,
			LocalDateTime.now(),
			LocalDateTime.now()
			);
	}
}
