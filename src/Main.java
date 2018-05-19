import java.sql.*;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws Exception {

////            if(conn != null) try{conn.close();} catch(SQLException se) {}
////            if(stmt != null) try{stmt.close();} catch(SQLException se) {}
////            if(rs != null) try{rs.close();} catch(SQLException se) {}

        reception();

    }

    //연결
    public static Connection getConnection() throws Exception {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver connection success");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?serverTimezone=UTC&autoReconnect=true&useSSL=false","root","rntl112*@");
            System.out.println("Database connection success");
            return conn;
        } catch(ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        return null;
    }

    //진찰
    public static void examine(String name, String birthday) throws Exception {
        System.out.println("Examination Step");
        System.out.print("Hospitalization: Y or N : ");
        String hospitalzation = scanner.nextLine().toLowerCase();
        try {
            Connection conn = getConnection();
//            String sql = "select patientID from patients where name = '" + name + "' and birthday = '" + birthday + "');";
            String sql = "select patientID from patients where name = '" + name + "');";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet result = pstmt.executeQuery();
            while(result.next()) {
                System.out.println(result.getInt("patientID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (hospitalzation.equals("y")) {

        } else if (hospitalzation.equals("n")) {

        } else {
            System.out.print("Error : Wrong Input");
            System.out.print("Hospitalization: Y or N : ");
            hospitalzation = scanner.nextLine().toLowerCase();
        }

    }
    // 접수
   public static void reception() throws Exception {
        String name, address, weight, height, birthday, gender;
        System.out.println("First time or not (Y,N) : ");
        String firstTime = scanner.nextLine().toLowerCase();
        if(firstTime.equals("n")) {

            System.out.print("Name : ");
            name = scanner.nextLine().toLowerCase();
            System.out.print("Birthday ex(19941116) : ");
            birthday = scanner.nextLine().toLowerCase();
            examine(name, birthday);
        } else if (firstTime.equals("y")) {
            System.out.println("Patient Enroll");
            System.out.print("Name : ");
            name = scanner.nextLine().toLowerCase();
            System.out.print("Address : ");
            address = scanner.nextLine().toLowerCase();
            System.out.print("Weight : ");
            weight = scanner.nextLine().toLowerCase();
            System.out.print("Height : ");
            height = scanner.nextLine().toLowerCase();
            System.out.print("Birthday : ");
            birthday = scanner.nextLine().toLowerCase();
            System.out.print("Gender : ");
            gender = scanner.nextLine().toLowerCase();
            try {
                Connection conn = getConnection();
                String sql = "insert into Patients(patientName, address, weight, height, birthday, gender) " +
                        "values ('" + name + "', '" + address + "', '" + weight + "', '" + height + "', '" + birthday + "', '" + gender + "');";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            examine(name, birthday);
        } else {
            System.out.println("Wrong input");
            System.out.println("First time or not : ");
            firstTime = scanner.nextLine().toLowerCase();
        }
    }

}
