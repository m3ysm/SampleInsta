package com.example.sample.data.model

data class MyResponse<T>(
    var status: Status,
    var data: T? = null,
    var throwable: Throwable? = null,
    val message: String? = null
) {

    companion object {

        fun <T> loading(data: T? = null): MyResponse<T> {
            return MyResponse(
                status = Status.LOADING,
                data = data
            )
        }

        fun <T> success(data: T? = null): MyResponse<T>? {
            return MyResponse(
                status = Status.SUCCESS,
                data = data
            )
        }

        fun <T> failed(throwable: Throwable): MyResponse<T> {
            return MyResponse(
                status = Status.FAILED,
                throwable = throwable
            )
        }

        fun <T> failed(message: String): MyResponse<T> {
            return MyResponse(
                status = Status.FAILED,
                message = message
            )
        }
    }
}

enum class Status {
    LOADING,
    SUCCESS,
    FAILED,
}