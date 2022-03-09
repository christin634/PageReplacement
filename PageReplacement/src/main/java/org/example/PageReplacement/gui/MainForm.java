package org.example.PageReplacement.gui;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import org.example.PageReplacement.util.Util;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class MainForm extends JFrame {

	private static final long serialVersionUID = 1L;
	int vmSize;
	int pageSize;
	int seqSize;
	Integer[] seqList;
	Object[] tableTitle;// 列名

	private JPanel contentPane;
	private JTextField vmtf;
	private JTextField pagetf;
	private JTextField seqtf;
	private JTextPane seqTextPane = new JTextPane();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MainForm frame = new MainForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainForm() {
		setTitle("PageReplacement");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 530, 209);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("117px"),
				ColumnSpec.decode("left:max(40px;default)"),
				ColumnSpec.decode("5dlu"),
				ColumnSpec.decode("max(47dlu;default)"),
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(57dlu;default)"),
				ColumnSpec.decode("max(5dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.LINE_GAP_ROWSPEC,
				RowSpec.decode("32px"),
				RowSpec.decode("6dlu"),
				FormSpecs.DEFAULT_ROWSPEC,
				RowSpec.decode("6dlu"),
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("max(23dlu;default)"),}));

		JLabel lblNewLabel = new JLabel("物理块数:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel, "1, 2, center, center");

		vmtf = new JTextField();
		vmtf.setToolTipText("输入整数");
		vmtf.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(vmtf, "2, 2, center, center");
		vmtf.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("页面数:");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel_1, "3, 2, 2, 1, center, center");

		pagetf = new JTextField();
		pagetf.setToolTipText("输入整数");
		contentPane.add(pagetf, "5, 2, left, default");
		pagetf.setColumns(10);

		JButton generateSeqBtn = new JButton("产生随机访问序列");
		generateSeqBtn.setFocusPainted(false);
		generateSeqBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				generateSeqList();
			}
		});
		contentPane.add(generateSeqBtn, "7, 2");

		JLabel lblNewLabel_2 = new JLabel("页面访问序列大小:");
		contentPane.add(lblNewLabel_2, "1, 4, right, default");

		seqtf = new JTextField();
		seqtf.setToolTipText("输入整数");
		contentPane.add(seqtf, "2, 4, center, center");
		seqtf.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("算法:");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel_4, "1, 6, center, center");

		JComboBox<String> algorithmComb = new JComboBox<>(new String[] { "-请选择-", "FIFO", "LRU", "OPT" });
		contentPane.add(algorithmComb, "2, 6, fill, default");

		JButton btnRun = new JButton("运行");
		btnRun.setFocusPainted(false);
		btnRun.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String algorithm = (String) algorithmComb.getSelectedItem();
				if(algorithm.equals("-请选择-")) {
					JOptionPane.showMessageDialog(contentPane, "请选择算法！", "提示", JOptionPane.PLAIN_MESSAGE);
					return;
				}
				if(seqTextPane.getText().isEmpty()) {
					JOptionPane.showMessageDialog(contentPane, "请先生成随机页面访问序列！", "提示", JOptionPane.PLAIN_MESSAGE);
					return;
				}
				new ShowResultForm(algorithm, vmSize, seqList, tableTitle);
			}
		});
		contentPane.add(btnRun, "4, 6");

		JButton btnNewButton = new JButton("比较");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(seqTextPane.getText().isEmpty()) {
					JOptionPane.showMessageDialog(contentPane, "请先输入相关内容！", "提示", JOptionPane.PLAIN_MESSAGE);
					return;
				}
				new CompareForm(vmSize, seqList).setVisible(true);
			}
		});
		btnNewButton.setFocusPainted(false);
		contentPane.add(btnNewButton, "5, 6");

		JButton displayBtn = new JButton("演示");
		displayBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String algorithm = (String) algorithmComb.getSelectedItem();
				if(algorithm.equals("-请选择-")) {
					JOptionPane.showMessageDialog(contentPane, "请选择算法！", "提示", JOptionPane.PLAIN_MESSAGE);
					return;
				}
				if(seqTextPane.getText().isEmpty()) {
					JOptionPane.showMessageDialog(contentPane, "请先生成随机页面访问序列！", "提示", JOptionPane.PLAIN_MESSAGE);
					return;
				}
				new DynamicForm(seqList, vmSize, algorithm,tableTitle);
			}
		});
		contentPane.add(displayBtn, "7, 6");

		JLabel lblNewLabel_3 = new JLabel("随机访问序列:");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel_3, "1, 8");

		seqTextPane.setEditable(false);
		contentPane.add(seqTextPane, "2, 8, 9, 1, fill, center");

		setLocationRelativeTo(null);
	}

	public void generateSeqList() {// 产生随机访问序列
		String vmString = vmtf.getText();
		String pageString = pagetf.getText();
		String seqString = seqtf.getText();
		if (vmString.equals("") || pageString.equals("") || seqString.equals("")) {
			JOptionPane.showMessageDialog(contentPane, "请先输入相关内容！", "提示", JOptionPane.PLAIN_MESSAGE);
			return;
		}
		if (!Util.isInteger(seqString) || !Util.isInteger(pageString) || !Util.isInteger(vmString)) {
			JOptionPane.showMessageDialog(contentPane, "输入内容请勿包含数字外其它字符！", "提示", JOptionPane.PLAIN_MESSAGE);
			return;
		}
		vmSize = Integer.parseInt(vmString);
		seqSize = Integer.parseInt(seqString);
		pageSize = Integer.parseInt(pageString);
		if (seqSize != 0 && vmSize != 0 && pageSize != 0) {
			seqList = Util.generateSeq(pageSize, seqSize);
			seqTextPane.setText(Arrays.toString(seqList));
			// 初始化table列名
			tableTitle = new Object[seqList.length + 1];
			tableTitle[0] = "物理块数";
			for (int i = 0; i < seqList.length; i++)
				tableTitle[i + 1] = seqList[i];
		} else {
			JOptionPane.showMessageDialog(contentPane, "输入内容不能为0！", "提示", JOptionPane.PLAIN_MESSAGE);
			return;
		}
	}

}
