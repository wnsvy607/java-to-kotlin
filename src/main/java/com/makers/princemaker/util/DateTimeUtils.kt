package com.makers.princemaker.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


/**
 * object 키워드를 활용해 싱글톤으로 생성하는 예제
 * static 으로 사용하고자 하는 메서드에 @JvmStatic 을 붙여준다.
 */
//object DateTimeUtils {
//    @JvmStatic
//    fun getLocalDateTimeString(localDateTime: LocalDateTime): String {
//        return localDateTime.format(
//            DateTimeFormatter
//                .ofPattern("yyyy-MM-dd 탄생")
//        )
//    }
//}

/**
 * top level function 을 활용해 생성하는 예제
 */
fun getLocalDateTimeString(localDateTime: LocalDateTime): String {
    return localDateTime.format(
        DateTimeFormatter
            .ofPattern("yyyy-MM-dd 탄생")
    )
}

/**
 * 두 방법 중 편한 것을 쓰면 된다.
 */
