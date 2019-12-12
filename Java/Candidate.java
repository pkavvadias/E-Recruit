import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Candidate {
    public Candidate(User u, Database d) {
        candidateMenu(u, d);
    }

    public static void candidateMenu(User u, Database d) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Candidate MANAGEMENT");
        Container frame1ContentPane = frame.getContentPane();
        frame1ContentPane.setLayout(new GridLayout(4, 1));

        {
            JButton editInfo = new JButton();
            editInfo.setText("Edit User Info");
            editInfo.addActionListener(e1 -> CandidateEditWindow(u, d));
            frame1ContentPane.add(editInfo);
        }

        {
            JButton insertAppl = new JButton();
            insertAppl.setText("Insert Application");
            insertAppl.addActionListener(e1 -> InsertApplicationWindow(u, d));
            frame1ContentPane.add(insertAppl);
        }

        {
            JButton getAppl = new JButton();
            getAppl.setText("Get Application");
            getAppl.addActionListener(e1 -> GetApplicationWindow(u, d));
            frame1ContentPane.add(getAppl);
        }

        {
            JButton deleteAppl = new JButton();
            deleteAppl.setText("Delete Application");
            deleteAppl.addActionListener(e1 -> DeleteApplicationWindow(u, d));
            frame1ContentPane.add(deleteAppl);
        }

        frame.setSize(540, 430);
        frame.setVisible(true);
    }

    public static void CandidateEditWindow(User u, Database d) {
        var frame = new JFrame("Edit user info");

        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        frame.getContentPane().setLayout(new FlowLayout());
//
        var unamefield = new JTextField(u.username, 12);
        unamefield.setEditable(false);
        JPanel user = new JPanel();
//        JPanel company = new JPanel();
        var pwfield = new JPasswordField(u.password, 10);
        pwfield.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pwfield.setText("");
            }
        });

        var namefield = new JTextField(u.name, 25);
        namefield.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                namefield.setText("");
            }
        });

        var surnamefield = new JTextField(u.surname, 35);
        surnamefield.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                surnamefield.setText("");
            }
        });

        var datefield = new JTextField(u.reg_date, 10);
        datefield.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                datefield.setText("");
            }
        });

        var emailfield = new JTextField(u.email, 30);
        emailfield.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                emailfield.setText("");
            }
        });

        var btn = new Button("Update user info");

        btn.addActionListener(e -> d.updateProfile(u.username, pwfield.getText(), namefield.getText(), surnamefield.getText(), datefield.getText(), emailfield.getText()));
        frame.pack();

        frame.getContentPane().add(pwfield);
        frame.getContentPane().add(namefield);
        frame.getContentPane().add(surnamefield);
        frame.getContentPane().add(datefield);
        frame.getContentPane().add(emailfield);
        frame.getContentPane().add(btn);

        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }




    public static void InsertApplicationWindow(User u, Database d) {
        var frame = new JFrame("Insert Application");

        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        frame.getContentPane().setLayout(new FlowLayout());

        ArrayList<Integer> temp1 = d.getJobsforApplic(u);

        var btn = new JButton("Insert Application");
        JList b = new JList(temp1.toArray());
        frame.getContentPane().add(b);

        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Integer job_id = (Integer) b.getSelectedValue();
                btn.addActionListener(l -> d.insertAppl(u, job_id));
            }
        };
        b.addMouseListener(mouseListener);
        frame.getContentPane().add(btn);

        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }


    public static void GetApplicationWindow(User u, Database d) {
        var frame = new JFrame("Get Application");
        var successTable = new ArrayList<Success>();
        var failTable = new ArrayList<Fail>();//never used

        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        frame.getContentPane().setLayout(new FlowLayout());

        var state = new JTextField("State", 40);
        state.setEditable(false);

        ArrayList<Integer> temp1 = d.getApplicationsperUser(u.username);

        JList b = new JList(temp1.toArray());
        frame.getContentPane().add(b);

        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Integer job_id = (Integer) b.getSelectedValue();
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                failTable.clear();
                successTable.clear();
                d.showRanking(job_id, successTable, failTable);
                DateCheck.Check(d.JobFinder(job_id).submission_date, formatter.format(date), state);
                for(int i=0; i<successTable.size();i++)
                {
                    if(successTable.get(i).candid.equals(u.username))
                    {
                        state.setText("Your ranking is "+ (i+1) +" from "+ (successTable.size()+failTable.size()) +"");
                    }
                }

            }
        };
        b.addMouseListener(mouseListener);
        frame.getContentPane().add(state);


        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }


    public static void DeleteApplicationWindow(User u, Database d) {
        var frame = new JFrame("Delete Application");
        final Integer[] jobid = {0};
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.getContentPane().setLayout(new FlowLayout());
        var btn = new JButton("Delete Application");
        var state = new JTextField("State", 40);
        state.setEditable(false);

        ArrayList<Integer> temp1 = d.getApplicationsperUser(u.username);

        JList b = new JList(temp1.toArray());
        frame.getContentPane().add(b);

        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Integer job_id = (Integer) b.getSelectedValue();
                jobid[0] =job_id;
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                if(!DateCheck.CheckBoolean(d.JobFinder(job_id).submission_date, formatter.format(date), state))
                {
                    btn.setEnabled(false);
                }
                else{
                    btn.setEnabled(true);
                }
            }
        };

        btn.addActionListener(e->d.deleteApplication(jobid[0],u.username));
        b.addMouseListener(mouseListener);
        frame.getContentPane().add(state);
        frame.getContentPane().add(btn);


        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }


}
