package fr.iim.iwm.a5.alex.landeau.kotlin.model

import fr.iim.iwm.a5.alex.landeau.kotlin.article.Article
import fr.iim.iwm.a5.alex.landeau.kotlin.comment.Comment

interface IMysqlModel {
    fun getListArticle(): List<Article>
    fun getSingleArticle(articleId: Int): Article?
    fun getCommentsArticle(articleId: Int): List<Comment>
    fun postComment(text: String, articleId: Int)
    fun deleteComment(commentId: Int)
    fun adminPostArticle(title: String, text: String)
    fun deleteArticle(articleId: Int)
    fun userLogin(username: String, password: String): Boolean
}