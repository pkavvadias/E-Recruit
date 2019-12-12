import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static java.lang.String.valueOf;

public class Recruiter {
    public Recruiter(User u, Etaireia e, Database d) {
        recruiterMenu(u, e, d);
    }

    public static void recruiterMenu(User u, Etaireia et, Database d) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("RECRUITER MANAGEMENT");
        Container frame1ContentPane = frame.getContentPane();
        frame1ContentPane.setLayout(new GridLayout(6, 1));

        {
            JButton editInfo = new JButton();
            editInfo.setText("Edit User Info");
            editInfo.addActionListener(e1 -> RecruiterEditWindow(u, d));
            frame1ContentPane.add(editInfo);
        }

        {
            JButton editCompInfo = new JButton();
            editCompInfo.setText("Edit Company Info");
            editCompInfo.addActionListener(e1 -> CompanyEditWindow(et, d));
            frame1ContentPane.add(editCompInfo);
        }

        {
            JButton addPosition = new JButton();
            addPosition.setText("Add Job");
            addPosition.addActionListener(e1 -> addJobWindow(d, u));
            frame1ContentPane.add(addPosition);
        }

        {
            JButton editJob = new JButton();
            editJob.setText("Edit Job");
            editJob.addActionListener(e1 -> ShowJobWindow(d, u));
            frame1ContentPane.add(editJob);
        }

        {
            JButton ViewJob = new JButton();
            ViewJob.setText("View Company Jobs");
            ViewJob.addActionListener(e1 -> GetCompanyJobsWindow(d, u, et));
            frame1ContentPane.add(ViewJob);
        }

