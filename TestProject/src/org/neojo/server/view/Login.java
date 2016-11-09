package org.neojo.server.view;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.neojo.server.action.Action;
import org.neojo.server.entity.User;
import org.neojo.server.factory.BeanFactory;
import org.neojo.server.thread.BaseResLoad;
import org.neojo.server.thread.BaseRes;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login {

	private JFrame frame;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton LoginButton;
	private Action act = BeanFactory.getActionInstance("ActionImpl");

	private void error(String title, String msg) {
		JOptionPane.showMessageDialog(frame.getContentPane(), msg, title, JOptionPane.ERROR_MESSAGE);
	}

	private void tip(String msg) {
		JOptionPane.showMessageDialog(frame.getContentPane(), msg, "提示", JOptionPane.INFORMATION_MESSAGE);
	}

	private void login() {
		LoginButton.setEnabled(false);
		String username = usernameField.getText();
		String password = new String(passwordField.getPassword());
		if (username.isEmpty()) {
			error("提示", "请输入用户名!");
			usernameField.requestFocus();
			LoginButton.setEnabled(true);
			return;
		}
		if (password.isEmpty()) {
			error("提示", "请输入密码!");
			passwordField.requestFocus();
			LoginButton.setEnabled(true);
			return;
		}
		int a = act.loginAction(username, password);
		if (a == 1) {
			User user = act.getUser(username, password);
			BaseRes.setLoginUser(user);
			Thread load = new BaseResLoad();
			load.start();
			// frame.setVisible(false);
			new Main();
			frame.dispose();
		} else if (a == 2) {
			error("提示", "该用户未激活");
			usernameField.requestFocus();
			LoginButton.setEnabled(true);
		} else if (a == 3) {
			error("提示", "该用户已被封禁");
			usernameField.requestFocus();
			LoginButton.setEnabled(true);
		} else if (a == -1) {
			error("提示", "用户不存在!");
			usernameField.requestFocus();
			LoginButton.setEnabled(true);
		} else if (a == -2) {
			error("提示", "密码错误!");
			passwordField.requestFocus();
			LoginButton.setEnabled(true);
		} else if (a > 0) {
			error("提示", "该用户状态不正常，状态码为" + a);
			usernameField.requestFocus();
			LoginButton.setEnabled(true);
		} else {
			error("提示", "未知错误");
			LoginButton.setEnabled(true);
		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Login");
		int width = 368;
		int height = 234;
		int w = (Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2;
		int h = (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2;
		frame.setBounds(w, h, width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JLabel lblUsername = new JLabel("\u7528\u6237\u540D");
		lblUsername.setBounds(79, 123, 57, 13);
		JLabel lblPassword = new JLabel("\u5BC6\u7801");
		lblPassword.setBounds(79, 146, 50, 13);
		usernameField = new JTextField();
		usernameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					if (usernameField.getText().isEmpty()) {
						return;
					}
					passwordField.requestFocus();
				}
			}
		});
		usernameField.setBounds(138, 123, 117, 19);
		usernameField.setColumns(10);
		usernameField.setBorder(null);
		passwordField = new JPasswordField();
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					if (usernameField.getText().isEmpty()) {
						usernameField.requestFocus();
					}
					if ((new String(passwordField.getPassword())).isEmpty()) {
						return;
					}
					login();
				}
			}
		});
		passwordField.setBounds(138, 143, 117, 19);
		passwordField.setBorder(null);
		LoginButton = new JButton("\u767B\u5F55");
		LoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				login();
			}
		});
		LoginButton.setMnemonic(10);
		LoginButton.setBounds(138, 172, 117, 21);

		frame.getContentPane().add(lblUsername);
		frame.getContentPane().add(lblPassword);
		frame.getContentPane().add(usernameField);
		frame.getContentPane().add(passwordField);
		frame.getContentPane().add(LoginButton);

		JLabel lblNewLabel = new JLabel("账户激活");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				String username = usernameField.getText();
				String password = new String(passwordField.getPassword());
				if (username.isEmpty()) {
					error("错误", "请输入用户名");
					return;
				}
				if (password.isEmpty()) {
					error("错误", "请输入密码");
					return;
				}

				switch (act.loginAction(username, password)) {
				case 1:
					error("错误", "该用户不需要激活");
					break;
				case 2:
					if (act.actUser(username)) {
						tip("激活成功");
					} else {
						error("错误", "未知错误");
					}
					break;
				case 3:
					error("错误", "该用户已被封禁");
					break;
				case -1:
					error("错误", "不存在此用户");
					break;
				case -2:
					error("错误", "密码错误");
					break;
				default:
					error("错误", "该用户状态不正常");
					break;
				}

				// new ActiveUser().setVisible(true);
			}
		});
		lblNewLabel.setForeground(SystemColor.textHighlight);
		lblNewLabel.setBounds(267, 123, 50, 13);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("\u5FD8\u8BB0\u5BC6\u7801");
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				new FindPwd().setVisible(true);
			}
		});
		lblNewLabel_1.setForeground(SystemColor.textHighlight);
		lblNewLabel_1.setBounds(267, 146, 50, 13);
		frame.getContentPane().add(lblNewLabel_1);

	}
}
