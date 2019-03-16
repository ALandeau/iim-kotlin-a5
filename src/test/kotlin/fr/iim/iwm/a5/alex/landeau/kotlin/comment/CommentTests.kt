package fr.iim.iwm.a5.alex.landeau.kotlin.comment

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import fr.iim.iwm.a5.alex.landeau.kotlin.article.Article
import fr.iim.iwm.a5.alex.landeau.kotlin.article.ArticleSingleController
import fr.iim.iwm.a5.alex.landeau.kotlin.model.IMysqlModel
import org.junit.Test

class CommentTests {

    @Test
    fun testPostComment() {
        val model = mock<IMysqlModel>{
            on { getSingleArticle(42) } doReturn Article(42, "title", "text", emptyList())
        }
        val controller = ArticleSingleController(model)
        controller.comment("text", 42)
        verify(model).postComment("text", 42)
    }

}