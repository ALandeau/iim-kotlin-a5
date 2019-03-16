package fr.iim.iwm.a5.alex.landeau.kotlin.comment.admin

import fr.iim.iwm.a5.alex.landeau.kotlin.model.IMysqlModel

class DeleteCommentController(val model: IMysqlModel) : IDeleteCommentController {
    override fun delete(commentId: Int) {
        model.deleteComment(commentId)
    }
}