package ceng.ceng351.cengvacdb;

import java.sql.*;
import java.util.Vector;

public class CENGVACDB implements ICENGVACDB{
    private static String user = "e1234567";
    private static String password = "password";
    private static String host = "localhost";
    private static String database = "db";
    private static int port = 8080; // port
    Connection connection = null;

    @Override
    public void initialize() {
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection =  DriverManager.getConnection(url, user, password);
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int createTables() {  
        try{
            Statement stmt = connection.createStatement();
            String sql = "CREATE TABLE User (" +
                    "userID int," +
                    "userName varchar(30)," +
                    "age int," +
                    "address varchar(150)," +
                    "password varchar(30)," +
                    "status varchar(15)," +
                    "PRIMARY KEY (userID)" +
                    ");";

            stmt.executeUpdate(sql);

            Statement stmt2 = connection.createStatement();
            String sql2 = "CREATE TABLE Vaccine (" +  
                    "code int," +
                    "vaccinename varchar(30)," +
                    "type varchar(30)," +
                    "PRIMARY KEY (code)" +
                    ");";

            stmt2.executeUpdate(sql2);


            Statement stmt3 = connection.createStatement();
            String sql3 = "CREATE TABLE  Vaccination (" +
                    "code int," +
                    "userID int," +
                    "dose int," +
                    "vacdate date," +
                    "PRIMARY KEY (code, userID, dose)," +
                    "FOREIGN KEY (code) REFERENCES Vaccine(code) ON DELETE CASCADE," +
                    "FOREIGN KEY (userID) REFERENCES User(userID)" +
                    ");";

            stmt3.executeUpdate(sql3);

            Statement stmt4 = connection.createStatement();
            String sql4 = "CREATE TABLE AllergicSideEffect (" +
                    "effectcode int," +
                    "effectname varchar(50)," +
                    "PRIMARY KEY (effectcode)" +
                    ");";

            stmt4.executeUpdate(sql4);

            Statement stmt5 = connection.createStatement();
            String sql5 = "CREATE TABLE Seen (" +
                    "effectcode int," +
                    "code int," +
                    "userID int," +
                    "date date," +
                    "degree varchar(30)," +
                    "PRIMARY KEY (effectcode, code, userID)," +
                    "FOREIGN KEY (code) REFERENCES Vaccine(code) ON DELETE CASCADE," +
                    "FOREIGN KEY (effectcode) REFERENCES AllergicSideEffect(effectcode) ON DELETE CASCADE," +
                    "FOREIGN KEY (userID) REFERENCES User(userID)" +
                    ");";

            stmt5.executeUpdate(sql5);

        }
        catch (Exception E){
            E.printStackTrace();
        }

        return 5;
    }

    @Override
    public int dropTables() {
        try{

            Statement stmt3 = connection.createStatement();
            String sql3 = "DROP TABLE  Vaccination" +
                    ";";
            stmt3.executeUpdate(sql3);

            Statement stmt5 = connection.createStatement();
            String sql5 = "DROP TABLE  Seen" +
                    ";";
            stmt5.executeUpdate(sql5);


            Statement stmt = connection.createStatement();
            String sql = "DROP TABLE  User" +
                    ";";
            stmt.executeUpdate(sql);


            Statement stmt2 = connection.createStatement();
            String sql2 = "DROP TABLE  Vaccine" +
                    ";";
            stmt2.executeUpdate(sql2);


            Statement stmt4 = connection.createStatement();
            String sql4 = "DROP TABLE  AllergicSideEffect" +
                    ";";
            stmt4.executeUpdate(sql4);

        }
        catch (Exception E){
            E.printStackTrace();
        }

        return 5;
    }

    @Override
    public int insertUser(User[] users) { 
        int count = 0;
        for (User u:
                users) {
            try {
                Statement stmt =  connection.createStatement();
                String sql = "INSERT INTO User " +
                        "VALUES (" + u.getUserID() + "," + "'" + u.getUserName() + "'" + "," + u.getAge() + "," + "'" + u.getAddress() + "'" + "," +
                        "'" + u.getPassword() + "'" + "," + "'" + u.getStatus() + "'" + ");"
                        ;
                stmt.executeUpdate(sql);
                count++;
            }
            catch (Exception E){
                E.printStackTrace();
            }
        }
        return count;
    }

    @Override
    public int insertAllergicSideEffect(AllergicSideEffect[] sideEffects) {
        int count = 0;
        for (AllergicSideEffect s:
                sideEffects) {
            try {
                Statement stmt =  connection.createStatement();
                String sql = "INSERT INTO AllergicSideEffect " +
                        "VALUES (" + s.getEffectCode() + "," + "'" + s.getEffectName() + "'" + ");"
                        ;
                stmt.executeUpdate(sql);
                count++;
            }
            catch (Exception E){
                E.printStackTrace();
            }
        }
        return count;
    }

    @Override
    public int insertVaccine(Vaccine[] vaccines) {
        int count = 0;
        for (Vaccine v:
                vaccines) {
            try {
                Statement stmt =  connection.createStatement();
                String sql = "INSERT INTO Vaccine " +
                        "VALUES (" + v.getCode() + "," + "'" + v.getVaccineName() + "'" + "," + "'" + v.getType() + "'" +  ");"
                        ;
                stmt.executeUpdate(sql);
                count++;
            }
            catch (Exception E){
                E.printStackTrace();
            }
        }
        return count;
    }

    @Override
    public int insertVaccination(Vaccination[] vaccinations) {
        int count = 0;
        for (Vaccination v:
                vaccinations) {
            try {
                Statement stmt =  connection.createStatement();
                String sql = "INSERT INTO Vaccination " +
                        "VALUES (" + v.getCode() + "," + v.getUserID() +  "," + v.getDose() + "," + "'" + v.getVacdate() + "'" + ");"
                        ;
                stmt.executeUpdate(sql);
                count++;
            }
            catch (Exception E){
                E.printStackTrace();
            }
        }
        return count;
    }

    @Override
    public int insertSeen(Seen[] seens) {
        int count = 0;
        for (Seen s:
                seens) {
            try {
                Statement stmt =  connection.createStatement();
                String sql = "INSERT INTO Seen " +
                        "VALUES (" + s.getEffectcode() + "," + s.getCode() +  "," + "'" + s.getUserID() + "'" + "," + "'" + s.getDate() + "'" + "," +
                        "'" + s.getDegree() + "'" +");"
                        ;
                stmt.executeUpdate(sql);
                count++;
            }
            catch (Exception E){
                E.printStackTrace();
            }
        }
        return count;

    }

    @Override
    public Vaccine[] getVaccinesNotAppliedAnyUser() {

        Vector<Vaccine> v = new Vector();
        Vaccine[] arr = new Vaccine[1];
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs;
            String sql = "SELECT * FROM Vaccine V " +
                    "WHERE V.code NOT IN " +
                    "(SELECT V1.code FROM Vaccination V1) ORDER BY code ASC;";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Vaccine vac = new Vaccine(rs.getInt(1),rs.getString(2),rs.getString(3));
                v.add(vac);
            }
            arr = new Vaccine[v.size()];
            for (int i = 0; i < v.size(); i++) {
                arr[i] = v.get(i);
            }

        } catch (Exception E) {
            E.printStackTrace();
        }
        return arr;
    }

    @Override
    public QueryResult.UserIDuserNameAddressResult[] getVaccinatedUsersforTwoDosesByDate(String vacdate) {
        QueryResult.UserIDuserNameAddressResult[] arr = new QueryResult.UserIDuserNameAddressResult[1];
        Vector<QueryResult.UserIDuserNameAddressResult> v = new Vector();

        try {
            Statement stmt1 = connection.createStatement();
            ResultSet rs;
            String sql = "SELECT Temp.userID, Temp.userName, Temp.address, COUNT(*) AS count \n" +
                    "FROM(SELECT U1.userID, U1.userName, U1.address, V1.vacdate \n" +
                    "    FROM Vaccination V1, User U1 \n" +
                    "    WHERE V1.userID = U1.userID AND V1.vacdate > " + "'" + vacdate + "'" + ") AS Temp \n" +
                    "GROUP BY Temp.userID \n" +
                    "HAVING count = 2 ORDER BY Temp.userID ASC;";

            rs = stmt1.executeQuery(sql);

            while (rs.next()) {
                QueryResult.UserIDuserNameAddressResult res = new QueryResult.UserIDuserNameAddressResult(rs.getString(1), rs.getString(2),rs.getString(3));
                v.add(res);
            }
            arr = new QueryResult.UserIDuserNameAddressResult[v.size()];
            for (int i = 0; i < v.size(); i++) {
                arr[i] = v.get(i);
            }

        } catch (Exception E) {
            E.printStackTrace();
        }

        return arr;
    }

    @Override
    public Vaccine[] getTwoRecentVaccinesDoNotContainVac() {

        Vector<Vaccine> v = new Vector();
        Vaccine[] arr = new Vaccine[1];
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs;
            String sql = "SELECT V1.code, V1.vaccinename, V1.type, MAX(V.vacdate) AS macdate " +
                    "FROM Vaccination V, Vaccine V1 " +
                    "WHERE  V1.code = V.code AND V1.vaccinename NOT LIKE '%vac%' " +
                    "GROUP BY V1.code " +
                    "ORDER BY macdate DESC LIMIT 0,2;";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Vaccine vac = new Vaccine(rs.getInt(1),rs.getString(2),rs.getString(3));
                v.add(vac);
            }
            arr = new Vaccine[v.size()];
            for (int i = 0; i < v.size(); i++) {
                arr[i] = v.get(i);
            }

        } catch (Exception E) {
            E.printStackTrace();
        }
        return arr;
    }

    @Override
    public QueryResult.UserIDuserNameAddressResult[] getUsersAtHasLeastTwoDoseAtMostOneSideEffect() {
        QueryResult.UserIDuserNameAddressResult[] arr = new QueryResult.UserIDuserNameAddressResult[1];
        Vector<QueryResult.UserIDuserNameAddressResult> v = new Vector();

        try {
            Statement stmt1 = connection.createStatement();
            ResultSet rs;
            String sql = "SELECT Temp.userID, Temp.userName, Temp.address " +
                    "FROM(SELECT U.userID, U.userName, U.address, COUNT(*) as count " +
                    "    FROM Vaccination V, User U " +
                    "    WHERE V.userID = U.userID AND U.userID " +
                    "    GROUP BY U.userID " +
                    "    HAVING count > 1) AS Temp " +
                    "     " +
                    "WHERE Temp.userID IN (SELECT U1.userID " +
                    "                    FROM User U1 " +
                    "                    WHERE U1.userID NOT IN (SELECT Temp2.userID " +
                    "                        FROM(SELECT U1.userID, U1.userName, U1.address, COUNT(*) AS count1 " +
                    "                            FROM Seen S, User U1 " +
                    "                            WHERE S.userID = U1.userID " +
                    "                            GROUP BY U1.userID " +
                    "                            HAVING count1 > 1) AS Temp2)) " +
                    "                            ORDER BY Temp.userID ASC;";

            rs = stmt1.executeQuery(sql);

            while (rs.next()) {
                QueryResult.UserIDuserNameAddressResult res = new QueryResult.UserIDuserNameAddressResult(rs.getString(1), rs.getString(2),rs.getString(3));
                v.add(res);
            }
            arr = new QueryResult.UserIDuserNameAddressResult[v.size()];
            for (int i = 0; i < v.size(); i++) {
                arr[i] = v.get(i);
            }

        } catch (Exception E) {
            E.printStackTrace();
        }

        return arr;
    }

    @Override
    public QueryResult.UserIDuserNameAddressResult[] getVaccinatedUsersWithAllVaccinesCanCauseGivenSideEffect(String effectname) {
        QueryResult.UserIDuserNameAddressResult[] arr = new QueryResult.UserIDuserNameAddressResult[1];
        Vector<QueryResult.UserIDuserNameAddressResult> v = new Vector();

        try {
            Statement stmt1 = connection.createStatement();
            ResultSet rs;
            String sql = "SELECT U.userID, U.userName, U.address " +
                    "FROM  User U " +
                    "WHERE NOT EXISTS (SELECT S.code " +
                    "                  FROM Seen S, AllergicSideEffect A " +
                        "                  WHERE S.effectcode=A.effectcode AND A.effectname = '" + effectname + "' AND NOT EXISTS (SELECT S1.code " +
                    "                                                                                                  FROM Seen S1 " +
                    "                                                                                                  WHERE  S1.userID = U.userID AND S1.code = S.code));";

            rs = stmt1.executeQuery(sql);

            while (rs.next()) {
                QueryResult.UserIDuserNameAddressResult res = new QueryResult.UserIDuserNameAddressResult(rs.getString(1), rs.getString(2),rs.getString(3));
                v.add(res);
            }
            arr = new QueryResult.UserIDuserNameAddressResult[v.size()];
            for (int i = 0; i < v.size(); i++) {
                arr[i] = v.get(i);
            }

        } catch (Exception E) {
            E.printStackTrace();
        }

        return arr;
    }

    @Override
    public QueryResult.UserIDuserNameAddressResult[] getUsersWithAtLeastTwoDifferentVaccineTypeByGivenInterval(String startdate, String enddate) {
        QueryResult.UserIDuserNameAddressResult[] arr = new QueryResult.UserIDuserNameAddressResult[1];
        Vector<QueryResult.UserIDuserNameAddressResult> v = new Vector();

        try {
            Statement stmt1 = connection.createStatement();
            ResultSet rs;
            String sql = "SELECT DISTINCT U.userID, U.userName, U.address " +
                    "FROM User U, Vaccination V1, Vaccination V2 " +
                    "WHERE U.userID = V1.userID AND U.userID = V2.userID AND V1.code <> V2.code AND V1.vacdate >='" + startdate + "' AND V1.vacdate <='" + enddate +
                    "'                                                                            AND V2.vacdate >='" + startdate +  "'AND V2.vacdate <='" + enddate + "' " +
                    "                                                                             ORDER BY U.userID ASC;";

            rs = stmt1.executeQuery(sql);

            while (rs.next()) {
                QueryResult.UserIDuserNameAddressResult res = new QueryResult.UserIDuserNameAddressResult(rs.getString(1), rs.getString(2),rs.getString(3));
                v.add(res);
            }
            arr = new QueryResult.UserIDuserNameAddressResult[v.size()];
            for (int i = 0; i < v.size(); i++) {
                arr[i] = v.get(i);
            }

        } catch (Exception E) {
            E.printStackTrace();
        }

        return arr;
    }

    @Override
    public AllergicSideEffect[] getSideEffectsOfUserWhoHaveTwoDosesInLessThanTwentyDays() {
        Vector<AllergicSideEffect> v = new Vector();
        AllergicSideEffect[] arr = new AllergicSideEffect[1];
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs;
            String sql = "SELECT A.effectcode, A.effectname " +
                    "FROM Seen S, AllergicSideEffect A, (SELECT Temp.userID, Temp.userName, Temp.address, COUNT(*) AS count  " +
                    "    FROM(SELECT U1.userID, U1.userName, U1.address, V1.vacdate  " +
                    "        FROM Vaccination V1, User U1  " +
                    "        WHERE V1.userID = U1.userID) AS Temp  " +
                    "    WHERE Temp.userID IN (SELECT v1.userID " +
                    "                            FROM Vaccination v1, Vaccination v2 " +
                    "                        WHERE v1.userID = v2.userID AND v1.dose-v2.dose = 1 AND v1.vacdate > v2.vacdate AND DATEDIFF(v1.vacdate,v2.vacdate) < 20) " +
                    " " +
                    "    GROUP BY Temp.userID  " +
                    "    HAVING count >= 2 ORDER BY Temp.userID ASC) AS Temp2 " +
                    " " +
                    "WHERE A.effectcode = S.effectcode AND S.userID = Temp2.userID " +
                    "ORDER BY effectcode ASC;";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                AllergicSideEffect alle = new AllergicSideEffect(rs.getInt(1),rs.getString(2));
                v.add(alle);
            }
            arr = new AllergicSideEffect[v.size()];
            for (int i = 0; i < v.size(); i++) {
                arr[i] = v.get(i);
            }

        } catch (Exception E) {
            E.printStackTrace();
        }
        return arr;
    }

    @Override
    public double averageNumberofDosesofVaccinatedUserOverSixtyFiveYearsOld() {
        double res=0;
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs;
            String sql = "SELECT AVG(Temp.count) AS Average " +
                    "FROM (SELECT U.userID, U.userName, U.address, COUNT(*) AS count " +
                    "FROM Vaccination V, User U " +
                    "WHERE V.userID = U.userID AND U.age > 65 " +
                    "GROUP BY V.userID) AS Temp;";
            rs = stmt.executeQuery(sql);
            rs.next();
            res = rs.getDouble(1);

        } catch (Exception E) {
            E.printStackTrace();
        }
        return res;
    }

    @Override
    public int updateStatusToEligible(String givendate) {
        int count = 0;
        try {
            Statement stmt = connection.createStatement();
            String sql = "UPDATE User " +
                    "SET status = 'eligible'  " +
                    "WHERE status = 'Not_Eligible' AND userID IN (SELECT V.userID " +
                    "                                            FROM Vaccination V " +
                    "                                            WHERE V.vacdate = (SELECT MAX(V1.vacdate) " +
                    "                                                                FROM Vaccination V1 " +
                    "                                                                WHERE V1.userID = V.userID " +
                    "                                                                GROUP BY V1.userID) AND DATEDIFF('" + givendate + "', V.vacdate) > 120);";
            stmt.executeUpdate(sql);
            count = stmt.getUpdateCount();
        } catch (Exception E) {
            E.printStackTrace();
        }
        return count;
    }

    @Override
    public Vaccine deleteVaccine(String vaccineName) {


        Vaccine v = null;
        try {
            Statement stmt = connection.createStatement();
            String sql1 = "SELECT * " +
                    "FROM Vaccine V " +
                    "WHERE V.vaccinename = '" + vaccineName + "';";
            ResultSet rs = stmt.executeQuery(sql1);
            rs.next();
            v = new Vaccine(rs.getInt(1), rs.getString(2), rs.getString(3));

            String sql = "DELETE FROM Vaccine " +
                    "WHERE vaccinename = '" + vaccineName + "';";

            stmt.executeUpdate(sql);
        } catch (Exception E) {
            E.printStackTrace();
        }
        return v;
    }
}