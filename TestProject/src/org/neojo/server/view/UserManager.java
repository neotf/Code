package org.neojo.server.view;

import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import org.neojo.server.action.Action;
import org.neojo.server.entity.Result;
import org.neojo.server.entity.User;
import org.neojo.server.entity.UserInfo;
import org.neojo.server.factory.BeanFactory;
import org.neojo.server.thread.BaseRes;
import org.neojo.server.thread.BaseResLoad;
import org.neojo.server.util.AuthCode;
import org.neojo.server.util.IDCheck;
import org.neojo.server.util.MD5;

import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class UserManager extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel usbmd;
	private JLabel UidLabel;
	private JLabel NameLabel;
	private JLabel PhoneLabel;
	private JLabel Uid_Label;
	private JLabel Edtime_Label;
	private JLabel IdnoStatus;
	private JLabel NameStatus;
	private JLabel EmailStatus;
	private JLabel UserStatus;
	private JTextField NameField;
	private JTextField PhoneField;
	private JTextField EmailField;
	private JTextField QQField;
	private JTextField IdnoField;
	private JTextField StuidField;
	private JTextField usernameField;
	private JComboBox<String> UserStatusBox;
	private JComboBox<String> SexBox;
	private JComboBox<String> DeptBox;
	private JComboBox<String> JobBox;
	private JButton SaveButton;
	private int[] uids;
	private String[] sex = { "女", "男" };
	private String[] dept = { "办公室", "技术部" };
	private String[] job = { "部员", "主管" };
	private String[] status = { "未知", "正常", "未激活", "封禁" };
	private int[] pb = { 0, 0, 0, 0, 0 };
	private String password;
	private boolean newuser = false;
	private boolean tblser = true;
	private Action act = BeanFactory.getActionInstance("ActionImpl");
	private JLabel PhoneStatus;

	private void error(String msg) {
		JOptionPane.showMessageDialog(getContentPane(), msg, "错误", JOptionPane.ERROR_MESSAGE);
	}

	private void tip(String title, String msg) {
		JOptionPane.showMessageDialog(this.getContentPane(), msg, title, JOptionPane.INFORMATION_MESSAGE);
	}

	private void checkpb() {
		boolean flag = true;
		for (int i = 0; i < pb.length; i++) {
			if (pb[i] == 0) {
				flag = false;
				switch (i) {
				case 0:
					NameStatus.setText("空");
					break;
				case 1:
					PhoneStatus.setText("空");
					break;
				case 2:
					EmailStatus.setText("空");
					break;
				case 3:
					IdnoStatus.setText("空");
					break;
				case 4:
					UserStatus.setText("空");
					break;
				default:
					break;
				}
			} else if (pb[i] == 1) {
				switch (i) {
				case 0:
					NameStatus.setText("ok");
					break;
				case 1:
					PhoneStatus.setText("ok");
					break;
				case 2:
					EmailStatus.setText("ok");
					break;
				case 3:
					IdnoStatus.setText("身份证输入正确");
					break;
				case 4:
					UserStatus.setText("ok");
					break;
				default:
					break;
				}
			} else if (pb[i] == 2) {
				flag = false;
				switch (i) {
				case 0:
					NameStatus.setText("误");
					break;
				case 1:
					PhoneStatus.setText("误");
					break;
				case 2:
					EmailStatus.setText("误");
					break;
				case 3:
					IdnoStatus.setText("误");
					break;
				case 4:
					UserStatus.setText("误");
					break;
				default:
					break;
				}
			}
		}
		SaveButton.setEnabled(flag);
		if (!flag) {
			
		}
	}

	private void clearuserinfo() {
		UserStatusBox.setSelectedIndex(2);
		Uid_Label.setText("");
		NameField.setText("");
		SexBox.setSelectedIndex(0);
		PhoneField.setText("");
		DeptBox.setSelectedIndex(0);
		JobBox.setSelectedIndex(0);
		EmailField.setText("");
		Edtime_Label.setText("");
		QQField.setText("");
		StuidField.setText("");
		IdnoField.setText("");
		usernameField.setText("");
	}

	private void newuser() {
		UserStatusBox.setEnabled(false);
		clearuserinfo();
		if (newuser) {
			usbmd.addRow(new String[] { "", "", "" });
		}
		table.setRowSelectionInterval(table.getRowCount() - 1, table.getRowCount() - 1);
	}

	private void queryUser(int uid) {
		EmailStatus.setText("");
		IdnoStatus.setText("");
		NameStatus.setText("");
		UserStatus.setText("");
		PhoneStatus.setText("");

		UserStatusBox.setEnabled(true);
		UserInfo user = act.getUserInfo(uid, BaseRes.getLoginUser());
		Uid_Label.setText(String.valueOf(user.getUid()));
		NameField.setText(user.getName());
		UserStatusBox.setSelectedIndex(user.getStatus());
		SexBox.setSelectedIndex(user.getSex());
		PhoneField.setText(user.getPhone());
		DeptBox.setSelectedIndex(user.getDeptno());
		JobBox.setSelectedIndex(user.getJob());
		EmailField.setText(user.getEmail());
		Edtime_Label.setText(new SimpleDateFormat("yyyy-MM-dd").format(user.getEdtime()));
		QQField.setText(user.getQq());
		StuidField.setText(user.getStuid());
		IdnoField.setText(user.getIdno());
		usernameField.setText(user.getUsername());
	}

	private void saveUser() {
		UserStatusBox.setEnabled(true);
		UserInfo user = new UserInfo();
		user.setUsername(usernameField.getText());
		user.setPassword(password);
		user.setName(NameField.getText());
		user.setSex(SexBox.getSelectedIndex());
		user.setPhone(PhoneField.getText());
		user.setEmail(EmailField.getText());
		user.setQq(QQField.getText());
		user.setDeptno(DeptBox.getSelectedIndex());
		user.setJob(JobBox.getSelectedIndex());
		user.setIdno(IdnoField.getText());
		user.setStuid(StuidField.getText());
		user.setStatus(UserStatusBox.getSelectedIndex());
		if (newuser) {
			JPanel test = new JPanel();
			JLabel b = new JLabel("密码(默认随机)：");
			JPasswordField pwd = new JPasswordField();
			pwd.setColumns(10);
			test.add(b);
			test.add(pwd);
			JOptionPane.showMessageDialog(null, test, "请输入新用户密码", JOptionPane.PLAIN_MESSAGE);
			String passwd = new String(pwd.getPassword());
			boolean rpwd = false;
			if (passwd.isEmpty()) {
				rpwd = true;
				passwd = String.format("%06d", new Random().nextInt(999999));
			}
			user.setPassword(MD5.getMd5(passwd));
			if (act.createUser(user) > 0) {
				Result a = null;
				if (rpwd) {
					a = AuthCode.sendPost(user.getPhone(), "尊敬的" + user.getName() + "，你已注册  test 系统 ，您的用户名："
							+ user.getUsername() + "，密码：" + passwd + "。请及时修改密码！");
				} else {
					a = AuthCode.sendPost(user.getPhone(), "您注册的用户名："+user.getUsername()+"，密码："+passwd+"。感谢您的注册！");
				}
				if (a.getId() < 0) {
					error(a.getMsg());
					return;
				}
			}
		} else {
			act.updateAccount(uids[table.getSelectedRow()], user);
		}
		tip("提示", "保存成功");
		new BaseResLoad().updateUser();
		updatetable();
		newuser = false;
	}

	private void updatetable() {
		tblser = false;
		Vector<String> columnNames = new Vector<>();
		columnNames.add("姓名");
		columnNames.add("部门");
		columnNames.add("状态");
		
		Vector<User> users = act.getAllUserUid();
		uids = new int[users.size()];
		for (int i = 0; i < users.size(); i++) {
			uids[i] = users.get(i).getUid();
		}
		Vector<Vector<String>> rowsdata = new Vector<>();
		for (int i = 0; i < users.size(); i++) {
			Vector<String> rowdata = new Vector<>();
			rowdata.add(users.get(i).getName());
			rowdata.add(dept[users.get(i).getDeptno()]);
			rowdata.add(status[users.get(i).getStatus()]);
			rowsdata.add(rowdata);
		}
		usbmd.setDataVector(rowsdata, columnNames);
		tblser = true;
	}

	/**
	 * Create the frame.
	 */
	private JScrollPane userTable() {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 10, 191, 263);
		table = new JTable() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setBackground(SystemColor.control);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				boolean bl = arg0.getValueIsAdjusting();
				if (tblser && !bl) {
					if (newuser && table.getSelectedRow() == table.getRowCount() - 1) {
						clearuserinfo();
					} else {
						queryUser(uids[table.getSelectedRow()]);
					}
				}

			}
		});
		Vector<String> columnNames = new Vector<>();
		columnNames.add("姓名");
		columnNames.add("部门");
		columnNames.add("状态");
		Vector<User> users = BaseRes.getUser();
		uids = new int[users.size()];
		for (int i = 0; i < users.size(); i++) {
			uids[i] = users.get(i).getUid();
		}
		Vector<Vector<String>> rowsdata = new Vector<>();
		for (int i = 0; i < users.size(); i++) {
			Vector<String> rowdata = new Vector<>();
			rowdata.add(users.get(i).getName());
			rowdata.add(dept[users.get(i).getDeptno()]);
			rowdata.add(status[users.get(i).getStatus()]);
			rowsdata.add(rowdata);
		}
		usbmd = new DefaultTableModel(rowsdata, columnNames);
		table.setModel(usbmd);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		return scrollPane;
	}

	private JPanel userInfo() {

		JPanel panel = new JPanel();
		panel.setBounds(215, 10, 567, 294);
		panel.setLayout(null);

		UidLabel = new JLabel("\u7528\u6237\u7F16\u53F7");
		UidLabel.setBounds(12, 10, 50, 13);
		panel.add(UidLabel);

		Uid_Label = new JLabel("");
		Uid_Label.setBounds(74, 10, 50, 13);
		panel.add(Uid_Label);

		JLabel UserStatusLabel = new JLabel("\u7528\u6237\u72B6\u6001");
		UserStatusLabel.setVerticalAlignment(SwingConstants.TOP);
		UserStatusLabel.setBounds(274, 10, 50, 13);
		panel.add(UserStatusLabel);

		UserStatusBox = new JComboBox<String>(new DefaultComboBoxModel<>(status));
		UserStatusBox.setBounds(336, 6, 50, 19);
		panel.add(UserStatusBox);

		NameLabel = new JLabel("\u59D3\u540D");
		NameLabel.setBounds(12, 33, 50, 13);
		panel.add(NameLabel);

		NameField = new JTextField();
		NameField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String input = NameField.getText();
				if (input.isEmpty()) {
					pb[0] = 0;
					checkpb();
					return;
				}
				String regex = "^[\u4e00-\u9fa5]{2,4}$";
				boolean flag = Pattern.compile(regex).matcher(input).find();
				if (flag) {
					pb[0] = 1;
					checkpb();
					return;
				} else {
					pb[0] = 2;
					checkpb();
					return;
				}
			}
		});
		NameField.setBounds(74, 30, 96, 19);
		panel.add(NameField);
		NameField.setColumns(10);

		JLabel SexLabel = new JLabel("\u6027\u522B");
		SexLabel.setBounds(274, 33, 50, 13);
		panel.add(SexLabel);

		SexBox = new JComboBox<>(new DefaultComboBoxModel<>(sex));
		SexBox.setBounds(336, 30, 50, 19);
		panel.add(SexBox);

		PhoneLabel = new JLabel("\u624B\u673A\u53F7");
		PhoneLabel.setBounds(12, 56, 50, 13);
		panel.add(PhoneLabel);

		PhoneField = new JTextField();
		PhoneField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String regex = "^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$";
				String input = PhoneField.getText();
				if (input.isEmpty()) {
					pb[1] = 0;
					checkpb();
					return;
				}
				boolean flag = Pattern.compile(regex).matcher(input).find();
				if (flag) {
					pb[1] = 1;
					checkpb();
					return;
				} else {
					pb[1] = 2;
					checkpb();
					return;
				}
			}
		});
		PhoneField.setBounds(74, 53, 96, 19);
		panel.add(PhoneField);
		PhoneField.setColumns(10);

		JLabel DeptLabel = new JLabel("\u90E8\u95E8");
		DeptLabel.setBounds(274, 56, 50, 13);
		panel.add(DeptLabel);

		DeptBox = new JComboBox<String>(new DefaultComboBoxModel<>(dept));
		DeptBox.setBounds(336, 53, 70, 19);
		panel.add(DeptBox);

		JLabel JobLabel = new JLabel("\u804C\u4F4D");
		JobLabel.setBounds(441, 56, 24, 13);
		panel.add(JobLabel);

		JobBox = new JComboBox<String>(new DefaultComboBoxModel<>(job));
		JobBox.setBounds(477, 53, 50, 19);
		panel.add(JobBox);

		JLabel EmailLabel = new JLabel("\u90AE\u7BB1");
		EmailLabel.setBounds(12, 79, 50, 13);
		panel.add(EmailLabel);

		EmailField = new JTextField();
		EmailField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String input = EmailField.getText();
				if (input.isEmpty()) {
					pb[2] = 0;
					checkpb();
					return;
				}
				String regex = "^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,5}$";
				boolean flag = Pattern.compile(regex).matcher(input).find();
				if (flag) {
					pb[2] = 1;
					checkpb();
					return;
				} else {
					pb[2] = 2;
					checkpb();
					return;
				}
			}
		});
		EmailField.setBounds(74, 76, 158, 19);
		panel.add(EmailField);
		EmailField.setColumns(10);

		JLabel EdtimeLabel = new JLabel("\u5165\u804C\u65F6\u95F4");
		EdtimeLabel.setBounds(274, 79, 50, 13);
		panel.add(EdtimeLabel);

		Edtime_Label = new JLabel("");
		Edtime_Label.setBounds(336, 79, 191, 13);
		panel.add(Edtime_Label);

		JLabel QQ_Label = new JLabel("QQ");
		QQ_Label.setBounds(12, 102, 50, 13);
		panel.add(QQ_Label);

		QQField = new JTextField();
		QQField.setBounds(74, 99, 96, 19);
		panel.add(QQField);
		QQField.setColumns(10);

		JLabel StuidLabel = new JLabel("\u5B66\u53F7");
		StuidLabel.setBounds(274, 102, 50, 13);
		panel.add(StuidLabel);

		StuidField = new JTextField();
		StuidField.setBounds(336, 99, 96, 19);
		panel.add(StuidField);
		StuidField.setColumns(10);

		JLabel IdnoLabel = new JLabel("\u8EAB\u4EFD\u8BC1\u53F7");
		IdnoLabel.setBounds(12, 125, 50, 13);
		panel.add(IdnoLabel);

		IdnoField = new JTextField();
		IdnoField.getDocument().addDocumentListener(new DocumentListener() {
			private void eve() {
				int ck = IDCheck.isIDCard(IdnoField.getText());
				String msg = null;
				switch (ck) {
				case -1:
					msg = "身份证位数错误";
					pb[3] = 2;
					break;
				case -2:
					msg = "身份证区位码错误";
					pb[3] = 2;
					break;
				case -3:
					msg = "身份证年份错误";
					pb[3] = 2;
					break;
				case -4:
					msg = "身份证月份错误";
					pb[3] = 2;
					break;
				case -5:
					msg = "身份证日期错误";
					pb[3] = 2;
					break;
				case -6:
					msg = "身份证校验码错误";
					pb[3] = 2;
					break;
				default:
					msg = "身份证输入正确";
					pb[3] = 1;
					break;
				}
				IdnoStatus.setText(msg);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				eve();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				eve();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				eve();
			}
		});
		IdnoField.setBounds(74, 122, 158, 19);
		panel.add(IdnoField);
		IdnoField.setColumns(10);

		JLabel usernameLabel = new JLabel("\u7528\u6237\u540D");
		usernameLabel.setBounds(274, 125, 50, 13);
		panel.add(usernameLabel);

		usernameField = new JTextField();
		usernameField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String input = usernameField.getText();
				if (input.isEmpty()) {
					pb[4] = 0;
					checkpb();
					return;
				}
				String regex = "^[a-zA-z][a-zA-Z0-9_]{2,16}$";
				boolean flag = Pattern.compile(regex).matcher(input).find();
				if (flag) {
					pb[4] = 1;
					checkpb();
					return;
				} else {
					pb[4] = 2;
					checkpb();
					return;
				}
			}
		});
		usernameField.setBounds(336, 122, 96, 19);
		panel.add(usernameField);
		usernameField.setColumns(10);

		SaveButton = new JButton("\u4FDD\u5B58");
		SaveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveUser();
			}
		});
		SaveButton.setBounds(485, 263, 70, 21);
		panel.add(SaveButton);

		IdnoStatus = new JLabel("");
		IdnoStatus.setBounds(74, 151, 158, 15);
		panel.add(IdnoStatus);

		PhoneStatus = new JLabel("");
		PhoneStatus.setBounds(180, 55, 54, 15);
		panel.add(PhoneStatus);

		NameStatus = new JLabel("");
		NameStatus.setBounds(178, 32, 54, 15);
		panel.add(NameStatus);

		EmailStatus = new JLabel("");
		EmailStatus.setBounds(242, 78, 24, 15);
		panel.add(EmailStatus);

		UserStatus = new JLabel("");
		UserStatus.setBounds(441, 124, 54, 15);
		panel.add(UserStatus);
		return panel;
	}

	public UserManager() {
		setVisible(true);
		setResizable(false);
		setTitle("员工信息管理");
		int width = 800;
		int height = 345;
		int w = (Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2;
		int h = (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2;
		setBounds(w, h, width, height);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(userTable());
		contentPane.add(userInfo());

		JButton AddButton = new JButton("\u6DFB\u52A0");
		AddButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newuser = true;
				newuser();
			}
		});
		AddButton.setBounds(12, 283, 58, 21);
		contentPane.add(AddButton);

		JButton RefreshButton = new JButton("\u5237\u65B0");
		RefreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updatetable();
			}
		});
		RefreshButton.setBounds(145, 283, 58, 21);
		contentPane.add(RefreshButton);
	}
}
