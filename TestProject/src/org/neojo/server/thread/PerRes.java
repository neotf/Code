package org.neojo.server.thread;

import java.util.List;

import org.neojo.server.entity.Finance;
import org.neojo.server.entity.Order;

public class PerRes {
	static boolean available = false;
	public static boolean isAvailable() {
		return available;
	}

	static List<Finance> flist;

	public static List<Finance> getFlist() {
		return flist;
	}

	public static void setFlist(List<Finance> flist) {
		PerRes.flist = flist;
	}
	static List<Order> olist;
	public static List<Order> getOlist() {
		return olist;
	}

	public static void setOlist(List<Order> olist) {
		PerRes.olist = olist;
	}
	
}
