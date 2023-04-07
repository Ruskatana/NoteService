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
}