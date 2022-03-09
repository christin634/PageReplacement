package org.example.PageReplacement.algorithm;

public class AlgorithmFactory {
	public static Algorithm createAlgorithm(String algorithm, int vmSize, Integer[] seqList) {
		switch (algorithm) {
		case "FIFO":
			return new FIFO(vmSize, seqList);
		case "LRU":
			return new LRU(vmSize, seqList);
		case "OPT":
			return new OPT(vmSize, seqList);
		default:
			return null;
		}
	}
}
