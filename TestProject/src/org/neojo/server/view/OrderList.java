package org.neojo.server.view;

import java.awt.BorderLayout;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.neojo.server.action.Action;
import org.neojo.server.entity.Finance;
import org.neojo.server.entity.OPType;
import org.neojo.server.entity.Order;
import org.neojo.server.factory.BeanFactory;
import org.neojo.server.thread.BaseRes;
import org.neojo.server.thread.PerRes;

import java.awt.FlowLayout;
import javax.swing.JComboBox;

public class OrderList extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Action act = BeanFactory.getActionInstance("ActionImpl");
	private JTable OrdTable;
	private Vector<String> columnNames;
	private JPopupMenu popupMenu;
	private int order;
	private JMenuItem QueryOrder;
	private JMenuItem CopyOrder;
	private JMenuItem cancel;
	private JMenuItem refund;
	private JMenuItem submit;
	private JMenu queryOrderMenu;
	private JComboBox<OPType> comboBox;
	private JButton submitbutton;
	private JButton cancelbutton;
	private JButton refundbutton;
	private JComboBox<String> comboBox_1;
	private JComboBox<String> comboBox_2;
	private List<Order> nods;

	private void error(String title, String msg) {
		JOptionPane.showMessageDialog(this.getContentPane(), msg, title, JOptionPane.ERROR_MESSAGE);
	}
	private void tip(String msg) {
		JOptionPane.showMessageDialog(getContentPane(), msg, "提示", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Create the frame.
	 */
	private JScrollPane MainPane() {
		JScrollPane p = new JScrollPane();
		popupMenu = new JPopupMenu();
		queryOrderMenu = new JMenu("查询订单");
		{
			QueryOrder = new JMenuItem();
			QueryOrder.setText("查询订单：0");
			QueryOrder.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					new OrderDetail().queryOrderDetail(order);

				}
			});
			CopyOrder = new JMenuItem();
			CopyOrder.setText("复制订单号");
			CopyOrder.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					StringSelection ss = new StringSelection(String.valueOf(order).trim());
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
				}
			});
		}

		queryOrderMenu.add(QueryOrder);
		queryOrderMenu.add(CopyOrder);
		cancel = new JMenuItem();
		cancel.setText("取消订单");
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cancle();
			}
		});
		refund = new JMenuItem();
		refund.setText("退款");
		refund.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				refund();
			}
		});
		submit = new JMenuItem();
		submit.setText("申报");
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				submit();
			}
		});

		popupMenu.add(queryOrderMenu);
		popupMenu.add(submit);
		popupMenu.add(cancel);
		popupMenu.add(refund);

		columnNames = new Vector<>();
		columnNames.add("单号");
		columnNames.add("日期");
		columnNames.add("类型");
		columnNames.add("客户");
		columnNames.add("金额");
		columnNames.add("操作人");
		columnNames.add("状态");
		OrdTable = new JTable() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		OrdTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					int selIndex = OrdTable.rowAtPoint(e.getPoint());
					OrdTable.setRowSelectionInterval(selIndex, selIndex);
					order = Integer.parseInt(OrdTable.getValueAt(OrdTable.getSelectedRow(), 0).toString());
					QueryOrder.setText("查询订单：" + order);

					Vector<OPType> ods = BaseRes.getOrderStatus();
					int status = -1;
					for (int i = 0; i < ods.size(); i++) {
						if (ods.get(i).getType().equals(OrdTable.getValueAt(selIndex, 6).toString())) {
							status = ods.get(i).getTid();
						}
					}
					switch (status) {
					case 0:
						submit.setVisible(true);
						cancel.setVisible(true);
						refund.setVisible(false);
						break;
					case 1:
						cancel.setVisible(false);
						refund.setVisible(true);
						submit.setVisible(false);
						break;
					default:
						cancel.setVisible(false);
						refund.setVisible(false);
						submit.setVisible(false);
						break;
					}
					popupMenu.show(e.getComponent(), e.getX(), e.getY());

				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
			}
		});
		OrdTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (OrdTable.getSelectedRowCount() == 0)
					return;
				int selIndex = OrdTable.getSelectedRow();
				Vector<OPType> ods = BaseRes.getOrderStatus();
				int status = -1;
				for (int i = 0; i < ods.size(); i++) {
					if (ods.get(i).getType().equals(OrdTable.getValueAt(selIndex, 6).toString())) {
						status = ods.get(i).getTid();
					}
				}
				switch (status) {
				case 0:
					submitbutton.setEnabled(true);
					cancelbutton.setEnabled(true);
					refundbutton.setEnabled(false);
					break;
				case 1:
					cancelbutton.setEnabled(false);
					refundbutton.setEnabled(true);
					submitbutton.setEnabled(false);
					break;
				default:
					cancelbutton.setEnabled(false);
					refundbutton.setEnabled(false);
					submitbutton.setEnabled(false);
					break;
				}

			}
		});
		OrdTable.setEnabled(true);
		OrdTable.setShowVerticalLines(false);
		OrdTable.setBackground(SystemColor.control);
		Vector<Vector<String>> odv = new Vector<>();
		OrdTable.setModel(new DefaultTableModel(odv, columnNames));
		OrdTable.getTableHeader().setReorderingAllowed(false);
		OrdTable.getTableHeader().setResizingAllowed(false);
		OrdTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ChangeTableSize();
		p.add(OrdTable);
		p.setViewportView(OrdTable);
		return p;
	}

	private void UpdateTable() {
		int status = ((OPType) comboBox.getSelectedItem()).getTid();
		if (comboBox_2.getSelectedIndex() == 0 && comboBox_1.getSelectedIndex() == 0) {
			PerRes.setOlist(act.getOrderbyMonth(new SimpleDateFormat("yyyyMM").format(new Date())));
			nods = PerRes.getOlist();
			LoadTable(ChooseTable(nods, status));
		} else {
			nods = act.getOrderbyMonth(comboBox_1.getSelectedItem().toString()
					+ String.format("%02d", Integer.parseInt(comboBox_2.getSelectedItem().toString())));
			LoadTable(ChooseTable(nods, status));
			PerRes.setOlist(act.getOrderbyMonth(new SimpleDateFormat("yyyyMM").format(new Date())));
		}
	}

	private List<Order> ChooseTable(List<Order> fods, int status) {
		if (status < 0)
			return fods;
		// if (status == 0)
		// return act.getOrderByStatus(status);
		List<Order> ods = new ArrayList<>();
		for (int i = 0; i < fods.size(); i++) {
			if (fods.get(i).getStatus() == status) {
				ods.add(nods.get(i));
			}
		}
		return ods;
	}

	private void LoadTable(List<Order> olist) {
		Vector<Vector<String>> odv = new Vector<>();
		for (int i = olist.size() - 1; i >= 0; i--) {
			Vector<String> od = new Vector<>();
			od.add(String.valueOf(olist.get(i).getId()));
			od.add(new SimpleDateFormat("yyyy-MM-dd").format(olist.get(i).getDate()));
			od.add(BaseRes.getOrderTypeMap().get(olist.get(i).getOtype()).toString());
			if (olist.get(i).getCustomid() > 0) {

			} else {
				od.add(String.valueOf(olist.get(i).getCustom()));
			}
			od.add(String.valueOf(olist.get(i).getTotal()));
			od.add(BaseRes.getUserMap().get(olist.get(i).getUid()).getName());
			od.add(BaseRes.getOrderStatusMap().get(olist.get(i).getStatus()).toString());
			odv.add(od);
		}
		OrdTable.setModel(new DefaultTableModel(odv, columnNames));
		ChangeTableSize();
	}

	private void ChangeTableSize() {
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.RIGHT);
		DefaultTableCellRenderer c = new DefaultTableCellRenderer();
		c.setHorizontalAlignment(JLabel.CENTER);
		OrdTable.getColumnModel().getColumn(0).setCellRenderer(r);
		OrdTable.getColumnModel().getColumn(1).setCellRenderer(c);
		OrdTable.getColumnModel().getColumn(2).setCellRenderer(c);
		OrdTable.getColumnModel().getColumn(5).setCellRenderer(c);
		OrdTable.getColumnModel().getColumn(6).setCellRenderer(c);
		OrdTable.getColumnModel().getColumn(6).setPreferredWidth(20);
		OrdTable.getColumnModel().getColumn(5).setPreferredWidth(20);
		OrdTable.getColumnModel().getColumn(4).setPreferredWidth(20);
		OrdTable.getColumnModel().getColumn(3).setPreferredWidth(230);
		OrdTable.getColumnModel().getColumn(2).setPreferredWidth(20);
		OrdTable.getColumnModel().getColumn(1).setPreferredWidth(50);
		OrdTable.getColumnModel().getColumn(0).setPreferredWidth(15);

	}

	private void cancle() {
		order = Integer.parseInt(OrdTable.getValueAt(OrdTable.getSelectedRow(), 0).toString());
		int n = JOptionPane.showConfirmDialog(null, "你确定要取消订单" + order + "吗?", "提示", JOptionPane.YES_NO_OPTION);
		if (n == 0) {
			act.changeOrderStatus(order, 2);
			UpdateTable();
			tip("取消成功");
		}
	}

	private void refund() {
		order = Integer.parseInt(OrdTable.getValueAt(OrdTable.getSelectedRow(), 0).toString());
		int n = JOptionPane.showConfirmDialog(null, "你确定要为订单" + order + "退款吗?", "提示", JOptionPane.YES_NO_OPTION);
		if (n == 0) {
			JPanel test = new JPanel();
			JLabel b = new JLabel("退款(默认全退)：");
			JTextField refund = new JTextField();
			refund.setColumns(10);
			test.add(b);
			test.add(refund);
			JOptionPane.showMessageDialog(null, test, "请输入退款总数", JOptionPane.PLAIN_MESSAGE);
			String rfd = refund.getText();
			if (rfd.isEmpty()){
				act.changeFinanceStatus(order, 3);
			}else{
				try {
					Double money = Double.parseDouble(rfd);
					if(money>Double.parseDouble(OrdTable.getValueAt(OrdTable.getSelectedRow(), 4).toString())){
						error("错误", "退款总数大于总金额");
						return;
					}
					Finance fin = new Finance();
					fin.setTid(3);
					fin.setUid(BaseRes.getLoginUser().getUid());
					fin.setMoney(-money);
					fin.setContent(order+"退款");
					fin.setOid(order);
					act.addFinace(fin);
					act.changeFinanceStatus(order, 2);
				} catch (NumberFormatException e) {
					error("错误", "请输入数字");
					return;
				}
			}
				
			act.changeOrderStatus(order, 3);
			UpdateTable();
			tip("退款成功");
		}
	}

	private void submit() {
		order = Integer.parseInt(OrdTable.getValueAt(OrdTable.getSelectedRow(), 0).toString());
		int n = JOptionPane.showConfirmDialog(null, "你确定要申报订单" + order + "吗?", "提示", JOptionPane.YES_NO_OPTION);
		if (n == 0) {
			act.changeOrderStatus(order, 1);
			Finance fin = new Finance();
			fin.setTid(1);
			fin.setUid(BaseRes.getLoginUser().getUid());
			fin.setMoney(Double.parseDouble(OrdTable.getValueAt(OrdTable.getSelectedRow(), 4).toString()));
			String otp = OrdTable.getValueAt(OrdTable.getSelectedRow(), 2).toString();
			String cst = OrdTable.getValueAt(OrdTable.getSelectedRow(), 3).toString();
			fin.setContent(otp+"："+cst);
			fin.setOid(order);
			act.addFinace(fin);
			UpdateTable();
			tip("申报成功");
		}
	}

	private JPanel EndPane() {
		JPanel p1 = new JPanel();

		JPanel p = new JPanel(new BorderLayout());
		p.add(BorderLayout.CENTER, p1);
		p1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton addbutton = new JButton("添加");
		p1.add(addbutton);

		submitbutton = new JButton("申报");
		submitbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				submit();
			}
		});
		submitbutton.setEnabled(false);
		p1.add(submitbutton);

		cancelbutton = new JButton("取消");
		cancelbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancle();
			}
		});
		cancelbutton.setEnabled(false);
		p1.add(cancelbutton);

		refundbutton = new JButton("退款");
		refundbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refund();
			}
		});
		refundbutton.setEnabled(false);
		p1.add(refundbutton);
		JButton updatebutton = new JButton("\u66F4\u65B0");
		p1.add(updatebutton);
		Vector<OPType> Otp = new Vector<>();
		Otp.add(new OPType(-1, "全部"));
		Otp.addAll(BaseRes.getOrderStatus());
		comboBox = new JComboBox<OPType>(new DefaultComboBoxModel<>(Otp));
		comboBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					LoadTable(ChooseTable(nods, ((OPType) comboBox.getSelectedItem()).getTid()));
				}

			}
		});
		p1.add(comboBox);
		Calendar a = Calendar.getInstance();
		Calendar c = Calendar.getInstance();
		c.setTime(BaseRes.getOrddate()[0]);
		int now = a.get(Calendar.YEAR);
		int start = c.get(Calendar.YEAR);
		Vector<String> year = new Vector<>();
		for (int i = now; i >= start; i--) {
			year.addElement(String.valueOf(i));
		}
		comboBox_1 = new JComboBox<String>(new DefaultComboBoxModel<>(year));
		comboBox_1.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if (comboBox_1.getSelectedIndex() == comboBox_1.getItemCount() - 1) {
					Vector<String> month = new Vector<>();
					Calendar c = Calendar.getInstance();
					c.setTime(BaseRes.getOrddate()[0]);
					for (int i = 12; i > c.get(Calendar.MONTH); i--) {
						month.addElement(String.valueOf(i));
					}
					comboBox_2.setModel(new DefaultComboBoxModel<>(month));
				} else if (comboBox_1.getSelectedIndex() == 0) {
					Vector<String> month = new Vector<>();
					for (int i = Calendar.getInstance().get(Calendar.MONTH) + 1; i > 0; i--) {
						month.addElement(String.valueOf(i));
					}
					comboBox_2.setModel(new DefaultComboBoxModel<>(month));
				}
				;
				nods = act.getOrderbyMonth(comboBox_1.getSelectedItem().toString()
						+ String.format("%02d", Integer.parseInt(comboBox_2.getSelectedItem().toString())));
				LoadTable(nods);
			}
		});
		p1.add(comboBox_1);
		now = a.get(Calendar.MONTH);
		start = c.get(Calendar.MONTH);
		Vector<String> month = new Vector<>();
		for (int i = a.get(Calendar.MONTH) + 1; i > 0; i--) {
			month.addElement(String.valueOf(i));
		}
		comboBox_2 = new JComboBox<String>(new DefaultComboBoxModel<>(month));
		comboBox_2.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				nods = act.getOrderbyMonth(comboBox_1.getSelectedItem().toString()
						+ String.format("%02d", Integer.parseInt(comboBox_2.getSelectedItem().toString())));
				LoadTable(nods);

			}
		});

		p1.add(comboBox_2);
		updatebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UpdateTable();
				// new OrderDetail();
			}
		});
		addbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new OrderDetail().addOrder();
			}
		});

		return p;
	}

	public OrderList() {
		setVisible(true);
		setResizable(false);
		setTitle("订单列表");
		int width = 600;
		int height = 320;
		int w = (Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2;
		int h = (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2;
		setBounds(w, h, width, height);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPane = new JPanel(new BorderLayout());
		contentPane.add(BorderLayout.CENTER, MainPane());
		contentPane.add(BorderLayout.SOUTH, EndPane());
		setContentPane(contentPane);
		if (PerRes.isAvailable()) {
			nods = PerRes.getOlist();
			LoadTable(nods);
		}
	}

}
