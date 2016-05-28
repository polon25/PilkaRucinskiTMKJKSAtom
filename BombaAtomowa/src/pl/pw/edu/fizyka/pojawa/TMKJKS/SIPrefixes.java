package pl.pw.edu.fizyka.pojawa.TMKJKS;

import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;

public class SIPrefixes {
	private static final NavigableMap<Double, String> suffixes = new TreeMap<> ();
	static {
	  suffixes.put(1_000.0, "k");
	  suffixes.put(1_000_000.0, "M");
	  suffixes.put(1_000_000_000.0, "G");
	  suffixes.put(1_000_000_000_000.0, "T");
	  suffixes.put(1_000_000_000_000_000.0, "P");
	  suffixes.put(1_000_000_000_000_000_000.0, "*10^18");
	  suffixes.put(1_000_000_000_000_000_000_000.0, "*10^21");
	  suffixes.put(1_000_000_000_000_000_000_000_000.0, "*10^24");
	  suffixes.put(1_000_000_000_000_000_000_000_000_000.0, "*10^27");
	}

	public String format(double value) {
	  //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
	  if (value == Double.MIN_VALUE) return format(Double.MIN_VALUE + 1);
	  if (value < 0) return "-" + format(-value);
	  if (value < 1000) return Double.toString(value); //deal with easy case

	  Entry<Double, String> e = suffixes.floorEntry(value);
	  Double divideBy = e.getKey();
	  String suffix = e.getValue();

	  double truncated = value / (divideBy / 10); //the number part of the output times 10
	  boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
	  return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
	}
}
