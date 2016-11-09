package org.neojo.server.view;

import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import org.neojo.server.thread.BaseRes;
import org.neojo.server.thread.PerResLoad;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;

public class Main extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JMenuItem queryFinance;
	private JMenuItem queryOrder;
	private JLabel status;
	private JMenu menu;
	private JMenuItem menuItem;
	private JPanel panel_1;

	/**
	 * Create the frame.
	 */
	private void loadComplete() {
		queryFinance.setEnabled(true);
		queryOrder.setEnabled(true);
		if (BaseRes.getLoginUser().getDeptno() == 0) {
			menuItem.setEnabled(true);
			queryFinance.setVisible(true);
		}
	}

	public Main() {
		//
		Thread LoadPerRes = new PerResLoad();
		LoadPerRes.start();
		//
		new Timer().schedule(new TimerTask() {
			int time = 0;

			public void run() {
				time++;
				if (BaseRes.isAvailable()) {
					status.setText("资源加载完成" + time);
					loadComplete();
					super.cancel();
				} else if (time > 10) {
					status.setText("加载资源超时");
				}
			}
		}, 500, 1000);
		//
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
			}
		});
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		setResizable(false);
		setTitle("工作台  " + BaseRes.getLoginUser());
		int width = 300;
		int height = 150;
		int w = Toolkit.getDefaultToolkit().getScreenSize().width - width;
		int h = Toolkit.getDefaultToolkit().getScreenSize().height - height - 50;
		setBounds(w, h, width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu query = new JMenu("查询");

		queryFinance = new JMenuItem("查询账单");
		queryFinance.setEnabled(false);
		queryFinance.setVisible(false);
		queryFinance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FinanceList();
			}
		});
		queryOrder = new JMenuItem("查询订单");
		queryOrder.setEnabled(false);
		queryOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new OrderList();
			}
		});
		query.add(queryFinance);
		query.add(queryOrder);

		menuBar.add(query);

		menu = new JMenu("\u7BA1\u7406");
		menuBar.add(menu);

		menuItem = new JMenuItem("\u5458\u5DE5\u7BA1\u7406");
		menuItem.setEnabled(false);
		if(BaseRes.getLoginUser().getDeptno()!=0){
			menuItem.setVisible(false);
		}
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new UserManager();
			}
		});
		menu.add(menuItem);
		getContentPane().setLayout(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);

		status = new JLabel("\u8D44\u6E90\u52A0\u8F7D\u4E2D");
		panel.add(status);
		
		panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);
	}
}
