package fr.iim.iwm.a5.alex.landeau.kotlin.model

import fr.iim.iwm.a5.alex.landeau.kotlin.article.Article
import fr.iim.iwm.a5.alex.landeau.kotlin.comment.Comment


class MysqlModel(url: String, user: String?, password: String?) : IMysqlModel {

    val connectionPool = ConnectionPool(url, user!!, password!!)

    override fun getListArticle(): List<Article> {
        val articles = ArrayList<Article>()

        connectionPool.use { connection ->
            connection.prepareStatement("SELECT * FROM articles").use { stmt ->
                val results = stmt.executeQuery()
                while (results.next()) {
                    articles += Article(
                        results.getInt("id"), results.getString("title"), results.getString("text"), emptyList()
                    )
                }
            }
        }
        return articles
    }

    override fun getSingleArticle(articleId: Int): Article? {
        connectionPool.use { connection ->
            connection.prepareStatement("SELECT * FROM articles where id = ?").use { stmt ->
                stmt.setInt(1, articleId)

                val results = stmt.executeQuery()
                val found = results.next()

                if (found) {
                    return Article(
                        results.getInt("id"),
                        results.getString("title"),
                        results.getString("text"),
                        emptyList()
                    )
                }
            }
        }
        return null
    }

    override fun getCommentsArticle(articleId: Int): List<Comment> {
        val comments = ArrayList<Comment>()
        connectionPool.use { connection ->
            connection.prepareStatement("SELECT * FROM comments WHERE article_id = ?").use { stmt ->
                stmt.setInt(1, articleId)
                val results = stmt.executeQuery()
                while (results.next()) {
                    comments += Comment(
                        results.getInt("id"), results.getString("text")
                    )
                }
            }
        }
        return comments
    }

    override fun postComment( text: String, articleId: Int){
        connectionPool.use{ connection ->
            connection.prepareStatement("INSERT INTO comments (text, article_id) VALUES (?, ((select id from articles where id = ?)))").use { stmt ->
                stmt.setString(1, text)
                stmt.setInt(2, articleId)
                stmt.execute()
            }
        }
    }

    override fun deleteComment(commentId: Int) {
        connectionPool.use{ connection ->
            connection.prepareStatement("DELETE FROM comments WHERE id = ?").use { stmt ->
                stmt.setInt(1, commentId)
                stmt.execute()
            }
        }
    }

    override fun adminPostArticle(title: String ,text: String){
        connectionPool.use{ connection ->
            connection.prepareStatement("INSERT INTO articles (title, text) VALUES (?, ?)").use { stmt ->
                stmt.setString(1, title)
                stmt.setString(2, text)
                stmt.execute()
            }
        }
    }

    override fun deleteArticle(articleId: Int) {
        connectionPool.use{ connection ->
            connection.prepareStatement("DELETE FROM articles WHERE id = ?").use { stmt ->
                stmt.setInt(1, articleId)
                stmt.execute()
            }
        }
    }

    override fun userLogin(username: String, password: String) : Boolean {
        connectionPool.use {  connection ->
            connection.prepareStatement("SELECT * FROM users WHERE username = ?").use { stmt ->
                stmt.setString(1, username)
                stmt.execute()

                val result = stmt.resultSet
                if (result.next()) {
                    val checkPassword = result.getString("password")
                    if (password == checkPassword) {
                        return true
                    }
                }
            }
        }
        return false
    }
}