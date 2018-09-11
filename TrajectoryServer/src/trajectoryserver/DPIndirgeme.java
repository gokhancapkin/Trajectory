/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trajectoryserver;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Gökhan
 */
public class DPIndirgeme {
    static public List<Veri> process(List<Veri> vertices,
			Double distanceThreshold) {
		List<Veri> result = null;

		if (vertices != null) {
			result = simplificationOf(vertices, distanceThreshold);
		}

		return result;
	}

	private static List<Veri> simplificationOf(List<Veri> vertices, Double distanceThreshold) {
		List<Veri> simplifiedVertices = new ArrayList<Veri>();

		System.out.println("input: " + vertices);
		
		Double maxDistance = null;
		int maxDistancePointIdx = 0;

		int lastPointIdx = vertices.size()-1;

		int currentIdx = 0;
		for (Veri aVertex : vertices) {

			if (currentIdx != 0 && currentIdx != lastPointIdx) {
			
				Double distance = shortestDistanceToSegment(aVertex, vertices.get(0), vertices.get(lastPointIdx));
				//System.out.println("aVertex: " + aVertex + ", segment[" + vertices.get(0) + ", " + vertices.get(lastPointIdx) + "] -> distance: " + distance);
				if (maxDistance == null || distance > maxDistance) {
					maxDistancePointIdx = currentIdx;
					maxDistance = distance;
				}
			
			}
			currentIdx++;
		}

		if (maxDistance != null) {
			if (maxDistance > distanceThreshold) {
				List<Veri> sub = DPIndirgeme.process(vertices.subList(0, maxDistancePointIdx+1), distanceThreshold);
				List<Veri> sup = DPIndirgeme.process(vertices.subList(maxDistancePointIdx, lastPointIdx+1), distanceThreshold);
				
				simplifiedVertices.addAll(sub);
				simplifiedVertices.addAll(sup);
				
			} else {
				simplifiedVertices.add(vertices.get(0));
				simplifiedVertices.add(vertices.get(lastPointIdx));
			}
		}
		return simplifiedVertices;
	}

	static Double shortestDistanceToSegment(Veri thePoint,Veri segmentPoint_A,Veri segmentPoint_B) {
		Double area = calculateTriangleAreaGivenVertices(thePoint, segmentPoint_A, segmentPoint_B);
		Double lengthSegment = calculateDistanceBetweenTwoPoints(segmentPoint_A, segmentPoint_B);
		return (2 * area) / lengthSegment;
	}

	static Double calculateTriangleAreaGivenVertices(Veri a, Veri b, Veri c) {
		Double area = Math.abs(((a.getX() * (b.getY() - c.getY())) + (b.getX() * (c.getY() - a.getY())) + (c.getX() * (a.getY() - b.getY()))) / 2);
		return area;
	}

	static Double calculateDistanceBetweenTwoPoints(Veri a, Veri b) {
		Double distance = Math.sqrt(((a.getX() - b.getX()) * (a.getX() - b.getX())) + ((a.getY() - b.getY()) * (a.getY() - b.getY())));
		return distance;
	}

    static void process(ArrayList<Point2D.Double> points, double MIN_NORMAL) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
