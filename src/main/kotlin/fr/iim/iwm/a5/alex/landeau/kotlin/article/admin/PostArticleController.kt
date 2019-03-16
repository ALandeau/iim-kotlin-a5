package fr.iim.iwm.a5.alex.landeau.kotlin.article.admin

import fr.iim.iwm.a5.alex.landeau.kotlin.model.IMysqlModel

class PostArticleController(val model: IMysqlModel) : IPostArticleController {
    override fun post(title: String, text: String) {
        if (title.isNotBlank() && text.isNotBlank())
            model.adminPostArticle(title, text)
    }
}