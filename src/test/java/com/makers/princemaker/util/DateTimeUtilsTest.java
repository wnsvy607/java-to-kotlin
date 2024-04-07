package com.makers.princemaker.util;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class DateTimeUtilsTest {
	@Test
	void getLocalDateTimeStringTest() {

		/**
		 * object 키워드를 사용한 클래스는 클래스명으로 바로 호출 가능
		 * 단 자바에서 static 메서드처럼 쓰기 위해서 @JvmStatic 을 붙여줘야 한다.
		 */
		// String result = DateTimeUtils.getLocalDateTimeString(
		//     LocalDateTime.of(2023, 12, 21, 10, 10)
		// );

		/**
		 * 탑 레벨 함수는 클래스명+Kt 붙은 이름으로 호출 가능
		 * 당연히 import static도 가능하다.
		 */
		String result = DateTimeUtilsKt.getLocalDateTimeString(
			LocalDateTime.of(2023, 12, 21, 10, 10)
		);

		assertEquals("2023-12-21 탄생", result);
	}
}
