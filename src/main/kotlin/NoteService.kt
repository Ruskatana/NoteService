data class NoteComments(
    var id: Int,
    var message: String
)

data class Notes(
    var id: Int,
    var title: String,
    var text: String,
    var notesComent: MutableList<NoteComments> = mutableListOf(),
    var notesDeleteComments: MutableMap<Int, NoteComments> = mutableMapOf()
)

class CommentAlreadyDeletedException(message: String) : RuntimeException(message)
class CommentIsNotDeleted(message: String) : RuntimeException(message)

object NotesService {
    private var noteIdNext = 0
    private var notes = mutableListOf<Notes>()
    private var commentIdNext = 0


    fun add(note: Notes): Int {
        notes.add(note)
        note.id = ++noteIdNext
        return notes.last().id
    }
    fun printAllNotes() {
        for (notee in notes) {
            println(notes)
        }
    }

    fun createComment(noteId: Int, noteComment: NoteComments): Int {
        for (index in notes) {
            if (index.id == noteId) {
                index.notesComent += noteComment
                noteComment.id = ++commentIdNext
                return index.notesComent.last().id
            }
        }
        return -1
    }

    fun delete(noteId: Int): Boolean {
        for (index in notes) {
            if (index.id == noteId) {
                notes.remove(index)
                return true
            }
        }
        return false

    }

    fun deleteComment(commentId: Int): Boolean {
        for (i in notes) {
            for (index in i.notesComent) {
                if (index.id == commentId) {
                    i.notesDeleteComments[index.id] = i.notesComent[i.id - 1]
                    i.notesComent.remove(index)
                    return true
                }
            }
            for (index in i.notesDeleteComments) {
                if (index.key == commentId) {
                    throw CommentAlreadyDeletedException("Comment has already deleted")
                }
            }
        }
        return false
    }

    fun editNote(note: Notes, newText: String, newTitle: String): Boolean {
        for (index in notes) {
            if (index.id == note.id) {
                notes[index.id - 1] = note.copy(text = newText, title = newTitle)
                return true
            }
        }
        return false
    }

    fun editComment(noteComment: NoteComments, newMessage: String): Boolean {
        for (i in notes) {
            for (index in i.notesComent) {
                if (index.id == noteComment.id) {
                    i.notesComent[index.id - 1] = noteComment.copy(message = newMessage)
                    return true
                }
            }
            for (index in i.notesDeleteComments) {
                if (index.key == noteComment.id) {
                    throw CommentAlreadyDeletedException("Comment is deleted")
                }
            }
        }
        return false
    }

    fun noteGetById(noteId: Int): Notes? {
        for (index in notes) {
            if (index.id == noteId) {
                return notes[index.id - 1]
            }
        }
        return null
    }

    fun getCommentsOfNote(noteId: Int): List<NoteComments>? {
        for (index in notes) {
            if (index.id == noteId) {
                return index.notesComent
            }
        }
        return null
    }

    fun restoreComment(commentId: Int): Boolean {
        for (i in notes) {
            for (index in i.notesDeleteComments) {
                if (commentId == index.key) {
                    i.notesComent += i.notesDeleteComments.getValue(commentId)
                    i.notesDeleteComments.remove(commentId)
                    return true
                }
            }
            for (index in i.notesComent) {
                if (index.id == commentId) {
                    throw CommentIsNotDeleted("Comment is not deleted")
                }
            }
        }
        return false
    }

    fun clear() {
        notes = mutableListOf<Notes>()
        noteIdNext = 0
        commentIdNext = 0
    }
}

fun main() {
    val notesComment = mutableListOf(NoteComments(5, "комментарий"))
    val note1 = Notes(2, "Заметка", "Заметка заметканная", notesComment)
    NotesService.add(note1)
    NotesService.printAllNotes()

}