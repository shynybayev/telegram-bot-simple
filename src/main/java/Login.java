import java.sql.*;
import java.util.ArrayList;

public class Login {
    private Connection con;
    private Statement statement;
    private ResultSet resultSet;

    String connectionURL = "https://localhost/surgebook?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    public Login(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/SurgeBook", "root", "root");
            statement = con.createStatement();
        } catch (Exception e) {
            System.err.println("doesn't connect to Database");;
        }
    }

    public String add(String name, String chatId){
        String sql = "INSERT INTO users(name, chat_id) VALUES(?,?)";
        PreparedStatement st;
        try {
            st = con.prepareStatement(sql);
            st.setString(1, name);
            st.setString(2, chatId);
            st.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to add information");
        }
        return "Information added";
    }

    public void change(String newName, String chatId){
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("UPDATE users SET name = ? WHERE chat_id = ?");
            st.setString(1, newName);
            st.setString(2, chatId);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList getChatId(){
        ArrayList<String> list = null;

        try {
            list = new ArrayList<>();
            PreparedStatement st = null;
            String query = "SELECT * FROM users";
            st = con.prepareStatement(query);
            resultSet = st.executeQuery();

            while (resultSet.next()){
                list.add(resultSet.getString("chat_id"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    public void remove(String name){
        PreparedStatement st;
        try {
            st = con.prepareStatement("DELETE * FROM users WHERE name = ?");
            st.setString(1, name);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
