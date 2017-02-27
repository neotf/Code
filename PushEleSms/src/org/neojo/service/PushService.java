package org.neojo.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.neojo.dao.ListPush;
import org.neojo.entity.Electricity;
import org.neojo.entity.Pusher;
import org.neojo.entity.Result;
import org.neojo.util.SmsSend;
import org.neojo.util.GetEleInfo;

import com.google.gson.Gson;

public class PushService {
	
	private static void service() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ListPush lp = new ListPush();
		List<Pusher> pushlist = lp.getPushList();
		int sum = 0;
		for (Pusher c : pushlist) {
			Gson gson = new Gson();
			Electricity ele = new Electricity();
			String json = GetEleInfo.getEle(c.getBuild(), c.getFloor(), c.getRoom(), c.getViewState());
			ele = gson.fromJson(json, Result.class).getData();
			
			if (ele.getNormal() == null) {
				Result rs = gson.fromJson(json, Result.class);
				System.out.println("[" + format.format(Calendar.getInstance().getTime()) + "][查询失败 ]失败代码："+rs.getCode()+":"+rs.getMsg());
			} else {
				if (ele.getNormal() <= 30) {
					String msg = "您好！您的剩余普通用电" + ele.getNormal() + "度，空调用电" + ele.getAirConditioner()
							+ "度，请及时充值。【黑箱科技】";
					Result rs = SmsSend.sendPost(c.getPhone(), msg);
					if (rs.getCode() > 0)
						sum++;
					System.out.println("[" + format.format(Calendar.getInstance().getTime()) + "]["
							+ (rs.getCode() > 0 ? "推送成功" : "推送失败") + "]余额警告：" + rs.getMsg() + ":" + c.getBuild() + "-"
							+ c.getFloor() + "-" + c.getRoom() + ":" + ele.getNormal() + "," + ele.getAirConditioner());
				} else {
					if ("daily".equals(c.getType())) {
						String msg = "您好！您的剩余普通用电" + ele.getNormal() + "度，空调用电" + ele.getAirConditioner()
								+ "度，黑箱科技将为您持续关注您的用电情况。【黑箱科技】";
						Result rs = SmsSend.sendPost(c.getPhone(), msg);
						if (rs.getCode() > 0)
							sum++;
						System.out.println("[" + format.format(Calendar.getInstance().getTime()) + "]["
								+ (rs.getCode() > 0 ? "推送成功" : "推送失败") + "]余额提醒：" + rs.getMsg() + ":" + c.getBuild() + "-"
								+ c.getFloor() + "-" + c.getRoom() + ":" + ele.getNormal() + ","
								+ ele.getAirConditioner());
					} else {
						System.out.println("[" + format.format(Calendar.getInstance().getTime()) + "]余额充足不推送："
								+ c.getBuild() + "-" + c.getFloor() + "-" + c.getRoom() + ":" + ele.getNormal() + ","
								+ ele.getAirConditioner());
					}
				}
			}
		}
		System.out.println("[" + format.format(Calendar.getInstance().getTime()) + "]今日查询：" + pushlist.size() + "次，推送："
				+ sum + "次");
	}

	public static void main(String[] args) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("[" + format.format(Calendar.getInstance().getTime()) + "]推送服务开始");
		Calendar calendar = Calendar.getInstance();
		int hour = args.length != 0 ? Integer.parseInt(args[0]) : 7;
		int min = args.length != 0 ? Integer.parseInt(args[1]) : 50;
		int snd = args.length != 0 ? Integer.parseInt(args[2]) : 0;
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, min);
		calendar.set(Calendar.SECOND, snd);
		if (calendar.getTime().before(Calendar.getInstance().getTime())) {
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
		}
		Date time = calendar.getTime();
		System.out.println("[" + format.format(Calendar.getInstance().getTime()) + "]下次执行:" + format.format(time));
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				service();
				System.out.println("[" + format.format(Calendar.getInstance().getTime()) + "]执行完毕");
			}
		}, time, 24 * 60 * 60 * 1000);

	}
}
