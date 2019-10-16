import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Note {

    private String noteMessage;
    private long chatId;
    private Date noteDate;

    public Note(String date,String message, long chat_id) {
        this.noteMessage = message;
        this.chatId = chat_id;
        try {
            this.noteDate = convertStringToDate(date);
        } catch (ParseException e){
            this.noteDate = new Date();
        }
    }

    public long getChatId() {
        return chatId;
    }
    public String getNoteMessage() {
        return noteMessage;
    }
    public Date getNoteDate() {
        return noteDate;
    }

    private Date convertStringToDate(String date) throws ParseException {
        var format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return format.parse(date);
    }
}
