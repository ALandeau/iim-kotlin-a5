package fr.iim.iwm.a5.alex.landeau.kotlin.model

import org.junit.Before
import org.junit.Test
import kotlin.test.*

class ModelTests {
    private val model = MysqlModel("jdbc:h2:mem:cms;MODE=MYSQL", "admin", "admin")

    // Article model
    @Before
    fun initDB() {
        model.connectionPool.use { connection ->
            connection.prepareStatement( """
                DROP TABLE IF EXISTS `articles`;
                DROP TABLE IF EXISTS `comments`;
                DROP TABLE IF EXISTS `users`;

                CREATE TABLE `articles` (
                  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
                  `title` varchar(255) DEFAULT NULL,
                  `text` text,
                  PRIMARY KEY (`id`)
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

                CREATE TABLE `comments` (
                  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
                  `text` text,
                  `article_id` int(11) unsigned NOT NULL,
                  PRIMARY KEY (`id`),
                  KEY `article_id` (`article_id`),
                  CONSTRAINT `article_id` FOREIGN KEY (`article_id`) REFERENCES `articles` (`id`) ON DELETE CASCADE
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

                CREATE TABLE `users` (
                  `id` int(11) NOT NULL DEFAULT '0',
                  `username` varchar(255) DEFAULT NULL,
                  `password` varchar(255) DEFAULT NULL,
                  PRIMARY KEY (`id`)
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

                INSERT INTO `articles` (`id`, `title`, `text`)
                VALUES
                    (1,'lorem','Vivamus venenatis leo sed velit aliquet, non elementum nisl convallis. Maecenas tellus orci, lobortis sit amet tortor sit amet, mattis finibus arcu. Aenean vel turpis nisi. Etiam a ultrices nisl. Duis molestie justo tellus, ac vestibulum felis lobortis nec. Ut commodo tempus justo, in egestas risus tincidunt sit amet. Nullam vulputate egestas ipsum quis feugiat. Sed imperdiet ultricies congue. Praesent hendrerit condimentum vehicula. Phasellus at viverra eros. Maecenas blandit lorem mi. Quisque mattis ipsum fermentum, condimentum arcu tempor, pharetra quam. Nunc laoreet convallis faucibus. Pellentesque sit amet arcu vel felis bibendum aliquet ac vel mi. Aliquam porttitor tincidunt nisi, et vulputate justo pulvinar eget. '),
                    (2,'dolor','Duis lobortis, magna in sollicitudin blandit, lectus urna eleifend tellus, et dapibus nisi arcu et elit. Duis id arcu vitae tortor convallis ultricies. Vestibulum gravida tristique nisi. Vestibulum at enim ut eros ultricies gravida eget in risus. Proin blandit, justo at sagittis vehicula, augue leo pellentesque massa, at mattis nisi tellus quis est. Interdum et malesuada fames ac ante ipsum primis in faucibus. Etiam eu tincidunt ante, nec dignissim nibh. Maecenas aliquet nibh nec consequat tristique. Cras ligula nunc, cursus ut est at, cursus posuere arcu. Nullam nec odio in ipsum venenatis tincidunt vel non magna. Donec elementum enim sollicitudin turpis eleifend, et mollis nisl tincidunt. Fusce a porttitor ipsum. Etiam bibendum porttitor dolor ut lobortis.\n\nSuspendisse potenti. Nam pulvinar sollicitudin maximus. Aenean sit amet molestie nisi. Proin eu aliquam dolor, at commodo felis. In eleifend tempor efficitur. Nunc fermentum nisi vitae sagittis finibus. Cras nisi velit, eleifend sit amet hendrerit in, porta ac massa. Etiam mattis pulvinar erat at posuere. Maecenas laoreet ut nisi at ullamcorper. Nunc aliquam volutpat semper. Sed non augue porta, iaculis nibh ac, maximus eros. Etiam bibendum consequat sapien sit amet sodales. Sed mollis neque augue. Morbi efficitur in mauris placerat sollicitudin.');

                INSERT INTO `comments` (`id`, `text`, `article_id`)
                VALUES
                    (1,'ceci est un commentaire',1);

                INSERT INTO `users` (`id`, `username`, `password`)
                VALUES
                    (1,'admin','admin');
            """).use { stmt ->
                stmt.execute()
            }
        }
    }

    @Test
    fun testArticleInDB() {
        val article = model.getSingleArticle(1)
        assertNotNull(article)
        assertEquals(1, article.id)
        assertEquals("lorem", article.title)
        assertTrue(article.text!!.startsWith("Vivamus"))
    }

    @Test
    fun testArticleNotInDB() {
        val article = model.getSingleArticle(3)
        assertNull(article)
    }

    @Test
    fun testCommentInDB() {
        val comments = model.getCommentsArticle(1)
        assertNotNull(comments)
        for (comment in comments) {
            assertEquals(1, comment.id)
            assertTrue(comment.text.startsWith("ceci"))
        }
    }

    @Test
    fun testCommentNotInDB() {
        val comments = model.getCommentsArticle(3)
        for (comment in comments) {
            assertNull(comment.id)
            assertNull(comment.text)
        }
    }

    @Test
    fun testAdminInDB() {
        val user = model.userLogin("admin", "admin")
        assertTrue(user)
    }

    @Test
    fun testAdminNotInDB() {
        val user = model.userLogin("user", "1234")
        assertFalse(user)
    }
}