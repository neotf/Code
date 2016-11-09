package org.neojo.server.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.neojo.server.action.Action;
import org.neojo.server.entity.Result;
import org.neojo.server.factory.BeanFactory;
import org.neojo.server.util.AuthCode;
import org.neojo.server.util.MD5;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;

public class FindPwd extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField UserField;
	private JTextField PhoneField;
	private JTextField AuthCodeField;
	private JPasswordField NPWDField;
	private JPasswordField RNPWDField;
	private JButton SendAuthCodeButton;
	private JButton okButton;
	private JLabel PwdStatus;
	private String code;
	private boolean rfocus;
	private Action act = BeanFactory.getActionInstance("ActionImpl");
	private JLabel PwdTips;

	private void error(String msg) {
		JOptionPane.showMessageDialog(getContentPane(), msg, "错误", JOptionPane.ERROR_MESSAGE);
	}

	private void tip(String msg) {
		JOptionPane.showMessageDialog(this.getContentPane(), msg, "提示", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Create the dialog.
	 */
	public FindPwd() {
		setTitle("密码找回");
		int width = 453;
		int height = 259;
		int w = (Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2;
		int h = (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2;
		setBounds(w, h, width, height);
		getContentPane().setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel UserLabel = new JLabel("用户名");
		UserLabel.setBounds(105, 38, 50, 13);
		contentPanel.add(UserLabel);

		UserField = new JTextField();
		UserField.setBounds(167, 35, 96, 19);
		contentPanel.add(UserField);
		UserField.setColumns(10);

		JLabel PhoneLabel = new JLabel("注册手机");
		PhoneLabel.setBounds(105, 64, 50, 13);
		contentPanel.add(PhoneLabel);

		PhoneField = new JTextField();
		PhoneField.setBounds(167, 61, 96, 19);
		contentPanel.add(PhoneField);
		PhoneField.setColumns(10);

		SendAuthCodeButton = new JButton("发送验证码");
		SendAuthCodeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String phone = PhoneField.getText();
				String username = UserField.getText();
				if (username.isEmpty()) {
					error("用户名为空");
					return;
				}
				if (phone.isEmpty()) {
					error("手机号为空");
					return;
				}
				if (!act.checkuser(username)) {
					error("用户不存在");
					return;
				}
				if (!Pattern.compile("^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$").matcher(phone)
						.find()) {
					error("手机号错误");
					return;
				}
				if (!act.checkuser(username, phone)) {
					error("手机号错误");
					return;
				}
				SendAuthCodeButton.setEnabled(false);
				UserField.setEditable(false);
				PhoneField.setEditable(false);
				code = String.format("%06d", new Random().nextInt(999999));
				Result a = AuthCode.sendPost(phone, "验证码：" + code + "。您正在找回 test 的密码 ，打死也不要告诉别人哦！");
				if (a.getId() < 0) {
					error(a.getMsg());
					return;
				}
				new Timer().schedule(new TimerTask() {
					int time = 0;

					@Override
					public void run() {
						time++;
						SendAuthCodeButton.setText("还剩" + (300 - time) + "秒");
						if (time > 300) {
							SendAuthCodeButton.setText("发送验证码");
							SendAuthCodeButton.setEnabled(true);
							super.cancel();
						}
					}
				}, 0, 1000);
			}
		});
		SendAuthCodeButton.setBounds(275, 60, 96, 21);
		contentPanel.add(SendAuthCodeButton);

		JLabel AuthCodeLabel = new JLabel("验证码");
		AuthCodeLabel.setBounds(105, 90, 50, 13);
		contentPanel.add(AuthCodeLabel);

		AuthCodeField = new JTextField();
		AuthCodeField.setBounds(167, 87, 96, 19);
		contentPanel.add(AuthCodeField);
		AuthCodeField.setColumns(10);

		JLabel label_1 = new JLabel("新密码");
		label_1.setBounds(105, 116, 50, 13);
		contentPanel.add(label_1);

		NPWDField = new JPasswordField();
		NPWDField.getDocument().addDocumentListener(new DocumentListener() {
			private void run() {
				String pwd = new String(NPWDField.getPassword());
				String rpwd = new String(RNPWDField.getPassword());
				if (!Pattern.compile("((?=.*\\d)(?=.*\\D)|(?=.*[a-zA-Z])(?=.*[^a-zA-Z]))^.{8,16}$").matcher(pwd)
						.find()){
					PwdStatus.setText("密码至少包含数字、字母");
					PwdTips.setText("、字符三种且为8-16位");
					okButton.setEnabled(false);
					return;
				}else{
					PwdStatus.setText("正确");
					PwdTips.setText("");
					
				}
				if (rfocus) {
					if (!pwd.equals(rpwd)) {
						PwdStatus.setText("验证密码错误");
						okButton.setEnabled(false);
						return;
					}else{
						PwdStatus.setText("正确");
						if(code==null)return;
						okButton.setEnabled(true);
					}
				}
				
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				run();

			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				run();

			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				run();

			}
		});
		NPWDField.setBounds(167, 113, 96, 19);
		contentPanel.add(NPWDField);
		NPWDField.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("验证密码");
		lblNewLabel_2.setBounds(105, 142, 50, 13);
		contentPanel.add(lblNewLabel_2);

		RNPWDField = new JPasswordField();
		RNPWDField.getDocument().addDocumentListener(new DocumentListener() {
			private void run() {
				rfocus = true;
				String pwd = new String(NPWDField.getPassword());
				String rpwd = new String(RNPWDField.getPassword());
				if (!pwd.equals(rpwd)) {
					PwdStatus.setText("验证密码错误");
					okButton.setEnabled(false);
					return;
				}else{
					PwdStatus.setText("正确");
					if(code==null)return;
				}
				okButton.setEnabled(true);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				run();

			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				run();

			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				run();

			}
		});
		RNPWDField.setBounds(167, 139, 96, 19);
		contentPanel.add(RNPWDField);
		RNPWDField.setColumns(10);

		PwdStatus = new JLabel("");
		PwdStatus.setBounds(275, 142, 150, 13);
		contentPanel.add(PwdStatus);
		
		PwdTips = new JLabel("");
		PwdTips.setBounds(275, 166, 150, 13);
		contentPanel.add(PwdTips);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setEnabled(false);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (AuthCodeField.getText().isEmpty()){
							error("请输入验证码");
							return;
						}
						if (AuthCodeField.getText().equals(code)) {
							String username = UserField.getText();
							String password = new String(NPWDField.getPassword());
							if (true)
								act.changePwd(username, MD5.getMd5(password));
							tip("修改成功");
							dispose();
						} else {
							error("验证码错误");
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
}
