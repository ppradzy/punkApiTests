package validator;

public class AbvValueValidator {
    public static boolean isDouble(String abvValue) {
        try {
            Double.parseDouble(abvValue);
            return true;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean hasValidValue(String abvValue) {
        if(Double.parseDouble(abvValue) < 4.0) {
            return false;
        }
        return true;
    }
}
