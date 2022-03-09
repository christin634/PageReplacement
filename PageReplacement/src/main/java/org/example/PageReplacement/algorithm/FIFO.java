package org.example.PageReplacement.algorithm;

/**
 * 先进先出算法
 */
public class FIFO extends Algorithm{

    public FIFO(int vmSize,Integer[] seqList){
    	super(vmSize, seqList);
    }

    public boolean isMissing(Integer page){
        return !vmList.contains(page);
    }

	@Override
    public void operation(){
        p=0;
        for(p1=0;p1<seqList.length;p1++){
            if(isMissing(seqList[p1])){//缺页
            	changecolor[vmSize][p1+1]=2;
                countMissing++;
                if(empty!=0){//直接调入页面
                    empty--;
                    vmList.add(seqList[p1]);
                }
                else{//页面置换
                    vmList.set(p,seqList[p1]);
                    changecolor[p][p1+1]=1;
                    p++;
                    p=p%vmSize;
                }
            }
            for(int i=0;i<vmList.size();i++) {
            	tableFIFO[i][p1+1]=vmList.get(i);
            }
        }
    }


}
