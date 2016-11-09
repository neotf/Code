package org.neojo.server.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import org.neojo.server.action.Action;
import org.neojo.server.entity.OPType;
import org.neojo.server.entity.Order;
import org.neojo.server.entity.OrderGoods;
import org.neojo.server.entity.User;
import org.neojo.server.factory.BeanFactory;
import org.neojo.server.thread.BaseRes;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;

public class OrderDetail extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Action act = BeanFactory.getActionInstance("ActionImpl");
	private JButton Querybutton;
	private JPanel contentPane;
	private JTextField OidField;
	private JLabel TsnLabel;
	private JTextField TsnField;
	private JTextField TotalField;
	private JComboBox<User> UserBox;
	private JComboBox<User> IuserBox;
	private JComboBox<OPType> OrderTypeBox;
	private JComboBox<OPType> PayTypeBox;
	private Vector<OPType> paytype;
	private Map<Integer, OPType> payTypeMap;
	private Vector<OPType> ordtype;
	private Map<Integer, OPType> ordTypeMap;
	private Vector<User> user;
	private Map<Integer, User> userMap;
	private Vector<String> columnNames;
	private JTable GoodTable;
	private JPopupMenu popupMenu;
	private JMenu queryOrderMenu;
	private JMenu queryOrderMenu2;
	private JMenuItem AddItem;
	private JMenuItem DelItem;
	private DefaultTableModel gtbmd;
	private JTextField ProfitField;
	private JTextField BonusField;
	private Order od;
	private List<OrderGoods> odgsl;
	private JLabel StatusLabel;
	private String tsn;
	private boolean perQueryOrder = false;
	private JLabel DateTextLabel;
	private JTextField CustomField;
	private JButton Savebutton;
	private JLabel DateLabel;
	private boolean editable = true;
	private boolean oidflistener = false;
	private boolean neworder = false;
	private boolean saveexit = true;

	/**
	 * function
	 */
	private void error(String title, String msg) {
		JOptionPane.showMessageDialog(this.getContentPane(), msg, title, JOptionPane.ERROR_MESSAGE);
	}

	private void tip(String title, String msg) {
		JOptionPane.showMessageDialog(this.getContentPane(), msg, title, JOptionPane.INFORMATION_MESSAGE);
	}

	private boolean perQueryOrder(int oid) {
		od = act.getOrderbyOid(oid);
		if (od == null) {
			error("错误", "不存在此订单号");
			return false;
		}
		odgsl = act.getOrderGoodsbyOid(oid);
		gtbmd.setRowCount(0);
		perQueryOrder = true;
		return true;
	}

	private void queryOrder(int oid) {
		editable = false;
		if (!(perQueryOrder || perQueryOrder(oid)))
			return;
		perQueryOrder = false;
		CustomField.setText(od.getCustom());
		OrderTypeBox.setSelectedItem(ordTypeMap.get(od.getOtype()));
		PayTypeBox.setSelectedItem(payTypeMap.get(od.getPtype()));

		UserBox.setSelectedItem(userMap.get(od.getUid()));
		IuserBox.setSelectedItem(userMap.get(od.getIuid()));
		TotalField.setText(String.valueOf(od.getTotal()));
		ProfitField.setText(String.valueOf(od.getProfit()));
		BonusField.setText(String.valueOf(od.getBonus()));
		StatusLabel.setText(BaseRes.getOrderStatusMap().get(od.getStatus()).toString());
		DateLabel.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(od.getDate()));
		if (((OPType) PayTypeBox.getSelectedItem()).getTid() > 0) {
			tsn = od.getTsn();
			TsnField.setEnabled(true);
			TsnField.setText(tsn);
		} else {
			tsn = null;
			TsnField.setEnabled(false);
			TsnField.setText("无");
		}

		if (od.getStatus() == 1) {
			oidflistener = true;
			Savebutton.setEnabled(false);
			Savebutton.setVisible(false);
			CustomField.setEditable(false);
			OrderTypeBox.setEnabled(false);
			PayTypeBox.setEnabled(false);
			TsnField.setEditable(false);
			IuserBox.setEnabled(false);
			ProfitField.setEditable(false);
			BonusField.setEditable(false);
		} else if (od.getStatus() == 0) {
			OidField.setEnabled(true);
			OidField.setText(od.getId() + "");
			oidflistener = true;
			Savebutton.setEnabled(true);
			Savebutton.setVisible(true);
			CustomField.setEditable(true);
			OrderTypeBox.setEnabled(true);
			PayTypeBox.setEnabled(true);
			TsnField.setEditable(true);
			IuserBox.setEnabled(true);
			ProfitField.setEditable(true);
			BonusField.setEditable(true);
		} else if (od.getStatus() == 2 || od.getStatus() == 3) {
			oidflistener = false;
			Savebutton.setEnabled(false);
			Savebutton.setVisible(false);
			OidField.setEnabled(true);
			OidField.setText(od.getId() + "");
			CustomField.setEditable(false);
			OrderTypeBox.setEnabled(false);
			PayTypeBox.setEnabled(false);
			TsnField.setEditable(false);
			IuserBox.setEnabled(false);
			ProfitField.setEditable(false);
			BonusField.setEditable(false);
		}
		GoodTable.setEnabled(false);
		for (int i = 0; i < odgsl.size(); i++) {
			Vector<String> odgs = new Vector<>();
			odgs.add(odgsl.get(i).getName());
			odgs.add(String.valueOf(odgsl.get(i).getNum()));
			odgs.add(String.valueOf(odgsl.get(i).getPrice()));
			odgs.add(String.valueOf(odgsl.get(i).getTotal()));
			gtbmd.addRow(odgs);
		}

	}

	private void save() {
		if (gtbmd.getRowCount() == 0) {
			error("错误", "未添加商品/服务信息");
			return;
		}
		int oid = -1;
		int otype = ((OPType) OrderTypeBox.getSelectedItem()).getTid();
		int ptype = ((OPType) PayTypeBox.getSelectedItem()).getTid();
		String tsn = TsnField.getText();
		int uid = ((User) UserBox.getSelectedItem()).getUid();
		int iuid = ((User) IuserBox.getSelectedItem()).getUid();
		double total = 0;
		double profit = 0;
		double bonus = 0;
		try {
			total = Double.parseDouble(TotalField.getText());
			if (ProfitField.getText().isEmpty()) {
				error("错误", "请输入利润");
				return;
			}
			profit = Double.parseDouble(ProfitField.getText());
			if (BonusField.getText().isEmpty()) {
				bonus = 0;
			} else {
				bonus = Double.parseDouble(BonusField.getText());
			}
		} catch (NumberFormatException e) {
			error("错误", "请输入数字");
			return;
		}
		String custom = CustomField.getText();
		int customid = 0;
		Order sod = new Order();
		sod.setOtype(otype);
		sod.setPtype(ptype);
		sod.setTsn(tsn);
		sod.setUid(uid);
		sod.setIuid(iuid);
		sod.setTotal(total);
		sod.setProfit(profit);
		sod.setBonus(bonus);
		sod.setStatus(0);
		sod.setCustom(custom);
		sod.setCustomid(customid);

		if (neworder) {
			List<OrderGoods> sodgs = new ArrayList<>();
			for (int i = 0; i < gtbmd.getRowCount(); i++) {
				OrderGoods sodg = new OrderGoods();
				sodg.setName(gtbmd.getValueAt(i, 0).toString());
				sodg.setNum(Integer.parseInt(gtbmd.getValueAt(i, 1).toString()));
				sodg.setPrice(Double.parseDouble(gtbmd.getValueAt(i, 2).toString()));
				sodg.setTotal(Double.parseDouble(gtbmd.getValueAt(i, 3).toString()));
				sodgs.add(sodg);
			}
			oid = act.createOrder(sod, sodgs);
			if (oid < 0) {
				error("错误", "未知错误");
				return;
			} else {
				tip("提示", "添加成功");
				dispose();
			}
		} else {
			oid = Integer.parseInt(OidField.getText());
			if (act.getOrderbyOid(oid).getStatus() == 0) {
				act.updateOrder(oid, sod);
			} else {
				error("错误", "该订单不可申报");
			}
		}
		queryOrder(oid);
		neworder = false;
		if (saveexit) {
			dispose();
		}
	}

	/**
	 * Create the frame.
	 */
	private JPanel OrderDetailPane() {
		JPanel p = new JPanel();
		p.setLayout(null);

		JLabel CustomLabel = new JLabel("\u5BA2\u6237");
		CustomLabel.setBounds(10, 10, 52, 15);
		p.add(CustomLabel);

		CustomField = new JTextField();
		CustomField.setBounds(74, 7, 66, 21);
		p.add(CustomField);
		CustomField.setColumns(10);

		JLabel OrderTypeLabel = new JLabel("\u8BA2\u5355\u7C7B\u578B");
		OrderTypeLabel.setBounds(150, 10, 54, 15);
		p.add(OrderTypeLabel);

		OrderTypeBox = new JComboBox<>(new DefaultComboBoxModel<>(ordtype));
		OrderTypeBox.setBounds(214, 7, 66, 21);
		p.add(OrderTypeBox);

		JLabel PayTypeLabel = new JLabel("\u4EA4\u6613\u65B9\u5F0F");
		PayTypeLabel.setBounds(10, 35, 54, 15);
		p.add(PayTypeLabel);
		PayTypeBox = new JComboBox<>(new DefaultComboBoxModel<>(paytype));
		PayTypeBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					int status = ((OPType) PayTypeBox.getSelectedItem()).getTid();
					if (status > 0) {
						TsnField.setEnabled(true);
						TsnField.setText(tsn);
					} else {
						TsnField.setEnabled(false);
						TsnField.setText("无");
					}
				}
			}
		});
		PayTypeBox.setBounds(74, 32, 66, 21);
		p.add(PayTypeBox);

		TsnLabel = new JLabel("\u6D41\u6C34\u53F7");
		TsnLabel.setBounds(150, 35, 54, 15);
		p.add(TsnLabel);

		TsnField = new JTextField();
		TsnField.setBounds(214, 32, 208, 21);
		TsnField.setEnabled(false);
		TsnField.setText("无");
		p.add(TsnField);
		TsnField.setColumns(10);

		JLabel UserLabel = new JLabel("\u64CD\u4F5C\u4EBA");
		UserLabel.setBounds(10, 60, 50, 13);
		p.add(UserLabel);

		UserBox = new JComboBox<>(new DefaultComboBoxModel<>(user));
		UserBox.setEnabled(false);
		UserBox.setBounds(74, 57, 66, 19);
		p.add(UserBox);

		JLabel IuserLabel = new JLabel("\u63A8\u8350\u4EBA");
		IuserLabel.setBounds(150, 60, 54, 15);
		p.add(IuserLabel);

		IuserBox = new JComboBox<>(new DefaultComboBoxModel<>(user));
		IuserBox.setBounds(214, 57, 66, 21);
		p.add(IuserBox);

		JLabel TotalLabel = new JLabel("\u91D1\u989D");
		TotalLabel.setBounds(450, 10, 54, 15);
		p.add(TotalLabel);

		TotalField = new JTextField();
		TotalField.setBounds(516, 7, 66, 21);
		TotalField.setEditable(false);
		p.add(TotalField);
		TotalField.setColumns(10);

		JLabel ProfitLabel = new JLabel("\u5229\u6DA6");
		ProfitLabel.setBounds(450, 35, 54, 15);
		p.add(ProfitLabel);

		ProfitField = new JTextField();
		ProfitField.setBounds(516, 32, 66, 21);
		p.add(ProfitField);
		ProfitField.setColumns(10);

		JLabel BonusLabel = new JLabel("\u63A8\u8350\u5229\u6DA6");
		BonusLabel.setBounds(450, 60, 54, 15);
		p.add(BonusLabel);

		BonusField = new JTextField();
		BonusField.setBounds(516, 57, 66, 21);
		p.add(BonusField);
		BonusField.setColumns(10);

		p.add(GoodList());
		return p;
	}

	private JScrollPane GoodList() {
		popupMenu = new JPopupMenu();
		{

			queryOrderMenu = new JMenu("添加商品");

			AddItem = new JMenuItem();
			AddItem.setText("添加新商品");
			AddItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					Vector<String> rowData = new Vector<>();
					gtbmd.addRow(rowData);

				}
			});
			queryOrderMenu2 = new JMenu("添加常规商品");
			queryOrderMenu.add(AddItem);
			queryOrderMenu.add(queryOrderMenu2);

			DelItem = new JMenuItem();
			DelItem.setText("删除");
			DelItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int[] sr = GoodTable.getSelectedRows();
					for (int i = sr.length - 1; i > -1; i--) {
						gtbmd.removeRow(sr[i]);
					}
				}
			});
			popupMenu.add(queryOrderMenu);
			popupMenu.add(DelItem);

		}

		JScrollPane p = new JScrollPane();
		p.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (editable && e.getButton() == MouseEvent.BUTTON3) {
					if (GoodTable.getSelectedRowCount() == 0 || gtbmd.getRowCount() == 0) {
						DelItem.setVisible(false);
					} else {
						DelItem.setVisible(true);
					}
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
		p.setBounds(10, 85, 572, 167);

		GoodTable = new JTable();
		GoodTable.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				int row = GoodTable.getSelectedRow();
				int column = GoodTable.getSelectedColumn();
				GoodTable.isCellEditable(row, column);
				if (arg0.getKeyCode() == 127) {
					gtbmd.setValueAt("", row, column);
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				int row = GoodTable.getSelectedRow();
				int column = GoodTable.getSelectedColumn();
				GoodTable.isCellEditable(row, column);
			}
		});
		GoodTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (editable && e.getButton() == MouseEvent.BUTTON3) {
					if (GoodTable.getSelectedRowCount() == 0 || GoodTable.getSelectedRowCount() == 1) {
						int selIndex = GoodTable.rowAtPoint(e.getPoint());
						GoodTable.setRowSelectionInterval(selIndex, selIndex);
					}

					if (GoodTable.getSelectedRowCount() == 0 || gtbmd.getRowCount() == 0) {
						DelItem.setVisible(false);
					} else {
						DelItem.setVisible(true);
					}

					popupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
		GoodTable.setEnabled(true);
		columnNames = new Vector<>();
		columnNames.add("名称");
		columnNames.add("数量");
		columnNames.add("单价");
		columnNames.add("总价");
		Vector<Vector<String>> fin = new Vector<>();
		gtbmd = new DefaultTableModel(fin, columnNames);
		gtbmd.addTableModelListener(new TableModelListener() {
			// TODO
			public void tableChanged(TableModelEvent event) {
				boolean flag = true;
				if (flag && (event.getColumn() == 1 || event.getColumn() == 2)) {
					int row = event.getFirstRow();
					Object o1 = gtbmd.getValueAt(row, 1);
					Object o2 = gtbmd.getValueAt(row, 2);
					double total = 0;
					if (o1 == null || o2 == null) {
						return;
					}
					try {
						double num1 = Double.parseDouble((String) o1);
						double num2 = Double.parseDouble((String) o2);
						flag = false;
						gtbmd.setValueAt(num1 * num2, row, 3);
						if (gtbmd.getValueAt(row, 3) != null) {
							for (int i = 0; i < gtbmd.getRowCount(); i++) {
								if (gtbmd.getValueAt(i, 3) != null)
									total += Double.parseDouble(gtbmd.getValueAt(i, 3).toString());
							}
							TotalField.setText(String.valueOf(total));
						}
						flag = true;
					} catch (NumberFormatException e) {
						error("错误", "请输入数字");
						flag = true;
						return;
					}
				}
			}
		});
		GoodTable.setModel(gtbmd);
		GoodTable.getTableHeader().setReorderingAllowed(false);
		GoodTable.getTableHeader().setResizingAllowed(false);

		p.add(GoodTable);
		p.setViewportView(GoodTable);
		return p;
	}

	private void LoadRes() {
		ordtype = BaseRes.getOrderType();
		ordTypeMap = BaseRes.getOrderTypeMap();
		paytype = BaseRes.getPayType();
		payTypeMap = BaseRes.getPayTypeMap();
		user = BaseRes.getUser();
		userMap = BaseRes.getUserMap();
	}

	private JPanel MainPane() {
		LoadRes();
		JPanel p = new JPanel(new BorderLayout());
		p.add(BorderLayout.CENTER, OrderDetailPane());
		return p;
	}

	private JPanel HeadPane() {
		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
		JLabel Oidlabel = new JLabel("\u8BA2\u5355\u53F7\uFF1A");
		Oidlabel.setBounds(10, 10, 48, 15);
		OidField = new JTextField();
		OidField.getDocument().addDocumentListener(new DocumentListener() {
			private void oidfo() {
				if (oidflistener) {
					oidflistener = false;
					if (od.getStatus() == 0) {
						Savebutton.setEnabled(false);
						Savebutton.setVisible(false);
					}
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				oidfo();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				oidfo();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				oidfo();
			}
		});
		OidField.setBounds(68, 7, 66, 21);
		OidField.setColumns(7);
		p.add(Oidlabel);
		p.add(OidField);

		Querybutton = new JButton("\u67E5\u8BE2");
		Querybutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int oid = 0;
				try {
					oid = Integer.parseInt(OidField.getText());
				} catch (NumberFormatException e) {
					error("错误", "请输入正确的订单号");
					return;
				}
				queryOrder(oid);
			}
		});
		Querybutton.setVisible(false);
		p.add(Querybutton);

		Savebutton = new JButton("\u4FDD\u5B58");
		Savebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				save();
			}
		});
		p.add(Savebutton);

		JLabel StatusTextLabel = new JLabel("\u8BA2\u5355\u72B6\u6001:");
		p.add(StatusTextLabel);

		StatusLabel = new JLabel("\u672A\u83B7\u53D6");
		p.add(StatusLabel);

		DateTextLabel = new JLabel("\u8BA2\u5355\u65F6\u95F4:");
		p.add(DateTextLabel);

		DateLabel = new JLabel("\u5F85\u83B7\u53D6");
		p.add(DateLabel);
		return p;
	}

	private void createWindow() {
		setResizable(false);
		setTitle("订单详细界面");
		int width = 600;
		int height = 320;
		int w = (Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2;
		int h = (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2;
		setBounds(w, h, width, height);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPane = new JPanel(new BorderLayout());
		contentPane.add(BorderLayout.NORTH, HeadPane());
		contentPane.add(BorderLayout.CENTER, MainPane());
		setContentPane(contentPane);
		setVisible(true);
	}

	public OrderDetail() {
		createWindow();
		Querybutton.setVisible(true);
	}

	public void queryOrderDetail(int oid) {
		perQueryOrder(oid);
		createWindow();
		Querybutton.setVisible(true);
		OidField.setText(String.valueOf(oid));
		queryOrder(oid);
	}

	public void addOrder() {
		createWindow();
		UserBox.setSelectedItem(userMap.get(BaseRes.getLoginUser().getUid()));
		neworder = true;
		StatusLabel.setText("未申报");
		OidField.setText("待生成");
		DateLabel.setText("待生成");
		OidField.setEnabled(false);
		saveexit = true;
	}
}
