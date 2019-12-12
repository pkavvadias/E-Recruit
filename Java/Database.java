import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

/*
Function to connect to database
 */
public class Database {
    public static Connection connection;

    public static void connect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/erecruit?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "12345");//to fix timezone warning
    }
/*
Function for the authentication process
 */
    public static String authenticate(String username, String password) {
        if (username.equals("admin")) {
            return "administrator";
        }
        Statement user_statement = null;
        boolean userfound = false;
        try {
            user_statement = connection.createStatement();
            String command;
            command = "SELECT * FROM `user` WHERE username='" + username + "' AND `password`='" + password + "'";
            ResultSet results = user_statement.executeQuery(command);
            while (results.next()) {
                userfound = true;
            }
            results.close();
            user_statement.close();
        } catch (Exception ex) {
            userfound = false;
            ex.printStackTrace();
        } finally {
            try {
                if (user_statement == null) {
                    user_statement.close();
                }
            } catch (Exception ex2) {
            }//We want to do nothing
        }
        if (userfound) {
            return positionOf(username);
        }
        return null;//Required else compile error
    }
/*
Get the role of the user
 */
    private static String positionOf(String username) {
        if (RecruiterFinder(username)) {
            return "recruiter";
        }

        if (CandidateFinder(username)) {
            return "candidate";
        }

        return null; // Required for compile,should never go here
    }
/*
Search candidate table by username
 */
    public static boolean CandidateFinder(String username) {
        Statement user_statement = null;
        boolean userfound = false;
        try {
            user_statement = connection.createStatement();
            String command;
            command = "SELECT * FROM candidate WHERE username='" + username + "'";
            ResultSet results = user_statement.executeQuery(command);
            while (results.next()) {
                userfound = true;
            }
            results.close();
            user_statement.close();
        } catch (Exception ex) {
            userfound = false;
            ex.printStackTrace();
        } finally {
            try {
                if (user_statement == null) {
                    user_statement.close();
                }
            } catch (Exception ex2) {
            }//We want to do nothing
        }
        return userfound;
    }
/*
Search recruiter table by username
 */
    public static boolean RecruiterFinder(String username) {
        Statement user_statement = null;
        boolean userfound = false;
        try {
            user_statement = connection.createStatement();
            String command;
            command = "SELECT * FROM recruiter WHERE username='" + username + "'";
            ResultSet results = user_statement.executeQuery(command);
            while (results.next()) {
                userfound = true;
            }
            results.close();
            user_statement.close();
        } catch (Exception ex) {
            userfound = false;
            ex.printStackTrace();
        } finally {
            try {
                if (user_statement == null) {
                    user_statement.close();
                }
            } catch (Exception ex2) {
            }//We want to do nothing
        }
        return userfound;
    }
/*
Update user profile
 */
    public static void updateProfile(String uname, String pw, String name, String surname, String date, String mail) {
        Statement update_statement = null;
        try {
            update_statement = connection.createStatement();
            String sql;
            sql = "UPDATE `user` SET `password`='" + pw + "'" + ", `name`='" + name + "'" + ", surname='" + surname + "', email='" + mail + "' WHERE username='" + uname + "'";
            update_statement.executeUpdate(sql);
            update_statement.close();
        } catch (Exception se) {
            se.printStackTrace();
        } finally {
            try {
                if (update_statement != null) {
                    update_statement.close();
                }
            } catch (SQLException se2) {
            }
        }
    }
/*
Search user table by username
 */
    public static void loadUser(String username, User u) {
        Statement load_statement = null;
        try {
            load_statement = connection.createStatement();
            String command;
            command = "SELECT * FROM `user` WHERE username='" + username + "'";
            ResultSet results = load_statement.executeQuery(command);
            while (results.next()) {
                u.username = username;
                u.password = results.getString("password");
                u.email = results.getString("email");
                u.name = results.getString("name");
                u.surname = results.getString("surname");
                u.reg_date = results.getDate("reg_date").toString();
            }
            results.close();
            load_statement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (load_statement == null) {
                    load_statement.close();
                }
            } catch (Exception ex2) {
            }//We want to do nothing
        }
    }
