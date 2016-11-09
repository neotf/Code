package org.neojo.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.neojo.Exception.LoginException;
import org.neojo.entity.Dormitory;
import org.neojo.util.TextParse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public final class ElectricityQuery {
	private static final String URL_PATH_LOGIN = "http://58.47.143.9:20002/Default.aspx";
	private static final String URL_PATH_GET_USED = "http://58.47.143.9:20002/usedRecord.aspx";
	private static final String URL_PATH_GET_BUY = "http://58.47.143.9:20002/buyRecord.aspx";

	private CloseableHttpClient httpclient;
	private HttpPost httpPost;
	private HttpGet httpGet;
	private HttpEntity httpEntity;
	private HttpResponse httpResponse;
	private int HttpStatus;
	private String __VIEWSTATE = "";
	private String __EVENTVALIDATION = "";
	private String drlouming;
	private String drceng;
	private String drfangjian;
	private String type;

	public ElectricityQuery(Dormitory dormitoryModel) {
		drlouming = "01" + Dormitory.formatBuild(dormitoryModel.getBuild());
		drceng = drlouming + Dormitory.formatFloor(dormitoryModel.getFloor());
		drfangjian = drceng + Dormitory.formatRoom(dormitoryModel.getRoom());
	}

	private String GetUsedPage() throws ClientProtocolException, IOException {
		httpGet = new HttpGet(URL_PATH_GET_USED);
		httpResponse = httpclient.execute(httpGet);
		httpEntity = httpResponse.getEntity();
		String html = EntityUtils.toString(httpEntity);
		__VIEWSTATE = TextParse.parseViewState(html);
		__EVENTVALIDATION = TextParse.parseEventValidation(html);
		return html;
	}

	private String GetUsedLog(int day) throws ClientProtocolException, IOException {
		int page = (day - 1) / 10 + 1;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar tempfirstday = Calendar.getInstance();
		Calendar templastday = Calendar.getInstance();
		templastday.add(Calendar.DAY_OF_MONTH, -day + 1);
		String lastday;
		String firstday;
		StringBuffer sb = new StringBuffer();
		httpPost = new HttpPost(URL_PATH_GET_USED);
		String[] ret = new String[page];
		for (int i = 1; i <= page; i++) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("__VIEWSTATE", __VIEWSTATE));
			params.add(new BasicNameValuePair("__EVENTVALIDATION", __EVENTVALIDATION));
			lastday = format.format(templastday.getTime());
			params.add(new BasicNameValuePair("txtstart", lastday));
			tempfirstday = (Calendar) templastday.clone();
			tempfirstday.add(Calendar.DAY_OF_MONTH, 9);
			templastday = (Calendar) tempfirstday.clone();
			templastday.add(Calendar.DAY_OF_MONTH, 1);
			if (i != page) {
				firstday = format.format(tempfirstday.getTime());
			} else {
				firstday = format.format(Calendar.getInstance().getTime());
			}
			params.add(new BasicNameValuePair("txtend", firstday));
			params.add(new BasicNameValuePair("btnser", "查询"));
			UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(params, "UTF-8");
			httpPost.setEntity(urlEncodedFormEntity);
			httpResponse = httpclient.execute(httpPost);
			HttpStatus = httpResponse.getStatusLine().getStatusCode();
			httpEntity = httpResponse.getEntity();
			String html = EntityUtils.toString(httpEntity);
			ret[i - 1] = TextParse.parseUsedLog(html);
		}
		for (int i = ret.length; i > 0; i--) {
			if (i == ret.length)
				sb.append("[");
			sb.append(ret[i - 1]);
			if (i == 1) {
				sb.append("]");
			} else {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	private String GetBuyPage() throws ClientProtocolException, IOException {
		httpGet = new HttpGet(URL_PATH_GET_BUY);
		httpResponse = httpclient.execute(httpGet);
		httpEntity = httpResponse.getEntity();
		String html = EntityUtils.toString(httpEntity);
		__VIEWSTATE = TextParse.parseViewState(html);
		__EVENTVALIDATION = TextParse.parseEventValidation(html);
		return html;
	}

	private String GetBuyLog(int day) throws ClientProtocolException, IOException {
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -day +1 );
		httpPost = new HttpPost(URL_PATH_GET_BUY);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("__VIEWSTATE", __VIEWSTATE));
		params.add(new BasicNameValuePair("__EVENTVALIDATION", __EVENTVALIDATION));
		params.add(new BasicNameValuePair("txtstart", format.format(cal.getTime())));
		params.add(new BasicNameValuePair("txtend", format.format(Calendar.getInstance().getTime())));
		params.add(new BasicNameValuePair("btnser", "查询"));
		UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(params, "UTF-8");
		httpPost.setEntity(urlEncodedFormEntity);
		httpResponse = httpclient.execute(httpPost);
		HttpStatus = httpResponse.getStatusLine().getStatusCode();
		httpEntity = httpResponse.getEntity();
		String html = EntityUtils.toString(httpEntity);
		
		return html;
	}

	private void Login(String __EVENTTARGET) throws ClientProtocolException, IOException, LoginException {
		httpclient = HttpClients.createDefault();
		httpPost = new HttpPost(URL_PATH_LOGIN);
		if (!"".equals(__EVENTTARGET)) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(
					new BasicNameValuePair("__EVENTTARGET", "drfangjian".equals(__EVENTTARGET) ? "" : __EVENTTARGET));
			params.add(new BasicNameValuePair("__EVENTARGUMENT", ""));
			params.add(new BasicNameValuePair("__LASTFOCUS", ""));
			params.add(new BasicNameValuePair("__VIEWSTATE", __VIEWSTATE));
			params.add(new BasicNameValuePair("drlouming", drlouming));
			params.add(new BasicNameValuePair("drceng", drceng));
			params.add(new BasicNameValuePair("drfangjian", drfangjian));
			if ("drfangjian".equals(__EVENTTARGET)) {
				params.add(new BasicNameValuePair("radio", type));
				params.add(new BasicNameValuePair("ImageButton1.x", "10"));
				params.add(new BasicNameValuePair("ImageButton1.y", "10"));
			}
			UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(params, "UTF-8");
			httpPost.setEntity(urlEncodedFormEntity);
		}
		httpResponse = httpclient.execute(httpPost);
		HttpStatus = httpResponse.getStatusLine().getStatusCode();
		httpEntity = httpResponse.getEntity();
		if (HttpStatus == 200) {
			String html = EntityUtils.toString(httpEntity);
			__VIEWSTATE = TextParse.parseViewState(html);
			if ("".equals(__EVENTTARGET)) {
				Login("drlouming");
			} else if ("drlouming".equals(__EVENTTARGET)) {
				Login("drceng");
			} else if ("drceng".equals(__EVENTTARGET)) {
				Login("drfangjian");
			} else {
				throw new LoginException();
			}
		} else if (HttpStatus == 302) {
			httpPost.abort();
		} else {
			// TODO Exception
		}
	}

	private void fastLogin(String __VIEWSTATE) throws ClientProtocolException, IOException, LoginException {
		httpclient = HttpClients.createDefault();
		httpPost = new HttpPost(URL_PATH_LOGIN);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("__EVENTTARGET", ""));
		params.add(new BasicNameValuePair("__EVENTARGUMENT", ""));
		params.add(new BasicNameValuePair("__LASTFOCUS", ""));
		params.add(new BasicNameValuePair("__VIEWSTATE", __VIEWSTATE));
		params.add(new BasicNameValuePair("drlouming", drlouming));
		params.add(new BasicNameValuePair("drceng", drceng));
		params.add(new BasicNameValuePair("drfangjian", drfangjian));
		params.add(new BasicNameValuePair("radio", type));
		params.add(new BasicNameValuePair("ImageButton1.x", "10"));
		params.add(new BasicNameValuePair("ImageButton1.y", "10"));
		UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(params, "UTF-8");
		httpPost.setEntity(urlEncodedFormEntity);
		httpResponse = httpclient.execute(httpPost);
		HttpStatus = httpResponse.getStatusLine().getStatusCode();
		httpEntity = httpResponse.getEntity();
		if (HttpStatus == 302) {
			httpPost.abort();
		} else {
			throw new LoginException();
		}
	}

	private String queryBalance() throws ClientProtocolException, IOException {
		return TextParse.parseBalance(GetUsedPage());
	}

	private String queryUsedLog(int day) throws ClientProtocolException, IOException {
		return GetUsedLog(day);
	}

	private String queryBuyLog(int day) throws ClientProtocolException, IOException {
		return TextParse.parseBuyLog(GetBuyLog(day));
	}

	public String getViewState() throws ClientProtocolException, IOException, LoginException {
		this.type = "usedR";
		Login("");
		return __VIEWSTATE;
	}

	public String checkBalance() throws ClientProtocolException, IOException, LoginException {
		this.type = "usedR";
		Login("");
		return queryBalance();
	}

	public String checkBalance(String __VIEWSTATE) throws ClientProtocolException, IOException, LoginException {
		this.type = "usedR";
		fastLogin(__VIEWSTATE);
		return queryBalance();
	}

	public String checkUsedLog(int day) throws ClientProtocolException, IOException, LoginException {
		this.type = "usedR";
		Login("");
		GetUsedPage();
		return queryUsedLog(day);
	}

	public String checkUsedLog(String __VIEWSTATE, int day)
			throws ClientProtocolException, IOException, LoginException {
		this.type = "usedR";
		Login("");
		GetUsedPage();
		return queryUsedLog(day);
	}

	public String checkBuyLog(int day) throws ClientProtocolException, IOException, LoginException {
		this.type = "buyR";
		Login("");
		GetBuyPage();
		return queryBuyLog(day);
	}

	public String checkBuyLog(String __VIEWSTATE, int day) throws ClientProtocolException, IOException, LoginException {
		this.type = "buyR";
		Login("");
		GetBuyPage();
		return queryBuyLog(day);
	}
}
