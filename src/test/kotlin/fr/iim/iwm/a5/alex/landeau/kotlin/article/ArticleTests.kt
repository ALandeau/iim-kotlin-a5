package fr.iim.iwm.a5.alex.landeau.kotlin.article

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import fr.iim.iwm.a5.alex.landeau.kotlin.comment.Comment
import fr.iim.iwm.a5.alex.landeau.kotlin.model.IMysqlModel
import io.ktor.http.HttpStatusCode
import org.junit.Test
import kotlin.test.assertEquals

class ArticleTests {

    @Test
    fun testArticleFound() {
        val model = mock<IMysqlModel> {
            on { getSingleArticle(42) } doReturn Article(42, "title", "text", emptyList())
            on { getCommentsArticle(42) } doReturn listOf(Comment(42, "text"))
        }
        val controller = ArticleSingleController(model)
        val result = controller.start(42)
        assertEquals(Article(42, "title", "text", listOf(Comment(42, "text"))), result)
    }

    @Test
    fun testArticleNotFound() {
        val model = mock<IMysqlModel> {}
        val controller = ArticleSingleController(model)
        val result = controller.start(42)
        assertEquals(HttpStatusCode.NotFound, result)
    }
}