/*
Find recruiter's company
 */
    public static String recruiter_Company_Finder(String username) {
        Statement load_statement = null;
        String afm = "";
        try {
            load_statement = connection.createStatement();
            String command;
            command = "SELECT * FROM `recruiter` WHERE username='" + username + "'";
            ResultSet results = load_statement.executeQuery(command);
            while (results.next()) {
                afm = results.getString("firm");
            }
            results.close();
            load_statement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (load_statement == null) {
                    load_statement.close();
                }
            } catch (Exception ex2) {
            }//We want to do nothing
        }
        return afm;
    }
/*
Search company table by afm
 */
    public static Etaireia companyFinder(String afm) {
        Statement company_statement = null;
        Etaireia e = new Etaireia();
        try {
            company_statement = connection.createStatement();
            String command;
            command = "SELECT * FROM `etaireia` WHERE AFM='" + afm + "'";
            ResultSet results = company_statement.executeQuery(command);
            while (results.next()) {
                e.AFM = afm;
                e.DOY = results.getString("DOY");
                e.name = results.getString("name");
                e.tel = results.getLong("tel");
                e.num = results.getInt("num");
                e.city = results.getString("city");
                e.country = results.getString("country");
                e.street = results.getString("street");
            }
            results.close();
            company_statement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (company_statement == null) {
                    company_statement.close();
                }
            } catch (Exception ex2) {
            }//We want to do nothing
        }
        return e;
    }
/*
Update company profile
 */
    public static void updateCompany(String afm, String doy, String name, long tel, int num, String city, String country, String street) {
        Statement update_statement = null;
        try {
            update_statement = connection.createStatement();
            String sql;
            sql = "UPDATE `etaireia` SET `AFM`='" + afm + "'" + ", `DOY`='" + doy + "'" + ", name='" + name + "', tel='" + tel + "', num= '" + num + "', city= '" + city + "', country='" + country + "', street= '" + street + "' WHERE AFM='" + afm + "'";
            update_statement.executeUpdate(sql);
            update_statement.close();
        } catch (Exception se) {
            se.printStackTrace();
        } finally {
            try {
                if (update_statement != null) {
                    update_statement.close();
                }
            } catch (SQLException se2) {
            }
        }
    }
/*
Add o a job
 */
    public static void addJob(String startdate, float salary, String position, String edra, String recruiter, String submissiondate, String antikeimeno) {
        Statement insert_statement1 = null;
        try {
            insert_statement1 = connection.createStatement();
            String sql;
            // sql = "INSERT INTO `job` VALUES `start_date`='" + startdate + "'" + ", `salary`='" + salary  + "'" + ", surname='" + surname + "', email='" + mail + "' WHERE username='" + uname + "'";
            sql = "INSERT INTO job " + "VALUE(NULL,'" + startdate + "'," + salary + ",'" + position + "','" + edra + "','" + recruiter + "',NOW(),'" + submissiondate + "')";
            insert_statement1.executeUpdate(sql);
            insert_statement1.close();
        } catch (Exception se) {
            se.printStackTrace();
        } finally {
            try {
                if (insert_statement1 != null) {
                    insert_statement1.close();
                }
            } catch (SQLException se2) {
            }
        }
        Statement insert_statement2 = null;
        try {
            insert_statement2 = connection.createStatement();
            String[] tempArray = antikeimeno.split(",");
            String sql;
            for (int i = 0; i < tempArray.length; i++) {
                sql = "INSERT INTO requires select id,'" + tempArray[i] + "' from job order by id desc limit 0,1";
                insert_statement2.executeUpdate(sql);
            }
            insert_statement2.close();
            {
                JFrame frame = new JFrame("COMPLETED");

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                JButton button = new JButton();
                frame.pack();
                button.setText("OK");
                button.setPreferredSize(new Dimension(5, 5));
                frame.add(button);
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                    }

                });
                frame.setSize(100, 100);
                frame.setVisible(true);
            }
        } catch (Exception se) {
            se.printStackTrace();
        } finally {
            try {
                if (insert_statement2 != null) {
                    insert_statement2.close();
                }
            } catch (SQLException se2) {
            }
        }

    }
/*
Search jobs by recruiter
 */
    public static ArrayList<Integer> getJobIds(User u) {
        ArrayList<Integer> temp= new ArrayList<Integer>();
        Statement find_statement = null;
        try {
            find_statement = connection.createStatement();
            String sql;
            sql = "select id from `job` where recruiter='" + u.username + "'";
            ResultSet results = find_statement.executeQuery(sql);
            while (results.next()) {
                temp.add(results.getInt("id"));
            }
                results.close();
                find_statement.close();
                return temp;
            } catch(Exception se){
                se.printStackTrace();
            } finally{
                try {
                    if (find_statement != null) {
                        find_statement.close();
                    }
                } catch (SQLException se2) {
                }
            }
        return temp;
        }
