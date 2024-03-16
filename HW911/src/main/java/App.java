import model.NoteBook;
import service.NoteBookServiseImpl;

public class App {
    public static void main(String[] args) {
        NoteBookServiseImpl noteBookServise = new NoteBookServiseImpl();
        noteBookServise.start();
    }
}
