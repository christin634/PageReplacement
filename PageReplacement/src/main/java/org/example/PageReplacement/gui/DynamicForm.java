package org.example.PageReplacement.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.example.PageReplacement.algorithm.Algorithm;
import org.example.PageReplacement.algorithm.AlgorithmFactory;

public class DynamicForm extends JFrame {
	private static final long serialVersionUID = 1L;
	public int count = 0;
	int vmSize = 0;
	Integer[] seqList;
	Object[][] data;
	Object[] tableTitle;
	Algorithm algori = null;
	ClockThread clockThread;
	JScrollPane jspdisplay;
	private JTable table;
	private JTextField result;

	public DynamicForm(Integer[] seqList, int vmSize, String algorithm, Object[] tableTitle) {
		this.seqList = seqList;
		this.vmSize = vmSize;
		this.tableTitle = tableTitle;

		this.algori = AlgorithmFactory.createAlgorithm(algorithm, vmSize, seqList);
		algori.operation();
		this.data = algori.getTableFIFO();

		// 实例化一个窗体;
		JFrame dynamicframe = new JFrame();
		dynamicframe.setTitle(algorithm + "动态演示");
		dynamicframe.setBounds(30, 30, 909, 393);
		dynamicframe.setDefaultCloseOperation(2);
		dynamicframe.getContentPane().setLayout(null);

		jspdisplay = new JScrollPane();
		jspdisplay.setBounds(192, 15, 693, 328);
		dynamicframe.getContentPane().add(jspdisplay);

		// 实例化运行功能面板
		JPanel functionPanel = new JPanel();
		functionPanel.setBackground(Color.WHITE);
		functionPanel.setToolTipText("");
		functionPanel.setLayout(null);
		functionPanel.setBounds(15, 15, 167, 328);
		dynamicframe.getContentPane().add(functionPanel);

		JButton begin = new JButton("开始");
		begin.setFocusPainted(false);
		begin.setBounds(15, 70, 133, 37);
		functionPanel.add(begin);

		JLabel lbldisplay = new JLabel();
		lbldisplay.setText("操作面板:");
		lbldisplay.setFont(UIManager.getFont("Label.font"));
		lbldisplay.setBounds(15, 30, 148, 37);
		functionPanel.add(lbldisplay);

		dynamicframe.setVisible(true);

		table = new JTable();
		jspdisplay.setViewportView(table);

		JButton stop = new JButton("停止");
		stop.setFocusPainted(false);
		stop.setBounds(15, 117, 133, 37);
		functionPanel.add(stop);

		result = new JTextField();
		result.setBackground(Color.WHITE);
		result.setEditable(false);
		result.setBorder(new EmptyBorder(0, 0, 0, 0));
		result.setBounds(0, 180, 167, 89);
		functionPanel.add(result);
		result.setColumns(10);
		stop.setVisible(false);

		stop.addActionListener(new ActionListener() {// 暂停
			@Override
			public void actionPerformed(ActionEvent arg0) {
				clockThread.exit = true;
			}
		});

		begin.addActionListener(new ActionListener() {// 自动运行
			@Override
			public void actionPerformed(ActionEvent arg0) {
				stop.setVisible(true);
				begin.setText("继续");
				clockThread = new ClockThread(DynamicForm.this);
				clockThread.start();
			}
		});

		setLocationRelativeTo(null);
	}

	public void onestep() {// 执行一步
		if (count <= seqList.length) {// 未执行完
			count++;
			Object[][] currData = new Object[vmSize][count];
			for (int i = 0; i < vmSize; i++) {
				for (int j = 0; j < count; j++) {
					currData[i][j] = data[i][j];
				}
			}
			DefaultTableModel tableModel = new DefaultTableModel(currData, tableTitle); // 表格模型对象
			table.setModel(tableModel);
			int columnCount1 = table.getColumnCount();
			table.getColumnModel().getColumn(0).setPreferredWidth(60);
			for (int i = 1; i < columnCount1; i++) {
				TableColumn tableColumn1 = table.getColumnModel().getColumn(i);
				tableColumn1.setPreferredWidth(35);
			}
			table.setRowHeight(30);// 指定每一行的行高30
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		} else {// 执行完了
			JOptionPane.showMessageDialog(null, "算法已结束");
			result.setText(String.format("缺页次数:%d\n\n,缺页率:%.2f", algori.getcountMissing(),
					(double) algori.getcountMissing() / seqList.length));
			clockThread.exit = true;
		}
	}
}

class ClockThread extends Thread {
	DynamicForm df;
	public volatile boolean exit = false;

	public ClockThread(DynamicForm df) {
		this.df = df;
	}

	@Override
	public void run() {
		super.run();
		while (!exit) {
			df.onestep();
			try {
				Thread.sleep(1000);// 暂停
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
