package org.neojo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;

public class GetEleInfo {

	public static String getEle(int build, int floor, int room, String __VIEWSTATE) {
		PrintWriter out = null;
		BufferedReader in = null;
		String rs = null;
		try {
			URL realUrl = new URL("http://localhost:8080/EleQueryAPI/Query");
			SSLTrust.ignoreSsl();
			URLConnection conn = realUrl.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			out = new PrintWriter(conn.getOutputStream());
			__VIEWSTATE = __VIEWSTATE.replaceAll("\\+", "2B%");
			String param = "build=" + build + "&floor=" + floor + "&room=" + room + "&type=balance" + (__VIEWSTATE==null ? "" : ("&vs=" + __VIEWSTATE));
			out.print(param);
			out.flush();
			InputStream is = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();

			String line = null;
			try {
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			rs = sb.toString();
		} catch (ConnectException e) {
			// rs.setId(-1000);
			// rs.setMsg("调用错误：网络连接失败");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return rs;
	}
}
