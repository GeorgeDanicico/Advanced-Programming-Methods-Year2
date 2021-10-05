package Validations;

import CustomException.CustomException;

import java.util.Locale;

public class Validate {
    public static boolean isNumber(String number) throws CustomException {
        try {
            int intValue = Integer.parseInt(number);
            return true;
        } catch (NumberFormatException e) {
            throw new CustomException("You didn't provide an integer! \n");
        }
    }

    public static boolean validCarType(String type) throws CustomException {
        String lowercaseType = type.toLowerCase(Locale.ROOT);
        if (lowercaseType.equals("car") || lowercaseType.equals("truck") || lowercaseType.equals("motorbike")) {
            return true;
        } else {
            throw new CustomException("Invalid vehicle type! \n");
        }
    }

    public static boolean enoughParams(String[] args, int requiredParams) throws CustomException {
        if (requiredParams == args.length) {
            return true;
        }
        else {
            throw new CustomException("Invalid parameters given! \n");
        }
    }
}
