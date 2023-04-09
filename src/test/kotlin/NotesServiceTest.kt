import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NotesServiceTest {
    @Before
    fun clearBeforeTest() {
        NotesService.clear()
    }

    @Test
    fun add() {
        val notesComment = mutableListOf(NoteComments(5, "комментарий"))
        val note = Notes(2, "Заметка", "Заметка заметканная", notesComment)

        val result = NotesService.add(note)
        assertEquals(result, 1)
    }

    @Test
    fun createCommentSuccessful() {
        val note = Notes(2, "заметка", "заметка заметканная")
        val comment = NoteComments(5, "комментарий")
        NotesService.add(note)
        val result = NotesService.createComment(1, comment)
        assertEquals(result, 1)
    }

    @Test
    fun createCommentUnsuccessful() {
        val note = Notes(2, "заметка", "заметка заметканная")
        val comment = NoteComments(5, "комментарий")
        NotesService.add(note)
        val result = NotesService.createComment(205, comment)
        assertEquals(result, -1)
    }

    @Test
    fun deleteSuccessful() {
        val note = Notes(2, "заметка", "заметка заметканная")
        NotesService.add(note)
        val result = NotesService.delete(note.id)
        assertEquals(result, true)
    }

    @Test
    fun deleteUnsuccessful() {
        val note = Notes(2, "заметка", "заметка заметканная")
        val result = NotesService.delete(note.id)
        assertEquals(result, false)
    }

    @Test
    fun deleteCommentSuccessful() {
        val note = Notes(2, "заметка", "заметка заметканная")
        val comment = NoteComments(5, "комментарий")
        NotesService.add(note)
        NotesService.createComment(1, comment)

        val result = NotesService.deleteComment(comment.id)
        assertEquals(result, true)
    }

    @Test
    fun deleteCommentUnsuccessful() {
        val note = Notes(2, "заметка", "заметка заметканная")
        val comment = NoteComments(5, "комментарий")
        NotesService.add(note)
        NotesService.createComment(1, comment)

        val result = NotesService.deleteComment(5)
        assertEquals(result, false)
    }

    @Test(expected = CommentAlreadyDeletedException::class)
    fun shouldThrowDeleteComment() {
        val note = Notes(2, "заметка", "заметка заметканная")
        val comment = NoteComments(5, "комментарий")
        NotesService.add(note)
        NotesService.createComment(1, comment)
        NotesService.deleteComment(1)
        NotesService.deleteComment(1)
    }

    @Test
    fun edit() {
        val note = Notes(2, "заметка", "заметка заметканная")
        NotesService.add(note)
        val result = NotesService.editNote(note, "текст", "заголовок")
        assertEquals(result, true)
    }

    @Test
    fun editComment() {
        val note = Notes(2, "заметка", "заметка заметканная")
        val comment = NoteComments(5, "комментарий")
        NotesService.add(note)
        NotesService.createComment(1, comment)

        val result = NotesService.editComment(comment, "сообщение")
        assertEquals(result, true)
    }
    @Test
    fun noteGet() {
        val note = Notes(2, "заметка", "заметка заметканная")
        NotesService.add(note)

        val result = NotesService.noteGetById(1)
        assertEquals(result, note)
    }
    @Test
    fun getCommentsOfNoteSuccessful() {
        val note = Notes(2, "заметка", "заметка заметканная")
        val comment = NoteComments (5, "комментарий")
        NotesService.add(note)
        NotesService.createComment(1, comment)

        val result = NotesService.getCommentsOfNote(1)
        assertEquals(result, note.notesComent)
    }
    @Test
    fun restoreCommentSuccessful() {
        val note = Notes(2, "заметка", "заметка заметканная")
        val comment = NoteComments (2, "заметка")
        NotesService.add(note)
        NotesService.createComment(1, comment)
        NotesService.deleteComment(comment.id)

        val result = NotesService.restoreComment(comment.id)
        assertEquals(result, true)
    }
}