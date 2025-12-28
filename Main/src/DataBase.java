import java.sql.*;
import java.util.*;

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
        public List<Map.Entry<String, Integer>> getTopScores() {
            String sql = "SELECT player_name, score FROM scores ORDER BY score DESC LIMIT 10";
            // HashMap yerine List kullanarak sıralamayı ve aynı isimli oyuncuları koruyoruz
            List<Map.Entry<String, Integer>> scoreList = new ArrayList<>();

            try (Connection conn = this.connect();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    String pName = rs.getString("player_name");
                    int pScore = rs.getInt("score");
                    // Her satırı bir Entry olarak listeye ekle
                    scoreList.add(new AbstractMap.SimpleEntry<>(pName, pScore));
                }
            } catch (SQLException e) {
                System.out.println("Hata (Listeleme): " + e.getMessage());
            }
            return scoreList;
        }

}
