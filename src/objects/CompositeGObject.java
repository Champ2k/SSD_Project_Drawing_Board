package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class CompositeGObject extends GObject {

	private List<GObject> gObjects;

	public CompositeGObject() {
		super(0, 0, 0, 0);
		gObjects = new ArrayList<GObject>();
	}

	public void add(GObject gObject) {
			gObjects.add(gObject);
	}

	public void remove(GObject gObject) {
			gObjects.remove(gObject);
	}

	@Override
	public void move(int dX, int dY) {
			//we should move all GObject in list syntax: for (type variableName : arrayName)
		for(GObject i : gObjects){
			i.move(dX,dY);
		}
		x += dX;
		y += dY;
	}
	
	public void recalculateRegion() {
		//check all element in array
		int x = gObjects.get(0).x;
		int y = gObjects.get(0).y;
		int width =  gObjects.get(0).x + gObjects.get(0).width;
		int height = gObjects.get(0).y + gObjects.get(0).height;
		for(GObject i : gObjects){
			if(i.x < x){
				x = i.x;
			}
			if(i.y < y) {
				y = i.y;
			}
			if(i.x + i.width > width) {
				width = i.x + i.width;
			}
			if(i.y + i.height > height){
				height = i.y + i.height;
			}
			}
		this.x = x;
		this.y = y;
		this.width = width - x;
		this.height = height - y;
		}

	@Override
	public void paintObject(Graphics g) {
		for(GObject i : gObjects){
			i.paintObject(g);
		}
	}

	@Override
	public void paintLabel(Graphics g) {
		for(GObject i : gObjects){
			i.paintLabel(g);
		}
		g.drawString("Group",x,y+height+15);
	}
	
}
