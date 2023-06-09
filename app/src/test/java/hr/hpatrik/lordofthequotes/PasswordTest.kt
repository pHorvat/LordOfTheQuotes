package hr.hpatrik.lordofthequotes

import isValidPassword
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Test

class PasswordTest {
    @Test
    fun `test isValidPassword with valid password`() {
        val password = "Abc123@#"

        val isValid = password.isValidPassword()

        assertTrue(isValid)
    }

    @Test
    fun `test isValidPassword with invalid password`() {
        val password = "password123"

        val isValid = password.isValidPassword()

        assertFalse(isValid)
    }
}