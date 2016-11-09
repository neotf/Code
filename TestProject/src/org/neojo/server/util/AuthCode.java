package org.neojo.server.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.neojo.server.entity.Result;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AuthCode {
	public static Result sendPost(String phone, String msg) {
		PrintWriter out = null;
		BufferedReader in = null;
		Result rs = new Result();
		try {
			URL realUrl = new URL("http://fico.heixiangkj.com/sms.php");
			URLConnection conn = realUrl.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			out = new PrintWriter(conn.getOutputStream());

			String param = "id=test&num=" + phone + "&msg=" + msg;

			out.print(param);
			out.flush();
			InputStream is = conn.getInputStream();
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(is);
			Element element = doc.getDocumentElement();
			NodeList childNodes = element.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node node = childNodes.item(i);
				if ("result".equals(node.getNodeName()))
					rs.setId(Integer.parseInt(childNodes.item(i).getTextContent()));
				if ("message".equals(node.getNodeName()))
					rs.setMsg(childNodes.item(i).getTextContent());
			}
		}catch (ConnectException e){
			rs.setId(-1000);
			rs.setMsg("调用错误：网络连接失败");
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
