package ru.qdts.training.streams;

import java.io.PrintStream;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Runner {
	final static PrintStream OUT = System.out;

	public static void main(String[] args) {
		List<SalesRec> list = SalesRecLoader.load("100000 Sales Records.csv");

		// timeTrial(list);
		 calculateStatistics(list);
	}

	static void calculateStatistics(List<SalesRec> list) {
		
		// ���������� ����������� ������ �������
		
		Optional<SalesRec> res1 = list.parallelStream().max((x,y)->Float.valueOf(x.getTotalCost()).compareTo(y.getTotalCost()));
		OUT.printf("%n%nMax Total Cost is %10.2f%n", res1.get().getTotalCost());
		
		// � ����� ������ ���� ��������� ��� �������?
		
		OUT.printf("%n%nCountry of Max Total Cost is %s%n", res1.get().getCountry());
		
		Optional<SalesRec> res2 = list.parallelStream().min((x,y)->Float.valueOf(x.getTotalCost()).compareTo(y.getTotalCost()));
		OUT.printf("%n%nMin Total Cost is %10.2f%n", res2.get().getTotalCost());
		
		// � ����� ������ ���� ��������� ��� �������?
		
		OUT.printf("%n%nCountry of Min Total Cost is %s%n", res2.get().getCountry());
		
		// ���������� ���������� ������ ������ �������� ��������� � ������ ������
		
		Map<String, Integer> res3 = list.stream().collect(Collectors.groupingBy(SalesRec::getCountry, Collectors.summingInt(SalesRec::getUnitsSold)));
		OUT.printf("%n%nCountry | Number Units Sold%n");
		for(String key : res3.keySet())
			OUT.printf("%s | %d%n", key, res3.get(key));
		
		// ���������� ������� ����� �������� �� ��������
		
		ToLongFunction<SalesRec> tlf = x -> x.getOrderDate().until(x.getShipDate(), ChronoUnit.DAYS);
		Map<String, Double> res4 = list.stream().collect(Collectors.groupingBy(SalesRec::getRegion, Collectors.averagingLong(tlf)));
		OUT.printf("%n%nRegion | Mean Sipping Time ( days )%n");
		for(String key : res4.keySet())
			OUT.printf("%s | %f%n", key, res4.get(key));
		
	}

	@SuppressWarnings(value = { "unchecked" })
	static void timeTrial(List<SalesRec> list) {
		final int ITER_NUM = 20;
		int iterCount = ITER_NUM;
		long sortTime = 0, sortTimeParal = 0;
		long maxTime = 0, maxTimeParal = 0;
		long minTime = 0, minTimeParal = 0;
		long mapTime = 0, mapTimeParal = 0, mapTimeParalUnord = 0;
		long sumTime = 0, sumTimeParal = 0, sumTimeParalUnord = 0, sumTimeLoop = 0;

		while (iterCount-- > 0) {
			sortTime += procTime((Stream<?> x) -> {
				sort((Stream<SalesRec>) x);
			}, list.stream());
			sortTimeParal += procTime((Stream<?> x) -> {
				sort((Stream<SalesRec>) x);
			}, list.parallelStream());
			maxTime += procTime((Stream<?> x) -> {
				max((Stream<SalesRec>) x);
			}, list.stream());
			maxTimeParal += procTime((Stream<?> x) -> {
				max((Stream<SalesRec>) x);
			}, list.stream());
				minTime += procTime((Stream<?> x) -> {
					min((Stream<SalesRec>) x);
				}, list.stream());
				minTimeParal += procTime((Stream<?> x) -> {
					min((Stream<SalesRec>) x);
			}, list.parallelStream());
			mapTime += procTime((Stream<?> x) -> {
				map((Stream<SalesRec>) x);
			}, list.stream());
			mapTimeParal += procTime((Stream<?> x) -> {
				map((Stream<SalesRec>) x);
			}, list.parallelStream());
			mapTimeParalUnord += procTime((Stream<?> x) -> {
				map((Stream<SalesRec>) x);
			}, list.parallelStream().unordered());
			sumTime += procTime((Stream<?> x) -> {
				sum((Stream<SalesRec>) x);
			}, list.stream());
			sumTimeParal += procTime((Stream<?> x) -> {
				sum((Stream<SalesRec>) x);
			}, list.parallelStream());
			sumTimeParalUnord += procTime((Stream<?> x) -> {
				sum((Stream<SalesRec>) x);
			}, list.parallelStream().unordered());
			sumTimeLoop += sumLoop(list);
		}

		long meanSortTime = sortTime / ITER_NUM;
		long meanSortTimeParal = sortTimeParal / ITER_NUM;
		long meanMaxTime = maxTime / ITER_NUM;
		long meanMaxTimeParal = maxTimeParal / ITER_NUM;
		long meanMinTime = minTime / ITER_NUM;
		long meanMinTimeParal = minTimeParal / ITER_NUM;
		long meanMapTime = mapTime / ITER_NUM;
		long meanMapTimeParal = mapTimeParal / ITER_NUM;
		long meanMapTimeParalUnord = mapTimeParalUnord / ITER_NUM;
		long meanSumTime = sumTime / ITER_NUM;
		long meanSumTimeParal = sumTimeParal / ITER_NUM;
		long meanSumTimeParalUnord = sumTimeParalUnord / ITER_NUM;
		long meanSumTimeLoop = sumTimeLoop / ITER_NUM;

		System.out.println("Mean time of operations in nanoseconds (% of Serial strem time)");
		System.out.println("Operation | Serial Stream | Parallel Ordered Stream | Parallel Unordered Stream | Loop");
		printProcTimeStat("SORT", meanSortTime, meanSortTimeParal, 0, 0);
		printProcTimeStat("MAX ", meanMaxTime, meanMaxTimeParal, 0, 0);
		printProcTimeStat("MIN ", meanMinTime, meanMinTimeParal, 0, 0);
		printProcTimeStat("MAP ", meanMapTime, meanMapTimeParal, meanMapTimeParalUnord, 0);
		printProcTimeStat("SUM ", meanSumTime, meanSumTimeParal, meanSumTimeParalUnord, meanSumTimeLoop);
	}

	static <T extends SalesRec> void sort(Stream<? extends SalesRec> str) {
		str.sorted(SalesRec::compareToUnitsSold).findFirst();
	}

	static void max(Stream<SalesRec> str) {
		str.max(SalesRec::compareToUnitsSold);
	}
	static void min(Stream<SalesRec> str) {
		str.min(SalesRec::compareToUnitsSold);
	}

	static void map(Stream<SalesRec> str) {
		str.map(x -> x.country).forEach(x -> {
		});
	}

	static void sum(Stream<SalesRec> str) {
		str.mapToInt(x -> x.unitsSold).sum();
	}

	static long sumLoop(List<SalesRec> list) {
		@SuppressWarnings("unused")
		long sum = 0;
		Instant start = Instant.now();
		for (SalesRec sr : list) {
			sum += sr.unitsSold;
		}
		Instant end = Instant.now();
		return start.until(end, ChronoUnit.NANOS);
	}

	// Returns time of given lambda-expression execution in nanoseconds
	public static long procTime(Consumer<Stream<?>> c, Stream<?> s) {
		Instant start = Instant.now();
		c.accept(s);
		Instant end = Instant.now();
		return start.until(end, ChronoUnit.NANOS);
	}

	// Prints procTime statistics in table-like format
	static void printProcTimeStat(String label, long s, long po, long puo, long l) {
		System.out.printf("%s | %d | %d ( %d%% )| %d ( %d%% ) | %d ( %d%% )%n",
				          label, s, po, (100 * po / s), puo, (100 * puo / s), l, (100 * l / s)); 
	}

}
