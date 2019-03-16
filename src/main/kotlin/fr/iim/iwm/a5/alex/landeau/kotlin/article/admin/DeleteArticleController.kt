package fr.iim.iwm.a5.alex.landeau.kotlin.article.admin

import fr.iim.iwm.a5.alex.landeau.kotlin.model.IMysqlModel

class DeleteArticleController(val model: IMysqlModel) : IDeleteArticleController {
    override fun delete(articleId: Int) {
        model.deleteArticle(articleId)
    }
}