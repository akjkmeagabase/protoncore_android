/*
 * Copyright (c) 2020 Proton Technologies AG
 * This file is part of Proton Technologies AG and ProtonCore.
 *
 * ProtonCore is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ProtonCore is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ProtonCore.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.proton.core.auth.data.api

import me.proton.core.auth.data.api.request.LoginInfoRequest
import me.proton.core.auth.data.api.response.LoginInfoResponse
import me.proton.core.auth.data.api.request.LoginRequest
import me.proton.core.auth.data.api.response.LoginResponse
import me.proton.core.auth.data.api.request.SecondFactorRequest
import me.proton.core.auth.data.api.response.SecondFactorResponse
import me.proton.core.auth.data.api.response.ModulusResponse
import me.proton.core.auth.data.api.response.ScopesResponse
import me.proton.core.network.data.protonApi.BaseRetrofitApi
import me.proton.core.network.data.protonApi.GenericResponse
import me.proton.core.network.domain.TimeoutOverride
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Tag

interface AuthenticationApi : BaseRetrofitApi {

    @POST("auth/info")
    suspend fun getLoginInfo(@Body request: LoginInfoRequest): LoginInfoResponse

    @GET("auth/modulus")
    suspend fun getRandomModulus(): ModulusResponse

    @GET("auth/scopes")
    suspend fun getScopes(): ScopesResponse

    @POST("auth")
    suspend fun performLogin(@Body request: LoginRequest): LoginResponse

    @POST("auth/2fa")
    suspend fun performSecondFactor(@Body request: SecondFactorRequest): SecondFactorResponse

    @DELETE("auth")
    suspend fun revokeSession(@Tag timeout: TimeoutOverride): GenericResponse
}
