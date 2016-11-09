package org.neojo.server.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.neojo.server.action.Action;
import org.neojo.server.entity.Finance;
import org.neojo.server.entity.OPType;
import org.neojo.server.factory.BeanFactory;
import org.neojo.server.thread.BaseRes;
import org.neojo.server.thread.PerRes;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

public class FinanceList extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Action act = BeanFactory.getActionInstance("ActionImpl");
	private JPanel contentPane;
	private JTable FinTable;
	private Vector<String> columnNames;
	private JPopupMenu popupMenu;
	private int order;
	private JMenuItem QueryOrder;
	private JMenuItem CopyOrder;
	private JMenuItem QueryFin;
	private JMenu queryOrderMenu;
	private List<Finance> nfns;
	private JComboBox<OPType> comboBox;
	private JComboBox<String> comboBox_1;
	private JComboBox<String> comboBox_2;
	private JComboBox<OPType> comboBox_3;
	private JButton query;

	private JScrollPane CreateTable() {

		JScrollPane p = new JScrollPane();
		popupMenu = new JPopupMenu();
		queryOrderMenu = new JMenu("查询");

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

		QueryFin = new JMenuItem();
		QueryFin.setText("查询账单");
		QueryFin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int id = Integer.parseInt(FinTable.getValueAt(FinTable.getSelectedRow(), 0).toString());
				new FinDetail().queryFinDetail(id);
			}
		});
		
		queryOrderMenu.add(QueryOrder);
		queryOrderMenu.add(CopyOrder);
		queryOrderMenu.add(QueryFin);
		QueryOrder.setVisible(false);
		CopyOrder.setVisible(false);
		popupMenu.add(queryOrderMenu);

		columnNames = new Vector<>();
		columnNames.add("序号");
		columnNames.add("日期");
		columnNames.add("类型");
		columnNames.add("订单号");
		columnNames.add("内容");
		columnNames.add("金额");
		columnNames.add("经办人");
		columnNames.add("状态");
		FinTable = new JTable() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		FinTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					int selIndex = FinTable.rowAtPoint(e.getPoint());
					FinTable.setRowSelectionInterval(selIndex, selIndex);
					order = Integer.parseInt(FinTable.getValueAt(selIndex, 3).toString());
					if (FinTable.getValueAt(selIndex, 2).toString().equals(BaseRes.getFinTypeMap().get(1).getType())) {
						QueryOrder.setText("查询订单：" + order);
						QueryOrder.setVisible(true);
						CopyOrder.setVisible(true);
					}else{
						QueryOrder.setVisible(false);
						CopyOrder.setVisible(false);
					}
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
		FinTable.setEnabled(true);
		FinTable.setShowVerticalLines(false);
		FinTable.setBackground(SystemColor.control);
		Vector<Vector<String>> fin = new Vector<>();
		FinTable.setModel(new DefaultTableModel(fin, columnNames));
		FinTable.getTableHeader().setReorderingAllowed(false);
		FinTable.getTableHeader().setResizingAllowed(false);
		FinTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ChangeTableSize();

		p.add(FinTable);
		p.setViewportView(FinTable);
		return p;
	}

	private void UpdateTable() {
		int status = ((OPType) comboBox_3.getSelectedItem()).getTid();
		int type = ((OPType) comboBox.getSelectedItem()).getTid();
		if (comboBox_2.getSelectedIndex() == 0 && comboBox_1.getSelectedIndex() == 0) {
			PerRes.setFlist(act.getFinancebyMonth(new SimpleDateFormat("yyyyMM").format(new Date())));
			nfns = PerRes.getFlist();
			LoadTable(ChooseTable(nfns, status, type));
		} else {
			nfns = act.getFinancebyMonth(comboBox_1.getSelectedItem().toString()
					+ String.format("%02d", Integer.parseInt(comboBox_2.getSelectedItem().toString())));
			LoadTable(ChooseTable(nfns, status, type));
			PerRes.setOlist(act.getOrderbyMonth(new SimpleDateFormat("yyyyMM").format(new Date())));
		}
	}

	private List<Finance> ChooseTable(List<Finance> fns, int status, int type) {
		List<Finance> fs = new ArrayList<>();
		if (status < 0 && type < 0) {
			fs = fns;
		} else if (status >= 0 && type < 0) {
			for (int i = 0; i < fns.size(); i++) {
				if (fns.get(i).getStatus() == status) {
					fs.add(nfns.get(i));
				}
			}
		} else if (status < 0 && type >= 0) {
			for (int i = 0; i < fns.size(); i++) {
				if (fns.get(i).getTid() == type) {
					fs.add(nfns.get(i));
				}
			}
		} else {
			for (int i = 0; i < fns.size(); i++) {
				if (fns.get(i).getStatus() == status && fns.get(i).getTid() == type) {
					fs.add(nfns.get(i));
				}
			}
		}
		return fs;
	}

	private void LoadTable(List<Finance> olist) {
		Vector<Vector<String>> fin = new Vector<>();
		for (int i = olist.size() - 1; i >= 0; i--) {
			Vector<String> fae = new Vector<>();
			fae.add(String.valueOf(olist.get(i).getId()));
			fae.add(String.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(olist.get(i).getDate())));
			fae.add(String.valueOf(BaseRes.getFinTypeMap().get(olist.get(i).getTid())));
			fae.add(String.valueOf(olist.get(i).getOid()));
			fae.add(String.valueOf(olist.get(i).getContent()));
			fae.add(String.valueOf(olist.get(i).getMoney()));
			fae.add(String.valueOf(BaseRes.getUserMap().get(olist.get(i).getUid()).getName()));
			fae.add(String.valueOf(BaseRes.getFinStatusMap().get(olist.get(i).getStatus())));
			fin.add(fae);
		}
		FinTable.setModel(new DefaultTableModel(fin, columnNames));
		ChangeTableSize();
	}

	private void ChangeTableSize() {
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.RIGHT);
		FinTable.getColumnModel().getColumn(0).setCellRenderer(r);
		FinTable.getColumnModel().getColumn(0).setPreferredWidth(25);
		FinTable.getColumnModel().getColumn(1).setPreferredWidth(65);
		FinTable.getColumnModel().getColumn(2).setPreferredWidth(35);
		FinTable.getColumnModel().getColumn(3).setPreferredWidth(35);
		FinTable.getColumnModel().getColumn(4).setPreferredWidth(150);
		FinTable.getColumnModel().getColumn(5).setPreferredWidth(40);
		FinTable.getColumnModel().getColumn(6).setPreferredWidth(40);
		FinTable.getColumnModel().getColumn(7).setPreferredWidth(40);
	}

	private JPanel EndPane() {
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		JButton add = new JButton("添加");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new FinDetail().addFin();
			}
		});
		
		query = new JButton("查询");
		query.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int id = Integer.parseInt(FinTable.getValueAt(FinTable.getSelectedRow(), 0).toString());
				new FinDetail().queryFinDetail(id);
			}
		});
		p1.add(query);

		p1.add(add);
		// p1.add(new JButton("删除"));
		JButton button = new JButton("\u66F4\u65B0");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UpdateTable();
			}
		});
		p1.add(button);
		Vector<OPType> ftp = new Vector<>();
		ftp.add(new OPType(-1, "全部"));
		ftp.addAll(BaseRes.getFinType());
		comboBox = new JComboBox<OPType>(new DefaultComboBoxModel<>(ftp));
		comboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					LoadTable(ChooseTable(nfns, ((OPType) comboBox_3.getSelectedItem()).getTid(),
							((OPType) comboBox.getSelectedItem()).getTid()));
				}

			}
		});
		p1.add(comboBox);

		Vector<OPType> fts = new Vector<>();
		fts.add(new OPType(-1, "全部"));
		fts.addAll(BaseRes.getFinStatus());
		comboBox_3 = new JComboBox<OPType>(new DefaultComboBoxModel<>(fts));
		comboBox_3.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					LoadTable(ChooseTable(nfns, ((OPType) comboBox_3.getSelectedItem()).getTid(),
							((OPType) comboBox.getSelectedItem()).getTid()));
				}

			}
		});
		p1.add(comboBox_3);

		Calendar a = Calendar.getInstance();
		Calendar c = Calendar.getInstance();
		c.setTime(BaseRes.getFindate()[0]);
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
					c.setTime(BaseRes.getFindate()[0]);
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
				nfns = act.getFinancebyMonth(comboBox_1.getSelectedItem().toString()
						+ String.format("%02d", Integer.parseInt(comboBox_2.getSelectedItem().toString())));
				LoadTable(nfns);
			}
		});

		p1.add(comboBox_1);
		now = a.get(Calendar.MONTH);
		start = c.get(Calendar.MONTH);
		Vector<String> month = new Vector<>();
		if (c.get(Calendar.YEAR) != a.get(Calendar.YEAR)) {
			for (int i = a.get(Calendar.MONTH) + 1; i > 0; i--) {
				month.addElement(String.valueOf(i));
			}
		} else {
			for (int i = a.get(Calendar.MONTH) + 1; i > start; i--) {
				month.addElement(String.valueOf(i));
			}
		}
		comboBox_2 = new JComboBox<String>(new DefaultComboBoxModel<>(month));
		comboBox_2.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				nfns = act.getFinancebyMonth(comboBox_1.getSelectedItem().toString()
						+ String.format("%02d", Integer.parseInt(comboBox_2.getSelectedItem().toString())));
				LoadTable(nfns);

			}
		});

		p1.add(comboBox_2);

		JPanel p = new JPanel(new BorderLayout());
		p.add(BorderLayout.NORTH, p1);// 按钮面板

		return p;
	}

	/**
	 * Create the frame.
	 */
	public FinanceList() {
		setVisible(true);
		setResizable(false);
		setTitle("财务列表");
		int width = 600;
		int height = 320;
		int w = (Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2;
		int h = (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2;
		setBounds(w, h, width, height);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPane = new JPanel(new BorderLayout());
		contentPane.add(BorderLayout.CENTER, CreateTable());
		contentPane.add(BorderLayout.SOUTH, EndPane());
		setContentPane(contentPane);
		if (PerRes.isAvailable()) {
			nfns = PerRes.getFlist();
			LoadTable(nfns);
		}
	}
}
