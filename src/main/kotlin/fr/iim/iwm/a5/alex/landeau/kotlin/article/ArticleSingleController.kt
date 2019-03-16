package fr.iim.iwm.a5.alex.landeau.kotlin.article

import fr.iim.iwm.a5.alex.landeau.kotlin.model.IMysqlModel
import io.ktor.http.HttpStatusCode

class ArticleSingleController(private val model: IMysqlModel) :
    IArticleSingleController {
    override fun start(id: Int): Any {
        val article = model.getSingleArticle(id)
        val comments = model.getCommentsArticle(id)
        if(article != null)
            return Article(id, article.title, article.text, comments)
        return HttpStatusCode.NotFound
    }

    override fun comment(text: String, articleId: Int){
        model.postComment(text, articleId)
    }
}