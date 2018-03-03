package ca.mcgill.ecse428.postalratecalculator.controller;

public class PostalRateCalculator {

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
			String fromPC = args[0], toPC = args[1], postType = args[6];
			String sLength = args[2], sWidth = args[3], sHeight = args[4], sWeight = args[5];
			float length = 0, width = 0, height = 0, weight = 0;
			try {
				length = Float.parseFloat(sLength);
				width = Float.parseFloat(sWidth);
				height = Float.parseFloat(sHeight);
				weight = Float.parseFloat(sWeight);
			} catch (NumberFormatException e) {
				System.out.print("Measurements must be numbers!");
				return;
			}
			
			if (height < 0.0 || weight < 0.0 || width < 0.0 || length < 0.0) {
				System.out.print("Measurements must be positive!");
				return;
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
			
			float girth = (width + height) * 2;
			if (length + girth > 300.0) {
				System.out.print("Length plus girth must not exceed 300.0 cm!");
				return;
			}
			
		}

	}

}
