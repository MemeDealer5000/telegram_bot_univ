import java.util.ArrayList;
import java.util.HashMap;

public class Reminder {
    private static  HashMap<Long, ArrayList<Note>> userAndNotes = new HashMap<>();

    public String addNote(Note note){
        var chat_id = note.getChatId();
        if (!userAndNotes.containsKey(chat_id)) {
            userAndNotes.put(chat_id, new ArrayList<Note>());
        }
        var list = userAndNotes.get(chat_id);
        list.add(note);
        return "Успешно сохранено: " + note.getNoteMessage();
    }

    public String showNotes(Long chat_id){
        var notes = userAndNotes.get(chat_id);
        var notesText = new StringBuilder();
        for (Note raw_note : notes){
            notesText.append(raw_note.getNoteMessage() + '\n');
        }
        return  notesText.toString();
    }

    public String deleteNote(Note note){
        var chat_id = note.getChatId();
        if (userAndNotes.containsKey(chat_id)){
            var notes = userAndNotes.get(chat_id);
            notes.remove(note);
            return  "Successfully deleted note";
        }
        return  "Note not found";
    }
}
