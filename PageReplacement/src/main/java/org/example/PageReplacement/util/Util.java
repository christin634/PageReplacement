package org.example.PageReplacement.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;

public class Util {

	public static boolean isInteger(String str) {//判断字符串是否只包含数字
		return str.matches("[0-9]+");
	}

	// vmList为当前内存中页面序列，ls2.size()=vmSize()-1
	public static Integer compareList(List<Integer> vmList, List<Integer> ls2) {
		List<Integer> ls = new ArrayList<>();
		// 将vmList拷贝到ls
		CollectionUtils.addAll(ls, new Object[vmList.size()]);
		Collections.copy(ls, vmList);
		for (Integer n : ls2) {//在ls中删除ls2中内容
			ls.remove(n);
		}
		return ls.get(0);
	}

	// 随机生成数[1,pagSize]
	public static Integer[] generateSeq(int pageSize, int seqSize) {
		Integer[] ls = new Integer[seqSize];
		Random r = new Random();
		for(int i=0;i<seqSize;i++) {
			ls[i]= r.nextInt(pageSize)+1;
		}
		return ls;
	}

}