        frame.setSize(540, 430);
        frame.setVisible(true);
    }

    public static void RecruiterEditWindow(User u, Database d) {
        var frame = new JFrame("Edit user info");

        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        frame.getContentPane().setLayout(new FlowLayout());
//
        var unamefield = new JTextField(u.username, 12);
        unamefield.setEditable(false);
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

    public static void CompanyEditWindow(Etaireia et, Database d) {
        var frame = new JFrame("Edit company info");

        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        frame.getContentPane().setLayout(new FlowLayout());
        var afmfield = new JTextField(et.AFM, 10);
        afmfield.setEditable(false);

        var doyfield = new JTextField(et.DOY, 25);
        doyfield.setEditable(false);

        var compnamefield = new JTextField(et.name, 25);
        compnamefield.setEditable(false);

        var telfield = new JTextField(Long.toString(et.tel), 20);
        telfield.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                telfield.setText("");
            }
        });

        var streetfield = new JTextField(et.street, 25);
        streetfield.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                streetfield.setText("");
            }
        });

        var numfield = new JTextField(Integer.toString(et.num), 5);
        numfield.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                numfield.setText("");
            }
        });

        var cityfield = new JTextField(et.city, 20);
        cityfield.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cityfield.setText("");
            }
        });

        var countryfield = new JTextField(et.country, 20);
        countryfield.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                countryfield.setText("");
            }
        });

        var btn = new Button("Update company info");
        btn.addActionListener(e -> d.updateCompany(afmfield.getText(), doyfield.getText(), compnamefield.getText(), Long.parseLong(telfield.getText()), Integer.parseInt(numfield.getText()), cityfield.getText(), countryfield.getText(), streetfield.getText()));
        frame.getContentPane().add(afmfield);
        frame.getContentPane().add(doyfield);
        frame.getContentPane().add(compnamefield);
        frame.getContentPane().add(telfield);
        frame.getContentPane().add(streetfield);
        frame.getContentPane().add(numfield);
        frame.getContentPane().add(cityfield);
        frame.getContentPane().add(countryfield);
        frame.getContentPane().add(btn);

        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    public static void addJobWindow(Database d, User u) {
        var frame = new JFrame("Add position info");

        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        frame.getContentPane().setLayout(new FlowLayout());

        var start_date = new JTextField("Start Date", 10);
        start_date.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                start_date.setText("");
            }
        });

        var salary = new JTextField("Salary", 10);
        salary.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                salary.setText("");
            }
        });

        var position = new JTextField("Position", 20);
        position.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                position.setText("");
            }
        });

        var edra = new JTextField("Edra", 10);
        edra.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                edra.setText("");
            }
        });

        var recruiter = new JTextField(u.username, 10);
        recruiter.setEditable(false);

        var submission_date = new JTextField("Submission Date", 10);
        submission_date.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                submission_date.setText("");
            }
        });

        var antikeimeno = new JTextField("Antikeimena-Use comma as seperator", 20);
        submission_date.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                start_date.setText("");
            }
        });

        var btn = new Button("Insert Job");
        btn.addActionListener(e -> d.addJob(start_date.getText(), Float.parseFloat(salary.getText()), position.getText(), edra.getText(), recruiter.getText(), submission_date.getText(), antikeimeno.getText()));

        frame.getContentPane().add(start_date);
        frame.getContentPane().add(salary);
        frame.getContentPane().add(position);
        frame.getContentPane().add(edra);
        frame.getContentPane().add(recruiter);
        frame.getContentPane().add(submission_date);
        frame.getContentPane().add(antikeimeno);
        frame.getContentPane().add(btn);

        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    public static void ShowJobWindow(Database d, User u) {
        var frame = new JFrame("Select job id");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        frame.getContentPane().setLayout(new FlowLayout());
        ArrayList<Integer> temp1 = d.getJobIds(u);
        JList b = new JList(temp1.toArray());
        var btn = new JButton("Update Job");
        JButton btn1 = new JButton("Edit Interviews for selected job");
        var btn2 = new JButton(("Show ranking tables"));
        var start_date = new JTextField("Start Date", 10);
        start_date.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                start_date.setText("");
            }
        });

        var salary = new JTextField("Salary", 10);
        salary.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                salary.setText("");
            }
        });

        var position = new JTextField("Position", 20);
        position.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                position.setText("");
            }
        });

        var edra = new JTextField("Edra", 10);
        edra.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                edra.setText("");
            }
        });

        var recruiter = new JTextField(u.username, 10);
        recruiter.setEditable(false);

        var announce_date = new JTextField("Announce Date", 10);
        announce_date.setEditable(false);

        var state = new JTextField("State", 10);
        state.setEditable(false);

        var submission_date = new JTextField("Submission Date", 10);
        submission_date.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                submission_date.setText("");
            }
        });

        var n_applies = new JTextField("Count of Applications", 20);
        state.setEditable(false);

        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //super.mouseClicked(e);
                Integer ids = (Integer) b.getSelectedValue();
                Job j = d.JobFinder(ids);
                start_date.setText(j.start_date);
                salary.setText(valueOf(j.salary));
                position.setText(j.position);
                edra.setText(j.edra);
                recruiter.setText(u.username);
                announce_date.setText(j.announce_date);
                submission_date.setText(j.submission_date);
                n_applies.setText(d.n_apps(ids).toString());
                //TODO check IFFFFFF DATE CHECK WORKSSSSSS
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

                DateCheck.Check(submission_date.getText(), formatter.format(date), state, btn1);
                btn.addActionListener(l -> d.updateJob(ids, start_date.getText(), Float.parseFloat(salary.getText()), position.getText(), edra.getText(), submission_date.getText()));
                btn.addActionListener(a -> DateCheck.Check(submission_date.getText(), announce_date.getText(), state, btn1)); //action listener to update the state jextfield
                btn1.addActionListener(p -> InterviewEditWindow(d, u, ids));
                btn2.addActionListener(h->rankingWindow(ids,d));

            }
        };


        b.addMouseListener(mouseListener);
        frame.getContentPane().add(b);
        frame.getContentPane().add(start_date);
        frame.getContentPane().add(salary);
        frame.getContentPane().add(position);
        frame.getContentPane().add(edra);
        frame.getContentPane().add(recruiter);
        frame.getContentPane().add(announce_date);
        frame.getContentPane().add(submission_date);
        frame.getContentPane().add(state);
        frame.getContentPane().add(n_applies);
        frame.getContentPane().add(btn);
        frame.getContentPane().add(btn1);
        frame.getContentPane().add(btn2);


        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    public static void InterviewEditWindow(Database d, User u, Integer job_id) {
        final Integer[] interid = {0};
        var frame = new JFrame("Edit Interview info");

        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        frame.getContentPane().setLayout(new FlowLayout());
        ArrayList<Integer> temp1 = d.getInterviewIds(u, job_id);
        var btn = new JButton("Update Interview");
        JList b = new JList(temp1.toArray());
        frame.getContentPane().add(b);

        var recruiter = new JTextField("Recruiter", 10);
        recruiter.setEditable(false);

        var candidate = new JTextField("Candidate", 10);
        candidate.setEditable(false);

        var jobid = new JTextField("Job id", 20);
        jobid.setEditable(false);

        var date = new JTextField("Date", 10);
        date.setEditable(false);

        var duration_hours = new JTextField("Duration hours", 10);
        duration_hours.setEditable(false);

        var persscore = new JTextField("Personality Score", 10);
        persscore.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                persscore.setText("");
            }
        });

        var edscore = new JTextField("Education Score", 10);
        edscore.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                edscore.setText("");
            }
        });

        var workexpscore = new JTextField("Work Experience Score", 20);
        workexpscore.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                workexpscore.setText("");
            }
        });

        var comment = new JTextField("Comments", 40);
        comment.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                comment.setText("");
            }
        });

        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent tr) {
                Integer intid = (Integer) b.getSelectedValue();
                interid[0] =intid;
                Interview i = d.InterviewFinder(intid, job_id, u);
                recruiter.setText(i.recruiter);
                candidate.setText(valueOf(i.candidate));
                jobid.setText(Integer.toString(i.job_id));
                date.setText(i.date);
                duration_hours.setText(Integer.toString(i.duration_hours));
                persscore.setText(Integer.toString(i.persscore));
                edscore.setText(Integer.toString(i.edscore));
                workexpscore.setText(Integer.toString(i.workexpscore));
                comment.setText(i.comments);
            }
        };

        btn.addActionListener(ppp -> d.updateInterview(interid[0], job_id, Integer.parseInt(persscore.getText()), Integer.parseInt(edscore.getText()), Integer.parseInt(workexpscore.getText()), comment.getText()));


        b.addMouseListener(mouseListener);
                frame.getContentPane().add(recruiter);
                frame.getContentPane().add(candidate);
                frame.getContentPane().add(jobid);
                frame.getContentPane().add(date);
                frame.getContentPane().add(duration_hours);
                frame.getContentPane().add(persscore);
                frame.getContentPane().add(edscore);
                frame.getContentPane().add(workexpscore);
                frame.getContentPane().add(comment);
                frame.getContentPane().add(btn);

                frame.setSize(800, 600);
                frame.setVisible(true);
            }

    public static void rankingWindow(Integer job_id,Database d) {
        var frame = new JFrame("Rankings");
        var successTable = new ArrayList<Success>();
        var failTable = new ArrayList<Fail>();
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        JTable successCands = new JTable();
        JTable failCands = new JTable();
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
        String colSucces[] = {"Candidate","Personality Score","Education Score","Work Experience Score","Total", "Number of Interviews"};
        String colFail[] = {"Candidate", "Reason"};

        d.showRanking(job_id,successTable,failTable);
        DefaultTableModel sTable = new DefaultTableModel(colSucces,0);
        for(int i=0;i<successTable.size();i++){
            String values[] ={successTable.get(i).candid, String.valueOf(successTable.get(i).per), String.valueOf(successTable.get(i).edu), String.valueOf(successTable.get(i).workex), String.valueOf(successTable.get(i).total), String.valueOf(successTable.get(i).n_ints)} ;
            sTable.addRow(values);
        }
        successCands.setModel(sTable);

        DefaultTableModel fTable = new DefaultTableModel(colFail,0);

        for(int i=0;i<failTable.size();i++){
            String values[] ={failTable.get(i).candid,failTable.get(i).reas} ;
            fTable.addRow(values);
        }
        failCands.setModel(fTable);
        successCands.setBounds(30,40,200,300);
        failCands.setBounds(30,40,200,300);
        JScrollPane sp1 = new JScrollPane(successCands);
        JScrollPane sp2 = new JScrollPane(failCands);

        mainPanel.add(sp1);
        mainPanel.add(sp2);
        frame.add(mainPanel);

        frame.setSize(800, 600);
        frame.setVisible(true);
    }


    public static void GetCompanyJobsWindow(Database d, User u, Etaireia et) {
        var frame = new JFrame("Company Jobs");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        var list = d.getJobs(u,et);
        JTable jobs = new JTable();
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
        String colJobs[] = {"ID","Start Date","Salary","Position","Edra", "Recruiter", "Announce Date", "Submission Date"};
        DefaultTableModel jTable = new DefaultTableModel(colJobs,0);

        for(int i=0;i<list.size();i++){
            String values[] ={String.valueOf(list.get(i).id), list.get(i).start_date, String.valueOf(list.get(i).salary), list.get(i).position, list.get(i).edra, list.get(i).recruiter, list.get(i).announce_date, list.get(i).submission_date} ;
            jTable.addRow(values);
        }
        jobs.setModel(jTable);
        JScrollPane sp1 = new JScrollPane(jobs);
        mainPanel.add(sp1);
        frame.add(mainPanel);

        frame.setSize(800, 600);
        frame.setVisible(true);
    }

}