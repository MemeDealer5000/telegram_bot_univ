public class Note {

    private String noteMessage;
    private long chatId;

    public Note(String message, long chat_id){
        this.noteMessage = message;
        this.chatId = chat_id;
    }


    public long getChatId() {
        return chatId;
    }
    public String getNoteMessage() {
        return noteMessage;
    }

}
