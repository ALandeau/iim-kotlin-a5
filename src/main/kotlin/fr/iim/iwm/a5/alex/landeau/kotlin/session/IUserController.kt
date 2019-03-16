package fr.iim.iwm.a5.alex.landeau.kotlin.session

interface IUserController {
    fun userLogin(username: String, password: String) : Boolean
}