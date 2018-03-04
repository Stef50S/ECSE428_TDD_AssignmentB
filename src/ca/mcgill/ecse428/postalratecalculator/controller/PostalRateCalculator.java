package ca.mcgill.ecse428.postalratecalculator.controller;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class PostalRateCalculator {

	//public static priceCalculator(String str1,String str2,String type)
	public static void main(String[] args) {
		
		
		if(args == null) {
			System.out.print("Must provide arguments!");
			return;
		}
		else if (args.length != 7) {
			System.out.print("Usage: FromPostalCode, ToPostalCode, Length (cm), Width (cm), Height (cm), Weight (kg), PostType");
			return;
		}
		else {
			String fromPC = args[0].replace(" ", ""), toPC = args[1].replace(" ", ""), postType = args[6];
			String sLength = args[2], sWidth = args[3], sHeight = args[4], sWeight = args[5];
			float length = 0, width = 0, height = 0, weight = 0;
			Pattern obj=Pattern.compile("([A-Z])(\\d)([A-Z])(\\d)([A-Z])(\\d)");
			Matcher match1=obj.matcher(fromPC);
			Matcher match2=obj.matcher(toPC);
			toPC=toPC.toUpperCase();
			fromPC=fromPC.toUpperCase();
			try {
				length = Float.parseFloat(sLength);
				width = Float.parseFloat(sWidth);
				height = Float.parseFloat(sHeight);
				weight = Float.parseFloat(sWeight);
			} catch (NumberFormatException e) {
				System.out.print("Measurements must be numbers!");
				return;
			}
			float girth = (width + height) * 2;
			if (height < 0.0 || weight < 0.0 || width < 0.0 || length < 0.0) {
				System.out.print("Measurements must be positive!");
				return;//change the order
			}
			
			else if (length < width || length < height) {
				System.out.print("Length must be longest side of parcel!");
				return;
			}
			
			else if (length > 200.0) {
				System.out.print("Length must not exceed 200.0 cm!");
				return;
			}
			
			else if (length < 0.10 || width < 0.10 || height < 0.10) {
				System.out.print("Measurements must be at least 0.10 cm!");
				return;
			}
			
			else if (weight > 30.0) {
				System.out.print("Weight must not exceed 30.0 kg!");
				return;
			}
			
			else if (!postType.equals("Regular") && !postType.equals("Xpress") && !postType.equals("Priority")) {
				System.out.print("Post Type must be either: Regular, Xpress or Priority");
				return;
			}
			else if(fromPC.length() !=6) {
				System.out.print("From postal code must have a length of 6");
				return;
			}
			
			else if ((length + girth) > 300.0) {
				System.out.print("Length plus girth must not exceed 300.0 cm!");
				return;
			}
			else if(toPC.length() !=6) {
				System.out.print("To postal code must have a length of 6");
				return;
			}
			else if(!match1.find()) {
				System.out.print("From postal code should have this format: letter number letter number letter number");
				return;
			}
			else if(!match2.find()) {
				System.out.print("To postal code should have this format: letter number letter number letter number");
				return;
			}
			//IF we had an error then we should ve returned above
			//so now we calculate
			float distance=Math.abs(toPC.charAt(0)-fromPC.charAt(0))/20;//dividing by 20 just to give us a "realistic" ratio
			float volumeRatio=((length*width*height)-6000)/6000;//with that we ll know the interval of the volume
			if(volumeRatio<0)
				volumeRatio=0;
			float Regprice=(float)9.59,Xprsprice=(float)11.64,Prioprice=(float)19.30;
			if(postType.equals("Regular")) {

			Regprice+=(float) (volumeRatio*0.7);//adding the 'Regular 'price for the volume
			Regprice+=weight*0.08;//adding the price for the weight
			Regprice*=1+distance+0.15;//calculating the price for the distance and the taxes
			
			System.out.format("Regular Price is:$%.2f",Regprice);
			}
			else if(postType.equals("Xpress")) {
				
				Xprsprice+=(float) (volumeRatio*0.87);//adding the 'Xpress 'price for the volume
				Xprsprice+=weight*0.24;//adding the price for the weight
				Xprsprice*=1+distance+0.15;//calculating the price for the distance and the taxes
				
				System.out.format("Xpress Price is:$%.2f",Xprsprice);
			}
			else if(postType.equals("Priority")) {
				
				Prioprice+=(float) (volumeRatio*1.09);//adding the 'Priority 'price for the volume
				Prioprice+=weight*0.48;//adding the price for the weight
				Prioprice*=1+distance+0.15;//calculating the price for the distance and the taxes
				
				System.out.format("Priority Price is:$%.2f",Prioprice);
			}

		}
	}

}
