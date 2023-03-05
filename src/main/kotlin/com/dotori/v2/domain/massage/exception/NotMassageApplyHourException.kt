package com.dotori.v2.domain.massage.exception

import com.dotori.v2.global.error.ErrorCode
import com.dotori.v2.global.error.exception.BasicException

class NotMassageApplyHourException : BasicException(ErrorCode.MASSAGE_CANT_REQUEST_THIS_TIME) {
}