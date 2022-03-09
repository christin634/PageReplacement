package org.example.PageReplacement.algorithm;

import java.util.ArrayList;
import java.util.List;

public abstract class Algorithm {
	protected int vmSize; // 虚拟内存大小
	protected List<Integer> vmList = new ArrayList<>(); // 虚拟内存
	protected int empty; // 虚拟内存剩余
	protected Integer[] seqList; // 页面
	protected int countMissing = 0; // 缺页次数
	protected int p; // 指针，指向替换的页面
	protected int p1; // 指针，指向seqList
	protected Object[][] tableFIFO;// 表格数据;
	protected int[][] changecolor;// 数据标记:换页1，缺页2;

	public Algorithm(int vmSize, Integer[] seqList) {
		this.vmSize = vmSize;
		this.empty = vmSize;
		this.seqList = seqList;
		this.tableFIFO = new Object[vmSize + 1][seqList.length + 1];
		this.changecolor = new int[vmSize + 1][seqList.length + 1];
		for (int i = 0; i < vmSize; i++) {
			tableFIFO[i][0] = "第" + i + "块";
		}
		tableFIFO[vmSize][0] = "缺页";
	}

	public abstract void operation();

	public int getcountMissing() {
		return this.countMissing;
	}

	public Object[][] getTableFIFO() {
		return this.tableFIFO;
	}

	public int[][] getChangeColor() {
		return this.changecolor;
	}
}
