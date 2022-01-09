package com.example.truthordaresaudi

import com.example.truthordaresaudi.data.model.Users
import org.junit.Assert
import org.junit.Test
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class TorDUnitTest {
    private lateinit var validUser: Users
    private lateinit var invalidUser: Users

    @Before
    fun startUp() {
        validUser = Users()
        validUser.email = "m@a.com"
        validUser.fullName = "Marah"
        validUser.uid = "8fc26aec-0c8c-45af-b120-94562385ba5e"

        invalidUser = Users()
    }

    @Test
    fun validLogin() {
        assert((validUser.email).isNotEmpty())
    }

    @Test
    fun invalidLogin() {
        assert((invalidUser.email).isEmpty())
    }

}