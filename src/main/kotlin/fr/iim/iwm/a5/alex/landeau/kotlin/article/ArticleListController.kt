package fr.iim.iwm.a5.alex.landeau.kotlin.article

import fr.iim.iwm.a5.alex.landeau.kotlin.model.IMysqlModel

class ArticleListController(private val model: IMysqlModel) :
    IArticleListController {
    override fun start(): List<Article> {
        return model.getListArticle()
    }
}