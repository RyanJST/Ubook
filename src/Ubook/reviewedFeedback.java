package Ubook;

public class reviewedFeedback {
	
	public String lFid = null;
	public String lScore = null;
	public String lText = null;
	public String lLogin = null;
	public String lFBdate = null;
	public String lAvgScore = null;
	public reviewedFeedback(String fid, String score, String text, String login, String fbdate,
			String avg_score) {
		// TODO Auto-generated constructor stub
		lFid = fid;
		lScore = score;
		lText = text;
		lLogin = login;
		lFBdate = fbdate;
		lAvgScore = avg_score;
	}

}
