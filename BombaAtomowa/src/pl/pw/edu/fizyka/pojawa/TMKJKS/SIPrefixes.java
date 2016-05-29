package pl.pw.edu.fizyka.pojawa.TMKJKS;

/**
 * 
 * @author Jacek Pi³ka
 * 
 * Changing E notation to SI prefixes and *10^x for bigger ones
 *
 */

public class SIPrefixes {
	
	String prefix="";
	double decimal=1;
	
	public String format(double value) {
		if(value>1_000_000_000_000_000_000_000_000_000.0){
			prefix="*10^27"; decimal=Math.pow(10.0, 27.0);
		}
		else if(value>1_000_000_000_000_000_000_000_000.0){
			prefix="*10^24"; decimal=Math.pow(10.0, 24.0);
		}
		else if(value>1_000_000_000_000_000_000_000.0){
			prefix="*10^21"; decimal=Math.pow(10.0, 21.0);
		}
		else if(value>1_000_000_000_000_000_000.0){
			prefix="*10^18"; decimal=Math.pow(10.0, 18.0);
		}
		else if(value>1_000_000_000_000_000.0){
			prefix="P"; decimal=Math.pow(10.0, 15.0);
		}
		else if(value>1_000_000_000_000.0){
			prefix="T"; decimal=Math.pow(10.0, 12.0);
		}
		else if(value>1_000_000_000.0){
			prefix="G"; decimal=Math.pow(10.0, 9.0);
		}
		else if(value>1_000_000.0){
			prefix="M"; decimal=Math.pow(10.0, 6.0);
		}
		else if(value>1_000.0){
			prefix="k"; decimal=Math.pow(10.0, 3.0);
		}
		else{
			prefix=""; decimal=Math.pow(10.0, 0);
		}
		
		return String.format("%.2f", value/decimal)+prefix;
	}
}
