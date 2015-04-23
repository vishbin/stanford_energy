package edu.stanford.widget.model;

import java.util.Map;


/**
 */
public class ProductBean {

	/** The productid. */
	String productid;
	
	/** The image url. */
	String imageUrl;
	
	/** The model number. */
	String modelNumber;
	
	/** The price. */
	String price;
	
	/** The decription. */
	String decription;
	
	/** The annual kwh. */
	String annualKwh;
	
	/** The life cycle energy cost. */
	String lifeCycleEnergyCost;
	
	/** The annual cost savings. */
	String annualCostSavings;

	
	/** The image dislay block. */
	String imageDislayBlock;
	
	
	/** The attribute. */
	Map<String,String> attribute;
	
	
		
	
	
	/**
	 * Gets the image dislay block.
	 *
	 * @return the image dislay block
	 */
	public String getImageDislayBlock() {
		return imageDislayBlock;
	}

	/**
	 * Sets the image dislay block.
	 *
	 * @param imageDislayBlock the new image dislay block
	 */
	public void setImageDislayBlock(String imageDislayBlock) {
		this.imageDislayBlock = imageDislayBlock;
	}

	/**
	 * Gets the attribute.
	 *
	 * @return the attribute
	 */
	public Map<String, String> getAttribute() {
		return attribute;
	}

	/**
	 * Sets the attribute.
	 *
	 * @param attribute the attribute
	 */
	public void setAttribute(Map<String, String> attribute) {
		this.attribute = attribute;
	}

	/**
	 * Gets the productid.
	 *
	 * @return the productid
	 */
	public String getProductid() {
		return productid;
	}

	/**
	 * Sets the productid.
	 *
	 * @param productid the new productid
	 */
	public void setProductid(String productid) {
		this.productid = productid;
	}

	/**
	 * Gets the image url.
	 *
	 * @return the image url
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * Sets the image url.
	 *
	 * @param imageUrl the new image url
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * Gets the model number.
	 *
	 * @return the model number
	 */
	public String getModelNumber() {
		return modelNumber;
	}

	/**
	 * Sets the model number.
	 *
	 * @param modelNumber the new model number
	 */
	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}

	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * Sets the price.
	 *
	 * @param price the new price
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * Gets the decription.
	 *
	 * @return the decription
	 */
	public String getDecription() {
		return decription;
	}

	/**
	 * Sets the decription.
	 *
	 * @param decription the new decription
	 */
	public void setDecription(String decription) {
		this.decription = decription;
	}

	/**
	 * Gets the annual kwh.
	 *
	 * @return the annual kwh
	 */
	public String getAnnualKwh() {
		return annualKwh;
	}

	/**
	 * Sets the annual kwh.
	 *
	 * @param annualKwh the new annual kwh
	 */
	public void setAnnualKwh(String annualKwh) {
		this.annualKwh = annualKwh;
	}

	/**
	 * Gets the life cycle energy cost.
	 *
	 * @return the life cycle energy cost
	 */
	public String getLifeCycleEnergyCost() {
		return lifeCycleEnergyCost;
	}

	/**
	 * Sets the life cycle energy cost.
	 *
	 * @param lifeCycleEnergyCost the new life cycle energy cost
	 */
	public void setLifeCycleEnergyCost(String lifeCycleEnergyCost) {
		this.lifeCycleEnergyCost = lifeCycleEnergyCost;
	}

	/**
	 * Gets the annual cost savings.
	 *
	 * @return the annual cost savings
	 */
	public String getAnnualCostSavings() {
		return annualCostSavings;
	}

	/**
	 * Sets the annual cost savings.
	 *
	 * @param annualCostSavings the new annual cost savings
	 */
	public void setAnnualCostSavings(String annualCostSavings) {
		this.annualCostSavings = annualCostSavings;
	}

}
