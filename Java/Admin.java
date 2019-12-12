import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import static java.lang.String.valueOf;

public class Admin {
    public Admin(User u, Database d) {
        adminMenu(u, d);
    }

    public static void adminMenu(User u, Database d) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("ADMINISTRATOR MENU");
        Container frame1ContentPane = frame.getContentPane();
        frame1ContentPane.setLayout(new GridLayout(5, 1));

        {
            JButton editInfo = new JButton();
            editInfo.setText("Add Recruiter");
            editInfo.addActionListener(e1 -> addRecruiterWindow(d));
            frame1ContentPane.add(editInfo);
        }
        {
            JButton editInfo = new JButton();
            editInfo.setText("Add Candidate");
            editInfo.addActionListener(e1 -> addCandidateWindow(d));
            frame1ContentPane.add(editInfo);
        }
        {
            JButton editInfo = new JButton();
            editInfo.setText("Add Antikeimena");
            editInfo.addActionListener(e1 -> addAntikeimenaWindow(d));
            frame1ContentPane.add(editInfo);
        }
        {
            JButton editInfo = new JButton();
            editInfo.setText("Add CompanyFields");
            editInfo.addActionListener(e1 -> companyFieldsWindow(d));
            frame1ContentPane.add(editInfo);
        }
        {
            JButton editInfo = new JButton();
            editInfo.setText("View Database Logs");
            editInfo.addActionListener(e1 -> viewLogsWindow(d));
            frame1ContentPane.add(editInfo);
        }
        frame.setSize(540, 430);
        frame.setVisible(true);
    }

    public static void addAntikeimenaWindow(Database d) {
        var frame = new JFrame("Add Antikeimena");

        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        frame.getContentPane().setLayout(new FlowLayout());

        var titlefield = new JTextField("Title", 12);
        titlefield.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                titlefield.setText("");
            }
        });

        var descfield = new JTextField("Description", 10);
        descfield.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                descfield.setText("");
            }
        });

        var belongfield = new JTextField("Belongs to", 25);
        belongfield.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                belongfield.setText("");
            }
        });


        var btn = new Button("Add Antikeimena");

        btn.addActionListener(e -> d.addAntikeimeno(titlefield.getText(), descfield.getText(), belongfield.getText()));
        frame.pack();

        frame.getContentPane().add(titlefield);
        frame.getContentPane().add(belongfield);
        frame.getContentPane().add(descfield);
        frame.getContentPane().add(btn);

        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    public static void companyFieldsWindow(Database d) {
        var frame = new JFrame("Add Fields per Company");

        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        frame.getContentPane().setLayout(new FlowLayout());

        var company = new JTextField("Company", 12);
        company.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                company.setText("");
            }
        });

        var field = new JTextField("Field", 10);
        field.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                field.setText("");
            }
        });
        var btn = new Button("Add Fields");

        btn.addActionListener(e -> d.addCompanyFields(company.getText(), field.getText()));
        frame.pack();

        frame.getContentPane().add(company);
        frame.getContentPane().add(field);
        frame.getContentPane().add(btn);

        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    public static void addCandidateWindow(Database d) {
        var frame = new JFrame("Add Candidate");

        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        frame.getContentPane().setLayout(new FlowLayout());

        var unamefield = new JTextField("Username", 12);
        unamefield.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                unamefield.setText("");
            }
        });

        var pwfield = new JTextField("Password", 10);
        pwfield.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pwfield.setText("");
            }
        });

        var namefield = new JTextField("Name", 25);
        namefield.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                namefield.setText("");
            }
        });

        var surnamefield = new JTextField("Surname", 35);
        surnamefield.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                surnamefield.setText("");
            }
        });

        var emailfield = new JTextField("Email", 30);
        emailfield.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                emailfield.setText("");
            }
        });
        var bio = new JTextField("Bio", 30);
        bio.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                bio.setText("");
            }
        });
        var sistatikes = new JTextField("Sistatikes", 20);
        sistatikes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                sistatikes.setText("");
            }
        });
        var certificates = new JTextField("Certificates", 20);
        certificates.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                certificates.setText("");
            }
        });

        var btn = new Button("Add Candidate");

        btn.addActionListener(e -> d.addCandidate(unamefield.getText(), pwfield.getText(), namefield.getText(), surnamefield.getText(), emailfield.getText(), bio.getText(), sistatikes.getText(), certificates.getText()));
        frame.pack();

        frame.getContentPane().add(pwfield);
        frame.getContentPane().add(namefield);
        frame.getContentPane().add(surnamefield);
        frame.getContentPane().add(emailfield);
        frame.getContentPane().add(bio);
        frame.getContentPane().add(sistatikes);
        frame.getContentPane().add(certificates);
        frame.getContentPane().add(btn);

        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    public static void addRecruiterWindow(Database d) {
        var frame = new JFrame("Add Recruiter");

        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        frame.getContentPane().setLayout(new FlowLayout());

        var unamefield = new JTextField("Username", 12);
        unamefield.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                unamefield.setText("");
            }
        });

        var pwfield = new JTextField("Password", 10);
        pwfield.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pwfield.setText("");
            }
        });

        var namefield = new JTextField("Name", 25);
        namefield.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                namefield.setText("");
            }
        });

        var surnamefield = new JTextField("Surname", 35);
        surnamefield.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                surnamefield.setText("");
            }
        });

        var emailfield = new JTextField("Email", 30);
        emailfield.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                emailfield.setText("");
            }
        });
        var expyears = new JTextField("Experience Years", 30);
        expyears.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                expyears.setText("");
            }
        });
        var company = new JTextField("Company AFM", 20);
        company.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                company.setText("");
            }
        });

        var btn = new Button("Add Recruiter");

        btn.addActionListener(e -> d.addRecruiter(unamefield.getText(), pwfield.getText(), namefield.getText(), surnamefield.getText(), emailfield.getText(), Integer.parseInt(expyears.getText()), company.getText()));
        frame.pack();

        frame.getContentPane().add(pwfield);
        frame.getContentPane().add(namefield);
        frame.getContentPane().add(surnamefield);
        frame.getContentPane().add(emailfield);
        frame.getContentPane().add(expyears);
        frame.getContentPane().add(company);
        frame.getContentPane().add(btn);

        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    public static void viewLogsWindow(Database d) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setTitle("LOGS MENU");
        Container frame1ContentPane = frame.getContentPane();
        frame1ContentPane.setLayout(new GridLayout(2, 1));

        {
            JButton editInfo = new JButton();
            editInfo.setText("View Logs Per User");
            editInfo.addActionListener(e1 -> viewLogsPerUserWindow(d));
            frame1ContentPane.add(editInfo);
        }
        {
            JButton editInfo = new JButton();
            editInfo.setText("View Logs Per Table");
            editInfo.addActionListener(e1 -> viewLogsPerTableWindow(d));
            frame1ContentPane.add(editInfo);
        }
        frame.setSize(340, 430);
        frame.setVisible(true);
    }

    public static void viewLogsPerUserWindow(Database d) {
        var frame = new JFrame("Select parametres");

        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        ArrayList<String> temp1 = d.getAllUsers();
        JList b = new JList(temp1.toArray());
        frame.getContentPane().setLayout(new FlowLayout());
        var startdate = new JTextField("Start Date", 12);
        startdate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startdate.setText("");
            }
        });
        var enddate = new JTextField("End Date", 10);
        enddate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                enddate.setText("");
            }
        });

        JButton btn = new JButton();
        btn.setText("View Logs");

        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String user = (String) b.getSelectedValue();
                btn.addActionListener(e1 -> viewLogsWindow(d,d.getHistoryByUser(user,startdate.getText(),enddate.getText())));
            }
        };

        b.addMouseListener(mouseListener);
        frame.getContentPane().add(b);
        frame.getContentPane().add(startdate);
        frame.getContentPane().add(enddate);
        frame.getContentPane().add(btn);

        frame.setSize(800, 600);
        frame.setVisible(true);
    }
    public static void viewLogsPerTableWindow(Database d) {
        var frame = new JFrame("Select parametres");

        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        String Tables[]={"candidate","recruiter","user","etaireia","job"};
        JList b = new JList(Tables);
        frame.getContentPane().setLayout(new FlowLayout());
        var startdate = new JTextField("Start Date", 12);
        startdate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                startdate.setText("");
            }
        });
        var enddate = new JTextField("End Date", 10);
        enddate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                enddate.setText("");
            }
        });

        JButton btn = new JButton();
        btn.setText("View Logs");

        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String table = (String) b.getSelectedValue();
                btn.addActionListener(e1 -> viewLogsWindow(d,d.getHistoryByTable(table,startdate.getText(),enddate.getText())));
            }
        };

        b.addMouseListener(mouseListener);
        frame.getContentPane().add(b);
        frame.getContentPane().add(startdate);
        frame.getContentPane().add(enddate);
        frame.getContentPane().add(btn);

        frame.setSize(800, 600);
        frame.setVisible(true);
    }


    public static void viewLogsWindow(Database d,ArrayList<History> h) {
        var frame = new JFrame("Logs");
        JTable logs = new JTable();
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));


        String colLog[] = {"ID","Username","Date","Success","Type of operation", "Table affected"};
        DefaultTableModel logTable = new DefaultTableModel(colLog,0);

        for(int i=0;i<h.size();i++){
            String values[] ={String.valueOf(h.get(i).history_id),h.get(i).username, h.get(i).date, h.get(i).success, h.get(i).type, h.get(i).tablename} ;
            logTable.addRow(values);
        }
        logs.setModel(logTable);

        logs.setBounds(30,40,200,300);
        JScrollPane sp1 = new JScrollPane(logs);
        mainPanel.add(sp1);


        frame.add(mainPanel);

        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}

