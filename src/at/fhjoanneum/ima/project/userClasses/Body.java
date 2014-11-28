package at.fhjoanneum.ima.project.userClasses;

/**
 * record of the table t_body
 * stores values of the body measurements
 * @author Wakonigg
 *
 */
public class Body {
	private int id;
	private Float weight;
	private Float height;
	private Float chest;
	private Float shoulder;
	private Float hips;
	private Float waist;
	private Float thigh;
	private Float biceps;

	public Float getChest() {
		return chest;
	}

	public void setChest(Float chest) {
		this.chest = chest;
	}

	public Float getShoulder() {
		return shoulder;
	}

	public void setShoulder(Float shoulder) {
		this.shoulder = shoulder;
	}

	public Float getHips() {
		return hips;
	}

	public void setHips(Float hips) {
		this.hips = hips;
	}

	public Float getWaist() {
		return waist;
	}

	public void setWaist(Float waist) {
		this.waist = waist;
	}

	public Float getThigh() {
		return thigh;
	}

	public void setThigh(Float thigh) {
		this.thigh = thigh;
	}

	public Float getBiceps() {
		return biceps;
	}

	public void setBiceps(Float biceps) {
		this.biceps = biceps;
	}


	public Body(int id) {
		this.id = id;
	}

	public int getID() {
		return id;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public Float getHeight() {
		return height;
	}

	public void setHeight(Float height) {
		this.height = height;
	}

}
