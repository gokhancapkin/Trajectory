
package trajectoryserver;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONObject;

public class TrajectoryServer 
{


    private ServerSocket server;
    public TrajectoryServer(String ipAddress) throws Exception {
        if (ipAddress != null && !ipAddress.isEmpty()) 
          this.server = new ServerSocket(5555, 1, InetAddress.getByName(ipAddress));
        else 
          this.server = new ServerSocket(5555, 1, InetAddress.getLocalHost());
    }
    private void listen() throws Exception {
        String data = null;
        Point2D.Double nokta = null;
        ArrayList<Point2D.Double> points = new ArrayList<Point2D.Double>();
        ArrayList<Double> indirgenmis = new ArrayList<Double>();
        Socket client = this.server.accept();
        String clientAddress = client.getInetAddress().getHostAddress();
        System.out.println("\r\nNew connection from " + clientAddress);
        
        BufferedReader in = new BufferedReader(
        new InputStreamReader(client.getInputStream())); 
        data = in.readLine();
        //System.out.println(data);
        
        String[] latlng = data.split("/");
        double[][] Latlong = new double[latlng.length][2];
        for(int i = 0;i<Latlong.length;i++){
            String[] lat = data.split("-");
            Latlong[i][0] = Double.parseDouble(lat[0]);
            Latlong[i][1] = Double.parseDouble(lat[1]);
            nokta.setLocation(Latlong[i][0],Latlong[i][1]);
            points.add(nokta);
        }
        Arrays.asList(Latlong);
        DPIndirgeme indir = new DPIndirgeme();
        int first = points.size();
        DPIndirgeme.process(points, 1.0);
        Veri conv = new Veri();
        for(int i = 0;i<Latlong.length;i++){
            conv.setX(Latlong[i][0]);
            conv.setY(Latlong[i][1]);
        }
        Quadtree qt = new Quadtree();
        for(int i = 0;i<points.size();i++)
        {
            qt(points.get(i));
        }
        
        

        
    }
    public InetAddress getSocketAddress() {
        return this.server.getInetAddress();
    }
    
    public int getPort() {
        return this.server.getLocalPort();
    }
    public static void main(String[] args) throws Exception {
        TrajectoryServer app = new TrajectoryServer("192.168.1.21");
        System.out.println("\r\nRunning Server: " + 
                "Host=" + app.getSocketAddress().getHostAddress() + 
                " Port=" + app.getPort());
        
        app.listen();
    } 

    private void qt(Point2D.Double get) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
