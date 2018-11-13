

import javafx.scene.Scene;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * A sample app that connects to a Cloud SQL instance and lists all available tables in a database.
 */
public class DB {

    Connection conn;

    //Generic SQL statements
    final String createTableSql = "CREATE TABLE IF NOT EXISTS employees ( "
            + "emp_id INTEGER PRIMARY KEY, first_name TEXT,last_name TEXT,auth TEXT,email TEXT,phone TEXT,problemSubmitted TEXT,"
            + "problemSolved TEXT,password TEXT)";

    final String dropTableSQL = "DROP TABLE employees";

    final String addEmployeeSQL = "INSERT INTO employees (emp_id, first_name, last_name,auth,email,phone,problemSubmitted,problemSolved,password) VALUES (?,?,?,?,?,?,?,?,?);";

    final String getAllEmpSQL = "SELECT * FROM employees ORDER BY emp_id ";



    String instanceConnectionName = "sql9.freemysqlhosting.net";
    String databaseName = "sql9263443";
    String username = "sql9263443";
    String password = "IqUyzSI4u1";

    String jdbcUrl = String.format(
            "jdbc:mysql://sql9.freemysqlhosting.net:3306/"
                    +
                    databaseName,
            instanceConnectionName);

    DB() throws SQLException{

        //Connecting to DB
        try {
            conn = DriverManager.getConnection(jdbcUrl, username, password);
            Statement statement = conn.createStatement();

            //statement.executeUpdate(dropTableSQL);
            statement.executeUpdate(createTableSql);

        }catch (SQLException sqle){
            System.out.println("Error connecting to database.");
            System.out.println(sqle);
        }

    }



    public void addTicket(String emp_ID,String lastName,String password,String desc,String dateAdded,String tech,String problemsSub){

        final String addTicketSQL = String.format("INSERT INTO %s (ticket_id, description, dateAdded,tech) VALUES (?,?,?,?);",lastName+password);


        try (PreparedStatement ps = conn.prepareStatement(addTicketSQL)){

            int id = generateUniqueID();
            ps.setInt(1,id);
            ps.setString(2,desc);
            ps.setString(3,dateAdded);
            ps.setString(4,tech);

            ps.executeUpdate();
            addTicketSub(Integer.parseInt(emp_ID),problemsSub);

        }catch (SQLException sqle){
            System.out.println(sqle);
        }
    }

