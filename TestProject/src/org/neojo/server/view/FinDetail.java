package org.neojo.server.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.util.Map;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.neojo.server.action.Action;
import org.neojo.server.entity.Finance;
import org.neojo.server.entity.OPType;
import org.neojo.server.entity.User;
import org.neojo.server.factory.BeanFactory;
import org.neojo.server.thread.BaseRes;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import javax.swing.JButton;

public class FinDetail extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Action act = BeanFactory.getActionInstance("ActionImpl");
	private JPanel contentPane;
	private JTextField idField;
	private JTextField TotalField;
	private JComboBox<User> UserBox;
	private JComboBox<OPType> FinTypeBox;
	private Vector<OPType> fintype;
	private Vector<User> user;
	private Map<Integer, User> userMap;
	private Map<Integer, OPType> FinTypeMap;
	private Finance fn;
	private JLabel StatusLabel;
	private boolean perQueryOrder = false;
	private JLabel DateTextLabel;
	private JButton Savebutton;
	private JLabel DateLabel;
	private boolean saveexit = true;
	private JTextField OnumField;
	private JLabel contentLabel;
	private JTextField contentField;
	private boolean newfin = false;

	/**
	 * function
	 */
	private void error(String title, String msg) {
		JOptionPane.showMessageDialog(this.getContentPane(), msg, title, JOptionPane.ERROR_MESSAGE);
	}

	private void tip(String title, String msg) {
		JOptionPane.showMessageDialog(this.getContentPane(), msg, title, JOptionPane.INFORMATION_MESSAGE);
	}

	private boolean perQueryFin(int id) {
		fn = act.getFinancebyId(id);
		if (fn == null) {
			error("错误", "不存在此订单号");
			return false;
		}
		perQueryOrder = true;
		return true;
	}

	private void queryfin(int id) {
		if (!(perQueryOrder || perQueryFin(id)))
			return;
		perQueryOrder = false;

		UserBox.setSelectedItem(userMap.get(fn.getUid()));
		StatusLabel.setText(BaseRes.getFinStatusMap().get(fn.getStatus()).toString());
		DateLabel.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(fn.getDate()));
		FinTypeBox.setSelectedItem(FinTypeMap.get(fn.getTid()));
		contentField.setText(fn.getContent());
		TotalField.setText(String.valueOf(fn.getMoney()));
		OnumField.setText(String.valueOf(fn.getOid()));
		

		switch (fn.getTid()) {
		case 1:
			Savebutton.setVisible(false);
			break;
		default:
			break;
		}
	}

	private void save() {
		int tid = ((OPType) FinTypeBox.getSelectedItem()).getTid();
		String content = contentField.getText();
		int uid = ((User) UserBox.getSelectedItem()).getUid();
		double money = Double.parseDouble(TotalField.getText());
		Finance fin = new Finance();
		fin.setTid(tid);
		fin.setMoney(money);
		fin.setContent(content);
		fin.setUid(uid);
		if (newfin) {
			if (act.addFinace(fin)) {
				tip("提示", "添加成功");
				dispose();
			} else {
				error("错误","未知错误");
			}
		}else{
			fin.setId(Integer.parseInt(idField.getText()));
			if (act.updateFinace(fin)) {
				tip("提示", "保存成功");
				dispose();
			} else {
				error("错误","未知错误");
			}
		}
		
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

		JLabel FinTypeLabel = new JLabel("财务类型");
		FinTypeLabel.setBounds(12, 10, 54, 15);
		p.add(FinTypeLabel);
		Vector<OPType> ftp = new Vector<>();
		ftp.addAll(fintype);
		if(newfin){
			ftp.removeElementAt(0);
		}
		FinTypeBox = new JComboBox<>(new DefaultComboBoxModel<>(ftp));
		FinTypeBox.setBounds(74, 7, 66, 21);
		p.add(FinTypeBox);

		JLabel UserLabel = new JLabel("\u64CD\u4F5C\u4EBA");
		UserLabel.setBounds(12, 39, 50, 13);
		p.add(UserLabel);

		UserBox = new JComboBox<>(new DefaultComboBoxModel<>(user));
		UserBox.setEnabled(false);
		UserBox.setBounds(74, 36, 66, 19);
		p.add(UserBox);

		JLabel TotalLabel = new JLabel("\u91D1\u989D");
		TotalLabel.setBounds(152, 38, 54, 15);
		p.add(TotalLabel);

		TotalField = new JTextField();
		TotalField.setBounds(214, 35, 96, 21);
		p.add(TotalField);
		TotalField.setColumns(10);

		JLabel OnumLabel = new JLabel("订单号");
		OnumLabel.setBounds(152, 11, 50, 13);
		p.add(OnumLabel);

		OnumField = new JTextField();
		OnumField.setEditable(false);
		OnumField.setBounds(214, 8, 96, 19);
		p.add(OnumField);
		OnumField.setColumns(10);

		DateTextLabel = new JLabel("\u8BA2\u5355\u65F6\u95F4:");
		DateTextLabel.setBounds(9, 92, 50, 13);
		p.add(DateTextLabel);

		DateLabel = new JLabel("\u5F85\u83B7\u53D6");
		DateLabel.setBounds(69, 92, 179, 13);
		p.add(DateLabel);

		Savebutton = new JButton("入账");
		Savebutton.setBounds(244, 88, 66, 21);
		p.add(Savebutton);

		contentLabel = new JLabel("内容");
		contentLabel.setBounds(12, 65, 31, 13);
		p.add(contentLabel);

		contentField = new JTextField();
		contentField.setBounds(74, 62, 236, 19);
		p.add(contentField);
		contentField.setColumns(10);
		FinTypeBox.setEnabled(false);
		TotalField.setEditable(false);
		contentField.setEditable(false);
		Savebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				save();
			}
		});
		return p;
	}

	private void LoadRes() {
		fintype = BaseRes.getFinType();
		FinTypeMap = BaseRes.getFinTypeMap();
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
		JLabel Oidlabel = new JLabel("单号：");
		Oidlabel.setBounds(10, 10, 48, 15);
		idField = new JTextField();
		idField.setEditable(false);
		idField.setBounds(68, 7, 66, 21);
		idField.setColumns(7);
		p.add(Oidlabel);
		p.add(idField);

		JLabel StatusTextLabel = new JLabel("\u8BA2\u5355\u72B6\u6001:");
		p.add(StatusTextLabel);

		StatusLabel = new JLabel("\u672A\u83B7\u53D6");
		p.add(StatusLabel);
		return p;
	}

	private void createWindow() {
		setResizable(false);
		setTitle("财务详细界面");
		int width = 330;
		int height = 180;
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

	public FinDetail() {
		createWindow();
	}

	public void queryFinDetail(int id) {
		perQueryFin(id);
		createWindow();
		idField.setText(String.valueOf(id));
		queryfin(id);
	}

	public void addFin() {
		newfin = true;
		createWindow();
		UserBox.setSelectedItem(userMap.get(BaseRes.getLoginUser().getUid()));	
		StatusLabel.setText("待入账");
		idField.setText("待生成");
		DateLabel.setText("待生成");
		idField.setEnabled(false);
		FinTypeBox.setEnabled(true);
		TotalField.setEditable(true);
		contentField.setEditable(true);
		saveexit = true;
	}
}
