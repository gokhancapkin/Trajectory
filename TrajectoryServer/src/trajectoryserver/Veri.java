/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trajectoryserver;

/**
 *
 * @author Gökhan
 */
public class Veri {
    private double x;
	private double y;
	
	public Veri(double x, double y){
		this.x = x;
		this.y = y;
	}

    Veri() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	public double getX() {
		return x;
	}
        
	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	@Override
    public boolean equals(Object obj) {
        boolean result = false;
        if (obj instanceof Veri) {
            Veri JC = (Veri) obj;
            if (JC.toString().equals(toString())) {
                result = true;
            }
        } else {
            result = super.equals(obj);
        }
        return result;
    }
	
	 @Override
	 public String toString() {
		 return "(" + getX() + "," + getY() + ")";
	 }
	
	 @Override
	 public int hashCode() {
	     return toString().hashCode();
	 }
}
