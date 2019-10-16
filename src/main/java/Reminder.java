
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.TimerTask;

public class Reminder extends TimerTask {
    private static  HashMap<Long, ArrayList<Note>> userAndNotes = new HashMap<>();

    public Reminder(Bot relatedBot){
        this.relatedBot = relatedBot;
    }

    private Bot relatedBot;
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
            notesText.append(raw_note.getNoteDate().toString()+ ' '+raw_note.getNoteMessage() + '\n');
        }
        return  notesText.toString();
    }

    public HashMap<Long, ArrayList<Note>> getUserAndNotes(){
        return  userAndNotes;
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

    public SendMessage checkNotificationToSend(){
        var chat_ids = userAndNotes.keySet();
        SendMessage msg = new SendMessage();
        msg.setChatId("-1");
        for(Long id : chat_ids){
            var listOfNotifications = userAndNotes.get(id);
            for (Note note : listOfNotifications){
                if (Math.abs(note.getNoteDate().getTime() - new Date().getTime())<100){
                    msg.setChatId(id);
                    msg.setText( "Ваше событие: " + note.getNoteMessage()+ " началось!");
                }
            }
        }
        return  msg;
    }

    @Override
    public void run() {
        var result = checkNotificationToSend();
        if (!result.equals("")){
            relatedBot.sendNotificaton(result);
        }
    }
}
