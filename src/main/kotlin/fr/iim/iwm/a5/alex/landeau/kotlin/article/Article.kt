package fr.iim.iwm.a5.alex.landeau.kotlin.article

import fr.iim.iwm.a5.alex.landeau.kotlin.comment.Comment

data class Article(val id: Int, val title: String, val text: String? = null, var comments: List<Comment>)