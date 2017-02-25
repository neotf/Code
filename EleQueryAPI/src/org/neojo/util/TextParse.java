package org.neojo.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.neojo.Exception.CheckException;
import org.neojo.entity.BuyLog;
import org.neojo.entity.Electricity;
import org.neojo.entity.UseLog;

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
	
	public static int getPage(String html){
		String startMark = "第 1 页 / 共 ";
		String endMark = " 页  每页 10 条";
		int index = html.indexOf(startMark) + endMark.length()-1;
		html = html.substring(index, index + html.substring(index).indexOf(endMark));
		return Integer.parseInt(html);
	}

	public static Electricity parseBalance(String html) {
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
		return new Electricity(normal, airConditioner);
	}

	public static List<UseLog> parseUsedLog(String html) {
		String startMark = "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" class=\"dataTable\">";
		String endMark = "</table>";
		int index = html.indexOf(startMark);
		html = html.substring(index, index + html.substring(index).indexOf(endMark) + endMark.length());
		Document doc = Jsoup.parse(html);
		Elements trs = doc.select("tr");
		List<UseLog> uselogs = new ArrayList<>();
		for (int i = 1; i < trs.size(); i++) {
			Elements tds = trs.get(i).select("td");
			uselogs.add(new UseLog(tds.get(0).text(), Float.parseFloat(tds.get(2).text())));
		}
		return uselogs;
	}

	public static List<BuyLog> parseBuyLog(String html) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String startMark = "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" class=\"dataTable\">";
		String endMark = "</table>";
		int index = html.indexOf(startMark);
		html = html.substring(index, index + html.substring(index).indexOf(endMark) + endMark.length());
		Document doc = Jsoup.parse(html);
		Elements trs = doc.select("tr");
		List<BuyLog> buylogs = new ArrayList<>();
		for (int i = 1; i < trs.size(); i++) {
			Elements tds = trs.get(i).select("td");
				if (!tds.get(2).text().equals("0.00")) {
					buylogs.add(new BuyLog(format.parse(tds.get(0).text(), new ParsePosition(0)), Float.parseFloat(tds.get(2).text()), Float.parseFloat(tds.get(3).text()), tds.get(4).text().equals("补助模板") ? 0 : 1 , tds.get(4).text()));
				}
		}
		return buylogs;
	}
	
	public static String checkhtml(String html) throws CheckException{
		if(html.indexOf("网络繁忙，请稍后再试")>=0){
			throw new CheckException("网络繁忙，请稍后再试");
		}else{
			return null;
		}
	}
}
