package org.neojo.server.thread;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.neojo.server.action.Action;
import org.neojo.server.factory.BeanFactory;

public class PerResLoad extends Thread {
	private Action act = BeanFactory.getActionInstance("ActionImpl");

	@Override
	public void run() {
		if (!PerRes.available) {
			PerRes.flist = act.getFinancebyMonth(new SimpleDateFormat("yyyyMM").format(new Date()));
			PerRes.olist = act.getOrderbyMonth(new SimpleDateFormat("yyyyMM").format(new Date()));
			PerRes.available = true;
		}
	}
}
