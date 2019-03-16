package fr.iim.iwm.a5.alex.landeau.kotlin.session

import fr.iim.iwm.a5.alex.landeau.kotlin.model.IMysqlModel

class UserController(val model: IMysqlModel) : IUserController {
    override fun userLogin(username: String, password: String) : Boolean {
        return model.userLogin(username, password)
    }
}