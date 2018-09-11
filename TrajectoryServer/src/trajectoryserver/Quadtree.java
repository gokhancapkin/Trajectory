/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trajectoryserver;
import java.awt.geom.Point2D;
/**
 *
 * @author Gökhan
 */
public class Quadtree {

    public Quadtree(double x,double y) 
    {
        Point2D data = new Point2D.Double();
        data.setLocation(x,y);
        Tree tree=new Tree(); 
        tree.addNode(data);
    }

    Quadtree() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    static class TreeNode
    {
        public TreeNode KuzeyB;
        public TreeNode KuzeyD;
        public TreeNode GuneyB;
        public TreeNode GuneyD;
        public Point2D point;
    }
   static class Tree
    {
        public TreeNode root;
        public TreeNode current;
        public TreeNode newNode;
        public TreeNode parent;
        
        public Tree()
        {
            root=null;
        }
        
        public void addNode(Point2D newPoint)
        {
    
            newNode=new TreeNode ();
            newNode.point=newPoint;
         
            if(root==null)
            {
                root=newNode;
            }
            
            else 
                {
                    current=root;
                    
                    while(true)
                    {
                    parent=current;
                    
                        if(newPoint.getX()<=current.point.getX())
                            {
                                if(newPoint.getY()<=current.point.getY())
                                    {
                                        current=current.KuzeyB;
                                        if(current==null)
                                            {
                                            current.KuzeyB=newNode;
                                            }
                                    }
                                else
                                        {
                                        current=current.GuneyB;
                                        if(current==null)
                                            {
                                            current.GuneyB=newNode;
                                            }
                                        }
                            }
                        else
                            if(newPoint.getX()>current.point.getX())
                            {
                                if(newPoint.getY()<=current.point.getY())
                                    {
                                        current=current.KuzeyD;
                                        if(current==null)
                                            {
                                            current.KuzeyD=newNode;
                                            }
                                    }
                                else
                                        {
                                        current=current.GuneyD;
                                        if(current==null)
                                            {
                                            current.GuneyD=newNode;
                                            }
                                        }
                            }
                        
                        
                    }
                
                
                }
        }
   }
   
}
