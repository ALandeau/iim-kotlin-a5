package fr.iim.iwm.a5.alex.landeau.kotlin.session

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import fr.iim.iwm.a5.alex.landeau.kotlin.model.IMysqlModel
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class SessionTests {

    @Test
    fun testAdminLogIn() {
        val model = mock<IMysqlModel>{
            on { userLogin("admin", "admin") } doReturn true
        }
        val controller = UserController(model)
        val result = controller.userLogin("admin", "admin")
        assertEquals(true, result)
    }

    @Test
    fun testAdminLogInFail() {
        val model = mock<IMysqlModel> {
            on { userLogin("admin", "admin") } doReturn true
        }
        val controller = UserController(model)
        val result = controller.userLogin("user", "1234")
        assertNotEquals(true, result)
    }

}