import java.sql.*;
public class Connexion
{
    public static Connection connecterDB()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            String url="jdbc:mysql://localhost:8889/projetdist";
            String user="root";
            String password="root";
            Connection cnx=DriverManager.getConnection(url,user,password);
            return cnx;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}