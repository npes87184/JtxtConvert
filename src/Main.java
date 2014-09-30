import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Main {

	private static final String APP_NAME = "JtxtConvert";
	private static final double version = 1.0;
	private static String versionString = " ";
	private static double temp_version;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainFrame mainFrame = new MainFrame();
		mainFrame.setVisible(true);
		checkVersion();
	}
	private static void checkVersion() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					URL url = new URL("http://www.cmlab.csie.ntu.edu.tw/~npes87184/version.csv");
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					connection
					.setRequestProperty(
							"User-Agent",
							"Mozilla/5.0 (Linux; U; Android 4.0.3; zh-tw; HTC_Sensation_Z710e Build/IML74K)AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");
					connection.connect();
					InputStream inStream = (InputStream) connection.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "BIG5"));
					String line = "";
					while ((line = reader.readLine()) != null) {
						String [] data = line.split(",");
						if(data[0].equals(APP_NAME)) {
							temp_version = Double.parseDouble(data[1]);
							
							boolean first = true;
							boolean second = true;
							int i = 1;
							for(String aString : data) {
								if(first) {
									first = false;
									continue;
								}
								if(second) {
									versionString = versionString + "New Version Name:" + aString;
									versionString = versionString + "<br>";
									second = false;
									continue;
								}
								versionString = versionString + "      " + i + "." +aString;
								versionString = versionString + "<br>";
								i++;
							}
							break;
						}
					}
					if(temp_version > version) {
						Ota ota = new Ota(versionString);
						ota.setVisible(true);
					}
				} catch(Exception e) {
					System.out.println("error");
				}
			}
		}).start();
	}
	

}
