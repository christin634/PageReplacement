package org.example.PageReplacement.gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.example.PageReplacement.algorithm.Algorithm;
import org.example.PageReplacement.algorithm.AlgorithmFactory;

public class ShowResultForm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	Object[][] data;// 存放FIFO页面置换结果
	int[][] changeColor;
	Integer[] seqList;

	/**
	 * Create the frame.
	 */
	public ShowResultForm(String algorithm,int vmSize,Integer[] seqList,Object[] tableTitle) {
		this.seqList = seqList;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(140, 140, 1048, 380);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		Algorithm algor = AlgorithmFactory.createAlgorithm(algorithm, vmSize, seqList);
		algor.operation();
		data = algor.getTableFIFO();
		changeColor = algor.getChangeColor();
		DefaultTableModel tableModel = new DefaultTableModel(data, tableTitle); // 表格模型对象
		JTable rsTable = new JTable(tableModel);
		int columnCount1 = rsTable.getColumnCount();
		rsTable.getColumnModel().getColumn(0).setPreferredWidth(60);
		for (int i = 1; i < columnCount1; i++) {
			TableColumn tableColumn1 = rsTable.getColumnModel().getColumn(i);
			tableColumn1.setPreferredWidth(35);
		}
		rsTable.setRowHeight(30);// 指定每一行的行高30
		rsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JScrollPane jsp = new JScrollPane(rsTable);
		jsp.setBounds(10, 10, 1048, 320);
		jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		TableCellRenderer tcr = new ColorTableCellRenderer(vmSize, changeColor);
		rsTable.setDefaultRenderer(Object.class, tcr);

		String title = String.format(algorithm+"算法  缺页率：%.2f，缺页次数：%d" , (double) algor.getcountMissing() / seqList.length,algor.getcountMissing());
		setTitle(title);
		contentPane.add(jsp);
		setVisible(true);

		setLocationRelativeTo(null);
	}

	// 自定义表格绘制器
	class ColorTableCellRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		int[][] flag;
		int vmSize;

		ColorTableCellRenderer(int vm, int[][] f) {
			vmSize = vm;
			flag = new int[1000][1000];
			// 缺页
			for (int i = 0; i < vmSize + 1; i++)
				for (int j = 0; j < seqList.length + 1; j++)
					flag[i][j] = f[i][j];
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (flag[row][column] == 1) {
				// 调用基类方法
				setBackground(Color.yellow); // 设置颜色
				return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			} else if (flag[row][column] == 2) {
				// 调用基类方法
				setBackground(Color.BLACK); // 设置颜色
				return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			} else {
				return renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			}
		}
	}
}
