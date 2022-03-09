package org.example.PageReplacement.algorithm;

import java.util.ArrayList;
import java.util.List;

import org.example.PageReplacement.util.Util;

/**
 * 最佳置换算法
 */
public class OPT extends Algorithm{

    public OPT(int vmSize,Integer[] seqList){
    	super(vmSize, seqList);
    }

    public boolean isMissing(Integer page){
        return !vmList.contains(page);
    }

	/**
	 * 遍历seqList，返回替换的页号
	 * @return 待替换页号
	 */
    public Integer traverse(){
        List<Integer> ls = new ArrayList<>();
        for(p=p1+1;p<seqList.length;p++){//从当前页的后一页开始
            if(vmList.contains(seqList[p]) && !ls.contains(seqList[p])){
                if(ls.size()<vmSize-1)
                    ls.add(seqList[p]);
                else break;
            }
        }
        return Util.compareList(vmList,ls);
    }

    @Override
    public void operation(){
        for(p1=0;p1<seqList.length;p1++){
            if(isMissing(seqList[p1])){//缺页
            	changecolor[vmSize][p1 + 1] = 2;
                countMissing++;
                if(empty!=0){//直接调入页面
                    empty--;
                    vmList.add(seqList[p1]);
                }
                else{//页面置换
                	int pageNum = traverse();
                	int pageLoc = vmList.indexOf(pageNum);
                    vmList.set(pageLoc, seqList[p1]);
                    changecolor[pageLoc][p1 + 1] = 1;
                }
            }
			for (int i = 0; i < vmList.size(); i++) {
				tableFIFO[i][p1 + 1] = vmList.get(i);
			}
        }
    }

}