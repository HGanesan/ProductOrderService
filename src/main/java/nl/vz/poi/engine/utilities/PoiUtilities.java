package nl.vz.poi.engine.utilities;

import nl.vz.poi.engine.core.request.ProductDetails;
import nl.vz.poi.engine.exception.InvalidEmailIdException;

public class PoiUtilities {
    /**
     * Method to validate productDetails.
     * @param productDetails ProductDetails
     * @throws InvalidEmailIdException Invalid emailId.
     */
    public static void validateRequest(ProductDetails productDetails) throws InvalidEmailIdException {
        if (!productDetails.getEmailId().matches(".+@.+\\..+")) {
            throw new InvalidEmailIdException("Please provide a valid email address");
        }
    }
}
