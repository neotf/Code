package org.neojo.server.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.neojo.server.action.Action;
import org.neojo.server.entity.OPType;
import org.neojo.server.entity.User;
import org.neojo.server.factory.BeanFactory;

public class BaseResLoad extends Thread {
	private Action act = BeanFactory.getActionInstance("ActionImpl");

	public void updateUser(){
		Map<Integer, User> UserListMap = new HashMap<>();
		Vector<User> uv = act.getAllUserUid();
		for (int i = 0; i < uv.size(); i++) {
			UserListMap.put(uv.get(i).getUid(), uv.get(i));
		}
		BaseRes.UserListMap = UserListMap;
		BaseRes.User = uv;
	}
	@Override
	public void run() {
		if (!BaseRes.available) {
			updateUser();
			Map<Integer, OPType> FinTypeMap = new HashMap<>();
			Vector<OPType> ftp = act.queryFinType();
			for (int i = 0; i < ftp.size(); i++) {
				FinTypeMap.put(ftp.get(i).getTid(), ftp.get(i));
			}
			BaseRes.FinType = ftp;
			BaseRes.FinTypeMap = FinTypeMap;
			
			Map<Integer, OPType> PayTypeMap = new HashMap<>();
			Vector<OPType> ptp = act.queryPayType();
			for (int i = 0; i < ptp.size(); i++) {
				PayTypeMap.put(ptp.get(i).getTid(), ptp.get(i));
			}
			BaseRes.PayType = ptp;
			BaseRes.PayTypeMap = PayTypeMap;
			
			Map<Integer, OPType> OrderTypeMap = new HashMap<>();
			Vector<OPType> otp = act.queryOrderType();
			for (int i = 0; i < otp.size(); i++) {
				OrderTypeMap.put(otp.get(i).getTid(), otp.get(i));
			}
			BaseRes.OrderType = otp;
			BaseRes.OrderTypeMap = OrderTypeMap;
			
			Map<Integer, OPType> OrderStatusMap = new HashMap<>();
			Vector<OPType> osp = act.queryOrderStatus();
			for (int i = 0; i < osp.size(); i++) {
				OrderStatusMap.put(osp.get(i).getTid(), osp.get(i));
			}
			BaseRes.OrderStatus = osp;
			BaseRes.OrderStatusMap = OrderStatusMap;
			
			Map<Integer, OPType> FinStatusMap = new HashMap<>();
			Vector<OPType> fsp = act.queryFinStatus();
			for (int i = 0; i < fsp.size(); i++) {
				FinStatusMap.put(fsp.get(i).getTid(), fsp.get(i));
			}
			BaseRes.FinStatus = fsp;
			BaseRes.FinStatusMap = FinStatusMap;
			
			BaseRes.orddate = act.QueryOrderDate();
			BaseRes.findate = act.QueryFinanceDate();
			
			BaseRes.available = true;
		}
	}
}
