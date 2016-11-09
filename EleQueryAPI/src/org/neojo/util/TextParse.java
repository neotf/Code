package org.neojo.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.neojo.entity.Electricity;

import com.google.gson.Gson;

public class TextParse {
	public static String parseViewState(String html) {
		String startMark = "id=\"__VIEWSTATE\" value=\"";
		String endMark = "\" />";
		int index = html.indexOf(startMark) + startMark.length();
		String __VIEWSTATE = html.substring(index, index + html.substring(index).indexOf(endMark));
		return __VIEWSTATE;
	}

	public static String parseEventValidation(String html) {
		String startMark = "id=\"__EVENTVALIDATION\" value=\"";
		String endMark = "\" />";
		int index = html.indexOf(startMark) + startMark.length();
		String __EVENTVALIDATION = html.substring(index, index + html.substring(index).indexOf(endMark));
		return __EVENTVALIDATION;
	}

	public static String parseBalance(String html) {
		String startMark = "<h6>";
		String endMark = "</h6>";
		int index = html.indexOf(startMark) + startMark.length();
		html = html.substring(index, index + html.substring(index).indexOf(endMark));
		html = html.replaceAll("<[^<]*>", "");
		startMark = "：";
		endMark = "度";
		index = html.indexOf(startMark) + startMark.length();
		float normal = Float.valueOf(html.substring(index, index + html.substring(index).indexOf(endMark)));
		index = html.lastIndexOf(startMark) + startMark.length();
		float airConditioner = Float.valueOf(html.substring(index, index + html.substring(index).lastIndexOf(endMark)));
		return new Gson().toJson(new Electricity(normal, airConditioner));
	}

	public static String parseUsedLog(String html) {
		String startMark = "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" class=\"dataTable\">";
		String endMark = "</table>";
		int index = html.indexOf(startMark) + startMark.length();
		String log = html.substring(index, index + html.substring(index).indexOf(endMark));
		log = log.replaceAll("[\\s]*", "");
		log = log.replaceAll("<td>0.60</td></tr><trclass=\"contentLine\">", "\"},{\"date\":\"");
		log = log.replace("<td>0.60</td></tr>", "\"}");
		log = log.replaceAll("<td>[^<]*-电表</td>", "\",\"used\":\"");
		log = log.replaceAll("<[^<]*>", "");
		log = log.replace("日期电表名称用量(度)单价(元/度)", "{\"date\":\"");
		return log;
	}

	public static String parseBuyLog(String html) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat oldformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String startMark = "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" class=\"dataTable\">";
		String endMark = "</table>";
		int index = html.indexOf(startMark);
		html = html.substring(index, index + html.substring(index).indexOf(endMark) + endMark.length());
		Document doc = Jsoup.parse(html);
		Elements trs = doc.select("tr");
		StringBuffer json = new StringBuffer();
		for (int i = 1; i < trs.size(); i++) {
			Elements tds = trs.get(i).select("td");
			for (int j = 0; j < tds.size(); j++) {
				if (tds.get(2).text().equals("0.00")) {
					break;
				}
				String text = "";
				switch (j) {
				case 0:
					if (i == 1) {
						text = "[{\"date\":\"" + format.format(oldformat.parse(tds.get(j).text(), new ParsePosition(0)))
						+ "\",";
					} else {
						text = "{\"date\":\"" + format.format(oldformat.parse(tds.get(j).text(), new ParsePosition(0)))
								+ "\",";
					}
					break;
				case 2:
					text = "\"kWh\":\"" + tds.get(j).text() + "\",";
					break;
				case 3:
					text = "\"value\":\"" + tds.get(j).text() + "\",";
					break;
				case 4:
					if (i == trs.size() - 2) {
						text = "\"type\":\"" + (tds.get(j).text().equals("补助模板") ? "gift\",\"opt\":\"Systen" : "pay\",\"opt\":\"" + tds.get(j).text())
								+ "\"}]";
					} else {
						text = "\"type\":\"" + (tds.get(j).text().equals("补助模板") ? "gift\",\"opt\":\"System" : "pay\",\"opt\":\"" + tds.get(j).text())
								+ "\"},";
					}
					break;
				default:
					break;
				}
				json.append(text);
			}
		}
		return json.toString();
	}
}
