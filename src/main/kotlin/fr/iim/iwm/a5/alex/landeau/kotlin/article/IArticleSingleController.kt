package fr.iim.iwm.a5.alex.landeau.kotlin.article

interface IArticleSingleController {
    fun start(id: Int): Any
    fun comment(text: String, articleId: Int)
}