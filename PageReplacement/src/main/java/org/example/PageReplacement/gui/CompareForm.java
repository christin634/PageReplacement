package org.example.PageReplacement.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.example.PageReplacement.algorithm.Algorithm;
import org.example.PageReplacement.algorithm.AlgorithmFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class CompareForm extends JFrame {

	private static final long serialVersionUID = 1L;
	int vmSize;
	Integer[] seqList;

	public CompareForm(int vmSize, Integer[] seqList) {
		this.vmSize = vmSize;
		this.seqList = seqList;
		initUI();
		setBounds(140, 140, 380, 380);
	}

	private void initUI() {
		CategoryDataset dataset = createDataset();
		JFreeChart chart = createChart(dataset);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setBackground(Color.white);
		add(chartPanel);
		pack();
		setTitle("缺页率比较");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}

	private CategoryDataset createDataset() {

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		Algorithm algorithm = AlgorithmFactory.createAlgorithm("FIFO", vmSize, seqList);
		algorithm.operation();
		dataset.setValue((double) algorithm.getcountMissing() / seqList.length, "缺页率", "FIFO");
		algorithm = AlgorithmFactory.createAlgorithm("LRU", vmSize, seqList);
		algorithm.operation();
		dataset.setValue((double) algorithm.getcountMissing() / seqList.length, "缺页率", "LRU");
		algorithm = AlgorithmFactory.createAlgorithm("OPT", vmSize, seqList);
		algorithm.operation();
		dataset.setValue((double) algorithm.getcountMissing() / seqList.length, "缺页率", "OPT");
		return dataset;
	}

	private JFreeChart createChart(CategoryDataset dataset) {

		StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
		// 设置标题字体
		standardChartTheme.setExtraLargeFont(new Font("隶书", Font.BOLD, 20));
		// 设置图例的字体
		standardChartTheme.setRegularFont(new Font("宋书", Font.PLAIN, 15));
		// 设置轴向的字体
		standardChartTheme.setLargeFont(new Font("宋书", Font.PLAIN, 15));
		// 应用主题样式
		ChartFactory.setChartTheme(standardChartTheme);
		JFreeChart barChart = ChartFactory.createBarChart("三种算法缺页率比较", "", "缺页率", dataset, PlotOrientation.VERTICAL,
				false, true, false);
		CategoryPlot plot = barChart.getCategoryPlot();
		BarRenderer render = (BarRenderer) plot.getRenderer();
		render.setSeriesPaint(0, Color.cyan);
		render.setBarPainter(new StandardBarPainter());
		return barChart;
	}

}
