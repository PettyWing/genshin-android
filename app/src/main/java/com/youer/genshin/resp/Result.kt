package com.youer.genshin.resp

import com.youer.genshin.constants.ResultCode
import java.io.Serializable

class Result<T> : Serializable {
    var isSuccess = false
        get() {
            return code == ResultCode.SUCCESS.code
        }
    var result: T? = null
        private set
    var code: String? = null
    var message: String? = null
}