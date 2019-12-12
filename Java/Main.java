import javax.swing.*;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try {
            Database.connect();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());//Sets ui to system
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        new Login();
    }
}