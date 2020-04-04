package main;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import objects.*;

public class DrawingBoard extends JPanel {

	private MouseAdapter mouseAdapter; 
	private List<GObject> gObjects;
	private GObject target;
	
	private int gridSize = 10;
	
	public DrawingBoard() {
		gObjects = new ArrayList<GObject>();
		mouseAdapter = new MAdapter();
		addMouseListener(mouseAdapter);
		addMouseMotionListener(mouseAdapter);
		setPreferredSize(new Dimension(800, 600));
	}
	
	public void addGObject(GObject gObject) {
		gObjects.add(gObject);
		repaint();
	}
	
	public void groupAll() {
		CompositeGObject group = new CompositeGObject();
		for(GObject node : gObjects){
			group.add(node);
		}
		group.recalculateRegion();
		gObjects.clear();
		gObjects.add(group);
		repaint();

	}

	public void deleteSelected() {
		gObjects.remove(target);
		repaint();
	}
	
	public void clear() {
		gObjects.clear();
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		paintBackground(g);
		paintGrids(g);
		paintObjects(g);
	}

	private void paintBackground(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	private void paintGrids(Graphics g) {
		g.setColor(Color.lightGray);
		int gridCountX = getWidth() / gridSize;
		int gridCountY = getHeight() / gridSize;
		for (int i = 0; i < gridCountX; i++) {
			g.drawLine(gridSize * i, 0, gridSize * i, getHeight());
		}
		for (int i = 0; i < gridCountY; i++) {
			g.drawLine(0, gridSize * i, getWidth(), gridSize * i);
		}
	}

	private void paintObjects(Graphics g) {
		for (GObject go : gObjects) {
			go.paint(g);
		}
	}

	class MAdapter extends MouseAdapter {

		private int lx;
		private int ly;
		
		private void deselectAll() {
			for(GObject node: gObjects){
				node.deselected();
			}
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			this.lx = e.getX();
			this.ly = e.getY();
			deselectAll();
			for(GObject node : gObjects){
				if(node.pointerHit(lx, ly) && !node.getSelected()) {
					node.selected();
					target = node;
				}
			}
			repaint();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if(target.getSelected()){
				target.move(e.getX() - lx, e.getY() - ly);

				lx = e.getX();
				ly = e.getY();

				repaint();
			}
		}
	}
	
}