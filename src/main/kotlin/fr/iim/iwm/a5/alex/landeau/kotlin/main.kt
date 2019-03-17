package fr.iim.iwm.a5.alex.landeau.kotlin

import fr.iim.iwm.a5.alex.landeau.kotlin.article.ArticleListController
import fr.iim.iwm.a5.alex.landeau.kotlin.article.ArticleSingleController
import fr.iim.iwm.a5.alex.landeau.kotlin.article.admin.DeleteArticleController
import fr.iim.iwm.a5.alex.landeau.kotlin.article.admin.PostArticleController
import fr.iim.iwm.a5.alex.landeau.kotlin.comment.admin.DeleteCommentController
import fr.iim.iwm.a5.alex.landeau.kotlin.model.MysqlModel
import fr.iim.iwm.a5.alex.landeau.kotlin.session.SingleSession
import fr.iim.iwm.a5.alex.landeau.kotlin.session.UserController
import freemarker.cache.ClassTemplateLoader
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.freemarker.FreeMarker
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.files
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.request.receiveParameters
import io.ktor.response.respond
import io.ktor.response.respondRedirect
import io.ktor.routing.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.sessions.*

class App

fun Application.cmsApp(
    articleList: ArticleListController,
    articleSingle: ArticleSingleController,
    postArticle: PostArticleController,
    deleteArticle: DeleteArticleController,
    delComment:  DeleteCommentController,
    user: UserController
) {
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(App::class.java.classLoader, "templates")
    }

    install(Sessions) {
        cookie<SingleSession>("session") {
            cookie.path = "/"
        }
    }


    routing {

        get("/") {
            val articles = articleList.start()
            val session = call.sessions.get<SingleSession>()
            call.respond(FreeMarkerContent("index.ftl", mapOf("articles" to articles, "session" to session), "e"))
        }

        route("/login") {
            get {
                val session: SingleSession? = call.sessions.get<SingleSession>()
                if (session != null) {
                    call.respondRedirect("/")
                } else {
                    call.respond(FreeMarkerContent("login.ftl", null))
                }
            }
            post {
                val session: SingleSession? = call.sessions.get<SingleSession>()
                val form  = call.receiveParameters()
                val username = form["username"]!!
                val password = form["password"]!!

                if (session != null){
                    call.respond(HttpStatusCode.Forbidden)
                } else {
                    if (user.userLogin(username, password)) {
                        call.sessions.set(SingleSession(username, password))
                        call.respondRedirect("/")
                    } else {
                        call.respond(FreeMarkerContent("login.ftl", mapOf("erreur" to "Erreur d'authentification<br>Identifiants incorrects")))
                    }
                }

            }
        }

        route("/logout") {
            get {
                val session: SingleSession? = call.sessions.get<SingleSession>()
                if(session != null) {
                    call.sessions.clear<SingleSession>()
                    call.respondRedirect("/")
                } else {
                    call.respondRedirect("/login")
                }
            }
        }

        route("/article") {
            route("/{articleId}") {
                get {
                    val articleId = call.parameters["articleId"]!!.toInt()
                    val article = articleSingle.start(articleId)
                    val session = call.sessions.get<SingleSession>()
                    call.respond(FreeMarkerContent("article.ftl", mapOf("article" to article, "session" to session), "e"))
                }
                post {
                    val articleId = call.parameters["articleId"]!!.toInt()
                    val content  = call.receiveParameters()["text"]!!
                    articleSingle.comment(content, articleId)
                    call.respondRedirect("/article/$articleId")
                }
                route("/comment/{commentId}/delete"){
                    post {
                        val articleId = call.parameters["articleId"]!!.toInt()
                        val commentId = call.parameters["commentId"]!!.toInt()
                        delComment.delete(commentId)
                        call.respondRedirect("/article/$articleId")
                    }
                }
            }
            route("/new") {
                get {
                    val session: SingleSession? = call.sessions.get<SingleSession>()
                    if (session != null) {
                        call.respond(FreeMarkerContent("new.ftl",  mapOf("session" to session)))
                    } else {
                        call.respond(HttpStatusCode.Forbidden)
                    }
                }
                post {
                    val session: SingleSession? = call.sessions.get<SingleSession>()
                    if (session != null) {
                        val parameters = call.receiveParameters()
                        val title = parameters["title"]!!
                        val content = parameters["content"]!!
                        postArticle.post(title, content)
                        call.respondRedirect("/")
                    } else {
                        call.respond(HttpStatusCode.Forbidden)
                    }
                }
            }
            route("/delete/{articleId}") {
                post {
                    val session: SingleSession? = call.sessions.get<SingleSession>()
                    if (session != null) {
                        val articleId = call.parameters["articleId"]!!.toInt()
                        deleteArticle.delete(articleId)
                        call.respondRedirect("/")
                    } else {
                        call.respond(HttpStatusCode.Forbidden)
                    }
                }
            }
        }

        static("/static") {
            static("/css") {
                resources("static/css")
            }
        }
    }
}

fun main() {
    val model = MysqlModel("jdbc:mysql://localhost:3306/CMS", "root", "root")

    val articleList = ArticleListController(model)
    val articleSingle = ArticleSingleController(model)
    val postArticle = PostArticleController(model)
    val deleteArticle = DeleteArticleController(model)
    val delComment = DeleteCommentController(model)
    val user = UserController(model)

    embeddedServer(Netty, 8080){
        cmsApp(
            articleList,
            articleSingle,
            postArticle,
            deleteArticle,
            delComment,
            user
        )
    }.start(true)
}