/*
 * Copyright (c) 2021 Proton Technologies AG
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

package me.proton.core.test.android.uitests.tests.payments

import me.proton.core.payment.presentation.R
import me.proton.core.test.android.plugins.data.User.Plan
import me.proton.core.test.android.robots.auth.AddAccountRobot
import me.proton.core.test.android.robots.payments.AddCreditCardRobot
import me.proton.core.test.android.uitests.CoreexampleRobot
import me.proton.core.test.android.uitests.tests.BaseTest
import org.junit.Before
import org.junit.Test

class NewCreditCardTests : BaseTest() {

    private val user = users.getUser { it.plan == Plan.Free && it.cards.isEmpty() }
    private val newCreditCardRobot = AddCreditCardRobot()

    @Before
    fun login() {
        AddAccountRobot()
            .signIn()
            .loginUser<CoreexampleRobot>(user)
            .upgradePrimary<AddCreditCardRobot>()
            .verify { billingDetailsDisplayed(Plan.Visionary, "yearly", "€", "48.00") }
    }

    @Test
    fun missingPaymentDetails() {
        newCreditCardRobot
            .pay<AddCreditCardRobot>()
            .verify {
                inputErrorDisplayed(R.string.payments_error_card_name)
                inputErrorDisplayed(R.string.payments_error_card_number)
                inputErrorDisplayed(R.string.payments_error_cvc)
            }
    }

    @Test
    fun invalidCreditCardNumber() {
        newCreditCardRobot
            .ccname("${user.firstName} ${user.lastName}")
            .country()
            .selectCountry<AddCreditCardRobot>("Angola")
            .expirationDate("1234")
            .cvc("123")
            .ccnumber("424242424242424")
            .postalCode("1234")
            .pay<AddCreditCardRobot>()
            .verify {
                errorSnackbarDisplayed("Your card has been declined. Please try a different card or contact your bank to authorize the charge")
            }
    }
}