/*
Search jobs by job id
 */
    public static Job JobFinder(Integer job_id) {
        Statement job_statement = null;
        Job j = new Job();
        try {
            job_statement = connection.createStatement();
            String command;
            command = "SELECT * FROM `job` WHERE id='" + job_id + "'";
            ResultSet results = job_statement.executeQuery(command);
            while (results.next()) {
                j.id = job_id;
                j.start_date = results.getDate("start_date").toString();
                j.salary= Float.parseFloat(results.getString("salary"));
                j.position = results.getString("position");
                j.edra = results.getString("edra");
                j.announce_date = results.getDate("announce_date").toString();
                j.submission_date = results.getString("submission_date");
            }
            results.close();
            job_statement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (job_statement == null) {
                    job_statement.close();
                }
            } catch (Exception ex2) {
            }//We want to do nothing
        }
        return j;
    }
/*
Update job details
 */
    public static void updateJob(Integer job_id, String start_date, Float salary,String edra, String position, String submission_date) {
        Statement update_statement = null;
        try {
            update_statement = connection.createStatement();
            String sql;
                sql = "UPDATE `job` SET   `start_date`='" + start_date + "' , salary='" + salary + "', position='" + position+ "', edra= '" + edra + "', submission_date='"+ submission_date +"'  where id="+ job_id+"" ;
            update_statement.executeUpdate(sql);
            update_statement.close();
        } catch (Exception se) {
            se.printStackTrace();
        } finally {
            try {
                if (update_statement != null) {
                    update_statement.close();
                }
            } catch (SQLException se2) {
            }
        }
    }
/*
Search for nu,ber of applications per job id
 */
    public static Integer n_apps(Integer job_id) {
        Statement query_statement = null;
        int temp =-1;
        try {
            query_statement = connection.createStatement();
            String sql;
            sql = " select count(cand_usrname) from `applies` where `job_id`='" + job_id + "'";
            ResultSet results = query_statement.executeQuery(sql);
            while (results.next()) {
                temp = results.getInt(1);
            }
            query_statement.close();
            return temp;
        } catch (Exception se) {
            se.printStackTrace();
        } finally {
            try {
                if (query_statement != null) {
                    query_statement.close();
                }
            } catch (SQLException se2) {
                return -10;
            }
        }
        return temp;
    }
/*
Find jobs per company
 */
    public static ArrayList<Job> getJobs(User u,Etaireia e) {
        ArrayList<Job> jobTemp= new ArrayList<Job>();

        Statement find_statement = null;
        try {
            find_statement = connection.createStatement();
            String sql;
            sql = "select * from `job` INNER JOIN `recruiter` ON username = `job`.recruiter where firm='" + e.AFM + "'";
            ResultSet results = find_statement.executeQuery(sql);
            while (results.next()) {
                Job j = new Job();
                j.id = results.getInt("id");
                j.start_date = results.getDate("start_date").toString();
                j.salary= Float.parseFloat(results.getString("salary"));
                j.position = results.getString("position");
                j.edra = results.getString("edra");
                j.recruiter = results.getString("recruiter");
                j.announce_date = results.getDate("announce_date").toString();
                j.submission_date = results.getString("submission_date");
                jobTemp.add(j);
            }
            results.close();
            find_statement.close();
            return jobTemp;
        } catch(Exception se){
            se.printStackTrace();
        } finally{
            try {
                if (find_statement != null) {
                    find_statement.close();
                }
            } catch (SQLException se2) {
            }
        }
        return jobTemp;
    }
/*
Find interview ids for a job per recruiter
 */
    public static ArrayList<Integer> getInterviewIds(User u, Integer job_id) {
        ArrayList<Integer> tempI= new ArrayList<Integer>();
        Statement find_statement = null;
        try {
            find_statement = connection.createStatement();
            String sql;
            sql = "SELECT interview_id FROM `interview` WHERE job_id='" + job_id + "' AND recruiter='"+ u.username + "'";
            ResultSet results = find_statement.executeQuery(sql);
            while (results.next()) {
                tempI.add(results.getInt("interview_id"));
            }
            results.close();
            find_statement.close();
            return tempI;
        } catch(Exception se){
            se.printStackTrace();
        } finally{
            try {
                if (find_statement != null) {
                    find_statement.close();
                }
            } catch (SQLException se2) {
            }
        }
        return tempI;
    }
