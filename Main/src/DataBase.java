import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataBase {

        private static final String URL = "jdbc:mariadb://localhost:3306/oyun_db";
        private static final String USER = "root"; // Kendi kullanıcı adın
        private static final String PASSWORD = "Yusuf1453*"; // Kendi şifren
        // Bağlantı oluşturma metodu
        private Connection connect() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }

        // Yeni skor kaydetme
        public void saveScore(String playerName, int score) {
            String sql = "INSERT INTO scores(player_name, score) VALUES(?, ?)";

            try (Connection conn = this.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, playerName);
                pstmt.setInt(2, score);
                pstmt.executeUpdate();
                System.out.println("Skor başarıyla kaydedildi.");

            } catch (SQLException e) {
                System.out.println("Hata (Kaydetme): " + e.getMessage());
            }
        }

        // Skorları büyükten küçüğe getirme
        public List<Map.Entry<String,Integer>> getTopScores() {
            String sql = "SELECT player_name, score FROM scores ORDER BY score DESC LIMIT 10";
            HashMap<String,Integer> names_scores=new HashMap<>();
                try (Connection conn = this.connect();
                 Statement stmt  = conn.createStatement();
                 ResultSet rs    = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    names_scores.put(rs.getString("player_name") ,rs.getInt("score"));
                }
            } catch (SQLException e) {
                System.out.println("Hata (Listeleme): " + e.getMessage());
            }
            return new ArrayList<>(names_scores.entrySet());
        }

}