    public void addTicketSub(int ID,String problemSubmitted){
        //Adding sumbitted tickets
        final String addTicketResolutionSQL = "UPDATE employees SET problemSubmitted = ? WHERE emp_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(addTicketResolutionSQL)){

            //int id = generateUniqueID();

            int submitted = Integer.parseInt(problemSubmitted) + 1;
            ps.setString(1,Integer.toString(submitted));
            ps.setInt(2,ID);

            ps.executeUpdate();

        }catch (SQLException sqle){
            System.out.println(sqle);
        }
    }

    public void addTicketRes(int emp_ID,String ticketRes){

        //Adding resolved tickets
        final String addTicketResolutionSQL = "UPDATE employees SET problemSolved = ?, WHERE emp_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(addTicketResolutionSQL)){

            //int id = generateUniqueID();

            int resolved = Integer.parseInt(ticketRes) + 1;
            ps.setString(1,Integer.toString(resolved));
            ps.setInt(2,emp_ID);

            ps.executeUpdate();

        }catch (SQLException sqle){
            System.out.println(sqle);
        }
    }





    public void addTicketResolution(int ID,String lastName,String password,String resolution, String dateResolved,int ticketID,String ticketsResolved){
        //Adding a description of how the problem was solved

        final String addTicketResolutionSQL = String.format("UPDATE %s SET resolution = ?, dateResolved = ? WHERE ticket_id = ?",lastName+password);


        try (PreparedStatement ps = conn.prepareStatement(addTicketResolutionSQL)){

            //int id = generateUniqueID();
            ps.setString(1,resolution);
            ps.setString(2,dateResolved);
            ps.setInt(3,ticketID);



            ps.executeUpdate();

            addTicketRes(ID,ticketsResolved);

        }catch (SQLException sqle){
            System.out.println(sqle);
        }
    }



    public ArrayList<Employees> getAll(){
        ArrayList<Employees> list = new ArrayList<>();

        try(PreparedStatement ps = conn.prepareStatement(getAllEmpSQL);){

            ResultSet rs = ps.executeQuery();

            if (!rs.isBeforeFirst()){
                System.out.println("Empty");
                return null;
            }else {
                while (rs.next()){

                    Employees employee = new Employees();
                    employee.setId(Integer.toString(rs.getInt("emp_id")));
                    employee.setFirstName(rs.getString("first_name"));
                    employee.setLastName(rs.getString("last_name"));
                    employee.setAuth(rs.getString("auth"));
                    employee.setEmail(rs.getString("email"));
                    employee.setPhone(rs.getString("phone"));
                    employee.setProblemsSol(rs.getString("problemSolved"));
                    employee.setProblemsSub(rs.getString("problemSubmitted"));
                    employee.setPassword(rs.getString("password"));


                    list.add(employee);
                }
                // returns a list of movie objects
                return list;
            }



        }catch (SQLException sqle){
            System.out.println(sqle);
            return null;
        }
    }

    public ArrayList<Ticket> getAllTickets(String lastName,String password){
        ArrayList<Ticket> list = new ArrayList<>();

        final String getAllTicketsSQL = String.format("SELECT * FROM %s",lastName+password);

        try(PreparedStatement ps = conn.prepareStatement(getAllTicketsSQL)){

            ResultSet rs = ps.executeQuery();

            if (!rs.isBeforeFirst()){
                System.out.println("Empty");
                return list;
            }else {
                while (rs.next()){

                    Ticket ticket = new Ticket(Integer.toString(rs.getInt("ticket_id")),rs.getString("description"),rs.getString("resolution"),
                            rs.getString("dateAdded"),rs.getString("dateResolved"),rs.getString("tech"));

                    list.add(ticket);
                }
                // returns a list of movie objects
                return list;
            }



        }catch (SQLException sqle){
            System.out.println(sqle);
            return null;
        }
    }








    public void addEmployee(String firstName,String lastName, String email,String phone,String password, String auth) {


        try (PreparedStatement ps = conn.prepareStatement(addEmployeeSQL);) {
            int id = generateUniqueID();
            ps.setInt(1,id);
            ps.setString(2,firstName);
            ps.setString(3,lastName);
            ps.setString(4,auth);
            ps.setString(5,email);
            ps.setString(6,phone);
            ps.setString(7,"0");
            ps.setString(8,"0");
            ps.setString(9,password);
            ps.executeUpdate();


            final String createTicket = String.format("CREATE TABLE IF NOT EXISTS %s ( " +
                     "ticket_id INTEGER PRIMARY KEY, description TEXT,resolution TEXT,dateAdded TEXT,dateResolved TEXT,tech TEXT)",lastName+password);

            try {
                Statement statement = conn.createStatement();

                //statement.executeUpdate(dropTableSQL);
                statement.executeUpdate(createTicket);


            }catch (SQLException sqlq){
                System.out.println(sqlq);
            }



        } catch (SQLException sqle) {
            System.out.println(sqle);

        }

        /*try (PreparedStatement statementCreateVisit = connection.prepareStatement(createVisitSql)) {
            connection.createStatement().executeUpdate(createTableSql);

            statementCreateVisit.setInt(1, 1);
            statementCreateVisit.setString(2, "firstname");
            statementCreateVisit.setString(3, "lastname");
            statementCreateVisit.setString(4, "admin");
            statementCreateVisit.executeUpdate();

            //statement.executeUpdate(createVisitSql);
            System.out.println("works");

        }*/


    }

    ArrayList<Employees> getTech(){
        // Get a random technician
        String deleteEmployee = "SELECT * FROM employees WHERE auth = ?";

        ArrayList<Employees> techs = new ArrayList<>();

        try(PreparedStatement ps = conn.prepareStatement(deleteEmployee)){



            ps.setString(1,"Technician");

            ResultSet rs = ps.executeQuery();

            if (!rs.isBeforeFirst()){
                System.out.println("Empty");
                return techs;
            }else {
                while (rs.next()){

                    Employees employee = new Employees();
                    employee.setId(Integer.toString(rs.getInt("emp_id")));
                    employee.setFirstName(rs.getString("first_name"));
                    employee.setLastName(rs.getString("last_name"));
                    employee.setAuth(rs.getString("auth"));
                    employee.setEmail(rs.getString("email"));
                    employee.setPhone(rs.getString("phone"));
                    employee.setProblemsSol(rs.getString("problemSolved"));
                    employee.setProblemsSub(rs.getString("problemSubmitted"));
                    employee.setPassword(rs.getString("password"));


                    techs.add(employee);
                }
                // returns a list of movie objects
                return techs;
            }



        }catch (SQLException sqle){
            System.out.println("SQL EXCEPTION");
            return null;
        }
    }

    void deleteItem(int emp_id){
        String deleteEmployee = "DELETE FROM employees WHERE emp_id = ?";

        try(PreparedStatement ps = conn.prepareStatement(deleteEmployee)){



            ps.setInt(1,emp_id);

            ps.executeUpdate();



        }catch (SQLException sqle){
            System.out.println("SQL EXCEPTION");

        }
    }

    void updateData(String firstName,String lastName, String email,String phone, String auth,String id){
        String updateReview = "UPDATE employees SET first_name = ?, last_name = ?, auth = ?, email = ?, phone = ? WHERE emp_id = ?";

        try(PreparedStatement ps = conn.prepareStatement(updateReview)){


            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, auth);
            ps.setString(4, email);
            ps.setString(5, phone);
            ps.setInt(6, Integer.parseInt(id));

            System.out.println(firstName + " "+ lastName + " "+ auth + " "+ email + " "+ phone + " "+ Integer.parseInt(id) + " " );

            ps.executeUpdate();



        }catch (SQLException sqle){
            System.out.println(sqle);


        }

    }

    ArrayList<Employees> checkLogin(int id,String password){
        String getEmp = "SELECT * FROM employees WHERE emp_id = ? AND password = ?";

        try(PreparedStatement ps = conn.prepareStatement(getEmp)){


            ArrayList<Employees> user = new ArrayList<>();

            ps.setInt(1,id);
            ps.setString(2,password);


            ResultSet rs = ps.executeQuery();


            if (!rs.isBeforeFirst()){
                System.out.println("Empty");
                return null;
            }
            else{
                rs.next();
                Employees employee = new Employees();
                employee.setId(Integer.toString(rs.getInt("emp_id")));
                employee.setFirstName(rs.getString("first_name"));
                employee.setLastName(rs.getString("last_name"));
                employee.setAuth(rs.getString("auth"));
                employee.setEmail(rs.getString("email"));
                employee.setPhone(rs.getString("phone"));
                employee.setProblemsSol(rs.getString("problemSolved"));
                employee.setProblemsSub(rs.getString("problemSubmitted"));
                employee.setPassword(rs.getString("password"));
                user.add(employee);

            }
                // returns a list of movie objects
            return user;

        }catch (SQLException sqle){
            System.out.println(sqle);
            return null;
        }
    }

    private int generateUniqueID(){
        Random rand = new Random();
        int n = rand.nextInt(1000000)+ 100000;



        return n;
    }

}