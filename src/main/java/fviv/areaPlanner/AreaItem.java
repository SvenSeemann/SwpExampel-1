package fviv.areaPlanner;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;

/**
 * 
 * @author Maximilian Schwarze
 *
 * Item of the area plan of the current festival
 *
 */

@Entity
public class AreaItem {
	
	public enum Type {
		TOILET, AREA, STAGE,BLOCKED, CATERING, CAMPING, PARKING
	}
	
	
	@javax.persistence.Id
	@GeneratedValue
	private long id;
	private long festivalId;
	private int width;
	private int height;
	private float yPos;
	private float xPos;
	private Type type;
	private String name;
	private float factor;
	public AreaItem(){
		
	}
	@Autowired 
	public AreaItem(Type type, String name, int width, int height, float xPos, float yPos, long festivalId) {
		this.width = width;
		this.height = height;
		this.xPos = xPos;
		this.yPos = yPos;
		this.type = type;
		this.name = name;
		this.festivalId = festivalId;
	}
	
	@Autowired
	public AreaItem(Type type, String name, int width, int height, float xPos, float yPos, float factor, long festivalId) {
		this.width = width;
		this.height = height;
		this.xPos = xPos;
		this.yPos = yPos;
		this.type = type;
		this.name = name;
		this.festivalId = festivalId;
		this.setFactor(factor);
	}
	public long getId(){
		return id;
	}
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getyPos() {
		return yPos;
	}

	public void setyPos(float yPos) {
		this.yPos = yPos;
	}

	public float getxPos() {
		return xPos;
	}

	public void setxPos(float xPos) {
		this.xPos = xPos;
	}
	
	public Type getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}
	public float getFactor() {
		return factor;
	}
	public void setFactor(float factor) {
		this.factor = factor;
	}
	@Override
	public String toString() {
		return "[width=" + width + ", height=" + height + ", yPos="
				+ yPos + ", xPos=" + xPos + ", type=" + type + ", name=" + name
				+ "]";
	}
	
}