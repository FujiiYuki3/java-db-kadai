package kadai_007;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Posts_Chapter07 {

	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement setStatement = null;
		Statement getStatement = null;
		
		String[][] postsList = {
				{"1003", "2023-02-08", "昨日の夜は徹夜でした・・", "13"},
				{"1002", "2023-02-08", "お疲れ様です！", "12"},
				{"1003", "2023-02-09", "今日も頑張ります！", "18"},
				{"1001", "2023-02-09", "無理は禁物ですよ！", "17"},
				{"1002", "2023-02-10", "明日から連休ですね！", "20"},
		};
		
		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/challenge_java",
					"root",
					"Gfooooo19941117");
			System.out.println("データベース接続成功：" + con);
			
			String setSql = "INSERT INTO posts(user_id, posted_at, post_content, likes) VALUES(?, ?, ?, ?);";
			setStatement = con.prepareStatement(setSql);
			
			int rowCnt = 0;
			for(int i = 0; i < postsList.length; i++) {
				setStatement.setString(1, postsList[i][0]);
				setStatement.setString(2, postsList[i][1]);
				setStatement.setString(3, postsList[i][2]);
				setStatement.setString(4, postsList[i][3]);
				
				rowCnt = setStatement.executeUpdate();
			}
			
			System.out.println("レコード追加を実施します");
			System.out.println(postsList.length + "件のレコードが追加されました");
			
			getStatement = con.createStatement();
			String getSql = "SELECT posted_at, post_content, likes FROM posts WHERE user_id = 1002;";
			
			ResultSet result = getStatement.executeQuery(getSql);
			
			while(result.next()) {
				Date postedAt = result.getDate("posted_at");
				String postContent = result.getString("post_content");
				int likes = result.getInt("likes");
				System.out.println(result.getRow() + "件目：投稿日時=" + postedAt + "／投稿内容=" + postContent + "／いいね数=" + likes);
			}
			
			
		}catch(SQLException e) {
			System.out.println("エラー発生：" + e.getMessage());
		}finally {
			if(setStatement != null) {
				try {setStatement.close();}catch(SQLException ignore) {}
			}
			if(getStatement != null) {
				try {getStatement.close();}catch(SQLException ignore) {}
			}
			if(con != null) {
				try {con.close();}catch(SQLException ignore) {}
			}
		}

	}

}
