import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login {
    public Login(){
        Database d=new Database();
        var frame = new JFrame("Login");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().setLayout(new FlowLayout());
        var usernameInput = new JTextField("username",12);
        usernameInput.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                usernameInput.setText("");
            }
        });

        var passwordInput = new JPasswordField("password",10);
        passwordInput.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                passwordInput.setText("");
            }
        });

        var info = new JTextField("Insert your credentials", 30);
        info.setVisible(true);

        var btn = new Button("Login");

        btn.addActionListener(e -> {
            var username = usernameInput.getText();
            var password = passwordInput.getText();

            var result = d.authenticate(username, password);

            if (result == null) {
                info.setText("Invalid credentials");
                info.setVisible(true);
                return;
            }
            User u =new User();
            Database.loadUser(username,u);

            switch (result){
                case "administrator":
                    new Admin(u,d);
                    break;
                case "recruiter":
                    new Recruiter(u,d.companyFinder(Database.recruiter_Company_Finder(username)),d);
                    break;
                case "candidate":
                    new Candidate(u,d);
                    break;
            }

            frame.setVisible(false);
            frame.dispose();
        });

        frame.getContentPane().add(usernameInput);
        frame.getContentPane().add(passwordInput);
        frame.getContentPane().add(btn);
        frame.getContentPane().add(info);
        frame.pack();

        frame.setVisible(true);
        frame.setSize(400, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}