/*
Search for a an interview by interview id and job id
 */
    public static Interview InterviewFinder(Integer interview_id, Integer job_id, User u) {
        Statement inter_statement = null;
        Interview i = new Interview();
        try {
            inter_statement = connection.createStatement();
            String command;
            command = "SELECT * FROM `interview` WHERE job_id='" + job_id + "' AND recruiter='"+ u.username + "' AND interview_id='"+ interview_id+"'";
            ResultSet results = inter_statement.executeQuery(command);
            while (results.next()) {
                i.interview_id = results.getInt("interview_id");
                i.recruiter= u.username;
                i.candidate = results.getString("candidate");
                i.job_id = job_id;
                i.date = results.getDate("intdate").toString();
                i.duration_hours = results.getInt("duration_hours");
                i.persscore = results.getInt("persscore");
                i.edscore = results.getInt("edscore");
                i.workexpscore = results.getInt("workexpscore");
                i.comments = results.getString("comments");
            }
            results.close();
            inter_statement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (inter_statement == null) {
                    inter_statement.close();
                }
            } catch (Exception ex2) {
            }//We want to do nothing
        }
        return i;
    }
/*
Update interview details
 */
    public static void updateInterview(Integer interview_id, Integer job_id, int persscrore, int edscore,int workexpscore, String comments) {
        Statement update_statement = null;
        try {
            update_statement = connection.createStatement();
            String sql;
            sql = "UPDATE `interview` SET   `persscore`='" + persscrore + "' , edscore='" + edscore + "', workexpscore='" + workexpscore+ "', comments='"+ comments + "' where interview_id='"+ interview_id+"'" ;
            update_statement.executeUpdate(sql);
            System.out.println(interview_id);
            update_statement.close();

            {
                JFrame frame = new JFrame("COMPLETED");

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                JButton button = new JButton();
                frame.pack();
                button.setText("OK");
                button.setPreferredSize(new Dimension(5, 5));
                frame.add(button);
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                    }

                });
                frame.setSize(100, 100);
                frame.setVisible(true);
            }

        } catch (Exception se) {
            se.printStackTrace();
        } finally {
            try {
                if (update_statement != null) {
                    update_statement.close();
                }
            } catch (SQLException se2) {
            }
        }
    }

/*
Find ranking of candidates for a job
 */
    public static void showRanking(Integer job_id, ArrayList<Success> s, ArrayList<Fail> f ) {
        CallableStatement proc_statement = null;
        try {
            proc_statement = connection.prepareCall("{call FIND_CANDIDATES("+ job_id +")}");

            ResultSet resFail = proc_statement.executeQuery();

            while (resFail.next()) {
                Fail failed = new Fail();
                failed.candid = resFail.getString("candid");
                failed.reas = resFail.getString("reas");
                f.add(failed);
            }
            proc_statement.getMoreResults();
            ResultSet resSuccess = proc_statement.getResultSet();
            while (resSuccess.next()) {
                Success successful = new Success();
                successful.candid = resSuccess.getString("candid");
                successful.per = resSuccess.getInt("per");
                successful.edu = resSuccess.getInt("edu");
                successful.workex = resSuccess.getInt("workex");
                successful.total = resSuccess.getInt("total");
                successful.n_ints = resSuccess.getInt("n_ints");

                s.add(successful);
            }
            resFail.close();
            resSuccess.close();
            proc_statement.close();

        } catch(Exception se){
            se.printStackTrace();
        } finally{
            try {
                if (proc_statement != null) {
                    proc_statement.close();
                }
            } catch (SQLException se2) {
            }
        }
    }

/*
Find jobs open for submission
 */
    public static ArrayList<Integer> getJobsforApplic(User u) {

        Statement find_statement = null;
        ArrayList<Integer> temp= new ArrayList<Integer>();
        try {
            find_statement = connection.createStatement();
            String sql;
            int i = 0;
            sql = "select `id` from `job` where `job`.submission_date> NOW();";
            ResultSet results = find_statement.executeQuery(sql);
            while (results.next()) {
               temp.add(results.getInt("id"));
                i++;
            }
            results.close();
            find_statement.close();
            return temp;
        } catch(Exception se){
            se.printStackTrace();
        } finally{
            try {
                if (find_statement != null) {
                    find_statement.close();
                }
            } catch (SQLException se2) {
            }
        }
        return temp;
    }


