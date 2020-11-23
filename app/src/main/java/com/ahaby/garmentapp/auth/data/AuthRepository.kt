package com.ahaby.garmentapp.auth.data

import com.ahaby.garmentapp.auth.data.remote.RemoteAuthDataSource
import com.ahaby.garmentapp.core.Api
import com.ahaby.garmentapp.core.Result
import com.ahaby.garmentapp.core.Utils

object AuthRepository {
    var user: User? = null
        private set

    val isLoggedIn: Boolean
        get() = Utils.instance()?.fetchValueString("token") != null

    init {
        Api.tokenInterceptor.token = Utils.instance()?.fetchValueString("token")
        user = null
    }

    fun logout() {
        user = null
        Utils.instance()?.deleteValueString("token")
        Api.tokenInterceptor.token = null
    }

    suspend fun login(username: String, password: String): Result<TokenHolder> {
        val user = User(username, password)
        val result = RemoteAuthDataSource.login(user)
        if (result is Result.Success<TokenHolder>) {
            setLoggedInUser(user, result.data)
            Utils.instance()?.storeValueString("token", result.data.token)
        }
        return result
    }

    private fun setLoggedInUser(user: User, tokenHolder: TokenHolder) {
        this.user = user
        Api.tokenInterceptor.token = tokenHolder.token
    }
}