/*
User applies for a job
 */
    public static void insertAppl(User u, Integer job_id) {
        Statement insert_statement = null;
        Etaireia e = new Etaireia();
        try {
            insert_statement = connection.createStatement();
            String command;
            command = "Insert into `applies` values ('" + u.username +"', "+ job_id +")";
            insert_statement.executeUpdate(command);
            insert_statement.close();


            {
                JFrame frame = new JFrame("COMPLETED");

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                JButton button = new JButton();
                frame.pack();
                button.setText("OK");
                button.setPreferredSize(new Dimension(5, 5));
                frame.add(button);
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                    }

                });
                frame.setSize(100, 100);
                frame.setVisible(true);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (insert_statement == null) {
                    insert_statement.close();
                }
            } catch (Exception ex2) {
            }//We want to do nothing
        }
    }

/*
Add a candidate
 */
    public static void addCandidate(String uname, String pw, String name, String surname, String mail, String bio, String sistatikes, String certificates) {
        Statement insert_statement = null;
        try {
            insert_statement = connection.createStatement();
            String sql;
            String sqlcand;
            sql = "INSERT INTO user" + "VALUE(" + uname + "'," + pw + ",'" + name + "','" + surname + "', NOW(),`"+ mail + "')";
            sqlcand = "INSERT INTO candidate" + "VALUE(" + uname + "'," + bio + ",'" + sistatikes + "','" + certificates + "')";
            insert_statement.executeUpdate(sql);
            insert_statement.executeUpdate(sqlcand);
            insert_statement.close();
        } catch (Exception se) {
            se.printStackTrace();
        } finally {
            try {
                if (insert_statement != null) {
                    insert_statement.close();
                }
            } catch (SQLException se2) {
            }
        }
    }
/*
Add a recruiter
 */
    public static void addRecruiter(String uname, String pw, String name, String surname, String mail, Integer expyears, String company) {
        Statement insert_statement = null;
        try {
            insert_statement = connection.createStatement();
            String sql;
            String sqlcand;
            sql = "INSERT INTO user" + "VALUE(" + uname + "'," + pw + ",'" + name + "','" + surname + "', NOW(),`"+ mail + "')";
            sqlcand = "INSERT INTO recruiter" + "VALUE(" + uname + "'," + expyears + ",'" + company + "')";
            insert_statement.executeUpdate(sql);
            insert_statement.executeUpdate(sqlcand);
            insert_statement.close();
        } catch (Exception se) {
            se.printStackTrace();
        } finally {
            try {
                if (insert_statement != null) {
                    insert_statement.close();
                }
            } catch (SQLException se2) {
            }
        }
    }
/*
Add antikeimeno
 */
    public static void addAntikeimeno(String title, String desc, String belongto) {
        Statement insert_statement = null;
        try {
            insert_statement = connection.createStatement();
            String sql;
            sql = "INSERT INTO `antikeim` " + "VALUE('" + title + "','" + desc + "','" + belongto + "')";
            insert_statement.executeUpdate(sql);
            insert_statement.close();
        } catch (Exception se) {
            se.printStackTrace();
        } finally {
            try {
                if (insert_statement != null) {
                    insert_statement.close();
                }
            } catch (SQLException se2) {
            }
        }
    }
/*
Add fields per company
 */
    public static void addCompanyFields(String company, String field) {
        Statement insert_statement = null;
        try {
            insert_statement = connection.createStatement();
            String sql;
            sql = "INSERT INTO `fields_per_company` " + "VALUE('" + company + "','" + field + "')";
            insert_statement.executeUpdate(sql);
            insert_statement.close();
        } catch (Exception se) {
            se.printStackTrace();
        } finally {
            try {
                if (insert_statement != null) {
                    insert_statement.close();
                }
            } catch (SQLException se2) {
            }
        }
    }



/*
Find all users
 */
    public static ArrayList<String> getAllUsers(){
        Statement find_statement = null;
        ArrayList<String> temp= new ArrayList<String>();
        try {
            find_statement = connection.createStatement();
            String sql;
            sql = "select * from `user`";
            ResultSet results = find_statement.executeQuery(sql);
            while (results.next()) {
                temp.add(results.getString("username"));
            }
            results.close();
            find_statement.close();
            return temp;
        } catch(Exception se){
            se.printStackTrace();
        } finally{
            try {
                if (find_statement != null) {
                    find_statement.close();
                }
            } catch (SQLException se2) {
            }
        }
        return temp;
    }
/*
Find changes per user
 */
    public static ArrayList<History> getHistoryByUser(String username,String startDate,String endDate){
        Statement find_statement = null;
        ArrayList<History> temp= new ArrayList<History>();
        try {
            find_statement = connection.createStatement();
            String sql;
            sql = "select * from `history` where `history`.h_date>= "+startDate+" AND `history`.h_date<= "+endDate+"AND `history`.username= '"+username+"'";
            ResultSet results = find_statement.executeQuery(sql);
            while (results.next()) {
                History h = new History();
                h.history_id = Integer.parseInt(results.getString("history_id"));
                h.username = username;
                h.date = results.getString("h_date");
                h.success = results.getString("success");
                h.type = results.getString("type");
                h.tablename = results.getString("table_name");
                temp.add(h);
            }
            results.close();
            find_statement.close();
            return temp;
        } catch(Exception se){
            se.printStackTrace();
        } finally{
            try {
                if (find_statement != null) {
                    find_statement.close();
                }
            } catch (SQLException se2) {
            }
        }
        return temp;
    }
/*
Find changes per table
 */
    public static ArrayList<History> getHistoryByTable(String tablename,String startDate,String endDate){
        Statement find_statement = null;
        ArrayList<History> temp= new ArrayList<History>();
        try {
            find_statement = connection.createStatement();
            String sql;
            sql = "select * from `history` where `history`.h_date>= "+startDate+" AND `history`.h_date<= "+endDate+" AND `history`.table_name= '"+tablename+"'";
            ResultSet results = find_statement.executeQuery(sql);
            while (results.next()) {
                History h = new History();
                h.history_id = Integer.parseInt(results.getString("history_id"));
                h.username = results.getString("username");
                h.date = results.getString("h_date");
                h.success = results.getString("success");
                h.type = results.getString("type");
                h.tablename = tablename;
                temp.add(h);
            }
            results.close();
            find_statement.close();
            return temp;
        } catch(Exception se){
            se.printStackTrace();
        } finally{
            try {
                if (find_statement != null) {
                    find_statement.close();
                }
            } catch (SQLException se2) {
            }
        }
        return temp;
    }

/*
Find applications per user
 */
    public static ArrayList<Integer> getApplicationsperUser(String username){
        Statement find_statement = null;
        ArrayList<Integer> temp= new ArrayList<Integer>();
        try {
            find_statement = connection.createStatement();
            String sql;
            sql = "select job_id from `applies` where  `cand_usrname`='"+ username +"'";
            ResultSet results = find_statement.executeQuery(sql);
            while (results.next()) {
                temp.add(Integer.parseInt(results.getString("job_id")));
            }
            results.close();
            find_statement.close();
            return temp;
        } catch(Exception se){
            se.printStackTrace();
        } finally{
            try {
                if (find_statement != null) {
                    find_statement.close();
                }
            } catch (SQLException se2) {
            }
        }
        return temp;
    }

/*
Deelete applications
 */
    public static void deleteApplication(Integer job_id, String username) {
        Statement delete_statement = null;
        try {
            delete_statement = connection.createStatement();
            String sql;
            sql = "DELETE from `applies` WHERE job_id ='"+ job_id +"' AND cand_usrname='"+ username + "'" ;
            delete_statement.executeUpdate(sql);
            delete_statement.close();

            {
                JFrame frame = new JFrame("COMPLETED");

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                JButton button = new JButton();
                frame.pack();
                button.setText("OK");
                button.setPreferredSize(new Dimension(5, 5));
                frame.add(button);
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                    }

                });
                frame.setSize(100, 100);
                frame.setVisible(true);
            }

        } catch (Exception se) {
            se.printStackTrace();
        } finally {
            try {
                if (delete_statement != null) {
                    delete_statement.close();
                }
            } catch (SQLException se2) {
            }
        }
    }


}



